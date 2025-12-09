#!/usr/bin/env python3
"""
SpiritAtlas Image Generation Cost Estimator
Calculate costs for generating spiritual asset library

Usage:
    python estimate_cost.py
    python estimate_cost.py --categories numerology astrology
    python estimate_cost.py --detailed
"""

import argparse
from typing import Dict, List

# Asset categories and counts
ASSET_CATEGORIES = {
    'numerology': {
        'count': 9,
        'description': 'Life Path Numbers 1-9',
        'assets': [
            'life_path_1', 'life_path_2', 'life_path_3',
            'life_path_4', 'life_path_5', 'life_path_6',
            'life_path_7', 'life_path_8', 'life_path_9'
        ]
    },
    'astrology': {
        'count': 12,
        'description': 'Zodiac Signs',
        'assets': [
            'aries', 'taurus', 'gemini', 'cancer',
            'leo', 'virgo', 'libra', 'scorpio',
            'sagittarius', 'capricorn', 'aquarius', 'pisces'
        ]
    },
    'chakras': {
        'count': 7,
        'description': '7 Chakra Centers',
        'assets': [
            'root_chakra', 'sacral_chakra', 'solar_plexus_chakra',
            'heart_chakra', 'throat_chakra', 'third_eye_chakra',
            'crown_chakra'
        ]
    },
    'elements': {
        'count': 5,
        'description': 'Five Elements',
        'assets': [
            'earth_element', 'water_element', 'fire_element',
            'air_element', 'ether_element'
        ]
    },
    'ayurveda': {
        'count': 3,
        'description': 'Dosha Types',
        'assets': ['vata_dosha', 'pitta_dosha', 'kapha_dosha']
    },
    'humandesign': {
        'count': 9,
        'description': 'HD Types & Profiles',
        'assets': [
            'manifestor', 'generator', 'manifesting_generator',
            'projector', 'reflector',
            'profile_1_3', 'profile_2_4', 'profile_3_5', 'profile_4_6'
        ]
    },
    'transits': {
        'count': 8,
        'description': 'Planetary Transits',
        'assets': [
            'sun_transit', 'moon_transit', 'mercury_transit',
            'venus_transit', 'mars_transit', 'jupiter_transit',
            'saturn_transit', 'pluto_transit'
        ]
    },
    'backgrounds': {
        'count': 6,
        'description': 'Cosmic Backgrounds',
        'assets': [
            'cosmic_void', 'nebula_pink', 'nebula_blue',
            'galaxy_spiral', 'star_field', 'aurora_mystical'
        ]
    }
}

# Provider pricing (as of Dec 2024)
PROVIDERS = {
    'fal': {
        'name': 'fal.ai (Flux Schnell)',
        'cost_per_image': 0.05,
        'avg_time_per_image': 2.5,  # seconds
        'quality': 'Excellent',
        'best_for': 'Speed, quality, abstract spiritual art',
        'free_tier': '$5 credit (100 images)'
    },
    'replicate': {
        'name': 'Replicate (SDXL)',
        'cost_per_image': 0.003,
        'avg_time_per_image': 10,  # seconds
        'quality': 'Very Good',
        'best_for': 'Budget, realistic styles',
        'free_tier': 'None (pay-per-use)'
    }
}

def calculate_category_cost(category_name: str, provider: str) -> Dict:
    """Calculate cost for a specific category"""
    category = ASSET_CATEGORIES[category_name]
    provider_info = PROVIDERS[provider]

    count = category['count']
    cost = count * provider_info['cost_per_image']
    time_seconds = count * provider_info['avg_time_per_image']
    time_minutes = time_seconds / 60

    return {
        'category': category_name,
        'description': category['description'],
        'count': count,
        'cost': cost,
        'time_seconds': time_seconds,
        'time_minutes': time_minutes,
        'assets': category['assets']
    }

def calculate_total_cost(categories: List[str], provider: str) -> Dict:
    """Calculate total cost for multiple categories"""
    total_count = 0
    total_cost = 0.0
    total_time = 0.0
    category_details = []

    for cat in categories:
        details = calculate_category_cost(cat, provider)
        total_count += details['count']
        total_cost += details['cost']
        total_time += details['time_minutes']
        category_details.append(details)

    return {
        'provider': provider,
        'total_count': total_count,
        'total_cost': total_cost,
        'total_time_minutes': total_time,
        'categories': category_details
    }

def format_time(minutes: float) -> str:
    """Format time duration"""
    if minutes < 1:
        return f"{int(minutes * 60)} seconds"
    elif minutes < 60:
        return f"{minutes:.1f} minutes"
    else:
        hours = int(minutes // 60)
        mins = int(minutes % 60)
        return f"{hours}h {mins}m"

def print_detailed_breakdown(categories: List[str]):
    """Print detailed cost breakdown by category"""
    print("\n" + "=" * 70)
    print("DETAILED ASSET BREAKDOWN")
    print("=" * 70)

    for cat_name in categories:
        category = ASSET_CATEGORIES[cat_name]
        print(f"\n{cat_name.upper()}: {category['description']}")
        print("-" * 70)
        for i, asset in enumerate(category['assets'], 1):
            print(f"  {i:2d}. {asset}")
        print(f"  Total: {category['count']} images")

def print_comparison(categories: List[str]):
    """Print cost comparison between providers"""
    print("\n" + "=" * 70)
    print("PROVIDER COMPARISON")
    print("=" * 70)

    total_images = sum(ASSET_CATEGORIES[cat]['count'] for cat in categories)

    print(f"\nGenerating {total_images} images:\n")

    for provider_key, provider_info in PROVIDERS.items():
        results = calculate_total_cost(categories, provider_key)

        print(f"{provider_info['name']}")
        print("-" * 70)
        print(f"  Cost per image:   ${provider_info['cost_per_image']:.3f}")
        print(f"  Total cost:       ${results['total_cost']:.2f}")
        print(f"  Avg generation:   {provider_info['avg_time_per_image']:.1f}s per image")
        print(f"  Total time:       {format_time(results['total_time_minutes'])}")
        print(f"  Quality:          {provider_info['quality']}")
        print(f"  Best for:         {provider_info['best_for']}")
        print(f"  Free tier:        {provider_info['free_tier']}")
        print()

def print_recommendation(categories: List[str]):
    """Print recommendation based on use case"""
    print("\n" + "=" * 70)
    print("RECOMMENDATIONS")
    print("=" * 70)

    fal_results = calculate_total_cost(categories, 'fal')
    rep_results = calculate_total_cost(categories, 'replicate')

    print("\n1. For DEVELOPMENT/TESTING:")
    print(f"   → Use fal.ai")
    print(f"   → Fast iterations ({format_time(fal_results['total_time_minutes'])})")
    print(f"   → Cost: ${fal_results['total_cost']:.2f}")

    print("\n2. For PRODUCTION (Premium Quality):")
    print(f"   → Use fal.ai (Flux Schnell or Flux Pro)")
    print(f"   → Excellent quality for spiritual/abstract art")
    print(f"   → Cost: ${fal_results['total_cost']:.2f} (Schnell) or ~${fal_results['total_cost'] * 5:.2f} (Pro)")

    print("\n3. For BUDGET-CONSCIOUS:")
    print(f"   → Use Replicate")
    print(f"   → 17x cheaper than fal.ai")
    print(f"   → Cost: ${rep_results['total_cost']:.2f}")
    print(f"   → Time: {format_time(rep_results['total_time_minutes'])}")

    print("\n4. For BEST RESULTS:")
    print(f"   → Generate with BOTH providers")
    print(f"   → Compare quality and choose best images")
    print(f"   → Total cost: ${fal_results['total_cost'] + rep_results['total_cost']:.2f}")

    # Free tier check
    if fal_results['total_cost'] <= 5.0:
        print("\n💡 TIP: fal.ai free tier ($5 credit) covers all assets!")

def main():
    parser = argparse.ArgumentParser(
        description='Estimate costs for SpiritAtlas image generation',
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  python estimate_cost.py                              # All categories
  python estimate_cost.py --categories numerology      # Single category
  python estimate_cost.py --categories numerology astrology chakras
  python estimate_cost.py --detailed                   # Show all asset names

Available categories:
  numerology    - 9 Life Path numbers
  astrology     - 12 Zodiac signs
  chakras       - 7 Chakra centers
  elements      - 5 Elements (Earth, Water, Fire, Air, Ether)
  ayurveda      - 3 Dosha types (Vata, Pitta, Kapha)
  humandesign   - 9 HD types and profiles
  transits      - 8 Planetary transits
  backgrounds   - 6 Cosmic backgrounds
        """
    )

    parser.add_argument('--categories', nargs='+', choices=list(ASSET_CATEGORIES.keys()),
                       help='Specific categories to estimate (default: all)')
    parser.add_argument('--detailed', action='store_true',
                       help='Show detailed breakdown of all assets')

    args = parser.parse_args()

    # Determine categories to estimate
    if args.categories:
        categories = args.categories
    else:
        categories = list(ASSET_CATEGORIES.keys())

    # Print header
    print("\n" + "=" * 70)
    print("SPIRITATLAS IMAGE GENERATION COST ESTIMATE")
    print("=" * 70)

    # Print summary
    total_images = sum(ASSET_CATEGORIES[cat]['count'] for cat in categories)
    print(f"\nTotal assets to generate: {total_images} images")
    print(f"Categories: {', '.join(categories)}")

    # Category breakdown
    print("\n" + "-" * 70)
    print("ASSET BREAKDOWN BY CATEGORY")
    print("-" * 70)
    for cat in categories:
        info = ASSET_CATEGORIES[cat]
        print(f"  {cat.capitalize():15s} {info['count']:2d} images  ({info['description']})")
    print("-" * 70)
    print(f"  {'TOTAL':15s} {total_images:2d} images")

    # Detailed breakdown if requested
    if args.detailed:
        print_detailed_breakdown(categories)

    # Cost comparison
    print_comparison(categories)

    # Recommendations
    print_recommendation(categories)

    print("\n" + "=" * 70)
    print("\nReady to generate? Run:")
    print("  python generate_images.py --provider fal")
    print("\nFor a test image:")
    print("  python quick_generate.py 'mystical spiritual art'")
    print("=" * 70 + "\n")

if __name__ == '__main__':
    main()
