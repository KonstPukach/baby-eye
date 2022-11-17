package com.pukachkosnt.babyeye.core.commonui.input_fields.show_err_strategy

import com.pukachkosnt.babyeye.core.commonui.input_fields.states.ValidatedState

/**
 * Defines the logic of cases when an error must be shown.
 *
 * `<T : Any>` - Type of data in a field,
 * `<VS : ValidatedState<T>>` - Type of state that need to be validated
 *
 * @see CommonTextInputFieldShowErrorStrategy
 */
fun interface ShowErrorStrategy<T : Any, VS : ValidatedState<T>> {
    fun canShowError(validatedFieldState: VS): Boolean
}
