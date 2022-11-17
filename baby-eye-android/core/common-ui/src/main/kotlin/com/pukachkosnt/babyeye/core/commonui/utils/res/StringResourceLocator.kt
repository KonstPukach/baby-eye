package com.pukachkosnt.babyeye.core.commonui.utils.res

import android.content.Context
import androidx.annotation.StringRes

interface StringResourceLocator {
    fun getString(context: Context): String

    companion object {
        fun from(@StringRes stringResId: Int): StringResourceLocator {
            return IdResLocator(stringResId)
        }
    }
}

fun StringResourceLocator?.getStringOrEmpty(context: Context): String {
    return this?.getString(context) ?: ""
}

private class IdResLocator(@StringRes private val stringResId: Int) : StringResourceLocator {
    override fun getString(context: Context): String {
        return context.getString(stringResId)
    }
}
