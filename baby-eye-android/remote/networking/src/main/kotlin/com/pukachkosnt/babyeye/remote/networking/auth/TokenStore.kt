package com.pukachkosnt.babyeye.remote.networking.auth

interface TokenStore {
    val token: String?
    val refreshToken: String?
    fun saveToken(token: String): Result<Unit>
    fun saveRefreshToken(refreshToken: String): Result<Unit>
}
