/**
 * framework/net 模块（KMP 网络层）Gradle 配置说明。
 *
 * 这个文件的核心目标：
 * 1. 声明这是一个 Kotlin Multiplatform 模块（同时支持 Android + iOS）。
 * 2. 为不同平台配置各自的 Ktor 引擎依赖。
 * 3. 给 commonMain 配置跨平台可复用的网络能力依赖。
 */
plugins {
    // Kotlin Multiplatform 插件：提供 commonMain / androidMain / iosMain 等源集能力。
    kotlin("multiplatform")
    // Android Library 插件：让该模块可以作为 Android library 参与构建。
    id("com.android.library")
}

kotlin {
    // Android 目标：用于生成 Android 侧产物（供 App 或其他模块依赖）。
    androidTarget {
        // 对 Android target 下的所有 compilation 统一配置 Kotlin 编译参数。
        compilations.all {
            kotlinOptions {
                // 与项目其余模块保持一致，统一使用 Java 17 字节码目标。
                jvmTarget = "17"
            }
        }
    }

    // iOS 三个目标：
    // 1) iosX64: Intel 模拟器（老款 Mac 或特定 CI 环境）
    // 2) iosArm64: 真机
    // 3) iosSimulatorArm64: Apple Silicon 模拟器
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        /**
         * commonMain：所有平台共享代码（本模块的核心网络抽象都在这里）。
         */
        val commonMain by getting {
            dependencies {
                // Ktor 客户端核心能力（请求/响应基础 API）。
                implementation(libs.ktor.client.core)
                // 内容协商插件（配合 JSON 序列化反序列化）。
                implementation(libs.ktor.client.content.negotiation)
                // Kotlinx JSON 序列化支持。
                implementation(libs.ktor.serialization.kotlinx.json)
                // 请求日志插件（开发调试阶段常用）。
                implementation(libs.ktor.client.logging)
            }
        }

        /**
         * androidMain：Android 平台专属代码与依赖。
         */
        val androidMain by getting {
            dependencies {
                // Android 平台使用 OkHttp 作为 Ktor 引擎。
                implementation(libs.ktor.client.okhttp)
            }
        }

        // 获取三个 iOS 平台的默认源集引用，后续用于与 iosMain 建立 dependsOn 关系。
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        /**
         * 手动创建 iosMain 共享源集。
         *
         * 为什么要手动创建：
         * - 当前项目配置下，iosMain 没有被默认模板直接暴露为可 `by getting` 的源集。
         * - 但我们把 iOS 的 actual 实现（如 HttpClientFactory.ios.kt）放在 src/iosMain 下，
         *   所以必须显式创建 iosMain，并让三个 iOS target 依赖它。
         *
         * 这样做的结果：
         * - iosMain 中的代码可以被三个 iOS target 复用；
         * - iosMain 中声明的依赖（如 Darwin 引擎）对该源集代码可见，
         *   避免 `Unresolved reference: darwin / Darwin`。
         */
        val iosMain by creating {
            // iosMain 先依赖 commonMain，继承公共代码与依赖。
            dependsOn(commonMain)
            // 再让各 iOS 具体源集依赖 iosMain，实现“iOS 层共享”。
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                // iOS 平台 Ktor 引擎（基于 Darwin / NSURLSession）。
                implementation(libs.ktor.client.darwin)
            }
        }

        /**
         * commonTest：跨平台测试源集。
         */
        val commonTest by getting {
            dependencies {
                // Kotlin 官方多平台测试库。
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    // Android 模块命名空间（R 类与清单合并时使用）。
    namespace = "com.example.wanandroidpractice.framework.net"

    // 编译 SDK 版本。这里与当前项目现有模块保持一致。
    compileSdk = 34

    defaultConfig {
        // 模块最低支持 Android 版本。
        minSdk = 24
    }

    // 指定 AndroidManifest 路径（KMP 模块常见写法）。
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    compileOptions {
        // Java 源/目标版本统一为 17，避免跨模块编译参数不一致。
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
