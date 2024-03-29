package com.pukachkosnt.babyeye.remote.networkingimpl.auth

import android.content.SharedPreferences
import com.pukachkosnt.babyeye.remote.networking.auth.TokenStore
import com.pukachkosnt.babyeye.remote.networkingimpl.utils.failure
import javax.inject.Inject

class PrefsTokenStore @Inject constructor(private val prefs: SharedPreferences) : TokenStore {

    private var cachedToken: String? = null

    override val token: String?
        get() = cachedToken ?: prefs.getString(TOKEN_KEY, null)?.also {
            cachedToken = it
        }

    override val refreshToken: String?
        get() = prefs.getString(REFRESH_TOKEN_KEY, null)

    override fun saveToken(token: String): Result<Unit> {
        cachedToken = token
        return saveToPrefs(TOKEN_KEY, token)
    }

    override fun saveRefreshToken(refreshToken: String): Result<Unit> {
        return saveToPrefs(REFRESH_TOKEN_KEY, refreshToken)
    }

    private fun saveToPrefs(key: String, value: String): Result<Unit> {
        val result = prefs.edit()
            .putString(key, value)
            .commit()

        return if (result) Result.success(Unit) else Result.failure()
    }

    companion object {
        private const val TOKEN_KEY = "token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
    }
}
