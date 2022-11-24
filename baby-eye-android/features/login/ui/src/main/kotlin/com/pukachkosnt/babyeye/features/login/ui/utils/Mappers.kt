package com.pukachkosnt.babyeye.features.login.ui.utils

import com.pukachkosnt.babyeye.features.login.domain.models.Email
import com.pukachkosnt.babyeye.features.login.domain.models.LoginModel
import com.pukachkosnt.babyeye.features.login.domain.models.Password
import com.pukachkosnt.babyeye.features.login.ui.composables.signin.models.UiSignInModel
import com.pukachkosnt.babyeye.features.login.ui.composables.signup.models.UiSignUpModel

internal fun UiSignInModel.toDomain(): LoginModel =
    LoginModel(email = Email(email), password = Password(password))

internal fun UiSignUpModel.toDomain(): LoginModel =
    LoginModel(email = Email(email), password = Password(password))
