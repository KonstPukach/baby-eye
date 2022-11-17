package com.pukachkosnt.babyeye.core.commonui.input_fields.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

open class TextInputFieldState {
    var text: String by mutableStateOf(value = "")

    private var _isFocused: Boolean by mutableStateOf(value = false)
    var isFocusedAtLeastOneTime: Boolean = false
        private set

    private var wasVisited: Boolean = false

    var isFocused: Boolean
        get() = _isFocused
        set(value) {
            _isFocused = value

            if (!value && wasVisited) isFocusedAtLeastOneTime = true
            if (_isFocused) wasVisited = true
        }
}
