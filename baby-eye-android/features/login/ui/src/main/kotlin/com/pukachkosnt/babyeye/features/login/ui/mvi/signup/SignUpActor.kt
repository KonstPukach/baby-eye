package com.pukachkosnt.babyeye.features.login.ui.mvi.signup

import com.pukachkosnt.babyeye.features.login.domain.models.Email
import com.pukachkosnt.babyeye.features.login.domain.models.LoginModel
import com.pukachkosnt.babyeye.features.login.domain.models.Password
import com.pukachkosnt.babyeye.features.login.domain.usecases.RegisterUseCase
import com.pukachkosnt.mvi.Actor
import kotlinx.coroutines.channels.SendChannel
import javax.inject.Inject

internal sealed class SignUpQuery {

    data class RequestSignUp(
        val email: String,
        val password: String
    ) : SignUpQuery()
}

internal class SignUpActor @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : Actor<SignUpModel, SignUpQuery, SignUpIntent> {

    override suspend fun invoke(
        state: SignUpModel,
        query: SignUpQuery,
        output: SendChannel<SignUpIntent>
    ) {
        if (query is SignUpQuery.RequestSignUp) {
            if (state.areFieldsValid) {
                output.send(SignUpIntent.Loading)

                val registerModel = LoginModel(Email(query.email), Password(query.password))

                registerUseCase.register(registerModel)
                    .onSuccess { output.send(SignUpIntent.SignUpSuccess) }
                    .onFailure { error -> output.send(SignUpIntent.SignUpFailed(error)) }
            } else {
                output.send(SignUpIntent.InvalidFormSent)
            }
        }
    }
}
