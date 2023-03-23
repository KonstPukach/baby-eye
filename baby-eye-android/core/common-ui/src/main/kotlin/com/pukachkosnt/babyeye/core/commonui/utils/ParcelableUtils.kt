package com.pukachkosnt.babyeye.core.commonui.utils

import android.os.Parcel

private const val ONE: Byte = 1

fun Parcel.writeBooleanAsByte(value: Boolean) {
    val byte = (if (value) 1 else 0).toByte()
    writeByte(byte)
}

fun Parcel.readBooleanAsByte(): Boolean {
    return readByte() == ONE
}
