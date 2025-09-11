package com.spiritatlas.domain.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

/**
 * Performance monitoring utility for tracking algorithm performance
 * and memory usage in compatibility analysis operations
 */
class PerformanceMonitor {
    
    private val _performanceMetrics = MutableStateFlow(PerformanceMetrics())
    val performanceMetrics: StateFlow<PerformanceMetrics> = _performanceMetrics.asStateFlow()
    
    data class PerformanceMetrics(
        val totalAnalysesPerformed: Long = 0,
        val averageAnalysisTimeMs: Double = 0.0,
        val peakAnalysisTimeMs: Long = 0,
        val totalMemoryUsedMB: Double = 0.0,
        val averageMemoryPerAnalysisMB: Double = 0.0,
        val cacheHitRate: Double = 0.0,
        val operationCounts: Map<String, Long> = emptyMap(),
        val performanceRecommendations: List<String> = emptyList()
    )
    
    data class OperationTimer(
        val operationName: String,
        val startTime: Long,
        val startMemory: Long
    )
    
    private val operationTimers = mutableMapOf<String, OperationTimer>()
    private val operationStats = mutableMapOf<String, MutableList<Long>>()
    private var totalAnalyses = 0L
    private val analysisTimeHistory = mutableListOf<Long>()
    private var cacheHits = 0L
    private var cacheMisses = 0L
    
    /**
     * Start timing an operation
     */
    fun startOperation(operationName: String) {
        val currentMemory = Runtime.getRuntime().let { 
            it.totalMemory() - it.freeMemory() 
        }
        operationTimers[operationName] = OperationTimer(
            operationName = operationName,
            startTime = System.nanoTime(),
            startMemory = currentMemory
        )
    }
    
    /**
     * End timing an operation and record statistics
     */
    fun endOperation(operationName: String) {
        val timer = operationTimers.remove(operationName) ?: return
        val endTime = System.nanoTime()
        val currentMemory = Runtime.getRuntime().let { 
            it.totalMemory() - it.freeMemory() 
        }
        
        val durationNanos = endTime - timer.startTime
        val memoryUsed = currentMemory - timer.startMemory
        
        // Record operation statistics
        operationStats.getOrPut(operationName) { mutableListOf() }.add(durationNanos)
        
        // Update analysis count for compatibility analyses
        if (operationName == "analyzeCompatibility") {
            totalAnalyses++
            analysisTimeHistory.add(durationNanos / 1_000_000) // Convert to milliseconds
            
            // Keep only last 100 analyses for rolling average
            if (analysisTimeHistory.size > 100) {
                analysisTimeHistory.removeAt(0)
            }
        }
        
        updateMetrics()
    }
    
    /**
     * Record cache hit/miss for monitoring cache effectiveness
     */
    fun recordCacheHit() {
        cacheHits++
        updateMetrics()
    }
    
    fun recordCacheMiss() {
        cacheMisses++
        updateMetrics()
    }
    
    /**
     * Measure and execute a block of code with performance tracking
     */
    inline fun <T> measureOperation(operationName: String, block: () -> T): T {
        startOperation(operationName)
        return try {
            block()
        } finally {
            endOperation(operationName)
        }
    }
    
    /**
     * Measure and execute a suspend block with performance tracking
     */
    suspend inline fun <T> measureSuspendOperation(operationName: String, crossinline block: suspend () -> T): T {
        startOperation(operationName)
        return try {
            block()
        } finally {
            endOperation(operationName)
        }
    }
    
    /**
     * Get current memory usage in MB
     */
    fun getCurrentMemoryUsageMB(): Double {
        val runtime = Runtime.getRuntime()
        val usedMemory = runtime.totalMemory() - runtime.freeMemory()
        return usedMemory / (1024.0 * 1024.0)
    }
    
    /**
     * Force garbage collection and measure impact
     */
    fun performGCAndMeasure(): MemoryStats {
        val beforeGC = getCurrentMemoryUsageMB()
        val gcTime = measureTimeMillis {
            System.gc()
            Thread.sleep(100) // Give GC time to complete
        }
        val afterGC = getCurrentMemoryUsageMB()
        
        return MemoryStats(
            memoryBeforeGC = beforeGC,
            memoryAfterGC = afterGC,
            memoryReclaimed = beforeGC - afterGC,
            gcTimeMs = gcTime
        )
    }
    
    data class MemoryStats(
        val memoryBeforeGC: Double,
        val memoryAfterGC: Double,
        val memoryReclaimed: Double,
        val gcTimeMs: Long
    )
    
    /**
     * Generate performance report
     */
    fun generatePerformanceReport(): PerformanceReport {
        val currentMetrics = _performanceMetrics.value
        
        return PerformanceReport(
            summary = "Performance analysis for ${currentMetrics.totalAnalysesPerformed} compatibility analyses",
            avgAnalysisTime = "${currentMetrics.averageAnalysisTimeMs}ms",
            peakAnalysisTime = "${currentMetrics.peakAnalysisTimeMs}ms",
            memoryUsage = "${currentMetrics.averageMemoryPerAnalysisMB}MB avg per analysis",
            cacheEffectiveness = "${(currentMetrics.cacheHitRate * 100).toInt()}% cache hit rate",
            recommendations = currentMetrics.performanceRecommendations,
            operationBreakdown = operationStats.mapValues { (_, times) ->
                if (times.isNotEmpty()) {
                    OperationStats(
                        count = times.size.toLong(),
                        avgTimeMs = times.average() / 1_000_000,
                        peakTimeMs = times.maxOrNull()?.div(1_000_000.0) ?: 0.0
                    )
                } else {
                    OperationStats(0, 0.0, 0.0)
                }
            }
        )
    }
    
    data class PerformanceReport(
        val summary: String,
        val avgAnalysisTime: String,
        val peakAnalysisTime: String,
        val memoryUsage: String,
        val cacheEffectiveness: String,
        val recommendations: List<String>,
        val operationBreakdown: Map<String, OperationStats>
    )
    
    data class OperationStats(
        val count: Long,
        val avgTimeMs: Double,
        val peakTimeMs: Double
    )
    
    /**
     * Update performance metrics based on current statistics
     */
    private fun updateMetrics() {
        val avgAnalysisTime = if (analysisTimeHistory.isNotEmpty()) {
            analysisTimeHistory.average()
        } else 0.0
        
        val peakAnalysisTime = if (analysisTimeHistory.isNotEmpty()) {
            analysisTimeHistory.maxOrNull() ?: 0L
        } else 0L
        
        val cacheHitRate = if (cacheHits + cacheMisses > 0) {
            cacheHits.toDouble() / (cacheHits + cacheMisses)
        } else 0.0
        
        val recommendations = generateRecommendations(avgAnalysisTime, cacheHitRate)
        
        val operationCounts = operationStats.mapValues { it.value.size.toLong() }
        
        _performanceMetrics.value = PerformanceMetrics(
            totalAnalysesPerformed = totalAnalyses,
            averageAnalysisTimeMs = avgAnalysisTime,
            peakAnalysisTimeMs = peakAnalysisTime,
            totalMemoryUsedMB = getCurrentMemoryUsageMB(),
            averageMemoryPerAnalysisMB = if (totalAnalyses > 0) getCurrentMemoryUsageMB() / totalAnalyses else 0.0,
            cacheHitRate = cacheHitRate,
            operationCounts = operationCounts,
            performanceRecommendations = recommendations
        )
    }
    
    /**
     * Generate performance recommendations based on metrics
     */
    private fun generateRecommendations(avgAnalysisTime: Double, cacheHitRate: Double): List<String> {
        val recommendations = mutableListOf<String>()
        
        // Analysis time recommendations
        when {
            avgAnalysisTime > 1000 -> recommendations.add("⚠️ Analysis time is high (${avgAnalysisTime.toInt()}ms). Consider optimizing algorithms or using caching.")
            avgAnalysisTime > 500 -> recommendations.add("💡 Analysis time could be improved (${avgAnalysisTime.toInt()}ms). Review algorithm efficiency.")
            avgAnalysisTime < 100 -> recommendations.add("✅ Excellent analysis performance (${avgAnalysisTime.toInt()}ms).")
        }
        
        // Cache hit rate recommendations
        when {
            cacheHitRate < 0.3 -> recommendations.add("⚠️ Low cache hit rate (${(cacheHitRate * 100).toInt()}%). Consider expanding cache strategy.")
            cacheHitRate < 0.6 -> recommendations.add("💡 Moderate cache efficiency (${(cacheHitRate * 100).toInt()}%). Cache tuning may help.")
            cacheHitRate > 0.8 -> recommendations.add("✅ Excellent cache performance (${(cacheHitRate * 100).toInt()}% hit rate).")
        }
        
        // Memory recommendations
        val currentMemory = getCurrentMemoryUsageMB()
        when {
            currentMemory > 100 -> recommendations.add("⚠️ High memory usage (${currentMemory.toInt()}MB). Consider clearing caches or optimizing data structures.")
            currentMemory > 50 -> recommendations.add("💡 Moderate memory usage (${currentMemory.toInt()}MB). Monitor for memory leaks.")
        }
        
        return recommendations
    }
    
    /**
     * Reset all performance statistics
     */
    fun reset() {
        operationTimers.clear()
        operationStats.clear()
        analysisTimeHistory.clear()
        totalAnalyses = 0L
        cacheHits = 0L
        cacheMisses = 0L
        updateMetrics()
    }
    
    /**
     * Get benchmark results for different profile types
     */
    fun runBenchmark(): BenchmarkResults {
        val results = mutableMapOf<String, Long>()
        
        // Simulate different operation benchmarks
        results["string_processing"] = measureNanoTime {
            repeat(1000) {
                val testString = "TestName$it"
                testString.count { it.lowercaseChar() in setOf('a', 'e', 'i', 'o', 'u') }
            }
        }
        
        results["map_lookups"] = measureNanoTime {
            val testMap = mapOf(
                "Fire" to mapOf("Fire" to 85.0, "Earth" to 70.0),
                "Earth" to mapOf("Fire" to 70.0, "Earth" to 85.0)
            )
            repeat(1000) {
                testMap["Fire"]?.get("Earth")
            }
        }
        
        results["collection_operations"] = measureNanoTime {
            repeat(1000) {
                val list = (1..100).toList()
                list.take(10).map { it * 2 }
            }
        }
        
        return BenchmarkResults(
            operationTimes = results.mapValues { it.value / 1_000_000.0 }, // Convert to milliseconds
            totalBenchmarkTime = results.values.sum() / 1_000_000.0
        )
    }
    
    data class BenchmarkResults(
        val operationTimes: Map<String, Double>,
        val totalBenchmarkTime: Double
    )
}
