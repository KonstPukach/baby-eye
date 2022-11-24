package com.pukachkosnt.babyeye.core.commonui.utils.compose

import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

private val PasswordKeyboardOption = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
val KeyboardOptions.Companion.Password: KeyboardOptions
    get() = PasswordKeyboardOption

private val EmailKeyboardOption = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
val KeyboardOptions.Companion.Email: KeyboardOptions
    get() = EmailKeyboardOption

fun KeyboardActions.Companion.next(onNext: KeyboardActionScope.() -> Unit) =
    KeyboardActions(onNext = onNext)
