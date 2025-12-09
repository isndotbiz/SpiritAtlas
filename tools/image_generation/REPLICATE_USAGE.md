# Replicate.ai Image Generation - Complete Guide

This guide covers using the `replicate_generator.py` script for generating high-quality spiritual and cosmic images using Flux and Stable Diffusion models via Replicate.ai.

## Setup

### 1. Install Dependencies

```bash
cd tools/image_generation
pip install -r requirements.txt
```

Or using a virtual environment (recommended):

```bash
python3 -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate
pip install -r requirements.txt
```

### 2. Set API Token

You have two options:

**Option A: Environment Variable (Recommended)**
```bash
export REPLICATE_API_TOKEN=your_replicate_api_key_here
```

**Option B: Pass as Command-Line Argument**
```bash
python replicate_generator.py --api-token your_replicate_api_key_here -p "your prompt"
```

## Quick Start

### Generate a Simple Image

```bash
python replicate_generator.py -p "cosmic meditation mandala, sacred geometry"
```

This will:
- Use Flux Dev model (best quality)
- Generate a 1024x1024 square image
- Save to `./generated_images/` directory
- Show progress and final file path

## Command-Line Usage

### Basic Syntax

```bash
python replicate_generator.py [OPTIONS]
```

### Essential Options

| Option | Description | Example |
|--------|-------------|---------|
| `-p, --prompt` | Text prompt for generation | `-p "cosmic energy"` |
| `-s, --spiritual` | Spiritual concept preset | `-s "chakra alignment"` |
| `-m, --model` | Model to use | `-m flux-dev` |
| `-a, --aspect-ratio` | Preset aspect ratio | `-a landscape` |
| `-w, --width` | Custom width | `-w 1024` |
| `-H, --height` | Custom height | `-H 768` |
| `-o, --output-dir` | Output directory | `-o ./my_images` |

### Examples

**1. Generate with Aspect Ratio Preset**
```bash
python replicate_generator.py -p "astral plane visualization" -a landscape
```

**2. Generate with Custom Dimensions**
```bash
python replicate_generator.py -p "chakra energy flow" -w 1024 -H 768
```

**3. Use Spiritual Preset Mode**
```bash
python replicate_generator.py -s "chakra alignment" --style "sacred geometry" -a portrait
```

**4. Generate Multiple Variations**
```bash
python replicate_generator.py -p "cosmic energy" -c 4
```

**5. Use Seed for Reproducibility**
```bash
python replicate_generator.py -p "spiritual awakening" --seed 42
```

**6. Use SDXL with Negative Prompt**
```bash
python replicate_generator.py -p "beautiful mandala" -m sdxl -n "blurry, low quality, distorted"
```

**7. Use Faster Flux Schnell Model**
```bash
python replicate_generator.py -p "quick spiritual art" -m flux-schnell --steps 4
```

## Aspect Ratios

Available presets via `-a, --aspect-ratio`:

| Preset | Dimensions | Best For |
|--------|------------|----------|
| `square` | 1024×1024 | Social media, icons, profile images |
| `portrait` | 768×1024 | Vertical artwork, phone wallpapers |
| `landscape` | 1024×768 | Desktop wallpapers, headers |
| `wide` | 1280×720 | Wide banners, YouTube thumbnails |
| `ultrawide` | 1536×640 | Ultra-wide monitors, cinematic |
| `instagram` | 1080×1080 | Instagram posts |
| `story` | 1080×1920 | Instagram/Facebook stories |

## Models

### Flux Dev (Default)
- **Model ID**: `flux-dev`
- **Best for**: Highest quality, production images
- **Speed**: Slower (~20-30s per image)
- **Cost**: ~$0.030 per image
- **Recommended steps**: 28
- **Recommended guidance**: 3.5

```bash
python replicate_generator.py -p "spiritual art" -m flux-dev --steps 28 --guidance 3.5
```

### Flux Schnell
- **Model ID**: `flux-schnell`
- **Best for**: Fast iterations, previews
- **Speed**: Fast (~5-10s per image)
- **Cost**: ~$0.003 per image
- **Recommended steps**: 4
- **Recommended guidance**: 3.5

```bash
python replicate_generator.py -p "spiritual art" -m flux-schnell --steps 4
```

### SDXL (Stable Diffusion XL)
- **Model ID**: `sdxl`
- **Best for**: Alternative style, supports negative prompts
- **Speed**: Medium (~15-20s per image)
- **Cost**: ~$0.020 per image
- **Recommended steps**: 50
- **Recommended guidance**: 7.5

```bash
python replicate_generator.py -p "spiritual art" -m sdxl --steps 50 --guidance 7.5 -n "blurry"
```

## Parameters Guide

### Generation Quality

**Inference Steps** (`--steps`)
- Controls generation quality and detail
- More steps = higher quality but slower
- Flux Dev: 4-28 (default: 28)
- Flux Schnell: 1-4 (default: 4)
- SDXL: 20-50 (default: 50)

**Guidance Scale** (`--guidance`)
- Controls how closely model follows the prompt
- Higher = more literal interpretation
- Flux: 2.0-5.0 (default: 3.5)
- SDXL: 5.0-15.0 (default: 7.5)

**Examples:**
```bash
# High quality, detailed
python replicate_generator.py -p "detailed mandala" --steps 50 --guidance 5.0

# Fast preview
python replicate_generator.py -p "concept art" -m flux-schnell --steps 4

# Balanced
python replicate_generator.py -p "spiritual scene" --steps 28 --guidance 3.5
```

### Output Options

**Count** (`-c, --count`)
- Number of images to generate (1-4)
- All images use the same settings

```bash
python replicate_generator.py -p "cosmic energy" -c 4
```

**Format** (`--format`)
- Output format: `png`, `jpg`, `webp`
- Default: `png`

**Quality** (`--quality`)
- Output quality: 1-100
- Default: 90
- Only applies to jpg/webp

```bash
python replicate_generator.py -p "spiritual art" --format jpg --quality 95
```

**Seed** (`--seed`)
- Random seed for reproducibility (0-2147483647)
- Same seed + settings = same image

```bash
python replicate_generator.py -p "mandala" --seed 42
```

### Negative Prompts (SDXL Only)

Use `-n, --negative` to specify things to avoid:

```bash
python replicate_generator.py \
  -p "beautiful spiritual mandala" \
  -m sdxl \
  -n "blurry, low quality, distorted, photorealistic, modern"
```

## Python API Usage

You can also use the generator programmatically:

### Basic Example

```python
from replicate_generator import ReplicateImageGenerator

# Initialize
generator = ReplicateImageGenerator(
    api_token="your_replicate_api_key_here",
    output_dir="./my_images"
)

# Generate an image
paths = generator.generate_image(
    prompt="cosmic meditation scene with stars and galaxies",
    aspect_ratio="square",
    model="flux-dev"
)

print(f"Generated: {paths[0]}")
```

### Advanced Example

```python
from replicate_generator import ReplicateImageGenerator

# Initialize
generator = ReplicateImageGenerator(output_dir="./spiritual_assets")

# Generate with custom parameters
paths = generator.generate_image(
    prompt="seven chakras aligned in cosmic meditation",
    model="flux-dev",
    width=1024,
    height=1536,
    num_inference_steps=28,
    guidance_scale=3.5,
    num_outputs=1,
    output_format="png",
    output_quality=90,
    seed=12345
)

for path in paths:
    print(f"Saved: {path}")
```

### Using Spiritual Preset

```python
from replicate_generator import ReplicateImageGenerator

generator = ReplicateImageGenerator()

# Use spiritual preset for quick generation
paths = generator.generate_spiritual_image(
    concept="chakra alignment",
    style="sacred geometry",
    aspect_ratio="portrait",
    model="flux-dev",
    num_outputs=4
)
```

## SpiritAtlas Use Cases

### Zodiac Signs

```bash
# Generate all 12 zodiac signs
for sign in "Aries" "Taurus" "Gemini" "Cancer" "Leo" "Virgo" "Libra" "Scorpio" "Sagittarius" "Capricorn" "Aquarius" "Pisces"; do
  python replicate_generator.py \
    -s "$sign zodiac constellation" \
    --style "cosmic ethereal" \
    -a square \
    -o ./zodiac_signs
done
```

### Chakras

```bash
# Root Chakra
python replicate_generator.py -s "root chakra Muladhara" --style "mandala sacred geometry" -a square

# Sacral Chakra
python replicate_generator.py -s "sacral chakra Svadhisthana" --style "flowing water energy" -a square

# Solar Plexus
python replicate_generator.py -s "solar plexus chakra Manipura" --style "fire sacred geometry" -a square

# Heart Chakra
python replicate_generator.py -s "heart chakra Anahata" --style "green light love energy" -a square

# Throat Chakra
python replicate_generator.py -s "throat chakra Vishuddha" --style "blue communication energy" -a square

# Third Eye
python replicate_generator.py -s "third eye chakra Ajna" --style "indigo intuition energy" -a square

# Crown Chakra
python replicate_generator.py -s "crown chakra Sahasrara" --style "violet divine light" -a square
```

### Numerology

```bash
# Life Path Numbers
for num in {1..9}; do
  python replicate_generator.py \
    -p "life path number $num, mystical symbols, sacred geometry, spiritual energy" \
    -a square \
    -o ./numerology
done

# Master Numbers
python replicate_generator.py -p "master number 11, spiritual awakening, sacred geometry" -a square
python replicate_generator.py -p "master number 22, master builder, sacred geometry" -a square
python replicate_generator.py -p "master number 33, master teacher, sacred geometry" -a square
```

### Moon Phases

```bash
python replicate_generator.py -p "new moon, dark sky, mystical energy" -a wide -o ./moon_phases
python replicate_generator.py -p "waxing crescent moon, night sky, spiritual glow" -a wide -o ./moon_phases
python replicate_generator.py -p "first quarter moon, half illuminated, cosmic energy" -a wide -o ./moon_phases
python replicate_generator.py -p "waxing gibbous moon, nearly full, spiritual light" -a wide -o ./moon_phases
python replicate_generator.py -p "full moon, bright glow, mystical night sky" -a wide -o ./moon_phases
python replicate_generator.py -p "waning gibbous moon, spiritual energy, night sky" -a wide -o ./moon_phases
python replicate_generator.py -p "last quarter moon, half moon, cosmic balance" -a wide -o ./moon_phases
python replicate_generator.py -p "waning crescent moon, fading light, mystical" -a wide -o ./moon_phases
```

### Human Design Types

```bash
python replicate_generator.py -s "Generator energy type" --style "sacred geometry" -a portrait
python replicate_generator.py -s "Manifesting Generator aura" --style "dynamic energy flow" -a portrait
python replicate_generator.py -s "Projector aura visualization" --style "ethereal cosmic" -a portrait
python replicate_generator.py -s "Manifestor energy field" --style "powerful sacred geometry" -a portrait
python replicate_generator.py -s "Reflector aura lunar energy" --style "mirror cosmic light" -a portrait
```

### Ayurveda Doshas

```bash
python replicate_generator.py -s "Vata dosha air element" --style "flowing ethereal" -a square
python replicate_generator.py -s "Pitta dosha fire element" --style "warm energy sacred geometry" -a square
python replicate_generator.py -s "Kapha dosha earth water elements" --style "grounded stable energy" -a square
```

### Backgrounds

```bash
python replicate_generator.py -p "cosmic void, stars, galaxies, deep space" -a landscape -o ./backgrounds
python replicate_generator.py -p "ethereal clouds, spiritual realm, soft light" -a landscape -o ./backgrounds
python replicate_generator.py -p "sacred temple interior, mystical atmosphere" -a landscape -o ./backgrounds
python replicate_generator.py -p "meditation space, peaceful, spiritual energy" -a landscape -o ./backgrounds
```

## Batch Generation Script

Create a batch script for generating multiple images:

```bash
#!/bin/bash
# batch_generate.sh

OUTPUT_DIR="./spiritual_assets"
MODEL="flux-dev"

# Zodiac signs
for sign in Aries Taurus Gemini Cancer Leo Virgo Libra Scorpio Sagittarius Capricorn Aquarius Pisces; do
  echo "Generating $sign..."
  python replicate_generator.py \
    -s "$sign zodiac constellation" \
    --style "cosmic ethereal" \
    -m $MODEL \
    -a square \
    -o "$OUTPUT_DIR/zodiac"
done

# Chakras
declare -a chakras=("root chakra" "sacral chakra" "solar plexus" "heart chakra" "throat chakra" "third eye" "crown chakra")
for chakra in "${chakras[@]}"; do
  echo "Generating $chakra..."
  python replicate_generator.py \
    -s "$chakra" \
    --style "sacred geometry mandala" \
    -m $MODEL \
    -a square \
    -o "$OUTPUT_DIR/chakras"
done

echo "Batch generation complete!"
```

Make it executable and run:
```bash
chmod +x batch_generate.sh
./batch_generate.sh
```

## Troubleshooting

### API Token Issues
```
Error: Replicate API token not found
```
**Solution**: Set the `REPLICATE_API_TOKEN` environment variable:
```bash
export REPLICATE_API_TOKEN=your_replicate_api_key_here
```

### Dimension Errors
```
Error: Width and height must be multiples of 32
```
**Solution**: Use dimensions like 512, 768, 1024, or preset aspect ratios:
```bash
python replicate_generator.py -p "prompt" -a square
```

### Model Errors
```
ModelError: Prediction failed
```
**Solutions**:
- Check prompt for inappropriate content
- Try reducing image size
- Switch to a different model
- Add retry delay: script automatically retries 3 times

### Out of Memory
```
Error: Out of memory
```
**Solutions**:
- Reduce image dimensions: `-w 768 -H 768`
- Use flux-schnell instead: `-m flux-schnell`
- Reduce inference steps: `--steps 20`

### Rate Limiting
```
ReplicateError: Rate limit exceeded
```
**Solution**: Wait 60 seconds between requests or upgrade your Replicate plan.

## Cost Management

### Approximate Costs

| Model | Per Image | 10 Images | 100 Images |
|-------|-----------|-----------|------------|
| Flux Dev | $0.030 | $0.30 | $3.00 |
| Flux Schnell | $0.003 | $0.03 | $0.30 |
| SDXL | $0.020 | $0.20 | $2.00 |

### Tips for Reducing Costs

1. **Use Flux Schnell for testing**
   - 10x cheaper than Flux Dev
   - Still good quality for previews

2. **Generate multiple variations at once**
   - Use `-c 4` instead of 4 separate runs
   - Saves on API overhead

3. **Use seeds for reproducibility**
   - Test with one image
   - Once satisfied, use same seed for production

4. **Batch similar images**
   - Group by aspect ratio and model
   - Reduces setup overhead

## Advanced Tips

### Prompt Engineering for Spiritual Images

**Good prompts include:**
- Style keywords: "ethereal", "mystical", "sacred geometry"
- Lighting: "soft glow", "cosmic light", "divine radiance"
- Colors: "violet", "indigo", "golden", "rainbow"
- Elements: "stars", "galaxies", "energy fields", "auras"

**Examples:**
```bash
# Good prompt
python replicate_generator.py -p "ethereal chakra meditation, sacred geometry mandala, soft purple and gold glow, cosmic stars background, spiritual energy, high detail"

# Even better with style consistency
python replicate_generator.py -p "ethereal watercolor art: chakra meditation scene, sacred geometry mandala, soft purple and gold glow, cosmic stars, spiritual energy, dreamy atmosphere"
```

### Consistent Style Across Images

Add a style prefix to all prompts:

```bash
STYLE="ethereal spiritual art with sacred geometry: "

python replicate_generator.py -p "${STYLE}chakra alignment"
python replicate_generator.py -p "${STYLE}zodiac constellation"
python replicate_generator.py -p "${STYLE}moon phases"
```

### Organizing Output

Create organized directory structure:

```bash
# Create directories
mkdir -p spiritual_assets/{zodiac,chakras,numerology,moon,backgrounds}

# Generate into specific directories
python replicate_generator.py -p "Aries constellation" -o ./spiritual_assets/zodiac
python replicate_generator.py -p "root chakra" -o ./spiritual_assets/chakras
```

## Integration with Android

After generating images, convert and copy to Android resources:

```bash
#!/bin/bash
# convert_for_android.sh

SOURCE_DIR="./generated_images"
ANDROID_RES="/Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res"

# Convert to WebP for smaller file size
for img in $SOURCE_DIR/*.png; do
  filename=$(basename "$img" .png)

  # Convert to WebP
  cwebp -q 90 "$img" -o "$SOURCE_DIR/${filename}.webp"

  # Copy to Android drawable
  cp "$SOURCE_DIR/${filename}.webp" "$ANDROID_RES/drawable/"

  echo "Converted: ${filename}.webp"
done

echo "All images converted and copied to Android resources"
```

## Resources

- [Replicate Documentation](https://replicate.com/docs)
- [Replicate Python Client](https://github.com/replicate/replicate-python)
- [Flux Models](https://replicate.com/collections/flux)
- [SDXL Model](https://replicate.com/stability-ai/sdxl)

## License

MIT License
