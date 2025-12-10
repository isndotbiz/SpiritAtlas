#!/usr/bin/env python3
"""
Create Low-Quality Image Placeholders (LQIP) for progressive loading
"""

import os
import subprocess
from pathlib import Path

BASE_RES_PATH = Path("/Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res")


def create_lqip_set():
    """Create LQIP placeholders for all images."""

    print("🌟 CREATING LQIP PLACEHOLDERS")
    print("=" * 80)

    # Source from xhdpi (middle density)
    source_dir = BASE_RES_PATH / "drawable-xhdpi"

    if not source_dir.exists():
        print(f"❌ Source directory not found: {source_dir}")
        return

    # Create LQIP directory
    lqip_dir = BASE_RES_PATH / "drawable-lqip"
    lqip_dir.mkdir(exist_ok=True)

    webp_files = sorted(source_dir.glob("*.webp"))

    print(f"Source: {source_dir}")
    print(f"Output: {lqip_dir}")
    print(f"Files to process: {len(webp_files)}")
    print()

    total_original = 0
    total_lqip = 0
    created = 0

    for webp_file in webp_files:
        lqip_path = lqip_dir / webp_file.name
        original_size = webp_file.stat().st_size

        # Create tiny 32x32 preview at low quality
        cmd = [
            'cwebp',
            '-resize', '32', '32',  # Tiny placeholder
            '-q', '40',  # Low quality
            '-m', '0',  # Fast method
            str(webp_file),
            '-o', str(lqip_path)
        ]

        try:
            result = subprocess.run(cmd, capture_output=True, timeout=10)

            if result.returncode == 0:
                lqip_size = lqip_path.stat().st_size
                total_original += original_size
                total_lqip += lqip_size
                created += 1

                compression = (1 - lqip_size/original_size) * 100
                print(f"  ✓ {webp_file.name:50s} | "
                      f"{original_size/1024:6.1f}KB → {lqip_size/1024:5.1f}KB | "
                      f"{compression:5.1f}% smaller")
            else:
                print(f"  ✗ {webp_file.name:50s} | Error: {result.stderr[:50]}")

        except Exception as e:
            print(f"  ✗ {webp_file.name:50s} | Exception: {e}")

    print("\n" + "=" * 80)
    print("📊 LQIP SUMMARY")
    print("=" * 80)
    print(f"Created: {created} placeholders")
    print(f"Original total: {total_original / (1024*1024):.2f} MB")
    print(f"LQIP total: {total_lqip / 1024:.2f} KB")

    if total_original > 0:
        compression = (1 - total_lqip/total_original) * 100
        print(f"Compression: {compression:.1f}%")
        print(f"Average LQIP size: {total_lqip/created/1024:.2f} KB" if created > 0 else "")


if __name__ == "__main__":
    create_lqip_set()
