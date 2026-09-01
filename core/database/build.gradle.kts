plugins {
    alias(libs.plugins.androidLibrary)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.tayler.core.database"
    compileSdk = 37

    defaultConfig {
        minSdk = 25
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            resValue("string", "encryption_key", config("preferences.encryption"))
        }
        debug {
            resValue("string", "encryption_key", config("preferences.encryption"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        resValues = true
    }
}

fun config(k: String): String {
    val value = project.findProperty(k)?.toString() ?: ""
    if (value.isEmpty()) {
        println("WARNING: Property $k is missing in gradle.properties")
    }
    return "\"$value\""
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.security.crypto)
    implementation(libs.androidx.core.ktx)
}
