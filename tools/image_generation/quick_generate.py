#!/usr/bin/env python3
"""
SpiritAtlas Quick Image Generator
Simple CLI tool for generating single images with fal.ai or Replicate

Usage:
    python quick_generate.py "your prompt here"
    python quick_generate.py "chakra meditation" --provider fal --size 1024x1024
    python quick_generate.py "zodiac constellation" --provider replicate --output ~/Desktop/zodiac.png
"""

import os
import sys
import argparse
import time
from pathlib import Path
from datetime import datetime

# Parse local.properties for API keys
def get_api_keys():
    """Extract API keys from local.properties"""
    script_dir = Path(__file__).parent
    project_root = script_dir.parent.parent
    local_props = project_root / "local.properties"

    keys = {}

    if local_props.exists():
        with open(local_props, 'r') as f:
            for line in f:
                line = line.strip()
                if line.startswith('fal.api.key='):
                    keys['fal'] = line.split('=', 1)[1].strip()
                elif line.startswith('replicate.api.key='):
                    keys['replicate'] = line.split('=', 1)[1].strip()

    return keys

# Image size presets
SIZE_PRESETS = {
    '512x512': (512, 512),
    '768x768': (768, 768),
    '1024x1024': (1024, 1024),
    '1024x1536': (1024, 1536),  # Portrait
    '1536x1024': (1536, 1024),  # Landscape
}

def generate_with_fal(prompt, size='1024x1024', api_key=None):
    """Generate image using fal.ai"""
    try:
        import fal_client
    except ImportError:
        print("ERROR: fal-client not installed. Run: pip3 install fal-client")
        sys.exit(1)

    if not api_key:
        print("ERROR: fal.ai API key not found in local.properties")
        print("Add: fal.api.key=YOUR_KEY")
        print("Get key from: https://fal.ai/dashboard/keys")
        sys.exit(1)

    # Set API key
    os.environ['FAL_KEY'] = api_key

    # Map size to fal.ai format
    size_map = {
        '512x512': 'square',
        '768x768': 'square',
        '1024x1024': 'square_hd',
        '1024x1536': 'portrait_4_3',
        '1536x1024': 'landscape_4_3',
    }

    image_size = size_map.get(size, 'square_hd')

    print(f"Provider: fal.ai (Flux Schnell)")
    print(f"Prompt: {prompt}")
    print(f"Size: {size} ({image_size})")
    print("Generating...", end='', flush=True)

    start_time = time.time()

    try:
        result = fal_client.subscribe(
            "fal-ai/flux-schnell",
            arguments={
                "prompt": prompt,
                "image_size": image_size,
                "num_inference_steps": 4,
                "num_images": 1
            }
        )

        elapsed = time.time() - start_time
        print(f" Done! ({elapsed:.1f}s)")

        if result and 'images' in result and len(result['images']) > 0:
            return result['images'][0]['url']
        else:
            print("ERROR: No image returned from API")
            return None

    except Exception as e:
        print(f"\nERROR: Generation failed: {str(e)}")
        return None

def generate_with_replicate(prompt, size='1024x1024', api_key=None):
    """Generate image using Replicate"""
    try:
        import replicate
    except ImportError:
        print("ERROR: replicate not installed. Run: pip3 install replicate")
        sys.exit(1)

    if not api_key:
        print("ERROR: Replicate API key not found in local.properties")
        print("Add: replicate.api.key=YOUR_KEY")
        print("Get key from: https://replicate.com/account/api-tokens")
        sys.exit(1)

    # Set API key
    os.environ['REPLICATE_API_TOKEN'] = api_key

    width, height = SIZE_PRESETS.get(size, (1024, 1024))

    print(f"Provider: Replicate (SDXL)")
    print(f"Prompt: {prompt}")
    print(f"Size: {size} ({width}x{height})")
    print("Generating...", end='', flush=True)

    start_time = time.time()

    try:
        output = replicate.run(
            "stability-ai/sdxl:39ed52f2a78e934b3ba6e2a89f5b1c712de7dfea535525255b1aa35c5565e08b",
            input={
                "prompt": prompt,
                "width": width,
                "height": height,
                "num_outputs": 1
            }
        )

        elapsed = time.time() - start_time
        print(f" Done! ({elapsed:.1f}s)")

        if output and len(output) > 0:
            return output[0]
        else:
            print("ERROR: No image returned from API")
            return None

    except Exception as e:
        print(f"\nERROR: Generation failed: {str(e)}")
        return None

def download_image(url, output_path):
    """Download image from URL"""
    try:
        import requests
    except ImportError:
        print("ERROR: requests not installed. Run: pip3 install requests")
        sys.exit(1)

    print(f"Downloading image...", end='', flush=True)

    try:
        response = requests.get(url, timeout=60)
        response.raise_for_status()

        with open(output_path, 'wb') as f:
            f.write(response.content)

        print(" Done!")
        return True

    except Exception as e:
        print(f"\nERROR: Download failed: {str(e)}")
        return False

def main():
    parser = argparse.ArgumentParser(
        description='Generate a single image using AI',
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  python quick_generate.py "mystical chakra meditation"
  python quick_generate.py "zodiac constellation" --provider fal --size 1024x1024
  python quick_generate.py "sacred geometry" --provider replicate --output ~/Desktop/test.png

Supported sizes:
  512x512, 768x768, 1024x1024, 1024x1536 (portrait), 1536x1024 (landscape)

Providers:
  fal      - Fast, good quality ($0.05/image) [default]
  replicate - Slower, cheaper ($0.003/image)
        """
    )

    parser.add_argument('prompt', help='Image generation prompt')
    parser.add_argument('--provider', choices=['fal', 'replicate'], default='fal',
                       help='AI provider to use (default: fal)')
    parser.add_argument('--size', default='1024x1024', choices=list(SIZE_PRESETS.keys()),
                       help='Image size (default: 1024x1024)')
    parser.add_argument('--output', help='Output file path (default: test_outputs/timestamp.png)')

    args = parser.parse_args()

    # Get API keys
    api_keys = get_api_keys()

    if not api_keys:
        print("ERROR: No API keys found in local.properties")
        print("Please configure your API keys first")
        sys.exit(1)

    # Generate image
    print("=" * 60)
    print("SpiritAtlas Quick Image Generator")
    print("=" * 60)

    if args.provider == 'fal':
        image_url = generate_with_fal(args.prompt, args.size, api_keys.get('fal'))
    else:
        image_url = generate_with_replicate(args.prompt, args.size, api_keys.get('replicate'))

    if not image_url:
        print("\n✗ Generation failed")
        sys.exit(1)

    # Determine output path
    if args.output:
        output_path = Path(args.output).expanduser()
    else:
        script_dir = Path(__file__).parent
        test_dir = script_dir / "test_outputs"
        test_dir.mkdir(exist_ok=True)
        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
        output_path = test_dir / f"quick_gen_{timestamp}.png"

    # Download image
    if download_image(image_url, output_path):
        file_size = output_path.stat().st_size / 1024  # KB
        print(f"\n✓ Success!")
        print(f"  File: {output_path}")
        print(f"  Size: {file_size:.1f} KB")
        print(f"  URL: {image_url}")

        # Try to open image (macOS)
        if sys.platform == 'darwin':
            os.system(f'open "{output_path}"')
    else:
        print("\n✗ Download failed")
        print(f"  You can download manually from: {image_url}")
        sys.exit(1)

    print("=" * 60)

if __name__ == '__main__':
    main()
