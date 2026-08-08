plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinAndroid) apply false

    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    id("com.google.dagger.hilt.android") version "2.60.1" apply false
    id("org.sonarqube") version "7.4.0.8496"
}

tasks.register<Delete>("clean") {
    description = "optimize"
    delete(layout.buildDirectory)
}

sonarqube {
    properties {
        property("sonar.projectName", "ValuShooping")
        property("sonar.projectKey", "valuShooping")
        property("sonar.host.url", "http://localhost:9000/")
        property("sonar.tests", listOf("src/test/java"))
        property("sonar.test.inclusions", "**/*Test*/**")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.sources", "src/main/java")
        property("sonar.token", "squ_8d97c3694c3f874004f9c2d50c535cbd662dbdef")
        property("sonar.exclusions", "**/*Test*/**,*.json,**/*test*/**,**/.gradle/**,**/R.class")
    }
}

//./gradlew sonarqube