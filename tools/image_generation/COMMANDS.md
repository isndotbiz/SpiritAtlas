# SpiritAtlas Image Generation - Command Reference

Quick reference for all available commands.

## Setup

```bash
# One-time setup (installs dependencies, validates API keys, runs test)
./setup.sh
```

## Quick Generate (Single Images)

```bash
# Basic usage
python quick_generate.py "your prompt here"

# With options
python quick_generate.py "prompt" --provider fal|replicate
python quick_generate.py "prompt" --size 1024x1024
python quick_generate.py "prompt" --output ~/Desktop/test.png

# Supported sizes
# 512x512, 768x768, 1024x1024, 1024x1536 (portrait), 1536x1024 (landscape)
```

## Cost Estimation

```bash
# All categories
python estimate_cost.py

# Specific categories
python estimate_cost.py --categories numerology astrology

# With detailed asset list
python estimate_cost.py --detailed
```

## Full Generation (All 59 Assets)

```bash
# List all prompts (no generation)
python generate_images.py --list

# Generate all assets
python generate_images.py --provider fal
python generate_images.py --provider replicate

# Generate specific categories
python generate_images.py --provider fal --categories numerology astrology

# Custom output directory
python generate_images.py --provider fal --output ./my_assets

# Skip existing files
python generate_images.py --provider fal --skip-existing

# Adjust concurrent requests (default: 5)
python generate_images.py --provider fal --batch-size 3
```

## Post-Processing

```bash
# Convert PNG to WebP (for Android optimization)
cd generated_assets
for img in */*.png; do
  cwebp -q 90 "$img" -o "${img%.png}.webp"
done

# Copy to Android project
cp generated_assets/*/*.png ../../../app/src/main/res/drawable/
```

## File Locations

```
Local properties:  /Users/jonathanmallinger/Workspace/SpiritAtlas/local.properties
Scripts:           /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation/
Generated images:  ./generated_assets/
Test images:       ./test_outputs/
```

## Categories

- `numerology` - 9 Life Path numbers (1-9)
- `astrology` - 12 Zodiac signs
- `chakras` - 7 Chakra centers
- `elements` - 5 Elements (Earth, Water, Fire, Air, Ether)
- `ayurveda` - 3 Dosha types (Vata, Pitta, Kapha)
- `humandesign` - 9 HD types and profiles
- `transits` - 8 Planetary transits
- `backgrounds` - 6 Cosmic backgrounds

**Total**: 59 images

## Providers

| Provider | Cost/image | Speed | Command |
|----------|-----------|-------|---------|
| fal.ai | $0.05 | 2-3s | `--provider fal` |
| Replicate | $0.003 | 8-12s | `--provider replicate` |

## Common Workflows

### Test Workflow
```bash
# 1. Test single image
python quick_generate.py "mystical test"

# 2. Check costs
python estimate_cost.py

# 3. Generate one category
python generate_images.py --provider fal --categories numerology
```

### Full Generation Workflow
```bash
# 1. Estimate costs
python estimate_cost.py

# 2. Generate all
python generate_images.py --provider fal

# 3. Review
open generated_assets/

# 4. Copy to Android
cp generated_assets/*/*.png ../../../app/src/main/res/drawable/
```

### Development Workflow
```bash
# Generate with fal.ai (fast)
python generate_images.py --provider fal

# Compare with Replicate
python generate_images.py --provider replicate --output ./replicate_comparison

# Choose best images from both
```

## Environment Variables

Set manually (optional):
```bash
export FAL_KEY="your_fal_key"
export REPLICATE_API_TOKEN="your_replicate_key"
```

Or use `local.properties` (recommended):
```properties
fal.api.key=YOUR_KEY
replicate.api.key=YOUR_KEY
```

## Help

```bash
python quick_generate.py --help
python estimate_cost.py --help
python generate_images.py --help
```
