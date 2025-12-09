package com.spiritatlas.domain.service

import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.ai.AnalysisType
import com.spiritatlas.domain.model.*
import com.spiritatlas.domain.tantric.TantricContent
import com.spiritatlas.domain.tantric.TantricContentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.abs
import kotlin.random.Random

/**
 * Core engine for analyzing compatibility between two user profiles
 * Integrates numerology, astrology, tantric insights, and AI-powered analysis
 *
 * This engine enhances calculated compatibility scores with optional AI insights
 * for deeper, multi-perspective analysis. Falls back gracefully if AI unavailable.
 */
class CompatibilityAnalysisEngine @Inject constructor(
    private val aiCompatibilityService: AiCompatibilityService? = null // Optional AI service
) {

    /**
     * Analyzes complete compatibility between two profiles
     *
     * @param profileA First person's profile
     * @param profileB Second person's profile
     * @param tantricContent Tantric content for analysis
     * @param includeAiInsights Whether to include AI-powered insights (if available)
     * @param aiAnalysisType Type of AI analysis (quick/standard/comprehensive)
     * @return Complete compatibility report with optional AI insights
     */
    suspend fun analyzeCompatibility(
        profileA: UserProfile,
        profileB: UserProfile,
        tantricContent: List<TantricContent> = emptyList(),
        includeAiInsights: Boolean = true,
        aiAnalysisType: AnalysisType = AnalysisType.STANDARD
    ): CompatibilityReport = withContext(Dispatchers.Default) {

        // Calculate base compatibility scores
        val scores = calculateCompatibilityScores(profileA, profileB)
        val insights = generateRelationshipInsights(profileA, profileB, scores)
        val strengths = identifyStrengths(profileA, profileB, scores)
        val challenges = identifyChallenges(profileA, profileB, scores)
        val recommendations = generateRecommendations(profileA, profileB, scores, challenges)
        val tantricMatches = analyzeTantricCompatibility(profileA, profileB, tantricContent)

        // Optionally enhance with AI insights
        val aiInsights = if (includeAiInsights && aiCompatibilityService != null) {
            try {
                when (val result = aiCompatibilityService.analyzeCompatibility(
                    profileA = profileA,
                    profileB = profileB,
                    calculatedScores = scores,
                    analysisType = aiAnalysisType
                )) {
                    is Result.Success -> result.data
                    else -> null // Gracefully fall back if AI unavailable
                }
            } catch (e: Exception) {
                null // Gracefully fall back on error
            }
        } else {
            null
        }

        return@withContext CompatibilityReport(
            profileA = profileA,
            profileB = profileB,
            overallScore = scores,
            insights = insights,
            strengths = strengths,
            challenges = challenges,
            recommendations = recommendations,
            tantricMatches = tantricMatches,
            aiInsights = aiInsights
        )
    }

    /**
     * Synchronous version for backward compatibility
     * Note: This version cannot include AI insights as they require suspend context
     */
    fun analyzeCompatibilitySync(
        profileA: UserProfile,
        profileB: UserProfile,
        tantricContent: List<TantricContent> = emptyList()
    ): CompatibilityReport {

        val scores = calculateCompatibilityScores(profileA, profileB)
        val insights = generateRelationshipInsights(profileA, profileB, scores)
        val strengths = identifyStrengths(profileA, profileB, scores)
        val challenges = identifyChallenges(profileA, profileB, scores)
        val recommendations = generateRecommendations(profileA, profileB, scores, challenges)
        val tantricMatches = analyzeTantricCompatibility(profileA, profileB, tantricContent)

        return CompatibilityReport(
            profileA = profileA,
            profileB = profileB,
            overallScore = scores,
            insights = insights,
            strengths = strengths,
            challenges = challenges,
            recommendations = recommendations,
            tantricMatches = tantricMatches,
            aiInsights = null // No AI in sync version
        )
    }
    
    /**
     * Calculates compatibility scores across different dimensions
     */
    private fun calculateCompatibilityScores(
        profileA: UserProfile,
        profileB: UserProfile
    ): CompatibilityScores {
        return CompatibilityScores(
            numerologyScore = calculateNumerologyCompatibility(profileA, profileB),
            astrologyScore = calculateAstrologyCompatibility(profileA, profileB),
            tantricScore = calculateTantricCompatibility(profileA, profileB),
            energeticScore = calculateEnergeticCompatibility(profileA, profileB),
            communicationScore = calculateCommunicationCompatibility(profileA, profileB),
            emotionalScore = calculateEmotionalCompatibility(profileA, profileB)
        )
    }
    
    /**
     * Numerology-based compatibility analysis
     */
    private fun calculateNumerologyCompatibility(
        profileA: UserProfile,
        profileB: UserProfile
    ): Double {
        // Mock calculation based on name energy until real numerology is implemented
        val nameEnergyA = calculateNameEnergy(profileA.name ?: "")
        val nameEnergyB = calculateNameEnergy(profileB.name ?: "")
        
        // Simple compatibility based on name energy harmony
        val energyHarmony = 100.0 - abs(nameEnergyA - nameEnergyB) * 5.0
        
        return energyHarmony.coerceIn(60.0, 95.0)
    }
    
    /**
     * Astrology-based compatibility analysis
     */
    private fun calculateAstrologyCompatibility(
        profileA: UserProfile,
        profileB: UserProfile
    ): Double {
        // Mock astrology calculation based on birth month until real astrology is implemented
        val monthA = profileA.birthDateTime?.monthValue ?: 1
        val monthB = profileB.birthDateTime?.monthValue ?: 1
        
        // Simple element compatibility based on birth months
        val elementA = when (monthA) {
            in 3..5 -> "Fire" // Spring
            in 6..8 -> "Earth" // Summer
            in 9..11 -> "Air" // Fall  
            else -> "Water" // Winter
        }
        
        val elementB = when (monthB) {
            in 3..5 -> "Fire"
            in 6..8 -> "Earth"
            in 9..11 -> "Air"
            else -> "Water"
        }
        
        return when {
            elementA == elementB -> 85.0 // Same element
            (elementA == "Fire" && elementB == "Air") || (elementA == "Air" && elementB == "Fire") -> 90.0
            (elementA == "Earth" && elementB == "Water") || (elementA == "Water" && elementB == "Earth") -> 88.0
            else -> 75.0
        }
    }
    
    /**
     * Tantric energy compatibility
     */
    private fun calculateTantricCompatibility(
        profileA: UserProfile,
        profileB: UserProfile
    ): Double {
        // Analyze sacred geometry preferences, chakra alignments, etc.
        var score = 75.0 // Base score
        
        // Gender energy dynamics
        if (profileA.gender != null && profileB.gender != null) {
            score += when {
                profileA.gender != profileB.gender -> 15.0 // Complementary energies
                profileA.gender == profileB.gender -> 5.0 // Similar energies
                else -> 0.0
            }
        }
        
        // Time-based compatibility (birth hour energetics)
        profileA.birthDateTime?.let { dtA ->
            profileB.birthDateTime?.let { dtB ->
                val hourA = dtA.hour
                val hourB = dtB.hour
                val hourDiff = abs(hourA - hourB)
                
                score += when {
                    hourDiff <= 2 || hourDiff >= 22 -> 10.0 // Similar energy cycles
                    hourDiff in 10..14 -> 15.0 // Complementary day/night energies
                    else -> 5.0
                }
            }
        }
        
        return score.coerceAtMost(100.0)
    }
    
    /**
     * Energetic compatibility based on chakras and energy patterns
     */
    private fun calculateEnergeticCompatibility(
        profileA: UserProfile,
        profileB: UserProfile
    ): Double {
        var score = 70.0
        
        // Birth season energy compatibility
        profileA.birthDateTime?.let { dtA ->
            profileB.birthDateTime?.let { dtB ->
                val seasonA = getSeason(dtA.monthValue)
                val seasonB = getSeason(dtB.monthValue)
                
                score += when {
                    seasonA == seasonB -> 20.0 // Same seasonal energy
                    complementarySeasons(seasonA, seasonB) -> 25.0
                    else -> 10.0
                }
            }
        }
        
        return score.coerceAtMost(100.0)
    }
    
    /**
     * Communication style compatibility
     */
    private fun calculateCommunicationCompatibility(
        profileA: UserProfile,
        profileB: UserProfile
    ): Double {
        // This would integrate with communication preferences, Mercury signs, etc.
        // For now, using profile completeness as a proxy for communication openness
        val completenessA = profileA.profileCompletion.completionPercentage
        val completenessB = profileB.profileCompletion.completionPercentage
        
        val averageCompleteness = (completenessA + completenessB) / 2.0
        val completenessGap = abs(completenessA - completenessB)
        
        return when {
            averageCompleteness >= 80 && completenessGap <= 20 -> 90.0
            averageCompleteness >= 60 && completenessGap <= 30 -> 80.0
            averageCompleteness >= 40 -> 70.0
            else -> 60.0
        }
    }
    
    /**
     * Emotional compatibility analysis
     */
    private fun calculateEmotionalCompatibility(
        profileA: UserProfile,
        profileB: UserProfile
    ): Double {
        // Would integrate Moon signs, Venus signs, emotional patterns
        // Using name energy and life path for basic emotional compatibility
        val nameEnergyA = calculateNameEnergy(profileA.name ?: "")
        val nameEnergyB = calculateNameEnergy(profileB.name ?: "")
        
        val energyHarmony = 100.0 - abs(nameEnergyA - nameEnergyB) * 10.0
        
        return energyHarmony.coerceIn(60.0, 95.0)
    }
    
    /**
     * Generate relationship insights based on analysis
     */
    private fun generateRelationshipInsights(
        profileA: UserProfile,
        profileB: UserProfile,
        scores: CompatibilityScores
    ): List<RelationshipInsight> {
        val insights = mutableListOf<RelationshipInsight>()
        
        // Soul connection insight
        if (scores.numerologyScore > 85) {
            insights.add(RelationshipInsight(
                title = "Deep Soul Connection",
                description = "Your numerological patterns indicate a profound soul-level connection. This suggests you may have shared spiritual purposes or karmic ties.",
                category = InsightCategory.SOUL_CONNECTION,
                importance = InsightImportance.HIGH,
                icon = "💫",
                supportingEvidence = listOf(
                    "Life path numbers show strong compatibility",
                    "Expression numbers indicate complementary life purposes"
                )
            ))
        }
        
        // Communication insight
        if (scores.communicationScore > 80) {
            insights.add(RelationshipInsight(
                title = "Natural Communication Flow",
                description = "You both have compatible communication styles and openness levels, creating a foundation for clear, honest dialogue.",
                category = InsightCategory.COMMUNICATION_STYLE,
                importance = InsightImportance.HIGH,
                icon = "💬"
            ))
        }
        
        // Tantric insight
        if (scores.tantricScore > 80) {
            insights.add(RelationshipInsight(
                title = "Sacred Energy Alignment",
                description = "Your energetic patterns show strong tantric compatibility, suggesting potential for deep intimate connection and spiritual growth together.",
                category = InsightCategory.PHYSICAL_ATTRACTION,
                importance = InsightImportance.MEDIUM,
                icon = "🔥"
            ))
        }
        
        return insights
    }
    
    /**
     * Identify relationship strengths
     */
    private fun identifyStrengths(
        profileA: UserProfile,
        profileB: UserProfile,
        scores: CompatibilityScores
    ): List<CompatibilityStrength> {
        val strengths = mutableListOf<CompatibilityStrength>()
        
        if (scores.numerologyScore > 80) {
            strengths.add(CompatibilityStrength(
                aspect = "Numerological Harmony",
                description = "Your core numbers create a harmonious vibration together",
                score = scores.numerologyScore,
                category = CompatibilityCategory.NUMEROLOGICAL,
                icon = "🔢"
            ))
        }
        
        if (scores.astrologyScore > 80) {
            strengths.add(CompatibilityStrength(
                aspect = "Astrological Alignment",
                description = "Your zodiac signs show natural compatibility and understanding",
                score = scores.astrologyScore,
                category = CompatibilityCategory.ASTROLOGICAL,
                icon = "⭐"
            ))
        }
        
        if (scores.tantricScore > 80) {
            strengths.add(CompatibilityStrength(
                aspect = "Sacred Energy Flow",
                description = "Strong tantric compatibility for deep intimacy and spiritual connection",
                score = scores.tantricScore,
                category = CompatibilityCategory.TANTRIC,
                icon = "💫"
            ))
        }
        
        return strengths
    }
    
    /**
     * Identify relationship challenges
     */
    private fun identifyChallenges(
        profileA: UserProfile,
        profileB: UserProfile,
        scores: CompatibilityScores
    ): List<CompatibilityChallenge> {
        val challenges = mutableListOf<CompatibilityChallenge>()
        
        if (scores.communicationScore < 70) {
            challenges.add(CompatibilityChallenge(
                aspect = "Communication Barriers",
                description = "Different communication styles may require extra effort to understand each other",
                severity = ChallengeSeverity.MODERATE,
                category = CompatibilityCategory.COMMUNICATION,
                solutions = listOf(
                    "Practice active listening techniques",
                    "Schedule regular check-ins about feelings",
                    "Learn each other's preferred communication methods"
                ),
                icon = "💭"
            ))
        }
        
        if (scores.emotionalScore < 65) {
            challenges.add(CompatibilityChallenge(
                aspect = "Emotional Processing Differences",
                description = "You may have different ways of processing and expressing emotions",
                severity = ChallengeSeverity.MODERATE,
                category = CompatibilityCategory.EMOTIONAL,
                solutions = listOf(
                    "Discuss emotional needs openly",
                    "Respect different processing timelines",
                    "Create safe spaces for vulnerability"
                ),
                icon = "💔"
            ))
        }
        
        return challenges
    }
    
    /**
     * Generate personalized recommendations
     */
    private fun generateRecommendations(
        profileA: UserProfile,
        profileB: UserProfile,
        scores: CompatibilityScores,
        challenges: List<CompatibilityChallenge>
    ): List<CompatibilityRecommendation> {
        val recommendations = mutableListOf<CompatibilityRecommendation>()
        
        // Always include at least one tantric practice
        recommendations.add(CompatibilityRecommendation(
            title = "Sacred Morning Ritual",
            description = "Begin each day with 5 minutes of synchronized breathing while maintaining eye contact",
            actionType = RecommendationType.TANTRIC_PRACTICE,
            priority = RecommendationPriority.HIGH,
            icon = "🌅"
        ))
        
        if (scores.communicationScore < 80) {
            recommendations.add(CompatibilityRecommendation(
                title = "Daily Connection Practice",
                description = "Set aside 15 minutes each evening to share your day without judgment or advice-giving",
                actionType = RecommendationType.COMMUNICATION_TECHNIQUE,
                priority = RecommendationPriority.HIGH,
                icon = "💬"
            ))
        }
        
        if (scores.tantricScore > 80) {
            recommendations.add(CompatibilityRecommendation(
                title = "Energy Exchange Meditation",
                description = "Practice palm-to-palm energy sharing meditation weekly to deepen your connection",
                actionType = RecommendationType.SPIRITUAL_EXERCISE,
                priority = RecommendationPriority.MEDIUM,
                relatedTantricContent = listOf("chakra_alignment", "energy_meditation"),
                icon = "⚡"
            ))
        }
        
        return recommendations
    }
    
    /**
     * Analyze tantric content compatibility
     */
    private fun analyzeTantricCompatibility(
        profileA: UserProfile,
        profileB: UserProfile,
        tantricContent: List<TantricContent>
    ): List<TantricCompatibility> {
        return tantricContent.take(3).map { content ->
            val score = when (content.contentType) {
                TantricContentType.KAMA_SUTRA -> Random.nextDouble(70.0, 95.0)
                TantricContentType.TANTRIC_PRACTICES -> Random.nextDouble(75.0, 90.0)
                TantricContentType.ROBERT_GREENE -> Random.nextDouble(70.0, 85.0)
                TantricContentType.COMPATIBILITY -> Random.nextDouble(80.0, 95.0)
            }
            
            TantricCompatibility(
                contentId = content.id,
                contentType = content.contentType.name,
                compatibilityScore = score,
                reason = "Based on your combined energetic patterns and spiritual compatibility",
                recommendation = generateTantricRecommendation(content.contentType)
            )
        }
    }
    
    // Helper methods
    private fun complementaryNumbers(a: Int, b: Int): Boolean {
        val complementaryPairs = setOf(
            setOf(1, 8), setOf(2, 7), setOf(3, 6), setOf(4, 5)
        )
        return complementaryPairs.contains(setOf(a, b))
    }
    
    private fun calculateElementCompatibility(signA: String, signB: String): Double {
        // Fire signs: Aries, Leo, Sagittarius
        // Earth signs: Taurus, Virgo, Capricorn  
        // Air signs: Gemini, Libra, Aquarius
        // Water signs: Cancer, Scorpio, Pisces
        
        val fireElements = setOf("Aries", "Leo", "Sagittarius")
        val earthElements = setOf("Taurus", "Virgo", "Capricorn")
        val airElements = setOf("Gemini", "Libra", "Aquarius")
        val waterElements = setOf("Cancer", "Scorpio", "Pisces")
        
        val elementA = when (signA) {
            in fireElements -> "Fire"
            in earthElements -> "Earth"
            in airElements -> "Air"
            in waterElements -> "Water"
            else -> "Unknown"
        }
        
        val elementB = when (signB) {
            in fireElements -> "Fire"
            in earthElements -> "Earth"  
            in airElements -> "Air"
            in waterElements -> "Water"
            else -> "Unknown"
        }
        
        return when {
            elementA == elementB -> 85.0 // Same element
            (elementA == "Fire" && elementB == "Air") || (elementA == "Air" && elementB == "Fire") -> 90.0
            (elementA == "Earth" && elementB == "Water") || (elementA == "Water" && elementB == "Earth") -> 88.0
            (elementA == "Fire" && elementB == "Earth") || (elementA == "Earth" && elementB == "Fire") -> 70.0
            (elementA == "Air" && elementB == "Water") || (elementA == "Water" && elementB == "Air") -> 75.0
            else -> 80.0
        }
    }
    
    private fun calculateModalityCompatibility(signA: String, signB: String): Double {
        // Cardinal: Aries, Cancer, Libra, Capricorn
        // Fixed: Taurus, Leo, Scorpio, Aquarius
        // Mutable: Gemini, Virgo, Sagittarius, Pisces
        
        val cardinalSigns = setOf("Aries", "Cancer", "Libra", "Capricorn")
        val fixedSigns = setOf("Taurus", "Leo", "Scorpio", "Aquarius")
        val mutableSigns = setOf("Gemini", "Virgo", "Sagittarius", "Pisces")
        
        val modalityA = when (signA) {
            in cardinalSigns -> "Cardinal"
            in fixedSigns -> "Fixed"
            in mutableSigns -> "Mutable"
            else -> "Unknown"
        }
        
        val modalityB = when (signB) {
            in cardinalSigns -> "Cardinal"
            in fixedSigns -> "Fixed"
            in mutableSigns -> "Mutable"
            else -> "Unknown"
        }
        
        return when {
            modalityA == modalityB -> 80.0 // Same modality
            (modalityA == "Cardinal" && modalityB == "Mutable") || (modalityA == "Mutable" && modalityB == "Cardinal") -> 85.0
            else -> 75.0
        }
    }
    
    private fun calculateSignCompatibility(signA: String, signB: String): Double {
        // Traditional compatibility based on zodiac positions
        val compatibilityMap = mapOf(
            "Aries" to mapOf("Leo" to 95.0, "Sagittarius" to 90.0, "Gemini" to 85.0, "Aquarius" to 88.0),
            "Taurus" to mapOf("Virgo" to 92.0, "Capricorn" to 90.0, "Cancer" to 85.0, "Pisces" to 87.0),
            "Gemini" to mapOf("Libra" to 93.0, "Aquarius" to 91.0, "Aries" to 85.0, "Leo" to 88.0),
            "Cancer" to mapOf("Scorpio" to 94.0, "Pisces" to 92.0, "Taurus" to 85.0, "Virgo" to 87.0),
            "Leo" to mapOf("Aries" to 95.0, "Sagittarius" to 93.0, "Gemini" to 88.0, "Libra" to 86.0),
            "Virgo" to mapOf("Taurus" to 92.0, "Capricorn" to 91.0, "Cancer" to 87.0, "Scorpio" to 85.0),
            // Add remaining signs...
        )
        
        return compatibilityMap[signA]?.get(signB) ?: 75.0
    }
    
    private fun getSeason(month: Int): String {
        return when (month) {
            12, 1, 2 -> "Winter"
            3, 4, 5 -> "Spring"
            6, 7, 8 -> "Summer"
            9, 10, 11 -> "Fall"
            else -> "Unknown"
        }
    }
    
    private fun complementarySeasons(seasonA: String, seasonB: String): Boolean {
        return (seasonA == "Spring" && seasonB == "Fall") || 
               (seasonA == "Fall" && seasonB == "Spring") ||
               (seasonA == "Summer" && seasonB == "Winter") ||
               (seasonA == "Winter" && seasonB == "Summer")
    }
    
    private fun calculateNameEnergy(name: String): Double {
        // Simple name energy calculation based on vowel/consonant ratio
        val vowels = name.count { it.lowercase() in "aeiou" }
        val consonants = name.count { it.isLetter() && it.lowercase() !in "aeiou" }
        
        return if (consonants > 0) {
            (vowels.toDouble() / consonants.toDouble()) * 10.0
        } else 5.0
    }
    
    private fun generateTantricRecommendation(contentType: TantricContentType): String {
        return when (contentType) {
            TantricContentType.KAMA_SUTRA -> "Explore these practices during your intimate moments for deeper connection"
            TantricContentType.TANTRIC_PRACTICES -> "Incorporate these tantric techniques into your daily spiritual practice"
            TantricContentType.ROBERT_GREENE -> "Apply these seduction and relationship strategies to enhance your connection"
            TantricContentType.COMPATIBILITY -> "Use this compatibility insight to understand and improve your relationship dynamics"
        }
    }
}
