package com.pukachkosnt.babyeye.core.commonui.validators

import com.pukachkosnt.babyeye.core.commonui.utils.res.StringResourceLocator

interface InputFieldValidator<in T : Any> {
    fun validate(input: T): Boolean
    fun getErrorText(input: T): StringResourceLocator?
}
