# fal.ai Image Generator - Quick Start Guide

Complete guide for generating spiritual images using fal.ai's Flux models for SpiritAtlas.

## Table of Contents
- [Installation](#installation)
- [Quick Start](#quick-start)
- [Example Commands](#example-commands)
- [Python API Usage](#python-api-usage)
- [Spiritual Prompts for SpiritAtlas](#spiritual-prompts-for-spiritatlas)
- [Advanced Tips](#advanced-tips)

---

## Installation

### 1. Install Dependencies

```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation
pip install -r requirements.txt
```

This installs:
- `fal` - fal.ai Python SDK
- `requests` - For downloading images
- Other optional utilities

### 2. Verify Installation

```bash
python3 -c "from fal.client import fal; print('fal.ai SDK installed successfully!')"
```

---

## Quick Start

### Basic Usage (Single Command)

```bash
# Generate your first image
python fal_generator.py "cosmic spiral galaxy with sacred geometry patterns"
```

**Output:**
- Image saved to: `generated_images/image_YYYYMMDD_HHMMSS_1.png`
- Shows generation progress and timing
- Prints image URL and metadata

### With Your API Key

```bash
# Set environment variable (recommended)
export FAL_KEY="your_fal_api_key_here"

# Or pass directly
python fal_generator.py "your prompt" --api-key "YOUR_API_KEY"
```

---

## Example Commands

### 1. Simple Generation

```bash
# Default settings (Flux1.dev, landscape 4:3, PNG)
python fal_generator.py "mystical mandala with glowing energy"
```

### 2. Choose Model

```bash
# Flux1.dev - Highest quality (recommended for production)
python fal_generator.py "spiritual chakra visualization" --model dev

# Flux1-schnell - Fast generation (good for testing)
python fal_generator.py "moon phases in cosmic sky" --model schnell
```

### 3. Different Sizes

```bash
# Square (1024x1024) - Perfect for profile images
python fal_generator.py "aura field visualization" --size square

# High-res square (1536x1536)
python fal_generator.py "sacred geometry mandala" --size square_hd

# Portrait orientation (896x1152) - Mobile screens
python fal_generator.py "spiritual portrait background" --size portrait_4_3

# Landscape (1280x720) - Widescreen
python fal_generator.py "cosmic background scene" --size landscape_16_9
```

### 4. Custom Dimensions

```bash
# Exact pixel dimensions
python fal_generator.py "celestial alignment visualization" \
  --width 1920 \
  --height 1080
```

### 5. Multiple Images

```bash
# Generate 4 variations in one go
python fal_generator.py "cosmic energy flow visualization" \
  --num-images 4 \
  --seed 12345
```

### 6. Quality Settings

```bash
# Maximum quality (slower but better)
python fal_generator.py "intricate sacred geometry pattern" \
  --model dev \
  --steps 40 \
  --guidance 4.5

# Fast generation (quick iterations)
python fal_generator.py "test visualization" \
  --model schnell \
  --steps 4
```

### 7. Organized Output

```bash
# Save to specific directory with custom naming
python fal_generator.py "chakra system visualization" \
  --output-dir ./assets/chakras \
  --prefix chakra_root \
  --format png
```

### 8. Reproducible Results

```bash
# Use seed for consistent results
python fal_generator.py "spiritual energy visualization" \
  --seed 42 \
  --steps 35 \
  --guidance 3.8
```

---

## Python API Usage

### Basic Script

```python
from fal_generator import FalImageGenerator

# Initialize
generator = FalImageGenerator(
    api_key="your_fal_api_key_here"
)

# Generate
result = generator.generate(
    prompt="cosmic spiral galaxy with sacred geometry",
    model=FalImageGenerator.MODEL_FLUX_DEV,
    image_size="landscape_4_3",
    num_images=1
)

# Download
saved_files = generator.download_images(result, output_dir="./my_images")
print(f"Saved: {saved_files}")
```

### Advanced Script with Options

```python
from fal_generator import FalImageGenerator

# Initialize generator
generator = FalImageGenerator(
    api_key="your_fal_api_key_here"
)

# Generate with custom settings
result = generator.generate(
    prompt="mystical chakra system glowing with cosmic energy, sacred geometry",
    model=FalImageGenerator.MODEL_FLUX_DEV,
    image_size="square_hd",
    num_images=2,
    num_inference_steps=35,
    guidance_scale=4.0,
    seed=12345,
    output_format="png",
    enable_safety_checker=True
)

# Print detailed info
generator.print_result_info(result)

# Download with custom naming
saved_files = generator.download_images(
    result,
    output_dir="./chakra_images",
    prefix="chakra_visualization"
)

print(f"\nGenerated {len(saved_files)} images:")
for filepath in saved_files:
    print(f"  - {filepath}")
```

### Batch Generation Script

```python
from fal_generator import FalImageGenerator
import time

# Your API key
API_KEY = "your_fal_api_key_here"

# Prompts for different spiritual elements
prompts = {
    "life_path_1": "Number 1 in golden sacred geometry, leadership energy",
    "life_path_2": "Number 2 in sacred geometry, partnership harmony",
    "aries": "Aries ram constellation in cosmic sky, spiritual art",
    "taurus": "Taurus bull constellation in cosmic sky, spiritual art",
    # ... more prompts
}

# Initialize generator
generator = FalImageGenerator(api_key=API_KEY)

# Generate each image
for name, prompt in prompts.items():
    print(f"\nGenerating: {name}")

    result = generator.generate(
        prompt=prompt,
        model=FalImageGenerator.MODEL_FLUX_DEV,
        image_size="square",
        num_images=1,
        seed=42
    )

    saved_files = generator.download_images(
        result,
        output_dir=f"./assets/{name.split('_')[0]}",
        prefix=name
    )

    print(f"✓ Saved: {saved_files[0]}")
    time.sleep(1)  # Rate limiting

print("\n✓ All images generated!")
```

---

## Spiritual Prompts for SpiritAtlas

### Numerology (Life Path Numbers)

```bash
# Life Path 1 - Leadership
python fal_generator.py "Number 1 in golden sacred geometry with leadership energy, spiritual art style, cosmic background" --size square

# Life Path 2 - Partnership
python fal_generator.py "Number 2 in sacred geometry with harmony symbols, partnership energy, ethereal spiritual art" --size square

# Life Path 3 - Creativity
python fal_generator.py "Number 3 in sacred geometry with creative energy flowing, artistic spiritual visualization" --size square
```

### Astrology (Zodiac Signs)

```bash
# Aries
python fal_generator.py "Aries ram constellation in cosmic night sky, spiritual art, glowing stars and sacred geometry" --size square

# Taurus
python fal_generator.py "Taurus bull constellation in cosmic sky, earthy spiritual energy, sacred geometry patterns" --size square

# Gemini
python fal_generator.py "Gemini twins constellation in cosmic sky, dual energy, communication symbols, spiritual art" --size square
```

### Chakras

```bash
# Root Chakra
python fal_generator.py "Root chakra glowing red at base of spine, grounding energy, spiritual visualization, sacred geometry" --size portrait_4_3

# Sacral Chakra
python fal_generator.py "Sacral chakra glowing orange, creative energy flow, spiritual art, sacred geometry mandala" --size portrait_4_3

# Solar Plexus
python fal_generator.py "Solar plexus chakra glowing yellow, personal power energy, spiritual visualization" --size portrait_4_3
```

### Ayurveda (Doshas)

```bash
# Vata
python fal_generator.py "Vata dosha visualization, air and ether elements, flowing ethereal energy, spiritual art" --size square

# Pitta
python fal_generator.py "Pitta dosha visualization, fire and water elements, transformative energy, spiritual art" --size square

# Kapha
python fal_generator.py "Kapha dosha visualization, earth and water elements, stable grounding energy, spiritual art" --size square
```

### Human Design

```bash
# Generator Type
python fal_generator.py "Human design generator type, sacral energy center glowing, life force visualization, spiritual bodygraph" --size portrait_4_3

# Manifesting Generator
python fal_generator.py "Human design manifesting generator, multi-passionate energy, dynamic spiritual visualization" --size portrait_4_3

# Manifestor
python fal_generator.py "Human design manifestor type, initiating energy, powerful spiritual visualization" --size portrait_4_3
```

### Cosmic Backgrounds

```bash
# Nebula
python fal_generator.py "Cosmic nebula with swirling colors, spiritual energy, sacred geometry overlay, ethereal atmosphere" --size landscape_16_9

# Galaxy Spiral
python fal_generator.py "Spiral galaxy with golden ratio sacred geometry, cosmic consciousness, spiritual background" --size landscape_16_9

# Starfield
python fal_generator.py "Deep space starfield with cosmic energy, spiritual atmosphere, sacred geometric patterns" --size landscape_16_9
```

---

## Advanced Tips

### 1. Prompt Engineering for Spiritual Content

**Good spiritual prompts include:**
- **Style keywords**: "spiritual art", "ethereal", "mystical", "sacred"
- **Geometry**: "sacred geometry", "mandala", "golden ratio"
- **Energy**: "glowing", "radiating", "flowing energy"
- **Colors**: Specific color palettes matching spiritual systems
- **Atmosphere**: "cosmic", "celestial", "divine light"

**Examples:**
```bash
# Weak prompt
python fal_generator.py "chakra"

# Strong prompt
python fal_generator.py "Root chakra glowing with vibrant red energy, sacred geometry mandala, spiritual visualization, ethereal cosmic background"
```

### 2. Optimal Settings for Different Use Cases

**Production assets (best quality):**
```bash
python fal_generator.py "your prompt" \
  --model dev \
  --steps 40 \
  --guidance 4.0 \
  --size square_hd
```

**Quick iterations (fast feedback):**
```bash
python fal_generator.py "your prompt" \
  --model schnell \
  --steps 4
```

**Consistent style (same seed):**
```bash
python fal_generator.py "prompt 1" --seed 42 --steps 35
python fal_generator.py "prompt 2" --seed 42 --steps 35
# Both will have similar visual style
```

### 3. Batch Processing

Create a script to generate multiple images:

```bash
#!/bin/bash
# generate_numerology.sh

for i in {1..9}; do
  python fal_generator.py \
    "Life path number $i in sacred geometry, numerology spiritual art, cosmic energy" \
    --model dev \
    --size square \
    --prefix "life_path_$i" \
    --output-dir ./numerology \
    --seed 42
done
```

### 4. Cost Optimization

**Current pricing (Dec 2024):**
- Flux1.dev: ~$0.025 per image (1024x1024)
- Flux1-schnell: ~$0.003 per image

**Strategies:**
1. Use `--model schnell` for testing/iterations
2. Switch to `--model dev` for final production images
3. Generate multiple variations with `--num-images 4` (more efficient)
4. Use smaller sizes during development

### 5. Image Size Guide

| Size Preset | Dimensions | Best For | Example Use Case |
|-------------|-----------|----------|------------------|
| `square` | 1024×1024 | Profile images, icons | Zodiac signs, chakras |
| `square_hd` | 1536×1536 | High-res profiles | Featured spiritual art |
| `portrait_4_3` | 896×1152 | Mobile screens | Full profile backgrounds |
| `portrait_16_9` | 720×1280 | Phone displays | App splash screens |
| `landscape_4_3` | 1152×896 | Tablets | Header images |
| `landscape_16_9` | 1280×720 | Widescreen | Desktop backgrounds |

### 6. Quality Parameters Explained

**num_inference_steps** (1-50, default: 28)
- Higher = better quality but slower
- 28-35: Good balance
- 40-50: Maximum quality
- 4-10: Fast iterations

**guidance_scale** (1-20, default: 3.5)
- Higher = follows prompt more closely
- 3.0-4.0: Good for spiritual/abstract art
- 5.0+: Very literal interpretation
- 2.0-3.0: More artistic freedom

**seed** (integer)
- Use same seed for consistent style
- Different seeds = different variations
- Good for A/B testing

---

## Troubleshooting

### Error: "fal package not installed"
```bash
pip install fal
```

### Error: "API key required"
```bash
export FAL_KEY="your_fal_api_key_here"
```

### Downloads fail
```bash
# Just get URLs without downloading
python fal_generator.py "your prompt" --no-download
```

### Images don't match spiritual aesthetic
- Add more descriptive keywords to prompt
- Use "spiritual art style" in prompt
- Adjust guidance_scale (try 4.0-4.5)
- Use consistent seed for style

---

## Next Steps

1. **Test the generator:**
   ```bash
   export FAL_KEY="your_fal_api_key_here"
   python fal_generator.py "cosmic energy visualization" --model dev
   ```

2. **Review the generated image** in `generated_images/`

3. **Create your spiritual assets** using the prompt examples above

4. **Integrate into Android app** by copying to `app/src/main/res/drawable/`

---

## Resources

- **fal.ai Documentation**: https://docs.fal.ai
- **Flux Models**: https://fal.ai/models/fal-ai/flux/dev
- **Python SDK**: https://docs.fal.ai/clients/python/
- **SpiritAtlas Project**: Check main README for integration details

---

## Your API Key

```
your_fal_api_key_here
```

**Keep this secure!** Don't commit to Git or share publicly.
