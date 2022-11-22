package com.pukachkosnt.babyeye.features.login.ui.composables.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.pukachkosnt.babyeye.core.commonui.input_fields.email
import com.pukachkosnt.babyeye.core.commonui.input_fields.show_err_strategy.RepeatPasswordShowErrorStrategy
import com.pukachkosnt.babyeye.core.commonui.input_fields.states.RepeatPasswordFormState
import com.pukachkosnt.babyeye.core.commonui.input_fields.states.ValidatedState
import com.pukachkosnt.babyeye.core.commonui.input_fields.states.ValidatedTextInputFieldState
import com.pukachkosnt.babyeye.core.commonui.input_fields.states.rememberRepeatPasswordFormState
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidModel
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidationPipeline
import com.pukachkosnt.babyeye.core.commonui.validators.password.RepeatPasswordValidator
import com.pukachkosnt.babyeye.features.login.ui.composables.signup.models.UiSignUpModel

internal class SignUpState(
    val emailState: ValidatedTextInputFieldState,
    val repeatPasswordFormState: RepeatPasswordFormState,
    override val validationPipeline: ValidationPipeline<UiSignUpModel, SignUpState>
) : ValidatedState<UiSignUpModel> {
    
    private val validatedStatesList = listOf(emailState, repeatPasswordFormState)

    val uiSignUpModel: UiSignUpModel
        get() = UiSignUpModel(
            email = emailState.text,
            password = repeatPasswordFormState.passwordState.text,
            repeatPassword = repeatPasswordFormState.repeatPasswordState.text,
        )

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
            validatedState = this, uiSignUpModel, forceShowError)

        return _validModel.value.isValid && areStatesValid
    }
}

@Composable
internal fun rememberSignUpState(
    emailState: ValidatedTextInputFieldState = remember { ValidatedTextInputFieldState.email() },
    repeatPasswordFormState: RepeatPasswordFormState = rememberRepeatPasswordFormState(
        validationPipeline = ValidationPipeline.from(
            RepeatPasswordValidator() to RepeatPasswordShowErrorStrategy
        )
    )
) = remember(emailState, repeatPasswordFormState) {
    SignUpState(emailState, repeatPasswordFormState, ValidationPipeline())
}
