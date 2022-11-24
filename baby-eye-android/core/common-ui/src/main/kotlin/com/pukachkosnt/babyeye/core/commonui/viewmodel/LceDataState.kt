package com.pukachkosnt.babyeye.core.commonui.viewmodel

import com.pukachkosnt.babyeye.core.commonui.utils.res.StringResourceLocator
import com.pukachkosnt.domain.Error as DomainError

/**
 * LCE:
 * - L - Loading
 * - C - Content
 * - E - Error
 */
sealed class LceDataState<T : Any> {
    open val data: T? get() = null

    class Loading<T : Any> : LceDataState<T>()

    class Content<T : Any>(override val data: T) : LceDataState<T>()

    class Error<T : Any>(
        val error: DomainError,
        val uiErrorText: StringResourceLocator,
        val showAsToast: Boolean = false
    ): LceDataState<T>()

    class Idle<T : Any> : LceDataState<T>()
}
