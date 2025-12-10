package com.spiritatlas.data.mock

import android.util.Log
import com.spiritatlas.domain.model.*
import com.spiritatlas.domain.repository.UserRepository
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Mock Profile Initializer for SpiritAtlas
 *
 * Pre-populates the database with 2 comprehensive UserProfile objects containing ALL 36 fields.
 * This enables immediate testing of:
 * - Compatibility analysis with fully enriched profiles
 * - Profile library browsing with varied data
 * - All spiritual calculation systems (Numerology, Astrology, Ayurveda, Human Design)
 * - UI components with realistic, diverse data
 *
 * Usage:
 * Call `initializeMockProfiles()` once on app first launch or during development/testing.
 * Profiles will be saved to the database via UserRepository.
 *
 * @see UserProfile for complete field documentation
 */
@Singleton
class MockProfileInitializer @Inject constructor(
    private val userRepository: UserRepository
) {
    companion object {
        private const val TAG = "MockProfileInitializer"

        // Profile IDs for consistency
        const val SARAH_CHEN_ID = "mock-profile-sarah-chen"
        const val MARCUS_RIVERA_ID = "mock-profile-marcus-rivera"
    }

    /**
     * Initialize mock profiles in the database.
     * Checks if profiles already exist to prevent duplicates.
     *
     * @return Number of profiles created (0-2)
     */
    suspend fun initializeMockProfiles(): Int {
        Log.d(TAG, "Starting mock profile initialization...")

        var createdCount = 0

        // Check if profiles already exist
        val existingProfiles = userRepository.getAllProfiles()
        val sarahExists = existingProfiles.any { it.id == SARAH_CHEN_ID }
        val marcusExists = existingProfiles.any { it.id == MARCUS_RIVERA_ID }

        if (!sarahExists) {
            Log.d(TAG, "Creating Sarah Chen profile...")
            userRepository.saveUserProfile(createSarahChenProfile())
            createdCount++
            Log.d(TAG, "Sarah Chen profile created successfully")
        } else {
            Log.d(TAG, "Sarah Chen profile already exists, skipping")
        }

        if (!marcusExists) {
            Log.d(TAG, "Creating Marcus Rivera profile...")
            userRepository.saveUserProfile(createMarcusRiveraProfile())
            createdCount++
            Log.d(TAG, "Marcus Rivera profile created successfully")
        } else {
            Log.d(TAG, "Marcus Rivera profile already exists, skipping")
        }

        Log.d(TAG, "Mock profile initialization complete. Created $createdCount new profiles.")
        return createdCount
    }

    /**
     * Profile 1: Sarah Chen
     *
     * Archetype: Feminine-core spiritual teacher and healer
     * - Born in San Francisco during a full moon
     * - Deep family spiritual lineage (Chinese-American)
     * - INFJ personality with secure attachment
     * - Practices Tantric and Vedic traditions
     * - All 36 fields populated for maximum compatibility accuracy
     */
    private fun createSarahChenProfile(): UserProfile {
        val now = LocalDateTime.now()

        // Birth details: June 21, 1988 (Summer Solstice) at 3:33 AM in San Francisco
        val birthDateTime = LocalDateTime.of(1988, 6, 21, 3, 33, 0)
        val firstBreath = birthDateTime.plusSeconds(12) // 12 seconds after birth time
        val firstCry = birthDateTime.plusSeconds(8) // Cried 8 seconds after birth
        val conceptionDate = LocalDateTime.of(1987, 9, 28, 22, 0) // ~9 months before
        val firstSteps = LocalDateTime.of(1989, 3, 15, 10, 30) // 9 months old

        return UserProfile(
            // === SYSTEM FIELDS ===
            id = SARAH_CHEN_ID,
            profileName = "Sarah Chen",
            createdAt = now.minusDays(30), // Created 30 days ago
            lastModified = now.minusDays(2), // Last updated 2 days ago

            // === CORE IDENTITY (Required - Pick 3 minimum) ===
            name = "Sarah Mei-Ling Chen",
            displayName = "Sarah Chen",
            birthDateTime = birthDateTime,
            birthPlace = BirthPlace(
                city = "San Francisco",
                state = "California",
                country = "United States",
                latitude = 37.7749,
                longitude = -122.4194,
                timezone = "America/Los_Angeles",
                altitude = 16.0, // 16 meters above sea level
                nearestSacredSite = "Grace Cathedral, Lands End Labyrinth"
            ),

            // === ADDITIONAL NAMES (Numerology Enhancement) ===
            middleName = "Mei-Ling",
            nickname = "Sasa",
            spiritualName = "Shakti Devi",
            maidenName = "Chen", // Not changed

            // === FAMILY & ANCESTRY (Deep Karmic Insights) ===
            motherName = "Lin Chen",
            fatherName = "David Chen",
            ancestry = "Chinese-American (Guangdong Province)",
            familyTradition = "Buddhist-Taoist with Western spirituality integration",

            // === PHYSICAL & ENERGETIC ===
            gender = Gender.FEMININE,
            bloodType = BloodType.A_POSITIVE,
            dominantHand = Hand.RIGHT,
            eyeColor = "Dark Brown",
            height = 165.0, // 165 cm (5'5")
            birthWeight = 3.2, // 3.2 kg (7 lbs 1 oz)

            // === TIMING & CYCLES ===
            firstBreath = firstBreath,
            conceptionDate = conceptionDate,
            firstCry = firstCry,

            // === ENVIRONMENTAL ===
            weatherConditions = "Clear sky, warm evening (Summer Solstice)",
            moonPhase = "Full Moon (Strawberry Moon)",
            hospitalName = "UCSF Medical Center",
            seasonalEnergy = "Summer - Yang peak, fire element dominant",

            // === LIFE PATTERNS ===
            firstWord = "mama",
            firstSteps = firstSteps,

            // === COMPATIBILITY & RELATIONSHIP FIELDS ===
            loveLanguage = LoveLanguage.QUALITY_TIME,
            personalityType = PersonalityType.INFJ,
            attachmentStyle = AttachmentStyle.SECURE,
            sexualEnergy = SexualEnergy.FEMININE_CORE,
            communicationStyle = CommunicationStyle.EMOTIONAL,
            conflictResolution = ConflictStyle.COLLABORATING,
            intimacyPreference = IntimacyStyle.SPIRITUAL_FOCUSED,
            spiritualConnection = SpiritualConnection.SHARED_PRACTICES,
            lifePurposeAlignment = "To guide others through spiritual awakening and help them discover their authentic path through ancient wisdom traditions",

            // === PREFERENCES & SETTINGS ===
            preferences = UserPreferences(
                usesSiderealZodiac = true,
                preferredHouseSystem = HouseSystem.WHOLE_SIGN,
                numerologySystem = NumerologySystem.CHALDEAN,
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

            // === AI ENRICHMENT ===
            enrichmentResult = """
                Sarah Chen is a deeply spiritual soul with a profound connection to both Eastern and Western mystical traditions.
                Born on the Summer Solstice during a Full Moon, her chart reveals a natural healer and teacher archetype.

                Her Chaldean name number (3) indicates creative expression and communication gifts, perfectly aligned with her calling
                as a spiritual guide. The INFJ personality combined with secure attachment style creates a nurturing, empathetic presence
                that draws people seeking transformation.

                Her feminine-core tantric energy flows harmoniously with her spiritual-focused intimacy preference, suggesting someone who
                experiences the divine through sacred union and deep emotional-spiritual bonds. The Quality Time love language reflects
                her need for presence and meaningful connection.

                With ancestral roots in Buddhist-Taoist traditions and birth during peak Yang energy (Summer Solstice), she embodies
                the balance of contemplative wisdom and active service. Her life path is one of bridging ancient wisdom with contemporary
                spiritual seekers, offering guidance through the labyrinth of awakening.
            """.trimIndent(),
            enrichmentGeneratedAt = now.minusDays(2)
        )
    }

    /**
     * Profile 2: Marcus Rivera
     *
     * Archetype: Masculine-core entrepreneur and transformational leader
     * - Born in Miami during a new moon
     * - Puerto Rican heritage with Catholic-Santeria background
     * - ENTJ personality with anxious-preoccupied attachment
     * - Fire energy, direct communication style
     * - All 36 fields populated for comprehensive compatibility testing
     */
    private fun createMarcusRiveraProfile(): UserProfile {
        val now = LocalDateTime.now()

        // Birth details: December 15, 1985 at 11:47 PM in Miami
        val birthDateTime = LocalDateTime.of(1985, 12, 15, 23, 47, 0)
        val firstBreath = birthDateTime.plusSeconds(5) // Quick first breath
        val firstCry = birthDateTime.plusSeconds(3) // Strong, immediate cry
        val conceptionDate = LocalDateTime.of(1985, 3, 22, 14, 0) // ~9 months before
        val firstSteps = LocalDateTime.of(1986, 9, 1, 15, 45) // 8.5 months old (early walker)

        return UserProfile(
            // === SYSTEM FIELDS ===
            id = MARCUS_RIVERA_ID,
            profileName = "Marcus Rivera",
            createdAt = now.minusDays(25), // Created 25 days ago
            lastModified = now.minusDays(1), // Last updated yesterday

            // === CORE IDENTITY (Required - Pick 3 minimum) ===
            name = "Marcus Antonio Rivera",
            displayName = "Marcus",
            birthDateTime = birthDateTime,
            birthPlace = BirthPlace(
                city = "Miami",
                state = "Florida",
                country = "United States",
                latitude = 25.7617,
                longitude = -80.1918,
                timezone = "America/New_York",
                altitude = 2.0, // 2 meters above sea level
                nearestSacredSite = "Ermita de la Caridad, Coral Gables"
            ),

            // === ADDITIONAL NAMES (Numerology Enhancement) ===
            middleName = "Antonio",
            nickname = "Marc",
            spiritualName = "Agni Das", // Fire servant
            maidenName = null, // Not applicable

            // === FAMILY & ANCESTRY (Deep Karmic Insights) ===
            motherName = "Isabella Maria Rivera",
            fatherName = "Carlos Rivera Santos",
            ancestry = "Puerto Rican (Taíno, Spanish, West African heritage)",
            familyTradition = "Catholic with Santeria influences (Yoruba traditions)",

            // === PHYSICAL & ENERGETIC ===
            gender = Gender.MASCULINE,
            bloodType = BloodType.O_POSITIVE,
            dominantHand = Hand.LEFT, // Left-handed (creative flow)
            eyeColor = "Hazel-Brown",
            height = 183.0, // 183 cm (6'0")
            birthWeight = 3.8, // 3.8 kg (8 lbs 6 oz)

            // === TIMING & CYCLES ===
            firstBreath = firstBreath,
            conceptionDate = conceptionDate,
            firstCry = firstCry,

            // === ENVIRONMENTAL ===
            weatherConditions = "Tropical storm approaching, humid, electric atmosphere",
            moonPhase = "New Moon (Dark Moon - new beginnings)",
            hospitalName = "Jackson Memorial Hospital",
            seasonalEnergy = "Winter Solstice approaching - Yin rising, introspection time",

            // === LIFE PATTERNS ===
            firstWord = "go",
            firstSteps = firstSteps,

            // === COMPATIBILITY & RELATIONSHIP FIELDS ===
            loveLanguage = LoveLanguage.ACTS_OF_SERVICE,
            personalityType = PersonalityType.ENTJ,
            attachmentStyle = AttachmentStyle.ANXIOUS_PREOCCUPIED,
            sexualEnergy = SexualEnergy.MASCULINE_CORE,
            communicationStyle = CommunicationStyle.DIRECT,
            conflictResolution = ConflictStyle.CONFRONTING,
            intimacyPreference = IntimacyStyle.PHYSICAL_FOCUSED,
            spiritualConnection = SpiritualConnection.INDIVIDUAL_PATHS,
            lifePurposeAlignment = "To build transformative businesses that empower communities and create financial freedom while honoring ancestral wisdom",

            // === PREFERENCES & SETTINGS ===
            preferences = UserPreferences(
                usesSiderealZodiac = false, // Prefers Tropical
                preferredHouseSystem = HouseSystem.PLACIDUS,
                numerologySystem = NumerologySystem.PYTHAGOREAN,
                includeKarmicNumbers = true,
                useTropicalForSun = true,
                themeSkinTone = 2,
                preferredLanguage = "en",
                spiritualTerminology = TerminologyStyle.MODERN,
                detailLevel = InsightDetail.MODERATE,
                allowDataEnrichment = true,
                shareWithCommunity = false, // More private
                saveReadings = true
            ),

            // === AI ENRICHMENT ===
            enrichmentResult = """
                Marcus Rivera embodies the archetype of the transformational warrior-leader. Born during a New Moon with an approaching
                storm, his chart speaks to someone who initiates new cycles and thrives in dynamic, challenging environments.

                His Pythagorean life path number (5) reveals the essence of change, freedom, and adventure - perfectly aligned with his
                entrepreneurial drive. The ENTJ personality combined with anxious-preoccupied attachment creates a powerful, achievement-oriented
                individual who deeply values connection but may struggle with vulnerability.

                His masculine-core tantric energy paired with physical-focused intimacy preference indicates someone who experiences
                spiritual connection through embodied presence and action. The Acts of Service love language shows he expresses care
                through tangible support and problem-solving.

                With Puerto Rican ancestry blending Taíno, Spanish, and West African traditions, plus Catholic-Santeria spiritual background,
                Marcus carries a rich tapestry of warrior and creator energies. His left-handed dominance (creative flow) combined with
                his direct communication style creates a unique leadership presence - intuitive yet decisive.

                Born near the Winter Solstice, he balances the fire of his nature with the reflective depth of Yin season, learning to
                integrate action with introspection. His life path involves transforming the material world while staying rooted in
                ancestral wisdom and community empowerment.
            """.trimIndent(),
            enrichmentGeneratedAt = now.minusDays(1)
        )
    }

    /**
     * Clear all mock profiles from the database.
     * Useful for testing and development reset scenarios.
     */
    suspend fun clearMockProfiles() {
        Log.d(TAG, "Clearing mock profiles...")

        val existingProfiles = userRepository.getAllProfiles()
        val mockProfiles = existingProfiles.filter {
            it.id == SARAH_CHEN_ID || it.id == MARCUS_RIVERA_ID
        }

        mockProfiles.forEach { profile ->
            userRepository.deleteProfile(profile.id)
            Log.d(TAG, "Deleted mock profile: ${profile.profileName}")
        }

        Log.d(TAG, "Mock profiles cleared. Removed ${mockProfiles.size} profiles.")
    }

    /**
     * Check if mock profiles exist in the database.
     *
     * @return Pair<Boolean, Boolean> where first is Sarah exists, second is Marcus exists
     */
    suspend fun mockProfilesExist(): Pair<Boolean, Boolean> {
        val existingProfiles = userRepository.getAllProfiles()
        val sarahExists = existingProfiles.any { it.id == SARAH_CHEN_ID }
        val marcusExists = existingProfiles.any { it.id == MARCUS_RIVERA_ID }

        return Pair(sarahExists, marcusExists)
    }
}
