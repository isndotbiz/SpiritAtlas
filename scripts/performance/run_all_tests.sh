#!/bin/bash
# Comprehensive Performance Test Suite for SpiritAtlas

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_ROOT="$SCRIPT_DIR/../.."

echo ""
echo "╔═══════════════════════════════════════════╗"
echo "║  SPIRITATLAS PERFORMANCE TEST SUITE      ║"
echo "╚═══════════════════════════════════════════╝"
echo ""
echo "Location: $PROJECT_ROOT"
echo "Date: $(date '+%Y-%m-%d %H:%M:%S')"
echo ""

# Change to project root
cd "$PROJECT_ROOT"

TESTS_PASSED=0
TESTS_FAILED=0
TESTS_WARNING=0

# Function to run a test
run_test() {
  TEST_NAME=$1
  TEST_SCRIPT=$2

  echo ""
  echo "┌────────────────────────────────────────────┐"
  echo "│ $TEST_NAME"
  echo "└────────────────────────────────────────────┘"

  # Make script executable
  chmod +x "$SCRIPT_DIR/$TEST_SCRIPT"

  # Run test
  if bash "$SCRIPT_DIR/$TEST_SCRIPT"; then
    TESTS_PASSED=$((TESTS_PASSED + 1))
  elif [ $? -eq 1 ]; then
    TESTS_WARNING=$((TESTS_WARNING + 1))
  else
    TESTS_FAILED=$((TESTS_FAILED + 1))
  fi
}

# Run all performance tests
run_test "APK Size Analysis" "apk_size_test.sh"

# Only run device tests if device is connected
if adb devices | grep -q "device$"; then
  run_test "Cold Start Performance" "cold_start_test.sh"
  run_test "Memory Usage & Leak Detection" "memory_test.sh"
else
  echo ""
  echo "⚠️  Skipping device tests (no device connected)"
  echo "   Connect a device or emulator to run full suite"
fi

# Summary
TOTAL_TESTS=$((TESTS_PASSED + TESTS_WARNING + TESTS_FAILED))

echo ""
echo "╔═══════════════════════════════════════════╗"
echo "║  TEST SUITE SUMMARY                       ║"
echo "╚═══════════════════════════════════════════╝"
echo ""
echo "Total Tests:  $TOTAL_TESTS"
echo "Passed:       $TESTS_PASSED ✅"
echo "Warnings:     $TESTS_WARNING ⚠️"
echo "Failed:       $TESTS_FAILED ❌"
echo ""

if [ $TESTS_FAILED -eq 0 ] && [ $TESTS_WARNING -eq 0 ]; then
  echo "═══════════════════════════════════════════"
  echo "         ✅ ALL TESTS PASSED!"
  echo "═══════════════════════════════════════════"
  exit 0
elif [ $TESTS_FAILED -eq 0 ]; then
  echo "═══════════════════════════════════════════"
  echo "      ⚠️  SOME TESTS NEED ATTENTION"
  echo "═══════════════════════════════════════════"
  exit 1
else
  echo "═══════════════════════════════════════════"
  echo "         ❌ TESTS FAILED"
  echo "═══════════════════════════════════════════"
  exit 1
fi
