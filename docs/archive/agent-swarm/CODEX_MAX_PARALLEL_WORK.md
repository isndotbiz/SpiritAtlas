# Codex Max Parallel Work Instructions
**DO NOT TOUCH CLAUDE CODE AGENT WORK - READ THIS CAREFULLY**

---

## Your Mission

While Claude Code's 21-agent swarm handles the primary Android app optimization, you will work on **complementary infrastructure and tooling** that accelerates the overall mission to reach 95/100 health score.

**CRITICAL**: You are working in PARALLEL with Claude Code agents. Your work must NOT conflict with their domains.

---

## What Claude Code Agents Are Handling (DO NOT TOUCH)

❌ **OFF LIMITS - DO NOT MODIFY**:
- `/app/**` - Android application code
- `/core/**` - Core calculation modules
- `/data/**` - Data layer
- `/domain/**` - Domain models
- `/feature/**` - Feature modules
- `*.kt` - Any Kotlin files
- `build.gradle.kts` - Build files
- `AndroidManifest.xml` - Android manifests
- Any files currently being modified by Claude Code agents

---

## Your Safe Work Zones (APPROVED FOR YOU)

✅ **YOU CAN WORK ON**:
- `/tools/**` - Tooling and automation scripts
- `/scripts/**` - Build and deployment scripts
- `/docs/**` - Documentation
- `*.md` - Markdown documentation files
- `*.py` - Python scripts for tooling
- `*.sh` - Shell scripts for automation
- `/assets/**` - Static assets and resources

---

## Your Specific Tasks

### Task 1: Enhanced Documentation System
**Priority**: HIGH
**Location**: `/docs/`

Create comprehensive documentation:

1. **Developer Onboarding Guide** (`/docs/DEVELOPER_ONBOARDING.md`)
   - Environment setup
   - Build process
   - Architecture overview
   - Contributing guidelines

2. **API Documentation** (`/docs/API_REFERENCE.md`)
   - All spiritual calculation APIs
   - Claude AI integration guide
   - fal.ai image generation guide
   - OpenRouter integration

3. **Feature Documentation** (`/docs/FEATURES.md`)
   - Complete feature breakdown
   - User journey maps
   - Screen flow diagrams
   - Use case examples

4. **Testing Guide** (`/docs/TESTING.md`)
   - How to run tests
   - Testing strategy
   - Coverage requirements
   - Writing new tests

---

### Task 2: Automated Testing Infrastructure
**Priority**: HIGH
**Location**: `/scripts/testing/`

Create testing automation scripts:

1. **Continuous Test Runner** (`/scripts/testing/continuous_test.sh`)
   ```bash
   #!/bin/bash
   # Watch for file changes and run relevant tests
   # Provide immediate feedback to developers
   ```

2. **Coverage Reporter** (`/scripts/testing/coverage_report.py`)
   ```python
   # Parse JaCoCo XML reports
   # Generate beautiful HTML coverage dashboard
   # Track coverage trends over time
   ```

3. **Test Result Aggregator** (`/scripts/testing/aggregate_results.py`)
   ```python
   # Combine test results from all modules
   # Generate comprehensive test report
   # Identify flaky tests
   ```

---

### Task 3: Build & Deployment Automation
**Priority**: HIGH
**Location**: `/scripts/build/`

Create build automation:

1. **Fast Build Script** (`/scripts/build/fast_build.sh`)
   ```bash
   #!/bin/bash
   # Parallel module compilation
   # Cached dependency resolution
   # Incremental build optimization
   ```

2. **Release Automation** (`/scripts/build/release.sh`)
   ```bash
   #!/bin/bash
   # Automated versioning
   # Build release APK
   # Generate release notes
   # Sign APK
   # Upload to Play Store (prep)
   ```

3. **APK Analyzer** (`/scripts/build/analyze_apk.py`)
   ```python
   # Analyze APK composition
   # Identify size opportunities
   # Track size over time
   # Generate size report
   ```

---

### Task 4: Image Generation Automation
**Priority**: MEDIUM
**Location**: `/tools/image_generation/`

Enhance image generation tooling:

1. **Batch Image Generator** (`/tools/image_generation/batch_generate.py`)
   ```python
   # Generate multiple images in parallel
   # Automatic quality validation
   # Android resource organization
   # Cost tracking
   ```

2. **Image Optimizer Pipeline** (`/tools/image_generation/optimize_pipeline.py`)
   ```python
   # Automatic WebP conversion
   # Multi-density generation
   # Quality validation
   # Integration verification
   ```

3. **Image Catalog Generator** (`/tools/image_generation/generate_catalog.py`)
   ```python
   # Generate visual catalog of all images
   # HTML gallery with metadata
   # Usage tracking across app
   # Gap analysis
   ```

---

### Task 5: Health Score Automation
**Priority**: MEDIUM
**Location**: `/scripts/health/`

Create health monitoring:

1. **Auto Health Checker** (`/scripts/health/check_health.sh`)
   ```bash
   #!/bin/bash
   # Automated health score calculation
   # Run on every commit
   # Track score over time
   # Alert on regressions
   ```

2. **Health Dashboard** (`/scripts/health/dashboard.py`)
   ```python
   # Generate HTML dashboard
   # Visualize health trends
   # Show progress to 95/100
   # Identify improvement opportunities
   ```

3. **Regression Detector** (`/scripts/health/detect_regressions.py`)
   ```python
   # Compare health scores over time
   # Identify which changes caused regressions
   # Automatic issue creation
   ```

---

### Task 6: Code Quality Tooling
**Priority**: MEDIUM
**Location**: `/scripts/quality/`

Create quality tools:

1. **Lint Automation** (`/scripts/quality/auto_lint.sh`)
   ```bash
   #!/bin/bash
   # Run lint on changed files only
   # Auto-fix common issues
   # Generate lint report
   ```

2. **Code Complexity Analyzer** (`/scripts/quality/complexity.py`)
   ```python
   # Calculate cyclomatic complexity
   # Identify refactoring candidates
   # Track complexity trends
   ```

3. **Dependency Auditor** (`/scripts/quality/audit_deps.py`)
   ```python
   # Check for outdated dependencies
   # Security vulnerability scanning
   # License compliance checking
   ```

---

### Task 7: Performance Monitoring Tools
**Priority**: LOW
**Location**: `/scripts/performance/`

Create performance tools:

1. **Startup Time Profiler** (`/scripts/performance/startup_profiler.sh`)
   ```bash
   #!/bin/bash
   # Measure app startup time
   # Track over time
   # Identify startup bottlenecks
   ```

2. **Memory Leak Detector** (`/scripts/performance/leak_detector.py`)
   ```python
   # Analyze heap dumps
   # Identify potential leaks
   # Generate leak report
   ```

3. **Frame Rate Monitor** (`/scripts/performance/fps_monitor.sh`)
   ```bash
   #!/bin/bash
   # Monitor frame rates during UI tests
   # Identify jank
   # Generate performance report
   ```

---

### Task 8: Market Research Tools
**Priority**: LOW
**Location**: `/tools/research/`

Create research automation:

1. **App Store Scraper** (`/tools/research/scrape_reviews.py`)
   ```python
   # Scrape reviews from spiritual apps
   # Sentiment analysis
   # Feature request extraction
   # Trend identification
   ```

2. **Competitor Monitor** (`/tools/research/monitor_competitors.py`)
   ```python
   # Track competitor updates
   # Feature comparison matrix
   # Pricing analysis
   ```

3. **Reddit Sentiment Analyzer** (`/tools/research/reddit_analysis.py`)
   ```python
   # Analyze r/astrology, r/spirituality posts
   # Extract common pain points
   # Identify feature requests
   # Generate insights report
   ```

---

## Coordination Protocol

### Before Starting Any Work
1. Check `/AGENT_SWARM_MASTER_PLAN.md` for agent status
2. Verify your file is not in the OFF LIMITS list
3. Create your files in approved directories only
4. Use feature branches for all changes

### While Working
1. **DO NOT** modify any Kotlin code
2. **DO NOT** touch Android build files
3. **DO NOT** change app architecture
4. **ONLY** work in approved zones

### After Completing Work
1. Commit to separate branch: `codex-max-tooling`
2. Document all new scripts in `/tools/README.md`
3. Create usage examples
4. Test all scripts before committing

---

## Success Criteria

### Your Goals
- [ ] Complete developer onboarding docs
- [ ] Create automated testing infrastructure
- [ ] Build release automation ready
- [ ] Health score automation working
- [ ] Image generation pipeline enhanced
- [ ] Code quality tools operational
- [ ] Performance monitoring ready

### Quality Standards
- All scripts must have usage documentation
- All scripts must handle errors gracefully
- All Python scripts must use type hints
- All scripts must be executable and tested

---

## File Naming Conventions

### Scripts
- Use lowercase with underscores: `check_health.sh`
- Make executable: `chmod +x script.sh`
- Include shebang: `#!/bin/bash` or `#!/usr/bin/env python3`

### Documentation
- Use SCREAMING_SNAKE_CASE: `DEVELOPER_ONBOARDING.md`
- Include table of contents for >100 lines
- Use clear section headers

---

## Example: Health Checker Script

Here's an example of what we need:

```bash
#!/bin/bash
# scripts/health/check_health.sh
# Automated health score calculator

set -e

echo "🏥 SpiritAtlas Health Check"
echo "=========================="

# Build status
echo -n "Build Status: "
if ./gradlew assembleDebug > /dev/null 2>&1; then
    echo "✅ PASS"
    BUILD_SCORE=10
else
    echo "❌ FAIL"
    BUILD_SCORE=0
fi

# Lint status
echo -n "Lint Status: "
if ./gradlew lint > /dev/null 2>&1; then
    echo "✅ PASS"
    LINT_SCORE=10
else
    echo "❌ FAIL"
    LINT_SCORE=0
fi

# Test coverage
echo -n "Test Coverage: "
./gradlew testDebugUnitTestCoverage > /dev/null 2>&1
COVERAGE=$(python3 scripts/health/parse_coverage.py)
echo "$COVERAGE%"
COVERAGE_SCORE=$((COVERAGE / 10))

# Calculate total
TOTAL=$((BUILD_SCORE + LINT_SCORE + COVERAGE_SCORE))
echo ""
echo "Total Health Score: $TOTAL/100"
```

---

## Priority Order

1. **CRITICAL - Do First**:
   - Developer onboarding docs
   - Testing infrastructure
   - Build automation

2. **HIGH - Do Second**:
   - Health score automation
   - Image generation enhancements
   - API documentation

3. **MEDIUM - Do Third**:
   - Code quality tooling
   - Performance monitoring
   - Feature documentation

4. **LOW - Do Last**:
   - Market research tools
   - Dependency auditing

---

## Communication

### Status Updates
- Commit messages should be clear: `feat(tools): Add health checker script`
- Create `/tools/STATUS.md` with your progress
- Update every hour with completed tasks

### Questions
- If you need clarification, check `/AGENT_SWARM_MASTER_PLAN.md`
- **DO NOT** interrupt Claude Code agents
- Document assumptions in your code

---

## Final Reminders

🚫 **NEVER TOUCH**:
- Kotlin code (`.kt`)
- Android build files (`build.gradle.kts`)
- Android manifests
- App source code (`/app`, `/feature`, `/core`, `/domain`, `/data`)

✅ **SAFE TO CREATE**:
- Documentation (`.md`)
- Scripts (`.sh`, `.py`)
- Tools and utilities
- Test infrastructure
- CI/CD configuration

---

## Expected Timeline

**Your Work**: 4-6 hours
**Claude Code Agents**: 6 hours
**Overlap**: You'll finish at same time or before

By working in parallel on complementary infrastructure, you'll accelerate the entire mission without conflicts.

---

**Status**: READY TO START
**Your Branch**: `codex-max-tooling`
**Your Domain**: Infrastructure & Tooling
**Their Domain**: Android App Code

**LET'S BUILD THE MOST ADVANCED SPIRITUAL APP EVER** 🚀✨
