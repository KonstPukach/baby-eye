package com.pukachkosnt.babyeye.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

typealias FeaturesMap = Map<out Destination, ComposeFeatureFactory>

fun NavGraphBuilder.fromFeatureMap(featureMap: FeaturesMap) {
    featureMap.forEach { (destination, factory) ->
        composable(destination.route) {
            factory.create().InvokeComposable()
        }
    }
}

fun featureMapOf(vararg pairs: Pair<Destination, ComposeFeatureFactory>): FeaturesMap {
    return pairs.toMap()
}
