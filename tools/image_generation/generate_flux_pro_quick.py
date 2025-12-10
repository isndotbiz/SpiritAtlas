#!/usr/bin/env python3
"""
Quick FLUX 1.1 Pro generator for SpiritAtlas
Generates all 99 images using existing prompts with FLUX 1.1 Pro model
"""

import os
import json
import re
from pathlib import Path
from datetime import datetime
import time

try:
    import fal_client
except ImportError:
    print("❌ fal_client not installed. Install with: pip install fal-client")
    exit(1)

# Configuration
MODEL = "fal-ai/flux-pro/v1.1"
OUTPUT_DIR = Path("generated_images/flux_pro_v1.1")
MANIFEST_FILE = OUTPUT_DIR / "manifest.json"
COST_PER_IMAGE = 0.04

# FLUX 1.1 Pro optimal settings
SETTINGS = {
    "guidance_scale": 3.5,
    "num_inference_steps": 28,
    "safety_tolerance": 2,
}

def parse_prompts_from_markdown():
    """Parse prompts from IMAGE_GENERATION_PROMPTS_99_FAL.md"""
    prompts_file = Path("IMAGE_GENERATION_PROMPTS_99_FAL.md")

    if not prompts_file.exists():
        print(f"❌ Prompts file not found: {prompts_file}")
        return []

    with open(prompts_file, 'r') as f:
        content = f.read()

    prompts = []
    current_category = None
    current_title = None
    current_size = None
    current_seed = None

    # Split by sections
    sections = re.split(r'###\s+\d+\.\d+\s+', content)

    for section in sections[1:]:  # Skip header
        lines = section.strip().split('\n')
        if not lines:
            continue

        title_line = lines[0]

        # Parse metadata
        size_match = re.search(r'\*\*Size:\*\*\s+(\d+)×(\d+)', section)
        seed_match = re.search(r'\*\*Seed:\*\*\s+(\d+)', section)
        prompt_match = re.search(r'\*\*Prompt:\*\*\s*```\s*\n(.*?)```', section, re.DOTALL)

        if prompt_match:
            width, height = (1024, 1024)  # default
            if size_match:
                width, height = int(size_match.group(1)), int(size_match.group(2))

            seed = int(seed_match.group(1)) if seed_match else 42
            prompt_text = prompt_match.group(1).strip()

            prompts.append({
                'title': title_line,
                'prompt': prompt_text,
                'width': width,
                'height': height,
                'seed': seed
            })

    return prompts

def generate_image(prompt_data, index, total):
    """Generate single image with FLUX 1.1 Pro"""
    print(f"\n[{index}/{total}] {prompt_data['title']}")
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
            import requests
            response = requests.get(image_url)

            # Save with descriptive filename
            filename = f"{index:03d}_{prompt_data['title'].lower().replace(' ', '_')[:50]}.png"
            filepath = OUTPUT_DIR / filename

            with open(filepath, 'wb') as f:
                f.write(response.content)

            print(f"  ✅ Generated in {elapsed:.1f}s")
            print(f"  💾 Saved: {filename}")

            return {
                'index': index,
                'title': prompt_data['title'],
                'filename': filename,
                'filepath': str(filepath),
                'size': f"{prompt_data['width']}x{prompt_data['height']}",
                'seed': prompt_data['seed'],
                'generation_time': elapsed,
                'cost': COST_PER_IMAGE,
                'timestamp': datetime.now().isoformat()
            }
        else:
            print(f"  ❌ No image returned")
            return None

    except Exception as e:
        print(f"  ❌ Error: {e}")
        return None

def main():
    print("=" * 60)
    print("FLUX 1.1 Pro Generator for SpiritAtlas")
    print("=" * 60)

    # Create output directory
    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

    # Parse prompts
    print("\n📖 Parsing prompts from IMAGE_GENERATION_PROMPTS_99_FAL.md...")
    prompts = parse_prompts_from_markdown()

    if not prompts:
        print("❌ No prompts found!")
        return

    print(f"✅ Found {len(prompts)} prompts")

    # Cost estimate
    total_cost = len(prompts) * COST_PER_IMAGE
    print(f"\n💰 Estimated cost: ${total_cost:.2f} ({len(prompts)} images @ ${COST_PER_IMAGE}/image)")

    # Load existing manifest
    manifest = []
    if MANIFEST_FILE.exists():
        with open(MANIFEST_FILE, 'r') as f:
            manifest = json.load(f)
        print(f"📄 Loaded existing manifest ({len(manifest)} images)")

    # Generate images
    print(f"\n🚀 Starting generation with FLUX 1.1 Pro...")
    print(f"   Model: {MODEL}")
    print(f"   Settings: {SETTINGS}")

    start_time = time.time()
    generated = 0
    failed = 0

    for i, prompt_data in enumerate(prompts, 1):
        result = generate_image(prompt_data, i, len(prompts))

        if result:
            manifest.append(result)
            generated += 1

            # Save manifest after each image
            with open(MANIFEST_FILE, 'w') as f:
                json.dump(manifest, f, indent=2)
        else:
            failed += 1

        # Small delay to avoid rate limiting
        time.sleep(0.5)

    # Summary
    total_time = time.time() - start_time
    total_cost_actual = generated * COST_PER_IMAGE

    print("\n" + "=" * 60)
    print("GENERATION COMPLETE!")
    print("=" * 60)
    print(f"✅ Successfully generated: {generated}/{len(prompts)}")
    if failed > 0:
        print(f"❌ Failed: {failed}")
    print(f"⏱️  Total time: {total_time/60:.1f} minutes")
    print(f"💰 Total cost: ${total_cost_actual:.2f}")
    print(f"📁 Output directory: {OUTPUT_DIR}")
    print(f"📄 Manifest: {MANIFEST_FILE}")
    print("=" * 60)

if __name__ == "__main__":
    main()
