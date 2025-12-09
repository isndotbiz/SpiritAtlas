# Replicate.ai Quick Reference

## Setup (One-Time)

```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation
pip install -r requirements.txt
export REPLICATE_API_TOKEN=your_replicate_api_key_here
```

## Test Installation

```bash
python test_replicate.py
```

## Most Common Commands

### Basic Generation
```bash
python replicate_generator.py -p "cosmic meditation mandala"
```

### With Aspect Ratio
```bash
python replicate_generator.py -p "spiritual art" -a square
python replicate_generator.py -p "spiritual art" -a landscape
python replicate_generator.py -p "spiritual art" -a portrait
```

### Spiritual Preset
```bash
python replicate_generator.py -s "chakra alignment" --style "sacred geometry"
```

### Different Models
```bash
# Highest quality (default)
python replicate_generator.py -p "prompt" -m flux-dev

# Fastest (10x cheaper)
python replicate_generator.py -p "prompt" -m flux-schnell --steps 4

# Alternative style
python replicate_generator.py -p "prompt" -m sdxl
```

### Multiple Variations
```bash
python replicate_generator.py -p "cosmic energy" -c 4 --seed 42
```

## Aspect Ratios

| Preset | Size | Use Case |
|--------|------|----------|
| `square` | 1024×1024 | Icons, social media |
| `portrait` | 768×1024 | Phone wallpaper |
| `landscape` | 1024×768 | Desktop wallpaper |
| `wide` | 1280×720 | Banners |
| `story` | 1080×1920 | Instagram story |

## Models

| Model | Speed | Cost | Quality |
|-------|-------|------|---------|
| `flux-dev` | Slow | $0.030 | Best |
| `flux-schnell` | Fast | $0.003 | Good |
| `sdxl` | Medium | $0.020 | High |

## SpiritAtlas Examples

```bash
# Zodiac
python replicate_generator.py -s "Aries zodiac" -a square

# Chakra
python replicate_generator.py -s "root chakra" --style "mandala" -a square

# Numerology
python replicate_generator.py -p "life path number 7, mystical" -a square

# Moon
python replicate_generator.py -p "full moon, mystical glow" -a wide

# Background
python replicate_generator.py -p "cosmic void, stars" -a landscape
```

## Help

```bash
python replicate_generator.py --help
```

## Common Issues

**API token error**: `export REPLICATE_API_TOKEN=your_token`
**Dimension error**: Use `-a square` or multiples of 32
**Rate limit**: Wait 60 seconds between requests
