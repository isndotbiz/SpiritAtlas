#!/usr/bin/env python3
"""
Quick test: Generate one image to verify API and settings
"""

import os
import time
import fal_client
import requests
from pathlib import Path

# API Key (from example_usage.py)
API_KEY = "0c6099f4-b6d7-4677-9062-b828e5155e89:171bfb7b414e56079497806b6a29cf32"
os.environ['FAL_KEY'] = API_KEY

# Test configuration
MODEL = "fal-ai/flux-pro/v1.1"
OUTPUT_DIR = Path("test_output")
OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

# Simple test prompt
TEST_PROMPT = """Abstract representation of sacred tantric union showing two luminous energy
silhouettes merging, left figure radiating golden masculine solar energy (#D97706), right figure
emanating purple feminine lunar energy (#7C3AED), creating harmonious heart-shaped energy field
where they meet, subtle sacred geometry mandala background, professional spiritual tantric art,
tasteful and reverent, no explicit imagery, cosmic background with stars, 8K ethereal detail,
divine union symbolism"""

print("=" * 70)
print("FLUX 1.1 Pro - API Test")
print("=" * 70)
print(f"Model: {MODEL}")
print(f"Output: {OUTPUT_DIR}")
print()
print("Testing with prompt:")
print(f"  '{TEST_PROMPT[:80]}...'")
print()

try:
    start_time = time.time()

    print("🚀 Calling fal.ai API...")
    result = fal_client.subscribe(
        MODEL,
        arguments={
            "prompt": TEST_PROMPT,
            "image_size": {
                "width": 1024,
                "height": 1024
            },
            "num_inference_steps": 28,
            "guidance_scale": 3.5,
            "num_images": 1,
            "enable_safety_checker": True,
            "safety_tolerance": 2,
            "output_format": "png",
            "seed": 200
        }
    )

    elapsed = time.time() - start_time

    if result and 'images' in result and result['images']:
        image_url = result['images'][0]['url']
        print(f"✅ Generated in {elapsed:.1f}s")
        print(f"   URL: {image_url[:60]}...")

        # Download image
        print("📥 Downloading image...")
        response = requests.get(image_url)

        filepath = OUTPUT_DIR / "test_100_sacred_union.png"
        with open(filepath, 'wb') as f:
            f.write(response.content)

        file_size = len(response.content)
        print(f"✅ Saved: {filepath}")
        print(f"   Size: {file_size/1024:.1f} KB")
        print()
        print("=" * 70)
        print("SUCCESS! API is working correctly.")
        print("=" * 70)
        print(f"Estimated cost: $0.04-0.20 per image")
        print(f"Ready to generate 20 images (budget: $5.00)")
    else:
        print("❌ No image returned from API")

except Exception as e:
    print(f"❌ Error: {e}")
    print()
    print("Possible issues:")
    print("  - API key invalid or expired")
    print("  - Rate limiting")
    print("  - Network connectivity")
    print("  - Model not available")
