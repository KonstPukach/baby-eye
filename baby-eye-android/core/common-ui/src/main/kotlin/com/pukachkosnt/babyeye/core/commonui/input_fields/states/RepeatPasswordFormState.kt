package com.pukachkosnt.babyeye.core.commonui.input_fields.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.pukachkosnt.babyeye.core.commonui.input_fields.password
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidModel
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidationPipeline
import com.pukachkosnt.babyeye.core.commonui.validators.password.RepeatableField
import com.pukachkosnt.babyeye.core.commonui.validators.password.RepeatableFields
import com.pukachkosnt.babyeye.core.commonui.validators.password.SimpleRepeatable
import com.pukachkosnt.babyeye.core.commonui.validators.password.repeats

/**
 * Defines a state of an input password form with 2 fields: password field and repeat password field.
 */
class RepeatPasswordFormState(
    val passwordState: ValidatedTextInputFieldState,
    val repeatPasswordState: TextInputFieldState,
    override val validationPipeline: ValidationPipeline<RepeatableFields, RepeatPasswordFormState>,
) : ValidatedState<RepeatableFields> {

    private var _validModel = mutableStateOf<ValidModel>(value = ValidModel.Valid)
    override val validModel: State<ValidModel> get() = _validModel

    private var repeatableField: RepeatableField =
        repeatPasswordState.text repeats passwordState.text

    override fun validate(forceShowError: Boolean): Boolean {
        val isPasswordValid = passwordState.validate(forceShowError)

        val repeatable = SimpleRepeatable(repeatableField)
        _validModel.value = validationPipeline.validate(
            validatedState = this, repeatable, forceShowError)
        return _validModel.value.isValid && isPasswordValid
    }

    fun onPasswordValueChanged(newValue: String) {
        repeatableField = repeatableField.repeatedField repeats newValue
        validate()
    }

    fun onRepeatPasswordValueChanged(newValue: String) {
        repeatableField = newValue repeats repeatableField.originalField
        validate()
    }
}

@Composable
fun rememberRepeatPasswordFormState(
    passwordState: ValidatedTextInputFieldState = remember {
        ValidatedTextInputFieldState.password()
    },
    repeatPasswordState: TextInputFieldState = remember { TextInputFieldState() },
    validationPipeline: ValidationPipeline<RepeatableFields, RepeatPasswordFormState>
) = remember(passwordState, repeatPasswordState) {
    RepeatPasswordFormState(passwordState, repeatPasswordState, validationPipeline)
}
