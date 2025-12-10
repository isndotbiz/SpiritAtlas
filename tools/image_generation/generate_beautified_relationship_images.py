#!/usr/bin/env python3
"""
Beautified Relationship Images Generator for SpiritAtlas
Generates 3 enhanced relationship/compatibility images with FLUX 1.1 Pro

Agent: IMAGE BEAUTIFICATION AGENT 16-18
Budget: $0.15 (3 images × $0.05)
"""

import os
import json
import time
from pathlib import Path
from datetime import datetime

try:
    import fal_client
except ImportError:
    print("❌ fal_client not installed. Install with: pip install fal-client")
    exit(1)

try:
    import requests
except ImportError:
    print("❌ requests not installed. Install with: pip install requests")
    exit(1)

# Configuration
MODEL = "fal-ai/flux-pro/v1.1"
OUTPUT_DIR = Path("generated_images/beautified_relationship")
OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

COST_PER_IMAGE = 0.05
TOTAL_BUDGET = 0.15

# FLUX 1.1 Pro optimal settings
SETTINGS = {
    "guidance_scale": 3.5,
    "num_inference_steps": 28,
    "safety_tolerance": 2,
}

# Enhanced Prompts for 3 Relationship Images
IMAGES = [
    {
        "id": 105,
        "title": "Soul Connection Visualization",
        "width": 1080,
        "height": 1920,
        "seed": 205,
        "prompt": """Breathtaking vertical illustration of two luminous souls connecting in cosmic space. Two ethereal humanoid silhouettes facing each other in profile, one emanating mystic purple energy (#7C3AED), the other radiating sacred gold light (#D97706). Between them, a magnificent vesica piscis formation glows where their aura fields overlap, creating almond-shaped sacred geometry filled with swirling rose pink energy (#EC4899). Delicate golden threads of light connect their heart chakras and third eye chakras, forming intricate web of destiny. Seven small chakra points glow along each figure's central axis in rainbow colors. The background is deep cosmic indigo (#1E1B4B) with scattered stars and subtle nebula clouds. Flowing energy ribbons in teal and purple create sense of movement and eternal dance. Sacred geometry patterns (flower of life, infinity symbols) subtly watermarked throughout.

Style: Cinematic spiritual romance art, photorealistic energy visualization, volumetric lighting creating depth, bioluminescent glow effects, particle systems showing energy flow, dreamy ethereal atmosphere, professional digital art, 8K ultra-detailed, emotional and heartfelt, sense of profound recognition and soul-level love, cosmic romance aesthetic."""
    },
    {
        "id": 107,
        "title": "Twin Flame Union",
        "width": 1024,
        "height": 1024,
        "seed": 207,
        "prompt": """Stunning square composition of twin flame reunion. Two perfectly mirrored cosmic souls spiraling toward each other in double helix formation, one soul in violet purple (#8B5CF6) representing divine feminine, the other in sacred gold (#D97706) representing divine masculine. As they spiral closer, their energies create infinity symbol (lemniscate) flowing continuously between them. At the center convergence point, a brilliant heart-shaped merkaba star explodes with rose gold light (#EC4899, #FBBF24), radiating outward in concentric waves. Each soul figure shows mirror-image pose, suggesting perfect reflection and recognition. Sacred geometry surrounds them: overlapping circles, twin pillars of light, and subtle yin-yang formation in their unified energy field.

Background: Deep indigo cosmic space (#1E1B4B) transitioning to soft aurora colors (purple, pink, gold) around the center. Thousands of tiny sparkle particles orbit the union like celebration confetti. Subtle text-ready space in lower third for "Twin Flame Recognition" messaging.

Style: Romantic spiritual art with technical precision, photorealistic energy rendering, cinematic god rays, volumetric lighting, particle effects suggesting joyful reunion, sense of coming home, mirror symmetry, divine timing, 8K ultra HD, professional concept art quality, emotionally powerful, tears-of-joy aesthetic."""
    },
    {
        "id": 108,
        "title": "Aura Interaction - Love Connection",
        "width": 1024,
        "height": 1024,
        "seed": 208,
        "prompt": """Beautiful square visualization of two auras in love connection. Two luminous energy fields approaching each other: left aura glowing in gradient from mystic purple core (#7C3AED) to violet edges (#8B5CF6), right aura glowing in gradient from sacred gold core (#D97706) to champagne edges (#F59E0B). Where the auras meet, they don't collide but beautifully interpenetrate, creating stunning interference pattern of rose pink (#EC4899) and teal (#14B8A6) energy waves. The interaction zone shows visible energy exchange: small orbs of light traveling between the two fields, creating figure-eight flow patterns (infinity loops). Each aura has seven colored layers corresponding to chakra energies (faint rainbow rings). Heart chakra regions glow brightest with emerald green (#22C55E) intensifying where they overlap.

The auras pulse rhythmically, suggesting heartbeat synchronization. Delicate energy tendrils reach out like gentle caresses, creating lace-like patterns at intersection. Small sparkle particles indicate heightened vibration when souls meet. Background is soft cosmic darkness (#1E1B4B) with scattered stars keeping focus on the aura interaction.

Style: Scientific visualization meets romantic art, bioluminescent glow effects, translucent layered auras, Kirlian photography aesthetic enhanced, volumetric rendering, particle physics visualization, professional medical illustration quality meets spiritual art, 8K photorealistic, sense of gentle magnetic pull and irresistible attraction, warm loving energy, palpable chemistry."""
    }
]

def generate_image(image_data):
    """Generate a single image with FLUX 1.1 Pro"""

    print(f"\n{'='*60}")
    print(f"🎨 Generating Image {image_data['id']}: {image_data['title']}")
    print(f"{'='*60}")
    print(f"📐 Size: {image_data['width']}×{image_data['height']}")
    print(f"🌱 Seed: {image_data['seed']}")
    print(f"💬 Prompt length: {len(image_data['prompt'])} characters")

    start_time = time.time()

    try:
        result = fal_client.subscribe(
            MODEL,
            arguments={
                "prompt": image_data['prompt'],
                "image_size": {
                    "width": image_data['width'],
                    "height": image_data['height']
                },
                "seed": image_data['seed'],
                **SETTINGS
            }
        )

        generation_time = time.time() - start_time

        if not result or 'images' not in result or not result['images']:
            print(f"❌ No image returned from API")
            return None

        image_url = result['images'][0]['url']
        print(f"✅ Generated in {generation_time:.1f}s")
        print(f"🔗 URL: {image_url[:60]}...")

        # Download image
        print(f"📥 Downloading image...")
        response = requests.get(image_url)
        response.raise_for_status()

        # Save with descriptive filename
        filename = f"img_{image_data['id']:03d}_{image_data['title'].lower().replace(' ', '_').replace('-', '_')}_beautified.png"
        filepath = OUTPUT_DIR / filename

        with open(filepath, 'wb') as f:
            f.write(response.content)

        file_size_kb = len(response.content) / 1024
        print(f"💾 Saved: {filename} ({file_size_kb:.1f} KB)")

        return {
            'id': image_data['id'],
            'title': image_data['title'],
            'filename': filename,
            'filepath': str(filepath),
            'size_kb': file_size_kb,
            'generation_time': generation_time,
            'dimensions': f"{image_data['width']}×{image_data['height']}"
        }

    except Exception as e:
        print(f"❌ Generation failed: {str(e)}")
        return None

def main():
    """Main generation workflow"""

    print("\n" + "="*60)
    print("🎨💖 BEAUTIFIED RELATIONSHIP IMAGES GENERATOR")
    print("="*60)
    print(f"📊 Total images: {len(IMAGES)}")
    print(f"💰 Budget: ${TOTAL_BUDGET}")
    print(f"🤖 Model: {MODEL}")
    print(f"📁 Output: {OUTPUT_DIR}")
    print("="*60)

    # Check for API key
    if not os.environ.get('FAL_KEY'):
        print("\n❌ ERROR: FAL_KEY environment variable not set")
        print("Set it with: export FAL_KEY=your_key_here")
        return

    print("\n✅ FAL_KEY found")

    # Confirm generation
    print(f"\n⚠️  This will generate {len(IMAGES)} images at ~${COST_PER_IMAGE} each")
    print(f"💰 Estimated total cost: ${TOTAL_BUDGET}")
    response = input("\n🤔 Proceed with generation? [y/N]: ")

    if response.lower() != 'y':
        print("\n🚫 Generation cancelled")
        return

    # Generate images
    print("\n🚀 Starting generation...\n")
    start_time = time.time()

    results = []
    total_cost = 0

    for i, image_data in enumerate(IMAGES, 1):
        print(f"\n[{i}/{len(IMAGES)}]")
        result = generate_image(image_data)

        if result:
            results.append(result)
            total_cost += COST_PER_IMAGE
            print(f"💵 Running total: ${total_cost:.2f} / ${TOTAL_BUDGET:.2f}")
        else:
            print(f"⚠️  Skipping image {image_data['id']} due to error")

        # Small delay between requests to be respectful
        if i < len(IMAGES):
            time.sleep(1)

    # Summary
    total_time = time.time() - start_time

    print("\n" + "="*60)
    print("📊 GENERATION SUMMARY")
    print("="*60)
    print(f"✅ Successfully generated: {len(results)}/{len(IMAGES)} images")
    print(f"💰 Total cost: ${total_cost:.2f}")
    print(f"⏱️  Total time: {total_time:.1f}s")
    print(f"📁 Output directory: {OUTPUT_DIR}")

    # Save manifest
    manifest = {
        'generation_date': datetime.now().isoformat(),
        'model': MODEL,
        'total_images': len(results),
        'total_cost': total_cost,
        'total_time': total_time,
        'images': results
    }

    manifest_path = OUTPUT_DIR / "manifest.json"
    with open(manifest_path, 'w') as f:
        json.dump(manifest, f, indent=2)

    print(f"📋 Manifest saved: {manifest_path}")

    # List generated files
    print("\n📂 Generated files:")
    for result in results:
        print(f"  • {result['filename']} ({result['size_kb']:.1f} KB)")

    print("\n✨ Beautification complete! Review images for emotional impact.")
    print("💖 Next steps:")
    print("  1. Visual QA - Check for heartfelt, romantic quality")
    print("  2. Optimize to WebP format")
    print("  3. Generate Android density variants")
    print("  4. Deploy to app/src/main/res/drawable-*/")
    print("  5. Test in Compatibility screens")
    print("\n🎉 Happy beautifying!\n")

if __name__ == "__main__":
    main()
