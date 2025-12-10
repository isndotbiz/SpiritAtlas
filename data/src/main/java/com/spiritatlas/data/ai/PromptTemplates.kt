package com.spiritatlas.data.ai

import com.spiritatlas.domain.ai.EnrichmentContext
import com.spiritatlas.domain.model.UserProfile
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Advanced prompt engineering for spiritual AI insights
 *
 * Uses:
 * - Few-shot learning with examples
 * - Structured output formatting
 * - Context framing for personality emphasis
 * - Role prompting for expertise
 * - Chain-of-thought reasoning
 */
object PromptTemplates {

    /**
     * System prompt that defines Claude's role and capabilities
     */
    fun getSystemPrompt(): String = """
        You are a Master Spiritual Advisor with deep expertise in:
        - Chaldean & Pythagorean Numerology
        - Sidereal & Tropical Astrology
        - Ayurvedic Dosha Theory
        - Human Design System
        - Tantric Energy Dynamics
        - Karmic Patterns & Soul Evolution

        Your communication style is:
        - Insightful yet accessible
        - Specific and evidence-based (reference the data)
        - Empowering and non-judgmental
        - Spiritually aligned with practical applications
        - Respectful of free will and personal growth

        Format all responses in clean markdown with:
        - Clear section headers (##)
        - Bullet points for key insights
        - Emphasis on actionable guidance
        - No emojis unless specifically requested

        Always ground your insights in the specific data provided.
        Reference exact numbers, placements, and configurations.
    """.trimIndent()

    /**
     * Profile enrichment prompt with few-shot examples
     */
    fun buildEnrichmentPrompt(context: EnrichmentContext): String {
        val accuracyNote = when (context.accuracyLevel) {
            "MAXIMUM", "EXCELLENT" -> "comprehensive data available for deep analysis"
            "GOOD" -> "solid data foundation for meaningful insights"
            "BASIC" -> "essential data present for foundational insights"
            else -> "limited data - focus on available information"
        }

        return """
            ## Profile Analysis Request

            **Data Quality:** ${context.completedFields}/${context.totalFields} fields ($accuracyNote)

            ### Available Data

            **Numerology Profile:**
            ${formatData(context.numerology)}

            **Astrological Chart:**
            ${formatData(context.astrology)}

            **Energy & Constitution:**
            ${formatData(context.energyProfile)}

            **Personal Context:**
            ${formatData(context.personalDetails)}

            ### Analysis Framework

            Provide a comprehensive spiritual analysis covering these dimensions:

            1. **Soul Purpose & Life Path**
               - What is their core soul mission in this lifetime?
               - How do their numerology and chart placements reveal their purpose?
               - Specific career paths and vocations that align

            2. **Natural Gifts & Strengths**
               - Innate talents indicated by Life Path, Expression, and Sun/Moon
               - How their chart supports or challenges these gifts
               - Practical ways to develop and express these strengths

            3. **Growth Edges & Karmic Lessons**
               - Challenges indicated by Karmic numbers and difficult aspects
               - What is their soul learning in this lifetime?
               - How to work with (not against) these lessons

            4. **Relationship Blueprint**
               - Communication style and emotional needs
               - Compatibility patterns and relationship dynamics
               - What they need in a partner for growth

            5. **Energetic Constitution**
               - Dosha balance and physical/mental tendencies
               - Human Design type and strategy
               - How to optimize energy and vitality

            6. **Spiritual Practices & Recommendations**
               - Personalized meditation/yoga practices
               - Best times for spiritual work (based on numerology)
               - Specific modalities that will resonate

            ### Output Format

            Structure your response in markdown with these exact section headers:

            ## Soul Purpose & Life Path
            [Your analysis here - 2-3 paragraphs, specific references to their data]

            ## Natural Gifts & Strengths
            - [Gift 1 with evidence]
            - [Gift 2 with evidence]
            - [Gift 3 with evidence]

            ## Growth Edges & Karmic Lessons
            [Analysis with specific chart references]

            ## Relationship Blueprint
            [How they love, communicate, and connect]

            ## Energetic Constitution
            [Physical and energetic tendencies]

            ## Spiritual Practices
            - [Practice 1]: [Why this works for them]
            - [Practice 2]: [Why this works for them]
            - [Practice 3]: [Why this works for them]

            ### Example Response Style

            GOOD: "Your Life Path 7 combined with Moon in Pisces creates a natural mystic and seeker. The 7 energy demands solitude for spiritual contemplation, while your Pisces Moon absorbs psychic information intuitively. This combination suggests your soul purpose involves bridging the spiritual and material realms through teaching, healing, or creative expression."

            BAD: "You're spiritual and intuitive." (too vague, no data references)

            Begin your analysis now, making specific references to their numerology numbers, astrological placements, and personal details.
        """.trimIndent()
    }

    /**
     * Daily insights prompt with current date context
     */
    fun buildDailyInsightsPrompt(
        profile: UserProfile,
        currentDate: LocalDate,
        personalYear: Int,
        personalMonth: Int,
        personalDay: Int
    ): String {
        val dateStr = currentDate.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"))

        return """
            ## Daily Spiritual Guidance

            **Date:** $dateStr
            **Personal Year:** $personalYear
            **Personal Month:** $personalMonth
            **Personal Day:** $personalDay

            ### Profile Summary
            ${buildProfileSummary(profile)}

            ### Today's Guidance Request

            Provide personalized daily insights for this person considering:

            1. **Numerological Day Energy**
               - What does their Personal Day ${personalDay} mean today?
               - How does it interact with their Life Path and Expression?
               - Key themes and opportunities

            2. **Astrological Transits** (if birth chart available)
               - Current moon phase and its effect
               - How today's cosmic weather affects their natal placements
               - Favorable and challenging periods

            3. **Energy & Wellness Focus**
               - Which chakra to focus on today
               - Dosha balancing recommendations
               - Physical and mental optimization tips

            4. **Timing & Activities**
               - Best times for important activities
               - What to initiate vs. what to complete
               - Social vs. solitary time recommendations

            5. **Spiritual Practice**
               - Specific meditation or mantra for today
               - Journaling prompts aligned with the day's energy
               - Evening reflection practice

            ### Output Format

            ## Today's Energy: [One-line theme]

            [2-3 sentence overview of the day's spiritual energy and how it relates to their chart]

            ## Key Opportunities
            - [Opportunity 1]
            - [Opportunity 2]
            - [Opportunity 3]

            ## Challenges to Navigate
            - [Challenge 1 with solution]
            - [Challenge 2 with solution]

            ## Optimal Times
            - **Morning (6-10am):** [Best for...]
            - **Midday (11am-2pm):** [Best for...]
            - **Afternoon (3-6pm):** [Best for...]
            - **Evening (7-10pm):** [Best for...]

            ## Today's Practice
            **Meditation Focus:** [Specific practice]
            **Affirmation:** "[Personalized affirmation]"
            **Evening Reflection:** [Journal prompt]

            Keep insights concise, actionable, and specific to their numerology and chart.
        """.trimIndent()
    }

    /**
     * Compatibility analysis prompt with both profiles
     */
    fun buildCompatibilityInsightsPrompt(
        profile1Summary: String,
        profile2Summary: String,
        calculatedScores: Map<String, Double>
    ): String = """
        ## Relationship Compatibility Analysis

        ### Profile A
        $profile1Summary

        ### Profile B
        $profile2Summary

        ### Calculated Compatibility Scores
        ${calculatedScores.entries.joinToString("\n") { "- ${it.key}: ${String.format("%.1f", it.value)}%" }}

        ### Analysis Request

        Provide deep relationship insights covering:

        1. **Soul Connection & Purpose**
           - Why did these souls choose to meet?
           - What is their karmic relationship contract?
           - How do they support each other's evolution?

        2. **Communication Dynamics**
           - How do their communication styles mesh?
           - Where are the natural friction points?
           - Techniques for clear, loving communication

        3. **Emotional Compatibility**
           - How do they give and receive love differently?
           - Attachment style interactions
           - Building emotional safety

        4. **Physical & Sexual Chemistry**
           - Tantric energy polarity
           - Passion vs. stability balance
           - Keeping the spark alive

        5. **Conflict Resolution**
           - How do they handle disagreements?
           - Common trigger points based on charts
           - Tools for healthy conflict

        6. **Long-term Potential**
           - Life path alignment
           - Growth trajectory compatibility
           - Keys to lasting partnership

        ### Output Format

        ## Soul Connection: [Relationship archetype]

        [3-4 sentences on why these souls are together and what they're learning]

        ## Strengths (What Works) ✨
        1. **[Strength 1 Name]**
           [Explanation with chart evidence]

        2. **[Strength 2 Name]**
           [Explanation with chart evidence]

        3. **[Strength 3 Name]**
           [Explanation with chart evidence]

        ## Growth Edges (Areas for Attention) ⚠️
        1. **[Challenge 1 Name]**
           **Why:** [Explanation]
           **Solution:** [Specific practice or technique]

        2. **[Challenge 2 Name]**
           **Why:** [Explanation]
           **Solution:** [Specific practice or technique]

        ## Recommendations for Thriving
        - **Daily Practice:** [Something to do together]
        - **Communication Tool:** [Specific technique]
        - **Intimacy Builder:** [Practice for connection]
        - **Conflict Protocol:** [How to fight fair]

        ## Long-term Outlook
        [2-3 paragraphs on the relationship's potential and keys to success]

        Ground all insights in specific numerology numbers, astrological aspects, and profile attributes.
    """.trimIndent()

    /**
     * Follow-up question prompt for multi-turn conversations
     */
    fun buildFollowUpPrompt(
        conversationHistory: List<String>,
        newQuestion: String,
        profileContext: String
    ): String = """
        ## Continuing Conversation

        **Profile Context:**
        $profileContext

        **Previous Discussion:**
        ${conversationHistory.takeLast(3).joinToString("\n\n---\n\n")}

        **New Question:**
        $newQuestion

        **Instructions:**
        - Reference previous insights when relevant
        - Maintain continuity with prior analysis
        - Provide specific, actionable guidance
        - Ground responses in their spiritual profile data
        - Keep response focused and concise (2-3 paragraphs)

        Answer the question now with the context of our previous conversation.
    """.trimIndent()

    /**
     * Format data map for prompts with better structure
     */
    private fun formatData(data: Map<String, Any>): String {
        if (data.isEmpty()) return "*No data available*"

        return data.entries.joinToString("\n") { (key, value) ->
            val formattedKey = key.replace("_", " ").split(" ")
                .joinToString(" ") { it.capitalize() }
            "- **$formattedKey:** $value"
        }
    }

    /**
     * Build concise profile summary for prompts
     */
    private fun buildProfileSummary(profile: UserProfile): String = buildString {
        profile.name?.let { appendLine("**Name:** $it") }
        profile.birthDateTime?.let {
            appendLine("**Birth:** ${it.format(DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm a"))}")
        }
        profile.birthPlace?.let { appendLine("**Birth Place:** ${it.city}, ${it.country}") }

        // Add key numerology if available
        val enrichment = profile.enrichmentResult
        if (enrichment != null) {
            appendLine("\n*Previous insights available from ${profile.enrichmentGeneratedAt?.format(DateTimeFormatter.ofPattern("MMM d"))}*")
        }
    }

    /**
     * Streaming response marker detection
     */
    fun isStreamingChunk(text: String): Boolean {
        return text.startsWith("data: ") || text.contains("\"type\":\"content_block")
    }

    /**
     * Extract JSON from streaming response
     */
    fun extractStreamingContent(chunk: String): String? {
        if (!chunk.startsWith("data: ")) return null

        val json = chunk.removePrefix("data: ").trim()
        if (json == "[DONE]") return null

        return try {
            // Parse streaming JSON chunk
            org.json.JSONObject(json)
                .optJSONObject("delta")
                ?.optString("text")
        } catch (e: Exception) {
            null
        }
    }
}
