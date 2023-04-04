package com.pukachkosnt.babyeye.features.login.ui.mvi

import android.os.Parcelable
import com.pukachkosnt.babyeye.core.commonui.utils.res.StringResourceLocator
import kotlinx.parcelize.Parcelize

@Parcelize
internal class LoginResponseError(
    val errorText: StringResourceLocator?,
    val showErrorStrategy: ShowLoginErrorStrategy
) : Parcelable {
        val showAsToast: Boolean
            get() = showErrorStrategy == ShowLoginErrorStrategy.TOAST
}

internal val LoginResponseError.textIfToast: StringResourceLocator?
    get() = errorText?.takeIf { showAsToast }

internal val LoginResponseError.textUnlessToast: StringResourceLocator?
    get() = errorText?.takeUnless { showAsToast }
