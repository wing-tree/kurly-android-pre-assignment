package convention.plugin

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import convention.plugin.extension.function.androidTestImplementation
import convention.plugin.extension.function.debugImplementation
import convention.plugin.extension.function.implementation
import convention.plugin.extension.property.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType

class AndroidxComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = with(receiver = extensions) {
                findByType<ApplicationExtension>() ?: findByType<LibraryExtension>()
            }

            extension?.buildFeatures?.compose = true

            apply(plugin = "org.jetbrains.kotlin.plugin.compose")

            dependencies {
                androidTestImplementation(libs.findLibrary("androidx.compose.ui.test.junit4").get())
                androidTestImplementation(platform(libs.findLibrary("androidx.compose.bom").get()))

                debugImplementation(libs.findLibrary("androidx.compose.ui.test.manifest").get())
                debugImplementation(libs.findLibrary("androidx.compose.ui.tooling").get())

                implementation(libs.findLibrary("androidx-activity-compose").get())
                implementation(libs.findLibrary("androidx.compose.animation.graphics.android").get())
                implementation(libs.findLibrary("androidx.compose.material3").get())
                implementation(libs.findLibrary("androidx.compose.ui").get())
                implementation(libs.findLibrary("androidx.compose.ui.graphics").get())
                implementation(libs.findLibrary("androidx.navigation.compose").get())
                implementation(libs.findLibrary("androidx.paging.compose").get())
                implementation(platform(libs.findLibrary("androidx.compose.bom").get()))
            }
        }
    }
}
