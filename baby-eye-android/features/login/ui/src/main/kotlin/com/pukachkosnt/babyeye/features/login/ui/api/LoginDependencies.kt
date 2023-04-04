package com.pukachkosnt.babyeye.features.login.ui.api

import androidx.navigation.NavHostController
import com.pukachkosnt.babyeye.remote.networking.auth.TokenStore
import com.pukachkosnt.babyeye.remote.networkingimpl.di.RetrofitServiceFactoryQualifier
import com.pukachkosnt.babyeye.remote.networkingimpl.retrofit.ServiceFactory

interface LoginDependencies {
    val navController: NavHostController
    val tokenStore: TokenStore
    val loginCallback: LoginCallback

    @RetrofitServiceFactoryQualifier
    fun retrofitServiceFactory(): ServiceFactory
}
