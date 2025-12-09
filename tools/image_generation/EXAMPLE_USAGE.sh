#!/bin/bash
################################################################################
# Example Usage Commands for Replicate Image Generation
# Copy and paste these commands to generate SpiritAtlas images
################################################################################

# Set your API token (do this once per terminal session)
export REPLICATE_API_TOKEN=your_replicate_api_key_here

# Test the setup
python test_replicate.py

################################################################################
# BASIC EXAMPLES
################################################################################

# Generate a simple spiritual image
python replicate_generator.py -p "cosmic meditation mandala, sacred geometry"

# Use spiritual preset mode
python replicate_generator.py -s "chakra alignment" --style "sacred geometry"

# Generate with landscape aspect ratio
python replicate_generator.py -p "astral plane visualization" -a landscape

################################################################################
# ZODIAC SIGNS
################################################################################

# Single zodiac sign
python replicate_generator.py -s "Aries zodiac constellation" --style "cosmic ethereal" -a square

# All zodiac signs (using batch script)
./generate_spiritual_assets.sh zodiac

################################################################################
# CHAKRAS
################################################################################

# Single chakra
python replicate_generator.py -s "root chakra Muladhara" --style "mandala sacred geometry" -a square

# All chakras (using batch script)
./generate_spiritual_assets.sh chakras

################################################################################
# NUMEROLOGY
################################################################################

# Life path number
python replicate_generator.py -p "life path number 7, mystical symbols, sacred geometry" -a square

# Master number
python replicate_generator.py -p "master number 11, spiritual awakening, sacred geometry" -a square

# All numerology (using batch script)
./generate_spiritual_assets.sh numerology

################################################################################
# MOON PHASES
################################################################################

# Full moon
python replicate_generator.py -p "full moon, bright mystical glow, night sky" -a wide

# All moon phases (using batch script)
./generate_spiritual_assets.sh moon

################################################################################
# AYURVEDA DOSHAS
################################################################################

# Single dosha
python replicate_generator.py -s "Vata dosha air element" --style "flowing ethereal" -a square

# All doshas (using batch script)
./generate_spiritual_assets.sh doshas

################################################################################
# HUMAN DESIGN
################################################################################

# Generator type
python replicate_generator.py -s "Generator energy type aura" --style "sacred geometry" -a portrait

# All HD types (using batch script)
./generate_spiritual_assets.sh hd

################################################################################
# BACKGROUNDS
################################################################################

# Cosmic background
python replicate_generator.py -p "cosmic void, stars, galaxies, deep space" -a landscape

# All backgrounds (using batch script)
./generate_spiritual_assets.sh backgrounds

################################################################################
# ADVANCED OPTIONS
################################################################################

# Generate multiple variations
python replicate_generator.py -p "cosmic energy" -c 4 --seed 42

# Use faster model (10x cheaper)
python replicate_generator.py -p "test image" -m flux-schnell --steps 4

# Use SDXL with negative prompt
python replicate_generator.py -p "beautiful mandala" -m sdxl -n "blurry, low quality"

# Custom dimensions
python replicate_generator.py -p "spiritual art" -w 1024 -h 768

# Save to custom directory
python replicate_generator.py -p "test" -o ~/Desktop/test_images

# High quality with more steps
python replicate_generator.py -p "detailed mandala" --steps 50 --guidance 5.0

################################################################################
# GENERATE ALL ASSETS (53 images)
################################################################################

# This will generate ALL spiritual assets for SpiritAtlas
# Estimated cost: ~$1.59 (flux-dev) or ~$0.16 (flux-schnell)
# Estimated time: 15-30 minutes
./generate_spiritual_assets.sh all

################################################################################
# COST COMPARISON
################################################################################

# Test with cheap model first
python replicate_generator.py -p "test prompt" -m flux-schnell --steps 4
# Cost: ~$0.003

# Then generate final with best quality
python replicate_generator.py -p "test prompt" -m flux-dev --steps 28
# Cost: ~$0.030

################################################################################
# TROUBLESHOOTING
################################################################################

# Check if API token is set
echo $REPLICATE_API_TOKEN

# Verify Python packages
python -c "import replicate; print('replicate installed')"

# Show help
python replicate_generator.py --help

# Test installation
python test_replicate.py
