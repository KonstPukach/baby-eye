package com.pukachkosnt.babyeye.features.login.ui.mvi.signin

import com.pukachkosnt.babyeye.features.login.domain.models.Email
import com.pukachkosnt.babyeye.features.login.domain.models.LoginModel
import com.pukachkosnt.babyeye.features.login.domain.models.Password
import com.pukachkosnt.babyeye.features.login.domain.usecases.LoginUseCase
import com.pukachkosnt.mvi.Actor
import kotlinx.coroutines.channels.SendChannel
import javax.inject.Inject

internal sealed class SignInQuery {

    data class RequestSignIn(
        val email: String,
        val password: String
    ) : SignInQuery()
}

internal class SignInActor @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : Actor<SignInModel, SignInQuery, SignInIntent> {

    override suspend fun invoke(
        state: SignInModel,
        query: SignInQuery,
        output: SendChannel<SignInIntent>
    ) {
        if (query is SignInQuery.RequestSignIn) {
            if (state.areFieldsValid) {
                output.send(SignInIntent.Loading)

                val loginModel = LoginModel(Email(query.email), Password(query.password))

                loginUseCase.login(loginModel)
                    .onSuccess { output.send(SignInIntent.SignInSuccess) }
                    .onFailure { error -> output.send(SignInIntent.SignInFailed(error)) }
            } else {
                output.send(SignInIntent.InvalidFormSent)
            }
        }
    }
}
