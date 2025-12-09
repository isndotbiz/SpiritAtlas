#!/usr/bin/env python3
"""
Example usage of FalImageGenerator for SpiritAtlas

This demonstrates how to use the generator in your Python scripts.
"""

from fal_generator import FalImageGenerator

# Your fal.ai API key
API_KEY = "0c6099f4-b6d7-4677-9062-b828e5155e89:171bfb7b414e56079497806b6a29cf32"


def example_simple_generation():
    """Simple example: Generate a single image."""
    print("=" * 70)
    print("EXAMPLE 1: Simple Generation")
    print("=" * 70)

    generator = FalImageGenerator(api_key=API_KEY)

    result = generator.generate(
        prompt="cosmic spiral galaxy with sacred geometry patterns",
        model=FalImageGenerator.MODEL_FLUX_DEV,
        image_size="landscape_4_3"
    )

    saved_files = generator.download_images(result)
    print(f"\n✓ Saved to: {saved_files[0]}")


def example_multiple_images():
    """Generate multiple variations with a seed."""
    print("\n" + "=" * 70)
    print("EXAMPLE 2: Multiple Variations")
    print("=" * 70)

    generator = FalImageGenerator(api_key=API_KEY)

    result = generator.generate(
        prompt="mystical chakra system glowing with energy, spiritual art",
        model=FalImageGenerator.MODEL_FLUX_DEV,
        image_size="square",
        num_images=4,
        seed=12345
    )

    generator.print_result_info(result)

    saved_files = generator.download_images(
        result,
        output_dir="./example_outputs",
        prefix="chakra"
    )

    print(f"\n✓ Generated {len(saved_files)} images:")
    for filepath in saved_files:
        print(f"  - {filepath}")


def example_custom_quality():
    """High-quality generation with custom settings."""
    print("\n" + "=" * 70)
    print("EXAMPLE 3: High-Quality Custom Settings")
    print("=" * 70)

    generator = FalImageGenerator(api_key=API_KEY)

    result = generator.generate(
        prompt="sacred geometry mandala with golden ratio, intricate patterns, spiritual art",
        model=FalImageGenerator.MODEL_FLUX_DEV,
        width=1920,
        height=1080,
        num_images=1,
        num_inference_steps=40,
        guidance_scale=4.5,
        seed=42,
        output_format="png"
    )

    generator.print_result_info(result)

    saved_files = generator.download_images(
        result,
        output_dir="./example_outputs",
        prefix="mandala_hd"
    )

    print(f"\n✓ High-quality image saved: {saved_files[0]}")


def example_fast_iteration():
    """Fast generation using Flux-schnell."""
    print("\n" + "=" * 70)
    print("EXAMPLE 4: Fast Generation (Flux-schnell)")
    print("=" * 70)

    generator = FalImageGenerator(api_key=API_KEY)

    result = generator.generate(
        prompt="spiritual moon phases in night sky",
        model=FalImageGenerator.MODEL_FLUX_SCHNELL,
        image_size="square",
        num_images=1
    )

    saved_files = generator.download_images(
        result,
        output_dir="./example_outputs",
        prefix="moon"
    )

    print(f"\n✓ Fast generation complete: {saved_files[0]}")


def example_batch_processing():
    """Generate multiple different images."""
    print("\n" + "=" * 70)
    print("EXAMPLE 5: Batch Processing")
    print("=" * 70)

    generator = FalImageGenerator(api_key=API_KEY)

    # Prompts for different spiritual elements
    prompts = {
        "life_path_1": "Number 1 in golden sacred geometry, leadership energy, spiritual art",
        "life_path_2": "Number 2 in sacred geometry, partnership harmony, spiritual art",
        "life_path_3": "Number 3 in sacred geometry, creative expression, spiritual art",
    }

    all_saved_files = []

    for name, prompt in prompts.items():
        print(f"\nGenerating: {name}")

        result = generator.generate(
            prompt=prompt,
            model=FalImageGenerator.MODEL_FLUX_DEV,
            image_size="square",
            num_images=1,
            seed=42,
            show_logs=False  # Quieter output for batch
        )

        saved_files = generator.download_images(
            result,
            output_dir="./example_outputs/numerology",
            prefix=name
        )

        all_saved_files.extend(saved_files)
        print(f"  ✓ {saved_files[0]}")

    print(f"\n✓ Batch complete! Generated {len(all_saved_files)} images")


def example_url_only():
    """Generate and get URLs without downloading."""
    print("\n" + "=" * 70)
    print("EXAMPLE 6: Get URLs Without Downloading")
    print("=" * 70)

    generator = FalImageGenerator(api_key=API_KEY)

    result = generator.generate(
        prompt="cosmic background with nebula and stars",
        model=FalImageGenerator.MODEL_FLUX_DEV,
        image_size="landscape_16_9"
    )

    # Print info but don't download
    images = result.data.get("images", [])
    print(f"\nGenerated {len(images)} image(s):")
    for idx, img in enumerate(images):
        print(f"  Image {idx + 1}:")
        print(f"    URL: {img.get('url')}")
        print(f"    Size: {img.get('width')}x{img.get('height')}")


if __name__ == "__main__":
    """
    Run examples one at a time by uncommenting the desired function.

    NOTE: Each example makes API calls and incurs costs (~$0.025 per image).
    """

    # Uncomment the example you want to run:

    # example_simple_generation()
    # example_multiple_images()
    # example_custom_quality()
    # example_fast_iteration()
    # example_batch_processing()
    # example_url_only()

    print("\n" + "=" * 70)
    print("EXAMPLES COMPLETE")
    print("=" * 70)
    print("\nTo run examples, uncomment the function calls at the bottom of this file.")
    print("Each example demonstrates different features of FalImageGenerator.")
    print("\nAvailable examples:")
    print("  1. example_simple_generation()    - Basic usage")
    print("  2. example_multiple_images()      - Generate variations")
    print("  3. example_custom_quality()       - High-quality settings")
    print("  4. example_fast_iteration()       - Fast generation")
    print("  5. example_batch_processing()     - Multiple images")
    print("  6. example_url_only()            - URLs without download")
    print()
