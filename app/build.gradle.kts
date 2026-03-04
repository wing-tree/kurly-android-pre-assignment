plugins {
    alias(libs.plugins.convention.plugin.android.application)
    alias(libs.plugins.convention.plugin.androidx.compose)
    alias(libs.plugins.convention.plugin.dagger.hilt)
    alias(libs.plugins.jetbrains.kotlin.plugin.serialization)
}

android {
    namespace = "kurly.android.pre.assignment"

    defaultConfig {
        applicationId = "kurly.android.pre.assignment"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.coil.kt.coil3.coil.compose)
    implementation(libs.coil.kt.coil3.coil.network.okhttp)
    implementation(libs.google.android.material)
    implementation(libs.jakewharton.retrofit.retrofit2.kotlinx.serialization.converter)
    implementation(libs.orbit.mvi.compose)
    implementation(libs.orbit.mvi.core)
    implementation(libs.orbit.mvi.viewmodel)

    implementation(project(":data"))
}
