
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.xavier_carpentier.realestatemanager"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.xavier_carpentier.realestatemanager"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        val mapsApiKey: String = providers.gradleProperty("MAPS_API_KEY").getOrElse("DEFAULT_API_KEY")
        buildConfigField("String", "MAPS_API_KEY", "\"$mapsApiKey\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
        compose=true
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.preference)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.fragment.ktx)

    //Location
    implementation (libs.play.services.location)
    implementation (libs.accompanist.permissions)

    //secret properties
    implementation(libs.secrets.gradle.plugin)

    //Google map
    implementation(libs.play.services.maps)
    // KTX for the Maps SDK for Android library
    implementation(libs.maps.ktx)
    // KTX for the Maps SDK for Android Utility Library
    implementation(libs.maps.utils.ktx)

    //Google Map Compose
    implementation(libs.maps.compose)
    // Google Maps Compose utility library
    implementation(libs.maps.compose.utils)
    // Google Maps Compose widgets library
    implementation(libs.maps.compose.widgets)

    //Compose
    implementation(libs.activity.compose)
    implementation (libs.androidx.ui)
    implementation (libs.androidx.ui.tooling.preview)
    implementation (libs.androidx.material3)
    implementation (libs.androidx.material3.window.sizeclass)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material3.adaptive.navigation.suite.android)
    // Compose - Bibliothèque pour les animations
    implementation (libs.androidx.animation)
    implementation(libs.androidx.ui.text.google.fonts)
    // Compose - Bibliothèque pour les tests
    androidTestImplementation (libs.androidx.ui.test.junit4)
    // Compose - Outil pour l'aperçu et le débogage
    debugImplementation (libs.androidx.ui.tooling)
    debugImplementation (libs.androidx.ui.test.manifest)
    // Compose - Integration with activities
    implementation(libs.activity.compose)
    // Compose - Integration with ViewModels
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Compose - Integration with LiveData
    implementation(libs.androidx.runtime.livedata)
    // Compose - custom design system based on Foundation)
    implementation(libs.androidx.material.icons.core)
    // Compose - Add full set of material icons
    implementation(libs.androidx.material.icons.extended)
    // Compose - Coil for load image by URL
    implementation(libs.coil.compose)




    //Room
    implementation(libs.androidx.room.runtime)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //Hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    kapt (libs.google.hilt.compiler)

    //Test Instrumentation
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.google.hilt.android.testing)
    kaptAndroidTest (libs.google.hilt.compiler)

    //test Unit
    testImplementation(libs.junit)
    testImplementation(libs.google.hilt.android.testing)
    testImplementation (libs.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    kaptTest (libs.hilt.compliler)
    // Optional -- Robolectric environment
    testImplementation (libs.robolectric)
    testImplementation (libs.androidx.core)
    // Optional -- Mockito framework
    testImplementation (libs.mockito.core)
    // Optional -- mockito-kotlin
    testImplementation (libs.mockito.kotlin)
    testImplementation (libs.androidx.core.testing)

}

secrets {

    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "local.defaults.properties"
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}