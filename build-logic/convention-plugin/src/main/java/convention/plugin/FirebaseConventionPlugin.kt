package convention.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import convention.plugin.extension.function.implementation
import convention.plugin.extension.property.libs

class FirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.google.gms.google-services")

            dependencies {
                implementation(libs.findLibrary("google.firebase.ai").get())
                implementation(libs.findLibrary("google.firebase.analytics").get())
                implementation(libs.findLibrary("google.firebase.auth").get())
                implementation(libs.findLibrary("google.firebase.crashlytics").get())
                implementation(libs.findLibrary("google.firebase.firestore").get())
                implementation(libs.findLibrary("google.firebase.messaging").get())
                implementation(libs.findLibrary("google.firebase.storage").get())
                implementation(platform(libs.findLibrary("google.firebase.bom").get()))
            }
        }
    }
}
