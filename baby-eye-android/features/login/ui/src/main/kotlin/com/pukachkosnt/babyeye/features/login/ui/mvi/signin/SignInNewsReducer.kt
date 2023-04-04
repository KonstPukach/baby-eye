package com.pukachkosnt.babyeye.features.login.ui.mvi.signin

import com.pukachkosnt.mvi.NewsReducer
import javax.inject.Inject

internal sealed class SignInNews {
    object NavigateToMainScreen : SignInNews()
}

internal class SignInNewsReducer @Inject constructor()
    : NewsReducer<SignInModel, SignInIntent, SignInNews> {

    override fun invoke(state: SignInModel, intent: SignInIntent): List<SignInNews> {
        return when (intent) {
            is SignInIntent.SignInSuccess -> listOf(SignInNews.NavigateToMainScreen)
            else                          -> emptyList()
        }
    }
}
