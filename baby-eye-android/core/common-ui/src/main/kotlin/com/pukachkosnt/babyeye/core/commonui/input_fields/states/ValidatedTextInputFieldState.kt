package com.pukachkosnt.babyeye.core.commonui.input_fields.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidModel
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidationPipeline


class ValidatedTextInputFieldState(
    override val validationPipeline: ValidationPipeline<String, ValidatedTextInputFieldState>
) : IValidatedTextInputFieldState<String>() {

    private val _validModel: MutableState<ValidModel> = mutableStateOf(value = ValidModel.Valid)
    override val validModel: State<ValidModel> get() = _validModel

    override fun validate(forceShowError: Boolean): Boolean {
        _validModel.value = validationPipeline.validate(validatedState = this, text, forceShowError)
        return _validModel.value.isValid
    }

    companion object
}
