package com.pukachkosnt.babyeye.di

import android.content.Context
import com.pukachkosnt.babyeye.remote.networking.auth.TokenStore
import com.pukachkosnt.babyeye.remote.networking.services.RefreshTokenService
import com.pukachkosnt.babyeye.remote.networkingimpl.auth.PrefsTokenStore
import com.pukachkosnt.babyeye.remote.networkingimpl.di.RetrofitAuthServiceFactoryQualifier
import com.pukachkosnt.babyeye.remote.networkingimpl.di.RetrofitServiceFactoryQualifier
import com.pukachkosnt.babyeye.remote.networkingimpl.retrofit.RetrofitAuthenticatedServiceFactory
import com.pukachkosnt.babyeye.remote.networkingimpl.retrofit.RetrofitServiceFactory
import com.pukachkosnt.babyeye.remote.networkingimpl.retrofit.ServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [MainActivityComponent::class])
interface AppModule {
    @Binds
    @Singleton
    @RetrofitServiceFactoryQualifier
    fun bindRetrofitServiceFactory(impl: RetrofitServiceFactory): ServiceFactory

    companion object {
        @Provides
        @Singleton
        fun provideTokenStore(context: Context): TokenStore =
            PrefsTokenStore(context.getSharedPreferences("TokenStore", Context.MODE_PRIVATE))

        @Provides
        @Singleton
        @RetrofitAuthServiceFactoryQualifier
        fun provideRetrofitAuthServiceFactory(
            @RetrofitServiceFactoryQualifier serviceFactory: ServiceFactory,
            tokenStore: TokenStore
        ): ServiceFactory {
            val refreshTokenService = serviceFactory
                .createService(RefreshTokenService::class.java).value
            return RetrofitAuthenticatedServiceFactory(tokenStore, refreshTokenService)
        }
    }
}
