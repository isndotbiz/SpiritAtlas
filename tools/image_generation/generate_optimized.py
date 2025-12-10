#!/usr/bin/env python3
"""
Generate all 99 optimized SpiritAtlas images with FLUX 1.1 Pro
Parses OPTIMIZED_FLUX_PRO_PROMPTS_99.md and generates via fal.ai
"""

import os
import re
import json
import time
from pathlib import Path
from datetime import datetime
import requests

try:
    import fal_client
except ImportError:
    print("❌ fal_client not installed. Install with: pip install fal-client")
    exit(1)

# Configuration
MODEL = "fal-ai/flux-pro/v1.1"
OUTPUT_DIR = Path("generated_images/optimized_flux_pro")
MANIFEST_FILE = OUTPUT_DIR / "manifest.json"
COST_PER_IMAGE = 0.04

def parse_optimized_prompts():
    """Parse OPTIMIZED_FLUX_PRO_PROMPTS_99.md"""
    prompts_file = Path("OPTIMIZED_FLUX_PRO_PROMPTS_99.md")

    if not prompts_file.exists():
        print(f"❌ File not found: {prompts_file}")
        return []

    with open(prompts_file, 'r') as f:
        content = f.read()

    prompts = []
    # Split by ### headers
    sections = re.split(r'###\s+\d+\.\d+\s+', content)[1:]  # Skip before first ###

    for section in sections:
        lines = section.strip().split('\n')
        if not lines:
            continue

        title = lines[0].strip()

        # Extract dimensions
        dim_match = re.search(r'\*\*Dimensions:\*\*\s+(\d+)[x×](\d+)', section)
        if not dim_match:
            continue

        width, height = int(dim_match.group(1)), int(dim_match.group(2))

        # Extract prompt
        prompt_match = re.search(r'\*\*Prompt:\*\*\s+(.*?)(?=\n\n|###|$)', section, re.DOTALL)
        if not prompt_match:
            continue

        prompt_text = prompt_match.group(1).strip()

        prompts.append({
            'title': title,
            'prompt': prompt_text,
            'width': width,
            'height': height
        })

    return prompts

def generate_image(prompt_data, index, total):
    """Generate single image with FLUX 1.1 Pro"""
    print(f"\n[{index}/{total}] {prompt_data['title']}")
    print(f"  Size: {prompt_data['width']}x{prompt_data['height']}")

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
                "num_inference_steps": 28,
                "guidance_scale": 3.5,
                "num_images": 1,
                "enable_safety_checker": True,
                "safety_tolerance": 2,
                "output_format": "png"
            }
        )

        elapsed = time.time() - start_time

        if result and 'images' in result and result['images']:
            image_url = result['images'][0]['url']

            # Download image
            response = requests.get(image_url)

            # Save with descriptive filename (sanitize slashes and other special chars)
            filename = f"{index:03d}_{prompt_data['title'].lower().replace(' ', '_').replace('/', '_').replace('(', '').replace(')', '')[:50]}.png"
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
    print("OPTIMIZED FLUX 1.1 PRO GENERATOR")
    print("=" * 60)

    # Create output directory
    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

    # Parse prompts
    print("\n📖 Parsing OPTIMIZED_FLUX_PRO_PROMPTS_99.md...")
    prompts = parse_optimized_prompts()

    if not prompts:
        print("❌ No prompts found!")
        return

    print(f"✅ Found {len(prompts)} prompts")

    # Cost estimate
    total_cost = len(prompts) * COST_PER_IMAGE
    print(f"\n💰 Estimated cost: ${total_cost:.2f}")

    # Load existing manifest
    manifest = []
    if MANIFEST_FILE.exists():
        with open(MANIFEST_FILE, 'r') as f:
            manifest = json.load(f)
        print(f"📄 Loaded existing manifest ({len(manifest)} images)")

    # Generate images
    print(f"\n🚀 Starting generation...")
    print(f"   Model: {MODEL}")

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

        # Small delay
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
