package com.spiritatlas.data.database.mappers

import com.spiritatlas.data.database.entities.UserProfileEntity
import com.spiritatlas.data.database.converters.LocalDateTimeJsonAdapter
import com.spiritatlas.domain.model.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Mappers between domain UserProfile and database UserProfileEntity
 * Handles serialization/privacy with care ✨
 */
object UserProfileMappers {
    
    private val moshi = Moshi.Builder()
        .add(LocalDateTime::class.java, LocalDateTimeJsonAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()
    
    /**
     * Convert domain UserProfile to database entity
     */
    fun UserProfile.toEntity(): UserProfileEntity {
        return UserProfileEntity(
            id = this.id,
            
            // Profile metadata
            profileName = this.profileName,
            createdAt = this.createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            lastModified = this.lastModified.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            
            // Core identity
            name = this.name,
            displayName = this.displayName,
            birthDateTime = this.birthDateTime?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            birthPlace = this.birthPlace?.let { 
                moshi.adapter(BirthPlace::class.java).toJson(it) 
            },
            
            // Additional names
            middleName = this.middleName,
            nickname = this.nickname,
            spiritualName = this.spiritualName,
            
            // Family & ancestry
            motherName = this.motherName,
            fatherName = this.fatherName,
            ancestry = this.ancestry,
            
            // Physical & energetic
            gender = this.gender?.name,
            bloodType = this.bloodType?.name,
            dominantHand = this.dominantHand?.name,
            eyeColor = this.eyeColor,
            
            // Key timing
            firstBreath = this.firstBreath?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            
            // Environmental
            weatherConditions = this.weatherConditions,
            moonPhase = this.moonPhase,
            hospitalName = this.hospitalName,
            
            // Life patterns
            firstWord = this.firstWord,
            firstSteps = this.firstSteps?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            
            // Preferences & completion
            preferences = moshi.adapter(UserPreferences::class.java).toJson(this.preferences),
            profileCompletion = moshi.adapter(ProfileCompletion::class.java).toJson(this.profileCompletion),
            
            // AI enrichment result
            enrichmentResult = this.enrichmentResult,
            enrichmentGeneratedAt = this.enrichmentGeneratedAt?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            
            // Metadata
            updatedAt = System.currentTimeMillis()
        )
    }
    
    /**
     * Convert database entity to domain UserProfile
     */
    fun UserProfileEntity.toDomain(): UserProfile {
        return UserProfile(
            id = this.id,
            
            // Profile metadata
            profileName = this.profileName,
            createdAt = LocalDateTime.parse(this.createdAt, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            lastModified = LocalDateTime.parse(this.lastModified, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
            
            // Core identity
            name = this.name,
            displayName = this.displayName,
            birthDateTime = this.birthDateTime?.let { 
                LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            },
            birthPlace = this.birthPlace?.let { 
                moshi.adapter(BirthPlace::class.java).fromJson(it)
            },
            
            // Additional names
            middleName = this.middleName,
            nickname = this.nickname,
            spiritualName = this.spiritualName,
            
            // Family & ancestry
            motherName = this.motherName,
            fatherName = this.fatherName,
            ancestry = this.ancestry,
            
            // Physical & energetic
            gender = this.gender?.let { enumValueOf<Gender>(it) },
            bloodType = this.bloodType?.let { enumValueOf<BloodType>(it) },
            dominantHand = this.dominantHand?.let { enumValueOf<Hand>(it) },
            eyeColor = this.eyeColor,
            
            // Key timing
            firstBreath = this.firstBreath?.let { 
                LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            },
            
            // Environmental
            weatherConditions = this.weatherConditions,
            moonPhase = this.moonPhase,
            hospitalName = this.hospitalName,
            
            // Life patterns
            firstWord = this.firstWord,
            firstSteps = this.firstSteps?.let { 
                LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            },
            
            // Preferences & completion
            preferences = this.preferences?.let { 
                moshi.adapter(UserPreferences::class.java).fromJson(it) 
            } ?: UserPreferences(),
            profileCompletion = this.profileCompletion?.let { 
                moshi.adapter(ProfileCompletion::class.java).fromJson(it) 
            } ?: ProfileCompletion(),
            
            // AI enrichment result
            enrichmentResult = this.enrichmentResult,
            enrichmentGeneratedAt = this.enrichmentGeneratedAt?.let {
                LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            }
        )
    }
    
}
