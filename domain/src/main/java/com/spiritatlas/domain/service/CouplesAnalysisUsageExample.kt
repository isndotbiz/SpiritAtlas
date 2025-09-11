package com.spiritatlas.domain.service

import com.spiritatlas.domain.model.*
import java.time.LocalDate

/**
 * Example usage of the Enhanced Couples Analysis Engine
 * Shows how to get detailed compatibility analysis for two profiles
 */
class CouplesAnalysisUsageExample {

    fun demonstrateDetailedCouplesAnalysis() {
        val analysisEngine = EnhancedCouplesAnalysisEngine()
        
        // Create sample user profiles
        val partner1 = createSampleProfile("Sarah", LocalDate.of(1990, 3, 15))
        val partner2 = createSampleProfile("Michael", LocalDate.of(1988, 7, 22))
        
        // Analyze couples compatibility
        val detailedReport = analysisEngine.analyzeCouplesCompatibility(partner1, partner2)
        
        // Access all compatibility details
        displayCompatibilityOverview(detailedReport)
        displayStrengthsAndChallenges(detailedReport)
        displayActionPlan(detailedReport)
    }
    
    private fun createSampleProfile(name: String, birthDate: LocalDate): UserProfile {
        return UserProfile(
            id = "sample_${name.lowercase()}",
            profileName = "$name's Profile",
            createdAt = java.time.LocalDateTime.now(),
            lastModified = java.time.LocalDateTime.now(),
            name = name,
            displayName = name,
            birthDateTime = birthDate.atTime(12, 0), // noon
            birthPlace = BirthPlace(
                city = "Sample City",
                country = "Sample Country",
                latitude = 40.7128,
                longitude = -74.0060
            ),
            loveLanguage = LoveLanguage.QUALITY_TIME,
            personalityType = PersonalityType.INFP,
            attachmentStyle = AttachmentStyle.SECURE,
            sexualEnergy = SexualEnergy.BALANCED_CORE,
            communicationStyle = CommunicationStyle.SUPPORTIVE,
            conflictResolution = ConflictStyle.COLLABORATING,
            intimacyPreference = IntimacyStyle.BALANCED,
            spiritualConnection = SpiritualConnection.SHARED_PRACTICES
        )
    }
    
    private fun displayCompatibilityOverview(report: DetailedCouplesReport) {
        println("=== COUPLES COMPATIBILITY ANALYSIS ===")
        println("${report.partner1.name} & ${report.partner2.name}")
        println("Overall Compatibility: ${String.format("%.1f", report.compatibilityScores.overallCompatibility)}%")
        println()
        
        println("--- COMPATIBILITY SCORES ---")
        with(report.compatibilityScores) {
            println("Numerology: ${String.format("%.1f", numerologyCompatibility.overallScore)}%")
            println("Astrology: ${String.format("%.1f", astrologyCompatibility.overallScore)}%")
            println("Communication: ${String.format("%.1f", communicationScore.overallScore)}%")
            println("Emotional: ${String.format("%.1f", emotionalCompatibility.overallScore)}%")
            println("Intimacy: ${String.format("%.1f", intimacyCompatibility.overallScore)}%")
            println("Tantric: ${String.format("%.1f", tantricCompatibility.overallScore)}%")
            println("Values Alignment: ${String.format("%.1f", valueAlignment.overallScore)}%")
        }
        println()
        
        println("--- RELATIONSHIP DYNAMICS ---")
        with(report.relationshipDynamics) {
            println("Power Dynamics: $powerDynamics")
            println("Decision Making: $decisionMakingStyle")
            println("Support Patterns: $supportPatterns")
            println("Growth Potential: $growthPotential")
        }
        println()
    }
    
    private fun displayStrengthsAndChallenges(report: DetailedCouplesReport) {
        println("=== STRENGTHS ===")
        report.strengthAnalysis.forEach { strength ->
            println("• ${strength.title} (${String.format("%.1f", strength.score)}%)")
            println("  ${strength.description}")
            strength.benefits.forEach { benefit ->
                println("  ✓ $benefit")
            }
            println("  How to maximize:")
            strength.howToMaximize.forEach { action ->
                println("    → $action")
            }
            println()
        }
        
        println("=== CHALLENGES ===")
        report.challengeAnalysis.forEach { challenge ->
            val severityIcon = when (challenge.severity) {
                ChallengeSeverity.MINOR -> "⚠️"
                ChallengeSeverity.MODERATE -> "🟡"
                ChallengeSeverity.MAJOR -> "🔴"
                ChallengeSeverity.CRITICAL -> "💥"
            }
            println("$severityIcon ${challenge.title}")
            println("  ${challenge.description}")
            println("  Specific issues:")
            challenge.specificIssues.forEach { issue ->
                println("    • $issue")
            }
            println("  Solutions:")
            challenge.solutions.forEach { solution ->
                println("    → $solution")
            }
            println("  Timeline: ${challenge.timelineToImprove}")
            println()
        }
    }
    
    private fun displayActionPlan(report: DetailedCouplesReport) {
        println("=== ACTION PLAN ===")
        println("Overall Timeline: ${report.actionPlan.overallTimeline}")
        println()
        
        println("--- IMMEDIATE ACTIONS (Next 30 Days) ---")
        report.actionPlan.immediateActions.forEach { action ->
            val difficultyIcon = when (action.difficulty) {
                ActionDifficulty.EASY -> "🟢"
                ActionDifficulty.MODERATE -> "🟡" 
                ActionDifficulty.ADVANCED -> "🔴"
            }
            println("$difficultyIcon ${action.title}")
            println("  ${action.description}")
            println("  Frequency: ${action.frequency}")
            println("  Expected outcome: ${action.expectedOutcome}")
            println()
        }
        
        println("--- SHORT-TERM GOALS (1-3 Months) ---")
        report.actionPlan.shortTermGoals.forEach { goal ->
            println("• ${goal.title}")
            println("  ${goal.description}")
            println("  Expected outcome: ${goal.expectedOutcome}")
            println()
        }
        
        println("--- LONG-TERM VISION (6-12 Months) ---")
        report.actionPlan.longTermVision.forEach { vision ->
            println("• ${vision.title}")
            println("  ${vision.description}")
            println("  Expected outcome: ${vision.expectedOutcome}")
            println()
        }
        
        println("--- KEY MILESTONES ---")
        report.actionPlan.keyMilestones.forEach { milestone ->
            println("✓ $milestone")
        }
        println()
        
        println("--- FUTURE POTENTIAL ---")
        println(report.futurePotential)
        println()
        
        println("--- OVERALL RECOMMENDATION ---")
        println(report.overallRecommendation)
    }
}

/**
 * Simple function to analyze two profiles and get all details
 */
fun analyzeCouplesCompatibility(
    partner1: UserProfile, 
    partner2: UserProfile
): DetailedCouplesReport {
    val analysisEngine = EnhancedCouplesAnalysisEngine()
    return analysisEngine.analyzeCouplesCompatibility(partner1, partner2)
}

/**
 * Helper function to get just the compatibility summary
 */
fun getCouplesCompatibilitySummary(
    partner1: UserProfile,
    partner2: UserProfile
): CouplesCompatibilitySummary {
    val fullReport = analyzeCouplesCompatibility(partner1, partner2)
    
    return CouplesCompatibilitySummary(
        overallScore = fullReport.compatibilityScores.overallCompatibility,
        topStrengths = fullReport.strengthAnalysis.take(3).map { it.title },
        majorChallenges = fullReport.challengeAnalysis
            .filter { it.severity == ChallengeSeverity.MAJOR }
            .map { it.title },
        keyRecommendation = fullReport.overallRecommendation,
        nextSteps = fullReport.actionPlan.immediateActions.take(2).map { it.title }
    )
}

data class CouplesCompatibilitySummary(
    val overallScore: Double,
    val topStrengths: List<String>,
    val majorChallenges: List<String>,
    val keyRecommendation: String,
    val nextSteps: List<String>
)
