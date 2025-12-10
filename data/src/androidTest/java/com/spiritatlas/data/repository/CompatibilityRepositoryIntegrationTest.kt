package com.spiritatlas.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.model.*
import com.spiritatlas.domain.repository.CompatibilityRepository
import com.spiritatlas.domain.service.CompatibilityAnalysisEngine
import com.spiritatlas.domain.tantric.TantricContentType
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

/**
 * Integration test for CompatibilityRepository
 * Tests compatibility analysis flow with CompatibilityAnalysisEngine
 */
@RunWith(AndroidJUnit4::class)
class CompatibilityRepositoryIntegrationTest {

    private lateinit var repository: CompatibilityRepository
    private lateinit var analysisEngine: CompatibilityAnalysisEngine

    @Before
    fun setup() {
        // Create real engine (without AI for testing)
        analysisEngine = CompatibilityAnalysisEngine(aiCompatibilityService = null)
        repository = CompatibilityRepositoryImpl(analysisEngine)
    }

    @Test
    fun analyzeCompatibility_returnsCompleteReport() = runTest {
        // Given
        val profileA = createTestProfile(id = "alice", name = "Alice")
        val profileB = createTestProfile(id = "bob", name = "Bob")

        // When/Then
        repository.analyzeCompatibility(profileA, profileB).test {
            // Should emit Loading first
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(Result.Loading::class.java)

            // Then emit Success with report
            val success = awaitItem()
            assertThat(success).isInstanceOf(Result.Success::class.java)

            val report = (success as Result.Success).data
            assertThat(report.profileA).isEqualTo(profileA)
            assertThat(report.profileB).isEqualTo(profileB)
            assertThat(report.overallScore).isNotNull()
            assertThat(report.insights).isNotEmpty()
            assertThat(report.strengths).isNotEmpty()
            assertThat(report.challenges).isNotEmpty()
            assertThat(report.recommendations).isNotEmpty()

            awaitComplete()
        }
    }

    @Test
    fun analyzeCompatibility_calculatesScoresCorrectly() = runTest {
        // Given
        val profileA = createTestProfile(id = "alice", name = "Alice")
        val profileB = createTestProfile(id = "bob", name = "Bob")

        // When/Then
        repository.analyzeCompatibility(profileA, profileB).test {
            skipItems(1) // Skip loading

            val success = awaitItem()
            val report = (success as Result.Success).data

            // Verify score structure
            assertThat(report.overallScore.overall).isAtLeast(0.0)
            assertThat(report.overallScore.overall).isAtMost(100.0)
            assertThat(report.overallScore.numerology).isAtLeast(0.0)
            assertThat(report.overallScore.astrology).isAtLeast(0.0)
            assertThat(report.overallScore.tantric).isAtLeast(0.0)

            awaitComplete()
        }
    }

    @Test
    fun analyzeCompatibility_includesTantricContent() = runTest {
        // Given
        val profileA = createTestProfile(id = "alice", name = "Alice")
        val profileB = createTestProfile(id = "bob", name = "Bob")

        // When/Then
        repository.analyzeCompatibility(profileA, profileB).test {
            skipItems(1) // Skip loading

            val success = awaitItem()
            val report = (success as Result.Success).data

            // Verify tantric matches exist
            assertThat(report.tantricMatches).isNotEmpty()
            assertThat(report.tantricMatches.map { it.content.contentType })
                .containsAtLeastOneOf(
                    TantricContentType.KAMA_SUTRA,
                    TantricContentType.TANTRIC_PRACTICES,
                    TantricContentType.ROBERT_GREENE,
                    TantricContentType.COMPATIBILITY
                )

            awaitComplete()
        }
    }

    @Test
    fun saveCompatibilityReport_persistsReport() = runTest {
        // Given
        val profileA = createTestProfile(id = "alice", name = "Alice")
        val profileB = createTestProfile(id = "bob", name = "Bob")

        var report: CompatibilityReport? = null
        repository.analyzeCompatibility(profileA, profileB).test {
            skipItems(1) // Skip loading
            report = ((awaitItem()) as Result.Success).data
            awaitComplete()
        }

        // When
        val saveResult = repository.saveCompatibilityReport(report!!)

        // Then
        assertThat(saveResult).isInstanceOf(Result.Success::class.java)

        // Verify it's cached
        repository.getCachedCompatibilityReports("alice").test {
            val cached = awaitItem()
            assertThat(cached).hasSize(1)
            assertThat(cached[0].profileA.id).isEqualTo("alice")
            awaitComplete()
        }
    }

    @Test
    fun getCachedCompatibilityReports_returnsReportsForBothProfiles() = runTest {
        // Given
        val profileA = createTestProfile(id = "alice", name = "Alice")
        val profileB = createTestProfile(id = "bob", name = "Bob")

        var report: CompatibilityReport? = null
        repository.analyzeCompatibility(profileA, profileB).test {
            skipItems(1)
            report = ((awaitItem()) as Result.Success).data
            awaitComplete()
        }

        repository.saveCompatibilityReport(report!!)

        // When/Then - Alice's reports
        repository.getCachedCompatibilityReports("alice").test {
            val aliceReports = awaitItem()
            assertThat(aliceReports).hasSize(1)
            awaitComplete()
        }

        // When/Then - Bob's reports
        repository.getCachedCompatibilityReports("bob").test {
            val bobReports = awaitItem()
            assertThat(bobReports).hasSize(1)
            awaitComplete()
        }
    }

    @Test
    fun deleteCompatibilityReport_removesReport() = runTest {
        // Given
        val profileA = createTestProfile(id = "alice", name = "Alice")
        val profileB = createTestProfile(id = "bob", name = "Bob")

        var report: CompatibilityReport? = null
        repository.analyzeCompatibility(profileA, profileB).test {
            skipItems(1)
            report = ((awaitItem()) as Result.Success).data
            awaitComplete()
        }

        repository.saveCompatibilityReport(report!!)

        // When
        val reportId = report!!.generatedAt.toString()
        val deleteResult = repository.deleteCompatibilityReport(reportId)

        // Then
        assertThat(deleteResult).isInstanceOf(Result.Success::class.java)

        repository.getCachedCompatibilityReports("alice").test {
            val cached = awaitItem()
            assertThat(cached).isEmpty()
            awaitComplete()
        }
    }

    @Test
    fun getTantricContentForCompatibility_returnsContent() = runTest {
        // When/Then
        repository.getTantricContentForCompatibility().test {
            val content = awaitItem()

            assertThat(content).isNotEmpty()
            assertThat(content).hasSize(4) // Mock data has 4 items

            // Verify content types
            val contentTypes = content.map { it.contentType }.toSet()
            assertThat(contentTypes).containsAtLeast(
                TantricContentType.KAMA_SUTRA,
                TantricContentType.TANTRIC_PRACTICES,
                TantricContentType.ROBERT_GREENE,
                TantricContentType.COMPATIBILITY
            )

            awaitComplete()
        }
    }

    @Test
    fun findCompatibleProfiles_returnsMatches() = runTest {
        // Given
        val baseProfile = createTestProfile(id = "alice", name = "Alice")
        val criteria = CompatibilityCriteria(
            minCompatibilityScore = 70.0
        )

        // When/Then
        repository.findCompatibleProfiles(baseProfile, criteria).test {
            val matches = awaitItem()

            assertThat(matches).isNotEmpty()
            assertThat(matches).hasSize(3) // Mock data has 3 matches

            // Verify match structure
            matches.forEach { match ->
                assertThat(match.profile).isNotNull()
                assertThat(match.compatibilityPreview).isNotNull()
                assertThat(match.compatibilityPreview.overallScore).isAtLeast(70.0)
                assertThat(match.matchReason).isNotEmpty()
                assertThat(match.confidence).isAtLeast(0.0)
                assertThat(match.confidence).isAtMost(1.0)
            }

            awaitComplete()
        }
    }

    @Test
    fun findCompatibleProfiles_respectsMinScoreCriteria() = runTest {
        // Given
        val baseProfile = createTestProfile(id = "alice", name = "Alice")
        val criteria = CompatibilityCriteria(
            minCompatibilityScore = 80.0 // High threshold
        )

        // When/Then
        repository.findCompatibleProfiles(baseProfile, criteria).test {
            val matches = awaitItem()

            // All matches should meet minimum score
            matches.forEach { match ->
                assertThat(match.compatibilityPreview.overallScore).isAtLeast(80.0)
            }

            awaitComplete()
        }
    }

    @Test
    fun analyzeCompatibility_handlesProfilesWithDifferentCompletionLevels() = runTest {
        // Given - minimal profile
        val minimalProfile = createTestProfile(
            id = "minimal",
            name = "Minimal",
            birthDateTime = null,
            birthPlace = null
        )

        // Given - complete profile
        val completeProfile = createTestProfile(
            id = "complete",
            name = "Complete"
        )

        // When/Then
        repository.analyzeCompatibility(minimalProfile, completeProfile).test {
            skipItems(1)

            val success = awaitItem()
            val report = (success as Result.Success).data

            // Should still generate a report
            assertThat(report).isNotNull()
            assertThat(report.overallScore).isNotNull()
            assertThat(report.insights).isNotEmpty()

            awaitComplete()
        }
    }

    @Test
    fun analyzeCompatibility_providesActionableRecommendations() = runTest {
        // Given
        val profileA = createTestProfile(id = "alice", name = "Alice")
        val profileB = createTestProfile(id = "bob", name = "Bob")

        // When/Then
        repository.analyzeCompatibility(profileA, profileB).test {
            skipItems(1)

            val success = awaitItem()
            val report = (success as Result.Success).data

            // Verify recommendations
            assertThat(report.recommendations).isNotEmpty()
            report.recommendations.forEach { recommendation ->
                assertThat(recommendation.category).isNotNull()
                assertThat(recommendation.recommendation).isNotEmpty()
                assertThat(recommendation.priority).isNotNull()
            }

            awaitComplete()
        }
    }

    @Test
    fun analyzeCompatibility_identifiesStrengthsAndChallenges() = runTest {
        // Given
        val profileA = createTestProfile(id = "alice", name = "Alice")
        val profileB = createTestProfile(id = "bob", name = "Bob")

        // When/Then
        repository.analyzeCompatibility(profileA, profileB).test {
            skipItems(1)

            val success = awaitItem()
            val report = (success as Result.Success).data

            // Verify strengths
            assertThat(report.strengths).isNotEmpty()
            report.strengths.forEach { strength ->
                assertThat(strength.category).isNotNull()
                assertThat(strength.description).isNotEmpty()
                assertThat(strength.score).isAtLeast(0.0)
            }

            // Verify challenges
            assertThat(report.challenges).isNotEmpty()
            report.challenges.forEach { challenge ->
                assertThat(challenge.category).isNotNull()
                assertThat(challenge.description).isNotEmpty()
                assertThat(challenge.severity).isNotNull()
            }

            awaitComplete()
        }
    }

    // Helper function to create test profiles
    private fun createTestProfile(
        id: String = "test_profile",
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
            gender = Gender.values().random(),
            preferences = UserPreferences(
                usesSiderealZodiac = false,
                numerologySystem = NumerologySystem.CHALDEAN,
                detailLevel = InsightDetail.COMPREHENSIVE
            ),
            profileCompletion = ProfileCompletion(
                totalFields = 27,
                completedFields = 15,
                completionPercentage = 55.0,
                accuracyLevel = AccuracyLevel.GOOD,
                missingCriticalFields = emptyList()
            )
        )
    }
}
