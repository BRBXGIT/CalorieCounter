plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    //Navigation
    alias(libs.plugins.kotlin.serialization)
    //Kapt
    id("kotlin-kapt")
    //Hilt
    id("com.google.dagger.hilt.android")
    //Firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.caloriecounter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.caloriecounter"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //Animated splash screen
    implementation(libs.androidx.core.splashscreen)
    //Retrofit impl
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    //Room db impl
    implementation(libs.androidx.room.ktx)
    implementation(libs.firebase.storage.ktx)
    kapt(libs.androidx.room.compiler)
    //Hilt impl
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    //Coil impl
    implementation(libs.coil.compose)
    //Firebase impl
    implementation(libs.firebase.auth.ktx)
    implementation(libs.play.services.auth)
    implementation(platform(libs.firebase.bom))
    //DataStore impl
    implementation(libs.androidx.datastore.preferences)
    //Adaptivity impl
    implementation("androidx.compose.material3:material3-window-size-class:1.2.1")
    //Nav impl
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    //Basic impl
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}