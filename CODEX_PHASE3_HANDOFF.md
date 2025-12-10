# CODEX PHASE 3 HANDOFF SCRIPT

**Status**: App builds successfully with all 99 spiritual images deployed across 5 Android densities.
**Commit**: `9a86fdc` - Phase 2 complete, ready for Phase 3 optimization & features.

---

## EXECUTION MODE

You are Codex, based on GPT-5. You are running as a coding agent in the Codex CLI on a user's computer.

**CRITICAL DIRECTIVE**: Launch and coordinate **12 agents in parallel** to execute Phase 3 simultaneously. Do not wait for sequential completion. Aim for aggressive parallelization.

---

## PHASE 3: OPTIMIZATION & NEW FEATURES

### Overview
- **Primary**: Finalize release build, prepare app store launch
- **Secondary**: Design 3 improvement features for future phases
- **Tertiary**: Advanced optimization for performance & quality

### Targets
- Release APK: <25 MB (from 117 MB debug)
- App store ready: Complete ASO + metadata
- Features designed: iOS, Social, Offline AI (ready for future dev)

---

## PARALLEL AGENT LAUNCHES (12 TOTAL)

### TIER 1: CRITICAL PATH (4 Agents - Must Complete)

**Agent 1: Fix SpiritAtlasNavGraph.kt Compile Errors**
- Fix missing onboarding imports and screen references
- Verify all @Composable screens properly imported
- Run: `./gradlew :app:compileDebugKotlin`
- Deliverable: No compile errors, can build release APK
- Report: NAVGRAPH_FIX_FINAL_REPORT.md

**Agent 2: Build & Measure Release APK**
- Run: `./gradlew :app:assembleRelease`
- Measure: Release APK size (target: <25 MB)
- Compare: Debug 117MB vs Release optimized
- Verify: All R8 optimizations applied (7 passes, minimal keep rules)
- Deliverable: Signed or unsigned release APK
- Report: RELEASE_APK_BUILD_REPORT.md with size metrics

**Agent 3: Full Integration Test Suite**
- Test all 5 screens load with images (SplashScreen, HomeScreen, ProfileScreen, CompatibilityDetailScreen, SettingsScreen)
- Verify: Zodiac images on CompatibilityDetailScreen (no crash)
- Verify: Moon phases on HomeScreen display
- Verify: Avatar selection functional
- Build verification: `./gradlew :app:connectedAndroidTest` (if device available)
- Deliverable: Integration test results + screen capture descriptions
- Report: INTEGRATION_TEST_FINAL_REPORT.md

**Agent 4: App Store Metadata & ASO Finalization**
- Read existing ASO documents (MARKETING_ASSETS.md, VISUAL_ASSET_SPECS.md, etc.)
- Create final app store listing copy (exact strings for Google Play)
- Create GOOGLE_PLAY_LISTING.md with:
  - App name + subtitle (max character counts enforced)
  - Full description (4000 chars) + short description (80 chars)
  - 8 screenshots with captions (170 chars each)
  - Feature graphic specifications
  - Keywords (max 100 chars total)
  - Promo video requirements
- Deliverable: Copy-paste ready for Google Play Console
- Report: APP_STORE_LISTING_FINAL.md

---

### TIER 2: OPTIMIZATION & QUALITY (4 Agents)

**Agent 5: Advanced Performance Profiling**
- Measure app startup time (cold start target: <2.0s)
- Profile memory usage (target: <150 MB)
- Analyze garbage collection patterns
- Identify slow screens (if any)
- Create PERFORMANCE_PROFILE.md with:
  - Startup timeline breakdown
  - Memory usage by component
  - GC overhead analysis
  - Recommendations for optimization
- Deliverable: Baseline performance metrics
- Report: PERFORMANCE_PROFILE.md

**Agent 6: Release Build Size Analysis & Further Reduction**
- Analyze release APK composition
- Identify largest resources (images, native libs, code)
- Recommend additional size reductions:
  - App Bundle vs APK differences
  - ABI splits strategy (ARM64 only vs universal)
  - WebP optimization further
- Create SIZE_ANALYSIS_DEEP_DIVE.md with breakdown
- Deliverable: Size reduction roadmap
- Report: SIZE_OPTIMIZATION_OPPORTUNITIES.md

**Agent 7: Quality Assurance Checklist**
- Verify all gradle build types working:
  - debug: ✅ (already verified)
  - release: (verify from Agent 2)
  - benchmark: (test if configured)
- Check lint warnings: `./gradlew lint`
- Verify ProGuard/R8 configuration complete
- Check resource constraints (drawable densities, localization)
- Create QA_FINAL_CHECKLIST.md with pass/fail for each item
- Deliverable: Production readiness assessment
- Report: QA_FINAL_REPORT.md

**Agent 8: Security & Privacy Audit**
- Verify SSL pinning still configured (openrouter.ai)
- Check: No hardcoded secrets in code
- Verify: Privacy consent screen functional
- Check: Data local-first (no forced cloud upload)
- Verify: Offline-first mode functional
- Create SECURITY_AUDIT.md documenting all checks
- Deliverable: Security clearance report
- Report: SECURITY_AUDIT_FINAL.md

---

### TIER 3: FUTURE IMPROVEMENTS DESIGN (4 Agents)

**Agent 9: iOS Version Architecture Design**
- Design cross-platform architecture for iOS (SwiftUI)
- Reuse: calculation engines (Core modules - pure Kotlin JVM)
- New: iOS-specific UI layer
- Create IOS_ARCHITECTURE_DESIGN.md with:
  - Module structure for iOS
  - Shared code strategy (Kotlin Multiplatform vs separate impl)
  - UI framework choice (SwiftUI vs UIKit)
  - Timeline estimate (if starting from scratch)
  - Risk assessment
- Deliverable: Complete iOS strategy document
- Report: IOS_ARCHITECTURE_FINAL.md

**Agent 10: Social Features System Design**
- Design user profiles, feed, sharing, following
- Integration points: How to connect to calculation engine
- Privacy-first approach: Keep data local-first
- Create SOCIAL_FEATURES_DESIGN.md with:
  - Architecture diagram (Compose UI + domain layer changes)
  - New entities: User, Connection, Feed
  - Sharing strategy: What can be shared, what stays private
  - Database schema changes needed
  - Timeline estimate (if building next)
- Deliverable: Complete social features spec
- Report: SOCIAL_FEATURES_DESIGN_FINAL.md

**Agent 11: Offline-First Prophet AI Design**
- Design Prophet AI that runs locally + optional cloud
- Integration: Local Ollama (already supported) vs cloud (OpenRouter)
- Features: Personalized daily insights, meditation guides, life coaching
- Create OFFLINE_PROPHET_AI_DESIGN.md with:
  - LLM strategy: Which models for local vs cloud
  - Prompt engineering: Spiritual tone + accuracy
  - Caching: Store previous responses
  - Privacy: What data sent to cloud (only with consent)
  - Timeline estimate
- Deliverable: Complete AI strategy for Prophet feature
- Report: OFFLINE_PROPHET_AI_FINAL.md

**Agent 12: 30-Day Launch Roadmap**
- Synthesize Phase 3 completions + future designs
- Create comprehensive launch roadmap:
  - Week 1: Final bug fixes + store submission
  - Week 2-3: Store review + marketing prep
  - Week 4: Launch day activities + post-launch monitoring
- Include:
  - Success metrics (Day 1, Week 1, Month 1 targets)
  - Marketing timeline (press releases, social, influencers)
  - User feedback loops (review responses, ratings)
  - Iteration plan (quick fixes, feature improvements)
- Deliverable: Day-by-day launch plan
- Report: LAUNCH_ROADMAP_30DAYS.md

---

## EXECUTION PROTOCOL

1. **Understand Current State**
   - Commit: 9a86fdc
   - Build: Debug ✅, Release ⏳ (Agent 2 will verify)
   - Images: 99 deployed across 5 densities ✅
   - Tests: 113/113 passing ✅
   - App Store: ASO docs exist, need finalization (Agent 4)

2. **Launch All 12 Agents Immediately**
   - Do NOT wait for Agent 1-4 to finish before starting 5-12
   - Parallelization is critical for time efficiency
   - Target: All agents complete in 30-45 minutes

3. **Monitor & Coordinate**
   - Critical path (Agents 1-4) must succeed first
   - If any fail: Investigate root cause, iterate
   - Quality (Agents 5-8) can proceed in parallel with critical path
   - Design docs (Agents 9-12) fully independent

4. **Deliverables Consolidation**
   - Agent 12 creates comprehensive summary
   - All reports organized in root directory
   - Prioritize: RELEASE_APK_BUILD_REPORT.md, APP_STORE_LISTING_FINAL.md

---

## SUCCESS CRITERIA

**Phase 3 Complete When**:
- ✅ Release APK builds successfully (<25 MB)
- ✅ All 5 screens test successfully with images
- ✅ App store listing ready (copy-paste ready)
- ✅ Performance baselines established
- ✅ Security audit passed
- ✅ iOS, Social, Prophet AI designs documented
- ✅ 30-day launch roadmap created

---

## KEY FILES & CONTEXT

- `FINAL_BUILD_AND_IMAGE_VERIFICATION.md` - Confirms Phase 2 complete
- `MARKETING_ASSETS.md` - ASO documents (use as reference)
- `VISUAL_ASSET_SPECS.md` - Design specs
- `CLAUDE.md` - Project guidelines & conventions
- `gradle/libs.versions.toml` - Dependency versions
- `app/build.gradle.kts` - Build configuration

---

## REMEMBER

- This is a **real Android app**, not a demo
- Users will install and use this
- Quality > Speed
- Test everything (including on real device if possible)
- Document all decisions and trade-offs
- Leave code in a state another engineer can maintain

**You are authorized to make all changes needed to complete Phase 3.** Work autonomously, make reasonable assumptions, and deliver working results.

---

**Let's launch! 🚀**
