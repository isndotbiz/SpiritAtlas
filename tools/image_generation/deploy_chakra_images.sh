#!/bin/bash
# Chakra Image Deployment Script
# Converts 7 chakra PNG images to WebP and deploys to all 5 density folders

set -e

SOURCE_DIR="/Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation/generated_images/optimized_flux_pro"
DEST_BASE="/Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res"
TEMP_DIR="/tmp/chakra_conversion"

# Create temp directory
mkdir -p "$TEMP_DIR"

# Define chakra images in order
CHAKRA_NUMS=("046" "047" "048" "049" "050" "051" "052")
CHAKRA_NAMES=(
    "root_chakra_muladhara"
    "sacral_chakra_svadhisthana"
    "solar_plexus_chakra_manipura"
    "heart_chakra_anahata"
    "throat_chakra_vishuddha"
    "third_eye_chakra_ajna"
    "crown_chakra_sahasrara"
)

# Density folders
DENSITIES=("mdpi" "hdpi" "xhdpi" "xxhdpi" "xxxhdpi")

echo "=========================================="
echo "CHAKRA IMAGE DEPLOYMENT"
echo "=========================================="
echo ""

# Step 1: Convert PNG to WebP
echo "Step 1: Converting PNG to WebP..."
for i in "${!CHAKRA_NUMS[@]}"; do
    num="${CHAKRA_NUMS[$i]}"
    name="${CHAKRA_NAMES[$i]}"
    src_file="$SOURCE_DIR/${num}_${name}.png"
    webp_file="$TEMP_DIR/img_${num}_${name}.webp"

    if [ -f "$src_file" ]; then
        echo "  Converting: ${num}_${name}.png -> img_${num}_${name}.webp"
        cwebp -q 90 "$src_file" -o "$webp_file" 2>/dev/null
    else
        echo "  ERROR: Source file not found: $src_file"
        exit 1
    fi
done

echo ""
echo "Step 2: Deploying to density folders..."

# Step 2: Copy to all density folders
deployed_count=0
for density in "${DENSITIES[@]}"; do
    dest_dir="$DEST_BASE/drawable-$density"
    mkdir -p "$dest_dir"

    echo "  Deploying to drawable-$density..."
    for i in "${!CHAKRA_NUMS[@]}"; do
        num="${CHAKRA_NUMS[$i]}"
        name="${CHAKRA_NAMES[$i]}"
        webp_file="$TEMP_DIR/img_${num}_${name}.webp"
        dest_file="$dest_dir/img_${num}_${name}.webp"

        cp "$webp_file" "$dest_file"
        ((deployed_count++))
    done
done

echo ""
echo "Step 3: Verification..."
echo "  Total files deployed: $deployed_count / 35"

# Verify deployment
missing=0
for density in "${DENSITIES[@]}"; do
    for i in "${!CHAKRA_NUMS[@]}"; do
        num="${CHAKRA_NUMS[$i]}"
        name="${CHAKRA_NAMES[$i]}"
        file="$DEST_BASE/drawable-$density/img_${num}_${name}.webp"
        if [ ! -f "$file" ]; then
            echo "  MISSING: drawable-$density/img_${num}_${name}.webp"
            ((missing++))
        fi
    done
done

echo ""
if [ $missing -eq 0 ]; then
    echo "✅ SUCCESS: All 35 chakra images deployed!"
else
    echo "❌ ERROR: $missing files missing"
    exit 1
fi

# Cleanup
rm -rf "$TEMP_DIR"

echo ""
echo "=========================================="
echo "DEPLOYMENT SUMMARY"
echo "=========================================="
echo "Source: $SOURCE_DIR"
echo "Images: 7 chakras (046-052)"
echo "Densities: 5 (mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi)"
echo "Total files: 35"
echo "Status: ✅ COMPLETE"
echo "=========================================="
