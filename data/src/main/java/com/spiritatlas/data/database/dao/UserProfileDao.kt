package com.spiritatlas.data.database.dao

import androidx.room.*
import com.spiritatlas.data.database.entities.UserProfileEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for UserProfile operations
 * Privacy-first: All queries work with encrypted data ✨
 */
@Dao
interface UserProfileDao {
    
    /**
     * Get the current user's profile as a Flow for reactive updates
     */
    @Query("SELECT * FROM user_profiles ORDER BY updatedAt DESC LIMIT 1")
    fun getUserProfileFlow(): Flow<UserProfileEntity?>
    
    /**
     * Get the current user's profile (one-time query)
     */
    @Query("SELECT * FROM user_profiles ORDER BY updatedAt DESC LIMIT 1") 
    suspend fun getUserProfile(): UserProfileEntity?
    
    /**
     * Get profile by specific ID
     */
    @Query("SELECT * FROM user_profiles WHERE id = :profileId")
    suspend fun getUserProfileById(profileId: String): UserProfileEntity?
    
    /**
     * Get all profiles with reactive updates
     */
    @Query("SELECT * FROM user_profiles ORDER BY updatedAt DESC")
    fun getAllProfilesFlow(): Flow<List<UserProfileEntity>>
    
    /**
     * Get all profiles (one-time query)
     */
    @Query("SELECT * FROM user_profiles ORDER BY updatedAt DESC")
    suspend fun getAllProfiles(): List<UserProfileEntity>
    
    /**
     * Get profile by ID as Flow for reactive updates
     */
    @Query("SELECT * FROM user_profiles WHERE id = :profileId")
    fun getUserProfileByIdFlow(profileId: String): Flow<UserProfileEntity?>
    
    /**
     * Search profiles by name
     */
    @Query("SELECT * FROM user_profiles WHERE profileName LIKE '%' || :query || '%' OR name LIKE '%' || :query || '%' ORDER BY updatedAt DESC")
    suspend fun searchProfiles(query: String): List<UserProfileEntity>
    
    /**
     * Insert or replace a user profile
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: UserProfileEntity)
    
    /**
     * Update specific fields of a profile
     */
    @Update
    suspend fun updateProfile(profile: UserProfileEntity)
    
    /**
     * Delete a specific profile
     */
    @Query("DELETE FROM user_profiles WHERE id = :profileId")
    suspend fun deleteProfile(profileId: String)
    
    /**
     * Delete all profiles (nuclear option - use with caution!)
     */
    @Query("DELETE FROM user_profiles")
    suspend fun deleteAllProfiles()
    
    /**
     * Get profiles that need syncing (for future cloud sync)
     */
    @Query("SELECT * FROM user_profiles WHERE syncStatus = 'LOCAL' OR syncStatus = 'SYNCING'")
    suspend fun getProfilesNeedingSync(): List<UserProfileEntity>
    
    /**
     * Mark profile as synced
     */
    @Query("UPDATE user_profiles SET syncStatus = 'SYNCED', updatedAt = :timestamp WHERE id = :profileId")
    suspend fun markProfileAsSynced(profileId: String, timestamp: Long = System.currentTimeMillis())
    
    /**
     * Get profile completion stats
     */
    @Query("SELECT COUNT(*) FROM user_profiles")
    suspend fun getProfileCount(): Int
    
    /**
     * Update profile completion metrics
     */
    @Query("""
        UPDATE user_profiles 
        SET profileCompletion = :completionJson, updatedAt = :timestamp 
        WHERE id = :profileId
    """)
    suspend fun updateProfileCompletion(
        profileId: String, 
        completionJson: String,
        timestamp: Long = System.currentTimeMillis()
    )
}
