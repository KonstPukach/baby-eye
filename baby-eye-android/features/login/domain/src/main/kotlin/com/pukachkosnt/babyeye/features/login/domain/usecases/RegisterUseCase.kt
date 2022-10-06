package com.pukachkosnt.babyeye.features.login.domain.usecases

import com.pukachkosnt.babyeye.features.login.domain.models.LoginModel

interface RegisterUseCase {
    suspend fun register(loginModel: LoginModel): Result<Unit>
}
