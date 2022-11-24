package com.pukachkosnt.babyeye.core.commonui.utils.err_mapper

import androidx.annotation.StringRes
import com.pukachkosnt.babyeye.core.commonui.R
import com.pukachkosnt.babyeye.core.commonui.utils.res.StringResourceLocator
import com.pukachkosnt.babyeye.core.commonui.viewmodel.LceDataState
import com.pukachkosnt.domain.Error
import com.pukachkosnt.domain.NetworkConnectionError
import com.pukachkosnt.domain.NetworkTimeoutError
import com.pukachkosnt.domain.ServerError

open class DefaultUiErrorMapper : UiErrorMapper {
    override fun <T : Any> mapError(error: Error): LceDataState.Error<T> = with(error) {
        return when (this) {
            is ServerError            -> error(R.string.server_error, true)
            is NetworkTimeoutError    -> error(R.string.response_timeout_error, true)
            is NetworkConnectionError -> error(R.string.internet_connection_error, true)
            else                      -> error(R.string.unknown_error, true)
        }
    }

    protected fun <T : Any> Error.error(@StringRes stringRes: Int, showAsToast: Boolean) =
        LceDataState.Error<T>(this, StringResourceLocator.from(stringRes), showAsToast)
}
