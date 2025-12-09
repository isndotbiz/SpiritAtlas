# SpiritAtlas Image Generation Orchestrator

Automated batch image generation for all SpiritAtlas Android app assets using Flux1.dev AI model.

## Overview

This orchestrator reads `IMAGE_GENERATION_PROMPTS.md` and generates all 59 high-quality assets organized by category:

- **1_app_icons** (6 assets): App icons, logos, splash screens
- **2_buttons** (5 assets): Button backgrounds and interactive elements
- **3_backgrounds** (6 assets): Full-screen backgrounds
- **4_avatars** (6 assets): Default profile placeholders
- **5_symbols** (31 assets): Chakras (7), Zodiac (12), Elements (4), Moon phases (8)
- **6_bonus_ui** (5 assets): Special UI elements

## Features

✅ Parses prompts with all metadata (dimensions, variants, categories)
✅ Generates images using Flux1.dev via fal.ai or replicate.ai
✅ Multi-image prompt support (chakras, zodiac, elements, moon phases)
✅ Progress tracking with ETA
✅ Resume capability on interruption
✅ Asset manifest with metadata
✅ Sample batch mode for review before full generation
✅ Cost estimation

## Installation

### 1. Install Python Dependencies

```bash
cd tools/image_generation
pip install -r requirements.txt
```

### 2. Get API Key

Choose one provider:

**Option A: fal.ai (Recommended)**
```bash
# Sign up at https://fal.ai
# Get API key from dashboard
export FAL_KEY="your_fal_api_key_here"
```

**Option B: replicate.ai**
```bash
# Sign up at https://replicate.com
# Get API token from account settings
export REPLICATE_API_TOKEN="your_replicate_token_here"
```

💡 **Tip**: Add to your `~/.zshrc` or `~/.bashrc` to persist:
```bash
echo 'export FAL_KEY="your_key_here"' >> ~/.zshrc
source ~/.zshrc
```

## Usage

### Step 1: Generate Sample Batch (5 images)

Start with a sample to verify quality and style before generating all 59 assets:

```bash
# From project root
cd /Users/jonathanmallinger/Workspace/SpiritAtlas
python tools/image_generation/generate_all_assets.py --sample
```

**What happens:**
- Generates 5 diverse sample images across categories
- Shows cost estimate (~$0.15 for 5 images)
- Saves to `app/src/main/res/generated_assets/`
- Creates manifest tracking progress

**Output structure:**
```
app/src/main/res/generated_assets/
├── 1_app_icons/
│   └── spiritatlas_app_icons_branding_primary_app_icon_1024x1024.png
├── 2_buttons/
│   └── spiritatlas_buttons_interactive_elements_primary_action_button_background_360x120.png
├── 3_backgrounds/
│   └── spiritatlas_background_graphics_home_screen_cosmic_background_1080x1920.png
└── ...
```

### Step 2: Review Samples

Check the generated images:

```bash
open app/src/main/res/generated_assets/
```

**Review checklist:**
- ✅ Colors match SpiritAtlas palette (purples, golds, cosmic theme)
- ✅ Dimensions are correct
- ✅ Style is consistent with app aesthetic
- ✅ Sacred geometry is accurate
- ✅ Quality is high enough for production

### Step 3: Generate Full Set (59 assets)

If satisfied with samples, generate all assets:

```bash
python tools/image_generation/generate_all_assets.py --full
```

**What happens:**
- Generates all 59 assets
- Shows cost estimate (~$1.77 for 59 images with fal.ai)
- Progress bar with ETA
- Saves manifest after each image (resume-safe)
- Estimated time: ~45-60 minutes

**Cost breakdown:**
- fal.ai: ~$0.03/image → $1.77 total
- replicate.ai: ~$0.025/image → $1.48 total

### Step 4: Resume if Interrupted

If generation is interrupted (network issue, error, etc.):

```bash
python tools/image_generation/generate_all_assets.py --full --resume
```

This skips already-completed assets and continues from where it left off.

## Advanced Usage

### Use Replicate Instead of fal.ai

```bash
python tools/image_generation/generate_all_assets.py --full --provider replicate
```

### Generate More Samples

```bash
python tools/image_generation/generate_all_assets.py --sample --count 10
```

### Use Custom Prompts File

```bash
python tools/image_generation/generate_all_assets.py --full --prompts /path/to/prompts.md
```

### Pass API Key Directly

```bash
python tools/image_generation/generate_all_assets.py --sample --api-key "your_key"
```

## Manifest File

`asset_manifest.json` tracks all generations:

```json
{
  "metadata": {
    "created": "2025-12-09T10:30:00",
    "total_assets": 59,
    "provider": "fal"
  },
  "assets": {
    "abc123def456": {
      "name": "Primary App Icon",
      "filename": "spiritatlas_app_icons_branding_primary_app_icon_1024x1024.png",
      "path": "app/src/main/res/generated_assets/1_app_icons/...",
      "success": true,
      "size_kb": 234.5,
      "generated_at": "2025-12-09T10:35:22"
    }
  },
  "summary": {
    "completed": 45,
    "failed": 0,
    "pending": 14
  }
}
```

## Asset Details

### Multi-Image Prompts

Some prompts generate multiple variants:

**Chakras (7 images):**
- Root, Sacral, Solar Plexus, Heart, Throat, Third Eye, Crown
- Files: `spiritatlas_spiritual_symbols_icons_chakra_visualization_complete_set_root_512x512.png`, etc.

**Zodiac (12 images):**
- Aries, Taurus, Gemini, Cancer, Leo, Virgo, Libra, Scorpio, Sagittarius, Capricorn, Aquarius, Pisces
- Files: `spiritatlas_spiritual_symbols_icons_zodiac_constellation_art_complete_set_aries_400x400.png`, etc.

**Elements (4 images):**
- Fire, Earth, Air, Water
- Files: `spiritatlas_spiritual_symbols_icons_element_symbols_fire_water_air_earth_fire_256x256.png`, etc.

**Moon Phases (8 images):**
- New, Waxing Crescent, First Quarter, Waxing Gibbous, Full, Waning Gibbous, Last Quarter, Waning Crescent
- Files: `spiritatlas_spiritual_symbols_icons_moon_phase_illustrations_complete_cycle_new_moon_200x200.png`, etc.

## Troubleshooting

### "API key not found"
```bash
# Set environment variable
export FAL_KEY="your_key_here"

# Or pass directly
python generate_all_assets.py --sample --api-key "your_key"
```

### "fal_client not installed"
```bash
pip install fal-client
```

### "Generation failed"
- Check API key is valid
- Verify account has credits
- Check internet connection
- Review error in manifest file
- Use `--resume` to retry

### "Wrong colors in output"
- Flux1.dev approximates hex colors
- May need manual color correction in post-processing
- Consider using color grading tools after generation

## Post-Processing

After generation, you may want to:

1. **Color Correction**: Adjust to exact hex codes using Photoshop/GIMP
2. **Optimization**: Run through TinyPNG or ImageOptim
3. **Resizing**: Generate Android density variants (mdpi, hdpi, xhdpi, etc.)
4. **Format Conversion**: Convert some to WebP for better compression

## Cost Estimation

| Assets | fal.ai | replicate.ai |
|--------|--------|--------------|
| 5 samples | $0.15 | $0.13 |
| 59 full set | $1.77 | $1.48 |

**Budget recommendations:**
- Start with $5 credit to cover samples + full generation + retries
- Each retry/variant costs ~$0.03

## Integration with Android

Once generated, move assets to proper Android resource folders:

```bash
# App icons (use Android Asset Studio for all densities)
# https://romannurik.github.io/AndroidAssetStudio/

# Backgrounds - keep in drawable-nodpi or drawable-xxxhdpi
cp generated_assets/3_backgrounds/* app/src/main/res/drawable-xxxhdpi/

# Symbols - use as-is or convert to vectors
# Consider VectorDrawables for symbols that need scaling
```

## License

Generated images are property of SpiritAtlas project.

## Support

For issues with:
- **Script bugs**: Check GitHub issues or create new one
- **API issues**: Contact fal.ai or replicate.ai support
- **Image quality**: Adjust prompts in IMAGE_GENERATION_PROMPTS.md and regenerate
