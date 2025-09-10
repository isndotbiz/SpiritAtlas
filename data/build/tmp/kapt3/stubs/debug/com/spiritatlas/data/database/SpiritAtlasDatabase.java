package com.spiritatlas.data.database;

/**
 * Room database for SpiritAtlas spiritual profiles
 * Privacy-first: Data encrypted at app level via EncryptedSharedPreferences 🔒✨
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00052\u00020\u0001:\u0001\u0005B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&\u00a8\u0006\u0006"}, d2 = {"Lcom/spiritatlas/data/database/SpiritAtlasDatabase;", "Landroidx/room/RoomDatabase;", "()V", "userProfileDao", "Lcom/spiritatlas/data/database/dao/UserProfileDao;", "Companion", "data_debug"})
@androidx.room.Database(entities = {com.spiritatlas.data.database.entities.UserProfileEntity.class}, version = 4, exportSchema = false)
@androidx.room.TypeConverters(value = {com.spiritatlas.data.database.converters.SpiritualTypeConverters.class})
public abstract class SpiritAtlasDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String DATABASE_NAME = "spirit_atlas.db";
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.spiritatlas.data.database.SpiritAtlasDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.room.migration.Migration MIGRATION_1_2 = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.room.migration.Migration MIGRATION_2_3 = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.room.migration.Migration MIGRATION_3_4 = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.spiritatlas.data.database.SpiritAtlasDatabase.Companion Companion = null;
    
    public SpiritAtlasDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.spiritatlas.data.database.dao.UserProfileDao userProfileDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0002J\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/spiritatlas/data/database/SpiritAtlasDatabase$Companion;", "", "()V", "DATABASE_NAME", "", "INSTANCE", "Lcom/spiritatlas/data/database/SpiritAtlasDatabase;", "MIGRATION_1_2", "Landroidx/room/migration/Migration;", "MIGRATION_2_3", "MIGRATION_3_4", "buildDatabase", "context", "Landroid/content/Context;", "closeDatabase", "", "getInstance", "data_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.spiritatlas.data.database.SpiritAtlasDatabase getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
        
        private final com.spiritatlas.data.database.SpiritAtlasDatabase buildDatabase(android.content.Context context) {
            return null;
        }
        
        /**
         * Close database instance (for testing or app cleanup)
         */
        public final void closeDatabase() {
        }
    }
}