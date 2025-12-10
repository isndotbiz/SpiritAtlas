#!/usr/bin/env python3
"""
Tantric Image Beautification Generator
Agent 7-9: Generate 3 premium beautified tantric images

Budget: $0.15
Quality Target: 9.8/10
"""

import os
import sys
from pathlib import Path
from datetime import datetime
import time

try:
    import fal_client
except ImportError:
    print("❌ fal_client not installed. Install with: pip install fal-client")
    sys.exit(1)

# Configuration
MODEL = "fal-ai/flux-pro/v1.1"
OUTPUT_DIR = Path("generated_images/tantric_beautified")
OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

COST_PER_IMAGE = 0.05  # FLUX Pro v1.1 pricing

# Image definitions
IMAGES = [
    {
        "id": "019",
        "title": "Sacred Tantric Background",
        "filename": "019_tantric_intimacy_screen_background_beautified.png",
        "width": 1920,
        "height": 1080,
        "seed": 2501,
        "prompt": """Mystical tantric background featuring flowing divine energies in cosmic space. Soft billowing clouds of purple (#7C3AED) and pink (#EC4899) nebulae representing divine feminine energy flowing from left, golden (#D97706) and warm orange solar clouds representing divine masculine energy flowing from right, meeting in harmonious sensual dance at center. Subtle Sri Yantra sacred geometry pattern barely visible within the cloud formations, glowing softly. Flowing energy streams suggest sacred union and balance. Ethereal stardust particles. Dreamy bokeh light effects. Professional spiritual tantric art background, tasteful and reverent, suitable for meditation and intimate spiritual practice. Deep cosmic space gradient from purple to gold. 8K ultra-detailed clouds, peaceful yet energetically charged atmosphere.""",
        "negative_prompt": """explicit content, nudity, sexual imagery, realistic human figures, inappropriate, graphic, pornographic, dark themes, scary, aggressive, busy patterns, text, watermark, harsh colors, neon, cartoonish, low quality, pixelated"""
    },
    {
        "id": "034",
        "title": "Divine Sacred Union",
        "filename": "034_sacred_union_beautified.png",
        "width": 1024,
        "height": 1024,
        "seed": 2502,
        "prompt": """Transcendent representation of sacred tantric union. Two luminous energy streams spiraling together in cosmic space - left stream flowing with divine feminine energy in radiant purple, pink, and violet tones (#7C3AED, #EC4899, #A855F7), right stream flowing with divine masculine energy in brilliant gold, amber, and white tones (#D97706, #F59E0B, #FFFFFF). Both streams intertwining in perfect double helix spiral, creating vesica piscis at their meeting point which glows with brilliant white-gold divine light. Sacred geometry patterns - flower of life, Sri Yantra triangles - integrated subtly throughout energy flow. Lotus petals unfurling around central union point. Cosmic starfield background transitioning from purple to gold. Professional premium spiritual tantric art, reverent and tasteful, no explicit imagery. 8K ultra-detailed energy ribbons with depth, iridescent glow effects, sense of divine ecstasy and cosmic orgasm of creation, pure spiritual love visualization.""",
        "negative_prompt": """explicit content, nudity, human bodies, sexual organs, inappropriate, graphic, pornographic, realistic people, faces, physical intimacy, dark themes, scary, aggressive, text, watermark, cartoon, anime, low quality, flat, dull colors"""
    },
    {
        "id": "102",
        "title": "Transcendent Sri Yantra Portal",
        "filename": "102_tantric_yantra_shri_yantra_3d_beautified.png",
        "width": 1024,
        "height": 1024,
        "seed": 2503,
        "prompt": """Transcendent three-dimensional Sri Yantra sacred geometry as cosmic meditation portal. Nine interlocking triangles (five feminine pointing down in purple-pink gradient #7C3AED to #EC4899, four masculine pointing up in gold-white gradient #D97706 to #FFFFFF) appearing to float and rotate in infinite cosmic space, creating perfect dimensional depth. Each intersection point (marma) glowing intensely with concentrated spiritual energy. Central bindu (sacred point) radiating brilliant white-gold divine light like a gateway to infinity. Purple cosmic nebulae flowing through and around geometric structure, emphasizing the sacred tantric energy channels. Subtle outer lotus petal rings barely visible. Professional premium 3D sacred geometry art, mathematically precise yet mystically alive. Dark starfield background with distant galaxies. 8K ultra-precise geometric rendering with ethereal glow effects, sense of portal to higher dimensions, powerful tantric meditation focus point, gateway to cosmic consciousness.""",
        "negative_prompt": """flat 2D, simple, basic geometry, text, labels, watermark, busy background, realistic textures, photo style, cartoon, low quality, imprecise geometry, cluttered, distorted triangles, incorrect yantra structure"""
    }
]

def generate_image(image_config):
    """Generate a single image using fal.ai FLUX Pro v1.1"""
    print(f"\n[{image_config['id']}] Generating: {image_config['title']}")
    print(f"  Size: {image_config['width']}x{image_config['height']}")
    print(f"  Seed: {image_config['seed']}")

    start_time = time.time()

    try:
        result = fal_client.submit(
            MODEL,
            arguments={
                "prompt": image_config["prompt"],
                "negative_prompt": image_config["negative_prompt"],
                "image_size": {
                    "width": image_config["width"],
                    "height": image_config["height"]
                },
                "num_images": 1,
                "guidance_scale": 3.5,
                "num_inference_steps": 28,
                "seed": image_config["seed"],
                "safety_tolerance": 2,
                "output_format": "png"
            }
        ).get()

        generation_time = time.time() - start_time

        # Download and save image
        image_url = result["images"][0]["url"]

        import requests
        response = requests.get(image_url)
        output_path = OUTPUT_DIR / image_config["filename"]

        with open(output_path, 'wb') as f:
            f.write(response.content)

        file_size_kb = len(response.content) / 1024

        print(f"  ✅ Generated in {generation_time:.1f}s")
        print(f"  💾 Saved: {output_path.name} ({file_size_kb:.1f} KB)")

        return {
            "success": True,
            "path": str(output_path),
            "generation_time": generation_time,
            "file_size_kb": file_size_kb
        }

    except Exception as e:
        print(f"  ❌ Error: {str(e)}")
        return {
            "success": False,
            "error": str(e)
        }

def main():
    """Main execution"""
    print("=" * 70)
    print("TANTRIC IMAGE BEAUTIFICATION - Agent 7-9")
    print("=" * 70)
    print(f"Model: {MODEL}")
    print(f"Output: {OUTPUT_DIR}")
    print(f"Budget: ${len(IMAGES) * COST_PER_IMAGE:.2f} ({len(IMAGES)} images × ${COST_PER_IMAGE})")
    print("=" * 70)

    # Check for FAL_KEY
    if not os.getenv("FAL_KEY"):
        print("\n❌ FAL_KEY environment variable not set!")
        print("Set it with: export FAL_KEY=your_key_here")
        print("Or create .env file with: FAL_KEY=your_key_here")
        sys.exit(1)

    print("\n🎨 Starting beautification generation...\n")

    results = []
    total_cost = 0
    successful = 0

    for i, image_config in enumerate(IMAGES, 1):
        print(f"\n{'='*70}")
        print(f"[{i}/{len(IMAGES)}] Processing Image {image_config['id']}")
        print('='*70)

        result = generate_image(image_config)
        results.append(result)

        if result["success"]:
            successful += 1
            total_cost += COST_PER_IMAGE
            print(f"   Running total: ${total_cost:.2f}")

        # Small delay between generations
        if i < len(IMAGES):
            time.sleep(2)

    # Summary
    print("\n" + "=" * 70)
    print("GENERATION COMPLETE")
    print("=" * 70)
    print(f"✅ Successful: {successful}/{len(IMAGES)}")
    print(f"❌ Failed: {len(IMAGES) - successful}/{len(IMAGES)}")
    print(f"💰 Total Cost: ${total_cost:.2f}")
    print(f"📁 Output Directory: {OUTPUT_DIR.absolute()}")

    if successful == len(IMAGES):
        print("\n🎉 All images beautified successfully!")
        print("\nNext steps:")
        print("1. Review images in:", OUTPUT_DIR.absolute())
        print("2. Compare with originals")
        print("3. Run optimization: python3 optimize_for_android.py")
        print("4. Deploy to app resources")
    else:
        print("\n⚠️  Some images failed. Check errors above.")

    print("=" * 70)

    # Save results log
    log_file = OUTPUT_DIR / f"generation_log_{datetime.now().strftime('%Y%m%d_%H%M%S')}.txt"
    with open(log_file, 'w') as f:
        f.write(f"Tantric Beautification Generation Log\n")
        f.write(f"Generated: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}\n")
        f.write(f"Model: {MODEL}\n")
        f.write(f"Total Cost: ${total_cost:.2f}\n\n")

        for i, (image, result) in enumerate(zip(IMAGES, results), 1):
            f.write(f"\n[{i}] {image['title']} (ID: {image['id']})\n")
            f.write(f"    Filename: {image['filename']}\n")
            f.write(f"    Size: {image['width']}x{image['height']}\n")
            f.write(f"    Seed: {image['seed']}\n")
            if result['success']:
                f.write(f"    ✅ Success - {result['generation_time']:.1f}s, {result['file_size_kb']:.1f} KB\n")
            else:
                f.write(f"    ❌ Failed - {result['error']}\n")

    print(f"\n📝 Log saved to: {log_file}")

if __name__ == "__main__":
    main()
