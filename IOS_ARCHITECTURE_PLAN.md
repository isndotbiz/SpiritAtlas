# iOS Architecture Plan for SpiritAtlas

**Document Version:** 1.0
**Created:** 2025-12-10
**Author:** Architecture Team
**Status:** Planning Phase

---

## Executive Summary

This document outlines the complete architecture plan for porting SpiritAtlas from Android (Kotlin + Jetpack Compose) to iOS (Swift + SwiftUI). The plan preserves the robust Clean Architecture foundation while leveraging iOS-native technologies and patterns.

**Key Metrics:**
- **Total Effort:** 16-20 weeks (4-5 months)
- **Team Size Recommended:** 3-4 iOS developers
- **Shared Code:** ~30% (calculation engines via Kotlin Multiplatform)
- **iOS-Specific Code:** ~70% (UI, platform features, integrations)
- **Risk Level:** Medium (well-defined Android architecture reduces ambiguity)

---

## Table of Contents

1. [Current Android Architecture Analysis](#1-current-android-architecture-analysis)
2. [iOS Technology Stack](#2-ios-technology-stack)
3. [Architecture Mapping: Android to iOS](#3-architecture-mapping-android-to-ios)
4. [Module-by-Module Migration Plan](#4-module-by-module-migration-plan)
5. [Shared Code Strategy (Kotlin Multiplatform)](#5-shared-code-strategy-kotlin-multiplatform)
6. [Timeline & Effort Breakdown](#6-timeline--effort-breakdown)
7. [Risk Assessment & Mitigation](#7-risk-assessment--mitigation)
8. [Testing Strategy](#8-testing-strategy)
9. [DevOps & CI/CD](#9-devops--cicd)
10. [Appendix: Code Examples](#10-appendix-code-examples)

---

## 1. Current Android Architecture Analysis

### 1.1 Android Project Structure

```
SpiritAtlas/
├── app/                          # Main app module (Navigation, Splash)
├── core/
│   ├── ui/                       # 69 Compose components, themes, animations
│   ├── common/                   # Shared utilities
│   ├── numerology/               # Chaldean & Pythagorean calculators (14 tests ✅)
│   ├── astro/                    # Astrology engine (83 tests ✅)
│   ├── ayurveda/                 # Dosha calculations (6 tests ✅)
│   └── humandesign/              # Human Design logic (10 tests ✅)
├── data/                         # Repositories, Room DB, network layer
│   ├── repository/               # UserRepository, CompatibilityRepository
│   ├── database/                 # Room entities, DAOs, type converters
│   └── ai/                       # AI provider integrations (Claude, Gemini, etc.)
├── domain/                       # Business logic, models, use cases
│   ├── model/                    # UserProfile (36 fields), compatibility models
│   └── service/                  # Spiritual calculation services
└── feature/
    ├── home/                     # Home screen with AI provider switcher
    ├── profile/                  # Profile CRUD (8 ViewModels)
    ├── compatibility/            # Compatibility analysis
    ├── consent/                  # Data consent management
    ├── onboarding/               # First-run experience
    └── settings/                 # App settings
```

### 1.2 Android Technology Stack

| Layer | Technology | iOS Equivalent |
|-------|-----------|----------------|
| **UI** | Jetpack Compose | **SwiftUI** |
| **Architecture** | MVVM + Clean | **MVVM + Clean** |
| **DI** | Hilt (Dagger) | **Resolver** or **Swinject** |
| **State Management** | StateFlow | **Combine** (@Published) |
| **Navigation** | Compose Navigation | **NavigationStack** (iOS 16+) |
| **Database** | Room (SQLite) | **CoreData** or **SwiftData** |
| **Network** | Retrofit + OkHttp | **URLSession** + Async/Await |
| **Reactive** | Coroutines + Flow | **Combine** or **AsyncSequence** |
| **Testing** | JUnit + Truth | **XCTest** + **Quick/Nimble** |
| **Build** | Gradle 8.13 | **Xcode 15+** (Swift Package Manager) |

### 1.3 Key Architectural Patterns

1. **Clean Architecture**: Strict separation of data, domain, and presentation layers
2. **MVVM**: ViewModels expose StateFlow/Combine publishers to UI
3. **Repository Pattern**: All data access abstracted through interfaces
4. **Single Responsibility**: Each module has a clear, focused purpose
5. **Dependency Injection**: Constructor injection throughout

### 1.4 Critical Statistics

- **Total Tests:** 113/113 passing ✅
- **Test Coverage:** 100% on core calculation modules
- **Build Time:** ~45 seconds (clean build on M1 Mac)
- **APK Size:** ~18 MB (release, minified)
- **Min Android SDK:** 26 (Android 8.0, ~95% device coverage)
- **Visual Assets:** 119 images × 5 densities = 595 files (44.3 MB WebP)

---

## 2. iOS Technology Stack

### 2.1 Recommended Stack

| Component | Technology | Version | Rationale |
|-----------|-----------|---------|-----------|
| **Language** | Swift | 5.9+ | Modern, safe, performant |
| **UI Framework** | SwiftUI | iOS 16+ | Declarative, matches Compose paradigm |
| **Architecture** | MVVM + Clean | - | Proven pattern, easy migration |
| **Dependency Injection** | Resolver | 1.5+ | Lightweight, Swift-friendly |
| **State Management** | Combine + @Published | - | Native, integrates with SwiftUI |
| **Navigation** | NavigationStack | iOS 16+ | Type-safe, modern API |
| **Database** | SwiftData | iOS 17+ | Modern Core Data replacement |
| **Networking** | URLSession + async/await | - | Native, no dependencies |
| **JSON Parsing** | Codable | - | Native, compile-time safety |
| **Testing** | XCTest + ViewInspector | - | Native + SwiftUI testing |
| **Build System** | Swift Package Manager | - | Native, no CocoaPods overhead |
| **Calculation Engines** | Kotlin Multiplatform | - | Share 30% of codebase |

### 2.2 Minimum iOS Version

**Target:** iOS 16.0+ (released September 2022)

**Reasoning:**
- **NavigationStack** (iOS 16+) provides type-safe navigation matching Compose
- **SwiftUI maturity** improved dramatically in iOS 16
- **Market coverage:** ~85% of active iOS devices as of January 2025
- Alternative: iOS 15+ (requires NavigationView, more complex code)

---

## 3. Architecture Mapping: Android to iOS

### 3.1 Layer-by-Layer Mapping

#### **Presentation Layer (UI)**

| Android (Compose) | iOS (SwiftUI) | Notes |
|------------------|---------------|-------|
| `@Composable` | `View` | Both declarative UI |
| `remember` | `@State` | Local state |
| `rememberCoroutineScope()` | `Task { }` | Async operations |
| `LaunchedEffect` | `.task()` / `.onAppear()` | Side effects |
| `collectAsState()` | `.onReceive()` or `@ObservedObject` | State observation |
| `ViewModel` | `ObservableObject` | State container |
| `StateFlow<T>` | `@Published var` | Reactive state |
| `MutableStateFlow` | `CurrentValueSubject` | Mutable state |
| `Modifier` | `ViewModifier` | Declarative styling |
| `MaterialTheme` | Custom theme with `@Environment` | Theming system |
| `CompositionLocal` | `@Environment` / `@EnvironmentObject` | Dependency injection |

#### **Domain Layer (Business Logic)**

| Android (Kotlin) | iOS (Swift) | Shared via KMP? |
|-----------------|-------------|-----------------|
| `UserProfile` model | `UserProfile` struct | ❌ (Swift-only) |
| Numerology calculators | Swift wrapper → KMP | ✅ **Shared** |
| Astrology engine | Swift wrapper → KMP | ✅ **Shared** |
| Ayurveda logic | Swift wrapper → KMP | ✅ **Shared** |
| Human Design | Swift wrapper → KMP | ✅ **Shared** |
| Use cases | Swift protocols | ❌ (iOS-specific) |

#### **Data Layer (Persistence & Network)**

| Android (Kotlin) | iOS (Swift) | Notes |
|-----------------|-------------|-------|
| `Room` database | `SwiftData` | Both ORM solutions |
| `UserProfileEntity` | `@Model class` | Database entities |
| `UserProfileDao` | SwiftData queries | Query interface |
| `TypeConverters` | `Codable` conformance | Type conversion |
| `Retrofit` + `OkHttp` | `URLSession` | HTTP client |
| `Moshi` | `Codable` | JSON parsing |
| `EncryptedSharedPreferences` | `Keychain` | Secure storage |
| `WorkManager` | `BackgroundTasks` framework | Background jobs |

### 3.2 Dependency Injection Mapping

#### Android (Hilt)
```kotlin
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao
) : UserRepository
```

#### iOS (Resolver)
```swift
extension Resolver: ResolverRegistering {
    public static func registerAllServices() {
        register { UserRepositoryImpl() }
            .implements(UserRepository.self)
            .scope(.application)
    }
}
```

### 3.3 Navigation Mapping

#### Android (Compose Navigation)
```kotlin
navController.navigate(Screen.Profile.createRoute(profileId))
```

#### iOS (NavigationStack)
```swift
navigationPath.append(Route.profile(id: profileId))
```

---

## 4. Module-by-Module Migration Plan

### 4.1 Core Modules (High Priority - Shared Logic)

#### **Module: core:numerology**
- **Android:** `ChaldeanCalculator.kt`, `PythagoreanCalculator.kt` (14 tests)
- **iOS Strategy:** Kotlin Multiplatform shared module
- **Effort:** 1 week (setup KMP + Swift bridge)
- **Dependencies:** None
- **Risk:** Low (pure calculation logic, no platform dependencies)

**Swift Bridge Example:**
```swift
import SpiritAtlasShared

class NumerologyService {
    func calculateChaldean(name: String) -> Int {
        return ChaldeanCalculator().calculateNameNumber(name: name)
    }
}
```

#### **Module: core:astro**
- **Android:** Astrology calculation engine (83 tests)
- **iOS Strategy:** Kotlin Multiplatform shared module
- **Effort:** 2 weeks (complex calculations, extensive tests)
- **Dependencies:** None
- **Risk:** Low (well-tested, deterministic)

#### **Module: core:ayurveda**
- **Android:** Dosha calculation logic (6 tests)
- **iOS Strategy:** Kotlin Multiplatform shared module
- **Effort:** 3 days
- **Dependencies:** None
- **Risk:** Low

#### **Module: core:humandesign**
- **Android:** Human Design calculations (10 tests)
- **iOS Strategy:** Kotlin Multiplatform shared module
- **Effort:** 1 week
- **Dependencies:** core:astro (shared)
- **Risk:** Low

#### **Module: core:ui → iOS: DesignSystem**
- **Android:** 69 Compose components, themes, animations
- **iOS Strategy:** Complete rewrite in SwiftUI
- **Effort:** 4 weeks (largest UI effort)
- **Components to Migrate:**
  - Theme system (colors, typography, shapes)
  - Reusable components (buttons, cards, inputs)
  - Animations (chakra, zodiac, sacred geometry)
  - Loading states, error handling
  - Accessibility utilities
- **Dependencies:** None
- **Risk:** Medium (animation complexity, platform differences)

**Priority Components (Week 1-2):**
1. Theme system (SpiritAtlasTheme.swift)
2. Typography & colors
3. Buttons, cards, text fields
4. Loading indicators

**Secondary Components (Week 3-4):**
5. Animations (SpiritualAnimations.swift)
6. Accessibility (VoiceOver, Dynamic Type)
7. Advanced components (bottom sheets, modals)
8. Visual polish (haptics, micro-interactions)

#### **Module: core:common → iOS: Utilities**
- **Android:** Shared utilities, extensions
- **iOS Strategy:** Swift utility module
- **Effort:** 3 days
- **Risk:** Low

### 4.2 Domain Layer

#### **Module: domain → iOS: Domain**
- **Android:** Business models, repository interfaces
- **iOS Strategy:** Swift protocols + structs
- **Effort:** 1.5 weeks
- **Key Models:**
  - `UserProfile` (36 fields)
  - `CompatibilityReport`
  - `EnrichmentResult`
  - All enums (Gender, BloodType, LoveLanguage, etc.)
- **Risk:** Low (straightforward translation)

**Model Example:**
```swift
struct UserProfile: Codable, Identifiable {
    let id: String
    let profileName: String
    let createdAt: Date
    let lastModified: Date

    // Core identity
    let name: String?
    let displayName: String?
    let birthDateTime: Date?
    let birthPlace: BirthPlace?

    // ... 32 more fields

    var profileCompletion: ProfileCompletion
}
```

### 4.3 Data Layer

#### **Module: data:database → iOS: Persistence**
- **Android:** Room database with 3 DAOs
- **iOS Strategy:** SwiftData (iOS 17+) or CoreData (iOS 16 fallback)
- **Effort:** 2 weeks
- **Entities:**
  - UserProfileEntity
  - CompatibilityReportEntity
  - AiResponseCacheEntity
- **Risk:** Medium (SwiftData is new, may need CoreData fallback)

**SwiftData Example:**
```swift
@Model
class UserProfileEntity {
    @Attribute(.unique) var id: String
    var profileName: String
    var createdAt: Date
    var lastModified: Date

    // 36 fields matching Android entity
    var name: String?
    var displayName: String?
    var birthDateTime: Date?
    // ... etc
}
```

#### **Module: data:repository → iOS: Repositories**
- **Android:** Repository implementations (UserRepositoryImpl, etc.)
- **iOS Strategy:** Swift classes conforming to protocols
- **Effort:** 2 weeks
- **Repositories:**
  - `UserRepository` (profile CRUD, search, export/import)
  - `CompatibilityRepository` (compatibility analysis, caching)
  - `AiSettingsRepository` (AI provider configuration)
  - `ConsentRepository` (privacy consent management)
- **Risk:** Low (well-defined interfaces)

#### **Module: data:ai → iOS: AIProviders**
- **Android:** 7 AI provider implementations (Claude, Gemini, OpenRouter, etc.)
- **iOS Strategy:** Swift protocol + provider implementations
- **Effort:** 2 weeks
- **Providers:**
  - ClaudeProvider (OAuth + API key support)
  - GeminiProvider
  - OpenRouterProvider
  - OllamaProvider (local AI)
- **Risk:** Medium (OAuth flows, SSL pinning, error handling)

**Network Security:**
```swift
// SSL Pinning (equivalent to Android's network_security_config.xml)
class PinnedURLSessionDelegate: NSObject, URLSessionDelegate {
    func urlSession(_ session: URLSession,
                   didReceive challenge: URLAuthenticationChallenge,
                   completionHandler: @escaping (URLSession.AuthChallengeDisposition, URLCredential?) -> Void) {
        // Implement certificate pinning for openrouter.ai
    }
}
```

### 4.4 Feature Modules

#### **feature:home → iOS: HomeView**
- **Android:** HomeScreen.kt, HomeViewModel.kt
- **iOS Strategy:** HomeView.swift + HomeViewModel.swift
- **Effort:** 3 days
- **UI Components:**
  - Dashboard with quick actions
  - AI provider status indicator
  - Navigation to profile/compatibility
- **Risk:** Low

#### **feature:profile → iOS: ProfileModule**
- **Android:** 8 screens, 8 ViewModels
- **iOS Strategy:** SwiftUI views + ObservableObjects
- **Effort:** 3 weeks (largest feature module)
- **Screens:**
  1. ProfileListView (grid/list of profiles)
  2. ProfileDetailView (single profile display)
  3. ProfileEditView (create/edit profile)
  4. ProfileComparisonView (side-by-side comparison)
  5. EnrichmentResultView (AI-generated insights)
  6. ProfileLibraryView (browse/search profiles)
  7. ProfileShareView (export/import profiles)
  8. ProfileSelectionView (choose profiles for compatibility)
- **Risk:** Medium (complex state management, forms)

#### **feature:compatibility → iOS: CompatibilityModule**
- **Android:** CompatibilityScreen.kt + supporting views
- **iOS Strategy:** SwiftUI views with animations
- **Effort:** 2 weeks
- **Features:**
  - Profile selection flow
  - Compatibility calculation (uses shared KMP logic)
  - Results visualization (charts, insights)
  - History management
- **Risk:** Medium (chart animations, complex layout)

#### **feature:onboarding → iOS: OnboardingModule**
- **Android:** OnboardingScreen.kt with HorizontalPager
- **iOS Strategy:** TabView or custom pager
- **Effort:** 1 week
- **Screens:**
  - Welcome
  - Feature highlights (3-4 pages)
  - Permissions request (notifications, AI enrichment)
- **Risk:** Low

#### **feature:consent → iOS: ConsentModule**
- **Android:** ConsentScreen.kt (data consent management)
- **iOS Strategy:** ConsentView.swift
- **Effort:** 1 week
- **Features:**
  - AI enrichment consent
  - Cloud sync consent
  - Analytics consent
  - AI provider selection
- **Risk:** Low

#### **feature:settings → iOS: SettingsModule**
- **Android:** SettingsScreen.kt
- **iOS Strategy:** SettingsView.swift (native iOS Settings style)
- **Effort:** 1 week
- **Settings:**
  - AI provider configuration
  - Calculation preferences (zodiac system, house system)
  - Theme selection
  - Data export/import
- **Risk:** Low

### 4.5 App Module

#### **Module: app → iOS: SpiritAtlasApp**
- **Android:** MainActivity, SplashScreen, Navigation
- **iOS Strategy:** @main App struct + NavigationStack
- **Effort:** 1 week
- **Components:**
  - App entry point
  - Splash screen with animation
  - Root navigation coordinator
  - Deep link handling
  - Dependency injection setup
- **Risk:** Low

---

## 5. Shared Code Strategy (Kotlin Multiplatform)

### 5.1 What to Share (30% of codebase)

**High-Value Targets:**
1. **core:numerology** (Chaldean + Pythagorean calculators) - Pure logic, 14 tests
2. **core:astro** (Astrology engine) - Complex calculations, 83 tests
3. **core:ayurveda** (Dosha calculations) - Pure logic, 6 tests
4. **core:humandesign** (Human Design logic) - Pure logic, 10 tests
5. **domain:models** (partial) - Enums, calculation results (not UI models)

**Total Shared:** ~8,000 LOC (lines of code)

### 5.2 What NOT to Share (70% platform-specific)

1. **UI Layer** (core:ui → iOS DesignSystem) - SwiftUI vs Compose too different
2. **Data Layer** (Room vs SwiftData) - Platform-specific persistence
3. **ViewModels** (StateFlow vs @Published) - Different reactive patterns
4. **Navigation** (Compose Navigation vs NavigationStack) - Platform APIs
5. **AI Providers** (partially shared logic possible, but HTTP clients differ)

### 5.3 Kotlin Multiplatform Setup

**Project Structure:**
```
shared/
├── commonMain/
│   ├── kotlin/
│   │   ├── numerology/
│   │   │   ├── ChaldeanCalculator.kt
│   │   │   └── PythagoreanCalculator.kt
│   │   ├── astro/
│   │   │   └── AstrologyEngine.kt
│   │   ├── ayurveda/
│   │   │   └── DoshaCalculator.kt
│   │   └── humandesign/
│   │       └── HumanDesignEngine.kt
├── androidMain/
│   └── kotlin/ (Android-specific code if needed)
├── iosMain/
│   └── kotlin/ (iOS-specific code if needed)
└── commonTest/
    └── kotlin/ (113 shared tests)
```

**Swift Integration:**
```swift
import SpiritAtlasShared

class SpiritualCalculationService {
    private let chaldean = ChaldeanCalculator()
    private let astro = AstrologyEngine()

    func calculateLifePath(name: String, birthDate: Date) -> Int {
        return chaldean.calculateNameNumber(name: name)
    }

    func generateBirthChart(date: Date, location: Location) -> BirthChart {
        return astro.generateChart(
            dateTime: date.toKotlinInstant(),
            latitude: location.latitude,
            longitude: location.longitude
        )
    }
}
```

### 5.4 KMP Migration Timeline

**Phase 1: Setup (Week 1)**
- Create `shared` module in Android project
- Configure Gradle for Kotlin Multiplatform
- Setup Xcode integration (CocoaPods or SPM)
- Verify "Hello World" builds on both platforms

**Phase 2: Migrate Calculators (Weeks 2-4)**
- Move core:numerology → shared/numerology
- Move core:astro → shared/astro
- Move core:ayurveda → shared/ayurveda
- Move core:humandesign → shared/humandesign
- Run all 113 tests on both platforms

**Phase 3: Swift Bridges (Week 5)**
- Create Swift-friendly wrappers
- Document API usage for iOS team
- Performance benchmarking

---

## 6. Timeline & Effort Breakdown

### 6.1 Overall Timeline: 16-20 Weeks (4-5 Months)

**Team Composition:**
- **1 KMP Specialist** (Kotlin Multiplatform setup, shared logic)
- **2-3 iOS Developers** (SwiftUI, iOS-specific features)
- **1 QA Engineer** (testing, automation)

### 6.2 Phase Breakdown

#### **Phase 1: Foundation (Weeks 1-4) - 25% Complete**

**Deliverables:**
- ✅ Kotlin Multiplatform shared module setup
- ✅ Core calculation engines (numerology, astro, ayurveda, humandesign) in KMP
- ✅ iOS design system (theme, colors, typography, basic components)
- ✅ SwiftData persistence layer (entities, basic CRUD)
- ✅ Domain models in Swift (UserProfile, etc.)

**Effort Distribution:**
- KMP Setup: 1 week
- Core Calculators (KMP): 3 weeks
- iOS Design System (SwiftUI): 4 weeks (parallel)
- Domain Models: 1 week (parallel)

**Milestone:** Can calculate spiritual profiles, theme system working

#### **Phase 2: Features - Part 1 (Weeks 5-9) - 60% Complete**

**Deliverables:**
- ✅ Onboarding flow
- ✅ Home screen
- ✅ Profile creation & editing
- ✅ Profile list & detail views
- ✅ Basic navigation working
- ✅ UserRepository implementation
- ✅ AI provider integrations (at least Claude + Gemini)

**Effort Distribution:**
- Onboarding: 1 week
- Home: 3 days
- Profile CRUD: 3 weeks
- Repositories: 2 weeks (parallel)
- AI Providers: 2 weeks (parallel)

**Milestone:** Can create, edit, and view profiles; AI enrichment working

#### **Phase 3: Features - Part 2 (Weeks 10-14) - 85% Complete**

**Deliverables:**
- ✅ Compatibility analysis (using KMP calculations)
- ✅ Compatibility history & search
- ✅ Profile comparison view
- ✅ Consent management
- ✅ Settings screen
- ✅ Profile export/import (JSON)
- ✅ Advanced animations (chakra, zodiac, sacred geometry)

**Effort Distribution:**
- Compatibility Module: 2 weeks
- Profile Comparison: 1 week
- Consent + Settings: 2 weeks (parallel)
- Advanced Animations: 2 weeks (parallel)

**Milestone:** Feature parity with Android app

#### **Phase 4: Testing & Polish (Weeks 15-16) - 100% Complete**

**Deliverables:**
- ✅ Unit tests for all ViewModels
- ✅ UI tests for critical flows
- ✅ Performance optimization (launch time, memory, battery)
- ✅ Accessibility audit (VoiceOver, Dynamic Type)
- ✅ Visual polish (micro-interactions, haptics)
- ✅ App Store assets (screenshots, preview video)

**Effort Distribution:**
- Testing: 1 week
- Performance: 3 days
- Accessibility: 2 days
- Polish: 2 days

**Milestone:** Production-ready iOS app

#### **Phase 5: Beta & Launch (Weeks 17-20) - Post-Development**

**Deliverables:**
- ✅ TestFlight beta (internal team)
- ✅ TestFlight beta (external users, 50-100 testers)
- ✅ Bug fixes from beta feedback
- ✅ App Store submission
- ✅ Marketing materials
- ✅ Launch!

**Effort Distribution:**
- Internal beta: 1 week
- External beta: 2 weeks
- Bug fixes: 1 week
- Submission & launch: 1 week

### 6.3 Effort by Category

| Category | Weeks | Percentage | Notes |
|----------|-------|------------|-------|
| **UI Development** | 6.5 | 40% | SwiftUI views, design system, animations |
| **Features** | 5.5 | 35% | Profile, compatibility, onboarding, settings |
| **Testing** | 3 | 20% | Unit tests, UI tests, manual QA |
| **DevOps** | 1 | 5% | CI/CD, TestFlight, App Store setup |
| **Total** | **16** | **100%** | Assumes 3-4 developers |

**Buffer Time:** +4 weeks for unknowns (total 20 weeks with buffer)

---

## 7. Risk Assessment & Mitigation

### 7.1 High-Risk Areas

#### **Risk 1: SwiftData Stability (iOS 17 Requirement)**
- **Impact:** High (core persistence layer)
- **Probability:** Medium
- **Mitigation:**
  1. Build CoreData fallback for iOS 16 support
  2. Extensive testing on iOS 17.0+ betas
  3. Monitor SwiftData release notes for bugs
  4. Fallback: Use CoreData for iOS 17 launch, migrate to SwiftData in v1.1

#### **Risk 2: Animation Complexity**
- **Impact:** Medium (UX quality)
- **Probability:** Medium
- **Android:** 69 Compose components with complex animations (chakra, zodiac, sacred geometry)
- **Mitigation:**
  1. Prioritize core animations (Week 1-2)
  2. Simplify complex Canvas-based animations
  3. Use Lottie for intricate animations
  4. Performance profiling on older devices (iPhone 11, 12)

#### **Risk 3: OAuth Implementation (Claude, OpenAI)**
- **Impact:** High (AI enrichment feature)
- **Probability:** Low
- **Android:** OAuth flow implemented but Anthropic doesn't offer it yet
- **Mitigation:**
  1. Implement API key flow first (working)
  2. OAuth infrastructure ready for when available
  3. Use OpenRouter as primary provider (has OAuth)

#### **Risk 4: Kotlin Multiplatform Learning Curve**
- **Impact:** Medium (shared code efficiency)
- **Probability:** Medium
- **Mitigation:**
  1. Allocate Week 1 for KMP setup and training
  2. Hire consultant for initial setup if needed
  3. Extensive documentation and code examples
  4. Fallback: Pure Swift implementation (lose shared code benefit)

### 7.2 Medium-Risk Areas

#### **Risk 5: Accessibility Compliance**
- **Impact:** Medium (App Store approval, user reach)
- **Probability:** Low (if planned early)
- **Mitigation:**
  1. VoiceOver testing from Week 1
  2. Dynamic Type support in design system
  3. Color contrast testing (WCAG AAA)
  4. Dedicated accessibility QA pass in Week 15

#### **Risk 6: Image Asset Size**
- **Impact:** Low (app size)
- **Probability:** Low
- **Android:** 119 images × 5 densities = 44.3 MB
- **iOS:** May need 3 asset scales (@1x, @2x, @3x)
- **Mitigation:**
  1. Asset catalog with compression
  2. On-demand resources for non-critical images
  3. WebP support (iOS 14+)
  4. Target: <50 MB app size

### 7.3 Risk Matrix

| Risk | Impact | Probability | Severity | Mitigation Status |
|------|--------|-------------|----------|-------------------|
| SwiftData stability | High | Medium | **Critical** | ✅ CoreData fallback |
| Animation complexity | Medium | Medium | **Moderate** | ✅ Prioritization plan |
| OAuth implementation | High | Low | **Moderate** | ✅ API key fallback |
| KMP learning curve | Medium | Medium | **Moderate** | ✅ Training + docs |
| Accessibility | Medium | Low | **Low** | ✅ Early testing |
| Image asset size | Low | Low | **Low** | ✅ Compression strategy |

---

## 8. Testing Strategy

### 8.1 Unit Testing

**Framework:** XCTest (native)

**Coverage Target:** 80%+ (matching Android)

**Shared Tests:** 113 tests in Kotlin Multiplatform (run on both platforms)

**iOS-Specific Tests:**
- ViewModels (state management, business logic)
- Repositories (SwiftData queries, Codable)
- Utilities (date formatting, validation)
- AI providers (network layer, OAuth)

**Example:**
```swift
import XCTest
@testable import SpiritAtlas

class UserRepositoryTests: XCTestCase {
    var repository: UserRepository!
    var mockDatabase: MockSwiftDataContext!

    override func setUp() {
        super.setUp()
        mockDatabase = MockSwiftDataContext()
        repository = UserRepositoryImpl(context: mockDatabase)
    }

    func testSaveProfile() async throws {
        let profile = UserProfile.sample
        try await repository.saveProfile(profile)
        let saved = try await repository.getProfile(id: profile.id)
        XCTAssertEqual(saved?.id, profile.id)
    }
}
```

### 8.2 UI Testing

**Framework:** XCTest UI + ViewInspector

**Critical Flows:**
1. Onboarding → Profile creation → AI enrichment
2. Profile list → Profile detail → Edit
3. Profile selection → Compatibility analysis
4. Settings → AI provider change

**Example:**
```swift
import XCTest

class OnboardingUITests: XCTestCase {
    let app = XCUIApplication()

    func testOnboardingFlow() {
        app.launch()

        // Welcome screen
        XCTAssertTrue(app.staticTexts["Welcome to SpiritAtlas"].exists)
        app.buttons["Next"].tap()

        // Feature 1
        XCTAssertTrue(app.staticTexts["Spiritual Profile"].exists)
        app.buttons["Next"].tap()

        // Complete onboarding
        app.buttons["Get Started"].tap()
        XCTAssertTrue(app.navigationBars["Home"].exists)
    }
}
```

### 8.3 Integration Testing

**Shared Logic Tests:** Run KMP tests on iOS via XCTest bridge

**Example:**
```swift
import XCTest
import SpiritAtlasShared

class ChaldeanCalculatorTests: XCTestCase {
    func testCalculateNameNumber() {
        let calculator = ChaldeanCalculator()
        let result = calculator.calculateNameNumber(name: "John")
        XCTAssertEqual(result, 2) // Expected Chaldean value
    }
}
```

### 8.4 Performance Testing

**Instruments Profiling:**
- Launch time: <2 seconds (cold start)
- Memory usage: <100 MB baseline
- Battery impact: <5% per hour (background)
- Frame rate: 60 FPS (animations)

**Critical Paths:**
- Profile list scrolling (1000+ profiles)
- Compatibility calculation (should use KMP, fast)
- AI enrichment (network-bound, not app perf)

### 8.5 Accessibility Testing

**VoiceOver:**
- All buttons labeled
- Images have alt text
- Semantic grouping (cards, forms)

**Dynamic Type:**
- All text scales with system font size
- No hard-coded font sizes
- Layout adapts to larger text

**Color:**
- WCAG AAA contrast (7:1 for text)
- Color is not the only indicator (icons + text)

---

## 9. DevOps & CI/CD

### 9.1 Build System

**Xcode:** 15.0+ (Swift 5.9+)

**Swift Package Manager:**
- Resolver (DI)
- ViewInspector (UI testing)
- KMP shared framework (via CocoaPods or local SPM)

**Build Targets:**
- SpiritAtlas (main app)
- SpiritAtlasTests (unit tests)
- SpiritAtlasUITests (UI tests)

### 9.2 CI/CD Pipeline (GitHub Actions or Bitrise)

**Trigger:** Push to `main`, Pull Requests

**Pipeline Steps:**

1. **Build**
   ```yaml
   - name: Build iOS App
     run: xcodebuild -scheme SpiritAtlas -destination 'platform=iOS Simulator,name=iPhone 15 Pro'
   ```

2. **Unit Tests**
   ```yaml
   - name: Run Unit Tests
     run: xcodebuild test -scheme SpiritAtlasTests -destination 'platform=iOS Simulator,name=iPhone 15 Pro'
   ```

3. **UI Tests**
   ```yaml
   - name: Run UI Tests
     run: xcodebuild test -scheme SpiritAtlasUITests -destination 'platform=iOS Simulator,name=iPhone 15 Pro'
   ```

4. **SwiftLint** (code quality)
   ```yaml
   - name: Lint
     run: swiftlint lint --strict
   ```

5. **Code Coverage**
   ```yaml
   - name: Generate Coverage Report
     run: xcrun xccov view --report --json derivedData/Logs/Test/*.xcresult > coverage.json
   ```

6. **TestFlight Upload** (release branches only)
   ```yaml
   - name: Upload to TestFlight
     run: fastlane beta
   ```

### 9.3 Versioning Strategy

**Semantic Versioning:** `MAJOR.MINOR.PATCH` (e.g., 1.0.0)

**Build Number:** Auto-increment via CI (e.g., 1, 2, 3...)

**Example:**
- iOS v1.0.0 (build 1) → Initial release
- iOS v1.0.1 (build 5) → Bug fix
- iOS v1.1.0 (build 10) → New feature (SwiftData migration)
- iOS v2.0.0 (build 50) → Major redesign

### 9.4 App Store Configuration

**App ID:** `com.spiritatlas.app` (match Android package)

**Capabilities:**
- Background processing (AI enrichment)
- Keychain sharing (secure storage)
- Push notifications (future: daily insights)
- HealthKit (future: wellness integration)

**App Store Listing:**
- **Category:** Lifestyle > Spirituality
- **Age Rating:** 4+ (no objectionable content)
- **Privacy:** Collect birth data (required for functionality), no tracking
- **Keywords:** spirituality, astrology, numerology, compatibility, human design

---

## 10. Appendix: Code Examples

### 10.1 Example: UserProfile View (SwiftUI)

```swift
import SwiftUI

struct ProfileDetailView: View {
    @StateObject private var viewModel: ProfileViewModel
    let profileId: String

    init(profileId: String) {
        self.profileId = profileId
        _viewModel = StateObject(wrappedValue: ProfileViewModel(profileId: profileId))
    }

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 20) {
                // Header
                ProfileHeaderView(profile: viewModel.profile)

                // Core Identity
                Section("Core Identity") {
                    ProfileFieldRow(label: "Name", value: viewModel.profile.name)
                    ProfileFieldRow(label: "Birth Date", value: viewModel.formattedBirthDate)
                    ProfileFieldRow(label: "Birth Place", value: viewModel.profile.birthPlace?.city)
                }

                // Spiritual Calculations
                Section("Spiritual Profile") {
                    ChakraVisualization(profile: viewModel.profile)
                    ZodiacChart(profile: viewModel.profile)
                }

                // AI Enrichment
                if let enrichment = viewModel.profile.enrichmentResult {
                    Section("AI Insights") {
                        EnrichmentCard(text: enrichment)
                    }
                }

                // Actions
                HStack {
                    Button("Edit Profile") {
                        viewModel.navigateToEdit()
                    }
                    .buttonStyle(.borderedProminent)

                    Button("Compare") {
                        viewModel.navigateToComparison()
                    }
                    .buttonStyle(.bordered)
                }
            }
            .padding()
        }
        .navigationTitle(viewModel.profile.profileName)
        .task {
            await viewModel.loadProfile()
        }
    }
}
```

### 10.2 Example: ProfileViewModel (MVVM)

```swift
import Foundation
import Combine

@MainActor
class ProfileViewModel: ObservableObject {
    @Published var profile: UserProfile?
    @Published var isLoading = false
    @Published var error: Error?

    private let profileId: String
    private let repository: UserRepository
    private var cancellables = Set<AnyCancellable>()

    init(profileId: String, repository: UserRepository = Resolver.resolve()) {
        self.profileId = profileId
        self.repository = repository
    }

    func loadProfile() async {
        isLoading = true
        defer { isLoading = false }

        do {
            profile = try await repository.getProfile(id: profileId)
        } catch {
            self.error = error
        }
    }

    var formattedBirthDate: String? {
        guard let date = profile?.birthDateTime else { return nil }
        return date.formatted(date: .long, time: .omitted)
    }

    func navigateToEdit() {
        // Navigation handled by coordinator
    }

    func navigateToComparison() {
        // Navigation handled by coordinator
    }
}
```

### 10.3 Example: UserRepository (Data Layer)

```swift
import Foundation
import SwiftData

protocol UserRepository {
    func saveProfile(_ profile: UserProfile) async throws
    func getProfile(id: String) async throws -> UserProfile?
    func getAllProfiles() async throws -> [UserProfile]
    func deleteProfile(id: String) async throws
    func searchProfiles(query: String) async throws -> [UserProfile]
}

class UserRepositoryImpl: UserRepository {
    private let modelContext: ModelContext

    init(modelContext: ModelContext) {
        self.modelContext = modelContext
    }

    func saveProfile(_ profile: UserProfile) async throws {
        let entity = UserProfileEntity(from: profile)
        modelContext.insert(entity)
        try modelContext.save()
    }

    func getProfile(id: String) async throws -> UserProfile? {
        let descriptor = FetchDescriptor<UserProfileEntity>(
            predicate: #Predicate { $0.id == id }
        )
        let entities = try modelContext.fetch(descriptor)
        return entities.first?.toDomain()
    }

    func getAllProfiles() async throws -> [UserProfile] {
        let descriptor = FetchDescriptor<UserProfileEntity>(
            sortBy: [SortDescriptor(\.lastModified, order: .reverse)]
        )
        let entities = try modelContext.fetch(descriptor)
        return entities.map { $0.toDomain() }
    }

    func deleteProfile(id: String) async throws {
        let descriptor = FetchDescriptor<UserProfileEntity>(
            predicate: #Predicate { $0.id == id }
        )
        let entities = try modelContext.fetch(descriptor)
        entities.forEach { modelContext.delete($0) }
        try modelContext.save()
    }

    func searchProfiles(query: String) async throws -> [UserProfile] {
        let descriptor = FetchDescriptor<UserProfileEntity>(
            predicate: #Predicate { profile in
                profile.profileName.localizedStandardContains(query) ||
                profile.name?.localizedStandardContains(query) == true
            }
        )
        let entities = try modelContext.fetch(descriptor)
        return entities.map { $0.toDomain() }
    }
}
```

### 10.4 Example: SwiftData Entity

```swift
import Foundation
import SwiftData

@Model
class UserProfileEntity {
    @Attribute(.unique) var id: String
    var profileName: String
    var createdAt: Date
    var lastModified: Date

    // Core identity
    var name: String?
    var displayName: String?
    var birthDateTime: Date?
    var birthPlaceCity: String?
    var birthPlaceLatitude: Double?
    var birthPlaceLongitude: Double?

    // Additional names
    var middleName: String?
    var nickname: String?
    var spiritualName: String?

    // ... 30+ more fields

    // Relationships
    @Relationship(deleteRule: .cascade)
    var compatibilityReports: [CompatibilityReportEntity]?

    init(from profile: UserProfile) {
        self.id = profile.id
        self.profileName = profile.profileName
        self.createdAt = profile.createdAt
        self.lastModified = profile.lastModified
        self.name = profile.name
        self.displayName = profile.displayName
        self.birthDateTime = profile.birthDateTime
        // ... map all fields
    }

    func toDomain() -> UserProfile {
        UserProfile(
            id: id,
            profileName: profileName,
            createdAt: createdAt,
            lastModified: lastModified,
            name: name,
            displayName: displayName,
            birthDateTime: birthDateTime,
            birthPlace: birthPlaceCity.map { city in
                BirthPlace(
                    city: city,
                    latitude: birthPlaceLatitude ?? 0,
                    longitude: birthPlaceLongitude ?? 0
                )
            }
            // ... map all fields
        )
    }
}
```

### 10.5 Example: Theme System (SwiftUI)

```swift
import SwiftUI

struct SpiritAtlasTheme {
    // Colors (matching Android SpiritualDarkColorScheme)
    struct Colors {
        static let primary = Color(hex: 0x9D7FF5) // Mystic purple
        static let secondary = Color(hex: 0xFF6BB5) // Aurora pink
        static let tertiary = Color(hex: 0xFFC857) // Sacred gold
        static let background = Color(hex: 0x1A1625) // Warm dark purple-black
        static let surface = Color(hex: 0x211D2A) // Warm dark surface
        static let onBackground = Color(hex: 0xEDE9F0) // Soft white

        // Chakra colors
        static let chakraColors: [Color] = [
            Color(hex: 0xFF0000), // Root (red)
            Color(hex: 0xFF6B00), // Sacral (orange)
            Color(hex: 0xFFD700), // Solar (yellow)
            Color(hex: 0x00FF00), // Heart (green)
            Color(hex: 0x00BFFF), // Throat (blue)
            Color(hex: 0x4B0082), // Third Eye (indigo)
            Color(hex: 0x9370DB)  // Crown (violet)
        ]
    }

    // Typography (matching Android Typography)
    struct Typography {
        static let largeTitle = Font.system(size: 34, weight: .bold)
        static let title = Font.system(size: 28, weight: .semibold)
        static let headline = Font.system(size: 17, weight: .semibold)
        static let body = Font.system(size: 17, weight: .regular)
        static let callout = Font.system(size: 16, weight: .regular)
        static let caption = Font.system(size: 12, weight: .regular)
    }

    // Spacing (8-point grid)
    struct Spacing {
        static let xs: CGFloat = 4
        static let sm: CGFloat = 8
        static let md: CGFloat = 16
        static let lg: CGFloat = 24
        static let xl: CGFloat = 32
        static let xxl: CGFloat = 48
    }

    // Corner radius (matching SpiritualShapes)
    struct CornerRadius {
        static let small: CGFloat = 8
        static let medium: CGFloat = 12
        static let large: CGFloat = 16
        static let extraLarge: CGFloat = 28
    }
}

// Extension for hex colors
extension Color {
    init(hex: UInt, alpha: Double = 1) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xff) / 255,
            green: Double((hex >> 8) & 0xff) / 255,
            blue: Double(hex & 0xff) / 255,
            opacity: alpha
        )
    }
}
```

---

## Summary

This iOS architecture plan provides a comprehensive roadmap for porting SpiritAtlas from Android to iOS. Key highlights:

1. **Technology Stack:** SwiftUI (iOS 16+) + Combine + SwiftData + Kotlin Multiplatform
2. **Architecture:** Clean Architecture + MVVM (matches Android)
3. **Shared Code:** 30% via Kotlin Multiplatform (calculation engines)
4. **Timeline:** 16-20 weeks (4-5 months) with 3-4 developers
5. **Effort:** 40% UI, 35% features, 20% testing, 5% DevOps
6. **Risk Level:** Medium (well-defined Android architecture reduces ambiguity)
7. **Testing:** 80%+ coverage, 113 shared tests from KMP

**Next Steps:**
1. Approve architecture plan
2. Assemble iOS development team
3. Begin Phase 1 (Kotlin Multiplatform setup + iOS design system)
4. Weekly progress reviews
5. TestFlight beta in Week 17

---

**Document Status:** ✅ Ready for Review
**Last Updated:** 2025-12-10
**Author:** Architecture Team
