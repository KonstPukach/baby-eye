package com.pukachkosnt.babyeye.remote.networkingimpl.utils

fun <T> Result.Companion.failure(): Result<T> = failure(Exception())
