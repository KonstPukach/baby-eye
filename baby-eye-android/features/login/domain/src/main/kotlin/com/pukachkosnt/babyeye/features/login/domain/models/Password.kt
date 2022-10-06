package com.pukachkosnt.babyeye.features.login.domain.models

@JvmInline
value class Password(val value: String) {
    init {
        require(value.length >= MIN_PASSWORD_LENGTH)
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
    }
}
