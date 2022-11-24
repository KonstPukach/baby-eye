package com.pukachkosnt.babyeye.core.commonui.validators.model

import com.pukachkosnt.babyeye.core.commonui.input_fields.show_err_strategy.ShowErrorStrategy
import com.pukachkosnt.babyeye.core.commonui.input_fields.states.ValidatedState
import com.pukachkosnt.babyeye.core.commonui.validators.InputFieldValidator
import java.util.*

typealias ValidationQueue<T, VS> = Queue<Pair<InputFieldValidator<T>, ShowErrorStrategy<T, VS>>>

class ValidationPipeline<T : Any, VS : ValidatedState<T>>(
    private val validators: ValidationQueue<T, VS> = LinkedList()
) {
    fun validate(validatedState: VS, value: T, forceShowError: Boolean = false): ValidModel {
        return validators.firstOrNull { (validator, showErrorStrategy) ->
            !validator.validate(value) && (showErrorStrategy.canShowError(validatedState) || forceShowError)
        }?.let { (validator, showErrorStrategy) ->
            ValidModel.Invalid(
                errorText = validator.getErrorText(value),
                showError = showErrorStrategy.canShowError(validatedState) || forceShowError
            )
        } ?: ValidModel.Valid
    }

    companion object {
        fun <T : Any, VS : ValidatedState<T>> from(
            vararg validators: Pair<InputFieldValidator<T>, ShowErrorStrategy<T, VS>>
        ) : ValidationPipeline<T, VS> {
            require(validators.isNotEmpty()) { "Argument [validators] must be not empty" }

            val queue = LinkedList<Pair<InputFieldValidator<T>, ShowErrorStrategy<T, VS>>>()
                .apply { addAll(validators) }
            return ValidationPipeline(queue)
        }
    }
}
