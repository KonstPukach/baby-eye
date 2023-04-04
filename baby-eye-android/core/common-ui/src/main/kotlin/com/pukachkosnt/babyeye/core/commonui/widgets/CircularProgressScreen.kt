package com.pukachkosnt.babyeye.core.commonui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

private val SemiTransparentColor = Color(0xCCFFFFFF) // 80% transparent white color

@Composable
fun CircularProgressScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(SemiTransparentColor)
            .clickable(enabled = false) { },//
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun CircularProgressScreenPreview() {
    Box(
        modifier         = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text  = "Text example",
            style = MaterialTheme.typography.h3
        )
        CircularProgressScreen(modifier = Modifier.fillMaxSize())
    }
}
