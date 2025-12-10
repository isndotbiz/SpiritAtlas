# SpiritAtlas Health Check - Automated Tests
## Comprehensive Automated Testing & Validation Scripts

**Version:** 1.0
**Date:** 2025-12-09
**Purpose:** Automated test suite to objectively measure app health score

---

## Overview

This document provides all automated tests, scripts, and commands to measure SpiritAtlas app health. Each test maps to specific criteria in the App Health Scoring System.

### Quick Start

```bash
# Clone this section for quick health check
cd /Users/jonathanmallinger/Workspace/SpiritAtlas

# Run all critical tests
./gradlew :core:numerology:test :core:astro:test :core:ayurveda:test :core:humandesign:test
./gradlew :feature:home:testDebugUnitTest :feature:compatibility:testDebugUnitTest
./gradlew lint
./gradlew assembleDebug

# Generate coverage report
./gradlew testDebugUnitTest jacocoTestReport
```

---

## Category 1: Visual Excellence

### 1.1 Image Asset Completeness Test

**Script:** `scripts/check_image_assets.sh`

```bash
#!/bin/bash
# Check image asset integration completeness

echo "=== IMAGE ASSET COMPLETENESS CHECK ==="

# Count optimized images
OPTIMIZED_COUNT=$(find tools/image_generation/generated_images/optimized_flux_pro -type f \( -name "*.png" -o -name "*.webp" \) | wc -l | tr -d ' ')
echo "Optimized images available: $OPTIMIZED_COUNT / 101"

# Count integrated images in app resources
INTEGRATED_COUNT=$(find app/src/main/res/drawable* -type f \( -name "*.png" -o -name "*.webp" \) 2>/dev/null | wc -l | tr -d ' ')
echo "Images integrated in app: $INTEGRATED_COUNT"

# Check specific image categories
echo ""
echo "--- Category Breakdown ---"

# Chakras (7 expected)
CHAKRA_COUNT=$(find app/src/main/res/drawable* -name "*chakra*" -type f 2>/dev/null | wc -l | tr -d ' ')
echo "Chakra images: $CHAKRA_COUNT / 7"

# Zodiac (12 expected)
ZODIAC_COUNT=$(find app/src/main/res/drawable* -name "*zodiac*" -type f 2>/dev/null | wc -l | tr -d ' ')
echo "Zodiac images: $ZODIAC_COUNT / 12"

# Moon phases (8 expected)
MOON_COUNT=$(find app/src/main/res/drawable* -name "*moon*" -type f 2>/dev/null | wc -l | tr -d ' ')
echo "Moon phase images: $MOON_COUNT / 8"

# Elements (4 expected)
ELEMENT_COUNT=$(find app/src/main/res/drawable* -name "*element*" -type f 2>/dev/null | wc -l | tr -d ' ')
echo "Element images: $ELEMENT_COUNT / 4"

# Calculate score
PERCENTAGE=$(echo "scale=2; ($INTEGRATED_COUNT / 101) * 100" | bc)
echo ""
echo "Integration percentage: ${PERCENTAGE}%"

if (( $(echo "$INTEGRATED_COUNT >= 101" | bc -l) )); then
    SCORE=2.5
elif (( $(echo "$INTEGRATED_COUNT >= 95" | bc -l) )); then
    SCORE=2.0
elif (( $(echo "$INTEGRATED_COUNT >= 85" | bc -l) )); then
    SCORE=1.5
elif (( $(echo "$INTEGRATED_COUNT >= 75" | bc -l) )); then
    SCORE=1.0
elif (( $(echo "$INTEGRATED_COUNT >= 60" | bc -l) )); then
    SCORE=0.5
else
    SCORE=0
fi

echo "Score: $SCORE / 2.5"
```

**Run Command:**
```bash
chmod +x scripts/check_image_assets.sh
./scripts/check_image_assets.sh
```

### 1.2 Color Consistency Test

**Script:** `scripts/check_color_consistency.sh`

```bash
#!/bin/bash
# Check for hardcoded colors in UI code

echo "=== COLOR CONSISTENCY CHECK ==="

# Find hardcoded Color(0xFFRRGGBB) in feature modules
HARDCODED_COLORS=$(grep -r "Color(0x[A-F0-9]\{8\})" --include="*.kt" feature/ app/src/main/java/com/spiritatlas/app --exclude-dir=build | wc -l | tr -d ' ')

echo "Hardcoded hex colors found: $HARDCODED_COLORS"

# List files with hardcoded colors
if [ "$HARDCODED_COLORS" -gt 0 ]; then
    echo ""
    echo "Files with hardcoded colors:"
    grep -r "Color(0x[A-F0-9]\{8\})" --include="*.kt" feature/ app/src/main/java/com/spiritatlas/app --exclude-dir=build -l
fi

# Calculate score (0 hardcoded = perfect)
if [ "$HARDCODED_COLORS" -eq 0 ]; then
    SCORE=2.5
elif [ "$HARDCODED_COLORS" -le 5 ]; then
    SCORE=2.0
elif [ "$HARDCODED_COLORS" -le 15 ]; then
    SCORE=1.5
elif [ "$HARDCODED_COLORS" -le 30 ]; then
    SCORE=1.0
elif [ "$HARDCODED_COLORS" -le 50 ]; then
    SCORE=0.5
else
    SCORE=0
fi

echo ""
echo "Score: $SCORE / 2.5"
```

### 1.3 Typography Harmony Test

```bash
#!/bin/bash
# Check for hardcoded font sizes/weights

echo "=== TYPOGRAPHY HARMONY CHECK ==="

# Find hardcoded fontSize/fontWeight outside Theme.kt
HARDCODED_TYPOGRAPHY=$(grep -r "fontSize\s*=\|fontWeight\s*=" --include="*.kt" feature/ app/src/main/java/com/spiritatlas/app --exclude-dir=build | grep -v "MaterialTheme.typography" | grep -v "Theme.kt" | wc -l | tr -d ' ')

echo "Hardcoded typography values: $HARDCODED_TYPOGRAPHY"

if [ "$HARDCODED_TYPOGRAPHY" -eq 0 ]; then
    SCORE=1.5
elif [ "$HARDCODED_TYPOGRAPHY" -le 10 ]; then
    SCORE=1.0
else
    SCORE=0.5
fi

echo "Score: $SCORE / 1.5"
```

### 1.4 Spacing Consistency Test

```bash
#!/bin/bash
# Check for non-standard spacing values

echo "=== SPACING CONSISTENCY CHECK ==="

# Find spacing values not multiples of 4dp
NON_STANDARD_SPACING=$(grep -rE "\.(padding|size|width|height)\s*\(\s*[0-9]+\.dp\s*\)" --include="*.kt" feature/ app/src/main/java/com/spiritatlas/app --exclude-dir=build | grep -E "\.dp" | grep -vE "(4|8|12|16|20|24|28|32|36|40|44|48|52|56|60|64)\.dp" | wc -l | tr -d ' ')

echo "Non-standard spacing values found: $NON_STANDARD_SPACING"

if [ "$NON_STANDARD_SPACING" -eq 0 ]; then
    SCORE=1.5
elif [ "$NON_STANDARD_SPACING" -le 10 ]; then
    SCORE=1.0
else
    SCORE=0.5
fi

echo "Score: $SCORE / 1.5"
```

---

## Category 2: Performance

### 2.1 App Startup Time Test

**Script:** `scripts/measure_startup.sh`

```bash
#!/bin/bash
# Measure cold and warm start times

echo "=== APP STARTUP TIME TEST ==="

# Ensure device connected
adb devices | grep -q "device$" || { echo "No device connected"; exit 1; }

# Force stop app
adb shell am force-stop com.spiritatlas.app

# Clear app data for true cold start
adb shell pm clear com.spiritatlas.app

# Measure cold start
echo "Measuring cold start..."
COLD_START=$(adb shell am start-activity -W com.spiritatlas.app/.MainActivity | grep "TotalTime" | awk '{print $2}')
echo "Cold start: ${COLD_START}ms"

# Wait a bit
sleep 3

# Send app to background
adb shell input keyevent 3

# Wait
sleep 2

# Measure warm start
echo "Measuring warm start..."
WARM_START=$(adb shell am start-activity -W com.spiritatlas.app/.MainActivity | grep "TotalTime" | awk '{print $2}')
echo "Warm start: ${WARM_START}ms"

# Calculate score
if [ "$COLD_START" -lt 2000 ] && [ "$WARM_START" -lt 500 ]; then
    SCORE=2.0
elif [ "$COLD_START" -lt 3000 ] && [ "$WARM_START" -lt 800 ]; then
    SCORE=1.5
elif [ "$COLD_START" -lt 4000 ] && [ "$WARM_START" -lt 1200 ]; then
    SCORE=1.0
elif [ "$COLD_START" -lt 5000 ] && [ "$WARM_START" -lt 1500 ]; then
    SCORE=0.5
else
    SCORE=0
fi

echo "Score: $SCORE / 2.0"
```

### 2.2 Frame Rate Test

```bash
#!/bin/bash
# Measure frame rate during scrolling

echo "=== FRAME RATE TEST ==="

# Start app
adb shell am start com.spiritatlas.app/.MainActivity

# Wait for app to load
sleep 3

# Reset gfxinfo
adb shell dumpsys gfxinfo com.spiritatlas.app reset

# Perform scrolling (simulate user interaction)
echo "Performing scroll test for 10 seconds..."
for i in {1..20}; do
    adb shell input swipe 500 1500 500 500 100
    sleep 0.5
done

# Get frame stats
FRAMESTATS=$(adb shell dumpsys gfxinfo com.spiritatlas.app framestats)

# Calculate metrics (simplified - real implementation would parse full stats)
echo "Frame statistics collected"
echo "Check Android Studio Profiler for detailed frame rate analysis"

# Manual score assignment based on profiler
echo "Score: [Manual evaluation required] / 2.0"
```

### 2.3 Memory Usage Test

```bash
#!/bin/bash
# Measure memory usage

echo "=== MEMORY USAGE TEST ==="

# Ensure app is running
adb shell am start com.spiritatlas.app/.MainActivity
sleep 3

# Get memory info
MEMINFO=$(adb shell dumpsys meminfo com.spiritatlas.app | grep "TOTAL PSS" | awk '{print $3}')
echo "Total PSS: ${MEMINFO} KB"

# Convert to MB
MEMORY_MB=$(echo "scale=2; $MEMINFO / 1024" | bc)
echo "Memory usage: ${MEMORY_MB} MB"

# Calculate score
if (( $(echo "$MEMORY_MB < 150" | bc -l) )); then
    SCORE=2.0
elif (( $(echo "$MEMORY_MB < 200" | bc -l) )); then
    SCORE=1.5
elif (( $(echo "$MEMORY_MB < 250" | bc -l) )); then
    SCORE=1.0
elif (( $(echo "$MEMORY_MB < 300" | bc -l) )); then
    SCORE=0.5
else
    SCORE=0
fi

echo "Score: $SCORE / 2.0"
```

---

## Category 3: Code Quality

### 3.1 Architecture Adherence Test

```bash
#!/bin/bash
# Check architecture layer violations

echo "=== ARCHITECTURE ADHERENCE CHECK ==="

# Check if feature modules import data layer directly
FEATURE_TO_DATA=$(grep -r "import com.spiritatlas.data" --include="*.kt" feature/ --exclude-dir=build | wc -l | tr -d ' ')
echo "Feature → Data direct imports: $FEATURE_TO_DATA (should be 0)"

# Check if domain imports Android classes
DOMAIN_ANDROID=$(grep -r "import android\." --include="*.kt" domain/src/main --exclude-dir=build | wc -l | tr -d ' ')
echo "Domain → Android imports: $DOMAIN_ANDROID (should be 0)"

# Check if ViewModels import data layer
VIEWMODEL_DATA=$(grep -r "class.*ViewModel" --include="*.kt" feature/ -A 10 | grep "import com.spiritatlas.data" | wc -l | tr -d ' ')
echo "ViewModel → Data imports: $VIEWMODEL_DATA (should be 0)"

VIOLATIONS=$((FEATURE_TO_DATA + DOMAIN_ANDROID + VIEWMODEL_DATA))

if [ "$VIOLATIONS" -eq 0 ]; then
    SCORE=2.5
elif [ "$VIOLATIONS" -le 3 ]; then
    SCORE=2.0
elif [ "$VIOLATIONS" -le 8 ]; then
    SCORE=1.5
elif [ "$VIOLATIONS" -le 15 ]; then
    SCORE=1.0
else
    SCORE=0.5
fi

echo "Total violations: $VIOLATIONS"
echo "Score: $SCORE / 2.5"
```

### 3.2 Code Documentation Test

```bash
#!/bin/bash
# Check KDoc coverage

echo "=== CODE DOCUMENTATION CHECK ==="

# Count files with KDoc
DOCUMENTED_FILES=$(find . -name "*.kt" -path "*/src/main/*" -exec grep -l "^/\*\*" {} + 2>/dev/null | wc -l | tr -d ' ')

# Count total source files
TOTAL_FILES=$(find . -name "*.kt" -path "*/src/main/*" 2>/dev/null | wc -l | tr -d ' ')

echo "Documented files: $DOCUMENTED_FILES / $TOTAL_FILES"

# Calculate percentage
PERCENTAGE=$(echo "scale=2; ($DOCUMENTED_FILES / $TOTAL_FILES) * 100" | bc)
echo "Documentation coverage: ${PERCENTAGE}%"

if (( $(echo "$PERCENTAGE >= 80" | bc -l) )); then
    SCORE=1.5
elif (( $(echo "$PERCENTAGE >= 60" | bc -l) )); then
    SCORE=1.0
else
    SCORE=0.5
fi

echo "Score: $SCORE / 1.5"
```

### 3.3 Technical Debt Test

```bash
#!/bin/bash
# Count technical debt markers

echo "=== TECHNICAL DEBT CHECK ==="

# Count TODO/FIXME/XXX/HACK comments
TODO_COUNT=$(grep -r "TODO\|FIXME\|XXX\|HACK" --include="*.kt" app/ feature/ core/ domain/ data/ --exclude-dir=build 2>/dev/null | wc -l | tr -d ' ')

echo "Technical debt markers: $TODO_COUNT"

if [ "$TODO_COUNT" -eq 0 ]; then
    SCORE=2.0
elif [ "$TODO_COUNT" -le 5 ]; then
    SCORE=1.5
elif [ "$TODO_COUNT" -le 15 ]; then
    SCORE=1.0
elif [ "$TODO_COUNT" -le 30 ]; then
    SCORE=0.5
else
    SCORE=0
fi

echo "Score: $SCORE / 2.0"

# List debt markers
if [ "$TODO_COUNT" -gt 0 ]; then
    echo ""
    echo "Debt markers found:"
    grep -rn "TODO\|FIXME\|XXX\|HACK" --include="*.kt" app/ feature/ core/ domain/ data/ --exclude-dir=build 2>/dev/null | head -20
fi
```

---

## Category 7: Testing Coverage

### 7.1 Unit Test Coverage

**Command:**
```bash
# Run all unit tests with coverage
./gradlew testDebugUnitTest jacocoTestReport

# View coverage report
open core/numerology/build/reports/jacoco/test/html/index.html
open core/astro/build/reports/jacoco/test/html/index.html
open core/ayurveda/build/reports/jacoco/test/html/index.html
open core/humandesign/build/reports/jacoco/test/html/index.html
```

**Script:** `scripts/check_test_coverage.sh`

```bash
#!/bin/bash
# Check test coverage across all modules

echo "=== UNIT TEST COVERAGE CHECK ==="

# Run tests with coverage
echo "Running tests with coverage report..."
./gradlew testDebugUnitTest jacocoTestReport --quiet

# Parse coverage reports (simplified - actual parsing would use XML)
echo ""
echo "Coverage reports generated:"
find . -name "index.html" -path "*/jacoco/test/html/*" | while read -r file; do
    MODULE=$(echo "$file" | awk -F'/' '{print $2}')
    echo "  - $MODULE: $(dirname "$file")"
done

echo ""
echo "Open reports to view detailed coverage"
echo "Target: ≥80% for all core modules"

# Manual score based on reports
echo ""
echo "Score: [Review coverage reports] / 4.0"
```

### 7.2 Test Execution

```bash
#!/bin/bash
# Run all critical tests

echo "=== RUNNING ALL CRITICAL TESTS ==="

# Core calculation tests
echo "Running core numerology tests..."
./gradlew :core:numerology:test --quiet
NUMEROLOGY_RESULT=$?

echo "Running core astrology tests..."
./gradlew :core:astro:test --quiet
ASTRO_RESULT=$?

echo "Running core ayurveda tests..."
./gradlew :core:ayurveda:test --quiet
AYURVEDA_RESULT=$?

echo "Running core human design tests..."
./gradlew :core:humandesign:test --quiet
HD_RESULT=$?

# Feature tests
echo "Running home feature tests..."
./gradlew :feature:home:testDebugUnitTest --quiet
HOME_RESULT=$?

echo "Running compatibility feature tests..."
./gradlew :feature:compatibility:testDebugUnitTest --quiet
COMPAT_RESULT=$?

# Data layer tests
echo "Running data layer tests..."
./gradlew :data:testDebugUnitTest --quiet
DATA_RESULT=$?

# Summary
echo ""
echo "=== TEST RESULTS SUMMARY ==="
echo "Numerology: $([ $NUMEROLOGY_RESULT -eq 0 ] && echo "✓ PASS" || echo "✗ FAIL")"
echo "Astrology: $([ $ASTRO_RESULT -eq 0 ] && echo "✓ PASS" || echo "✗ FAIL")"
echo "Ayurveda: $([ $AYURVEDA_RESULT -eq 0 ] && echo "✓ PASS" || echo "✗ FAIL")"
echo "Human Design: $([ $HD_RESULT -eq 0 ] && echo "✓ PASS" || echo "✗ FAIL")"
echo "Home Feature: $([ $HOME_RESULT -eq 0 ] && echo "✓ PASS" || echo "✗ FAIL")"
echo "Compatibility: $([ $COMPAT_RESULT -eq 0 ] && echo "✓ PASS" || echo "✗ FAIL")"
echo "Data Layer: $([ $DATA_RESULT -eq 0 ] && echo "✓ PASS" || echo "✗ FAIL")"

TOTAL_FAILURES=$((NUMEROLOGY_RESULT + ASTRO_RESULT + AYURVEDA_RESULT + HD_RESULT + HOME_RESULT + COMPAT_RESULT + DATA_RESULT))

if [ $TOTAL_FAILURES -eq 0 ]; then
    echo ""
    echo "All tests passed! ✓"
    exit 0
else
    echo ""
    echo "Some tests failed. Check output above."
    exit 1
fi
```

---

## Category 8: Accessibility

### 8.1 Content Description Check

```bash
#!/bin/bash
# Check for missing contentDescription

echo "=== CONTENT DESCRIPTION CHECK ==="

# Find Image/Icon composables without contentDescription
MISSING_DESCRIPTIONS=$(grep -rE "Image\(|Icon\(" --include="*.kt" feature/ app/src/main/java/com/spiritatlas/app --exclude-dir=build -A 3 | grep -v "contentDescription" | grep -c "Image\(\\|Icon\(")

echo "Potential missing contentDescription: $MISSING_DESCRIPTIONS"

if [ "$MISSING_DESCRIPTIONS" -eq 0 ]; then
    SCORE=3.0
elif [ "$MISSING_DESCRIPTIONS" -le 10 ]; then
    SCORE=2.0
elif [ "$MISSING_DESCRIPTIONS" -le 30 ]; then
    SCORE=1.0
else
    SCORE=0
fi

echo "Score: $SCORE / 3.0"
```

### 8.2 Touch Target Size Check

```bash
#!/bin/bash
# Check for small touch targets

echo "=== TOUCH TARGET SIZE CHECK ==="

# Find size modifiers less than 48dp
SMALL_TARGETS=$(grep -rE "\.size\s*\(\s*[1-3][0-9]?\.dp\s*\)" --include="*.kt" feature/ app/src/main/java/com/spiritatlas/app --exclude-dir=build | wc -l | tr -d ' ')

echo "Potentially small touch targets: $SMALL_TARGETS"

if [ "$SMALL_TARGETS" -eq 0 ]; then
    SCORE=1.5
elif [ "$SMALL_TARGETS" -le 10 ]; then
    SCORE=1.0
else
    SCORE=0.5
fi

echo "Score: $SCORE / 1.5"
```

---

## Category 9: Android Standards

### 9.1 Lint Check

```bash
#!/bin/bash
# Run Android Lint

echo "=== ANDROID LINT CHECK ==="

./gradlew lint

LINT_REPORT="app/build/reports/lint-results.html"

if [ -f "$LINT_REPORT" ]; then
    echo "Lint report generated: $LINT_REPORT"

    # Count errors and warnings (simplified)
    ERRORS=$(grep -c "priority=\"1\"" "$LINT_REPORT" 2>/dev/null || echo "0")
    WARNINGS=$(grep -c "priority=\"2\"" "$LINT_REPORT" 2>/dev/null || echo "0")

    echo "Errors: $ERRORS"
    echo "Warnings: $WARNINGS"

    if [ "$ERRORS" -eq 0 ] && [ "$WARNINGS" -lt 10 ]; then
        echo "Lint check: EXCELLENT"
    elif [ "$ERRORS" -eq 0 ] && [ "$WARNINGS" -lt 50 ]; then
        echo "Lint check: GOOD"
    else
        echo "Lint check: NEEDS IMPROVEMENT"
    fi
else
    echo "Lint report not found"
fi

open "$LINT_REPORT"
```

### 9.2 Build Configuration Check

```bash
#!/bin/bash
# Verify build configuration

echo "=== BUILD CONFIGURATION CHECK ==="

# Check versions from libs.versions.toml
KOTLIN_VERSION=$(grep "^kotlin = " gradle/libs.versions.toml | awk -F'"' '{print $2}')
AGP_VERSION=$(grep "^agp = " gradle/libs.versions.toml | awk -F'"' '{print $2}')
MIN_SDK=$(grep "minSdk = " app/build.gradle.kts | awk '{print $3}')
TARGET_SDK=$(grep "targetSdk = " app/build.gradle.kts | awk '{print $3}')
COMPILE_SDK=$(grep "compileSdk = " app/build.gradle.kts | awk '{print $3}')

echo "Kotlin version: $KOTLIN_VERSION"
echo "AGP version: $AGP_VERSION"
echo "MinSDK: $MIN_SDK"
echo "TargetSDK: $TARGET_SDK"
echo "CompileSDK: $COMPILE_SDK"

# Verify optimal configuration
SCORE=1.5
if [ "$MIN_SDK" -lt 26 ]; then
    echo "⚠ MinSDK below recommended (26)"
    SCORE=$(echo "$SCORE - 0.2" | bc)
fi

if [ "$TARGET_SDK" -lt 34 ]; then
    echo "⚠ TargetSDK below recommended (34)"
    SCORE=$(echo "$SCORE - 0.3" | bc)
fi

echo "Score: $SCORE / 1.5"
```

---

## Comprehensive Health Check Script

**Master Script:** `scripts/full_health_check.sh`

```bash
#!/bin/bash
# Comprehensive SpiritAtlas Health Check
# Runs all automated tests and generates health report

echo "╔══════════════════════════════════════════════════════════╗"
echo "║     SpiritAtlas Comprehensive Health Check              ║"
echo "╚══════════════════════════════════════════════════════════╝"
echo ""

REPORT_FILE="health_report_$(date +%Y%m%d_%H%M%S).txt"

echo "Generating health report: $REPORT_FILE"
echo ""

{
    echo "SpiritAtlas Health Report"
    echo "Generated: $(date)"
    echo "================================================"
    echo ""

    # Category 1: Visual Excellence
    echo "CATEGORY 1: VISUAL EXCELLENCE"
    ./scripts/check_image_assets.sh
    echo ""
    ./scripts/check_color_consistency.sh
    echo ""

    # Category 2: Performance
    echo "CATEGORY 2: PERFORMANCE"
    ./scripts/measure_startup.sh
    echo ""

    # Category 3: Code Quality
    echo "CATEGORY 3: CODE QUALITY"
    ./scripts/check_architecture.sh
    echo ""
    ./scripts/check_technical_debt.sh
    echo ""

    # Category 7: Testing
    echo "CATEGORY 7: TESTING"
    ./gradlew test --quiet
    echo "Tests completed"
    echo ""

    # Category 8: Accessibility
    echo "CATEGORY 8: ACCESSIBILITY"
    ./scripts/check_content_descriptions.sh
    echo ""

    # Category 9: Android Standards
    echo "CATEGORY 9: ANDROID STANDARDS"
    ./gradlew lint --quiet
    echo "Lint completed"
    echo ""

    echo "================================================"
    echo "Health check complete!"
    echo "Review $REPORT_FILE for full results"

} | tee "$REPORT_FILE"

echo ""
echo "Report saved to: $REPORT_FILE"
```

---

## CI/CD Integration

### GitHub Actions Workflow

```yaml
# .github/workflows/health_check.yml
name: SpiritAtlas Health Check

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]
  schedule:
    - cron: '0 0 * * 0'  # Weekly on Sunday

jobs:
  health-check:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v3

    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'

    - name: Cache Gradle packages
      uses: actions/cache@v3
      with:
        path: ~/.gradle/caches
        key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*') }}

    - name: Run Unit Tests
      run: ./gradlew test

    - name: Run Lint
      run: ./gradlew lint

    - name: Generate Test Coverage
      run: ./gradlew jacocoTestReport

    - name: Upload Coverage Reports
      uses: codecov/codecov-action@v3
      with:
        files: ./core/*/build/reports/jacoco/test/jacocoTestReport.xml

    - name: Check Architecture
      run: ./scripts/check_architecture.sh

    - name: Check Technical Debt
      run: ./scripts/check_technical_debt.sh

    - name: Generate Health Report
      run: ./scripts/full_health_check.sh

    - name: Upload Health Report
      uses: actions/upload-artifact@v3
      with:
        name: health-report
        path: health_report_*.txt
```

---

## Performance Profiling Scripts

### Android Studio Profiler Commands

```bash
# Start profiling session
adb shell am start com.spiritatlas.app/.MainActivity

# CPU Profiling
adb shell am profile start com.spiritatlas.app /sdcard/spiritatlas.trace
# Use app for 30 seconds
adb shell am profile stop com.spiritatlas.app
adb pull /sdcard/spiritatlas.trace .

# Memory Profiling
adb shell dumpsys meminfo com.spiritatlas.app > memory_report.txt

# Network Profiling
adb shell tcpdump -i any -w /sdcard/network.pcap
# Use app
adb shell killall tcpdump
adb pull /sdcard/network.pcap .
```

---

## Maintenance Schedule

### Daily (Automated)
- Run unit tests on commit
- Check lint warnings
- Verify build success

### Weekly (Automated)
- Full test suite with coverage
- Performance benchmarks
- Dependency vulnerability scan
- Generate health report

### Monthly (Manual)
- Complete health audit
- Accessibility testing with real users
- Performance profiling
- Update scoring criteria if needed

---

## Troubleshooting

### Tests Failing
```bash
# Clean build and retry
./gradlew clean
./gradlew test --rerun-tasks

# Check for flaky tests
./gradlew test --tests "*TestName" --info
```

### Coverage Reports Not Generating
```bash
# Ensure Jacoco plugin configured
# Check build.gradle.kts for jacoco plugin

# Generate manually
./gradlew jacocoTestReport --stacktrace
```

### Lint Issues
```bash
# View detailed lint report
./gradlew lint
open app/build/reports/lint-results.html

# Fix auto-fixable issues
./gradlew lintFix
```

---

## Tools & Dependencies

### Required Tools
- Android Studio (latest stable)
- JDK 17
- Android SDK (API 26-35)
- Gradle 8.13+

### Optional Tools
- LeakCanary (memory leak detection)
- Android Profiler (performance analysis)
- Scrcpy (device mirroring for manual testing)
- Battery Historian (battery analysis)

---

**Created by:** Claude Code (Anthropic)
**For:** SpiritAtlas Android Application
**Version:** 1.0
**Last Updated:** 2025-12-09
