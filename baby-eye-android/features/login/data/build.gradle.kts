plugins {
    id("kotlin")
    kotlin("kapt")
}

val daggerVersion: String by rootProject.extra

dependencies {
    // Project modules dependencies
    implementation(project(":remote:networking"))
    implementation(project(":features:login:domain"))

    // Network
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")

    // DI
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
}
