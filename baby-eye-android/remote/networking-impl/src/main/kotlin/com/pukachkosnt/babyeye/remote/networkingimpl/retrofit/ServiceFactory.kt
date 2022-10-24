package com.pukachkosnt.babyeye.remote.networkingimpl.retrofit

interface ServiceFactory {
    fun <T> createService(serviceClass: Class<T>): Lazy<T>
}
