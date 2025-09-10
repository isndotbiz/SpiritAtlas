package com.spiritatlas.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.spiritatlas.data.database.converters.SpiritualTypeConverters
import com.spiritatlas.domain.model.*

/**
 * Room entity for storing UserProfile data with encryption support
 * Supports multiple profiles for relationship matching and comparison
 * Privacy-first: All sensitive data is encrypted at rest ✨
 */
@Entity(tableName = "user_profiles")
@TypeConverters(SpiritualTypeConverters::class)
data class UserProfileEntity(
    @PrimaryKey
    val id: String,
    
    // === PROFILE METADATA ===
    val profileName: String, // Display name for this profile
    val createdAt: String, // ISO string for LocalDateTime
    val lastModified: String, // ISO string for LocalDateTime
    
    // === CORE IDENTITY ===
    val name: String? = null,
    val displayName: String? = null,
    val birthDateTime: String? = null, // Stored as ISO string for encryption
    val birthPlace: String? = null, // JSON serialized BirthPlace
    
    // === ADDITIONAL NAMES ===
    val middleName: String? = null,
    val nickname: String? = null,
    val spiritualName: String? = null,
    
    // === FAMILY & ANCESTRY ===
    val motherName: String? = null,
    val fatherName: String? = null,
    val ancestry: String? = null,
    
    // === PHYSICAL & ENERGETIC ===
    val gender: String? = null, // Enum as string
    val bloodType: String? = null, // Enum as string
    val dominantHand: String? = null, // Enum as string
    val eyeColor: String? = null,
    
    // === KEY TIMING ===
    val firstBreath: String? = null, // ISO string
    
    // === ENVIRONMENTAL ===
    val weatherConditions: String? = null,
    val moonPhase: String? = null,
    val hospitalName: String? = null,
    
    // === LIFE PATTERNS ===
    val firstWord: String? = null,
    val firstSteps: String? = null, // ISO string
    
    // === PREFERENCES ===
    val preferences: String? = null, // JSON serialized UserPreferences
    
    // === PROFILE COMPLETION ===
    val profileCompletion: String? = null, // JSON serialized ProfileCompletion
    
    // === AI ENRICHMENT RESULT ===
    val enrichmentResult: String? = null, // Saved AI-generated spiritual report
    val enrichmentGeneratedAt: String? = null, // ISO string for LocalDateTime
    
    // === METADATA ===
    val updatedAt: Long = System.currentTimeMillis(),
    val syncStatus: String = "LOCAL", // LOCAL, SYNCING, SYNCED
    val encryptionVersion: Int = 1 // For future encryption upgrades
)
