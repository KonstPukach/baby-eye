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
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Button2")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Button3")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Button2")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Button3")
        }
    }
}
