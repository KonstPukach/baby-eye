package com.pukachkosnt.babyeye.features.login.data.models

import com.squareup.moshi.Json

data class LoginRequestApi(
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String
)
