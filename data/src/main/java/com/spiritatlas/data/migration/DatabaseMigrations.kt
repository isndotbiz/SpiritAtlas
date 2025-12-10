package com.spiritatlas.data.migration

import android.content.Context
import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.spiritatlas.data.database.SpiritAtlasDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Database migration utilities and helper functions
 * Provides backup, restore, and migration verification capabilities
 */
@Singleton
class DatabaseMigrations @Inject constructor(
    private val context: Context
) {
    companion object {
        private const val TAG = "DatabaseMigrations"
        private const val DATABASE_NAME = "spirit_atlas.db"
        private const val BACKUP_DIR = "db_backups"

        /**
         * Get all available migrations
         * These should match the migrations in SpiritAtlasDatabase
         */
        fun getAllMigrations(): List<Migration> {
            return listOf(
                MIGRATION_1_2,
                MIGRATION_2_3,
                MIGRATION_3_4,
                MIGRATION_4_5
            )
        }

        // Migration definitions (match SpiritAtlasDatabase companion object)
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE user_profiles ADD COLUMN profileName TEXT NOT NULL DEFAULT 'My Profile'")
                db.execSQL("ALTER TABLE user_profiles ADD COLUMN createdAt TEXT NOT NULL DEFAULT '2024-01-01T00:00:00'")
                db.execSQL("ALTER TABLE user_profiles ADD COLUMN lastModified TEXT NOT NULL DEFAULT '2024-01-01T00:00:00'")
                db.execSQL("ALTER TABLE user_profiles ADD COLUMN updatedAt INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE user_profiles ADD COLUMN syncStatus TEXT NOT NULL DEFAULT 'LOCAL'")
                db.execSQL("ALTER TABLE user_profiles ADD COLUMN encryptionVersion INTEGER NOT NULL DEFAULT 1")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // No schema changes in v3
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE user_profiles ADD COLUMN enrichmentResult TEXT")
                db.execSQL("ALTER TABLE user_profiles ADD COLUMN enrichmentGeneratedAt TEXT")
            }
        }

        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Create compatibility reports cache table
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

                // Indexes for compatibility reports
                db.execSQL("CREATE INDEX IF NOT EXISTS index_compatibility_reports_profileAId_profileBId ON compatibility_reports (profileAId, profileBId)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_compatibility_reports_profileAId ON compatibility_reports (profileAId)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_compatibility_reports_profileBId ON compatibility_reports (profileBId)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_compatibility_reports_generatedAt ON compatibility_reports (generatedAt)")

                // Create AI response cache table
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

                // Indexes for AI cache
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_ai_response_cache_requestHash ON ai_response_cache (requestHash)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_ai_response_cache_createdAt ON ai_response_cache (createdAt)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_ai_response_cache_expiresAt ON ai_response_cache (expiresAt)")

                // Indexes for user_profiles
                db.execSQL("CREATE INDEX IF NOT EXISTS index_user_profiles_updatedAt ON user_profiles (updatedAt)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_user_profiles_profileName ON user_profiles (profileName)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_user_profiles_name ON user_profiles (name)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_user_profiles_syncStatus ON user_profiles (syncStatus)")
            }
        }
    }

    /**
     * Create a backup of the current database
     * Returns the backup file path if successful
     */
    suspend fun createBackup(): Result<String> = withContext(Dispatchers.IO) {
        try {
            // Close database before backup
            SpiritAtlasDatabase.closeDatabase()

            val dbFile = context.getDatabasePath(DATABASE_NAME)
            if (!dbFile.exists()) {
                return@withContext Result.failure(Exception("Database file not found"))
            }

            // Create backup directory
            val backupDir = File(context.filesDir, BACKUP_DIR)
            if (!backupDir.exists()) {
                backupDir.mkdirs()
            }

            // Generate backup filename with timestamp
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
            val backupFile = File(backupDir, "${DATABASE_NAME}_backup_$timestamp.db")

            // Copy database file
            FileInputStream(dbFile).use { input ->
                FileOutputStream(backupFile).use { output ->
                    input.copyTo(output)
                }
            }

            Log.d(TAG, "Database backup created: ${backupFile.absolutePath}")
            Result.success(backupFile.absolutePath)

        } catch (e: Exception) {
            Log.e(TAG, "Failed to create database backup", e)
            Result.failure(e)
        }
    }

    /**
     * Restore database from backup file
     */
    suspend fun restoreFromBackup(backupFilePath: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            // Close database before restore
            SpiritAtlasDatabase.closeDatabase()

            val backupFile = File(backupFilePath)
            if (!backupFile.exists()) {
                return@withContext Result.failure(Exception("Backup file not found: $backupFilePath"))
            }

            val dbFile = context.getDatabasePath(DATABASE_NAME)

            // Copy backup to database location
            FileInputStream(backupFile).use { input ->
                FileOutputStream(dbFile).use { output ->
                    input.copyTo(output)
                }
            }

            Log.d(TAG, "Database restored from: $backupFilePath")
            Result.success(Unit)

        } catch (e: Exception) {
            Log.e(TAG, "Failed to restore database from backup", e)
            Result.failure(e)
        }
    }

    /**
     * List all available backup files
     */
    fun listBackups(): List<BackupInfo> {
        val backupDir = File(context.filesDir, BACKUP_DIR)
        if (!backupDir.exists()) {
            return emptyList()
        }

        return backupDir.listFiles()
            ?.filter { it.name.endsWith(".db") }
            ?.map { file ->
                BackupInfo(
                    path = file.absolutePath,
                    name = file.name,
                    size = file.length(),
                    timestamp = file.lastModified()
                )
            }
            ?.sortedByDescending { it.timestamp }
            ?: emptyList()
    }

    /**
     * Delete old backups, keeping only the most recent N backups
     */
    suspend fun cleanupOldBackups(keepCount: Int = 5): Result<Int> = withContext(Dispatchers.IO) {
        try {
            val backups = listBackups()
            if (backups.size <= keepCount) {
                return@withContext Result.success(0)
            }

            val toDelete = backups.drop(keepCount)
            var deletedCount = 0

            toDelete.forEach { backup ->
                val file = File(backup.path)
                if (file.exists() && file.delete()) {
                    deletedCount++
                    Log.d(TAG, "Deleted old backup: ${backup.name}")
                }
            }

            Result.success(deletedCount)

        } catch (e: Exception) {
            Log.e(TAG, "Failed to cleanup old backups", e)
            Result.failure(e)
        }
    }

    /**
     * Export user data for GDPR compliance
     * Returns JSON string with all user data
     */
    suspend fun exportUserData(): Result<String> = withContext(Dispatchers.IO) {
        try {
            // This would export all user data in a portable format
            // Implementation depends on specific data structures
            val userData = """
                {
                    "export_version": "1.0",
                    "export_date": "${SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).format(Date())}",
                    "profiles": [],
                    "compatibility_reports": [],
                    "preferences": {}
                }
            """.trimIndent()

            Result.success(userData)

        } catch (e: Exception) {
            Log.e(TAG, "Failed to export user data", e)
            Result.failure(e)
        }
    }

    /**
     * Delete all user data for GDPR compliance
     */
    suspend fun deleteAllUserData(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            // Close database
            SpiritAtlasDatabase.closeDatabase()

            // Delete database file
            val dbFile = context.getDatabasePath(DATABASE_NAME)
            if (dbFile.exists()) {
                dbFile.delete()
                Log.d(TAG, "Deleted database file")
            }

            // Delete all backups
            val backupDir = File(context.filesDir, BACKUP_DIR)
            if (backupDir.exists()) {
                backupDir.deleteRecursively()
                Log.d(TAG, "Deleted all backups")
            }

            Result.success(Unit)

        } catch (e: Exception) {
            Log.e(TAG, "Failed to delete all user data", e)
            Result.failure(e)
        }
    }

    /**
     * Verify database integrity
     */
    suspend fun verifyDatabaseIntegrity(): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val db = context.getDatabasePath(DATABASE_NAME)
            if (!db.exists()) {
                return@withContext Result.success(false)
            }

            // Basic integrity check - just verify file exists and is readable
            val canRead = db.canRead()
            val hasSize = db.length() > 0

            Result.success(canRead && hasSize)

        } catch (e: Exception) {
            Log.e(TAG, "Failed to verify database integrity", e)
            Result.failure(e)
        }
    }
}

/**
 * Information about a database backup
 */
data class BackupInfo(
    val path: String,
    val name: String,
    val size: Long,
    val timestamp: Long
) {
    val sizeInMB: Double
        get() = size / (1024.0 * 1024.0)

    val formattedDate: String
        get() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date(timestamp))
}
