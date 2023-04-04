package com.pukachkosnt.babyeye.core.navigation.animation

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.*
import androidx.navigation.compose.LocalOwnersProvider
import kotlinx.coroutines.flow.map

@Composable
fun AnimatedNavHost(
    navController: AnimatedNavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit
) {
    AnimatedNavHost(
        navController = navController,
        modifier = modifier,
        graph = remember(route, startDestination, builder) {
            navController.createGraph(startDestination, route, builder)
        }
    )
}

@Composable
fun AnimatedNavHost(
    navController: AnimatedNavHostController,
    modifier: Modifier = Modifier,
    graph: NavGraph
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "NavHost requires a ViewModelStoreOwner to be provided via LocalViewModelStoreOwner"
    }
    val onBackPressedDispatcherOwner = LocalOnBackPressedDispatcherOwner.current
    val onBackPressedDispatcher = onBackPressedDispatcherOwner?.onBackPressedDispatcher

    navController.setLifecycleOwner(lifecycleOwner)
    navController.setViewModelStore(viewModelStoreOwner.viewModelStore)

    if (onBackPressedDispatcher != null) {
        navController.setOnBackPressedDispatcher(onBackPressedDispatcher)
    }

    navController.graph = graph

    val saveableStateHolder = rememberSaveableStateHolder()

    // Find the ComposeNavigator, returning early if it isn't found
    val animComposeNavigator = navController.navigatorProvider
        .get<Navigator<out NavDestination>>(AnimatedComposeNavigator.NAME)
        as? AnimatedComposeNavigator
        ?: return

    // Listen to visibleEntries changes
    val visibleEntries by remember(navController.visibleEntries) {
        navController.visibleEntries.map { visibleEntries ->
            visibleEntries.filter { entry ->
                entry.destination.navigatorName == AnimatedComposeNavigator.NAME
            }
        }
    }.collectAsState(emptyList())

    val incomingEntry = visibleEntries.lastOrNull()
    val (_, exitEntry, isPopped) = animComposeNavigator.navigatorTransition

    if (incomingEntry != null) {
        val entryAnims = when {
            isPopped && exitEntry == null -> ComposeNavAnimOptions()
            isPopped && exitEntry != null -> navController.getNavAnimOptions(exitEntry)
            else -> navController.getNavAnimOptions(incomingEntry)
        }

        val onTransitionCompleted = {
            visibleEntries.forEach(animComposeNavigator::onTransitionComplete)
        }

        val enterContent: @Composable () -> Unit = {
            ShowNavContent(
                incomingEntry,
                if (isPopped) entryAnims.popEnterAnim else entryAnims.enterAnim,
                isExitContent = false,
                onTransitionCompleted
            )
        }
        val exitContent: @Composable () -> Unit = {
            if (exitEntry != null) ShowNavContent(
                exitEntry,
                if (isPopped) entryAnims.popExitAnim else entryAnims.exitAnim,
                isExitContent = true,
                onTransitionCompleted
            )
        }

        Box(modifier) {
            incomingEntry.LocalOwnersProvider(saveableStateHolder) {
                enterContent()
                exitContent()
            }
        }
    }

}

@Composable
private fun ShowNavContent(
    entry: NavBackStackEntry,
    animation: Animation?,
    isExitContent: Boolean,
    onAnimationComplete: () -> Unit
) {
    if (animation != null) {
         animation(
             content = { ShowNavContent(entry) },
             onAnimationComplete = onAnimationComplete
         )
    } else {
        if (!isExitContent) ShowNavContent(entry)
        onAnimationComplete()
    }
}

@Composable
private fun ShowNavContent(entry: NavBackStackEntry) {
    (entry.destination as AnimatedComposeNavigator.AnimatedDestination).content(entry)
}
