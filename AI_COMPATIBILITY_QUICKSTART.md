# AI Compatibility Analysis - Quick Start Guide

## 30-Second Overview

SpiritAtlas compatibility analysis now includes **AI-powered insights** that enhance calculated scores with deep, multi-dimensional spiritual analysis. Works seamlessly with or without AI.

## Quick Usage

### In ViewModel

```kotlin
@Inject constructor(
    private val analysisEngine: CompatibilityAnalysisEngine
)

viewModelScope.launch {
    val report = analysisEngine.analyzeCompatibility(
        profileA = profileA,
        profileB = profileB,
        includeAiInsights = true  // 👈 Enable AI
    )

    // Check for AI insights
    if (report.hasAiInsights) {
        val aiAnalysis = report.aiInsights?.overallSummary
        val numerology = report.aiInsights?.numerologyInsight
        // Show enhanced insights in UI
    }
}
```

### In Composable

```kotlin
@Composable
fun CompatibilityScreen(report: CompatibilityReport) {
    Column {
        // Regular scores (always present)
        ScoresCard(report.overallScore)

        // AI insights (optional)
        if (report.hasAiInsights) {
            AiInsightsCard(report.aiInsights!!)
        }
    }
}

@Composable
fun AiInsightsCard(insights: AiCompatibilityInsights) {
    Card {
        // Overall summary
        insights.overallSummary?.let { Text(it) }

        // Each dimension
        insights.numerologyInsight?.let { DimensionCard("Numerology", it) }
        insights.astrologyInsight?.let { DimensionCard("Astrology", it) }
        insights.tantricInsight?.let { DimensionCard("Tantric", it) }
        insights.emotionalInsight?.let { DimensionCard("Emotional", it) }
        insights.communicationInsight?.let { DimensionCard("Communication", it) }
        insights.karmicInsight?.let { DimensionCard("Karmic", it) }
    }
}
```

## What You Get

### AI Analyzes 6 Dimensions

1. **Numerology** - Life path, expression, soul urge compatibility
2. **Astrology** - Sun/Moon/Rising synastry, planetary aspects
3. **Tantric** - Sacred energy, polarity, chakra alignment
4. **Emotional** - Processing styles, attachment patterns
5. **Communication** - Expression styles, conflict resolution
6. **Karmic** - Past lives, soul contracts, spiritual lessons

### Each Dimension Includes

```kotlin
data class AiDimensionInsight(
    val analysis: String,              // Deep explanation
    val keyPoints: List<String>,       // Quick highlights
    val warnings: List<String>,        // Potential challenges
    val recommendations: List<String>, // Actionable advice
    val confidence: Float              // AI confidence (0-1)
)
```

## Analysis Depth Levels

```kotlin
// Fast (~500 words)
aiAnalysisType = AnalysisType.QUICK

// Balanced (~1500 words) - DEFAULT
aiAnalysisType = AnalysisType.STANDARD

// Deep (~3000+ words)
aiAnalysisType = AnalysisType.COMPREHENSIVE
```

## Key Features

### ✅ Graceful Degradation

Works perfectly with or without AI:
- **AI Available**: Full enhanced insights
- **AI Unavailable**: Regular calculated scores only
- **AI Error**: Catches exception, continues without AI

### ✅ Backward Compatible

```kotlin
// Old sync version (no AI)
val report = engine.analyzeCompatibilitySync(profileA, profileB)

// New async version (with AI)
val report = engine.analyzeCompatibility(
    profileA, profileB,
    includeAiInsights = true
)
```

### ✅ User Control

```kotlin
// Respect user preferences
val includeAi = userPreferences.allowDataEnrichment

val report = engine.analyzeCompatibility(
    profileA = profileA,
    profileB = profileB,
    includeAiInsights = includeAi
)
```

## Common Patterns

### Check AI Availability

```kotlin
suspend fun checkAiAvailability(): Boolean {
    return aiCompatibilityService?.isAvailable() ?: false
}
```

### Adaptive Analysis Depth

```kotlin
fun getAnalysisType(profileA: UserProfile, profileB: UserProfile): AnalysisType {
    val avgCompletion = (profileA.profileCompletion.completionPercentage +
                        profileB.profileCompletion.completionPercentage) / 2.0
    return when {
        avgCompletion >= 75.0 -> AnalysisType.COMPREHENSIVE
        avgCompletion >= 40.0 -> AnalysisType.STANDARD
        else -> AnalysisType.QUICK
    }
}
```

### Progressive Loading

```kotlin
viewModelScope.launch {
    // Show calculated scores immediately
    val quickReport = engine.analyzeCompatibilitySync(profileA, profileB)
    _state.value = State.Success(quickReport)

    // Load AI insights in background
    val enhancedReport = engine.analyzeCompatibility(
        profileA, profileB,
        includeAiInsights = true,
        aiAnalysisType = AnalysisType.COMPREHENSIVE
    )
    _state.value = State.Success(enhancedReport)
}
```

## File Locations

```
domain/
├── ai/
│   ├── AiCompatibilityModels.kt     # Data models
│   └── AiTextProvider.kt            # Provider interface
├── model/
│   └── CompatibilityModels.kt       # Updated with aiInsights
└── service/
    ├── AiCompatibilityService.kt         # AI service interface
    ├── AiCompatibilityServiceImpl.kt     # AI service implementation
    ├── CompatibilityAnalysisEngine.kt    # Enhanced engine
    └── optimized/
        └── OptimizedCompatibilityAnalysisEngine.kt  # Enhanced optimized

data/
└── di/
    └── CompatibilityModule.kt       # DI setup
```

## Dependencies

Already configured! The service uses:
- `AiTextProvider` (existing AI infrastructure)
- `CombinedAiProvider` (Gemini, Groq, OpenAI, Claude, etc.)
- Automatic provider selection based on availability

## Quick Troubleshooting

| Issue | Solution |
|-------|----------|
| No AI insights | Check `includeAiInsights = true` |
| Null insights | Verify API key in `local.properties` |
| Slow response | Use `AnalysisType.QUICK` |
| Poor quality | Switch to `AnalysisType.COMPREHENSIVE` |
| Want sync only | Use `analyzeCompatibilitySync()` |

## Example: Full Implementation

```kotlin
@HiltViewModel
class CompatibilityViewModel @Inject constructor(
    private val analysisEngine: CompatibilityAnalysisEngine,
    private val aiCompatibilityService: AiCompatibilityService?
) : ViewModel() {

    private val _state = MutableStateFlow<CompatibilityState>(CompatibilityState.Idle)
    val state: StateFlow<CompatibilityState> = _state.asStateFlow()

    fun analyzeCompatibility(profileA: UserProfile, profileB: UserProfile) {
        viewModelScope.launch {
            _state.value = CompatibilityState.Loading

            try {
                // Check if AI is available
                val aiAvailable = aiCompatibilityService?.isAvailable() ?: false

                // Determine analysis depth based on profile completeness
                val analysisType = when {
                    profileA.profileCompletion.completionPercentage >= 75 &&
                    profileB.profileCompletion.completionPercentage >= 75 ->
                        AnalysisType.COMPREHENSIVE
                    else -> AnalysisType.STANDARD
                }

                // Perform analysis
                val report = analysisEngine.analyzeCompatibility(
                    profileA = profileA,
                    profileB = profileB,
                    includeAiInsights = aiAvailable,
                    aiAnalysisType = analysisType
                )

                _state.value = CompatibilityState.Success(report, aiAvailable)
            } catch (e: Exception) {
                _state.value = CompatibilityState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class CompatibilityState {
    object Idle : CompatibilityState()
    object Loading : CompatibilityState()
    data class Success(
        val report: CompatibilityReport,
        val hasAiInsights: Boolean
    ) : CompatibilityState()
    data class Error(val message: String) : CompatibilityState()
}

@Composable
fun CompatibilityScreen(
    viewModel: CompatibilityViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    when (val currentState = state) {
        is CompatibilityState.Loading -> LoadingScreen()
        is CompatibilityState.Success -> {
            CompatibilityResultScreen(
                report = currentState.report,
                showAiBadge = currentState.hasAiInsights
            )
        }
        is CompatibilityState.Error -> ErrorScreen(currentState.message)
        else -> IdleScreen()
    }
}
```

## That's It!

You now have AI-powered compatibility analysis integrated into SpiritAtlas. The system:
- ✅ Works with or without AI
- ✅ Provides 6-dimensional spiritual insights
- ✅ Includes actionable recommendations
- ✅ Respects user preferences
- ✅ Handles errors gracefully
- ✅ Optimized for performance

For more details, see `AI_COMPATIBILITY_INTEGRATION.md`.
