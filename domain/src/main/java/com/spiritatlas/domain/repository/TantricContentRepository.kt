package com.spiritatlas.domain.repository

import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.tantric.*
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for tantric content operations
 */
interface TantricContentRepository {
    
    /**
     * Get all tantric content from local and remote sources
     */
    fun getAllTantricContent(): Flow<Result<List<TantricContent>>>
    
    /**
     * Get tantric content filtered by type
     */
    fun getTantricContentByType(type: TantricContentType): Flow<Result<List<TantricContent>>>
    
    /**
     * Search tantric content by query string
     */
    fun searchTantricContent(query: String): Flow<Result<List<TantricContent>>>
    
    /**
     * Get personalized recommendations based on user profile
     */
    fun getPersonalizedRecommendations(): Flow<Result<List<TantricContent>>>
    
    /**
     * Refresh content from remote sources
     */
    fun refreshTantricContent(): Flow<Result<Unit>>
    
    /**
     * Get user's favorite tantric content IDs
     */
    fun getFavorites(): Flow<List<String>>
    
    /**
     * Add content to favorites
     */
    suspend fun addToFavorites(contentId: String): Result<Unit>
    
    /**
     * Remove content from favorites
     */
    suspend fun removeFromFavorites(contentId: String): Result<Unit>
    
    /**
     * Get compatibility analysis between two users
     */
    fun getCompatibilityAnalysis(
        user1Profile: UserSpiritualProfile,
        user2Profile: UserSpiritualProfile
    ): Flow<Result<CompatibilityAnalysis>>
    
    /**
     * Generate tantric practice recommendations based on user profile
     */
    fun getTantricPracticeRecommendations(
        profile: UserSpiritualProfile,
        intensity: Int = 5
    ): Flow<Result<List<TantricContent>>>
    
    /**
     * Get Kama Sutra positions based on preferences and experience level
     */
    fun getKamaSutraPositions(
        experienceLevel: ExperienceLevel,
        preferences: List<String> = emptyList()
    ): Flow<Result<List<TantricContent>>>
    
    /**
     * Get Robert Greene relationship archetypes and strategies
     */
    fun getRobertGreeneContent(
        personalityType: String? = null,
        relationshipGoal: String? = null
    ): Flow<Result<List<TantricContent>>>
    
    /**
     * Cache content locally for offline access
     */
    suspend fun cacheContent(content: List<TantricContent>): Result<Unit>
    
    /**
     * Clear cached content
     */
    suspend fun clearCache(): Result<Unit>
}

/**
 * User experience levels for content filtering
 */
enum class ExperienceLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED,
    EXPERT
}

/**
 * Compatibility analysis result
 */
data class CompatibilityAnalysis(
    val overallScore: Float,
    val spiritualCompatibility: Float,
    val emotionalCompatibility: Float,
    val physicalCompatibility: Float,
    val intellectualCompatibility: Float,
    val strengths: List<String>,
    val challenges: List<String>,
    val recommendations: List<String>,
    val tantricPractices: List<TantricContent>,
    val generatedAt: Long = System.currentTimeMillis()
)

/**
 * User spiritual profile for personalization
 */
data class UserSpiritualProfile(
    val userId: String,
    val numerologyProfile: NumerologyProfile?,
    val astrologicalProfile: AstrologicalProfile?,
    val chakraProfile: ChakraProfile?,
    val tantricPreferences: TantricPreferences?,
    val experienceLevel: ExperienceLevel = ExperienceLevel.BEGINNER,
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * Tantric preferences for personalization
 */
data class TantricPreferences(
    val preferredPractices: List<String> = emptyList(),
    val intensityPreference: Int = 5, // 1-10 scale
    val focusAreas: List<String> = emptyList(), // e.g., "intimacy", "spirituality", "energy"
    val partnerPreferences: PartnerPreferences? = null,
    val meditationExperience: ExperienceLevel = ExperienceLevel.BEGINNER,
    val breathworkExperience: ExperienceLevel = ExperienceLevel.BEGINNER
)

/**
 * Partner preferences for compatibility analysis
 */
data class PartnerPreferences(
    val preferredAgeRange: IntRange? = null,
    val preferredSigns: List<String> = emptyList(),
    val dealBreakers: List<String> = emptyList(),
    val musthaves: List<String> = emptyList(),
    val communicationStyle: String? = null,
    val intimacyStyle: String? = null
)
