package com.pukachkosnt.babyeye.features.login.data.repositories

import com.pukachkosnt.babyeye.features.login.data.mappers.toApi
import com.pukachkosnt.babyeye.features.login.data.sources.LoginSource
import com.pukachkosnt.babyeye.features.login.domain.models.LoginModel
import com.pukachkosnt.babyeye.features.login.domain.models.UserAlreadyExistError
import com.pukachkosnt.babyeye.features.login.domain.models.WrongPasswordError
import com.pukachkosnt.babyeye.features.login.domain.repositories.AuthRepository
import com.pukachkosnt.babyeye.remote.networking.auth.TokenStore
import com.pukachkosnt.babyeye.remote.networking.models.AuthResponseApi
import com.pukachkosnt.domain.*
import retrofit2.Response
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val loginSource: LoginSource,
    private val tokenStore: TokenStore
) : AuthRepository {

    override suspend fun register(loginData: LoginModel): Result<Unit, Error> {
        return executeAuthentication {
            loginSource.register(loginData.toApi())
        }
    }

    override suspend fun login(loginData: LoginModel): Result<Unit, Error> {
        return executeAuthentication {
            loginSource.login(loginData.toApi())
        }
    }

    private suspend fun executeAuthentication(
        authOperation: suspend () -> Response<AuthResponseApi>
    ): Result<Unit, Error> {
        return try {
            val response = authOperation()

            response.mapToResult()
                .mapSuccess { response.body()?.let(::Success) ?: Failure(ServerError()) }
                .mapSuccess { tokens ->
                    tokenStore.saveToken(tokens.token).let { result ->
                        if (result.isFailure) return result
                    }
                    tokenStore.saveRefreshToken(tokens.refreshToken)
                }
        } catch (e: SocketTimeoutException) {
            Failure(NetworkTimeoutError(e.message))
        } catch (e: ConnectException) {
            Failure(NetworkConnectionError(e.message))
        }
    }

    private fun Response<AuthResponseApi>.mapToResult(): Result<Unit, Error> {
        return if (isSuccessful) {
            Success(Unit)
        } else {
            when (code()) {
                HttpURLConnection.HTTP_INTERNAL_ERROR -> ServerError()
                HttpURLConnection.HTTP_CONFLICT -> UserAlreadyExistError()
                HttpURLConnection.HTTP_UNAUTHORIZED -> WrongPasswordError()
                else -> UnknownError()
            }.let(::Failure)
        }
    }
}
