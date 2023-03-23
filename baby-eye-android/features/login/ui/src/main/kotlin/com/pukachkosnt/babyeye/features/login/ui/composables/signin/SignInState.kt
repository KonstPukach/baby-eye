package com.pukachkosnt.babyeye.features.login.ui.composables.signin

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.pukachkosnt.babyeye.core.commonui.input_fields.email
import com.pukachkosnt.babyeye.core.commonui.input_fields.password
import com.pukachkosnt.babyeye.core.commonui.input_fields.states.ValidatedState
import com.pukachkosnt.babyeye.core.commonui.input_fields.states.ValidatedTextInputFieldState
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidModel
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidationPipeline
import com.pukachkosnt.babyeye.features.login.ui.composables.signin.models.UiSignInModel

internal class SignInState(
    val emailState: ValidatedTextInputFieldState,
    val passwordState: ValidatedTextInputFieldState,
    override val validationPipeline: ValidationPipeline<UiSignInModel, SignInState>
) : ValidatedState<UiSignInModel>, Parcelable {

    private val validatedStatesList = listOf(emailState, passwordState)

    val uiSignInModel: UiSignInModel
        get() = UiSignInModel(email = emailState.text, password = passwordState.text)

    private val _validModel = mutableStateOf<ValidModel>(value = ValidModel.Valid)
    override val validModel: State<ValidModel> get() = _validModel

    override fun validate(forceShowError: Boolean): Boolean {
        val areStatesValid = validatedStatesList
            .onEach { state -> state.validate(forceShowError) }
            .firstOrNull { state -> !state.validModel.value.isValid }
            .let { invalid ->
                _validModel.value = ValidModel.Invalid()
                invalid == null
            }

        _validModel.value = validationPipeline.validate(
            validatedState = this, uiSignInModel, forceShowError)

        return _validModel.value.isValid && areStatesValid
    }

    // ############### PARCELABLE IMPLEMENTATION ###############

    @Suppress("DEPRECATION")
    private constructor(parcel: Parcel) : this(
        requireNotNull(parcel.readParcelable(ValidatedTextInputFieldState::class.java.classLoader)),
        requireNotNull(parcel.readParcelable(ValidatedTextInputFieldState::class.java.classLoader)),
        ValidationPipeline()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(emailState, flags)
        parcel.writeParcelable(passwordState, flags)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<SignInState> {
        override fun createFromParcel(parcel: Parcel): SignInState = SignInState(parcel)
        override fun newArray(size: Int): Array<SignInState?> = arrayOfNulls(size)
    }
}

@Composable
internal fun rememberSignInState(
    emailState: ValidatedTextInputFieldState = rememberSaveable { ValidatedTextInputFieldState.email() },
    passwordState: ValidatedTextInputFieldState = rememberSaveable { ValidatedTextInputFieldState.password() }
) = rememberSaveable(emailState, passwordState) {
    SignInState(emailState, passwordState, ValidationPipeline())
}
