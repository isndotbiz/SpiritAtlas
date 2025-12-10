package com.spiritatlas.feature.profile

import com.google.common.truth.Truth.assertThat
import com.spiritatlas.domain.model.BirthPlace
import com.spiritatlas.domain.model.UserProfile
import com.spiritatlas.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class ProfileViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        userRepository = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading`() {
        viewModel = ProfileViewModel(userRepository)

        val state = viewModel.uiState.value
        assertThat(state.isLoading).isTrue()
        assertThat(state.isSaving).isFalse()
        assertThat(state.saveSuccess).isFalse()
        assertThat(state.errorMessage).isNull()
    }

    @Test
    fun `loadProfile with null id creates empty profile`() = runTest {
        viewModel = ProfileViewModel(userRepository)

        viewModel.loadProfile(null)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.currentProfile.name).isNull()
        assertThat(state.currentProfile.displayName).isNull()
        assertThat(state.currentProfile.birthDateTime).isNull()
    }

    @Test
    fun `loadProfile with new id creates empty profile`() = runTest {
        viewModel = ProfileViewModel(userRepository)

        viewModel.loadProfile("new")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.currentProfile.name).isNull()
        assertThat(state.errorMessage).isNull()
    }

    @Test
    fun `loadProfile with existing id loads profile from repository`() = runTest {
        val existingProfile = createSampleProfile("test-id")
        coEvery { userRepository.getProfileById("test-id") } returns existingProfile

        viewModel = ProfileViewModel(userRepository)
        viewModel.loadProfile("test-id")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.currentProfile).isEqualTo(existingProfile)
        assertThat(state.errorMessage).isNull()

        coVerify { userRepository.getProfileById("test-id") }
    }

    @Test
    fun `loadProfile with non-existent id creates empty profile`() = runTest {
        coEvery { userRepository.getProfileById("non-existent") } returns null

        viewModel = ProfileViewModel(userRepository)
        viewModel.loadProfile("non-existent")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.currentProfile.name).isNull()
    }

    @Test
    fun `loadProfile handles repository exception gracefully`() = runTest {
        coEvery { userRepository.getProfileById("error-id") } throws RuntimeException("Database error")

        viewModel = ProfileViewModel(userRepository)
        viewModel.loadProfile("error-id")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.errorMessage).contains("Failed to load profile")
        assertThat(state.errorMessage).contains("Database error")
    }

    @Test
    fun `updateProfile updates current profile in state`() = runTest {
        viewModel = ProfileViewModel(userRepository)
        viewModel.loadProfile(null)
        advanceUntilIdle()

        val updatedProfile = viewModel.uiState.value.currentProfile.copy(name = "Updated Name")
        viewModel.updateProfile(updatedProfile)

        val state = viewModel.uiState.value
        assertThat(state.currentProfile.name).isEqualTo("Updated Name")
    }

    @Test
    fun `saveProfile saves to repository and shows success`() = runTest {
        coEvery { userRepository.saveUserProfile(any()) } returns Unit

        viewModel = ProfileViewModel(userRepository)
        viewModel.loadProfile(null)
        advanceUntilIdle()

        viewModel.saveProfile()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.isSaving).isFalse()
        assertThat(state.saveSuccess).isTrue()
        assertThat(state.errorMessage).isNull()

        coVerify { userRepository.saveUserProfile(any()) }
    }

    @Test
    fun `saveProfile sets isSaving during save operation`() = runTest {
        coEvery { userRepository.saveUserProfile(any()) } coAnswers {
            kotlinx.coroutines.delay(100)
        }

        viewModel = ProfileViewModel(userRepository)
        viewModel.loadProfile(null)
        advanceUntilIdle()

        viewModel.saveProfile()
        advanceTimeBy(50) // Advance partway through save

        assertThat(viewModel.uiState.value.isSaving).isTrue()

        advanceUntilIdle()
        assertThat(viewModel.uiState.value.isSaving).isFalse()
    }

    @Test
    fun `saveProfile clears success message after delay`() = runTest {
        coEvery { userRepository.saveUserProfile(any()) } returns Unit

        viewModel = ProfileViewModel(userRepository)
        viewModel.loadProfile(null)
        advanceUntilIdle()

        viewModel.saveProfile()
        advanceUntilIdle()

        assertThat(viewModel.uiState.value.saveSuccess).isTrue()

        // Advance time to trigger success message clear
        advanceTimeBy(3000)
        advanceUntilIdle()

        assertThat(viewModel.uiState.value.saveSuccess).isFalse()
    }

    @Test
    fun `saveProfile handles save exception`() = runTest {
        coEvery { userRepository.saveUserProfile(any()) } throws RuntimeException("Save failed")

        viewModel = ProfileViewModel(userRepository)
        viewModel.loadProfile(null)
        advanceUntilIdle()

        viewModel.saveProfile()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.isSaving).isFalse()
        assertThat(state.saveSuccess).isFalse()
        assertThat(state.errorMessage).contains("Failed to save profile")
        assertThat(state.errorMessage).contains("Save failed")
    }

    @Test
    fun `clearProfile creates new empty profile`() = runTest {
        viewModel = ProfileViewModel(userRepository)
        viewModel.loadProfile(null)
        advanceUntilIdle()

        // Update profile with data
        val updatedProfile = viewModel.uiState.value.currentProfile.copy(name = "Test Name")
        viewModel.updateProfile(updatedProfile)
        assertThat(viewModel.uiState.value.currentProfile.name).isEqualTo("Test Name")

        // Clear profile
        viewModel.clearProfile()

        val state = viewModel.uiState.value
        assertThat(state.currentProfile.name).isNull()
        assertThat(state.currentProfile.displayName).isNull()
    }

    @Test
    fun `setActiveSection updates active section`() = runTest {
        viewModel = ProfileViewModel(userRepository)

        assertThat(viewModel.uiState.value.activeSection).isEqualTo(ProfileSection.CORE)

        viewModel.setActiveSection(ProfileSection.FAMILY)
        assertThat(viewModel.uiState.value.activeSection).isEqualTo(ProfileSection.FAMILY)

        viewModel.setActiveSection(ProfileSection.PREFERENCES)
        assertThat(viewModel.uiState.value.activeSection).isEqualTo(ProfileSection.PREFERENCES)
    }

    @Test
    fun `clearError removes error message`() = runTest {
        coEvery { userRepository.getProfileById(any()) } throws RuntimeException("Test error")

        viewModel = ProfileViewModel(userRepository)
        viewModel.loadProfile("error-id")
        advanceUntilIdle()

        assertThat(viewModel.uiState.value.errorMessage).isNotNull()

        viewModel.clearError()
        assertThat(viewModel.uiState.value.errorMessage).isNull()
    }

    @Test
    fun `loadDemoProfile loads sample profile`() = runTest {
        viewModel = ProfileViewModel(userRepository)

        viewModel.loadDemoProfile()

        val state = viewModel.uiState.value
        assertThat(state.currentProfile.name).isNotNull()
        assertThat(state.currentProfile.displayName).isNotNull()
    }

    @Test
    fun `loadTier0Profile generates tier 0 profile`() = runTest {
        viewModel = ProfileViewModel(userRepository)

        viewModel.loadTier0Profile()

        val profile = viewModel.uiState.value.currentProfile
        assertThat(profile.name).isNotNull()
        // Tier 0 should have minimal fields
        assertThat(profile.profileCompletion.completedFields).isAtLeast(3)
    }

    @Test
    fun `loadTier1Profile generates tier 1 profile`() = runTest {
        viewModel = ProfileViewModel(userRepository)

        viewModel.loadTier1Profile()

        val profile = viewModel.uiState.value.currentProfile
        assertThat(profile.name).isNotNull()
        // Tier 1 should have more fields than tier 0
        assertThat(profile.profileCompletion.completedFields).isAtLeast(10)
    }

    @Test
    fun `loadTier2Profile generates tier 2 profile`() = runTest {
        viewModel = ProfileViewModel(userRepository)

        viewModel.loadTier2Profile()

        val profile = viewModel.uiState.value.currentProfile
        assertThat(profile.name).isNotNull()
        assertThat(profile.profileCompletion.completedFields).isAtLeast(19)
    }

    @Test
    fun `loadTier3Profile generates tier 3 profile`() = runTest {
        viewModel = ProfileViewModel(userRepository)

        viewModel.loadTier3Profile()

        val profile = viewModel.uiState.value.currentProfile
        assertThat(profile.name).isNotNull()
        assertThat(profile.profileCompletion.completedFields).isAtLeast(28)
    }

    @Test
    fun `generateCompatibilityReport with valid profiles navigates`() = runTest {
        val profile1 = createSampleProfile("id1").copy(
            profileCompletion = com.spiritatlas.domain.model.ProfileCompletion(
                completedFields = 5,
                totalFields = 36
            )
        )
        val profile2 = createSampleProfile("id2").copy(
            profileCompletion = com.spiritatlas.domain.model.ProfileCompletion(
                completedFields = 5,
                totalFields = 36
            )
        )

        coEvery { userRepository.getProfileById("id1") } returns profile1
        coEvery { userRepository.getProfileById("id2") } returns profile2

        viewModel = ProfileViewModel(userRepository)
        viewModel.generateCompatibilityReport("id1", "id2")
        advanceUntilIdle()

        val navEvent = viewModel.navigationEvent.value
        assertThat(navEvent).isInstanceOf(ProfileNavigationEvent.NavigateToCompatibility::class.java)
        val event = navEvent as ProfileNavigationEvent.NavigateToCompatibility
        assertThat(event.profile1Id).isEqualTo("id1")
        assertThat(event.profile2Id).isEqualTo("id2")
    }

    @Test
    fun `generateCompatibilityReport with insufficient data shows error`() = runTest {
        val profile1 = createSampleProfile("id1").copy(
            profileCompletion = com.spiritatlas.domain.model.ProfileCompletion(
                completedFields = 2,
                totalFields = 36
            )
        )
        val profile2 = createSampleProfile("id2").copy(
            profileCompletion = com.spiritatlas.domain.model.ProfileCompletion(
                completedFields = 5,
                totalFields = 36
            )
        )

        coEvery { userRepository.getProfileById("id1") } returns profile1
        coEvery { userRepository.getProfileById("id2") } returns profile2

        viewModel = ProfileViewModel(userRepository)
        viewModel.generateCompatibilityReport("id1", "id2")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.errorMessage).contains("at least 3 completed fields")
    }

    @Test
    fun `generateCompatibilityReport with missing profile shows error`() = runTest {
        coEvery { userRepository.getProfileById("id1") } returns null
        coEvery { userRepository.getProfileById("id2") } returns createSampleProfile("id2")

        viewModel = ProfileViewModel(userRepository)
        viewModel.generateCompatibilityReport("id1", "id2")
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state.errorMessage).contains("Could not find both profiles")
    }

    @Test
    fun `clearNavigationEvent clears navigation event`() = runTest {
        val profile1 = createSampleProfile("id1").copy(
            profileCompletion = com.spiritatlas.domain.model.ProfileCompletion(
                completedFields = 5,
                totalFields = 36
            )
        )
        val profile2 = createSampleProfile("id2").copy(
            profileCompletion = com.spiritatlas.domain.model.ProfileCompletion(
                completedFields = 5,
                totalFields = 36
            )
        )

        coEvery { userRepository.getProfileById("id1") } returns profile1
        coEvery { userRepository.getProfileById("id2") } returns profile2

        viewModel = ProfileViewModel(userRepository)
        viewModel.generateCompatibilityReport("id1", "id2")
        advanceUntilIdle()

        assertThat(viewModel.navigationEvent.value).isNotNull()

        viewModel.clearNavigationEvent()
        assertThat(viewModel.navigationEvent.value).isNull()
    }

    private fun createSampleProfile(id: String): UserProfile {
        val now = LocalDateTime.of(2024, 1, 1, 12, 0)
        return UserProfile(
            id = id,
            profileName = "Test Profile",
            createdAt = now,
            lastModified = now,
            name = "Test Name",
            displayName = "Test Display",
            birthDateTime = now,
            birthPlace = BirthPlace(
                city = "Test City",
                country = "US",
                latitude = 0.0,
                longitude = 0.0
            )
        )
    }
}
