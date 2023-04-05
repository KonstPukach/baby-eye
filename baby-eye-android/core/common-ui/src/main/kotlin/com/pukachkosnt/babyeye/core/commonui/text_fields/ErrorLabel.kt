package com.pukachkosnt.babyeye.core.commonui.text_fields

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorLabel(
    text: String,
    modifier: Modifier = Modifier
) = Text(
    text     = text,
    modifier = modifier,
    style    = MaterialTheme.typography.caption,
    color    = MaterialTheme.colors.error
)
