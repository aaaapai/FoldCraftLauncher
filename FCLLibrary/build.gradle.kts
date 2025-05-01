plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.tungsten.fcllibrary"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    lint {
        targetSdk = libs.versions.targetSdk.get().toInt()
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            zipAlignEnabled = true
            shrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("fordebug") {
            initWith(getByName("debug"))
            resValue("string", "file_browser_provider", "com.tungsten.fcl.debug.provider")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":FCLauncher"))
    implementation(project(":FCLCore"))
    implementation("commons-io:commons-io:2.19.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.13.0-alpha13")
    implementation("androidx.asynclayoutinflater:asynclayoutinflater:1.1.0")
    implementation("net.fornwall:jelf:0.9.0")
    implementation("com.github.bumptech.glide:glide:5.0.0-rc01")
}
