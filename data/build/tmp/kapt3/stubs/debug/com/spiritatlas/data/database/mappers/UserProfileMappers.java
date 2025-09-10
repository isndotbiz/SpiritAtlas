package com.spiritatlas.data.database.mappers;

/**
 * Mappers between domain UserProfile and database UserProfileEntity
 * Handles serialization/privacy with care ✨
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\n\u0010\u0006\u001a\u00020\u0007*\u00020\bJ\n\u0010\t\u001a\u00020\b*\u00020\u0007R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/spiritatlas/data/database/mappers/UserProfileMappers;", "", "()V", "moshi", "Lcom/squareup/moshi/Moshi;", "kotlin.jvm.PlatformType", "toDomain", "Lcom/spiritatlas/domain/model/UserProfile;", "Lcom/spiritatlas/data/database/entities/UserProfileEntity;", "toEntity", "data_debug"})
public final class UserProfileMappers {
    private static final com.squareup.moshi.Moshi moshi = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.spiritatlas.data.database.mappers.UserProfileMappers INSTANCE = null;
    
    private UserProfileMappers() {
        super();
    }
    
    /**
     * Convert domain UserProfile to database entity
     */
    @org.jetbrains.annotations.NotNull()
    public final com.spiritatlas.data.database.entities.UserProfileEntity toEntity(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.domain.model.UserProfile $this$toEntity) {
        return null;
    }
    
    /**
     * Convert database entity to domain UserProfile
     */
    @org.jetbrains.annotations.NotNull()
    public final com.spiritatlas.domain.model.UserProfile toDomain(@org.jetbrains.annotations.NotNull()
    com.spiritatlas.data.database.entities.UserProfileEntity $this$toDomain) {
        return null;
    }
}