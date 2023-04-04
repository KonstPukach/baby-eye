package com.pukachkosnt.babyeye.features.login.ui.utils

import com.pukachkosnt.babyeye.core.commonui.utils.res.StringResourceLocator
import com.pukachkosnt.babyeye.core.commonui.validators.InputFieldValidator
import com.pukachkosnt.babyeye.features.login.ui.R

internal class RepeatPasswordFieldValidator : InputFieldValidator<Pair<String, String>> {
    override fun validate(input: Pair<String, String>): Boolean {
        return input.first == input.second
    }

    override fun getErrorText(input: Pair<String, String>): StringResourceLocator {
        return StringResourceLocator.from(R.string.passwords_are_not_the_same_err)
    }
}
