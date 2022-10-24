package com.pukachkosnt.babyeye.remote.networkingimpl.retrofit

import com.pukachkosnt.babyeye.remote.networkingimpl.BuildConfig
import com.pukachkosnt.babyeye.remote.networkingimpl.utils.normalizeUrl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class RetrofitServiceFactory @Inject constructor() : ServiceFactory {

    private val retrofit: Retrofit

    init {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL.normalizeUrl())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    override fun <T> createService(serviceClass: Class<T>): Lazy<T> = lazy {
        retrofit.create(serviceClass)
    }
}
