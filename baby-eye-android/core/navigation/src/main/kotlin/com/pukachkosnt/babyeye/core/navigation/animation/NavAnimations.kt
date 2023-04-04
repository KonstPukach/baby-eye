package com.pukachkosnt.babyeye.core.navigation.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext

@Composable
fun SlideFromRight(
    Content: @Composable () -> Unit,
    onAnimationComplete: () -> Unit = { }
) {
    SlideHorizontally(
        Content = Content,
        newX = { screenWidth, increment -> screenWidth - increment },
        onAnimationComplete = onAnimationComplete
    )
}

@Composable
fun SlideToLeft(
    Content: @Composable () -> Unit,
    onAnimationComplete: () -> Unit = { }
) {
    SlideHorizontally(
        Content = Content,
        newX = { _, increment -> -increment },
        onAnimationComplete = onAnimationComplete
    )
}

@Composable
fun SlideToRight(
    Content: @Composable () -> Unit,
    onAnimationComplete: () -> Unit = { }
) {
    SlideHorizontally(
        Content = Content,
        newX = { _, increment -> increment },
        onAnimationComplete = onAnimationComplete
    )
}

@Composable
fun SlideFromLeft(
    Content: @Composable () -> Unit,
    onAnimationComplete: () -> Unit = { }
) {
    SlideHorizontally(
        Content = Content,
        newX = { screenWidth, increment -> increment - screenWidth },
        onAnimationComplete = onAnimationComplete
    )
}

@Composable
internal fun SlideHorizontally(
    Content: @Composable () -> Unit,
    newX: (screenWidth: Float, increment: Float) -> Float,
    onAnimationComplete: () -> Unit = { }
) {
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels.toFloat()
    val x = remember { Animatable(initialValue = 0F) }

    LaunchedEffect(x) {
        x.animateTo(screenWidth, animationSpec = tween())
    }

    Box(
        modifier = Modifier.graphicsLayer {
            translationX = newX(screenWidth, x.value)
            if (x.value >= screenWidth) {
                onAnimationComplete()
            }
        }
    ) {
        Content()
    }
}
