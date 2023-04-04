package com.pukachkosnt.babyeye.features.login.ui.mvi.signup

import com.pukachkosnt.mvi.NewsReducer
import javax.inject.Inject

internal sealed class SignUpNews {
    object NavigateToMainScreen : SignUpNews()
}

internal class SignUpNewsReducer @Inject constructor()
    : NewsReducer<SignUpModel, SignUpIntent, SignUpNews> {

    override fun invoke(state: SignUpModel, intent: SignUpIntent): List<SignUpNews> {
        return when (intent) {
            is SignUpIntent.SignUpSuccess -> listOf(SignUpNews.NavigateToMainScreen)
            else                          -> emptyList()
        }
    }
}
