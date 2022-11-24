package com.pukachkosnt.babyeye.features.login.ui.composables.signup.models

import com.pukachkosnt.babyeye.core.commonui.validators.password.RepeatableField
import com.pukachkosnt.babyeye.core.commonui.validators.password.RepeatableFields
import com.pukachkosnt.babyeye.core.commonui.validators.password.repeats

internal data class UiSignUpModel(
    val email: String,
    val password: String,
    val repeatPassword: String
) : RepeatableFields {
    override val repeatableFields: List<RepeatableField>
        get() = listOf(password repeats repeatPassword)
}
