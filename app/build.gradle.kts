import java.io.FileInputStream
import java.io.IOException
import java.util.Properties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.ktfmt)
    id("kotlin-parcelize")
    id("androidx.room")
}

android {
    namespace = "com.manishsputnikcorporation.movies"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.manishsputnikcorporation.movies"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.manishsputnikcorporation.movies.HiltTestRunner"
        vectorDrawables { useSupportLibrary = true }

        val localProperties = Properties()
        try {
            localProperties.load(FileInputStream(rootProject.file("local.properties")))
            buildConfigField("String", "BEARER_TOKEN", localProperties["BEARER_TOKEN"] as String)
        } catch (e: IOException) {
            println("Exception1: $e")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin { compilerOptions { jvmTarget.set(JvmTarget.JVM_21) } }
    buildFeatures { buildConfig = true }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
    sourceSets { getByName("androidTest").assets.srcDir("$projectDir/schemas") }
}

dependencies {
    // region Hilt:
    runtimeOnly(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)
    // endregion

    // region Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    // endregion

    // region Firebase:
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    // endregion

    // region Retrofit
    implementation(platform(libs.com.squareup.retrofit2.bom))
    implementation(libs.bundles.retrofit2)
    ksp(libs.com.squareup.moshi.moshi.kotlin.codegen)

    // region OkHttp
    implementation(platform(libs.com.squareup.okhttp3.bom))
    implementation(libs.bundles.okhttp3)
    // endregion

    // region Room:
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)
    // endregion

    implementation(libs.com.jakewharton.timber)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.compose)

    // region Testing libraries:
    testImplementation(project(":shared-test-util"))
    testImplementation(kotlin("test"))
    testImplementation(libs.bundles.unit.testing)

    androidTestImplementation(project(":shared-test-util"))
    androidTestImplementation(kotlin("test"))
    androidTestImplementation(libs.bundles.android.testing)
    // endregion
}

room { schemaDirectory("$projectDir/schemas") }

ktfmt {
    kotlinLangStyle()
    maxWidth.set(140)
    removeUnusedImports.set(true)
}
