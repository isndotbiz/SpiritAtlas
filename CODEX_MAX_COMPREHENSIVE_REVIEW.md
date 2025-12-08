# Codex Max: Comprehensive App Review & Optimization

## Coordination Status

**CLAUDE HAS COMPLETED THEIR WORK AND IS NO LONGER ACTIVE ON THIS CODEBASE.**

You now have FULL ACCESS to ALL files. The previous restrictions in `CODEX_MAX_PROMPT.md` no longer apply.

### What Claude Completed (for your awareness):
- All UI beautification (28 components)
- Visualizations: Zodiac wheel, Chakra, Bodygraph, Dosha, Numerology, Tantric, Moon phases, Transits, Sacred geometry, Particle effects
- Screens: Splash, Onboarding, Home, Settings, Profile detail, Compatibility detail
- Components: Animations, Shimmer, Gradients, Bottom sheets, Tooltips, Pull-to-refresh, Haptics
- Theme enhancements and navigation transitions
- ~25,000 lines of new UI code

### What You (Codex Max) Have Completed (Phase 1):
- ✅ Unit tests added for numerology, astrology, ayurveda, human design, ViewModels, repositories, AI providers
- ✅ Test dependencies wired in (data, home, compatibility modules)
- ✅ Network security config added with certificate pinning structure
- ✅ Branch `codex-max/testing-coverage` created

### KNOWN ISSUES TO FIX IN PHASE 2:
- ⚠️ **Chaldean calculator** - Empty string causes recursion crash (fix in NumerologyVisualization/Calculator)
- ⚠️ **Master number 33** - Missing support (only 11, 22 handled)
- ⚠️ **Certificate pins** - Placeholder SHA-256 values in network_security_config.xml need real pins for openrouter.ai
- ⚠️ **Tests not executed** - Expect failures when first run

### Keeping Both AIs Coordinated:
When you complete work, update `CODEX_MAX_COMPLETED.md` with:
- Files you modified/created
- Changes made
- Issues found and fixed
- Test coverage achieved

This file will be read by Claude if they return to the project, ensuring continuity.

---

## Mission
You are tasked with comprehensively reviewing, fixing, and optimizing the SpiritAtlas Android app. Launch multiple agents in parallel to maximize speed. Always search the web for current best practices, latest library versions, and industry standards.

---

## CRITICAL: Multi-Agent Approach

**ALWAYS launch 5-10 agents in parallel for maximum efficiency.**

For each area below, create a dedicated agent that:
1. Searches the web for 2024/2025 best practices
2. Reviews the existing code
3. Identifies issues and improvements
4. Implements fixes
5. Documents changes

---

## Agent Tasks to Launch

### Agent 0: CRITICAL - Run Critical Tests & Fix Failures
```
PHASE 2 PRIORITY 1: Run ONLY critical/high-priority tests in this Codex session.
Other tests run separately on user's local machine.

STEP 1: Run CRITICAL TESTS ONLY (run in Codex)
./gradlew :core:numerology:test :core:astro:test :core:ayurveda:test :core:humandesign:test

STEP 2: Fix known failures
- **Chaldean Calculator empty string recursion** (core/numerology)
  Problem: calculateNameNumber("") causes infinite recursion
  Fix: Add guard clause at start: if (name.isBlank()) return 0

- **Master number 33 support** (core/numerology)
  Problem: Only 11 and 22 are treated as master numbers
  Fix: Add support for 33 (add to master numbers list)

STEP 3: Update certificate pins (app/src/main/res/xml/network_security_config.xml)
- Get real SHA-256 pins for openrouter.ai
- Replace AAAA...= and BBBB...= placeholder values
- Verify pins don't expire (set expiration date)

STEP 4: Run critical tests again until all pass

---

HIGH-PRIORITY TESTS (user runs locally after this session):
./gradlew :feature:home:testDebugUnitTest :feature:compatibility:testDebugUnitTest

OPTIONAL TESTS (user runs when ready):
./gradlew :data:testDebugUnitTest  # Repository tests
./gradlew test  # All tests (comprehensive, slower)

POLICY on fake/mock data:
- Fake/mock/sample data is ALLOWED in: unit tests, instrumented tests, @Preview composables
- Fake/mock/sample data is FORBIDDEN in: production builds, release APKs, user-facing code

The app MUST NOT show fake/placeholder data to real users.
Users must see REAL calculated data from actual algorithms, not hallucinated values.

Search for and AUDIT all instances of:
- "fake", "mock", "sample", "dummy", "placeholder", "test", "example"
- Hardcoded profile data
- Hardcoded compatibility scores
- Hardcoded horoscope/numerology results
- Fake repositories (FakeUserRepository, MockRepository, etc.)
- Sample data that could leak to production

Review ALL:
- Repository implementations (ensure real DB/API calls in production, fakes only in tests)
- ViewModel sample states (ensure guarded by BuildConfig.DEBUG or test scope)
- Preview data (ensure wrapped with LocalInspectionMode.current check)
- Hardcoded strings that look like real spiritual data
- Default values that should come from real calculations

For each fake/mock found:
1. If it's in src/test/ or src/androidTest/ - LEAVE IT (testing is fine)
2. If it's for @Preview only - ensure it CANNOT reach production (use if (LocalInspectionMode.current))
3. If it's a fake repository - ensure Hilt/DI only injects it in test builds, not release
4. If it's sample data in production UI - REMOVE IT or guard with BuildConfig.DEBUG
5. If it's a placeholder calculation - implement REAL calculation or show "Calculating..." / "Not available"

Create report: FAKE_DATA_AUDIT.md listing:
- All fake data found
- Location (file:line)
- Status: OK (test only) / FIXED (was leaking) / NEEDS_ATTENTION
- Verification that release builds are clean

Files to search: **/*.kt, **/*.java
Patterns: grep -r "fake|mock|sample|dummy|placeholder|hardcode|TODO.*data" --include="*.kt"
```

### Agent 0.5: CRITICAL - Expand AI Provider Support
```
The app currently supports OpenRouter and Ollama. We need to support ALL major AI providers
that users might have API keys for.

Search the web for:
- "AI API providers 2025 comparison"
- "LLM API services list"
- Each provider's API documentation and authentication

PROVIDERS TO ADD (implement ALL):

Cloud Providers (API Key based):
1. OpenAI - GPT-4o, GPT-4, GPT-3.5 (api.openai.com)
2. Anthropic - Claude 3.5, Claude 3 (api.anthropic.com)
3. Google AI - Gemini Pro, Gemini Ultra (generativelanguage.googleapis.com)
4. Mistral AI - Mistral Large, Medium, Small (api.mistral.ai)
5. Cohere - Command R+, Command R (api.cohere.ai)
6. Groq - Llama, Mixtral on fast inference (api.groq.com)
7. Together AI - Open source models (api.together.xyz)
8. Fireworks AI - Fast inference (api.fireworks.ai)
9. Perplexity - pplx-api (api.perplexity.ai)
10. DeepSeek - DeepSeek models (api.deepseek.com)
11. Anyscale - Open source models (api.endpoints.anyscale.com)
12. Replicate - Various models (api.replicate.com)
13. Hugging Face - Inference API (api-inference.huggingface.co)
14. AI21 Labs - Jurassic models (api.ai21.com)
15. Reka AI - Multimodal (api.reka.ai)

Aggregators (already have OpenRouter, add more):
16. LiteLLM compatible endpoints
17. LocalAI (local hosting)

Local Options (already have Ollama, ensure working):
18. Ollama - Verify all model support
19. LM Studio - Local API compatibility
20. llama.cpp server - Direct integration

IMPLEMENTATION REQUIREMENTS:

1. Create unified AiProvider interface:
```kotlin
interface AiProvider {
    val name: String
    val requiresApiKey: Boolean
    val supportedModels: List<AiModel>
    val baseUrl: String

    suspend fun generateCompletion(prompt: String, model: AiModel): Result<String>
    suspend fun validateApiKey(apiKey: String): Boolean
    fun getAvailableModels(): List<AiModel>
}
```

2. Create provider implementations in data/ai/:
- OpenAiProvider.kt
- AnthropicProvider.kt
- GoogleAiProvider.kt
- MistralProvider.kt
- CohereProvider.kt
- GroqProvider.kt
- TogetherProvider.kt
- FireworksProvider.kt
- PerplexityProvider.kt
- DeepSeekProvider.kt
- (etc for all above)

3. Update Settings screen to:
- Show all available providers
- Allow API key entry for each
- Test connection button
- Model selection per provider
- Set preferred provider order (fallback chain)

4. Create AiProviderManager:
- Auto-detect available providers
- Fallback logic if primary fails
- Rate limiting handling
- Cost tracking (optional)

5. Secure API key storage:
- Use EncryptedSharedPreferences
- Never log API keys
- Never include in crash reports

6. Update existing AI features to use new provider system:
- Profile enrichment
- Compatibility analysis
- Any AI-generated insights

Search web for each provider's:
- Current API documentation
- Authentication method
- Rate limits
- Pricing (for user reference)
- Available models

Create: AI_PROVIDERS_IMPLEMENTATION.md with full documentation

Files: data/ai/**, domain/ai/**, feature/settings/**
```

### Agent 1: Dependency Audit & Updates
```
Search the web for:
- Latest stable versions of all Android/Kotlin dependencies (2025)
- Jetpack Compose BOM latest version
- Kotlin version compatibility
- Security advisories for current dependencies

Then:
- Review all build.gradle.kts files
- Update dependencies to latest stable versions
- Check for deprecated APIs
- Ensure version compatibility
- Update Gradle wrapper if needed

Files: **/build.gradle.kts, gradle/libs.versions.toml, gradle-wrapper.properties
```

### Agent 2: Performance Optimization
```
Search the web for:
- Jetpack Compose performance best practices 2025
- Android app startup optimization
- Memory leak prevention in Compose
- LazyColumn/LazyRow optimization techniques

Then review and fix:
- Unnecessary recompositions (add @Stable, remember, derivedStateOf)
- Heavy computations in composables (move to ViewModel)
- Image loading optimization (Coil best practices)
- Baseline profiles for app startup
- R8/ProGuard optimization

Files: All Composable files, ViewModels, image loading code
```

### Agent 3: Accessibility (A11y) Audit
```
Search the web for:
- Android accessibility guidelines 2025
- Jetpack Compose accessibility best practices
- WCAG 2.1 AA compliance for mobile
- TalkBack optimization

Then review and fix:
- Missing contentDescription on images/icons
- Touch target sizes (minimum 48dp)
- Color contrast ratios
- Screen reader navigation order
- Focus management
- Semantic properties

Files: All UI components, screens, custom components
```

### Agent 4: Security Hardening
```
Search the web for:
- Android security best practices 2025
- OWASP Mobile Top 10
- Secure data storage Android
- API key protection techniques
- Certificate pinning implementation

Then review and fix:
- API key exposure (move to BuildConfig or secrets)
- Network security config
- Data encryption at rest
- Secure SharedPreferences usage
- Input validation
- SQL injection prevention (Room)
- Intent security

Files: Network code, data storage, API calls, manifests
```

### Agent 5: Code Quality & Architecture
```
Search the web for:
- Clean Architecture Android 2025
- Kotlin best practices and idioms
- SOLID principles in Android
- Error handling patterns

Then review and fix:
- Code duplication (DRY violations)
- Long functions (break into smaller)
- Missing error handling
- Inconsistent naming conventions
- Improper dependency injection
- ViewModel state management
- Repository pattern compliance

Files: All source files, focus on domain and data layers
```

### Agent 6: UI/UX Polish
```
Search the web for:
- Material Design 3 latest guidelines
- Android animation best practices
- Micro-interaction patterns
- Loading state patterns

Then review and fix:
- Inconsistent spacing/padding
- Missing loading states
- Jarring transitions
- Color inconsistencies
- Typography hierarchy
- Empty state designs
- Error state designs

Files: All UI components and screens
```

### Agent 7: Testing Coverage
```
Search the web for:
- Android testing best practices 2025
- Compose UI testing patterns
- MockK vs Mockito comparison
- Test coverage tools

Then:
- Identify untested code paths
- Write unit tests for ViewModels
- Write unit tests for Repositories
- Write unit tests for Use Cases
- Write UI tests for critical flows
- Aim for 80%+ coverage

Files: Create test files in */src/test/ and */src/androidTest/
```

### Agent 8: Documentation & Comments
```
Search the web for:
- KDoc best practices
- README standards for Android projects
- Architecture documentation patterns

Then:
- Add KDoc to all public functions/classes
- Update README with setup instructions
- Document architecture decisions
- Create API documentation for core modules
- Add inline comments for complex logic

Files: All source files, README.md, docs/
```

### Agent 9: Localization & Internationalization
```
Search the web for:
- Android localization best practices 2025
- RTL layout support
- Date/time formatting internationalization
- Pluralization patterns

Then:
- Extract all hardcoded strings to resources
- Set up string resource structure
- Add RTL support where missing
- Use proper date/time formatters
- Prepare for translation

Files: All UI files, strings.xml, layouts
```

### Agent 10: Build & CI/CD Optimization
```
Search the web for:
- Gradle build optimization 2025
- Android CI/CD best practices
- GitHub Actions for Android
- Build caching strategies

Then:
- Optimize Gradle build times
- Set up proper build variants
- Configure signing configs
- Create GitHub Actions workflow
- Add pre-commit hooks
- Set up lint rules

Files: build.gradle.kts, .github/workflows/, gradle.properties
```

---

## Web Search Requirements

For EVERY task, search the web first:

```kotlin
// Example searches to perform:
"Jetpack Compose performance optimization 2025"
"Android security best practices OWASP 2025"
"Material Design 3 components Android"
"Kotlin coroutines best practices 2025"
"Room database optimization Android"
"Coil image loading best practices"
"Android accessibility TalkBack 2025"
"Gradle build optimization Android 2025"
```

---

## Output Requirements

Each agent must produce:

1. **CHANGES.md** - List of all changes made
2. **ISSUES_FOUND.md** - Issues discovered during review
3. **RECOMMENDATIONS.md** - Future improvements suggested
4. **WEB_RESEARCH.md** - Key findings from web searches

---

## Priority Order

If resources are limited, prioritize:
0. **FAKE DATA AUDIT (Agent 0) - HIGHEST PRIORITY** - App must not show hallucinated/fake data to users (OK in tests)
0.5. **AI PROVIDERS (Agent 0.5) - HIGH PRIORITY** - Expand to support 20+ AI providers
1. Security (Agent 4) - Critical
2. Performance (Agent 2) - High impact
3. Accessibility (Agent 3) - Legal/ethical requirement
4. Dependencies (Agent 1) - Security updates
5. Code Quality (Agent 5) - Maintainability
6. Testing (Agent 7) - Reliability
7. UI/UX (Agent 6) - User experience
8. Documentation (Agent 8) - Team efficiency
9. Localization (Agent 9) - Market expansion
10. Build/CI (Agent 10) - Developer experience

---

## Coordination

Create a summary file when complete:
`COMPREHENSIVE_REVIEW_COMPLETE.md`

Include:
- Total issues found
- Total issues fixed
- Test coverage achieved
- Performance improvements measured
- Security vulnerabilities addressed
- Recommendations for next sprint

---

## Remember

- ALWAYS search the web for current best practices
- ALWAYS launch multiple agents in parallel
- ALWAYS document your changes
- ALWAYS run tests after changes
- NEVER break existing functionality
- NEVER skip security considerations
