package com.pukachkosnt.babyeye.features.login.ui.mvi.signin

import android.os.Parcel
import android.os.Parcelable
import com.pukachkosnt.babyeye.core.commonui.field_models.InputFieldModel
import com.pukachkosnt.babyeye.core.commonui.field_models.TextInputFieldModel
import com.pukachkosnt.babyeye.core.commonui.field_models.emptyTextModel
import com.pukachkosnt.babyeye.core.commonui.utils.err_mapper.UiErrorMapper
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidationPipeline
import com.pukachkosnt.babyeye.features.login.domain.models.WrongPasswordError
import com.pukachkosnt.babyeye.features.login.ui.mvi.LoginResponseError
import com.pukachkosnt.babyeye.features.login.ui.mvi.ShowLoginErrorStrategy
import com.pukachkosnt.mvi.Reducer
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
internal data class SignInModel(
    val email: TextInputFieldModel         = InputFieldModel.emptyTextModel(isValid = false),
    val password: TextInputFieldModel      = InputFieldModel.emptyTextModel(isValid = false),
    val showLoadingScreen: Boolean         = false,
    val responseError: LoginResponseError? = null
) : Parcelable {
    val areFieldsValid: Boolean
        get() = email.validationResult.isValid && password.validationResult.isValid

    private companion object : Parceler<SignInModel> {
        override fun SignInModel.write(parcel: Parcel, flags: Int): Unit = with (parcel) {
            writeParcelable(email, flags)
            writeParcelable(password, flags)
            writeParcelable(responseError, flags)
        }

        @Suppress("DEPRECATION")
        override fun create(parcel: Parcel) = SignInModel(
            email             = parcel.readParcelable(InputFieldModel::class.java.classLoader)
                                ?: InputFieldModel.emptyTextModel(isValid = false),
            password          = parcel.readParcelable(InputFieldModel::class.java.classLoader)
                                ?: InputFieldModel.emptyTextModel(isValid = false),
            showLoadingScreen = false,
            responseError     = parcel.readParcelable(LoginResponseError::class.java.classLoader),
        )
    }
}

internal class SignInReducer @Inject constructor(
    private val emailValidationPipeline: ValidationPipeline<String>,
    private val passwordValidationPipeline: ValidationPipeline<String>,
    private val errorMapper: UiErrorMapper
) : Reducer<SignInModel, SignInIntent> {

    override fun invoke(state: SignInModel, intent: SignInIntent): SignInModel {
        return when (intent) {
            is SignInIntent.EmailChanged -> {
                state.copy(
                    email = state.email.copy(
                        value            = intent.email,
                        validationResult = emailValidationPipeline.validate(intent.email)
                    )
                )
            }
            is SignInIntent.PasswordChanged -> {
                state.copy(
                    password = state.password.copy(
                        value            = intent.password,
                        validationResult = passwordValidationPipeline.validate(intent.password)
                    )
                )
            }
            SignInIntent.EmailFieldVisited -> {
                state.copy(email = state.email.copy(
                    showError        = true,
                    validationResult = emailValidationPipeline.validate(state.email.value)
                ))
            }
            SignInIntent.PasswordFieldVisited -> {
                state.copy(password = state.password.copy(
                    showError        = true,
                    validationResult = passwordValidationPipeline.validate(state.password.value)
                ))
            }
            SignInIntent.Loading -> {
                state.copy(showLoadingScreen = true)
            }
            is SignInIntent.SignInFailed -> {
                state.copy(
                    showLoadingScreen = false,
                    responseError     = LoginResponseError(
                        errorText         = errorMapper.mapError(intent.error),
                        showErrorStrategy =
                            if (intent.error is WrongPasswordError)
                                ShowLoginErrorStrategy.INSIDE_FORM
                            else
                                ShowLoginErrorStrategy.TOAST
                    )
                )
            }
            SignInIntent.SignInSuccess -> {
                state.copy(showLoadingScreen = false)
            }
            SignInIntent.InvalidFormSent -> {
                state.copy(
                    email = state.email.copy(
                        showError        = true,
                        validationResult = emailValidationPipeline.validate(state.email.value)
                    ),
                    password = state.password.copy(
                        showError        = true,
                        validationResult = passwordValidationPipeline.validate(state.password.value)
                    ),
                    showLoadingScreen = false
                )
            }
        }
    }
}
