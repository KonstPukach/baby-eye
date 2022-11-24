package com.pukachkosnt.babyeye.features.login.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.pukachkosnt.babyeye.features.login.ui.R

@Composable
fun LoginButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .padding(top = dimensionResource(id = com.pukachkosnt.babyeye.core.commonui.R.dimen.padding_medium))
            .height(dimensionResource(id = R.dimen.sign_in_btn_height))
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Text(text.uppercase())
    }
}

@Composable
fun LoginButton(@StringRes text: Int, onClick: () -> Unit) {
    LoginButton(text = stringResource(id = text), onClick = onClick)
}
