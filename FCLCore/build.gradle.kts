plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.tungsten.fclcore"
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("fordebug") {
            initWith(getByName("debug"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(project(":FCLauncher"))
    implementation(project(":ZipFileSystem"))
    implementation("org.nanohttpd:nanohttpd:2.3.1")
    implementation("com.github.steveice10:opennbt:1.5")
    implementation("org.tukaani:xz:1.10")
    implementation("commons-io:commons-io:2.19.0")
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("org.apache.commons:commons-compress:1.27.1")
    implementation("io.hotmoka:toml4j:0.7.3")
    implementation("org.jenkins-ci:constant-pool-scanner:1.2")
    implementation("com.google.code.gson:gson:2.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.14.0-alpha01")
    implementation("org.jsoup:jsoup:1.20.1")
}
