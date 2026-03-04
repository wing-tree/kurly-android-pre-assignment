# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Kurly Android Pre-Assignment: a grocery e-commerce app displaying product sections with different layout types (grid, horizontal, vertical). Uses a local mock server instead of a real backend -- all API responses come from JSON asset files.

## Build & Run Commands

```bash
./gradlew assembleDebug          # Build debug APK
./gradlew app:assembleDebug      # Build only :app module
./gradlew test                   # Run all unit tests
./gradlew mockserver:test        # Run mockserver unit tests only
./gradlew connectedAndroidTest   # Run instrumented tests
./gradlew clean                  # Clean build outputs
```

Requires JDK 21. Android SDK with compileSdk 36, minSdk 28, targetSdk 36.

## Architecture

### Module Structure

- **`:app`** -- Android application module. Jetpack Compose UI with Material3, Orbit MVI for state management, Hilt DI. Depends on `:data`.
- **`:data`** -- Android library. Networking layer with Retrofit + OkHttp + Kotlinx Serialization. DataStore for preferences. Depends on `:mockserver`.
- **`:mockserver`** -- Android library. OkHttp `Interceptor` (`MockInterceptor`) that intercepts HTTP requests and returns JSON from `assets/`. Simulates 1-3 second network delay. No real server needed.
- **`build-logic`** -- Included build with convention plugins that standardize Android, Compose, Hilt, and Room configurations across modules.

### Key Patterns

- **DI**: Dagger Hilt throughout. `@HiltAndroidApp` on `App`, `@AndroidEntryPoint` on `MainActivity`.
- **State management**: Orbit MVI (orbit-core, orbit-viewmodel, orbit-compose).
- **Serialization**: Kotlinx Serialization (not Gson) for network models in `:app` and `:data`. The `:mockserver` module uses Gson separately.
- **Image loading**: Coil 3 with Compose integration.
- **Navigation**: Jetpack Navigation Compose.

### Mock Server API

The `MockInterceptor` intercepts OkHttp requests and routes:
- `GET /sections?page={n}` -- returns paginated section list from `sections/sections_{n}.json`
- `GET /section/products?sectionId={id}` -- returns products for a section from `section/products/section_products_{id}.json`

Section types in response: `"grid"`, `"horizontal"`, `"vertical"` -- each should render with a distinct layout.

Product fields: `id`, `name`, `image`, `originalPrice`, `discountedPrice` (optional), `isSoldOut`.

### Convention Plugins

Located in `build-logic/convention-plugin/`. Applied via version catalog aliases (e.g., `convention.plugin.android.application`). Key plugins:
- `AndroidApplicationConventionPlugin` / `AndroidLibraryConventionPlugin` -- sets compileSdk, minSdk, Java 21, JVM target 21
- `AndroidxComposeConventionPlugin` -- enables Compose, adds BOM + core Compose dependencies
- `DaggerHiltConventionPlugin` -- applies KSP + Hilt plugin, adds Hilt dependencies

## Version Catalog

All dependencies managed in `gradle/libs.versions.toml`. Reference libraries as `libs.<alias>` in build scripts.