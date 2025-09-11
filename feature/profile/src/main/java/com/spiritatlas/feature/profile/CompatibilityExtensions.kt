package com.spiritatlas.feature.profile

import com.spiritatlas.domain.service.DetailedCouplesReport

/**
 * Extension properties to provide backward compatibility for UI
 * Maps the domain model structure to the expected UI property names
 */

// Overall compatibility score from the detailed scores
val DetailedCouplesReport.overallCompatibility: Double
    get() = compatibilityScores.overallCompatibility

// Simplified compatibility breakdown structure
val DetailedCouplesReport.compatibilityBreakdown: CompatibilityBreakdown
    get() = CompatibilityBreakdown(
        spiritual = (compatibilityScores.numerologyCompatibility.overallScore + 
                    compatibilityScores.astrologyCompatibility.overallScore + 
                    compatibilityScores.chakraCompatibility.overallScore) / 3.0,
        emotional = compatibilityScores.emotionalCompatibility.overallScore,
        physical = (compatibilityScores.intimacyCompatibility.overallScore + 
                   compatibilityScores.sexualEnergyCompatibility.overallScore) / 2.0
    )

// Alias for UI consistency
val DetailedCouplesReport.strengthsAnalysis: List<com.spiritatlas.domain.service.CouplesStrength>
    get() = strengthAnalysis

// Alias for UI consistency  
val DetailedCouplesReport.challengesAnalysis: List<com.spiritatlas.domain.service.CouplesChallenge>
    get() = challengeAnalysis

/**
 * Simple breakdown structure for UI
 */
data class CompatibilityBreakdown(
    val spiritual: Double,
    val emotional: Double,
    val physical: Double
)
