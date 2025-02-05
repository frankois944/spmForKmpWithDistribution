import com.vanniktech.maven.publish.SonatypeHost
import io.github.frankois944.spmForKmp.definition.SwiftDependency
import io.github.frankois944.spmForKmp.definition.product.ProductName
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.konan.target.KonanTarget
import java.net.URI

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.spmForKmp)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

group = "fr.frankois944"
version = "0.0.6"

kotlin {

    explicitApi()

    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }

    val xcFramework = XCFramework()

    fun getFrameworks(path: String, target: KonanTarget) : List<String>{
        val directory = when (target) {
            KonanTarget.IOS_ARM64 -> "arm64-apple-ios"
            KonanTarget.IOS_SIMULATOR_ARM64 -> "arm64-apple-ios-simulator"
            KonanTarget.IOS_X64 -> "x86_64-apple-ios-simulator"
            else -> ""
        }

        val buildDir = "$path/$directory/release"

        val frameworks = File(buildDir).listFiles()?.filter { file -> file.name.endsWith(".framework") } ?: emptyList()
        return buildList {
            frameworks.forEach { framework ->
                add("-framework")
                add(framework.nameWithoutExtension)
            }
        } + "-F$buildDir" + "-L$buildDir"
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { target ->
        target.binaries.framework {
            // explicitly link dependency framework to the xcFramework
            // you will find the required library in the check directory of the spmForKmp build directory
            // library/build/spmKmpPlugin/appleDeps/scratch/checkouts/FFmpegKit/Package.swift
            linkerOpts(
                getFrameworks("${layout.buildDirectory.asFile.get().path}/spmKmpPlugin/appleDeps/scratch/", target.konanTarget)
                        + "-lxml2"
                        + "-lz"
                        + "-liconv"
                        + "-lresolv"
                        + "-lMoltenVK"
            )
            xcFramework.add(this)
        }
        target.compilations {
            val main by getting {
                cinterops.create("appleDeps")
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        androidMain.dependencies {
            implementation(libs.media3.exoplayer)
            implementation(libs.media3.exoplayer.dash)
            implementation(libs.media3.ui)
        }
    }
}

android {
    namespace = "fr.frankois944.kmpPlayer.android"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

mavenPublishing {
    coordinates(group.toString(), "kmpPlayer", version.toString())
}


swiftPackageConfig {
    create("appleDeps") {
        minIos = "15.0"
        minMacos = "10.15"
        minTvos = "13.0"
        minWatchos = "2.0"
        dependency(
            SwiftDependency.Package.Remote.Branch(
                url = URI("https://github.com/kingslay/KSPlayer"),
                products = {
                    add("KSPlayer")
                },
                branch = "main"
            )
        )
    }
}