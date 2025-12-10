# Data Persistence Quick Start Guide

## Quick Reference

### Cache a Profile
```kotlin
@Inject lateinit var cacheManager: CacheManager

suspend fun saveProfile(profile: UserProfile) {
    // Save to database
    userRepository.saveUserProfile(profile)

    // Cache in memory for fast access
    cacheManager.cacheProfile(profile)
}
```

### Get Cached Profile
```kotlin
// Try memory cache first (instant)
val cached = cacheManager.getCachedProfile(profileId)
if (cached != null) {
    return cached
}

// Fallback to database
val profile = userRepository.getProfileById(profileId)
if (profile != null) {
    // Warm the cache for next time
    cacheManager.cacheProfile(profile)
}
```

### Cache Compatibility Report
```kotlin
suspend fun analyzeAndCache(profileA: UserProfile, profileB: UserProfile) {
    val report = compatibilityEngine.analyze(profileA, profileB)

    // Cache in memory
    cacheManager.cacheCompatibilityReport(report)

    // Save to database for persistence
    compatibilityRepository.saveCompatibilityReport(report)
}
```

### Cache AI Response
```kotlin
suspend fun getAiInsight(prompt: String): String {
    // Check cache first
    val cached = cacheManager.getCachedAiResponse(
        prompt = prompt,
        model = "claude-3-5-sonnet",
        provider = "anthropic"
    )

    if (cached != null) {
        return cached // Cache hit!
    }

    // Generate and cache
    val response = aiProvider.generateText(prompt)
    cacheManager.cacheAiResponse(prompt, model, provider, response)

    return response
}
```

### Monitor Offline Status
```kotlin
@Inject lateinit var offlineSyncManager: OfflineSyncManager

fun observeConnectivity() {
    lifecycleScope.launch {
        offlineSyncManager.isOnline.collect { isOnline ->
            if (isOnline) {
                showSnackbar("Back online - syncing data...")
            } else {
                showSnackbar("Offline mode - changes will sync later")
            }
        }
    }
}
```

### Queue Offline Operation
```kotlin
suspend fun updateProfileOffline(profile: UserProfile) {
    // Save locally
    userRepository.saveUserProfile(profile)

    // Queue for sync when online
    offlineSyncManager.queueOperation(
        PendingOperation(
            id = UUID.randomUUID().toString(),
            type = OperationType.PROFILE_UPDATE,
            data = mapOf("profileId" to profile.id)
        )
    )
}
```

### View Cache Statistics
```kotlin
suspend fun showCacheStats() {
    val stats = cacheManager.getCacheStatistics()

    Log.d("Cache", """
        Overall Hit Rate: ${(stats.overallHitRate * 100).toInt()}%
        Memory Entries: ${stats.totalMemoryEntries}
        Database Entries: ${stats.totalPersistentEntries}

        Profile Cache: ${stats.profiles.size}/${CacheManager.MAX_PROFILES}
        Compatibility Cache: ${stats.compatibilityReports.size}/${CacheManager.MAX_COMPATIBILITY_REPORTS}
        AI Cache: ${stats.aiResponses.size}/${CacheManager.MAX_AI_RESPONSES}
    """.trimIndent())
}
```

### Clear Cache
```kotlin
// Clear specific cache
cacheManager.clearCache(CacheType.PROFILES)

// Clear all caches
cacheManager.clearCache(CacheType.ALL)
```

### Create Database Backup
```kotlin
@Inject lateinit var databaseMigrations: DatabaseMigrations

suspend fun backupDatabase() {
    val result = databaseMigrations.createBackup()

    result.onSuccess { backupPath ->
        Log.d("Backup", "Created: $backupPath")
        showToast("Backup created successfully")
    }

    result.onFailure { error ->
        Log.e("Backup", "Failed", error)
        showToast("Backup failed: ${error.message}")
    }
}
```

### Restore from Backup
```kotlin
suspend fun restoreDatabase(backupPath: String) {
    val result = databaseMigrations.restoreFromBackup(backupPath)

    result.onSuccess {
        Log.d("Restore", "Database restored")
        // Restart app to reload data
        restartApp()
    }
}
```

### Export User Data (GDPR)
```kotlin
suspend fun exportUserData(): String {
    val result = databaseMigrations.exportUserData()

    return result.getOrElse {
        Log.e("Export", "Failed", it)
        "{\"error\": \"${it.message}\"}"
    }
}
```

### Delete All Data (GDPR)
```kotlin
suspend fun deleteAllUserData() {
    val confirmed = showConfirmationDialog(
        "This will permanently delete all your data. This cannot be undone."
    )

    if (confirmed) {
        val result = databaseMigrations.deleteAllUserData()

        result.onSuccess {
            // Clear session and return to welcome screen
            clearSession()
            navigateToWelcome()
        }
    }
}
```

## Performance Tips

### Warm Critical Data on App Start
```kotlin
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val cacheManager: CacheManager
) : ViewModel() {

    init {
        viewModelScope.launch {
            // Load and cache user profile
            userRepository.getUserProfile().first()?.let { profile ->
                cacheManager.cacheProfile(profile)
            }
        }
    }
}
```

### Batch Database Operations
```kotlin
// Instead of this:
profiles.forEach { profile ->
    userProfileDao.insertOrUpdateProfile(profile)
}

// Do this (single transaction):
database.withTransaction {
    profiles.forEach { profile ->
        userProfileDao.insertOrUpdateProfile(profile)
    }
}
```

### Use Flow for Reactive Updates
```kotlin
// Observe profile changes
userRepository.getUserProfile()
    .flowOn(Dispatchers.IO)
    .collect { profile ->
        // UI automatically updates
        _uiState.value = _uiState.value.copy(profile = profile)
    }
```

### Schedule Maintenance
```kotlin
class MainApplication : Application() {

    @Inject lateinit var cacheManager: CacheManager
    @Inject lateinit var databaseMigrations: DatabaseMigrations

    override fun onCreate() {
        super.onCreate()

        // Schedule periodic maintenance
        lifecycleScope.launch {
            while (true) {
                delay(TimeUnit.HOURS.toMillis(6))

                // Cleanup expired entries
                cacheManager.cleanupExpired()

                // Trim caches to limits
                cacheManager.trimCaches()

                // Cleanup old backups (keep 5 most recent)
                databaseMigrations.cleanupOldBackups(keepCount = 5)
            }
        }
    }
}
```

## Common Patterns

### Repository with Caching
```kotlin
@Singleton
class OptimizedUserRepository @Inject constructor(
    private val userProfileDao: UserProfileDao,
    private val cacheManager: CacheManager
) : UserRepository {

    override suspend fun getProfileById(profileId: String): UserProfile? {
        // 1. Check memory cache
        cacheManager.getCachedProfile(profileId)?.let { return it }

        // 2. Query database
        val entity = userProfileDao.getUserProfileById(profileId) ?: return null
        val profile = entity.toDomain()

        // 3. Warm cache
        cacheManager.cacheProfile(profile)

        return profile
    }

    override suspend fun saveUserProfile(profile: UserProfile) {
        // 1. Save to database
        userProfileDao.insertOrUpdateProfile(profile.toEntity())

        // 2. Update memory cache
        cacheManager.cacheProfile(profile)

        // 3. Queue for sync if needed
        if (!offlineSyncManager.isOnline.value) {
            offlineSyncManager.queueOperation(
                PendingOperation(
                    id = profile.id,
                    type = OperationType.PROFILE_UPDATE,
                    data = mapOf("profileId" to profile.id)
                )
            )
        }
    }
}
```

### Offline-First ViewModel
```kotlin
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val offlineSyncManager: OfflineSyncManager
) : ViewModel() {

    val isOnline = offlineSyncManager.isOnline
    val syncStatus = offlineSyncManager.syncStatus

    fun saveProfile(profile: UserProfile) {
        viewModelScope.launch {
            try {
                // Always save locally first
                userRepository.saveUserProfile(profile)

                // Sync will happen automatically when online
                _uiState.value = _uiState.value.copy(
                    saveSuccess = true,
                    message = if (isOnline.value) {
                        "Profile saved and synced"
                    } else {
                        "Profile saved - will sync when online"
                    }
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to save: ${e.message}"
                )
            }
        }
    }
}
```

## Debugging

### Enable Database Logging
```kotlin
// In build.gradle.kts (debug build)
Room.databaseBuilder(context, SpiritAtlasDatabase::class.java, "spirit_atlas.db")
    .setQueryCallback({ sqlQuery, bindArgs ->
        Log.d("RoomQuery", "SQL: $sqlQuery | Args: $bindArgs")
    }, Executors.newSingleThreadExecutor())
    .build()
```

### Monitor Cache Performance
```kotlin
// Add to debug settings screen
@Composable
fun CacheDebugSection() {
    val stats by remember { mutableStateOf<CombinedCacheStats?>(null) }

    LaunchedEffect(Unit) {
        stats = cacheManager.getCacheStatistics()
    }

    stats?.let { s ->
        Column {
            Text("Cache Hit Rate: ${(s.overallHitRate * 100).toInt()}%")
            Text("Memory: ${s.totalMemoryEntries}")
            Text("Database: ${s.totalPersistentEntries}")

            Button(onClick = { cacheManager.clearCache(CacheType.ALL) }) {
                Text("Clear All Caches")
            }
        }
    }
}
```

## Testing

### Unit Test with In-Memory Database
```kotlin
@Test
fun testProfileCaching() = runTest {
    // Arrange
    val database = Room.inMemoryDatabaseBuilder(
        context,
        SpiritAtlasDatabase::class.java
    ).build()

    val dao = database.userProfileDao()
    val profile = createTestProfile()

    // Act
    dao.insertOrUpdateProfile(profile.toEntity())
    val retrieved = dao.getUserProfileById(profile.id)

    // Assert
    assertNotNull(retrieved)
    assertEquals(profile.id, retrieved?.id)

    database.close()
}
```

## Migration Checklist

When creating a new database migration:

- [ ] Increment database version number
- [ ] Create Migration object with SQL statements
- [ ] Register migration in database builder
- [ ] Add migration to DatabaseMigrations.kt
- [ ] Test migration with existing data
- [ ] Update schema documentation
- [ ] Create backup before migration in production
- [ ] Test rollback scenario

## Troubleshooting

### Cache Not Working
- Check TTL hasn't expired
- Verify cache key generation is consistent
- Check LRU eviction (cache may be full)
- Enable debug logging to trace cache hits/misses

### Database Migration Failed
- Check SQL syntax in migration
- Verify all required migrations are registered
- Use fallbackToDestructiveMigration() only in development
- Test with real user data patterns

### Offline Sync Not Triggering
- Check network connectivity monitoring is active
- Verify operations are being queued correctly
- Check sync permissions in AndroidManifest.xml
- Monitor offlineSyncManager.syncStatus flow

### Performance Issues
- Add indexes to frequently queried fields
- Use database profiling to find slow queries
- Monitor cache hit rates
- Consider batch operations for bulk updates
- Profile with Android Studio Profiler
