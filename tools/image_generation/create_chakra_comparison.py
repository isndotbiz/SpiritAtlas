#!/usr/bin/env python3
"""
Chakra Before/After Comparison Generator

Creates side-by-side comparison images showing original vs beautified chakras.

Usage:
    python3 create_chakra_comparison.py
    python3 create_chakra_comparison.py --output-dir ./comparisons
"""

import os
import sys
import argparse
import subprocess
from pathlib import Path
from typing import Dict, List

# Configuration
ORIGINAL_DIR = Path("generated_images/optimized_flux_pro")
BEAUTIFIED_DIR = Path("generated_images/beautified_chakras")
OUTPUT_DIR = Path("generated_images/chakra_comparisons")

# Chakra mapping
CHAKRAS = {
    "047": {
        "name": "Sacral Chakra (Svadhisthana)",
        "original": "047_sacral_chakra_svadhisthana.png",
        "color": "#FF6B35"
    },
    "048": {
        "name": "Solar Plexus Chakra (Manipura)",
        "original": "048_solar_plexus_chakra_manipura.png",
        "color": "#FFD60A"
    },
    "050": {
        "name": "Throat Chakra (Vishuddha)",
        "original": "050_throat_chakra_vishuddha.png",
        "color": "#00B4D8"
    }
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
        return True
    except (subprocess.CalledProcessError, FileNotFoundError):
        print("❌ ImageMagick not found!")
        print("\nInstall ImageMagick:")
        print("   macOS: brew install imagemagick")
        print("   Linux: sudo apt-get install imagemagick")
        print("   Windows: https://imagemagick.org/script/download.php\n")
        return False


def find_beautified_image(chakra_id: str) -> Path:
    """Find the beautified image for a chakra."""
    pattern = f"{chakra_id}_*_beautified_*.png"

    matches = list(BEAUTIFIED_DIR.glob(pattern))

    if not matches:
        return None

    # Return most recent if multiple
    return sorted(matches, key=lambda p: p.stat().st_mtime)[-1]


def create_comparison(
    chakra_id: str,
    chakra_info: Dict,
    original_path: Path,
    beautified_path: Path,
    output_path: Path
) -> bool:
    """
    Create a side-by-side comparison image.

    Args:
        chakra_id: Chakra ID (e.g., "047")
        chakra_info: Chakra metadata
        original_path: Path to original image
        beautified_path: Path to beautified image
        output_path: Output path for comparison

    Returns:
        True if successful
    """
    print(f"\n🖼️  Creating comparison: {chakra_info['name']}")
    print(f"   Original: {original_path.name}")
    print(f"   Beautified: {beautified_path.name}")

    # Create labeled images
    temp_dir = output_path.parent / "temp"
    temp_dir.mkdir(parents=True, exist_ok=True)

    labeled_original = temp_dir / f"{chakra_id}_original_labeled.png"
    labeled_beautified = temp_dir / f"{chakra_id}_beautified_labeled.png"

    # Add labels to images
    label_font_size = 48
    label_padding = 20

    # Label original
    cmd_original = [
        "magick", str(original_path),
        "-resize", "800x800",
        "-gravity", "North",
        "-background", "white",
        "-splice", f"0x{label_padding + label_font_size + label_padding}",
        "-font", "Arial",
        "-pointsize", str(label_font_size),
        "-fill", "black",
        "-annotate", f"+0+{label_padding + label_font_size//2}",
        "ORIGINAL (9.4-9.5/10)",
        str(labeled_original)
    ]

    # Label beautified
    cmd_beautified = [
        "magick", str(beautified_path),
        "-resize", "800x800",
        "-gravity", "North",
        "-background", "white",
        "-splice", f"0x{label_padding + label_font_size + label_padding}",
        "-font", "Arial",
        "-pointsize", str(label_font_size),
        "-fill", chakra_info['color'],
        "-annotate", f"+0+{label_padding + label_font_size//2}",
        "BEAUTIFIED (Target: 9.8/10)",
        str(labeled_beautified)
    ]

    try:
        # Create labeled images
        subprocess.run(cmd_original, check=True, capture_output=True)
        subprocess.run(cmd_beautified, check=True, capture_output=True)

        # Create side-by-side montage
        cmd_montage = [
            "magick", "montage",
            str(labeled_original),
            str(labeled_beautified),
            "-tile", "2x1",
            "-geometry", "+20+10",
            "-background", "#1E1B4B",  # Night Sky color
            "-title", chakra_info['name'],
            "-fill", "white",
            "-pointsize", "64",
            str(output_path)
        ]

        subprocess.run(cmd_montage, check=True, capture_output=True)

        # Clean up temp files
        labeled_original.unlink()
        labeled_beautified.unlink()

        file_size = output_path.stat().st_size / 1024  # KB
        print(f"   ✅ Created: {output_path.name} ({file_size:.2f} KB)")

        return True

    except subprocess.CalledProcessError as e:
        print(f"   ❌ Failed: {e.stderr.decode()}")
        return False


def create_combined_comparison(comparison_files: List[Path], output_path: Path) -> bool:
    """Create a combined image showing all 3 chakra comparisons."""
    print(f"\n🎨 Creating combined comparison (all 3 chakras)...")

    if len(comparison_files) != 3:
        print(f"   ⚠️  Expected 3 comparison files, found {len(comparison_files)}")
        return False

    cmd = [
        "magick", "montage",
        *[str(f) for f in comparison_files],
        "-tile", "1x3",
        "-geometry", "+0+20",
        "-background", "#1E1B4B",
        "-title", "SpiritAtlas Chakra Beautification: Before & After",
        "-fill", "white",
        "-pointsize", "72",
        str(output_path)
    ]

    try:
        subprocess.run(cmd, check=True, capture_output=True)

        file_size = output_path.stat().st_size / (1024 * 1024)  # MB
        print(f"   ✅ Created: {output_path.name} ({file_size:.2f} MB)")
        return True

    except subprocess.CalledProcessError as e:
        print(f"   ❌ Failed: {e.stderr.decode()}")
        return False


def print_summary(successful: int, total: int):
    """Print summary."""
    print("\n" + "="*80)
    print("🎉 COMPARISON GENERATION COMPLETE!")
    print("="*80)

    print(f"\n📊 Summary:")
    print(f"   Successful: {successful}/{total}")
    print(f"   Output Directory: {OUTPUT_DIR}")

    if successful > 0:
        print(f"\n🖼️  Generated Comparisons:")
        for file in sorted(OUTPUT_DIR.glob("*.png")):
            size = file.stat().st_size / 1024
            print(f"   • {file.name} ({size:.2f} KB)")

    print("\n" + "="*80)
    print("📋 USE CASES:")
    print("   1. Visual QA: Review quality improvements")
    print("   2. Documentation: Include in CHAKRA_BEAUTIFICATION_RESULTS.md")
    print("   3. Marketing: Show before/after on social media")
    print("   4. Team Review: Share for feedback")
    print("="*80 + "\n")


def main():
    """Main execution."""
    parser = argparse.ArgumentParser(
        description="Create before/after comparison images for beautified chakras",
        formatter_class=argparse.RawDescriptionHelpFormatter
    )

    parser.add_argument(
        "--output-dir",
        type=str,
        help="Custom output directory"
    )

    parser.add_argument(
        "--chakras",
        type=str,
        nargs="+",
        choices=["047", "048", "050"],
        help="Specific chakras to compare (default: all)"
    )

    args = parser.parse_args()

    # Update output directory if specified
    global OUTPUT_DIR
    if args.output_dir:
        OUTPUT_DIR = Path(args.output_dir)

    # Create output directory
    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)

    # Print header
    print("\n" + "="*80)
    print("CHAKRA COMPARISON GENERATOR")
    print("="*80)
    print(f"Original Dir: {ORIGINAL_DIR}")
    print(f"Beautified Dir: {BEAUTIFIED_DIR}")
    print(f"Output Dir: {OUTPUT_DIR}")
    print("="*80)

    # Check dependencies
    if not check_imagemagick():
        sys.exit(1)

    # Determine which chakras to process
    chakras_to_process = CHAKRAS
    if args.chakras:
        chakras_to_process = {
            k: v for k, v in CHAKRAS.items()
            if k in args.chakras
        }

    print(f"\n🔍 Processing {len(chakras_to_process)} chakra(s)...")

    # Create individual comparisons
    successful = 0
    comparison_files = []

    for chakra_id, chakra_info in chakras_to_process.items():
        # Find original
        original_path = ORIGINAL_DIR / chakra_info['original']

        if not original_path.exists():
            print(f"\n❌ Original not found: {chakra_info['name']}")
            print(f"   Expected: {original_path}")
            continue

        # Find beautified
        beautified_path = find_beautified_image(chakra_id)

        if not beautified_path:
            print(f"\n❌ Beautified not found: {chakra_info['name']}")
            print(f"   Pattern: {chakra_id}_*_beautified_*.png in {BEAUTIFIED_DIR}")
            continue

        # Create comparison
        output_path = OUTPUT_DIR / f"chakra_{chakra_id}_comparison.png"

        if create_comparison(
            chakra_id,
            chakra_info,
            original_path,
            beautified_path,
            output_path
        ):
            successful += 1
            comparison_files.append(output_path)

    # Create combined comparison if all 3 succeeded
    if successful == 3:
        combined_output = OUTPUT_DIR / "chakras_all_comparison.png"
        create_combined_comparison(comparison_files, combined_output)

    # Print summary
    print_summary(successful, len(chakras_to_process))


if __name__ == "__main__":
    main()
