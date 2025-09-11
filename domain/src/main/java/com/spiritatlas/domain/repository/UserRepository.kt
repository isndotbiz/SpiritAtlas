package com.spiritatlas.domain.repository

import com.spiritatlas.domain.model.UserProfile
import com.spiritatlas.domain.model.ShareableProfile
import com.spiritatlas.domain.model.ProfileImportResult
import com.spiritatlas.domain.model.ProfileLibraryEntry
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
    
    // === PROFILE SHARING & DISCOVERY ===
    suspend fun exportProfileForSharing(profileId: String): Result<ShareableProfile>
    suspend fun importSharedProfile(sharedProfile: ShareableProfile): Result<ProfileImportResult>
    suspend fun getProfileLibraryEntries(): List<ProfileLibraryEntry>
    suspend fun searchProfileLibrary(query: String): List<ProfileLibraryEntry>
    suspend fun addProfileTags(profileId: String, tags: List<String>)
    suspend fun getProfilesByTag(tag: String): List<ProfileLibraryEntry>
}


