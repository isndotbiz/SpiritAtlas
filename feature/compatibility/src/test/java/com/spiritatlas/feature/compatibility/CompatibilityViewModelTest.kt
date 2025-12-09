package com.spiritatlas.feature.compatibility

import com.google.common.truth.Truth.assertThat
import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.model.*
import com.spiritatlas.domain.repository.CompatibilityCriteria
import com.spiritatlas.domain.repository.CompatibilityPreview
import com.spiritatlas.domain.repository.CompatibilityRepository
import com.spiritatlas.domain.repository.ProfileMatch
import com.spiritatlas.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.util.UUID

class CompatibilityViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `profiles are loaded on init`() = runTest {
        val userRepo = FakeUserRepository()
        val vm = CompatibilityViewModel(FakeCompatibilityRepository(), userRepo)

        dispatcher.scheduler.advanceUntilIdle()

        assertThat(vm.uiState.value.availableProfiles).hasSize(2)
        assertThat(vm.uiState.value.isLoadingProfiles).isFalse()
    }

    @Test
    fun `missing selections surface error`() = runTest {
        val vm = CompatibilityViewModel(FakeCompatibilityRepository(), FakeUserRepository())

        vm.analyzeCompatibility()
        dispatcher.scheduler.advanceUntilIdle()

        assertThat(vm.uiState.value.errorMessage).isEqualTo("Please select both profiles for analysis")
    }

    @Test
    fun `successful analysis updates report and tab`() = runTest {
        val userRepo = FakeUserRepository()
        val compatRepo = FakeCompatibilityRepository()
        val vm = CompatibilityViewModel(compatRepo, userRepo)

        dispatcher.scheduler.advanceUntilIdle()
        val profiles = userRepo.profilesFlow.value

        vm.selectProfileA(profiles[0])
        vm.selectProfileB(profiles[1])
        vm.analyzeCompatibility()

        dispatcher.scheduler.advanceUntilIdle()

        assertThat(vm.compatibilityReport.value).isNotNull()
        assertThat(vm.uiState.value.compatibilityReport).isNotNull()
        assertThat(vm.uiState.value.activeTab).isEqualTo(CompatibilityTab.RESULTS)
        assertThat(vm.uiState.value.isAnalyzing).isFalse()
    }

    @Test
    fun `search compatible profiles populates matches`() = runTest {
        val userRepo = FakeUserRepository()
        val compatRepo = FakeCompatibilityRepository()
        val vm = CompatibilityViewModel(compatRepo, userRepo)

        dispatcher.scheduler.advanceUntilIdle()
        val base = userRepo.profilesFlow.value.first()

        vm.searchCompatibleProfiles(base)
        dispatcher.scheduler.advanceUntilIdle()

        assertThat(vm.uiState.value.compatibleMatches).isNotEmpty()
        assertThat(vm.uiState.value.isSearching).isFalse()
    }
}

private class FakeCompatibilityRepository : CompatibilityRepository {
    private val sampleReport = CompatibilityReport(
        profileA = sampleProfile("A"),
        profileB = sampleProfile("B"),
        overallScore = CompatibilityScores(80.0, 70.0, 75.0, 78.0, 82.0, 76.0),
        insights = listOf(
            RelationshipInsight("Harmony", "Strong emotional attunement", InsightCategory.EMOTIONAL_HARMONY, InsightImportance.HIGH, "💞")
        ),
        strengths = listOf(
            CompatibilityStrength("Communication", "Clear and open", 85.0, CompatibilityCategory.COMMUNICATION)
        ),
        challenges = emptyList(),
        recommendations = listOf(
            CompatibilityRecommendation("Weekly check-in", "Schedule a weekly reflective chat", RecommendationType.COMMUNICATION_TECHNIQUE, RecommendationPriority.HIGH, icon = "🗒️")
        ),
        tantricMatches = emptyList()
    )

    override fun analyzeCompatibility(profileA: UserProfile, profileB: UserProfile): Flow<Result<CompatibilityReport>> {
        return flow {
            emit(Result.Loading)
            emit(Result.Success(sampleReport.copy(profileA = profileA, profileB = profileB)))
        }
    }

    override fun getCachedCompatibilityReports(profileId: String): Flow<List<CompatibilityReport>> {
        return flowOf(emptyList())
    }

    override suspend fun saveCompatibilityReport(report: CompatibilityReport): Result<Unit> = Result.Success(Unit)

    override suspend fun deleteCompatibilityReport(reportId: String): Result<Unit> = Result.Success(Unit)

    override fun getTantricContentForCompatibility(): Flow<List<com.spiritatlas.domain.tantric.TantricContent>> {
        return flowOf(emptyList())
    }

    override fun findCompatibleProfiles(baseProfile: UserProfile, criteria: CompatibilityCriteria): Flow<List<ProfileMatch>> {
        val match = ProfileMatch(
            profile = sampleProfile("C"),
            compatibilityPreview = CompatibilityPreview(
                overallScore = 78.0,
                topStrength = "Emotional resonance",
                topChallenge = "Schedule alignment",
                compatibilityLevel = CompatibilityLevel.GOOD
            ),
            matchReason = "High emotional and communication scores",
            confidence = 0.7
        )
        return flowOf(listOf(match))
    }
}

private class FakeUserRepository : UserRepository {
    val profilesFlow = MutableStateFlow(listOf(sampleProfile("1"), sampleProfile("2")))

    override suspend fun saveUserProfile(profile: UserProfile) {}
    override fun getUserProfile(): Flow<UserProfile?> = flowOf(null)
    override suspend fun clearUserData() {}
    override suspend fun getAllProfiles(): List<UserProfile> = profilesFlow.value
    override fun getAllProfilesFlow(): Flow<List<UserProfile>> = profilesFlow
    override suspend fun getProfileById(profileId: String): UserProfile? = profilesFlow.value.find { it.id == profileId }
    override fun getProfileByIdFlow(profileId: String): Flow<UserProfile?> = flowOf(profilesFlow.value.find { it.id == profileId })
    override suspend fun deleteProfile(profileId: String) {}
    override suspend fun searchProfiles(query: String): List<UserProfile> = profilesFlow.value.filter { it.profileName.contains(query, true) }
    override suspend fun getProfileCount(): Int = profilesFlow.value.size
    override suspend fun exportProfileForSharing(profileId: String): kotlin.Result<ShareableProfile> = kotlin.Result.failure(NotImplementedError())
    override suspend fun importSharedProfile(sharedProfile: ShareableProfile): kotlin.Result<ProfileImportResult> = kotlin.Result.failure(NotImplementedError())
    override suspend fun getProfileLibraryEntries(): List<ProfileLibraryEntry> = emptyList()
    override suspend fun searchProfileLibrary(query: String): List<ProfileLibraryEntry> = emptyList()
    override suspend fun addProfileTags(profileId: String, tags: List<String>) {}
    override suspend fun getProfilesByTag(tag: String): List<ProfileLibraryEntry> = emptyList()
}

private fun sampleProfile(suffix: String): UserProfile {
    val now = LocalDateTime.of(2024, 1, 1, 12, 0)
    return UserProfile(
        id = UUID.randomUUID().toString() + suffix,
        profileName = "Profile-$suffix",
        createdAt = now,
        lastModified = now,
        name = "Name $suffix",
        displayName = "Display $suffix",
        birthDateTime = now,
        birthPlace = BirthPlace(city = "City", country = "US", latitude = 0.0, longitude = 0.0),
        profileCompletion = ProfileCompletion(completedFields = 10, totalFields = 36)
    )
}

