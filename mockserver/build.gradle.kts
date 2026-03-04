plugins {
    alias(libs.plugins.convention.plugin.android.library)
    alias(libs.plugins.convention.plugin.dagger.hilt)
}

android {
    namespace = "mockserver"
}

dependencies {
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.google.dagger.hilt.android.testing)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.google.android.material)
    implementation(libs.google.code.gson)
    implementation(libs.squareup.okhttp3.okhttp)

    testImplementation(libs.google.dagger.hilt.android.testing)
    testImplementation(libs.junit)
}
