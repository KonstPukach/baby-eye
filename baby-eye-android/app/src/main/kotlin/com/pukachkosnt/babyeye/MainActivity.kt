package com.pukachkosnt.babyeye

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.pukachkosnt.babyeye.core.navigation.featureMapOf
import com.pukachkosnt.babyeye.core.navigation.fromFeatureMap
import com.pukachkosnt.babyeye.navigation.AppDestinations.Login
import com.pukachkosnt.babyeye.ui.theme.BabyEyeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = (applicationContext as BabyEyeApp).appComponent

        setContent {
            BabyEyeTheme {
//                val navController = rememberNavController()
//
//                val mainComponent = appComponent
//                    .mainActivityComponent()
//                    .create(navController)
//
//                val features = featureMapOf(
//                    Login to mainComponent.createLoginFeatureFactory()
//                )
//
//                NavHost(navController = navController, startDestination = Login.route) {
//                    fromFeatureMap(features)
                 }
            }
        }
    }
}
