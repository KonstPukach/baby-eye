package com.pukachkosnt.babyeye.core.navigation

import androidx.compose.runtime.Composable

/**
 * Works as a bridge between :app and :feature modules. [ComposeFeatureFactory] should be the
 * only exposed api that other modules can see.
 *
 * **Note:** all dependencies of a :feature module should be resolved via Dagger
 * dependency injection.
 */
interface ComposeFeatureFactory {
    fun create(): ComposableFeature
}

/**
 * [ComposableFeature] represents a composable function that is going to be added to an application
 * as a feature.
 */
fun interface ComposableFeature {
    @Composable
    fun InvokeComposable()
}
