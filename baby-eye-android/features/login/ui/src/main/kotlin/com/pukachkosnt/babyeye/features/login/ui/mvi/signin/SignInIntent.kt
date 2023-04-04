package com.pukachkosnt.babyeye.features.login.ui.mvi.signin

import com.pukachkosnt.domain.Error

internal sealed class SignInIntent {
    data class EmailChanged(val email: String) : SignInIntent()

    data class PasswordChanged(val password: String) : SignInIntent()

    object EmailFieldVisited : SignInIntent()

    object PasswordFieldVisited : SignInIntent()

    object InvalidFormSent : SignInIntent()

    object Loading : SignInIntent()

    object SignInSuccess : SignInIntent()

    data class SignInFailed(val error: Error) : SignInIntent()
}
