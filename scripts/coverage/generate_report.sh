#!/bin/bash
#
# Generate comprehensive test coverage report for SpiritAtlas
#
# This script:
# 1. Runs all unit tests across all modules
# 2. Generates JaCoCo coverage reports
# 3. Creates a unified HTML report
# 4. Displays coverage summary
#
# Usage:
#   ./scripts/coverage/generate_report.sh [--verify]
#
# Options:
#   --verify    Also run coverage verification (fail if < 80%)
#

set -e

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${BLUE}═══════════════════════════════════════════════════════════════${NC}"
echo -e "${BLUE}  SpiritAtlas Test Coverage Report Generator${NC}"
echo -e "${BLUE}═══════════════════════════════════════════════════════════════${NC}"
echo

# Change to project root
cd "$(dirname "$0")/../.."

# Define modules
JVM_MODULES=(
    ":core:numerology"
    ":core:astro"
    ":core:ayurveda"
    ":core:humandesign"
    ":domain"
)

ANDROID_MODULES=(
    ":data"
    ":feature:compatibility"
    ":feature:home"
    ":feature:profile"
    ":feature:settings"
)

# Step 1: Run tests
echo -e "${YELLOW}Step 1: Running tests...${NC}"
echo

# Run JVM module tests
for module in "${JVM_MODULES[@]}"; do
    echo -e "  ${BLUE}Testing${NC} $module"
    ./gradlew $module:test --quiet || {
        echo -e "${RED}Tests failed for $module${NC}"
        exit 1
    }
done

# Run Android module tests
for module in "${ANDROID_MODULES[@]}"; do
    echo -e "  ${BLUE}Testing${NC} $module"
    ./gradlew $module:testDebugUnitTest --quiet --continue || true
done

echo -e "${GREEN}✓ All tests completed${NC}"
echo

# Step 2: Generate JaCoCo reports
echo -e "${YELLOW}Step 2: Generating coverage reports...${NC}"
echo

# Generate reports for JVM modules
for module in "${JVM_MODULES[@]}"; do
    echo -e "  ${BLUE}Generating report for${NC} $module"
    ./gradlew $module:jacocoTestReport --quiet
done

# Generate reports for Android modules
for module in "${ANDROID_MODULES[@]}"; do
    echo -e "  ${BLUE}Generating report for${NC} $module"
    ./gradlew $module:jacocoTestReport --quiet --continue || true
done

echo -e "${GREEN}✓ Coverage reports generated${NC}"
echo

# Step 3: Generate unified report
echo -e "${YELLOW}Step 3: Generating unified coverage report...${NC}"
./gradlew testCoverageReport --quiet
echo -e "${GREEN}✓ Unified report generated${NC}"
echo

# Step 4: Extract coverage statistics
echo -e "${BLUE}═══════════════════════════════════════════════════════════════${NC}"
echo -e "${BLUE}  Coverage Summary${NC}"
echo -e "${BLUE}═══════════════════════════════════════════════════════════════${NC}"
echo

# Parse coverage from XML reports
parse_coverage() {
    local module=$1
    local xml_file=""

    # Determine XML report path
    if [[ " ${JVM_MODULES[@]} " =~ " $module " ]]; then
        xml_file="$(echo $module | tr ':' '/')/build/reports/jacoco/test/jacocoTestReport.xml"
    else
        xml_file="$(echo $module | tr ':' '/')/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"
    fi

    if [ -f "$xml_file" ]; then
        # Extract instruction coverage
        local line=$(grep -m 1 '<counter type="LINE"' "$xml_file" || echo "")
        if [ -n "$line" ]; then
            local covered=$(echo "$line" | sed -n 's/.*covered="\([0-9]*\)".*/\1/p')
            local missed=$(echo "$line" | sed -n 's/.*missed="\([0-9]*\)".*/\1/p')
            local total=$((covered + missed))
            if [ $total -gt 0 ]; then
                local percentage=$(awk "BEGIN {printf \"%.1f\", ($covered / $total) * 100}")
                echo "$percentage% ($covered/$total lines)"
            else
                echo "N/A (no code)"
            fi
        else
            echo "N/A"
        fi
    else
        echo "No report"
    fi
}

# Core modules
echo -e "${YELLOW}Core Modules:${NC}"
for module in "${JVM_MODULES[@]}"; do
    coverage=$(parse_coverage "$module")
    module_name=$(echo $module | sed 's/:/ /g' | awk '{print $NF}')
    printf "  %-20s %s\n" "$module_name" "$coverage"
done

echo
echo -e "${YELLOW}Feature Modules:${NC}"
for module in "${ANDROID_MODULES[@]}"; do
    coverage=$(parse_coverage "$module")
    module_name=$(echo $module | sed 's/:/ /g' | awk '{print $NF}')
    printf "  %-20s %s\n" "$module_name" "$coverage"
done

echo
echo -e "${BLUE}═══════════════════════════════════════════════════════════════${NC}"
echo

# Step 5: Display report locations
echo -e "${YELLOW}Coverage Reports:${NC}"
echo
echo -e "  ${GREEN}Unified HTML Report:${NC}"
echo -e "    file://$(pwd)/build/reports/jacoco/testCoverageReport/html/index.html"
echo

echo -e "  ${GREEN}Module Reports:${NC}"
for module in "${JVM_MODULES[@]}"; do
    module_path=$(echo $module | tr ':' '/')
    echo -e "    $module: file://$(pwd)/$module_path/build/reports/jacoco/test/html/index.html"
done

echo

# Step 6: Optional verification
if [ "$1" == "--verify" ]; then
    echo -e "${YELLOW}Step 6: Running coverage verification...${NC}"
    echo

    failed_modules=()

    for module in "${JVM_MODULES[@]}"; do
        echo -e "  ${BLUE}Verifying${NC} $module"
        if ! ./gradlew $module:jacocoTestCoverageVerification --quiet 2>&1; then
            failed_modules+=("$module")
        fi
    done

    if [ ${#failed_modules[@]} -eq 0 ]; then
        echo
        echo -e "${GREEN}✓ All modules meet 80% coverage threshold${NC}"
    else
        echo
        echo -e "${RED}✗ The following modules do not meet 80% coverage:${NC}"
        for module in "${failed_modules[@]}"; do
            echo -e "${RED}  - $module${NC}"
        done
        exit 1
    fi
fi

echo
echo -e "${GREEN}═══════════════════════════════════════════════════════════════${NC}"
echo -e "${GREEN}  Coverage report generation complete!${NC}"
echo -e "${GREEN}═══════════════════════════════════════════════════════════════${NC}"
echo
