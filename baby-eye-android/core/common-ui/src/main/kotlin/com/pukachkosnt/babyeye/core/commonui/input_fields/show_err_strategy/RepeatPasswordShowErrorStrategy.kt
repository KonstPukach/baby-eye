package com.pukachkosnt.babyeye.core.commonui.input_fields.show_err_strategy

import com.pukachkosnt.babyeye.core.commonui.input_fields.states.RepeatPasswordFormState
import com.pukachkosnt.babyeye.core.commonui.validators.password.RepeatableFields

/**
 * Common field show error logic
 */
object RepeatPasswordShowErrorStrategy : ShowErrorStrategy<RepeatableFields, RepeatPasswordFormState> {
    override fun canShowError(validatedFieldState: RepeatPasswordFormState): Boolean {
        return validatedFieldState.repeatPasswordState.isFocusedAtLeastOneTime
    }
}
