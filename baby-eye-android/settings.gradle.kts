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

buildCache {
    local {
        isEnabled = System.getenv().containsKey("CI")
    }
}

rootProject.name = "BabyEye"

include(":app")

include(
    ":remote:networking",
    ":remote:networking-impl"
)

include(
    ":core:navigation",
    ":core:di:core",
    "core:common-ui",
    ":core:mvi"
)

include(
    ":features:login:data",
    ":features:login:domain",
    ":features:login:ui"
)
include(":core:domain")
