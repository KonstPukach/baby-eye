package com.pukachkosnt.babyeye.features.login.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

internal const val LOGO_FRACTION = 0.3F
private const val HIDE_LOGO_RATIO = 1.1

/**
 * Layout can decide if it can show Logo or not. If there is no enough space than is won't be shown.
 * Example: small devices with opened keyboard
 */
@Composable
internal fun AdjustedLoginLayout(
    modifier: Modifier = Modifier,
    logoFraction: Float = LOGO_FRACTION,
    logo: @Composable () -> Unit,
    inputForm: @Composable () -> Unit
) = Layout(
    modifier = modifier,
    content = {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            logo()
        }
        inputForm()
    }
) { measurables, constraints ->
    val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

    val loginFormPlaceable = measurables[1].measure(looseConstraints)

    val heightForLogo = constraints.maxHeight - loginFormPlaceable.measuredHeight
    val maxHeightForLogo = (constraints.maxHeight * logoFraction).toInt()

    val logoHeight = if (heightForLogo > maxHeightForLogo) maxHeightForLogo else heightForLogo
    val logoConstraints = looseConstraints.copy(maxHeight = logoHeight)

    val logoBox = measurables.first()
    val logoMinHeight = logoBox.minIntrinsicHeight(constraints.maxWidth)
    val logoPlaceable = logoBox.measure(logoConstraints)

    layout(constraints.maxWidth, constraints.maxHeight) {
        if (logoMinHeight * HIDE_LOGO_RATIO < logoConstraints.maxHeight) {
            logoPlaceable.place(x = 0, y = 0)
            loginFormPlaceable.place(x = 0, y = logoPlaceable.measuredHeight)
        } else {
            val y = (looseConstraints.maxHeight - loginFormPlaceable.measuredHeight) / 2
            loginFormPlaceable.place(x = 0, y = y)
        }
    }
}
