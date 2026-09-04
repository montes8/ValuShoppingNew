import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

val keystoreProps = Properties().apply {
    val keystoreFile = rootProject.file("keystore.properties")
    if (keystoreFile.exists()) {
        load(FileInputStream(keystoreFile))
    }
}

fun config(k: String): String {
    val value = keystoreProps.getProperty(k) ?: project.findProperty(k)?.toString() ?: ""
    if (value.isEmpty()) {
        println("WARNING: Property $k is missing in keystore.properties or gradle.properties")
    }
    return "\"$value\""
}

val baseUrl = "BASE_URL"
val pinning = "PINNIG"
val pinningRoot = "PINNIG_ROOT"
val hashOne = "HASH_ONE"
val hashTwo = "HASH_TWO"

android {
    namespace = "com.tayler.core.network"
    compileSdk = 37

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
            buildConfigField("String", hashOne, config("hash.one"))
            buildConfigField("String", hashTwo, config("hash.two"))
        }
        debug {
            buildConfigField("String", baseUrl, config("qa.server.url"))
            buildConfigField("String", pinning, config("pinning.encryption"))
            buildConfigField("String", pinningRoot, config("pinning.root"))
            buildConfigField("String", hashOne, config("hash.one"))
            buildConfigField("String", hashTwo, config("hash.two"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.tay.compose.library)
    
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:database"))
    
    implementation(libs.androidx.core.ktx)
    
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockwebserver)
}
