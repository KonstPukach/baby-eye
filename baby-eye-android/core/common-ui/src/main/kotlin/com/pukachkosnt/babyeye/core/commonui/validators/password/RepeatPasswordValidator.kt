package com.pukachkosnt.babyeye.core.commonui.validators.password

import com.pukachkosnt.babyeye.core.commonui.R
import com.pukachkosnt.babyeye.core.commonui.utils.res.StringResourceLocator

class RepeatPasswordValidator : RepeatFieldValidator() {

    override fun validate(input: RepeatableFields): Boolean {
        require(input.repeatableFields.size <= 1) {
            "Number of repeatable fields must be 1 or less. " +
            "Use custom implementation of [RepeatFieldValidator] in other cases"
        }
        return super.validate(input)
    }

    override fun getErrorText(input: RepeatableFields): StringResourceLocator {
        return StringResourceLocator.from(R.string.passwords_are_not_the_same_err)
    }
}
