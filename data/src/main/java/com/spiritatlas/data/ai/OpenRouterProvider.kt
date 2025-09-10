package com.spiritatlas.data.ai

import com.spiritatlas.data.BuildConfig
import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.ai.AiTextProvider
import com.spiritatlas.domain.ai.EnrichmentContext
import com.spiritatlas.domain.ai.EnrichmentResult
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Inject

class OpenRouterProvider @Inject constructor(
    @Named("openrouter") private val retrofit: Retrofit
) : AiTextProvider {
    
    private val api = retrofit.create(OpenRouterApi::class.java)
    
    override suspend fun generateEnrichment(context: EnrichmentContext): Result<EnrichmentResult> {
        return try {
            if (BuildConfig.OPENROUTER_API_KEY.isEmpty()) {
                return Result.Error(IllegalStateException("OpenRouter API key not configured"))
            }
            
            val prompt = buildPrompt(context)
            // Model selection based on field count (adjusted for 27-field model)
            val model = when {
                context.completedFields >= 24 -> "openai/gpt-4o-mini" // Master analysis
                context.completedFields >= 20 -> "openai/gpt-4o-mini" // Comprehensive
                context.completedFields >= 10 -> "openai/gpt-3.5-turbo" // Detailed
                else -> "openai/gpt-3.5-turbo" // Basic/minimal
            }
            
            val response = api.complete(
                authorization = "Bearer ${BuildConfig.OPENROUTER_API_KEY}",
                request = CompletionRequest(
                    model = model,
                    messages = listOf(Message("user", prompt)),
                    maxTokens = when {
                        context.completedFields >= 24 -> 4000 // Master (3000 words)
                        context.completedFields >= 20 -> 3000 // Comprehensive (2000 words)
                        context.completedFields >= 10 -> 1500 // Detailed (1000 words)
                        context.completedFields >= 3 -> 500   // Basic (300 words)
                        else -> 250                           // Minimal (150 words)
                    }
                )
            )
            
            Result.Success(EnrichmentResult(
                text = response.choices.firstOrNull()?.message?.content ?: "",
                confidence = 0.8f
            ))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    override suspend fun isAvailable(): Boolean {
        return BuildConfig.OPENROUTER_API_KEY.isNotEmpty()
    }
    
    private fun buildPrompt(context: EnrichmentContext): String {
        return when {
            context.completedFields >= 24 -> buildMasterPrompt(context, 3000)
            context.completedFields >= 20 -> buildComprehensivePrompt(context, 2000)
            context.completedFields >= 10 -> buildDetailedPrompt(context, 1000)
            context.completedFields >= 3 -> buildBasicPrompt(context, 300)
            else -> buildMinimalPrompt(context, 150)
        }
    }
    
    private fun buildMinimalPrompt(context: EnrichmentContext, words: Int): String {
        return """
            Provide a very brief spiritual insight (${words} words max) based on:
            Numerology: ${context.numerology}
            Astrology: ${context.astrology}
            Energy Profile: ${context.energyProfile}
            Personal Details: ${context.personalDetails}
            Tone: positive, empowering, mystical.
        """.trimIndent()
    }
    
    private fun buildBasicPrompt(context: EnrichmentContext, words: Int): String {
        return """
            Based on the following spiritual profile data (${context.completedFields}/${context.totalFields} fields), provide a concise spiritual insight (~${words} words):
            
            Numerology: ${context.numerology}
            Astrology: ${context.astrology}
            Energy Profile: ${context.energyProfile}
            Personal Details: ${context.personalDetails}
            
            Focus on the most significant themes. Use a warm, insightful tone with light emoji.
        """.trimIndent()
    }
    
    private fun buildDetailedPrompt(context: EnrichmentContext, words: Int): String {
        return """
            Create a detailed spiritual analysis (~${words} words) using:
            - Numerology (Pythagorean, Chaldean, master numbers, karmic debts)
            - Astrology (tropical/sidereal sun, houses, aspects, transits)
            - Energy systems (chakras, meridians, human design style energy)
            - Personal and environmental context
            
            Data:
            Personal: ${formatMap(context.personalDetails)}
            Numerology: ${formatMap(context.numerology)}
            Astrology: ${formatMap(context.astrology)}
            Energy: ${formatMap(context.energyProfile)}
            
            Guidelines:
            - Use structured headings and lists
            - Reference data specifically
            - Balance mystical depth with practical steps
            - Include supportive emoji sparingly
        """.trimIndent()
    }
    
    private fun buildComprehensivePrompt(context: EnrichmentContext, words: Int): String {
        return """
            Generate a comprehensive spiritual report (~${words} words).
            
            Formatting requirements (Markdown):
            - Use clear H1-H3 headings, bold, italics, quotes, and bullet lists
            - Include small tables where helpful (traits, scores)
            - Sprinkle relevant emoji tastefully
            - Insert 2-3 lightweight images using markdown: ![alt](https://img.icons8.com/?size=100&id=59852&format=png)
            
            Sections:
            1) Core Soul Blueprint
            2) Personality & Character Analysis
            3) Life Path & Destiny
            4) Relationships & Compatibility
            5) Health & Wellbeing
            6) Future Guidance & Manifestation
            7) Practical Action Steps
            
            Data to use and cite explicitly:
            Personal: ${formatMap(context.personalDetails)}
            Numerology: ${formatMap(context.numerology)}
            Astrology: ${formatMap(context.astrology)}
            Energy: ${formatMap(context.energyProfile)}
            
            Tone: Insightful, empowering, mystical-practical balance. Use tasteful emoji.
        """.trimIndent()
    }
    
    private fun buildMasterPrompt(context: EnrichmentContext, words: Int): String {
        return """
            You are an expert spiritual advisor with mastery in astrology, numerology, hermeticism, human design-like systems, chakras, and modern psychology.
            
            Generate an elite spiritual analysis (~${words} words) with deep cross-system synthesis. Include explicit references to zodiac modalities, elements, decans, ayanamsa, numerological reductions, master numbers, karmic indicators, energy centers, and planetary archetypes.
            
            Strict formatting (Markdown):
            - Hero title (H1) with bold and emoji
            - Intro quote block
            - Multiple H2 sections with H3 subsections
            - 1-2 small tables (e.g., trait vs score)
            - Callout blocks using > and lists
            - 3-4 lightweight images via markdown (e.g., icons8 URLs)
            - Conclude with bold affirmation and a numbered 3-day plan
            
            Data:
            Personal: ${formatMap(context.personalDetails)}
            Numerology: ${formatMap(context.numerology)}
            Astrology: ${formatMap(context.astrology)}
            Energy: ${formatMap(context.energyProfile)}
            
            Style: Profound yet grounded; warm; emoji used meaningfully.
        """.trimIndent()
    }
    
    private fun formatMap(map: Map<String, Any>): String {
        return map.entries.joinToString("\n") { (key, value) -> "- $key: $value" }
    }
}


