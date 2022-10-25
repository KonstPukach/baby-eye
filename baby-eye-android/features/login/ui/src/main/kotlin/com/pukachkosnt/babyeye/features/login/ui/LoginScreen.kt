package com.pukachkosnt.babyeye.features.login.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
internal fun LoginScreen(
    loginViewModel: LoginViewModel,
    navController: NavController
) {
    Column {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Button")
        }
    }
    Column {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Button")
            Text(text = "Button2")
        }
    }
}
