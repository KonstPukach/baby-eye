package com.pukachkosnt.babyeye.features.login.ui.composables.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.pukachkosnt.babyeye.core.commonui.input_fields.email
import com.pukachkosnt.babyeye.core.commonui.input_fields.password
import com.pukachkosnt.babyeye.core.commonui.input_fields.states.ValidatedState
import com.pukachkosnt.babyeye.core.commonui.input_fields.states.ValidatedTextInputFieldState
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidModel
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidationPipeline
import com.pukachkosnt.babyeye.features.login.ui.composables.signin.models.UiSignInModel

class SignInState(
    val emailState: ValidatedTextInputFieldState,
    val passwordState: ValidatedTextInputFieldState,
    override val validationPipeline: ValidationPipeline<UiSignInModel, SignInState>
) : ValidatedState<UiSignInModel> {

    private val validatedStatesList = listOf(emailState, passwordState)

    private val uiSignInModel: UiSignInModel
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
}

@Composable
fun rememberSignInState(
    emailState: ValidatedTextInputFieldState = remember { ValidatedTextInputFieldState.email() },
    passwordState: ValidatedTextInputFieldState = remember { ValidatedTextInputFieldState.password() }
) = remember(emailState, passwordState) {
    SignInState(emailState, passwordState, ValidationPipeline())
}
