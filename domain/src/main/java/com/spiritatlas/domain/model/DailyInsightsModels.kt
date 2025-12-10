package com.spiritatlas.domain.model

import androidx.compose.runtime.Immutable
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Domain models for personalized daily spiritual insights
 *
 * Features:
 * - Numerological day analysis
 * - Astrological transits
 * - Energy and wellness guidance
 * - Optimal timing recommendations
 * - Spiritual practices
 */

@Immutable
data class DailyInsight(
    val profileId: String,
    val date: LocalDate,
    val personalYear: Int,
    val personalMonth: Int,
    val personalDay: Int,
    val theme: String, // One-line theme for the day
    val overview: String, // 2-3 sentence overview
    val opportunities: List<DailyOpportunity>,
    val challenges: List<DailyChallenge>,
    val optimalTimes: OptimalTimesGuide,
    val practice: SpiritualPractice,
    val energyFocus: EnergyFocus,
    val transitInfo: TransitInfo? = null, // Astrological transits if available
    val generatedAt: LocalDateTime = LocalDateTime.now(),
    val aiProvider: String? = null
) {
    val dayOfWeek: String get() = date.dayOfWeek.name.lowercase().capitalize()
    val formattedDate: String get() = date.toString()
}

@Immutable
data class DailyOpportunity(
    val title: String,
    val description: String,
    val category: OpportunityCategory,
    val timeWindow: TimeWindow? = null
)

enum class OpportunityCategory {
    CAREER,
    RELATIONSHIPS,
    CREATIVITY,
    SPIRITUAL_GROWTH,
    HEALTH,
    COMMUNICATION,
    LEARNING,
    MANIFESTATION
}

@Immutable
data class DailyChallenge(
    val title: String,
    val description: String,
    val solution: String,
    val severity: ChallengeSeverity
)

@Immutable
data class OptimalTimesGuide(
    val morning: TimeGuidance, // 6-10am
    val midday: TimeGuidance, // 11am-2pm
    val afternoon: TimeGuidance, // 3-6pm
    val evening: TimeGuidance // 7-10pm
)

@Immutable
data class TimeGuidance(
    val period: TimePeriod,
    val bestFor: List<String>,
    val avoid: List<String> = emptyList(),
    val energyLevel: EnergyLevel
)

enum class TimePeriod {
    MORNING,
    MIDDAY,
    AFTERNOON,
    EVENING,
    LATE_NIGHT
}

enum class EnergyLevel {
    HIGH,
    MODERATE,
    LOW,
    VARIABLE
}

@Immutable
data class SpiritualPractice(
    val meditationFocus: String,
    val affirmation: String,
    val eveningReflection: String,
    val additionalPractices: List<PracticeRecommendation> = emptyList()
)

@Immutable
data class PracticeRecommendation(
    val name: String,
    val description: String,
    val duration: String, // e.g., "10 minutes"
    val optimalTime: TimePeriod
)

@Immutable
data class EnergyFocus(
    val chakra: ChakraFocus,
    val dosha: DoshaBalance,
    val elementalEnergy: ElementalEnergy
)

enum class ChakraFocus(val displayName: String, val color: String) {
    ROOT("Root (Muladhara)", "#FF0000"),
    SACRAL("Sacral (Svadhisthana)", "#FF6600"),
    SOLAR_PLEXUS("Solar Plexus (Manipura)", "#FFCC00"),
    HEART("Heart (Anahata)", "#00CC66"),
    THROAT("Throat (Vishuddha)", "#0099FF"),
    THIRD_EYE("Third Eye (Ajna)", "#6600CC"),
    CROWN("Crown (Sahasrara)", "#9900CC")
}

@Immutable
data class DoshaBalance(
    val recommendation: String,
    val activities: List<String>,
    val foodsToFavor: List<String>,
    val foodsToAvoid: List<String>
)

enum class ElementalEnergy(val description: String) {
    FIRE("Dynamic, action-oriented, transformative"),
    WATER("Emotional, intuitive, flowing"),
    EARTH("Grounding, stable, practical"),
    AIR("Mental, communicative, expansive"),
    ETHER("Spiritual, transcendent, universal")
}

@Immutable
data class TransitInfo(
    val moonPhase: MoonPhase,
    val currentSign: String,
    val majorTransits: List<Transit>,
    val retrogradeWarnings: List<String> = emptyList()
)

@Immutable
data class Transit(
    val planet: String,
    val aspect: String,
    val interpretation: String,
    val influence: TransitInfluence
)

enum class TransitInfluence {
    HIGHLY_POSITIVE,
    POSITIVE,
    NEUTRAL,
    CHALLENGING,
    HIGHLY_CHALLENGING
}

enum class MoonPhase(val displayName: String, val emoji: String) {
    NEW_MOON("New Moon", "🌑"),
    WAXING_CRESCENT("Waxing Crescent", "🌒"),
    FIRST_QUARTER("First Quarter", "🌓"),
    WAXING_GIBBOUS("Waxing Gibbous", "🌔"),
    FULL_MOON("Full Moon", "🌕"),
    WANING_GIBBOUS("Waning Gibbous", "🌖"),
    THIRD_QUARTER("Third Quarter", "🌗"),
    WANING_CRESCENT("Waning Crescent", "🌘")
}

@Immutable
data class TimeWindow(
    val start: String, // "09:00"
    val end: String, // "11:00"
    val description: String
)

/**
 * Weekly insights summary
 */
@Immutable
data class WeeklyInsights(
    val profileId: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val weekTheme: String,
    val dailyInsights: List<DailyInsight>,
    val weeklyHighlights: List<String>,
    val focusAreas: List<String>,
    val generatedAt: LocalDateTime = LocalDateTime.now()
) {
    val weekNumber: Int get() = startDate.dayOfYear / 7
}

/**
 * Monthly insights summary
 */
@Immutable
data class MonthlyInsights(
    val profileId: String,
    val month: Int,
    val year: Int,
    val personalMonth: Int,
    val monthTheme: String,
    val overview: String,
    val keyDates: List<KeyDate>,
    val focusAreas: List<String>,
    val challenges: List<String>,
    val opportunities: List<String>,
    val generatedAt: LocalDateTime = LocalDateTime.now()
)

@Immutable
data class KeyDate(
    val date: LocalDate,
    val significance: String,
    val recommendation: String
)

/**
 * Cache for daily insights to avoid regeneration
 */
data class DailyInsightCache(
    val profileId: String,
    val date: LocalDate,
    val insight: DailyInsight,
    val cachedAt: LocalDateTime = LocalDateTime.now()
) {
    fun isExpired(): Boolean {
        val now = LocalDateTime.now()
        val cacheDate = cachedAt.toLocalDate()
        val currentDate = now.toLocalDate()

        // Cache expires if:
        // 1. It's a different day
        // 2. Or it's the same day but was generated yesterday (for next day prep)
        return currentDate != cacheDate && date != currentDate
    }
}
