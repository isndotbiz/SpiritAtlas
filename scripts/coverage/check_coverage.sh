#!/bin/bash
#
# Quick coverage check script
#
# Usage:
#   ./scripts/coverage/check_coverage.sh          # Run and display coverage
#   ./scripts/coverage/check_coverage.sh --verify # Also enforce 80% threshold
#

set -e

cd "$(dirname "$0")/../.."

echo "🧪 Running tests..."
./gradlew :core:numerology:test :core:astro:test :core:ayurveda:test :core:humandesign:test --quiet

echo "📊 Generating coverage reports..."
./gradlew :core:numerology:jacocoTestReport :core:astro:jacocoTestReport :core:ayurveda:jacocoTestReport :core:humandesign:jacocoTestReport --quiet

echo "📈 Analyzing coverage..."
python3 scripts/coverage/parse_coverage.py

echo "🎨 Generating dashboard..."
python3 scripts/coverage/generate_dashboard.py

echo ""
echo "✅ Coverage check complete!"
echo ""
echo "View reports:"
echo "  Dashboard: file://$(pwd)/build/reports/coverage_dashboard.html"
echo "  Summary:   cat build/reports/coverage_summary.json"
echo ""

if [ "$1" == "--verify" ]; then
    echo "🔍 Running coverage verification..."
    ./gradlew :core:numerology:jacocoTestCoverageVerification :core:astro:jacocoTestCoverageVerification :core:ayurveda:jacocoTestCoverageVerification :core:humandesign:jacocoTestCoverageVerification --quiet
    echo "✅ All modules meet 80% threshold!"
fi
