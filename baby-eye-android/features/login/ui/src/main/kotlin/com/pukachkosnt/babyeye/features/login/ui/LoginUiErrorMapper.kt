package com.pukachkosnt.babyeye.features.login.ui

import com.pukachkosnt.babyeye.core.commonui.utils.err_mapper.DefaultUiErrorMapper
import com.pukachkosnt.babyeye.core.commonui.viewmodel.LceDataState
import com.pukachkosnt.babyeye.features.login.domain.models.UserAlreadyExistError
import com.pukachkosnt.babyeye.features.login.domain.models.WrongPasswordError
import com.pukachkosnt.domain.Error
import javax.inject.Inject

internal class LoginUiErrorMapper @Inject constructor() : DefaultUiErrorMapper() {
    override fun <T : Any> mapError(error: Error): LceDataState.Error<T> = with(error) {
        return when (this) {
            is UserAlreadyExistError -> error(R.string.user_already_exists, false)
            is WrongPasswordError    -> error(R.string.wrong_password, false)
            else -> super.mapError(this)
        }
    }
}
