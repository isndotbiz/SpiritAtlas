#!/bin/bash
# Memory Usage and Leak Detection Test for SpiritAtlas

PACKAGE="com.spiritatlas.app"
CYCLES=5

echo "=========================================="
echo "MEMORY USAGE & LEAK DETECTION TEST"
echo "=========================================="
echo "Package: $PACKAGE"
echo "Usage Cycles: $CYCLES"
echo ""

# Check if device is connected
if ! adb devices | grep -q "device$"; then
  echo "❌ Error: No Android device connected"
  exit 1
fi

# Function to get PSS Total in KB
get_pss() {
  adb shell dumpsys meminfo $PACKAGE 2>/dev/null | grep "TOTAL PSS:" | awk '{print $3}'
}

# Start app
echo "Starting app..."
adb shell am start -n $PACKAGE/.MainActivity > /dev/null 2>&1
sleep 5

# Wait for app to stabilize
echo "Waiting for app to stabilize..."
sleep 3

# Get baseline memory
PSS_START=$(get_pss)
if [ -z "$PSS_START" ]; then
  echo "❌ Error: Could not get memory info (is app running?)"
  exit 1
fi

PSS_START_MB=$((PSS_START / 1024))
echo "Initial memory: ${PSS_START_MB}MB"
echo ""

# Simulate user navigation through screens
for cycle in $(seq 1 $CYCLES); do
  echo "Usage cycle $cycle/$CYCLES:"

  # Navigate to Profile List (approximate tap locations)
  echo "  → Profile Library"
  adb shell input tap 180 1900 2>/dev/null || adb shell input tap 180 1700 2>/dev/null
  sleep 2

  # Navigate to Compatibility
  echo "  → Compatibility"
  adb shell input tap 540 1900 2>/dev/null || adb shell input tap 540 1700 2>/dev/null
  sleep 2

  # Navigate to Settings
  echo "  → Settings"
  adb shell input tap 900 1900 2>/dev/null || adb shell input tap 900 1700 2>/dev/null
  sleep 2

  # Back to Home (multiple back presses to ensure we're at home)
  echo "  → Home"
  for i in {1..3}; do
    adb shell input keyevent KEYCODE_BACK 2>/dev/null
    sleep 0.5
  done
  sleep 1

  # Get current memory
  PSS_CURRENT=$(get_pss)
  PSS_CURRENT_MB=$((PSS_CURRENT / 1024))
  GROWTH=$((PSS_CURRENT - PSS_START))
  GROWTH_MB=$((GROWTH / 1024))

  echo "  Memory: ${PSS_CURRENT_MB}MB (+${GROWTH_MB}MB)"
  echo ""
done

# Wait for any GC activity
echo "Waiting for garbage collection..."
sleep 5

# Final measurement
PSS_END=$(get_pss)
PSS_END_MB=$((PSS_END / 1024))
TOTAL_GROWTH=$((PSS_END - PSS_START))
TOTAL_GROWTH_MB=$((TOTAL_GROWTH / 1024))
GROWTH_PERCENT=$((TOTAL_GROWTH * 100 / PSS_START))

echo "=========================================="
echo "RESULTS"
echo "=========================================="
echo "Initial Memory:  ${PSS_START_MB}MB"
echo "Final Memory:    ${PSS_END_MB}MB"
echo "Memory Growth:   ${TOTAL_GROWTH_MB}MB (${GROWTH_PERCENT}%)"
echo "Target Memory:   <80MB average"
echo "Leak Threshold:  <15MB growth"
echo ""

# Determine status
if [ $PSS_END_MB -lt 80 ]; then
  MEMORY_STATUS="✅ GOOD"
  MEMORY_OK=0
else
  MEMORY_STATUS="⚠️  HIGH"
  MEMORY_OK=1
fi

if [ $TOTAL_GROWTH_MB -lt 10 ]; then
  LEAK_STATUS="🚀 EXCELLENT (No leaks)"
  LEAK_OK=0
elif [ $TOTAL_GROWTH_MB -lt 15 ]; then
  LEAK_STATUS="✅ GOOD (Minor growth)"
  LEAK_OK=0
else
  LEAK_STATUS="⚠️  WARNING (Possible leak)"
  LEAK_OK=1
fi

echo "Memory Usage: $MEMORY_STATUS"
echo "Leak Check:   $LEAK_STATUS"
echo ""

# Overall status
if [ $MEMORY_OK -eq 0 ] && [ $LEAK_OK -eq 0 ]; then
  echo "Overall: ✅ PASS"
  exit 0
else
  echo "Overall: ⚠️  NEEDS ATTENTION"
  exit 1
fi
