package com.pukachkosnt.babyeye

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pukachkosnt.babyeye.features.login.ui.LoginScreen
import com.pukachkosnt.babyeye.ui.theme.BabyEyeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BabyEyeTheme {
                LoginScreen()
            }
        }
    }
}
