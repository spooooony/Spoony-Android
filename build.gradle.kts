buildscript {
    repositories {
        google()
        mavenCentral()
        maven(
            url = uri("https://repository.map.naver.com/archive/maven")
        )
        maven(
            url = uri("https://dl.google.com/dl/android/maven2")
        )
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.ktlint) apply false
}