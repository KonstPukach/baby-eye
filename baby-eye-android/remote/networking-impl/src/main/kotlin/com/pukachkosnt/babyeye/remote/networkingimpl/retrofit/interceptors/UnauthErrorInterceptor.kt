package com.pukachkosnt.babyeye.remote.networkingimpl.retrofit.interceptors

import com.pukachkosnt.babyeye.remote.networking.auth.TokenStore
import com.pukachkosnt.babyeye.remote.networking.models.RefreshTokenRequestApi
import com.pukachkosnt.babyeye.remote.networking.services.RefreshTokenService
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection

class UnauthErrorInterceptor(
    private val tokenStore: TokenStore,
    private val refreshTokenService: RefreshTokenService
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            val token = tokenStore.token ?: return response
            val refreshToken = tokenStore.refreshToken ?: return response

            val refreshTokenRequest = RefreshTokenRequestApi(token, refreshToken)

            val authResponse = refreshTokenService
                .refreshToken(refreshTokenRequest)
                .execute()
                .body()
                ?: return response

            response.close()

            tokenStore.saveToken(authResponse.token)
            tokenStore.saveRefreshToken(authResponse.refreshToken)

            val requestWithNewToken = request.newBuilder()
                .header("Authorization", "Bearer ${authResponse.token}")
                .build()

            return chain.proceed(requestWithNewToken)
        }

        return response
    }
}
