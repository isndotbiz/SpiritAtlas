#!/usr/bin/env python3
"""
Aggressive WebP Optimizer - Second pass with more aggressive settings
Targets files that didn't optimize in first pass
"""

import os
import subprocess
import shutil
from pathlib import Path

BASE_RES_PATH = Path("/Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res")
DENSITIES = ['mdpi', 'hdpi', 'xhdpi', 'xxhdpi', 'xxxhdpi']

# More aggressive profiles
AGGRESSIVE_PROFILES = {
    'large_lossless': {
        # Icons/logos still lossless - convert to high quality lossy
        'min_size_kb': 100,
        'quality': 92,
        'method': 6,
        'alpha_quality': 100,
        'sharp_yuv': True
    },
    'medium_lossless': {
        'min_size_kb': 30,
        'quality': 93,
        'method': 6,
        'alpha_quality': 100,
        'sharp_yuv': True
    },
    'backgrounds': {
        # Backgrounds can be more aggressive
        'patterns': ['background', 'screen_bg'],
        'quality': 82,
        'method': 6,
        'preprocessing': 4
    },
    'large_illustrations': {
        # Large illustration files > 200KB
        'min_size_kb': 200,
        'quality': 85,
        'method': 6
    }
}


def is_lossless(filepath: Path) -> bool:
    """Check if WebP is lossless."""
    result = subprocess.run(['webpinfo', str(filepath)], capture_output=True, text=True)
    return 'Lossless' in result.stdout


def optimize_aggressive(input_path: Path, quality: int, **kwargs):
    """Apply aggressive optimization."""
    original_size = input_path.stat().st_size
    temp_output = input_path.with_suffix('.webp.tmp')

    cmd = [
        'cwebp',
        '-q', str(quality),
        '-m', str(kwargs.get('method', 6)),
        '-af',
        '-mt',
    ]

    if kwargs.get('sharp_yuv'):
        cmd.append('-sharp_yuv')

    if 'alpha_quality' in kwargs:
        cmd.extend(['-alpha_q', str(kwargs['alpha_quality'])])

    if 'preprocessing' in kwargs:
        cmd.extend(['-pre', str(kwargs['preprocessing'])])

    cmd.extend([str(input_path), '-o', str(temp_output)])

    try:
        result = subprocess.run(cmd, capture_output=True, timeout=30)

        if result.returncode == 0 and temp_output.exists():
            new_size = temp_output.stat().st_size

            if new_size < original_size * 0.92:  # At least 8% reduction
                shutil.move(temp_output, input_path)
                return True, original_size, new_size

        if temp_output.exists():
            temp_output.unlink()

    except Exception:
        if temp_output.exists():
            temp_output.unlink()

    return False, original_size, original_size


def aggressive_pass():
    """Second optimization pass with aggressive settings."""

    print("💪 AGGRESSIVE OPTIMIZATION PASS")
    print("=" * 80)

    total_orig = 0
    total_new = 0
    files_optimized = 0

    for density in DENSITIES:
        drawable_dir = BASE_RES_PATH / f"drawable-{density}"
        if not drawable_dir.exists():
            continue

        print(f"\n📁 {density}...")

        for webp_file in sorted(drawable_dir.glob("*.webp")):
            size_kb = webp_file.stat().st_size / 1024

            # Skip files already well optimized
            if size_kb < 20:
                continue

            optimized = False

            # Check if lossless
            if is_lossless(webp_file):
                if size_kb > 100:
                    profile = AGGRESSIVE_PROFILES['large_lossless']
                else:
                    profile = AGGRESSIVE_PROFILES['medium_lossless']

                success, orig, new = optimize_aggressive(webp_file, **profile)

                if success:
                    reduction = ((orig - new) / orig) * 100
                    print(f"  ✓ {webp_file.name:50s} | Lossless→Lossy | "
                          f"{orig/1024:6.1f}KB → {new/1024:6.1f}KB | -{reduction:5.1f}%")
                    total_orig += orig
                    total_new += new
                    files_optimized += 1
                    optimized = True

            # Large files (even if lossy)
            if not optimized and size_kb > 200:
                profile = AGGRESSIVE_PROFILES['large_illustrations']
                success, orig, new = optimize_aggressive(webp_file, **profile)

                if success:
                    reduction = ((orig - new) / orig) * 100
                    print(f"  ✓ {webp_file.name:50s} | Large file   | "
                          f"{orig/1024:6.1f}KB → {new/1024:6.1f}KB | -{reduction:5.1f}%")
                    total_orig += orig
                    total_new += new
                    files_optimized += 1

    print("\n" + "=" * 80)
    print("📊 AGGRESSIVE PASS SUMMARY")
    print("=" * 80)
    print(f"Files optimized: {files_optimized}")

    if total_orig > 0:
        print(f"Original size: {total_orig / (1024*1024):.2f} MB")
        print(f"New size: {total_new / (1024*1024):.2f} MB")
        reduction = ((total_orig - total_new) / total_orig) * 100
        savings = (total_orig - total_new) / (1024*1024)
        print(f"Reduction: {reduction:.1f}% ({savings:.2f} MB saved)")
    else:
        print("No files needed aggressive optimization")


if __name__ == "__main__":
    aggressive_pass()
