package com.spiritatlas.domain.model

import java.time.LocalDateTime

/**
 * Comprehensive spiritual profile with 36 fields for maximum compatibility insights.
 * Supports multiple profiles for relationship matching and comparison.
 * Minimum requirement: Only 3 fields needed for Tier 0 (300 words)
 * Up to 36 fields for Tier 3 Master analysis (2700 words) ✨
 */
data class UserProfile(
    val id: String, // Unique profile identifier
    val profileName: String, // Display name for this profile (e.g., "John's Profile", "Sarah", etc.)
    val createdAt: LocalDateTime, // When this profile was created
    val lastModified: LocalDateTime, // When this profile was last updated
    
    // === CORE IDENTITY (Required - Pick 3 minimum) ===
    val name: String?, // Full birth name for numerology
    val displayName: String?, // Preferred/current name
    val birthDateTime: LocalDateTime?, // Complete birth date & time
    val birthPlace: BirthPlace?, // Birth location for astrology
    
    // === ADDITIONAL NAMES (Numerology Enhancement) ===
    val middleName: String? = null, // Middle name(s)
    val nickname: String? = null, // Common nickname for personality insights
    val spiritualName: String? = null, // Chosen spiritual/sacred name
    val maidenName: String? = null, // Birth surname if different
    
    // === FAMILY & ANCESTRY (Deep Karmic Insights) ===
    val motherName: String? = null, // Mother's full name
    val fatherName: String? = null, // Father's full name
    val ancestry: String? = null, // Cultural/ethnic background
    val familyTradition: String? = null, // Spiritual/religious tradition
    
    // === PHYSICAL & ENERGETIC ===
    val gender: Gender? = null, // For energy calculations
    val bloodType: BloodType? = null, // Eastern spiritual systems
    val dominantHand: Hand? = null, // Energy flow patterns
    val eyeColor: String? = null, // Soul color connections
    val height: Double? = null, // Physical manifestation (in cm)
    val birthWeight: Double? = null, // Life force indicators (in kg)
    
    // === TIMING & CYCLES ===
    val firstBreath: LocalDateTime? = null, // Exact moment of life
    val conceptionDate: LocalDateTime? = null, // Prenatal influences
    val firstCry: LocalDateTime? = null, // Voice activation
    
    // === ENVIRONMENTAL ===
    val weatherConditions: String? = null, // Birth day weather
    val moonPhase: String? = null, // Lunar influences
    val hospitalName: String? = null, // Place energy signature
    val seasonalEnergy: String? = null, // Seasonal birth energy
    
    // === LIFE PATTERNS ===
    val firstWord: String? = null, // Communication patterns
    val firstSteps: LocalDateTime? = null, // Movement/progress indicators
    
    // === COMPATIBILITY & RELATIONSHIP FIELDS ===
    val loveLanguage: LoveLanguage? = null, // Primary love language
    val personalityType: PersonalityType? = null, // MBTI or similar
    val attachmentStyle: AttachmentStyle? = null, // Relationship patterns
    val sexualEnergy: SexualEnergy? = null, // Tantric energy type
    val communicationStyle: CommunicationStyle? = null, // How they express
    val conflictResolution: ConflictStyle? = null, // How they handle disputes
    val intimacyPreference: IntimacyStyle? = null, // Physical/emotional intimacy
    val spiritualConnection: SpiritualConnection? = null, // Sacred partnership approach
    val lifePurposeAlignment: String? = null, // What drives them
    
    // === PREFERENCES & SETTINGS ===
    val preferences: UserPreferences = UserPreferences(),
    
    // === PROFILE COMPLETION ===
    val profileCompletion: ProfileCompletion = ProfileCompletion(),
    
    // === AI ENRICHMENT RESULT ===
    val enrichmentResult: String? = null, // Saved AI-generated spiritual report
    val enrichmentGeneratedAt: LocalDateTime? = null // When the report was generated
)

data class BirthPlace(
    val city: String,
    val state: String? = null, // State/province
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val timezone: String? = null, // Original timezone
    val altitude: Double? = null, // Elevation above sea level
    val nearestSacredSite: String? = null // Nearby spiritual locations
)


data class UserPreferences(
    // === CALCULATION PREFERENCES ===
    val usesSiderealZodiac: Boolean = true,
    val preferredHouseSystem: HouseSystem = HouseSystem.WHOLE_SIGN,
    val numerologySystem: NumerologySystem = NumerologySystem.CHALDEAN,
    val includeKarmicNumbers: Boolean = true,
    val useTropicalForSun: Boolean = false, // Sun sign preference
    
    // === UI & EXPERIENCE ===
    val themeSkinTone: Int = 0, // For UI theming only
    val preferredLanguage: String = "en",
    val spiritualTerminology: TerminologyStyle = TerminologyStyle.TRADITIONAL,
    val detailLevel: InsightDetail = InsightDetail.COMPREHENSIVE,
    
    // === PRIVACY & SHARING ===
    val allowDataEnrichment: Boolean = true,
    val shareWithCommunity: Boolean = false,
    val saveReadings: Boolean = true
)

data class ProfileCompletion(
    val totalFields: Int = 36, // Total possible fields (comprehensive)
    val completedFields: Int = 0,
    val completionPercentage: Double = 0.0,
    val accuracyLevel: AccuracyLevel = AccuracyLevel.BASIC,
    val missingCriticalFields: List<String> = emptyList()
)

// === ENUMS ===

enum class HouseSystem {
    WHOLE_SIGN,
    PLACIDUS,
    EQUAL_HOUSE,
    KOCH,
    CAMPANUS
}

enum class NumerologySystem {
    CHALDEAN,
    PYTHAGOREAN,
    BOTH // Calculate using both systems
}

enum class Gender {
    MASCULINE, FEMININE, NON_BINARY, PREFER_NOT_TO_SAY
}

enum class BloodType {
    A_POSITIVE, A_NEGATIVE,
    B_POSITIVE, B_NEGATIVE, 
    AB_POSITIVE, AB_NEGATIVE,
    O_POSITIVE, O_NEGATIVE,
    UNKNOWN
}

enum class Hand {
    LEFT, RIGHT, AMBIDEXTROUS
}


enum class TerminologyStyle {
    TRADITIONAL, // Classical spiritual terms
    MODERN, // Contemporary language
    SCIENTIFIC // Psychological/scientific framing
}

enum class InsightDetail {
    BASIC, // Essential insights only
    MODERATE, // Balanced depth
    COMPREHENSIVE // Full detailed analysis
}

enum class AccuracyLevel {
    MINIMAL, // < 3 fields
    BASIC, // 3-9 fields
    GOOD, // 10-18 fields 
    EXCELLENT, // 19-27 fields
    MAXIMUM // 28-36 fields
}

// === RELATIONSHIP & COMPATIBILITY ENUMS ===

enum class LoveLanguage {
    WORDS_OF_AFFIRMATION, ACTS_OF_SERVICE, RECEIVING_GIFTS, 
    QUALITY_TIME, PHYSICAL_TOUCH
}

enum class PersonalityType {
    // MBTI Types
    INTJ, INTP, ENTJ, ENTP, INFJ, INFP, ENFJ, ENFP,
    ISTJ, ISFJ, ESTJ, ESFJ, ISTP, ISFP, ESTP, ESFP,
    // Enneagram Wings
    PERFECTIONIST, HELPER, ACHIEVER, INDIVIDUALIST, INVESTIGATOR,
    LOYALIST, ENTHUSIAST, CHALLENGER, PEACEMAKER
}

enum class AttachmentStyle {
    SECURE, ANXIOUS_PREOCCUPIED, DISMISSIVE_AVOIDANT, DISORGANIZED
}

enum class SexualEnergy {
    // Based on Tantric and David Deida teachings
    MASCULINE_CORE, FEMININE_CORE, BALANCED_CORE,
    FIRE_ENERGY, WATER_ENERGY, EARTH_ENERGY, AIR_ENERGY
}

enum class CommunicationStyle {
    DIRECT, INDIRECT, EMOTIONAL, ANALYTICAL, SUPPORTIVE, CHALLENGING
}

enum class ConflictStyle {
    CONFRONTING, AVOIDING, COMPROMISING, ACCOMMODATING, COLLABORATING
}

enum class IntimacyStyle {
    PHYSICAL_FOCUSED, EMOTIONAL_FOCUSED, SPIRITUAL_FOCUSED, MENTAL_FOCUSED, BALANCED
}

enum class SpiritualConnection {
    SHARED_PRACTICES, INDIVIDUAL_PATHS, TEACHER_STUDENT, TWIN_FLAME, KARMIC_PARTNERS
}

