# Coverage Scripts

This directory contains scripts for managing test coverage in the SpiritAtlas project.

## Scripts

### check_coverage.sh
**Quick coverage check and dashboard generation**

```bash
# Run and display coverage
./scripts/coverage/check_coverage.sh

# Run with verification (fail if < 80%)
./scripts/coverage/check_coverage.sh --verify
```

**What it does:**
1. Runs all core module tests
2. Generates JaCoCo coverage reports
3. Parses coverage data
4. Generates HTML dashboard
5. Optionally verifies 80% threshold

### generate_report.sh
**Comprehensive coverage report generation**

```bash
./scripts/coverage/generate_report.sh [--verify]
```

**What it does:**
1. Runs tests for all modules (JVM + Android)
2. Generates JaCoCo reports for each module
3. Creates unified coverage report
4. Displays coverage summary table
5. Shows individual module statistics

### parse_coverage.py
**Parse JaCoCo XML reports to JSON**

```bash
python3 scripts/coverage/parse_coverage.py
```

**Output:**
- `build/reports/coverage_summary.json` - Coverage statistics in JSON format
- Console output with coverage table

**Features:**
- Parses all module JaCoCo XML reports
- Calculates overall and per-module coverage
- Identifies files with < 80% coverage
- Color-coded console output
- Exits with error if coverage < 80%

### generate_dashboard.py
**Generate HTML coverage dashboard**

```bash
python3 scripts/coverage/generate_dashboard.py
```

**Output:**
- `build/reports/coverage_dashboard.html` - Interactive HTML dashboard

**Features:**
- Visual coverage statistics
- Bar charts for each module
- Detailed module table
- Color-coded status indicators
- Responsive design
- Dark theme UI

## Usage Examples

### Quick Check
```bash
./scripts/coverage/check_coverage.sh
```

### Pre-Commit Verification
```bash
./scripts/coverage/check_coverage.sh --verify || exit 1
```

### CI/CD Pipeline
```bash
# In .github/workflows/test.yml
- name: Check Coverage
  run: |
    ./scripts/coverage/check_coverage.sh --verify
    cat build/reports/coverage_summary.json
```

### Manual Analysis
```bash
# Generate reports
./gradlew test jacocoTestReport

# Parse and analyze
python3 scripts/coverage/parse_coverage.py

# View specific module
open core/astro/build/reports/jacoco/test/html/index.html
```

## Requirements

### Shell Scripts
- Bash 4.0+
- Python 3.7+
- Gradle 8.13+

### Python Scripts
- Python 3.7+
- Standard library only (no external dependencies)

## Output Files

### Generated Reports
```
build/reports/
├── coverage_dashboard.html     # Interactive HTML dashboard
├── coverage_summary.json       # JSON coverage data
└── jacoco/
    └── testCoverageReport/     # Unified JaCoCo report
        ├── html/
        │   └── index.html
        └── jacocoTestReport.xml
```

### Module Reports
```
[module]/build/reports/jacoco/
├── test/                       # JVM modules
│   ├── html/
│   │   └── index.html
│   └── jacocoTestReport.xml
└── jacocoTestReport/          # Android modules
    ├── html/
    │   └── index.html
    └── jacocoTestReport.xml
```

## Configuration

### Coverage Thresholds
Defined in each module's `build.gradle.kts`:

```kotlin
tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.80".toBigDecimal()
            }
        }
    }
}
```

### Exclusions
Common exclusions (Android-generated code, DI, etc.):
- `**/R.class`, `**/BuildConfig.*`
- `**/*_Factory.*`, `**/Hilt_*.*`
- `**/databinding/**`, `**/generated/**`

See individual module build files for full exclusion lists.

## Troubleshooting

### "No coverage reports found"
```bash
# Clean and rebuild
./gradlew clean test
./scripts/coverage/check_coverage.sh
```

### "Coverage below 80%"
```bash
# See detailed breakdown
python3 scripts/coverage/parse_coverage.py

# View HTML report
open build/reports/coverage_dashboard.html
```

### "Python not found"
```bash
# Check Python version
python3 --version

# Ensure Python 3.7+ is installed
brew install python3  # macOS
```

### "Permission denied"
```bash
# Make scripts executable
chmod +x scripts/coverage/*.sh
```

## Integration

### Git Hooks
Add to `.git/hooks/pre-commit`:
```bash
#!/bin/bash
./scripts/coverage/check_coverage.sh --verify
```

### GitHub Actions
```yaml
name: Coverage
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-java@v3
        with:
          java-version: '17'
      - name: Check Coverage
        run: ./scripts/coverage/check_coverage.sh --verify
      - name: Upload Dashboard
        uses: actions/upload-artifact@v3
        with:
          name: coverage-dashboard
          path: build/reports/coverage_dashboard.html
```

### Gradle Integration
Use the built-in Gradle tasks:
```bash
./gradlew verifyCoverage
./gradlew coverageDashboard
```

## Development

### Adding New Modules
1. Add JaCoCo configuration to module's `build.gradle.kts`
2. Add module to `check_coverage.sh` and `generate_report.sh`
3. Update `parse_coverage.py` to include module path
4. Test coverage generation

### Modifying Scripts
- **Shell scripts**: Follow ShellCheck recommendations
- **Python scripts**: Follow PEP 8 style guide
- **Test changes**: Run on all modules before committing

## Documentation

- **Full Report**: [docs/TEST_COVERAGE_REPORT.md](../../docs/TEST_COVERAGE_REPORT.md)
- **Quick Start**: [docs/COVERAGE_QUICK_START.md](../../docs/COVERAGE_QUICK_START.md)
- **Project Guidelines**: [CLAUDE.md](../../CLAUDE.md)

## Support

For issues or questions:
1. Check documentation in `docs/`
2. Review script comments
3. Examine JaCoCo reports for details
4. Check Gradle build logs

---

**Last Updated**: 2025-12-10
**Maintained by**: Agent 10 - Test Coverage Monitor
