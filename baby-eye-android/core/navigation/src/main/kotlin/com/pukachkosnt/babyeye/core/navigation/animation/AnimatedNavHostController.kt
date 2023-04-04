package com.pukachkosnt.babyeye.core.navigation.animation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.*
import androidx.navigation.compose.DialogNavigator
import java.util.*

class AnimatedNavHostController(context: Context) : NavHostController(context) {
    /**
     *  The main idea of making animated compose navigating is to associate each animation set
     *  with the [NavBackStackEntry] object. But it is not possible to extend nav framework so that
     *  it would have only one source of truth.
     *
     *  It is necessary to have [AnimatedNavHostController] and [AnimatedComposeNavigator]
     *  associated with it. So, the [AnimatedNavHostController] object stores all animations with
     *  unique ids in the single map (Id to Animation). And [AnimatedComposeNavigator] has the same
     *  map but it associates [NavBackStackEntry] to unique animation id. We have to do this,
     *  because we can provide only int value to the [Navigator].
     */
    private val _animsMap = HashMap<Int, Animation>()

    /**
     * @return [ComposeNavAnimOptions] instance for a given [entry]
     */
    fun getNavAnimOptions(entry: NavBackStackEntry): ComposeNavAnimOptions {
        val animIds = requireNotNull(
            navigatorProvider.get<AnimatedComposeNavigator>(AnimatedComposeNavigator.NAME),
            lazyMessage = { "\"animated-composable\" not found" }
        ).animIdsMap[entry] ?: return ComposeNavAnimOptions()

        return ComposeNavAnimOptions(
            enterAnim    = _animsMap[animIds.enterAnim],
            exitAnim     = _animsMap[animIds.exitAnim],
            popEnterAnim = _animsMap[animIds.popEnterAnim],
            popExitAnim  = _animsMap[animIds.popExitAnim]
        )
    }

    fun navigateWithAnim(
        route: String,
        composeAnimOptions: ComposeNavAnimOptions,
        builder: NavOptionsBuilder.() -> Unit
    ) {
        navigateWithAnim(route, composeAnimOptions, navOptions(builder))
    }

    fun navigateWithAnim(
        route: String,
        composeAnimOptions: ComposeNavAnimOptions,
        navOptions: NavOptions? = null,
        navExtras: Navigator.Extras? = null
    ) {
        val navOptionWithAnims = fillAnimsMap(composeAnimOptions, navOptions)
        navigate(route, navOptionWithAnims, navExtras)
    }

    private fun fillAnimsMap(
        composeAnimOptions: ComposeNavAnimOptions,
        navOptions: NavOptions?
    ): NavOptions {
        val newNavOptionsBuilder = navOptions?.copyAsBuilder() ?: NavOptions.Builder()

        val (enter, exit, popEnter, popExit) = composeAnimOptions

        if (enter != null) {
            val animUniqueId = UUID.randomUUID().toString().hashCode()
            _animsMap[animUniqueId] = enter
            newNavOptionsBuilder.setEnterAnim(animUniqueId)
        }

        if (exit != null) {
            val animUniqueId = UUID.randomUUID().toString().hashCode()
            _animsMap[animUniqueId] = exit
            newNavOptionsBuilder.setExitAnim(animUniqueId)
        }

        if (popEnter != null) {
            val animUniqueId = UUID.randomUUID().toString().hashCode()
            _animsMap[animUniqueId] = popEnter
            newNavOptionsBuilder.setPopEnterAnim(animUniqueId)
        }

        if (popExit != null) {
            val animUniqueId = UUID.randomUUID().toString().hashCode()
            _animsMap[animUniqueId] = popExit
            newNavOptionsBuilder.setPopExitAnim(animUniqueId)
        }

        return newNavOptionsBuilder.build()
    }

    private fun NavOptions.copyAsBuilder(): NavOptions.Builder {
        return NavOptions.Builder().apply {
            val prev = this@copyAsBuilder

            setLaunchSingleTop(prev.shouldLaunchSingleTop())
            setRestoreState(prev.shouldRestoreState())

            if (prev.popUpToRoute != null) {
                setPopUpTo(
                    prev.popUpToRoute,
                    prev.isPopUpToInclusive(),
                    prev.shouldPopUpToSaveState()
                )
            } else {
                setPopUpTo(
                    prev.popUpToId,
                    prev.isPopUpToInclusive(),
                    prev.shouldPopUpToSaveState()
                )
            }
        }
    }
}

@Composable
fun rememberAnimatedNavController(
    vararg navigators: Navigator<out NavDestination>
): AnimatedNavHostController {
    val context = LocalContext.current
    return rememberSaveable(
        inputs = navigators,
        saver = navControllerSaver(context),
        init = { createAnimatedNavController(context) }
    ).apply {
        navigators.forEach(navigatorProvider::addNavigator)
    }
}

private fun createAnimatedNavController(context: Context) =
    AnimatedNavHostController(context).apply {
        navigatorProvider.addNavigator(AnimatedComposeNavigator())
        navigatorProvider.addNavigator(DialogNavigator())
    }

private fun navControllerSaver(
    context: Context
): Saver<AnimatedNavHostController, *> = Saver(
    save = { it.saveState() },
    restore = { createAnimatedNavController(context).apply { restoreState(it) } }
)
