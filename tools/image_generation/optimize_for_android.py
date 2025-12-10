#!/usr/bin/env python3
"""
Android Image Optimization Pipeline for SpiritAtlas

This script optimizes FLUX 1.1 Pro generated images for Android:
- Converts PNG to WebP with quality=90 (lossy) or 100 (lossless for icons)
- Generates multiple density versions (mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi)
- Organizes images into proper Android resource directories
- Maintains transparency where needed
- Provides size reduction statistics

Usage:
    python optimize_for_android.py [--input INPUT_DIR] [--output OUTPUT_DIR] [--dry-run]

Android Density Guidelines:
- mdpi (baseline):   1x   (160 dpi)
- hdpi:              1.5x (240 dpi)
- xhdpi:             2x   (320 dpi)
- xxhdpi:            3x   (480 dpi)
- xxxhdpi:           4x   (640 dpi)
"""

import os
import sys
import json
import argparse
from pathlib import Path
from typing import Dict, List, Tuple, Optional
from dataclasses import dataclass
from enum import Enum
import shutil

try:
    from PIL import Image
    import PIL.features
except ImportError:
    print("ERROR: Pillow is required. Install with: pip install Pillow")
    sys.exit(1)


class ImageCategory(Enum):
    """Image categories with different optimization strategies"""
    APP_ICON = "app_icons"           # App launcher icons, adaptive icons
    SYMBOL = "symbols"               # Spiritual symbols, chakras, zodiac
    AVATAR = "avatars"               # Profile avatars, placeholders
    BUTTON = "buttons"               # UI buttons, interactive elements
    BACKGROUND = "backgrounds"       # Full-screen backgrounds, gradients
    BONUS_UI = "bonus_ui"           # Additional UI elements


class DensityConfig(Enum):
    """Android density configurations"""
    MDPI = ("mdpi", 1.0)
    HDPI = ("hdpi", 1.5)
    XHDPI = ("xhdpi", 2.0)
    XXHDPI = ("xxhdpi", 3.0)
    XXXHDPI = ("xxxhdpi", 4.0)

    def __init__(self, folder: str, scale: float):
        self.folder = folder
        self.scale = scale


@dataclass
class OptimizationConfig:
    """Configuration for image optimization"""
    category: ImageCategory
    base_density: DensityConfig = DensityConfig.XXHDPI  # 3x is our baseline (most images are high-res)
    target_densities: List[DensityConfig] = None
    webp_quality: int = 90
    use_lossless: bool = False
    max_dimension: Optional[int] = None
    preserve_transparency: bool = True

    def __post_init__(self):
        if self.target_densities is None:
            # Generate all densities by default
            self.target_densities = list(DensityConfig)


class ImageOptimizer:
    """Optimizes images for Android following best practices"""

    def __init__(self, input_dir: Path, output_dir: Path, dry_run: bool = False):
        self.input_dir = Path(input_dir)
        self.output_dir = Path(output_dir)
        self.dry_run = dry_run
        self.stats = {
            'processed': 0,
            'total_input_size': 0,
            'total_output_size': 0,
            'skipped': 0,
            'errors': 0
        }

        # Check WebP support
        if not PIL.features.check('webp'):
            raise RuntimeError("Pillow was not compiled with WebP support. "
                             "Install with: pip install Pillow --force-reinstall")

    def categorize_image(self, image_path: Path) -> Tuple[ImageCategory, OptimizationConfig]:
        """Determine image category and optimization config from path/filename"""
        path_str = str(image_path).lower()
        filename = image_path.stem.lower()

        # App icons and launcher resources - use lossless
        # Category 1: App Branding (8 images)
        if any(x in path_str for x in ['app_icon', 'launcher', 'adaptive_icon', 'foreground_adaptive',
                                        'notification_icon', 'monogram', 'loading_spinner', 'feature_graphic']):
            return ImageCategory.APP_ICON, OptimizationConfig(
                category=ImageCategory.APP_ICON,
                use_lossless=True,
                webp_quality=100,
                base_density=DensityConfig.XXXHDPI  # Icons often 1024x1024 = 4x for 256dp
            )

        # Wordmark/Logo - treat as icon
        if 'wordmark' in path_str or 'logo' in path_str:
            return ImageCategory.APP_ICON, OptimizationConfig(
                category=ImageCategory.APP_ICON,
                use_lossless=True,
                webp_quality=100,
                base_density=DensityConfig.XXHDPI,  # 2048x512 horizontal
                preserve_transparency=True
            )

        # Splash screens - keep high quality but lossy
        if 'splash' in path_str:
            return ImageCategory.BACKGROUND, OptimizationConfig(
                category=ImageCategory.BACKGROUND,
                webp_quality=92,
                base_density=DensityConfig.XXHDPI,  # 1080x1920 = 3x for 360x640dp
                max_dimension=1920
            )

        # Category 2: Backgrounds (15 images)
        if 'background' in path_str or '3_backgrounds' in path_str:
            return ImageCategory.BACKGROUND, OptimizationConfig(
                category=ImageCategory.BACKGROUND,
                webp_quality=85,
                base_density=DensityConfig.XXHDPI,
                max_dimension=1920
            )

        # Category 3: Avatars (10 images) - medium quality, preserve transparency
        if 'avatar' in path_str or 'frame' in path_str or '4_avatars' in path_str:
            return ImageCategory.AVATAR, OptimizationConfig(
                category=ImageCategory.AVATAR,
                webp_quality=88,
                base_density=DensityConfig.XHDPI,  # 512x512 = 2x for 256dp
                preserve_transparency=True
            )

        # Category 4: Zodiac Signs (12 images)
        if any(x in filename for x in ['zodiac', 'aries', 'taurus', 'gemini', 'cancer', 'leo', 'virgo',
                                       'libra', 'scorpio', 'sagittarius', 'capricorn', 'aquarius', 'pisces']):
            return ImageCategory.SYMBOL, OptimizationConfig(
                category=ImageCategory.SYMBOL,
                webp_quality=92,
                base_density=DensityConfig.XHDPI,  # 512x512
                preserve_transparency=True
            )

        # Category 5: Chakras (7 images)
        if any(x in filename for x in ['chakra', 'muladhara', 'svadhisthana', 'manipura',
                                       'anahata', 'vishuddha', 'ajna', 'sahasrara']):
            return ImageCategory.SYMBOL, OptimizationConfig(
                category=ImageCategory.SYMBOL,
                webp_quality=92,
                base_density=DensityConfig.XHDPI,  # 512x512
                preserve_transparency=True
            )

        # Category 6: Moon Phases (8 images)
        if any(x in filename for x in ['moon', 'waxing', 'waning', 'crescent', 'gibbous', 'quarter']):
            return ImageCategory.SYMBOL, OptimizationConfig(
                category=ImageCategory.SYMBOL,
                webp_quality=92,
                base_density=DensityConfig.XHDPI,  # 512x512
                preserve_transparency=False  # Moon phases can be opaque
            )

        # Category 7: Elements (5 images)
        if any(x in filename for x in ['element', 'fire', 'water', 'earth', 'air', 'ether', 'spirit']):
            return ImageCategory.SYMBOL, OptimizationConfig(
                category=ImageCategory.SYMBOL,
                webp_quality=92,
                base_density=DensityConfig.XHDPI,  # 512x512
                preserve_transparency=True
            )

        # Category 8: Sacred Geometry (8 images)
        if any(x in filename for x in ['sacred', 'geometry', 'flower_of_life', 'metatron', 'sri_yantra',
                                       'seed_of_life', 'vesica', 'torus', 'merkaba', 'spiral', 'fibonacci']):
            return ImageCategory.SYMBOL, OptimizationConfig(
                category=ImageCategory.SYMBOL,
                webp_quality=92,
                base_density=DensityConfig.XXHDPI,  # 1024x1024 for detailed patterns
                preserve_transparency=True
            )

        # Category 9: UI Elements (12 images) - buttons, cards, progress bars
        if 'button' in path_str or '2_buttons' in path_str or any(x in filename for x in
                ['card', 'progress', 'loading_state', 'empty_state', 'checkmark', 'error', 'warning',
                 'tooltip', 'chevron', 'dropdown']):
            return ImageCategory.BUTTON, OptimizationConfig(
                category=ImageCategory.BUTTON,
                webp_quality=90,
                base_density=DensityConfig.XHDPI,
                preserve_transparency=True
            )

        # Category 10: Spiritual Symbols (8 images)
        if any(x in filename for x in ['om', 'aum', 'lotus', 'hamsa', 'tree_of_life', 'ankh',
                                       'yin_yang', 'mandala', 'infinity']):
            return ImageCategory.SYMBOL, OptimizationConfig(
                category=ImageCategory.SYMBOL,
                webp_quality=92,
                base_density=DensityConfig.XHDPI,  # 512x512
                preserve_transparency=True
            )

        # Category 11: Onboarding Illustrations (6 images)
        if any(x in filename for x in ['onboarding', 'welcome', 'feature', 'guidance', 'discovery',
                                       'insights', 'analysis', 'getting_started', 'ready']):
            return ImageCategory.BONUS_UI, OptimizationConfig(
                category=ImageCategory.BONUS_UI,
                webp_quality=88,
                base_density=DensityConfig.XXHDPI,  # 1080x1080
                preserve_transparency=True
            )

        # Default - bonus UI
        return ImageCategory.BONUS_UI, OptimizationConfig(
            category=ImageCategory.BONUS_UI,
            webp_quality=90,
            base_density=DensityConfig.XHDPI
        )

    def calculate_target_size(self, original_size: Tuple[int, int],
                            base_density: DensityConfig,
                            target_density: DensityConfig) -> Tuple[int, int]:
        """Calculate target size for a given density"""
        width, height = original_size
        scale_factor = target_density.scale / base_density.scale

        new_width = int(width * scale_factor)
        new_height = int(height * scale_factor)

        # Ensure dimensions are at least 1px
        return max(1, new_width), max(1, new_height)

    def get_android_resource_name(self, original_name: str) -> str:
        """Convert filename to Android resource naming convention"""
        # Remove extension
        name = Path(original_name).stem

        # Convert to lowercase
        name = name.lower()

        # Replace invalid characters with underscore
        # Android resources: [a-z0-9_.]
        cleaned = ''
        for char in name:
            if char.isalnum() or char == '_':
                cleaned += char
            elif char in [' ', '-', '.']:
                cleaned += '_'

        # Remove consecutive underscores
        while '__' in cleaned:
            cleaned = cleaned.replace('__', '_')

        # Remove leading/trailing underscores
        cleaned = cleaned.strip('_')

        # Shorten extremely long names
        if len(cleaned) > 80:
            # Try to keep meaningful parts
            parts = cleaned.split('_')
            if len(parts) > 3:
                # Keep first and last few parts
                cleaned = '_'.join(parts[:2] + ['...'] + parts[-2:])
            cleaned = cleaned[:80]

        return cleaned

    def optimize_image(self, input_path: Path, category: ImageCategory,
                      config: OptimizationConfig) -> Dict[str, int]:
        """Optimize a single image and generate all density variants"""

        result = {'input_size': 0, 'output_sizes': {}}

        try:
            # Load original image
            with Image.open(input_path) as img:
                # Get original size
                result['input_size'] = input_path.stat().st_size
                self.stats['total_input_size'] += result['input_size']

                # Convert RGBA to RGB if image has no transparency and we don't need it
                has_transparency = img.mode in ('RGBA', 'LA') or (
                    img.mode == 'P' and 'transparency' in img.info
                )

                # Apply max dimension constraint if specified
                original_size = img.size
                if config.max_dimension and max(img.size) > config.max_dimension:
                    ratio = config.max_dimension / max(img.size)
                    new_size = (int(img.size[0] * ratio), int(img.size[1] * ratio))
                    img = img.resize(new_size, Image.Resampling.LANCZOS)
                    print(f"  Resized from {original_size} to {img.size} (max_dimension={config.max_dimension})")

                # Generate resource name
                resource_name = self.get_android_resource_name(input_path.name)

                # Generate variants for each density
                for density in config.target_densities:
                    # Calculate target size
                    target_size = self.calculate_target_size(
                        img.size, config.base_density, density
                    )

                    # Skip if target size is too small
                    if min(target_size) < 8:
                        print(f"  Skipping {density.folder}: size would be {target_size} (too small)")
                        continue

                    # Create output directory
                    output_dir = self.output_dir / f"drawable-{density.folder}"
                    if not self.dry_run:
                        output_dir.mkdir(parents=True, exist_ok=True)

                    # Generate output path
                    output_path = output_dir / f"{resource_name}.webp"

                    # Resize if needed
                    if target_size != img.size:
                        resized = img.resize(target_size, Image.Resampling.LANCZOS)
                    else:
                        resized = img

                    # Convert and save as WebP
                    if not self.dry_run:
                        # Determine WebP mode
                        if config.preserve_transparency and has_transparency:
                            save_mode = resized.mode
                        else:
                            save_mode = 'RGB'
                            if resized.mode in ('RGBA', 'LA', 'P'):
                                # Create white background for transparency
                                background = Image.new('RGB', resized.size, (255, 255, 255))
                                if resized.mode == 'P':
                                    resized = resized.convert('RGBA')
                                background.paste(resized, mask=resized.split()[-1] if resized.mode in ('RGBA', 'LA') else None)
                                resized = background

                        # Save WebP
                        if save_mode != resized.mode:
                            resized = resized.convert(save_mode)

                        resized.save(
                            output_path,
                            'WEBP',
                            quality=config.webp_quality,
                            lossless=config.use_lossless,
                            method=6  # Slowest but best compression
                        )

                        output_size = output_path.stat().st_size
                        result['output_sizes'][density.folder] = output_size
                        self.stats['total_output_size'] += output_size

                        print(f"  {density.folder}: {target_size[0]}x{target_size[1]} "
                              f"({output_size / 1024:.1f} KB)")
                    else:
                        print(f"  [DRY RUN] {density.folder}: {target_size[0]}x{target_size[1]}")

                self.stats['processed'] += 1
                return result

        except Exception as e:
            print(f"  ERROR: {str(e)}")
            self.stats['errors'] += 1
            return result

    def process_directory(self, input_dir: Optional[Path] = None):
        """Process all images in the input directory"""

        if input_dir is None:
            input_dir = self.input_dir

        # Find all PNG files
        png_files = list(input_dir.rglob("*.png"))

        if not png_files:
            print(f"No PNG files found in {input_dir}")
            return

        print(f"\nFound {len(png_files)} PNG files to process")
        print(f"{'=' * 80}")

        # Process each image
        for i, png_path in enumerate(png_files, 1):
            print(f"\n[{i}/{len(png_files)}] Processing: {png_path.name}")
            print(f"  Size: {png_path.stat().st_size / 1024:.1f} KB")

            # Categorize and get config
            category, config = self.categorize_image(png_path)
            print(f"  Category: {category.value}")
            print(f"  WebP Quality: {config.webp_quality} ({'lossless' if config.use_lossless else 'lossy'})")
            print(f"  Base Density: {config.base_density.folder} ({config.base_density.scale}x)")

            # Optimize
            self.optimize_image(png_path, category, config)

    def print_summary(self):
        """Print optimization summary statistics"""
        print(f"\n{'=' * 80}")
        print("OPTIMIZATION SUMMARY")
        print(f"{'=' * 80}")
        print(f"Processed: {self.stats['processed']} images")
        print(f"Skipped:   {self.stats['skipped']} images")
        print(f"Errors:    {self.stats['errors']} images")

        if self.stats['processed'] > 0 and not self.dry_run:
            input_size_mb = self.stats['total_input_size'] / (1024 * 1024)
            output_size_mb = self.stats['total_output_size'] / (1024 * 1024)
            reduction = (1 - output_size_mb / input_size_mb) * 100 if input_size_mb > 0 else 0

            print(f"\nInput Size:  {input_size_mb:.2f} MB")
            print(f"Output Size: {output_size_mb:.2f} MB")
            print(f"Reduction:   {reduction:.1f}%")

            avg_densities = self.stats['total_output_size'] / self.stats['processed'] if self.stats['processed'] > 0 else 0
            print(f"Avg per image (all densities): {avg_densities / 1024:.1f} KB")

        print(f"{'=' * 80}\n")


def create_resource_mapping(output_dir: Path) -> Dict:
    """Create a JSON mapping of resources for easy reference"""
    mapping = {}

    for drawable_dir in output_dir.glob("drawable-*"):
        density = drawable_dir.name.replace("drawable-", "")
        mapping[density] = []

        for webp_file in drawable_dir.glob("*.webp"):
            mapping[density].append({
                'name': webp_file.stem,
                'file': webp_file.name,
                'size': webp_file.stat().st_size,
                'dimensions': None  # Could add with PIL if needed
            })

    return mapping


def main():
    parser = argparse.ArgumentParser(
        description="Optimize SpiritAtlas images for Android",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog=__doc__
    )

    parser.add_argument(
        '--input', '-i',
        type=Path,
        default=Path(__file__).parent / 'app' / 'src' / 'main' / 'res' / 'generated_assets',
        help='Input directory containing PNG images (default: ./app/src/main/res/generated_assets)'
    )

    parser.add_argument(
        '--output', '-o',
        type=Path,
        default=Path(__file__).parent / 'app' / 'src' / 'main' / 'res',
        help='Output directory for Android resources (default: ./app/src/main/res)'
    )

    parser.add_argument(
        '--dry-run',
        action='store_true',
        help='Preview what would be done without making changes'
    )

    parser.add_argument(
        '--create-mapping',
        action='store_true',
        help='Create resource_mapping.json after optimization'
    )

    args = parser.parse_args()

    # Validate input directory
    if not args.input.exists():
        print(f"ERROR: Input directory does not exist: {args.input}")
        sys.exit(1)

    print(f"""
╔══════════════════════════════════════════════════════════════════════════════╗
║                    SpiritAtlas Android Image Optimizer                       ║
╚══════════════════════════════════════════════════════════════════════════════╝

Input:  {args.input}
Output: {args.output}
Mode:   {'DRY RUN (no files will be created)' if args.dry_run else 'LIVE (files will be created)'}
""")

    # Create optimizer and process
    optimizer = ImageOptimizer(args.input, args.output, dry_run=args.dry_run)
    optimizer.process_directory()
    optimizer.print_summary()

    # Create resource mapping if requested
    if args.create_mapping and not args.dry_run:
        mapping_path = args.output / 'resource_mapping.json'
        mapping = create_resource_mapping(args.output)
        with open(mapping_path, 'w') as f:
            json.dump(mapping, f, indent=2)
        print(f"Created resource mapping: {mapping_path}")

    print("""
Next Steps:
1. Review the generated images in {output}/drawable-*/ directories
2. Update your Gradle build to include the new resources
3. Configure Coil image loader (see IMAGE_OPTIMIZATION_GUIDE.md)
4. Test on multiple device densities
5. Run: ./gradlew :app:assembleDebug to verify build

For complete usage instructions, see IMAGE_OPTIMIZATION_GUIDE.md
""".format(output=args.output))


if __name__ == '__main__':
    main()
