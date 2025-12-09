# AI Usage Tracking Implementation

## Overview

This implementation adds comprehensive usage tracking for free-tier AI provider limits in SpiritAtlas. The system uses a sliding window algorithm to track requests per minute (RPM) and requests per day (RPD) for Gemini and Groq providers.

## Features

### Rate Limits Tracked

#### Gemini (Google)
- 15 requests per minute (RPM)
- 1,500 requests per day (RPD)
- 1 million tokens per minute (TPM) - not currently tracked

#### Groq
- 30 requests per minute (RPM)
- No daily limit
- 6,000-30,000 tokens per minute (TPM) - not currently tracked

### Capabilities

1. **Pre-request Rate Limiting**: Checks limits before making API calls
2. **Automatic Tracking**: Records successful requests automatically
3. **Sliding Window Algorithm**: Accurate rate limiting using timestamp-based windows
4. **Persistent Storage**: Uses SharedPreferences for app restart resilience
5. **Real-time UI Updates**: StateFlow-based reactive updates
6. **Smart Fallback**: AUTO mode automatically switches between providers when rate limited
7. **Manual Reset**: Users can reset usage counters from settings

## Implementation Details

### Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     Presentation Layer                       │
│  ┌────────────────────┐         ┌─────────────────────┐     │
│  │  SettingsScreen    │────────▶│  SettingsViewModel  │     │
│  │  (Compose UI)      │         │  (StateFlow)        │     │
│  └────────────────────┘         └─────────────────────┘     │
└──────────────────────────────────────┬──────────────────────┘
                                       │
┌──────────────────────────────────────▼──────────────────────┐
│                      Domain Layer                            │
│  ┌────────────────────┐         ┌─────────────────────┐     │
│  │  UsageRepository   │────────▶│   UsageTracker      │     │
│  │  (Public API)      │         │   (Core Logic)      │     │
│  └────────────────────┘         └─────────────────────┘     │
└──────────────────────────────────────┬──────────────────────┘
                                       │
┌──────────────────────────────────────▼──────────────────────┐
│                        Data Layer                            │
│  ┌────────────────────┐  ┌──────────────┐  ┌─────────────┐  │
│  │  GeminiProvider    │  │ GroqProvider │  │ Combined    │  │
│  │  (Rate limited)    │  │ (Rate limited│  │ AiProvider  │  │
│  └────────────────────┘  └──────────────┘  └─────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

### Core Components

#### 1. UsageTracker
**Location**: `/data/src/main/java/com/spiritatlas/data/tracking/UsageTracker.kt`

**Responsibilities**:
- Sliding window rate limit calculations
- Timestamp storage in SharedPreferences
- Thread-safe operations using Mutex
- Real-time state emission via StateFlow

**Key Methods**:
```kotlin
suspend fun canMakeRequest(provider: AiProvider): Boolean
suspend fun recordRequest(provider: AiProvider)
suspend fun getUsageStats(provider: AiProvider): ProviderUsage
suspend fun getTimeUntilNextRequest(provider: AiProvider): Long
suspend fun resetUsage(provider: AiProvider)
```

#### 2. UsageRepository
**Location**: `/data/src/main/java/com/spiritatlas/data/tracking/UsageRepository.kt`

**Responsibilities**:
- Public API for usage tracking
- StateFlow exposure for UI observation
- Formatted wait time messages

**Key Methods**:
```kotlin
fun observeGeminiUsage(): StateFlow<ProviderUsage>
fun observeGroqUsage(): StateFlow<ProviderUsage>
suspend fun getWaitTimeMessage(provider: AiProvider): String?
```

#### 3. Provider Integration
**Locations**:
- `/data/src/main/java/com/spiritatlas/data/ai/GeminiProvider.kt`
- `/data/src/main/java/com/spiritatlas/data/ai/GroqProvider.kt`
- `/data/src/main/java/com/spiritatlas/data/ai/CombinedAiProvider.kt`

**Integration Pattern**:
```kotlin
override suspend fun generateEnrichment(context: EnrichmentContext): Result<EnrichmentResult> {
    // 1. Check rate limits BEFORE making request
    if (!usageTracker.canMakeRequest(AiProvider.GEMINI)) {
        val waitTime = usageTracker.getTimeUntilNextRequest(AiProvider.GEMINI)
        return Result.Error(RateLimitException("Rate limit reached. Wait: $waitTime ms"))
    }

    try {
        // 2. Make API request
        val response = model.generateContent(prompt)

        // 3. Record successful request AFTER completion
        usageTracker.recordRequest(AiProvider.GEMINI)

        return Result.Success(...)
    } catch (e: Exception) {
        return Result.Error(e)
    }
}
```

#### 4. Settings UI
**Location**: `/feature/settings/src/main/java/com/spiritatlas/feature/settings/SettingsScreen.kt`

**UI Components**:
- `UsageStatsCard`: Displays per-minute and per-day usage with progress bars
- Visual warnings when approaching limits (>80%)
- Reset button for clearing usage counters

**Features**:
- Real-time progress bars
- Color-coded warnings (red when near limit)
- Separate cards for Gemini and Groq
- Manual reset capability

### Data Models

#### ProviderUsage
```kotlin
data class ProviderUsage(
    val requestsPerMinute: Int,
    val requestsPerDay: Int,
    val limitPerMinute: Int,
    val limitPerDay: Int?
) {
    val percentagePerMinute: Float
    val percentagePerDay: Float
    val isNearLimitPerMinute: Boolean  // >= 80%
    val isNearLimitPerDay: Boolean     // >= 80%
}
```

#### AiProvider
```kotlin
enum class AiProvider {
    GEMINI,  // 15 RPM, 1500 RPD
    GROQ     // 30 RPM, no daily limit
}
```

## Storage Format

Usage data is stored in SharedPreferences (`ai_usage_tracker`) as comma-separated timestamps:

```
Key: gemini_timestamps
Value: "1733676000000,1733676010000,1733676020000,..."

Key: groq_timestamps
Value: "1733676005000,1733676015000,..."
```

Each timestamp represents a successful API request. The system automatically:
- Cleans up timestamps older than 24 hours
- Keeps only relevant timestamps for sliding window calculations
- Persists across app restarts

## Rate Limiting Algorithm

The system uses a **sliding window** approach:

```kotlin
// For per-minute limit:
val oneMinuteAgo = System.currentTimeMillis() - 60_000
val recentRequests = timestamps.filter { it >= oneMinuteAgo }
val allowed = recentRequests.count() < limit

// For per-day limit:
val oneDayAgo = System.currentTimeMillis() - (24 * 60 * 60 * 1000)
val dailyRequests = timestamps.filter { it >= oneDayAgo }
val allowed = dailyRequests.count() < dailyLimit
```

### Why Sliding Window?

- **Accurate**: Prevents burst requests at boundary periods
- **Fair**: Distributes requests evenly over time
- **Simple**: No complex token bucket or leaky bucket logic
- **Efficient**: O(n) where n = number of timestamps (small)

## AUTO Mode Behavior

When `AiProviderMode.AUTO` is selected, the system intelligently selects providers:

1. **Gemini** - if available AND not rate limited
2. **Groq** - if available AND not rate limited
3. **OpenAI** - if user has configured API key
4. **Claude** - if user has configured API key
5. **OpenRouter** - if available
6. **Ollama** - if available
7. **Fallback to Gemini/Groq** - even if rate limited (will show error)

This ensures users always get the best available provider while respecting rate limits.

## Testing

Comprehensive tests are provided in `/data/src/test/java/com/spiritatlas/data/tracking/UsageTrackerTest.kt`:

**Test Coverage**:
- Initial state (requests allowed)
- Request recording and stats updates
- Per-minute rate limiting (Gemini: 15, Groq: 30)
- Per-day rate limiting (Gemini: 1500)
- Provider isolation (separate counters)
- Usage reset (individual and all)
- Percentage calculations
- Wait time calculations
- Persistence across app restarts
- StateFlow updates

**Run Tests**:
```bash
./gradlew :data:testDebugUnitTest --tests "*UsageTrackerTest"
```

## Usage Examples

### Observing Usage in UI (Compose)

```kotlin
@Composable
fun MyScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val geminiUsage by viewModel.geminiUsage.collectAsState()

    Text("Gemini: ${geminiUsage.requestsPerMinute}/${geminiUsage.limitPerMinute} RPM")

    LinearProgressIndicator(
        progress = geminiUsage.percentagePerMinute / 100f
    )

    if (geminiUsage.isNearLimitPerMinute) {
        Text("Warning: Approaching rate limit", color = Color.Red)
    }
}
```

### Manual Usage Check

```kotlin
class MyViewModel @Inject constructor(
    private val usageRepository: UsageRepository
) : ViewModel() {

    fun checkIfCanMakeRequest() {
        viewModelScope.launch {
            if (usageRepository.canMakeRequest(AiProvider.GEMINI)) {
                // Safe to make request
            } else {
                val waitTime = usageRepository.getWaitTimeMessage(AiProvider.GEMINI)
                showError("Rate limited. Wait: $waitTime")
            }
        }
    }
}
```

### Resetting Usage

```kotlin
fun resetUsageCounters() {
    viewModelScope.launch {
        usageRepository.resetAllUsage()
        // UI will automatically update via StateFlow
    }
}
```

## Error Handling

When rate limits are exceeded, providers throw `RateLimitException`:

```kotlin
class RateLimitException(message: String) : Exception(message)
```

**Error Messages**:
- `"Gemini rate limit reached. Try again in 45s"`
- `"Gemini rate limit reached. Try again in 2m 30s"`
- `"Gemini rate limit reached. Try again in 1h 15m"`
- `"Groq rate limit reached. Try again in 30s"`

These are shown to users via standard error handling flows.

## Performance Considerations

### Memory
- Stores only timestamps for last 24 hours
- Typical: ~50-100 timestamps per provider = ~1KB
- Maximum: 1500 timestamps (Gemini daily limit) = ~12KB

### CPU
- O(n) filtering operations where n = number of timestamps
- Runs in background coroutines (no UI blocking)
- Mutex ensures thread-safety with minimal contention

### Disk I/O
- Uses SharedPreferences (fast key-value store)
- Writes only on successful API requests (~1-5 per minute max)
- Reads cached in memory via StateFlow

## Future Enhancements

Potential improvements for future versions:

1. **Token Usage Tracking**: Track TPM (tokens per minute) limits
2. **Provider-Specific Analytics**: Success rates, average response times
3. **Cost Tracking**: Estimate API costs for paid tiers
4. **Historical Data**: Show usage trends over time
5. **Smart Scheduling**: Delay requests to optimize rate limit usage
6. **Per-User Limits**: Track limits separately for different user accounts
7. **Background Sync**: Sync usage data to cloud for cross-device awareness

## Troubleshooting

### Issue: Usage stats not updating in UI

**Solution**: Ensure ViewModel is observing StateFlow correctly:
```kotlin
val geminiUsage: StateFlow<ProviderUsage> = usageRepository.observeGeminiUsage()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProviderUsage(0, 0, 15, 1500))
```

### Issue: Rate limits not being enforced

**Solution**: Verify `recordRequest()` is called AFTER successful API responses:
```kotlin
val response = api.makeRequest(...)
usageTracker.recordRequest(AiProvider.GEMINI)  // Must be after success
```

### Issue: Usage persisting after app restart when it shouldn't

**Solution**: Check SharedPreferences are being cleared properly:
```kotlin
context.getSharedPreferences("ai_usage_tracker", Context.MODE_PRIVATE)
    .edit()
    .clear()
    .apply()
```

## Summary

This implementation provides:
- ✅ Accurate rate limiting using sliding windows
- ✅ Pre-request checks to prevent API errors
- ✅ Automatic request tracking
- ✅ Real-time UI updates
- ✅ Persistent storage across restarts
- ✅ Smart provider selection in AUTO mode
- ✅ User-friendly usage statistics display
- ✅ Comprehensive test coverage

The system is production-ready and follows Android/Kotlin best practices with clean architecture, dependency injection, and reactive state management.
