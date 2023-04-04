package com.pukachkosnt.babyeye.core.navigation.animation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.ComposeNavigator
import java.util.*
import kotlin.collections.set

/**
 * **Note:** class [AnimatedComposeNavigator] copies almost the full implementation of
 * the class [ComposeNavigator]
 */
@Navigator.Name("animated-composable")
class AnimatedComposeNavigator : Navigator<AnimatedComposeNavigator.AnimatedDestination>() {
    private val backStack get() = state.backStack

    private val _animIdsMap = WeakHashMap<NavBackStackEntry, ComposeNavAnimIds>()
    internal val animIdsMap: Map<NavBackStackEntry, ComposeNavAnimIds>
        get() = _animIdsMap

    internal var navigatorTransition: NavigatorTransition = NavigatorTransition()
        private set

    override fun navigate(
        entries: List<NavBackStackEntry>,
        navOptions: NavOptions?,
        navigatorExtras: Extras?
    ) {
        navigatorTransition = NavigatorTransition(
            incomingBackStackEntry = entries.last(),
            exitBackStackEntry = backStack.value.lastOrNull(),
            isPopped = false
        )

        entries.forEach { entry ->
            if (navOptions != null) {
                _animIdsMap[entry] = ComposeNavAnimIds.from(navOptions)
            }
            state.pushWithTransition(entry)
        }
    }

    override fun createDestination(): AnimatedDestination {
        return AnimatedDestination(this) { }
    }

    override fun popBackStack(popUpTo: NavBackStackEntry, savedState: Boolean) {
        val incomingBackStackEntry = backStack.value.lastOrNull { entry ->
            entry != popUpTo &&
                    backStack.value.lastIndexOf(entry) < backStack.value.lastIndexOf(popUpTo)
        }

        navigatorTransition = NavigatorTransition(
            incomingBackStackEntry = incomingBackStackEntry,
            exitBackStackEntry = popUpTo,
            isPopped = true
        )

        state.popWithTransition(popUpTo, savedState)
    }

    internal fun onTransitionComplete(entry: NavBackStackEntry) {
        state.markTransitionComplete(entry)
    }

    @NavDestination.ClassType(Composable::class)
    class AnimatedDestination(
        navigator: AnimatedComposeNavigator,
        internal val content: @Composable (NavBackStackEntry) -> Unit
    ) : NavDestination(navigator)

    internal companion object {
        internal const val NAME = "animated-composable"
    }
}

internal data class NavigatorTransition(
    val incomingBackStackEntry: NavBackStackEntry? = null,
    val exitBackStackEntry: NavBackStackEntry? = null,
    val isPopped: Boolean = false
)
