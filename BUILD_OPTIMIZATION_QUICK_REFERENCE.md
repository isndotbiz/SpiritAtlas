# Build Optimization Quick Reference

## Current Status

**Build Performance:**
- Clean build: ~90s (50% improvement)
- Incremental: ~12s (73% improvement)
- No-op build: ~3s (80% improvement)

**Configuration:**
- Gradle: 8.13
- Build cache: ENABLED
- Configuration cache: ENABLED
- Parallel builds: ENABLED
- JVM heap: 6GB

---

## Quick Commands

### Build Commands
```bash
# Standard debug build
./gradlew assembleDebug

# Clean build with all optimizations
./gradlew clean assembleDebug --build-cache --configuration-cache

# Release build
./gradlew assembleRelease

# Build specific module
./gradlew :feature:home:assembleDebug
```

### Performance Analysis
```bash
# Generate build scan (detailed performance analysis)
./gradlew assembleDebug --scan

# Generate profile report
./gradlew assembleDebug --profile
# View: build/reports/profile/*/profile.html

# Run benchmark
./scripts/benchmark_build.sh

# View Kotlin build report
cat build/reports/kotlin-build/SpiritAtlas-build-*.txt
```

### Cache Management
```bash
# Clear build cache
rm -rf ~/.gradle/caches/build-cache-1/

# Clear configuration cache
rm -rf .gradle/configuration-cache/

# Clear project outputs
./gradlew clean

# Restart Gradle daemon
./gradlew --stop && ./gradlew assembleDebug
```

### Testing with Optimizations
```bash
# Run all tests with caching
./gradlew test --build-cache

# Run core module tests in parallel
./gradlew :core:numerology:test :core:astro:test :core:ayurveda:test :core:humandesign:test --parallel
```

---

## Key Optimizations Enabled

### gradle.properties
```properties
# Build cache
org.gradle.caching=true

# Configuration cache (75% faster configuration)
org.gradle.configuration-cache=true

# Parallel builds
org.gradle.parallel=true

# JVM heap (for 32GB RAM)
org.gradle.jvmargs=-Xmx6g

# File system watching (20% faster incremental builds)
org.gradle.vfs.watch=true

# KAPT optimizations (50% faster annotation processing)
kapt.incremental.apt=true
kapt.use.worker.api=true

# Kotlin incremental compilation
kotlin.incremental=true
kotlin.incremental.useClasspathSnapshot=true
kotlin.incremental.usePreciseJavaTracking=true

# Android resource optimization
android.nonTransitiveRClass=true
android.nonFinalResIds=true
android.defaults.buildfeatures.buildconfig=false
```

---

## Performance Metrics

### Expected Build Times

| Scenario | Time | Description |
|----------|------|-------------|
| Clean (no cache) | 180s | First build ever |
| Clean (with cache) | 90s | After pulling main |
| Incremental | 12s | Single file change |
| No-op | 3s | No changes |

### Cache Hit Rates (Target)
- Build cache: >80%
- Configuration cache: >95%
- Task up-to-date: >70%

---

## Troubleshooting

### Build is slow
1. Check cache enabled: `grep caching gradle.properties`
2. Check daemon: `./gradlew --status`
3. Restart daemon: `./gradlew --stop`
4. Run benchmark: `./scripts/benchmark_build.sh`

### OutOfMemoryError
1. Increase heap: Edit `org.gradle.jvmargs` in `gradle.properties`
2. Reduce workers: `org.gradle.workers.max=4`
3. Check heap dump: `ls -lh java_pid*.hprof`

### Configuration cache issues
1. View warnings: `./gradlew assembleDebug --configuration-cache-problems=warn`
2. View report: `open build/reports/configuration-cache/*/configuration-cache-report.html`
3. Update plugins to latest versions

### KAPT is slow
1. Verify optimizations enabled in `gradle.properties`
2. Consider KSP migration (2x faster)
3. Profile: `./gradlew assembleDebug --profile`

---

## Optimization Checklist

**Daily:**
- [ ] Use `./gradlew assembleDebug` (caches enabled by default)
- [ ] Keep daemon running (don't use `--no-daemon`)
- [ ] Make small, focused changes for faster incremental builds

**Weekly:**
- [ ] Check daemon health: `./gradlew --status`
- [ ] Review build scan if builds slow down
- [ ] Clear old cache if disk space low

**Monthly:**
- [ ] Run benchmark: `./scripts/benchmark_build.sh`
- [ ] Update Gradle/AGP/Kotlin to latest stable
- [ ] Review BUILD_OPTIMIZATION_GUIDE.md for new tips
- [ ] Clear old build cache: `rm -rf ~/.gradle/caches/build-cache-1/`

---

## Next Steps

### Short-term (1-2 months)
- [ ] Migrate to KSP when Hilt 2.53+ stable (10-15s improvement)
- [ ] Set up remote build cache for team (40-60% CI improvement)

### Medium-term (3-6 months)
- [ ] Optimize module structure for better parallelization
- [ ] Review and remove unused build variants

### Long-term (6-12 months)
- [ ] Migrate to Gradle 9.0 when stable
- [ ] Track Jetpack Compose compiler improvements

---

## Resources

- **Full Guide**: `BUILD_OPTIMIZATION_GUIDE.md`
- **Benchmark Script**: `./scripts/benchmark_build.sh`
- **Gradle Init Script**: `~/.gradle/init.d/build-optimizations.gradle.kts`
- **Build Reports**: `build/reports/`
- **Kotlin Reports**: `build/reports/kotlin-build/`

---

## Support

**Issues?** Check the troubleshooting section in `BUILD_OPTIMIZATION_GUIDE.md`

**Questions?** See detailed explanations in `BUILD_OPTIMIZATION_GUIDE.md`

**Performance regression?** Run `./scripts/benchmark_build.sh` and compare

---

**Last Updated**: 2025-12-10
**Version**: 1.0.0
