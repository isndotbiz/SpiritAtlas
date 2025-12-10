package com.spiritatlas.data.ai

import com.spiritatlas.domain.model.*

/**
 * Advanced parser for AI-generated responses
 *
 * Features:
 * - Markdown structure extraction
 * - Key point extraction
 * - Section parsing
 * - List and bullet point parsing
 * - Affirmation and practice extraction
 * - Error-tolerant parsing
 */
object ResponseParser {

    /**
     * Parses AI response into structured daily insight
     */
    fun parseDailyInsight(
        aiText: String,
        profileId: String,
        date: java.time.LocalDate,
        personalYear: Int,
        personalMonth: Int,
        personalDay: Int
    ): DailyInsight {
        val sections = extractSections(aiText)

        return DailyInsight(
            profileId = profileId,
            date = date,
            personalYear = personalYear,
            personalMonth = personalMonth,
            personalDay = personalDay,
            theme = extractTheme(aiText, sections),
            overview = extractOverview(aiText, sections),
            opportunities = extractOpportunities(sections),
            challenges = extractChallenges(sections),
            optimalTimes = extractOptimalTimes(sections),
            practice = extractPractice(sections),
            energyFocus = extractEnergyFocus(sections),
            transitInfo = extractTransitInfo(sections)
        )
    }

    /**
     * Extracts compatibility insights from AI response
     */
    fun parseCompatibilityInsights(aiText: String): Map<String, List<String>> {
        val sections = extractSections(aiText)
        val insights = mutableMapOf<String, List<String>>()

        // Extract different compatibility dimensions
        insights["strengths"] = extractBulletPoints(
            sections["strengths"] ?: sections["what works"] ?: ""
        )
        insights["challenges"] = extractBulletPoints(
            sections["growth edges"] ?: sections["challenges"] ?: ""
        )
        insights["recommendations"] = extractBulletPoints(
            sections["recommendations"] ?: sections["advice"] ?: ""
        )
        insights["soul_connection"] = extractBulletPoints(
            sections["soul connection"] ?: ""
        )

        return insights
    }

    /**
     * Extracts key points from any AI response
     */
    fun extractKeyPoints(text: String): List<String> {
        val points = mutableListOf<String>()

        // Extract bullet points
        points.addAll(extractBulletPoints(text))

        // Extract numbered points
        points.addAll(extractNumberedPoints(text))

        // Extract sentences with key indicators
        val keyIndicators = listOf("importantly", "key", "essential", "critical", "must")
        text.split("\n").forEach { line ->
            if (keyIndicators.any { indicator -> line.lowercase().contains(indicator) }) {
                val cleaned = line.trim().removePrefix("-").removePrefix("*").trim()
                if (cleaned.isNotEmpty() && cleaned.length > 20) {
                    points.add(cleaned)
                }
            }
        }

        return points.distinct().take(10) // Limit to top 10
    }

    /**
     * Extracts affirmations from text
     */
    fun extractAffirmations(text: String): List<String> {
        val affirmations = mutableListOf<String>()

        // Look for affirmation markers
        val affirmationMarkers = listOf(
            "affirmation:",
            "mantra:",
            "repeat:",
            "\"i am",
            "\"i have",
            "\"i choose"
        )

        text.split("\n").forEach { line ->
            val lowerLine = line.lowercase()
            if (affirmationMarkers.any { marker -> lowerLine.contains(marker) }) {
                // Extract the affirmation text
                val affirmation = line
                    .substringAfter(":", line)
                    .trim()
                    .removeSurrounding("\"")
                    .removeSurrounding("*")
                    .trim()

                if (affirmation.isNotEmpty() && affirmation.length > 10) {
                    affirmations.add(affirmation)
                }
            }
        }

        return affirmations
    }

    /**
     * Extracts practices and recommendations
     */
    fun extractPractices(text: String): List<PracticeRecommendation> {
        val practices = mutableListOf<PracticeRecommendation>()
        val sections = extractSections(text)

        val practiceSection = sections["spiritual practices"]
            ?: sections["practices"]
            ?: sections["recommendations"]
            ?: ""

        val bulletPoints = extractBulletPoints(practiceSection)

        bulletPoints.forEach { point ->
            // Try to extract practice name and description
            val parts = point.split(":", limit = 2)
            if (parts.size == 2) {
                practices.add(
                    PracticeRecommendation(
                        name = parts[0].trim(),
                        description = parts[1].trim(),
                        duration = extractDuration(point),
                        optimalTime = inferOptimalTime(point)
                    )
                )
            } else {
                practices.add(
                    PracticeRecommendation(
                        name = point.take(50),
                        description = point,
                        duration = extractDuration(point),
                        optimalTime = TimePeriod.MORNING
                    )
                )
            }
        }

        return practices.take(5) // Limit to 5 practices
    }

    /**
     * Extracts warnings and cautions
     */
    fun extractWarnings(text: String): List<String> {
        val warnings = mutableListOf<String>()

        val warningMarkers = listOf("warning", "caution", "avoid", "be careful", "watch out")

        text.split("\n").forEach { line ->
            val lowerLine = line.lowercase()
            if (warningMarkers.any { marker -> lowerLine.contains(marker) }) {
                val cleaned = line.trim().removePrefix("-").removePrefix("*").trim()
                if (cleaned.isNotEmpty()) {
                    warnings.add(cleaned)
                }
            }
        }

        return warnings
    }

    // Private helper methods

    /**
     * Extracts sections from markdown text
     */
    private fun extractSections(text: String): Map<String, String> {
        val sections = mutableMapOf<String, String>()
        val lines = text.lines()

        var currentSection = "overview"
        val currentContent = StringBuilder()

        for (line in lines) {
            when {
                line.startsWith("##") || line.startsWith("**") && line.endsWith("**") -> {
                    // Save previous section
                    if (currentContent.isNotEmpty()) {
                        sections[currentSection.lowercase()] = currentContent.toString().trim()
                        currentContent.clear()
                    }

                    // Start new section
                    currentSection = line
                        .removePrefix("##")
                        .removePrefix("**")
                        .removeSuffix("**")
                        .trim()
                }
                else -> {
                    currentContent.appendLine(line)
                }
            }
        }

        // Save last section
        if (currentContent.isNotEmpty()) {
            sections[currentSection.lowercase()] = currentContent.toString().trim()
        }

        return sections
    }

    private fun extractTheme(text: String, sections: Map<String, String>): String {
        // Look for theme indicators
        val themeMarkers = listOf(
            "today's energy:",
            "day theme:",
            "theme:",
            "energy:"
        )

        text.lines().forEach { line ->
            val lowerLine = line.lowercase()
            themeMarkers.forEach { marker ->
                if (lowerLine.contains(marker)) {
                    return line.substringAfter(":", line).trim()
                }
            }
        }

        // Fallback: use first sentence of overview
        return sections["overview"]?.split(".")?.firstOrNull()?.trim()
            ?: "Day of Growth and Alignment"
    }

    private fun extractOverview(text: String, sections: Map<String, String>): String {
        return sections["overview"]
            ?: sections["summary"]
            ?: text.split("\n\n").firstOrNull()?.take(300)
            ?: ""
    }

    private fun extractOpportunities(sections: Map<String, String>): List<DailyOpportunity> {
        val opportunitiesText = sections["key opportunities"]
            ?: sections["opportunities"]
            ?: ""

        val bulletPoints = extractBulletPoints(opportunitiesText)

        return bulletPoints.take(5).mapIndexed { index, point ->
            DailyOpportunity(
                title = "Opportunity ${index + 1}",
                description = point,
                category = inferOpportunityCategory(point)
            )
        }
    }

    private fun extractChallenges(sections: Map<String, String>): List<DailyChallenge> {
        val challengesText = sections["challenges to navigate"]
            ?: sections["challenges"]
            ?: sections["growth edges"]
            ?: ""

        val bulletPoints = extractBulletPoints(challengesText)

        return bulletPoints.take(3).mapIndexed { index, point ->
            val parts = point.split(":", limit = 2)
            if (parts.size == 2) {
                DailyChallenge(
                    title = parts[0].trim(),
                    description = parts[1].substringBefore("Solution").trim(),
                    solution = extractSolution(parts[1]),
                    severity = ChallengeSeverity.MODERATE
                )
            } else {
                DailyChallenge(
                    title = "Challenge ${index + 1}",
                    description = point,
                    solution = "Stay mindful and centered",
                    severity = ChallengeSeverity.MINOR
                )
            }
        }
    }

    private fun extractOptimalTimes(sections: Map<String, String>): OptimalTimesGuide {
        val timesText = sections["optimal times"] ?: ""

        return OptimalTimesGuide(
            morning = extractTimeGuidance(timesText, "morning", TimePeriod.MORNING),
            midday = extractTimeGuidance(timesText, "midday", TimePeriod.MIDDAY),
            afternoon = extractTimeGuidance(timesText, "afternoon", TimePeriod.AFTERNOON),
            evening = extractTimeGuidance(timesText, "evening", TimePeriod.EVENING)
        )
    }

    private fun extractTimeGuidance(text: String, periodName: String, period: TimePeriod): TimeGuidance {
        val lines = text.lines()
        val periodLine = lines.find { it.lowercase().contains(periodName) } ?: ""

        val activities = periodLine
            .substringAfter(":", periodLine)
            .split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        return TimeGuidance(
            period = period,
            bestFor = activities.ifEmpty { listOf("General activities") },
            energyLevel = inferEnergyLevel(periodName)
        )
    }

    private fun extractPractice(sections: Map<String, String>): SpiritualPractice {
        val practiceText = sections["today's practice"]
            ?: sections["spiritual practice"]
            ?: ""

        val affirmation = extractAffirmations(practiceText).firstOrNull()
            ?: "I am aligned with my highest purpose"

        val meditation = practiceText.lines()
            .find { it.lowercase().contains("meditation") }
            ?.substringAfter(":", "")
            ?.trim()
            ?: "Breath awareness"

        val reflection = practiceText.lines()
            .find { it.lowercase().contains("reflection") || it.lowercase().contains("journal") }
            ?.substringAfter(":", "")
            ?.trim()
            ?: "What did I learn today?"

        return SpiritualPractice(
            meditationFocus = meditation,
            affirmation = affirmation,
            eveningReflection = reflection,
            additionalPractices = extractPractices(practiceText)
        )
    }

    private fun extractEnergyFocus(sections: Map<String, String>): EnergyFocus {
        val energyText = sections["energy focus"]
            ?: sections["energetic constitution"]
            ?: ""

        val chakra = inferChakra(energyText)
        val element = inferElement(energyText)

        return EnergyFocus(
            chakra = chakra,
            dosha = DoshaBalance(
                "Balance all three doshas",
                listOf("Gentle movement", "Mindful eating"),
                listOf("Fresh, whole foods"),
                emptyList()
            ),
            elementalEnergy = element
        )
    }

    private fun extractTransitInfo(sections: Map<String, String>): TransitInfo? {
        val transitText = sections["astrological transits"] ?: return null

        return TransitInfo(
            moonPhase = MoonPhase.WAXING_CRESCENT, // Would need actual calculation
            currentSign = "Aries", // Would need actual calculation
            majorTransits = emptyList()
        )
    }

    private fun extractBulletPoints(text: String): List<String> {
        return text.lines()
            .filter { it.trim().startsWith("-") || it.trim().startsWith("*") || it.trim().startsWith("•") }
            .map {
                it.trim()
                    .removePrefix("-")
                    .removePrefix("*")
                    .removePrefix("•")
                    .trim()
            }
            .filter { it.isNotEmpty() }
    }

    private fun extractNumberedPoints(text: String): List<String> {
        val numberPattern = Regex("^\\d+\\.\\s+")
        return text.lines()
            .filter { numberPattern.containsMatchIn(it.trim()) }
            .map { it.trim().replace(numberPattern, "") }
            .filter { it.isNotEmpty() }
    }

    private fun extractSolution(text: String): String {
        val solution = text.substringAfter("solution:", "")
            .substringAfter("try:", "")
            .trim()

        return solution.ifEmpty { "Stay centered and mindful" }
    }

    private fun extractDuration(text: String): String {
        val durationPattern = Regex("\\d+\\s*(minutes?|mins?|hours?|hrs?)")
        return durationPattern.find(text)?.value ?: "10 minutes"
    }

    private fun inferOptimalTime(text: String): TimePeriod {
        val lowerText = text.lowercase()
        return when {
            lowerText.contains("morning") || lowerText.contains("sunrise") -> TimePeriod.MORNING
            lowerText.contains("evening") || lowerText.contains("sunset") -> TimePeriod.EVENING
            lowerText.contains("midday") || lowerText.contains("noon") -> TimePeriod.MIDDAY
            lowerText.contains("afternoon") -> TimePeriod.AFTERNOON
            else -> TimePeriod.MORNING
        }
    }

    private fun inferEnergyLevel(period: String): EnergyLevel {
        return when (period.lowercase()) {
            "morning" -> EnergyLevel.HIGH
            "midday" -> EnergyLevel.MODERATE
            "afternoon" -> EnergyLevel.MODERATE
            "evening" -> EnergyLevel.LOW
            else -> EnergyLevel.MODERATE
        }
    }

    private fun inferOpportunityCategory(text: String): OpportunityCategory {
        val lowerText = text.lowercase()
        return when {
            lowerText.contains("career") || lowerText.contains("work") -> OpportunityCategory.CAREER
            lowerText.contains("relationship") || lowerText.contains("love") -> OpportunityCategory.RELATIONSHIPS
            lowerText.contains("creative") || lowerText.contains("art") -> OpportunityCategory.CREATIVITY
            lowerText.contains("spiritual") || lowerText.contains("meditation") -> OpportunityCategory.SPIRITUAL_GROWTH
            lowerText.contains("health") || lowerText.contains("wellness") -> OpportunityCategory.HEALTH
            lowerText.contains("communication") || lowerText.contains("express") -> OpportunityCategory.COMMUNICATION
            lowerText.contains("learn") || lowerText.contains("study") -> OpportunityCategory.LEARNING
            else -> OpportunityCategory.MANIFESTATION
        }
    }

    private fun inferChakra(text: String): ChakraFocus {
        val lowerText = text.lowercase()
        return when {
            lowerText.contains("root") || lowerText.contains("muladhara") -> ChakraFocus.ROOT
            lowerText.contains("sacral") || lowerText.contains("svadhisthana") -> ChakraFocus.SACRAL
            lowerText.contains("solar") || lowerText.contains("manipura") -> ChakraFocus.SOLAR_PLEXUS
            lowerText.contains("heart") || lowerText.contains("anahata") -> ChakraFocus.HEART
            lowerText.contains("throat") || lowerText.contains("vishuddha") -> ChakraFocus.THROAT
            lowerText.contains("third eye") || lowerText.contains("ajna") -> ChakraFocus.THIRD_EYE
            lowerText.contains("crown") || lowerText.contains("sahasrara") -> ChakraFocus.CROWN
            else -> ChakraFocus.HEART
        }
    }

    private fun inferElement(text: String): ElementalEnergy {
        val lowerText = text.lowercase()
        return when {
            lowerText.contains("fire") || lowerText.contains("dynamic") -> ElementalEnergy.FIRE
            lowerText.contains("water") || lowerText.contains("emotion") -> ElementalEnergy.WATER
            lowerText.contains("earth") || lowerText.contains("ground") -> ElementalEnergy.EARTH
            lowerText.contains("air") || lowerText.contains("mental") -> ElementalEnergy.AIR
            lowerText.contains("ether") || lowerText.contains("spiritual") -> ElementalEnergy.ETHER
            else -> ElementalEnergy.EARTH
        }
    }
}
