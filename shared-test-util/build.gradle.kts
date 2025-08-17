plugins {
    id("com.android.library")
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.manishsputnikcorporation.movies.common_test"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        testOptions.targetSdk = 36
    }

}

dependencies {
    implementation(project(":app"))
    implementation(libs.kotlinx.coroutines.test)
}