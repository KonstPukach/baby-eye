package com.pukachkosnt.babyeye.features.login.ui.mvi.signup

import android.os.Parcel
import android.os.Parcelable
import com.pukachkosnt.babyeye.core.commonui.field_models.InputFieldModel
import com.pukachkosnt.babyeye.core.commonui.field_models.TextInputFieldModel
import com.pukachkosnt.babyeye.core.commonui.field_models.emptyTextModel
import com.pukachkosnt.babyeye.core.commonui.utils.err_mapper.UiErrorMapper
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidationPipeline
import com.pukachkosnt.babyeye.features.login.domain.models.UserAlreadyExistError
import com.pukachkosnt.babyeye.features.login.ui.mvi.LoginResponseError
import com.pukachkosnt.babyeye.features.login.ui.mvi.ShowLoginErrorStrategy
import com.pukachkosnt.mvi.Reducer
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
internal data class SignUpModel(
    val email: TextInputFieldModel          = InputFieldModel.emptyTextModel(isValid = false),
    val password: TextInputFieldModel       = InputFieldModel.emptyTextModel(isValid = false),
    val repeatPassword: TextInputFieldModel = InputFieldModel.emptyTextModel(isValid = true),
    val showLoadingScreen: Boolean          = false,
    val responseError: LoginResponseError?  = null
) : Parcelable {
    val areFieldsValid: Boolean
        get() = email.validationResult.isValid
                && password.validationResult.isValid
                && repeatPassword.validationResult.isValid

    private companion object : Parceler<SignUpModel> {
        override fun SignUpModel.write(parcel: Parcel, flags: Int): Unit = with (parcel) {
            writeParcelable(email, flags)
            writeParcelable(password, flags)
            writeParcelable(repeatPassword, flags)
            writeParcelable(responseError, flags)
        }

        @Suppress("DEPRECATION")
        override fun create(parcel: Parcel) = SignUpModel(
            email             = parcel.readParcelable(InputFieldModel::class.java.classLoader)
                                ?: InputFieldModel.emptyTextModel(isValid = false),
            password          = parcel.readParcelable(InputFieldModel::class.java.classLoader)
                                ?: InputFieldModel.emptyTextModel(isValid = false),
            repeatPassword    = parcel.readParcelable(InputFieldModel::class.java.classLoader)
                                ?: InputFieldModel.emptyTextModel(isValid = false),
            showLoadingScreen = false,
            responseError     = parcel.readParcelable(LoginResponseError::class.java.classLoader),
        )
    }
}

internal class SignUpReducer @Inject constructor(
    private val emailValidationPipeline: ValidationPipeline<String>,
    private val passwordValidationPipeline: ValidationPipeline<String>,
    private val repeatPasswordValidationPipeline: ValidationPipeline<Pair<String, String>>,
    private val errorMapper: UiErrorMapper
) : Reducer<SignUpModel, SignUpIntent> {

    override fun invoke(state: SignUpModel, intent: SignUpIntent): SignUpModel {
        return when (intent) {
            is SignUpIntent.EmailChanged -> {
                state.copy(
                    email = state.email.copy(
                        value            = intent.email,
                        validationResult = emailValidationPipeline.validate(intent.email)
                    )
                )
            }
            is SignUpIntent.PasswordChanged -> {
                state.copy(
                    password = state.password.copy(
                        value            = intent.password,
                        validationResult = passwordValidationPipeline.validate(intent.password)
                    )
                )
            }
            is SignUpIntent.RepeatPasswordChanged -> {
                state.copy(
                    repeatPassword = state.repeatPassword.copy(
                        value            = intent.repeatPassword,
                        validationResult = repeatPasswordValidationPipeline
                            .validate(state.password.value to intent.repeatPassword)
                    )
                )
            }
            SignUpIntent.EmailFieldVisited -> {
                state.copy(email = state.email.copy(
                    showError        = true,
                    validationResult = emailValidationPipeline.validate(state.email.value)
                ))
            }
            SignUpIntent.PasswordFieldVisited -> {
                state.copy(password = state.password.copy(
                    showError        = true,
                    validationResult = passwordValidationPipeline.validate(state.password.value)
                ))
            }
            SignUpIntent.RepeatPasswordFieldVisited -> {
                state.copy(repeatPassword = state.repeatPassword.copy(
                    showError        = true,
                    validationResult = repeatPasswordValidationPipeline
                        .validate(state.password.value to state.repeatPassword.value)
                ))
            }
            SignUpIntent.InvalidFormSent -> {
                state.copy(
                    email = state.email.copy(
                        showError        = true,
                        validationResult = emailValidationPipeline.validate(state.email.value)
                    ),
                    password = state.password.copy(
                        showError        = true,
                        validationResult = passwordValidationPipeline.validate(state.password.value)
                    ),
                    repeatPassword = state.repeatPassword.copy(
                        showError        = true,
                        validationResult = repeatPasswordValidationPipeline
                            .validate(state.password.value to state.repeatPassword.value)
                    ),
                    showLoadingScreen = false
                )
            }
            SignUpIntent.Loading -> {
                state.copy(showLoadingScreen = true)
            }
            SignUpIntent.SignUpSuccess -> {
                state.copy(showLoadingScreen = false)
            }
            is SignUpIntent.SignUpFailed -> {
                state.copy(
                    showLoadingScreen = false,
                    responseError     = LoginResponseError(
                        errorText         = errorMapper.mapError(intent.error),
                        showErrorStrategy =
                            if (intent.error is UserAlreadyExistError)
                                ShowLoginErrorStrategy.INSIDE_FORM
                            else
                                ShowLoginErrorStrategy.TOAST
                    )
                )
            }
        }
    }
}
