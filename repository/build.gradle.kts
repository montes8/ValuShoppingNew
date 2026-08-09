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
            enableUnitTestCoverage = true
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
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.security.crypto)
    // Networking
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)
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