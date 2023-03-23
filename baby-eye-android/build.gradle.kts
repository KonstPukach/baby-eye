buildscript {
    extra.apply {
        set("composeVersion", "1.3.2")
        set("composeMaterialVersion", "1.3.1")
        set("composeCompilerVersion", "1.3.2")
        set("daggerVersion", "2.44")
        set("navigationVersion", "2.5.3")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.3.0")

        // Firebase plugins
        classpath("com.google.gms:google-services:4.3.14")
        classpath("com.google.firebase:firebase-appdistribution-gradle:3.0.3")
    }
}

plugins {
    id("com.android.application") version("7.2.1") apply false
    id("com.android.library") version("7.2.1") apply false
    id("org.jetbrains.kotlin.android") version("1.7.20") apply false
    id("org.jetbrains.kotlin.jvm") version("1.7.20") apply false
}

allprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
            )
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
