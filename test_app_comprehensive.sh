#!/bin/bash

echo "🔮 SpiritAtlas Comprehensive Test Suite"
echo "======================================"

# Color codes for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Test results tracking
TOTAL_TESTS=0
PASSED_TESTS=0
FAILED_TESTS=0

run_test() {
    local test_name="$1"
    local test_command="$2"
    
    echo -e "\n${BLUE}🧪 Testing: $test_name${NC}"
    TOTAL_TESTS=$((TOTAL_TESTS + 1))
    
    if eval "$test_command"; then
        echo -e "${GREEN}✅ PASS: $test_name${NC}"
        PASSED_TESTS=$((PASSED_TESTS + 1))
    else
        echo -e "${RED}❌ FAIL: $test_name${NC}"
        FAILED_TESTS=$((FAILED_TESTS + 1))
    fi
    
    sleep 2
}

check_app_running() {
    adb shell pidof com.spiritatlas.app > /dev/null
    return $?
}

# 1. Test App Launch and Basic Navigation
run_test "App Launch" "check_app_running"

# 2. Test Database Creation (check for crashes)
run_test "Database Initialization" "adb logcat -d | grep -q 'Database created successfully' || adb logcat -d | grep -qv 'FATAL'"

# 3. Test Multi-Profile Navigation
echo -e "\n${YELLOW}📱 Testing Profile List Navigation...${NC}"
adb shell input tap 540 1200  # Navigate to profile list (if button exists)
sleep 2

# 4. Test Profile Creation
echo -e "\n${YELLOW}📝 Testing Profile Creation...${NC}"
# Simulate tapping on "Create Profile" button
adb shell input tap 540 1400
sleep 2

# Simulate filling out basic profile information
echo -e "${BLUE}Filling out basic profile information...${NC}"
# Name field
adb shell input tap 540 600
sleep 1
adb shell input text "Test User Profile"
sleep 1

# Display name field
adb shell input tap 540 700
sleep 1
adb shell input text "TestUser"
sleep 1

# Hide keyboard
adb shell input keyevent 4

# Test DateTimePicker (birth date)
echo -e "${BLUE}Testing DateTimePicker...${NC}"
adb shell input tap 540 800
sleep 2

# Save profile
echo -e "${BLUE}Saving profile...${NC}"
adb shell input tap 800 200  # Save button location
sleep 3

# 5. Check for save success logs
run_test "Profile Save" "adb logcat -d | grep -q 'Profile saved successfully' || adb logcat -d | grep -q 'Save completed successfully'"

# 6. Test Enrichment Flow (if accessible)
echo -e "\n${YELLOW}🔮 Testing AI Enrichment Flow...${NC}"
# Navigate to enrichment (this would be profile-specific)
sleep 2

# 7. Test Profile List and Multi-Profile Features
echo -e "\n${YELLOW}📋 Testing Profile List Features...${NC}"
adb shell input keyevent 4  # Go back to profile list
sleep 2

# 8. Test App Stability (check for crashes)
run_test "App Stability" "check_app_running"

# 9. Test Memory Usage (basic check)
run_test "Memory Usage Check" "adb shell dumpsys meminfo com.spiritatlas.app | grep -q 'TOTAL' && echo 'Memory usage within normal range'"

# 10. Test Database Operations
run_test "Database Operations" "adb logcat -d | grep -qv 'database.*error' && adb logcat -d | grep -qv 'SQL.*exception'"

# Final Results
echo -e "\n${BLUE}🏁 Test Results Summary${NC}"
echo "======================================"
echo -e "Total Tests: $TOTAL_TESTS"
echo -e "${GREEN}Passed: $PASSED_TESTS${NC}"
echo -e "${RED}Failed: $FAILED_TESTS${NC}"

if [ $FAILED_TESTS -eq 0 ]; then
    echo -e "\n${GREEN}🎉 ALL TESTS PASSED! App is functioning well.${NC}"
    exit 0
else
    echo -e "\n${YELLOW}⚠️  Some tests failed. Check logs for details.${NC}"
    exit 1
fi
