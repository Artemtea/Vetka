plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

val clerkPublishableKey = providers.fileContents(
    rootProject.layout.projectDirectory.file(".env")
).asText.map { content ->
    content.lineSequence()
        .map { it.trim() }
        .firstOrNull {
            it.startsWith("CLERK_PUBLISHABLE_KEY=")
        }
        ?.substringAfter("=")
        ?.trim()
        ?.removeSurrounding("\"")
        ?.removeSurrounding("'")
        ?: error("В .env отсутствует CLERK_PUBLISHABLE_KEY")
}.get()

android {
    namespace = "com.example.vetka"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.vetka"
        minSdk = 35
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "CLERK_PUBLISHABLE_KEY",
            "\"$clerkPublishableKey\""
        )
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation("com.clerk:clerk-android-api:1.4.1")
    implementation("androidx.navigation:navigation-compose:2.10.2")
    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}