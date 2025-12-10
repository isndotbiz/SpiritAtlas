package com.spiritatlas.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.spiritatlas.data.database.converters.SpiritualTypeConverters
import com.spiritatlas.data.database.dao.AiResponseCacheDao
import com.spiritatlas.data.database.dao.CompatibilityReportDao
import com.spiritatlas.data.database.dao.UserProfileDao
import com.spiritatlas.data.database.entities.AiResponseCacheEntity
import com.spiritatlas.data.database.entities.CompatibilityReportEntity
import com.spiritatlas.data.database.entities.UserProfileEntity

/**
 * Room database for SpiritAtlas spiritual profiles
 * Privacy-first: Data encrypted at app level via EncryptedSharedPreferences
 * Optimized: Intelligent caching with LRU and TTL strategies
 */
@Database(
    entities = [
        UserProfileEntity::class,
        CompatibilityReportEntity::class,
        AiResponseCacheEntity::class
    ],
    version = 5,
    exportSchema = true // Track schema changes for production
)
@TypeConverters(SpiritualTypeConverters::class)
abstract class SpiritAtlasDatabase : RoomDatabase() {

    abstract fun userProfileDao(): UserProfileDao
    abstract fun compatibilityReportDao(): CompatibilityReportDao
    abstract fun aiResponseCacheDao(): AiResponseCacheDao
    
    companion object {
        private const val DATABASE_NAME = "spirit_atlas.db"
        
        @Volatile
        private var INSTANCE: SpiritAtlasDatabase? = null
        
        fun getInstance(context: Context): SpiritAtlasDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = buildDatabase(context)
                INSTANCE = instance
                instance
            }
        }
        
        private fun buildDatabase(context: Context): SpiritAtlasDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                SpiritAtlasDatabase::class.java,
                DATABASE_NAME
            )
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5) // Schema migrations
            .fallbackToDestructiveMigration() // Only for development
            .enableMultiInstanceInvalidation() // Support multi-process access
            .build()
        }
        
        // Migration from single profile (v1) to multi-profile schema (v2)
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Add profile metadata fields for multi-profile support
                db.execSQL("ALTER TABLE user_profiles ADD COLUMN profileName TEXT NOT NULL DEFAULT 'My Profile'")
                db.execSQL("ALTER TABLE user_profiles ADD COLUMN createdAt TEXT NOT NULL DEFAULT '2024-01-01T00:00:00'")
                db.execSQL("ALTER TABLE user_profiles ADD COLUMN lastModified TEXT NOT NULL DEFAULT '2024-01-01T00:00:00'")
                
                // Add metadata fields
                db.execSQL("ALTER TABLE user_profiles ADD COLUMN updatedAt INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE user_profiles ADD COLUMN syncStatus TEXT NOT NULL DEFAULT 'LOCAL'")
                db.execSQL("ALTER TABLE user_profiles ADD COLUMN encryptionVersion INTEGER NOT NULL DEFAULT 1")
            }
        }
        
        // Migration from v2 to v3 - Clean schema (no changes needed)
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Schema is already correct in v3, no changes needed
            }
        }
        
        // Migration from v3 to v4 - Add enrichment result fields
        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Add enrichment result fields for AI-generated reports
                db.execSQL("ALTER TABLE user_profiles ADD COLUMN enrichmentResult TEXT")
                db.execSQL("ALTER TABLE user_profiles ADD COLUMN enrichmentGeneratedAt TEXT")
            }
        }

        // Migration from v4 to v5 - Add caching tables and optimize indexes
        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Create compatibility reports cache table with indexes
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS compatibility_reports (
                        id TEXT PRIMARY KEY NOT NULL,
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
                    )
                """.trimIndent())

                // Add indexes for compatibility reports
                db.execSQL("CREATE INDEX IF NOT EXISTS index_compatibility_reports_profileAId_profileBId ON compatibility_reports (profileAId, profileBId)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_compatibility_reports_profileAId ON compatibility_reports (profileAId)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_compatibility_reports_profileBId ON compatibility_reports (profileBId)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_compatibility_reports_generatedAt ON compatibility_reports (generatedAt)")

                // Create AI response cache table with indexes
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS ai_response_cache (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        requestHash TEXT NOT NULL,
                        prompt TEXT NOT NULL,
                        model TEXT NOT NULL,
                        provider TEXT NOT NULL,
                        response TEXT NOT NULL,
                        createdAt INTEGER NOT NULL,
                        expiresAt INTEGER NOT NULL,
                        hitCount INTEGER NOT NULL,
                        lastHitAt INTEGER NOT NULL,
                        tokensUsed INTEGER NOT NULL
                    )
                """.trimIndent())

                // Add indexes for AI cache
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_ai_response_cache_requestHash ON ai_response_cache (requestHash)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_ai_response_cache_createdAt ON ai_response_cache (createdAt)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_ai_response_cache_expiresAt ON ai_response_cache (expiresAt)")

                // Add indexes to user_profiles for performance
                db.execSQL("CREATE INDEX IF NOT EXISTS index_user_profiles_updatedAt ON user_profiles (updatedAt)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_user_profiles_profileName ON user_profiles (profileName)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_user_profiles_name ON user_profiles (name)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_user_profiles_syncStatus ON user_profiles (syncStatus)")
            }
        }
        
        /**
         * Close database instance (for testing or app cleanup)
         */
        fun closeDatabase() {
            INSTANCE?.close()
            INSTANCE = null
        }
    }
}
