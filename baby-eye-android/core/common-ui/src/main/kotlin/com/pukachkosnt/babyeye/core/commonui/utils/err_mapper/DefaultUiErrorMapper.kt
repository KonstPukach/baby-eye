package com.pukachkosnt.babyeye.core.commonui.utils.err_mapper

import com.pukachkosnt.babyeye.core.commonui.R
import com.pukachkosnt.babyeye.core.commonui.utils.res.StringResourceLocator
import com.pukachkosnt.domain.Error
import com.pukachkosnt.domain.NetworkConnectionError
import com.pukachkosnt.domain.NetworkTimeoutError
import com.pukachkosnt.domain.ServerError

open class DefaultUiErrorMapper : UiErrorMapper {
    override fun mapError(error: Error): StringResourceLocator = with(error) {
        return when (this) {
            is ServerError            -> StringResourceLocator.from(R.string.server_error)
            is NetworkTimeoutError    -> StringResourceLocator.from(R.string.response_timeout_error)
            is NetworkConnectionError -> StringResourceLocator.from(R.string.internet_connection_error)
            else                      -> StringResourceLocator.from(R.string.unknown_error)
        }
    }
}
