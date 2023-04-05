package com.pukachkosnt.babyeye.features.login.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pukachkosnt.babyeye.core.commonui.text_fields.Logo
import com.pukachkosnt.babyeye.core.navigation.animation.*
import com.pukachkosnt.babyeye.features.login.ui.composables.signin.SignInInputForm
import com.pukachkosnt.babyeye.features.login.ui.composables.signup.SignUpInputForm
import com.pukachkosnt.babyeye.features.login.ui.vm.SignInViewModel
import com.pukachkosnt.babyeye.features.login.ui.vm.SignUpViewModel

internal const val SingInDestination = "sign-in"
internal const val SingUpDestination = "sign-up"

@Composable
internal fun LoginNavHost(
    signInViewModel: SignInViewModel,
    signUpViewModel: SignUpViewModel,
    loginNavController: AnimatedNavHostController,
    modifier: Modifier = Modifier,
    showLoadingScreen: (Boolean) -> Unit
) {
    AnimatedNavHost(
        modifier         = modifier,
        navController    = loginNavController,
        startDestination = SingInDestination
    ) {
        animatedComposable(SingInDestination) {
            AdjustedLoginLayout(
                modifier  = Modifier.fillMaxSize(),
                logo      = { Logo() },
                inputForm = {
                    SignInInputForm(
                        goToSignUp = {
                            loginNavController.navigateWithAnim(
                                route = SingUpDestination,
                                composeAnimOptions = ComposeNavAnimOptions(
                                    enterAnim    = { c, o -> SlideFromRight(c, o) },
                                    exitAnim     = { c, o -> SlideToLeft(c, o) },
                                    popEnterAnim = { c, o -> SlideFromLeft(c, o) },
                                    popExitAnim  = { c, o -> SlideToRight(c, o) }
                                )
                            )
                         },
                        signInViewModel   = signInViewModel,
                        showLoadingScreen = showLoadingScreen
                    )
                }
            )
        }
        animatedComposable(SingUpDestination) {
            AdjustedLoginLayout(
                modifier  = Modifier.fillMaxSize(),
                logo      = { Logo() },
                inputForm = {
                    SignUpInputForm(
                        goToSignIn        = { loginNavController.navigateUp() },
                        signUpViewModel   = signUpViewModel,
                        showLoadingScreen = showLoadingScreen
                    )
                }
            )
        }
    }
}
