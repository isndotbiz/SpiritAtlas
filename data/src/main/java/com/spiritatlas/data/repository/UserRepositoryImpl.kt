package com.spiritatlas.data.repository

import com.spiritatlas.data.database.dao.UserProfileDao
import com.spiritatlas.data.database.mappers.UserProfileMappers.toDomain
import com.spiritatlas.data.database.mappers.UserProfileMappers.toEntity
import com.spiritatlas.domain.model.*
import com.spiritatlas.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * UserRepository implementation with Room database backend
 * Privacy-first: All profile data encrypted at rest with intelligent completion tracking ✨
 */
@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao
) : UserRepository {
    
    // Alias for consistent naming
    private val dao = userProfileDao
    
    override suspend fun saveUserProfile(profile: UserProfile) {
        try {
            Log.d("SpiritAtlas", "Starting to save profile: ${profile.id}")
            
            // Calculate profile completion before saving
            val updatedProfile = profile.copy(
                profileCompletion = calculateProfileCompletion(profile)
            )
            
            Log.d("SpiritAtlas", "Profile completion calculated: ${updatedProfile.profileCompletion.completedFields} fields")
            
            val entity = updatedProfile.toEntity()
            Log.d("SpiritAtlas", "Profile converted to entity: ${entity.id}")
            
            userProfileDao.insertOrUpdateProfile(entity)
            Log.d("SpiritAtlas", "Profile saved successfully to database")
            
        } catch (e: Exception) {
            Log.e("SpiritAtlas", "Error saving profile: ${e.message}", e)
            throw e
        }
    }
    
    override fun getUserProfile(): Flow<UserProfile?> {
        return userProfileDao.getUserProfileFlow().map { entity ->
            entity?.let { 
                val profile = it.toDomain()
                profile.copy(profileCompletion = calculateProfileCompletion(profile))
            }
        }
    }
    
    override suspend fun clearUserData() {
        userProfileDao.deleteAllProfiles()
    }
    
    // === Multiple Profile Methods ===
    
    override suspend fun getAllProfiles(): List<UserProfile> {
        return userProfileDao.getAllProfiles().map { entity ->
            val profile = entity.toDomain()
            profile.copy(profileCompletion = calculateProfileCompletion(profile))
        }
    }
    
    override fun getAllProfilesFlow(): Flow<List<UserProfile>> {
        return userProfileDao.getAllProfilesFlow().map { entities ->
            entities.map { entity -> 
                val profile = entity.toDomain()
                profile.copy(profileCompletion = calculateProfileCompletion(profile))
            }
        }
    }
    
    override suspend fun getProfileById(profileId: String): UserProfile? {
        return userProfileDao.getUserProfileById(profileId)?.let { entity ->
            val profile = entity.toDomain()
            profile.copy(profileCompletion = calculateProfileCompletion(profile))
        }
    }
    
    override fun getProfileByIdFlow(profileId: String): Flow<UserProfile?> {
        return userProfileDao.getUserProfileByIdFlow(profileId).map { entity ->
            entity?.let {
                val profile = it.toDomain()
                profile.copy(profileCompletion = calculateProfileCompletion(profile))
            }
        }
    }
    
    override suspend fun deleteProfile(profileId: String) {
        userProfileDao.deleteProfile(profileId)
    }
    
    override suspend fun searchProfiles(query: String): List<UserProfile> {
        return userProfileDao.searchProfiles(query).map { entity ->
            val profile = entity.toDomain()
            profile.copy(profileCompletion = calculateProfileCompletion(profile))
        }
    }
    
    override suspend fun getProfileCount(): Int {
        return dao.getProfileCount()
    }
    
    // === PROFILE SHARING & DISCOVERY ===
    override suspend fun exportProfileForSharing(profileId: String): Result<ShareableProfile> {
        return try {
            val profile = dao.getUserProfileById(profileId)?.toDomain()
                ?: return Result.failure(Exception("Profile not found"))
            
            val shareableProfile = ShareableProfile(
                profileName = profile.profileName,
                basicInfo = ShareableBasicInfo(
                    firstName = profile.name,
                    birthDateTime = profile.birthDateTime,
                    birthPlace = profile.birthPlace,
                    gender = profile.gender,
                    loveLanguage = profile.loveLanguage,
                    personalityType = profile.personalityType,
                    sexualEnergy = profile.sexualEnergy,
                    communicationStyle = profile.communicationStyle
                ),
                enrichmentSummary = profile.enrichmentResult?.take(200), // First 200 chars
                compatibilityReadiness = profile.profileCompletion.completedFields >= 10,
                sharedAt = LocalDateTime.now(),
                sharedBy = "User" // Could be customized
            )
            
            Result.success(shareableProfile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun importSharedProfile(sharedProfile: ShareableProfile): Result<ProfileImportResult> {
        return try {
            val existingProfile = dao.getAllProfiles()
                .find { it.profileName == sharedProfile.profileName }
            
            if (existingProfile != null) {
                return Result.success(
                    ProfileImportResult(
                        success = false,
                        importedProfile = null,
                        errorMessage = "Profile with name '${sharedProfile.profileName}' already exists",
                        isCompatibilityReady = false
                    )
                )
            }
            
            val importedProfile = UserProfile(
                id = UUID.randomUUID().toString(),
                profileName = "${sharedProfile.profileName} (Imported)",
                createdAt = LocalDateTime.now(),
                lastModified = LocalDateTime.now(),
                name = sharedProfile.basicInfo.firstName,
                displayName = sharedProfile.basicInfo.firstName,
                birthDateTime = sharedProfile.basicInfo.birthDateTime,
                birthPlace = sharedProfile.basicInfo.birthPlace,
                gender = sharedProfile.basicInfo.gender,
                loveLanguage = sharedProfile.basicInfo.loveLanguage,
                personalityType = sharedProfile.basicInfo.personalityType,
                sexualEnergy = sharedProfile.basicInfo.sexualEnergy,
                communicationStyle = sharedProfile.basicInfo.communicationStyle
            )
            
            val profileWithCompletion = importedProfile.copy(
                profileCompletion = calculateProfileCompletion(importedProfile)
            )
            
            dao.insertOrUpdateProfile(profileWithCompletion.toEntity())
            
            Result.success(
                ProfileImportResult(
                    success = true,
                    importedProfile = profileWithCompletion,
                    errorMessage = null,
                    isCompatibilityReady = sharedProfile.compatibilityReadiness
                )
            )
        } catch (e: Exception) {
            Result.success(
                ProfileImportResult(
                    success = false,
                    importedProfile = null,
                    errorMessage = "Failed to import: ${e.message}",
                    isCompatibilityReady = false
                )
            )
        }
    }
    
    override suspend fun getProfileLibraryEntries(): List<ProfileLibraryEntry> {
        return try {
            dao.getAllProfiles().map { entity ->
                val profile = entity.toDomain()
                ProfileLibraryEntry(
                    id = profile.id,
                    profileName = profile.profileName,
                    displayName = profile.displayName,
                    thumbnailEmoji = when {
                        profile.gender == Gender.FEMININE -> "👩"
                        profile.gender == Gender.MASCULINE -> "👨"
                        else -> "✨"
                    },
                    completionLevel = profile.profileCompletion.accuracyLevel,
                    hasEnrichment = profile.enrichmentResult != null,
                    isOwn = true, // All profiles are owned by user in this implementation
                    isImported = profile.profileName.contains("(Imported)"),
                    lastUpdated = profile.lastModified,
                    tags = emptyList() // Could be extended
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    override suspend fun searchProfileLibrary(query: String): List<ProfileLibraryEntry> {
        return getProfileLibraryEntries().filter { entry ->
            entry.profileName.contains(query, ignoreCase = true) ||
            entry.displayName?.contains(query, ignoreCase = true) == true
        }
    }
    
    override suspend fun addProfileTags(profileId: String, tags: List<String>) {
        // Implementation could store tags in a separate table or as JSON
        // For now, this is a placeholder
    }
    
    override suspend fun getProfilesByTag(tag: String): List<ProfileLibraryEntry> {
        // Implementation would filter by tag
        // For now, return all profiles
        return getProfileLibraryEntries()
    }
    
    /**
     * Calculate profile completion metrics based on filled fields (streamlined model)
     * This determines accuracy levels for spiritual calculations 🔮
     */
    private fun calculateProfileCompletion(profile: UserProfile): ProfileCompletion {
        val totalFields = 27 // Total possible fields in streamlined profile
        var completedFields = 0
        val missingCriticalFields = mutableListOf<String>()
        
        // Profile metadata (always present for new profiles)
        completedFields++ // profileName
        completedFields++ // id
        completedFields++ // createdAt
        completedFields++ // lastModified
        
        // Core identity fields
        profile.name?.let { if (it.isNotBlank()) completedFields++ } ?: missingCriticalFields.add("name")
        profile.displayName?.let { if (it.isNotBlank()) completedFields++ }
        profile.birthDateTime?.let { completedFields++ } ?: missingCriticalFields.add("birthDateTime")
        profile.birthPlace?.let { completedFields++ } ?: missingCriticalFields.add("birthPlace")
        
        // Additional names (3 fields - streamlined)
        profile.middleName?.let { if (it.isNotBlank()) completedFields++ }
        profile.nickname?.let { if (it.isNotBlank()) completedFields++ }
        profile.spiritualName?.let { if (it.isNotBlank()) completedFields++ }
        
        // Family & ancestry (3 fields - streamlined)
        profile.motherName?.let { if (it.isNotBlank()) completedFields++ }
        profile.fatherName?.let { if (it.isNotBlank()) completedFields++ }
        profile.ancestry?.let { if (it.isNotBlank()) completedFields++ }
        
        // Physical & energetic (4 fields - streamlined)
        profile.gender?.let { completedFields++ }
        profile.bloodType?.let { completedFields++ }
        profile.dominantHand?.let { completedFields++ }
        profile.eyeColor?.let { if (it.isNotBlank()) completedFields++ }
        
        // Key timing (1 field - streamlined)
        profile.firstBreath?.let { completedFields++ }
        
        // Environmental (3 fields - streamlined)
        profile.weatherConditions?.let { if (it.isNotBlank()) completedFields++ }
        profile.moonPhase?.let { if (it.isNotBlank()) completedFields++ }
        profile.hospitalName?.let { if (it.isNotBlank()) completedFields++ }
        
        // Life patterns (2 fields)
        profile.firstWord?.let { if (it.isNotBlank()) completedFields++ }
        profile.firstSteps?.let { completedFields++ }
        
        // Calculate completion percentage
        val completionPercentage = (completedFields.toDouble() / totalFields) * 100.0
        
        // Determine accuracy level based on field count (adjusted for streamlined model)
        val accuracyLevel = when {
            completedFields < 3 -> AccuracyLevel.MINIMAL
            completedFields < 8 -> AccuracyLevel.BASIC
            completedFields < 16 -> AccuracyLevel.GOOD
            completedFields < 24 -> AccuracyLevel.EXCELLENT
            else -> AccuracyLevel.MAXIMUM
        }
        
        return ProfileCompletion(
            totalFields = totalFields,
            completedFields = completedFields,
            completionPercentage = completionPercentage,
            accuracyLevel = accuracyLevel,
            missingCriticalFields = missingCriticalFields
        )
    }

    // === BULK EXPORT/IMPORT IMPLEMENTATION ===

    override suspend fun exportAllProfilesToJson(): Result<String> {
        return try {
            val profiles = getAllProfiles()
            val jsonArray = JSONArray()

            profiles.forEach { profile ->
                val profileJson = JSONObject().apply {
                    put("id", profile.id)
                    put("profileName", profile.profileName)
                    put("name", profile.name)
                    put("displayName", profile.displayName)

                    // Birth info
                    profile.birthDateTime?.let { put("birthDateTime", it.toString()) }
                    profile.birthPlace?.let { place ->
                        val birthPlaceJson = JSONObject().apply {
                            put("city", place.city)
                            put("state", place.state)
                            put("country", place.country)
                            put("latitude", place.latitude)
                            put("longitude", place.longitude)
                            put("timezone", place.timezone)
                            put("altitude", place.altitude)
                            put("nearestSacredSite", place.nearestSacredSite)
                        }
                        put("birthPlace", birthPlaceJson)
                    }

                    // Additional names
                    put("middleName", profile.middleName)
                    put("nickname", profile.nickname)
                    put("spiritualName", profile.spiritualName)
                    put("maidenName", profile.maidenName)

                    // Family
                    put("motherName", profile.motherName)
                    put("fatherName", profile.fatherName)
                    put("ancestry", profile.ancestry)
                    put("familyTradition", profile.familyTradition)

                    // Physical attributes
                    profile.gender?.let { put("gender", it.name) }
                    profile.bloodType?.let { put("bloodType", it.name) }
                    profile.dominantHand?.let { put("dominantHand", it.name) }
                    put("eyeColor", profile.eyeColor)
                    profile.height?.let { put("height", it) }
                    profile.birthWeight?.let { put("birthWeight", it) }

                    // Timing & cycles
                    profile.firstBreath?.let { put("firstBreath", it.toString()) }
                    profile.conceptionDate?.let { put("conceptionDate", it.toString()) }
                    profile.firstCry?.let { put("firstCry", it.toString()) }

                    // Environmental
                    put("weatherConditions", profile.weatherConditions)
                    put("moonPhase", profile.moonPhase)
                    put("hospitalName", profile.hospitalName)
                    put("seasonalEnergy", profile.seasonalEnergy)

                    // Life patterns
                    put("firstWord", profile.firstWord)
                    profile.firstSteps?.let { put("firstSteps", it.toString()) }

                    // Compatibility fields
                    profile.loveLanguage?.let { put("loveLanguage", it.name) }
                    profile.personalityType?.let { put("personalityType", it.name) }
                    profile.attachmentStyle?.let { put("attachmentStyle", it.name) }
                    profile.sexualEnergy?.let { put("sexualEnergy", it.name) }
                    profile.communicationStyle?.let { put("communicationStyle", it.name) }
                    profile.conflictResolution?.let { put("conflictResolution", it.name) }
                    profile.intimacyPreference?.let { put("intimacyPreference", it.name) }
                    profile.spiritualConnection?.let { put("spiritualConnection", it.name) }
                    put("lifePurposeAlignment", profile.lifePurposeAlignment)

                    // Enrichment
                    put("enrichmentResult", profile.enrichmentResult)
                    profile.enrichmentGeneratedAt?.let { put("enrichmentGeneratedAt", it.toString()) }

                    // Metadata
                    put("createdAt", profile.createdAt.toString())
                    put("lastModified", profile.lastModified.toString())
                }
                jsonArray.put(profileJson)
            }

            val exportData = JSONObject().apply {
                put("version", "1.0")
                put("exportedAt", LocalDateTime.now().toString())
                put("profileCount", profiles.size)
                put("profiles", jsonArray)
            }

            Result.success(exportData.toString(2)) // Pretty print with indent
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "Error exporting profiles", e)
            Result.failure(e)
        }
    }

    override suspend fun importProfilesFromJson(json: String): Result<Int> {
        return try {
            val exportData = JSONObject(json)
            val profilesArray = exportData.getJSONArray("profiles")
            var importedCount = 0

            for (i in 0 until profilesArray.length()) {
                val profileJson = profilesArray.getJSONObject(i)

                try {
                    val birthPlace = if (profileJson.has("birthPlace")) {
                        val bpJson = profileJson.getJSONObject("birthPlace")
                        BirthPlace(
                            city = bpJson.getString("city"),
                            state = bpJson.optString("state", null),
                            country = bpJson.getString("country"),
                            latitude = bpJson.getDouble("latitude"),
                            longitude = bpJson.getDouble("longitude"),
                            timezone = bpJson.optString("timezone", null),
                            altitude = if (bpJson.has("altitude")) bpJson.optDouble("altitude") else null,
                            nearestSacredSite = bpJson.optString("nearestSacredSite", null)
                        )
                    } else null

                    val profile = UserProfile(
                        id = profileJson.optString("id", UUID.randomUUID().toString()),
                        profileName = profileJson.optString("profileName", "Imported Profile"),
                        name = profileJson.optString("name", null),
                        displayName = profileJson.optString("displayName", null),

                        // Birth info
                        birthDateTime = profileJson.optString("birthDateTime", null)?.let {
                            runCatching { LocalDateTime.parse(it) }.getOrNull()
                        },
                        birthPlace = birthPlace,

                        // Additional names
                        middleName = profileJson.optString("middleName", null),
                        nickname = profileJson.optString("nickname", null),
                        spiritualName = profileJson.optString("spiritualName", null),
                        maidenName = profileJson.optString("maidenName", null),

                        // Family
                        motherName = profileJson.optString("motherName", null),
                        fatherName = profileJson.optString("fatherName", null),
                        ancestry = profileJson.optString("ancestry", null),
                        familyTradition = profileJson.optString("familyTradition", null),

                        // Physical attributes
                        gender = profileJson.optString("gender", null)?.let {
                            runCatching { Gender.valueOf(it) }.getOrNull()
                        },
                        bloodType = profileJson.optString("bloodType", null)?.let {
                            runCatching { BloodType.valueOf(it) }.getOrNull()
                        },
                        dominantHand = profileJson.optString("dominantHand", null)?.let {
                            runCatching { Hand.valueOf(it) }.getOrNull()
                        },
                        eyeColor = profileJson.optString("eyeColor", null),
                        height = if (profileJson.has("height")) profileJson.optDouble("height") else null,
                        birthWeight = if (profileJson.has("birthWeight")) profileJson.optDouble("birthWeight") else null,

                        // Timing & cycles
                        firstBreath = profileJson.optString("firstBreath", null)?.let {
                            runCatching { LocalDateTime.parse(it) }.getOrNull()
                        },
                        conceptionDate = profileJson.optString("conceptionDate", null)?.let {
                            runCatching { LocalDateTime.parse(it) }.getOrNull()
                        },
                        firstCry = profileJson.optString("firstCry", null)?.let {
                            runCatching { LocalDateTime.parse(it) }.getOrNull()
                        },

                        // Environmental
                        weatherConditions = profileJson.optString("weatherConditions", null),
                        moonPhase = profileJson.optString("moonPhase", null),
                        hospitalName = profileJson.optString("hospitalName", null),
                        seasonalEnergy = profileJson.optString("seasonalEnergy", null),

                        // Life patterns
                        firstWord = profileJson.optString("firstWord", null),
                        firstSteps = profileJson.optString("firstSteps", null)?.let {
                            runCatching { LocalDateTime.parse(it) }.getOrNull()
                        },

                        // Compatibility fields
                        loveLanguage = profileJson.optString("loveLanguage", null)?.let {
                            runCatching { LoveLanguage.valueOf(it) }.getOrNull()
                        },
                        personalityType = profileJson.optString("personalityType", null)?.let {
                            runCatching { PersonalityType.valueOf(it) }.getOrNull()
                        },
                        attachmentStyle = profileJson.optString("attachmentStyle", null)?.let {
                            runCatching { AttachmentStyle.valueOf(it) }.getOrNull()
                        },
                        sexualEnergy = profileJson.optString("sexualEnergy", null)?.let {
                            runCatching { SexualEnergy.valueOf(it) }.getOrNull()
                        },
                        communicationStyle = profileJson.optString("communicationStyle", null)?.let {
                            runCatching { CommunicationStyle.valueOf(it) }.getOrNull()
                        },
                        conflictResolution = profileJson.optString("conflictResolution", null)?.let {
                            runCatching { ConflictStyle.valueOf(it) }.getOrNull()
                        },
                        intimacyPreference = profileJson.optString("intimacyPreference", null)?.let {
                            runCatching { IntimacyStyle.valueOf(it) }.getOrNull()
                        },
                        spiritualConnection = profileJson.optString("spiritualConnection", null)?.let {
                            runCatching { SpiritualConnection.valueOf(it) }.getOrNull()
                        },
                        lifePurposeAlignment = profileJson.optString("lifePurposeAlignment", null),

                        // Enrichment
                        enrichmentResult = profileJson.optString("enrichmentResult", null),
                        enrichmentGeneratedAt = profileJson.optString("enrichmentGeneratedAt", null)?.let {
                            runCatching { LocalDateTime.parse(it) }.getOrNull()
                        },

                        // Metadata - preserve original timestamps if available
                        createdAt = profileJson.optString("createdAt", null)?.let {
                            runCatching { LocalDateTime.parse(it) }.getOrNull()
                        } ?: LocalDateTime.now(),
                        lastModified = LocalDateTime.now(),

                        // Profile completion will be calculated on save
                        profileCompletion = ProfileCompletion(0, 0, 0.0, AccuracyLevel.BASIC, emptyList())
                    )

                    saveUserProfile(profile)
                    importedCount++
                } catch (e: Exception) {
                    Log.e("UserRepositoryImpl", "Error importing profile at index $i", e)
                    // Continue with next profile
                }
            }

            Log.d("UserRepositoryImpl", "Successfully imported $importedCount profiles")
            Result.success(importedCount)
        } catch (e: Exception) {
            Log.e("UserRepositoryImpl", "Error importing profiles from JSON", e)
            Result.failure(e)
        }
    }
}
