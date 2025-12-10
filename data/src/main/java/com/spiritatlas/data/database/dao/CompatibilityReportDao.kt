package com.spiritatlas.data.database.dao

import androidx.room.*
import com.spiritatlas.data.database.entities.CompatibilityReportEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for compatibility report caching
 * Optimized queries with proper indexing
 */
@Dao
interface CompatibilityReportDao {

    /**
     * Get all cached reports for a specific profile
     */
    @Query("SELECT * FROM compatibility_reports WHERE profileAId = :profileId OR profileBId = :profileId ORDER BY lastAccessedAt DESC")
    fun getReportsForProfile(profileId: String): Flow<List<CompatibilityReportEntity>>

    /**
     * Get a specific compatibility report between two profiles
     */
    @Query("""
        SELECT * FROM compatibility_reports
        WHERE (profileAId = :profileAId AND profileBId = :profileBId)
           OR (profileAId = :profileBId AND profileBId = :profileAId)
        ORDER BY generatedAt DESC
        LIMIT 1
    """)
    suspend fun getReport(profileAId: String, profileBId: String): CompatibilityReportEntity?

    /**
     * Insert or update a compatibility report
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: CompatibilityReportEntity)

    /**
     * Update access tracking for cache hit
     */
    @Query("""
        UPDATE compatibility_reports
        SET accessCount = accessCount + 1,
            lastAccessedAt = :timestamp
        WHERE id = :reportId
    """)
    suspend fun recordAccess(reportId: String, timestamp: Long = System.currentTimeMillis())

    /**
     * Delete expired reports based on expiration time
     */
    @Query("DELETE FROM compatibility_reports WHERE expiresAt < :currentTime")
    suspend fun deleteExpiredReports(currentTime: Long = System.currentTimeMillis())

    /**
     * Delete least recently used reports when over limit
     * Keeps only the most recently accessed reports
     */
    @Query("""
        DELETE FROM compatibility_reports
        WHERE id NOT IN (
            SELECT id FROM compatibility_reports
            ORDER BY lastAccessedAt DESC
            LIMIT :maxReports
        )
    """)
    suspend fun trimToLimit(maxReports: Int = 50)

    /**
     * Get cache statistics
     */
    @Query("SELECT COUNT(*) FROM compatibility_reports")
    suspend fun getCacheSize(): Int

    /**
     * Delete all cached reports for cleanup
     */
    @Query("DELETE FROM compatibility_reports")
    suspend fun clearCache()

    /**
     * Delete specific report by ID
     */
    @Query("DELETE FROM compatibility_reports WHERE id = :reportId")
    suspend fun deleteReport(reportId: String)

    /**
     * Get reports sorted by overall score for discovery
     */
    @Query("""
        SELECT * FROM compatibility_reports
        WHERE profileAId = :profileId OR profileBId = :profileId
        ORDER BY overallScore DESC
        LIMIT :limit
    """)
    suspend fun getTopCompatibilityReports(profileId: String, limit: Int = 10): List<CompatibilityReportEntity>
}
