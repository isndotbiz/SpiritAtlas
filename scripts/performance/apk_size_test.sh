#!/bin/bash
# APK Size Analysis Test for SpiritAtlas

echo "=========================================="
echo "APK SIZE ANALYSIS"
echo "=========================================="
echo ""

# Build release APK
echo "Building release APK..."
./gradlew :app:assembleRelease --quiet

APK_PATH="app/build/outputs/apk/release/app-release.apk"

if [ ! -f "$APK_PATH" ]; then
  echo "❌ Error: APK not found at $APK_PATH"
  echo "Build may have failed"
  exit 1
fi

# Get APK size
if [[ "$OSTYPE" == "darwin"* ]]; then
  # macOS
  SIZE_BYTES=$(stat -f%z "$APK_PATH")
else
  # Linux
  SIZE_BYTES=$(stat -c%s "$APK_PATH")
fi

SIZE_KB=$((SIZE_BYTES / 1024))
SIZE_MB=$((SIZE_BYTES / 1024 / 1024))

echo "APK Size: ${SIZE_MB}MB (${SIZE_KB}KB)"
echo "Target:   <12MB"
echo ""

# Detailed breakdown
echo "Size breakdown:"
echo "----------------------------------------"

# Unzip and analyze
TMP_DIR=$(mktemp -d)
unzip -q "$APK_PATH" -d "$TMP_DIR"

# Calculate directory sizes
if [[ "$OSTYPE" == "darwin"* ]]; then
  RES_SIZE=$(du -sk "$TMP_DIR/res" 2>/dev/null | awk '{print $1}')
  LIB_SIZE=$(du -sk "$TMP_DIR/lib" 2>/dev/null | awk '{print $1}')
  ASSETS_SIZE=$(du -sk "$TMP_DIR/assets" 2>/dev/null | awk '{print $1}')
  DEX_SIZE=$(find "$TMP_DIR" -name "*.dex" -exec stat -f%z {} \; | awk '{sum+=$1} END {print sum/1024}')
else
  RES_SIZE=$(du -sk "$TMP_DIR/res" 2>/dev/null | awk '{print $1}')
  LIB_SIZE=$(du -sk "$TMP_DIR/lib" 2>/dev/null | awk '{print $1}')
  ASSETS_SIZE=$(du -sk "$TMP_DIR/assets" 2>/dev/null | awk '{print $1}')
  DEX_SIZE=$(find "$TMP_DIR" -name "*.dex" -exec stat -c%s {} \; | awk '{sum+=$1} END {print sum/1024}')
fi

# Default to 0 if directory doesn't exist
RES_SIZE=${RES_SIZE:-0}
LIB_SIZE=${LIB_SIZE:-0}
ASSETS_SIZE=${ASSETS_SIZE:-0}
DEX_SIZE=${DEX_SIZE:-0}

RES_MB=$((RES_SIZE / 1024))
LIB_MB=$((LIB_SIZE / 1024))
ASSETS_MB=$((ASSETS_SIZE / 1024))
DEX_MB=$((DEX_SIZE / 1024))

echo "Resources (res/): ${RES_MB}MB"
echo "Code (dex):       ${DEX_MB}MB"
echo "Native libs:      ${LIB_MB}MB"
echo "Assets:           ${ASSETS_MB}MB"
echo ""

# Top 10 largest files
echo "Largest files:"
echo "----------------------------------------"
find "$TMP_DIR" -type f -exec du -k {} \; | sort -rn | head -10 | while read size file; do
  size_mb=$((size / 1024))
  filename=$(basename "$file")
  echo "  ${size_mb}MB - $filename"
done

# Cleanup
rm -rf "$TMP_DIR"

echo ""
echo "=========================================="
echo "RESULTS"
echo "=========================================="

# Determine status
if [ $SIZE_MB -lt 8 ]; then
  RATING="🚀 EXCELLENT"
  STATUS=0
elif [ $SIZE_MB -lt 12 ]; then
  RATING="✅ GOOD"
  STATUS=0
elif [ $SIZE_MB -lt 20 ]; then
  RATING="⚠️  ACCEPTABLE"
  STATUS=1
else
  RATING="❌ TOO LARGE"
  STATUS=1
fi

echo "Status: $RATING"
echo ""

# Build App Bundle for comparison
echo "Building App Bundle (AAB)..."
./gradlew :app:bundleRelease --quiet

AAB_PATH="app/build/outputs/bundle/release/app-release.aab"
if [ -f "$AAB_PATH" ]; then
  if [[ "$OSTYPE" == "darwin"* ]]; then
    AAB_SIZE_MB=$(stat -f%z "$AAB_PATH" | awk '{print int($1/1024/1024)}')
  else
    AAB_SIZE_MB=$(stat -c%s "$AAB_PATH" | awk '{print int($1/1024/1024)}')
  fi
  echo "App Bundle: ${AAB_SIZE_MB}MB"
  echo "(Users download ~60-70% of this size)"
  echo ""
fi

exit $STATUS
