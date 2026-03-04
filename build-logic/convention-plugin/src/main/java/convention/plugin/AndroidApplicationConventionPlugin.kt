package convention.plugin

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.application")
        }

        target.extensions.configure<ApplicationExtension> {
            compileSdk {
                version = release(36) {
                    minorApiLevel = 1
                }
            }

            defaultConfig {
                minSdk = 28
                targetSdk = 36

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            buildTypes {
                release {
                    isMinifyEnabled = false
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }

            buildFeatures {
                buildConfig = true
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }

            when (this) {
                is KotlinAndroidProjectExtension -> compilerOptions
                is KotlinJvmProjectExtension -> compilerOptions
                else -> null
            }?.apply {
                jvmTarget = JvmTarget.JVM_21
            }
        }
    }
}
