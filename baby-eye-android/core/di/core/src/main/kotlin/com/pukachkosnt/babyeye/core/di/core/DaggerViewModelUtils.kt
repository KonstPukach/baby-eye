package com.pukachkosnt.babyeye.core.di.core

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 *  [androidx.lifecycle.viewmodel.compose.viewModel] is adapted for Dagger DI
 */
@Composable
inline fun <reified T: ViewModel> injectViewModel(
    key: String? = null,
    crossinline viewModelFactory: () -> T
): T = viewModel(
    modelClass = T::class.java,
    key = key,
    factory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <VM: ViewModel> create(modelClass: Class<VM>): VM {
            return viewModelFactory() as VM
        }
    }
)
