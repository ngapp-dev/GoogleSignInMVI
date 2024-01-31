@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    namespace = "com.ngapps.googlesigninmvi"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ngapps.googlesigninmvi"
        minSdk = 21
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
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.activity.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.security)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
    implementation(libs.coil.kt.svg)
    implementation(libs.core.ktx)
    implementation(libs.gms.auth)
    implementation(libs.hilt.android)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewModel.compose)
    implementation(libs.material3)
    implementation(libs.okhttp.logging)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(platform(libs.compose.bom))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    kapt(libs.hilt.compiler)
}