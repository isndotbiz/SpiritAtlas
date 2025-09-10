package com.spiritatlas.domain.repository

import com.spiritatlas.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    // === Single Profile Methods (Legacy) ===
    suspend fun saveUserProfile(profile: UserProfile)
    fun getUserProfile(): Flow<UserProfile?>
    suspend fun clearUserData()
    
    // === Multiple Profile Methods ===
    suspend fun getAllProfiles(): List<UserProfile>
    fun getAllProfilesFlow(): Flow<List<UserProfile>>
    suspend fun getProfileById(profileId: String): UserProfile?
    fun getProfileByIdFlow(profileId: String): Flow<UserProfile?>
    suspend fun deleteProfile(profileId: String)
    suspend fun searchProfiles(query: String): List<UserProfile>
    suspend fun getProfileCount(): Int
}


