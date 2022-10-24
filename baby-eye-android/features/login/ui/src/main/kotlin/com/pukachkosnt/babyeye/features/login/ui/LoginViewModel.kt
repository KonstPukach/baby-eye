package com.pukachkosnt.babyeye.features.login.ui

import androidx.lifecycle.ViewModel
import com.pukachkosnt.babyeye.features.login.domain.usecases.LoginUseCase
import com.pukachkosnt.babyeye.features.login.domain.usecases.RegisterUseCase
import javax.inject.Inject

internal class LoginViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel()
