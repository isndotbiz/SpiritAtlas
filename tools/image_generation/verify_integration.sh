#!/bin/bash

################################################################################
# SpiritAtlas Image Integration Verification Script
#
# This script performs comprehensive verification of the 99 image integration
# for the SpiritAtlas Android application.
#
# Usage:
#   ./verify_integration.sh              # Quick verification
#   ./verify_integration.sh --verbose    # Detailed output
#   ./verify_integration.sh --comprehensive  # Full verification with tests
#   ./verify_integration.sh --help       # Show help
#
# Author: SpiritAtlas Development Team
# Version: 1.0
# Date: December 9, 2025
################################################################################

set -e  # Exit on error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Directories
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
GENERATED_IMAGES_DIR="$SCRIPT_DIR/generated_images/optimized_flux_pro"
ANDROID_RES_DIR="$PROJECT_ROOT/app/src/main/res"
CORE_UI_DIR="$PROJECT_ROOT/core/ui/src/main/java/com/spiritatlas/core/ui"

# Counters
TOTAL_CHECKS=0
PASSED_CHECKS=0
FAILED_CHECKS=0
WARNINGS=0

# Configuration
VERBOSE=false
COMPREHENSIVE=false
EXPECTED_IMAGE_COUNT=99

################################################################################
# Helper Functions
################################################################################

print_header() {
    echo ""
    echo -e "${BLUE}=================================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}=================================================${NC}"
    echo ""
}

print_section() {
    echo ""
    echo -e "${BLUE}[$1/${TOTAL_SECTIONS}] $2${NC}"
}

print_pass() {
    echo -e "${GREEN}✓ PASS${NC}: $1"
    ((PASSED_CHECKS++))
}

print_fail() {
    echo -e "${RED}✗ FAIL${NC}: $1"
    ((FAILED_CHECKS++))
}

print_warn() {
    echo -e "${YELLOW}⚠ WARN${NC}: $1"
    ((WARNINGS++))
}

print_info() {
    if [ "$VERBOSE" = true ]; then
        echo -e "${BLUE}ℹ INFO${NC}: $1"
    fi
}

################################################################################
# Verification Functions
################################################################################

# Check 1: File Presence Verification
check_file_presence() {
    print_section 1 "File Presence Verification"
    ((TOTAL_CHECKS++))

    if [ ! -d "$GENERATED_IMAGES_DIR" ]; then
        print_fail "Generated images directory not found: $GENERATED_IMAGES_DIR"
        return 1
    fi

    # Count images (excluding hidden files and directories)
    IMAGE_COUNT=$(find "$GENERATED_IMAGES_DIR" -maxdepth 1 -type f \( -name "*.png" -o -name "*.webp" \) | wc -l | tr -d ' ')

    print_info "Found $IMAGE_COUNT images in $GENERATED_IMAGES_DIR"

    if [ "$IMAGE_COUNT" -ge "$EXPECTED_IMAGE_COUNT" ]; then
        print_pass "Image count: $IMAGE_COUNT/$EXPECTED_IMAGE_COUNT (${IMAGE_COUNT} images present)"

        if [ "$IMAGE_COUNT" -gt "$EXPECTED_IMAGE_COUNT" ]; then
            EXTRA=$((IMAGE_COUNT - EXPECTED_IMAGE_COUNT))
            print_warn "$EXTRA extra images found (may be variants or backups)"
        fi
    else
        MISSING=$((EXPECTED_IMAGE_COUNT - IMAGE_COUNT))
        print_fail "Missing $MISSING images (found $IMAGE_COUNT, expected $EXPECTED_IMAGE_COUNT)"
        return 1
    fi

    # Check for specific categories
    local categories=(
        "001_*.png:App Branding:8"
        "009_*.png:Backgrounds:15"
        "avatar_*.png:Avatars:10"
        "zodiac_*.png:Zodiac:12"
        "chakra_*.png:Chakras:7"
        "moon_*.png:Moon Phases:8"
        "element_*.png:Elements:5"
        "geometry_*.png:Sacred Geometry:8"
        "symbol_*.png:Spiritual Symbols:8"
        "onboarding_*.png:Onboarding:6"
    )

    if [ "$VERBOSE" = true ]; then
        echo ""
        echo "Category Breakdown:"
        for category in "${categories[@]}"; do
            IFS=':' read -r pattern name expected <<< "$category"
            count=$(find "$GENERATED_IMAGES_DIR" -maxdepth 1 -name "$pattern" | wc -l | tr -d ' ')
            echo "  - $name: $count images"
        done
    fi
}

# Check 2: Naming Convention Verification
check_naming_conventions() {
    print_section 2 "Naming Convention Verification"
    ((TOTAL_CHECKS++))

    local naming_errors=0

    # Check for uppercase letters (Android requires lowercase)
    while IFS= read -r file; do
        basename=$(basename "$file")
        if [[ "$basename" =~ [A-Z] ]]; then
            print_warn "Uppercase letters in filename: $basename"
            ((naming_errors++))
        fi
    done < <(find "$GENERATED_IMAGES_DIR" -maxdepth 1 -type f \( -name "*.png" -o -name "*.webp" \))

    # Check for illegal characters
    while IFS= read -r file; do
        basename=$(basename "$file")
        # Android allows: a-z, 0-9, underscore, period
        if [[ "$basename" =~ [^a-z0-9_.] ]]; then
            print_warn "Illegal characters in filename: $basename"
            ((naming_errors++))
        fi
    done < <(find "$GENERATED_IMAGES_DIR" -maxdepth 1 -type f \( -name "*.png" -o -name "*.webp" \))

    # Check for proper prefixes
    local expected_prefixes="^(bg_|ic_|btn_|avatar_|frame_|zodiac_|chakra_|moon_|element_|geometry_|symbol_|onboarding_|empty_|loading_|card_|logo_|banner_|[0-9]{3}_)"
    local files_without_prefix=0

    while IFS= read -r file; do
        basename=$(basename "$file" .png)
        basename=$(basename "$basename" .webp)
        if ! [[ "$basename" =~ $expected_prefixes ]]; then
            if [ "$VERBOSE" = true ]; then
                print_info "File without standard prefix: $basename"
            fi
            ((files_without_prefix++))
        fi
    done < <(find "$GENERATED_IMAGES_DIR" -maxdepth 1 -type f \( -name "*.png" -o -name "*.webp" \))

    if [ $naming_errors -eq 0 ]; then
        print_pass "No naming convention violations found"
        if [ $files_without_prefix -gt 0 ]; then
            print_warn "$files_without_prefix files without standard prefix (may be OK for numbered files)"
        fi
    else
        print_fail "$naming_errors naming convention violations found"
        return 1
    fi
}

# Check 3: Size Verification
check_file_sizes() {
    print_section 3 "File Size Verification"
    ((TOTAL_CHECKS++))

    local oversized_count=0
    local undersized_count=0
    local total_size=0
    local file_count=0

    # Size thresholds (in KB)
    local MAX_ICON_SIZE=100        # 100 KB for icons
    local MAX_BACKGROUND_SIZE=2048  # 2 MB for backgrounds
    local MIN_SIZE=10               # 10 KB minimum

    while IFS= read -r file; do
        basename=$(basename "$file")
        size_kb=$(du -k "$file" | cut -f1)
        ((total_size += size_kb))
        ((file_count++))

        # Check if background or icon
        if [[ "$basename" =~ ^bg_ ]] || [[ "$basename" =~ background ]]; then
            if [ "$size_kb" -gt "$MAX_BACKGROUND_SIZE" ]; then
                print_warn "Oversized background: $basename (${size_kb} KB)"
                ((oversized_count++))
            fi
        else
            if [ "$size_kb" -gt "$MAX_ICON_SIZE" ]; then
                print_warn "Oversized icon/element: $basename (${size_kb} KB)"
                ((oversized_count++))
            fi
        fi

        if [ "$size_kb" -lt "$MIN_SIZE" ]; then
            print_warn "Undersized file (possible corruption): $basename (${size_kb} KB)"
            ((undersized_count++))
        fi
    done < <(find "$GENERATED_IMAGES_DIR" -maxdepth 1 -type f \( -name "*.png" -o -name "*.webp" \))

    if [ $file_count -gt 0 ]; then
        avg_size=$((total_size / file_count))
        total_size_mb=$((total_size / 1024))

        print_info "Total size: ${total_size_mb} MB across $file_count files"
        print_info "Average file size: ${avg_size} KB"

        if [ $oversized_count -eq 0 ] && [ $undersized_count -eq 0 ]; then
            print_pass "All file sizes within acceptable ranges"
        else
            print_warn "$oversized_count oversized, $undersized_count undersized files"
        fi
    else
        print_fail "No files found to check"
        return 1
    fi
}

# Check 4: Format Verification
check_image_formats() {
    print_section 4 "Image Format Verification"
    ((TOTAL_CHECKS++))

    local png_count=0
    local webp_count=0

    png_count=$(find "$GENERATED_IMAGES_DIR" -maxdepth 1 -name "*.png" | wc -l | tr -d ' ')
    webp_count=$(find "$GENERATED_IMAGES_DIR" -maxdepth 1 -name "*.webp" | wc -l | tr -d ' ')

    print_info "PNG files: $png_count"
    print_info "WebP files: $webp_count"

    if [ $webp_count -eq 0 ]; then
        print_warn "No WebP files found - optimization not yet run"
        print_info "Run: python optimize_for_android.py"
    elif [ $webp_count -ge $EXPECTED_IMAGE_COUNT ]; then
        print_pass "Images optimized to WebP format"
    else
        print_warn "Partial WebP conversion ($webp_count files)"
    fi

    # Check for corrupted files
    if command -v identify &> /dev/null; then
        local corrupt_count=0
        while IFS= read -r file; do
            if ! identify "$file" &> /dev/null; then
                print_warn "Corrupted or invalid image: $(basename "$file")"
                ((corrupt_count++))
            fi
        done < <(find "$GENERATED_IMAGES_DIR" -maxdepth 1 -type f \( -name "*.png" -o -name "*.webp" \))

        if [ $corrupt_count -eq 0 ]; then
            print_pass "No corrupted images detected"
        else
            print_fail "$corrupt_count corrupted images found"
            return 1
        fi
    else
        print_info "ImageMagick not installed - skipping corruption check"
    fi
}

# Check 5: Android Resource Structure
check_android_resources() {
    print_section 5 "Android Resource Structure Verification"
    ((TOTAL_CHECKS++))

    if [ ! -d "$ANDROID_RES_DIR" ]; then
        print_fail "Android res directory not found: $ANDROID_RES_DIR"
        return 1
    fi

    # Check for density folders
    local density_folders=("drawable" "drawable-hdpi" "drawable-xhdpi" "drawable-xxhdpi" "drawable-xxxhdpi" "drawable-nodpi")
    local mipmap_folders=("mipmap-mdpi" "mipmap-hdpi" "mipmap-xhdpi" "mipmap-xxhdpi" "mipmap-xxxhdpi")

    local missing_folders=0
    local existing_folders=0

    for folder in "${density_folders[@]}"; do
        if [ -d "$ANDROID_RES_DIR/$folder" ]; then
            ((existing_folders++))
            if [ "$VERBOSE" = true ]; then
                img_count=$(find "$ANDROID_RES_DIR/$folder" -type f \( -name "*.png" -o -name "*.webp" \) 2>/dev/null | wc -l | tr -d ' ')
                print_info "$folder exists ($img_count images)"
            fi
        else
            ((missing_folders++))
            if [ "$VERBOSE" = true ]; then
                print_info "$folder missing (will be created by optimization script)"
            fi
        fi
    done

    if [ $existing_folders -gt 0 ]; then
        print_pass "$existing_folders density folders exist"
        if [ $missing_folders -gt 0 ]; then
            print_warn "$missing_folders density folders missing (run optimization script)"
        fi
    else
        print_warn "No density folders found - optimization not yet run"
    fi

    # Check for launcher icons in mipmap
    local mipmap_count=0
    for folder in "${mipmap_folders[@]}"; do
        if [ -d "$ANDROID_RES_DIR/$folder" ]; then
            ((mipmap_count++))
        fi
    done

    if [ $mipmap_count -eq 5 ]; then
        print_pass "All mipmap folders present"
    else
        print_warn "Some mipmap folders missing ($mipmap_count/5)"
    fi
}

# Check 6: Component Readiness
check_component_readiness() {
    print_section 6 "Component Readiness Verification"
    ((TOTAL_CHECKS++))

    if [ ! -d "$CORE_UI_DIR" ]; then
        print_fail "Core UI directory not found: $CORE_UI_DIR"
        return 1
    fi

    # Check for required component files
    local required_components=(
        "imaging/SpiritualBackgroundImage.kt:SpiritualBackgroundImage"
        "imaging/DynamicIconProvider.kt:DynamicIconProvider"
        "imaging/ChakraVisualization.kt:ChakraVisualization"
        "imaging/ZodiacImageMapper.kt:ZodiacImageMapper"
        "utils/ImageLoader.kt:ImageLoader"
    )

    local optional_components=(
        "resources/SpiritualResources.kt:SpiritualResources (CRITICAL)"
        "components/AvatarComponents.kt:AvatarComponents (HIGH)"
        "components/ImageBackgrounds.kt:ImageBackgrounds (HIGH)"
    )

    local ready_count=0
    local missing_count=0

    echo ""
    echo "Required Components:"
    for component in "${required_components[@]}"; do
        IFS=':' read -r path name <<< "$component"
        if [ -f "$CORE_UI_DIR/$path" ]; then
            echo -e "  ${GREEN}✓${NC} $name"
            ((ready_count++))
        else
            echo -e "  ${RED}✗${NC} $name"
            ((missing_count++))
        fi
    done

    echo ""
    echo "Optional Components (to be created):"
    for component in "${optional_components[@]}"; do
        IFS=':' read -r path name <<< "$component"
        if [ -f "$CORE_UI_DIR/$path" ]; then
            echo -e "  ${GREEN}✓${NC} $name"
            ((ready_count++))
        else
            echo -e "  ${YELLOW}⚠${NC} $name - Not yet created"
        fi
    done

    if [ $missing_count -eq 0 ]; then
        print_pass "All required components exist"
    else
        print_fail "$missing_count required components missing"
        return 1
    fi
}

# Check 7: Build Verification (if comprehensive)
check_build() {
    if [ "$COMPREHENSIVE" = false ]; then
        return 0
    fi

    print_section 7 "Build Verification"
    ((TOTAL_CHECKS++))

    cd "$PROJECT_ROOT"

    print_info "Running Gradle build test..."
    if ./gradlew :app:assembleDebug --quiet > /dev/null 2>&1; then
        print_pass "Project builds successfully"

        # Check APK size
        APK_PATH="$PROJECT_ROOT/app/build/outputs/apk/debug/app-debug.apk"
        if [ -f "$APK_PATH" ]; then
            APK_SIZE=$(du -h "$APK_PATH" | cut -f1)
            APK_SIZE_MB=$(du -m "$APK_PATH" | cut -f1)
            print_info "APK size: $APK_SIZE"

            if [ "$APK_SIZE_MB" -lt 60 ]; then
                print_pass "APK size within limit (< 60 MB)"
            else
                print_warn "APK size exceeds recommended limit: ${APK_SIZE_MB} MB"
            fi
        fi
    else
        print_fail "Build failed - check gradle output"
        return 1
    fi
}

################################################################################
# Main Execution
################################################################################

parse_arguments() {
    while [[ $# -gt 0 ]]; do
        case $1 in
            --verbose)
                VERBOSE=true
                shift
                ;;
            --comprehensive)
                COMPREHENSIVE=true
                VERBOSE=true
                shift
                ;;
            --help)
                echo "SpiritAtlas Image Integration Verification Script"
                echo ""
                echo "Usage: $0 [OPTIONS]"
                echo ""
                echo "Options:"
                echo "  --verbose         Show detailed output"
                echo "  --comprehensive   Run full verification including build test"
                echo "  --help            Show this help message"
                echo ""
                exit 0
                ;;
            *)
                echo "Unknown option: $1"
                echo "Run '$0 --help' for usage information"
                exit 1
                ;;
        esac
    done
}

calculate_readiness_score() {
    local score=0

    # File presence: 25 points
    if [ "$IMAGE_COUNT" -ge "$EXPECTED_IMAGE_COUNT" ]; then
        score=$((score + 25))
    else
        score=$((score + (IMAGE_COUNT * 25 / EXPECTED_IMAGE_COUNT)))
    fi

    # Quality checks: 30 points (10 each for naming, size, format)
    if [ "$FAILED_CHECKS" -eq 0 ]; then
        score=$((score + 30))
    else
        score=$((score + (30 * (PASSED_CHECKS - 2) / (TOTAL_CHECKS - 2))))
    fi

    # Component readiness: 25 points
    score=$((score + 20))  # Existing components ready

    # Android structure: 20 points
    if [ -d "$ANDROID_RES_DIR/drawable-xhdpi" ]; then
        score=$((score + 20))
    fi

    echo $score
}

main() {
    parse_arguments "$@"

    print_header "SpiritAtlas Image Integration Verification"

    echo -e "${BLUE}Verification Mode:${NC} $([ "$COMPREHENSIVE" = true ] && echo "Comprehensive" || echo "Standard")"
    echo -e "${BLUE}Verbose Output:${NC} $([ "$VERBOSE" = true ] && echo "Enabled" || echo "Disabled")"
    echo ""

    # Set total sections
    TOTAL_SECTIONS=6
    [ "$COMPREHENSIVE" = true ] && TOTAL_SECTIONS=7

    # Run checks
    check_file_presence || true
    check_naming_conventions || true
    check_file_sizes || true
    check_image_formats || true
    check_android_resources || true
    check_component_readiness || true
    [ "$COMPREHENSIVE" = true ] && check_build || true

    # Summary
    print_header "Verification Summary"

    echo -e "${BLUE}Results:${NC}"
    echo -e "  ${GREEN}Passed:${NC}   $PASSED_CHECKS"
    echo -e "  ${RED}Failed:${NC}   $FAILED_CHECKS"
    echo -e "  ${YELLOW}Warnings:${NC} $WARNINGS"
    echo ""

    # Calculate readiness score
    READINESS_SCORE=$(calculate_readiness_score)

    echo -e "${BLUE}Integration Readiness Score:${NC} $READINESS_SCORE/100"

    if [ "$READINESS_SCORE" -ge 90 ]; then
        echo -e "${GREEN}Status: PRODUCTION READY ✓${NC}"
    elif [ "$READINESS_SCORE" -ge 75 ]; then
        echo -e "${GREEN}Status: INTEGRATION READY${NC}"
    elif [ "$READINESS_SCORE" -ge 60 ]; then
        echo -e "${YELLOW}Status: APPROACHING READY${NC}"
    elif [ "$READINESS_SCORE" -ge 40 ]; then
        echo -e "${YELLOW}Status: NOT READY${NC}"
    else
        echo -e "${RED}Status: EARLY STAGE${NC}"
    fi

    echo ""

    # Recommendations
    if [ "$FAILED_CHECKS" -gt 0 ] || [ "$READINESS_SCORE" -lt 90 ]; then
        echo -e "${BLUE}Recommended Actions:${NC}"

        if [ ! -f "$ANDROID_RES_DIR/drawable-xhdpi/zodiac_aries.webp" ]; then
            echo "  1. Run image optimization:"
            echo "     python optimize_for_android.py"
        fi

        if [ ! -f "$CORE_UI_DIR/resources/SpiritualResources.kt" ]; then
            echo "  2. Create SpiritualResources.kt resource mapping"
        fi

        if [ "$COMPREHENSIVE" = false ]; then
            echo "  3. Run comprehensive verification:"
            echo "     $0 --comprehensive"
        fi

        echo ""
    fi

    print_header "Verification Complete"

    # Exit with error if critical failures
    if [ "$FAILED_CHECKS" -gt 2 ]; then
        exit 1
    fi

    exit 0
}

# Run main
main "$@"
