package com.spiritatlas.data.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Room entity for caching AI responses to reduce API calls
 * Implements TTL-based expiration for freshness
 */
@Entity(
    tableName = "ai_response_cache",
    indices = [
        Index(value = ["requestHash"], unique = true),
        Index(value = ["createdAt"]),
        Index(value = ["expiresAt"])
    ]
)
data class AiResponseCacheEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    // Request identifier (hash of prompt + model + params)
    val requestHash: String,

    // Request details
    val prompt: String,
    val model: String,
    val provider: String,

    // Response
    val response: String,

    // Cache metadata
    val createdAt: Long = System.currentTimeMillis(),
    val expiresAt: Long = System.currentTimeMillis() + (24 * 60 * 60 * 1000), // 24hr TTL default
    val hitCount: Int = 0,
    val lastHitAt: Long = System.currentTimeMillis(),

    // Token usage tracking
    val tokensUsed: Int = 0
)
