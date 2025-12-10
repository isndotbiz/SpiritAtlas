# SpiritAtlas: Comprehensive Multi-Agent Quality Assurance & Enhancement Audit

You are leading a team of specialized AI agents to conduct a thorough audit and enhancement of the SpiritAtlas Android app. This is a privacy-first spiritual insights application built with **Jetpack Compose**, following **Clean Architecture** with **multi-module design** and **Material Design 3** principles.

## Mission Objectives

1. **Ensure zero non-functional UI elements** - Every button, toggle, and interactive element must have working implementations
2. **Eliminate lazy/placeholder code** - Replace all TODOs, stub implementations, and incomplete features with production-ready code
3. **Implement high-quality visual design** - Add high-resolution cosmic/spiritual backgrounds, smooth animations, and professional polish following Material You guidelines
4. **Validate modern Android best practices** - Ensure code follows 2024 standards for Jetpack Compose, MVVM Clean Architecture, and performance optimization
5. **Ensure extensibility** - Architecture must easily support adding new data fields, AI providers, and spiritual analysis features
6. **Add Claude OAuth integration** - Implement authentication flow to use Claude API with user's subscription
7. **Validate secrets management** - Confirm OpenRouter, Gemini, and Groq API keys are properly configured and secure

---

## Agent Team Structure

### Agent 1: UI/UX Audit & Material Design Compliance
**Focus:** Material Design 3 compliance, dynamic theming, personalization, and modern UI components

**Tasks:**
- Audit every screen against Material Design 3 guidelines
- Verify dynamic color theming works with user wallpaper
- Check all interactive elements (buttons, cards, toggles, inputs) for:
  - Proper ripple effects and touch feedback
  - Correct elevation and shadows
  - Accessibility (minimum touch target 48dp, proper contrast ratios 3:1+)
  - State hoisting and proper event handling
- **Background Graphics:**
  - Ensure `CosmicBackgrounds.kt` components are used throughout
  - Verify starfield, sacred geometry, and cosmic connection backgrounds render at high quality
  - Check animation performance (60fps minimum)
  - Validate Canvas-based backgrounds (no raster images for performance)
- Avoid hardcoded colors, use theme attributes, implement dynamic colors properly
- Verify typography uses Material 3 styles (headlineSmall, bodyMedium, etc.)
- Check rounded corners and card designs for consistency

**Deliverables:** List of UI issues, missing animations, non-functional buttons, visual inconsistencies

---

### Agent 2: Architecture & Code Quality Validator
**Focus:** Clean Architecture, unidirectional data flow, state management

**Tasks:**
- Validate multi-module architecture:
  ```
  app → features → domain
       ↘ core ↗
   data → domain
  ```
- Ensure composables hold minimal information, promote decoupling and reuse
- Verify ViewModels use StateFlow/LiveData, proper state encapsulation, testability
- Check state hoisting - state moved up component tree for centralized management
- Audit dependency injection (Hilt):
  - All repositories, providers, and use cases properly injected
  - No circular dependencies
  - Singleton scopes correct
- Find and eliminate:
  - TODOs and placeholder comments
  - Empty function stubs
  - Unused imports and dead code
  - God classes/composables (keep functions under 50 lines)
- Verify `remember` and `rememberSaveable` usage for recomposition optimization
- Check navigation graph for proper backstack handling

**Deliverables:** Architecture violations, code smell locations, refactoring recommendations

---

### Agent 3: Functionality & Integration Tester
**Focus:** End-to-end feature validation, AI provider integration, data flow

**Tasks:**
- **Test every user flow:**
  - Onboarding → Profile creation → Insights generation → Compatibility
  - Settings changes → Theme switching → Provider selection
  - Consent management → Data encryption verification
- **AI Provider Integration:**
  - Verify all 7 providers work (AUTO, GEMINI, GROQ, OPENAI, CLAUDE, OPENROUTER, LOCAL/Ollama)
  - Test rate limiting and usage tracking
  - Validate error handling and fallback logic
  - Check consent gates prevent unauthorized API calls
- **Data Persistence:**
  - Verify `EncryptedSharedPreferences` (AES-256) for all user data
  - Test profile data survives app restart
  - Check Room database operations if applicable
- **Background Work:**
  - Validate `EnrichmentWorker` and `DataSyncWorker` with WorkManager
  - Test Hilt injection in workers
- **Secrets Management:**
  - Confirm API keys loaded from `local.properties` → `BuildConfig`
  - Verify keys not committed to VCS
  - Test graceful degradation when keys missing

**Deliverables:** Broken features list, integration issues, API failure scenarios

---

### Agent 4: Performance & Optimization Analyst
**Focus:** App speed, memory efficiency, battery usage, smooth animations

**Tasks:**
- **Recomposition Analysis:**
  - Profile unnecessary recompositions
  - Verify stable keys in LazyColumn/LazyRow
  - Check derivedStateOf usage for computed values
- **Memory Leaks:**
  - Audit coroutine cancellation (viewModelScope usage)
  - Check CompositionLocal providers don't leak
  - Verify image loading (if any) uses proper memory management
- **Network Efficiency:**
  - Validate OkHttp timeouts (connect: 30s, read: 60-120s)
  - Check retry logic and exponential backoff
  - Verify offline graceful degradation
- **Startup Time:**
  - Profile cold start time (target <2s)
  - Check for synchronous initialization on main thread
- **Animation Smoothness:**
  - Verify 60fps for all transitions
  - Check `LaunchedEffect`, `rememberInfiniteTransition` performance
  - Test on low-end devices (if possible)

**Deliverables:** Performance bottlenecks, memory issues, optimization opportunities

---

### Agent 5: Extensibility & Scalability Architect
**Focus:** Future-proofing for new features, data fields, AI capabilities

**Tasks:**
- **Module Boundaries:**
  - Verify domain layer has zero Android dependencies
  - Check data layer properly implements domain interfaces
  - Validate features only depend on domain and core
- **Data Field Extensibility:**
  - Audit `ProfileRepository`, `NumerologyCalculator`, `AstrologyCalculator`
  - Ensure new fields can be added without breaking existing code
  - Check JSON serialization handles unknown fields gracefully
- **AI Provider Pluggability:**
  - Verify `AiTextProvider` interface allows easy addition of new providers
  - Check `CombinedAiProvider` selection logic is maintainable
  - Validate usage tracking scales to new providers
- **Database Schema Evolution:**
  - If using Room, check migration strategy exists
  - Verify backward compatibility for data models
- **Testing Infrastructure:**
  - Ensure >80% coverage on core calculation modules
  - Check unit tests don't depend on Android framework
  - Validate repository tests use fake/mock data sources

**Deliverables:** Extensibility gaps, tight coupling issues, testing deficiencies

---

### Agent 6: OAuth & Advanced Features Implementer
**Focus:** Claude OAuth, API key management, advanced AI features

**Tasks:**
- **Claude OAuth Implementation:**
  - Design OAuth 2.0 flow for Claude API
  - Create `ClaudeAuthScreen` composable with WebView or Custom Tabs
  - Implement secure token storage (encrypted)
  - Add token refresh logic
  - Update `ClaudeProvider` to use OAuth tokens
  - Add user subscription tier detection
- **API Key Management UI:**
  - Create settings screen for user-provided API keys (OpenAI, Claude fallback)
  - Implement key validation before saving
  - Add key masking in UI (show only last 4 characters)
  - Verify keys stored in EncryptedSharedPreferences
- **Advanced AI Features:**
  - Design database schema for storing enrichment results
  - Implement caching to reduce API calls
  - Add comparison/analysis across multiple profiles
  - Create visualization for spiritual data trends
- **Research Database:**
  - Design extensible schema for spiritual datapoints
  - Implement export/backup functionality
  - Add analytics for pattern recognition across user base (anonymized)

**Deliverables:** OAuth implementation plan, API key management screen, research database schema

---

## Cross-Agent Coordination

After individual audits, agents should:
1. **Consolidate findings** - Merge reports to avoid duplicate issues
2. **Prioritize by impact** - Critical (app crashes) → High (broken features) → Medium (UX issues) → Low (polish)
3. **Create implementation plan** - Ordered task list with time estimates
4. **Identify dependencies** - Which fixes must happen before others
5. **Generate code patches** - Provide specific file changes with line numbers

---

## Final Deliverable: Comprehensive Report

### Executive Summary
- App health score (0-100)
- Critical issues count and severity
- Estimated effort to resolve (hours/days)

### Detailed Findings
- **By Category:** UI, Architecture, Functionality, Performance, Extensibility, Security
- **By Module:** app, features, data, domain, core
- **By Priority:** P0 (blocking), P1 (major), P2 (minor), P3 (enhancement)

### Implementation Roadmap
- Phase 1: Critical fixes (must-have for release)
- Phase 2: Feature completion (OAuth, advanced AI)
- Phase 3: Polish & optimization
- Phase 4: Research database & analytics

### Code Artifacts
- Specific file edits with diff format
- New files to create (e.g., `ClaudeAuthScreen.kt`)
- Gradle dependencies to add
- Local.properties template with all required keys

---

## Success Criteria

✅ Zero non-functional UI elements  
✅ Zero TODO/stub code remaining  
✅ All screens have high-quality backgrounds & animations  
✅ Material Design 3 with dynamic personalization fully implemented  
✅ Unidirectional data flow pattern throughout  
✅ Clean Architecture maintained across all modules  
✅ All 7 AI providers working with proper error handling  
✅ Claude OAuth integrated for development usage  
✅ Secrets properly managed and not in VCS  
✅ Extensible architecture supports unlimited new fields  
✅ App syncs perfectly with Android Studio (no Gradle errors)  
✅ Performance metrics: <2s cold start, 60fps animations, <200MB memory  

---

**Begin multi-agent audit now. Each agent should work in parallel, then reconvene to synthesize findings.**
