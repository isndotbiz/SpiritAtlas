#!/bin/bash
# Check image asset integration completeness

echo "=== IMAGE ASSET COMPLETENESS CHECK ==="

# Count optimized images
OPTIMIZED_COUNT=$(find tools/image_generation/generated_images/optimized_flux_pro -type f \( -name "*.png" -o -name "*.webp" \) 2>/dev/null | wc -l | tr -d ' ')
echo "Optimized images available: $OPTIMIZED_COUNT / 101"

# Count integrated images in app resources
INTEGRATED_COUNT=$(find app/src/main/res/drawable* -type f \( -name "*.png" -o -name "*.webp" \) 2>/dev/null | wc -l | tr -d ' ')
echo "Images integrated in app: $INTEGRATED_COUNT"

# Check specific image categories
echo ""
echo "--- Category Breakdown ---"

# Chakras (7 expected)
CHAKRA_COUNT=$(find app/src/main/res/drawable* -name "*chakra*" -type f 2>/dev/null | wc -l | tr -d ' ')
echo "Chakra images: $CHAKRA_COUNT / 7"

# Zodiac (12 expected)
ZODIAC_COUNT=$(find app/src/main/res/drawable* -name "*zodiac*" -type f 2>/dev/null | wc -l | tr -d ' ')
echo "Zodiac images: $ZODIAC_COUNT / 12"

# Moon phases (8 expected)
MOON_COUNT=$(find app/src/main/res/drawable* -name "*moon*" -type f 2>/dev/null | wc -l | tr -d ' ')
echo "Moon phase images: $MOON_COUNT / 8"

# Elements (4 expected)
ELEMENT_COUNT=$(find app/src/main/res/drawable* -name "*element*" -type f 2>/dev/null | wc -l | tr -d ' ')
echo "Element images: $ELEMENT_COUNT / 4"

# Calculate score
PERCENTAGE=$(echo "scale=2; ($INTEGRATED_COUNT / 101) * 100" | bc)
echo ""
echo "Integration percentage: ${PERCENTAGE}%"

if (( $(echo "$INTEGRATED_COUNT >= 101" | bc -l) )); then
    SCORE=2.5
elif (( $(echo "$INTEGRATED_COUNT >= 95" | bc -l) )); then
    SCORE=2.0
elif (( $(echo "$INTEGRATED_COUNT >= 85" | bc -l) )); then
    SCORE=1.5
elif (( $(echo "$INTEGRATED_COUNT >= 75" | bc -l) )); then
    SCORE=1.0
elif (( $(echo "$INTEGRATED_COUNT >= 60" | bc -l) )); then
    SCORE=0.5
else
    SCORE=0
fi

echo "Score: $SCORE / 2.5"
