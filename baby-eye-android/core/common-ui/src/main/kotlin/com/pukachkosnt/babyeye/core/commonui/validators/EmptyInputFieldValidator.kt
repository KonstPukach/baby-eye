package com.pukachkosnt.babyeye.core.commonui.validators

import com.pukachkosnt.babyeye.core.commonui.R
import com.pukachkosnt.babyeye.core.commonui.utils.res.StringResourceLocator

class EmptyInputFieldValidator : TextInputFieldValidator {
    override fun validate(input: String) = input.isNotEmpty()
    override fun getErrorText(input: String) = StringResourceLocator.from(R.string.empty_field_err)
}
