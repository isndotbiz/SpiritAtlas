package com.spiritatlas.domain.model

import java.time.LocalDateTime
import java.time.Month

/**
 * Sample fully-filled UserProfile demonstrating maximum spiritual accuracy ✨
 * This shows all possible fields filled for comprehensive readings
 */
object SampleUserProfile {
    
    val maxAccuracySample = UserProfile(
        id = "sample_max_accuracy_001",
        profileName = "Luna's Maximum Spiritual Profile",
        createdAt = LocalDateTime.of(2024, Month.JANUARY, 15, 10, 30),
        lastModified = LocalDateTime.of(2024, Month.JANUARY, 20, 14, 45),
        
        // === CORE IDENTITY ===
        name = "Luna Alexandra Moonwhisper",
        displayName = "Luna",
        birthDateTime = LocalDateTime.of(1992, Month.JULY, 23, 3, 45, 30), // Leo Sun, exact time
        birthPlace = BirthPlace(
            city = "Sedona",
            state = "Arizona", 
            country = "USA",
            latitude = 34.8697,
            longitude = -111.7610,
            timezone = "America/Phoenix",
            altitude = 1317.0, // meters above sea level
            nearestSacredSite = "Cathedral Rock Vortex"
        ),
        
        // === ADDITIONAL NAMES ===
        middleName = "Alexandra Rose",
        nickname = "Lunar",
        spiritualName = "Moonwhisper",
        
        // === FAMILY & ANCESTRY ===
        motherName = "Celeste Maya Starweaver",
        fatherName = "Orion David Starweaver", 
        ancestry = "Celtic-Cherokee-Italian",
        
        // === PHYSICAL & ENERGETIC ===
        gender = Gender.FEMININE,
        bloodType = BloodType.A_NEGATIVE,
        dominantHand = Hand.LEFT,
        eyeColor = "Deep ocean blue with golden flecks",
        
        // === KEY TIMING ===
        firstBreath = LocalDateTime.of(1992, Month.JULY, 23, 3, 45, 32),
        
        // === ENVIRONMENTAL ===
        weatherConditions = "Clear night, full moon visible, gentle desert breeze",
        moonPhase = "Waxing Gibbous (84% illumination)",
        hospitalName = "Sedona Sacred Birth Center",
        
        // === LIFE PATTERNS ===
        firstWord = "Light",
        firstSteps = LocalDateTime.of(1993, Month.JUNE, 15, 14, 30),
        
        // === PREFERENCES ===
        preferences = UserPreferences(
            usesSiderealZodiac = true,
            preferredHouseSystem = HouseSystem.WHOLE_SIGN,
            numerologySystem = NumerologySystem.BOTH,
            includeKarmicNumbers = true,
            useTropicalForSun = false,
            themeSkinTone = 0,
            preferredLanguage = "en",
            spiritualTerminology = TerminologyStyle.TRADITIONAL,
            detailLevel = InsightDetail.COMPREHENSIVE,
            allowDataEnrichment = true,
            shareWithCommunity = false,
            saveReadings = true
        ),
        
        // === COMPLETION ===
        profileCompletion = ProfileCompletion(
            totalFields = 27,
            completedFields = 20, // High completion
            completionPercentage = 74.1,
            accuracyLevel = AccuracyLevel.EXCELLENT,
            missingCriticalFields = emptyList()
        )
    )
    
    /**
     * Minimum viable profile (only 3 core fields) for comparison
     */
    val minimumViableSample = UserProfile(
        id = "sample_minimum_001",
        profileName = "Alex's Basic Profile",
        createdAt = LocalDateTime.of(2024, Month.JANUARY, 10, 9, 15),
        lastModified = LocalDateTime.of(2024, Month.JANUARY, 10, 9, 15),
        name = "Alex River",
        displayName = "Alex",
        birthDateTime = LocalDateTime.of(1995, Month.MARCH, 15, 14, 30),
        birthPlace = BirthPlace(
            city = "Portland",
            country = "USA", 
            latitude = 45.5152,
            longitude = -122.6784
        ),
        profileCompletion = ProfileCompletion(
            totalFields = 27,
            completedFields = 3,
            completionPercentage = 11.1,
            accuracyLevel = AccuracyLevel.BASIC,
            missingCriticalFields = listOf("Birth time", "Birth location details", "Full name")
        )
    )
    
    /**
     * Balanced profile (moderate completion) for realistic user scenarios
     */
    val balancedSample = UserProfile(
        id = "sample_balanced_001",
        profileName = "Maya's Balanced Profile",
        createdAt = LocalDateTime.of(2024, Month.JANUARY, 12, 16, 20),
        lastModified = LocalDateTime.of(2024, Month.JANUARY, 18, 11, 30),
        name = "Maya Chen",
        displayName = "Maya",
        birthDateTime = LocalDateTime.of(1990, Month.NOVEMBER, 8, 7, 20),
        birthPlace = BirthPlace(
            city = "San Francisco",
            state = "California",
            country = "USA",
            latitude = 37.7749,
            longitude = -122.4194,
            timezone = "America/Los_Angeles"
        ),
        middleName = "Sun",
        motherName = "Li Wei Chen",
        ancestry = "Chinese-German",
        gender = Gender.FEMININE,
        dominantHand = Hand.RIGHT,
        moonPhase = "New Moon",
        preferences = UserPreferences(
            usesSiderealZodiac = false,
            numerologySystem = NumerologySystem.CHALDEAN,
            detailLevel = InsightDetail.MODERATE
        ),
        profileCompletion = ProfileCompletion(
            totalFields = 27,
            completedFields = 12,
            completionPercentage = 44.4,
            accuracyLevel = AccuracyLevel.GOOD,
            missingCriticalFields = emptyList()
        )
    )
    
    /**
     * Testing profiles for NEW tiered AI enrichment system (36-field model)
     * Randomized profiles for comprehensive compatibility analysis:
     * - Tier 0: 3-9 fields → 300 words (Minimal)
     * - Tier 1: 10-18 fields → 900 words (Basic)
     * - Tier 2: 19-27 fields → 1800 words (Detailed)
     * - Tier 3: 28-36 fields → 2700 words (Master)
     */
    
    /** Tier 1: Basic (3 fields) - ~300 words */
    val tierOneSample = UserProfile(
        id = "test_tier1_basic",
        profileName = "Tier 1: Basic Test (3 fields)",
        createdAt = LocalDateTime.now().minusDays(1),
        lastModified = LocalDateTime.now(),
        name = "Jordan Smith",
        displayName = "Jordan",
        birthDateTime = LocalDateTime.of(1988, Month.JUNE, 21, 12, 0),
        birthPlace = BirthPlace(
            city = "Denver",
            country = "USA",
            latitude = 39.7392,
            longitude = -104.9903
        ),
        profileCompletion = ProfileCompletion(
            totalFields = 27,
            completedFields = 3,
            completionPercentage = 11.1,
            accuracyLevel = AccuracyLevel.MINIMAL
        )
    )
    
    /** Tier 2: Detailed (10 fields) - ~1000 words */
    val tierTwoSample = UserProfile(
        id = "test_tier2_detailed",
        profileName = "Tier 2: Detailed Test (10 fields)",
        createdAt = LocalDateTime.now().minusDays(2),
        lastModified = LocalDateTime.now(),
        name = "River Phoenix Willow",
        displayName = "River",
        birthDateTime = LocalDateTime.of(1985, Month.OCTOBER, 31, 23, 45),
        birthPlace = BirthPlace(
            city = "Santa Fe",
            state = "New Mexico",
            country = "USA",
            latitude = 35.6870,
            longitude = -105.9378,
            timezone = "America/Denver"
        ),
        middleName = "Phoenix",
        gender = Gender.NON_BINARY,
        dominantHand = Hand.LEFT,
        moonPhase = "Full Moon - Blood Moon Eclipse",
        ancestry = "Native American-Irish",
        weatherConditions = "Stormy night with lightning",
        profileCompletion = ProfileCompletion(
            totalFields = 27,
            completedFields = 10,
            completionPercentage = 37.0,
            accuracyLevel = AccuracyLevel.BASIC
        )
    )
    
    /** Tier 3: Comprehensive (20 fields) - ~2000 words */
    val tierThreeSample = UserProfile(
        id = "test_tier3_comprehensive",
        profileName = "Tier 3: Comprehensive Test (20 fields)",
        createdAt = LocalDateTime.now().minusDays(3),
        lastModified = LocalDateTime.now(),
        name = "Sage Aurora Nightingale",
        displayName = "Sage",
        birthDateTime = LocalDateTime.of(1991, Month.DECEMBER, 21, 6, 30), // Winter Solstice
        birthPlace = BirthPlace(
            city = "Mount Shasta",
            state = "California",
            country = "USA",
            latitude = 41.3099,
            longitude = -122.3103,
            timezone = "America/Los_Angeles",
            altitude = 1067.0,
            nearestSacredSite = "Mount Shasta Vortex"
        ),
        middleName = "Aurora",
        nickname = "Starlight",
        spiritualName = "Nightingale",
        motherName = "Diana Moon Stormweaver",
        fatherName = "Marcus Earth Stormweaver",
        ancestry = "Celtic-Norse-Cherokee",
        gender = Gender.FEMININE,
        bloodType = BloodType.O_POSITIVE,
        dominantHand = Hand.RIGHT,
        eyeColor = "Violet-blue with silver flecks",
        firstBreath = LocalDateTime.of(1991, Month.DECEMBER, 21, 6, 30, 15),
        weatherConditions = "Clear dawn sky, winter solstice sunrise",
        moonPhase = "New Moon in Sagittarius",
        hospitalName = "Mount Shasta Sacred Birth Center",
        firstWord = "Star",
        firstSteps = LocalDateTime.of(1993, Month.JUNE, 15, 14, 30),
        preferences = UserPreferences(
            usesSiderealZodiac = true,
            preferredHouseSystem = HouseSystem.PLACIDUS,
            numerologySystem = NumerologySystem.BOTH,
            includeKarmicNumbers = true,
            detailLevel = InsightDetail.COMPREHENSIVE
        ),
        profileCompletion = ProfileCompletion(
            totalFields = 27,
            completedFields = 20,
            completionPercentage = 74.1,
            accuracyLevel = AccuracyLevel.EXCELLENT
        )
    )
    
    /** Tier 4: Master (24+ fields) - ~3000 words */
    val tierFourSample = UserProfile(
        id = "test_tier4_master",
        profileName = "Tier 4: Master Test (24+ fields)",
        createdAt = LocalDateTime.now().minusDays(4),
        lastModified = LocalDateTime.now(),
        name = "Phoenix Luna Arcanum Starweaver",
        displayName = "Phoenix",
        birthDateTime = LocalDateTime.of(1987, Month.AUGUST, 8, 8, 8, 8), // Leo Lion's Gate Portal
        birthPlace = BirthPlace(
            city = "Glastonbury",
            state = "Somerset",
            country = "United Kingdom",
            latitude = 51.1441,
            longitude = -2.7143,
            timezone = "Europe/London",
            altitude = 145.0,
            nearestSacredSite = "Glastonbury Tor - Avalon Portal"
        ),
        middleName = "Luna Arcanum",
        nickname = "Phoenix Fire",
        spiritualName = "Starweaver of the Sacred Flame",
        motherName = "Isadora Crystal Moonbeam Crystalweaver",
        fatherName = "Orion Storm Thunder Crystalweaver",
        ancestry = "Celtic Druid-Egyptian Priestess-Atlantean Starseed",
        gender = Gender.NON_BINARY,
        bloodType = BloodType.AB_NEGATIVE,
        dominantHand = Hand.AMBIDEXTROUS,
        eyeColor = "Deep emerald with golden starbursts and violet rings",
        firstBreath = LocalDateTime.of(1987, Month.AUGUST, 8, 8, 8, 8),
        weatherConditions = "Mystical dawn mist, perfect temperature, gentle sacred breeze, rainbow appeared",
        moonPhase = "Waning Crescent in Gemini (sacred messenger moon)",
        hospitalName = "Avalon Sacred Birth Sanctuary",
        firstWord = "Light",
        firstSteps = LocalDateTime.of(1988, Month.AUGUST, 8, 8, 8), // Exactly one year later
        preferences = UserPreferences(
            usesSiderealZodiac = true,
            preferredHouseSystem = HouseSystem.WHOLE_SIGN,
            numerologySystem = NumerologySystem.BOTH,
            includeKarmicNumbers = true,
            useTropicalForSun = false,
            themeSkinTone = 0,
            preferredLanguage = "en",
            spiritualTerminology = TerminologyStyle.TRADITIONAL,
            detailLevel = InsightDetail.COMPREHENSIVE,
            allowDataEnrichment = true,
            shareWithCommunity = true,
            saveReadings = true
        ),
        profileCompletion = ProfileCompletion(
            totalFields = 27,
            completedFields = 24, // Master tier (24+ fields)
            completionPercentage = 88.9,
            accuracyLevel = AccuracyLevel.MAXIMUM
        )
    )
}
