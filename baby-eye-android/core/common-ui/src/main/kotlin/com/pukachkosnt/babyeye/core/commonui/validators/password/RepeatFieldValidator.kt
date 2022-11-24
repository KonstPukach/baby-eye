package com.pukachkosnt.babyeye.core.commonui.validators.password

import com.pukachkosnt.babyeye.core.commonui.validators.InputFieldValidator

/**
 * Validates fields that need to repeated.
 *
 * **Example:** when user inputs password in the signup form he must repeat the password twice
 *
 * @see RepeatPasswordValidator
 */
abstract class RepeatFieldValidator : InputFieldValidator<RepeatableFields> {
    override fun validate(input: RepeatableFields): Boolean {
        return input.repeatableFields.none { (first, second) ->
            first != second
        }
    }
}
