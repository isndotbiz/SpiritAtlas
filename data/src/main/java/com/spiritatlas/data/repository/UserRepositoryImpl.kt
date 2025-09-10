package com.spiritatlas.data.repository

import com.spiritatlas.data.database.dao.UserProfileDao
import com.spiritatlas.data.database.mappers.UserProfileMappers.toDomain
import com.spiritatlas.data.database.mappers.UserProfileMappers.toEntity
import com.spiritatlas.domain.model.AccuracyLevel
import com.spiritatlas.domain.model.ProfileCompletion
import com.spiritatlas.domain.model.UserProfile
import com.spiritatlas.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

/**
 * UserRepository implementation with Room database backend
 * Privacy-first: All profile data encrypted at rest with intelligent completion tracking ✨
 */
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao
) : UserRepository {
    
    override suspend fun saveUserProfile(profile: UserProfile) {
        try {
            Log.d("SpiritAtlas", "Starting to save profile: ${profile.id}")
            
            // Calculate profile completion before saving
            val updatedProfile = profile.copy(
                profileCompletion = calculateProfileCompletion(profile)
            )
            
            Log.d("SpiritAtlas", "Profile completion calculated: ${updatedProfile.profileCompletion.completedFields} fields")
            
            val entity = updatedProfile.toEntity()
            Log.d("SpiritAtlas", "Profile converted to entity: ${entity.id}")
            
            userProfileDao.insertOrUpdateProfile(entity)
            Log.d("SpiritAtlas", "Profile saved successfully to database")
            
        } catch (e: Exception) {
            Log.e("SpiritAtlas", "Error saving profile: ${e.message}", e)
            throw e
        }
    }
    
    override fun getUserProfile(): Flow<UserProfile?> {
        return userProfileDao.getUserProfileFlow().map { entity ->
            entity?.let { 
                val profile = it.toDomain()
                profile.copy(profileCompletion = calculateProfileCompletion(profile))
            }
        }
    }
    
    override suspend fun clearUserData() {
        userProfileDao.deleteAllProfiles()
    }
    
    // === Multiple Profile Methods ===
    
    override suspend fun getAllProfiles(): List<UserProfile> {
        return userProfileDao.getAllProfiles().map { entity ->
            val profile = entity.toDomain()
            profile.copy(profileCompletion = calculateProfileCompletion(profile))
        }
    }
    
    override fun getAllProfilesFlow(): Flow<List<UserProfile>> {
        return userProfileDao.getAllProfilesFlow().map { entities ->
            entities.map { entity -> 
                val profile = entity.toDomain()
                profile.copy(profileCompletion = calculateProfileCompletion(profile))
            }
        }
    }
    
    override suspend fun getProfileById(profileId: String): UserProfile? {
        return userProfileDao.getUserProfileById(profileId)?.let { entity ->
            val profile = entity.toDomain()
            profile.copy(profileCompletion = calculateProfileCompletion(profile))
        }
    }
    
    override fun getProfileByIdFlow(profileId: String): Flow<UserProfile?> {
        return userProfileDao.getUserProfileByIdFlow(profileId).map { entity ->
            entity?.let {
                val profile = it.toDomain()
                profile.copy(profileCompletion = calculateProfileCompletion(profile))
            }
        }
    }
    
    override suspend fun deleteProfile(profileId: String) {
        userProfileDao.deleteProfile(profileId)
    }
    
    override suspend fun searchProfiles(query: String): List<UserProfile> {
        return userProfileDao.searchProfiles(query).map { entity ->
            val profile = entity.toDomain()
            profile.copy(profileCompletion = calculateProfileCompletion(profile))
        }
    }
    
    override suspend fun getProfileCount(): Int {
        return userProfileDao.getProfileCount()
    }
    
    /**
     * Calculate profile completion metrics based on filled fields (streamlined model)
     * This determines accuracy levels for spiritual calculations 🔮
     */
    private fun calculateProfileCompletion(profile: UserProfile): ProfileCompletion {
        val totalFields = 27 // Total possible fields in streamlined profile
        var completedFields = 0
        val missingCriticalFields = mutableListOf<String>()
        
        // Profile metadata (always present for new profiles)
        completedFields++ // profileName
        completedFields++ // id
        completedFields++ // createdAt
        completedFields++ // lastModified
        
        // Core identity fields
        profile.name?.let { if (it.isNotBlank()) completedFields++ } ?: missingCriticalFields.add("name")
        profile.displayName?.let { if (it.isNotBlank()) completedFields++ }
        profile.birthDateTime?.let { completedFields++ } ?: missingCriticalFields.add("birthDateTime")
        profile.birthPlace?.let { completedFields++ } ?: missingCriticalFields.add("birthPlace")
        
        // Additional names (3 fields - streamlined)
        profile.middleName?.let { if (it.isNotBlank()) completedFields++ }
        profile.nickname?.let { if (it.isNotBlank()) completedFields++ }
        profile.spiritualName?.let { if (it.isNotBlank()) completedFields++ }
        
        // Family & ancestry (3 fields - streamlined)
        profile.motherName?.let { if (it.isNotBlank()) completedFields++ }
        profile.fatherName?.let { if (it.isNotBlank()) completedFields++ }
        profile.ancestry?.let { if (it.isNotBlank()) completedFields++ }
        
        // Physical & energetic (4 fields - streamlined)
        profile.gender?.let { completedFields++ }
        profile.bloodType?.let { completedFields++ }
        profile.dominantHand?.let { completedFields++ }
        profile.eyeColor?.let { if (it.isNotBlank()) completedFields++ }
        
        // Key timing (1 field - streamlined)
        profile.firstBreath?.let { completedFields++ }
        
        // Environmental (3 fields - streamlined)
        profile.weatherConditions?.let { if (it.isNotBlank()) completedFields++ }
        profile.moonPhase?.let { if (it.isNotBlank()) completedFields++ }
        profile.hospitalName?.let { if (it.isNotBlank()) completedFields++ }
        
        // Life patterns (2 fields)
        profile.firstWord?.let { if (it.isNotBlank()) completedFields++ }
        profile.firstSteps?.let { completedFields++ }
        
        // Calculate completion percentage
        val completionPercentage = (completedFields.toDouble() / totalFields) * 100.0
        
        // Determine accuracy level based on field count (adjusted for streamlined model)
        val accuracyLevel = when {
            completedFields < 3 -> AccuracyLevel.MINIMAL
            completedFields < 8 -> AccuracyLevel.BASIC
            completedFields < 16 -> AccuracyLevel.GOOD
            completedFields < 24 -> AccuracyLevel.EXCELLENT
            else -> AccuracyLevel.MAXIMUM
        }
        
        return ProfileCompletion(
            totalFields = totalFields,
            completedFields = completedFields,
            completionPercentage = completionPercentage,
            accuracyLevel = accuracyLevel,
            missingCriticalFields = missingCriticalFields
        )
    }
}
