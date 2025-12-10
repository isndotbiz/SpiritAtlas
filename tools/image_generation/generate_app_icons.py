#!/usr/bin/env python3
"""
SpiritAtlas App Icon Generator
Generates high-quality app icons using FLUX.1 [dev] on fal.ai

Usage:
    python3 generate_app_icons.py

Budget: ~$0.50 of remaining $4.20 fal.ai credits
Cost per 1024x1024 image: ~$0.025
"""

import os
import sys
import time
from pathlib import Path

try:
    import fal_client
except ImportError:
    print("❌ fal_client not installed. Run: pip install fal-client")
    sys.exit(1)

try:
    import requests
except ImportError:
    print("❌ requests not installed. Run: pip install requests")
    sys.exit(1)


# Output directory
OUTPUT_DIR = Path(__file__).parent / "generated_icons"
OUTPUT_DIR.mkdir(exist_ok=True)


# Icon Concept Prompts
CONCEPTS = {
    "lotus_master": {
        "prompt": """A modern, minimalist app icon featuring a stylized lotus flower with 8 geometric petals.
The lotus uses a stunning vertical gradient: deep purple petals at the base (#2E1B5B)
transitioning through vibrant pink (#E91E63), warm orange (#FFA500), to bright gold
(#FFD700) and pure white at the top. The design symbolizes spiritual transformation from
darkness to enlightenment. Background is a deep cosmic gradient from rich purple (#1A0B2E)
to near-black (#0A051A) with subtle golden glow behind the lotus. Clean, geometric style
with smooth gradients. Sacred geometry using Golden Ratio proportions. Professional app
icon design, flat design aesthetic, vector-style rendering, perfect for mobile app launcher.
Centered composition, symmetrical, elegant, premium spiritual aesthetic. 8K resolution,
ultra-sharp, professional product design.""",
        "filename": "lotus_master_1024.png",
        "size": "1024x1024"
    },
    "lotus_foreground": {
        "prompt": """Isolated lotus flower icon on transparent background. 8 geometric petals arranged
symmetrically. Vertical gradient from deep purple (#2E1B5B) at bottom through vibrant
pink (#E91E63) and orange (#FFA500) to gold (#FFD700) and white at top. Clean vector
style, sacred geometry, Golden Ratio proportions. No background. Transparent PNG.
Centered, 108x108dp canvas with 66x66dp safe zone. Professional app icon foreground layer.
Ultra-sharp, 8K quality. Isolated on transparent or pure white background.""",
        "filename": "lotus_foreground_1024.png",
        "size": "1024x1024"
    },
    "lotus_background": {
        "prompt": """Deep cosmic gradient background for app icon. Smooth transition from rich purple
(#1A0B2E) to near-black (#0A051A). Subtle golden glow (#FFD700 at 15% opacity) radiating
from center. Minimalist, clean, no additional elements. 108x108dp square. Professional
app icon background layer. Perfect for spiritual/meditation app. 8K quality, smooth gradients.
No text, no symbols, just gradient background with subtle glow.""",
        "filename": "lotus_background_1024.png",
        "size": "1024x1024"
    },
    "sacred_geometry_master": {
        "prompt": """A sophisticated app icon featuring Metatron's Cube sacred geometry. 13 interconnected
circles with golden lines (#FFD700) forming the cube structure against a deep purple
cosmic background (#2E1B5B to #1A0B2E gradient). Simplified, clean design focusing on
central hexagon with 6 surrounding circles. Metallic gold lines with subtle glow.
Scattered white star particles (40-60% opacity) in background. Golden Ratio proportions
(1.618:1). Professional spiritual app icon, flat design, vector-style, premium aesthetic.
Centered, symmetrical, mathematical perfection. 8K resolution, ultra-crisp.""",
        "filename": "sacred_geometry_master_1024.png",
        "size": "1024x1024"
    },
    "cosmic_eye_master": {
        "prompt": """A minimalist third eye app icon. Stylized eye outline in soft lavender (#E0BBE4) with
cosmic galaxy iris in purple gradient (#4A148C to #7B1FA2). Golden starburst pupil (#FFD700).
Concentric ripple patterns fading outward (white, 20-40% opacity). Deep space background
(#0A0E27 to #1A1B3E gradient). Clean, modern, mystical aesthetic. Centered composition.
Perfect for spiritual intuition app. Vector-style flat design. Premium, sophisticated.
8K resolution, professional app icon design.""",
        "filename": "cosmic_eye_master_1024.png",
        "size": "1024x1024"
    },
    "zodiac_constellation_master": {
        "prompt": """An elegant constellation app icon featuring 8-12 connected stars forming geometric pattern.
Stars vary in size and brightness (white #FFFFFF, gold #FFD700, light blue #4FC3F7).
Delicate connecting lines (30% white opacity). Central brightest star as focal point.
Deep space background with nebula gradients (#0D1B2A to #1B263B to #2D3E50) and subtle
pink nebula accent (#E91E63 at 15% opacity). Celestial navigation aesthetic. Professional
astrology app icon. Vector-style, clean, modern. 8K quality.""",
        "filename": "zodiac_constellation_master_1024.png",
        "size": "1024x1024"
    },
    "minimalist_om_master": {
        "prompt": """An ultra-minimalist Om (ॐ) symbol app icon. Geometric, modern interpretation in brilliant
gold (#FFD700) on deep purple background (#2E1B5B). Subtle concentric sound wave ripples
emanating outward (white, 10-30% opacity fading). Soft golden glow behind symbol (orange
#FFA500 at 20% opacity). Clean, sophisticated, vector-style design. Sacred sound symbolism.
Professional meditation app icon. Centered, simple, iconic. 8K resolution, ultra-sharp,
premium spiritual aesthetic.""",
        "filename": "minimalist_om_master_1024.png",
        "size": "1024x1024"
    }
}


def generate_icon(prompt: str, filename: str, size: str = "1024x1024") -> str:
    """
    Generate an app icon using FLUX.1 [dev] on fal.ai

    Args:
        prompt: The image generation prompt
        filename: Output filename
        size: Image size (default: 1024x1024)

    Returns:
        Path to generated image file
    """
    print(f"\n🎨 Generating: {filename}")
    print(f"   Prompt: {prompt[:100]}...")

    try:
        # Submit generation request to fal.ai
        handler = fal_client.submit(
            "fal-ai/flux/dev",
            arguments={
                "prompt": prompt,
                "image_size": size,
                "num_inference_steps": 28,  # Good balance of quality/speed
                "guidance_scale": 3.5,  # FLUX.1 [dev] optimal range
                "num_images": 1,
                "enable_safety_checker": True,
            },
        )

        print("   ⏳ Waiting for generation...")
        result = handler.get()

        # Download generated image
        if result.get("images"):
            img_url = result["images"][0]["url"]
            print(f"   📥 Downloading from: {img_url}")

            img_data = requests.get(img_url).content

            # Save to file
            filepath = OUTPUT_DIR / filename
            with open(filepath, "wb") as f:
                f.write(img_data)

            print(f"   ✅ Saved: {filepath}")
            return str(filepath)
        else:
            print(f"   ❌ No image generated. Result: {result}")
            return None

    except Exception as e:
        print(f"   ❌ Error: {e}")
        return None


def main():
    """Generate all app icon concepts"""
    print("=" * 80)
    print("SpiritAtlas App Icon Generator")
    print("=" * 80)
    print(f"\n📁 Output directory: {OUTPUT_DIR}")
    print(f"🎯 Generating {len(CONCEPTS)} icon concepts")
    print(f"💰 Estimated cost: ~${len(CONCEPTS) * 0.025:.2f}")

    # Confirm with user
    response = input("\n🚀 Start generation? (yes/no): ").strip().lower()
    if response not in ["yes", "y"]:
        print("❌ Cancelled by user")
        return

    # Generate all concepts
    generated = []
    failed = []

    start_time = time.time()

    for concept_id, config in CONCEPTS.items():
        result = generate_icon(
            prompt=config["prompt"],
            filename=config["filename"],
            size=config["size"]
        )

        if result:
            generated.append((concept_id, result))
        else:
            failed.append(concept_id)

        # Be nice to the API - small delay between requests
        if concept_id != list(CONCEPTS.keys())[-1]:
            time.sleep(2)

    # Summary
    elapsed = time.time() - start_time
    print("\n" + "=" * 80)
    print("GENERATION SUMMARY")
    print("=" * 80)
    print(f"✅ Successfully generated: {len(generated)}/{len(CONCEPTS)}")
    print(f"❌ Failed: {len(failed)}")
    print(f"⏱️  Total time: {elapsed:.1f}s")
    print(f"💰 Estimated cost: ~${len(generated) * 0.025:.2f}")

    if generated:
        print("\n📸 Generated icons:")
        for concept_id, filepath in generated:
            print(f"   - {concept_id}: {filepath}")

    if failed:
        print("\n❌ Failed concepts:")
        for concept_id in failed:
            print(f"   - {concept_id}")

    print("\n" + "=" * 80)
    print("NEXT STEPS")
    print("=" * 80)
    print("1. Review generated icons in:", OUTPUT_DIR)
    print("2. Select your favorite concept (recommended: lotus_master)")
    print("3. Run optimization script: python3 optimize_app_icons.py")
    print("4. Update Android app resources")
    print("5. Test on real device")
    print("\n📖 Full documentation: /SPIRIT_ATLAS_ICON_CONCEPTS.md")


if __name__ == "__main__":
    main()
