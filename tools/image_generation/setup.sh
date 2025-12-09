#!/bin/bash

# SpiritAtlas Image Generation Setup Script
# Automated setup for the image generation system

set -e  # Exit on error

echo "=========================================="
echo "SpiritAtlas Image Generation Setup"
echo "=========================================="
echo ""

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Determine project root
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
LOCAL_PROPERTIES="$PROJECT_ROOT/local.properties"

echo "Project root: $PROJECT_ROOT"
echo "Script directory: $SCRIPT_DIR"
echo ""

# Step 1: Check Python version
echo "Step 1/6: Checking Python installation..."
if command -v python3 &> /dev/null; then
    PYTHON_VERSION=$(python3 --version)
    echo -e "${GREEN}✓${NC} Found $PYTHON_VERSION"
else
    echo -e "${RED}✗${NC} Python 3 not found!"
    echo "Please install Python 3.8 or higher from https://www.python.org/downloads/"
    exit 1
fi
echo ""

# Step 2: Create directories
echo "Step 2/6: Creating directories..."
mkdir -p "$SCRIPT_DIR/generated_assets"
mkdir -p "$SCRIPT_DIR/test_outputs"
mkdir -p "$SCRIPT_DIR/generated_assets/numerology"
mkdir -p "$SCRIPT_DIR/generated_assets/astrology"
mkdir -p "$SCRIPT_DIR/generated_assets/chakras"
mkdir -p "$SCRIPT_DIR/generated_assets/elements"
mkdir -p "$SCRIPT_DIR/generated_assets/ayurveda"
mkdir -p "$SCRIPT_DIR/generated_assets/humandesign"
mkdir -p "$SCRIPT_DIR/generated_assets/transits"
mkdir -p "$SCRIPT_DIR/generated_assets/backgrounds"
echo -e "${GREEN}✓${NC} Created output directories"
echo ""

# Step 3: Install Python dependencies
echo "Step 3/6: Installing Python dependencies..."
echo "This may take a minute..."

# Check if pip3 is available
if ! command -v pip3 &> /dev/null; then
    echo -e "${RED}✗${NC} pip3 not found!"
    echo "Please install pip3 first"
    exit 1
fi

# Install dependencies
pip3 install --quiet --upgrade pip
pip3 install --quiet fal-client replicate requests Pillow

# Verify installations
if python3 -c "import fal_client; import replicate; import requests; from PIL import Image" 2>/dev/null; then
    echo -e "${GREEN}✓${NC} Installed: fal-client, replicate, requests, Pillow"
else
    echo -e "${RED}✗${NC} Failed to install dependencies"
    echo "Try manually: pip3 install fal-client replicate requests Pillow"
    exit 1
fi
echo ""

# Step 4: Validate API keys
echo "Step 4/6: Validating API keys..."

if [ ! -f "$LOCAL_PROPERTIES" ]; then
    echo -e "${RED}✗${NC} local.properties not found at: $LOCAL_PROPERTIES"
    echo "Please create local.properties with your API keys"
    exit 1
fi

# Extract API keys
FAL_KEY=$(grep "fal.api.key=" "$LOCAL_PROPERTIES" | cut -d'=' -f2 | tr -d ' ')
REPLICATE_KEY=$(grep "replicate.api.key=" "$LOCAL_PROPERTIES" | cut -d'=' -f2 | tr -d ' ')

# Check fal.ai key
if [ -n "$FAL_KEY" ] && [ "$FAL_KEY" != "YOUR_FAL_KEY" ]; then
    echo -e "${GREEN}✓${NC} fal.ai API key found (${FAL_KEY:0:8}...)"
    export FAL_KEY="$FAL_KEY"
    FAL_CONFIGURED=true
else
    echo -e "${YELLOW}⚠${NC} fal.ai API key not configured"
    echo "  Add to local.properties: fal.api.key=YOUR_KEY"
    echo "  Get key from: https://fal.ai/dashboard/keys"
    FAL_CONFIGURED=false
fi

# Check Replicate key
if [ -n "$REPLICATE_KEY" ] && [ "$REPLICATE_KEY" != "YOUR_REPLICATE_KEY" ]; then
    echo -e "${GREEN}✓${NC} Replicate API key found (${REPLICATE_KEY:0:8}...)"
    export REPLICATE_API_TOKEN="$REPLICATE_KEY"
    REPLICATE_CONFIGURED=true
else
    echo -e "${YELLOW}⚠${NC} Replicate API key not configured"
    echo "  Add to local.properties: replicate.api.key=YOUR_KEY"
    echo "  Get key from: https://replicate.com/account/api-tokens"
    REPLICATE_CONFIGURED=false
fi

if [ "$FAL_CONFIGURED" = false ] && [ "$REPLICATE_CONFIGURED" = false ]; then
    echo ""
    echo -e "${RED}✗${NC} No API keys configured! Please add at least one API key to local.properties"
    exit 1
fi
echo ""

# Step 5: Create example prompts if prompts.json doesn't exist
echo "Step 5/6: Checking prompts.json..."
if [ ! -f "$SCRIPT_DIR/prompts.json" ]; then
    echo -e "${YELLOW}⚠${NC} prompts.json not found, will be created by scripts when needed"
else
    echo -e "${GREEN}✓${NC} prompts.json exists"
fi
echo ""

# Step 6: Run test generation
echo "Step 6/6: Running test generation..."
echo ""

if [ "$FAL_CONFIGURED" = true ]; then
    echo "Testing fal.ai image generation..."
    echo "Generating: 'Mystical sacred geometry with golden ratio spirals'"

    # Create a simple test script
    cat > "$SCRIPT_DIR/test_generation.py" << 'EOF'
import os
import sys

# Get API key from environment
fal_key = os.environ.get('FAL_KEY')
if not fal_key:
    print("ERROR: FAL_KEY environment variable not set")
    sys.exit(1)

try:
    import fal_client

    # Set API key
    os.environ['FAL_KEY'] = fal_key

    print("Submitting image generation request...")
    result = fal_client.subscribe(
        "fal-ai/flux-schnell",
        arguments={
            "prompt": "Mystical sacred geometry with golden ratio spirals, ethereal cosmic energy, spiritual art",
            "image_size": "square_hd",
            "num_inference_steps": 4,
            "num_images": 1
        }
    )

    if result and 'images' in result and len(result['images']) > 0:
        image_url = result['images'][0]['url']
        print(f"✓ Success! Generated image URL: {image_url}")

        # Download the image
        import requests
        response = requests.get(image_url)
        if response.status_code == 200:
            output_path = os.path.join(os.path.dirname(__file__), 'test_outputs', 'setup_test.png')
            with open(output_path, 'wb') as f:
                f.write(response.content)
            print(f"✓ Saved test image to: {output_path}")
            print("\nSetup complete! You're ready to generate images.")
        else:
            print("✗ Failed to download image")
            sys.exit(1)
    else:
        print("✗ No image generated")
        sys.exit(1)

except Exception as e:
    print(f"✗ Test generation failed: {str(e)}")
    sys.exit(1)
EOF

    if python3 "$SCRIPT_DIR/test_generation.py"; then
        echo ""
        echo -e "${GREEN}✓${NC} Test generation successful!"
        rm "$SCRIPT_DIR/test_generation.py"
    else
        echo ""
        echo -e "${YELLOW}⚠${NC} Test generation failed, but setup is complete"
        echo "You can still use the scripts manually"
        rm "$SCRIPT_DIR/test_generation.py"
    fi
elif [ "$REPLICATE_CONFIGURED" = true ]; then
    echo -e "${YELLOW}⚠${NC} Only Replicate configured (no fal.ai)"
    echo "Skipping test generation (slower with Replicate)"
    echo "Run manually: python quick_generate.py 'test prompt' --provider replicate"
fi

echo ""
echo "=========================================="
echo "Setup Complete!"
echo "=========================================="
echo ""
echo "Next steps:"
echo ""
echo "1. Test single image generation:"
echo "   cd $SCRIPT_DIR"
echo "   python quick_generate.py 'mystical chakra meditation'"
echo ""
echo "2. Estimate costs:"
echo "   python estimate_cost.py"
echo ""
echo "3. Generate all 59 assets:"
echo "   python generate_images.py --provider fal"
echo ""
echo "4. Read the documentation:"
echo "   cat README.md"
echo ""
echo "For help, see: $SCRIPT_DIR/README.md"
echo "=========================================="
