# Test Coverage Quick Start Guide

## Quick Commands

### Check Coverage
```bash
# Quick coverage check
./scripts/coverage/check_coverage.sh

# Check with verification (fails if < 80%)
./scripts/coverage/check_coverage.sh --verify

# Using Gradle
./gradlew coverageDashboard
```

### View Reports
```bash
# Open HTML dashboard
open build/reports/coverage_dashboard.html

# View JSON summary
cat build/reports/coverage_summary.json | python3 -m json.tool

# View individual module reports
open core/numerology/build/reports/jacoco/test/html/index.html
```

## Current Status

✅ **100% Line Coverage** across all core modules
- core:numerology - 100% (14 tests)
- core:astro - 100% (83 tests)
- core:ayurveda - 100% (6 tests)
- core:humandesign - 100% (10 tests)

## Adding New Tests

1. Create test file in `src/test/java`
2. Write tests using JUnit 5
3. Run coverage check
4. Ensure module maintains ≥80% coverage

## Gradle Tasks

| Task | Command | Purpose |
|------|---------|---------|
| **Run All Tests** | `./gradlew test` | Execute all unit tests |
| **Generate Coverage** | `./gradlew jacocoTestReport` | Create coverage reports |
| **Verify Coverage** | `./gradlew verifyCoverage` | Check 80% threshold |
| **Full Dashboard** | `./gradlew coverageDashboard` | Generate HTML dashboard |

## File Locations

- **Dashboard**: `build/reports/coverage_dashboard.html`
- **JSON Summary**: `build/reports/coverage_summary.json`
- **Module HTML**: `[module]/build/reports/jacoco/test/html/index.html`
- **Module XML**: `[module]/build/reports/jacoco/test/jacocoTestReport.xml`

## Troubleshooting

### No reports found
```bash
./gradlew clean test
```

### Coverage below 80%
```bash
# See which files need tests
python3 scripts/coverage/parse_coverage.py
```

### Dashboard won't generate
```bash
# Ensure Python 3 is installed
python3 --version

# Install requirements if needed
pip3 install -r requirements.txt  # if exists
```

## CI/CD Integration

Add to your CI pipeline:
```yaml
- name: Check Coverage
  run: ./scripts/coverage/check_coverage.sh --verify
```

## More Information

See [TEST_COVERAGE_REPORT.md](TEST_COVERAGE_REPORT.md) for comprehensive documentation.
