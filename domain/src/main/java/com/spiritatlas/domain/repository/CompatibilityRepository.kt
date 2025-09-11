package com.spiritatlas.domain.repository

import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.model.*
import com.spiritatlas.domain.tantric.TantricContent
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for relationship compatibility analysis
 * Provides methods for analyzing compatibility between user profiles
 */
interface CompatibilityRepository {
    
    /**
     * Analyzes compatibility between two user profiles
     * @param profileA First profile to analyze
     * @param profileB Second profile to analyze  
     * @return Flow of Result containing CompatibilityReport or error
     */
    fun analyzeCompatibility(
        profileA: UserProfile,
        profileB: UserProfile
    ): Flow<Result<CompatibilityReport>>
    
    /**
     * Gets cached compatibility reports for a specific profile
     * @param profileId Profile ID to get reports for
     * @return Flow of cached compatibility reports
     */
    fun getCachedCompatibilityReports(
        profileId: String
    ): Flow<List<CompatibilityReport>>
    
    /**
     * Saves a compatibility report for future reference
     * @param report The compatibility report to save
     * @return Result indicating success or failure
     */
    suspend fun saveCompatibilityReport(
        report: CompatibilityReport
    ): Result<Unit>
    
    /**
     * Deletes a cached compatibility report
     * @param reportId ID of the report to delete
     * @return Result indicating success or failure
     */
    suspend fun deleteCompatibilityReport(
        reportId: String
    ): Result<Unit>
    
    /**
     * Gets all available tantric content for compatibility matching
     * @return Flow of tantric content items
     */
    fun getTantricContentForCompatibility(): Flow<List<TantricContent>>
    
    /**
     * Searches for compatible profiles based on criteria
     * @param baseProfile Base profile to find matches for
     * @param criteria Compatibility criteria for matching
     * @return Flow of compatible profile matches
     */
    fun findCompatibleProfiles(
        baseProfile: UserProfile,
        criteria: CompatibilityCriteria = CompatibilityCriteria()
    ): Flow<List<ProfileMatch>>
}

/**
 * Criteria for finding compatible profiles
 */
data class CompatibilityCriteria(
    val minCompatibilityScore: Double = 60.0,
    val preferredCategories: List<CompatibilityCategory> = emptyList(),
    val maxDistance: Double? = null, // km radius for location-based matching
    val ageRange: IntRange? = null,
    val includeReverseMatches: Boolean = true // Include profiles that would match the base profile
)

/**
 * Profile match result from compatibility search
 */
data class ProfileMatch(
    val profile: UserProfile,
    val compatibilityPreview: CompatibilityPreview,
    val matchReason: String,
    val confidence: Double
)

/**
 * Lightweight preview of compatibility for search results
 */
data class CompatibilityPreview(
    val overallScore: Double,
    val topStrength: String,
    val topChallenge: String,
    val compatibilityLevel: CompatibilityLevel
)
