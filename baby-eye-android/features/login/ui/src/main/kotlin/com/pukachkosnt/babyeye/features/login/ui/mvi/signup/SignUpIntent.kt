package com.pukachkosnt.babyeye.features.login.ui.mvi.signup

import com.pukachkosnt.domain.Error

sealed class SignUpIntent {
    data class EmailChanged(val email: String) : SignUpIntent()

    data class PasswordChanged(val password: String) : SignUpIntent()

    data class RepeatPasswordChanged(val repeatPassword: String) : SignUpIntent()

    object EmailFieldVisited : SignUpIntent()

    object PasswordFieldVisited : SignUpIntent()

    object RepeatPasswordFieldVisited : SignUpIntent()

    object InvalidFormSent : SignUpIntent()

    object Loading : SignUpIntent()

    object SignUpSuccess : SignUpIntent()

    data class SignUpFailed(val error: Error) : SignUpIntent()
}