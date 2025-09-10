package com.spiritatlas.data.database.dao;

/**
 * Data Access Object for UserProfile operations
 * Privacy-first: All queries work with encrypted data ✨
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\t\n\u0002\b\b\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\rH\'J\u000e\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0012\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0018\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\r2\u0006\u0010\u0006\u001a\u00020\u0007H\'J\u0010\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\rH\'J\u0016\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0017J \u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\u0019\u001a\u00020\u001aH\u00a7@\u00a2\u0006\u0002\u0010\u001bJ\u001c\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u001d\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0017J(\u0010\u001f\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010 \u001a\u00020\u00072\b\b\u0002\u0010\u0019\u001a\u00020\u001aH\u00a7@\u00a2\u0006\u0002\u0010!\u00a8\u0006\""}, d2 = {"Lcom/spiritatlas/data/database/dao/UserProfileDao;", "", "deleteAllProfiles", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteProfile", "profileId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllProfiles", "", "Lcom/spiritatlas/data/database/entities/UserProfileEntity;", "getAllProfilesFlow", "Lkotlinx/coroutines/flow/Flow;", "getProfileCount", "", "getProfilesNeedingSync", "getUserProfile", "getUserProfileById", "getUserProfileByIdFlow", "getUserProfileFlow", "insertOrUpdateProfile", "profile", "(Lcom/spiritatlas/data/database/entities/UserProfileEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markProfileAsSynced", "timestamp", "", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchProfiles", "query", "updateProfile", "updateProfileCompletion", "completionJson", "(Ljava/lang/String;Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "data_debug"})
@androidx.room.Dao()
public abstract interface UserProfileDao {
    
    /**
     * Get the current user's profile as a Flow for reactive updates
     */
    @androidx.room.Query(value = "SELECT * FROM user_profiles ORDER BY updatedAt DESC LIMIT 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.spiritatlas.data.database.entities.UserProfileEntity> getUserProfileFlow();
    
    /**
     * Get the current user's profile (one-time query)
     */
    @androidx.room.Query(value = "SELECT * FROM user_profiles ORDER BY updatedAt DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUserProfile(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.spiritatlas.data.database.entities.UserProfileEntity> $completion);
    
    /**
     * Get profile by specific ID
     */
    @androidx.room.Query(value = "SELECT * FROM user_profiles WHERE id = :profileId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getUserProfileById(@org.jetbrains.annotations.NotNull()
    java.lang.String profileId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.spiritatlas.data.database.entities.UserProfileEntity> $completion);
    
    /**
     * Get all profiles with reactive updates
     */
    @androidx.room.Query(value = "SELECT * FROM user_profiles ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.spiritatlas.data.database.entities.UserProfileEntity>> getAllProfilesFlow();
    
    /**
     * Get all profiles (one-time query)
     */
    @androidx.room.Query(value = "SELECT * FROM user_profiles ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllProfiles(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.spiritatlas.data.database.entities.UserProfileEntity>> $completion);
    
    /**
     * Get profile by ID as Flow for reactive updates
     */
    @androidx.room.Query(value = "SELECT * FROM user_profiles WHERE id = :profileId")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.spiritatlas.data.database.entities.UserProfileEntity> getUserProfileByIdFlow(@org.jetbrains.annotations.NotNull()
    java.lang.String profileId);
    
    /**
     * Search profiles by name
     */
    @androidx.room.Query(value = "SELECT * FROM user_profiles WHERE profileName LIKE \'%\' || :query || \'%\' OR name LIKE \'%\' || :query || \'%\' ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchProfiles(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.spiritatlas.data.database.entities.UserProfileEntity>> $completion);
    
    /**
     * Insert or replace a user profile
     */
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertOrUpdateProfile(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.data.database.entities.UserProfileEntity profile, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Update specific fields of a profile
     */
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateProfile(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.data.database.entities.UserProfileEntity profile, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Delete a specific profile
     */
    @androidx.room.Query(value = "DELETE FROM user_profiles WHERE id = :profileId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String profileId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Delete all profiles (nuclear option - use with caution!)
     */
    @androidx.room.Query(value = "DELETE FROM user_profiles")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllProfiles(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Get profiles that need syncing (for future cloud sync)
     */
    @androidx.room.Query(value = "SELECT * FROM user_profiles WHERE syncStatus = \'LOCAL\' OR syncStatus = \'SYNCING\'")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getProfilesNeedingSync(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.spiritatlas.data.database.entities.UserProfileEntity>> $completion);
    
    /**
     * Mark profile as synced
     */
    @androidx.room.Query(value = "UPDATE user_profiles SET syncStatus = \'SYNCED\', updatedAt = :timestamp WHERE id = :profileId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markProfileAsSynced(@org.jetbrains.annotations.NotNull()
    java.lang.String profileId, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Get profile completion stats
     */
    @androidx.room.Query(value = "SELECT COUNT(*) FROM user_profiles")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getProfileCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    /**
     * Update profile completion metrics
     */
    @androidx.room.Query(value = "\n        UPDATE user_profiles \n        SET profileCompletion = :completionJson, updatedAt = :timestamp \n        WHERE id = :profileId\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateProfileCompletion(@org.jetbrains.annotations.NotNull()
    java.lang.String profileId, @org.jetbrains.annotations.NotNull()
    java.lang.String completionJson, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Data Access Object for UserProfile operations
     * Privacy-first: All queries work with encrypted data ✨
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}