# SpiritAtlas Batch Image Generation - Complete Instructions

## Overview

This document provides complete instructions for generating all 59 SpiritAtlas image assets using the batch orchestrator script.

---

## System Architecture

### What This Tool Does

1. **Parses** IMAGE_GENERATION_PROMPTS.md to extract all 36 prompts
2. **Expands** multi-image prompts (chakras, zodiac, etc.) into 59 individual assets
3. **Generates** images using Flux1.dev via fal.ai or replicate.ai
4. **Organizes** output into Android resource structure
5. **Tracks** progress with manifest for resume capability
6. **Reports** cost, time, and success/failure for each asset

### Asset Breakdown

| Category | Prompts | Assets | Examples |
|----------|---------|--------|----------|
| App Icons & Branding | 6 | 6 | Launcher icon, splash, logo, wordmark |
| Buttons & Interactive | 5 | 5 | Primary button, FAB, toggle switch |
| Background Graphics | 6 | 6 | Home, profile, settings backgrounds |
| Profile Avatars | 6 | 6 | Default placeholders, frames, badges |
| Spiritual Symbols | 8 | 31 | 7 chakras, 12 zodiac, 4 elements, 8 moon |
| Bonus UI Elements | 5 | 5 | Cards, dividers, spinners, states |
| **TOTAL** | **36** | **59** | |

---

## Installation

### Prerequisites

- Python 3.8 or higher
- pip package manager
- Internet connection
- API key from fal.ai or replicate.ai

### Step 1: Install Python Dependencies

```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation
pip install -r requirements.txt
```

**This installs:**
- `fal-client>=0.4.0` - fal.ai Python SDK
- `replicate>=0.25.0` - replicate.ai Python SDK
- `requests>=2.31.0` - HTTP requests for downloads
- `Pillow>=10.0.0` - Image processing
- `tqdm>=4.66.0` - Progress bars (optional)
- `python-dotenv>=1.0.0` - Environment variables (optional)

**Verify installation:**
```bash
python -c "import fal_client; import replicate; from PIL import Image; print('All dependencies installed!')"
```

### Step 2: Get API Key

Choose one provider (or both for flexibility):

#### Option A: fal.ai (Recommended)

**Why fal.ai:**
- ✅ Faster generation (2-3 seconds per image)
- ✅ Higher quality Flux1.dev Ultra model
- ✅ More reliable uptime
- ❌ Slightly more expensive (~$0.03/image)

**Setup:**
1. Sign up: https://fal.ai
2. Go to dashboard: https://fal.ai/dashboard
3. Navigate to "API Keys" section
4. Create new API key
5. Copy the key

**Set environment variable:**
```bash
export FAL_KEY="your_fal_api_key_here"
```

**Make permanent (recommended):**
```bash
# For zsh (macOS default)
echo 'export FAL_KEY="your_fal_api_key_here"' >> ~/.zshrc
source ~/.zshrc

# For bash
echo 'export FAL_KEY="your_fal_api_key_here"' >> ~/.bashrc
source ~/.bashrc
```

#### Option B: replicate.ai (Alternative)

**Why replicate.ai:**
- ✅ Cheaper (~$0.025/image)
- ✅ Good quality Flux1.1 Pro model
- ❌ Slower generation (5-10 seconds per image)

**Setup:**
1. Sign up: https://replicate.com
2. Go to account: https://replicate.com/account/api-tokens
3. Create API token
4. Copy the token

**Set environment variable:**
```bash
export REPLICATE_API_TOKEN="your_replicate_token_here"
```

**Make permanent:**
```bash
echo 'export REPLICATE_API_TOKEN="your_token"' >> ~/.zshrc
source ~/.zshrc
```

### Step 3: Verify Setup

```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas
python -c "from tools.image_generation.generate_all_assets import *; print('Setup verified!')"
```

---

## Usage Workflows

### Workflow 1: Sample → Review → Full (Recommended)

This is the recommended workflow to avoid wasting money on poor quality.

#### Step 1: Generate Sample Batch

```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas
python tools/image_generation/generate_all_assets.py --sample
```

**What happens:**
```
📖 Parsing IMAGE_GENERATION_PROMPTS.md...
✅ Found 59 assets to generate

💰 Estimated cost for 5 samples: $0.15

Proceed? (y/n): y

📦 SAMPLE GENERATION MODE
Generating 5 sample images for review

======================================================================
🎨 [1/5] Primary App Icon
   Variant: None
   Category: App Icons & Branding
   Size: 1024x1024
   Output: spiritatlas_app_icons_branding_primary_app_icon_1024x1024.png
   ⏳ Generating with fal...
   ✅ Generated in 2.3s (234.5 KB)
   📊 Progress: 1/5 (20.0%) | ETA: 12:34:56

[... 4 more images ...]

======================================================================
📦 SAMPLE GENERATION COMPLETE
======================================================================
✅ Successful: 5/5
❌ Failed: 0/5
⏱️  Total time: 0.2 minutes
📦 Total size: 1.2 MB
💰 Estimated cost: $0.15
📄 Manifest saved: asset_manifest.json
======================================================================

👀 Please review the sample images in:
   app/src/main/res/generated_assets/

If satisfied, run with --full to generate all 59 assets
```

#### Step 2: Review Samples

```bash
# Open in Finder (macOS)
open app/src/main/res/generated_assets/

# Or navigate manually
cd app/src/main/res/generated_assets/
ls -lR
```

**Review checklist:**
- [ ] Colors match SpiritAtlas palette (purples #6B21A8, golds #D97706, etc.)
- [ ] Sacred geometry is accurate and recognizable
- [ ] Dimensions are correct (check with Preview or image tool)
- [ ] Style is cosmic/spiritual/mystical (not photorealistic)
- [ ] Transparency works where expected (PNG backgrounds)
- [ ] Quality is high enough for production (4K, sharp details)

**If NOT satisfied:**
1. Edit IMAGE_GENERATION_PROMPTS.md to refine prompts
2. Delete `asset_manifest.json`
3. Re-run sample generation
4. Repeat until satisfied

#### Step 3: Generate Full Set

Once satisfied with samples:

```bash
python tools/image_generation/generate_all_assets.py --full
```

**What happens:**
```
🚀 FULL GENERATION MODE
Generating all 59 assets

💰 Estimated cost for all 59 assets: $1.77

Proceed? (y/n): y

======================================================================
🎨 [1/59] Primary App Icon
   ... (generates all 59 assets)
🎨 [59/59] Error State - Gentle Cosmic
   ✅ Generated in 2.4s (125.3 KB)
   📊 Progress: 59/59 (100.0%) | ETA: --:--:--

======================================================================
🎨 FULL GENERATION COMPLETE
======================================================================
✅ Successful: 59/59
❌ Failed: 0/59
⏱️  Total time: 45.3 minutes
📦 Total size: 18.7 MB
💰 Estimated cost: $1.77
📄 Manifest saved: asset_manifest.json
======================================================================

🎉 All assets generated!
   Check app/src/main/res/generated_assets/ for all images
```

### Workflow 2: Direct Full Generation

If you're confident in the prompts or have already tested:

```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas
python tools/image_generation/generate_all_assets.py --full
```

This skips the sample phase and generates all 59 assets immediately.

### Workflow 3: Resume Interrupted Generation

If generation is interrupted (network issue, API error, ctrl+C):

```bash
python tools/image_generation/generate_all_assets.py --full --resume
```

**What happens:**
- Reads `asset_manifest.json` to find completed assets
- Skips already-generated images
- Continues from where it left off
- Preserves existing successful generations

**Example output:**
```
♻️  RESUME MODE: 23 assets remaining
======================================================================
🎨 [37/59] Zodiac Constellation - Leo
   ... (continues generation)
```

---

## Advanced Usage

### Custom Provider

```bash
# Use replicate.ai instead of fal.ai
python tools/image_generation/generate_all_assets.py --full --provider replicate
```

### More Samples

```bash
# Generate 10 samples instead of 5
python tools/image_generation/generate_all_assets.py --sample --count 10
```

### Custom Prompts File

If you've created a custom prompts markdown:

```bash
python tools/image_generation/generate_all_assets.py --full --prompts /path/to/custom_prompts.md
```

### Pass API Key Directly

Instead of environment variable:

```bash
python tools/image_generation/generate_all_assets.py --sample --api-key "your_key_here"
```

### Custom Output Location

```bash
# Not recommended - script is configured for Android structure
# But possible if needed for testing
```

---

## Output Structure

All generated images are saved to:
```
app/src/main/res/generated_assets/
```

With this structure:
```
generated_assets/
├── 1_app_icons/
│   ├── spiritatlas_app_icons_branding_primary_app_icon_1024x1024.png
│   ├── spiritatlas_app_icons_branding_splash_screen_background_1080x1920.png
│   ├── spiritatlas_app_icons_branding_logo_sacred_geometry_symbol_512x512.png
│   ├── spiritatlas_app_icons_branding_logo_wordmark_spiritatlas_1200x300.png
│   ├── spiritatlas_app_icons_branding_icon_badge_notification_96x96.png
│   └── spiritatlas_app_icons_branding_foreground_adaptive_icon_432x432.png
│
├── 2_buttons/
│   ├── spiritatlas_buttons_interactive_elements_primary_action_button_background_360x120.png
│   ├── spiritatlas_buttons_interactive_elements_secondary_button_border_360x120.png
│   ├── spiritatlas_buttons_interactive_elements_floating_action_button_fab_background_128x128.png
│   ├── spiritatlas_buttons_interactive_elements_icon_button_gradient_circle_96x96.png
│   └── spiritatlas_buttons_interactive_elements_toggle_switch_track_120x48.png
│
├── 3_backgrounds/
│   ├── spiritatlas_background_graphics_home_screen_cosmic_background_1080x1920.png
│   ├── spiritatlas_background_graphics_profile_screen_background_1080x1920.png
│   ├── spiritatlas_background_graphics_settings_background_1080x1920.png
│   ├── spiritatlas_background_graphics_compatibility_analysis_background_1080x1920.png
│   ├── spiritatlas_background_graphics_meditationloading_state_background_1080x1920.png
│   └── spiritatlas_background_graphics_chakra_energy_background_1080x1920.png
│
├── 4_avatars/
│   ├── spiritatlas_profile_avatars_placeholders_default_avatar_cosmic_silhouette_masculine_512x512.png
│   ├── spiritatlas_profile_avatars_placeholders_default_avatar_cosmic_silhouette_feminine_512x512.png
│   ├── spiritatlas_profile_avatars_placeholders_default_avatar_cosmic_silhouette_nonbinary_512x512.png
│   ├── spiritatlas_profile_avatars_placeholders_avatar_frame_sacred_circle_600x600.png
│   ├── spiritatlas_profile_avatars_placeholders_avatar_ring_energy_aura_560x560.png
│   └── spiritatlas_profile_avatars_placeholders_avatar_overlay_compatibility_heart_128x128.png
│
├── 5_symbols/
│   ├── spiritatlas_spiritual_symbols_icons_chakra_visualization_complete_set_root_512x512.png
│   ├── spiritatlas_spiritual_symbols_icons_chakra_visualization_complete_set_sacral_512x512.png
│   ├── spiritatlas_spiritual_symbols_icons_chakra_visualization_complete_set_solar_plexus_512x512.png
│   ├── spiritatlas_spiritual_symbols_icons_chakra_visualization_complete_set_heart_512x512.png
│   ├── spiritatlas_spiritual_symbols_icons_chakra_visualization_complete_set_throat_512x512.png
│   ├── spiritatlas_spiritual_symbols_icons_chakra_visualization_complete_set_third_eye_512x512.png
│   ├── spiritatlas_spiritual_symbols_icons_chakra_visualization_complete_set_crown_512x512.png
│   ├── spiritatlas_spiritual_symbols_icons_zodiac_constellation_art_complete_set_aries_400x400.png
│   ├── spiritatlas_spiritual_symbols_icons_zodiac_constellation_art_complete_set_taurus_400x400.png
│   ├── ... (12 zodiac signs)
│   ├── spiritatlas_spiritual_symbols_icons_element_symbols_fire_water_air_earth_fire_256x256.png
│   ├── ... (4 elements)
│   ├── spiritatlas_spiritual_symbols_icons_moon_phase_illustrations_complete_cycle_new_moon_200x200.png
│   ├── ... (8 moon phases)
│   ├── spiritatlas_spiritual_symbols_icons_sacred_geometry_flower_of_life_512x512.png
│   ├── spiritatlas_spiritual_symbols_icons_sacred_geometry_metatrons_cube_512x512.png
│   ├── spiritatlas_spiritual_symbols_icons_yin_yang_cosmic_edition_512x512.png
│   └── spiritatlas_spiritual_symbols_icons_om_symbol_glowing_sacred_512x512.png
│
└── 6_bonus_ui/
    ├── spiritatlas_bonus_special_ui_elements_card_background_glassmorphic_720x400.png
    ├── spiritatlas_bonus_special_ui_elements_divider_line_cosmic_gradient_800x4.png
    ├── spiritatlas_bonus_special_ui_elements_loading_spinner_sacred_geometry_200x200.png
    ├── spiritatlas_bonus_special_ui_elements_success_checkmark_cosmic_256x256.png
    └── spiritatlas_bonus_special_ui_elements_error_state_gentle_cosmic_256x256.png
```

---

## Manifest File

`asset_manifest.json` tracks all generation activity:

```json
{
  "metadata": {
    "created": "2025-12-09T10:30:00.000000",
    "total_assets": 59,
    "provider": "fal",
    "version": "1.0"
  },
  "assets": {
    "abc123def456": {
      "name": "Primary App Icon",
      "variant": null,
      "filename": "spiritatlas_app_icons_branding_primary_app_icon_1024x1024.png",
      "path": "app/src/main/res/generated_assets/1_app_icons/...",
      "success": true,
      "size_kb": 234.5,
      "generated_at": "2025-12-09T10:35:22.000000"
    }
  },
  "summary": {
    "completed": 59,
    "failed": 0,
    "pending": 0
  }
}
```

**Use manifest to:**
- Resume interrupted generation
- Track which assets succeeded/failed
- See file sizes and generation times
- Audit what was generated when

---

## Cost & Time Estimates

### Sample Generation (5 images)

| Provider | Cost | Time | Best For |
|----------|------|------|----------|
| fal.ai | $0.15 | 5 min | Quick validation |
| replicate.ai | $0.13 | 8 min | Budget testing |

### Full Generation (59 images)

| Provider | Cost | Time | Best For |
|----------|------|------|----------|
| fal.ai | $1.77 | 45-60 min | Production quality |
| replicate.ai | $1.48 | 75-90 min | Budget production |

### With Expected Retries

Assume ~10% need regeneration (color issues, quality):

| Provider | Total Cost | Total Time |
|----------|-----------|------------|
| fal.ai | $1.95 | 50-65 min |
| replicate.ai | $1.63 | 80-100 min |

### Recommended Budget

**$5.00** covers:
- Sample generation
- Full generation
- 10-15 retries for refinements
- Experimentation with prompts

---

## Troubleshooting

### "API key not found"

**Problem:** Script can't find your API key

**Solutions:**
```bash
# Check if environment variable is set
echo $FAL_KEY
echo $REPLICATE_API_TOKEN

# Set it
export FAL_KEY="your_key"

# Or pass directly to script
python generate_all_assets.py --sample --api-key "your_key"

# Make permanent
echo 'export FAL_KEY="your_key"' >> ~/.zshrc
source ~/.zshrc
```

### "Module not found: fal_client"

**Problem:** Python dependencies not installed

**Solution:**
```bash
cd tools/image_generation
pip install -r requirements.txt

# Or install individually
pip install fal-client replicate requests Pillow
```

### "Generation failed: Rate limit exceeded"

**Problem:** Too many requests too fast

**Solutions:**
- Wait 60 seconds
- Upgrade API plan
- Use `--resume` to continue later
- Switch provider

### "Generation failed: Invalid image size"

**Problem:** Requested size not supported

**Solution:**
- Check IMAGE_GENERATION_PROMPTS.md for correct dimensions
- Supported sizes: 512, 768, 1024, 1536, 1920
- Most common: 512x512, 1024x1024, 1080x1920

### "Wrong colors in generated images"

**Problem:** AI approximates hex colors, may not be exact

**Solutions:**
1. Regenerate with more explicit color descriptions
2. Use post-processing (Photoshop, GIMP) to color-correct
3. Use gradient maps to force exact palette
4. Accept close approximations (usually good enough)

### Resume not working

**Problem:** `--resume` still regenerates all

**Solution:**
- Check `asset_manifest.json` exists
- Verify assets are marked `"success": true`
- Don't delete manifest between runs
- If corrupted, delete manifest and start over

---

## Post-Processing

After generation, you may want to:

### 1. Optimize File Sizes

Generated PNGs are high quality but large. Optimize:

```bash
# Using TinyPNG (online, free tier: 500/month)
# https://tinypng.com

# Using ImageOptim (macOS, free)
open -a ImageOptim app/src/main/res/generated_assets/

# Using pngquant (CLI, free)
cd app/src/main/res/generated_assets
find . -name "*.png" -exec pngquant --ext .png --force {} \;
```

**Expected savings:** 50-70% file size reduction

### 2. Color Correction

If colors don't exactly match palette:

```bash
# Using GIMP (free)
# 1. Open image
# 2. Colors → Map → Color Exchange
# 3. Set source/dest colors to match palette
# 4. Export

# Using Photoshop
# 1. Open image
# 2. Image → Adjustments → Selective Color
# 3. Adjust to match exact hex codes
# 4. Save
```

### 3. Generate Android Densities

Create density variants for different screen sizes:

```bash
# Using ImageMagick (CLI, free)
cd app/src/main/res/generated_assets/1_app_icons

# Generate all densities for app icon
convert spiritatlas_*_primary_app_icon_1024x1024.png -resize 192x192 xxxhdpi.png
convert spiritatlas_*_primary_app_icon_1024x1024.png -resize 144x144 xxhdpi.png
convert spiritatlas_*_primary_app_icon_1024x1024.png -resize 96x96 xhdpi.png
convert spiritatlas_*_primary_app_icon_1024x1024.png -resize 72x72 hdpi.png
convert spiritatlas_*_primary_app_icon_1024x1024.png -resize 48x48 mdpi.png
```

Or use Android Asset Studio: https://romannurik.github.io/AndroidAssetStudio/

### 4. Convert to WebP

For smaller file sizes in Android:

```bash
cd app/src/main/res/generated_assets
for img in **/*.png; do
  cwebp -q 90 "$img" -o "${img%.png}.webp"
done
```

---

## Integration with Android

### Move Assets to Proper Locations

```bash
# App icons → mipmap folders
mkdir -p app/src/main/res/mipmap-{m,h,xh,xxh,xxxh}dpi
# ... copy appropriate densities

# Backgrounds → drawable-nodpi or drawable-xxxhdpi
cp generated_assets/3_backgrounds/* app/src/main/res/drawable-xxxhdpi/

# Symbols → drawable
cp generated_assets/5_symbols/* app/src/main/res/drawable/

# UI elements → drawable
cp generated_assets/6_bonus_ui/* app/src/main/res/drawable/
```

### Update AndroidManifest.xml

```xml
<application
    android:icon="@mipmap/ic_launcher"
    android:roundIcon="@mipmap/ic_launcher_round"
    ...>
```

### Use in Composables

```kotlin
Image(
    painter = painterResource(id = R.drawable.spiritatlas_background_graphics_home_screen_cosmic_background_1080x1920),
    contentDescription = "Cosmic background"
)
```

---

## FAQ

**Q: Can I regenerate just one asset?**
A: Not directly. Edit the markdown to keep only that prompt, or delete it from manifest and use `--resume`.

**Q: Can I change prompts after sample generation?**
A: Yes! Edit IMAGE_GENERATION_PROMPTS.md, delete manifest, regenerate samples.

**Q: Which provider should I choose?**
A: fal.ai for speed and quality, replicate.ai for budget. Try samples with both.

**Q: Can I use my own prompts?**
A: Yes! Create a markdown file with same format and use `--prompts custom.md`.

**Q: What if I run out of API credits mid-generation?**
A: Generation stops. Add more credits, then use `--resume` to continue.

**Q: Can I generate higher resolutions?**
A: Edit prompts markdown to change dimensions. Max depends on provider (usually 2048x2048).

**Q: Are generated images licensed for commercial use?**
A: Yes, with fal.ai and replicate.ai, generated images are yours to use commercially.

---

## Support

**Script Issues:**
- Check this documentation
- Review manifest file for errors
- Create GitHub issue with error details

**API Issues:**
- fal.ai support: https://fal.ai/support
- replicate.ai support: https://replicate.com/support

**Quality Issues:**
- Edit IMAGE_GENERATION_PROMPTS.md to refine prompts
- Try different provider
- Use post-processing tools

---

**Ready to generate?**
```bash
python tools/image_generation/generate_all_assets.py --sample
```
