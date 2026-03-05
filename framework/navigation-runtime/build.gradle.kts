plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":framework:navigation-contract"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.compose.runtime)
                implementation(libs.jetbrains.navigation.compose)
                implementation(libs.koin.core)
            }
        }
    }
}

android {
    namespace = "com.example.wanandroidpractice.framework.navigation.runtime"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
