plugins {
    id("kotlin")
    kotlin("kapt")
}

val daggerVersion: String by rootProject.extra

dependencies {
    // Project modules dependencies
    implementation(project(":core:domain"))

    // DI
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
}
