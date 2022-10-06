package com.pukachkosnt.babyeye.remote.networkingimpl.retrofit.interceptors

import com.pukachkosnt.babyeye.remote.networking.auth.TokenStore
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val tokenStore: TokenStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenStore.token

        return if (token == null) {
            chain.proceed(chain.request())
        } else {
            chain.request()
                .newBuilder()
                .header(AuthHeader, "Bearer ${tokenStore.token}")
                .build()
                .let(chain::proceed)
        }
    }

    companion object {
        private const val AuthHeader = "Authorization"
    }
}
