package com.spiritatlas.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Enhanced user profile with comprehensive spiritual and personal data
 */
data class EnhancedUserProfile(
    val id: String,
    val name: String,
    val birthDate: LocalDate,
    val birthTime: String? = null,
    val birthLocation: String? = null,
    
    // Parents' information for generational insights
    val motherBirthDate: LocalDate? = null,
    val motherBirthTime: String? = null,
    val motherBirthLocation: String? = null,
    
    val fatherBirthDate: LocalDate? = null,
    val fatherBirthTime: String? = null,
    val fatherBirthLocation: String? = null,
    
    // Relationship milestones
    val firstKissDate: LocalDate? = null,
    val relationshipMilestones: Map<String, LocalDate> = emptyMap(),
    
    // Ayurveda constitution
    val doshaProfile: DoshaProfile? = null,
    val ayurvedaAnswers: Map<String, Int> = emptyMap(),
    
    // Existing spiritual calculations
    val numerologyProfile: NumerologyProfile? = null,
    val astroProfile: AstroProfile? = null,
    val energyProfile: EnergyProfile? = null,
    
    // Metadata
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val profileCompletion: ProfileCompletion = ProfileCompletion()
) {
    val hasParentalData: Boolean
        get() = motherBirthDate != null || fatherBirthDate != null
        
    val hasRelationshipData: Boolean
        get() = firstKissDate != null || relationshipMilestones.isNotEmpty()
        
    val hasAyurvedaData: Boolean
        get() = doshaProfile != null
        
    val comprehensiveScore: Float
        get() {
            var score = 0f
            var maxScore = 0f
            
            // Basic data (40% weight)
            maxScore += 4f
            if (name.isNotBlank()) score += 1f
            if (birthTime != null) score += 1f
            if (birthLocation != null) score += 1f
            if (profileCompletion.accuracyLevel != AccuracyLevel.BASIC) score += 1f
            
            // Parental data (20% weight)
            maxScore += 2f
            if (motherBirthDate != null) score += 1f
            if (fatherBirthDate != null) score += 1f
            
            // Relationship data (20% weight)
            maxScore += 2f
            if (firstKissDate != null) score += 1f
            if (relationshipMilestones.isNotEmpty()) score += 1f
            
            // Ayurveda data (20% weight)
            maxScore += 2f
            if (doshaProfile != null) score += 2f
            
            return if (maxScore > 0f) score / maxScore else 0f
        }
}

data class DoshaProfile(
    val vata: Int,
    val pitta: Int,
    val kapha: Int
) {
    val dominant: Dosha
        get() = when {
            vata >= pitta && vata >= kapha -> Dosha.VATA
            pitta >= vata && pitta >= kapha -> Dosha.PITTA
            else -> Dosha.KAPHA
        }
    
    val dominantPercentage: Float
        get() {
            val total = vata + pitta + kapha
            return if (total > 0) {
                when (dominant) {
                    Dosha.VATA -> vata.toFloat() / total
                    Dosha.PITTA -> pitta.toFloat() / total
                    Dosha.KAPHA -> kapha.toFloat() / total
                }
            } else 0f
        }
}

enum class Dosha(val displayName: String, val description: String, val element: String) {
    VATA("Vata", "Movement and communication", "Air & Space"),
    PITTA("Pitta", "Transformation and metabolism", "Fire & Water"),
    KAPHA("Kapha", "Structure and lubrication", "Earth & Water")
}
