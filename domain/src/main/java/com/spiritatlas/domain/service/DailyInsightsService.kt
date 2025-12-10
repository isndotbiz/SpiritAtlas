package com.spiritatlas.domain.service

import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.model.*
import java.time.LocalDate

/**
 * Service for generating personalized daily spiritual insights
 *
 * Combines numerology cycles, astrological transits, and AI-powered guidance
 * to provide actionable daily recommendations.
 */
interface DailyInsightsService {

    /**
     * Generates a complete daily insight for a profile
     *
     * @param profile The user profile to generate insights for
     * @param date The date to generate insights for (defaults to today)
     * @param useCache Whether to use cached insights if available
     * @return Result containing the daily insight or error
     */
    suspend fun generateDailyInsight(
        profile: UserProfile,
        date: LocalDate = LocalDate.now(),
        useCache: Boolean = true
    ): Result<DailyInsight>

    /**
     * Generates insights for a date range (week or month)
     *
     * @param profile The user profile
     * @param startDate Start of the range
     * @param endDate End of the range
     * @return Result containing list of daily insights
     */
    suspend fun generateInsightRange(
        profile: UserProfile,
        startDate: LocalDate,
        endDate: LocalDate
    ): Result<List<DailyInsight>>

    /**
     * Generates weekly insights summary
     *
     * @param profile The user profile
     * @param startDate First day of the week
     * @return Result containing weekly insights
     */
    suspend fun generateWeeklyInsights(
        profile: UserProfile,
        startDate: LocalDate = LocalDate.now()
    ): Result<WeeklyInsights>

    /**
     * Generates monthly insights summary
     *
     * @param profile The user profile
     * @param month Month (1-12)
     * @param year Year
     * @return Result containing monthly insights
     */
    suspend fun generateMonthlyInsights(
        profile: UserProfile,
        month: Int = LocalDate.now().monthValue,
        year: Int = LocalDate.now().year
    ): Result<MonthlyInsights>

    /**
     * Gets cached insight if available and not expired
     *
     * @param profileId Profile ID
     * @param date Date to check
     * @return Cached insight or null
     */
    suspend fun getCachedInsight(
        profileId: String,
        date: LocalDate
    ): DailyInsight?

    /**
     * Clears all cached insights for a profile
     *
     * @param profileId Profile ID
     */
    suspend fun clearCache(profileId: String): Result<Unit>

    /**
     * Calculates numerology cycle numbers for a date
     *
     * @param birthDate Birth date from profile
     * @param currentDate Date to calculate for
     * @return Triple of (personalYear, personalMonth, personalDay)
     */
    fun calculatePersonalCycles(
        birthDate: LocalDate,
        currentDate: LocalDate
    ): Triple<Int, Int, Int>
}
