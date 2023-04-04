package com.pukachkosnt.babyeye.core.commonui.utils.err_mapper

import com.pukachkosnt.babyeye.core.commonui.utils.res.StringResourceLocator
import com.pukachkosnt.domain.Error

interface UiErrorMapper {
    fun mapError(error: Error): StringResourceLocator?
}
