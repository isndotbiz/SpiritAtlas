package com.spiritatlas.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.spiritatlas.data.database.converters.SpiritualTypeConverters
import com.spiritatlas.data.database.dao.UserProfileDao
import com.spiritatlas.data.database.entities.UserProfileEntity

/**
 * Room database for SpiritAtlas spiritual profiles
 * Privacy-first: Data encrypted at app level via EncryptedSharedPreferences 🔒✨
 */
@Database(
    entities = [
        UserProfileEntity::class
    ],
    version = 4,
    exportSchema = false // Set to true for production to track schema changes
)
@TypeConverters(SpiritualTypeConverters::class)
abstract class SpiritAtlasDatabase : RoomDatabase() {
    
    abstract fun userProfileDao(): UserProfileDao
    
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
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4) // Schema migrations
            .fallbackToDestructiveMigration() // Only for development
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
        
        /**
         * Close database instance (for testing or app cleanup)
         */
        fun closeDatabase() {
            INSTANCE?.close()
            INSTANCE = null
        }
    }
}
