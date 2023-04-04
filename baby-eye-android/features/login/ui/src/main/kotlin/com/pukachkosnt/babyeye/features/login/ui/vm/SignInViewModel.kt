package com.pukachkosnt.babyeye.features.login.ui.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pukachkosnt.babyeye.features.login.ui.mvi.signin.*
import com.pukachkosnt.babyeye.features.login.ui.mvi.signin.SignInActor
import com.pukachkosnt.babyeye.features.login.ui.mvi.signin.SignInModel
import com.pukachkosnt.babyeye.features.login.ui.mvi.signin.SignInNewsReducer
import com.pukachkosnt.babyeye.features.login.ui.mvi.signin.SignInReducer
import com.pukachkosnt.mvi.launchActorBasedRedux
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SignInViewModel @Inject constructor(
    signInReducer: SignInReducer,
    signInNewsReducer: SignInNewsReducer,
    signInActor: SignInActor,
    savedState: SavedStateHandle
) : ViewModel() {
    private val mviStore = launchActorBasedRedux(
        initialState = SignInModel(),
        reducer      = signInReducer,
        actor        = signInActor,
        newsReducer  = signInNewsReducer,
        savedState   = savedState
    )

    val state: StateFlow<SignInModel> get() = mviStore.state

    fun signIn() {
        viewModelScope.launch {
            with(mviStore.state.value) {
                mviStore.queries.send(SignInQuery.RequestSignIn(email.value, password.value))
            }
        }
    }

    fun onEmailFieldChanged(value: String) {
        mviStore.intents.trySend(SignInIntent.EmailChanged(value))
    }

    fun onPasswordFieldChanged(value: String) {
        mviStore.intents.trySend(SignInIntent.PasswordChanged(value))
    }

    fun onEmailFieldVisited() {
        if (!mviStore.state.value.email.showError) {
            mviStore.intents.trySend(SignInIntent.EmailFieldVisited)
        }
    }

    fun onPasswordFieldVisited() {
        if (!mviStore.state.value.password.showError) {
            mviStore.intents.trySend(SignInIntent.PasswordFieldVisited)
        }
    }
}
