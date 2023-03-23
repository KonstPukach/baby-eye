import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.appdistribution")
}

val composeCompilerVersion: String by rootProject.extra
val composeMaterialVersion: String by rootProject.extra
val composeVersion: String by rootProject.extra
val daggerVersion: String by rootProject.extra

val qaKeystoreProperties = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}

android {
    compileSdk = 33

    namespace = "com.pukachkosnt.babyeye"

    defaultConfig {
        applicationId = "com.pukachkosnt.babyeye"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("qa") {
            storeFile     = qaKeystoreProperties.getProperty("qaSignKeystoreFile")?.let(::file)
            storePassword = qaKeystoreProperties.getProperty("qaSignKeystorePassword")
            keyAlias      = qaKeystoreProperties.getProperty("qaSignKeystoreKeyAlias")
            keyPassword   = qaKeystoreProperties.getProperty("qaSignKeyPassword")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        create("qa") {
            isDebuggable = true
            signingConfig = signingConfigs["qa"]
            firebaseAppDistribution {
                artifactType = "APK"
                testers = "pukachkosnt@gmail.com"
                releaseNotesFile = rootProject.projectDir.path + "/app/release-notes.txt"
            }

            defaultConfig {
                val qaVersion = 2
                versionCode = createQaReleaseVersionCode(qaVersion)
                versionName = createQaReleaseVersionName(qaVersion)
            }
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = composeCompilerVersion
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Project modules dependencies
    implementation(project(":features:login:ui"))
    implementation(project(":core:navigation"))
    implementation(project(":core:di:core"))
    implementation(project(":remote:networking-impl"))
    implementation(project(":remote:networking"))
    implementation(project(":core:common-ui"))

    // Core
    implementation("androidx.core:core-ktx:1.9.0")

    // Compose
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeMaterialVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.activity:activity-compose:1.6.1")

    // Android
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")

    // DI - Dagger 2
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")

    // Debug
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")

    // Tests
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
}

tasks.register("uploadQaRelease", GradleBuild::class.java) {
    description = "Uploads QA release apk to firebase with given release notes in release-notes.txt"

    doFirst {
        generateReleaseNotesIfNotExist()
        tasks = listOf("assembleQa", "appDistributionUploadQa")
    }
}
