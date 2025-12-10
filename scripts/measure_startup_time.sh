#!/bin/bash
# Startup Time Measurement Script for SpiritAtlas
# This script measures cold and warm app startup times using ADB and Android Profiler

set -e

# Color codes for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
PACKAGE_NAME="com.spiritatlas.app"
ACTIVITY_NAME="com.spiritatlas.app.MainActivity"
TARGET_COLD_STARTUP="2000" # Target: < 2.0 seconds
TARGET_WARM_STARTUP="1000" # Target: < 1.0 second
NUM_MEASUREMENTS=5

echo -e "${BLUE}=========================================${NC}"
echo -e "${BLUE}SpiritAtlas Startup Time Measurement${NC}"
echo -e "${BLUE}=========================================${NC}"
echo ""

# Check if device is connected
if ! adb devices | grep -q "device$"; then
    echo -e "${RED}Error: No Android device connected${NC}"
    echo "Please connect a device or start an emulator"
    exit 1
fi

# Check if app is installed
if ! adb shell pm list packages | grep -q "$PACKAGE_NAME"; then
    echo -e "${RED}Error: $PACKAGE_NAME is not installed${NC}"
    echo "Run: ./gradlew installDebug"
    exit 1
fi

# Function to measure cold start
measure_cold_start() {
    echo -e "${YELLOW}Measuring Cold Start (iteration $1/$NUM_MEASUREMENTS)${NC}"

    # Force stop the app
    adb shell am force-stop "$PACKAGE_NAME" 2>/dev/null || true

    # Clear app data to simulate first launch
    adb shell pm clear "$PACKAGE_NAME" > /dev/null 2>&1

    # Wait for system to settle
    sleep 2

    # Start activity and capture timing
    local output=$(adb shell am start -W -n "$PACKAGE_NAME/$ACTIVITY_NAME" 2>&1)

    # Extract TotalTime (milliseconds)
    local total_time=$(echo "$output" | grep "TotalTime" | awk '{print $2}')

    if [ -z "$total_time" ]; then
        echo -e "${RED}Failed to measure startup time${NC}"
        return 1
    fi

    echo "$total_time"
}

# Function to measure warm start
measure_warm_start() {
    echo -e "${YELLOW}Measuring Warm Start (iteration $1/$NUM_MEASUREMENTS)${NC}"

    # Force stop but don't clear data
    adb shell am force-stop "$PACKAGE_NAME" 2>/dev/null || true

    # Wait briefly
    sleep 1

    # Start activity and capture timing
    local output=$(adb shell am start -W -n "$PACKAGE_NAME/$ACTIVITY_NAME" 2>&1)

    # Extract TotalTime
    local total_time=$(echo "$output" | grep "TotalTime" | awk '{print $2}')

    if [ -z "$total_time" ]; then
        echo -e "${RED}Failed to measure startup time${NC}"
        return 1
    fi

    echo "$total_time"
}

# Function to calculate average
calculate_average() {
    local sum=0
    local count=0

    for time in "$@"; do
        sum=$((sum + time))
        count=$((count + 1))
    done

    echo $((sum / count))
}

# Measure cold starts
echo -e "\n${GREEN}=== Cold Start Measurements ===${NC}"
cold_times=()

for i in $(seq 1 $NUM_MEASUREMENTS); do
    time=$(measure_cold_start $i)
    cold_times+=($time)
    echo -e "  Time: ${BLUE}${time}ms${NC}"
    sleep 1
done

# Calculate cold start average
cold_avg=$(calculate_average "${cold_times[@]}")
echo -e "\n${GREEN}Cold Start Average: ${BLUE}${cold_avg}ms${NC}"

# Measure warm starts
echo -e "\n${GREEN}=== Warm Start Measurements ===${NC}"
warm_times=()

for i in $(seq 1 $NUM_MEASUREMENTS); do
    time=$(measure_warm_start $i)
    warm_times+=($time)
    echo -e "  Time: ${BLUE}${time}ms${NC}"
    sleep 1
done

# Calculate warm start average
warm_avg=$(calculate_average "${warm_times[@]}")
echo -e "\n${GREEN}Warm Start Average: ${BLUE}${warm_avg}ms${NC}"

# Generate summary
echo -e "\n${BLUE}=========================================${NC}"
echo -e "${BLUE}Summary${NC}"
echo -e "${BLUE}=========================================${NC}"

# Cold start evaluation
echo -e "\n${GREEN}Cold Start:${NC}"
echo -e "  Average: ${BLUE}${cold_avg}ms${NC}"
echo -e "  Target:  ${BLUE}< ${TARGET_COLD_STARTUP}ms${NC}"

if [ $cold_avg -lt $TARGET_COLD_STARTUP ]; then
    echo -e "  Status:  ${GREEN}PASS ✓${NC}"
    cold_pass=true
else
    echo -e "  Status:  ${RED}NEEDS IMPROVEMENT${NC}"
    cold_pass=false
fi

# Warm start evaluation
echo -e "\n${GREEN}Warm Start:${NC}"
echo -e "  Average: ${BLUE}${warm_avg}ms${NC}"
echo -e "  Target:  ${BLUE}< ${TARGET_WARM_STARTUP}ms${NC}"

if [ $warm_avg -lt $TARGET_WARM_STARTUP ]; then
    echo -e "  Status:  ${GREEN}PASS ✓${NC}"
    warm_pass=true
else
    echo -e "  Status:  ${RED}NEEDS IMPROVEMENT${NC}"
    warm_pass=false
fi

# Baseline profile status
echo -e "\n${GREEN}Baseline Profile:${NC}"
if [ -f "app/src/main/baseline-prof.txt" ]; then
    baseline_entries=$(grep -c "^[HPS]" app/src/main/baseline-prof.txt)
    echo -e "  Status:   ${GREEN}ENABLED ✓${NC}"
    echo -e "  Entries:  ${BLUE}${baseline_entries} methods${NC}"
    echo -e "  Location: ${BLUE}app/src/main/baseline-prof.txt${NC}"
else
    echo -e "  Status:   ${RED}NOT FOUND${NC}"
fi

# Recommendations
echo -e "\n${BLUE}=========================================${NC}"
echo -e "${BLUE}Recommendations${NC}"
echo -e "${BLUE}=========================================${NC}"

if [ "$cold_pass" = false ] || [ "$warm_pass" = false ]; then
    echo -e "\n${YELLOW}To improve startup time:${NC}"
    echo "  1. Update baseline-prof.txt with Android Studio Profiler"
    echo "  2. Use 'Profile > Startup' to capture startup trace"
    echo "  3. Identify slow methods and add to baseline profile"
    echo "  4. Defer non-critical initialization"
    echo "  5. Use LazyColumn instead of Column for lists"
    echo "  6. Optimize Compose recompositions"
    echo ""
    echo "  See STARTUP_OPTIMIZATION.md for detailed guidance"
else
    echo -e "\n${GREEN}Great job! Startup times are within target ranges.${NC}"
fi

# Device info
echo -e "\n${BLUE}=========================================${NC}"
echo -e "${BLUE}Device Information${NC}"
echo -e "${BLUE}=========================================${NC}"
device_model=$(adb shell getprop ro.product.model | tr -d '\r')
android_version=$(adb shell getprop ro.build.version.release | tr -d '\r')
sdk_version=$(adb shell getprop ro.build.version.sdk | tr -d '\r')

echo -e "  Model:   ${BLUE}${device_model}${NC}"
echo -e "  Android: ${BLUE}${android_version} (API ${sdk_version})${NC}"

echo ""
echo -e "${GREEN}Measurement complete!${NC}"
echo ""

# Exit with appropriate code
if [ "$cold_pass" = true ] && [ "$warm_pass" = true ]; then
    exit 0
else
    exit 1
fi
