plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    compileOnly(libs.android.tools.build.gradle)
    compileOnly(libs.androidx.room.gradle.plugin)
    compileOnly(libs.jetbrains.kotlin.gradle.plugin)
    compileOnly(libs.google.devtools.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        val packageName = "convention.plugin"

        register("android-application") {
            id = "convention.plugin.android.application"
            implementationClass = "$packageName.AndroidApplicationConventionPlugin"
        }

        register("android-library") {
            id = "convention.plugin.android.library"
            implementationClass = "$packageName.AndroidLibraryConventionPlugin"
        }

        register("androidx-compose") {
            id = "convention.plugin.androidx.compose"
            implementationClass = "$packageName.AndroidxComposeConventionPlugin"
        }

        register("androidx-room") {
            id = "convention.plugin.androidx.room"
            implementationClass = "$packageName.AndroidxRoomConventionPlugin"
        }

        register("dagger-hilt") {
            id = "convention.plugin.dagger.hilt"
            implementationClass = "$packageName.DaggerHiltConventionPlugin"
        }

        register("firebase") {
            id = "convention.plugin.firebase"
            implementationClass = "$packageName.FirebaseConventionPlugin"
        }

        register("level-play") {
            id = "convention.plugin.level.play"
            implementationClass = "$packageName.LevelPlayConventionPlugin"
        }
    }
}
