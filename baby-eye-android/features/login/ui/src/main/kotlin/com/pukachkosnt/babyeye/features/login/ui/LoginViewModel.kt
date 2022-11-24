package com.pukachkosnt.babyeye.features.login.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pukachkosnt.babyeye.core.commonui.utils.err_mapper.UiErrorMapper
import com.pukachkosnt.babyeye.core.commonui.viewmodel.LceDataState
import com.pukachkosnt.babyeye.features.login.domain.usecases.LoginUseCase
import com.pukachkosnt.babyeye.features.login.domain.usecases.RegisterUseCase
import com.pukachkosnt.babyeye.features.login.ui.api.LoginCallback
import com.pukachkosnt.babyeye.features.login.ui.composables.signin.models.UiSignInModel
import com.pukachkosnt.babyeye.features.login.ui.composables.signup.models.UiSignUpModel
import com.pukachkosnt.babyeye.features.login.ui.utils.toDomain
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class LoginViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val loginErrorMapper: UiErrorMapper,
    private val loginCallback: LoginCallback
) : ViewModel() {

    var loginState: LceDataState<Unit> by mutableStateOf(value = LceDataState.Idle())
        private set

    fun signIn(uiSignInModel: UiSignInModel) {
        viewModelScope.launch {
            loginState = LceDataState.Loading()
            loginUseCase.login(uiSignInModel.toDomain())
                .onSuccess {
                    loginState = LceDataState.Content(it)
                    loginCallback.onLoginDone()
                }
                .onFailure { loginState = loginErrorMapper.mapError(it) }
        }
    }

    fun signUp(uiSignUpModel: UiSignUpModel) {
        viewModelScope.launch {
            loginState = LceDataState.Loading()
            registerUseCase.register(uiSignUpModel.toDomain())
                .onSuccess {
                    loginState = LceDataState.Content(it)
                    loginCallback.onLoginDone()
                }
                .onFailure { loginState = loginErrorMapper.mapError(it) }
        }
    }

    fun cancelOperation() {
        try {
            viewModelScope.coroutineContext.cancelChildren()
            loginState = LceDataState.Content(Unit)
        } catch (e: IllegalStateException) {
            Log.e(this::class.simpleName, e.message.toString())
        }
    }
}
