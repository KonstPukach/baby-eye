import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

val daggerVersion: String by rootProject.extra

val buildProperties = Properties()
if (rootProject.file("build.properties").exists()) {
    buildProperties.load(rootProject.file("build.properties").inputStream())
}

android {
    compileSdk = 33

    namespace = "com.pukachkosnt.babyeye.remote.networkingimpl"

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("debug") {
            buildConfigField(
                type = "String",
                name = "SERVER_URL",
                value = buildProperties["serverUrl"] as String
            )
        }

        getByName("release") {
            buildConfigField(type = "String", name = "SERVER_URL", value = "TODO")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        create("qa") {
            buildConfigField(
                type = "String",
                name = "SERVER_URL",
                value = buildProperties["qaServerUrl"] as String
            )
        }
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
}

dependencies {
    implementation(project(":remote:networking"))

    // Networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")

    // DI - Dagger 2
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
}
