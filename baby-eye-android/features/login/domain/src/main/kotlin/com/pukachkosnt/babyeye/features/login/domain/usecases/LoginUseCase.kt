package com.pukachkosnt.babyeye.features.login.domain.usecases

import com.pukachkosnt.babyeye.features.login.domain.models.LoginModel

interface LoginUseCase {
    suspend fun login(loginModel: LoginModel): Result<Unit>
}
