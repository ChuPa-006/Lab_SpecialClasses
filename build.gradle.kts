plugins {
    kotlin("jvm") version "2.2.0"
}

repositories {
    mavenCentral()
}

// Gradle may run on a newer JDK (for example JDK 25), but this lab
// produces bytecode compatible with JVM 17 for both Java and Kotlin tasks.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}
