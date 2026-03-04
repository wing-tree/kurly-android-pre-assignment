plugins {
    alias(libs.plugins.convention.plugin.android.library)
    alias(libs.plugins.convention.plugin.dagger.hilt)
    alias(libs.plugins.jetbrains.kotlin.plugin.serialization)
}

android {
    namespace = "kurly.android.pre.assignment.data"
}

dependencies {
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.jakewharton.retrofit.retrofit2.kotlinx.serialization.converter)
    implementation(libs.jetbrains.kotlinx.serialization.json)
    implementation(libs.squareup.okhttp3.logging.interceptor)
    implementation(libs.squareup.okhttp3.okhttp)
    implementation(libs.squareup.retrofit2.retrofit)

    implementation(project(":mockserver"))
}
