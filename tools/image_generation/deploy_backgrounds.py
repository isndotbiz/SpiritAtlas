#!/usr/bin/env python3
"""
Deploy background images to Android drawable folders.
Optimizes for backgrounds and creates all 5 densities using sips (macOS).
"""

import os
import subprocess
from pathlib import Path

# Source directory
SOURCE_DIR = Path(__file__).parent / "generated_images/optimized_flux_pro"
DEST_BASE = Path(__file__).parent.parent.parent / "app/src/main/res"

# Background images to deploy
BACKGROUNDS = [
    "003_splash_screen_background.png",
    "009_home_screen_background.png",
    "010_profile_creation_background.png",
    "011_compatibility_screen_background.png",
    "013_settings_screen_background.png",
    "014_meditation_chakra_screen_background.png",
]

# Android density configurations
# For backgrounds, we use full resolution at xxxhdpi and scale down
DENSITIES = {
    "drawable-mdpi": 0.25,      # 1/4 of xxxhdpi
    "drawable-hdpi": 0.375,     # 3/8 of xxxhdpi
    "drawable-xhdpi": 0.5,      # 1/2 of xxxhdpi
    "drawable-xxhdpi": 0.75,    # 3/4 of xxxhdpi
    "drawable-xxxhdpi": 1.0,    # Full resolution
}

def get_image_dimensions(image_path: Path) -> tuple[int, int]:
    """Get image dimensions using sips."""
    result = subprocess.run(
        ["sips", "-g", "pixelWidth", "-g", "pixelHeight", str(image_path)],
        capture_output=True,
        text=True,
        check=True
    )
    lines = result.stdout.strip().split('\n')
    width = int([l for l in lines if 'pixelWidth' in l][0].split(':')[1].strip())
    height = int([l for l in lines if 'pixelHeight' in l][0].split(':')[1].strip())
    return width, height

def optimize_and_deploy(source_file: Path, dest_name: str):
    """Optimize and deploy a background image to all densities."""
    print(f"\nProcessing: {source_file.name}")
    print(f"  Output name: {dest_name}")

    # Get original dimensions
    try:
        orig_width, orig_height = get_image_dimensions(source_file)
        print(f"  Original: {orig_width}x{orig_height}")
    except Exception as e:
        print(f"  ✗ Failed to get dimensions: {e}")
        return False

    for density_dir, scale in DENSITIES.items():
        dest_dir = DEST_BASE / density_dir
        dest_dir.mkdir(parents=True, exist_ok=True)
        dest_file = dest_dir / dest_name

        # Calculate target dimensions
        target_width = int(orig_width * scale)
        target_height = int(orig_height * scale)

        if scale == 1.0:
            # For xxxhdpi, just copy the file
            try:
                subprocess.run(["cp", str(source_file), str(dest_file)], check=True)
                file_size = dest_file.stat().st_size / 1024  # KB
                print(f"  ✓ {density_dir}: {file_size:.1f}KB ({target_width}x{target_height})")
            except Exception as e:
                print(f"  ✗ {density_dir}: Failed - {e}")
                return False
        else:
            # For other densities, resize using sips
            try:
                # First copy the file
                subprocess.run(["cp", str(source_file), str(dest_file)], check=True)

                # Then resize it
                subprocess.run([
                    "sips",
                    "-z", str(target_height), str(target_width),
                    str(dest_file)
                ], check=True, capture_output=True)

                file_size = dest_file.stat().st_size / 1024  # KB
                print(f"  ✓ {density_dir}: {file_size:.1f}KB ({target_width}x{target_height})")
            except subprocess.CalledProcessError as e:
                print(f"  ✗ {density_dir}: Failed - {e}")
                return False

    return True

def main():
    """Deploy all background images."""
    print("=" * 60)
    print("BACKGROUND IMAGE DEPLOYMENT")
    print("=" * 60)

    # Check sips is available (should be on macOS)
    try:
        subprocess.run(["sips", "--version"], check=True, capture_output=True)
    except (subprocess.CalledProcessError, FileNotFoundError):
        print("ERROR: sips not found. This script requires macOS.")
        return 1

    success_count = 0
    total_count = len(BACKGROUNDS)

    for img_file in BACKGROUNDS:
        source_path = SOURCE_DIR / img_file

        if not source_path.exists():
            print(f"\n✗ NOT FOUND: {img_file}")
            continue

        # Convert filename: 003_splash_screen_background.png -> img_003_splash_screen_background.png
        dest_name = f"img_{img_file}"

        if optimize_and_deploy(source_path, dest_name):
            success_count += 1

    print("\n" + "=" * 60)
    print(f"DEPLOYMENT COMPLETE: {success_count}/{total_count} backgrounds deployed")
    print(f"Total files created: {success_count * len(DENSITIES)}")
    print("=" * 60)

    return 0 if success_count == total_count else 1

if __name__ == "__main__":
    exit(main())
