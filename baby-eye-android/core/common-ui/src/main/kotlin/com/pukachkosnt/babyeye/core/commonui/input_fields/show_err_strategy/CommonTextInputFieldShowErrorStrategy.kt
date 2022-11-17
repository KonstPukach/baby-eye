package com.pukachkosnt.babyeye.core.commonui.input_fields.show_err_strategy

import com.pukachkosnt.babyeye.core.commonui.input_fields.states.ValidatedTextInputFieldState

/**
 * Common text input field show error logic
 */
object CommonTextInputFieldShowErrorStrategy : ShowErrorStrategy<String, ValidatedTextInputFieldState> {
    override fun canShowError(validatedFieldState: ValidatedTextInputFieldState): Boolean {
        return with(validatedFieldState) {
            isFocusedAtLeastOneTime && text.isNotEmpty()
        }
    }
}
