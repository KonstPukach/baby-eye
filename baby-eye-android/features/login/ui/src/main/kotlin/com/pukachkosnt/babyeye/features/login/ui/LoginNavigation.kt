package com.pukachkosnt.babyeye.features.login.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pukachkosnt.babyeye.features.login.ui.composables.signin.SignInInputForm
import com.pukachkosnt.babyeye.features.login.ui.composables.signup.SignUpInputForm

internal const val SingInDestination = "sign-in"
internal const val SingUpDestination = "sign-up"

@Composable
internal fun LoginNavHost(loginNavController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        modifier = modifier,
        navController = loginNavController,
        startDestination = SingInDestination
    ) {
        composable(SingInDestination) {
            SignInInputForm(
                goToSignUp = {
                    loginNavController.navigate(SingUpDestination) {

                    }
                }
            )
        }
        composable(SingUpDestination) {
            SignUpInputForm(
                goToSignIn = {
                    loginNavController.navigateUp()
                }
            )
        }
    }
}
