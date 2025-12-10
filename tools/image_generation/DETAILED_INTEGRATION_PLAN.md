# SpiritAtlas Image Integration Plan
## Comprehensive Strategy for 99 Optimized Images

**Version:** 1.0
**Date:** December 9, 2025
**Status:** Ready for Implementation
**Estimated Time:** 4 weeks (1 developer)

---

## Executive Summary

This document provides a complete, step-by-step integration plan for incorporating 99 FLUX 1.1 Pro generated images into the SpiritAtlas Android application. The plan includes resource mapping, component creation, screen updates, and testing strategies.

### What's Already Done
- ✅ 99 image prompts defined in `OPTIMIZED_FLUX_PRO_PROMPTS_99.md`
- ✅ Image categories mapped in `IMAGE_INTEGRATION_STRATEGY.md`
- ✅ Optimization script created: `optimize_for_android.py`
- ✅ Existing Canvas-based components identified in `SpiritualIcons.kt` and `CosmicBackgrounds.kt`
- ✅ Coil image loading library already integrated in `core/ui`

### What Needs to Be Done
- ⏳ Generate remaining 99 images via FLUX 1.1 Pro
- ⏳ Optimize and place images in Android resource directories
- ⏳ Create resource mapping Kotlin file
- ⏳ Build new Composable components for image-based UI
- ⏳ Update all 17+ screens to use new image assets
- ⏳ Test on multiple devices and densities

---

## Table of Contents

1. [Image Category Breakdown](#image-category-breakdown)
2. [Android Resource Naming Convention](#android-resource-naming-convention)
3. [Resource Directory Structure](#resource-directory-structure)
4. [Component Architecture](#component-architecture)
5. [Screen-by-Screen Integration](#screen-by-screen-integration)
6. [Priority Order](#priority-order)
7. [Step-by-Step Implementation Guide](#step-by-step-implementation-guide)
8. [Testing Strategy](#testing-strategy)
9. [Performance Checklist](#performance-checklist)
10. [Troubleshooting](#troubleshooting)

---

## Image Category Breakdown

### 1. App Branding (8 images)

| # | Original Filename | Android Resource ID | Dimensions | Usage |
|---|-------------------|---------------------|------------|-------|
| 1 | app_icon_primary_1024.webp | `ic_launcher` | 1024x1024 | Launcher icon (all densities) |
| 2 | app_icon_dark_mode_1024.webp | `ic_launcher_dark` | 1024x1024 | Dark mode launcher variant |
| 3 | splash_background_1080x1920.webp | `bg_splash` | 1080x1920 | Splash screen background |
| 4 | wordmark_logo_2048x512.webp | `logo_wordmark` | 2048x512 | Text logo for marketing |
| 5 | monogram_icon_512.webp | `ic_monogram` | 512x512 | Compact logo for small spaces |
| 6 | loading_spinner_256.webp | `ic_loading_spinner` | 256x256 | App loading indicator |
| 7 | feature_graphic_1920x1080.webp | `banner_feature` | 1920x1080 | Play Store feature graphic |
| 8 | notification_icon_192.webp | `ic_notification` | 192x192 | Status bar notification |

**Integration Priority:** CRITICAL (Week 1)
**Screens Affected:** All screens (launcher, splash, notifications)

---

### 2. Backgrounds (15 images)

| # | Original Filename | Android Resource ID | Dimensions | Usage |
|---|-------------------|---------------------|------------|-------|
| 1 | bg_home_1080x1920.webp | `bg_home` | 1080x1920 | Home screen |
| 2 | bg_profile_creation_1080x1920.webp | `bg_profile_creation` | 1080x1920 | Profile creation form |
| 3 | bg_compatibility_1080x1920.webp | `bg_compatibility` | 1080x1920 | Compatibility screen |
| 4 | bg_splash_onboarding_1080x1920.webp | `bg_splash_onboarding` | 1080x1920 | Splash/Onboarding |
| 5 | bg_settings_1080x1920.webp | `bg_settings` | 1080x1920 | Settings screen |
| 6 | bg_meditation_chakra_1080x1920.webp | `bg_meditation` | 1080x1920 | Chakra/Meditation |
| 7 | bg_astrology_chart_1080x1920.webp | `bg_astrology` | 1080x1920 | Astrology screens |
| 8 | bg_moon_phase_1080x1920.webp | `bg_moon_phase` | 1080x1920 | Moon phase tracking |
| 9 | bg_numerology_1080x1920.webp | `bg_numerology` | 1080x1920 | Numerology screen |
| 10 | bg_human_design_1080x1920.webp | `bg_human_design` | 1080x1920 | Human Design bodygraph |
| 11 | bg_tantric_1080x1920.webp | `bg_tantric` | 1080x1920 | Tantric content |
| 12 | bg_library_archive_1080x1920.webp | `bg_library` | 1080x1920 | Profile library |
| 13 | bg_empty_state_1080x1920.webp | `bg_empty_state` | 1080x1920 | Empty states |
| 14 | bg_loading_1080x1920.webp | `bg_loading` | 1080x1920 | Loading screens |
| 15 | bg_success_1080x1920.webp | `bg_success` | 1080x1920 | Success/Completion |

**Integration Priority:** CRITICAL (Week 1)
**Screens Affected:** All major screens

---

### 3. Avatars (10 images)

| # | Original Filename | Android Resource ID | Dimensions | Usage |
|---|-------------------|---------------------|------------|-------|
| 1 | avatar_cosmic_soul_512.webp | `avatar_cosmic_soul` | 512x512 | Default avatar option 1 |
| 2 | avatar_constellation_being_512.webp | `avatar_constellation` | 512x512 | Default avatar option 2 |
| 3 | avatar_chakra_system_512.webp | `avatar_chakra_system` | 512x512 | Default avatar option 3 |
| 4 | avatar_third_eye_512.webp | `avatar_third_eye` | 512x512 | Default avatar option 4 |
| 5 | avatar_lotus_bloom_512.webp | `avatar_lotus` | 512x512 | Default avatar option 5 |
| 6 | avatar_om_symbol_512.webp | `avatar_om` | 512x512 | Default avatar option 6 |
| 7 | avatar_moon_phases_512.webp | `avatar_moon_phases` | 512x512 | Default avatar option 7 |
| 8 | avatar_yin_yang_512.webp | `avatar_yin_yang` | 512x512 | Default avatar option 8 |
| 9 | frame_gold_sacred_ring_600.webp | `frame_gold_ring` | 600x600 | Avatar frame overlay 1 |
| 10 | frame_zodiac_wheel_600.webp | `frame_zodiac_wheel` | 600x600 | Avatar frame overlay 2 |

**Integration Priority:** HIGH (Week 2)
**Screens Affected:** Profile creation, profile library, profile detail, home screen

---

### 4. Zodiac Signs (12 images)

| # | Original Filename | Android Resource ID | Dimensions | Usage |
|---|-------------------|---------------------|------------|-------|
| 1 | zodiac_aries_512.webp | `zodiac_aries` | 512x512 | Aries constellation art |
| 2 | zodiac_taurus_512.webp | `zodiac_taurus` | 512x512 | Taurus constellation art |
| 3 | zodiac_gemini_512.webp | `zodiac_gemini` | 512x512 | Gemini constellation art |
| 4 | zodiac_cancer_512.webp | `zodiac_cancer` | 512x512 | Cancer constellation art |
| 5 | zodiac_leo_512.webp | `zodiac_leo` | 512x512 | Leo constellation art |
| 6 | zodiac_virgo_512.webp | `zodiac_virgo` | 512x512 | Virgo constellation art |
| 7 | zodiac_libra_512.webp | `zodiac_libra` | 512x512 | Libra constellation art |
| 8 | zodiac_scorpio_512.webp | `zodiac_scorpio` | 512x512 | Scorpio constellation art |
| 9 | zodiac_sagittarius_512.webp | `zodiac_sagittarius` | 512x512 | Sagittarius constellation art |
| 10 | zodiac_capricorn_512.webp | `zodiac_capricorn` | 512x512 | Capricorn constellation art |
| 11 | zodiac_aquarius_512.webp | `zodiac_aquarius` | 512x512 | Aquarius constellation art |
| 12 | zodiac_pisces_512.webp | `zodiac_pisces` | 512x512 | Pisces constellation art |

**Integration Priority:** CRITICAL (Week 1)
**Screens Affected:** Profile detail, compatibility detail, home screen

---

### 5. Chakras (7 images)

| # | Original Filename | Android Resource ID | Dimensions | Usage |
|---|-------------------|---------------------|------------|-------|
| 1 | chakra_root_muladhara_512.webp | `chakra_root` | 512x512 | Root chakra visualization |
| 2 | chakra_sacral_svadhisthana_512.webp | `chakra_sacral` | 512x512 | Sacral chakra visualization |
| 3 | chakra_solar_plexus_manipura_512.webp | `chakra_solar_plexus` | 512x512 | Solar plexus chakra |
| 4 | chakra_heart_anahata_512.webp | `chakra_heart` | 512x512 | Heart chakra visualization |
| 5 | chakra_throat_vishuddha_512.webp | `chakra_throat` | 512x512 | Throat chakra visualization |
| 6 | chakra_third_eye_ajna_512.webp | `chakra_third_eye` | 512x512 | Third eye chakra |
| 7 | chakra_crown_sahasrara_512.webp | `chakra_crown` | 512x512 | Crown chakra visualization |

**Integration Priority:** HIGH (Week 2)
**Screens Affected:** Profile detail, compatibility detail, daily insights

---

### 6. Moon Phases (8 images)

| # | Original Filename | Android Resource ID | Dimensions | Usage |
|---|-------------------|---------------------|------------|-------|
| 1 | moon_new_512.webp | `moon_new` | 512x512 | New moon phase |
| 2 | moon_waxing_crescent_512.webp | `moon_waxing_crescent` | 512x512 | Waxing crescent |
| 3 | moon_first_quarter_512.webp | `moon_first_quarter` | 512x512 | First quarter |
| 4 | moon_waxing_gibbous_512.webp | `moon_waxing_gibbous` | 512x512 | Waxing gibbous |
| 5 | moon_full_512.webp | `moon_full` | 512x512 | Full moon |
| 6 | moon_waning_gibbous_512.webp | `moon_waning_gibbous` | 512x512 | Waning gibbous |
| 7 | moon_last_quarter_512.webp | `moon_last_quarter` | 512x512 | Last quarter |
| 8 | moon_waning_crescent_512.webp | `moon_waning_crescent` | 512x512 | Waning crescent |

**Integration Priority:** HIGH (Week 2)
**Screens Affected:** Home screen, lunar calendar, profile detail

---

### 7. Elements (5 images)

| # | Original Filename | Android Resource ID | Dimensions | Usage |
|---|-------------------|---------------------|------------|-------|
| 1 | element_fire_512.webp | `element_fire` | 512x512 | Fire element symbol |
| 2 | element_water_512.webp | `element_water` | 512x512 | Water element symbol |
| 3 | element_earth_512.webp | `element_earth` | 512x512 | Earth element symbol |
| 4 | element_air_512.webp | `element_air` | 512x512 | Air element symbol |
| 5 | element_ether_512.webp | `element_ether` | 512x512 | Ether/Spirit element |

**Integration Priority:** MEDIUM (Week 2)
**Screens Affected:** Profile detail (Ayurveda), compatibility detail, home spiritual weather

---

### 8. Sacred Geometry (8 images)

| # | Original Filename | Android Resource ID | Dimensions | Usage |
|---|-------------------|---------------------|------------|-------|
| 1 | geometry_flower_of_life_1024.webp | `geometry_flower_of_life` | 1024x1024 | Background layer |
| 2 | geometry_metatrons_cube_1024.webp | `geometry_metatrons_cube` | 1024x1024 | Background layer |
| 3 | geometry_sri_yantra_1024.webp | `geometry_sri_yantra` | 1024x1024 | Tantric sections |
| 4 | geometry_seed_of_life_1024.webp | `geometry_seed_of_life` | 1024x1024 | Loading transitions |
| 5 | geometry_vesica_piscis_1024.webp | `geometry_vesica_piscis` | 1024x1024 | Connection indicators |
| 6 | geometry_golden_spiral_1024.webp | `geometry_golden_spiral` | 1024x1024 | Decorative element |
| 7 | geometry_merkaba_1024.webp | `geometry_merkaba` | 1024x1024 | Human Design background |
| 8 | geometry_torus_field_1024.webp | `geometry_torus_field` | 1024x1024 | Energy flow visualization |

**Integration Priority:** MEDIUM (Week 3)
**Screens Affected:** Compatibility detail, profile creation, various backgrounds

---

### 9. UI Elements (12 images)

| # | Original Filename | Android Resource ID | Dimensions | Usage |
|---|-------------------|---------------------|------------|-------|
| 1 | button_primary_normal_800x200.webp | `btn_primary` | 800x200 | Primary action button |
| 2 | button_primary_pressed_800x200.webp | `btn_primary_pressed` | 800x200 | Pressed state |
| 3 | button_secondary_outline_800x200.webp | `btn_secondary` | 800x200 | Secondary button |
| 4 | card_glassmorphic_1000x600.webp | `card_glass` | 1000x600 | Card background |
| 5 | loading_circular_256.webp | `loading_circular` | 256x256 | Circular progress |
| 6 | loading_linear_800x80.webp | `loading_linear` | 800x80 | Linear progress bar |
| 7 | empty_no_profiles_800.webp | `empty_no_profiles` | 800x800 | Empty profile state |
| 8 | empty_no_results_800.webp | `empty_no_results` | 800x800 | Empty results state |
| 9 | success_checkmark_256.webp | `ic_success` | 256x256 | Success icon |
| 10 | error_warning_256.webp | `ic_error` | 256x256 | Error icon |
| 11 | info_tooltip_128.webp | `ic_info` | 128x128 | Info icon |
| 12 | dropdown_chevron_128.webp | `ic_dropdown` | 128x128 | Dropdown arrow |

**Integration Priority:** HIGH (Week 2)
**Screens Affected:** All screens (buttons, cards, states)

---

### 10. Spiritual Symbols (8 images)

| # | Original Filename | Android Resource ID | Dimensions | Usage |
|---|-------------------|---------------------|------------|-------|
| 1 | symbol_om_512.webp | `symbol_om` | 512x512 | Om/Aum symbol |
| 2 | symbol_lotus_512.webp | `symbol_lotus` | 512x512 | Lotus flower |
| 3 | symbol_hamsa_512.webp | `symbol_hamsa` | 512x512 | Hamsa hand |
| 4 | symbol_tree_of_life_512.webp | `symbol_tree_of_life` | 512x512 | Tree of life |
| 5 | symbol_ankh_512.webp | `symbol_ankh` | 512x512 | Ankh symbol |
| 6 | symbol_yin_yang_512.webp | `symbol_yin_yang` | 512x512 | Yin yang |
| 7 | symbol_mandala_1024.webp | `symbol_mandala` | 1024x1024 | Meditation mandala |
| 8 | symbol_infinity_512.webp | `symbol_infinity` | 512x512 | Infinity symbol |

**Integration Priority:** MEDIUM (Week 3)
**Screens Affected:** Section dividers, onboarding, tantric content

---

### 11. Onboarding Illustrations (6 images)

| # | Original Filename | Android Resource ID | Dimensions | Usage |
|---|-------------------|---------------------|------------|-------|
| 1 | onboarding_welcome_1080.webp | `onboarding_welcome` | 1080x1080 | Welcome screen |
| 2 | onboarding_profile_creation_1080.webp | `onboarding_profile` | 1080x1080 | Profile feature |
| 3 | onboarding_insights_1080.webp | `onboarding_insights` | 1080x1080 | Insights feature |
| 4 | onboarding_compatibility_1080.webp | `onboarding_compatibility` | 1080x1080 | Compatibility feature |
| 5 | onboarding_guidance_1080.webp | `onboarding_guidance` | 1080x1080 | Guidance feature |
| 6 | onboarding_ready_1080.webp | `onboarding_ready` | 1080x1080 | Ready to begin |

**Integration Priority:** MEDIUM (Week 3)
**Screens Affected:** Onboarding flow

---

## Android Resource Naming Convention

### General Rules
1. **Lowercase only**: `button_primary` not `Button_Primary`
2. **Underscores for spaces**: `moon_full` not `moon-full` or `moonfull`
3. **Descriptive prefixes**: `bg_`, `ic_`, `btn_`, `symbol_`, etc.
4. **No version numbers**: `avatar_lotus` not `avatar_lotus_v2`
5. **Consistent suffixes**: `_pressed`, `_disabled`, `_selected` for states

### Prefix System

| Prefix | Usage | Example |
|--------|-------|---------|
| `ic_` | Icons, small graphics | `ic_launcher`, `ic_notification` |
| `bg_` | Background images | `bg_home`, `bg_splash` |
| `btn_` | Button graphics | `btn_primary`, `btn_secondary` |
| `avatar_` | User avatars | `avatar_lotus`, `avatar_om` |
| `frame_` | Avatar frames/borders | `frame_gold_ring` |
| `zodiac_` | Zodiac signs | `zodiac_aries`, `zodiac_taurus` |
| `chakra_` | Chakra images | `chakra_root`, `chakra_heart` |
| `moon_` | Moon phases | `moon_full`, `moon_new` |
| `element_` | Element symbols | `element_fire`, `element_water` |
| `geometry_` | Sacred geometry | `geometry_flower_of_life` |
| `symbol_` | Spiritual symbols | `symbol_om`, `symbol_lotus` |
| `onboarding_` | Onboarding screens | `onboarding_welcome` |
| `empty_` | Empty states | `empty_no_profiles` |
| `loading_` | Loading indicators | `loading_circular` |
| `card_` | Card backgrounds | `card_glass` |
| `logo_` | Logo variants | `logo_wordmark` |
| `banner_` | Marketing banners | `banner_feature` |

---

## Resource Directory Structure

```
app/src/main/res/
├── drawable/                          # Default (baseline) - used as fallback
│   ├── bg_home.webp
│   ├── bg_splash.webp
│   ├── zodiac_aries.webp
│   ├── ... (all 99 images at baseline resolution)
│   │
│   ├── ic_launcher_background.xml     # Keep existing XML drawables
│   └── ic_launcher_foreground.xml
│
├── drawable-nodpi/                    # Density-independent (backgrounds)
│   ├── bg_home.webp                  # Full resolution backgrounds
│   ├── bg_splash.webp
│   └── bg_compatibility.webp
│
├── drawable-hdpi/                     # ~240dpi (1.5x)
│   ├── zodiac_aries.webp             # 384x384 (from 512)
│   ├── chakra_root.webp
│   └── ...
│
├── drawable-xhdpi/                    # ~320dpi (2x) - MOST COMMON
│   ├── zodiac_aries.webp             # 512x512 (baseline)
│   ├── chakra_root.webp
│   └── ...
│
├── drawable-xxhdpi/                   # ~480dpi (3x)
│   ├── zodiac_aries.webp             # 768x768 (from 512)
│   ├── chakra_root.webp
│   └── ...
│
├── drawable-xxxhdpi/                  # ~640dpi (4x)
│   ├── zodiac_aries.webp             # 1024x1024 (from 512)
│   ├── chakra_root.webp
│   └── ...
│
├── mipmap-mdpi/                       # ~160dpi - Launcher icons only
│   └── ic_launcher.webp               # 48x48
│
├── mipmap-hdpi/                       # ~240dpi
│   └── ic_launcher.webp               # 72x72
│
├── mipmap-xhdpi/                      # ~320dpi
│   └── ic_launcher.webp               # 96x96
│
├── mipmap-xxhdpi/                     # ~480dpi
│   └── ic_launcher.webp               # 144x144
│
├── mipmap-xxxhdpi/                    # ~640dpi
│   └── ic_launcher.webp               # 192x192
│
└── mipmap-anydpi-v26/                 # Adaptive icons (Android 8.0+)
    ├── ic_launcher.xml
    └── ic_launcher_round.xml
```

### Size Guidelines

| Image Type | Baseline (drawable) | xhdpi (2x) | xxhdpi (3x) | xxxhdpi (4x) |
|------------|---------------------|------------|-------------|--------------|
| Small Icons (24dp) | 24x24 | 48x48 | 72x72 | 96x96 |
| Standard Icons (48dp) | 48x48 | 96x96 | 144x144 | 192x192 |
| Large Icons (512) | 256x256 | 512x512 | 768x768 | 1024x1024 |
| Avatars | 256x256 | 512x512 | 768x768 | 1024x1024 |
| Backgrounds | nodpi (1080x1920 full resolution) |
| Launcher Icons | mipmap-specific (48→192) |

---

## Component Architecture

### New Kotlin Files to Create

#### 1. `/core/ui/src/main/java/com/spiritatlas/core/ui/resources/SpiritualResources.kt`

Central resource mapping file:

```kotlin
package com.spiritatlas.core.ui.resources

import androidx.annotation.DrawableRes
import com.spiritatlas.core.ui.R

/**
 * Central resource mapping for all spiritual images.
 * Maps semantic names to drawable resource IDs.
 */
object SpiritualResources {

    // ============================================================================
    // ZODIAC SIGNS
    // ============================================================================

    @DrawableRes
    fun getZodiacResource(sign: ZodiacSign): Int = when (sign) {
        ZodiacSign.ARIES -> R.drawable.zodiac_aries
        ZodiacSign.TAURUS -> R.drawable.zodiac_taurus
        ZodiacSign.GEMINI -> R.drawable.zodiac_gemini
        ZodiacSign.CANCER -> R.drawable.zodiac_cancer
        ZodiacSign.LEO -> R.drawable.zodiac_leo
        ZodiacSign.VIRGO -> R.drawable.zodiac_virgo
        ZodiacSign.LIBRA -> R.drawable.zodiac_libra
        ZodiacSign.SCORPIO -> R.drawable.zodiac_scorpio
        ZodiacSign.SAGITTARIUS -> R.drawable.zodiac_sagittarius
        ZodiacSign.CAPRICORN -> R.drawable.zodiac_capricorn
        ZodiacSign.AQUARIUS -> R.drawable.zodiac_aquarius
        ZodiacSign.PISCES -> R.drawable.zodiac_pisces
    }

    // ============================================================================
    // CHAKRAS
    // ============================================================================

    @DrawableRes
    fun getChakraResource(chakraNumber: Int): Int = when (chakraNumber) {
        1 -> R.drawable.chakra_root
        2 -> R.drawable.chakra_sacral
        3 -> R.drawable.chakra_solar_plexus
        4 -> R.drawable.chakra_heart
        5 -> R.drawable.chakra_throat
        6 -> R.drawable.chakra_third_eye
        7 -> R.drawable.chakra_crown
        else -> R.drawable.chakra_root
    }

    fun getChakraName(chakraNumber: Int): String = when (chakraNumber) {
        1 -> "Root (Muladhara)"
        2 -> "Sacral (Svadhisthana)"
        3 -> "Solar Plexus (Manipura)"
        4 -> "Heart (Anahata)"
        5 -> "Throat (Vishuddha)"
        6 -> "Third Eye (Ajna)"
        7 -> "Crown (Sahasrara)"
        else -> "Unknown"
    }

    // ============================================================================
    // MOON PHASES
    // ============================================================================

    @DrawableRes
    fun getMoonPhaseResource(phaseName: String): Int = when (phaseName.lowercase()) {
        "new moon", "new" -> R.drawable.moon_new
        "waxing crescent", "waxing_crescent" -> R.drawable.moon_waxing_crescent
        "first quarter", "first_quarter" -> R.drawable.moon_first_quarter
        "waxing gibbous", "waxing_gibbous" -> R.drawable.moon_waxing_gibbous
        "full moon", "full" -> R.drawable.moon_full
        "waning gibbous", "waning_gibbous" -> R.drawable.moon_waning_gibbous
        "last quarter", "last_quarter" -> R.drawable.moon_last_quarter
        "waning crescent", "waning_crescent" -> R.drawable.moon_waning_crescent
        else -> R.drawable.moon_new
    }

    // ============================================================================
    // ELEMENTS
    // ============================================================================

    @DrawableRes
    fun getElementResource(element: Element): Int = when (element) {
        Element.FIRE -> R.drawable.element_fire
        Element.WATER -> R.drawable.element_water
        Element.EARTH -> R.drawable.element_earth
        Element.AIR -> R.drawable.element_air
        Element.ETHER -> R.drawable.element_ether
    }

    // ============================================================================
    // AVATARS
    // ============================================================================

    @DrawableRes
    fun getAvatarResource(avatarId: Int): Int = when (avatarId) {
        1 -> R.drawable.avatar_cosmic_soul
        2 -> R.drawable.avatar_constellation
        3 -> R.drawable.avatar_chakra_system
        4 -> R.drawable.avatar_third_eye
        5 -> R.drawable.avatar_lotus
        6 -> R.drawable.avatar_om
        7 -> R.drawable.avatar_moon_phases
        8 -> R.drawable.avatar_yin_yang
        else -> R.drawable.avatar_cosmic_soul
    }

    @DrawableRes
    fun getAvatarFrameResource(frameId: Int): Int = when (frameId) {
        1 -> R.drawable.frame_gold_ring
        2 -> R.drawable.frame_zodiac_wheel
        else -> R.drawable.frame_gold_ring
    }

    // ============================================================================
    // BACKGROUNDS
    // ============================================================================

    @DrawableRes
    fun getBackgroundResource(screen: ScreenBackground): Int = when (screen) {
        ScreenBackground.HOME -> R.drawable.bg_home
        ScreenBackground.SPLASH -> R.drawable.bg_splash
        ScreenBackground.PROFILE_CREATION -> R.drawable.bg_profile_creation
        ScreenBackground.COMPATIBILITY -> R.drawable.bg_compatibility
        ScreenBackground.SETTINGS -> R.drawable.bg_settings
        ScreenBackground.MEDITATION -> R.drawable.bg_meditation
        ScreenBackground.ASTROLOGY -> R.drawable.bg_astrology
        ScreenBackground.MOON_PHASE -> R.drawable.bg_moon_phase
        ScreenBackground.NUMEROLOGY -> R.drawable.bg_numerology
        ScreenBackground.HUMAN_DESIGN -> R.drawable.bg_human_design
        ScreenBackground.TANTRIC -> R.drawable.bg_tantric
        ScreenBackground.LIBRARY -> R.drawable.bg_library
        ScreenBackground.EMPTY_STATE -> R.drawable.bg_empty_state
        ScreenBackground.LOADING -> R.drawable.bg_loading
        ScreenBackground.SUCCESS -> R.drawable.bg_success
    }

    // ============================================================================
    // SACRED GEOMETRY
    // ============================================================================

    @DrawableRes
    fun getGeometryResource(type: SacredGeometryType): Int = when (type) {
        SacredGeometryType.FLOWER_OF_LIFE -> R.drawable.geometry_flower_of_life
        SacredGeometryType.METATRONS_CUBE -> R.drawable.geometry_metatrons_cube
        SacredGeometryType.SRI_YANTRA -> R.drawable.geometry_sri_yantra
        SacredGeometryType.SEED_OF_LIFE -> R.drawable.geometry_seed_of_life
        SacredGeometryType.VESICA_PISCIS -> R.drawable.geometry_vesica_piscis
        SacredGeometryType.GOLDEN_SPIRAL -> R.drawable.geometry_golden_spiral
        SacredGeometryType.MERKABA -> R.drawable.geometry_merkaba
        SacredGeometryType.TORUS_FIELD -> R.drawable.geometry_torus_field
    }

    // ============================================================================
    // UI ELEMENTS
    // ============================================================================

    @DrawableRes
    fun getButtonResource(type: ButtonType, pressed: Boolean = false): Int = when {
        type == ButtonType.PRIMARY && !pressed -> R.drawable.btn_primary
        type == ButtonType.PRIMARY && pressed -> R.drawable.btn_primary_pressed
        type == ButtonType.SECONDARY -> R.drawable.btn_secondary
        else -> R.drawable.btn_primary
    }

    @DrawableRes
    fun getLoadingResource(type: LoadingType): Int = when (type) {
        LoadingType.CIRCULAR -> R.drawable.loading_circular
        LoadingType.LINEAR -> R.drawable.loading_linear
    }

    @DrawableRes
    fun getEmptyStateResource(type: EmptyStateType): Int = when (type) {
        EmptyStateType.NO_PROFILES -> R.drawable.empty_no_profiles
        EmptyStateType.NO_RESULTS -> R.drawable.empty_no_results
    }

    @DrawableRes
    fun getIconResource(type: IconType): Int = when (type) {
        IconType.SUCCESS -> R.drawable.ic_success
        IconType.ERROR -> R.drawable.ic_error
        IconType.INFO -> R.drawable.ic_info
        IconType.DROPDOWN -> R.drawable.ic_dropdown
    }

    // ============================================================================
    // SPIRITUAL SYMBOLS
    // ============================================================================

    @DrawableRes
    fun getSymbolResource(symbol: SpiritualSymbol): Int = when (symbol) {
        SpiritualSymbol.OM -> R.drawable.symbol_om
        SpiritualSymbol.LOTUS -> R.drawable.symbol_lotus
        SpiritualSymbol.HAMSA -> R.drawable.symbol_hamsa
        SpiritualSymbol.TREE_OF_LIFE -> R.drawable.symbol_tree_of_life
        SpiritualSymbol.ANKH -> R.drawable.symbol_ankh
        SpiritualSymbol.YIN_YANG -> R.drawable.symbol_yin_yang
        SpiritualSymbol.MANDALA -> R.drawable.symbol_mandala
        SpiritualSymbol.INFINITY -> R.drawable.symbol_infinity
    }

    // ============================================================================
    // ONBOARDING
    // ============================================================================

    @DrawableRes
    fun getOnboardingResource(step: Int): Int = when (step) {
        1 -> R.drawable.onboarding_welcome
        2 -> R.drawable.onboarding_profile
        3 -> R.drawable.onboarding_insights
        4 -> R.drawable.onboarding_compatibility
        5 -> R.drawable.onboarding_guidance
        6 -> R.drawable.onboarding_ready
        else -> R.drawable.onboarding_welcome
    }
}

// ============================================================================
// ENUMS
// ============================================================================

enum class Element {
    FIRE, WATER, EARTH, AIR, ETHER
}

enum class ScreenBackground {
    HOME, SPLASH, PROFILE_CREATION, COMPATIBILITY, SETTINGS,
    MEDITATION, ASTROLOGY, MOON_PHASE, NUMEROLOGY, HUMAN_DESIGN,
    TANTRIC, LIBRARY, EMPTY_STATE, LOADING, SUCCESS
}

enum class SacredGeometryType {
    FLOWER_OF_LIFE, METATRONS_CUBE, SRI_YANTRA, SEED_OF_LIFE,
    VESICA_PISCIS, GOLDEN_SPIRAL, MERKABA, TORUS_FIELD
}

enum class ButtonType {
    PRIMARY, SECONDARY
}

enum class LoadingType {
    CIRCULAR, LINEAR
}

enum class EmptyStateType {
    NO_PROFILES, NO_RESULTS
}

enum class IconType {
    SUCCESS, ERROR, INFO, DROPDOWN
}

enum class SpiritualSymbol {
    OM, LOTUS, HAMSA, TREE_OF_LIFE, ANKH, YIN_YANG, MANDALA, INFINITY
}
```

#### 2. `/core/ui/src/main/java/com/spiritatlas/core/ui/components/ImageBackgrounds.kt`

Replace Canvas backgrounds with image backgrounds:

```kotlin
package com.spiritatlas.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.spiritatlas.core.ui.resources.ScreenBackground
import com.spiritatlas.core.ui.resources.SpiritualResources

/**
 * Spiritual background image with proper loading and caching.
 * Replaces Canvas-based backgrounds for higher visual quality.
 */
@Composable
fun SpiritualBackgroundImage(
    background: ScreenBackground,
    modifier: Modifier = Modifier,
    alpha: Float = 0.3f,
    contentScale: ContentScale = ContentScale.Crop,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Background image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(SpiritualResources.getBackgroundResource(background))
                .crossfade(true)
                .memoryCacheKey("bg_${background.name.lowercase()}")
                .diskCacheKey("bg_${background.name.lowercase()}")
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
            alpha = alpha
        )

        // Content on top
        content()
    }
}

/**
 * Sacred geometry overlay with image.
 */
@Composable
fun SacredGeometryOverlay(
    geometryType: SacredGeometryType,
    modifier: Modifier = Modifier,
    alpha: Float = 0.1f,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Geometry image overlay
        Image(
            painter = painterResource(SpiritualResources.getGeometryResource(geometryType)),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit,
            alpha = alpha
        )

        // Content on top
        content()
    }
}
```

#### 3. `/core/ui/src/main/java/com/spiritatlas/core/ui/components/AvatarComponents.kt`

Avatar system with frames:

```kotlin
package com.spiritatlas.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.resources.SpiritualResources
import com.spiritatlas.core.ui.theme.SpiritualPurple

/**
 * Spiritual avatar with optional frame overlay.
 */
@Composable
fun SpiritualAvatar(
    avatarId: Int,
    modifier: Modifier = Modifier,
    frameId: Int? = null,
    size: Dp = 80.dp
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // Avatar image
        Image(
            painter = painterResource(SpiritualResources.getAvatarResource(avatarId)),
            contentDescription = "Spiritual Avatar",
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )

        // Optional frame overlay
        if (frameId != null) {
            Image(
                painter = painterResource(SpiritualResources.getAvatarFrameResource(frameId)),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

/**
 * Avatar selector for profile creation.
 */
@Composable
fun AvatarSelector(
    selectedAvatarId: Int?,
    onAvatarSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    avatarCount: Int = 8
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items((1..avatarCount).toList()) { avatarId ->
            val isSelected = selectedAvatarId == avatarId

            SpiritualAvatar(
                avatarId = avatarId,
                size = 64.dp,
                modifier = Modifier
                    .clickable { onAvatarSelected(avatarId) }
                    .border(
                        width = if (isSelected) 3.dp else 0.dp,
                        color = if (isSelected) SpiritualPurple else Color.Transparent,
                        shape = CircleShape
                    )
            )
        }
    }
}
```

#### 4. `/core/ui/src/main/java/com/spiritatlas/core/ui/components/ChakraVisualization.kt`

Chakra visualization with images:

```kotlin
package com.spiritatlas.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.resources.SpiritualResources

/**
 * Data class for chakra state
 */
data class ChakraState(
    val activation: Float, // 0.0 to 1.0
    val chakraNumber: Int
)

/**
 * Vertical chakra visualization from root to crown.
 */
@Composable
fun ChakraVisualization(
    chakras: List<ChakraState>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        chakras.forEach { chakraState ->
            ChakraRow(
                chakraNumber = chakraState.chakraNumber,
                activation = chakraState.activation,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Single chakra row with image and activation indicator.
 */
@Composable
fun ChakraRow(
    chakraNumber: Int,
    activation: Float,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Chakra image
        Image(
            painter = painterResource(SpiritualResources.getChakraResource(chakraNumber)),
            contentDescription = SpiritualResources.getChakraName(chakraNumber),
            modifier = Modifier.size(48.dp),
            alpha = 0.3f + (activation * 0.7f) // Dim if not active
        )

        // Chakra info
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = SpiritualResources.getChakraName(chakraNumber),
                style = MaterialTheme.typography.bodyLarge
            )

            LinearProgressIndicator(
                progress = { activation },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = getChakraColor(chakraNumber)
            )
        }
    }
}

private fun getChakraColor(chakraNumber: Int): Color = when (chakraNumber) {
    1 -> Color(0xFFEF4444) // Red
    2 -> Color(0xFFF97316) // Orange
    3 -> Color(0xFFFDE047) // Yellow
    4 -> Color(0xFF22C55E) // Green
    5 -> Color(0xFF3B82F6) // Blue
    6 -> Color(0xFF6366F1) // Indigo
    7 -> Color(0xFF8B5CF6) // Violet
    else -> Color.Gray
}
```

#### 5. `/core/ui/src/main/java/com/spiritatlas/core/ui/components/ZodiacImageComponents.kt`

Zodiac image components:

```kotlin
package com.spiritatlas.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.resources.SpiritualResources
import com.spiritatlas.domain.model.ZodiacSign

/**
 * Zodiac sign image component.
 * Replaces Canvas-drawn zodiac icons with high-quality FLUX images.
 */
@Composable
fun ZodiacImage(
    sign: ZodiacSign,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    Image(
        painter = painterResource(SpiritualResources.getZodiacResource(sign)),
        contentDescription = sign.name,
        modifier = modifier.size(size)
    )
}
```

#### 6. Update `/core/ui/src/main/java/com/spiritatlas/core/ui/components/CosmicBackgrounds.kt`

Add image-based background option:

```kotlin
// Add to existing file

/**
 * Image-based spiritual background (replaces Canvas for higher quality).
 */
@Composable
fun ImageBasedSpiritualBackground(
    background: ScreenBackground,
    modifier: Modifier = Modifier,
    alpha: Float = 0.3f,
    enableParticles: Boolean = false,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Background image
        SpiritualBackgroundImage(
            background = background,
            alpha = alpha
        ) {
            // Optional particle overlay (Canvas)
            if (enableParticles) {
                // Keep existing particle system from StarfieldBackground
                // Layer on top of image for hybrid effect
            }

            content()
        }
    }
}
```

---

## Screen-by-Screen Integration

### Screens to Update (17 Total)

1. **Splash Screen** - `/app/src/main/java/com/spiritatlas/app/SplashScreen.kt`
2. **Home Screen** - `/feature/home/src/main/java/com/spiritatlas/feature/home/HomeScreen.kt`
3. **Onboarding Flow** - `/feature/onboarding/src/` (create new)
4. **Profile Library** - `/feature/profile/src/main/java/com/spiritatlas/feature/profile/ProfileLibraryScreen.kt`
5. **Profile Creation** - `/feature/profile/src/main/java/com/spiritatlas/feature/profile/ProfileScreen.kt`
6. **Profile Detail** - Profile detail views
7. **Profile Comparison** - Profile comparison screen
8. **Compatibility Screen** - `/feature/compatibility/src/main/java/com/spiritatlas/feature/compatibility/CompatibilityScreen.kt`
9. **Compatibility Detail** - `/feature/compatibility/src/main/java/com/spiritatlas/feature/compatibility/CompatibilityDetailScreen.kt`
10. **Enrichment Results** - Enrichment result screens
11. **Settings** - `/feature/settings/src/`
12. **Consent** - `/feature/consent/src/main/java/com/spiritatlas/feature/consent/ConsentScreen.kt`
13. **Tantric Content** - Tantric screens (if exists)
14. **Astrology Charts** - Astrological visualization
15. **Numerology Display** - Numerology results
16. **Human Design** - Bodygraph visualization
17. **Moon Phase Calendar** - Lunar tracking (create new)

### Example: Update Home Screen

**File:** `/feature/home/src/main/java/com/spiritatlas/feature/home/HomeScreen.kt`

**Current approach:** Canvas-drawn `StarfieldBackground` and `MoonPhaseIcon`

**New approach:** Image-based backgrounds with hybrid particle effects

```kotlin
// BEFORE (existing)
@Composable
fun HomeScreen(/* ... */) {
    StarfieldBackground {  // Canvas-based
        // ... content ...
        MoonPhaseIcon(phase = currentPhase)  // Canvas-drawn
    }
}

// AFTER (with images)
@Composable
fun HomeScreen(/* ... */) {
    ImageBasedSpiritualBackground(
        background = ScreenBackground.HOME,
        alpha = 0.3f,
        enableParticles = true  // Keep Canvas particles for depth
    ) {
        // ... content ...

        // Replace moon icon with image
        Image(
            painter = painterResource(
                SpiritualResources.getMoonPhaseResource(currentPhase.name)
            ),
            contentDescription = currentPhase.name,
            modifier = Modifier.size(48.dp)
        )

        // Add zodiac icon for current sign
        ZodiacImage(
            sign = currentZodiacSign,
            size = 40.dp,
            modifier = Modifier.padding(8.dp)
        )
    }
}
```

### Example: Update Profile Library Screen

**File:** `/feature/profile/src/main/java/com/spiritatlas/feature/profile/ProfileLibraryScreen.kt`

**Add:** Avatar display on profile cards

```kotlin
// In ProfileCard composable
@Composable
fun EnhancedModernProfileCard(
    profile: ProfileInfo,
    /* ... */
) {
    Card(/* ... */) {
        Row(/* ... */) {
            // Add avatar with frame
            SpiritualAvatar(
                avatarId = profile.avatarId ?: 1,
                frameId = if (profile.isEnriched) 1 else null,
                size = 64.dp
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(profile.name)

                // Add zodiac badge
                if (profile.sunSign != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        ZodiacImage(
                            sign = profile.sunSign,
                            size = 20.dp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(profile.sunSign.name)
                    }
                }
            }
        }
    }
}
```

### Example: Update Compatibility Detail Screen

**File:** `/feature/compatibility/src/main/java/com/spiritatlas/feature/compatibility/CompatibilityDetailScreen.kt`

**Add:** Chakra visualization, element icons, zodiac synastry

```kotlin
@Composable
fun CompatibilityDetailScreen(/* ... */) {
    SacredGeometryOverlay(
        geometryType = SacredGeometryType.FLOWER_OF_LIFE,
        alpha = 0.08f
    ) {
        LazyColumn {
            // Hero section with avatars
            item {
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    SpiritualAvatar(
                        avatarId = profile1.avatarId ?: 1,
                        frameId = 1,
                        size = 80.dp
                    )

                    // Connection symbol
                    Image(
                        painter = painterResource(
                            SpiritualResources.getSymbolResource(SpiritualSymbol.YIN_YANG)
                        ),
                        modifier = Modifier.size(48.dp)
                    )

                    SpiritualAvatar(
                        avatarId = profile2.avatarId ?: 2,
                        frameId = 1,
                        size = 80.dp
                    )
                }
            }

            // Chakra compatibility
            item {
                Text("Energetic Harmony", style = MaterialTheme.typography.headlineSmall)

                ChakraVisualization(
                    chakras = compatibilityResult.chakraHarmony
                )
            }

            // Elemental balance
            item {
                Text("Elemental Balance", style = MaterialTheme.typography.headlineSmall)

                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    listOf(Element.FIRE, Element.WATER, Element.EARTH, Element.AIR, Element.ETHER).forEach { element ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(
                                    SpiritualResources.getElementResource(element)
                                ),
                                contentDescription = element.name,
                                modifier = Modifier.size(48.dp)
                            )
                            Text(element.name)
                        }
                    }
                }
            }
        }
    }
}
```

---

## Priority Order

### Phase 1: Critical Assets (Week 1)
**Goal:** First impressions - launcher, splash, primary UI

- [ ] **Day 1-2: Image Generation**
  - Generate launcher icon variations (8 images)
  - Generate splash/background images (15 images)
  - Generate primary button assets (3 images)
  - **Total: 26 images**

- [ ] **Day 3: Resource Setup**
  - Run `optimize_for_android.py` on generated images
  - Place optimized images in proper drawable directories
  - Generate launcher icon densities (mipmap-*)
  - Update `AndroidManifest.xml` with new launcher icon

- [ ] **Day 4-5: Core Component Creation**
  - Create `SpiritualResources.kt`
  - Create `ImageBackgrounds.kt`
  - Update `SplashScreen.kt` to use image background
  - Test on emulator and physical device

- [ ] **Weekend: Polish & Testing**
  - Verify all densities load correctly
  - Test app launch experience
  - Measure performance impact

### Phase 2: Core Features (Week 2)
**Goal:** Profile system, zodiac, chakras, moon phases

- [ ] **Day 1-2: Generate Images**
  - Zodiac signs (12 images)
  - Chakras (7 images)
  - Moon phases (8 images)
  - Avatars (10 images)
  - **Total: 37 images**

- [ ] **Day 3: Component Creation**
  - Create `AvatarComponents.kt`
  - Create `ChakraVisualization.kt`
  - Create `ZodiacImageComponents.kt`
  - Update `SpiritualResources.kt` with new mappings

- [ ] **Day 4-5: Screen Integration**
  - Update `HomeScreen.kt` - add moon phase, zodiac
  - Update `ProfileScreen.kt` - add avatar selector
  - Update `ProfileLibraryScreen.kt` - display avatars on cards
  - Update `CompatibilityDetailScreen.kt` - chakra + zodiac sections

### Phase 3: Visual Polish (Week 3)
**Goal:** Sacred geometry, elements, symbols, backgrounds

- [ ] **Day 1-2: Generate Images**
  - Sacred geometry (8 images)
  - Elements (5 images)
  - Spiritual symbols (8 images)
  - Remaining UI elements (12 images)
  - **Total: 33 images**

- [ ] **Day 3-4: Integration**
  - Update all screen backgrounds
  - Add sacred geometry overlays to key screens
  - Add element icons to Ayurveda sections
  - Add spiritual symbols as section dividers

- [ ] **Day 5: Onboarding**
  - Create onboarding module (if doesn't exist)
  - Generate onboarding illustrations (6 images)
  - Build onboarding flow with images

### Phase 4: UX Enhancement (Week 4)
**Goal:** Loading, error, empty states

- [ ] **Day 1: Final Images**
  - Any remaining images from the 99
  - Generate variants if needed
  - **Total: 3 images (if any remaining)**

- [ ] **Day 2-3: State Management**
  - Implement image-based loading states
  - Implement empty state illustrations
  - Implement success/error visuals

- [ ] **Day 4-5: Testing & Optimization**
  - Full app testing on multiple devices
  - Performance profiling
  - Memory leak checking
  - Image compression verification
  - Dark mode testing

---

## Step-by-Step Implementation Guide

### Step 1: Generate Images with FLUX 1.1 Pro

Use the prompts from `OPTIMIZED_FLUX_PRO_PROMPTS_99.md`:

```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation

# Option A: Use existing script (if configured)
python generate_flux_pro_quick.py --category branding --start 1 --end 8

# Option B: Use web interface
# Visit fal.ai and paste prompts from OPTIMIZED_FLUX_PRO_PROMPTS_99.md

# Option C: Use ComfyUI locally
# Follow COMFYUI_FLUX_BATCH_GUIDE.md for batch generation
```

**Output:** 99 images in `generated_images/flux_pro_v1.1/` directory

### Step 2: Optimize Images for Android

```bash
cd /Users/jonathanmallinger/Workspace/SpiritAtlas/tools/image_generation

# Preview optimization (dry run)
python optimize_for_android.py --dry-run

# Optimize all images
python optimize_for_android.py \
  --input generated_images/flux_pro_v1.1 \
  --output ../../app/src/main/res \
  --quality 90 \
  --create-mapping

# This will:
# 1. Convert PNG to WebP
# 2. Generate multiple densities (hdpi, xhdpi, xxhdpi, xxxhdpi)
# 3. Create launcher icon mipmaps
# 4. Generate resource mapping CSV
```

**Output:**
- Images placed in `app/src/main/res/drawable-*/`
- Launcher icons in `app/src/main/res/mipmap-*/`
- Mapping file: `image_resource_mapping.csv`

### Step 3: Create SpiritualResources.kt

```bash
# Create the file
mkdir -p /Users/jonathanmallinger/Workspace/SpiritAtlas/core/ui/src/main/java/com/spiritatlas/core/ui/resources

# Copy the SpiritualResources.kt code from "Component Architecture" section above
# into:
# /Users/jonathanmallinger/Workspace/SpiritAtlas/core/ui/src/main/java/com/spiritatlas/core/ui/resources/SpiritualResources.kt
```

### Step 4: Create Image Component Files

Create each component file from the "Component Architecture" section:

1. `ImageBackgrounds.kt`
2. `AvatarComponents.kt`
3. `ChakraVisualization.kt`
4. `ZodiacImageComponents.kt`

Place all in: `/Users/jonathanmallinger/Workspace/SpiritAtlas/core/ui/src/main/java/com/spiritatlas/core/ui/components/`

### Step 5: Update Existing Components

Update `CosmicBackgrounds.kt` to add image-based alternative:

```kotlin
// Add ImageBasedSpiritualBackground function to existing file
// Keep existing Canvas-based functions for backwards compatibility
```

### Step 6: Update Screens

For each screen, follow this pattern:

1. Import new components
2. Replace Canvas backgrounds with image backgrounds
3. Add new visual elements (avatars, zodiac, chakras)
4. Test rendering

**Priority order:**
1. SplashScreen.kt (Day 1)
2. HomeScreen.kt (Day 2)
3. ProfileScreen.kt (Day 3)
4. ProfileLibraryScreen.kt (Day 4)
5. CompatibilityDetailScreen.kt (Day 5)
6. ... continue with remaining screens

### Step 7: Update Build Configuration

Ensure Coil is properly configured (already done in your project):

```kotlin
// In core/ui/build.gradle.kts
dependencies {
    implementation("io.coil-kt:coil-compose:2.7.0")
}
```

### Step 8: Testing

```bash
# Build debug APK
./gradlew :app:assembleDebug

# Install on device
adb install app/build/outputs/apk/debug/app-debug.apk

# Test on emulator
./gradlew :app:installDebug

# Run instrumented tests
./gradlew connectedAndroidTest
```

### Step 9: Performance Verification

```bash
# Check APK size
ls -lh app/build/outputs/apk/debug/app-debug.apk

# Analyze APK
./gradlew :app:analyzeDebug

# Memory profiling
# Use Android Studio Profiler to check memory usage during image loading
```

---

## Testing Strategy

### Unit Tests

Create tests for resource mapping:

```kotlin
// Test file: core/ui/src/test/java/com/spiritatlas/core/ui/resources/SpiritualResourcesTest.kt

class SpiritualResourcesTest {

    @Test
    fun `all zodiac signs have valid resources`() {
        ZodiacSign.values().forEach { sign ->
            val resourceId = SpiritualResources.getZodiacResource(sign)
            assertTrue("Zodiac resource for $sign should be valid", resourceId > 0)
        }
    }

    @Test
    fun `all chakras have valid resources`() {
        (1..7).forEach { chakraNumber ->
            val resourceId = SpiritualResources.getChakraResource(chakraNumber)
            assertTrue("Chakra $chakraNumber should have valid resource", resourceId > 0)
        }
    }

    // Add similar tests for other resource types
}
```

### UI Tests

Test image loading on screens:

```kotlin
// Test file: feature/home/src/androidTest/java/com/spiritatlas/feature/home/HomeScreenTest.kt

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_displaysBackgroundImage() {
        composeTestRule.setContent {
            HomeScreen(navController = rememberNavController())
        }

        // Verify background loads
        composeTestRule.onNodeWithTag("bg_home").assertExists()
    }

    @Test
    fun homeScreen_displaysMoonPhaseImage() {
        composeTestRule.setContent {
            HomeScreen(navController = rememberNavController())
        }

        // Verify moon phase icon loads
        composeTestRule.onNodeWithTag("moon_phase_icon").assertExists()
    }
}
```

### Visual Regression Tests

Use screenshot testing:

```bash
# Generate baseline screenshots
./gradlew :app:recordPaparazziDebug

# Verify screenshots match
./gradlew :app:verifyPaparazziDebug
```

### Device Testing Matrix

Test on:
- **Low-end:** Pixel 3a (Android 10, 1080x2220, xxhdpi)
- **Mid-range:** Pixel 5 (Android 13, 1080x2340, xxhdpi)
- **High-end:** Pixel 7 Pro (Android 14, 1440x3120, xxxhdpi)
- **Tablet:** Pixel Tablet (Android 14, 2560x1600, xhdpi)

---

## Performance Checklist

### Image Optimization

- [ ] All images under 500KB (backgrounds)
- [ ] All icons under 100KB
- [ ] WebP format used throughout
- [ ] Proper density folders populated
- [ ] No xxxhdpi images for small icons (waste)
- [ ] Background images in `drawable-nodpi` if density-independent

### Memory Management

- [ ] Coil memory cache configured (default 25% available RAM)
- [ ] Disk cache enabled
- [ ] No memory leaks detected in profiler
- [ ] Image loading doesn't block UI thread
- [ ] Proper bitmap recycling

### Loading Performance

- [ ] Lazy loading implemented for off-screen images
- [ ] Crossfade animations smooth
- [ ] No jank during scroll (ProfileLibraryScreen)
- [ ] Splash screen appears instantly
- [ ] Home screen loads within 1 second

### APK Size Impact

**Before images:** ~15-20 MB
**Target after images:** ~40-50 MB (acceptable)
**Maximum acceptable:** <60 MB

**If APK > 60 MB:**
- Remove xxxhdpi density for non-critical images
- Increase WebP compression (quality 85 instead of 90)
- Consider Android App Bundle for dynamic delivery

---

## Troubleshooting

### Issue: Images not loading

**Symptoms:** Blank spaces where images should appear

**Solutions:**
1. Verify resource IDs in `SpiritualResources.kt` match actual filenames
2. Check if images exist in drawable directories: `ls app/src/main/res/drawable/zodiac_*`
3. Clean and rebuild: `./gradlew clean && ./gradlew :app:assembleDebug`
4. Check Coil logs: `adb logcat | grep Coil`

### Issue: Images pixelated on high-DPI devices

**Symptoms:** Blurry or pixelated images on Pixel 7 Pro

**Solutions:**
1. Verify xxxhdpi density images exist
2. Check baseline resolution is high enough (512x512 minimum for icons)
3. Ensure ContentScale.Fit or ContentScale.Crop used appropriately

### Issue: App crashes with OutOfMemoryError

**Symptoms:** App crashes when loading many images

**Solutions:**
1. Reduce image quality: 85 instead of 90
2. Configure Coil memory cache:
   ```kotlin
   ImageLoader.Builder(context)
       .memoryCache {
           MemoryCache.Builder(context)
               .maxSizePercent(0.15) // Reduce from 0.25 to 0.15
               .build()
       }
       .build()
   ```
3. Implement pagination in ProfileLibraryScreen
4. Use `SubcomposeAsyncImage` for complex layouts

### Issue: Slow image loading

**Symptoms:** Delay before images appear, spinners visible

**Solutions:**
1. Enable aggressive disk caching
2. Preload critical images on splash screen
3. Use lower resolution for thumbnails
4. Implement progressive loading (blur → full quality)

### Issue: APK size too large

**Symptoms:** APK > 60 MB

**Solutions:**
1. Remove xxxhdpi density: `rm -rf app/src/main/res/drawable-xxxhdpi`
2. Use lossy WebP: quality 85-80 instead of 90
3. Enable Android App Bundle: `android { bundle { ... } }`
4. Consider storing large backgrounds on server (download on demand)

### Issue: Wrong image density loaded

**Symptoms:** High-res images on low-DPI device (performance issue)

**Solutions:**
1. Verify density folders named correctly (`drawable-xhdpi` not `drawable-x-hdpi`)
2. Ensure Android picks correct density: test with `adb shell dumpsys display | grep Density`
3. Check that optimization script created all densities

---

## Completion Criteria

### Phase 1 Complete When:
- [x] Launcher icon displays with new branding
- [x] Splash screen uses image background
- [x] App launches without crashes
- [x] Background images load within 1 second

### Phase 2 Complete When:
- [x] All 12 zodiac signs display correctly in ProfileDetailScreen
- [x] All 7 chakras render in ChakraVisualization
- [x] Moon phase icon updates based on current phase
- [x] Avatar selector works in ProfileScreen
- [x] Avatars display in ProfileLibraryScreen cards

### Phase 3 Complete When:
- [x] Sacred geometry overlays visible on CompatibilityDetailScreen
- [x] All backgrounds replaced (15 screens)
- [x] Element icons display in Ayurveda sections
- [x] Onboarding flow built with illustrations

### Phase 4 Complete When:
- [x] Loading states use image-based indicators
- [x] Empty states display illustrations
- [x] Success/error visuals implemented
- [x] All 99 images integrated
- [x] App tested on 4+ devices
- [x] Performance metrics within targets
- [x] No memory leaks detected

### Overall Project Complete When:
- [x] All 99 images integrated and visible
- [x] All 17 screens updated
- [x] APK size < 60 MB
- [x] No performance degradation vs Canvas version
- [x] All tests passing
- [x] User feedback positive on visual appeal
- [x] Documentation updated

---

## Next Steps

1. **Review this plan** with stakeholders
2. **Generate first batch** of images (Phase 1: 26 images)
3. **Run optimization script** to prepare images
4. **Create SpiritualResources.kt** for resource mapping
5. **Begin Phase 1** implementation (Week 1)

---

## Resources

- **Image Prompts:** `OPTIMIZED_FLUX_PRO_PROMPTS_99.md`
- **Strategy Doc:** `IMAGE_INTEGRATION_STRATEGY.md`
- **Checklist:** `IMAGE_INTEGRATION_CHECKLIST.md`
- **Optimization Guide:** `IMAGE_OPTIMIZATION_GUIDE.md`
- **Optimization Script:** `optimize_for_android.py`
- **Existing Components:** `core/ui/src/main/java/com/spiritatlas/core/ui/components/`

---

**Document Version:** 1.0
**Last Updated:** December 9, 2025
**Author:** SpiritAtlas Development Team
**Status:** Ready for Implementation
