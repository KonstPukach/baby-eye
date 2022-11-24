buildscript {
    extra.apply {
        set("composeVersion", "1.3.0")
        set("composeCompilerVersion", "1.3.1")
        set("daggerVersion", "2.44")
        set("navigationVersion", "2.5.2")
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
    id("org.jetbrains.kotlin.android") version("1.7.10") apply false
    id("org.jetbrains.kotlin.jvm") version("1.7.10") apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
