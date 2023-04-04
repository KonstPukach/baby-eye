package com.pukachkosnt.babyeye.core.navigation.animation

import androidx.compose.runtime.Composable
import androidx.navigation.*

fun NavGraphBuilder.animatedComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    addDestination(
        AnimatedComposeNavigator.AnimatedDestination(
            provider[AnimatedComposeNavigator::class],
            content
        ).apply {
            this.route = route
            arguments.forEach { (argumentName, argument) -> addArgument(argumentName, argument) }
            deepLinks.forEach(::addDeepLink)
        }
    )
}
