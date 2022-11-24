package com.pukachkosnt.babyeye.core.commonui.validators.password

data class RepeatableField(
    val originalField: String,
    val repeatedField: String
)

infix fun String.repeats(that: String): RepeatableField = RepeatableField(that, this)
