#!/usr/bin/env python3
"""
Quick test script for Replicate image generation.
Tests the setup and generates a sample image.
"""

import os
import sys

def test_installation():
    """Test that all required packages are installed."""
    print("Testing installation...")

    try:
        import replicate
        print("✓ replicate package installed")
    except ImportError:
        print("✗ replicate package not found")
        print("  Install with: pip install replicate")
        return False

    return True


def test_api_token():
    """Test that API token is configured."""
    print("\nTesting API token...")

    token = os.environ.get('REPLICATE_API_TOKEN')
    if not token:
        print("✗ REPLICATE_API_TOKEN not set")
        print("  Set with: export REPLICATE_API_TOKEN=your_token")
        return False

    # Check if token looks valid (starts with r8_)
    if not token.startswith('r8_'):
        print("⚠ Warning: Token doesn't start with 'r8_' - might be invalid")

    print(f"✓ API token found: {token[:10]}...")
    return True


def test_generation():
    """Test generating a simple image."""
    print("\nTesting image generation...")
    print("This will generate a small test image (costs ~$0.003)")

    try:
        from replicate_generator import ReplicateImageGenerator

        # Create output directory for test
        test_dir = "./test_outputs"
        os.makedirs(test_dir, exist_ok=True)

        # Initialize generator
        generator = ReplicateImageGenerator(output_dir=test_dir)

        # Generate a small test image with Flux Schnell (cheapest)
        print("\nGenerating test image with Flux Schnell...")
        paths = generator.generate_image(
            prompt="simple geometric mandala, minimal, test image",
            model="flux-schnell",
            width=512,
            height=512,
            num_inference_steps=4,
            guidance_scale=3.5
        )

        if paths:
            print(f"\n✓ Test image generated successfully!")
            print(f"  Saved to: {paths[0]}")
            return True
        else:
            print("✗ Generation failed - no output returned")
            return False

    except ImportError as e:
        print(f"✗ Import error: {e}")
        print("  Make sure replicate_generator.py is in the same directory")
        return False
    except Exception as e:
        print(f"✗ Generation error: {e}")
        return False


def main():
    """Run all tests."""
    print("=" * 70)
    print("SpiritAtlas Replicate.ai Setup Test")
    print("=" * 70)
    print()

    # Run tests
    installation_ok = test_installation()
    if not installation_ok:
        print("\n❌ Installation test failed. Please install required packages.")
        sys.exit(1)

    token_ok = test_api_token()
    if not token_ok:
        print("\n❌ API token test failed. Please set REPLICATE_API_TOKEN.")
        sys.exit(1)

    # Ask user before generating (costs money)
    print("\n" + "=" * 70)
    response = input("Run generation test? This will cost ~$0.003 (y/n): ")
    if response.lower() != 'y':
        print("\nSkipping generation test.")
        print("✓ Setup validation complete (without generation test)")
        sys.exit(0)

    generation_ok = test_generation()

    # Summary
    print("\n" + "=" * 70)
    if installation_ok and token_ok and generation_ok:
        print("✓ All tests passed! Setup is complete.")
        print("\nYou can now use:")
        print("  python replicate_generator.py -p 'your prompt here'")
    else:
        print("❌ Some tests failed. Please check the errors above.")
        sys.exit(1)
    print("=" * 70)


if __name__ == "__main__":
    main()
