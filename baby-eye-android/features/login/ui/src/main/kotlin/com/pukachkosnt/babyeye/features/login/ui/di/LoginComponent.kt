package com.pukachkosnt.babyeye.features.login.ui.di

import com.pukachkosnt.babyeye.core.di.core.AssistedSavedStateVMFactory
import com.pukachkosnt.babyeye.features.login.domain.di.LoginScreenScope
import com.pukachkosnt.babyeye.features.login.ui.api.LoginDependencies
import com.pukachkosnt.babyeye.features.login.ui.vm.SignInViewModel
import com.pukachkosnt.babyeye.features.login.ui.vm.SignUpViewModel
import dagger.Component

@Component(modules = [LoginModule::class], dependencies = [LoginDependencies::class])
@LoginScreenScope
internal interface LoginComponent {

    @Component.Factory
    interface Factory {
        fun create(dependencies: LoginDependencies): LoginComponent
    }

    fun createSignInViewModelFactory(): AssistedSavedStateVMFactory<SignInViewModel>

    fun createSignUpViewModelFactory(): AssistedSavedStateVMFactory<SignUpViewModel>
}
