package com.pukachkosnt.babyeye.core.commonui.validators

import android.util.Patterns
import com.pukachkosnt.babyeye.core.commonui.R
import com.pukachkosnt.babyeye.core.commonui.utils.res.StringResourceLocator

class EmailInputFieldValidator : TextInputFieldValidator {
    override fun validate(input: String) = Patterns.EMAIL_ADDRESS.matcher(input).matches()
    override fun getErrorText(input: String) = StringResourceLocator.from(R.string.email_is_not_valid_error)
}
