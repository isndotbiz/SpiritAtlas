#!/usr/bin/env python3
"""
SpiritAtlas Full Asset Library Generator
Generate all 59 spiritual images using fal.ai or Replicate

Usage:
    python generate_images.py --provider fal
    python generate_images.py --provider replicate --output ./my_assets
    python generate_images.py --provider fal --categories numerology astrology
    python generate_images.py --list
"""

import os
import sys
import json
import time
import argparse
from pathlib import Path
from datetime import datetime
from concurrent.futures import ThreadPoolExecutor, as_completed
from typing import Dict, List, Optional

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

def load_prompts(prompts_file: Path) -> Dict:
    """Load prompts from JSON file"""
    if not prompts_file.exists():
        print(f"ERROR: Prompts file not found: {prompts_file}")
        sys.exit(1)

    with open(prompts_file, 'r') as f:
        return json.load(f)

def generate_with_fal(prompt: str, size: str, api_key: str) -> Optional[str]:
    """Generate image using fal.ai"""
    try:
        import fal_client
    except ImportError:
        print("ERROR: fal-client not installed. Run: pip3 install fal-client")
        return None

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

        if result and 'images' in result and len(result['images']) > 0:
            return result['images'][0]['url']
        return None

    except Exception as e:
        print(f"ERROR: {str(e)}")
        return None

def generate_with_replicate(prompt: str, size: str, api_key: str) -> Optional[str]:
    """Generate image using Replicate"""
    try:
        import replicate
    except ImportError:
        print("ERROR: replicate not installed. Run: pip3 install replicate")
        return None

    os.environ['REPLICATE_API_TOKEN'] = api_key

    # Parse size
    size_map = {
        '512x512': (512, 512),
        '768x768': (768, 768),
        '1024x1024': (1024, 1024),
        '1024x1536': (1024, 1536),
        '1536x1024': (1536, 1024),
    }
    width, height = size_map.get(size, (1024, 1024))

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

        if output and len(output) > 0:
            return output[0]
        return None

    except Exception as e:
        print(f"ERROR: {str(e)}")
        return None

def download_image(url: str, output_path: Path) -> bool:
    """Download image from URL"""
    try:
        import requests
    except ImportError:
        print("ERROR: requests not installed. Run: pip3 install requests")
        return False

    try:
        response = requests.get(url, timeout=60)
        response.raise_for_status()

        output_path.parent.mkdir(parents=True, exist_ok=True)

        with open(output_path, 'wb') as f:
            f.write(response.content)

        return True

    except Exception as e:
        print(f"ERROR downloading: {str(e)}")
        return False

def generate_asset(asset_name: str, asset_data: Dict, provider: str, api_key: str,
                  output_dir: Path, index: int, total: int) -> Dict:
    """Generate a single asset"""
    start_time = time.time()

    prompt = asset_data['prompt']
    size = asset_data.get('size', '1024x1024')
    category = asset_data.get('category', 'general')

    # Output path
    output_path = output_dir / category / f"{asset_name}.png"

    # Check if already exists
    if output_path.exists():
        elapsed = time.time() - start_time
        return {
            'success': True,
            'asset': asset_name,
            'path': output_path,
            'elapsed': elapsed,
            'skipped': True
        }

    # Generate image
    if provider == 'fal':
        image_url = generate_with_fal(prompt, size, api_key)
    else:
        image_url = generate_with_replicate(prompt, size, api_key)

    if not image_url:
        return {
            'success': False,
            'asset': asset_name,
            'error': 'Generation failed'
        }

    # Download image
    if download_image(image_url, output_path):
        elapsed = time.time() - start_time
        return {
            'success': True,
            'asset': asset_name,
            'path': output_path,
            'elapsed': elapsed,
            'skipped': False
        }
    else:
        return {
            'success': False,
            'asset': asset_name,
            'error': 'Download failed'
        }

def list_prompts(prompts: Dict, categories: Optional[List[str]] = None):
    """List all prompts without generating"""
    print("\n" + "=" * 70)
    print("SPIRITATLAS ASSET LIBRARY PROMPTS")
    print("=" * 70)

    total = 0

    for category_name, category_assets in prompts.items():
        if categories and category_name not in categories:
            continue

        print(f"\n{category_name.upper()} ({len(category_assets)} images)")
        print("-" * 70)

        for asset_name, asset_data in category_assets.items():
            prompt = asset_data['prompt']
            size = asset_data.get('size', '1024x1024')
            print(f"\n  {asset_name}")
            print(f"    Size: {size}")
            print(f"    Prompt: {prompt[:100]}...")
            total += 1

    print("\n" + "=" * 70)
    print(f"Total: {total} images")
    print("=" * 70 + "\n")

def main():
    parser = argparse.ArgumentParser(
        description='Generate SpiritAtlas image asset library',
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  python generate_images.py --list                                    # List all prompts
  python generate_images.py --provider fal                           # Generate all with fal.ai
  python generate_images.py --provider replicate                     # Generate all with Replicate
  python generate_images.py --provider fal --categories numerology   # Generate one category
  python generate_images.py --provider fal --skip-existing           # Skip existing images

Categories:
  numerology, astrology, chakras, elements, ayurveda,
  humandesign, transits, backgrounds
        """
    )

    parser.add_argument('--provider', choices=['fal', 'replicate'],
                       help='AI provider to use')
    parser.add_argument('--output', default='./generated_assets',
                       help='Output directory (default: ./generated_assets)')
    parser.add_argument('--categories', nargs='+',
                       help='Specific categories to generate (default: all)')
    parser.add_argument('--list', action='store_true',
                       help='List all prompts without generating')
    parser.add_argument('--batch-size', type=int, default=5,
                       help='Number of concurrent generations (default: 5)')
    parser.add_argument('--skip-existing', action='store_true',
                       help='Skip images that already exist')
    parser.add_argument('--prompts', default='prompts.json',
                       help='Prompts JSON file (default: prompts.json)')

    args = parser.parse_args()

    # Load prompts
    script_dir = Path(__file__).parent
    prompts_file = script_dir / args.prompts
    prompts = load_prompts(prompts_file)

    # List mode
    if args.list:
        list_prompts(prompts, args.categories)
        return

    # Generation mode
    if not args.provider:
        print("ERROR: --provider required for generation")
        print("Use: --provider fal  or  --provider replicate")
        sys.exit(1)

    # Get API keys
    api_keys = get_api_keys()
    api_key = api_keys.get(args.provider)

    if not api_key:
        print(f"ERROR: {args.provider} API key not found in local.properties")
        sys.exit(1)

    # Setup output directory
    output_dir = Path(args.output).resolve()
    output_dir.mkdir(parents=True, exist_ok=True)

    # Filter categories
    if args.categories:
        filtered_prompts = {k: v for k, v in prompts.items() if k in args.categories}
        if not filtered_prompts:
            print(f"ERROR: No matching categories found: {args.categories}")
            sys.exit(1)
        prompts = filtered_prompts

    # Count total assets
    total_assets = sum(len(assets) for assets in prompts.values())

    # Print header
    print("\n" + "=" * 70)
    print("SPIRITATLAS IMAGE GENERATION")
    print("=" * 70)
    print(f"Provider:     {args.provider}")
    print(f"Output:       {output_dir}")
    print(f"Total assets: {total_assets}")
    print(f"Categories:   {', '.join(prompts.keys())}")
    print(f"Batch size:   {args.batch_size}")
    print("=" * 70 + "\n")

    # Prepare asset list
    asset_list = []
    for category_name, category_assets in prompts.items():
        for asset_name, asset_data in category_assets.items():
            asset_list.append({
                'name': asset_name,
                'data': asset_data,
                'category': category_name
            })

    # Generate images
    start_time = time.time()
    successful = 0
    failed = 0
    skipped = 0

    with ThreadPoolExecutor(max_workers=args.batch_size) as executor:
        futures = []

        for i, asset_info in enumerate(asset_list):
            future = executor.submit(
                generate_asset,
                asset_info['name'],
                asset_info['data'],
                args.provider,
                api_key,
                output_dir,
                i + 1,
                total_assets
            )
            futures.append((future, asset_info['name'], i + 1))

        # Process results
        for future, asset_name, index in futures:
            result = future.result()

            if result['success']:
                if result.get('skipped'):
                    status = '↷ skipped'
                    skipped += 1
                else:
                    status = f"✓ ({result['elapsed']:.1f}s)"
                    successful += 1
                print(f"[{index:2d}/{total_assets}] {asset_name:40s} {status}")
            else:
                status = f"✗ {result.get('error', 'failed')}"
                failed += 1
                print(f"[{index:2d}/{total_assets}] {asset_name:40s} {status}")

    # Summary
    total_time = time.time() - start_time
    minutes = int(total_time // 60)
    seconds = int(total_time % 60)

    print("\n" + "=" * 70)
    print("GENERATION COMPLETE")
    print("=" * 70)
    print(f"Successful:    {successful}")
    if skipped > 0:
        print(f"Skipped:       {skipped}")
    if failed > 0:
        print(f"Failed:        {failed}")
    print(f"Total time:    {minutes}m {seconds}s")

    # Cost estimate
    cost_per_image = 0.05 if args.provider == 'fal' else 0.003
    total_cost = successful * cost_per_image
    print(f"Estimated cost: ${total_cost:.2f}")

    print(f"\nOutput directory: {output_dir}")
    print("=" * 70 + "\n")

    # Instructions
    print("Next steps:")
    print("1. Review generated images")
    print("2. Convert to WebP for Android (optional):")
    print(f"   cd {output_dir}")
    print("   for img in */*.png; do cwebp -q 90 \"$img\" -o \"${img%.png}.webp\"; done")
    print("3. Copy to Android project:")
    print("   cp */*.png /path/to/SpiritAtlas/app/src/main/res/drawable/")
    print()

if __name__ == '__main__':
    main()
