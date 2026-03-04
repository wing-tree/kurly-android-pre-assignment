package convention.plugin

import convention.plugin.extension.function.implementation
import convention.plugin.extension.property.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class LevelPlayConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                implementation(libs.findLibrary("applovin.sdk").get())
                implementation(libs.findLibrary("facebook.android.audience.network.sdk").get())
                implementation(libs.findLibrary("unity3d.ads.mediation.applovin.adapter").get())
                implementation(libs.findLibrary("unity3d.ads.mediation.facebook.adapter").get())
                implementation(libs.findLibrary("unity3d.ads.mediation.sdk").get())
                implementation(libs.findLibrary("unity3d.ads.mediation.unityads.adapter").get())
                implementation(libs.findLibrary("unity3d.ads.unity.ads").get())
            }
        }
    }
}
