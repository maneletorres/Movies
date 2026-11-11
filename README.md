## Table of Contents
- [Overview](#overview)
- [Getting started](#getting-started)
- [Mock mode vs real API](#mock-mode-vs-real-api)
- [Architecture](#architecture)
- [Next steps](#next-steps)
- [Screenshots](#screenshots)

## Overview
Movies is a simple Android application built in Kotlin with Jetpack Compose. It follows modern Android app architecture patterns and is designed to be easy to review, demo, and extend without forcing a TMDB API key setup for every user.

## Getting started
The app supports two modes:

1. Mock mode (recommended for reviewers and consumers who do not want to create a TMDB API key)
2. Real API mode (for using live data from The Movie Database)

### 1) Mock mode (no TMDB key needed)
If the `BEARER_TOKEN` value is empty or not defined in `local.properties`, the app will use the local mock JSON files bundled in the project instead of calling the real TMDB API.

This is useful for:
- reviewers
- demo sessions
- environments where the user does not want to create a TMDB account
- avoiding API-key setup friction

Example:
```properties
BEARER_TOKEN=""
```

When the value is empty, the app uses the local assets under `app/src/debug/assets/mock/...`.

### 2) Real API mode (TMDB)
If you want to use the real API, create an account at:
https://developer.themoviedb.org/

Then generate an API key and set it in `local.properties` with this format:
```properties
BEARER_TOKEN="<BEARER_TOKEN VALUE FROM API>"
```

Example:
```properties
BEARER_TOKEN="fyHjbGcjOi..."
```

When `BEARER_TOKEN` is populated, the app will call the real TMDB endpoints normally.

## Mock mode vs real API
The app checks the value of `BEARER_TOKEN` in `local.properties`:

- If `BEARER_TOKEN.isEmpty()` → uses local mocked JSON data
- If `BEARER_TOKEN` is not empty → uses the real TMDB API

This allows the project to be demoable and review-friendly without requiring an external API key, while still supporting production usage with the real backend when configured.

## Architecture
The project is structured around a clean, maintainable architecture that separates responsibilities across layers.

### Core architectural patterns
- MVVM
- Clean Architecture
- SOLID principles
- Separation of concerns (SoC)
- Single-Activity navigation pattern

### Main components
- `ui/` — screens, composables, and UI state
- `domain/` — business logic and use cases
- `data/` — repositories, local storage, and remote API access
- `network/` — Retrofit, DTOs, and service layer
- `di/` — dependency injection wiring

### Android / Kotlin stack
- Kotlin
- Jetpack Compose
- Lifecycle components
- Navigation
- Coroutines + Flow
- Hilt / Dagger for dependency injection
- Retrofit + Moshi for API data handling

### Quality and tooling
- Testing support
- ktfmt formatting
- Version catalog management
- Gradle-based build configuration

## Next steps
- Add JaCoCo for code coverage
- Extend the mock dataset for more screens and edge cases
- Add more automated UI and integration tests
- Improve offline support and caching

## Screenshots
<img width="200" alt="Splash screen" src="https://user-images.githubusercontent.com/33102786/220135136-cfdc255c-41d7-4a3f-9664-bc97148dcf2f.jpg"> <img width="200" alt="Movies home screen" src="https://user-images.githubusercontent.com/33102786/220135247-76172262-990e-4009-bb95-62ce8e0a4933.jpg"> <img width="200" alt="Movie detail screen" src="https://user-images.githubusercontent.com/33102786/220135288-22982b86-a86c-4f1d-a036-551a340631e9.jpg">

## Firebase / Google Services note

This project is prepared to run as an Android app, but the `google-services.json` file required by Firebase is not included in the repository.

If you open the project in Android Studio without that file, the app will not start correctly unless Firebase is configured separately.

The `google-services.json` must include Firebase Authentication with the sign-in methods enabled for:
- anonymous sign-in
- Google sign-in
- email/password sign-in

Without this Firebase Authentication setup, the app will not start or function as expected.

For review/demo execution, the reviewer should either:
- create their own Firebase project and download the corresponding `google-services.json` with the required Authentication providers enabled, or
- contact the project maintainer for the configured file.

This repository intentionally does not include the Firebase configuration file to avoid committing environment-specific credentials.
