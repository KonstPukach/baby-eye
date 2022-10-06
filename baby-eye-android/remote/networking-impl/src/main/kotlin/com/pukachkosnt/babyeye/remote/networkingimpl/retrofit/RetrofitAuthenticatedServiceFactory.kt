package com.pukachkosnt.babyeye.remote.networkingimpl.retrofit

import com.pukachkosnt.babyeye.remote.networking.auth.TokenStore
import com.pukachkosnt.babyeye.remote.networking.services.RefreshTokenService
import com.pukachkosnt.babyeye.remote.networkingimpl.BuildConfig
import com.pukachkosnt.babyeye.remote.networkingimpl.retrofit.interceptors.TokenInterceptor
import com.pukachkosnt.babyeye.remote.networkingimpl.retrofit.interceptors.UnauthErrorInterceptor
import com.pukachkosnt.babyeye.remote.networkingimpl.utils.normalizeUrl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitAuthenticatedServiceFactory(
    tokenStore: TokenStore,
    refreshTokenService: RefreshTokenService
) : ServiceFactory {

    private val retrofit: Retrofit

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(tokenStore))
            .addInterceptor(UnauthErrorInterceptor(tokenStore, refreshTokenService))
            .build()

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL.normalizeUrl())
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    override fun <T> createService(serviceClass: Class<T>): Lazy<T> = lazy {
        retrofit.create(serviceClass)
    }
}
