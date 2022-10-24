package com.pukachkosnt.babyeye.features.login.data.mappers

import com.pukachkosnt.babyeye.features.login.data.models.LoginRequestApi
import com.pukachkosnt.babyeye.features.login.domain.models.LoginModel

fun LoginModel.toApi(): LoginRequestApi =
    LoginRequestApi(email = email.value, password = password.value)
