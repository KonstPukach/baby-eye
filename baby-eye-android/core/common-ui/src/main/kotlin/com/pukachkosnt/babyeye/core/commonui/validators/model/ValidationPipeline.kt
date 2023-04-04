package com.pukachkosnt.babyeye.core.commonui.validators.model

import android.os.Parcelable
import com.pukachkosnt.babyeye.core.commonui.utils.res.StringResourceLocator
import com.pukachkosnt.babyeye.core.commonui.validators.InputFieldValidator
import kotlinx.parcelize.Parcelize

class ValidationPipeline<T : Any>(
    private val validators: List<InputFieldValidator<T>> = emptyList()
) {
    fun validate(value: T): ValidationResult {
        return validators
            .firstOrNull { validator-> !validator.validate(value) }
            ?.let { validator -> ValidationResult.error(validator.getErrorText(value)) }
            ?: ValidationResult.Valid
    }

    companion object {
        fun <T : Any> from(vararg validators: InputFieldValidator<T>) : ValidationPipeline<T> {
            require(validators.isNotEmpty()) { "Argument [validators] must be not empty" }
            return ValidationPipeline(validators.toList())
        }
    }
}

@Parcelize
class ValidationResult private constructor(
    val isValid: Boolean,
    val errorText: StringResourceLocator?
) : Parcelable {
    companion object {
        val Valid = ValidationResult(isValid = true, errorText = null)

        fun error(errorText: StringResourceLocator? = null) =
            ValidationResult(isValid = false, errorText)
    }
}
