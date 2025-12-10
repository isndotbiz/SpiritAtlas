#!/bin/bash
# Cold Start Performance Test for SpiritAtlas
# Measures time from app launch to first interactive frame

PACKAGE="com.spiritatlas.app"
ACTIVITY=".MainActivity"
ITERATIONS=10

echo "=========================================="
echo "COLD START PERFORMANCE TEST"
echo "=========================================="
echo "Package: $PACKAGE"
echo "Iterations: $ITERATIONS"
echo ""

# Check if device is connected
if ! adb devices | grep -q "device$"; then
  echo "❌ Error: No Android device connected"
  echo "Connect a device or start an emulator"
  exit 1
fi

SUM=0
MIN=999999
MAX=0

for i in $(seq 1 $ITERATIONS); do
  # Force stop and clear data for true cold start
  adb shell am force-stop $PACKAGE > /dev/null 2>&1
  adb shell pm clear $PACKAGE > /dev/null 2>&1
  sleep 2

  # Measure startup with -W flag (wait for launch)
  RESULT=$(adb shell am start -W -n $PACKAGE/$ACTIVITY 2>&1 | grep "TotalTime" | awk '{print $2}')

  if [ -z "$RESULT" ]; then
    echo "Run $i: ERROR (app may not be installed)"
    continue
  fi

  echo "Run $i: ${RESULT}ms"

  SUM=$((SUM + RESULT))

  # Track min/max
  if [ $RESULT -lt $MIN ]; then
    MIN=$RESULT
  fi
  if [ $RESULT -gt $MAX ]; then
    MAX=$RESULT
  fi

  sleep 3
done

if [ $SUM -eq 0 ]; then
  echo ""
  echo "❌ Error: No valid measurements collected"
  exit 1
fi

AVERAGE=$((SUM / ITERATIONS))

echo ""
echo "=========================================="
echo "RESULTS"
echo "=========================================="
echo "Average:  ${AVERAGE}ms"
echo "Min:      ${MIN}ms"
echo "Max:      ${MAX}ms"
echo "Target:   <1000ms"
echo ""

# Performance rating
if [ $AVERAGE -lt 800 ]; then
  RATING="🚀 EXCELLENT"
  STATUS=0
elif [ $AVERAGE -lt 1000 ]; then
  RATING="✅ GOOD"
  STATUS=0
elif [ $AVERAGE -lt 1500 ]; then
  RATING="⚠️  ACCEPTABLE"
  STATUS=1
else
  RATING="❌ NEEDS OPTIMIZATION"
  STATUS=1
fi

echo "Status: $RATING"
echo ""

exit $STATUS
