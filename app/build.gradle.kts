    plugins {
        alias(libs.plugins.android.application)
        alias(libs.plugins.kotlin.android)
        alias(libs.plugins.kotlin.compose)
        id("com.google.devtools.ksp") version "1.9.20-1.0.14" // Plugin KSP (nếu bạn dùng)
        id("kotlin-kapt")
        alias(libs.plugins.google.gms.google.services) // Plugin KAPT
        kotlin("plugin.serialization") version "1.9.20"

    }

android {
    namespace = "com.example.jitbook"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.jitbook"
        minSdk = 28
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
//    Retrofit + kotlinx.serialization
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

//    Coil (Hiển thị ảnh bìa sách)
    implementation("io.coil-kt:coil-compose:2.4.0")

//    ViewModel + StateFlow (Quản lý trạng thái dữ liệu)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

//    Hilt (Dependency Injection - DI)
    implementation("com.google.dagger:hilt-android:2.50")
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.core.i18n)
    implementation(libs.firebase.database)
    implementation(libs.firebase.crashlytics.buildtools)
    kapt("com.google.dagger:hilt-compiler:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

//    Navigation Compose (Điều hướng giữa các màn hình)
    implementation("androidx.navigation:navigation-compose:2.7.6")

//    Room Database (Lưu dữ liệu offline - tùy chọn)
    implementation("androidx.room:room-runtime:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")

//    Text-to-Speech API (Chuyển văn bản thành giọng nói)
    implementation("androidx.compose.ui:ui:1.5.0") // Đảm bảo dùng Compose UI

    implementation("androidx.compose.material:material-icons-extended:1.3.0") // Use the appropriate version

    implementation("androidx.compose.material3:material3:1.2.1")

    implementation("com.google.accompanist:accompanist-pager:0.34.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.34.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("io.ktor:ktor-client-core:2.3.7")
    implementation("io.ktor:ktor-client-cio:2.3.7") // hoặc ktor-client-okhttp tùy bạn
    implementation("io.insert-koin:koin-androidx-compose:3.5.3")
// Ktor Content Negotiation + kotlinx.serialization JSON
    implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
    implementation("io.ktor:ktor-client-logging:2.3.7")
    implementation("io.ktor:ktor-client-okhttp:2.3.7")

    implementation(libs.material3.window.size)

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