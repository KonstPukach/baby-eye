package com.pukachkosnt.babyeye.features.login.domain.repositories

import com.pukachkosnt.babyeye.features.login.domain.models.LoginModel
import com.pukachkosnt.domain.Result
import com.pukachkosnt.domain.Error

interface AuthRepository {
    suspend fun register(loginData: LoginModel): Result<Unit, Error>
    suspend fun login(loginData: LoginModel): Result<Unit, Error>
}
