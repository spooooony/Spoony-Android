import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ktlint)
}

val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
    signingConfigs {
        create("release") {
            keyAlias = properties.getProperty("keyAlias")
            keyPassword = properties.getProperty("keyPassword")
            storeFile = File("${project.rootDir.absolutePath}/keystore/spoony-release-key.jks")
            storePassword = properties.getProperty("storePassword")
        }
    }

    namespace = "com.spoony.spoony"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        manifestPlaceholders += mapOf()
        applicationId = "com.spoony.spoony"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "BASE_URL",
            properties.getProperty("dev.base.url")
        )

        manifestPlaceholders["naverClientId"] = properties["naverClientId"] as String
        buildConfigField("String", "NAVER_CLIENT_ID", properties["naver.client.id"] as String)

        manifestPlaceholders["nativeAppKey"] = properties["nativeAppKey"] as String
        buildConfigField("String", "NATIVE_APP_KEY", properties["native.app.key"] as String)
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.play.services.maps)
    // Test
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.test)

    // Debug
    debugImplementation(libs.bundles.debug)

    // Androidx
    implementation(libs.bundles.androidx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.kotlinx.immutable)
    implementation(libs.androidx.datastore.preferences)

    // Network
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
    implementation(libs.kotlinx.serialization.json)

    // Hilt
    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)

    implementation(libs.coil.compose)
    implementation(libs.timber)
    implementation(libs.lottie)
    implementation(libs.advanced.bottom.sheet)

    // Naver Map
    implementation(libs.bundles.naverMap)

    // Kakao
    implementation(libs.kakao.user)

    // Room
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    implementation(libs.pebble)
    implementation(libs.jakewharton.process.phoenix)

    implementation(libs.accompanist.systemuicontroller)
}

ktlint {
    android = true
    debug = true
    coloredOutput = true
    verbose = true
    outputToConsole = true
}
