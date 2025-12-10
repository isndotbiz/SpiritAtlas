# Build Performance Quick Start Guide

Quick reference for optimized build commands and troubleshooting.

## Quick Commands

### Daily Development
```bash
# Fast incremental build
./gradlew :app:assembleDebug

# Run core tests
./gradlew :core:numerology:test :core:astro:test :core:ayurveda:test :core:humandesign:test

# Build with performance profile
./gradlew assembleDebug --profile --scan
```

### Troubleshooting

```bash
# Clean build (when things break)
./gradlew clean assembleDebug

# Restart Gradle daemon (weekly)
./gradlew --stop

# Clear build cache (if corrupted)
rm -rf .gradle/build-cache && ./gradlew assembleDebug

# Disable configuration cache (if issues)
./gradlew assembleDebug --no-configuration-cache
```

### Performance Analysis

```bash
# Full performance report
./gradlew assembleDebug --profile --build-cache --scan

# View build reports
open build/reports/profile/profile-*.html

# View Kotlin build report
cat build/reports/kotlin-build/*.txt
```

## Key Settings

### gradle.properties Highlights
- **Heap Size:** 8GB (Xmx8g)
- **Parallel Workers:** 8 threads
- **Build Cache:** 30-day retention
- **Configuration Cache:** Enabled
- **Kapt Optimization:** Worker API + incremental

### Expected Build Times
- **Incremental (1 file):** <15s ✅
- **Incremental (module):** <25s ✅
- **Clean build (cached):** <50s ✅
- **Full clean build:** <70s ✅

## Optimization Checklist

Weekly:
- [ ] `./gradlew --stop` (restart daemon)
- [ ] Review build profile reports

Monthly:
- [ ] Clear old cache: `find .gradle/build-cache -mtime +30 -delete`
- [ ] Benchmark builds: `./gradlew clean && ./gradlew assembleDebug --profile`
- [ ] Check for updates: `./gradlew dependencyUpdates`

## Common Issues

### OutOfMemoryError
```bash
# Increase heap in gradle.properties
org.gradle.jvmargs=-Xmx10g
```

### Slow builds after dependency update
```bash
# Clear dependency cache
./gradlew --refresh-dependencies
```

### Configuration cache warnings
```bash
# Build with details
./gradlew assembleDebug --warning-mode=all
```

## See BUILD_PERFORMANCE_REPORT.md for full details
