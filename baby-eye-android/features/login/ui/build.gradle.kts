plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    kotlin("kapt")
}

val composeCompilerVersion: String by rootProject.extra
val composeVersion: String by rootProject.extra
val daggerVersion: String by rootProject.extra
val lifecycleViewModelVersion: String by rootProject.extra

android {
    compileSdk = 33

    namespace = "com.pukachkosnt.babyeye.features.login.ui"

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        create("qa")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeCompilerVersion
    }
}

dependencies {
    // Project modules
    implementation(project(":features:login:domain"))
    implementation(project(":features:login:data"))
    implementation(project(":remote:networking-impl"))
    implementation(project(":remote:networking"))
    implementation(project(":core:navigation"))
    implementation(project(":core:di:core"))
    implementation(project(":core:common-ui"))
    implementation(project(":core:mvi"))

    // Compose
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleViewModelVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleViewModelVersion")

    // DI
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
}
