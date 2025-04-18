import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.atlysimdb"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.atlysimdb"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(localPropertiesFile.inputStream())
        }
        val tmdbApiKey = localProperties.getProperty("tmdb_api_key") ?: ""
        buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs += listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        )
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    buildFeatures {
        compose = true
    }

    kapt {
        correctErrorTypes = true
        useBuildCache = true
        arguments {
            arg("dagger.fastInit", "true")
            arg("dagger.formatGeneratedSource", "enabled")
        }
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
        arg("room.incremental", "true")
        arg("room.expandProjection", "true")
    }
}

dependencies {
    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    // Jetpack DataStore
    implementation(libs.datastore.preferences)

    // Core Android
    implementation(libs.androidx.core)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.navigation)



    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation)
    kapt(libs.hilt.androidx.compiler)

    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Retrofit & Network
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.codegen)

    // Room
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    // Add MultiDex
    implementation(libs.multidex)

    // Paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    // Image Loading
    implementation(libs.coil.compose)

    // Accompanist
    implementation(libs.bundles.accompanist)

    // Testing
    testImplementation(libs.bundles.testing)
    androidTestImplementation(libs.bundles.android.testing)
    androidTestImplementation(platform(libs.compose.bom))

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}