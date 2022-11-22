package com.pukachkosnt.babyeye.features.login.ui.api

import androidx.compose.runtime.Composable
import com.pukachkosnt.babyeye.core.di.core.injectViewModel
import com.pukachkosnt.babyeye.core.navigation.ComposableFeature
import com.pukachkosnt.babyeye.core.navigation.ComposeFeatureFactory
import com.pukachkosnt.babyeye.features.login.ui.LoginScreen
import com.pukachkosnt.babyeye.features.login.ui.LoginViewModel
import com.pukachkosnt.babyeye.features.login.ui.di.DaggerLoginComponent
import javax.inject.Inject

class LoginFeatureFactory @Inject constructor(
    private val loginDependencies: LoginDependencies
) : ComposeFeatureFactory {

    override fun create() = object : ComposableFeature {
        @Composable
        override fun InvokeComposable() {
            val component = DaggerLoginComponent.factory().create(loginDependencies)

            val viewModel: LoginViewModel = injectViewModel {
                component.createLoginViewModel()
            }

            LoginScreen(loginViewModel = viewModel)
        }
    }
}
