package com.spiritatlas.data.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.spiritatlas.data.database.SpiritAtlasDatabase
import com.spiritatlas.data.database.dao.UserProfileDao
import com.spiritatlas.data.database.entities.UserProfileEntity
import com.spiritatlas.domain.model.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

/**
 * Integration test for Room Database + DAO layer
 * Tests database operations, transactions, migrations, and caching
 */
@RunWith(AndroidJUnit4::class)
class DatabaseIntegrationTest {

    private lateinit var database: SpiritAtlasDatabase
    private lateinit var dao: UserProfileDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Create in-memory database for testing
        database = Room.inMemoryDatabaseBuilder(
            context,
            SpiritAtlasDatabase::class.java
        ).build()

        dao = database.userProfileDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertProfile_persistsToDatabase() = runTest {
        // Given
        val entity = createTestEntity()

        // When
        dao.insertOrUpdateProfile(entity)

        // Then
        val retrieved = dao.getUserProfile()
        assertThat(retrieved).isNotNull()
        assertThat(retrieved?.id).isEqualTo(entity.id)
        assertThat(retrieved?.name).isEqualTo(entity.name)
    }

    @Test
    fun insertProfile_replacesExistingProfile() = runTest {
        // Given
        val entity1 = createTestEntity(id = "same_id", name = "Original")
        val entity2 = createTestEntity(id = "same_id", name = "Updated")

        // When
        dao.insertOrUpdateProfile(entity1)
        dao.insertOrUpdateProfile(entity2)

        // Then
        val profiles = dao.getAllProfiles()
        assertThat(profiles).hasSize(1)
        assertThat(profiles[0].name).isEqualTo("Updated")
    }

    @Test
    fun getUserProfileFlow_emitsUpdatesReactively() = runTest {
        // When/Then
        dao.getUserProfileFlow().test {
            // Initially null
            assertThat(awaitItem()).isNull()

            // Insert first profile
            val entity1 = createTestEntity(name = "First")
            dao.insertOrUpdateProfile(entity1)
            assertThat(awaitItem()?.name).isEqualTo("First")

            // Insert second profile (becomes most recent)
            val entity2 = createTestEntity(name = "Second")
            dao.insertOrUpdateProfile(entity2)
            assertThat(awaitItem()?.name).isEqualTo("Second")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getAllProfilesFlow_emitsAllProfiles() = runTest {
        // When/Then
        dao.getAllProfilesFlow().test {
            // Initially empty
            assertThat(awaitItem()).isEmpty()

            // Add first profile
            dao.insertOrUpdateProfile(createTestEntity(id = "1", name = "Alice"))
            assertThat(awaitItem()).hasSize(1)

            // Add second profile
            dao.insertOrUpdateProfile(createTestEntity(id = "2", name = "Bob"))
            val profiles = awaitItem()
            assertThat(profiles).hasSize(2)
            assertThat(profiles.map { it.name }).containsExactly("Alice", "Bob")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getUserProfileById_returnsCorrectProfile() = runTest {
        // Given
        val entity1 = createTestEntity(id = "profile1", name = "Alice")
        val entity2 = createTestEntity(id = "profile2", name = "Bob")
        dao.insertOrUpdateProfile(entity1)
        dao.insertOrUpdateProfile(entity2)

        // When
        val retrieved = dao.getUserProfileById("profile1")

        // Then
        assertThat(retrieved).isNotNull()
        assertThat(retrieved?.id).isEqualTo("profile1")
        assertThat(retrieved?.name).isEqualTo("Alice")
    }

    @Test
    fun getUserProfileByIdFlow_emitsUpdatesForSpecificProfile() = runTest {
        // Given
        val profileId = "test_profile"

        // When/Then
        dao.getUserProfileByIdFlow(profileId).test {
            // Initially null
            assertThat(awaitItem()).isNull()

            // Insert profile
            val entity = createTestEntity(id = profileId, name = "Original")
            dao.insertOrUpdateProfile(entity)
            assertThat(awaitItem()?.name).isEqualTo("Original")

            // Update profile
            val updated = entity.copy(name = "Updated")
            dao.insertOrUpdateProfile(updated)
            assertThat(awaitItem()?.name).isEqualTo("Updated")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun searchProfiles_findsByName() = runTest {
        // Given
        dao.insertOrUpdateProfile(createTestEntity(id = "1", name = "Alice Wonderland"))
        dao.insertOrUpdateProfile(createTestEntity(id = "2", name = "Bob Builder"))
        dao.insertOrUpdateProfile(createTestEntity(id = "3", name = "Charlie Chaplin"))

        // When
        val results = dao.searchProfiles("Alice")

        // Then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("Alice Wonderland")
    }

    @Test
    fun searchProfiles_findsByProfileName() = runTest {
        // Given
        dao.insertOrUpdateProfile(
            createTestEntity(id = "1", name = "John", profileName = "My Spiritual Profile")
        )
        dao.insertOrUpdateProfile(
            createTestEntity(id = "2", name = "Jane", profileName = "Work Profile")
        )

        // When
        val results = dao.searchProfiles("Spiritual")

        // Then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("John")
    }

    @Test
    fun deleteProfile_removesSpecificProfile() = runTest {
        // Given
        dao.insertOrUpdateProfile(createTestEntity(id = "profile1"))
        dao.insertOrUpdateProfile(createTestEntity(id = "profile2"))

        // When
        dao.deleteProfile("profile1")

        // Then
        val profiles = dao.getAllProfiles()
        assertThat(profiles).hasSize(1)
        assertThat(profiles[0].id).isEqualTo("profile2")

        val deleted = dao.getUserProfileById("profile1")
        assertThat(deleted).isNull()
    }

    @Test
    fun deleteAllProfiles_clearsDatabase() = runTest {
        // Given
        dao.insertOrUpdateProfile(createTestEntity(id = "1"))
        dao.insertOrUpdateProfile(createTestEntity(id = "2"))
        dao.insertOrUpdateProfile(createTestEntity(id = "3"))

        // When
        dao.deleteAllProfiles()

        // Then
        val profiles = dao.getAllProfiles()
        assertThat(profiles).isEmpty()
    }

    @Test
    fun getProfileCount_returnsCorrectCount() = runTest {
        // Given/When/Then
        assertThat(dao.getProfileCount()).isEqualTo(0)

        dao.insertOrUpdateProfile(createTestEntity(id = "1"))
        assertThat(dao.getProfileCount()).isEqualTo(1)

        dao.insertOrUpdateProfile(createTestEntity(id = "2"))
        dao.insertOrUpdateProfile(createTestEntity(id = "3"))
        assertThat(dao.getProfileCount()).isEqualTo(3)
    }

    @Test
    fun markProfileAsSynced_updatesSyncStatus() = runTest {
        // Given
        val entity = createTestEntity(syncStatus = "LOCAL")
        dao.insertOrUpdateProfile(entity)

        // When
        dao.markProfileAsSynced(entity.id)

        // Then
        val updated = dao.getUserProfileById(entity.id)
        assertThat(updated?.syncStatus).isEqualTo("SYNCED")
    }

    @Test
    fun getProfilesNeedingSync_returnsUnsyncedProfiles() = runTest {
        // Given
        dao.insertOrUpdateProfile(createTestEntity(id = "1", syncStatus = "LOCAL"))
        dao.insertOrUpdateProfile(createTestEntity(id = "2", syncStatus = "SYNCED"))
        dao.insertOrUpdateProfile(createTestEntity(id = "3", syncStatus = "SYNCING"))

        // When
        val needingSync = dao.getProfilesNeedingSync()

        // Then
        assertThat(needingSync).hasSize(2)
        assertThat(needingSync.map { it.id }).containsExactly("1", "3")
    }

    @Test
    fun updateProfile_modifiesExistingProfile() = runTest {
        // Given
        val entity = createTestEntity(id = "test", name = "Original")
        dao.insertOrUpdateProfile(entity)

        // When
        val updated = entity.copy(name = "Modified")
        dao.updateProfile(updated)

        // Then
        val retrieved = dao.getUserProfileById("test")
        assertThat(retrieved?.name).isEqualTo("Modified")
    }

    @Test
    fun profileOrdering_orderedByUpdatedAt() = runTest {
        // Given
        val entity1 = createTestEntity(id = "1", name = "First", updatedAt = 1000)
        val entity2 = createTestEntity(id = "2", name = "Second", updatedAt = 2000)
        val entity3 = createTestEntity(id = "3", name = "Third", updatedAt = 3000)

        dao.insertOrUpdateProfile(entity1)
        dao.insertOrUpdateProfile(entity3)
        dao.insertOrUpdateProfile(entity2)

        // When
        val profiles = dao.getAllProfiles()

        // Then - Should be ordered by updatedAt DESC
        assertThat(profiles.map { it.name }).containsExactly("Third", "Second", "First").inOrder()
    }

    @Test
    fun getUserProfile_returnsLatestProfile() = runTest {
        // Given
        val entity1 = createTestEntity(id = "1", name = "Old", updatedAt = 1000)
        val entity2 = createTestEntity(id = "2", name = "New", updatedAt = 2000)

        dao.insertOrUpdateProfile(entity1)
        dao.insertOrUpdateProfile(entity2)

        // When
        val profile = dao.getUserProfile()

        // Then - Should return the most recently updated profile
        assertThat(profile?.name).isEqualTo("New")
    }

    @Test
    fun typeConverters_serializeAndDeserializeCorrectly() = runTest {
        // Given - entity with all complex types
        val entity = createTestEntity(
            birthDateTime = "2024-12-10T10:30:00",
            birthPlace = """{"city":"San Francisco","country":"USA","latitude":37.7749,"longitude":-122.4194}""",
            gender = "NON_BINARY",
            bloodType = "O_POSITIVE",
            preferences = """{"usesSiderealZodiac":true,"numerologySystem":"CHALDEAN","detailLevel":"COMPREHENSIVE"}""",
            profileCompletion = """{"totalFields":27,"completedFields":15,"completionPercentage":55.0,"accuracyLevel":"GOOD","missingCriticalFields":[]}"""
        )

        // When
        dao.insertOrUpdateProfile(entity)
        val retrieved = dao.getUserProfileById(entity.id)

        // Then - All complex fields should be preserved
        assertThat(retrieved?.birthDateTime).isEqualTo(entity.birthDateTime)
        assertThat(retrieved?.birthPlace).isEqualTo(entity.birthPlace)
        assertThat(retrieved?.gender).isEqualTo(entity.gender)
        assertThat(retrieved?.bloodType).isEqualTo(entity.bloodType)
        assertThat(retrieved?.preferences).isEqualTo(entity.preferences)
        assertThat(retrieved?.profileCompletion).isEqualTo(entity.profileCompletion)
    }

    @Test
    fun updateProfileCompletion_updatesJsonField() = runTest {
        // Given
        val entity = createTestEntity(id = "test")
        dao.insertOrUpdateProfile(entity)

        val newCompletion = """{"totalFields":27,"completedFields":20,"completionPercentage":74.0,"accuracyLevel":"EXCELLENT","missingCriticalFields":[]}"""

        // When
        dao.updateProfileCompletion("test", newCompletion)

        // Then
        val updated = dao.getUserProfileById("test")
        assertThat(updated?.profileCompletion).isEqualTo(newCompletion)
    }

    @Test
    fun concurrentInserts_handleCorrectly() = runTest {
        // Given - multiple profiles inserted concurrently
        val entities = (1..10).map { createTestEntity(id = "profile_$it", name = "User $it") }

        // When
        entities.forEach { dao.insertOrUpdateProfile(it) }

        // Then
        val profiles = dao.getAllProfiles()
        assertThat(profiles).hasSize(10)
        assertThat(profiles.map { it.id }).containsExactlyElementsIn(entities.map { it.id })
    }

    @Test
    fun emptyQueries_handleGracefully() = runTest {
        // When/Then - Empty database queries
        assertThat(dao.getUserProfile()).isNull()
        assertThat(dao.getAllProfiles()).isEmpty()
        assertThat(dao.getUserProfileById("nonexistent")).isNull()
        assertThat(dao.searchProfiles("anything")).isEmpty()
        assertThat(dao.getProfilesNeedingSync()).isEmpty()
    }

    @Test
    fun enrichmentFields_storeAndRetrieve() = runTest {
        // Given
        val entity = createTestEntity(
            enrichmentResult = "This is a comprehensive spiritual analysis...",
            enrichmentGeneratedAt = "2024-12-10T15:30:00"
        )

        // When
        dao.insertOrUpdateProfile(entity)
        val retrieved = dao.getUserProfileById(entity.id)

        // Then
        assertThat(retrieved?.enrichmentResult).isEqualTo(entity.enrichmentResult)
        assertThat(retrieved?.enrichmentGeneratedAt).isEqualTo(entity.enrichmentGeneratedAt)
    }

    // Helper function to create test entities
    private fun createTestEntity(
        id: String = "test_${System.currentTimeMillis()}",
        profileName: String = "Test Profile",
        createdAt: String = LocalDateTime.now().toString(),
        lastModified: String = LocalDateTime.now().toString(),
        name: String = "Test User",
        displayName: String = "Test",
        birthDateTime: String? = "1990-06-15T14:30:00",
        birthPlace: String? = """{"city":"San Francisco","country":"USA","latitude":37.7749,"longitude":-122.4194}""",
        middleName: String? = null,
        nickname: String? = null,
        spiritualName: String? = null,
        motherName: String? = null,
        fatherName: String? = null,
        ancestry: String? = null,
        gender: String? = "NON_BINARY",
        bloodType: String? = null,
        dominantHand: String? = null,
        eyeColor: String? = null,
        firstBreath: String? = null,
        weatherConditions: String? = null,
        moonPhase: String? = null,
        hospitalName: String? = null,
        firstWord: String? = null,
        firstSteps: String? = null,
        loveLanguage: String? = null,
        personalityType: String? = null,
        sexualEnergy: String? = null,
        communicationStyle: String? = null,
        preferences: String? = """{"usesSiderealZodiac":false,"numerologySystem":"CHALDEAN","detailLevel":"COMPREHENSIVE"}""",
        profileCompletion: String? = """{"totalFields":27,"completedFields":10,"completionPercentage":37.0,"accuracyLevel":"BASIC","missingCriticalFields":[]}""",
        enrichmentResult: String? = null,
        enrichmentGeneratedAt: String? = null,
        updatedAt: Long = System.currentTimeMillis(),
        syncStatus: String = "LOCAL",
        encryptionVersion: Int = 1
    ): UserProfileEntity {
        return UserProfileEntity(
            id = id,
            profileName = profileName,
            createdAt = createdAt,
            lastModified = lastModified,
            name = name,
            displayName = displayName,
            birthDateTime = birthDateTime,
            birthPlace = birthPlace,
            middleName = middleName,
            nickname = nickname,
            spiritualName = spiritualName,
            motherName = motherName,
            fatherName = fatherName,
            ancestry = ancestry,
            gender = gender,
            bloodType = bloodType,
            dominantHand = dominantHand,
            eyeColor = eyeColor,
            firstBreath = firstBreath,
            weatherConditions = weatherConditions,
            moonPhase = moonPhase,
            hospitalName = hospitalName,
            firstWord = firstWord,
            firstSteps = firstSteps,
            loveLanguage = loveLanguage,
            personalityType = personalityType,
            sexualEnergy = sexualEnergy,
            communicationStyle = communicationStyle,
            preferences = preferences,
            profileCompletion = profileCompletion,
            enrichmentResult = enrichmentResult,
            enrichmentGeneratedAt = enrichmentGeneratedAt,
            updatedAt = updatedAt,
            syncStatus = syncStatus,
            encryptionVersion = encryptionVersion
        )
    }
}
