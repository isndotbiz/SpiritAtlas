# Replicate.ai Image Generation Setup - Complete ✓

## What Was Created

The following files have been created in `/Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation/`:

### Core Script
- **`replicate_generator.py`** - Main Python script for image generation
  - Supports Flux1.dev, Flux Schnell, and SDXL models
  - Command-line interface with extensive options
  - Python API for programmatic use
  - Error handling and retry logic
  - Progress tracking
  - Multiple aspect ratio presets
  - Spiritual preset mode

### Documentation
- **`REPLICATE_USAGE.md`** - Complete usage guide (30+ pages)
  - Detailed setup instructions
  - All command-line options explained
  - Python API examples
  - SpiritAtlas-specific use cases
  - Troubleshooting guide
  - Cost management tips

- **`QUICK_REFERENCE.md`** - Quick reference card
  - Most common commands
  - Quick examples
  - Model comparison table
  - Troubleshooting shortcuts

### Helper Scripts
- **`test_replicate.py`** - Setup validation script
  - Tests package installation
  - Validates API token
  - Optional generation test

- **`generate_spiritual_assets.sh`** - Batch generation script
  - Generate all SpiritAtlas assets
  - Category-based generation (zodiac, chakras, numerology, etc.)
  - Progress tracking
  - Cost estimation

### Dependencies
- **`requirements.txt`** - Updated with replicate package
  - Already includes fal-client, replicate, requests, Pillow

---

## Quick Start Guide

### 1. Install Dependencies (1 minute)

```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation
pip install -r requirements.txt
```

### 2. Set API Token (30 seconds)

```bash
export REPLICATE_API_TOKEN=your_replicate_api_key_here
```

**Tip:** Add to `~/.zshrc` or `~/.bashrc` to make permanent:
```bash
echo 'export REPLICATE_API_TOKEN=your_replicate_api_key_here' >> ~/.zshrc
source ~/.zshrc
```

### 3. Test Installation (1 minute)

```bash
python test_replicate.py
```

This will:
- ✓ Verify replicate package is installed
- ✓ Check API token is set
- ✓ Optionally generate a test image

### 4. Generate Your First Image (30 seconds)

```bash
python replicate_generator.py -p "cosmic meditation mandala, sacred geometry"
```

Your image will be saved to `./generated_images/`

---

## Common Usage Examples

### Quick Spiritual Image
```bash
python replicate_generator.py -s "chakra alignment" --style "sacred geometry" -a square
```

### Generate with Specific Aspect Ratio
```bash
python replicate_generator.py -p "astrology zodiac constellation" -a landscape
```

### Generate Multiple Variations
```bash
python replicate_generator.py -p "cosmic energy" -c 4 --seed 42
```

### Use Faster Model for Testing
```bash
python replicate_generator.py -p "test image" -m flux-schnell --steps 4
```

### Batch Generate SpiritAtlas Assets
```bash
# All chakra images
./generate_spiritual_assets.sh chakras

# All zodiac signs
./generate_spiritual_assets.sh zodiac

# Everything (53 images)
./generate_spiritual_assets.sh all
```

---

## Models Available

| Model | Speed | Cost/Image | Quality | Best For |
|-------|-------|------------|---------|----------|
| **flux-dev** | 20-30s | $0.030 | Highest | Production images |
| **flux-schnell** | 5-10s | $0.003 | Good | Testing, previews |
| **sdxl** | 15-20s | $0.020 | High | Alternative style |

### Model Selection Tips

**Use Flux Dev when:**
- Creating final production images
- Quality is top priority
- You need the best prompt following

**Use Flux Schnell when:**
- Testing prompts and iterations
- Budget is limited (10x cheaper)
- Speed is important

**Use SDXL when:**
- You need negative prompts
- Want a different aesthetic
- Need an alternative to Flux

---

## File Structure

```
tools/image_generation/
├── replicate_generator.py          # Main generation script
├── test_replicate.py               # Setup test script
├── generate_spiritual_assets.sh    # Batch generation script
├── requirements.txt                # Python dependencies
├── REPLICATE_USAGE.md             # Complete guide
├── QUICK_REFERENCE.md             # Quick reference
├── REPLICATE_SETUP_COMPLETE.md    # This file
└── generated_images/              # Output directory (auto-created)
    ├── 20241209_120530_flux-dev_1024x1024_cosmic_meditation.png
    └── ...
```

---

## Python API Usage

```python
from replicate_generator import ReplicateImageGenerator

# Initialize
generator = ReplicateImageGenerator(
    api_token="your_replicate_api_key_here",
    output_dir="./my_images"
)

# Generate a spiritual image
paths = generator.generate_spiritual_image(
    concept="chakra alignment",
    style="sacred geometry",
    aspect_ratio="square",
    model="flux-dev"
)

print(f"Generated: {paths[0]}")

# Or use direct prompt
paths = generator.generate_image(
    prompt="cosmic meditation with sacred geometry",
    width=1024,
    height=1024,
    model="flux-dev",
    num_inference_steps=28,
    guidance_scale=3.5
)
```

---

## SpiritAtlas Asset Categories

The batch script can generate these categories:

### Zodiac Signs (12 images)
```bash
./generate_spiritual_assets.sh zodiac
```
Aries, Taurus, Gemini, Cancer, Leo, Virgo, Libra, Scorpio, Sagittarius, Capricorn, Aquarius, Pisces

### Chakras (7 images)
```bash
./generate_spiritual_assets.sh chakras
```
Root, Sacral, Solar Plexus, Heart, Throat, Third Eye, Crown

### Numerology (12 images)
```bash
./generate_spiritual_assets.sh numerology
```
Life Path 1-9, Master Numbers 11, 22, 33

### Moon Phases (8 images)
```bash
./generate_spiritual_assets.sh moon
```
New, Waxing Crescent, First Quarter, Waxing Gibbous, Full, Waning Gibbous, Last Quarter, Waning Crescent

### Ayurvedic Doshas (3 images)
```bash
./generate_spiritual_assets.sh doshas
```
Vata, Pitta, Kapha

### Human Design Types (5 images)
```bash
./generate_spiritual_assets.sh hd
```
Generator, Manifesting Generator, Projector, Manifestor, Reflector

### Backgrounds (6 images)
```bash
./generate_spiritual_assets.sh backgrounds
```
Cosmic void, Ethereal clouds, Sacred temple, Meditation space, Aurora, Nebula

### Generate All Assets (53 images)
```bash
./generate_spiritual_assets.sh all
```

**Estimated cost:** ~$1.59 (with flux-dev) or ~$0.16 (with flux-schnell)
**Estimated time:** ~15-30 minutes

---

## Cost Management

### Cost Breakdown

| Asset Set | Images | Cost (Flux Dev) | Cost (Flux Schnell) |
|-----------|--------|-----------------|---------------------|
| Zodiac | 12 | $0.36 | $0.04 |
| Chakras | 7 | $0.21 | $0.02 |
| Numerology | 12 | $0.36 | $0.04 |
| Moon Phases | 8 | $0.24 | $0.02 |
| Doshas | 3 | $0.09 | $0.01 |
| Human Design | 5 | $0.15 | $0.02 |
| Backgrounds | 6 | $0.18 | $0.02 |
| **Total** | **53** | **$1.59** | **$0.16** |

### Cost Saving Tips

1. **Test with Flux Schnell first**
   ```bash
   python replicate_generator.py -p "test" -m flux-schnell --steps 4
   ```

2. **Use seeds for reproducibility**
   ```bash
   python replicate_generator.py -p "final" --seed 42
   ```

3. **Generate multiple at once**
   ```bash
   python replicate_generator.py -p "variants" -c 4
   ```

---

## Troubleshooting

### Issue: "API token not found"
**Solution:**
```bash
export REPLICATE_API_TOKEN=your_replicate_api_key_here
```

### Issue: "Width and height must be multiples of 32"
**Solution:** Use preset aspect ratios:
```bash
python replicate_generator.py -p "prompt" -a square
```

### Issue: "ModuleNotFoundError: No module named 'replicate'"
**Solution:**
```bash
pip install -r requirements.txt
```

### Issue: Generation is too slow
**Solution:** Use Flux Schnell:
```bash
python replicate_generator.py -p "prompt" -m flux-schnell --steps 4
```

### Issue: Images don't match spiritual aesthetic
**Solution:** Use spiritual preset mode:
```bash
python replicate_generator.py -s "concept" --style "sacred geometry"
```

---

## Next Steps

### 1. Test the Setup
```bash
python test_replicate.py
```

### 2. Generate a Sample Image
```bash
python replicate_generator.py -s "test chakra" -a square
```

### 3. Review Documentation
- Read `REPLICATE_USAGE.md` for detailed guide
- Check `QUICK_REFERENCE.md` for quick commands

### 4. Generate Assets for Your App
```bash
# Start with one category
./generate_spiritual_assets.sh chakras

# Review output
ls -lh generated_assets/chakras/

# If satisfied, generate all
./generate_spiritual_assets.sh all
```

### 5. Integrate with Android
Copy generated images to your Android project:
```bash
cp generated_assets/**/*.png /Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res/drawable/
```

---

## Resources

### Documentation Files
- **Complete Guide:** `REPLICATE_USAGE.md`
- **Quick Reference:** `QUICK_REFERENCE.md`
- **This Summary:** `REPLICATE_SETUP_COMPLETE.md`

### Online Resources
- [Replicate Documentation](https://replicate.com/docs)
- [Replicate Python Client](https://github.com/replicate/replicate-python)
- [Flux Models Collection](https://replicate.com/collections/flux)
- [SDXL Model Page](https://replicate.com/stability-ai/sdxl)

### Research Sources Used
- [Run a model from Python - Replicate](https://replicate.com/docs/get-started/python)
- [GitHub - replicate/replicate-python](https://github.com/replicate/replicate-python)
- [FLUX AI models on Replicate](https://replicate.com/collections/flux)
- [Run SDXL with an API – Replicate blog](https://replicate.com/blog/run-sdxl-with-an-api)

---

## Support

### Get Help
```bash
# Show all command options
python replicate_generator.py --help

# Run setup test
python test_replicate.py

# Check documentation
cat QUICK_REFERENCE.md
```

### Common Commands Reference

```bash
# Basic generation
python replicate_generator.py -p "your prompt"

# With aspect ratio
python replicate_generator.py -p "prompt" -a landscape

# Spiritual preset
python replicate_generator.py -s "concept" --style "style"

# Different model
python replicate_generator.py -p "prompt" -m flux-schnell

# Multiple outputs
python replicate_generator.py -p "prompt" -c 4

# Batch generation
./generate_spiritual_assets.sh category
```

---

## Summary

✓ **Created:** Complete Replicate.ai image generation system
✓ **Features:** Flux1.dev, Flux Schnell, and SDXL support
✓ **Interface:** CLI and Python API
✓ **Documentation:** Comprehensive guides and quick reference
✓ **Utilities:** Test script and batch generation
✓ **Ready:** For generating SpiritAtlas spiritual imagery

**API Token:** Already configured (your_replicate_api_key_here)

**Next Step:** Run `python test_replicate.py` to validate setup

---

**Created:** December 9, 2024
**Project:** SpiritAtlas Android App
**Purpose:** Generate high-quality spiritual and cosmic imagery
