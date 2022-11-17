package com.pukachkosnt.babyeye.core.commonui.text_fields

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.pukachkosnt.babyeye.core.commonui.R

@Composable
fun Logo(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.babyeye).uppercase(),
        style = MaterialTheme.typography.h2
    )
}