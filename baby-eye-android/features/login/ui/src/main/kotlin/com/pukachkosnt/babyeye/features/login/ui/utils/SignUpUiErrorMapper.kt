package com.pukachkosnt.babyeye.features.login.ui.utils

import com.pukachkosnt.babyeye.core.commonui.utils.err_mapper.DefaultUiErrorMapper
import com.pukachkosnt.babyeye.core.commonui.utils.res.StringResourceLocator
import com.pukachkosnt.babyeye.features.login.domain.models.UserAlreadyExistError
import com.pukachkosnt.babyeye.features.login.ui.R
import com.pukachkosnt.domain.Error
import javax.inject.Inject

internal class SignUpUiErrorMapper @Inject constructor() : DefaultUiErrorMapper() {
    override fun mapError(error: Error): StringResourceLocator = with(error) {
        return when (this) {
            is UserAlreadyExistError -> StringResourceLocator.from(R.string.user_already_exists)
            else                     -> super.mapError(error)
        }
    }
}
