package com.pukachkosnt.babyeye.features.login.domain.usecasesimpl

import com.pukachkosnt.babyeye.features.login.domain.models.LoginModel
import com.pukachkosnt.babyeye.features.login.domain.repositories.AuthRepository
import com.pukachkosnt.babyeye.features.login.domain.usecases.LoginUseCase
import com.pukachkosnt.domain.Result
import com.pukachkosnt.domain.Error
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val loginRepository: AuthRepository
) : LoginUseCase {
    override suspend fun login(loginModel: LoginModel): Result<Unit, Error> {
        return loginRepository.login(loginModel)
    }
}
