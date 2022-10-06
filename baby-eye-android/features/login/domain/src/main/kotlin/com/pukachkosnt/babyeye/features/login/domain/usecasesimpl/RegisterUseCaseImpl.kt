package com.pukachkosnt.babyeye.features.login.domain.usecasesimpl

import com.pukachkosnt.babyeye.features.login.domain.models.LoginModel
import com.pukachkosnt.babyeye.features.login.domain.repositories.AuthRepository
import com.pukachkosnt.babyeye.features.login.domain.usecases.RegisterUseCase

class RegisterUseCaseImpl(private val loginRepository: AuthRepository) : RegisterUseCase {
    override suspend fun register(loginModel: LoginModel): Result<Unit> {
        return loginRepository.register(loginModel)
    }
}
