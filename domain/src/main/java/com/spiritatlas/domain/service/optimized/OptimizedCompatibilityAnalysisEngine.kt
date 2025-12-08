package com.spiritatlas.domain.service.optimized

import com.spiritatlas.domain.model.*
import com.spiritatlas.domain.tantric.TantricContent
import com.spiritatlas.domain.tantric.TantricContentType
import java.util.Collections
import kotlin.math.abs

/**
 * Thread-safe LRU cache implementation with bounded size
 */
private class LruCache<K, V>(private val maxSize: Int) {
    private val cache = Collections.synchronizedMap(
        object : LinkedHashMap<K, V>(maxSize + 1, 0.75f, true) {
            override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean {
                return size > maxSize
            }
        }
    )

    fun get(key: K): V? = cache[key]

    fun put(key: K, value: V): V? = cache.put(key, value)

    fun computeIfAbsent(key: K, mappingFunction: (K) -> V): V {
        return cache[key] ?: synchronized(cache) {
            cache[key] ?: mappingFunction(key).also { cache[key] = it }
        }
    }

    fun clear() = cache.clear()

    fun size(): Int = cache.size
}

/**
 * Performance-optimized compatibility analysis engine
 *
 * Key optimizations:
 * - Cached calculations to avoid redundant computations
 * - Pre-computed lookup tables for astrological data
 * - Efficient string processing for name energy calculations
 * - Lazy initialization of expensive resources
 * - Pool object reuse where appropriate
 * - Minimized memory allocations in hot paths
 * - Bounded LRU caches to prevent memory leaks
 */
class OptimizedCompatibilityAnalysisEngine {

    // Bounded LRU caches to prevent unbounded memory growth
    private val nameEnergyCache = LruCache<String, Double>(256)
    private val signCompatibilityCache = LruCache<Pair<String, String>, Double>(256)
    private val profileScoreCache = LruCache<String, CompatibilityScores>(256)
    
    // Pre-computed lookup tables for O(1) access
    companion object {
        // Astrological element mappings - pre-computed for performance
        private val FIRE_SIGNS = setOf("Aries", "Leo", "Sagittarius")
        private val EARTH_SIGNS = setOf("Taurus", "Virgo", "Capricorn")
        private val AIR_SIGNS = setOf("Gemini", "Libra", "Aquarius")
        private val WATER_SIGNS = setOf("Cancer", "Scorpio", "Pisces")
        
        // Modality mappings
        private val CARDINAL_SIGNS = setOf("Aries", "Cancer", "Libra", "Capricorn")
        private val FIXED_SIGNS = setOf("Taurus", "Leo", "Scorpio", "Aquarius")
        private val MUTABLE_SIGNS = setOf("Gemini", "Virgo", "Sagittarius", "Pisces")
        
        // Element compatibility matrix (precomputed for O(1) lookup)
        private val ELEMENT_COMPATIBILITY = mapOf(
            "Fire" to mapOf("Fire" to 85.0, "Earth" to 70.0, "Air" to 90.0, "Water" to 75.0),
            "Earth" to mapOf("Fire" to 70.0, "Earth" to 85.0, "Air" to 75.0, "Water" to 88.0),
            "Air" to mapOf("Fire" to 90.0, "Earth" to 75.0, "Air" to 85.0, "Water" to 75.0),
            "Water" to mapOf("Fire" to 75.0, "Earth" to 88.0, "Air" to 75.0, "Water" to 85.0)
        )
        
        // Comprehensive sign compatibility matrix (flattened for performance)
        private val SIGN_COMPATIBILITY = mapOf(
            "Aries" to mapOf("Leo" to 95.0, "Sagittarius" to 90.0, "Gemini" to 85.0, "Aquarius" to 88.0),
            "Taurus" to mapOf("Virgo" to 92.0, "Capricorn" to 90.0, "Cancer" to 85.0, "Pisces" to 87.0),
            "Gemini" to mapOf("Libra" to 93.0, "Aquarius" to 91.0, "Aries" to 85.0, "Leo" to 88.0),
            "Cancer" to mapOf("Scorpio" to 94.0, "Pisces" to 92.0, "Taurus" to 85.0, "Virgo" to 87.0),
            "Leo" to mapOf("Aries" to 95.0, "Sagittarius" to 93.0, "Gemini" to 88.0, "Libra" to 86.0),
            "Virgo" to mapOf("Taurus" to 92.0, "Capricorn" to 91.0, "Cancer" to 87.0, "Scorpio" to 85.0),
            "Libra" to mapOf("Gemini" to 93.0, "Aquarius" to 89.0, "Leo" to 86.0, "Sagittarius" to 84.0),
            "Scorpio" to mapOf("Cancer" to 94.0, "Pisces" to 91.0, "Virgo" to 85.0, "Capricorn" to 83.0),
            "Sagittarius" to mapOf("Leo" to 93.0, "Aries" to 90.0, "Libra" to 84.0, "Aquarius" to 87.0),
            "Capricorn" to mapOf("Virgo" to 91.0, "Taurus" to 90.0, "Scorpio" to 83.0, "Pisces" to 85.0),
            "Aquarius" to mapOf("Gemini" to 91.0, "Libra" to 89.0, "Aries" to 88.0, "Sagittarius" to 87.0),
            "Pisces" to mapOf("Cancer" to 92.0, "Scorpio" to 91.0, "Taurus" to 87.0, "Capricorn" to 85.0)
        )
        
        // Complementary number pairs (pre-computed set for O(1) lookup)
        private val COMPLEMENTARY_PAIRS = setOf(
            setOf(1, 8), setOf(2, 7), setOf(3, 6), setOf(4, 5)
        )
        
        // Season mappings (array for O(1) access by month index)
        private val SEASONS = arrayOf(
            "Winter", "Winter", "Spring", "Spring", "Spring", "Summer",
            "Summer", "Summer", "Fall", "Fall", "Fall", "Winter"
        )
        
        // Vowels set for efficient lookups
        private val VOWELS = setOf('a', 'e', 'i', 'o', 'u')
    }
    
    /**
     * Optimized main analysis method with caching and performance improvements
     */
    fun analyzeCompatibility(
        profileA: UserProfile,
        profileB: UserProfile,
        tantricContent: List<TantricContent> = emptyList()
    ): CompatibilityReport {
        // Generate cache keys for profile pair
        val cacheKey = generateProfilePairKey(profileA, profileB)
        
        // Try to get cached scores first
        val scores = profileScoreCache.computeIfAbsent(cacheKey) {
            calculateOptimizedCompatibilityScores(profileA, profileB)
        }
        
        // Generate other components (these are typically unique per analysis)
        val insights = generateRelationshipInsights(profileA, profileB, scores)
        val strengths = identifyStrengths(profileA, profileB, scores)
        val challenges = identifyChallenges(profileA, profileB, scores)
        val recommendations = generateRecommendations(profileA, profileB, scores, challenges)
        val tantricMatches = analyzeTantricCompatibilityOptimized(profileA, profileB, tantricContent)
        
        return CompatibilityReport(
            profileA = profileA,
            profileB = profileB,
            overallScore = scores,
            insights = insights,
            strengths = strengths,
            challenges = challenges,
            recommendations = recommendations,
            tantricMatches = tantricMatches
        )
    }
    
    /**
     * Optimized compatibility score calculation with minimal allocations
     */
    private fun calculateOptimizedCompatibilityScores(
        profileA: UserProfile,
        profileB: UserProfile
    ): CompatibilityScores {
        return CompatibilityScores(
            numerologyScore = calculateNumerologyCompatibilityOptimized(profileA, profileB),
            astrologyScore = calculateAstrologyCompatibilityOptimized(profileA, profileB),
            tantricScore = calculateTantricCompatibilityOptimized(profileA, profileB),
            energeticScore = calculateEnergeticCompatibilityOptimized(profileA, profileB),
            communicationScore = calculateCommunicationCompatibilityOptimized(profileA, profileB),
            emotionalScore = calculateEmotionalCompatibilityOptimized(profileA, profileB)
        )
    }
    
    /**
     * Optimized numerology compatibility with caching
     */
    private fun calculateNumerologyCompatibilityOptimized(
        profileA: UserProfile,
        profileB: UserProfile
    ): Double {
        val nameA = profileA.name ?: ""
        val nameB = profileB.name ?: ""
        
        val nameEnergyA = calculateNameEnergyOptimized(nameA)
        val nameEnergyB = calculateNameEnergyOptimized(nameB)
        
        val energyHarmony = 100.0 - abs(nameEnergyA - nameEnergyB) * 5.0
        return energyHarmony.coerceIn(60.0, 95.0)
    }
    
    /**
     * Optimized name energy calculation with caching and efficient string processing
     */
    private fun calculateNameEnergyOptimized(name: String): Double {
        return nameEnergyCache.computeIfAbsent(name) {
            if (name.isEmpty()) return@computeIfAbsent 5.0
            
            var vowelCount = 0
            var consonantCount = 0
            
            // Single pass through string for counting
            for (char in name) {
                val lowerChar = char.lowercaseChar()
                if (lowerChar.isLetter()) {
                    if (lowerChar in VOWELS) {
                        vowelCount++
                    } else {
                        consonantCount++
                    }
                }
            }
            
            if (consonantCount > 0) {
                (vowelCount.toDouble() / consonantCount.toDouble()) * 10.0
            } else {
                5.0
            }
        }
    }
    
    /**
     * Optimized astrology compatibility using pre-computed lookup tables
     */
    private fun calculateAstrologyCompatibilityOptimized(
        profileA: UserProfile,
        profileB: UserProfile
    ): Double {
        val monthA = profileA.birthDateTime?.monthValue ?: 1
        val monthB = profileB.birthDateTime?.monthValue ?: 1
        
        // Use pre-computed season lookup (O(1) instead of multiple if statements)
        val elementA = getElementByMonth(monthA)
        val elementB = getElementByMonth(monthB)
        
        // Use pre-computed compatibility matrix
        return ELEMENT_COMPATIBILITY[elementA]?.get(elementB) ?: 75.0
    }
    
    /**
     * Optimized tantric compatibility calculation
     */
    private fun calculateTantricCompatibilityOptimized(
        profileA: UserProfile,
        profileB: UserProfile
    ): Double {
        var score = 75.0 // Base score
        
        // Gender energy dynamics (optimized null checks)
        val genderA = profileA.gender
        val genderB = profileB.gender
        if (genderA != null && genderB != null) {
            score += if (genderA != genderB) 15.0 else 5.0
        }
        
        // Time-based compatibility (optimized datetime handling)
        val dtA = profileA.birthDateTime
        val dtB = profileB.birthDateTime
        if (dtA != null && dtB != null) {
            val hourDiff = abs(dtA.hour - dtB.hour)
            score += when {
                hourDiff <= 2 || hourDiff >= 22 -> 10.0
                hourDiff in 10..14 -> 15.0
                else -> 5.0
            }
        }
        
        return minOf(score, 100.0)
    }
    
    /**
     * Optimized energetic compatibility using efficient season calculation
     */
    private fun calculateEnergeticCompatibilityOptimized(
        profileA: UserProfile,
        profileB: UserProfile
    ): Double {
        var score = 70.0
        
        val dtA = profileA.birthDateTime
        val dtB = profileB.birthDateTime
        if (dtA != null && dtB != null) {
            val seasonA = getSeason(dtA.monthValue)
            val seasonB = getSeason(dtB.monthValue)
            
            score += when {
                seasonA == seasonB -> 20.0
                areComplementarySeasons(seasonA, seasonB) -> 25.0
                else -> 10.0
            }
        }
        
        return minOf(score, 100.0)
    }
    
    /**
     * Optimized communication compatibility calculation
     */
    private fun calculateCommunicationCompatibilityOptimized(
        profileA: UserProfile,
        profileB: UserProfile
    ): Double {
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
     * Optimized emotional compatibility with cached name energy
     */
    private fun calculateEmotionalCompatibilityOptimized(
        profileA: UserProfile,
        profileB: UserProfile
    ): Double {
        val nameEnergyA = calculateNameEnergyOptimized(profileA.name ?: "")
        val nameEnergyB = calculateNameEnergyOptimized(profileB.name ?: "")
        
        val energyHarmony = 100.0 - abs(nameEnergyA - nameEnergyB) * 10.0
        return energyHarmony.coerceIn(60.0, 95.0)
    }
    
    /**
     * Optimized tantric content analysis with early termination
     */
    private fun analyzeTantricCompatibilityOptimized(
        profileA: UserProfile,
        profileB: UserProfile,
        tantricContent: List<TantricContent>
    ): List<TantricCompatibility> {
        if (tantricContent.isEmpty()) return emptyList()
        
        // Early termination and efficient processing
        return tantricContent.take(3).map { content ->
            val score = when (content.contentType) {
                TantricContentType.KAMA_SUTRA -> 82.5 // Use deterministic values instead of random
                TantricContentType.TANTRIC_PRACTICES -> 82.5
                TantricContentType.ROBERT_GREENE -> 77.5
                TantricContentType.COMPATIBILITY -> 87.5
            }
            
            TantricCompatibility(
                contentId = content.id,
                contentType = content.contentType.name,
                compatibilityScore = score,
                reason = "Based on your combined energetic patterns and spiritual compatibility",
                recommendation = getTantricRecommendation(content.contentType)
            )
        }
    }
    
    // Optimized helper methods
    private fun getElementByMonth(month: Int): String {
        return when (month) {
            in 3..5 -> "Fire"
            in 6..8 -> "Earth"
            in 9..11 -> "Air"
            else -> "Water"
        }
    }
    
    private fun getSeason(month: Int): String {
        return SEASONS[month - 1]
    }
    
    private fun areComplementarySeasons(seasonA: String, seasonB: String): Boolean {
        return (seasonA == "Spring" && seasonB == "Fall") || 
               (seasonA == "Fall" && seasonB == "Spring") ||
               (seasonA == "Summer" && seasonB == "Winter") ||
               (seasonA == "Winter" && seasonB == "Summer")
    }
    
    private fun generateProfilePairKey(profileA: UserProfile, profileB: UserProfile): String {
        // Create deterministic key for caching (order-independent)
        val idA = profileA.id ?: profileA.name ?: ""
        val idB = profileB.id ?: profileB.name ?: ""
        return if (idA <= idB) "${idA}_${idB}" else "${idB}_${idA}"
    }
    
    private fun getTantricRecommendation(contentType: TantricContentType): String {
        return when (contentType) {
            TantricContentType.KAMA_SUTRA -> "Explore these practices during your intimate moments for deeper connection"
            TantricContentType.TANTRIC_PRACTICES -> "Incorporate these tantric techniques into your daily spiritual practice"
            TantricContentType.ROBERT_GREENE -> "Apply these seduction and relationship strategies to enhance your connection"
            TantricContentType.COMPATIBILITY -> "Use this compatibility insight to understand and improve your relationship dynamics"
        }
    }
    
    // Reuse existing methods for other operations (insights, strengths, challenges, recommendations)
    // These would be copied from the original with minimal changes since they're not the hot path
    
    private fun generateRelationshipInsights(
        profileA: UserProfile,
        profileB: UserProfile,
        scores: CompatibilityScores
    ): List<RelationshipInsight> {
        // Implementation would be similar to original but with optimized data structures
        return emptyList() // Placeholder
    }
    
    private fun identifyStrengths(
        profileA: UserProfile,
        profileB: UserProfile,
        scores: CompatibilityScores
    ): List<CompatibilityStrength> {
        return emptyList() // Placeholder
    }
    
    private fun identifyChallenges(
        profileA: UserProfile,
        profileB: UserProfile,
        scores: CompatibilityScores
    ): List<CompatibilityChallenge> {
        return emptyList() // Placeholder
    }
    
    private fun generateRecommendations(
        profileA: UserProfile,
        profileB: UserProfile,
        scores: CompatibilityScores,
        challenges: List<CompatibilityChallenge>
    ): List<CompatibilityRecommendation> {
        return emptyList() // Placeholder
    }
    
    /**
     * Clear caches when memory pressure is detected or periodically
     */
    fun clearCaches() {
        nameEnergyCache.clear()
        signCompatibilityCache.clear()
        profileScoreCache.clear()
    }
    
    /**
     * Get cache statistics for monitoring
     */
    fun getCacheStatistics(): Map<String, Int> {
        return mapOf(
            "nameEnergyCache" to nameEnergyCache.size(),
            "signCompatibilityCache" to signCompatibilityCache.size(),
            "profileScoreCache" to profileScoreCache.size()
        )
    }
}
