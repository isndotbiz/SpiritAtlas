#!/usr/bin/env python3
"""
Advanced WebP Image Optimizer
Applies aggressive optimization while maintaining visual quality
"""

import os
import subprocess
import shutil
from pathlib import Path
from typing import Dict, List, Tuple

# Base paths
BASE_RES_PATH = Path("/Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res")
BACKUP_PATH = Path("/Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation/backup_originals")

# Densities to optimize
DENSITIES = ['mdpi', 'hdpi', 'xhdpi', 'xxhdpi', 'xxxhdpi']

# Optimization profiles based on image type
OPTIMIZATION_PROFILES = {
    'icon_lossless': {
        # App icons, logos - use near-lossless for transparency
        'patterns': ['img_001_', 'img_002_', 'img_004_', 'img_005_', 'img_008_'],
        'quality': 95,
        'method': 6,
        'near_lossless': 60,
        'alpha_quality': 100
    },
    'ui_high': {
        # UI elements, buttons - high quality lossy
        'patterns': ['img_074_', 'img_075_', 'img_076_', 'img_077_', 'img_078_',
                     'img_031_', 'img_032_', 'img_033_'],
        'quality': 90,
        'method': 6,
        'alpha_quality': 95
    },
    'background_medium': {
        # Backgrounds - medium quality, highly optimized
        'patterns': ['img_003_', 'img_009_', 'img_010_', 'img_011_', 'img_012_',
                     'img_013_', 'img_014_', 'img_015_', 'img_016_', 'img_017_',
                     'img_018_', 'img_019_', 'img_020_', 'img_021_', 'img_022_',
                     'img_023_', 'img_024_'],
        'quality': 85,
        'method': 6,
        'preprocessing': 4
    },
    'art_high': {
        # Constellation art, detailed graphics
        'patterns': ['img_034_', 'img_035_', 'img_036_', 'img_037_', 'img_038_',
                     'img_039_', 'img_040_', 'img_041_', 'img_042_', 'img_043_',
                     'img_044_', 'img_045_', 'img_046_', 'img_047_', 'img_048_',
                     'img_049_', 'img_050_', 'img_051_', 'img_052_', 'img_053_',
                     'img_054_', 'img_055_', 'img_056_', 'img_057_', 'img_058_'],
        'quality': 88,
        'method': 6
    },
    'illustration': {
        # Chakras, elements, symbols
        'patterns': ['img_059_', 'img_060_', 'img_061_', 'img_062_', 'img_063_',
                     'img_064_', 'img_065_', 'img_066_', 'img_067_', 'img_068_',
                     'img_069_', 'img_070_', 'img_071_', 'img_072_', 'img_073_',
                     'img_079_', 'img_080_', 'img_081_', 'img_082_', 'img_083_',
                     'img_084_', 'img_085_', 'img_086_', 'img_087_', 'img_088_',
                     'img_089_', 'img_090_', 'img_091_', 'img_092_', 'img_093_',
                     'img_094_', 'img_095_', 'img_096_', 'img_097_', 'img_098_',
                     'img_099_'],
        'quality': 88,
        'method': 6
    },
    'photo': {
        # Photo-realistic content
        'patterns': ['img_025_', 'img_026_', 'img_027_', 'img_028_', 'img_029_',
                     'img_030_'],
        'quality': 85,
        'method': 6,
        'preprocessing': 2
    },
    'spinner': {
        # Loading spinner - needs to be small
        'patterns': ['img_006_'],
        'quality': 85,
        'method': 6
    },
    'feature_graphic': {
        # Feature graphic - balance size and quality
        'patterns': ['img_007_'],
        'quality': 88,
        'method': 6
    }
}


def get_profile_for_image(filename: str) -> Dict:
    """Determine which optimization profile to use for an image."""
    for profile_name, profile in OPTIMIZATION_PROFILES.items():
        for pattern in profile['patterns']:
            if filename.startswith(pattern):
                return profile

    # Default profile for unmatched images
    return {
        'quality': 88,
        'method': 6
    }


def backup_original(filepath: Path) -> None:
    """Backup original file before optimization."""
    backup_file = BACKUP_PATH / filepath.relative_to(BASE_RES_PATH.parent)
    backup_file.parent.mkdir(parents=True, exist_ok=True)

    if not backup_file.exists():
        shutil.copy2(filepath, backup_file)


def optimize_webp(input_path: Path, profile: Dict) -> Tuple[bool, int, int]:
    """
    Optimize a WebP image with advanced settings.
    Returns: (success, original_size, new_size)
    """
    if not input_path.exists():
        return False, 0, 0

    original_size = input_path.stat().st_size
    temp_output = input_path.with_suffix('.webp.tmp')

    # Build cwebp command with advanced options
    cmd = [
        'cwebp',
        '-q', str(profile['quality']),
        '-m', str(profile['method']),  # Maximum compression effort
        '-af',  # Auto-adjust filter strength
        '-mt',  # Multi-threading
    ]

    # Add optional parameters
    if 'near_lossless' in profile:
        cmd.extend(['-near_lossless', str(profile['near_lossless'])])

    if 'alpha_quality' in profile:
        cmd.extend(['-alpha_q', str(profile['alpha_quality'])])

    if 'preprocessing' in profile:
        cmd.extend(['-pre', str(profile['preprocessing'])])

    # Sharp YUV for better color
    cmd.append('-sharp_yuv')

    cmd.extend([str(input_path), '-o', str(temp_output)])

    try:
        result = subprocess.run(cmd, capture_output=True, text=True, timeout=30)

        if result.returncode != 0:
            print(f"  Error: {result.stderr}")
            if temp_output.exists():
                temp_output.unlink()
            return False, original_size, original_size

        new_size = temp_output.stat().st_size

        # Only replace if we achieved meaningful reduction (>5%)
        if new_size < original_size * 0.95:
            shutil.move(temp_output, input_path)
            return True, original_size, new_size
        else:
            temp_output.unlink()
            return False, original_size, original_size

    except Exception as e:
        print(f"  Exception: {e}")
        if temp_output.exists():
            temp_output.unlink()
        return False, original_size, original_size


def create_lqip(input_path: Path) -> Path:
    """
    Create Low-Quality Image Placeholder (LQIP) for progressive loading.
    Creates a 32x32 preview at very low quality.
    """
    lqip_dir = input_path.parent.parent / "drawable-lqip"
    lqip_dir.mkdir(exist_ok=True)

    lqip_path = lqip_dir / input_path.name

    # Create tiny preview
    cmd = [
        'cwebp',
        '-resize', '32', '32',  # Tiny size
        '-q', '50',  # Low quality
        '-m', '0',  # Fast method
        str(input_path),
        '-o', str(lqip_path)
    ]

    try:
        subprocess.run(cmd, capture_output=True, check=True, timeout=10)
        return lqip_path
    except Exception as e:
        print(f"  LQIP creation failed: {e}")
        return None


def optimize_all_images(create_lqips: bool = True, skip_xxxhdpi: bool = False):
    """Optimize all images with advanced compression."""

    print("🎨 ADVANCED IMAGE OPTIMIZATION")
    print("=" * 80)
    print(f"Base path: {BASE_RES_PATH}")
    print(f"Backup path: {BACKUP_PATH}")
    print(f"Create LQIPs: {create_lqips}")
    print(f"Skip xxxhdpi: {skip_xxxhdpi}")
    print()

    BACKUP_PATH.mkdir(parents=True, exist_ok=True)

    total_original = 0
    total_optimized = 0
    files_processed = 0
    files_reduced = 0

    densities_to_process = [d for d in DENSITIES if not (skip_xxxhdpi and d == 'xxxhdpi')]

    for density in densities_to_process:
        drawable_dir = BASE_RES_PATH / f"drawable-{density}"

        if not drawable_dir.exists():
            print(f"⚠️  {density}: Directory not found")
            continue

        print(f"\n📁 Processing {density}...")
        print("-" * 80)

        webp_files = sorted(drawable_dir.glob("*.webp"))

        for webp_file in webp_files:
            profile = get_profile_for_image(webp_file.name)
            profile_name = next(
                (name for name, p in OPTIMIZATION_PROFILES.items()
                 if any(webp_file.name.startswith(pat) for pat in p['patterns'])),
                'default'
            )

            # Backup original
            backup_original(webp_file)

            # Optimize
            success, orig_size, new_size = optimize_webp(webp_file, profile)

            total_original += orig_size
            total_optimized += new_size
            files_processed += 1

            if success:
                files_reduced += 1
                reduction = ((orig_size - new_size) / orig_size) * 100
                print(f"  ✓ {webp_file.name:50s} | {profile_name:15s} | "
                      f"{orig_size/1024:6.1f}KB → {new_size/1024:6.1f}KB | "
                      f"-{reduction:5.1f}%")
            else:
                print(f"  → {webp_file.name:50s} | {profile_name:15s} | "
                      f"{orig_size/1024:6.1f}KB | No change")

            # Create LQIP for xhdpi only (one version is enough)
            if create_lqips and density == 'xhdpi':
                lqip = create_lqip(webp_file)
                if lqip:
                    print(f"    → LQIP created: {lqip.stat().st_size/1024:.1f}KB")

    # Summary
    print("\n" + "=" * 80)
    print("📊 OPTIMIZATION SUMMARY")
    print("=" * 80)
    print(f"Files processed: {files_processed}")
    print(f"Files reduced: {files_reduced}")
    print(f"Original size: {total_original / (1024*1024):.2f} MB")
    print(f"Optimized size: {total_optimized / (1024*1024):.2f} MB")

    if total_original > 0:
        reduction = ((total_original - total_optimized) / total_original) * 100
        savings = (total_original - total_optimized) / (1024*1024)
        print(f"Total reduction: {reduction:.1f}% ({savings:.2f} MB saved)")

    print()
    print(f"✓ Optimization complete!")
    print(f"✓ Original files backed up to: {BACKUP_PATH}")


def analyze_density_distribution():
    """Analyze which densities are actually needed."""
    print("\n📊 DENSITY DISTRIBUTION ANALYSIS")
    print("=" * 80)

    for density in DENSITIES:
        drawable_dir = BASE_RES_PATH / f"drawable-{density}"
        if drawable_dir.exists():
            webp_files = list(drawable_dir.glob("*.webp"))
            total_size = sum(f.stat().st_size for f in webp_files)
            avg_size = total_size / len(webp_files) if webp_files else 0

            print(f"{density:8s}: {len(webp_files):3d} files | "
                  f"Total: {total_size/(1024*1024):6.2f} MB | "
                  f"Avg: {avg_size/1024:6.1f} KB")

    print()
    print("💡 RECOMMENDATIONS:")
    print("  • xxxhdpi: Consider removing if app doesn't target tablets")
    print("  • mdpi: Keep for low-end devices")
    print("  • hdpi/xhdpi: Primary target for most phones")
    print("  • xxhdpi: For high-end phones")


if __name__ == "__main__":
    import argparse

    parser = argparse.ArgumentParser(description='Advanced WebP Image Optimizer')
    parser.add_argument('--skip-lqip', action='store_true',
                       help='Skip LQIP generation')
    parser.add_argument('--skip-xxxhdpi', action='store_true',
                       help='Skip xxxhdpi optimization (largest files)')
    parser.add_argument('--analyze-only', action='store_true',
                       help='Only analyze distribution, do not optimize')

    args = parser.parse_args()

    if args.analyze_only:
        analyze_density_distribution()
    else:
        optimize_all_images(
            create_lqips=not args.skip_lqip,
            skip_xxxhdpi=args.skip_xxxhdpi
        )
