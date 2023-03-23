plugins {
    id("com.android.library")
    id("kotlin-android")
}

val composeCompilerVersion: String by rootProject.extra
val composeVersion: String by rootProject.extra
val navigationVersion: String by rootProject.extra

android {
    compileSdk = 33

    namespace = "com.pukachkosnt.babyeye.core.navigation"

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
    api("androidx.navigation:navigation-compose:$navigationVersion")

    // Compose
    implementation("androidx.compose.ui:ui:$composeVersion")
}
