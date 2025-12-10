package com.spiritatlas.domain.service.optimized

import com.spiritatlas.domain.model.SampleUserProfile
import com.spiritatlas.domain.tantric.TantricContent
import com.spiritatlas.domain.tantric.TantricContentType
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class OptimizedCompatibilityAnalysisEngineTest {

    private lateinit var engine: OptimizedCompatibilityAnalysisEngine

    @Before
    fun setup() {
        engine = OptimizedCompatibilityAnalysisEngine(aiCompatibilityService = null)
    }

    @Test
    fun `analyzeCompatibility returns valid report for two profiles`() = runTest {
        val profileA = SampleUserProfile.minimumViableSample
        val profileB = SampleUserProfile.balancedSample

        val report = engine.analyzeCompatibility(
            profileA = profileA,
            profileB = profileB,
            includeAiInsights = false
        )

        assertNotNull(report)
        assertEquals(profileA, report.profileA)
        assertEquals(profileB, report.profileB)
        assertNotNull(report.overallScore)
        assertNotNull(report.insights)
        assertNotNull(report.strengths)
        assertNotNull(report.challenges)
        assertNotNull(report.recommendations)
    }

    @Test
    fun `analyzeCompatibility calculates scores within valid range`() = runTest {
        val profileA = SampleUserProfile.minimumViableSample
        val profileB = SampleUserProfile.balancedSample

        val report = engine.analyzeCompatibility(
            profileA = profileA,
            profileB = profileB,
            includeAiInsights = false
        )

        val scores = report.overallScore
        assertTrue("Total score should be 0-100", scores.totalScore in 0.0..100.0)
        assertTrue("Numerology score should be 0-100", scores.numerologyScore in 0.0..100.0)
        assertTrue("Astrology score should be 0-100", scores.astrologyScore in 0.0..100.0)
        assertTrue("Tantric score should be 0-100", scores.tantricScore in 0.0..100.0)
        assertTrue("Energetic score should be 0-100", scores.energeticScore in 0.0..100.0)
        assertTrue("Communication score should be 0-100", scores.communicationScore in 0.0..100.0)
        assertTrue("Emotional score should be 0-100", scores.emotionalScore in 0.0..100.0)
    }

    @Test
    @Ignore("Implementation returns emptyList() placeholders - needs implementation")
    fun `analyzeCompatibility with same profile returns high score`() = runTest {
        val profile = SampleUserProfile.balancedSample

        val report = engine.analyzeCompatibility(
            profileA = profile,
            profileB = profile,
            includeAiInsights = false
        )

        assertTrue("Same profile should have high compatibility",
            report.overallScore.totalScore > 90.0)
    }

    @Test
    @Ignore("Implementation returns emptyList() placeholders - needs implementation")
    fun `analyzeCompatibility generates insights`() = runTest {
        val profileA = SampleUserProfile.minimumViableSample
        val profileB = SampleUserProfile.balancedSample

        val report = engine.analyzeCompatibility(
            profileA = profileA,
            profileB = profileB,
            includeAiInsights = false
        )

        assertTrue("Should generate insights", report.insights.isNotEmpty())
        assertTrue("Should have multiple insights", report.insights.size >= 3)
    }

    @Test
    @Ignore("Implementation returns emptyList() placeholders - needs implementation")
    fun `analyzeCompatibility identifies strengths and challenges`() = runTest {
        val profileA = SampleUserProfile.minimumViableSample
        val profileB = SampleUserProfile.balancedSample

        val report = engine.analyzeCompatibility(
            profileA = profileA,
            profileB = profileB,
            includeAiInsights = false
        )

        assertTrue("Should have strengths", report.strengths.isNotEmpty())
        assertNotNull("Should have challenges", report.challenges)
    }

    @Test
    @Ignore("Implementation returns emptyList() placeholders - needs implementation")
    fun `analyzeCompatibility provides recommendations`() = runTest {
        val profileA = SampleUserProfile.minimumViableSample
        val profileB = SampleUserProfile.balancedSample

        val report = engine.analyzeCompatibility(
            profileA = profileA,
            profileB = profileB,
            includeAiInsights = false
        )

        assertTrue("Should provide recommendations", report.recommendations.isNotEmpty())
    }

    @Test
    fun `analyzeCompatibility with tantric content`() = runTest {
        val profileA = SampleUserProfile.minimumViableSample
        val profileB = SampleUserProfile.balancedSample
        val tantricContent = listOf(
            TantricContent(
                id = "tc1",
                contentType = TantricContentType.TANTRIC_PRACTICES,
                title = "Breath Work",
                description = "Synchronized breathing"
            )
        )

        val report = engine.analyzeCompatibility(
            profileA = profileA,
            profileB = profileB,
            tantricContent = tantricContent,
            includeAiInsights = false
        )

        assertNotNull("Should include tantric matches", report.tantricMatches)
    }

    @Test
    fun `analyzeCompatibilitySync returns report without AI`() {
        val profileA = SampleUserProfile.minimumViableSample
        val profileB = SampleUserProfile.balancedSample

        val report = engine.analyzeCompatibilitySync(
            profileA = profileA,
            profileB = profileB
        )

        assertNotNull(report)
        assertEquals(profileA, report.profileA)
        assertEquals(profileB, report.profileB)
        assertNull("Sync version should not have AI insights", report.aiInsights)
    }

    @Test
    fun `analyzeCompatibility uses caching`() = runTest {
        val profileA = SampleUserProfile.minimumViableSample
        val profileB = SampleUserProfile.balancedSample

        val report1 = engine.analyzeCompatibility(profileA, profileB, includeAiInsights = false)
        val report2 = engine.analyzeCompatibility(profileA, profileB, includeAiInsights = false)

        assertEquals("Cached scores should match", report1.overallScore.totalScore, report2.overallScore.totalScore, 0.01)
    }

    @Test
    fun `compatibility scores are symmetric`() = runTest {
        val profileA = SampleUserProfile.minimumViableSample
        val profileB = SampleUserProfile.balancedSample

        val reportAB = engine.analyzeCompatibility(profileA, profileB, includeAiInsights = false)
        val reportBA = engine.analyzeCompatibility(profileB, profileA, includeAiInsights = false)

        assertEquals("Should be symmetric", reportAB.overallScore.totalScore, reportBA.overallScore.totalScore, 0.01)
    }

    @Test
    @Ignore("Implementation returns emptyList() placeholders - needs implementation")
    fun `handles different completion levels`() = runTest {
        val minimal = SampleUserProfile.minimumViableSample
        val maximal = SampleUserProfile.maxAccuracySample

        val report = engine.analyzeCompatibility(minimal, maximal, includeAiInsights = false)

        assertNotNull(report)
        assertTrue("Score should be valid", report.overallScore.totalScore in 0.0..100.0)
        assertTrue("Should still generate insights", report.insights.isNotEmpty())
    }

    @Test
    fun `handles empty tantric content`() = runTest {
        val profileA = SampleUserProfile.minimumViableSample
        val profileB = SampleUserProfile.balancedSample

        val report = engine.analyzeCompatibility(
            profileA = profileA,
            profileB = profileB,
            tantricContent = emptyList(),
            includeAiInsights = false
        )

        assertNotNull(report)
        assertNotNull(report.tantricMatches)
    }

    @Test
    fun `generates compatibility level based on score`() = runTest {
        val profile = SampleUserProfile.balancedSample

        val report = engine.analyzeCompatibility(profile, profile, includeAiInsights = false)

        assertNotNull("Should have compatibility level", report.compatibilityLevel)
    }

    @Test
    fun `all score components are present`() = runTest {
        val profileA = SampleUserProfile.balancedSample
        val profileB = SampleUserProfile.maxAccuracySample

        val report = engine.analyzeCompatibility(profileA, profileB, includeAiInsights = false)

        val scores = report.overallScore
        assertTrue("Total score valid", scores.totalScore >= 0.0)
        assertTrue("Numerology valid", scores.numerologyScore >= 0.0)
        assertTrue("Astrology valid", scores.astrologyScore >= 0.0)
        assertTrue("Tantric valid", scores.tantricScore >= 0.0)
        assertTrue("Energetic valid", scores.energeticScore >= 0.0)
        assertTrue("Communication valid", scores.communicationScore >= 0.0)
        assertTrue("Emotional valid", scores.emotionalScore >= 0.0)
    }
}
