package com.pukachkosnt.babyeye.navigation

import com.pukachkosnt.babyeye.core.navigation.Destination

enum class AppDestinations(override val route: String) : Destination {
    Login(route = "login")
}
