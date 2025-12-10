package com.spiritatlas.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.spiritatlas.data.database.SpiritAtlasDatabase
import com.spiritatlas.data.database.dao.UserProfileDao
import com.spiritatlas.domain.model.*
import com.spiritatlas.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

/**
 * Integration test for UserRepository with Room database
 * Tests data flow from Repository through DAO to Database
 */
@RunWith(AndroidJUnit4::class)
class ProfileRepositoryIntegrationTest {

    private lateinit var database: SpiritAtlasDatabase
    private lateinit var userProfileDao: UserProfileDao
    private lateinit var repository: UserRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Create in-memory database for testing
        database = Room.inMemoryDatabaseBuilder(
            context,
            SpiritAtlasDatabase::class.java
        ).build()

        userProfileDao = database.userProfileDao()
        repository = UserRepositoryImpl(userProfileDao)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun saveUserProfile_persistsToDatabase() = runTest {
        // Given
        val profile = createTestProfile()

        // When
        repository.saveUserProfile(profile)

        // Then
        val savedProfile = repository.getUserProfile().first()
        assertThat(savedProfile).isNotNull()
        assertThat(savedProfile?.id).isEqualTo(profile.id)
        assertThat(savedProfile?.name).isEqualTo(profile.name)
        assertThat(savedProfile?.profileName).isEqualTo(profile.profileName)
    }

    @Test
    fun saveUserProfile_calculatesProfileCompletion() = runTest {
        // Given
        val profile = createTestProfile()

        // When
        repository.saveUserProfile(profile)

        // Then
        val savedProfile = repository.getUserProfile().first()
        assertThat(savedProfile).isNotNull()
        assertThat(savedProfile?.profileCompletion).isNotNull()
        assertThat(savedProfile?.profileCompletion?.completedFields).isGreaterThan(0)
    }

    @Test
    fun getUserProfile_emitsUpdatesReactively() = runTest {
        // Given
        val profile1 = createTestProfile(name = "First Profile")
        val profile2 = createTestProfile(name = "Updated Profile")

        // When/Then
        repository.getUserProfile().test {
            // Initially no profile
            assertThat(awaitItem()).isNull()

            // Save first profile
            repository.saveUserProfile(profile1)
            val firstEmission = awaitItem()
            assertThat(firstEmission?.name).isEqualTo("First Profile")

            // Update with second profile
            repository.saveUserProfile(profile2)
            val secondEmission = awaitItem()
            assertThat(secondEmission?.name).isEqualTo("Updated Profile")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun clearUserData_removesAllProfiles() = runTest {
        // Given
        val profile = createTestProfile()
        repository.saveUserProfile(profile)

        // When
        repository.clearUserData()

        // Then
        val profiles = repository.getAllProfiles()
        assertThat(profiles).isEmpty()
    }

    @Test
    fun getAllProfiles_returnsMultipleProfiles() = runTest {
        // Given
        val profile1 = createTestProfile(id = "profile1", name = "Alice")
        val profile2 = createTestProfile(id = "profile2", name = "Bob")
        val profile3 = createTestProfile(id = "profile3", name = "Charlie")

        // When
        repository.saveUserProfile(profile1)
        repository.saveUserProfile(profile2)
        repository.saveUserProfile(profile3)

        // Then
        val profiles = repository.getAllProfiles()
        assertThat(profiles).hasSize(3)
        assertThat(profiles.map { it.name }).containsExactly("Alice", "Bob", "Charlie")
    }

    @Test
    fun getProfileById_returnsCorrectProfile() = runTest {
        // Given
        val profile1 = createTestProfile(id = "profile1", name = "Alice")
        val profile2 = createTestProfile(id = "profile2", name = "Bob")
        repository.saveUserProfile(profile1)
        repository.saveUserProfile(profile2)

        // When
        val retrieved = repository.getProfileById("profile1")

        // Then
        assertThat(retrieved).isNotNull()
        assertThat(retrieved?.id).isEqualTo("profile1")
        assertThat(retrieved?.name).isEqualTo("Alice")
    }

    @Test
    fun deleteProfile_removesSpecificProfile() = runTest {
        // Given
        val profile1 = createTestProfile(id = "profile1", name = "Alice")
        val profile2 = createTestProfile(id = "profile2", name = "Bob")
        repository.saveUserProfile(profile1)
        repository.saveUserProfile(profile2)

        // When
        repository.deleteProfile("profile1")

        // Then
        val profiles = repository.getAllProfiles()
        assertThat(profiles).hasSize(1)
        assertThat(profiles[0].name).isEqualTo("Bob")

        val deleted = repository.getProfileById("profile1")
        assertThat(deleted).isNull()
    }

    @Test
    fun searchProfiles_findsMatchingProfiles() = runTest {
        // Given
        val profile1 = createTestProfile(id = "1", name = "Alice Wonderland")
        val profile2 = createTestProfile(id = "2", name = "Bob Builder")
        val profile3 = createTestProfile(id = "3", name = "Charlie Chaplin")
        repository.saveUserProfile(profile1)
        repository.saveUserProfile(profile2)
        repository.saveUserProfile(profile3)

        // When
        val results = repository.searchProfiles("Alice")

        // Then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("Alice Wonderland")
    }

    @Test
    fun getProfileCount_returnsCorrectCount() = runTest {
        // Given
        val profile1 = createTestProfile(id = "1")
        val profile2 = createTestProfile(id = "2")
        val profile3 = createTestProfile(id = "3")

        // When/Then
        assertThat(repository.getProfileCount()).isEqualTo(0)

        repository.saveUserProfile(profile1)
        assertThat(repository.getProfileCount()).isEqualTo(1)

        repository.saveUserProfile(profile2)
        repository.saveUserProfile(profile3)
        assertThat(repository.getProfileCount()).isEqualTo(3)
    }

    @Test
    fun exportProfileForSharing_createsShareableProfile() = runTest {
        // Given
        val profile = createTestProfile()
        repository.saveUserProfile(profile)

        // When
        val result = repository.exportProfileForSharing(profile.id)

        // Then
        assertThat(result.isSuccess).isTrue()
        val shareable = result.getOrNull()
        assertThat(shareable).isNotNull()
        assertThat(shareable?.profileName).isEqualTo(profile.profileName)
        assertThat(shareable?.basicInfo?.firstName).isEqualTo(profile.name)
    }

    @Test
    fun importSharedProfile_createsNewProfile() = runTest {
        // Given
        val sharedProfile = ShareableProfile(
            profileName = "Shared Profile",
            basicInfo = ShareableBasicInfo(
                firstName = "Shared",
                birthDateTime = LocalDateTime.of(1990, 1, 1, 12, 0),
                birthPlace = BirthPlace(
                    city = "San Francisco",
                    country = "USA",
                    latitude = 37.7749,
                    longitude = -122.4194
                ),
                gender = Gender.NON_BINARY
            ),
            enrichmentSummary = "Test enrichment",
            compatibilityReadiness = true,
            sharedAt = LocalDateTime.now(),
            sharedBy = "Test User"
        )

        // When
        val result = repository.importSharedProfile(sharedProfile)

        // Then
        assertThat(result.isSuccess).isTrue()
        val importResult = result.getOrNull()
        assertThat(importResult?.success).isTrue()
        assertThat(importResult?.importedProfile).isNotNull()

        val profiles = repository.getAllProfiles()
        assertThat(profiles).hasSize(1)
        assertThat(profiles[0].name).isEqualTo("Shared")
    }

    @Test
    fun importSharedProfile_rejectsDuplicateNames() = runTest {
        // Given
        val profile = createTestProfile(profileName = "Test Profile")
        repository.saveUserProfile(profile)

        val sharedProfile = ShareableProfile(
            profileName = "Test Profile",
            basicInfo = ShareableBasicInfo(
                firstName = "Duplicate",
                birthDateTime = LocalDateTime.of(1990, 1, 1, 12, 0),
                birthPlace = BirthPlace(
                    city = "San Francisco",
                    country = "USA",
                    latitude = 37.7749,
                    longitude = -122.4194
                ),
                gender = Gender.NON_BINARY
            ),
            enrichmentSummary = null,
            compatibilityReadiness = false,
            sharedAt = LocalDateTime.now(),
            sharedBy = "Test User"
        )

        // When
        val result = repository.importSharedProfile(sharedProfile)

        // Then
        assertThat(result.isSuccess).isTrue()
        val importResult = result.getOrNull()
        assertThat(importResult?.success).isFalse()
        assertThat(importResult?.errorMessage).contains("already exists")
    }

    @Test
    fun getProfileLibraryEntries_returnsAllProfiles() = runTest {
        // Given
        val profile1 = createTestProfile(id = "1", name = "Alice")
        val profile2 = createTestProfile(id = "2", name = "Bob")
        repository.saveUserProfile(profile1)
        repository.saveUserProfile(profile2)

        // When
        val entries = repository.getProfileLibraryEntries()

        // Then
        assertThat(entries).hasSize(2)
        assertThat(entries.map { it.displayName }).containsExactly("Alice", "Bob")
    }

    @Test
    fun profileCompletion_tracksFieldsAccurately() = runTest {
        // Given - minimal profile
        val minimalProfile = createTestProfile(
            name = null,
            birthDateTime = null,
            birthPlace = null
        )

        // When
        repository.saveUserProfile(minimalProfile)

        // Then
        val saved = repository.getUserProfile().first()
        assertThat(saved?.profileCompletion?.accuracyLevel).isEqualTo(AccuracyLevel.MINIMAL)
        assertThat(saved?.profileCompletion?.missingCriticalFields).contains("name")

        // Given - complete profile
        val completeProfile = createTestProfile().copy(
            id = "complete",
            middleName = "Marie",
            nickname = "Ally",
            spiritualName = "Aura",
            motherName = "Mary",
            fatherName = "John",
            ancestry = "European",
            bloodType = BloodType.O_POSITIVE,
            dominantHand = "Right",
            eyeColor = "Blue",
            firstBreath = LocalDateTime.now(),
            weatherConditions = "Clear",
            moonPhase = "Full",
            hospitalName = "General Hospital",
            firstWord = "Mama",
            firstSteps = LocalDateTime.now().minusYears(20)
        )

        // When
        repository.saveUserProfile(completeProfile)

        // Then
        val completeSaved = repository.getProfileById("complete")
        assertThat(completeSaved?.profileCompletion?.completedFields).isGreaterThan(20)
        assertThat(completeSaved?.profileCompletion?.missingCriticalFields).isEmpty()
    }

    // Helper function to create test profiles
    private fun createTestProfile(
        id: String = "test_profile_1",
        name: String? = "Test User",
        profileName: String = "Test Profile",
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
            profileName = profileName,
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
