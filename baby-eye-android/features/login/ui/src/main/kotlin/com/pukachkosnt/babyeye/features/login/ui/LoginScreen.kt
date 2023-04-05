package com.pukachkosnt.babyeye.features.login.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.pukachkosnt.babyeye.core.commonui.widgets.CircularProgressScreen
import com.pukachkosnt.babyeye.core.navigation.animation.rememberAnimatedNavController
import com.pukachkosnt.babyeye.features.login.ui.vm.SignInViewModel
import com.pukachkosnt.babyeye.features.login.ui.vm.SignUpViewModel
import com.pukachkosnt.babyeye.core.commonui.R as CR

@Composable
internal fun LoginScreen(
    signInViewModel: SignInViewModel,
    signUpViewModel: SignUpViewModel
) {
    val loginNavController = rememberAnimatedNavController()
    var showLoadingPage by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        LoginNavHost(
            signInViewModel    = signInViewModel,
            signUpViewModel    = signUpViewModel,
            loginNavController = loginNavController,
            modifier           = Modifier
                .fillMaxSize()
                .padding(
                    vertical   = dimensionResource(id = CR.dimen.padding_medium),
                    horizontal = dimensionResource(id = CR.dimen.padding_small)
                ),
            showLoadingScreen  = { show -> showLoadingPage = show }
        )

        if (showLoadingPage) {
            CircularProgressScreen(modifier = Modifier.fillMaxSize())
        }
    }
}
