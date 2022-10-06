package com.pukachkosnt.babyeye.features.login.data.repositories

import com.pukachkosnt.babyeye.features.login.data.mappers.toApi
import com.pukachkosnt.babyeye.features.login.data.sources.LoginSource
import com.pukachkosnt.babyeye.features.login.domain.models.LoginModel
import com.pukachkosnt.babyeye.features.login.domain.repositories.AuthRepository
import com.pukachkosnt.babyeye.remote.networking.auth.TokenStore
import com.pukachkosnt.babyeye.remote.networking.models.AuthResponseApi
import retrofit2.Response

class AuthRepositoryImpl(
    private val loginSource: LoginSource,
    private val tokenStore: TokenStore
) : AuthRepository {

    override suspend fun register(loginData: LoginModel): Result<Unit> {
        return executeAuthentication {
            loginSource.register(loginData.toApi())
        }
    }

    override suspend fun login(loginData: LoginModel): Result<Unit> {
        return executeAuthentication {
            loginSource.login(loginData.toApi())
        }
    }

    private suspend fun executeAuthentication(
        authOperation: suspend () -> Response<AuthResponseApi>
    ): Result<Unit> {
        return try {
            val tokens = authOperation().body()
                ?: return Result.failure(NullPointerException())

            tokenStore.saveToken(tokens.token)
            tokenStore.saveRefreshToken(tokens.refreshToken)
            Result.success(Unit)
        } catch (e: java.lang.Exception) {
            Result.failure(e)
        }
    }
}
