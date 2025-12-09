#!/usr/bin/env python3
"""
SpiritAtlas Image Generator using Replicate.ai API

This script generates high-quality spiritual/cosmic images using Flux and Stable Diffusion models
via the Replicate.ai API. It supports multiple aspect ratios, error handling, and progress tracking.

Author: SpiritAtlas Team
License: MIT
"""

import os
import sys
import time
import argparse
from pathlib import Path
from typing import Optional, Dict, Any, List
from datetime import datetime
import replicate
from replicate.exceptions import ReplicateError, ModelError


class ReplicateImageGenerator:
    """
    A comprehensive image generator using Replicate.ai API.
    Supports Flux1.dev (primary) and SDXL (fallback) models.
    """

    # Model identifiers
    FLUX_DEV = "black-forest-labs/flux-dev"
    FLUX_SCHNELL = "black-forest-labs/flux-schnell"
    SDXL = "stability-ai/sdxl:39ed52f2a78e934b3ba6e2a89f5b1c712de7dfea535525255b1aa35c5565e08b"

    # Aspect ratio presets (width x height)
    ASPECT_RATIOS = {
        "square": (1024, 1024),
        "portrait": (768, 1024),
        "landscape": (1024, 768),
        "wide": (1280, 720),
        "ultrawide": (1536, 640),
        "instagram": (1080, 1080),
        "story": (1080, 1920),
    }

    def __init__(self, api_token: Optional[str] = None, output_dir: str = "./generated_images"):
        """
        Initialize the image generator.

        Args:
            api_token: Replicate API token (if not set via environment variable)
            output_dir: Directory to save generated images
        """
        # Set API token
        if api_token:
            os.environ['REPLICATE_API_TOKEN'] = api_token
        elif not os.environ.get('REPLICATE_API_TOKEN'):
            raise ValueError(
                "Replicate API token not found. "
                "Set REPLICATE_API_TOKEN environment variable or pass api_token parameter."
            )

        # Create output directory
        self.output_dir = Path(output_dir)
        self.output_dir.mkdir(parents=True, exist_ok=True)

        print(f"✓ Replicate client initialized")
        print(f"✓ Output directory: {self.output_dir.absolute()}")

    def generate_image(
        self,
        prompt: str,
        model: str = "flux-dev",
        width: Optional[int] = None,
        height: Optional[int] = None,
        aspect_ratio: Optional[str] = None,
        num_inference_steps: int = 28,
        guidance_scale: float = 3.5,
        num_outputs: int = 1,
        output_format: str = "png",
        output_quality: int = 90,
        seed: Optional[int] = None,
        negative_prompt: Optional[str] = None,
        max_retries: int = 3,
        retry_delay: int = 5
    ) -> List[str]:
        """
        Generate image(s) using the specified model and parameters.

        Args:
            prompt: Text description of the image to generate
            model: Model to use ('flux-dev', 'flux-schnell', or 'sdxl')
            width: Image width (must be multiple of 32, 256-1440)
            height: Image height (must be multiple of 32, 256-1440)
            aspect_ratio: Preset aspect ratio (overrides width/height)
            num_inference_steps: Number of denoising steps (more = higher quality, slower)
            guidance_scale: How closely to follow the prompt (3.5 for Flux, 7.5 for SDXL)
            num_outputs: Number of images to generate
            output_format: Image format ('png', 'jpg', 'webp')
            output_quality: Output quality (1-100)
            seed: Random seed for reproducibility
            negative_prompt: Things to avoid in the image (SDXL only)
            max_retries: Maximum number of retry attempts on failure
            retry_delay: Delay between retries in seconds

        Returns:
            List of file paths to generated images
        """
        # Determine dimensions
        if aspect_ratio:
            if aspect_ratio not in self.ASPECT_RATIOS:
                raise ValueError(
                    f"Invalid aspect_ratio: {aspect_ratio}. "
                    f"Choose from: {', '.join(self.ASPECT_RATIOS.keys())}"
                )
            width, height = self.ASPECT_RATIOS[aspect_ratio]
            print(f"Using aspect ratio '{aspect_ratio}': {width}x{height}")
        elif not (width and height):
            # Default to square
            width, height = self.ASPECT_RATIOS["square"]
            print(f"Using default square aspect ratio: {width}x{height}")

        # Validate dimensions
        if width % 32 != 0 or height % 32 != 0:
            raise ValueError("Width and height must be multiples of 32")
        if not (256 <= width <= 1440) or not (256 <= height <= 1440):
            raise ValueError("Width and height must be between 256 and 1440")

        # Select model
        model_id = self._get_model_id(model)
        print(f"\n{'='*70}")
        print(f"Model: {model}")
        print(f"Prompt: {prompt}")
        print(f"Dimensions: {width}x{height}")
        print(f"Steps: {num_inference_steps}, Guidance: {guidance_scale}")
        print(f"{'='*70}\n")

        # Build input parameters
        input_params = self._build_input_params(
            model=model,
            prompt=prompt,
            width=width,
            height=height,
            num_inference_steps=num_inference_steps,
            guidance_scale=guidance_scale,
            num_outputs=num_outputs,
            output_format=output_format,
            output_quality=output_quality,
            seed=seed,
            negative_prompt=negative_prompt
        )

        # Generate images with retry logic
        for attempt in range(max_retries):
            try:
                print(f"🎨 Generating image... (attempt {attempt + 1}/{max_retries})")
                start_time = time.time()

                output = replicate.run(model_id, input=input_params)

                elapsed = time.time() - start_time
                print(f"✓ Generation completed in {elapsed:.2f}s")

                # Save images
                saved_paths = self._save_images(output, prompt, model, width, height)

                return saved_paths

            except ModelError as e:
                print(f"✗ Model error: {e}")
                if attempt < max_retries - 1:
                    print(f"Retrying in {retry_delay}s...")
                    time.sleep(retry_delay)
                else:
                    raise

            except ReplicateError as e:
                print(f"✗ Replicate API error: {e}")
                if attempt < max_retries - 1:
                    print(f"Retrying in {retry_delay}s...")
                    time.sleep(retry_delay)
                else:
                    raise

            except Exception as e:
                print(f"✗ Unexpected error: {e}")
                raise

        raise RuntimeError("Failed to generate image after maximum retries")

    def _get_model_id(self, model: str) -> str:
        """Get the full model identifier."""
        model_map = {
            "flux-dev": self.FLUX_DEV,
            "flux-schnell": self.FLUX_SCHNELL,
            "sdxl": self.SDXL
        }

        if model not in model_map:
            raise ValueError(
                f"Invalid model: {model}. Choose from: {', '.join(model_map.keys())}"
            )

        return model_map[model]

    def _build_input_params(
        self,
        model: str,
        prompt: str,
        width: int,
        height: int,
        num_inference_steps: int,
        guidance_scale: float,
        num_outputs: int,
        output_format: str,
        output_quality: int,
        seed: Optional[int],
        negative_prompt: Optional[str]
    ) -> Dict[str, Any]:
        """Build model-specific input parameters."""

        # Common parameters
        params = {
            "prompt": prompt,
            "width": width,
            "height": height,
            "num_inference_steps": num_inference_steps,
            "guidance_scale": guidance_scale,
            "output_format": output_format,
            "output_quality": output_quality,
        }

        # Add seed if specified
        if seed is not None:
            params["seed"] = seed

        # Model-specific parameters
        if model in ["flux-dev", "flux-schnell"]:
            # Flux models
            params["num_outputs"] = num_outputs
            # Flux doesn't use negative_prompt

        elif model == "sdxl":
            # SDXL model
            params["num_outputs"] = num_outputs
            if negative_prompt:
                params["negative_prompt"] = negative_prompt
            # SDXL-specific defaults
            params["scheduler"] = "K_EULER"
            params["refine"] = "expert_ensemble_refiner"
            params["high_noise_frac"] = 0.8

        return params

    def _save_images(
        self,
        output: Any,
        prompt: str,
        model: str,
        width: int,
        height: int
    ) -> List[str]:
        """Save generated images to disk."""

        saved_paths = []
        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")

        # Create safe filename from prompt
        safe_prompt = "".join(c if c.isalnum() or c in (' ', '-', '_') else '' for c in prompt)
        safe_prompt = safe_prompt.strip().replace(' ', '_')[:50]

        # Handle different output formats
        outputs = output if isinstance(output, list) else [output]

        for idx, file_output in enumerate(outputs):
            # Generate filename
            filename = f"{timestamp}_{model}_{width}x{height}_{safe_prompt}"
            if len(outputs) > 1:
                filename += f"_{idx+1}"

            # Determine extension from URL or default to png
            if hasattr(file_output, 'url'):
                ext = Path(file_output.url).suffix or '.png'
            else:
                ext = '.png'

            filepath = self.output_dir / f"{filename}{ext}"

            # Save the image
            try:
                with open(filepath, 'wb') as f:
                    if hasattr(file_output, 'read'):
                        f.write(file_output.read())
                    else:
                        # If it's a URL string
                        import urllib.request
                        with urllib.request.urlopen(str(file_output)) as response:
                            f.write(response.read())

                saved_paths.append(str(filepath))
                print(f"✓ Saved: {filepath}")

            except Exception as e:
                print(f"✗ Failed to save image {idx+1}: {e}")

        return saved_paths

    def generate_spiritual_image(
        self,
        concept: str,
        style: str = "ethereal cosmic",
        aspect_ratio: str = "square",
        **kwargs
    ) -> List[str]:
        """
        Convenience method for generating spiritual/cosmic images.

        Args:
            concept: The spiritual concept (e.g., "chakra alignment", "astral plane")
            style: Visual style (e.g., "ethereal cosmic", "sacred geometry", "mandala")
            aspect_ratio: Preset aspect ratio
            **kwargs: Additional parameters passed to generate_image()

        Returns:
            List of file paths to generated images
        """
        # Build spiritual prompt
        prompt = f"{concept}, {style} art, mystical, spiritual energy, vibrant colors, detailed, high quality"

        print(f"🔮 Generating spiritual image: {concept}")

        return self.generate_image(
            prompt=prompt,
            aspect_ratio=aspect_ratio,
            **kwargs
        )


def main():
    """Command-line interface for the image generator."""

    parser = argparse.ArgumentParser(
        description="Generate images using Replicate.ai API (Flux/SDXL models)",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  # Generate a spiritual image using Flux (default)
  python replicate_generator.py -p "cosmic meditation mandala, sacred geometry" -a square

  # Generate with specific dimensions
  python replicate_generator.py -p "astral plane visualization" -w 1024 -h 768

  # Generate using SDXL with negative prompt
  python replicate_generator.py -p "chakra energy flow" -m sdxl -n "blurry, low quality"

  # Generate multiple variations with a seed
  python replicate_generator.py -p "spiritual awakening" -c 4 --seed 42

  # Use spiritual preset
  python replicate_generator.py -s "chakra alignment" --style "sacred geometry" -a portrait
        """
    )

    # Required arguments
    prompt_group = parser.add_mutually_exclusive_group(required=True)
    prompt_group.add_argument('-p', '--prompt', help='Text prompt for image generation')
    prompt_group.add_argument('-s', '--spiritual', help='Spiritual concept (uses preset styling)')

    # Model selection
    parser.add_argument('-m', '--model', choices=['flux-dev', 'flux-schnell', 'sdxl'],
                       default='flux-dev', help='Model to use (default: flux-dev)')

    # Dimensions
    dimension_group = parser.add_mutually_exclusive_group()
    dimension_group.add_argument('-a', '--aspect-ratio',
                                choices=['square', 'portrait', 'landscape', 'wide', 'ultrawide', 'instagram', 'story'],
                                help='Preset aspect ratio')
    dimension_group.add_argument('--dimensions', nargs=2, type=int, metavar=('WIDTH', 'HEIGHT'),
                                help='Custom width and height')
    parser.add_argument('-w', '--width', type=int, help='Image width (multiple of 32, 256-1440)')
    parser.add_argument('-H', '--height', type=int, help='Image height (multiple of 32, 256-1440)')

    # Generation parameters
    parser.add_argument('--steps', type=int, default=28,
                       help='Number of inference steps (default: 28)')
    parser.add_argument('--guidance', type=float, default=3.5,
                       help='Guidance scale (default: 3.5 for Flux, 7.5 for SDXL)')
    parser.add_argument('-c', '--count', type=int, default=1,
                       help='Number of images to generate (default: 1)')
    parser.add_argument('--seed', type=int, help='Random seed for reproducibility')
    parser.add_argument('-n', '--negative', help='Negative prompt (SDXL only)')

    # Output options
    parser.add_argument('-o', '--output-dir', default='./generated_images',
                       help='Output directory (default: ./generated_images)')
    parser.add_argument('--format', choices=['png', 'jpg', 'webp'], default='png',
                       help='Output format (default: png)')
    parser.add_argument('--quality', type=int, default=90,
                       help='Output quality 1-100 (default: 90)')

    # Spiritual preset options
    parser.add_argument('--style', default='ethereal cosmic',
                       help='Visual style for spiritual images (default: ethereal cosmic)')

    # API configuration
    parser.add_argument('--api-token', help='Replicate API token (or set REPLICATE_API_TOKEN env var)')

    args = parser.parse_args()

    try:
        # Initialize generator
        generator = ReplicateImageGenerator(
            api_token=args.api_token,
            output_dir=args.output_dir
        )

        # Determine dimensions
        width = args.width
        height = args.height
        aspect_ratio = args.aspect_ratio

        if args.dimensions:
            width, height = args.dimensions
            aspect_ratio = None

        # Adjust guidance scale for SDXL if not explicitly set
        guidance_scale = args.guidance
        if args.model == 'sdxl' and args.guidance == 3.5:
            guidance_scale = 7.5

        # Generate images
        if args.spiritual:
            # Use spiritual preset
            saved_paths = generator.generate_spiritual_image(
                concept=args.spiritual,
                style=args.style,
                aspect_ratio=aspect_ratio or 'square',
                model=args.model,
                width=width,
                height=height,
                num_inference_steps=args.steps,
                guidance_scale=guidance_scale,
                num_outputs=args.count,
                output_format=args.format,
                output_quality=args.quality,
                seed=args.seed,
                negative_prompt=args.negative
            )
        else:
            # Use direct prompt
            saved_paths = generator.generate_image(
                prompt=args.prompt,
                model=args.model,
                width=width,
                height=height,
                aspect_ratio=aspect_ratio,
                num_inference_steps=args.steps,
                guidance_scale=guidance_scale,
                num_outputs=args.count,
                output_format=args.format,
                output_quality=args.quality,
                seed=args.seed,
                negative_prompt=args.negative
            )

        # Summary
        print(f"\n{'='*70}")
        print(f"✓ Successfully generated {len(saved_paths)} image(s)")
        print(f"{'='*70}")
        for path in saved_paths:
            print(f"  {path}")
        print()

    except KeyboardInterrupt:
        print("\n\nOperation cancelled by user.")
        sys.exit(1)
    except Exception as e:
        print(f"\n✗ Error: {e}", file=sys.stderr)
        sys.exit(1)


if __name__ == "__main__":
    main()
