package com.pukachkosnt.babyeye.core.navigation.animation

import androidx.compose.runtime.Composable
import androidx.navigation.NavOptions

typealias Animation = @Composable (
    content: @Composable () -> Unit,
    onAnimationComplete: () -> Unit
) -> Unit

data class ComposeNavAnimOptions(
    val enterAnim: Animation? = null,
    val exitAnim: Animation? = null,
    val popEnterAnim: Animation? = null,
    val popExitAnim: Animation? = null,
)

internal data class ComposeNavAnimIds(
    val enterAnim: Int = -1,
    val exitAnim: Int = -1,
    val popEnterAnim: Int = -1,
    val popExitAnim: Int = -1
) {
    companion object {
        fun from(navOptions: NavOptions) = ComposeNavAnimIds(
            enterAnim    = navOptions.enterAnim,
            exitAnim     = navOptions.exitAnim,
            popEnterAnim = navOptions.popEnterAnim,
            popExitAnim  = navOptions.popExitAnim
        )
    }
}
