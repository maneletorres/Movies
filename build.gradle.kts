// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.version.catalog.update)
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}

buildscript {
    dependencies {
        classpath(libs.androidx.room.gradle.plugin)
    }
}

// region Gradle versions plugin configuration:
// https://github.com/ben-manes/gradle-versions-plugin
versionCatalogUpdate {
    sortByKey = false
}
// endregion