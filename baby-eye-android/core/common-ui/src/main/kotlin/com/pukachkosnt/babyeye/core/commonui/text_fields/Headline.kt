package com.pukachkosnt.babyeye.core.commonui.text_fields

import androidx.annotation.StringRes
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun Headline(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.h6
    )
}

@Composable
fun Headline(@StringRes text: Int, modifier: Modifier = Modifier) =
    Headline(text = stringResource(id = text), modifier = modifier)
