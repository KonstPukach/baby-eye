plugins {
    id("kotlin")
    kotlin("kapt")
}

val daggerVersion: String by rootProject.extra

dependencies {
    // DI - Dagger 2
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
}
