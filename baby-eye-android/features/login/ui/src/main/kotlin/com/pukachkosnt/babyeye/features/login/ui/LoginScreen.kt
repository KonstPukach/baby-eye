package com.pukachkosnt.babyeye.features.login.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pukachkosnt.babyeye.core.commonui.text_fields.Logo
import com.pukachkosnt.babyeye.core.commonui.R as CR

private const val LOGO_FRACTION = 0.3F

@Composable
internal fun LoginScreen(
    loginViewModel: LoginViewModel,
    navController: NavController
) {
    val loginNavController = rememberNavController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = CR.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(LOGO_FRACTION)
                .weight(1F),
            contentAlignment = Alignment.Center
        ) {
            Logo()
        }

        LoginNavHost(
            loginNavController = loginNavController,
            modifier = Modifier.padding(vertical = dimensionResource(id = CR.dimen.padding_medium))
        )
    }
}
