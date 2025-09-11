package com.spiritatlas.domain.performance

import com.spiritatlas.domain.model.UserProfile
import com.spiritatlas.domain.model.Gender
import com.spiritatlas.domain.model.ProfileCompletion
import com.spiritatlas.domain.service.CompatibilityAnalysisEngine
import com.spiritatlas.domain.service.optimized.OptimizedCompatibilityAnalysisEngine
import com.spiritatlas.domain.util.PerformanceMonitor
import com.spiritatlas.domain.tantric.TantricContent
import com.spiritatlas.domain.tantric.TantricContentType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDateTime
import kotlin.system.measureTimeMillis

/**
 * Performance comparison tests between original and optimized compatibility analysis engines
 */
class PerformanceComparisonTest {
    
    private val originalEngine = CompatibilityAnalysisEngine()
    private val optimizedEngine = OptimizedCompatibilityAnalysisEngine()
    private val performanceMonitor = PerformanceMonitor()
    
    private lateinit var testProfiles: List<UserProfile>
    private lateinit var tantricContent: List<TantricContent>
    
    @BeforeEach
    fun setup() {
        testProfiles = createTestProfiles()
        tantricContent = createTestTantricContent()
        performanceMonitor.reset()
    }
    
    @Test
    fun `compare single analysis performance`() {
        val profile1 = testProfiles[0]
        val profile2 = testProfiles[1]
        
        // Warm up both engines
        repeat(5) {
            originalEngine.analyzeCompatibility(profile1, profile2, tantricContent)
            optimizedEngine.analyzeCompatibility(profile1, profile2, tantricContent)
        }
        
        // Run performance comparison
        val originalTime = measureTimeMillis {
            repeat(10) {
                originalEngine.analyzeCompatibility(profile1, profile2, tantricContent)
            }
        }
        
        val optimizedTime = measureTimeMillis {
            repeat(10) {
                optimizedEngine.analyzeCompatibility(profile1, profile2, tantricContent)
            }
        }
        
        println("=== Single Analysis Performance ===")
        println("Original Engine: ${originalTime}ms total, ${originalTime / 10.0}ms avg")
        println("Optimized Engine: ${optimizedTime}ms total, ${optimizedTime / 10.0}ms avg")
        println("Performance Improvement: ${((originalTime - optimizedTime).toDouble() / originalTime * 100).toInt()}%")
        
        // Optimized should be faster (allowing 10% variance for test environment)
        assert(optimizedTime < originalTime * 1.1) { 
            "Optimized engine should be faster or at least comparable: ${optimizedTime}ms vs ${originalTime}ms" 
        }
    }
    
    @Test
    fun `compare batch analysis performance`() {
        // Test with many profile combinations
        val profiles = testProfiles.take(8) // 8 profiles = 28 unique pairs
        val pairs = profiles.flatMapIndexed { i, profile1 ->
            profiles.drop(i + 1).map { profile2 -> profile1 to profile2 }
        }
        
        println("Testing with ${pairs.size} profile pairs...")
        
        // Original engine batch test
        val originalTime = measureTimeMillis {
            pairs.forEach { (profile1, profile2) ->
                originalEngine.analyzeCompatibility(profile1, profile2, tantricContent)
            }
        }
        
        // Optimized engine batch test  
        val optimizedTime = measureTimeMillis {
            pairs.forEach { (profile1, profile2) ->
                optimizedEngine.analyzeCompatibility(profile1, profile2, tantricContent)
            }
        }
        
        println("=== Batch Analysis Performance ===")
        println("Original Engine: ${originalTime}ms total, ${originalTime.toDouble() / pairs.size}ms per analysis")
        println("Optimized Engine: ${optimizedTime}ms total, ${optimizedTime.toDouble() / pairs.size}ms per analysis")
        println("Performance Improvement: ${((originalTime - optimizedTime).toDouble() / originalTime * 100).toInt()}%")
        
        // Show cache statistics for optimized engine
        val cacheStats = optimizedEngine.getCacheStatistics()
        println("Cache Statistics: $cacheStats")
    }
    
    @Test
    fun `test memory usage comparison`() {
        val profile1 = testProfiles[0]
        val profile2 = testProfiles[1]
        
        // Force GC before test
        System.gc()
        Thread.sleep(100)
        
        val beforeMemory = performanceMonitor.getCurrentMemoryUsageMB()
        
        // Run original engine
        repeat(50) {
            originalEngine.analyzeCompatibility(profile1, profile2, tantricContent)
        }
        
        val afterOriginal = performanceMonitor.getCurrentMemoryUsageMB()
        
        // Force GC and reset
        System.gc()
        Thread.sleep(100)
        val afterGC = performanceMonitor.getCurrentMemoryUsageMB()
        
        // Run optimized engine
        repeat(50) {
            optimizedEngine.analyzeCompatibility(profile1, profile2, tantricContent)
        }
        
        val afterOptimized = performanceMonitor.getCurrentMemoryUsageMB()
        
        val originalMemoryIncrease = afterOriginal - beforeMemory
        val optimizedMemoryIncrease = afterOptimized - afterGC
        
        println("=== Memory Usage Comparison ===")
        println("Before Test: ${beforeMemory.toInt()}MB")
        println("After Original Engine: ${afterOriginal.toInt()}MB (increase: ${originalMemoryIncrease.toInt()}MB)")
        println("After GC: ${afterGC.toInt()}MB")
        println("After Optimized Engine: ${afterOptimized.toInt()}MB (increase: ${optimizedMemoryIncrease.toInt()}MB)")
        
        if (optimizedMemoryIncrease < originalMemoryIncrease) {
            println("Memory Improvement: ${((originalMemoryIncrease - optimizedMemoryIncrease) / originalMemoryIncrease * 100).toInt()}%")
        }
    }
    
    @Test
    fun `test cache effectiveness`() {
        // Use same profiles multiple times to test caching
        val profile1 = testProfiles[0]
        val profile2 = testProfiles[1]
        val profile3 = testProfiles[2]
        
        // Pairs that will benefit from caching (repeated profiles)
        val profilePairs = listOf(
            profile1 to profile2,
            profile2 to profile3,
            profile1 to profile3,
            profile1 to profile2, // Repeat - should hit cache
            profile2 to profile3, // Repeat - should hit cache
            profile1 to profile3  // Repeat - should hit cache
        )
        
        val timeWithCaching = measureTimeMillis {
            profilePairs.forEach { (p1, p2) ->
                optimizedEngine.analyzeCompatibility(p1, p2, tantricContent)
            }
        }
        
        // Clear cache and run again
        optimizedEngine.clearCaches()
        
        val timeWithoutCaching = measureTimeMillis {
            profilePairs.forEach { (p1, p2) ->
                optimizedEngine.analyzeCompatibility(p1, p2, tantricContent)
            }
        }
        
        println("=== Cache Effectiveness Test ===")
        println("With Caching: ${timeWithCaching}ms")
        println("Without Caching: ${timeWithoutCaching}ms")
        println("Cache Benefit: ${((timeWithoutCaching - timeWithCaching).toDouble() / timeWithoutCaching * 100).toInt()}%")
        
        // Verify caching provides benefit
        assert(timeWithCaching <= timeWithoutCaching) { "Caching should improve or maintain performance" }
    }
    
    @Test
    fun `benchmark specific operations`() {
        val results = performanceMonitor.runBenchmark()
        
        println("=== Operation Benchmarks ===")
        results.operationTimes.forEach { (operation, timeMs) ->
            println("$operation: ${timeMs}ms")
        }
        println("Total Benchmark Time: ${results.totalBenchmarkTime}ms")
        
        // All operations should complete reasonably fast
        results.operationTimes.values.forEach { timeMs ->
            assert(timeMs < 1000.0) { "Individual operations should be fast: ${timeMs}ms" }
        }
    }
    
    @Test
    fun `generate performance report`() {
        // Run some analyses with monitoring
        val profile1 = testProfiles[0]
        val profile2 = testProfiles[1]
        
        repeat(20) {
            performanceMonitor.measureOperation("analyzeCompatibility") {
                optimizedEngine.analyzeCompatibility(profile1, profile2, tantricContent)
            }
            
            // Simulate some cache hits/misses
            if (it % 3 == 0) {
                performanceMonitor.recordCacheHit()
            } else {
                performanceMonitor.recordCacheMiss()
            }
        }
        
        val report = performanceMonitor.generatePerformanceReport()
        
        println("=== Performance Report ===")
        println("Summary: ${report.summary}")
        println("Avg Analysis Time: ${report.avgAnalysisTime}")
        println("Peak Analysis Time: ${report.peakAnalysisTime}")
        println("Memory Usage: ${report.memoryUsage}")
        println("Cache Effectiveness: ${report.cacheEffectiveness}")
        
        println("\\nRecommendations:")
        report.recommendations.forEach { println("- $it") }
        
        println("\\nOperation Breakdown:")
        report.operationBreakdown.forEach { (operation, stats) ->
            println("$operation: ${stats.count} operations, avg ${stats.avgTimeMs}ms")
        }
    }
    
    /**
     * Create diverse test profiles for performance testing
     */
    private fun createTestProfiles(): List<UserProfile> {
        return listOf(
            createProfile("Alice", Gender.FEMALE, "1990-06-15T10:30:00"),
            createProfile("Bob", Gender.MALE, "1985-12-22T14:45:00"),
            createProfile("Charlie", Gender.NON_BINARY, "1992-03-08T08:15:00"),
            createProfile("Diana", Gender.FEMALE, "1988-09-30T16:20:00"),
            createProfile("Erik", Gender.MALE, "1995-01-12T12:00:00"),
            createProfile("Fiona", Gender.FEMALE, "1987-07-04T20:30:00"),
            createProfile("George", Gender.MALE, "1993-11-18T06:45:00"),
            createProfile("Hannah", Gender.FEMALE, "1991-05-25T22:10:00"),
            createProfile("", null, null), // Edge case: empty profile
            createProfile("SingleChar", Gender.MALE, "2000-01-01T00:00:00") // Edge case: minimal data
        )
    }
    
    private fun createProfile(name: String, gender: Gender?, birthDateTime: String?): UserProfile {
        val dateTime = birthDateTime?.let { LocalDateTime.parse(it) }
        return UserProfile(
            id = "test_${name.lowercase()}",
            name = name,
            gender = gender,
            birthDateTime = dateTime,
            profileCompletion = ProfileCompletion(
                completionPercentage = when {
                    name.isBlank() -> 20.0
                    gender == null || dateTime == null -> 60.0
                    else -> 85.0
                }
            )
        )
    }
    
    private fun createTestTantricContent(): List<TantricContent> {
        return listOf(
            TantricContent(
                id = "kama_1",
                contentType = TantricContentType.KAMA_SUTRA,
                title = "Test Kama Sutra Content"
            ),
            TantricContent(
                id = "tantric_1", 
                contentType = TantricContentType.TANTRIC_PRACTICES,
                title = "Test Tantric Practice"
            ),
            TantricContent(
                id = "greene_1",
                contentType = TantricContentType.ROBERT_GREENE,
                title = "Test Robert Greene Content"
            ),
            TantricContent(
                id = "compat_1",
                contentType = TantricContentType.COMPATIBILITY,
                title = "Test Compatibility Content"
            )
        )
    }
}
