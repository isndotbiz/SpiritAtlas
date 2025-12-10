#!/usr/bin/env python3
"""
Sacred Geometry Showcase Generator for SpiritAtlas
Generates 5 premium quality sacred geometry images using FLUX 1.1 Pro
Target quality: 9.8+/10, Budget: $0.25 (5 images @ $0.05 each)
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

# Check for FAL_KEY
if not os.environ.get('FAL_KEY'):
    print("\n❌ FAL_KEY environment variable not set!")
    print("\n📝 Setup Instructions:")
    print("1. Get your API key from: https://fal.ai/dashboard")
    print("2. Export it: export FAL_KEY='your-api-key-here'")
    print("3. Or create .env file with: FAL_KEY=your-api-key-here")
    print("\nAlternatively, run:")
    print("  export FAL_KEY='your-key' && python3 generate_sacred_geometry.py")
    exit(1)

# Configuration
MODEL = "fal-ai/flux-pro/v1.1"
OUTPUT_DIR = Path("generated_images/sacred_geometry")
MANIFEST_FILE = OUTPUT_DIR / "sacred_geometry_manifest.json"
COST_PER_IMAGE = 0.05  # $0.25 / 5 images

# FLUX 1.1 Pro optimal settings for maximum quality
SETTINGS = {
    "guidance_scale": 3.5,
    "num_inference_steps": 28,
    "safety_tolerance": 2,
}

# Sacred Geometry Prompts - Ultra High Quality
SACRED_GEOMETRY_PROMPTS = [
    {
        "title": "Flower of Life - Sacred Creation Pattern",
        "prompt": """Perfect sacred geometry Flower of Life mandala, 19 interlocking circles in precise mathematical harmony,
glowing golden lines (#D97706) on deep cosmic purple background (#6B21A8 to #1E1B4B gradient), each circle perfectly
proportioned with 6-fold symmetry, luminous energy emanating from center, subtle rainbow prismatic light refraction at
intersection points, ancient mystical symbols, geometric perfection, professional sacred geometry illustration,
8K ultra-detailed rendering, spiritual enlightenment aesthetic, museum quality, photorealistic glow effects,
crystal clear lines, divine proportion ratios, mesmerizing symmetry""",
        "width": 1024,
        "height": 1024,
        "seed": 777
    },
    {
        "title": "Sri Yantra - Divine Cosmic Diagram",
        "prompt": """Intricate Sri Yantra sacred geometry mandala, 9 interlocking triangles creating 43 smaller triangles in perfect
mathematical precision, 5 upward pointing Shiva triangles and 4 downward pointing Shakti triangles, central bindu point
glowing with pure white divine light, surrounded by lotus petals in first ring (8 petals) and outer ring (16 petals),
sacred Sanskrit Om symbol subtly integrated, golden metallic lines (#D97706) with purple-violet mystic energy aura
(#7C3AED), concentric circular gates (bhupura square), three-dimensional depth illusion, tantric spiritual power radiating
from center, photorealistic metallic texture, 8K ultra-high resolution, professional Hindu sacred art, divine feminine and
masculine balance, cosmic harmony visualization, temple worship quality""",
        "width": 1024,
        "height": 1024,
        "seed": 108
    },
    {
        "title": "Merkaba - Light Body Vehicle",
        "prompt": """Perfect three-dimensional Merkaba sacred geometry, two interlocking tetrahedrons forming 8-pointed star,
one pointing upward (masculine energy) in brilliant gold (#D97706), one pointing downward (feminine energy) in luminous
violet (#7C3AED), rotating counter to each other, central energy sphere glowing with pure white light, sacred geometry
vertices precisely aligned, cosmic starfield background (#1E1B4B), ethereal energy field surrounding the structure,
light trails showing rotation, photorealistic 3D rendering with crystal material, divine light rays emanating,
8K cinematic quality, spiritual ascension symbolism, professional CGI perfection, sacred dimensional gateway aesthetic,
volumetric lighting, particle effects""",
        "width": 1024,
        "height": 1024,
        "seed": 888
    },
    {
        "title": "Metatron's Cube - Archangelic Blueprint",
        "prompt": """Sacred Metatron's Cube geometric pattern, 13 circles of equal size arranged in perfect hexagonal symmetry,
13 circles representing 13 archangels, all 5 Platonic solids contained within (tetrahedron, cube, octahedron, dodecahedron,
icosahedron) visible through connecting lines, intricate golden wireframe (#D97706) on cosmic violet background
(#6B21A8 gradient), each connection point glowing with subtle white light, sacred geometry perfection, mathematical
beauty, universal creation blueprint, professional architectural precision, 8K ultra-detailed rendering, spiritual
wisdom symbol, divine proportion, crystalline structure, heavenly light effects, angelic presence, photorealistic glow""",
        "width": 1024,
        "height": 1024,
        "seed": 999
    },
    {
        "title": "Seed of Life - Genesis Pattern",
        "prompt": """Pristine Seed of Life sacred geometry mandala, 7 overlapping circles in perfect formation representing
7 days of creation, central circle surrounded by 6 identical circles forming hexagonal pattern, pure golden lines
(#D97706) with subtle luminous glow, deep spiritual purple background (#6B21A8 to #8B5CF6 radial gradient), each
intersection point sparkling with divine light, vesica piscis forms creating almond-shaped light portals, sacred
proportions based on divine mathematics, origin of Flower of Life pattern, creation energy radiating outward,
photorealistic rendering with crystal clear geometry, 8K ultra-high definition, museum quality sacred art,
spiritual genesis symbolism, perfect circular symmetry, mystical depth, professional precision""",
        "width": 1024,
        "height": 1024,
        "seed": 369
    }
]

def generate_image(prompt_data, index, total):
    """Generate single sacred geometry image with FLUX 1.1 Pro"""
    print(f"\n[{index}/{total}] {prompt_data['title']}")
    print(f"  Size: {prompt_data['width']}x{prompt_data['height']}")
    print(f"  Seed: {prompt_data['seed']}")
    print(f"  Estimated cost: ${COST_PER_IMAGE}")

    try:
        start_time = time.time()

        result = fal_client.subscribe(
            MODEL,
            arguments={
                "prompt": prompt_data['prompt'],
                "image_size": {
                    "width": prompt_data['width'],
                    "height": prompt_data['height']
                },
                "num_inference_steps": SETTINGS["num_inference_steps"],
                "guidance_scale": SETTINGS["guidance_scale"],
                "num_images": 1,
                "enable_safety_checker": True,
                "safety_tolerance": SETTINGS["safety_tolerance"],
                "output_format": "png",
                "seed": prompt_data['seed']
            }
        )

        elapsed = time.time() - start_time

        if result and 'images' in result and result['images']:
            image_url = result['images'][0]['url']

            # Download image
            import requests
            response = requests.get(image_url)

            # Save with descriptive filename
            filename = f"sacred_geometry_{index:02d}_{prompt_data['title'].lower().replace(' ', '_').replace('-', '_')[:40]}.png"
            filepath = OUTPUT_DIR / filename

            with open(filepath, 'wb') as f:
                f.write(response.content)

            file_size_mb = len(response.content) / (1024 * 1024)

            print(f"  ✅ Generated in {elapsed:.1f}s")
            print(f"  💾 Saved: {filename}")
            print(f"  📦 Size: {file_size_mb:.2f} MB")

            return {
                'index': index,
                'title': prompt_data['title'],
                'filename': filename,
                'filepath': str(filepath),
                'size': f"{prompt_data['width']}x{prompt_data['height']}",
                'seed': prompt_data['seed'],
                'generation_time': elapsed,
                'file_size_mb': file_size_mb,
                'cost': COST_PER_IMAGE,
                'timestamp': datetime.now().isoformat(),
                'image_url': image_url
            }
        else:
            print(f"  ❌ No image returned")
            return None

    except Exception as e:
        print(f"  ❌ Error: {e}")
        return None

def main():
    print("=" * 70)
    print("SACRED GEOMETRY SHOWCASE GENERATOR")
    print("High-Quality Image Generation for SpiritAtlas")
    print("=" * 70)

    # Create output directory
    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

    print(f"\n📊 Generation Plan:")
    print(f"   Images: {len(SACRED_GEOMETRY_PROMPTS)}")
    print(f"   Model: {MODEL}")
    print(f"   Quality Target: 9.8+/10")
    print(f"   Resolution: 1024x1024")
    print(f"   Settings: {SETTINGS}")

    # Cost estimate
    total_cost = len(SACRED_GEOMETRY_PROMPTS) * COST_PER_IMAGE
    print(f"\n💰 Budget:")
    print(f"   Cost per image: ${COST_PER_IMAGE}")
    print(f"   Total cost: ${total_cost:.2f}")
    print(f"   Budget: $0.25 ✅")

    print("\n🔮 Sacred Geometry Images:")
    for i, prompt in enumerate(SACRED_GEOMETRY_PROMPTS, 1):
        print(f"   {i}. {prompt['title']}")

    input("\n⏸️  Press Enter to start generation...")

    # Generate images
    print(f"\n🚀 Starting generation with FLUX 1.1 Pro...")

    start_time = time.time()
    manifest = []
    generated = 0
    failed = 0

    for i, prompt_data in enumerate(SACRED_GEOMETRY_PROMPTS, 1):
        result = generate_image(prompt_data, i, len(SACRED_GEOMETRY_PROMPTS))

        if result:
            manifest.append(result)
            generated += 1

            # Save manifest after each image
            with open(MANIFEST_FILE, 'w') as f:
                json.dump(manifest, f, indent=2)
        else:
            failed += 1

        # Small delay to avoid rate limiting
        if i < len(SACRED_GEOMETRY_PROMPTS):
            time.sleep(1)

    # Summary
    total_time = time.time() - start_time
    total_cost_actual = generated * COST_PER_IMAGE
    avg_time = total_time / generated if generated > 0 else 0

    print("\n" + "=" * 70)
    print("GENERATION COMPLETE!")
    print("=" * 70)
    print(f"✅ Successfully generated: {generated}/{len(SACRED_GEOMETRY_PROMPTS)}")
    if failed > 0:
        print(f"❌ Failed: {failed}")
    print(f"⏱️  Total time: {total_time/60:.1f} minutes ({avg_time:.1f}s per image)")
    print(f"💰 Total cost: ${total_cost_actual:.2f}")
    print(f"📁 Output directory: {OUTPUT_DIR.absolute()}")
    print(f"📄 Manifest: {MANIFEST_FILE.absolute()}")
    print("=" * 70)

    if generated > 0:
        print("\n🎨 Generated Images:")
        for item in manifest:
            print(f"   • {item['filename']}")
            print(f"     Size: {item['file_size_mb']:.2f} MB, Time: {item['generation_time']:.1f}s")

    print("\n✨ Sacred geometry images ready for integration!")

if __name__ == "__main__":
    main()
