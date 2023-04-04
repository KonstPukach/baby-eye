package com.pukachkosnt.babyeye.features.login.ui.di

import com.pukachkosnt.babyeye.core.commonui.utils.err_mapper.UiErrorMapper
import com.pukachkosnt.babyeye.core.commonui.validators.EmailInputFieldValidator
import com.pukachkosnt.babyeye.core.commonui.validators.EmptyInputFieldValidator
import com.pukachkosnt.babyeye.core.commonui.validators.model.ValidationPipeline
import com.pukachkosnt.babyeye.core.commonui.validators.password.PasswordInputFieldValidator
import com.pukachkosnt.babyeye.core.di.core.AssistedSavedStateVMFactory
import com.pukachkosnt.babyeye.features.login.data.repositories.AuthRepositoryImpl
import com.pukachkosnt.babyeye.features.login.data.sources.LoginSource
import com.pukachkosnt.babyeye.features.login.domain.di.LoginScreenScope
import com.pukachkosnt.babyeye.features.login.domain.repositories.AuthRepository
import com.pukachkosnt.babyeye.features.login.domain.usecases.LoginUseCase
import com.pukachkosnt.babyeye.features.login.domain.usecases.RegisterUseCase
import com.pukachkosnt.babyeye.features.login.domain.usecasesimpl.LoginUseCaseImpl
import com.pukachkosnt.babyeye.features.login.domain.usecasesimpl.RegisterUseCaseImpl
import com.pukachkosnt.babyeye.features.login.ui.utils.SignInUiErrorMapper
import com.pukachkosnt.babyeye.features.login.ui.mvi.signin.SignInActor
import com.pukachkosnt.babyeye.features.login.ui.mvi.signin.SignInNewsReducer
import com.pukachkosnt.babyeye.features.login.ui.mvi.signin.SignInReducer
import com.pukachkosnt.babyeye.features.login.ui.mvi.signup.SignUpActor
import com.pukachkosnt.babyeye.features.login.ui.mvi.signup.SignUpNewsReducer
import com.pukachkosnt.babyeye.features.login.ui.mvi.signup.SignUpReducer
import com.pukachkosnt.babyeye.features.login.ui.utils.RepeatPasswordFieldValidator
import com.pukachkosnt.babyeye.features.login.ui.utils.SignUpUiErrorMapper
import com.pukachkosnt.babyeye.features.login.ui.vm.SignInViewModel
import com.pukachkosnt.babyeye.features.login.ui.vm.SignUpViewModel
import com.pukachkosnt.babyeye.remote.networkingimpl.di.RetrofitServiceFactoryQualifier
import com.pukachkosnt.babyeye.remote.networkingimpl.retrofit.ServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
internal interface LoginModule {
    @Binds
    @LoginScreenScope
    fun bindLoginUseCase(impl: LoginUseCaseImpl): LoginUseCase

    @Binds
    @LoginScreenScope
    fun bindRegisterUseCase(impl: RegisterUseCaseImpl): RegisterUseCase

    @Binds
    @LoginScreenScope
    fun authRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @LoginScreenScope
    @Named("sign-in")
    fun bindSignInUiErrorMapper(impl: SignInUiErrorMapper): UiErrorMapper

    @Binds
    @LoginScreenScope
    @Named("sign-up")
    fun bindSignUpUiErrorMapper(impl: SignUpUiErrorMapper): UiErrorMapper

    companion object {
        @Provides
        @LoginScreenScope
        fun provideLoginSource(
            @RetrofitServiceFactoryQualifier retrofitServiceFactory: ServiceFactory
        ): LoginSource {
            return retrofitServiceFactory.createService(LoginSource::class.java).value
        }

        @Provides
        @LoginScreenScope
        fun provideSignInReducer(
            @Named("sign-in") errorMapper: UiErrorMapper
        ): SignInReducer {
            return SignInReducer(
                emailValidationPipeline = ValidationPipeline.from(
                    EmptyInputFieldValidator(),
                    EmailInputFieldValidator()
                ),
                passwordValidationPipeline = ValidationPipeline.from(
                    EmptyInputFieldValidator(),
                    PasswordInputFieldValidator()
                ),
                errorMapper = errorMapper
            )
        }

        @Provides
        @LoginScreenScope
        fun provideSignUpReducer(
            @Named("sign-up") errorMapper: UiErrorMapper
        ): SignUpReducer {
            return SignUpReducer(
                emailValidationPipeline = ValidationPipeline.from(
                    EmptyInputFieldValidator(),
                    EmailInputFieldValidator()
                ),
                passwordValidationPipeline = ValidationPipeline.from(
                    EmptyInputFieldValidator(),
                    PasswordInputFieldValidator()
                ),
                repeatPasswordValidationPipeline = ValidationPipeline.from(
                    RepeatPasswordFieldValidator()
                ),
                errorMapper = errorMapper
            )
        }

        @Provides
        @LoginScreenScope
        fun provideSignInVMFactory(
            signInReducer: SignInReducer,
            signInNewsReducer: SignInNewsReducer,
            signInActor: SignInActor
        ): AssistedSavedStateVMFactory<SignInViewModel> {
            return AssistedSavedStateVMFactory { savedStateHandle ->
                SignInViewModel(
                    signInReducer     = signInReducer,
                    signInNewsReducer = signInNewsReducer,
                    signInActor       = signInActor,
                    savedState        = savedStateHandle
                )
            }
        }

        @Provides
        @LoginScreenScope
        fun provideSignUpVMFactory(
            signUpReducer: SignUpReducer,
            signUpNewsReducer: SignUpNewsReducer,
            signUpActor: SignUpActor
        ): AssistedSavedStateVMFactory<SignUpViewModel> {
            return AssistedSavedStateVMFactory { savedStateHandle ->
                SignUpViewModel(
                    signUpReducer     = signUpReducer,
                    signUpNewsReducer = signUpNewsReducer,
                    signUpActor       = signUpActor,
                    savedState        = savedStateHandle
                )
            }
        }
    }
}
