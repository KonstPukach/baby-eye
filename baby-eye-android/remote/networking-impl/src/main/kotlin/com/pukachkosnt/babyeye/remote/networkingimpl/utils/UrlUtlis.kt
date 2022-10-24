package com.pukachkosnt.babyeye.remote.networkingimpl.utils

fun String.normalizeUrl(): String = if (endsWith("/")) this else "$this/"
