package com.spiritatlas.data.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.spiritatlas.data.database.converters.SpiritualTypeConverters

/**
 * Room entity for caching compatibility reports
 * Indexes optimized for common query patterns
 */
@Entity(
    tableName = "compatibility_reports",
    indices = [
        Index(value = ["profileAId", "profileBId"]),
        Index(value = ["profileAId"]),
        Index(value = ["profileBId"]),
        Index(value = ["generatedAt"])
    ]
)
@TypeConverters(SpiritualTypeConverters::class)
data class CompatibilityReportEntity(
    @PrimaryKey
    val id: String,

    // Profile references
    val profileAId: String,
    val profileBId: String,

    // Report data (stored as JSON)
    val reportJson: String,

    // Metadata for caching strategy
    val generatedAt: Long = System.currentTimeMillis(),
    val expiresAt: Long = System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000), // 7 days default
    val accessCount: Int = 0,
    val lastAccessedAt: Long = System.currentTimeMillis(),

    // Quick access fields (denormalized for performance)
    val overallScore: Double,
    val compatibilityLevel: String,
    val hasAiInsights: Boolean = false
)
