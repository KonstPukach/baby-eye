package com.pukachkosnt.babyeye.features.login.data.sources

import com.pukachkosnt.babyeye.features.login.data.models.LoginRequestApi
import com.pukachkosnt.babyeye.remote.networking.models.AuthResponseApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginSource {
    @POST("Auth/register")
    suspend fun register(@Body loginModel: LoginRequestApi): Response<AuthResponseApi>

    @POST("Auth/login")
    suspend fun login(@Body loginModel: LoginRequestApi): Response<AuthResponseApi>
}
