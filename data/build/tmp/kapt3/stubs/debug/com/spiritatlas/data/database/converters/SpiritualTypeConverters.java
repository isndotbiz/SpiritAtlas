package com.spiritatlas.data.database.converters;

/**
 * Type converters for spiritual data types in Room database
 * Handles serialization/deserialization of complex objects with privacy in mind ✨
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0007J\u0014\u0010\n\u001a\u0004\u0018\u00010\u00072\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007J\u0014\u0010\r\u001a\u0004\u0018\u00010\u00072\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0007J\u0014\u0010\u0010\u001a\u0004\u0018\u00010\u00072\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0007J\u0014\u0010\u0013\u001a\u0004\u0018\u00010\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0007J\u0014\u0010\u0016\u001a\u0004\u0018\u00010\u00072\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0007J\u0014\u0010\u0019\u001a\u0004\u0018\u00010\u00072\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0007J\u0014\u0010\u001c\u001a\u0004\u0018\u00010\t2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0007H\u0007J\u0014\u0010\u001e\u001a\u0004\u0018\u00010\f2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0007H\u0007J\u0014\u0010 \u001a\u0004\u0018\u00010\u000f2\b\u0010!\u001a\u0004\u0018\u00010\u0007H\u0007J\u0014\u0010\"\u001a\u0004\u0018\u00010\u00122\b\u0010#\u001a\u0004\u0018\u00010\u0007H\u0007J\u0014\u0010$\u001a\u0004\u0018\u00010\u00152\b\u0010%\u001a\u0004\u0018\u00010\u0007H\u0007J\u0014\u0010&\u001a\u0004\u0018\u00010\u00182\b\u0010\'\u001a\u0004\u0018\u00010\u0007H\u0007J\u0014\u0010(\u001a\u0004\u0018\u00010\u001b2\b\u0010)\u001a\u0004\u0018\u00010\u0007H\u0007R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lcom/spiritatlas/data/database/converters/SpiritualTypeConverters;", "", "()V", "moshi", "Lcom/squareup/moshi/Moshi;", "kotlin.jvm.PlatformType", "fromBirthPlace", "", "birthPlace", "Lcom/spiritatlas/domain/model/BirthPlace;", "fromBloodType", "bloodType", "Lcom/spiritatlas/domain/model/BloodType;", "fromGender", "gender", "Lcom/spiritatlas/domain/model/Gender;", "fromHand", "hand", "Lcom/spiritatlas/domain/model/Hand;", "fromLocalDateTime", "dateTime", "Ljava/time/LocalDateTime;", "fromProfileCompletion", "completion", "Lcom/spiritatlas/domain/model/ProfileCompletion;", "fromUserPreferences", "preferences", "Lcom/spiritatlas/domain/model/UserPreferences;", "toBirthPlace", "birthPlaceJson", "toBloodType", "bloodTypeString", "toGender", "genderString", "toHand", "handString", "toLocalDateTime", "dateTimeString", "toProfileCompletion", "completionJson", "toUserPreferences", "preferencesJson", "data_debug"})
public final class SpiritualTypeConverters {
    private final com.squareup.moshi.Moshi moshi = null;
    
    public SpiritualTypeConverters() {
        super();
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String fromLocalDateTime(@org.jetbrains.annotations.Nullable()
    java.time.LocalDateTime dateTime) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.time.LocalDateTime toLocalDateTime(@org.jetbrains.annotations.Nullable()
    java.lang.String dateTimeString) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String fromBirthPlace(@org.jetbrains.annotations.Nullable()
    com.spiritatlas.domain.model.BirthPlace birthPlace) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final com.spiritatlas.domain.model.BirthPlace toBirthPlace(@org.jetbrains.annotations.Nullable()
    java.lang.String birthPlaceJson) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String fromGender(@org.jetbrains.annotations.Nullable()
    com.spiritatlas.domain.model.Gender gender) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final com.spiritatlas.domain.model.Gender toGender(@org.jetbrains.annotations.Nullable()
    java.lang.String genderString) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String fromBloodType(@org.jetbrains.annotations.Nullable()
    com.spiritatlas.domain.model.BloodType bloodType) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final com.spiritatlas.domain.model.BloodType toBloodType(@org.jetbrains.annotations.Nullable()
    java.lang.String bloodTypeString) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String fromHand(@org.jetbrains.annotations.Nullable()
    com.spiritatlas.domain.model.Hand hand) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final com.spiritatlas.domain.model.Hand toHand(@org.jetbrains.annotations.Nullable()
    java.lang.String handString) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String fromUserPreferences(@org.jetbrains.annotations.Nullable()
    com.spiritatlas.domain.model.UserPreferences preferences) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final com.spiritatlas.domain.model.UserPreferences toUserPreferences(@org.jetbrains.annotations.Nullable()
    java.lang.String preferencesJson) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String fromProfileCompletion(@org.jetbrains.annotations.Nullable()
    com.spiritatlas.domain.model.ProfileCompletion completion) {
        return null;
    }
    
    @androidx.room.TypeConverter()
    @org.jetbrains.annotations.Nullable()
    public final com.spiritatlas.domain.model.ProfileCompletion toProfileCompletion(@org.jetbrains.annotations.Nullable()
    java.lang.String completionJson) {
        return null;
    }
}