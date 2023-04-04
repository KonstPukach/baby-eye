plugins {
    id("com.android.library")
    id("kotlin-android")
}

val androidxVersion: String           by rootProject.extra
val lifecycleViewModelVersion: String by rootProject.extra
val coroutinesVersion: String         by rootProject.extra

android {
    namespace = "com.pukachkosnt.mvi"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        create("qa")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // Android
    implementation("androidx.core:core-ktx:$androidxVersion")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleViewModelVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleViewModelVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleViewModelVersion")

    // Tests
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    testImplementation("junit:junit:4.13.2")
}