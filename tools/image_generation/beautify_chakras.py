#!/usr/bin/env python3
"""
SpiritAtlas Chakra Beautification Script
IMAGE BEAUTIFICATION AGENT 1-3: Chakra Specialist

Mission: Generate 3 premium chakra images using FLUX 1.1 Pro
Budget: $0.15 (3 images @ $0.05 each)
Target Quality: 9.8+/10

Usage:
    export FAL_KEY="your_fal_api_key_here"
    python3 beautify_chakras.py

Or:
    python3 beautify_chakras.py --api-key YOUR_KEY
"""

import os
import sys
import argparse
import requests
import json
from pathlib import Path
from datetime import datetime
from typing import Dict, Any, List

try:
    import fal_client
except ImportError:
    print("❌ fal_client not installed. Install with: pip install fal-client")
    sys.exit(1)

# Configuration
MODEL = "fal-ai/flux-pro/v1.1"
OUTPUT_DIR = Path("generated_images/beautified_chakras")
COST_PER_IMAGE = 0.05

# Optimal FLUX 1.1 Pro settings
GENERATION_SETTINGS = {
    "num_inference_steps": 28,
    "guidance_scale": 3.5,
    "num_images": 1,
    "enable_safety_checker": True,
    "safety_tolerance": "2",
    "output_format": "png"
}

# Target chakras with enhanced prompts
CHAKRA_DEFINITIONS = {
    "047_sacral_svadhisthana": {
        "name": "Sacral Chakra (Svadhisthana)",
        "current_score": 9.4,
        "target_score": 9.8,
        "sanskrit": "वं (Vam)",
        "color": "#FF6B35 to #FF8C42",
        "petals": 6,
        "element": "Water",
        "prompt": """Ultra-detailed spiritual chakra visualization of Svadhisthana (Sacral Chakra), radiant luminous orange energy center with gradient from deep amber (#FF6B35) to bright tangerine (#FF8C42), floating six-petaled lotus flower with intricate sacred geometry detail showing each petal distinctly, concentric energy rings pulsing outward with flowing water element particles suggesting fluid motion, Vesica Piscis sacred geometry pattern subtly integrated in background creating dimensional depth, Sanskrit symbol 'वं' (Vam) glowing softly in pure white at center with elegant calligraphy, ethereal cosmic purple-blue space background (#1E1B4B to #6B21A8 gradient) with scattered stars, photorealistic energy glow with volumetric lighting and soft bloom effect, multiple translucent dimensional layers creating 3D depth, spiritual healing and creative vitality aesthetic, 8K ultra-sharp resolution, professional 3D render quality with physically accurate light scattering, sacred geometry mathematical precision, mystical atmosphere with golden bokeh light particles floating, perfectly centered circular composition, cinematic color grading"""
    },
    "048_solar_plexus_manipura": {
        "name": "Solar Plexus Chakra (Manipura)",
        "current_score": 9.5,
        "target_score": 9.8,
        "sanskrit": "रं (Ram)",
        "color": "#FFD60A to #FFC300",
        "petals": 10,
        "element": "Fire",
        "prompt": """Magnificent spiritual chakra visualization of Manipura (Solar Plexus Chakra), intensely radiant golden-yellow solar energy sphere with brilliant gradient from pure gold (#FFD60A) to rich amber (#FFC300), ten-petaled lotus mandala with precise geometric arrangement and fire element integration showing dancing flames, explosive sunburst rays emanating in all directions with god rays effect, sacred geometry inverted triangle (Agni Tattwa fire element symbol) at core glowing intensely, Sanskrit symbol 'रं' (Ram) blazing in pure white at center with powerful presence, multiple concentric rings of golden energy particles swirling dynamically, deep cosmic violet background (#6B21A8 to #1E1B4B gradient) creating dramatic high contrast, photorealistic volumetric lighting with lens flare effects and corona glow, dimensional depth with overlapping translucent energy layers creating 3D space, personal power and inner strength aesthetic radiating confidence, 8K ultra-detailed resolution, professional 3D render with physically accurate light scattering and caustics, sacred geometry mathematical precision with golden ratio proportions, mystical atmosphere with floating golden light particles suggesting solar energy, dynamic centered composition suggesting expansion and empowerment, cinematic dramatic lighting"""
    },
    "050_throat_vishuddha": {
        "name": "Throat Chakra (Vishuddha)",
        "current_score": 9.4,
        "target_score": 9.8,
        "sanskrit": "हं (Ham)",
        "color": "#00B4D8 to #0096C7",
        "petals": 16,
        "element": "Ether/Space",
        "prompt": """Stunning spiritual chakra visualization of Vishuddha (Throat Chakra), brilliant turquoise-blue energy sphere with luminous gradient from cyan (#00B4D8) to deep cerulean (#0096C7), sixteen-petaled lotus mandala with precise sacred geometry showing intricate petal detail, ethereal sound wave patterns radiating outward in concentric circles suggesting vibration and frequency, Metatron's Cube sacred geometry subtly visible in underlying structure with perfect geometric proportions, Sanskrit symbol 'हं' (Ham) glowing in pure white with divine luminescence at center, deep cosmic indigo space background (#1E1B4B to #2D1B69 gradient) with scattered diamond-like stars, photorealistic energy corona with rim lighting and ethereal glow, multiple translucent dimensional layers creating holographic 3D depth, spiritual communication and truth expression essence, 8K ultra-crisp detail with macro sharpness, professional volumetric 3D rendering with subsurface scattering, sacred geometric precision with hexagonal symmetry elements, mystical atmosphere with light ray effects emanating from center suggesting divine voice, floating silver-blue particles of ethereal energy, perfect circular composition with balanced symmetry, cinematic atmospheric lighting with soft bloom"""
    }
}


def generate_beautified_chakra(name: str, config: Dict[str, Any], api_key: str) -> Dict[str, Any]:
    """
    Generate a single beautified chakra image.

    Args:
        name: Chakra identifier (e.g., "047_sacral_svadhisthana")
        config: Chakra configuration with prompt and metadata
        api_key: fal.ai API key

    Returns:
        Dict with generation results and metadata
    """
    print("\n" + "="*80)
    print(f"🎨 GENERATING: {config['name']}")
    print("="*80)
    print(f"📊 Current Score: {config['current_score']}/10")
    print(f"🎯 Target Score: {config['target_score']}/10")
    print(f"🌈 Color: {config['color']}")
    print(f"🌸 Petals: {config['petals']}")
    print(f"🔤 Sanskrit: {config['sanskrit']}")
    print(f"🔥 Element: {config['element']}")
    print(f"💰 Cost: ${COST_PER_IMAGE}")
    print("="*80)

    # Set API key
    os.environ["FAL_KEY"] = api_key

    # Build parameters
    params = {
        "prompt": config["prompt"],
        "image_size": {
            "width": 1536,
            "height": 1536
        },
        **GENERATION_SETTINGS
    }

    print(f"\n⚙️  Model: {MODEL}")
    print(f"⚙️  Settings: steps={params['num_inference_steps']}, guidance={params['guidance_scale']}")
    print(f"⚙️  Size: 1536×1536 (optimal for Android multi-density)")
    print(f"\n🚀 Generating... (this may take 30-60 seconds)")

    start_time = datetime.now()

    try:
        # Call fal.ai API
        result = fal_client.subscribe(MODEL, arguments=params)

        elapsed = (datetime.now() - start_time).total_seconds()

        if not result or 'images' not in result or not result['images']:
            print(f"❌ Generation failed: No images returned")
            return None

        # Get image URL
        image_url = result['images'][0]['url']

        # Download image
        print(f"⬇️  Downloading image...")
        response = requests.get(image_url, timeout=60)
        response.raise_for_status()

        # Save with descriptive filename
        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
        filename = f"{name}_beautified_{timestamp}.png"
        filepath = OUTPUT_DIR / filename

        OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

        with open(filepath, 'wb') as f:
            f.write(response.content)

        file_size_mb = len(response.content) / (1024 * 1024)

        print(f"\n✅ SUCCESS!")
        print(f"⏱️  Generation time: {elapsed:.1f}s")
        print(f"💾 Saved: {filename}")
        print(f"📦 Size: {file_size_mb:.2f} MB")
        print(f"🔗 URL: {image_url}")

        return {
            'chakra_id': name,
            'chakra_name': config['name'],
            'filename': filename,
            'filepath': str(filepath),
            'url': image_url,
            'file_size_mb': file_size_mb,
            'generation_time_s': elapsed,
            'cost': COST_PER_IMAGE,
            'current_score': config['current_score'],
            'target_score': config['target_score'],
            'timestamp': datetime.now().isoformat(),
            'model': MODEL,
            'settings': GENERATION_SETTINGS
        }

    except Exception as e:
        print(f"\n❌ ERROR: {str(e)}")
        return None


def save_manifest(results: List[Dict[str, Any]]):
    """Save generation manifest with all metadata."""
    manifest_file = OUTPUT_DIR / "beautification_manifest.json"

    manifest = {
        'mission': 'IMAGE BEAUTIFICATION AGENT 1-3: Chakra Specialist',
        'date': datetime.now().isoformat(),
        'model': MODEL,
        'settings': GENERATION_SETTINGS,
        'budget': f"${len(results) * COST_PER_IMAGE:.2f}",
        'images_generated': len(results),
        'results': results,
        'summary': {
            'total_cost': sum(r['cost'] for r in results if r),
            'total_time_s': sum(r['generation_time_s'] for r in results if r),
            'avg_file_size_mb': sum(r['file_size_mb'] for r in results if r) / len([r for r in results if r]) if results else 0
        }
    }

    with open(manifest_file, 'w') as f:
        json.dump(manifest, f, indent=2)

    print(f"\n📄 Manifest saved: {manifest_file}")
    return manifest_file


def print_summary(results: List[Dict[str, Any]]):
    """Print beautification summary."""
    successful = [r for r in results if r is not None]
    failed = len(results) - len(successful)

    print("\n" + "="*80)
    print("🎉 CHAKRA BEAUTIFICATION COMPLETE!")
    print("="*80)

    if successful:
        print(f"\n✅ Successfully generated: {len(successful)}/{len(results)}")
        for r in successful:
            print(f"   • {r['chakra_name']}: {r['filename']}")

        total_cost = sum(r['cost'] for r in successful)
        total_time = sum(r['generation_time_s'] for r in successful)
        avg_time = total_time / len(successful)

        print(f"\n📊 Statistics:")
        print(f"   Total Cost: ${total_cost:.2f}")
        print(f"   Total Time: {total_time:.1f}s ({total_time/60:.1f} minutes)")
        print(f"   Avg Time per Image: {avg_time:.1f}s")
        print(f"   Output Directory: {OUTPUT_DIR}")

    if failed > 0:
        print(f"\n❌ Failed: {failed}")

    print("\n" + "="*80)
    print("📋 NEXT STEPS:")
    print("   1. Review generated images in: " + str(OUTPUT_DIR))
    print("   2. Run WebP optimization: python3 optimize_beautified_chakras.py")
    print("   3. Create before/after comparisons: python3 create_chakra_comparison.py")
    print("   4. Deploy to Android resources")
    print("   5. Update VISUAL_QUALITY_ASSESSMENT.md with new scores")
    print("="*80 + "\n")


def main():
    """Main execution function."""
    parser = argparse.ArgumentParser(
        description="Generate beautified chakra images for SpiritAtlas",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
    # Using environment variable
    export FAL_KEY="your_key_here"
    python3 beautify_chakras.py

    # Using command-line argument
    python3 beautify_chakras.py --api-key YOUR_KEY

    # Generate specific chakras only
    python3 beautify_chakras.py --chakras 047 050
        """
    )

    parser.add_argument(
        "--api-key",
        type=str,
        default=os.environ.get("FAL_KEY"),
        help="fal.ai API key (default: FAL_KEY environment variable)"
    )

    parser.add_argument(
        "--chakras",
        type=str,
        nargs="+",
        choices=["047", "048", "050"],
        help="Specific chakras to generate (default: all 3)"
    )

    parser.add_argument(
        "--output-dir",
        type=str,
        help="Custom output directory"
    )

    args = parser.parse_args()

    # Validate API key
    if not args.api_key:
        print("\n❌ ERROR: FAL_KEY not found!")
        print("\nPlease set your fal.ai API key:")
        print("   export FAL_KEY='your_key_here'")
        print("\nOr use --api-key argument:")
        print("   python3 beautify_chakras.py --api-key YOUR_KEY")
        print("\nGet your API key at: https://fal.ai/dashboard/keys\n")
        sys.exit(1)

    # Set custom output directory if specified
    global OUTPUT_DIR
    if args.output_dir:
        OUTPUT_DIR = Path(args.output_dir)

    # Determine which chakras to generate
    if args.chakras:
        chakras_to_generate = {
            k: v for k, v in CHAKRA_DEFINITIONS.items()
            if k.split('_')[0] in args.chakras
        }
    else:
        chakras_to_generate = CHAKRA_DEFINITIONS

    # Print mission header
    print("\n" + "="*80)
    print("IMAGE BEAUTIFICATION AGENT 1-3: CHAKRA SPECIALIST")
    print("="*80)
    print(f"Mission: Generate {len(chakras_to_generate)} premium chakra images")
    print(f"Budget: ${len(chakras_to_generate) * COST_PER_IMAGE:.2f}")
    print(f"Model: {MODEL}")
    print(f"Output: {OUTPUT_DIR}")
    print("="*80)

    # Generate images
    results = []
    for chakra_id, config in chakras_to_generate.items():
        result = generate_beautified_chakra(chakra_id, config, args.api_key)
        results.append(result)

        # Small delay between generations to avoid rate limiting
        if result and chakra_id != list(chakras_to_generate.keys())[-1]:
            print("\n⏳ Cooling down (5 seconds)...")
            import time
            time.sleep(5)

    # Save manifest
    if any(results):
        save_manifest([r for r in results if r])

    # Print summary
    print_summary(results)


if __name__ == "__main__":
    main()
