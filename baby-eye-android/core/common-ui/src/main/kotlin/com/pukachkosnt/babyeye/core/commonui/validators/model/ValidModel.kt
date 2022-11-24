package com.pukachkosnt.babyeye.core.commonui.validators.model

import com.pukachkosnt.babyeye.core.commonui.utils.res.StringResourceLocator

sealed class ValidModel(val isValid: Boolean) {
    object Valid : ValidModel(isValid = true)

    data class Invalid(
        val errorText: StringResourceLocator? = null,
        val showError: Boolean = false
    ) : ValidModel(isValid = false)
}
