plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.tungsten.fclauncher"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    lint {
        targetSdk = libs.versions.targetSdk.get().toInt()
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
        create("fordebug") {
            initWith(getByName("debug"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    externalNativeBuild {
        ndkBuild {
            path = file("src/main/jni/Android.mk")
            abiFilters.addAll(listOf("arm64-v8a"))
        }
    }

    ndkVersion = "28.1.13356709"

    buildFeatures {
        prefab = true
    }

    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation("com.bytedance:bytehook:1.0.10")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.13.0-alpha13")
}
