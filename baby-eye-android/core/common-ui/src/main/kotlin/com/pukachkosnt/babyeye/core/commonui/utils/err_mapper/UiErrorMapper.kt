package com.pukachkosnt.babyeye.core.commonui.utils.err_mapper

import com.pukachkosnt.babyeye.core.commonui.viewmodel.LceDataState
import com.pukachkosnt.domain.Error

interface UiErrorMapper {
    fun <T : Any> mapError(error: Error): LceDataState.Error<T>
}
