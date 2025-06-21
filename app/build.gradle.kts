plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id ("androidx.navigation.safeargs.kotlin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

}

android {
    namespace = "com.example.carecallapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.carecallapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.recyclerview)
    implementation (libs.androidx.navigation.ui.ktx.v277) // أو آخر إصدار عندك
    implementation (libs.material.v1100)


    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    //live data
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.common)

    //Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.android)

    //Hilt Dagger
    implementation(libs.hilt.android)
    implementation(libs.androidx.annotation)
    ksp(libs.hilt.compiler)

    //lottie for animation
    implementation("com.airbnb.android:lottie:6.0.0")

    //circular image view
    implementation("de.hdodenhof:circleimageview:3.1.0")

    //swipe
    implementation(libs.swipelayout)

    //unit testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //google maps
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.okhttp)

}

secrets {
// To add your Maps API key to this project:
// 1. If the secrets.properties file does not exist, create it in the same folder as the local.properties file.
// 2. Add this line, where YOUR_API_KEY is your API key:
// MAPS_API_KEY=YOUR_API_KEY
    propertiesFileName = "secrets.properties"
// A properties file containing default secret values. This file can be
// checked in version control.
    defaultPropertiesFileName = "local.defaults.properties"
}
