import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "es.pictorario.app"
    compileSdk = 36

    defaultConfig {
        // Se conserva el applicationId del proyecto B4A para publicar como
        // actualización de la ficha existente en Google Play.
        applicationId = "javi.prieto.pictorario"
        minSdk = 26
        targetSdk = 36
        versionCode = 200
        versionName = "2.0"
    }

    // keystore.properties no se versiona. Sin él el release se compila igualmente,
    // pero sin firmar: así un clon limpio del repositorio puede construir el
    // proyecto entero sin disponer de la clave de subida.
    val keystorePropertiesFile = rootProject.file("keystore.properties")
    val hasKeystore = keystorePropertiesFile.exists()

    signingConfigs {
        create("release") {
            if (hasKeystore) {
                val keystoreProperties = Properties()
                keystoreProperties.load(FileInputStream(keystorePropertiesFile))
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
            }
        }
    }

    buildTypes {
        debug {
            // Permite tener instalada a la vez la versión B4A publicada, para
            // comparar pantalla a pantalla durante la migración.
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        release {
            isMinifyEnabled = true
            signingConfig = if (hasKeystore) signingConfigs.getByName("release") else null
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }
}

// Las versiones están fijadas a las últimas compatibles con AGP 8.10.1 y
// compileSdk 36, la misma cadena de herramientas que el proyecto hermano
// Colorear. Las versiones más recientes de AndroidX exigen AGP 9.1 y
// compileSdk 37; esa actualización queda anotada como mejora futura.
dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2025.12.01")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    implementation("androidx.activity:activity-compose:1.11.0")
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.4")

    implementation("androidx.datastore:datastore-core:1.1.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

    testImplementation("junit:junit:4.13.2")
}
