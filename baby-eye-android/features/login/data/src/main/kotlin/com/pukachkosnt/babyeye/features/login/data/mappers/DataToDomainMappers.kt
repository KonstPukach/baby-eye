package com.pukachkosnt.babyeye.features.login.data.mappers

import com.pukachkosnt.babyeye.features.login.data.models.LoginRequestApi
import com.pukachkosnt.babyeye.features.login.domain.models.Email
import com.pukachkosnt.babyeye.features.login.domain.models.LoginModel
import com.pukachkosnt.babyeye.features.login.domain.models.Password

fun LoginRequestApi.toDomain(): LoginModel =
    LoginModel(email = Email(email), password = Password(password))
