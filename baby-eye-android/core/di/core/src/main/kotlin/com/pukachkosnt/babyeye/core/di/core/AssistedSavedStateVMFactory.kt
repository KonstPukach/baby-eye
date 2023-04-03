package com.pukachkosnt.babyeye.core.di.core

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

fun interface AssistedSavedStateVMFactory <out T : ViewModel> {
    fun create(savedStateHandle: SavedStateHandle): T
}
