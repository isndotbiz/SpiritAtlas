package com.spiritatlas.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.spiritatlas.core.astro.AstrologyCalculator
import com.spiritatlas.core.ayurveda.AyurvedaCalculator
import com.spiritatlas.core.common.Result
import com.spiritatlas.core.humandesign.HumanDesignCalculator
import com.spiritatlas.core.numerology.ChaldeanCalculator
import com.spiritatlas.data.database.SpiritAtlasDatabase
import com.spiritatlas.data.database.dao.UserProfileDao
import com.spiritatlas.data.repository.CompatibilityRepositoryImpl
import com.spiritatlas.data.repository.UserRepositoryImpl
import com.spiritatlas.domain.model.*
import com.spiritatlas.domain.repository.CompatibilityRepository
import com.spiritatlas.domain.repository.UserRepository
import com.spiritatlas.domain.service.CompatibilityAnalysisEngine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

/**
 * End-to-end integration test validating complete data flows
 * Tests all 5 spiritual systems working together across all layers
 */
@RunWith(AndroidJUnit4::class)
class EndToEndCalculationTest {

    private lateinit var database: SpiritAtlasDatabase
    private lateinit var userProfileDao: UserProfileDao
    private lateinit var userRepository: UserRepository
    private lateinit var compatibilityRepository: CompatibilityRepository
    private lateinit var compatibilityEngine: CompatibilityAnalysisEngine

    // Calculation engines
    private lateinit var numerologyCalculator: ChaldeanCalculator
    private lateinit var astrologyCalculator: AstrologyCalculator
    private lateinit var ayurvedaCalculator: AyurvedaCalculator
    private lateinit var humanDesignCalculator: HumanDesignCalculator

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Setup database
        database = Room.inMemoryDatabaseBuilder(
            context,
            SpiritAtlasDatabase::class.java
        ).build()

        userProfileDao = database.userProfileDao()
        userRepository = UserRepositoryImpl(userProfileDao)

        // Setup calculation engines
        numerologyCalculator = ChaldeanCalculator()
        astrologyCalculator = AstrologyCalculator()
        ayurvedaCalculator = AyurvedaCalculator()
        humanDesignCalculator = HumanDesignCalculator()

        // Setup compatibility engine and repository
        compatibilityEngine = CompatibilityAnalysisEngine(aiCompatibilityService = null)
        compatibilityRepository = CompatibilityRepositoryImpl(compatibilityEngine)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun endToEnd_completeProfileCreationFlow() = runTest {
        // Given - Create a profile with birth data
        val profile = UserProfile(
            id = "test_user",
            profileName = "John's Spiritual Profile",
            createdAt = LocalDateTime.now(),
            lastModified = LocalDateTime.now(),
            name = "John Smith",
            displayName = "John",
            birthDateTime = LocalDateTime.of(1990, 6, 15, 14, 30),
            birthPlace = BirthPlace(
                city = "San Francisco",
                country = "USA",
                latitude = 37.7749,
                longitude = -122.4194
            ),
            gender = Gender.MASCULINE,
            preferences = UserPreferences(
                usesSiderealZodiac = false,
                numerologySystem = NumerologySystem.CHALDEAN,
                detailLevel = InsightDetail.COMPREHENSIVE
            )
        )

        // When - Save profile
        userRepository.saveUserProfile(profile)

        // Then - Verify persistence
        val saved = userRepository.getUserProfile().first()
        assertThat(saved).isNotNull()
        assertThat(saved?.id).isEqualTo("test_user")
        assertThat(saved?.name).isEqualTo("John Smith")

        // Then - Verify profile completion calculated
        assertThat(saved?.profileCompletion).isNotNull()
        assertThat(saved?.profileCompletion?.completedFields).isGreaterThan(0)
        assertThat(saved?.profileCompletion?.accuracyLevel).isNotNull()
    }

    @Test
    fun endToEnd_numerologyCalculationFlow() = runTest {
        // Given - Profile with name and birth date
        val profile = createTestProfile(
            name = "John Smith",
            birthDateTime = LocalDateTime.of(1990, 6, 15, 14, 30)
        )

        // When - Calculate numerology
        val lifePath = numerologyCalculator.calculateLifePath(1990, 6, 15)
        val destiny = numerologyCalculator.calculateDestiny("John Smith")
        val soul = numerologyCalculator.calculateSoul("John Smith")
        val personality = numerologyCalculator.calculatePersonality("John Smith")

        // Then - Verify calculations
        assertThat(lifePath).isNotNull()
        assertThat(lifePath).isAtLeast(1)
        assertThat(lifePath).isAtMost(33) // Master numbers included

        assertThat(destiny).isNotNull()
        assertThat(soul).isNotNull()
        assertThat(personality).isNotNull()

        // Save profile with calculated data
        userRepository.saveUserProfile(profile)
        val saved = userRepository.getUserProfile().first()
        assertThat(saved).isNotNull()
    }

    @Test
    fun endToEnd_astrologyCalculationFlow() = runTest {
        // Given - Profile with birth data
        val birthDateTime = LocalDateTime.of(1990, 6, 15, 14, 30)
        val birthPlace = BirthPlace(
            city = "San Francisco",
            country = "USA",
            latitude = 37.7749,
            longitude = -122.4194
        )

        val profile = createTestProfile(
            birthDateTime = birthDateTime,
            birthPlace = birthPlace
        )

        // When - Calculate astrology
        val sunSign = astrologyCalculator.calculateSunSign(
            birthDateTime.year,
            birthDateTime.monthValue,
            birthDateTime.dayOfMonth,
            tropical = true
        )

        val moonSign = astrologyCalculator.calculateMoonSign(
            birthDateTime.year,
            birthDateTime.monthValue,
            birthDateTime.dayOfMonth,
            birthDateTime.hour,
            birthDateTime.minute,
            birthPlace.latitude,
            birthPlace.longitude,
            tropical = true
        )

        val risingSign = astrologyCalculator.calculateRisingSign(
            birthDateTime.year,
            birthDateTime.monthValue,
            birthDateTime.dayOfMonth,
            birthDateTime.hour,
            birthDateTime.minute,
            birthPlace.latitude,
            birthPlace.longitude,
            tropical = true
        )

        // Then - Verify calculations
        assertThat(sunSign).isNotNull()
        assertThat(sunSign).isNotEmpty()

        assertThat(moonSign).isNotNull()
        assertThat(risingSign).isNotNull()

        // Save profile
        userRepository.saveUserProfile(profile)
        assertThat(userRepository.getUserProfile().first()).isNotNull()
    }

    @Test
    fun endToEnd_ayurvedaCalculationFlow() = runTest {
        // Given - Profile with birth data
        val birthDateTime = LocalDateTime.of(1990, 6, 15, 14, 30)
        val birthPlace = BirthPlace(
            city = "San Francisco",
            country = "USA",
            latitude = 37.7749,
            longitude = -122.4194
        )

        val profile = createTestProfile(
            birthDateTime = birthDateTime,
            birthPlace = birthPlace
        )

        // When - Calculate ayurveda dosha
        val dosha = ayurvedaCalculator.calculateDosha(
            birthDateTime.year,
            birthDateTime.monthValue,
            birthDateTime.dayOfMonth,
            birthDateTime.hour,
            birthDateTime.minute,
            birthPlace.latitude,
            birthPlace.longitude
        )

        // Then - Verify calculation
        assertThat(dosha).isNotNull()
        assertThat(dosha).isIn(listOf("Vata", "Pitta", "Kapha"))

        // Save profile
        userRepository.saveUserProfile(profile)
        assertThat(userRepository.getUserProfile().first()).isNotNull()
    }

    @Test
    fun endToEnd_humanDesignCalculationFlow() = runTest {
        // Given - Profile with birth data
        val birthDateTime = LocalDateTime.of(1990, 6, 15, 14, 30)
        val birthPlace = BirthPlace(
            city = "San Francisco",
            country = "USA",
            latitude = 37.7749,
            longitude = -122.4194
        )

        val profile = createTestProfile(
            birthDateTime = birthDateTime,
            birthPlace = birthPlace
        )

        // When - Calculate Human Design
        val designType = humanDesignCalculator.calculateType(
            birthDateTime.year,
            birthDateTime.monthValue,
            birthDateTime.dayOfMonth,
            birthDateTime.hour,
            birthDateTime.minute,
            birthPlace.latitude,
            birthPlace.longitude
        )

        val authority = humanDesignCalculator.calculateAuthority(
            birthDateTime.year,
            birthDateTime.monthValue,
            birthDateTime.dayOfMonth,
            birthDateTime.hour,
            birthDateTime.minute,
            birthPlace.latitude,
            birthPlace.longitude
        )

        val profile_hd = humanDesignCalculator.calculateProfile(
            birthDateTime.year,
            birthDateTime.monthValue,
            birthDateTime.dayOfMonth,
            birthDateTime.hour,
            birthDateTime.minute,
            birthPlace.latitude,
            birthPlace.longitude
        )

        // Then - Verify calculations
        assertThat(designType).isNotNull()
        assertThat(designType).isIn(listOf(
            "Manifestor", "Generator", "Manifesting Generator", "Projector", "Reflector"
        ))

        assertThat(authority).isNotNull()
        assertThat(profile_hd).isNotNull()

        // Save profile
        userRepository.saveUserProfile(profile)
        assertThat(userRepository.getUserProfile().first()).isNotNull()
    }

    @Test
    fun endToEnd_compatibilityAnalysisFlow() = runTest {
        // Given - Two profiles
        val profile1 = createTestProfile(
            id = "profile1",
            name = "Alice",
            birthDateTime = LocalDateTime.of(1990, 6, 15, 14, 30)
        )

        val profile2 = createTestProfile(
            id = "profile2",
            name = "Bob",
            birthDateTime = LocalDateTime.of(1992, 3, 20, 10, 15)
        )

        // Save both profiles
        userRepository.saveUserProfile(profile1)
        userRepository.saveUserProfile(profile2)

        // When - Analyze compatibility
        var report: CompatibilityReport? = null
        compatibilityRepository.analyzeCompatibility(profile1, profile2).test {
            skipItems(1) // Skip loading
            val result = awaitItem()
            assertThat(result).isInstanceOf(Result.Success::class.java)
            report = (result as Result.Success).data
            awaitComplete()
        }

        // Then - Verify complete report
        assertThat(report).isNotNull()
        assertThat(report?.profileA?.id).isEqualTo("profile1")
        assertThat(report?.profileB?.id).isEqualTo("profile2")

        // Verify all score categories
        assertThat(report?.overallScore?.overall).isAtLeast(0.0)
        assertThat(report?.overallScore?.overall).isAtMost(100.0)
        assertThat(report?.overallScore?.numerology).isAtLeast(0.0)
        assertThat(report?.overallScore?.astrology).isAtLeast(0.0)
        assertThat(report?.overallScore?.tantric).isAtLeast(0.0)
        assertThat(report?.overallScore?.emotional).isAtLeast(0.0)
        assertThat(report?.overallScore?.intellectual).isAtLeast(0.0)

        // Verify insights
        assertThat(report?.insights).isNotEmpty()
        assertThat(report?.strengths).isNotEmpty()
        assertThat(report?.challenges).isNotEmpty()
        assertThat(report?.recommendations).isNotEmpty()

        // When - Save report
        val saveResult = compatibilityRepository.saveCompatibilityReport(report!!)
        assertThat(saveResult).isInstanceOf(Result.Success::class.java)

        // Then - Verify cached
        compatibilityRepository.getCachedCompatibilityReports("profile1").test {
            val cached = awaitItem()
            assertThat(cached).hasSize(1)
            awaitComplete()
        }
    }

    @Test
    fun endToEnd_multipleProfilesCompatibilityFlow() = runTest {
        // Given - Three profiles
        val profiles = listOf(
            createTestProfile(id = "alice", name = "Alice"),
            createTestProfile(id = "bob", name = "Bob"),
            createTestProfile(id = "charlie", name = "Charlie")
        )

        profiles.forEach { userRepository.saveUserProfile(it) }

        // When - Analyze multiple compatibility pairs
        val pairs = listOf(
            profiles[0] to profiles[1],
            profiles[0] to profiles[2],
            profiles[1] to profiles[2]
        )

        val reports = mutableListOf<CompatibilityReport>()
        pairs.forEach { (profileA, profileB) ->
            compatibilityRepository.analyzeCompatibility(profileA, profileB).test {
                skipItems(1)
                val result = awaitItem()
                reports.add((result as Result.Success).data)
                awaitComplete()
            }
        }

        // Then - Verify all reports
        assertThat(reports).hasSize(3)
        reports.forEach { report ->
            assertThat(report.overallScore.overall).isAtLeast(0.0)
            assertThat(report.insights).isNotEmpty()
        }

        // Save all reports
        reports.forEach { compatibilityRepository.saveCompatibilityReport(it) }

        // Verify Alice has 2 reports
        compatibilityRepository.getCachedCompatibilityReports("alice").test {
            val aliceReports = awaitItem()
            assertThat(aliceReports).hasSize(2)
            awaitComplete()
        }
    }

    @Test
    fun endToEnd_profileSearchAndRetrievalFlow() = runTest {
        // Given - Multiple profiles
        val profiles = listOf(
            createTestProfile(id = "1", name = "Alice Wonderland"),
            createTestProfile(id = "2", name = "Bob Builder"),
            createTestProfile(id = "3", name = "Charlie Chaplin"),
            createTestProfile(id = "4", name = "Diana Prince")
        )

        profiles.forEach { userRepository.saveUserProfile(it) }

        // When - Search by name
        val searchResults = userRepository.searchProfiles("Alice")

        // Then - Verify search
        assertThat(searchResults).hasSize(1)
        assertThat(searchResults[0].name).isEqualTo("Alice Wonderland")

        // When - Get all profiles
        val allProfiles = userRepository.getAllProfiles()

        // Then - Verify all
        assertThat(allProfiles).hasSize(4)
        assertThat(allProfiles.map { it.name }).containsExactly(
            "Alice Wonderland", "Bob Builder", "Charlie Chaplin", "Diana Prince"
        )

        // When - Get profile library
        val library = userRepository.getProfileLibraryEntries()

        // Then - Verify library entries
        assertThat(library).hasSize(4)
        library.forEach { entry ->
            assertThat(entry.id).isNotEmpty()
            assertThat(entry.profileName).isNotEmpty()
            assertThat(entry.completionLevel).isNotNull()
        }
    }

    @Test
    fun endToEnd_profileExportImportFlow() = runTest {
        // Given - Original profile
        val original = createTestProfile(
            id = "original",
            name = "Alice",
            birthDateTime = LocalDateTime.of(1990, 6, 15, 14, 30)
        )

        userRepository.saveUserProfile(original)

        // When - Export profile
        val exportResult = userRepository.exportProfileForSharing("original")
        assertThat(exportResult.isSuccess).isTrue()

        val shareable = exportResult.getOrNull()
        assertThat(shareable).isNotNull()

        // When - Import into new profile
        val importResult = userRepository.importSharedProfile(shareable!!)
        assertThat(importResult.isSuccess).isTrue()

        val imported = importResult.getOrNull()?.importedProfile
        assertThat(imported).isNotNull()

        // Then - Verify imported profile
        val allProfiles = userRepository.getAllProfiles()
        assertThat(allProfiles).hasSize(2) // Original + Imported

        assertThat(imported?.name).isEqualTo("Alice")
        assertThat(imported?.birthDateTime).isEqualTo(original.birthDateTime)
        assertThat(imported?.id).isNotEqualTo(original.id) // Different ID
    }

    @Test
    fun endToEnd_dataLayerTransformationFlow() = runTest {
        // Given - Domain model
        val domainProfile = createTestProfile()

        // When - Save through repository (domain -> entity transformation)
        userRepository.saveUserProfile(domainProfile)

        // Then - Retrieve through repository (entity -> domain transformation)
        val retrieved = userRepository.getUserProfile().first()

        // Verify data integrity through transformation
        assertThat(retrieved?.id).isEqualTo(domainProfile.id)
        assertThat(retrieved?.name).isEqualTo(domainProfile.name)
        assertThat(retrieved?.birthDateTime).isEqualTo(domainProfile.birthDateTime)
        assertThat(retrieved?.birthPlace?.city).isEqualTo(domainProfile.birthPlace?.city)
        assertThat(retrieved?.gender).isEqualTo(domainProfile.gender)
        assertThat(retrieved?.preferences?.numerologySystem)
            .isEqualTo(domainProfile.preferences.numerologySystem)
    }

    @Test
    fun endToEnd_reactiveDataFlowUpdates() = runTest {
        // Given - Initial profile
        val profile = createTestProfile(id = "reactive", name = "Original")

        // When/Then - Monitor reactive updates
        userRepository.getUserProfile().test {
            // Initially null
            assertThat(awaitItem()).isNull()

            // Save first version
            userRepository.saveUserProfile(profile)
            assertThat(awaitItem()?.name).isEqualTo("Original")

            // Update profile
            val updated = profile.copy(name = "Updated")
            userRepository.saveUserProfile(updated)
            assertThat(awaitItem()?.name).isEqualTo("Updated")

            cancelAndIgnoreRemainingEvents()
        }
    }

    // Helper function
    private fun createTestProfile(
        id: String = "test_${System.currentTimeMillis()}",
        name: String = "Test User",
        birthDateTime: LocalDateTime? = LocalDateTime.of(1990, 6, 15, 14, 30),
        birthPlace: BirthPlace? = BirthPlace(
            city = "San Francisco",
            country = "USA",
            latitude = 37.7749,
            longitude = -122.4194
        )
    ): UserProfile {
        return UserProfile(
            id = id,
            profileName = "$name's Profile",
            createdAt = LocalDateTime.now(),
            lastModified = LocalDateTime.now(),
            name = name,
            displayName = name,
            birthDateTime = birthDateTime,
            birthPlace = birthPlace,
            gender = Gender.NON_BINARY,
            preferences = UserPreferences(
                usesSiderealZodiac = false,
                numerologySystem = NumerologySystem.CHALDEAN,
                detailLevel = InsightDetail.COMPREHENSIVE
            )
        )
    }
}
