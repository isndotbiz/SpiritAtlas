package com.spiritatlas.domain.model

import java.time.LocalDateTime
import java.time.Month
import kotlin.random.Random

/**
 * Random Profile Generator for comprehensive compatibility testing
 * Generates 10 variations each for Tier 0-3, both male and female profiles
 * Includes tantric, relationship wisdom, and spiritual partnership data
 */
object RandomProfileGenerator {
    
    private val maleNames = listOf(
        "Alexander Phoenix", "Marcus Storm", "River Stone", "Sage Midnight", "Phoenix Fire",
        "Hunter Moon", "Atlas Wild", "Orion Deep", "Zane Sacred", "Kai Thunder"
    )
    
    private val femaleNames = listOf(
        "Luna Starweaver", "Aurora Moonbeam", "Sage Crystalwind", "Phoenix Rose", "Maya Stardust",
        "Aria Moonlight", "Nova Earthsong", "Raven Silverwood", "Iris Dreamweaver", "Serenity Wilde"
    )
    
    private val maleDisplayNames = listOf(
        "Alex", "Marcus", "River", "Sage", "Phoenix", "Hunter", "Atlas", "Orion", "Zane", "Kai"
    )
    
    private val femaleDisplayNames = listOf(
        "Luna", "Aurora", "Sage", "Phoenix", "Maya", "Aria", "Nova", "Raven", "Iris", "Serenity"
    )
    
    private val spiritualNames = listOf(
        "Moonwhisper", "Starweaver", "Soulfire", "Earthsong", "Skywalker", 
        "Heartlight", "Spiritdancer", "Cosmicwind", "Sacredflame", "Dreamkeeper"
    )
    
    private val cities = listOf(
        "Sedona", "Mount Shasta", "Glastonbury", "Santa Fe", "Machu Picchu",
        "Stonehenge", "Varanasi", "Kyoto", "Rishikesh", "Big Sur"
    )
    
    private val countries = listOf(
        "USA", "USA", "UK", "USA", "Peru", "UK", "India", "Japan", "India", "USA"
    )
    
    private val moonPhases = listOf(
        "New Moon in Scorpio", "Full Moon in Taurus", "Waxing Crescent", "Waning Gibbous",
        "Blood Moon Eclipse", "Blue Moon", "Super Moon", "Dark Moon", "Harvest Moon", "Wolf Moon"
    )
    
    private val weatherConditions = listOf(
        "Clear starlit night", "Gentle rain with rainbow", "Mystical morning mist", "Perfect sunset glow",
        "Soft snowfall", "Warm breeze and sunshine", "Lightning storm clearing", "Aurora borealis visible",
        "Sacred dawn light", "Peaceful candlelit evening"
    )
    
    private val ancestries = listOf(
        "Celtic-Norse", "Native American-Irish", "Italian-Greek", "Egyptian-Ethiopian", "Chinese-Japanese",
        "Hindu-Tibetan", "Germanic-Slavic", "Polynesian-Hawaiian", "Mayan-Aztec", "Aboriginal-Maori"
    )
    
    private val familyTraditions = listOf(
        "Celtic Shamanism", "Tibetan Buddhism", "Hindu Tantra", "Native American Medicine Ways",
        "Egyptian Mystery Schools", "Greek Philosophy", "Norse Spirituality", "Sufi Mysticism",
        "Zen Buddhism", "Christian Mysticism"
    )
    
    private val lifePurposes = listOf(
        "To heal and guide others toward authentic truth", "To create beauty that transforms hearts",
        "To teach ancient wisdom in modern ways", "To bridge science and spirituality",
        "To awaken consciousness through love", "To protect and preserve sacred knowledge",
        "To inspire others to live their highest potential", "To heal ancestral patterns and trauma",
        "To create sacred community and connection", "To serve as a bridge between worlds"
    )
    
    /**
     * Generate randomized demo profile based on tier and gender
     */
    fun generateRandomProfile(tier: Int, isMale: Boolean, variationIndex: Int = 0): UserProfile {
        val random = Random(System.currentTimeMillis() + variationIndex + if (isMale) 1000 else 2000)
        val now = LocalDateTime.now()
        
        // Base profile with required fields
        val names = if (isMale) maleNames else femaleNames
        val displayNames = if (isMale) maleDisplayNames else femaleDisplayNames
        val genderValue = if (isMale) Gender.MASCULINE else Gender.FEMININE
        
        val name = names[variationIndex % names.size]
        val displayName = displayNames[variationIndex % displayNames.size]
        
        val profile = UserProfile(
            id = "random_${tier}_${if (isMale) "male" else "female"}_${variationIndex}",
            profileName = "$displayName's ${getTierName(tier)} Profile",
            createdAt = now.minusDays(random.nextLong(1, 30)),
            lastModified = now,
            name = name,
            displayName = displayName,
            birthDateTime = generateRandomBirthDate(random),
            birthPlace = generateRandomBirthPlace(random, variationIndex),
            gender = genderValue
        )
        
        // Add fields based on tier
        return when (tier) {
            0 -> addTier0Fields(profile, random, variationIndex) // 3-9 fields
            1 -> addTier1Fields(profile, random, variationIndex) // 10-18 fields
            2 -> addTier2Fields(profile, random, variationIndex) // 19-27 fields
            3 -> addTier3Fields(profile, random, variationIndex) // 28-36 fields
            else -> profile
        }
    }
    
    private fun getTierName(tier: Int): String = when (tier) {
        0 -> "Tier 0 Minimal"
        1 -> "Tier 1 Basic"
        2 -> "Tier 2 Detailed"
        3 -> "Tier 3 Master"
        else -> "Unknown"
    }
    
    private fun generateRandomBirthDate(random: Random): LocalDateTime {
        val year = random.nextInt(1970, 2005)
        val month = random.nextInt(1, 13)
        val day = random.nextInt(1, 28) // Safe day range
        val hour = random.nextInt(0, 24)
        val minute = random.nextInt(0, 60)
        return LocalDateTime.of(year, month, day, hour, minute)
    }
    
    private fun generateRandomBirthPlace(random: Random, variationIndex: Int): BirthPlace {
        val cityIndex = variationIndex % cities.size
        return BirthPlace(
            city = cities[cityIndex],
            country = countries[cityIndex],
            latitude = random.nextDouble(-90.0, 90.0),
            longitude = random.nextDouble(-180.0, 180.0),
            timezone = "America/Los_Angeles" // Simplified
        )
    }
    
    /**
     * Tier 0: 3-9 fields → 300 words
     * Minimal profile with just basic identity
     */
    private fun addTier0Fields(base: UserProfile, random: Random, index: Int): UserProfile {
        val fieldsToAdd = random.nextInt(1, 7) // Add 1-6 more fields (total 4-9)
        var profile = base
        
        // Always add a few basic fields for tier 0
        if (fieldsToAdd >= 1) profile = profile.copy(nickname = "Nick${index}")
        if (fieldsToAdd >= 2) profile = profile.copy(eyeColor = getRandomEyeColor(random))
        if (fieldsToAdd >= 3) profile = profile.copy(moonPhase = moonPhases[index % moonPhases.size])
        if (fieldsToAdd >= 4) profile = profile.copy(firstWord = getRandomFirstWord(random))
        if (fieldsToAdd >= 5) profile = profile.copy(dominantHand = Hand.values()[random.nextInt(Hand.values().size)])
        if (fieldsToAdd >= 6) profile = profile.copy(bloodType = BloodType.values()[random.nextInt(BloodType.values().size)])
        
        return profile.copy(profileCompletion = ProfileCompletion(
            totalFields = 36,
            completedFields = 4 + fieldsToAdd,
            completionPercentage = ((4 + fieldsToAdd).toDouble() / 36 * 100),
            accuracyLevel = AccuracyLevel.MINIMAL
        ))
    }
    
    /**
     * Tier 1: 10-18 fields → 900 words
     * Basic profile with core relationship data
     */
    private fun addTier1Fields(base: UserProfile, random: Random, index: Int): UserProfile {
        val tier0 = addTier0Fields(base, random, index)
        var profile = tier0
        
        // Add relationship-focused fields
        profile = profile.copy(
            middleName = "Rose",
            spiritualName = spiritualNames[index % spiritualNames.size],
            motherName = "Diana Moon",
            ancestry = ancestries[index % ancestries.size],
            weatherConditions = weatherConditions[index % weatherConditions.size],
            hospitalName = "${cities[index % cities.size]} Sacred Birth Center",
            loveLanguage = LoveLanguage.values()[random.nextInt(LoveLanguage.values().size)],
            personalityType = PersonalityType.values()[random.nextInt(PersonalityType.values().size)],
            attachmentStyle = AttachmentStyle.values()[random.nextInt(AttachmentStyle.values().size)],
            communicationStyle = CommunicationStyle.values()[random.nextInt(CommunicationStyle.values().size)]
        )
        
        return profile.copy(profileCompletion = ProfileCompletion(
            totalFields = 36,
            completedFields = random.nextInt(12, 19), // 12-18 fields
            completionPercentage = (random.nextInt(12, 19).toDouble() / 36 * 100),
            accuracyLevel = AccuracyLevel.GOOD
        ))
    }
    
    /**
     * Tier 2: 19-27 fields → 1800 words
     * Detailed profile with deeper compatibility insights
     */
    private fun addTier2Fields(base: UserProfile, random: Random, index: Int): UserProfile {
        val tier1 = addTier1Fields(base, random, index)
        var profile = tier1
        
        // Add deeper relationship and tantric fields
        profile = profile.copy(
            fatherName = "Orion Storm",
            familyTradition = familyTraditions[index % familyTraditions.size],
            height = random.nextDouble(150.0, 200.0),
            birthWeight = random.nextDouble(2.5, 4.5),
            firstBreath = base.birthDateTime?.plusSeconds(30),
            conceptionDate = base.birthDateTime?.minusMonths(9),
            seasonalEnergy = getSeasonalEnergy(base.birthDateTime?.month),
            firstSteps = base.birthDateTime?.plusMonths(12),
            sexualEnergy = SexualEnergy.values()[random.nextInt(SexualEnergy.values().size)],
            conflictResolution = ConflictStyle.values()[random.nextInt(ConflictStyle.values().size)],
            intimacyPreference = IntimacyStyle.values()[random.nextInt(IntimacyStyle.values().size)],
            spiritualConnection = SpiritualConnection.values()[random.nextInt(SpiritualConnection.values().size)],
            lifePurposeAlignment = lifePurposes[index % lifePurposes.size]
        )
        
        return profile.copy(profileCompletion = ProfileCompletion(
            totalFields = 36,
            completedFields = random.nextInt(21, 28), // 21-27 fields
            completionPercentage = (random.nextInt(21, 28).toDouble() / 36 * 100),
            accuracyLevel = AccuracyLevel.EXCELLENT
        ))
    }
    
    /**
     * Tier 3: 28-36 fields → 2700 words
     * Master profile with complete tantric/relationship wisdom
     */
    private fun addTier3Fields(base: UserProfile, random: Random, index: Int): UserProfile {
        val tier2 = addTier2Fields(base, random, index)
        var profile = tier2
        
        // Fill remaining fields for maximum compatibility analysis
        profile = profile.copy(
            maidenName = if (base.gender == Gender.FEMININE) "Starweaver" else null,
            firstCry = base.birthDateTime?.plusSeconds(15)
        )
        
        return profile.copy(profileCompletion = ProfileCompletion(
            totalFields = 36,
            completedFields = random.nextInt(30, 37), // 30-36 fields
            completionPercentage = (random.nextInt(30, 37).toDouble() / 36 * 100),
            accuracyLevel = AccuracyLevel.MAXIMUM
        ))
    }
    
    private fun getRandomEyeColor(random: Random): String {
        val colors = listOf(
            "Deep brown with golden flecks", "Ocean blue with silver hints", "Emerald green with amber rings",
            "Hazel with golden starbursts", "Violet blue with mystical depth", "Warm amber with copper tones",
            "Steel gray with silver flecks", "Rich chocolate brown", "Sea green with golden specks", "Ice blue with crystal clarity"
        )
        return colors[random.nextInt(colors.size)]
    }
    
    private fun getRandomFirstWord(random: Random): String {
        val words = listOf("Light", "Love", "Peace", "Star", "Moon", "Heart", "Soul", "Spirit", "Joy", "Sacred")
        return words[random.nextInt(words.size)]
    }
    
    private fun getSeasonalEnergy(month: Month?): String {
        return when (month) {
            Month.DECEMBER, Month.JANUARY, Month.FEBRUARY -> "Deep winter contemplation energy"
            Month.MARCH, Month.APRIL, Month.MAY -> "Spring awakening and new growth energy"
            Month.JUNE, Month.JULY, Month.AUGUST -> "Summer fire and manifestation energy" 
            Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER -> "Autumn harvest and wisdom energy"
            null -> "Balanced seasonal energy"
        }
    }
}
