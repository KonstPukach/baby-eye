package com.pukachkosnt.babyeye.core.commonui.utils.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.pukachkosnt.babyeye.core.commonui.utils.res.StringResourceLocator

@Composable
fun StringResourceLocator.string(): String = getString(LocalContext.current)
