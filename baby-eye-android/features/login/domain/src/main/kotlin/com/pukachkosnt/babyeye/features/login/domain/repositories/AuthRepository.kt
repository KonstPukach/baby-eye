package com.pukachkosnt.babyeye.features.login.domain.repositories

import com.pukachkosnt.babyeye.features.login.domain.models.LoginModel

interface AuthRepository {
    suspend fun register(loginData: LoginModel): Result<Unit>
    suspend fun login(loginData: LoginModel): Result<Unit>
}
