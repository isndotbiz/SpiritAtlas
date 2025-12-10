package com.spiritatlas.data.repository

import com.google.common.truth.Truth.assertThat
import com.spiritatlas.data.database.dao.UserProfileDao
import com.spiritatlas.data.database.entity.UserProfileEntity
import com.spiritatlas.data.database.mappers.UserProfileMappers.toDomain
import com.spiritatlas.data.database.mappers.UserProfileMappers.toEntity
import com.spiritatlas.domain.model.BirthPlace
import com.spiritatlas.domain.model.UserProfile
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class UserRepositoryImplTest {

    private lateinit var dao: UserProfileDao
    private lateinit var repository: UserRepositoryImpl

    @Before
    fun setUp() {
        dao = mockk(relaxed = true)
        repository = UserRepositoryImpl(dao)
    }

    @Test
    fun `saveUserProfile calls dao with entity`() = runTest {
        val profile = createSampleProfile()
        coEvery { dao.insertOrUpdateProfile(any()) } returns Unit

        repository.saveUserProfile(profile)

        coVerify { dao.insertOrUpdateProfile(any()) }
    }

    @Test
    fun `saveUserProfile calculates profile completion`() = runTest {
        val profile = createSampleProfile().copy(
            name = "Test Name",
            displayName = "Test",
            birthDateTime = LocalDateTime.now()
        )
        coEvery { dao.insertOrUpdateProfile(any()) } returns Unit

        repository.saveUserProfile(profile)

        coVerify {
            dao.insertOrUpdateProfile(match { entity ->
                entity.profileCompletionFields > 0
            })
        }
    }

    @Test
    fun `saveUserProfile throws exception when dao fails`() = runTest {
        val profile = createSampleProfile()
        coEvery { dao.insertOrUpdateProfile(any()) } throws RuntimeException("Database error")

        try {
            repository.saveUserProfile(profile)
            throw AssertionError("Expected exception was not thrown")
        } catch (e: RuntimeException) {
            assertThat(e.message).isEqualTo("Database error")
        }
    }

    @Test
    fun `getUserProfile returns null when no profile exists`() = runTest {
        every { dao.getUserProfileFlow() } returns flowOf(null)

        val result = repository.getUserProfile().first()

        assertThat(result).isNull()
    }

    @Test
    fun `getUserProfile returns profile with completion calculated`() = runTest {
        val entity = createSampleProfile().toEntity()
        every { dao.getUserProfileFlow() } returns flowOf(entity)

        val result = repository.getUserProfile().first()

        assertThat(result).isNotNull()
        assertThat(result?.profileCompletion).isNotNull()
    }

    @Test
    fun `clearUserData calls dao deleteAllProfiles`() = runTest {
        coEvery { dao.deleteAllProfiles() } returns Unit

        repository.clearUserData()

        coVerify { dao.deleteAllProfiles() }
    }

    @Test
    fun `getAllProfiles returns list with completion calculated`() = runTest {
        val entities = listOf(
            createSampleProfile("id1").toEntity(),
            createSampleProfile("id2").toEntity()
        )
        coEvery { dao.getAllProfiles() } returns entities

        val result = repository.getAllProfiles()

        assertThat(result).hasSize(2)
        assertThat(result[0].profileCompletion).isNotNull()
        assertThat(result[1].profileCompletion).isNotNull()
    }

    @Test
    fun `getAllProfilesFlow returns flow with completion calculated`() = runTest {
        val entities = listOf(
            createSampleProfile("id1").toEntity(),
            createSampleProfile("id2").toEntity()
        )
        every { dao.getAllProfilesFlow() } returns flowOf(entities)

        val result = repository.getAllProfilesFlow().first()

        assertThat(result).hasSize(2)
        result.forEach { profile ->
            assertThat(profile.profileCompletion).isNotNull()
        }
    }

    @Test
    fun `getProfileById returns null when profile not found`() = runTest {
        coEvery { dao.getUserProfileById("non-existent") } returns null

        val result = repository.getProfileById("non-existent")

        assertThat(result).isNull()
    }

    @Test
    fun `getProfileById returns profile when found`() = runTest {
        val entity = createSampleProfile("test-id").toEntity()
        coEvery { dao.getUserProfileById("test-id") } returns entity

        val result = repository.getProfileById("test-id")

        assertThat(result).isNotNull()
        assertThat(result?.id).isEqualTo("test-id")
        assertThat(result?.profileCompletion).isNotNull()
    }

    @Test
    fun `getProfileByIdFlow returns null flow when not found`() = runTest {
        every { dao.getUserProfileByIdFlow("non-existent") } returns flowOf(null)

        val result = repository.getProfileByIdFlow("non-existent").first()

        assertThat(result).isNull()
    }

    @Test
    fun `getProfileByIdFlow returns profile when found`() = runTest {
        val entity = createSampleProfile("test-id").toEntity()
        every { dao.getUserProfileByIdFlow("test-id") } returns flowOf(entity)

        val result = repository.getProfileByIdFlow("test-id").first()

        assertThat(result).isNotNull()
        assertThat(result?.id).isEqualTo("test-id")
    }

    @Test
    fun `deleteProfile calls dao with profileId`() = runTest {
        coEvery { dao.deleteProfile("delete-id") } returns Unit

        repository.deleteProfile("delete-id")

        coVerify { dao.deleteProfile("delete-id") }
    }

    @Test
    fun `searchProfiles returns matching profiles`() = runTest {
        val entities = listOf(
            createSampleProfile("id1").copy(name = "Alice").toEntity(),
            createSampleProfile("id2").copy(name = "Bob").toEntity()
        )
        coEvery { dao.searchProfiles("alice") } returns listOf(entities[0])

        val result = repository.searchProfiles("alice")

        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo("Alice")
    }

    @Test
    fun `searchProfiles returns empty list when no matches`() = runTest {
        coEvery { dao.searchProfiles("nonexistent") } returns emptyList()

        val result = repository.searchProfiles("nonexistent")

        assertThat(result).isEmpty()
    }

    @Test
    fun `getProfileCount returns count from dao`() = runTest {
        coEvery { dao.getProfileCount() } returns 5

        val result = repository.getProfileCount()

        assertThat(result).isEqualTo(5)
    }

    @Test
    fun `exportProfileForSharing returns shareable profile`() = runTest {
        val profile = createSampleProfile("export-id")
        coEvery { dao.getUserProfileById("export-id") } returns profile.toEntity()

        val result = repository.exportProfileForSharing("export-id")

        assertThat(result.isSuccess).isTrue()
        val shareableProfile = result.getOrNull()
        assertThat(shareableProfile?.profileName).isEqualTo(profile.profileName)
    }

    @Test
    fun `exportProfileForSharing returns failure when profile not found`() = runTest {
        coEvery { dao.getUserProfileById("non-existent") } returns null

        val result = repository.exportProfileForSharing("non-existent")

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()?.message).contains("not found")
    }

    @Test
    fun `importSharedProfile detects duplicate profiles`() = runTest {
        val existingProfile = createSampleProfile("existing-id")
        val sharedProfile = createShareableProfile(existingProfile.profileName)

        coEvery { dao.getAllProfiles() } returns listOf(existingProfile.toEntity())

        val result = repository.importSharedProfile(sharedProfile)

        assertThat(result.isSuccess).isTrue()
        val importResult = result.getOrNull()
        assertThat(importResult?.isDuplicate).isTrue()
    }

    @Test
    fun `profile completion calculation counts non-null fields`() = runTest {
        val profileWithMinFields = createSampleProfile().copy(
            name = "Test",
            displayName = "Test",
            birthDateTime = LocalDateTime.now()
        )
        coEvery { dao.insertOrUpdateProfile(any()) } returns Unit

        repository.saveUserProfile(profileWithMinFields)

        coVerify {
            dao.insertOrUpdateProfile(match { entity ->
                entity.profileCompletionFields >= 3 // At least name, displayName, birthDateTime
            })
        }
    }

    private fun createSampleProfile(id: String = "test-id"): UserProfile {
        val now = LocalDateTime.of(2024, 1, 1, 12, 0)
        return UserProfile(
            id = id,
            profileName = "Test Profile",
            createdAt = now,
            lastModified = now,
            name = "Test Name",
            displayName = "Test",
            birthDateTime = now,
            birthPlace = BirthPlace(
                city = "Test City",
                country = "US",
                latitude = 0.0,
                longitude = 0.0
            )
        )
    }

    private fun createShareableProfile(name: String): com.spiritatlas.domain.model.ShareableProfile {
        return com.spiritatlas.domain.model.ShareableProfile(
            profileName = name,
            basicInfo = com.spiritatlas.domain.model.ShareableBasicInfo(
                firstName = "Test",
                birthDateTime = LocalDateTime.now(),
                birthPlace = BirthPlace("City", "US", 0.0, 0.0),
                gender = null,
                loveLanguage = null,
                personalityType = null,
                sexualEnergy = null,
                communicationStyle = null
            ),
            enrichmentSummary = null,
            compatibilityReadiness = true,
            sharedAt = LocalDateTime.now(),
            sharedBy = "User"
        )
    }
}
