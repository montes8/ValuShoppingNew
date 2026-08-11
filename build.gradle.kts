import java.util.Properties

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinAndroid) apply false

    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    id("com.google.dagger.hilt.android") version "2.60.1" apply false
    id("org.sonarqube") version "7.4.0.8496"
    id("jacoco")
}

val fileFilter = listOf(
    "**/R.class",
    "**/R$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*Test*.*",
    "android/**/*.*",
    "**/*\$ViewInjector*.*",
    "**/*\$Lambda$*.*",
    "**/*Companion*.*",
    "**/*Module*.*",
    "**/*_Factory*.*",
    "**/*_MembersInjector*.*",
    "**/*_HiltModules*.*",
    "**/*Hilt*.*",
    "**/di/**",
    "**/*Dagger*",
    "**/*HiltWrapper*",
    "**/hilt_aggregated_deps/**",
    "**/ComposableSingletons**",
    "**/QuantumModelsKt**",
    "**/*_ComponentTreeDeps*",
    "**/ui/**/Screen*.class",
    "**/component/**/Screen*.class"
)

subprojects {
    apply(plugin = "jacoco")

    tasks.register<JacocoReport>("testCoverageReport") {
        val isAndroid = project.plugins.hasPlugin("com.android.application") || project.plugins.hasPlugin("com.android.library")
        val testTaskName = if (isAndroid) "testDebugUnitTest" else "test"
        
        dependsOn(testTaskName)
        
        group = "Reporting"
        description = "Generate Jacoco coverage reports"

        reports {
            xml.required.set(true)
            html.required.set(true)
        }

        val mainSrc = "${project.projectDir}/src/main/java"
        val kotlinSrc = "${project.projectDir}/src/main/kotlin"
        
        sourceDirectories.setFrom(files(mainSrc, kotlinSrc))
        
        val classDirs = if (isAndroid) {
            listOf(
                fileTree("${project.layout.buildDirectory.get()}/tmp/kotlin-classes/debug") { 
                    exclude(fileFilter)
                    exclude("**/Screen*.class")
                    exclude("**/component/*.class")
                },
                fileTree("${project.layout.buildDirectory.get()}/intermediates/javac/debug/classes") { exclude(fileFilter) },
                fileTree("${project.layout.buildDirectory.get()}/intermediates/classes/debug/transformDebugClassesWithAsm/dirs") { exclude(fileFilter) }
            )
        } else {
            listOf(
                fileTree("${project.layout.buildDirectory.get()}/classes/kotlin/main") { exclude(fileFilter) },
                fileTree("${project.layout.buildDirectory.get()}/classes/java/main") { exclude(fileFilter) }
            )
        }
        classDirectories.setFrom(classDirs)

        val execData = if (isAndroid) {
            fileTree("${project.layout.buildDirectory.get()}") {
                include("outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec")
            }
        } else {
            fileTree("${project.layout.buildDirectory.get()}") {
                include("jacoco/test.exec")
            }
        }
        executionData.setFrom(execData)
    }
}

tasks.register<Delete>("clean") {
    group = "build"
    description = "optimize"
    delete(layout.buildDirectory)
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { load(it) }
    }
}

sonarqube {
    properties {
        property("sonar.projectName", "ValuShooping")
        property("sonar.projectKey", "valuShooping")
        property("sonar.host.url", "http://localhost:9000/")
        property("sonar.token", localProperties.getProperty("sonar.token") ?: "")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.test.inclusions", "**/*Test*/**")
        property("sonar.exclusions", "**/R.class, **/BuildConfig.*, **/Manifest*.*, **/di/**, **/*Module.kt, **/*_Factory.java, **/*_MembersInjector.java, **/*_HiltModules*")
        property("sonar.coverage.exclusions", "**/ui/**, **/component/**, **/application/**, **/*Activity.kt, **/*Fragment.kt, **/*Adapter.kt")
        property("sonar.junit.reportPaths", "**/build/test-results/testDebugUnitTest/*.xml")
        property("sonar.coverage.jacoco.xmlReportPaths", "**/build/reports/jacoco/testCoverageReport/testCoverageReport.xml")
    }
}
