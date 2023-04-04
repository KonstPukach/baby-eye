package com.pukachkosnt.babyeye.features.login.ui.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pukachkosnt.babyeye.features.login.ui.mvi.signup.*
import com.pukachkosnt.mvi.launchActorBasedRedux
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class SignUpViewModel @Inject constructor(
    signUpReducer: SignUpReducer,
    signUpNewsReducer: SignUpNewsReducer,
    signUpActor: SignUpActor,
    savedState: SavedStateHandle
): ViewModel() {
    private val mviStore = launchActorBasedRedux(
        initialState = SignUpModel(),
        reducer      = signUpReducer,
        actor        = signUpActor,
        newsReducer  = signUpNewsReducer,
        savedState   = savedState
    )

    val state: StateFlow<SignUpModel> = mviStore.state

    fun signUp() {
        viewModelScope.launch {
            with(mviStore.state.value) {
                mviStore.queries.send(SignUpQuery.RequestSignUp(email.value, password.value))
            }
        }
    }

    fun onEmailFieldChanged(value: String) {
        mviStore.intents.trySend(SignUpIntent.EmailChanged(value))
    }

    fun onPasswordFieldChanged(value: String) {
        mviStore.intents.trySend(SignUpIntent.PasswordChanged(value))
    }

    fun onRepeatPasswordFieldChanged(value: String) {
        mviStore.intents.trySend(SignUpIntent.RepeatPasswordChanged(value))
    }

    fun onEmailFieldVisited() {
        if (!mviStore.state.value.email.showError) {
            mviStore.intents.trySend(SignUpIntent.EmailFieldVisited)
        }
    }

    fun onPasswordFieldVisited() {
        if (!mviStore.state.value.password.showError) {
            mviStore.intents.trySend(SignUpIntent.PasswordFieldVisited)
        }
    }

    fun onRepeatPasswordFieldVisited() {
        if (!mviStore.state.value.repeatPassword.showError) {
            mviStore.intents.trySend(SignUpIntent.RepeatPasswordFieldVisited)
        }
    }
}
