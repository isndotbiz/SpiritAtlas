#!/usr/bin/env python3
"""
SpiritAtlas App Icon Optimizer
Processes generated icons into all required Android sizes and formats

Requires: Pillow (pip install Pillow)

Usage:
    python3 optimize_app_icons.py lotus_master_1024.png

This will generate:
- All mipmap densities (mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi)
- Round variants
- Play Store asset (512x512)
- Preview composites
"""

import os
import sys
from pathlib import Path

try:
    from PIL import Image, ImageDraw, ImageFilter
except ImportError:
    print("❌ Pillow not installed. Run: pip install Pillow")
    sys.exit(1)


# Android mipmap density sizes (px)
DENSITIES = {
    "mdpi": 48,
    "hdpi": 72,
    "xhdpi": 96,
    "xxhdpi": 144,
    "xxxhdpi": 192,
}

# Additional sizes
PLAY_STORE_SIZE = 512


def create_round_mask(size: int) -> Image.Image:
    """Create a circular mask for round icons"""
    mask = Image.new("L", (size, size), 0)
    draw = ImageDraw.Draw(mask)
    draw.ellipse((0, 0, size, size), fill=255)
    return mask


def resize_icon(input_path: Path, output_path: Path, size: int, round_icon: bool = False):
    """
    Resize icon to specified size, optionally applying circular mask

    Args:
        input_path: Source image path
        output_path: Destination path
        size: Target size (square)
        round_icon: Apply circular mask
    """
    # Open source image
    img = Image.open(input_path)

    # Convert to RGBA if needed
    if img.mode != "RGBA":
        img = img.convert("RGBA")

    # Resize with high-quality Lanczos filter
    img_resized = img.resize((size, size), Image.Resampling.LANCZOS)

    # Apply circular mask for round icons
    if round_icon:
        mask = create_round_mask(size)
        img_resized.putalpha(mask)

    # Save
    output_path.parent.mkdir(parents=True, exist_ok=True)
    img_resized.save(output_path, "PNG", optimize=True)
    print(f"   ✅ {output_path.name} ({size}x{size}px)")


def create_adaptive_preview(foreground_path: Path, background_path: Path, output_path: Path):
    """
    Create a preview showing how adaptive icon looks with different masks

    Args:
        foreground_path: Foreground layer image
        background_path: Background layer image
        output_path: Preview output path
    """
    # Open layers
    fg = Image.open(foreground_path).convert("RGBA")
    bg = Image.open(background_path).convert("RGBA")

    # Resize to standard size
    size = 512
    fg = fg.resize((size, size), Image.Resampling.LANCZOS)
    bg = bg.resize((size, size), Image.Resampling.LANCZOS)

    # Create preview with different shapes
    preview_width = size * 4 + 50
    preview_height = size + 100
    preview = Image.new("RGBA", (preview_width, preview_height), (240, 240, 240, 255))

    shapes = [
        ("Full", None),
        ("Circle", "circle"),
        ("Squircle", "squircle"),
        ("Rounded", "rounded")
    ]

    x_offset = 10
    for label, shape in shapes:
        # Composite foreground on background
        composite = bg.copy()
        composite.alpha_composite(fg)

        # Apply mask
        if shape == "circle":
            mask = create_round_mask(size)
            composite.putalpha(mask)
        elif shape == "squircle":
            mask = create_squircle_mask(size)
            composite.putalpha(mask)
        elif shape == "rounded":
            mask = create_rounded_square_mask(size, radius=size // 8)
            composite.putalpha(mask)

        # Paste into preview
        preview.alpha_composite(composite, (x_offset, 50))

        # Add label (would need PIL text rendering, simplified here)
        x_offset += size + 10

    # Save preview
    preview.save(output_path, "PNG", optimize=True)
    print(f"\n📸 Preview created: {output_path}")


def create_squircle_mask(size: int) -> Image.Image:
    """Create a squircle (superellipse) mask"""
    mask = Image.new("L", (size, size), 0)
    draw = ImageDraw.Draw(mask)

    # Approximate squircle with rounded rectangle
    radius = size // 4
    draw.rounded_rectangle((0, 0, size, size), radius=radius, fill=255)
    return mask


def create_rounded_square_mask(size: int, radius: int) -> Image.Image:
    """Create a rounded square mask"""
    mask = Image.new("L", (size, size), 0)
    draw = ImageDraw.Draw(mask)
    draw.rounded_rectangle((0, 0, size, size), radius=radius, fill=255)
    return mask


def optimize_icon(input_path: str):
    """
    Main optimization workflow

    Args:
        input_path: Path to source icon (1024x1024 recommended)
    """
    input_path = Path(input_path)

    if not input_path.exists():
        print(f"❌ File not found: {input_path}")
        return

    print("=" * 80)
    print("SpiritAtlas App Icon Optimizer")
    print("=" * 80)
    print(f"\n📁 Source: {input_path}")

    # Determine output directory structure
    base_name = input_path.stem  # e.g., "lotus_master_1024"
    output_base = input_path.parent / f"{base_name}_optimized"
    output_base.mkdir(exist_ok=True)

    print(f"📁 Output: {output_base}")

    # Generate all density variants
    print("\n🔄 Generating density variants...")
    for density, size in DENSITIES.items():
        # Standard icon
        standard_path = output_base / "mipmap" / f"mipmap-{density}" / "ic_launcher.png"
        resize_icon(input_path, standard_path, size, round_icon=False)

        # Round icon
        round_path = output_base / "mipmap" / f"mipmap-{density}" / "ic_launcher_round.png"
        resize_icon(input_path, round_path, size, round_icon=True)

    # Generate Play Store asset
    print("\n🔄 Generating Play Store asset (512x512)...")
    playstore_path = output_base / "playstore" / "ic_launcher_playstore.png"
    resize_icon(input_path, playstore_path, PLAY_STORE_SIZE, round_icon=False)

    # Generate preview composites (if foreground/background layers available)
    print("\n🔄 Checking for adaptive icon layers...")
    base_dir = input_path.parent
    possible_fg = base_dir / input_path.name.replace("master", "foreground")
    possible_bg = base_dir / input_path.name.replace("master", "background")

    if possible_fg.exists() and possible_bg.exists():
        print("   Found foreground and background layers!")
        preview_path = output_base / "preview" / "adaptive_icon_preview.png"
        create_adaptive_preview(possible_fg, possible_bg, preview_path)
    else:
        print("   No separate layers found (master icon only)")

    # Summary
    print("\n" + "=" * 80)
    print("OPTIMIZATION COMPLETE")
    print("=" * 80)
    print(f"✅ Generated {len(DENSITIES) * 2} mipmap assets")
    print(f"✅ Generated 1 Play Store asset")
    print(f"📁 Output directory: {output_base}")

    print("\n" + "=" * 80)
    print("NEXT STEPS")
    print("=" * 80)
    print("1. Review generated assets in:", output_base)
    print("2. Copy mipmap-* folders to: app/src/main/res/")
    print("3. Update drawable XML files: ic_launcher_background.xml, ic_launcher_foreground.xml")
    print("4. Update adaptive-icon config: mipmap-anydpi-v26/ic_launcher.xml")
    print("5. Build and test:")
    print("   ./gradlew assembleDebug")
    print("   adb install -r app/build/outputs/apk/debug/app-debug.apk")

    # Show copy commands
    print("\n📋 Quick copy commands:")
    app_res = Path("/Users/jonathanmallinger/Workspace/SpiritAtlas/app/src/main/res")
    for density in DENSITIES.keys():
        src = output_base / "mipmap" / f"mipmap-{density}"
        dst = app_res / f"mipmap-{density}"
        print(f"   cp {src}/ic_launcher*.png {dst}/")


def main():
    if len(sys.argv) < 2:
        print("Usage: python3 optimize_app_icons.py <input_icon.png>")
        print("\nExample:")
        print("  python3 optimize_app_icons.py generated_icons/lotus_master_1024.png")
        sys.exit(1)

    input_path = sys.argv[1]
    optimize_icon(input_path)


if __name__ == "__main__":
    main()
