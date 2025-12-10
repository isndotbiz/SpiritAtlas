# AI Compatibility Analysis Integration

## Overview

The SpiritAtlas compatibility analysis engine has been enhanced with AI-powered insights that provide deep, multi-perspective analysis across all spiritual dimensions. This integration complements (not replaces) the existing calculated scores with intelligent, contextual insights.

## Architecture

### Components

1. **AiCompatibilityService** (`domain/service/AiCompatibilityService.kt`)
   - Interface for AI-powered compatibility analysis
   - Provides multi-dimensional insights and dimension-specific analysis
   - Gracefully handles AI unavailability

2. **AiCompatibilityServiceImpl** (`domain/service/AiCompatibilityServiceImpl.kt`)
   - Implementation using existing AiTextProvider infrastructure
   - Generates structured prompts for comprehensive analysis
   - Parses AI responses into typed models

3. **AI Models** (`domain/ai/AiCompatibilityModels.kt`)
   - `AiCompatibilityInsights`: Container for all AI-generated insights
   - `AiDimensionInsight`: Insights for specific compatibility dimensions
   - `ProfileSummary`: Optimized profile data for AI context
   - `ScoreSummary`: Calculated scores to inform AI analysis

4. **Enhanced Analysis Engines**
   - `CompatibilityAnalysisEngine`: Updated with AI integration
   - `OptimizedCompatibilityAnalysisEngine`: Performance-optimized with AI

5. **Updated Models** (`domain/model/CompatibilityModels.kt`)
   - `CompatibilityReport` now includes optional `aiInsights` field
   - `hasAiInsights` property for checking AI availability

## Features

### Multi-Dimensional AI Analysis

The AI analyzes compatibility across six key dimensions:

1. **Numerology Compatibility**
   - Life path number harmony
   - Expression number alignment
   - Soul urge compatibility
   - Karmic debt patterns

2. **Astrological Synastry**
   - Sun, Moon, Rising sign interactions
   - Planetary aspects and transits
   - Element and modality compatibility
   - Houses and timing influences

3. **Tantric/Sexual Energy**
   - Sacred energy dynamics
   - Polarity and balance
   - Chakra alignment
   - Kundalini compatibility

4. **Emotional Compatibility**
   - Emotional processing styles
   - Vulnerability and intimacy patterns
   - Attachment dynamics
   - Healing opportunities

5. **Communication Dynamics**
   - Communication style matching
   - Conflict resolution approaches
   - Expression preferences
   - Listening and understanding patterns

6. **Karmic Connections**
   - Past life influences
   - Soul contracts and lessons
   - Karmic debts and credits
   - Spiritual growth opportunities

### AI Insight Structure

Each dimension provides:
- **Analysis**: Deep contextual explanation
- **Key Points**: Bullet-point highlights
- **Warnings**: Potential challenges to be aware of
- **Recommendations**: Actionable advice
- **Confidence Score**: AI's confidence in the analysis

### Analysis Types

Three levels of analysis depth:

1. **QUICK**: Fast overview, minimal tokens (~500 words)
2. **STANDARD**: Balanced analysis (~1500 words) - default
3. **COMPREHENSIVE**: Deep, multi-dimensional analysis (~3000+ words)

## Usage

### Basic Usage (Async with AI)

```kotlin
class CompatibilityViewModel @Inject constructor(
    private val analysisEngine: CompatibilityAnalysisEngine
) : ViewModel() {

    fun analyzeCompatibility(profileA: UserProfile, profileB: UserProfile) {
        viewModelScope.launch {
            val report = analysisEngine.analyzeCompatibility(
                profileA = profileA,
                profileB = profileB,
                includeAiInsights = true,  // Enable AI
                aiAnalysisType = AnalysisType.STANDARD
            )

            // Check if AI insights are available
            if (report.hasAiInsights) {
                val numerologyInsight = report.aiInsights?.numerologyInsight
                val summary = report.aiInsights?.overallSummary
                // Use AI insights in UI
            }
        }
    }
}
```

### Optimized Version

```kotlin
class CompatibilityViewModel @Inject constructor(
    private val optimizedEngine: OptimizedCompatibilityAnalysisEngine
) : ViewModel() {

    fun analyzeCompatibility(profileA: UserProfile, profileB: UserProfile) {
        viewModelScope.launch {
            val report = optimizedEngine.analyzeCompatibility(
                profileA = profileA,
                profileB = profileB,
                includeAiInsights = true,
                aiAnalysisType = AnalysisType.COMPREHENSIVE  // Deep analysis
            )

            // Access AI insights
            report.aiInsights?.let { insights ->
                insights.numerologyInsight?.let { numerology ->
                    println("Analysis: ${numerology.analysis}")
                    println("Key Points: ${numerology.keyPoints}")
                    println("Recommendations: ${numerology.recommendations}")
                }
            }
        }
    }
}
```

### Backward Compatible (Sync, No AI)

```kotlin
class CompatibilityViewModel @Inject constructor(
    private val analysisEngine: CompatibilityAnalysisEngine
) : ViewModel() {

    fun analyzeCompatibilitySync(profileA: UserProfile, profileB: UserProfile) {
        val report = analysisEngine.analyzeCompatibilitySync(
            profileA = profileA,
            profileB = profileB
        )
        // No AI insights, only calculated scores
        // report.aiInsights will be null
    }
}
```

### Direct AI Service Usage

```kotlin
class DeepAnalysisViewModel @Inject constructor(
    private val aiCompatibilityService: AiCompatibilityService
) : ViewModel() {

    fun analyzeDimension(
        profileA: UserProfile,
        profileB: UserProfile,
        dimension: String,
        score: Double
    ) {
        viewModelScope.launch {
            when (val result = aiCompatibilityService.analyzeDimension(
                profileA = profileA,
                profileB = profileB,
                dimension = "numerology",
                calculatedScore = score
            )) {
                is Result.Success -> {
                    val insight = result.data
                    println(insight.analysis)
                    println(insight.keyPoints)
                }
                is Result.Error -> {
                    // Handle error or fall back
                }
            }
        }
    }
}
```

## UI Integration Examples

### Displaying AI Insights

```kotlin
@Composable
fun CompatibilityResultScreen(report: CompatibilityReport) {
    Column {
        // Traditional scores
        ScoresSection(report.overallScore)

        // AI-enhanced insights (if available)
        if (report.hasAiInsights) {
            AiInsightsSection(report.aiInsights!!)
        }

        // Regular insights
        InsightsSection(report.insights)
    }
}

@Composable
fun AiInsightsSection(insights: AiCompatibilityInsights) {
    Card {
        Column {
            Text("AI-Powered Insights", style = MaterialTheme.typography.headlineSmall)

            // Overall summary
            insights.overallSummary?.let { summary ->
                Text(summary, style = MaterialTheme.typography.bodyMedium)
            }

            // Dimension-specific insights
            insights.numerologyInsight?.let { NumerologyInsightCard(it) }
            insights.astrologyInsight?.let { AstrologyInsightCard(it) }
            insights.tantricInsight?.let { TantricInsightCard(it) }
            insights.emotionalInsight?.let { EmotionalInsightCard(it) }
            insights.communicationInsight?.let { CommunicationInsightCard(it) }
            insights.karmicInsight?.let { KarmicInsightCard(it) }
        }
    }
}

@Composable
fun DimensionInsightCard(insight: AiDimensionInsight) {
    Card {
        Column {
            Text(insight.dimension.capitalize(), style = MaterialTheme.typography.titleMedium)

            // Main analysis
            Text(insight.analysis, style = MaterialTheme.typography.bodyMedium)

            // Key points
            if (insight.keyPoints.isNotEmpty()) {
                Text("Key Points:", style = MaterialTheme.typography.titleSmall)
                insight.keyPoints.forEach { point ->
                    Row {
                        Icon(Icons.Default.Check, contentDescription = null)
                        Text(point)
                    }
                }
            }

            // Warnings
            if (insight.warnings.isNotEmpty()) {
                Text("Things to Watch:", style = MaterialTheme.typography.titleSmall)
                insight.warnings.forEach { warning ->
                    Row {
                        Icon(Icons.Default.Warning, contentDescription = null)
                        Text(warning)
                    }
                }
            }

            // Recommendations
            if (insight.recommendations.isNotEmpty()) {
                Text("Recommendations:", style = MaterialTheme.typography.titleSmall)
                insight.recommendations.forEach { rec ->
                    Row {
                        Icon(Icons.Default.Star, contentDescription = null)
                        Text(rec)
                    }
                }
            }
        }
    }
}
```

## Graceful Degradation

The system is designed to work seamlessly whether AI is available or not:

1. **AI Available**: Full AI insights included in report
2. **AI Unavailable**: Report contains only calculated scores and rule-based insights
3. **AI Error**: Exception caught, report generated without AI insights
4. **No AI Provider**: Service set to null, no AI attempted

### Checking AI Availability

```kotlin
class CompatibilityViewModel @Inject constructor(
    private val aiCompatibilityService: AiCompatibilityService?
) : ViewModel() {

    suspend fun checkAiAvailability(): Boolean {
        return aiCompatibilityService?.isAvailable() ?: false
    }

    fun analyzeCompatibility(profileA: UserProfile, profileB: UserProfile) {
        viewModelScope.launch {
            val aiAvailable = checkAiAvailability()

            val report = analysisEngine.analyzeCompatibility(
                profileA = profileA,
                profileB = profileB,
                includeAiInsights = aiAvailable  // Only if available
            )

            // Update UI based on availability
            if (aiAvailable && report.hasAiInsights) {
                showEnhancedResults(report)
            } else {
                showBasicResults(report)
            }
        }
    }
}
```

## Performance Considerations

### Caching

Both engines support caching:
- `OptimizedCompatibilityAnalysisEngine` caches calculated scores
- AI insights are NOT cached (intentionally fresh each time)
- Profile score cache uses deterministic keys

### Token Optimization

AI prompts are optimized for token efficiency:
- Profile data summarized to essential fields only
- Calculated scores provided to reduce AI computation
- Analysis type controls prompt depth and length
- JSON response format for structured parsing

### Async/Await Pattern

AI analysis is asynchronous:
- Uses `suspend` functions with coroutines
- Runs on `Dispatchers.IO` for network calls
- Main calculation on `Dispatchers.Default` for CPU work
- Gracefully handles timeouts and errors

## Configuration

### Enabling/Disabling AI

```kotlin
// In ViewModel or use case
suspend fun analyzeWithUserPreference(
    profileA: UserProfile,
    profileB: UserProfile,
    userPreferences: UserPreferences
) {
    val includeAi = userPreferences.allowDataEnrichment  // Use existing preference

    val report = analysisEngine.analyzeCompatibility(
        profileA = profileA,
        profileB = profileB,
        includeAiInsights = includeAi
    )
}
```

### Analysis Depth Based on Profile Completeness

```kotlin
fun determineAnalysisType(profileA: UserProfile, profileB: UserProfile): AnalysisType {
    val avgCompletion = (profileA.profileCompletion.completionPercentage +
                        profileB.profileCompletion.completionPercentage) / 2.0

    return when {
        avgCompletion >= 75.0 -> AnalysisType.COMPREHENSIVE  // Detailed profiles
        avgCompletion >= 40.0 -> AnalysisType.STANDARD       // Medium profiles
        else -> AnalysisType.QUICK                            // Basic profiles
    }
}
```

## Testing

### Unit Testing

```kotlin
class AiCompatibilityServiceTest {

    @Mock
    private lateinit var aiTextProvider: AiTextProvider

    private lateinit var service: AiCompatibilityServiceImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        service = AiCompatibilityServiceImpl(aiTextProvider)
    }

    @Test
    fun `analyzeCompatibility returns insights when AI available`() = runTest {
        // Given
        val profileA = createTestProfile("Alice")
        val profileB = createTestProfile("Bob")
        val scores = createTestScores()

        whenever(aiTextProvider.isAvailable()).thenReturn(true)
        whenever(aiTextProvider.generateEnrichment(any())).thenReturn(
            Result.Success(EnrichmentResult(testAiResponse, 0.9f))
        )

        // When
        val result = service.analyzeCompatibility(profileA, profileB, scores)

        // Then
        assertTrue(result is Result.Success)
        val insights = (result as Result.Success).data
        assertNotNull(insights.numerologyInsight)
        assertNotNull(insights.overallSummary)
    }

    @Test
    fun `analyzeCompatibility handles AI unavailable gracefully`() = runTest {
        // Given
        whenever(aiTextProvider.isAvailable()).thenReturn(false)

        // When
        val result = service.analyzeCompatibility(profileA, profileB, scores)

        // Then
        assertTrue(result is Result.Error)
    }
}
```

### Integration Testing

```kotlin
@HiltAndroidTest
class CompatibilityAnalysisIntegrationTest {

    @Inject
    lateinit var analysisEngine: CompatibilityAnalysisEngine

    @Test
    fun `full compatibility analysis with AI`() = runTest {
        // Given
        val profileA = createRealProfile()
        val profileB = createRealProfile()

        // When
        val report = analysisEngine.analyzeCompatibility(
            profileA = profileA,
            profileB = profileB,
            includeAiInsights = true
        )

        // Then
        assertNotNull(report.overallScore)
        assertTrue(report.insights.isNotEmpty())
        // AI insights may or may not be present depending on provider availability
    }
}
```

## AI Provider Requirements

The AI compatibility service requires an `AiTextProvider` that:
- Supports `generateEnrichment()` with custom prompts
- Can handle structured output (JSON preferred)
- Has sufficient context window (4K+ tokens recommended)
- Supports async/await patterns

Current compatible providers:
- Gemini (recommended - free tier, excellent reasoning)
- Groq (fast, free tier)
- OpenAI (GPT-3.5, GPT-4)
- Claude (Sonnet, Opus)
- OpenRouter
- Ollama (local models)

## Future Enhancements

Potential improvements:
1. **Caching AI Insights**: Store AI analyses for repeated queries
2. **Incremental Analysis**: Analyze one dimension at a time for faster initial results
3. **AI Consensus**: Query multiple providers and synthesize insights
4. **Learning System**: Fine-tune prompts based on user feedback
5. **Multilingual Support**: Generate insights in user's preferred language
6. **Visualization Generation**: AI-generated compatibility charts and diagrams
7. **Timing Analysis**: AI-powered timing recommendations for relationship milestones

## Troubleshooting

### AI Insights Not Appearing

1. Check if `aiCompatibilityService` is injected (not null)
2. Verify `includeAiInsights = true` in call
3. Check `aiTextProvider.isAvailable()` returns true
4. Verify API keys are configured in `local.properties`
5. Check network connectivity
6. Review logs for AI provider errors

### Poor Quality Insights

1. Try different `AnalysisType` (COMPREHENSIVE for more depth)
2. Ensure profiles have sufficient data (name, birth date/time, etc.)
3. Switch AI provider (Gemini or Claude recommended for spiritual content)
4. Check if calculated scores are reasonable (garbage in, garbage out)

### Performance Issues

1. Use `AnalysisType.QUICK` for faster results
2. Set `includeAiInsights = false` to skip AI entirely
3. Use `OptimizedCompatibilityAnalysisEngine` instead of base engine
4. Consider lazy loading AI insights after showing calculated scores
5. Implement pagination for dimension-specific insights

## Summary

The AI compatibility integration provides:
- **Enhanced Insights**: Deep, contextual analysis across 6 dimensions
- **Graceful Degradation**: Works with or without AI
- **Flexible Configuration**: Control depth and scope of analysis
- **Performance Optimized**: Caching, async, and efficient prompts
- **User Choice**: Optional AI based on preferences
- **Future-Proof**: Extensible architecture for new AI capabilities

The system maintains all existing functionality while adding powerful AI-driven insights that help users understand their compatibility at a deeper, more nuanced level.
