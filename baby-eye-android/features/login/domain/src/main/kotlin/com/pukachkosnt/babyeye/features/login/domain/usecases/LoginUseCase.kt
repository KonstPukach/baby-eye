package com.pukachkosnt.babyeye.features.login.domain.usecases

import com.pukachkosnt.babyeye.features.login.domain.models.LoginModel
import com.pukachkosnt.domain.Result
import com.pukachkosnt.domain.Error

interface LoginUseCase {
    suspend fun login(loginModel: LoginModel): Result<Unit, Error>
}
