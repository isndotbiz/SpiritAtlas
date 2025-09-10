package com.spiritatlas.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.spiritatlas.core.astro.AstroCalculator
import com.spiritatlas.core.humandesign.EnergyProfileCalculator
import com.spiritatlas.core.numerology.ChaldeanCalculator
import com.spiritatlas.core.numerology.PythagoreanCalculator
import com.spiritatlas.domain.ai.AiTextProvider
import com.spiritatlas.domain.ai.EnrichmentContext
import com.spiritatlas.domain.model.UserProfile
import com.spiritatlas.domain.repository.ConsentRepository
import com.spiritatlas.domain.repository.ConsentType
import com.spiritatlas.domain.repository.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class EnrichmentWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val aiProvider: AiTextProvider,
    private val consentRepository: ConsentRepository,
    private val userRepository: UserRepository
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            Log.d("SpiritAtlas", "EnrichmentWorker: Starting enrichment work")
            
            // Check consent first
            val consent = consentRepository.getConsentStatus(ConsentType.AI_ENRICHMENT).first()
            if (consent.name != "GRANTED") {
                Log.w("SpiritAtlas", "EnrichmentWorker: AI enrichment consent not granted")
                return Result.failure()
            }
            
            // Check AI provider availability
            if (!aiProvider.isAvailable()) {
                Log.w("SpiritAtlas", "EnrichmentWorker: AI provider not available")
                return Result.retry()
            }
            
            // Get user profile (specific profile if ID provided, otherwise default)
            val profileId = inputData.getString("PROFILE_ID")
            val userProfile = if (profileId != null) {
                Log.d("SpiritAtlas", "EnrichmentWorker: Loading specific profile: $profileId")
                userRepository.getProfileById(profileId)
            } else {
                Log.d("SpiritAtlas", "EnrichmentWorker: Loading default user profile")
                userRepository.getUserProfile().first()
            }
            
            if (userProfile == null) {
                Log.w("SpiritAtlas", "EnrichmentWorker: No user profile found (profileId=$profileId)")
                return Result.failure()
            }
            
            Log.d("SpiritAtlas", "EnrichmentWorker: User profile loaded: ${userProfile.name}")
            
            // Build enrichment context with calculated data
            val enrichmentContext = buildEnrichmentContext(userProfile)
            Log.d("SpiritAtlas", "EnrichmentWorker: Enrichment context built with ${enrichmentContext.numerology.size} numerology, ${enrichmentContext.astrology.size} astrology, ${enrichmentContext.energyProfile.size} energy fields")
            
            // Generate enrichment using AI
            val result = aiProvider.generateEnrichment(enrichmentContext)
            
            when (result) {
                is com.spiritatlas.core.common.Result.Success -> {
                    Log.i("SpiritAtlas", "EnrichmentWorker: AI enrichment successful - confidence: ${result.data.confidence}")
                    Result.success()
                }
                is com.spiritatlas.core.common.Result.Error -> {
                    Log.e("SpiritAtlas", "EnrichmentWorker: AI enrichment error", result.exception)
                    Result.retry()
                }
                com.spiritatlas.core.common.Result.Loading -> {
                    Log.d("SpiritAtlas", "EnrichmentWorker: AI enrichment still loading")
                    Result.retry()
                }
            }
            
        } catch (e: Exception) {
            Log.e("SpiritAtlas", "EnrichmentWorker: Unexpected error during enrichment work", e)
            Result.failure()
        }
    }
    
    /**
     * Build enrichment context from user profile using spiritual calculation modules
     */
    private fun buildEnrichmentContext(userProfile: UserProfile): EnrichmentContext {
        val numerologyMap = buildNumerologyData(userProfile)
        val astrologyMap = buildAstrologyData(userProfile)
        val energyProfileMap = buildEnergyProfileData(userProfile)
        
        val personal = buildPersonalDetails(userProfile)
        return EnrichmentContext(
            numerology = numerologyMap,
            astrology = astrologyMap,
            energyProfile = energyProfileMap,
            personalDetails = personal,
            completedFields = userProfile.profileCompletion.completedFields,
            totalFields = userProfile.profileCompletion.totalFields,
            accuracyLevel = userProfile.profileCompletion.accuracyLevel.name
        )
    }
    
    private fun buildNumerologyData(profile: UserProfile): Map<String, Any> {
        val numerologyData = mutableMapOf<String, Any>()
        
        try {
            profile.name?.let { name ->
                if (name.isNotBlank()) {
                    numerologyData["chaldeanName"] = ChaldeanCalculator.calculateNameNumber(name)
                    numerologyData["pythagoreanName"] = PythagoreanCalculator.calculateNameNumber(name)
                }
            }
            
            profile.birthDateTime?.let { birthDateTime ->
                val day = birthDateTime.dayOfMonth
                val month = birthDateTime.monthValue
                val year = birthDateTime.year
                
                numerologyData["lifePath"] = PythagoreanCalculator.calculateLifePath(day, month, year)
                numerologyData["birthDay"] = day
                numerologyData["birthMonth"] = month
                numerologyData["birthYear"] = year
            }
            
            // Additional name calculations if available
            profile.displayName?.let { displayName ->
                if (displayName.isNotBlank()) {
                    numerologyData["displayNameChaldean"] = ChaldeanCalculator.calculateNameNumber(displayName)
                    numerologyData["displayNamePythagorean"] = PythagoreanCalculator.calculateNameNumber(displayName)
                }
            }
            
        } catch (e: Exception) {
            Log.w("SpiritAtlas", "Error calculating numerology data", e)
        }
        
        return numerologyData
    }
    
    private fun buildAstrologyData(profile: UserProfile): Map<String, Any> {
        val astrologyData = mutableMapOf<String, Any>()
        
        try {
            profile.birthDateTime?.let { birthDateTime ->
                val year = birthDateTime.year
                
                // Calculate basic astrological data
                val ayanamsa = AstroCalculator.calculateAyanamsa(year)
                astrologyData["ayanamsa"] = ayanamsa
                astrologyData["birthYear"] = year
                
                // For demonstration, calculate sun sign based on approximate tropical longitude
                // In a real implementation, you'd use proper astronomical calculations
                val month = birthDateTime.monthValue
                val day = birthDateTime.dayOfMonth
                val approximateSunLongitude = ((month - 1) * 30.0) + (day * 0.98)
                val siderealSunLongitude = AstroCalculator.tropicalToSidereal(approximateSunLongitude, year)
                val sunPosition = AstroCalculator.getZodiacSign(siderealSunLongitude)
                
                astrologyData["sunSign"] = sunPosition.sign.name
                astrologyData["sunDegree"] = sunPosition.degree
                astrologyData["siderealLongitude"] = siderealSunLongitude
                astrologyData["tropicalLongitude"] = approximateSunLongitude
                
                profile.birthPlace?.let { birthPlace ->
                    astrologyData["birthLatitude"] = birthPlace.latitude
                    astrologyData["birthLongitude"] = birthPlace.longitude
                    astrologyData["birthCity"] = birthPlace.city
                    astrologyData["birthCountry"] = birthPlace.country
                }
            }
            
        } catch (e: Exception) {
            Log.w("SpiritAtlas", "Error calculating astrology data", e)
        }
        
        return astrologyData
    }
    
    private fun buildEnergyProfileData(profile: UserProfile): Map<String, Any> {
        val energyData = mutableMapOf<String, Any>()
        
        try {
            profile.birthDateTime?.let { birthDateTime ->
                val day = birthDateTime.dayOfMonth
                val month = birthDateTime.monthValue
                val year = birthDateTime.year
                
                val energyType = EnergyProfileCalculator.calculateEnergyType(day, month, year)
                val energyProfile = EnergyProfileCalculator.calculateProfile(day, month)
                
                energyData["energyType"] = energyType.name
                energyData["profileNotation"] = energyProfile.notation
                energyData["consciousLine"] = energyProfile.conscious.number
                energyData["unconsciousLine"] = energyProfile.unconscious.number
                energyData["consciousDescription"] = energyProfile.conscious.description
                energyData["unconsciousDescription"] = energyProfile.unconscious.description
            }
            
            // Add additional profile context
            profile.gender?.let { energyData["gender"] = it.name }
            profile.dominantHand?.let { energyData["dominantHand"] = it.name }
            profile.bloodType?.let { energyData["bloodType"] = it.name }
            
        } catch (e: Exception) {
            Log.w("SpiritAtlas", "Error calculating energy profile data", e)
        }
        
        return energyData
    }
    
    private fun buildPersonalDetails(profile: UserProfile): Map<String, Any> {
        val personalData = mutableMapOf<String, Any>()
        
        try {
            // Core identity
            profile.name?.let { personalData["fullName"] = it }
            profile.displayName?.let { personalData["preferredName"] = it }
            profile.birthDateTime?.let { 
                personalData["birthDate"] = it.toLocalDate().toString()
                personalData["birthTime"] = it.toLocalTime().toString()
            }
            profile.birthPlace?.let { place ->
                personalData["birthCity"] = place.city
                personalData["birthCountry"] = place.country
                place.state?.let { personalData["birthState"] = it }
            }
            
            // Names and identity
            profile.middleName?.let { personalData["middleName"] = it }
            profile.nickname?.let { personalData["nickname"] = it }
            profile.spiritualName?.let { personalData["spiritualName"] = it }
            
            // Family background
            profile.motherName?.let { personalData["motherName"] = it }
            profile.fatherName?.let { personalData["fatherName"] = it }
            profile.ancestry?.let { personalData["ancestry"] = it }
            
            // Physical characteristics
            profile.gender?.let { personalData["gender"] = it.name }
            profile.eyeColor?.let { personalData["eyeColor"] = it }
            profile.dominantHand?.let { personalData["dominantHand"] = it.name }
            profile.bloodType?.let { personalData["bloodType"] = it.name }
            
            // Environmental and timing
            profile.weatherConditions?.let { personalData["birthWeather"] = it }
            profile.moonPhase?.let { personalData["moonPhase"] = it }
            profile.hospitalName?.let { personalData["hospitalName"] = it }
            profile.firstWord?.let { personalData["firstWord"] = it }
            
            // Life timing events
            profile.firstBreath?.let { personalData["firstBreath"] = it.toString() }
            profile.firstSteps?.let { personalData["firstSteps"] = it.toString() }
            
        } catch (e: Exception) {
            Log.w("SpiritAtlas", "Error building personal details", e)
        }
        
        return personalData
    }
}


