# SpiritAtlas Image Generation - Quick Start

## Installation (One-Time Setup)

```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation
./setup.sh
```

This installs dependencies and validates your API keys.

---

## Quick Commands

### 1. Generate a Test Image (30 seconds)

```bash
# Using fal.ai (fast, recommended)
python quick_generate.py "mystical chakra meditation with cosmic energy"

# Using Replicate (slower, cheaper)
python quick_generate.py "zodiac constellation art" --provider replicate

# Save to desktop for quick preview
python quick_generate.py "sacred geometry mandala" --output ~/Desktop/test.png
```

**Output**: Image saved to `test_outputs/` or your specified location

---

### 2. Check Costs Before Generating

```bash
python estimate_cost.py
```

**Expected Output**:
```
Total assets: 59 images

fal.ai:      $2.95 (3-5 minutes)
Replicate:   $0.18 (8-12 minutes)

💡 TIP: fal.ai free tier ($5 credit) covers all assets!
```

---

### 3. Generate All 59 Assets

```bash
# Generate everything with fal.ai (recommended)
python generate_images.py --provider fal

# Generate with Replicate (budget option)
python generate_images.py --provider replicate

# Generate specific categories only
python generate_images.py --provider fal --categories numerology astrology chakras
```

**Progress Output**:
```
[1/59] life_path_1         ✓ (2.3s)
[2/59] life_path_2         ✓ (2.1s)
...
[59/59] aurora_mystical    ✓ (2.4s)

Complete! Generated 59 images in 3m 45s
Total cost: ~$2.95
```

**Output Location**: `./generated_assets/`

---

## File Structure

```
tools/image_generation/
├── README.md              # Full documentation
├── QUICK_START.md         # This file
├── setup.sh               # One-time setup
├── prompts.json           # All 59 prompts
├── quick_generate.py      # Test single images
├── estimate_cost.py       # Cost calculator
├── generate_images.py     # Full generation
├── generated_assets/      # Generated images
│   ├── numerology/        # 9 Life Path numbers
│   ├── astrology/         # 12 Zodiac signs
│   ├── chakras/           # 7 Chakras
│   ├── elements/          # 5 Elements
│   ├── ayurveda/          # 3 Doshas
│   ├── humandesign/       # 9 HD types/profiles
│   ├── transits/          # 8 Planetary transits
│   └── backgrounds/       # 6 Cosmic backgrounds
└── test_outputs/          # Quick test images
```

---

## Common Use Cases

### Test a Custom Prompt
```bash
python quick_generate.py "your custom prompt here" --size 1024x1024
```

### Generate Only Numerology Images
```bash
python generate_images.py --provider fal --categories numerology
```

### Skip Already Generated Images
```bash
python generate_images.py --provider fal --skip-existing
```

### List All Prompts Without Generating
```bash
python generate_images.py --list
```

### Compare Costs for Specific Categories
```bash
python estimate_cost.py --categories numerology astrology
```

---

## Troubleshooting

### "API key not found"
Check that `/Users/jonathanmallinger/Workspace/SpiritAtlas/local.properties` contains:
```
fal.api.key=YOUR_KEY_HERE
replicate.api.key=YOUR_KEY_HERE
```

### "Module not found"
```bash
pip3 install fal-client replicate requests Pillow
```

### "Rate limit exceeded"
Wait 60 seconds or reduce batch size:
```bash
python generate_images.py --provider fal --batch-size 3
```

### Generation Failed
Try a different provider or check API status:
- fal.ai status: https://status.fal.ai/
- Replicate status: https://status.replicate.com/

---

## Next Steps After Generation

### 1. Review Images
```bash
open generated_assets/
```

### 2. Convert to WebP (Optional, for Android)
```bash
cd generated_assets
for img in */*.png; do
  cwebp -q 90 "$img" -o "${img%.png}.webp"
done
```

### 3. Copy to Android Project
```bash
# Copy PNGs
cp generated_assets/*/*.png /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/drawable/

# Or copy WebPs
cp generated_assets/*/*.webp /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/drawable/
```

---

## Cost Summary

| Provider | Per Image | 59 Assets | Speed | Quality |
|----------|-----------|-----------|-------|---------|
| **fal.ai** | $0.05 | $2.95 | 3-5 min | Excellent |
| **Replicate** | $0.003 | $0.18 | 8-12 min | Very Good |

**Recommendation**: Use fal.ai for development (fast iterations), Replicate for budget production.

---

## Support

- Full documentation: `README.md`
- API keys setup: `local.properties`
- Get fal.ai key: https://fal.ai/dashboard/keys
- Get Replicate key: https://replicate.com/account/api-tokens

---

## Example Workflow

```bash
# 1. Setup (once)
./setup.sh

# 2. Test single image
python quick_generate.py "test mystical art"

# 3. Check costs
python estimate_cost.py

# 4. Generate all assets
python generate_images.py --provider fal

# 5. Review results
open generated_assets/

# 6. Copy to Android
cp generated_assets/*/*.png ../../../app/src/main/res/drawable/
```

**Total time**: ~5 minutes setup + 5 minutes generation = 10 minutes to full asset library!
