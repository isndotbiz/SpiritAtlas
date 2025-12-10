package com.spiritatlas.domain.service

import com.google.common.truth.Truth.assertThat
import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.ai.*
import com.spiritatlas.domain.model.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class AiCompatibilityServiceImplTest {

    private lateinit var aiTextProvider: AiTextProvider
    private lateinit var service: AiCompatibilityServiceImpl

    @Before
    fun setUp() {
        aiTextProvider = mockk(relaxed = true)
        service = AiCompatibilityServiceImpl(aiTextProvider)
    }

    @Test
    fun `analyzeCompatibility returns error when AI unavailable`() = runTest {
        coEvery { aiTextProvider.isAvailable() } returns false

        val result = service.analyzeCompatibility(
            createSampleProfile("id1"),
            createSampleProfile("id2"),
            createSampleScores(),
            AnalysisType.COMPREHENSIVE
        )

        assertThat(result).isInstanceOf(Result.Error::class.java)
        val error = result as Result.Error
        assertThat(error.exception.message).contains("not available")
    }

    @Test
    fun `analyzeCompatibility calls AI provider with context`() = runTest {
        coEvery { aiTextProvider.isAvailable() } returns true
        coEvery { aiTextProvider.generateEnrichment(any()) } returns
            Result.Success(EnrichmentResult("Test analysis", 0.8f))

        service.analyzeCompatibility(
            createSampleProfile("id1"),
            createSampleProfile("id2"),
            createSampleScores(),
            AnalysisType.COMPREHENSIVE
        )

        coVerify { aiTextProvider.generateEnrichment(any()) }
    }

    @Test
    fun `analyzeCompatibility returns success with parsed insights`() = runTest {
        coEvery { aiTextProvider.isAvailable() } returns true
        coEvery { aiTextProvider.generateEnrichment(any()) } returns
            Result.Success(EnrichmentResult("Detailed compatibility analysis", 0.85f))

        val result = service.analyzeCompatibility(
            createSampleProfile("id1"),
            createSampleProfile("id2"),
            createSampleScores(),
            AnalysisType.COMPREHENSIVE
        )

        assertThat(result).isInstanceOf(Result.Success::class.java)
        val success = result as Result.Success
        assertThat(success.data.overallSummary).isNotEmpty()
    }

    @Test
    fun `analyzeCompatibility handles AI provider error`() = runTest {
        coEvery { aiTextProvider.isAvailable() } returns true
        coEvery { aiTextProvider.generateEnrichment(any()) } returns
            Result.Error(RuntimeException("AI service error"))

        val result = service.analyzeCompatibility(
            createSampleProfile("id1"),
            createSampleProfile("id2"),
            createSampleScores(),
            AnalysisType.COMPREHENSIVE
        )

        assertThat(result).isInstanceOf(Result.Error::class.java)
        val error = result as Result.Error
        assertThat(error.exception.message).contains("AI service error")
    }

    @Test
    fun `analyzeCompatibility handles loading state as error`() = runTest {
        coEvery { aiTextProvider.isAvailable() } returns true
        coEvery { aiTextProvider.generateEnrichment(any()) } returns Result.Loading

        val result = service.analyzeCompatibility(
            createSampleProfile("id1"),
            createSampleProfile("id2"),
            createSampleScores(),
            AnalysisType.COMPREHENSIVE
        )

        assertThat(result).isInstanceOf(Result.Error::class.java)
    }

    @Test
    fun `isAvailable returns true when provider available`() = runTest {
        coEvery { aiTextProvider.isAvailable() } returns true

        val result = service.isAvailable()

        assertThat(result).isTrue()
    }

    @Test
    fun `isAvailable returns false when provider unavailable`() = runTest {
        coEvery { aiTextProvider.isAvailable() } returns false

        val result = service.isAvailable()

        assertThat(result).isFalse()
    }

    @Test
    fun `isAvailable returns false on exception`() = runTest {
        coEvery { aiTextProvider.isAvailable() } throws RuntimeException("Connection error")

        val result = service.isAvailable()

        assertThat(result).isFalse()
    }

    @Test
    fun `analyzeDimension returns error when AI unavailable`() = runTest {
        coEvery { aiTextProvider.isAvailable() } returns false

        val result = service.analyzeDimension(
            createSampleProfile("id1"),
            createSampleProfile("id2"),
            "numerology",
            85.0
        )

        assertThat(result).isInstanceOf(Result.Error::class.java)
    }

    @Test
    fun `analyzeDimension calls AI for specific dimension`() = runTest {
        coEvery { aiTextProvider.isAvailable() } returns true
        coEvery { aiTextProvider.generateEnrichment(any()) } returns
            Result.Success(EnrichmentResult("Numerology analysis", 0.9f))

        service.analyzeDimension(
            createSampleProfile("id1"),
            createSampleProfile("id2"),
            "numerology",
            85.0
        )

        coVerify { aiTextProvider.generateEnrichment(any()) }
    }

    @Test
    fun `analyzeDimension returns parsed dimension insight`() = runTest {
        coEvery { aiTextProvider.isAvailable() } returns true
        coEvery { aiTextProvider.generateEnrichment(any()) } returns
            Result.Success(EnrichmentResult("Astrology dimension analysis", 0.88f))

        val result = service.analyzeDimension(
            createSampleProfile("id1"),
            createSampleProfile("id2"),
            "astrology",
            78.5
        )

        assertThat(result).isInstanceOf(Result.Success::class.java)
        val success = result as Result.Success
        assertThat(success.data.dimension).isEqualTo("astrology")
        assertThat(success.data.analysis).isNotEmpty()
    }

    @Test
    fun `analyzeDimension handles AI error`() = runTest {
        coEvery { aiTextProvider.isAvailable() } returns true
        coEvery { aiTextProvider.generateEnrichment(any()) } returns
            Result.Error(RuntimeException("Dimension analysis failed"))

        val result = service.analyzeDimension(
            createSampleProfile("id1"),
            createSampleProfile("id2"),
            "tantric",
            92.0
        )

        assertThat(result).isInstanceOf(Result.Error::class.java)
    }

    @Test
    fun `analyzeCompatibility uses COMPREHENSIVE analysis type correctly`() = runTest {
        coEvery { aiTextProvider.isAvailable() } returns true
        coEvery { aiTextProvider.generateEnrichment(any()) } returns
            Result.Success(EnrichmentResult("Comprehensive analysis", 0.9f))

        val result = service.analyzeCompatibility(
            createSampleProfile("id1"),
            createSampleProfile("id2"),
            createSampleScores(),
            AnalysisType.COMPREHENSIVE
        )

        assertThat(result).isInstanceOf(Result.Success::class.java)
        coVerify { aiTextProvider.generateEnrichment(any()) }
    }

    @Test
    fun `analyzeCompatibility uses QUICK analysis type correctly`() = runTest {
        coEvery { aiTextProvider.isAvailable() } returns true
        coEvery { aiTextProvider.generateEnrichment(any()) } returns
            Result.Success(EnrichmentResult("Quick analysis", 0.75f))

        val result = service.analyzeCompatibility(
            createSampleProfile("id1"),
            createSampleProfile("id2"),
            createSampleScores(),
            AnalysisType.QUICK
        )

        assertThat(result).isInstanceOf(Result.Success::class.java)
    }

    @Test
    fun `analyzeCompatibility includes both profiles in context`() = runTest {
        coEvery { aiTextProvider.isAvailable() } returns true
        coEvery { aiTextProvider.generateEnrichment(any()) } returns
            Result.Success(EnrichmentResult("Analysis with both profiles", 0.85f))

        service.analyzeCompatibility(
            createSampleProfile("alice"),
            createSampleProfile("bob"),
            createSampleScores(),
            AnalysisType.COMPREHENSIVE
        )

        coVerify {
            aiTextProvider.generateEnrichment(match { context ->
                context.personalDetails.containsValue("compatibility")
            })
        }
    }

    @Test
    fun `analyzeCompatibility handles exception during analysis`() = runTest {
        coEvery { aiTextProvider.isAvailable() } throws RuntimeException("Unexpected error")

        val result = service.analyzeCompatibility(
            createSampleProfile("id1"),
            createSampleProfile("id2"),
            createSampleScores(),
            AnalysisType.COMPREHENSIVE
        )

        assertThat(result).isInstanceOf(Result.Error::class.java)
        val error = result as Result.Error
        assertThat(error.exception.message).contains("Unexpected error")
    }

    private fun createSampleProfile(id: String): UserProfile {
        val now = LocalDateTime.of(2024, 1, 1, 12, 0)
        return UserProfile(
            id = id,
            profileName = "Profile $id",
            createdAt = now,
            lastModified = now,
            name = "Name $id",
            displayName = "Display $id",
            birthDateTime = now,
            birthPlace = BirthPlace(
                city = "Test City",
                country = "US",
                latitude = 0.0,
                longitude = 0.0
            )
        )
    }

    private fun createSampleScores(): CompatibilityScores {
        return CompatibilityScores(
            numerologyScore = 85.0,
            astrologyScore = 78.0,
            tantricScore = 92.0,
            emotionalScore = 88.0,
            communicationScore = 82.0,
            energeticScore = 86.0
        )
    }
}
