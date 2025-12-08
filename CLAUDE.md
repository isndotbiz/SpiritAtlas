# CLAUDE.md - SpiritAtlas Project Guidelines

This file provides guidance to Claude Code when working with the SpiritAtlas Android codebase.

## Project Overview

SpiritAtlas is a comprehensive spiritual profile and compatibility analysis Android application. It combines multiple spiritual calculation systems (Numerology, Astrology, Ayurveda, Human Design, Tantric) to provide personalized insights and relationship compatibility analysis.

## Technology Stack

- **Language**: Kotlin 1.9.25
- **UI Framework**: Jetpack Compose with Material3
- **Compose Compiler**: 1.5.15
- **Architecture**: Clean Architecture with MVVM
- **DI**: Hilt
- **Build System**: Gradle 8.13 with Kotlin DSL
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)

## Project Structure

```
SpiritAtlas/
├── app/                          # Main application module
│   └── navigation/               # Navigation graph and transitions
├── core/
│   ├── ui/                       # Shared UI components, theme, animations
│   │   ├── components/           # Reusable Compose components
│   │   ├── theme/               # Colors, typography, gradients
│   │   ├── animation/           # Spiritual animations
│   │   └── haptics/             # Haptic feedback utilities
│   ├── common/                  # Shared utilities
│   ├── numerology/              # Numerology calculations (Chaldean, Pythagorean)
│   ├── astro/                   # Astrology calculations
│   ├── ayurveda/                # Ayurveda/Dosha calculations
│   └── humandesign/             # Human Design calculations
├── data/                        # Data layer (repositories, database, network)
│   ├── repository/              # Repository implementations
│   └── ai/                      # AI provider integrations
├── domain/                      # Domain layer (models, use cases)
│   ├── model/                   # Domain models
│   └── service/                 # Business logic services
└── feature/
    ├── home/                    # Home screen
    ├── profile/                 # Profile creation and viewing
    ├── compatibility/           # Compatibility analysis
    ├── consent/                 # Data consent management
    └── settings/                # App settings
```

## Development Commands

### Build
```bash
./gradlew :app:assembleDebug     # Build debug APK
./gradlew :app:assembleRelease   # Build release APK
```

### Testing
```bash
# Critical tests (run before commits)
./gradlew :core:numerology:test :core:astro:test :core:ayurveda:test :core:humandesign:test

# Feature tests
./gradlew :feature:home:testDebugUnitTest :feature:compatibility:testDebugUnitTest

# All tests
./gradlew test
```

### Linting
```bash
./gradlew lint                   # Run Android lint
./gradlew ktlintCheck           # Check Kotlin style (if configured)
```

## Key Architectural Patterns

1. **Clean Architecture**: Strict separation between data, domain, and presentation layers
2. **MVVM**: ViewModels expose StateFlow to Compose UI
3. **Repository Pattern**: All data access through repository interfaces
4. **Hilt DI**: Constructor injection throughout

## Compose Best Practices

- Use `@Stable` and `@Immutable` for state classes to prevent unnecessary recompositions
- Prefer `remember` and `derivedStateOf` for computed values
- Use `LaunchedEffect` for side effects, `rememberCoroutineScope` for event handlers
- Never use `GlobalScope` in Composables

## Important Conventions

- **Imports**: Always use explicit imports for Compose functions
- **Coroutines**: Use `rememberCoroutineScope()` in Composables, not `GlobalScope`
- **Experimental APIs**: Add `@OptIn` annotations for experimental Material3 APIs
- **Type Conversions**: When drawing in Canvas, convert `IntSize` to `Size` explicitly

## Common Issues & Fixes

1. **Unresolved reference in Compose**
   - Check if import is missing (e.g., `import kotlin.random.Random`)
   - Check if experimental API needs `@OptIn` annotation

2. **Type mismatch IntSize vs Size**
   - Use `Size(size.width.toFloat(), size.height.toFloat())`

3. **Coroutine scope in Composable**
   - Replace `GlobalScope.launch` with `scope.launch` where `val scope = rememberCoroutineScope()`

4. **Missing module dependency**
   - Add to build.gradle.kts: `implementation(project(":core:modulename"))`

## Files to Avoid Modifying

- Documentation files in `docs/` (reference only)
- Test data fixtures in `*/src/test/resources/`

## Commit Guidelines

- Use conventional commits: `feat:`, `fix:`, `refactor:`, `test:`, `docs:`
- Run critical tests before committing
- Include module name in scope: `fix(core:ui): description`
