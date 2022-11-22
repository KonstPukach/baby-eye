package com.pukachkosnt.babyeye.features.login.domain.models

import com.pukachkosnt.domain.Error

/**
 * All possible login screen errors
 */
class UserAlreadyExistError(errorText: String? = null) : Error(errorText)

class WrongPasswordError(errorText: String? = null) : Error(errorText)
