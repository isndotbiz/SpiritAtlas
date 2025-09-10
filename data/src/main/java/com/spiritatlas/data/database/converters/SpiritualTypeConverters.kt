package com.spiritatlas.data.database.converters

import androidx.room.TypeConverter
import com.spiritatlas.domain.model.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * JsonAdapter for LocalDateTime serialization/deserialization in Moshi
 * Centralized adapter to prevent duplicates
 */
class LocalDateTimeJsonAdapter : JsonAdapter<LocalDateTime>() {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    
    override fun fromJson(reader: JsonReader): LocalDateTime? {
        return if (reader.peek() != JsonReader.Token.NULL) {
            LocalDateTime.parse(reader.nextString(), formatter)
        } else {
            reader.nextNull()
        }
    }
    
    override fun toJson(writer: JsonWriter, value: LocalDateTime?) {
        if (value != null) {
            writer.value(value.format(formatter))
        } else {
            writer.nullValue()
        }
    }
}

/**
 * Type converters for spiritual data types in Room database
 * Handles serialization/deserialization of complex objects with privacy in mind ✨
 */
class SpiritualTypeConverters {
    
    private val moshi = Moshi.Builder()
        .add(LocalDateTime::class.java, LocalDateTimeJsonAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()
    
    // === DATE TIME CONVERTERS ===
    
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
    
    @TypeConverter
    fun toLocalDateTime(dateTimeString: String?): LocalDateTime? {
        return dateTimeString?.let { 
            LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME) 
        }
    }
    
    // === BIRTH PLACE CONVERTER ===
    
    @TypeConverter
    fun fromBirthPlace(birthPlace: BirthPlace?): String? {
        return birthPlace?.let { moshi.adapter(BirthPlace::class.java).toJson(it) }
    }
    
    @TypeConverter
    fun toBirthPlace(birthPlaceJson: String?): BirthPlace? {
        return birthPlaceJson?.let { 
            moshi.adapter(BirthPlace::class.java).fromJson(it) 
        }
    }
    
    // === ENUM CONVERTERS ===
    
    @TypeConverter
    fun fromGender(gender: Gender?): String? = gender?.name
    
    @TypeConverter
    fun toGender(genderString: String?): Gender? {
        return genderString?.let { enumValueOf<Gender>(it) }
    }
    
    @TypeConverter
    fun fromBloodType(bloodType: BloodType?): String? = bloodType?.name
    
    @TypeConverter
    fun toBloodType(bloodTypeString: String?): BloodType? {
        return bloodTypeString?.let { enumValueOf<BloodType>(it) }
    }
    
    @TypeConverter
    fun fromHand(hand: Hand?): String? = hand?.name
    
    @TypeConverter
    fun toHand(handString: String?): Hand? {
        return handString?.let { enumValueOf<Hand>(it) }
    }
    
    // === LIST CONVERTERS === (Removed LifeEvent and SpiritualEvent converters)
    
    // === PREFERENCES CONVERTER ===
    
    @TypeConverter
    fun fromUserPreferences(preferences: UserPreferences?): String? {
        return preferences?.let { moshi.adapter(UserPreferences::class.java).toJson(it) }
    }
    
    @TypeConverter
    fun toUserPreferences(preferencesJson: String?): UserPreferences? {
        return preferencesJson?.let { 
            moshi.adapter(UserPreferences::class.java).fromJson(it) 
        } ?: UserPreferences()
    }
    
    // === PROFILE COMPLETION CONVERTER ===
    
    @TypeConverter
    fun fromProfileCompletion(completion: ProfileCompletion?): String? {
        return completion?.let { moshi.adapter(ProfileCompletion::class.java).toJson(it) }
    }
    
    @TypeConverter
    fun toProfileCompletion(completionJson: String?): ProfileCompletion? {
        return completionJson?.let { 
            moshi.adapter(ProfileCompletion::class.java).fromJson(it) 
        } ?: ProfileCompletion()
    }
}
