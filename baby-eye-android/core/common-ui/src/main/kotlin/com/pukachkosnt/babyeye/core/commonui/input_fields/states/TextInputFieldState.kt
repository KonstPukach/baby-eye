package com.pukachkosnt.babyeye.core.commonui.input_fields.states

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.pukachkosnt.babyeye.core.commonui.utils.readBooleanAsByte
import com.pukachkosnt.babyeye.core.commonui.utils.writeBooleanAsByte

open class TextInputFieldState() : Parcelable {
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

    // ############### PARCELABLE IMPLEMENTATION ###############

    protected constructor(
        text: String?,
        isFocusedAtLeastOneTime: Boolean?,
        wasVisited: Boolean?
    ) : this() {
        this.text = text ?: ""
        this.isFocusedAtLeastOneTime = isFocusedAtLeastOneTime ?: false
        this.wasVisited = wasVisited ?: false
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(text)
        writeBooleanAsByte(isFocusedAtLeastOneTime)
        writeBooleanAsByte(wasVisited)
    }

    companion object CREATOR: Parcelable.Creator<TextInputFieldState?> {
        override fun createFromParcel(`in`: Parcel): TextInputFieldState {
            return TextInputFieldState(
                text = `in`.readString(),
                isFocusedAtLeastOneTime = `in`.readBooleanAsByte(),
                wasVisited = `in`.readBooleanAsByte()
            )
        }

        override fun newArray(size: Int): Array<TextInputFieldState?> = arrayOfNulls(size)
    }
}
