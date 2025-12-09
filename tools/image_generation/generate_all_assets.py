#!/usr/bin/env python3
"""
SpiritAtlas Batch Image Generation Orchestrator

This script reads IMAGE_GENERATION_PROMPTS.md and generates all 59 assets
using Flux1.dev via fal.ai or replicate.ai for ultra-high quality.

Features:
- Parses prompts from markdown with all metadata
- Generates images with progress tracking and ETA
- Organizes output in Android resource structure
- Supports resume on interruption
- Creates manifest of all generated assets
- Allows sample batch generation for review

Usage:
    # Generate 5 sample images for review
    python generate_all_assets.py --sample

    # Generate all 59 images
    python generate_all_assets.py --full

    # Resume interrupted generation
    python generate_all_assets.py --resume

    # Use specific provider
    python generate_all_assets.py --full --provider replicate
"""

import os
import re
import json
import time
import argparse
from dataclasses import dataclass, asdict
from typing import List, Dict, Optional, Tuple
from pathlib import Path
import hashlib
from datetime import datetime, timedelta

try:
    import fal_client
    FAL_AVAILABLE = True
except ImportError:
    FAL_AVAILABLE = False
    print("⚠️  fal_client not installed. Install with: pip install fal-client")

try:
    import replicate
    REPLICATE_AVAILABLE = True
except (ImportError, Exception) as e:
    REPLICATE_AVAILABLE = False
    # Silently disable replicate if there's any import error (e.g., Python 3.14 compatibility)

try:
    import requests
    from PIL import Image
    from io import BytesIO
    IMAGING_AVAILABLE = True
except ImportError:
    IMAGING_AVAILABLE = False
    print("⚠️  requests/PIL not installed. Install with: pip install requests pillow")


@dataclass
class ImageAsset:
    """Represents a single image asset to generate"""
    category: str
    name: str
    prompt: str
    width: int
    height: int
    output_filename: str
    section_number: str
    prompt_number: str
    variant: Optional[str] = None
    notes: Optional[str] = None

    def get_output_path(self, base_dir: Path) -> Path:
        """Get full output path for this asset"""
        category_map = {
            "1": "1_app_icons",
            "2": "2_buttons",
            "3": "3_backgrounds",
            "4": "4_avatars",
            "5": "5_symbols",
            "6": "6_bonus_ui"
        }
        category_folder = category_map.get(self.section_number, "other")
        return base_dir / category_folder / self.output_filename

    def get_hash(self) -> str:
        """Get unique hash for this asset (for resume functionality)"""
        content = f"{self.category}|{self.name}|{self.prompt}|{self.width}x{self.height}"
        return hashlib.md5(content.encode()).hexdigest()[:12]


@dataclass
class GenerationResult:
    """Result of generating an image"""
    asset: ImageAsset
    success: bool
    output_path: Optional[Path] = None
    error: Optional[str] = None
    generation_time: float = 0.0
    file_size_kb: Optional[float] = None


class PromptParser:
    """Parses IMAGE_GENERATION_PROMPTS.md to extract all asset definitions"""

    def __init__(self, markdown_path: Path):
        self.markdown_path = markdown_path
        self.content = self._read_file()

    def _read_file(self) -> str:
        """Read the markdown file"""
        with open(self.markdown_path, 'r', encoding='utf-8') as f:
            return f.read()

    def parse_all_assets(self) -> List[ImageAsset]:
        """Parse all assets from the markdown file"""
        assets = []

        # Split into sections
        sections = re.split(r'^## \d+\.', self.content, flags=re.MULTILINE)

        for section in sections[1:]:  # Skip the intro
            section_assets = self._parse_section(section)
            assets.extend(section_assets)

        return assets

    def _parse_section(self, section_text: str) -> List[ImageAsset]:
        """Parse all assets from a single section"""
        assets = []

        # Extract section number and category
        first_line = section_text.split('\n')[0]
        section_match = re.match(r'^\s*(.+?)\s*\((\d+)\s+prompts?\)', first_line)
        if not section_match:
            return assets

        category = section_match.group(1).strip()
        section_num = self._get_section_number(category)

        # Split into individual prompts
        prompt_sections = re.split(r'^### \d+\.\d+', section_text, flags=re.MULTILINE)

        for prompt_section in prompt_sections[1:]:
            prompt_assets = self._parse_prompt_section(prompt_section, category, section_num)
            assets.extend(prompt_assets)

        return assets

    def _parse_prompt_section(self, prompt_text: str, category: str, section_num: str) -> List[ImageAsset]:
        """Parse a single prompt section which may generate multiple assets"""
        assets = []

        # Extract prompt number and name
        lines = prompt_text.split('\n')
        name_line = lines[0].strip()

        # Extract dimensions
        dimensions = self._extract_dimensions(name_line, prompt_text)
        if not dimensions:
            return assets

        # Extract actual prompt
        prompt = self._extract_prompt(prompt_text)
        if not prompt:
            return assets

        # Extract name
        name_match = re.match(r'^(.*?)\s*\(', name_line)
        if name_match:
            name = name_match.group(1).strip()
        else:
            name = name_line.split('-')[0].strip() if '-' in name_line else name_line.strip()

        # Check if this is a multi-image prompt
        multi_images = self._detect_multi_images(prompt_text, name)

        if multi_images:
            # Generate multiple assets for this prompt
            for variant_name, variant_prompt in multi_images:
                filename = self._generate_filename(category, name, variant_name, dimensions)
                asset = ImageAsset(
                    category=category,
                    name=name,
                    prompt=variant_prompt,
                    width=dimensions[0],
                    height=dimensions[1],
                    output_filename=filename,
                    section_number=section_num,
                    prompt_number=self._extract_prompt_number(name_line),
                    variant=variant_name
                )
                assets.append(asset)
        else:
            # Single asset
            filename = self._generate_filename(category, name, None, dimensions)
            asset = ImageAsset(
                category=category,
                name=name,
                prompt=prompt,
                width=dimensions[0],
                height=dimensions[1],
                output_filename=filename,
                section_number=section_num,
                prompt_number=self._extract_prompt_number(name_line)
            )
            assets.append(asset)

        return assets

    def _extract_dimensions(self, name_line: str, full_text: str) -> Optional[Tuple[int, int]]:
        """Extract dimensions from name line or full text"""
        # Try name line first (e.g., "1024x1024" or "512x512")
        dim_match = re.search(r'\((\d+)x(\d+)', name_line)
        if dim_match:
            return (int(dim_match.group(1)), int(dim_match.group(2)))

        # Try to find in full text under **Dimensions:**
        dim_section = re.search(r'\*\*Dimensions:\*\*.*?(\d+)x(\d+)', full_text, re.DOTALL)
        if dim_section:
            return (int(dim_section.group(1)), int(dim_section.group(2)))

        # Default dimensions based on type
        if 'background' in name_line.lower() or 'Background' in full_text[:200]:
            return (1080, 1920)
        elif 'icon' in name_line.lower():
            return (512, 512)
        else:
            return (512, 512)  # Default

    def _extract_prompt(self, prompt_text: str) -> Optional[str]:
        """Extract the actual prompt from the section"""
        # Find content between ```
        prompt_match = re.search(r'```\n(.*?)\n```', prompt_text, re.DOTALL)
        if prompt_match:
            prompt = prompt_match.group(1).strip()
            # If this contains multiple prompts (like chakras), take first
            if '\n\n' in prompt and ':' in prompt:
                # Multi-prompt section, take the first one
                first_prompt = prompt.split('\n\n')[0]
                if 'CHAKRA' in first_prompt or 'SIGNS' in first_prompt:
                    return prompt  # Return full multi-prompt
                return first_prompt
            return prompt
        return None

    def _detect_multi_images(self, prompt_text: str, name: str) -> Optional[List[Tuple[str, str]]]:
        """Detect if this prompt generates multiple images and extract variants"""
        variants = []

        # Detect chakras (7 images)
        if 'CHAKRA' in prompt_text and 'Complete Set' in name:
            chakra_data = [
                ("root", "ROOT (MULADHARA)", "#EF4444", "red", "Four-petaled lotus", "'Lam'", "Downward-pointing triangle"),
                ("sacral", "SACRAL (SVADHISTHANA)", "#F97316", "orange", "Six-petaled lotus", "'Vam'", "Crescent moon"),
                ("solar_plexus", "SOLAR PLEXUS (MANIPURA)", "#FDE047", "yellow", "Ten-petaled lotus", "'Ram'", "Downward-pointing triangle"),
                ("heart", "HEART (ANAHATA)", "#22C55E", "green", "Twelve-petaled lotus", "'Yam'", "Two overlapping triangles forming star of David"),
                ("throat", "THROAT (VISHUDDHA)", "#3B82F6", "blue", "Sixteen-petaled lotus", "'Ham'", "Circle with downward-pointing triangle"),
                ("third_eye", "THIRD EYE (AJNA)", "#6366F1", "indigo", "Two-petaled lotus", "'Om'", "Circle with downward-pointing triangle"),
                ("crown", "CROWN (SAHASRARA)", "#8B5CF6", "purple", "Thousand-petaled lotus", "'Ah'", "Full blooming lotus with inner sacred geometry mandala")
            ]
            for variant, full_name, color, color_name, petals, sanskrit, geometry in chakra_data:
                prompt = f"A glowing sacred symbol of {full_name.split('(')[0].lower()}chakra. {petals} in rich {color_name} ({color}) with Sanskrit symbol for {sanskrit} in center. {geometry} in darker {color_name}. Ethereal {color_name} glow radiating outward. Black/transparent background. Sacred geometry, spiritual chakra art, hindu symbolism, professional icon design, 4K PNG."
                variants.append((variant, prompt))
            return variants

        # Detect zodiac (12 images)
        if 'Zodiac Constellation' in name and 'Complete Set' in name:
            zodiac_data = [
                ("aries", "Aries", "Fire", "orange #F97316", "Ram"),
                ("taurus", "Taurus", "Earth", "green #22C55E", "Bull"),
                ("gemini", "Gemini", "Air", "cyan #06B6D4", "Twins"),
                ("cancer", "Cancer", "Water", "teal #14B8A6", "Crab"),
                ("leo", "Leo", "Fire", "orange #F97316", "Lion"),
                ("virgo", "Virgo", "Earth", "green #22C55E", "Maiden"),
                ("libra", "Libra", "Air", "cyan #06B6D4", "Scales"),
                ("scorpio", "Scorpio", "Water", "teal #14B8A6", "Scorpion"),
                ("sagittarius", "Sagittarius", "Fire", "orange #F97316", "Archer"),
                ("capricorn", "Capricorn", "Earth", "green #22C55E", "Goat"),
                ("aquarius", "Aquarius", "Air", "cyan #06B6D4", "Water bearer"),
                ("pisces", "Pisces", "Water", "teal #14B8A6", "Fish")
            ]
            for variant, sign, element, color, symbol in zodiac_data:
                prompt = f"A mystical constellation artwork for {sign}. The constellation pattern in glowing stardust blue (#3B82F6) stars connected by ethereal golden lines (#D97706). The stars vary in size and brightness creating depth. Around the constellation, subtle cosmic nebula in {color}. The {sign} glyph appears subtly in corner in matching element color. Deep space cosmic background with night sky gradient (#1E1B4B). The constellation forms recognizable shape of {symbol}. Astrological art, cosmic constellation, zodiac symbolism, celestial map, 4K square format."
                variants.append((variant, prompt))
            return variants

        # Detect elements (4 images)
        if 'Element Symbols' in name:
            element_data = [
                ("fire", "An upward-pointing triangle in flames. The triangle is filled with fire gradient from orange (#F97316) to red (#EF4444) to golden yellow (#FBBF24). Flame particles dancing around the triangle edges. Ethereal fire glow radiating outward. Sacred geometry precision. Black/transparent background. Fire element symbol, spiritual iconography, elemental magic, alchemical symbol, 4K PNG."),
                ("earth", "A downward-pointing triangle filled with earth tones. Gradient from forest green (#22C55E) through emerald to earth brown (#78350F). Texture of crystalline structures or sacred geometric patterns within. Root-like tendrils extending from bottom point. Earthy glow in green. Black/transparent background. Earth element symbol, spiritual iconography, elemental magic, alchemical symbol, 4K PNG."),
                ("air", "An upward-pointing triangle with horizontal line through center (traditional air symbol). Filled with flowing cyan (#06B6D4) to sky blue (#3B82F6) gradient. Wisps of wind and cloud-like formations swirling around. Light particles floating upward. Airy ethereal glow. Black/transparent background. Air element symbol, spiritual iconography, elemental magic, alchemical symbol, 4K PNG."),
                ("water", "A downward-pointing triangle filled with water gradient. Flowing from deep teal (#14B8A6) through ocean blue (#3B82F6) to light aqua. Water droplets and wave patterns within the triangle. Flowing energy downward like waterfall. Watery shimmer and glow. Black/transparent background. Water element symbol, spiritual iconography, elemental magic, alchemical symbol, 4K PNG.")
            ]
            for variant, prompt in element_data:
                variants.append((variant, prompt))
            return variants

        # Detect moon phases (8 images)
        if 'Moon Phase' in name and 'Complete' in name:
            moon_phases = [
                ("new_moon", "Completely dark circle with faint outline glow"),
                ("waxing_crescent", "Thin crescent on right, growing"),
                ("first_quarter", "Right half illuminated, left half dark"),
                ("waxing_gibbous", "More than half illuminated, right side"),
                ("full_moon", "Completely illuminated, bright with full detail"),
                ("waning_gibbous", "More than half illuminated, left side"),
                ("last_quarter", "Left half illuminated, right half dark"),
                ("waning_crescent", "Thin crescent on left, shrinking")
            ]
            for variant, description in moon_phases:
                prompt = f"A realistic moon phase illustration: {description}. The moon is rendered in realistic detail with soft moonlight silver (#E2E8F0) surface showing craters and maria. The moon floats on deep night sky background (#1E1B4B) with scattered stars. Subtle ethereal glow appropriate for this phase. Realistic lunar surface texture, astronomical accuracy, ethereal moonlight glow, spiritual moon art, 4K quality, 200x200 pixels."
                variants.append((variant, prompt))
            return variants

        return None

    def _generate_filename(self, category: str, name: str, variant: Optional[str], dimensions: Tuple[int, int]) -> str:
        """Generate standardized filename"""
        # Clean name
        clean_name = re.sub(r'[^\w\s-]', '', name.lower())
        clean_name = re.sub(r'\s+', '_', clean_name)

        # Clean category
        clean_category = re.sub(r'[^\w\s-]', '', category.lower())
        clean_category = re.sub(r'\s+', '_', clean_category)
        clean_category = clean_category.replace('__', '_').replace('_&_', '_')

        # Build filename
        parts = ['spiritatlas', clean_category, clean_name]
        if variant:
            parts.append(variant)
        parts.append(f"{dimensions[0]}x{dimensions[1]}")

        filename = '_'.join(parts) + '.png'
        # Clean up any double underscores
        filename = filename.replace('__', '_')

        return filename

    def _get_section_number(self, category: str) -> str:
        """Get section number from category name"""
        if 'Icon' in category or 'Branding' in category:
            return "1"
        elif 'Button' in category or 'Interactive' in category:
            return "2"
        elif 'Background' in category:
            return "3"
        elif 'Avatar' in category or 'Placeholder' in category:
            return "4"
        elif 'Symbol' in category or 'Icon' in category:
            return "5"
        elif 'Bonus' in category or 'UI Element' in category:
            return "6"
        return "0"

    def _extract_prompt_number(self, name_line: str) -> str:
        """Extract prompt number like '1.1' from name line"""
        match = re.search(r'(\d+\.\d+)', name_line)
        return match.group(1) if match else "0.0"


class ImageGenerator:
    """Handles image generation using fal.ai or replicate.ai"""

    # Cost estimates per image (approximate)
    COSTS = {
        'fal': 0.03,      # ~$0.03 per Flux1.dev generation on fal.ai
        'replicate': 0.025  # ~$0.025 per Flux1.dev generation on replicate
    }

    def __init__(self, provider: str = 'fal', api_key: Optional[str] = None):
        self.provider = provider
        self.api_key = api_key or os.environ.get(
            'FAL_KEY' if provider == 'fal' else 'REPLICATE_API_TOKEN'
        )

        if not self.api_key:
            raise ValueError(
                f"API key not found. Set {('FAL_KEY' if provider == 'fal' else 'REPLICATE_API_TOKEN')} "
                "environment variable or pass api_key parameter"
            )

        if provider == 'fal' and not FAL_AVAILABLE:
            raise ImportError("fal_client not installed. Run: pip install fal-client")
        if provider == 'replicate' and not REPLICATE_AVAILABLE:
            raise ImportError("replicate not installed. Run: pip install replicate")
        if not IMAGING_AVAILABLE:
            raise ImportError("requests/PIL not installed. Run: pip install requests pillow")

    def generate(self, asset: ImageAsset) -> GenerationResult:
        """Generate a single image asset"""
        start_time = time.time()

        try:
            if self.provider == 'fal':
                image_url = self._generate_fal(asset)
            elif self.provider == 'replicate':
                image_url = self._generate_replicate(asset)
            else:
                raise ValueError(f"Unknown provider: {self.provider}")

            # Download and save image
            output_path = self._download_image(image_url, asset)

            generation_time = time.time() - start_time
            file_size = output_path.stat().st_size / 1024  # KB

            return GenerationResult(
                asset=asset,
                success=True,
                output_path=output_path,
                generation_time=generation_time,
                file_size_kb=file_size
            )

        except Exception as e:
            generation_time = time.time() - start_time
            return GenerationResult(
                asset=asset,
                success=False,
                error=str(e),
                generation_time=generation_time
            )

    def _generate_fal(self, asset: ImageAsset) -> str:
        """Generate image using fal.ai"""
        os.environ['FAL_KEY'] = self.api_key

        handler = fal_client.submit(
            "fal-ai/flux-pro/v1.1",
            arguments={
                "prompt": asset.prompt,
                "image_size": {
                    "width": asset.width,
                    "height": asset.height
                },
                "num_inference_steps": 28,
                "guidance_scale": 3.5,
                "num_images": 1,
                "enable_safety_checker": False
            }
        )

        result = handler.get()

        if 'images' in result and len(result['images']) > 0:
            return result['images'][0]['url']
        else:
            raise Exception("No image generated by fal.ai")

    def _generate_replicate(self, asset: ImageAsset) -> str:
        """Generate image using replicate.ai"""
        client = replicate.Client(api_token=self.api_key)

        output = client.run(
            "black-forest-labs/flux-1.1-pro",
            input={
                "prompt": asset.prompt,
                "width": asset.width,
                "height": asset.height,
                "num_inference_steps": 50,
                "guidance_scale": 7.5,
                "num_outputs": 1
            }
        )

        if isinstance(output, list) and len(output) > 0:
            return output[0]
        elif isinstance(output, str):
            return output
        else:
            raise Exception("No image generated by replicate")

    def _download_image(self, url: str, asset: ImageAsset) -> Path:
        """Download image from URL and save to appropriate location"""
        response = requests.get(url)
        response.raise_for_status()

        # Load image
        img = Image.open(BytesIO(response.content))

        # Ensure output directory exists
        output_path = asset.get_output_path(Path.cwd() / 'app' / 'src' / 'main' / 'res' / 'generated_assets')
        output_path.parent.mkdir(parents=True, exist_ok=True)

        # Save image
        img.save(output_path, 'PNG', optimize=True)

        return output_path

    @classmethod
    def estimate_cost(cls, num_images: int, provider: str = 'fal') -> float:
        """Estimate total cost for generating images"""
        return num_images * cls.COSTS.get(provider, 0.03)


class GenerationOrchestrator:
    """Orchestrates the entire batch generation process"""

    def __init__(
        self,
        prompts_file: Path,
        provider: str = 'fal',
        api_key: Optional[str] = None,
        manifest_file: Optional[Path] = None
    ):
        self.prompts_file = prompts_file
        self.provider = provider
        self.manifest_file = manifest_file or Path('asset_manifest.json')

        # Parse prompts
        print("📖 Parsing IMAGE_GENERATION_PROMPTS.md...")
        parser = PromptParser(prompts_file)
        self.assets = parser.parse_all_assets()
        print(f"✅ Found {len(self.assets)} assets to generate\n")

        # Initialize generator
        self.generator = ImageGenerator(provider=provider, api_key=api_key)

        # Load manifest if exists (for resume)
        self.manifest = self._load_manifest()

    def _load_manifest(self) -> Dict:
        """Load existing manifest or create new one"""
        if self.manifest_file.exists():
            with open(self.manifest_file, 'r') as f:
                return json.load(f)
        else:
            return {
                'metadata': {
                    'created': datetime.now().isoformat(),
                    'total_assets': len(self.assets),
                    'provider': self.provider,
                    'version': '1.0'
                },
                'assets': {},
                'summary': {
                    'completed': 0,
                    'failed': 0,
                    'pending': len(self.assets)
                }
            }

    def _save_manifest(self):
        """Save manifest to file"""
        with open(self.manifest_file, 'w') as f:
            json.dump(self.manifest, f, indent=2)

    def _is_completed(self, asset: ImageAsset) -> bool:
        """Check if asset has already been generated"""
        asset_hash = asset.get_hash()
        return asset_hash in self.manifest['assets'] and \
               self.manifest['assets'][asset_hash].get('success', False)

    def generate_sample(self, count: int = 5) -> List[GenerationResult]:
        """Generate a sample batch for review"""
        print(f"\n🎨 SAMPLE GENERATION MODE")
        print(f"Generating {count} sample images for review\n")
        print("=" * 70)

        # Select diverse samples across categories
        samples = self._select_diverse_samples(count)

        return self._generate_batch(samples, is_sample=True)

    def generate_full(self, resume: bool = False) -> List[GenerationResult]:
        """Generate all assets"""
        assets_to_generate = self.assets

        if resume:
            # Filter out already completed
            assets_to_generate = [a for a in self.assets if not self._is_completed(a)]
            print(f"\n♻️  RESUME MODE: {len(assets_to_generate)} assets remaining")
        else:
            print(f"\n🚀 FULL GENERATION MODE")
            print(f"Generating all {len(self.assets)} assets\n")

        if not assets_to_generate:
            print("✅ All assets already generated!")
            return []

        print("=" * 70)

        return self._generate_batch(assets_to_generate, is_sample=False)

    def _select_diverse_samples(self, count: int) -> List[ImageAsset]:
        """Select diverse sample assets across categories"""
        # Group by section
        by_section = {}
        for asset in self.assets:
            section = asset.section_number
            if section not in by_section:
                by_section[section] = []
            by_section[section].append(asset)

        # Take one from each section first
        samples = []
        for section in sorted(by_section.keys()):
            if len(samples) < count:
                samples.append(by_section[section][0])

        # Fill remaining with variety
        remaining = count - len(samples)
        if remaining > 0:
            other_assets = [a for a in self.assets if a not in samples]
            samples.extend(other_assets[:remaining])

        return samples[:count]

    def _generate_batch(self, assets: List[ImageAsset], is_sample: bool) -> List[GenerationResult]:
        """Generate a batch of assets with progress tracking"""
        results = []
        total = len(assets)
        start_time = time.time()

        for i, asset in enumerate(assets, 1):
            # Progress header
            print(f"\n{'📦' if is_sample else '🎨'} [{i}/{total}] {asset.name}")
            if asset.variant:
                print(f"   Variant: {asset.variant}")
            print(f"   Category: {asset.category}")
            print(f"   Size: {asset.width}x{asset.height}")
            print(f"   Output: {asset.output_filename}")

            # Generate
            print(f"   ⏳ Generating with {self.provider}...", end='', flush=True)
            result = self.generator.generate(asset)

            if result.success:
                print(f"\r   ✅ Generated in {result.generation_time:.1f}s ({result.file_size_kb:.1f} KB)")

                # Update manifest
                self.manifest['assets'][asset.get_hash()] = {
                    'name': asset.name,
                    'variant': asset.variant,
                    'filename': asset.output_filename,
                    'path': str(result.output_path),
                    'success': True,
                    'size_kb': result.file_size_kb,
                    'generated_at': datetime.now().isoformat()
                }
                self.manifest['summary']['completed'] += 1
            else:
                print(f"\r   ❌ Failed: {result.error}")

                # Update manifest
                self.manifest['assets'][asset.get_hash()] = {
                    'name': asset.name,
                    'variant': asset.variant,
                    'filename': asset.output_filename,
                    'success': False,
                    'error': result.error,
                    'generated_at': datetime.now().isoformat()
                }
                self.manifest['summary']['failed'] += 1

            self.manifest['summary']['pending'] = total - i
            results.append(result)

            # Save manifest after each generation (for resume)
            self._save_manifest()

            # ETA calculation
            if i < total:
                elapsed = time.time() - start_time
                avg_time = elapsed / i
                remaining_time = avg_time * (total - i)
                eta = datetime.now() + timedelta(seconds=remaining_time)
                print(f"   📊 Progress: {i}/{total} ({i/total*100:.1f}%) | ETA: {eta.strftime('%H:%M:%S')}")

        # Final summary
        self._print_summary(results, time.time() - start_time, is_sample)

        return results

    def _print_summary(self, results: List[GenerationResult], total_time: float, is_sample: bool):
        """Print generation summary"""
        successful = sum(1 for r in results if r.success)
        failed = sum(1 for r in results if not r.success)
        total_size = sum(r.file_size_kb or 0 for r in results if r.success)

        print("\n" + "=" * 70)
        print(f"{'📦 SAMPLE' if is_sample else '🎨 FULL'} GENERATION COMPLETE")
        print("=" * 70)
        print(f"✅ Successful: {successful}/{len(results)}")
        print(f"❌ Failed: {failed}/{len(results)}")
        print(f"⏱️  Total time: {total_time/60:.1f} minutes")
        print(f"📦 Total size: {total_size/1024:.1f} MB")
        print(f"💰 Estimated cost: ${ImageGenerator.estimate_cost(successful, self.provider):.2f}")
        print(f"📄 Manifest saved: {self.manifest_file}")
        print("=" * 70)

        if is_sample:
            print("\n👀 Please review the sample images in:")
            print("   app/src/main/res/generated_assets/")
            print("\nIf satisfied, run with --full to generate all 59 assets")
        else:
            print("\n🎉 All assets generated!")
            print("   Check app/src/main/res/generated_assets/ for all images")


def main():
    parser = argparse.ArgumentParser(
        description='SpiritAtlas Batch Image Generation Orchestrator',
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  # Generate 5 sample images for review
  python generate_all_assets.py --sample

  # Generate all 59 images
  python generate_all_assets.py --full

  # Resume interrupted generation
  python generate_all_assets.py --full --resume

  # Use replicate instead of fal
  python generate_all_assets.py --full --provider replicate
        """
    )

    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument('--sample', action='store_true',
                      help='Generate 5 sample images for review')
    group.add_argument('--full', action='store_true',
                      help='Generate all 59 assets')

    parser.add_argument('--resume', action='store_true',
                       help='Resume interrupted generation (skip completed)')
    parser.add_argument('--provider', choices=['fal', 'replicate'], default='fal',
                       help='Image generation provider (default: fal)')
    parser.add_argument('--prompts', type=Path,
                       default=Path('IMAGE_GENERATION_PROMPTS.md'),
                       help='Path to prompts markdown file')
    parser.add_argument('--api-key', type=str,
                       help='API key (or set FAL_KEY/REPLICATE_API_TOKEN env var)')
    parser.add_argument('--count', type=int, default=5,
                       help='Number of samples to generate (default: 5)')

    args = parser.parse_args()

    # Check if prompts file exists
    if not args.prompts.exists():
        print(f"❌ Error: Prompts file not found: {args.prompts}")
        print("   Make sure IMAGE_GENERATION_PROMPTS.md exists in the current directory")
        return 1

    try:
        # Initialize orchestrator
        orchestrator = GenerationOrchestrator(
            prompts_file=args.prompts,
            provider=args.provider,
            api_key=args.api_key
        )

        # Show cost estimate
        if args.sample:
            cost = ImageGenerator.estimate_cost(args.count, args.provider)
            print(f"\n💰 Estimated cost for {args.count} samples: ${cost:.2f}")
        else:
            cost = ImageGenerator.estimate_cost(len(orchestrator.assets), args.provider)
            print(f"\n💰 Estimated cost for all {len(orchestrator.assets)} assets: ${cost:.2f}")

        response = input("\nProceed? (y/n): ")
        if response.lower() != 'y':
            print("❌ Cancelled")
            return 0

        # Generate
        if args.sample:
            orchestrator.generate_sample(count=args.count)
        else:
            orchestrator.generate_full(resume=args.resume)

        return 0

    except Exception as e:
        print(f"\n❌ Error: {e}")
        import traceback
        traceback.print_exc()
        return 1


if __name__ == '__main__':
    exit(main())
