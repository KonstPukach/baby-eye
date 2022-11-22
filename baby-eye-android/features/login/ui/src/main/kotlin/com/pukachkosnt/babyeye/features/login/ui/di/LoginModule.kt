package com.pukachkosnt.babyeye.features.login.ui.di

import com.pukachkosnt.babyeye.core.commonui.utils.err_mapper.UiErrorMapper
import com.pukachkosnt.babyeye.features.login.data.repositories.AuthRepositoryImpl
import com.pukachkosnt.babyeye.features.login.data.sources.LoginSource
import com.pukachkosnt.babyeye.features.login.domain.di.LoginScreenScope
import com.pukachkosnt.babyeye.features.login.domain.repositories.AuthRepository
import com.pukachkosnt.babyeye.features.login.domain.usecases.LoginUseCase
import com.pukachkosnt.babyeye.features.login.domain.usecases.RegisterUseCase
import com.pukachkosnt.babyeye.features.login.domain.usecasesimpl.LoginUseCaseImpl
import com.pukachkosnt.babyeye.features.login.domain.usecasesimpl.RegisterUseCaseImpl
import com.pukachkosnt.babyeye.features.login.ui.LoginUiErrorMapper
import com.pukachkosnt.babyeye.features.login.ui.LoginViewModel
import com.pukachkosnt.babyeye.remote.networkingimpl.di.RetrofitServiceFactoryQualifier
import com.pukachkosnt.babyeye.remote.networkingimpl.retrofit.ServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

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
    fun bindLoginUiErrorMapper(impl: LoginUiErrorMapper): UiErrorMapper

    companion object {
        @Provides
        @LoginScreenScope
        fun provideViewModel(
            registerUseCase: RegisterUseCase,
            loginUseCase: LoginUseCase,
            loginUiErrorMapper: UiErrorMapper
        ): LoginViewModel = LoginViewModel(registerUseCase, loginUseCase, loginUiErrorMapper)

        @Provides
        @LoginScreenScope
        fun provideLoginSource(
            @RetrofitServiceFactoryQualifier retrofitServiceFactory: ServiceFactory
        ): LoginSource {
            return retrofitServiceFactory.createService(LoginSource::class.java).value
        }
    }
}
