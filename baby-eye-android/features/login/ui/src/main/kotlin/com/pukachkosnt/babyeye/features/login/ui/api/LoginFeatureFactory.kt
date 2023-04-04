package com.pukachkosnt.babyeye.features.login.ui.api

import androidx.compose.runtime.Composable
import com.pukachkosnt.babyeye.core.di.core.injectSavedStateViewModel
import com.pukachkosnt.babyeye.core.navigation.ComposableFeature
import com.pukachkosnt.babyeye.core.navigation.ComposeFeatureFactory
import com.pukachkosnt.babyeye.features.login.ui.LoginScreen
import com.pukachkosnt.babyeye.features.login.ui.di.DaggerLoginComponent
import com.pukachkosnt.babyeye.features.login.ui.vm.SignInViewModel
import com.pukachkosnt.babyeye.features.login.ui.vm.SignUpViewModel
import javax.inject.Inject

class LoginFeatureFactory @Inject constructor(
    private val loginDependencies: LoginDependencies
) : ComposeFeatureFactory {

    override fun create() = object : ComposableFeature {
        @Composable
        override fun InvokeComposable() {
            val component = DaggerLoginComponent.factory().create(loginDependencies)

            val signInViewModel: SignInViewModel = injectSavedStateViewModel(
                viewModelFactory = component.createSignInViewModelFactory())

            val signUpViewModel: SignUpViewModel = injectSavedStateViewModel(
                viewModelFactory = component.createSignUpViewModelFactory())

            LoginScreen(
                signInViewModel = signInViewModel,
                signUpViewModel = signUpViewModel
            )
        }
    }
}
