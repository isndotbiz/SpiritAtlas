#!/usr/bin/env python3
"""
Generate additional 20 premium spiritual images (100-119) for SpiritAtlas
Focus: Tantric imagery, relationship dynamics, energy flows, meditation, spiritual practices
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
OUTPUT_DIR = Path("generated_images/additional_100-119")
MANIFEST_FILE = OUTPUT_DIR / "manifest_100-119.json"
COST_PER_IMAGE_MIN = 0.04
COST_PER_IMAGE_MAX = 0.20
BUDGET = 5.00

# FLUX 1.1 Pro optimal settings
SETTINGS = {
    "guidance_scale": 3.5,
    "num_inference_steps": 28,
    "safety_tolerance": 2,
}

# New prompts (100-119)
PROMPTS = [
    {
        "id": 100,
        "title": "Sacred Union - Divine Masculine & Feminine",
        "width": 1024,
        "height": 1024,
        "seed": 200,
        "prompt": "Artistic representation of sacred tantric union showing two luminous energy silhouettes merging, left figure radiating golden masculine solar energy (#D97706), right figure emanating purple feminine lunar energy (#7C3AED), creating harmonious heart-shaped energy field where they meet, subtle sacred geometry mandala background, professional spiritual tantric art, tasteful and reverent, no explicit imagery, cosmic background with stars, 8K ethereal detail, divine union symbolism"
    },
    {
        "id": 101,
        "title": "Kundalini Rising",
        "width": 512,
        "height": 1536,
        "seed": 201,
        "prompt": "Vertical visualization of kundalini energy rising through chakra system, luminous serpent coiled at base in crimson red (#EF4444), spiraling upward through all seven chakras in authentic colors (red, orange, yellow, green, blue, indigo, violet), reaching crown with white-gold explosion of divine light, meditating figure silhouette in background, professional spiritual energy illustration, dark cosmic background, 8K detailed energy flow, awakening symbolism"
    },
    {
        "id": 102,
        "title": "Tantric Yantra - Shri Yantra 3D",
        "width": 1024,
        "height": 1024,
        "seed": 202,
        "prompt": "Three-dimensional Shri Yantra sacred geometry visualization, nine interlocking triangles appearing to float and rotate in space, golden geometric structure (#D97706) with purple cosmic energy flowing through intersections (#7C3AED), central bindu point glowing intensely white, professional 3D sacred geometry art, depth and dimension, dark starfield background, 8K ultra-precise geometry, tantric meditation focus point"
    },
    {
        "id": 103,
        "title": "Divine Feminine Shakti",
        "width": 1024,
        "height": 1024,
        "seed": 203,
        "prompt": "Abstract representation of Shakti divine feminine energy, flowing luminous purple and pink cosmic form (#7C3AED, #EC4899) suggesting goddess silhouette without specific features, surrounded by lotus petals and sacred geometry, crescent moon above, creative life force energy radiating outward, professional spiritual feminine art, powerful yet graceful, dark cosmic background with nebula clouds, 8K ethereal goddess detail, universal feminine principle"
    },
    {
        "id": 104,
        "title": "Divine Masculine Shiva",
        "width": 1024,
        "height": 1024,
        "seed": 204,
        "prompt": "Abstract representation of Shiva divine masculine consciousness, strong luminous golden and white cosmic form (#D97706, #FFFFFF) suggesting meditative figure without specific features, third eye prominent and glowing indigo (#6366F1), surrounded by trident symbol and sacred geometry, full sun above, pure awareness energy emanating, professional spiritual masculine art, strong yet peaceful, dark mountain peak background, 8K powerful consciousness detail, universal masculine principle"
    },
    {
        "id": 105,
        "title": "Soul Connection Visualization",
        "width": 1080,
        "height": 1920,
        "seed": 205,
        "prompt": "Two luminous soul spheres floating in cosmic space, left sphere in deep purple (#7C3AED), right sphere in warm gold (#D97706), connected by flowing ribbons of light energy spiraling between them suggesting communication and bond, heart-shaped energy field forming around connection point, beautiful starfield background, professional relationship visualization art, romantic yet spiritual, 8K detailed energy ribbons, soul mate connection symbolism"
    },
    {
        "id": 106,
        "title": "Karmic Bond Visualization",
        "width": 1024,
        "height": 1024,
        "seed": 206,
        "prompt": "Two human silhouettes standing facing each other, connected by intricate golden thread patterns weaving between their heart chakras and solar plexus areas (#D97706), threads forming complex web suggesting karmic history, purple cosmic background with clockwork and infinity symbols barely visible (#6B21A8), professional karmic relationship art, mystical and profound, past life connection aesthetic, 8K detailed thread work, destiny bond symbolism"
    },
    {
        "id": 107,
        "title": "Twin Flame Union",
        "width": 1024,
        "height": 1024,
        "seed": 207,
        "prompt": "Abstract representation of twin flame connection, single luminous flame splitting into two perfect mirror images (purple #7C3AED and gold #D97706) that dance around each other creating infinity symbol pattern, intense white-gold light where flames touch, sacred geometry and mirror reflections in background, professional twin flame spiritual art, intense but harmonious, cosmic starfield background, 8K detailed flame dance, one soul two bodies symbolism"
    },
    {
        "id": 108,
        "title": "Aura Interaction - Love Connection",
        "width": 1024,
        "height": 1024,
        "seed": 208,
        "prompt": "Side view of two human silhouettes in close proximity, aura fields visible as luminous energy bodies extending beyond physical form, left aura in purple gradient (#7C3AED to #A855F7), right aura in gold gradient (#D97706 to #F59E0B), auras overlapping and blending in center creating new colors, heart chakras both glowing bright green, professional aura photography style with spiritual enhancement, dark background, 8K detailed aura layers, energetic compatibility visualization"
    },
    {
        "id": 109,
        "title": "Relationship Chakra Alignment",
        "width": 1080,
        "height": 1920,
        "seed": 209,
        "prompt": "Vertical visualization showing two human silhouettes side by side, both displaying seven glowing chakras in authentic colors aligned vertically (red, orange, yellow, green, blue, indigo, violet), golden energy bridges connecting matching chakras between figures, harmonic resonance waves visible, professional spiritual relationship compatibility art, symmetrical composition, purple cosmic background (#6B21A8), 8K detailed chakra alignment, energetic harmony symbolism"
    },
    {
        "id": 110,
        "title": "Meridian Energy Map",
        "width": 1024,
        "height": 1536,
        "seed": 210,
        "prompt": "Human figure in standing meditation pose showing complete meridian system, luminous golden lines (#D97706) tracing all 12 primary meridians through body, acupuncture points glowing as small stars along meridian paths, energy flowing visible as subtle light animation effects, professional medical illustration style with spiritual enhancement, dark indigo background (#1E1B4B), anatomically accurate, 8K detailed meridian mapping, life force flow visualization"
    },
    {
        "id": 111,
        "title": "Chakra Energy Flow - Giving & Receiving",
        "width": 1024,
        "height": 1024,
        "seed": 211,
        "prompt": "Circular diagram showing human figure in lotus position at center, seven chakras glowing in authentic colors, energy flowing upward through chakras (receiving cosmic energy from crown, grounding energy from root), infinity symbol pattern showing circulation, arrows indicating flow direction, professional spiritual energy diagram, educational yet beautiful, dark background with subtle sacred geometry grid, 8K clear instructional detail, energy balance visualization"
    },
    {
        "id": 112,
        "title": "Cosmic Energy Download",
        "width": 1024,
        "height": 1536,
        "seed": 212,
        "prompt": "Meditating figure silhouette viewed from behind, brilliant white-gold light beam descending from cosmic source above entering through crown chakra, light spreading through entire body and chakra system illuminating all in turn, energy overflowing from heart chakra outward to world, professional spiritual channeling art, powerful but peaceful, cosmic starfield background transitioning to earthly ground below, 8K detailed light penetration, divine channel symbolism"
    },
    {
        "id": 113,
        "title": "Aura Layers Anatomy",
        "width": 1024,
        "height": 1024,
        "seed": 213,
        "prompt": "Human figure in center showing seven concentric aura layers extending outward, each layer labeled by color: physical (red), emotional (orange), mental (yellow), astral (green), etheric template (blue), celestial (indigo), ketheric (violet), professional metaphysical anatomy illustration, semi-transparent layers, sacred geometry patterns in outer layers, dark background, 8K educational detail, multidimensional body visualization"
    },
    {
        "id": 114,
        "title": "Deep Meditation - Theta State",
        "width": 1080,
        "height": 1920,
        "seed": 214,
        "prompt": "Serene meditating figure in lotus position, head surrounded by flowing purple theta wave patterns (#7C3AED) visualized as smooth sine waves, third eye chakra glowing intensely indigo (#6366F1), body partially dissolving into cosmic particles suggesting transcendence, professional meditation state visualization, peaceful and deep, dark starfield background, 8K detailed wave patterns, deep trance state symbolism"
    },
    {
        "id": 115,
        "title": "Spiritual Awakening Moment",
        "width": 1024,
        "height": 1024,
        "seed": 215,
        "prompt": "Human figure in meditation with arms slightly raised, massive burst of brilliant white-golden light exploding from crown chakra upward and outward like supernova, all chakras simultaneously illuminated and spinning, sacred geometry patterns appearing in air around figure, professional spiritual awakening art, dramatic but reverent, cosmic background with reality seeming to shatter into light, 8K intense illumination detail, enlightenment moment symbolism"
    },
    {
        "id": 116,
        "title": "Astral Projection Journey",
        "width": 1080,
        "height": 1920,
        "seed": 216,
        "prompt": "Two versions of same figure, physical body sitting in meditation position at bottom in dim light, astral body as luminous purple-silver duplicate floating upward and outward (#8B5CF6, #E2E8F0), silver cord connecting astral body to physical body at solar plexus, cosmic star tunnel in background suggesting travel, professional astral projection visualization, mystical and ethereal, 8K detailed astral form, consciousness travel symbolism"
    },
    {
        "id": 117,
        "title": "Transcendent Unity Consciousness",
        "width": 1024,
        "height": 1024,
        "seed": 217,
        "prompt": "Human figure in meditation dissolving into geometric light patterns that expand outward to merge with cosmic web of interconnected nodes and lines suggesting all of existence, figure at center but also everywhere simultaneously, purple to gold to white color progression (#6B21A8 to #D97706 to #FFFFFF), professional unity consciousness art, profound and expansive, infinite cosmic space background, 8K dissolution detail, 'I am the universe' symbolism"
    },
    {
        "id": 118,
        "title": "Yoga Asana - Tree Pose with Energy Overlay",
        "width": 1024,
        "height": 1536,
        "seed": 218,
        "prompt": "Graceful human silhouette in Vrikshasana (tree pose) standing on one leg with perfect balance, arms raised overhead with hands in prayer position, all seven chakras glowing in authentic colors along spine, luminous roots extending from standing foot deep into earth (grounding), luminous branches extending from hands toward cosmos (reaching), professional yoga spiritual art, elegant and balanced, gradient background from earth brown below to cosmic purple above (#78350F to #6B21A8), 8K detailed energy roots and branches, as above so below symbolism"
    },
    {
        "id": 119,
        "title": "Crystal Healing Meditation Setup",
        "width": 1080,
        "height": 1920,
        "seed": 219,
        "prompt": "Overhead view of meditation space showing person lying in Savasana (corpse pose) surrounded by arrangement of healing crystals placed on and around body, seven different crystal types aligned with seven chakras (red jasper at root, carnelian at sacral, citrine at solar plexus, rose quartz at heart, blue lace agate at throat, amethyst at third eye, clear quartz at crown), each crystal glowing with corresponding chakra color, sacred geometry grid pattern beneath, professional crystal healing setup photography, serene and powerful, soft purple ambient light (#8B5CF6), 8K photorealistic crystal detail, energy healing symbolism"
    }
]


def generate_image(prompt_data, index, total):
    """Generate single image with FLUX 1.1 Pro"""
    print(f"\n[{index}/{total}] Image {prompt_data['id']:03d}: {prompt_data['title']}")
    print(f"  Size: {prompt_data['width']}x{prompt_data['height']}")
    print(f"  Seed: {prompt_data['seed']}")

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
            response = requests.get(image_url)

            # Save with descriptive filename
            filename = f"{prompt_data['id']:03d}_{prompt_data['title'].lower().replace(' ', '_').replace('-', '_')[:50]}.png"
            filepath = OUTPUT_DIR / filename

            with open(filepath, 'wb') as f:
                f.write(response.content)

            file_size = len(response.content)

            print(f"  ✅ Generated in {elapsed:.1f}s")
            print(f"  💾 Saved: {filename} ({file_size/1024:.1f} KB)")

            return {
                'id': prompt_data['id'],
                'index': index,
                'title': prompt_data['title'],
                'filename': filename,
                'filepath': str(filepath),
                'size': f"{prompt_data['width']}x{prompt_data['height']}",
                'file_size_bytes': file_size,
                'seed': prompt_data['seed'],
                'generation_time_seconds': elapsed,
                'estimated_cost_usd': COST_PER_IMAGE_MIN,  # Conservative estimate
                'timestamp': datetime.now().isoformat(),
                'model': MODEL,
                'settings': SETTINGS
            }
        else:
            print(f"  ❌ No image returned")
            return None

    except Exception as e:
        print(f"  ❌ Error: {e}")
        return None


def main():
    print("=" * 70)
    print("FLUX 1.1 Pro - Additional Images Generator (100-119)")
    print("SpiritAtlas Premium Spiritual Imagery")
    print("=" * 70)

    # Create output directory
    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

    print(f"\n📁 Output directory: {OUTPUT_DIR}")
    print(f"🎨 Total prompts: {len(PROMPTS)}")

    # Cost estimate
    min_cost = len(PROMPTS) * COST_PER_IMAGE_MIN
    max_cost = len(PROMPTS) * COST_PER_IMAGE_MAX
    print(f"\n💰 Estimated cost: ${min_cost:.2f} - ${max_cost:.2f}")
    print(f"   Budget: ${BUDGET:.2f}")

    if max_cost > BUDGET:
        print(f"\n⚠️  WARNING: Maximum estimate (${max_cost:.2f}) exceeds budget (${BUDGET:.2f})")
        print(f"   Actual cost typically closer to ${min_cost:.2f}")

    print("\n📊 Focus areas:")
    print("   - Tantric & Sacred Union (100-104): 5 images")
    print("   - Relationship Dynamics (105-109): 5 images")
    print("   - Energy Flow Diagrams (110-113): 4 images")
    print("   - Meditation States (114-117): 4 images")
    print("   - Spiritual Practices (118-119): 2 images")

    # Confirmation
    response = input("\n🚀 Proceed with generation? (yes/no): ")
    if response.lower() not in ['yes', 'y']:
        print("❌ Generation cancelled")
        return

    # Load existing manifest
    manifest = []
    if MANIFEST_FILE.exists():
        with open(MANIFEST_FILE, 'r') as f:
            manifest = json.load(f)
        print(f"\n📄 Loaded existing manifest ({len(manifest)} images)")

    # Generate images
    print(f"\n🎨 Starting generation with FLUX 1.1 Pro...")
    print(f"   Model: {MODEL}")
    print(f"   Settings: {SETTINGS}")

    start_time = time.time()
    generated = 0
    failed = 0
    total_cost = 0.0

    for i, prompt_data in enumerate(PROMPTS, 1):
        result = generate_image(prompt_data, i, len(PROMPTS))

        if result:
            manifest.append(result)
            generated += 1
            total_cost += result['estimated_cost_usd']

            # Save manifest after each image
            with open(MANIFEST_FILE, 'w') as f:
                json.dump(manifest, f, indent=2)

            print(f"   Running total: ${total_cost:.2f} / ${BUDGET:.2f} budget")
        else:
            failed += 1

        # Small delay to avoid rate limiting
        time.sleep(0.5)

    # Summary
    total_time = time.time() - start_time

    print("\n" + "=" * 70)
    print("GENERATION COMPLETE!")
    print("=" * 70)
    print(f"✅ Successfully generated: {generated}/{len(PROMPTS)}")
    if failed > 0:
        print(f"❌ Failed: {failed}")
    print(f"⏱️  Total time: {total_time/60:.1f} minutes")
    print(f"   Average: {total_time/generated:.1f} seconds per image")
    print(f"💰 Estimated total cost: ${total_cost:.2f}")
    print(f"   Under budget: ${BUDGET - total_cost:.2f}")
    print(f"📁 Output directory: {OUTPUT_DIR}")
    print(f"📄 Manifest: {MANIFEST_FILE}")

    print("\n📋 Next steps:")
    print("   1. Review generated images for quality")
    print("   2. Convert PNG to WebP format")
    print("   3. Run optimize_for_android.py to create density variants")
    print("   4. Update app/src/main/res/resource_mapping.json")
    print("   5. Copy files to drawable-* directories")
    print("=" * 70)


if __name__ == "__main__":
    main()
