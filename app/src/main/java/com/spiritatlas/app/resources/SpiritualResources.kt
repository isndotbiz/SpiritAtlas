package com.spiritatlas.app.resources

import androidx.annotation.DrawableRes
import com.spiritatlas.core.ui.R

/**
 * Central resource mapping for all spiritual images.
 * Maps semantic names to drawable resource IDs from the 99 generated FLUX images.
 */
object SpiritualResources {

    // ============================================================================
    // BRANDING & APP ICONS (Images 1-8)
    // ============================================================================

    object Branding {
        val primaryAppIcon: Int
            @DrawableRes get() = R.drawable.`img_001_primary_app_icon`
        val darkModeAppIcon: Int
            @DrawableRes get() = R.drawable.`img_002_alternative_app_icon_dark_mode`
        val splashBackground: Int
            @DrawableRes get() = R.drawable.`img_003_splash_screen_background`
        val wordmarkLogo: Int
            @DrawableRes get() = R.drawable.`img_004_wordmark_logo`
        val monogramIcon: Int
            @DrawableRes get() = R.drawable.`img_005_monogram_icon`
        val loadingSpinner: Int
            @DrawableRes get() = R.drawable.`img_006_loading_spinner_icon`
        val featureGraphic: Int
            @DrawableRes get() = R.drawable.`img_007_app_store_feature_graphic`
        val notificationIcon: Int
            @DrawableRes get() = R.drawable.`img_008_notification_icon`
    }

    // ============================================================================
    // BACKGROUNDS (Images 9-23)
    // ============================================================================

    @DrawableRes
    fun getBackgroundResource(screen: ScreenBackground): Int = when (screen) {
        ScreenBackground.HOME -> R.drawable.`img_009_home_screen_background`
        ScreenBackground.PROFILE_CREATION -> R.drawable.`img_010_profile_creation_background`
        ScreenBackground.COMPATIBILITY -> R.drawable.`img_011_compatibility_screen_background`
        ScreenBackground.SPLASH_ONBOARDING -> R.drawable.`img_012_splash_onboarding_background`
        ScreenBackground.SETTINGS -> R.drawable.`img_013_settings_screen_background`
        ScreenBackground.MEDITATION -> R.drawable.`img_014_meditation_chakra_screen_background`
        ScreenBackground.ASTROLOGY -> R.drawable.`img_015_astrology_chart_background`
        ScreenBackground.MOON_PHASE -> R.drawable.`img_016_moon_phase_tracking_background`
        ScreenBackground.NUMEROLOGY -> R.drawable.`img_017_numerology_screen_background`
        ScreenBackground.HUMAN_DESIGN -> R.drawable.`img_018_human_design_bodygraph_background`
        ScreenBackground.TANTRIC -> R.drawable.`img_019_tantric_intimacy_screen_background`
        ScreenBackground.LIBRARY -> R.drawable.`img_020_library_archive_background`
        ScreenBackground.EMPTY_STATE -> R.drawable.`img_021_empty_state_background`
        ScreenBackground.LOADING -> R.drawable.`img_022_loading_screen_background`
        ScreenBackground.SUCCESS -> R.drawable.`img_023_success_completion_background`
    }

    // ============================================================================
    // AVATARS (Images 24-33)
    // ============================================================================

    @DrawableRes
    fun getAvatarResource(avatarId: Int): Int = when (avatarId) {
        1 -> R.drawable.`img_024_default_avatar_cosmic_soul`
        2 -> R.drawable.`img_025_default_avatar_constellation_being`
        3 -> R.drawable.`img_026_default_avatar_chakra_system`
        4 -> R.drawable.`img_027_default_avatar_third_eye`
        5 -> R.drawable.`img_028_default_avatar_lotus_bloom`
        6 -> R.drawable.`img_029_default_avatar_om_symbol`
        7 -> R.drawable.`img_030_default_avatar_moon_phases`
        8 -> R.drawable.`img_031_default_avatar_yin_yang_cosmic`
        else -> R.drawable.`img_024_default_avatar_cosmic_soul`
    }

    @DrawableRes
    fun getAvatarFrameResource(frameId: Int): Int = when (frameId) {
        1 -> R.drawable.`img_032_avatar_frame_gold_sacred_ring`
        2 -> R.drawable.`img_033_avatar_frame_zodiac_wheel`
        else -> R.drawable.`img_032_avatar_frame_gold_sacred_ring`
    }

    // ============================================================================
    // ZODIAC SIGNS (Images 34-45)
    // ============================================================================

    @DrawableRes
    fun getZodiacResource(sign: String): Int = when (sign.uppercase()) {
        "ARIES" -> R.drawable.`img_034_aries_constellation_art`
        "TAURUS" -> R.drawable.`img_035_taurus_constellation_art`
        "GEMINI" -> R.drawable.`img_036_gemini_constellation_art`
        "CANCER" -> R.drawable.`img_037_cancer_constellation_art`
        "LEO" -> R.drawable.`img_038_leo_constellation_art`
        "VIRGO" -> R.drawable.`img_039_virgo_constellation_art`
        "LIBRA" -> R.drawable.`img_040_libra_constellation_art`
        "SCORPIO" -> R.drawable.`img_041_scorpio_constellation_art`
        "SAGITTARIUS" -> R.drawable.`img_042_sagittarius_constellation_art`
        "CAPRICORN" -> R.drawable.`img_043_capricorn_constellation_art`
        "AQUARIUS" -> R.drawable.`img_044_aquarius_constellation_art`
        "PISCES" -> R.drawable.`img_045_pisces_constellation_art`
        else -> R.drawable.`img_034_aries_constellation_art`
    }

    // ============================================================================
    // CHAKRAS (Images 46-52)
    // ============================================================================

    @DrawableRes
    fun getChakraResource(chakraNumber: Int): Int = when (chakraNumber) {
        1 -> R.drawable.`img_046_root_chakra_muladhara`
        2 -> R.drawable.`img_047_sacral_chakra_svadhisthana`
        3 -> R.drawable.`img_048_solar_plexus_chakra_manipura`
        4 -> R.drawable.`img_049_heart_chakra_anahata`
        5 -> R.drawable.`img_050_throat_chakra_vishuddha`
        6 -> R.drawable.`img_051_third_eye_chakra_ajna`
        7 -> R.drawable.`img_052_crown_chakra_sahasrara`
        else -> R.drawable.`img_046_root_chakra_muladhara`
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
    // MOON PHASES (Images 53-60)
    // ============================================================================

    @DrawableRes
    fun getMoonPhaseResource(phaseName: String): Int = when (phaseName.lowercase().replace(" ", "_")) {
        "new_moon", "new", "dark_renewal" -> R.drawable.`img_053_new_moon_dark_renewal`
        "waxing_crescent", "waxing crescent", "growing_light" -> R.drawable.`img_054_waxing_crescent_growing_light`
        "first_quarter", "first quarter", "decisive_moment" -> R.drawable.`img_055_first_quarter_decisive_moment`
        "waxing_gibbous", "waxing gibbous", "almost_complete" -> R.drawable.`img_056_waxing_gibbous_almost_complete`
        "full_moon", "full", "peak_illumination" -> R.drawable.`img_057_full_moon_peak_illumination`
        "waning_gibbous", "waning gibbous", "sharing_light" -> R.drawable.`img_058_waning_gibbous_sharing_light`
        "last_quarter", "last quarter", "release_point" -> R.drawable.`img_059_last_quarter_release_point`
        "waning_crescent", "waning crescent", "rest_and_reflection" -> R.drawable.`img_060_waning_crescent_rest_and_reflection`
        else -> R.drawable.`img_053_new_moon_dark_renewal`
    }

    // ============================================================================
    // ELEMENTS (Images 61-65)
    // ============================================================================

    @DrawableRes
    fun getElementResource(element: Element): Int = when (element) {
        Element.FIRE -> R.drawable.`img_061_fire_element_symbol`
        Element.WATER -> R.drawable.`img_062_water_element_symbol`
        Element.EARTH -> R.drawable.`img_063_earth_element_symbol`
        Element.AIR -> R.drawable.`img_064_air_element_symbol`
        Element.ETHER -> R.drawable.`img_065_ether_spirit_element_symbol`
    }

    // ============================================================================
    // SACRED GEOMETRY (Images 66-73)
    // ============================================================================

    @DrawableRes
    fun getGeometryResource(type: SacredGeometryType): Int = when (type) {
        SacredGeometryType.FLOWER_OF_LIFE -> R.drawable.`img_066_flower_of_life`
        SacredGeometryType.METATRONS_CUBE -> R.drawable.`img_067_metatrons_cube`
        SacredGeometryType.SRI_YANTRA -> R.drawable.`img_068_sri_yantra`
        SacredGeometryType.SEED_OF_LIFE -> R.drawable.`img_069_seed_of_life`
        SacredGeometryType.VESICA_PISCIS -> R.drawable.`img_070_vesica_piscis`
        SacredGeometryType.TORUS_FIELD -> R.drawable.`img_071_torus_field`
        SacredGeometryType.MERKABA -> R.drawable.`img_072_merkaba_star_tetrahedron`
        SacredGeometryType.GOLDEN_SPIRAL -> R.drawable.`img_073_golden_spiral_fibonacci`
    }

    // ============================================================================
    // UI ELEMENTS (Images 74-85)
    // ============================================================================

    object UIElements {
        val primaryButtonNormal: Int
            @DrawableRes get() = R.drawable.`img_074_primary_action_button_normal_state`
        val primaryButtonPressed: Int
            @DrawableRes get() = R.drawable.`img_075_primary_action_button_pressed_state`
        val secondaryButton: Int
            @DrawableRes get() = R.drawable.`img_076_secondary_button_outline_style`
        val cardGlass: Int
            @DrawableRes get() = R.drawable.`img_077_card_background_glassmorphic`
        val loadingCircular: Int
            @DrawableRes get() = R.drawable.`img_078_loading_state_circular_progress`
        val loadingLinear: Int
            @DrawableRes get() = R.drawable.`img_079_loading_state_linear_progress_bar`
        val emptyNoProfiles: Int
            @DrawableRes get() = R.drawable.`img_080_empty_state_illustration_no_profiles`
        val emptyNoResults: Int
            @DrawableRes get() = R.drawable.`img_081_empty_state_illustration_no_compatibility_result`
        val successIcon: Int
            @DrawableRes get() = R.drawable.`img_082_success_checkmark_icon`
        val errorIcon: Int
            @DrawableRes get() = R.drawable.`img_083_error_warning_icon`
        val infoIcon: Int
            @DrawableRes get() = R.drawable.`img_084_info_tooltip_icon`
        val dropdownIcon: Int
            @DrawableRes get() = R.drawable.`img_085_dropdown_chevron_icon`
    }

    // ============================================================================
    // SPIRITUAL SYMBOLS (Images 86-93)
    // ============================================================================

    @DrawableRes
    fun getSymbolResource(symbol: SpiritualSymbol): Int = when (symbol) {
        SpiritualSymbol.OM -> R.drawable.`img_086_om_aum_symbol`
        SpiritualSymbol.LOTUS -> R.drawable.`img_087_lotus_flower`
        SpiritualSymbol.HAMSA -> R.drawable.`img_088_hamsa_hand`
        SpiritualSymbol.TREE_OF_LIFE -> R.drawable.`img_089_tree_of_life`
        SpiritualSymbol.ANKH -> R.drawable.`img_090_ankh_symbol`
        SpiritualSymbol.YIN_YANG -> R.drawable.`img_091_yin_yang_symbol`
        SpiritualSymbol.MANDALA -> R.drawable.`img_092_mandala_spiritual_meditation`
        SpiritualSymbol.INFINITY -> R.drawable.`img_093_infinity_symbol_with_cosmic_enhancement`
    }

    // ============================================================================
    // ONBOARDING (Images 94-99)
    // ============================================================================

    @DrawableRes
    fun getOnboardingResource(step: Int): Int = when (step) {
        1 -> R.drawable.`img_094_welcome_screen_cosmic_discovery`
        2 -> R.drawable.`img_095_feature_1_profile_creation`
        3 -> R.drawable.`img_096_feature_2_spiritual_insights`
        4 -> R.drawable.`img_097_feature_3_compatibility_analysis`
        5 -> R.drawable.`img_098_feature_4_personalized_guidance`
        6 -> R.drawable.`img_099_getting_started_ready_to_begin`
        else -> R.drawable.`img_094_welcome_screen_cosmic_discovery`
    }
}

// ============================================================================
// ENUMS
// ============================================================================

enum class Element {
    FIRE, WATER, EARTH, AIR, ETHER
}

enum class ScreenBackground {
    HOME, SPLASH_ONBOARDING, PROFILE_CREATION, COMPATIBILITY, SETTINGS,
    MEDITATION, ASTROLOGY, MOON_PHASE, NUMEROLOGY, HUMAN_DESIGN,
    TANTRIC, LIBRARY, EMPTY_STATE, LOADING, SUCCESS
}

enum class SacredGeometryType {
    FLOWER_OF_LIFE, METATRONS_CUBE, SRI_YANTRA, SEED_OF_LIFE,
    VESICA_PISCIS, GOLDEN_SPIRAL, MERKABA, TORUS_FIELD
}

enum class SpiritualSymbol {
    OM, LOTUS, HAMSA, TREE_OF_LIFE, ANKH, YIN_YANG, MANDALA, INFINITY
}
