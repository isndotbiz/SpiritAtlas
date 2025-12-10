#!/usr/bin/env python3
"""
Generate 6 Hero Background Images for SpiritAtlas
Budget: $0.30 (6 images @ $0.05/image with FLUX 1.1 Pro)
Following beautification strategy and visual specifications
"""

import os
import json
from pathlib import Path
from datetime import datetime
import time

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
OUTPUT_DIR = Path("generated_images/hero_backgrounds")
MANIFEST_FILE = OUTPUT_DIR / "hero_backgrounds_manifest.json"
COST_PER_IMAGE = 0.05
TOTAL_BUDGET = 0.30

# FLUX 1.1 Pro optimal settings
SETTINGS = {
    "guidance_scale": 3.5,
    "num_inference_steps": 28,
    "safety_tolerance": 2,
}

# Hero background prompts following VISUAL_SPECIFICATION_GUIDE.md
HERO_PROMPTS = [
    {
        "id": "hero_001_cosmic_awakening",
        "title": "Cosmic Awakening - Spiritual Discovery",
        "width": 1080,
        "height": 1920,
        "seed": 42001,
        "prompt": """Epic vertical cosmic vista for spiritual app hero background, deep space indigo (#1E1B4B) at top transitioning to rich mystic purple (#7C3AED) in center, fading to cosmic violet (#6B21A8) at bottom, scattered photorealistic stars of varying sizes and brightness, ethereal purple nebula clouds at 40% opacity suggesting spiritual energy, subtle sacred geometry pattern (Flower of Life) barely visible as watermark at 5% opacity in center, bokeh light effects with soft golden particles (#D97706) floating upward, cinematic depth of field, professional astronomical photography style with mystical enhancement, 8K ultra-detailed, clean composition with central focal area for content overlay, premium spiritual app aesthetic, suitable for hero/splash screen, maintains excellent text readability"""
    },
    {
        "id": "hero_002_aurora_mystical",
        "title": "Aurora Mystical - Divine Connection",
        "width": 1080,
        "height": 1920,
        "seed": 42002,
        "prompt": """Breathtaking vertical aurora borealis background for spiritual app, ethereal aurora waves flowing from top to bottom in gradient from mystic purple (#7C3AED) through cosmic violet (#6B21A8) to aurora pink (#EC4899), deep night sky indigo (#1E1B4B) as base layer, realistic star field with varied brightness, soft glowing energy wisps suggesting divine connection, professional long-exposure night photography aesthetic, dreamy and transcendent atmosphere, 8K photorealistic quality, cinematic color grading, suitable for meditation and spiritual awakening screens, maintains content readability with 50% central area kept subtle, premium app background design"""
    },
    {
        "id": "hero_003_golden_enlightenment",
        "title": "Golden Enlightenment - Inner Light",
        "width": 1080,
        "height": 1920,
        "seed": 42003,
        "prompt": """Inspirational vertical background showing radiant golden light burst from center, sacred gold gradient (#D97706 to #F59E0B) emanating from focal point, surrounded by deep cosmic purple (#6B21A8) and mystic violet (#7C3AED) nebula clouds, soft spiritual energy particles radiating outward, subtle lens flare effects, professional cinematic lighting with rim light, suggests inner enlightenment and awakening, photorealistic space photography quality enhanced with spiritual aesthetic, 8K ultra-detailed, bokeh background blur, clean central composition area for overlay content, celebratory and uplifting mood, premium spiritual app hero background"""
    },
    {
        "id": "hero_004_celestial_harmony",
        "title": "Celestial Harmony - Cosmic Balance",
        "width": 1080,
        "height": 1920,
        "seed": 42004,
        "prompt": """Serene vertical cosmic background featuring celestial spheres in harmony, multiple glowing orbs representing planets and stars in deep space, color palette of mystic purple (#7C3AED), cosmic blue (#3B82F6), and sacred gold (#D97706) accents, deep indigo-black space background (#1E1B4B), subtle connecting energy lines between celestial bodies suggesting universal connection, professional space photography with mystical enhancement, soft glowing halos around each sphere, peaceful and balanced composition, 8K photorealistic detail, suitable for profile creation and compatibility screens, maintains excellent readability for UI overlay, premium spiritual app aesthetic"""
    },
    {
        "id": "hero_005_sacred_portal",
        "title": "Sacred Portal - Dimensional Gateway",
        "width": 1080,
        "height": 1920,
        "seed": 42005,
        "prompt": """Mystical vertical background featuring circular portal or gateway in center, golden ring structure (#D97706) forming perfect circle, interior shows swirling cosmic purple and violet energy (#6B21A8, #7C3AED), deep space background with realistic star field, subtle sacred geometry patterns (Metatron's Cube) faintly visible within portal at 20% opacity, ethereal light particles flowing through portal suggesting transformation, professional cinematic VFX quality, depth and dimension with 3D effect, soft radial glow from portal center, 8K ultra-detailed, suitable for onboarding journey visualization, maintains central area clarity for content, premium mystical app background design"""
    },
    {
        "id": "hero_006_stardust_dreams",
        "title": "Stardust Dreams - Cosmic Potential",
        "width": 1080,
        "height": 1920,
        "seed": 42006,
        "prompt": """Dreamy vertical cosmic background with flowing stardust streams, vertical gradient from deep night sky indigo (#1E1B4B) at top through mystic purple (#7C3AED) in center to cosmic violet (#6B21A8) at bottom, countless tiny golden and white light particles (#D97706, #FFFFFF) flowing diagonally creating sense of gentle motion, soft purple nebula wisps providing depth, professional long-exposure astrophotography aesthetic with artistic enhancement, ethereal and hopeful atmosphere, bokeh background with varied depth of field, 8K photorealistic quality with dreamy post-processing, suitable for aspirational content and goal-setting screens, excellent text overlay readability, premium spiritual app hero background design"""
    }
]

def generate_hero_image(prompt_data, index, total):
    """Generate single hero background with FLUX 1.1 Pro"""
    print(f"\n{'='*70}")
    print(f"[{index}/{total}] {prompt_data['title']}")
    print(f"{'='*70}")
    print(f"  ID: {prompt_data['id']}")
    print(f"  Size: {prompt_data['width']}x{prompt_data['height']}")
    print(f"  Seed: {prompt_data['seed']}")
    print(f"  Cost: ${COST_PER_IMAGE:.2f}")

    try:
        start_time = time.time()

        print(f"\n  🚀 Submitting to FLUX 1.1 Pro...")

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

            print(f"  ✅ Generated in {elapsed:.1f}s")
            print(f"  📥 Downloading image...")

            # Download image
            response = requests.get(image_url)
            response.raise_for_status()

            # Save with descriptive filename
            filename = f"{prompt_data['id']}.png"
            filepath = OUTPUT_DIR / filename

            with open(filepath, 'wb') as f:
                f.write(response.content)

            file_size_kb = len(response.content) / 1024

            print(f"  💾 Saved: {filename}")
            print(f"  📊 File size: {file_size_kb:.1f} KB")
            print(f"  ✅ SUCCESS!")

            return {
                'index': index,
                'id': prompt_data['id'],
                'title': prompt_data['title'],
                'filename': filename,
                'filepath': str(filepath),
                'size': f"{prompt_data['width']}x{prompt_data['height']}",
                'seed': prompt_data['seed'],
                'file_size_kb': round(file_size_kb, 1),
                'generation_time_seconds': round(elapsed, 1),
                'cost': COST_PER_IMAGE,
                'timestamp': datetime.now().isoformat(),
                'model': MODEL,
                'settings': SETTINGS
            }
        else:
            print(f"  ❌ No image returned from API")
            return None

    except Exception as e:
        print(f"  ❌ Error: {e}")
        import traceback
        traceback.print_exc()
        return None

def main():
    print("=" * 70)
    print("HERO BACKGROUND GENERATOR FOR SPIRITATLAS")
    print("=" * 70)
    print(f"Model: {MODEL}")
    print(f"Budget: ${TOTAL_BUDGET:.2f}")
    print(f"Images: {len(HERO_PROMPTS)}")
    print(f"Cost per image: ${COST_PER_IMAGE:.2f}")
    print(f"Settings: {SETTINGS}")
    print("=" * 70)

    # Create output directory
    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)
    print(f"\n📁 Output directory: {OUTPUT_DIR}")

    # Verify budget
    total_cost = len(HERO_PROMPTS) * COST_PER_IMAGE
    if total_cost > TOTAL_BUDGET:
        print(f"\n⚠️  WARNING: Estimated cost ${total_cost:.2f} exceeds budget ${TOTAL_BUDGET:.2f}")
        response = input("Continue anyway? (y/n): ")
        if response.lower() != 'y':
            print("Cancelled.")
            return

    print(f"\n💰 Estimated cost: ${total_cost:.2f}")

    # Load existing manifest
    manifest = []
    if MANIFEST_FILE.exists():
        with open(MANIFEST_FILE, 'r') as f:
            manifest = json.load(f)
        print(f"📄 Loaded existing manifest ({len(manifest)} images)")

    # Generate images
    print(f"\n🚀 Starting generation...")
    print(f"   Rate limit protection: 0.5s delay between images")

    start_time = time.time()
    generated = 0
    failed = 0
    total_size_kb = 0

    for i, prompt_data in enumerate(HERO_PROMPTS, 1):
        result = generate_hero_image(prompt_data, i, len(HERO_PROMPTS))

        if result:
            manifest.append(result)
            generated += 1
            total_size_kb += result['file_size_kb']

            # Save manifest after each image
            with open(MANIFEST_FILE, 'w') as f:
                json.dump(manifest, f, indent=2)

            print(f"\n  📊 Progress: {generated}/{len(HERO_PROMPTS)} images")
            print(f"  💰 Cost so far: ${generated * COST_PER_IMAGE:.2f}")
        else:
            failed += 1
            print(f"\n  ⚠️  Failed: {failed} images")

        # Small delay to avoid rate limiting
        if i < len(HERO_PROMPTS):
            print(f"\n  ⏳ Waiting 0.5s before next image...")
            time.sleep(0.5)

    # Summary
    total_time = time.time() - start_time
    total_cost_actual = generated * COST_PER_IMAGE
    avg_size_kb = total_size_kb / generated if generated > 0 else 0

    print("\n" + "=" * 70)
    print("GENERATION COMPLETE!")
    print("=" * 70)
    print(f"✅ Successfully generated: {generated}/{len(HERO_PROMPTS)}")
    if failed > 0:
        print(f"❌ Failed: {failed}")
    print(f"⏱️  Total time: {total_time/60:.1f} minutes")
    print(f"⏱️  Average time per image: {total_time/generated:.1f}s" if generated > 0 else "")
    print(f"💰 Total cost: ${total_cost_actual:.2f}")
    print(f"💰 Budget remaining: ${TOTAL_BUDGET - total_cost_actual:.2f}")
    print(f"📊 Total file size: {total_size_kb/1024:.1f} MB")
    print(f"📊 Average file size: {avg_size_kb:.1f} KB")
    print(f"📁 Output directory: {OUTPUT_DIR}")
    print(f"📄 Manifest: {MANIFEST_FILE}")
    print("=" * 70)

    # List generated files
    if generated > 0:
        print("\n📸 Generated Images:")
        for item in manifest:
            print(f"  • {item['filename']}")
            print(f"    {item['title']}")
            print(f"    {item['size']} | {item['file_size_kb']} KB | {item['generation_time_seconds']}s")

        print("\n✨ Hero backgrounds are ready for integration!")
        print("   Follow VISUAL_SPECIFICATION_GUIDE.md for optimization and deployment.")

if __name__ == "__main__":
    main()
