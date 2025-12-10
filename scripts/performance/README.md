# Performance Testing Scripts

Automated performance testing suite for SpiritAtlas.

## Quick Start

```bash
# Run all tests
./run_all_tests.sh
```

## Individual Tests

### Cold Start Test
Measures app startup time from cold launch.

```bash
./cold_start_test.sh
```

**Output**:
- Average startup time across 10 runs
- Min/Max values
- Pass/Fail against <1000ms target

### Memory Test
Detects memory leaks through usage cycles.

```bash
./memory_test.sh
```

**Output**:
- Initial memory usage
- Final memory usage after 5 navigation cycles
- Memory growth detection
- Leak warning if growth >15MB

### APK Size Test
Analyzes release APK size and breakdown.

```bash
./apk_size_test.sh
```

**Output**:
- Total APK size
- Breakdown by component (res/, dex, lib/)
- Largest files
- App Bundle size

## Requirements

- Android device or emulator connected
- ADB installed and in PATH
- SpiritAtlas debug/release APK installed

## CI/CD Integration

Add to GitHub Actions:

```yaml
- name: Performance Tests
  run: |
    chmod +x scripts/performance/*.sh
    ./scripts/performance/run_all_tests.sh
```

## Troubleshooting

**No device found**:
```bash
adb devices
# Connect device or start emulator
```

**App not installed**:
```bash
./gradlew installDebug
# or
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

**Permission denied**:
```bash
chmod +x scripts/performance/*.sh
```

## Performance Targets

| Metric | Target |
|--------|--------|
| Cold Start | <1000ms |
| Memory Usage | <80MB |
| Memory Leak | <15MB growth |
| APK Size | <12MB |

## More Information

- Full guide: `/PERFORMANCE_TESTING_GUIDE.md`
- Benchmarks: `/PERFORMANCE_BENCHMARKS.md`
- Optimization plan: `/PERFORMANCE_OPTIMIZATION_PLAN.md`
