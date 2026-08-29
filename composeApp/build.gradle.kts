import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKmpLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.compilerPlugin)
}

kotlin {
    android {
        namespace = libs.versions.project.packageName.get()
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }

        androidResources {
            enable = true
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName.set("composeApp")
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    // Serve sources to debug inside browser
                    static(directory = project.rootDir.path, false)
                    static(directory = project.projectDir.path, false)
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.materialIconsExtended)
            implementation(libs.compose.ui)
            implementation(libs.compose.preview)
            implementation(libs.compose.components.resources)
            implementation(libs.adaptive)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.kotlinx.coroutines)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.json)
            implementation(libs.androidx.navigation)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            api(libs.koin.annotations)

            implementation(libs.settings.noArg)
            implementation(libs.settings.coroutines)
            implementation(libs.settings.serialization)

            implementation(libs.ktor.client.core)
            implementation(libs.okio)

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            implementation(libs.filekit.core)
            implementation(libs.filekit.dialogs)
            implementation(libs.filekit.dialogs.compose)
            implementation(libs.filekit.coil)
        }
        androidMain.dependencies {
            implementation(libs.compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.android)

            implementation(libs.androidx.media3.exoplayer)
            implementation(libs.androidx.media3.ui)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.okio)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.ktor.client.cio)
            implementation(libs.mp3spi)
        }

        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
            implementation(libs.okio.fakefilesystem)
            implementation(npm("@js-joda/core", "5.6.3"))
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

compose.resources {
    packageOfResClass = "${libs.versions.project.packageName.get()}.resources"
}

compose.desktop {
    application {
        mainClass = "${libs.versions.project.packageName.get()}.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Exe, TargetFormat.Msi, TargetFormat.Dmg, TargetFormat.Deb)
            packageName = "Quran Planet"
            packageVersion = libs.versions.project.versionName.get()
            vendor = libs.versions.project.vendor.get()
            val commonIcon = "src/commonMain/composeResources/drawable/icon.ico"
            windows {
                iconFile.set(project.file(commonIcon))
                shortcut = true
            }
            linux {
                iconFile.set(project.file(commonIcon))
                shortcut = true
            }
            macOS { iconFile.set(project.file(commonIcon)) }

            buildTypes.release.proguard {
                isEnabled.set(false)
                obfuscate.set(false)
            }

            tasks.withType<JavaExec>().configureEach {
                if (name.contains("release", ignoreCase = true)) {
                    systemProperty("app.build.mode", "release")
                } else {
                    systemProperty("app.build.mode", "debug")
                }
            }
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.ui.tooling)
}
