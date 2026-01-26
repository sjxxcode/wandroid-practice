# koin-practice

Kotlin Multiplatform starter with a shared module and an Android app.

## Structure
- `shared`: KMP shared code (common + Android + iOS targets)
- `androidApp`: Android application using shared code

## Getting started
1. Open the project in Android Studio.
2. Sync Gradle.
3. Run the `androidApp` configuration.

## Notes
- iOS targets are configured in `shared`, but no Xcode project is included yet.
- If you want a Gradle wrapper, run `gradle wrapper --gradle-version 8.2.2` after installing Gradle.
