# Data Persistence Architecture

## Overview

SpiritAtlas implements a sophisticated multi-tier data persistence strategy optimized for performance, offline-first operation, and intelligent caching. This document outlines the architecture, implementation details, and best practices.

## Architecture Layers

### 1. In-Memory Cache Layer (Hot Data)
- **Implementation**: `InMemoryCache<T>` with Android LRU cache
- **Purpose**: Ultra-fast access to frequently used data
- **Features**:
  - Thread-safe with Kotlin coroutines Mutex
  - TTL-based expiration
  - LRU eviction policy
  - Access tracking for analytics
  - Automatic cleanup of expired entries

**Configuration**:
```kotlin
// Profiles: 100 max, 24hr TTL
// Compatibility Reports: 50 max, 7 day TTL
// AI Responses: 100 max, 24hr TTL
```

### 2. Room Database Layer (Warm/Cold Data)
- **Implementation**: Room with SQLite backend
- **Purpose**: Persistent local storage with ACID guarantees
- **Features**:
  - Optimized indexes on frequently queried fields
  - Type converters for complex objects
  - Reactive Flow-based queries
  - Multi-instance invalidation support

**Entities**:
- `UserProfileEntity` - User spiritual profiles
- `CompatibilityReportEntity` - Cached compatibility analysis results
- `AiResponseCacheEntity` - Cached AI API responses

### 3. Coordination Layer
- **Implementation**: `CacheManager`
- **Purpose**: Orchestrates multi-tier caching strategy
- **Features**:
  - Transparent cache hierarchy (memory → database)
  - Cache hit/miss tracking
  - Periodic cleanup and trimming
  - Cache statistics and monitoring

## Database Schema (v5)

### User Profiles
```sql
CREATE TABLE user_profiles (
    id TEXT PRIMARY KEY,
    profileName TEXT NOT NULL,
    createdAt TEXT NOT NULL,
    lastModified TEXT NOT NULL,
    -- [additional profile fields]
    updatedAt INTEGER NOT NULL,
    syncStatus TEXT NOT NULL DEFAULT 'LOCAL',
    encryptionVersion INTEGER NOT NULL DEFAULT 1
);

-- Indexes for performance
CREATE INDEX index_user_profiles_updatedAt ON user_profiles(updatedAt);
CREATE INDEX index_user_profiles_profileName ON user_profiles(profileName);
CREATE INDEX index_user_profiles_name ON user_profiles(name);
CREATE INDEX index_user_profiles_syncStatus ON user_profiles(syncStatus);
```

### Compatibility Reports Cache
```sql
CREATE TABLE compatibility_reports (
    id TEXT PRIMARY KEY,
    profileAId TEXT NOT NULL,
    profileBId TEXT NOT NULL,
    reportJson TEXT NOT NULL,
    generatedAt INTEGER NOT NULL,
    expiresAt INTEGER NOT NULL,
    accessCount INTEGER NOT NULL,
    lastAccessedAt INTEGER NOT NULL,
    overallScore REAL NOT NULL,
    compatibilityLevel TEXT NOT NULL,
    hasAiInsights INTEGER NOT NULL DEFAULT 0
);

-- Composite and single indexes for query optimization
CREATE INDEX index_compatibility_reports_profileAId_profileBId
    ON compatibility_reports(profileAId, profileBId);
CREATE INDEX index_compatibility_reports_profileAId
    ON compatibility_reports(profileAId);
CREATE INDEX index_compatibility_reports_profileBId
    ON compatibility_reports(profileBId);
CREATE INDEX index_compatibility_reports_generatedAt
    ON compatibility_reports(generatedAt);
```

### AI Response Cache
```sql
CREATE TABLE ai_response_cache (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    requestHash TEXT NOT NULL UNIQUE,
    prompt TEXT NOT NULL,
    model TEXT NOT NULL,
    provider TEXT NOT NULL,
    response TEXT NOT NULL,
    createdAt INTEGER NOT NULL,
    expiresAt INTEGER NOT NULL,
    hitCount INTEGER NOT NULL,
    lastHitAt INTEGER NOT NULL,
    tokensUsed INTEGER NOT NULL
);

-- Unique index on request hash for deduplication
CREATE UNIQUE INDEX index_ai_response_cache_requestHash
    ON ai_response_cache(requestHash);
CREATE INDEX index_ai_response_cache_createdAt
    ON ai_response_cache(createdAt);
CREATE INDEX index_ai_response_cache_expiresAt
    ON ai_response_cache(expiresAt);
```

## Offline-First Architecture

### Network Monitoring
`OfflineSyncManager` continuously monitors network connectivity using Android's ConnectivityManager:

```kotlin
val isOnline: StateFlow<Boolean> = offlineSyncManager.isOnline
```

### Operation Queuing
When offline, operations are queued for later synchronization:

```kotlin
offlineSyncManager.queueOperation(
    PendingOperation(
        id = UUID.randomUUID().toString(),
        type = OperationType.PROFILE_UPDATE,
        data = mapOf("profileId" to profile.id)
    )
)
```

### Automatic Sync
When network becomes available, queued operations are automatically synced:
- Retry logic with exponential backoff
- Conflict resolution (last-write-wins by default)
- Partial failure handling

## Cache Management

### Cache Hierarchy
1. **Memory Cache** (checked first)
   - Instant access
   - Limited capacity (LRU eviction)
   - TTL-based expiration

2. **Database Cache** (fallback)
   - Durable storage
   - Larger capacity
   - Indexed queries

3. **Network/Computation** (last resort)
   - Regenerate or fetch from API
   - Cache result in both tiers

### Cache Invalidation Strategies

**Time-based (TTL)**:
- Profiles: 24 hours
- Compatibility Reports: 7 days
- AI Responses: 24 hours

**Event-based**:
- Profile updated → Invalidate profile cache
- Report generated → Cache new report
- User logout → Clear all caches

**Size-based (LRU)**:
- Automatic eviction when limits reached
- Keeps most recently accessed items

### Cache Statistics
Monitor cache performance:

```kotlin
val stats = cacheManager.getCacheStatistics()
println("Overall hit rate: ${stats.overallHitRate}")
println("Memory entries: ${stats.totalMemoryEntries}")
println("Persistent entries: ${stats.totalPersistentEntries}")
```

## Database Migrations

### Migration History
- **v1 → v2**: Multi-profile support
- **v2 → v3**: Schema cleanup (no changes)
- **v3 → v4**: AI enrichment fields
- **v4 → v5**: Cache tables and optimized indexes

### Creating New Migrations

1. **Update Version Number**:
```kotlin
@Database(
    entities = [...],
    version = 6, // Increment
    exportSchema = true
)
```

2. **Add Migration**:
```kotlin
private val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // SQL migration statements
        db.execSQL("ALTER TABLE ...")
    }
}
```

3. **Register Migration**:
```kotlin
.addMigrations(..., MIGRATION_5_6)
```

### Backup & Restore

**Create Backup**:
```kotlin
val result = databaseMigrations.createBackup()
result.onSuccess { backupPath ->
    println("Backup created: $backupPath")
}
```

**Restore from Backup**:
```kotlin
val result = databaseMigrations.restoreFromBackup(backupPath)
result.onSuccess {
    println("Database restored successfully")
}
```

**Cleanup Old Backups**:
```kotlin
databaseMigrations.cleanupOldBackups(keepCount = 5)
```

## GDPR Compliance

### Data Export
Export all user data in portable JSON format:

```kotlin
val result = databaseMigrations.exportUserData()
result.onSuccess { jsonData ->
    // Provide to user for download
}
```

### Data Deletion
Complete removal of all user data:

```kotlin
val result = databaseMigrations.deleteAllUserData()
// Deletes:
// - Database file
// - All backups
// - Cache files
// - Encrypted preferences
```

## Performance Optimization Tips

### Query Optimization
1. **Use Indexes**: All frequently queried fields should have indexes
2. **Projection**: Select only needed columns
3. **Batch Operations**: Use transactions for multiple inserts
4. **Flow vs Suspend**: Use Flow for reactive updates, suspend for one-time queries

### Cache Optimization
1. **Warm Critical Data**: Pre-cache user's own profile on app start
2. **Monitor Hit Rates**: Track and optimize based on cache statistics
3. **Adjust TTLs**: Tune TTL values based on data volatility
4. **Periodic Cleanup**: Schedule cleanup during idle time

### Database Optimization
1. **Vacuum**: Periodically run VACUUM to reclaim space
2. **Analyze**: Update query planner statistics
3. **WAL Mode**: Use Write-Ahead Logging for better concurrency
4. **Page Size**: Tune SQLite page size for your data

## Monitoring & Debugging

### Enable Debug Logging
```kotlin
// In debug builds
if (BuildConfig.DEBUG) {
    // Database query logging
    RoomDatabase.Builder<>()
        .setQueryCallback({ sqlQuery, bindArgs ->
            Log.d("RoomQuery", "SQL: $sqlQuery")
        }, Executors.newSingleThreadExecutor())
}
```

### Cache Metrics
```kotlin
// Periodic cache stats logging
scope.launch {
    while (true) {
        delay(TimeUnit.MINUTES.toMillis(5))
        val stats = cacheManager.getCacheStatistics()
        Log.d("CacheMetrics", """
            Hit Rate: ${stats.overallHitRate}
            Memory: ${stats.totalMemoryEntries}
            Persistent: ${stats.totalPersistentEntries}
        """.trimIndent())
    }
}
```

### Database Inspection
Use Android Studio's Database Inspector to:
- View live database contents
- Run SQL queries
- Monitor query performance
- Validate migrations

## Testing

### Unit Tests
- Test DAO queries with in-memory database
- Test cache eviction policies
- Test TTL expiration logic
- Test migration scripts

### Integration Tests
- Test cache coordination
- Test offline queue and sync
- Test conflict resolution
- Test backup and restore

### Performance Tests
- Measure query execution time
- Measure cache hit rates
- Measure sync operation duration
- Memory usage profiling

## Best Practices

1. **Always Check Cache First**: Memory → Database → Network/Compute
2. **Update All Tiers**: When data changes, update memory + database caches
3. **Handle Offline Gracefully**: Queue operations, show offline indicator
4. **Monitor Performance**: Track cache hit rates and query times
5. **Regular Maintenance**: Schedule cleanup, vacuum, and backup operations
6. **Test Migrations**: Always test migrations with real user data patterns
7. **GDPR Ready**: Ensure all data can be exported and deleted on request

## Future Enhancements

- Cloud sync with Firebase/custom backend
- Encrypted database using SQLCipher
- Advanced conflict resolution strategies
- Incremental sync for large datasets
- GraphQL subscription support for real-time updates
- Multi-device sync with CRDTs
- Intelligent prefetching based on usage patterns
