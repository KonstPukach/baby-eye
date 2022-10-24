pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "BabyEye"

include(":app")

include(
    ":remote:networking",
    ":remote:networking-impl"
)

include(
    ":features:login:data",
    ":features:login:domain",
    ":features:login:ui"
)
