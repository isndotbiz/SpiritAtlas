#!/usr/bin/env python3
"""
SpiritAtlas Image Generator using fal.ai API with Flux Models

This script generates high-quality spiritual/cosmic images using fal.ai's Flux models.
Supports both Flux1.dev (highest quality) and Flux1-schnell (fastest generation).

Author: SpiritAtlas Team
API Provider: fal.ai
"""

import os
import sys
import argparse
import requests
from pathlib import Path
from typing import Optional, Dict, Any, List
from datetime import datetime

try:
    import fal_client
except ImportError:
    print("Error: fal_client package not installed. Run: pip install fal-client")
    sys.exit(1)


class FalImageGenerator:
    """
    Image generator using fal.ai API with Flux models.

    Features:
    - Flux1.dev for highest quality images
    - Flux1-schnell for fast generation
    - Customizable image sizes and parameters
    - Automatic file downloads and management
    - Progress tracking and error handling
    """

    # Available Flux models
    MODEL_FLUX_PRO = "fal-ai/flux-pro/v1.1"     # Commercial license, 6x faster (RECOMMENDED)
    MODEL_FLUX_DEV = "fal-ai/flux/dev"          # Non-commercial only
    MODEL_FLUX_SCHNELL = "fal-ai/flux/schnell"  # Fast generation

    # Predefined image sizes
    SIZE_PRESETS = {
        "square": "square",           # 1024x1024
        "square_hd": "square_hd",     # 1536x1536
        "portrait_4_3": "portrait_4_3",       # 896x1152
        "portrait_16_9": "portrait_16_9",     # 720x1280
        "landscape_4_3": "landscape_4_3",     # 1152x896
        "landscape_16_9": "landscape_16_9",   # 1280x720
    }

    def __init__(self, api_key: str):
        """
        Initialize the generator with API key.

        Args:
            api_key: Your fal.ai API key
        """
        if not api_key:
            raise ValueError("API key is required")

        os.environ["FAL_KEY"] = api_key
        self.api_key = api_key

    def generate(
        self,
        prompt: str,
        model: str = MODEL_FLUX_PRO,
        image_size: str = "landscape_4_3",
        width: Optional[int] = None,
        height: Optional[int] = None,
        num_images: int = 1,
        num_inference_steps: int = 28,
        guidance_scale: float = 3.5,
        seed: Optional[int] = None,
        output_format: str = "png",
        enable_safety_checker: bool = True,
        show_logs: bool = True
    ) -> Dict[str, Any]:
        """
        Generate images using fal.ai API.

        Args:
            prompt: Text description for image generation
            model: Model to use (MODEL_FLUX_DEV or MODEL_FLUX_SCHNELL)
            image_size: Predefined size preset (e.g., "landscape_4_3")
            width: Custom width (overrides image_size if both width and height provided)
            height: Custom height (overrides image_size if both width and height provided)
            num_images: Number of images to generate (1-4)
            num_inference_steps: Diffusion steps (1-50, default 28)
            guidance_scale: How closely to follow prompt (1-20, default 3.5)
            seed: Random seed for reproducibility
            output_format: Image format ("png" or "jpeg")
            enable_safety_checker: Enable NSFW content filtering
            show_logs: Show generation logs

        Returns:
            Dict containing image URLs, metadata, and generation info
        """
        # Build input parameters
        input_params = {
            "prompt": prompt,
            "num_images": num_images,
            "num_inference_steps": num_inference_steps,
            "guidance_scale": guidance_scale,
            "output_format": output_format,
            "enable_safety_checker": enable_safety_checker,
        }

        # Handle image size
        if width and height:
            input_params["image_size"] = {"width": width, "height": height}
        else:
            input_params["image_size"] = image_size

        # Add optional seed
        if seed is not None:
            input_params["seed"] = seed

        print(f"\n{'='*70}")
        print(f"Generating with {model}")
        print(f"{'='*70}")
        print(f"Prompt: {prompt}")
        print(f"Size: {input_params['image_size']}")
        print(f"Images: {num_images}")
        print(f"Steps: {num_inference_steps}")
        print(f"Guidance: {guidance_scale}")
        if seed:
            print(f"Seed: {seed}")
        print(f"{'='*70}\n")

        try:
            # Subscribe to the model endpoint
            result = fal_client.subscribe(
                model,
                arguments=input_params
            )

            print("\n✓ Generation completed successfully!")
            return result

        except Exception as e:
            print(f"\n✗ Error during generation: {str(e)}")
            raise

    def download_images(
        self,
        result: Dict[str, Any],
        output_dir: str = "generated_images",
        prefix: str = "image"
    ) -> List[str]:
        """
        Download generated images to local directory.

        Args:
            result: Result dictionary from generate()
            output_dir: Directory to save images
            prefix: Filename prefix

        Returns:
            List of saved file paths
        """
        output_path = Path(output_dir)
        output_path.mkdir(parents=True, exist_ok=True)

        saved_files = []
        # Handle both dict and object result types
        if isinstance(result, dict):
            images = result.get("images", [])
        else:
            images = getattr(result, "data", {}).get("images", [])

        print(f"\nDownloading {len(images)} image(s)...")

        for idx, image_data in enumerate(images):
            url = image_data.get("url")
            if not url:
                print(f"  ✗ Image {idx + 1}: No URL found")
                continue

            # Determine file extension
            content_type = image_data.get("content_type", "image/png")
            ext = "png" if "png" in content_type else "jpg"

            # Generate filename with timestamp
            timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
            filename = f"{prefix}_{timestamp}_{idx + 1}.{ext}"
            filepath = output_path / filename

            try:
                # Download image
                response = requests.get(url, timeout=30)
                response.raise_for_status()

                # Save to file
                with open(filepath, "wb") as f:
                    f.write(response.content)

                saved_files.append(str(filepath))
                print(f"  ✓ Image {idx + 1}: {filepath}")

            except Exception as e:
                print(f"  ✗ Image {idx + 1}: Download failed - {str(e)}")

        return saved_files

    def print_result_info(self, result: Dict[str, Any]):
        """Print detailed information about generation result."""
        # Handle both dict and object result types
        if isinstance(result, dict):
            data = result
            request_id = result.get("request_id", "N/A")
        else:
            data = getattr(result, "data", result)
            request_id = getattr(result, "request_id", "N/A")

        print(f"\n{'='*70}")
        print("GENERATION RESULT")
        print(f"{'='*70}")

        # Request info
        print(f"\nRequest ID: {request_id}")
        print(f"Prompt: {data.get('prompt', 'N/A')}")
        print(f"Seed: {data.get('seed', 'N/A')}")

        # Image info
        images = data.get("images", [])
        print(f"\nImages Generated: {len(images)}")
        for idx, img in enumerate(images):
            print(f"\n  Image {idx + 1}:")
            print(f"    URL: {img.get('url', 'N/A')}")
            print(f"    Size: {img.get('width', '?')}x{img.get('height', '?')}")
            print(f"    Format: {img.get('content_type', 'N/A')}")

        # Safety check
        nsfw_concepts = data.get("has_nsfw_concepts", [])
        if nsfw_concepts:
            print(f"\n  NSFW Check: {nsfw_concepts}")

        # Timing info
        timings = data.get("timings", {})
        if timings:
            print(f"\nTimings:")
            for key, value in timings.items():
                print(f"  {key}: {value:.2f}s")

        print(f"{'='*70}\n")


def main():
    """Command-line interface for image generation."""
    parser = argparse.ArgumentParser(
        description="Generate images using fal.ai Flux models",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  # Generate with Flux1.dev (best quality)
  python fal_generator.py "cosmic spiral galaxy with sacred geometry" \\
    --model dev --size landscape_4_3

  # Generate multiple images with custom seed
  python fal_generator.py "spiritual mandala glowing with energy" \\
    --model dev --num-images 4 --seed 12345

  # Fast generation with Flux-schnell
  python fal_generator.py "mystical moon phases" \\
    --model schnell --size square_hd

  # Custom dimensions
  python fal_generator.py "celestial bodies in alignment" \\
    --width 1920 --height 1080 --steps 35

  # Save to specific directory
  python fal_generator.py "spiritual chakra visualization" \\
    --output-dir ./spiritual_assets --prefix chakra
        """
    )

    # Required arguments
    parser.add_argument(
        "prompt",
        type=str,
        help="Text description for image generation"
    )

    # Optional arguments
    parser.add_argument(
        "--api-key",
        type=str,
        default=os.environ.get("FAL_KEY"),
        help="fal.ai API key (default: FAL_KEY env variable)"
    )

    parser.add_argument(
        "--model",
        type=str,
        choices=["pro", "dev", "schnell"],
        default="pro",
        help="Model to use: pro (commercial, 6x faster), dev (non-commercial), or schnell (fast) (default: pro)"
    )

    parser.add_argument(
        "--size",
        type=str,
        choices=list(FalImageGenerator.SIZE_PRESETS.keys()),
        default="landscape_4_3",
        help="Predefined image size (default: landscape_4_3)"
    )

    parser.add_argument(
        "--width",
        type=int,
        help="Custom width (requires --height)"
    )

    parser.add_argument(
        "--height",
        type=int,
        help="Custom height (requires --width)"
    )

    parser.add_argument(
        "--num-images",
        type=int,
        default=1,
        choices=[1, 2, 3, 4],
        help="Number of images to generate (1-4) (default: 1)"
    )

    parser.add_argument(
        "--steps",
        type=int,
        default=28,
        help="Number of inference steps (1-50) (default: 28)"
    )

    parser.add_argument(
        "--guidance",
        type=float,
        default=3.5,
        help="Guidance scale (1-20) (default: 3.5)"
    )

    parser.add_argument(
        "--seed",
        type=int,
        help="Random seed for reproducibility"
    )

    parser.add_argument(
        "--format",
        type=str,
        choices=["png", "jpeg"],
        default="png",
        help="Output format (default: png)"
    )

    parser.add_argument(
        "--output-dir",
        type=str,
        default="generated_images",
        help="Directory to save images (default: generated_images)"
    )

    parser.add_argument(
        "--prefix",
        type=str,
        default="image",
        help="Filename prefix (default: image)"
    )

    parser.add_argument(
        "--no-download",
        action="store_true",
        help="Don't download images, just show URLs"
    )

    parser.add_argument(
        "--no-safety",
        action="store_true",
        help="Disable NSFW safety checker"
    )

    parser.add_argument(
        "--no-logs",
        action="store_true",
        help="Don't show generation logs"
    )

    args = parser.parse_args()

    # Validate API key
    if not args.api_key:
        print("Error: API key required. Set FAL_KEY environment variable or use --api-key")
        sys.exit(1)

    # Validate custom dimensions
    if (args.width and not args.height) or (args.height and not args.width):
        print("Error: Both --width and --height must be specified together")
        sys.exit(1)

    # Map model name to full model ID
    model_map = {
        "pro": FalImageGenerator.MODEL_FLUX_PRO,
        "dev": FalImageGenerator.MODEL_FLUX_DEV,
        "schnell": FalImageGenerator.MODEL_FLUX_SCHNELL,
    }
    model = model_map[args.model]

    try:
        # Initialize generator
        generator = FalImageGenerator(args.api_key)

        # Generate images
        result = generator.generate(
            prompt=args.prompt,
            model=model,
            image_size=args.size,
            width=args.width,
            height=args.height,
            num_images=args.num_images,
            num_inference_steps=args.steps,
            guidance_scale=args.guidance,
            seed=args.seed,
            output_format=args.format,
            enable_safety_checker=not args.no_safety,
            show_logs=not args.no_logs
        )

        # Print result information
        generator.print_result_info(result)

        # Download images
        if not args.no_download:
            saved_files = generator.download_images(
                result,
                output_dir=args.output_dir,
                prefix=args.prefix
            )

            if saved_files:
                print(f"\n✓ Successfully saved {len(saved_files)} image(s)")
                print(f"  Location: {args.output_dir}/")

        print("\n✓ Done!\n")

    except Exception as e:
        print(f"\n✗ Error: {str(e)}\n")
        sys.exit(1)


if __name__ == "__main__":
    main()
