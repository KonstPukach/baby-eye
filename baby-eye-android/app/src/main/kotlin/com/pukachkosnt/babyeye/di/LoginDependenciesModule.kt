package com.pukachkosnt.babyeye.di

import com.pukachkosnt.babyeye.features.login.ui.api.LoginFeatureFactory
import dagger.Module
import dagger.Provides

@Module
object LoginDependenciesModule {

    @Provides
    @MainActivityScope
    fun provideLoginFeatureFactory(dependencies: MainActivityComponent): LoginFeatureFactory {
        return LoginFeatureFactory(dependencies)
    }
}
