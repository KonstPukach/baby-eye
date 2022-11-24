package com.pukachkosnt.babyeye.features.login.domain.usecases

import com.pukachkosnt.babyeye.features.login.domain.models.LoginModel
import com.pukachkosnt.domain.Result
import com.pukachkosnt.domain.Error

interface RegisterUseCase {
    suspend fun register(loginModel: LoginModel): Result<Unit, Error>
}
