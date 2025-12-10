# AI Enhancement Quick Start Guide

## Overview

This guide shows you how to use the enhanced AI features in SpiritAtlas.

## Setup

All AI services are available via Hilt dependency injection:

```kotlin
@AndroidEntryPoint
class YourViewModel @Inject constructor(
    private val claudeProvider: ClaudeProvider,
    private val conversationManager: ConversationManager,
    private val dailyInsightsService: DailyInsightsService
) : ViewModel()
```

## Use Cases

### 1. Generate Daily Insight

```kotlin
viewModelScope.launch {
    val result = dailyInsightsService.generateDailyInsight(
        profile = currentUserProfile,
        date = LocalDate.now(),
        useCache = true // Uses cached result if available
    )

    when (result) {
        is Result.Success -> {
            val insight = result.data
            _dailyInsightState.value = DailyInsightState.Success(
                theme = insight.theme,
                overview = insight.overview,
                opportunities = insight.opportunities,
                challenges = insight.challenges,
                optimalTimes = insight.optimalTimes,
                practice = insight.practice
            )
        }
        is Result.Error -> {
            _dailyInsightState.value = DailyInsightState.Error(
                result.exception.message ?: "Unknown error"
            )
        }
    }
}
```

### 2. Multi-Turn Conversation

```kotlin
// Step 1: Create or load conversation
val conversation = conversationManager.getActiveConversation(profile.id)
    ?: conversationManager.createConversation(
        profileId = profile.id,
        initialContext = buildProfileContext(profile)
    ).data!!

// Step 2: User asks a question
conversationManager.addUserMessage(
    conversationId = conversation.id,
    message = userQuestion
)

// Step 3: Get AI response
val updatedConversation = conversationManager.loadConversation(conversation.id)!!
val history = updatedConversation.turns.map { turn ->
    ClaudeMessage(
        role = when (turn.role) {
            ConversationManager.Role.USER -> ClaudeRole.USER
            ConversationManager.Role.ASSISTANT -> ClaudeRole.ASSISTANT
            ConversationManager.Role.SYSTEM -> ClaudeRole.SYSTEM
        },
        content = turn.content
    )
}

val response = claudeProvider.generateFollowUp(
    conversationHistory = history,
    systemPrompt = PromptTemplates.getSystemPrompt()
)

// Step 4: Save response
if (response is Result.Success) {
    conversationManager.addAssistantResponse(
        conversationId = conversation.id,
        response = response.data.text
    )
}
```

### 3. Enhanced Profile Enrichment

```kotlin
viewModelScope.launch {
    val context = EnrichmentContext(
        numerology = extractNumerologyData(profile),
        astrology = extractAstrologyData(profile),
        energyProfile = extractEnergyData(profile),
        personalDetails = extractPersonalData(profile),
        completedFields = profile.profileCompletion.completedFields,
        totalFields = profile.profileCompletion.totalFields,
        accuracyLevel = profile.profileCompletion.accuracyLevel.name
    )

    val result = claudeProvider.generateEnrichment(context)

    when (result) {
        is Result.Success -> {
            val enrichedText = result.data.text
            val keyPoints = ResponseParser.extractKeyPoints(enrichedText)
            val affirmations = ResponseParser.extractAffirmations(enrichedText)
            val practices = ResponseParser.extractPractices(enrichedText)

            // Display structured content
            _enrichmentState.value = EnrichmentState.Success(
                fullText = enrichedText,
                keyPoints = keyPoints,
                affirmations = affirmations,
                practices = practices
            )
        }
        is Result.Error -> {
            _enrichmentState.value = EnrichmentState.Error(
                result.exception.message ?: "Failed to generate insights"
            )
        }
    }
}
```

### 4. Compatibility Analysis with AI

```kotlin
viewModelScope.launch {
    // First get calculated scores from compatibility engine
    val compatibilityReport = compatibilityEngine.analyzeCompatibility(
        profile1, profile2
    )

    // Then enhance with AI insights
    val profile1Summary = buildProfileSummary(profile1)
    val profile2Summary = buildProfileSummary(profile2)
    val scores = compatibilityReport.overallScore.scoreBreakdown

    val aiResult = claudeProvider.generateCompatibilityInsights(
        profile1Summary = profile1Summary,
        profile2Summary = profile2Summary,
        calculatedScores = scores
    )

    when (aiResult) {
        is Result.Success -> {
            val insights = ResponseParser.parseCompatibilityInsights(
                aiResult.data.text
            )

            _compatibilityState.value = CompatibilityState.Enhanced(
                report = compatibilityReport,
                aiStrengths = insights["strengths"] ?: emptyList(),
                aiChallenges = insights["challenges"] ?: emptyList(),
                aiRecommendations = insights["recommendations"] ?: emptyList(),
                soulConnection = insights["soul_connection"]?.firstOrNull()
            )
        }
        is Result.Error -> {
            // Fall back to report without AI enhancement
            _compatibilityState.value = CompatibilityState.Basic(
                report = compatibilityReport
            )
        }
    }
}
```

### 5. Weekly Insights Summary

```kotlin
viewModelScope.launch {
    val startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY)

    val result = dailyInsightsService.generateWeeklyInsights(
        profile = currentUserProfile,
        startDate = startOfWeek
    )

    when (result) {
        is Result.Success -> {
            val weeklyInsights = result.data
            _weeklyState.value = WeeklyState.Success(
                theme = weeklyInsights.weekTheme,
                dailyInsights = weeklyInsights.dailyInsights,
                highlights = weeklyInsights.weeklyHighlights,
                focusAreas = weeklyInsights.focusAreas
            )
        }
        is Result.Error -> {
            _weeklyState.value = WeeklyState.Error(
                result.exception.message ?: "Failed to generate weekly insights"
            )
        }
    }
}
```

## Helper Functions

### Building Profile Context

```kotlin
private fun buildProfileContext(profile: UserProfile): String {
    return buildString {
        appendLine("**Profile:** ${profile.name}")
        profile.birthDateTime?.let {
            appendLine("**Birth:** ${it.format(DateTimeFormatter.ISO_DATE_TIME)}")
        }
        profile.birthPlace?.let {
            appendLine("**Location:** ${it.city}, ${it.country}")
        }

        // Add key numerology
        // Add key astrology
        // Add relationship preferences
    }
}

private fun buildProfileSummary(profile: UserProfile): String {
    return """
        Name: ${profile.name}
        Birth Date: ${profile.birthDateTime?.toLocalDate()}
        Sun Sign: [extract from chart]
        Life Path: [extract from numerology]
        Love Language: ${profile.loveLanguage?.name}
        Communication Style: ${profile.communicationStyle?.name}
    """.trimIndent()
}
```

### Extracting Data for Context

```kotlin
private fun extractNumerologyData(profile: UserProfile): Map<String, Any> {
    val data = mutableMapOf<String, Any>()
    profile.name?.let { name ->
        // Calculate numerology numbers
        data["life_path"] = calculateLifePath(profile.birthDateTime)
        data["expression"] = calculateExpression(name)
        data["soul_urge"] = calculateSoulUrge(name)
    }
    return data
}

private fun extractAstrologyData(profile: UserProfile): Map<String, Any> {
    val data = mutableMapOf<String, Any>()
    profile.birthDateTime?.let {
        data["birth_date"] = it.toString()
    }
    profile.birthPlace?.let {
        data["latitude"] = it.latitude
        data["longitude"] = it.longitude
        data["location"] = "${it.city}, ${it.country}"
    }
    return data
}

private fun extractEnergyData(profile: UserProfile): Map<String, Any> {
    val data = mutableMapOf<String, Any>()
    profile.gender?.let { data["gender"] = it.name }
    profile.bloodType?.let { data["blood_type"] = it.name }
    profile.sexualEnergy?.let { data["sexual_energy"] = it.name }
    return data
}

private fun extractPersonalData(profile: UserProfile): Map<String, Any> {
    val data = mutableMapOf<String, Any>()
    profile.name?.let { data["name"] = it }
    profile.displayName?.let { data["display_name"] = it }
    profile.loveLanguage?.let { data["love_language"] = it.name }
    profile.communicationStyle?.let { data["communication_style"] = it.name }
    return data
}
```

## Composable UI Examples

### Daily Insight Card

```kotlin
@Composable
fun DailyInsightCard(
    insight: DailyInsight,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Theme
            Text(
                text = insight.theme,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            // Overview
            Text(
                text = insight.overview,
                style = MaterialTheme.typography.bodyMedium
            )

            // Opportunities
            Text(
                text = "Opportunities",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            insight.opportunities.forEach { opportunity ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFF4ECDC4)
                    )
                    Text(
                        text = opportunity.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Practice
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Today's Practice",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Meditation: ${insight.practice.meditationFocus}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "\"${insight.practice.affirmation}\"",
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}
```

### Conversation UI

```kotlin
@Composable
fun ConversationScreen(
    conversation: ConversationManager.Conversation,
    onSendMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var messageText by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {
        // Messages list
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true
        ) {
            items(conversation.turns.reversed()) { turn ->
                when (turn.role) {
                    ConversationManager.Role.USER -> {
                        UserMessageBubble(message = turn.content)
                    }
                    ConversationManager.Role.ASSISTANT -> {
                        AiMessageBubble(message = turn.content)
                    }
                    else -> {}
                }
            }
        }

        // Input field
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Ask a question...") }
            )
            IconButton(
                onClick = {
                    if (messageText.isNotBlank()) {
                        onSendMessage(messageText)
                        messageText = ""
                    }
                }
            ) {
                Icon(Icons.Default.Send, contentDescription = "Send")
            }
        }
    }
}
```

## Error Handling

### Common Error Scenarios

```kotlin
sealed class AiError {
    data class AuthenticationFailed(val message: String) : AiError()
    data class RateLimitExceeded(val retryAfter: String?) : AiError()
    data class NetworkError(val message: String) : AiError()
    data class InvalidRequest(val message: String) : AiError()
    data class Unknown(val exception: Exception) : AiError()
}

fun handleAiError(error: Exception): AiError {
    return when {
        error.message?.contains("authentication", ignoreCase = true) == true ->
            AiError.AuthenticationFailed(error.message!!)
        error.message?.contains("rate limit", ignoreCase = true) == true ->
            AiError.RateLimitExceeded(extractRetryAfter(error.message))
        error.message?.contains("network", ignoreCase = true) == true ->
            AiError.NetworkError(error.message!!)
        error.message?.contains("invalid", ignoreCase = true) == true ->
            AiError.InvalidRequest(error.message!!)
        else ->
            AiError.Unknown(error)
    }
}

@Composable
fun ErrorDisplay(error: AiError) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = when (error) {
                    is AiError.AuthenticationFailed -> "Authentication Failed"
                    is AiError.RateLimitExceeded -> "Rate Limit Exceeded"
                    is AiError.NetworkError -> "Network Error"
                    is AiError.InvalidRequest -> "Invalid Request"
                    is AiError.Unknown -> "Unknown Error"
                },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )

            Text(
                text = when (error) {
                    is AiError.AuthenticationFailed -> error.message
                    is AiError.RateLimitExceeded ->
                        "Please try again ${error.retryAfter ?: "later"}"
                    is AiError.NetworkError -> error.message
                    is AiError.InvalidRequest -> error.message
                    is AiError.Unknown -> error.exception.message ?: "An error occurred"
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
```

## Best Practices

### 1. Always Use Caching
```kotlin
// ✅ Good - uses cache
val insight = dailyInsightsService.generateDailyInsight(
    profile = profile,
    useCache = true
)

// ❌ Bad - regenerates every time
val insight = dailyInsightsService.generateDailyInsight(
    profile = profile,
    useCache = false
)
```

### 2. Handle Loading States
```kotlin
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

@Composable
fun InsightScreen(state: UiState<DailyInsight>) {
    when (state) {
        is UiState.Loading -> LoadingIndicator()
        is UiState.Success -> InsightContent(state.data)
        is UiState.Error -> ErrorMessage(state.message)
    }
}
```

### 3. Optimize Token Usage
```kotlin
// ✅ Good - summarizes long conversations
if (conversationManager.estimateTokenCount(conversation) > 8000) {
    conversationManager.summarizeConversation(
        conversationId = conversation.id,
        keepRecentTurns = 6
    )
}

// ✅ Good - uses appropriate model
claudeProvider.generateDailyInsights() // Uses Haiku (cheap)
claudeProvider.generateEnrichment() // Uses Sonnet/Opus (quality)
```

### 4. Graceful Degradation
```kotlin
val insights = try {
    claudeProvider.generateCompatibilityInsights(...)
} catch (e: Exception) {
    // Fall back to calculated insights only
    null
}

displayCompatibility(
    calculatedReport = report,
    aiEnhancement = insights
)
```

## Testing

### Mock Services for Testing

```kotlin
class MockDailyInsightsService : DailyInsightsService {
    override suspend fun generateDailyInsight(
        profile: UserProfile,
        date: LocalDate,
        useCache: Boolean
    ): Result<DailyInsight> {
        return Result.Success(
            DailyInsight(
                profileId = profile.id,
                date = date,
                personalYear = 5,
                personalMonth = 3,
                personalDay = 1,
                theme = "Test Theme",
                overview = "Test overview",
                opportunities = emptyList(),
                challenges = emptyList(),
                optimalTimes = mockOptimalTimes(),
                practice = mockPractice(),
                energyFocus = mockEnergyFocus()
            )
        )
    }

    // Implement other methods...
}
```

## Resources

- **PromptTemplates.kt** - All prompt templates and examples
- **ResponseParser.kt** - Parsing utilities and helpers
- **DailyInsightsModels.kt** - Complete data model reference
- **AI_ENHANCEMENT_SUMMARY.md** - Detailed technical documentation

## Support

For issues or questions:
1. Check the error message and logs
2. Verify API key is configured
3. Check network connectivity
4. Review rate limits
5. Consult the summary documentation
