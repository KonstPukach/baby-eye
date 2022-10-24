package com.pukachkosnt.babyeye.features.login.ui.di

import com.pukachkosnt.babyeye.features.login.domain.di.LoginScreenScope
import com.pukachkosnt.babyeye.features.login.ui.LoginViewModel
import com.pukachkosnt.babyeye.features.login.ui.api.LoginDependencies
import dagger.Component

@Component(modules = [LoginModule::class], dependencies = [LoginDependencies::class])
@LoginScreenScope
internal interface LoginComponent {

    @Component.Factory
    interface Factory {
        fun create(dependencies: LoginDependencies): LoginComponent
    }

    fun createLoginViewModel(): LoginViewModel
}
