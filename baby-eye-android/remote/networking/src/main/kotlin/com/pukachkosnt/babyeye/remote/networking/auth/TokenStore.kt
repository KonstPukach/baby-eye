package com.pukachkosnt.babyeye.remote.networking.auth

import com.pukachkosnt.domain.Result
import com.pukachkosnt.domain.Error

interface TokenStore {
    val token: String?
    val refreshToken: String?
    fun saveToken(token: String): Result<Unit, Error>
    fun saveRefreshToken(refreshToken: String): Result<Unit, Error>
}
