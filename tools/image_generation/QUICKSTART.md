# SpiritAtlas Image Generation - Quick Start Guide

## 3-Step Process

### Step 1: Install Dependencies (2 minutes)

```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation
pip install -r requirements.txt
```

**What gets installed:**
- `fal-client` - For fal.ai image generation
- `replicate` - For replicate.ai (alternative)
- `requests` - For downloading images
- `Pillow` - For image processing

### Step 2: Set API Key (1 minute)

**Choose your provider:**

**Option A: fal.ai (Recommended - Faster)**
```bash
# Sign up: https://fal.ai
# Dashboard: https://fal.ai/dashboard
export FAL_KEY="your_api_key_here"
```

**Option B: replicate.ai (Cheaper)**
```bash
# Sign up: https://replicate.com
# API Tokens: https://replicate.com/account/api-tokens
export REPLICATE_API_TOKEN="your_token_here"
```

💡 **Make it permanent:**
```bash
echo 'export FAL_KEY="your_key"' >> ~/.zshrc
source ~/.zshrc
```

### Step 3: Generate Images

**3A. Sample Batch First (Recommended)**
```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas
python tools/image_generation/generate_all_assets.py --sample
```

This generates 5 sample images (~$0.15, ~5 minutes) for you to review.

**Review samples:**
```bash
open app/src/main/res/generated_assets/
```

**3B. Full Generation (After Review)**
```bash
python tools/image_generation/generate_all_assets.py --full
```

This generates all 59 assets (~$1.77, ~45-60 minutes).

---

## Command Reference

### Sample Generation
```bash
# Generate 5 diverse samples
python tools/image_generation/generate_all_assets.py --sample

# Generate 10 samples
python tools/image_generation/generate_all_assets.py --sample --count 10
```

### Full Generation
```bash
# Generate all 59 assets with fal.ai
python tools/image_generation/generate_all_assets.py --full

# Generate with replicate.ai (cheaper)
python tools/image_generation/generate_all_assets.py --full --provider replicate

# Resume interrupted generation
python tools/image_generation/generate_all_assets.py --full --resume
```

### Advanced Options
```bash
# Custom prompts file
python tools/image_generation/generate_all_assets.py --full --prompts custom.md

# Pass API key directly (don't export)
python tools/image_generation/generate_all_assets.py --sample --api-key "key_here"
```

---

## Cost Summary

| What | Images | fal.ai | replicate | Time |
|------|--------|--------|-----------|------|
| Sample | 5 | $0.15 | $0.13 | 5 min |
| Full Set | 59 | $1.77 | $1.48 | 45-60 min |

**Recommended Budget:** $5.00 (covers everything + retries + experiments)

---

## Output Structure

```
app/src/main/res/generated_assets/
├── 1_app_icons/           (6 images)
│   ├── spiritatlas_..._primary_app_icon_1024x1024.png
│   ├── spiritatlas_..._splash_screen_background_1080x1920.png
│   └── ...
├── 2_buttons/             (5 images)
├── 3_backgrounds/         (6 images)
├── 4_avatars/             (6 images)
├── 5_symbols/             (31 images)
│   ├── Chakras (7)
│   ├── Zodiac (12)
│   ├── Elements (4)
│   └── Moon Phases (8)
└── 6_bonus_ui/            (5 images)
```

---

## Troubleshooting

### "API key not found"
```bash
# Check if set
echo $FAL_KEY

# Set it
export FAL_KEY="your_key_here"
```

### "Module not found: fal_client"
```bash
pip install fal-client replicate requests Pillow
```

### "Generation failed"
1. Check API key is correct
2. Check internet connection
3. Verify account has credits
4. Use `--resume` to continue

---

## What Each Asset Is For

**1_app_icons (6)**
- App launcher icon (1024x1024)
- Splash screen background (1080x1920)
- Logo symbol (512x512)
- Wordmark (1200x300)
- Notification badge (96x96)
- Adaptive icon foreground (432x432)

**2_buttons (5)**
- Primary action button (360x120)
- Secondary button border (360x120)
- Floating action button (128x128)
- Icon button (96x96)
- Toggle switch track (120x48)

**3_backgrounds (6)**
- Home screen cosmic (1080x1920)
- Profile screen (1080x1920)
- Settings screen (1080x1920)
- Compatibility analysis (1080x1920)
- Meditation/loading (1080x1920)
- Chakra energy (1080x1920)

**4_avatars (6)**
- Default masculine (512x512)
- Default feminine (512x512)
- Default non-binary (512x512)
- Avatar frame (600x600)
- Avatar ring (560x560)
- Compatibility heart (128x128)

**5_symbols (31)**
- 7 Chakras (512x512 each): Root, Sacral, Solar Plexus, Heart, Throat, Third Eye, Crown
- 12 Zodiac (400x400 each): Aries through Pisces
- 4 Elements (256x256 each): Fire, Earth, Air, Water
- 8 Moon phases (200x200 each): New to Waning Crescent
- Flower of Life (512x512)
- Metatron's Cube (512x512)
- Yin Yang cosmic (512x512)
- Om symbol (512x512)

**6_bonus_ui (5)**
- Glassmorphic card (720x400)
- Gradient divider (800x4)
- Loading spinner (200x200)
- Success checkmark (256x256)
- Error state (256x256)

---

## Next Steps After Generation

1. **Review all images**
   ```bash
   open app/src/main/res/generated_assets/
   ```

2. **Optimize file sizes**
   ```bash
   # Use TinyPNG, ImageOptim, or similar
   ```

3. **Create Android densities**
   - Use Android Asset Studio for icons
   - Downscale backgrounds for different densities
   - Convert to WebP for smaller sizes

4. **Move to proper locations**
   ```bash
   # Icons to mipmap folders
   # Backgrounds to drawable folders
   # See Android integration guide in README.md
   ```

5. **Update app to use new assets**

---

## Getting Help

**Documentation:**
- Full docs: `README.md`
- Cost details: `COST_ESTIMATE.md`
- API setup: `.env.example`

**Common Issues:**
- API keys: See `.env.example`
- Installation: Check `requirements.txt`
- Errors: Check `asset_manifest.json` for details

**Support:**
- Script issues: Create GitHub issue
- API issues: Contact fal.ai or replicate.ai
- Quality issues: Edit IMAGE_GENERATION_PROMPTS.md

---

**Ready to start?** Run the sample generation:
```bash
python tools/image_generation/generate_all_assets.py --sample
```
