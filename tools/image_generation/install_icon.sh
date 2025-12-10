#!/bin/bash
#
# SpiritAtlas App Icon - One-Command Installation
#
# This script generates, optimizes, and installs the new app icon in one go.
#
# Usage:
#   ./install_icon.sh [concept_name]
#
# Examples:
#   ./install_icon.sh lotus          # Generate and install Gradient Lotus (RECOMMENDED)
#   ./install_icon.sh sacred_geometry # Generate and install Sacred Geometry
#   ./install_icon.sh cosmic_eye      # Generate and install Cosmic Eye
#   ./install_icon.sh all             # Generate all concepts for review
#
# Requirements:
#   - Python 3 with fal-client, requests, Pillow
#   - FAL_KEY environment variable set
#   - ImageMagick (for image processing)
#

set -e  # Exit on error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

# Paths
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
APP_RES="$PROJECT_ROOT/app/src/main/res"
GENERATED_DIR="$SCRIPT_DIR/generated_icons"
TEMPLATES_DIR="$SCRIPT_DIR/icon_xml_templates"

# Banner
echo -e "${PURPLE}"
echo "╔══════════════════════════════════════════════════════════════╗"
echo "║                                                              ║"
echo "║           SpiritAtlas App Icon Installation                 ║"
echo "║                                                              ║"
echo "╚══════════════════════════════════════════════════════════════╝"
echo -e "${NC}"

# Check requirements
echo -e "\n${BLUE}Checking requirements...${NC}"

if ! command -v python3 &> /dev/null; then
    echo -e "${RED}❌ Python 3 not found. Please install Python 3.${NC}"
    exit 1
fi

if ! python3 -c "import fal_client" 2>/dev/null; then
    echo -e "${YELLOW}⚠️  fal-client not installed. Installing...${NC}"
    pip3 install fal-client
fi

if ! python3 -c "import PIL" 2>/dev/null; then
    echo -e "${YELLOW}⚠️  Pillow not installed. Installing...${NC}"
    pip3 install Pillow
fi

if ! python3 -c "import requests" 2>/dev/null; then
    echo -e "${YELLOW}⚠️  requests not installed. Installing...${NC}"
    pip3 install requests
fi

if [ -z "$FAL_KEY" ]; then
    echo -e "${RED}❌ FAL_KEY environment variable not set.${NC}"
    echo -e "${YELLOW}Please set it with: export FAL_KEY='your-api-key'${NC}"
    exit 1
fi

echo -e "${GREEN}✅ All requirements met${NC}"

# Parse arguments
CONCEPT="${1:-lotus}"

if [ "$CONCEPT" == "all" ]; then
    echo -e "\n${CYAN}Generating ALL icon concepts for review...${NC}"
    cd "$SCRIPT_DIR"
    python3 generate_app_icons.py
    echo -e "\n${GREEN}✅ All concepts generated!${NC}"
    echo -e "${YELLOW}📁 Review them in: $GENERATED_DIR${NC}"
    echo -e "${YELLOW}Then run: ./install_icon.sh [concept_name] to install your favorite${NC}"
    exit 0
fi

# Map concept names to file patterns
case "$CONCEPT" in
    lotus)
        MASTER_FILE="lotus_master_1024.png"
        CONCEPT_NAME="Gradient Lotus"
        ;;
    sacred_geometry|geometry)
        MASTER_FILE="sacred_geometry_master_1024.png"
        CONCEPT_NAME="Sacred Geometry Pro"
        ;;
    cosmic_eye|eye)
        MASTER_FILE="cosmic_eye_master_1024.png"
        CONCEPT_NAME="Cosmic Eye"
        ;;
    zodiac|constellation)
        MASTER_FILE="zodiac_constellation_master_1024.png"
        CONCEPT_NAME="Zodiac Constellation"
        ;;
    om)
        MASTER_FILE="minimalist_om_master_1024.png"
        CONCEPT_NAME="Minimalist Om"
        ;;
    *)
        echo -e "${RED}❌ Unknown concept: $CONCEPT${NC}"
        echo -e "${YELLOW}Valid options: lotus, sacred_geometry, cosmic_eye, zodiac, om, all${NC}"
        exit 1
        ;;
esac

echo -e "\n${CYAN}Installing: $CONCEPT_NAME${NC}"

# Step 1: Generate icon if not exists
MASTER_PATH="$GENERATED_DIR/$MASTER_FILE"
if [ ! -f "$MASTER_PATH" ]; then
    echo -e "\n${BLUE}Step 1: Generating icon concept...${NC}"
    cd "$SCRIPT_DIR"
    python3 generate_app_icons.py

    if [ ! -f "$MASTER_PATH" ]; then
        echo -e "${RED}❌ Failed to generate icon: $MASTER_FILE${NC}"
        exit 1
    fi
    echo -e "${GREEN}✅ Icon generated${NC}"
else
    echo -e "\n${GREEN}✅ Icon already generated (using cached)${NC}"
fi

# Step 2: Optimize icon
echo -e "\n${BLUE}Step 2: Optimizing icon for Android...${NC}"
cd "$SCRIPT_DIR"
python3 optimize_app_icons.py "$MASTER_PATH"

OPTIMIZED_DIR="$GENERATED_DIR/${MASTER_FILE%.png}_optimized"
if [ ! -d "$OPTIMIZED_DIR" ]; then
    echo -e "${RED}❌ Optimization failed${NC}"
    exit 1
fi
echo -e "${GREEN}✅ Icon optimized${NC}"

# Step 3: Copy raster assets
echo -e "\n${BLUE}Step 3: Installing raster assets to app...${NC}"
for density in mdpi hdpi xhdpi xxhdpi xxxhdpi; do
    SRC_DIR="$OPTIMIZED_DIR/mipmap/mipmap-$density"
    DST_DIR="$APP_RES/mipmap-$density"

    if [ -d "$SRC_DIR" ]; then
        cp "$SRC_DIR/ic_launcher.png" "$DST_DIR/"
        cp "$SRC_DIR/ic_launcher_round.png" "$DST_DIR/"
        echo -e "  ${GREEN}✓${NC} Copied mipmap-$density"
    else
        echo -e "  ${YELLOW}⚠${NC} Missing mipmap-$density (skipping)"
    fi
done
echo -e "${GREEN}✅ Raster assets installed${NC}"

# Step 4: Copy XML templates (only for lotus concept, others need custom XML)
if [ "$CONCEPT" == "lotus" ]; then
    echo -e "\n${BLUE}Step 4: Installing vector XML layers...${NC}"

    if [ -d "$TEMPLATES_DIR" ]; then
        cp "$TEMPLATES_DIR/ic_launcher_background_lotus.xml" "$APP_RES/drawable/ic_launcher_background.xml"
        echo -e "  ${GREEN}✓${NC} Copied background layer"

        cp "$TEMPLATES_DIR/ic_launcher_foreground_lotus.xml" "$APP_RES/drawable/ic_launcher_foreground.xml"
        echo -e "  ${GREEN}✓${NC} Copied foreground layer"

        cp "$TEMPLATES_DIR/ic_launcher_monochrome_lotus.xml" "$APP_RES/drawable/ic_launcher_monochrome.xml"
        echo -e "  ${GREEN}✓${NC} Copied monochrome layer"

        cp "$TEMPLATES_DIR/ic_launcher.xml" "$APP_RES/mipmap-anydpi-v26/ic_launcher.xml"
        cp "$TEMPLATES_DIR/ic_launcher_round.xml" "$APP_RES/mipmap-anydpi-v26/ic_launcher_round.xml"
        echo -e "  ${GREEN}✓${NC} Copied adaptive icon configs"

        echo -e "${GREEN}✅ Vector XML layers installed${NC}"
    else
        echo -e "${YELLOW}⚠️  XML templates not found (using existing)${NC}"
    fi
else
    echo -e "\n${YELLOW}⚠️  Note: Vector XML templates only available for Lotus concept${NC}"
    echo -e "${YELLOW}   Using raster icons only. Consider creating custom XML vectors.${NC}"
fi

# Step 5: Build APK
echo -e "\n${BLUE}Step 5: Building APK with new icon...${NC}"
cd "$PROJECT_ROOT"
./gradlew clean assembleDebug

if [ $? -ne 0 ]; then
    echo -e "${RED}❌ Build failed${NC}"
    exit 1
fi
echo -e "${GREEN}✅ APK built successfully${NC}"

# Step 6: Install on device
echo -e "\n${BLUE}Step 6: Installing on device...${NC}"
APK_PATH="$PROJECT_ROOT/app/build/outputs/apk/debug/app-debug.apk"

if [ ! -f "$APK_PATH" ]; then
    echo -e "${RED}❌ APK not found: $APK_PATH${NC}"
    exit 1
fi

# Check if device is connected
if ! adb devices | grep -q "device$"; then
    echo -e "${YELLOW}⚠️  No Android device connected${NC}"
    echo -e "${YELLOW}Please connect a device or start an emulator, then run:${NC}"
    echo -e "${CYAN}adb install -r $APK_PATH${NC}"
else
    adb install -r "$APK_PATH"
    echo -e "${GREEN}✅ App installed on device${NC}"

    # Launch app
    echo -e "\n${BLUE}Step 7: Launching app...${NC}"
    adb shell am start -n com.spiritatlas.app/.MainActivity
    echo -e "${GREEN}✅ App launched${NC}"
fi

# Summary
echo -e "\n${PURPLE}"
echo "╔══════════════════════════════════════════════════════════════╗"
echo "║                                                              ║"
echo "║                    INSTALLATION COMPLETE                     ║"
echo "║                                                              ║"
echo "╚══════════════════════════════════════════════════════════════╝"
echo -e "${NC}"

echo -e "${GREEN}✅ Successfully installed: $CONCEPT_NAME${NC}"
echo -e "\n${CYAN}Next steps:${NC}"
echo -e "1. Check the icon on your home screen"
echo -e "2. Test different launcher shapes (Settings > Display > Icon Shape)"
echo -e "3. Test themed icons on Android 13+ (Settings > Wallpaper & Style)"
echo -e "4. Test on light and dark wallpapers"
echo -e "\n${CYAN}Generated files:${NC}"
echo -e "  📁 Optimized assets: $OPTIMIZED_DIR"
echo -e "  📁 App resources: $APP_RES"
echo -e "  📦 APK: $APK_PATH"
echo -e "\n${YELLOW}Cost: ~$0.025 (single concept) or ~$0.50 (all concepts)${NC}"
echo -e "${YELLOW}Budget remaining: ~$3.70 - $4.17 of $4.20${NC}"

echo -e "\n${PURPLE}Documentation:${NC}"
echo -e "  📖 Full guide: $PROJECT_ROOT/SPIRIT_ATLAS_ICON_CONCEPTS.md"
echo -e "  📖 Quick start: $PROJECT_ROOT/APP_ICON_QUICK_START.md"
echo -e "  📖 Comparison: $PROJECT_ROOT/ICON_COMPARISON_GUIDE.md"

echo -e "\n${GREEN}Enjoy your stunning new app icon! 🎉${NC}\n"
