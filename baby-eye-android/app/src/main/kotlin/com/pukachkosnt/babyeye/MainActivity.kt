package com.pukachkosnt.babyeye

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.pukachkosnt.babyeye.core.navigation.featureMapOf
import com.pukachkosnt.babyeye.core.navigation.fromFeatureMap
import com.pukachkosnt.babyeye.features.login.ui.api.LoginCallback
import com.pukachkosnt.babyeye.navigation.AppDestinations.*
import com.pukachkosnt.babyeye.ui.theme.BabyEyeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = (applicationContext as BabyEyeApp).appComponent

        setContent {
            BabyEyeTheme {
                val navController = rememberNavController()

                val loginCallback = LoginCallback {
                    // TODO: Go to Home screen
                }

                val mainComponent = appComponent
                    .mainActivityComponent()
                    .create(navController, loginCallback)

                val features = featureMapOf(
                    Login to mainComponent.createLoginFeatureFactory(),
                    Home to mainComponent.createLoginFeatureFactory()
                )

                NavHost(navController = navController, startDestination = Login.route) {
                    fromFeatureMap(features)
                }
            }
        }
    }
}

@Preview
@Composable
private fun HeadlinePreview() {
    BabyEyeTheme {

    }
}
