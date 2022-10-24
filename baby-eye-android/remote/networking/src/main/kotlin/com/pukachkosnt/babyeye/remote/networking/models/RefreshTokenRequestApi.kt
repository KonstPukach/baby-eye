package com.pukachkosnt.babyeye.remote.networking.models

import com.squareup.moshi.Json

data class RefreshTokenRequestApi(
    @Json(name = "token") val token: String,
    @Json(name = "refreshToken") val refreshToken: String
)
