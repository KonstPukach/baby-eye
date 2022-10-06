package com.pukachkosnt.babyeye.remote.networking.services

import com.pukachkosnt.babyeye.remote.networking.models.AuthResponseApi
import com.pukachkosnt.babyeye.remote.networking.models.RefreshTokenRequestApi
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenService {
    @POST("Auth/refresh-token")
    fun refreshToken(@Body refreshToken: RefreshTokenRequestApi): Call<AuthResponseApi>
}
