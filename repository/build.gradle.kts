plugins {
    alias(libs.plugins.androidLibrary)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}
fun config(k: String): String = "\"${project.properties[k]}\""
val baseUrl = "BASE_URL"
val pinning = "PINNIG"
val pinningRoot = "PINNIG_ROOT"

android {
    namespace = "com.tayler.repository"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 25

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            buildConfigField("String", baseUrl, config("production.server.url"))
            buildConfigField("String", pinning, config("pinning.encryption"))
            buildConfigField("String", pinningRoot, config("pinning.root"))
            resValue("string", "encryption_key", config("preferences.encryption"))

        }
        debug {
            buildConfigField("String", baseUrl, config("qa.server.url"))
            buildConfigField("String", pinning, config("pinning.encryption"))
            buildConfigField("String", pinningRoot, config("pinning.root"))
            resValue("string", "encryption_key", config("preferences.encryption"))

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        buildConfig = true
        resValues = true
    }

}

dependencies {
    // Dagger Hilt con KSP
    implementation("com.google.dagger:hilt-android:2.60.1")
    ksp("com.google.dagger:hilt-compiler:2.60.1")
    implementation("androidx.security:security-crypto:1.1.0-alpha03")
    // Networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation(project(":entity"))
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.mockwebserver)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}