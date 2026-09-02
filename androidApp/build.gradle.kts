import org.gradle.kotlin.dsl.libs
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.compilerPlugin)
}

// apply gms & firebase plugin only for gms build flavour
if (gradle.startParameter.taskRequests.toString().contains("gms", ignoreCase = true)) {
    apply(plugin = libs.plugins.google.services.get().pluginId)
    apply(plugin = libs.plugins.firebase.crashlytics.get().pluginId)
}

private val projectPackageName = libs.versions.project.packageName.get()
private val localProperties = getLocalProperties(rootProject)

android {
    namespace = "${projectPackageName}.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = projectPackageName
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = libs.versions.project.versionCode.get().toInt()
        versionName = libs.versions.project.versionName.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    signingConfigs {
        if (localProperties.hasDebugStoreConfig()) {
            getByName("debug") {
                keyAlias = localProperties["DEBUG_KEY_ALIAS"] as? String
                keyPassword = localProperties["DEBUG_KEY_PASSWORD"] as? String
                storeFile = file(localProperties["DEBUG_STORE_FILE"] as String)
                storePassword = localProperties["DEBUG_STORE_PASSWORD"] as? String
            }
        }
        if (localProperties.hasReleaseStoreConfig()) {
            create("release") {
                keyAlias = localProperties["RELEASE_KEY_ALIAS"] as? String
                keyPassword = localProperties["RELEASE_KEY_PASSWORD"] as? String
                storeFile = file(localProperties["RELEASE_STORE_FILE"] as String)
                storePassword = localProperties["RELEASE_STORE_PASSWORD"] as? String
            }
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = when {
                localProperties.hasReleaseStoreConfig() -> signingConfigs.getByName("release")
                else -> signingConfigs.getByName("debug")
            }
        }
    }

    flavorDimensions += "services"
    productFlavors {
        create("gms") {
            isDefault = true
            dimension = "services"
        }
        create("default") {
            dimension = "services"
            dependenciesInfo {
                includeInApk = false
                includeInBundle = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(projects.composeApp)
    implementation(libs.compose.material3)
    implementation(libs.compose.preview)
    implementation(libs.androidx.activity.compose)

    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)

    implementation(libs.filekit.core)
    implementation(libs.filekit.dialogs)

    "gmsImplementation"(platform(libs.firebase.bom))
    "gmsImplementation"(libs.firebase.crashlytics.ktx)
    "gmsImplementation"(libs.firebase.analytics.ktx)
}

fun getLocalProperties(root: Project): Properties {
    val localProperties = Properties()
    val localPropertiesFile = root.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
    }
    return localProperties
}

fun Properties.hasDebugStoreConfig() = this.containsKey("DEBUG_STORE_FILE")
fun Properties.hasReleaseStoreConfig() = this.containsKey("RELEASE_STORE_FILE")
