# fal.ai Flux Image Generator - Quickstart

**Generate high-quality spiritual images for SpiritAtlas in 3 steps.**

---

## Step 1: Install Dependencies (30 seconds)

```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation
pip install fal requests
```

**Verify installation:**
```bash
python3 -c "from fal.client import fal; print('✓ Ready!')"
```

---

## Step 2: Set API Key (10 seconds)

```bash
export FAL_KEY="your_fal_api_key_here"
```

**Or add to your shell profile (~/.zshrc or ~/.bashrc):**
```bash
echo 'export FAL_KEY="your_fal_api_key_here"' >> ~/.zshrc
source ~/.zshrc
```

---

## Step 3: Generate Your First Image (20 seconds)

```bash
python fal_generator.py "cosmic spiral galaxy with sacred geometry patterns"
```

**Output:**
```
======================================================================
Generating with fal-ai/flux/dev
======================================================================
Prompt: cosmic spiral galaxy with sacred geometry patterns
Size: landscape_4_3
Images: 1
Steps: 28
Guidance: 3.5
======================================================================

✓ Generation completed successfully!

======================================================================
GENERATION RESULT
======================================================================

Request ID: req_abc123...
Prompt: cosmic spiral galaxy with sacred geometry patterns
Seed: 1234567

Images Generated: 1

  Image 1:
    URL: https://fal.media/files/...
    Size: 1152x896
    Format: image/png

Timings:
  inference: 2.34s

======================================================================

Downloading 1 image(s)...
  ✓ Image 1: generated_images/image_20251209_000652_1.png

✓ Successfully saved 1 image(s)
  Location: generated_images/

✓ Done!
```

---

## Common Use Cases

### Generate for Mobile (Portrait)

```bash
python fal_generator.py "spiritual chakra visualization" --size portrait_4_3
```

### Generate Multiple Variations

```bash
python fal_generator.py "mystical mandala" --num-images 4 --seed 42
```

### Fast Generation (Testing)

```bash
python fal_generator.py "test image" --model schnell
```

### High Quality (Production)

```bash
python fal_generator.py "sacred geometry mandala" \
  --model dev \
  --steps 40 \
  --guidance 4.5 \
  --size square_hd
```

### Custom Size

```bash
python fal_generator.py "cosmic background" --width 1920 --height 1080
```

---

## Quick Reference

### Available Sizes
- `square` - 1024×1024
- `square_hd` - 1536×1536
- `portrait_4_3` - 896×1152
- `portrait_16_9` - 720×1280
- `landscape_4_3` - 1152×896
- `landscape_16_9` - 1280×720

### Models
- `dev` - Flux1.dev (best quality, ~$0.025/image)
- `schnell` - Flux1-schnell (fast, ~$0.003/image)

### Key Parameters
- `--num-images 1-4` - Generate variations
- `--steps 1-50` - Quality (higher = better)
- `--guidance 1-20` - Prompt adherence
- `--seed NUMBER` - Reproducibility
- `--format png|jpeg` - File format
- `--output-dir DIR` - Save location
- `--prefix NAME` - Filename prefix

---

## Example Commands

```bash
# Numerology: Life Path 1
python fal_generator.py \
  "Number 1 in golden sacred geometry, leadership energy, spiritual art" \
  --size square --prefix life_path_1

# Astrology: Aries Sign
python fal_generator.py \
  "Aries ram constellation in cosmic sky, spiritual art style" \
  --size square --prefix aries

# Chakra: Root Chakra
python fal_generator.py \
  "Root chakra glowing red at base of spine, grounding energy" \
  --size portrait_4_3 --prefix root_chakra

# Background: Cosmic Nebula
python fal_generator.py \
  "Cosmic nebula with sacred geometry overlay, ethereal atmosphere" \
  --size landscape_16_9 --prefix cosmic_bg
```

---

## Python Script Usage

```python
from fal_generator import FalImageGenerator

# Initialize
gen = FalImageGenerator(
    api_key="your_fal_api_key_here"
)

# Generate
result = gen.generate(
    prompt="cosmic spiral galaxy with sacred geometry",
    model=gen.MODEL_FLUX_DEV,
    image_size="square"
)

# Download
files = gen.download_images(result, output_dir="./my_images")
print(f"Saved: {files}")
```

---

## Troubleshooting

**Import Error:**
```bash
pip install fal requests
```

**API Key Error:**
```bash
export FAL_KEY="your-api-key"
```

**Permission Error:**
```bash
chmod +x fal_generator.py
```

---

## Next Steps

- **Full guide**: See `FAL_USAGE_GUIDE.md`
- **Python examples**: See `example_usage.py`
- **All commands**: Run `python fal_generator.py --help`

---

## Costs (Dec 2024)

- **Flux1.dev**: $0.025 per image (1024×1024)
- **Flux1-schnell**: $0.003 per image
- **Your credit**: Check https://fal.ai/dashboard

---

**You're ready to generate spiritual images! 🎨**
