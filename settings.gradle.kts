pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

rootProject.name = "ValuShoppingNew"
include(":app")
include(":core:model")
include(":core:network")
include(":core:database")
include(":core:common")
include(":core:domain")
include(":core:navigation")
include(":feature:informative")
include(":core:ui")
include(":feature:home")
include(":feature:detail")
include(":feature:profile")
include(":feature:configProduct")
include(":feature:auth")