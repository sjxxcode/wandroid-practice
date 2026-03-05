pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "wanandroid-practice"
include(":shared")
include(":androidApp")
include(":framework:net")
include(":framework:navigation-contract")
include(":framework:navigation-runtime")
include(":feature:main")
