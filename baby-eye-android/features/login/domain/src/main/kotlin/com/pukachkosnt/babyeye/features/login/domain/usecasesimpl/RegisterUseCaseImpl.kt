package com.pukachkosnt.babyeye.features.login.domain.usecasesimpl

import com.pukachkosnt.babyeye.features.login.domain.models.LoginModel
import com.pukachkosnt.babyeye.features.login.domain.repositories.AuthRepository
import com.pukachkosnt.babyeye.features.login.domain.usecases.RegisterUseCase
import com.pukachkosnt.domain.Result
import com.pukachkosnt.domain.Error
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(
    private val loginRepository: AuthRepository
) : RegisterUseCase {
    override suspend fun register(loginModel: LoginModel): Result<Unit, Error> {
        return loginRepository.register(loginModel)
    }
}
