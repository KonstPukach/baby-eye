package com.pukachkosnt.babyeye.features.login.ui

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.compose.rememberNavController
import com.pukachkosnt.babyeye.core.commonui.text_fields.Logo
import com.pukachkosnt.babyeye.core.commonui.utils.compose.string
import com.pukachkosnt.babyeye.core.commonui.viewmodel.LceDataState
import com.pukachkosnt.babyeye.core.commonui.widgets.CircularProgressScreen
import com.pukachkosnt.babyeye.core.commonui.R as CR

private const val LOGO_FRACTION = 0.3F

@Composable
internal fun LoginScreen(loginViewModel: LoginViewModel) {
    val loginNavController = rememberNavController()
    val showToastMsg by rememberUpdatedState(::showToast)
    val context = LocalContext.current

    val errorState = (loginViewModel.loginState as? LceDataState.Error)

    Box(modifier = Modifier.fillMaxSize()) {
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
                loginViewModel = loginViewModel,
                loginNavController = loginNavController,
                modifier = Modifier.padding(vertical = dimensionResource(id = CR.dimen.padding_medium)),
                errorText = errorState?.takeUnless { it.showAsToast }?.uiErrorText?.string()
            )
        }

        if (loginViewModel.loginState is LceDataState.Loading) {
            BackHandler { loginViewModel.cancelOperation() }
            CircularProgressScreen(modifier = Modifier.fillMaxSize())
        }

        if (errorState?.showAsToast == true) {
            LaunchedEffect(errorState) {
                showToastMsg(context, errorState.uiErrorText.getString(context))
            }
        }
    }
}

private fun showToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}
