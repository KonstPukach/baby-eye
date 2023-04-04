package com.pukachkosnt.babyeye.core.commonui.field_models

import android.os.Parcelable
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidationResult
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class InputFieldModel<T : Serializable>(
    val value: T,
    val validationResult: ValidationResult,
    val showError: Boolean
) : Parcelable {
    companion object
}

typealias TextInputFieldModel = InputFieldModel<String>
fun InputFieldModel.Companion.emptyTextModel(isValid: Boolean) =
    TextInputFieldModel(
        value            = "",
        validationResult = if (isValid) ValidationResult.Valid else ValidationResult.error(),
        showError        = false
    )
