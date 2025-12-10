#!/usr/bin/env python3
"""
WebP Multi-Density Optimization for Beautified Chakras

Converts beautified chakra PNG images to WebP format at 5 Android densities:
- mdpi (1x): 128×128
- hdpi (1.5x): 192×192
- xhdpi (2x): 256×256
- xxhdpi (3x): 384×384
- xxxhdpi (4x): 512×512

Usage:
    python3 optimize_beautified_chakras.py
    python3 optimize_beautified_chakras.py --quality 95
"""

import os
import sys
import json
import argparse
import subprocess
from pathlib import Path
from typing import Dict, List, Tuple

# Configuration
SOURCE_DIR = Path("generated_images/beautified_chakras")
OUTPUT_BASE = Path("/Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res")
BACKUP_DIR = Path("/Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation/backup_originals_chakras")

# Android density specifications
DENSITIES = {
    "mdpi": 128,      # 1x baseline
    "hdpi": 192,      # 1.5x
    "xhdpi": 256,     # 2x
    "xxhdpi": 384,    # 3x
    "xxxhdpi": 512    # 4x
}

# Chakra mapping
CHAKRA_MAPPING = {
    "047": "sacral_chakra_svadhisthana",
    "048": "solar_plexus_chakra_manipura",
    "050": "throat_chakra_vishuddha"
}


def check_imagemagick():
    """Check if ImageMagick is installed."""
    try:
        result = subprocess.run(
            ["magick", "--version"],
            capture_output=True,
            text=True,
            check=True
        )
        print(f"✅ ImageMagick found: {result.stdout.split('\\n')[0]}")
        return True
    except (subprocess.CalledProcessError, FileNotFoundError):
        print("❌ ImageMagick not found!")
        print("\nInstall ImageMagick:")
        print("   macOS: brew install imagemagick")
        print("   Linux: sudo apt-get install imagemagick")
        print("   Windows: https://imagemagick.org/script/download.php\n")
        return False


def backup_original_chakras():
    """Backup original chakra images before replacement."""
    print("\n📦 Backing up original chakra images...")

    BACKUP_DIR.mkdir(parents=True, exist_ok=True)
    backup_count = 0

    for chakra_id, name in CHAKRA_MAPPING.items():
        for density, size in DENSITIES.items():
            source = OUTPUT_BASE / f"drawable-{density}" / f"img_{chakra_id}_{name}.webp"

            if source.exists():
                dest = BACKUP_DIR / density / f"img_{chakra_id}_{name}.webp"
                dest.parent.mkdir(parents=True, exist_ok=True)

                import shutil
                shutil.copy2(source, dest)
                backup_count += 1

    print(f"   ✅ Backed up {backup_count} files to: {BACKUP_DIR}")


def find_beautified_images() -> Dict[str, Path]:
    """Find beautified chakra images in source directory."""
    if not SOURCE_DIR.exists():
        print(f"❌ Source directory not found: {SOURCE_DIR}")
        return {}

    images = {}

    for img_file in SOURCE_DIR.glob("*_beautified_*.png"):
        # Extract chakra ID from filename (e.g., "047" from "047_sacral_svadhisthana_beautified_20251210_123456.png")
        filename = img_file.stem
        for chakra_id in CHAKRA_MAPPING.keys():
            if filename.startswith(chakra_id):
                images[chakra_id] = img_file
                break

    return images


def optimize_chakra_to_webp(
    source_image: Path,
    chakra_id: str,
    chakra_name: str,
    quality: int = 90
) -> List[Dict]:
    """
    Optimize a single chakra image to WebP at all densities.

    Args:
        source_image: Path to source PNG
        chakra_id: Chakra ID (e.g., "047")
        chakra_name: Chakra name (e.g., "sacral_chakra_svadhisthana")
        quality: WebP quality (1-100)

    Returns:
        List of generated file info
    """
    print(f"\n🔧 Processing: {chakra_id}_{chakra_name}")
    print(f"   Source: {source_image.name}")

    generated_files = []

    for density, size in DENSITIES.items():
        output_dir = OUTPUT_BASE / f"drawable-{density}"
        output_dir.mkdir(parents=True, exist_ok=True)

        output_file = output_dir / f"img_{chakra_id}_{chakra_name}.webp"

        # ImageMagick command
        cmd = [
            "magick",
            str(source_image),
            "-resize", f"{size}x{size}",
            "-quality", str(quality),
            str(output_file)
        ]

        try:
            subprocess.run(cmd, check=True, capture_output=True)

            file_size = output_file.stat().st_size / 1024  # KB

            generated_files.append({
                'density': density,
                'size': f"{size}×{size}",
                'file': str(output_file),
                'size_kb': round(file_size, 2)
            })

            print(f"   ✅ {density:8s} ({size:3d}×{size:3d}): {file_size:6.2f} KB")

        except subprocess.CalledProcessError as e:
            print(f"   ❌ {density:8s}: Failed - {e.stderr.decode()}")

    return generated_files


def save_optimization_report(all_results: Dict[str, List[Dict]], quality: int):
    """Save optimization report."""
    report_file = SOURCE_DIR / "optimization_report.json"

    total_files = sum(len(files) for files in all_results.values())
    total_size_kb = sum(
        f['size_kb']
        for files in all_results.values()
        for f in files
    )

    report = {
        'timestamp': str(Path(__file__).parent),
        'quality': quality,
        'total_chakras': len(all_results),
        'total_files': total_files,
        'total_size_kb': round(total_size_kb, 2),
        'total_size_mb': round(total_size_kb / 1024, 2),
        'densities': list(DENSITIES.keys()),
        'chakras': all_results
    }

    with open(report_file, 'w') as f:
        json.dump(report, f, indent=2)

    print(f"\n📄 Optimization report saved: {report_file}")
    return report


def print_summary(report: Dict):
    """Print optimization summary."""
    print("\n" + "="*80)
    print("🎉 WEBP OPTIMIZATION COMPLETE!")
    print("="*80)

    print(f"\n📊 Summary:")
    print(f"   Chakras Processed: {report['total_chakras']}")
    print(f"   Total Files Generated: {report['total_files']}")
    print(f"   Total Size: {report['total_size_mb']} MB ({report['total_size_kb']} KB)")
    print(f"   Avg Size per File: {report['total_size_kb'] / report['total_files']:.2f} KB")
    print(f"   WebP Quality: {report['quality']}")

    print(f"\n📁 Output Locations:")
    for density in DENSITIES.keys():
        output_dir = OUTPUT_BASE / f"drawable-{density}"
        print(f"   {density:8s}: {output_dir}")

    print(f"\n💾 Backup Location: {BACKUP_DIR}")

    print("\n" + "="*80)
    print("📋 NEXT STEPS:")
    print("   1. Verify images: ls -lh app/src/main/res/drawable-xhdpi/img_04*chakra*.webp")
    print("   2. Build Android app: ./gradlew :app:assembleDebug")
    print("   3. Test on device: ./gradlew installDebug")
    print("   4. Create comparisons: python3 create_chakra_comparison.py")
    print("   5. Update documentation: CHAKRA_BEAUTIFICATION_RESULTS.md")
    print("="*80 + "\n")


def main():
    """Main execution."""
    parser = argparse.ArgumentParser(
        description="Optimize beautified chakras to WebP multi-density",
        formatter_class=argparse.RawDescriptionHelpFormatter
    )

    parser.add_argument(
        "--quality",
        type=int,
        default=90,
        help="WebP quality (1-100, default: 90)"
    )

    parser.add_argument(
        "--no-backup",
        action="store_true",
        help="Skip backing up original files"
    )

    parser.add_argument(
        "--source-dir",
        type=str,
        help="Custom source directory"
    )

    args = parser.parse_args()

    # Update source directory if specified
    global SOURCE_DIR
    if args.source_dir:
        SOURCE_DIR = Path(args.source_dir)

    # Print header
    print("\n" + "="*80)
    print("CHAKRA WEBP OPTIMIZATION")
    print("="*80)
    print(f"Source: {SOURCE_DIR}")
    print(f"Output: {OUTPUT_BASE}")
    print(f"Quality: {args.quality}")
    print(f"Densities: {len(DENSITIES)} ({', '.join(DENSITIES.keys())})")
    print("="*80)

    # Check dependencies
    if not check_imagemagick():
        sys.exit(1)

    # Backup originals
    if not args.no_backup:
        backup_original_chakras()

    # Find beautified images
    print(f"\n🔍 Searching for beautified chakra images...")
    beautified_images = find_beautified_images()

    if not beautified_images:
        print(f"❌ No beautified chakra images found in: {SOURCE_DIR}")
        print("\nRun this first:")
        print("   python3 beautify_chakras.py\n")
        sys.exit(1)

    print(f"✅ Found {len(beautified_images)} beautified chakra(s):")
    for chakra_id, img_path in beautified_images.items():
        print(f"   • {chakra_id}: {img_path.name}")

    # Optimize each chakra
    all_results = {}

    for chakra_id, source_image in beautified_images.items():
        chakra_name = CHAKRA_MAPPING[chakra_id]

        results = optimize_chakra_to_webp(
            source_image,
            chakra_id,
            chakra_name,
            quality=args.quality
        )

        all_results[f"{chakra_id}_{chakra_name}"] = results

    # Save report
    report = save_optimization_report(all_results, args.quality)

    # Print summary
    print_summary(report)


if __name__ == "__main__":
    main()
