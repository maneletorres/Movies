plugins {
    id("com.android.library")
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.manishsputnikcorporation.movies.common_test"
    compileSdk = 36

    defaultConfig {
        minSdk = 30
        testOptions.targetSdk = 36
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    implementation(project(":app"))
    implementation(libs.kotlinx.coroutines.test)
}