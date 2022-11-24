package com.pukachkosnt.babyeye.core.commonui.validators.password

import androidx.annotation.StringRes
import com.pukachkosnt.babyeye.core.commonui.R
import com.pukachkosnt.babyeye.core.commonui.utils.res.StringResourceLocator
import com.pukachkosnt.babyeye.core.commonui.validators.TextInputFieldValidator

class PasswordInputFieldValidator : TextInputFieldValidator {

    override fun validate(input: String): Boolean = getErrorStringId(input) == null

    override fun getErrorText(input: String) =
        getErrorStringId(input)?.let(StringResourceLocator::from)

    @StringRes
    private fun getErrorStringId(input: String): Int? = when {
        input.length < MIN_PASSWORD_LEN -> R.string.short_pswd_err
        input.length > MAX_PASSWORD_LEN -> R.string.long_pswd_err
        !input.matches(passwordRegex)   -> R.string.invalid_pswd_err
        else -> null
    }

    companion object {
        private const val MIN_PASSWORD_LEN = 8
        private const val MAX_PASSWORD_LEN = 20
        private val passwordRegex = Regex("(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).+")
    }
}
