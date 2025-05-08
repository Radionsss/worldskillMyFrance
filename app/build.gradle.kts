plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "workwork.company.worldskillstest"
    compileSdk = 34

    defaultConfig {
        applicationId = "workwork.company.worldskillstest"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("io.github.vanpra.compose-material-dialogs:datetime:0.8.1-rc")
    implementation("org.burnoutcrew.composereorderable:reorderable:0.9.6")

    implementation("me.saket.swipe:swipe:1.1.1")
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

    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)

    ksp(libs.hilt.compiler)


    // Navigation
    implementation(libs.androidx.navigation.compose)

    //Splash Screen
    // implementation(libs.androidx.core.splashscreen)

    //Glassmorphic effect
    // implementation(libs.haze)

    //Dots indicator
    //implementation(libs.dotsindicator)

    //added by Vitali
    // implementation(libs.androidx.runtime)

    //Retrofit and Moshi
    implementation(libs.converter.moshi)
    implementation(libs.retrofit)

    //Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    //  testImplementation(libs.androidx.room.testing)

    //Datastore
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.kotlinx.serialization.json)

    //Coil
    implementation(libs.coil.compose)
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    // Swipe Refresh
    //implementation(libs.accompanist.swiperefresh)

    // Lottie
    implementation(libs.lottie.compose)

    // Gson converter
    implementation(libs.converter.gson)
}