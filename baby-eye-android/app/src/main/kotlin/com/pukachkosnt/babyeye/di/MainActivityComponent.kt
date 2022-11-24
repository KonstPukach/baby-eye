package com.pukachkosnt.babyeye.di

import androidx.navigation.NavHostController
import com.pukachkosnt.babyeye.features.login.ui.api.LoginCallback
import com.pukachkosnt.babyeye.features.login.ui.api.LoginDependencies
import com.pukachkosnt.babyeye.features.login.ui.api.LoginFeatureFactory
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Scope

@Scope
annotation class MainActivityScope

@Subcomponent(modules = [LoginDependenciesModule::class])
@MainActivityScope
interface MainActivityComponent : LoginDependencies {

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance navController: NavHostController,
            @BindsInstance loginCallback: LoginCallback
        ): MainActivityComponent
    }

    fun createLoginFeatureFactory(): LoginFeatureFactory
}
