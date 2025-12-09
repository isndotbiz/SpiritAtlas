#!/bin/bash
################################################################################
# SpiritAtlas Spiritual Assets Generator
#
# This script generates all spiritual imagery assets for the SpiritAtlas app
# using the Replicate.ai API with Flux models.
#
# Usage:
#   ./generate_spiritual_assets.sh [category]
#
# Categories:
#   all       - Generate all assets (default)
#   zodiac    - 12 zodiac signs
#   chakras   - 7 chakra centers
#   numerology - 9 life path numbers + 3 master numbers
#   moon      - 8 moon phases
#   doshas    - 3 Ayurvedic doshas
#   hd        - 5 Human Design types
#   backgrounds - 6 spiritual backgrounds
#
# Examples:
#   ./generate_spiritual_assets.sh zodiac
#   ./generate_spiritual_assets.sh all
################################################################################

set -e  # Exit on error

# Configuration
OUTPUT_DIR="./generated_assets"
MODEL="flux-dev"  # Options: flux-dev, flux-schnell, sdxl
ASPECT_RATIO="square"
STYLE="ethereal cosmic art with sacred geometry"

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Check if API token is set
if [ -z "$REPLICATE_API_TOKEN" ]; then
    echo -e "${RED}Error: REPLICATE_API_TOKEN not set${NC}"
    echo "Set with: export REPLICATE_API_TOKEN=your_token"
    exit 1
fi

# Check if replicate_generator.py exists
if [ ! -f "replicate_generator.py" ]; then
    echo -e "${RED}Error: replicate_generator.py not found${NC}"
    echo "Run this script from tools/image_generation directory"
    exit 1
fi

# Create output directory
mkdir -p "$OUTPUT_DIR"

# Helper function to generate an image
generate_image() {
    local concept="$1"
    local category="$2"
    local filename="$3"
    local custom_style="${4:-$STYLE}"

    echo -e "${BLUE}[$(date +'%H:%M:%S')]${NC} Generating: $filename"

    mkdir -p "$OUTPUT_DIR/$category"

    python replicate_generator.py \
        -s "$concept" \
        --style "$custom_style" \
        -m "$MODEL" \
        -a "$ASPECT_RATIO" \
        -o "$OUTPUT_DIR/$category" \
        > /dev/null 2>&1

    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓${NC} Generated: $category/$filename"
    else
        echo -e "${RED}✗${NC} Failed: $category/$filename"
    fi
}

# Generate Zodiac Signs
generate_zodiac() {
    echo -e "\n${YELLOW}=== Generating Zodiac Signs (12 images) ===${NC}\n"

    declare -a signs=("Aries" "Taurus" "Gemini" "Cancer" "Leo" "Virgo"
                      "Libra" "Scorpio" "Sagittarius" "Capricorn" "Aquarius" "Pisces")

    for sign in "${signs[@]}"; do
        generate_image "$sign zodiac constellation" "zodiac" "${sign,,}.png" "cosmic ethereal constellation art"
    done
}

# Generate Chakras
generate_chakras() {
    echo -e "\n${YELLOW}=== Generating Chakras (7 images) ===${NC}\n"

    generate_image "root chakra Muladhara red energy" "chakras" "root_chakra.png" "mandala sacred geometry"
    generate_image "sacral chakra Svadhisthana orange energy" "chakras" "sacral_chakra.png" "flowing water mandala"
    generate_image "solar plexus chakra Manipura yellow energy" "chakras" "solar_plexus_chakra.png" "fire mandala sacred geometry"
    generate_image "heart chakra Anahata green energy" "chakras" "heart_chakra.png" "love light mandala"
    generate_image "throat chakra Vishuddha blue energy" "chakras" "throat_chakra.png" "communication mandala"
    generate_image "third eye chakra Ajna indigo energy" "chakras" "third_eye_chakra.png" "intuition mandala"
    generate_image "crown chakra Sahasrara violet energy" "chakras" "crown_chakra.png" "divine light mandala"
}

# Generate Numerology
generate_numerology() {
    echo -e "\n${YELLOW}=== Generating Numerology (12 images) ===${NC}\n"

    # Life Path Numbers 1-9
    for num in {1..9}; do
        generate_image "life path number $num spiritual energy" "numerology" "life_path_${num}.png" "sacred geometry golden ratio"
    done

    # Master Numbers
    generate_image "master number 11 spiritual awakening" "numerology" "master_11.png" "sacred geometry divine light"
    generate_image "master number 22 master builder" "numerology" "master_22.png" "sacred geometry manifestation"
    generate_image "master number 33 master teacher" "numerology" "master_33.png" "sacred geometry wisdom"
}

# Generate Moon Phases
generate_moon() {
    echo -e "\n${YELLOW}=== Generating Moon Phases (8 images) ===${NC}\n"

    generate_image "new moon dark sky mystical" "moon" "new_moon.png" "night sky spiritual energy"
    generate_image "waxing crescent moon night sky" "moon" "waxing_crescent.png" "celestial glow"
    generate_image "first quarter moon half illuminated" "moon" "first_quarter.png" "cosmic balance"
    generate_image "waxing gibbous moon nearly full" "moon" "waxing_gibbous.png" "spiritual light"
    generate_image "full moon bright mystical glow" "moon" "full_moon.png" "luminous night sky"
    generate_image "waning gibbous moon spiritual energy" "moon" "waning_gibbous.png" "mystical moonlight"
    generate_image "last quarter moon cosmic balance" "moon" "last_quarter.png" "half moon energy"
    generate_image "waning crescent moon fading light" "moon" "waning_crescent.png" "mystical darkness"
}

# Generate Ayurvedic Doshas
generate_doshas() {
    echo -e "\n${YELLOW}=== Generating Ayurvedic Doshas (3 images) ===${NC}\n"

    generate_image "Vata dosha air ether elements" "ayurveda" "vata.png" "flowing ethereal energy"
    generate_image "Pitta dosha fire water elements" "ayurveda" "pitta.png" "warm transformative energy"
    generate_image "Kapha dosha earth water elements" "ayurveda" "kapha.png" "grounded stable energy"
}

# Generate Human Design Types
generate_hd() {
    echo -e "\n${YELLOW}=== Generating Human Design Types (5 images) ===${NC}\n"

    generate_image "Generator energy type aura" "humandesign" "generator.png" "life force energy field"
    generate_image "Manifesting Generator dynamic aura" "humandesign" "mani_gen.png" "multi-passionate energy"
    generate_image "Projector focused aura" "humandesign" "projector.png" "guiding light energy"
    generate_image "Manifestor powerful aura" "humandesign" "manifestor.png" "initiating force energy"
    generate_image "Reflector lunar aura mirror" "humandesign" "reflector.png" "reflecting cosmic wisdom"
}

# Generate Backgrounds
generate_backgrounds() {
    echo -e "\n${YELLOW}=== Generating Backgrounds (6 images) ===${NC}\n"

    generate_image "cosmic void stars galaxies deep space" "backgrounds" "cosmic_void.png" "universe infinite space"
    generate_image "ethereal clouds spiritual realm" "backgrounds" "ethereal_clouds.png" "soft divine light"
    generate_image "sacred temple mystical atmosphere" "backgrounds" "sacred_temple.png" "spiritual sanctuary"
    generate_image "meditation space peaceful energy" "backgrounds" "meditation_space.png" "tranquil spiritual"
    generate_image "aurora borealis cosmic energy" "backgrounds" "aurora.png" "celestial lights flowing"
    generate_image "starfield nebula cosmic dust" "backgrounds" "nebula.png" "space clouds stellar"
}

# Main execution
main() {
    local category="${1:-all}"

    echo -e "${GREEN}"
    echo "╔════════════════════════════════════════════════════════════════╗"
    echo "║         SpiritAtlas Spiritual Assets Generator                ║"
    echo "╚════════════════════════════════════════════════════════════════╝"
    echo -e "${NC}"
    echo "Model: $MODEL"
    echo "Output: $OUTPUT_DIR"
    echo "Style: $STYLE"
    echo ""

    START_TIME=$(date +%s)

    case "$category" in
        zodiac)
            generate_zodiac
            ;;
        chakras)
            generate_chakras
            ;;
        numerology)
            generate_numerology
            ;;
        moon)
            generate_moon
            ;;
        doshas)
            generate_doshas
            ;;
        hd|humandesign)
            generate_hd
            ;;
        backgrounds)
            generate_backgrounds
            ;;
        all)
            generate_zodiac
            generate_chakras
            generate_numerology
            generate_moon
            generate_doshas
            generate_hd
            generate_backgrounds
            ;;
        *)
            echo -e "${RED}Error: Unknown category '$category'${NC}"
            echo ""
            echo "Available categories:"
            echo "  zodiac       - 12 zodiac signs"
            echo "  chakras      - 7 chakra centers"
            echo "  numerology   - 12 numerology numbers"
            echo "  moon         - 8 moon phases"
            echo "  doshas       - 3 Ayurvedic doshas"
            echo "  hd           - 5 Human Design types"
            echo "  backgrounds  - 6 spiritual backgrounds"
            echo "  all          - Generate all assets"
            exit 1
            ;;
    esac

    END_TIME=$(date +%s)
    DURATION=$((END_TIME - START_TIME))

    echo -e "\n${GREEN}╔════════════════════════════════════════════════════════════════╗${NC}"
    echo -e "${GREEN}║                    Generation Complete!                       ║${NC}"
    echo -e "${GREEN}╚════════════════════════════════════════════════════════════════╝${NC}"
    echo ""
    echo "Time elapsed: $((DURATION / 60))m $((DURATION % 60))s"
    echo "Output directory: $OUTPUT_DIR"
    echo ""

    # Count generated files
    TOTAL_FILES=$(find "$OUTPUT_DIR" -type f -name "*.png" -o -name "*.jpg" -o -name "*.webp" | wc -l)
    echo "Total images: $TOTAL_FILES"

    # Estimate cost
    COST_PER_IMAGE=0.030  # Flux Dev cost
    if [ "$MODEL" = "flux-schnell" ]; then
        COST_PER_IMAGE=0.003
    elif [ "$MODEL" = "sdxl" ]; then
        COST_PER_IMAGE=0.020
    fi

    TOTAL_COST=$(echo "$TOTAL_FILES * $COST_PER_IMAGE" | bc)
    echo "Estimated cost: \$${TOTAL_COST}"
    echo ""
}

# Run main function
main "$@"
