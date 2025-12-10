#!/usr/bin/env python3
"""
Energy Flow Image Beautification Generator
Agent 13-15: Generate 3 improved energy flow images with dynamic, flowing aesthetics

Budget: $0.15 (3 images @ $0.05 each)
"""

import os
import json
import time
from pathlib import Path
from datetime import datetime

try:
    import fal_client
    import requests
except ImportError:
    print("❌ Required packages not installed.")
    print("Install with: pip install fal-client requests")
    exit(1)

# Configuration
MODEL = "fal-ai/flux-pro/v1.1"
OUTPUT_DIR = Path("generated_images/energy_flow_beautified")
COST_PER_IMAGE = 0.05

# FLUX 1.1 Pro optimal settings
SETTINGS = {
    "guidance_scale": 3.5,
    "num_inference_steps": 28,
    "safety_tolerance": 2,
    "output_format": "png",
    "enable_safety_checker": True
}

# Enhanced Energy Flow Prompts
ENERGY_IMAGES = [
    {
        "title": "Compatibility Energy Field (Beautified)",
        "filename": "027_compatibility_energy_field_v2_beautified.png",
        "width": 1080,
        "height": 1920,
        "seed": 777,
        "prompt": """Cinematic energy field visualization showing two souls connecting, ultra-dynamic flowing energy streams merging at center in explosive burst of light, left side emanating deep cosmic purple energy (#6B21A8) with flowing corona discharge patterns, right side radiating pink-gold feminine energy (#EC4899 to #D97706), center fusion point creating brilliant white-gold explosion with particle dispersion, organic Kirlian photography inspired corona effects, bio-energetic field visualization with fluid plasma-like tendrils, multiple layers of semi-transparent energy waves creating depth, stochastic electric ionization patterns adding natural randomness, ethereal particle effects floating and swirling, professional aura photography aesthetic meets high-energy physics visualization, dramatic motion blur suggesting energy in motion, dark purple-black cosmic background (#1E1B4B) with subtle starfield, photorealistic energy physics rendering, 8K ultra-detailed with volumetric lighting, breathtaking dynamic composition showing soul connection and energetic fusion"""
    },
    {
        "title": "Torus Energy Field (Beautified)",
        "filename": "059_torus_energy_field_v2_beautified.png",
        "width": 1024,
        "height": 1024,
        "seed": 888,
        "prompt": """Breathtaking toroidal energy field visualization, organic flowing donut-shaped energy vortex with fluid plasma streams circulating from top center downward around outer edges and back up through core, dynamic purple to gold gradient (#7C3AED to #D97706) with rainbow chakra color spectrum visible in energy flow layers, Kirlian-inspired corona discharge effects creating natural irregular edges, bio-energetic field with GDV (Gas Discharge Visualization) aesthetic, multiple semi-transparent energy layers showing depth and movement, particle streams following toroidal flow paths with motion blur, electromagnetic field lines visible as flowing tendrils, stochastic ionization creating organic randomness and life-like quality, ethereal glow with volumetric lighting, professional physics simulation meets spiritual energy visualization, deep space cosmic background with subtle stars, 8K photorealistic detail with cinema-quality depth of field, universal life force energy pattern in constant flowing motion, stunning visual representation of torus field dynamics"""
    },
    {
        "title": "Chakra Energy Flow - Giving & Receiving (Beautified)",
        "filename": "111_chakra_energy_flow_v2_beautified.png",
        "width": 512,
        "height": 512,
        "seed": 999,
        "prompt": """Ultra-dynamic chakra energy circulation visualization, meditative human silhouette in center with seven luminous chakra orbs vertically aligned (authentic colors: red root, orange sacral, yellow solar plexus, green heart, blue throat, indigo third eye, violet crown), flowing energy streams circulating in figure-eight infinity patterns between external cosmic chakra spheres and internal body chakras, organic Kirlian corona discharge effects emanating from each energy center, bio-energetic field showing energy entering crown from above (cosmic download), circulating through body chakra system, exiting through hands and heart in giving motion, and receiving through root and feet from earth, fluid plasma-like energy tendrils connecting all points, particle effects showing constant energy exchange and flow, multiple semi-transparent aura layers surrounding figure in corresponding chakra colors, stochastic electric ionization creating natural organic flow patterns, sacred geometry mandala framework with flowing energy overlaid, professional spiritual energy photography meets advanced visualization technology, deep cosmic background transitioning from dark indigo to lighter purple, volumetric lighting with dramatic glow effects, 8K photorealistic rendering showing energy as living flowing force, breathtaking composition demonstrating universal energy circulation through human energy system"""
    }
]

def generate_image(image_data, index, total):
    """Generate single image with FLUX 1.1 Pro"""
    print(f"\n{'='*70}")
    print(f"[{index}/{total}] {image_data['title']}")
    print(f"{'='*70}")
    print(f"  Size: {image_data['width']}x{image_data['height']}")
    print(f"  Seed: {image_data['seed']}")
    print(f"  Cost: ${COST_PER_IMAGE}")

    try:
        start_time = time.time()

        print("\n  Generating with FLUX 1.1 Pro...")
        result = fal_client.subscribe(
            MODEL,
            arguments={
                "prompt": image_data['prompt'],
                "image_size": {
                    "width": image_data['width'],
                    "height": image_data['height']
                },
                "num_inference_steps": SETTINGS["num_inference_steps"],
                "guidance_scale": SETTINGS["guidance_scale"],
                "num_images": 1,
                "enable_safety_checker": SETTINGS["enable_safety_checker"],
                "safety_tolerance": SETTINGS["safety_tolerance"],
                "output_format": SETTINGS["output_format"],
                "seed": image_data['seed']
            }
        )

        elapsed = time.time() - start_time

        if result and 'images' in result and result['images']:
            image_url = result['images'][0]['url']

            # Download image
            print("  Downloading...")
            response = requests.get(image_url)

            # Save with descriptive filename
            filepath = OUTPUT_DIR / image_data['filename']

            with open(filepath, 'wb') as f:
                f.write(response.content)

            file_size_mb = len(response.content) / (1024 * 1024)

            print(f"\n  ✅ SUCCESS!")
            print(f"     Generated in {elapsed:.1f}s")
            print(f"     File: {image_data['filename']}")
            print(f"     Size: {file_size_mb:.2f} MB")
            print(f"     Path: {filepath}")

            return {
                'index': index,
                'title': image_data['title'],
                'filename': image_data['filename'],
                'filepath': str(filepath),
                'dimensions': f"{image_data['width']}x{image_data['height']}",
                'seed': image_data['seed'],
                'generation_time_seconds': round(elapsed, 2),
                'file_size_mb': round(file_size_mb, 2),
                'cost': COST_PER_IMAGE,
                'timestamp': datetime.now().isoformat(),
                'model': MODEL,
                'settings': SETTINGS
            }
        else:
            print(f"\n  ❌ FAILED: No image returned from API")
            return None

    except Exception as e:
        print(f"\n  ❌ ERROR: {e}")
        return None

def main():
    print("\n" + "="*70)
    print("   ENERGY FLOW IMAGE BEAUTIFICATION GENERATOR")
    print("   Agent 13-15: Dynamic, Flowing, Energetic!")
    print("="*70)

    # Create output directory
    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)
    print(f"\n📁 Output directory: {OUTPUT_DIR}")

    # Display plan
    print(f"\n📋 GENERATION PLAN:")
    print(f"   Model: {MODEL}")
    print(f"   Images to generate: {len(ENERGY_IMAGES)}")
    print(f"   Total budget: ${len(ENERGY_IMAGES) * COST_PER_IMAGE:.2f}")

    print(f"\n🎨 IMAGES:")
    for i, img in enumerate(ENERGY_IMAGES, 1):
        print(f"   {i}. {img['title']}")
        print(f"      {img['width']}x{img['height']} | Seed: {img['seed']}")

    # Cost confirmation
    total_cost = len(ENERGY_IMAGES) * COST_PER_IMAGE
    print(f"\n💰 Estimated cost: ${total_cost:.2f}")

    input("\n⏸️  Press ENTER to start generation...")

    # Generate images
    print("\n" + "="*70)
    print("🚀 STARTING GENERATION")
    print("="*70)

    start_time = time.time()
    results = []
    generated = 0
    failed = 0

    for i, image_data in enumerate(ENERGY_IMAGES, 1):
        result = generate_image(image_data, i, len(ENERGY_IMAGES))

        if result:
            results.append(result)
            generated += 1
        else:
            failed += 1

        # Small delay between generations
        if i < len(ENERGY_IMAGES):
            print("\n  ⏳ Waiting 2 seconds before next generation...")
            time.sleep(2)

    # Save manifest
    manifest_file = OUTPUT_DIR / "manifest.json"
    with open(manifest_file, 'w') as f:
        json.dump({
            'generation_date': datetime.now().isoformat(),
            'agent': 'IMAGE_BEAUTIFICATION_AGENT_13-15',
            'model': MODEL,
            'total_images': len(ENERGY_IMAGES),
            'successful': generated,
            'failed': failed,
            'total_cost': round(generated * COST_PER_IMAGE, 2),
            'images': results
        }, f, indent=2)

    # Final summary
    total_time = time.time() - start_time
    actual_cost = generated * COST_PER_IMAGE

    print("\n" + "="*70)
    print("   GENERATION COMPLETE!")
    print("="*70)
    print(f"\n📊 SUMMARY:")
    print(f"   ✅ Successfully generated: {generated}/{len(ENERGY_IMAGES)}")

    if failed > 0:
        print(f"   ❌ Failed: {failed}")

    print(f"\n⏱️  Total time: {total_time/60:.1f} minutes ({total_time:.1f} seconds)")
    print(f"💰 Total cost: ${actual_cost:.2f}")
    print(f"\n📁 Output directory: {OUTPUT_DIR}")
    print(f"📄 Manifest saved: {manifest_file}")

    if generated > 0:
        print(f"\n✨ BEAUTIFICATION SUCCESS! Generated {generated} dynamic energy flow images!")
        print("\n🎨 Generated Images:")
        for result in results:
            print(f"   • {result['filename']}")
            print(f"     {result['dimensions']} | {result['file_size_mb']} MB | {result['generation_time_seconds']}s")

    print("\n" + "="*70)

if __name__ == "__main__":
    main()
