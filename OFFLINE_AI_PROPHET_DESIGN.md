# Offline-First Prophet AI: Technical Design Document

**Feature Name:** Prophet AI - Daily Spiritual Insights
**Priority:** Improvement #3
**Status:** Design Phase
**Target:** Privacy-first, offline-capable daily spiritual guidance
**Estimated Timeline:** 4 weeks total (Models: 2 weeks, Inference: 1 week, UI: 1 week)

---

## Table of Contents

1. [Executive Summary](#executive-summary)
2. [Technical Architecture](#technical-architecture)
3. [Model Selection & Specifications](#model-selection--specifications)
4. [Storage Strategy](#storage-strategy)
5. [Implementation Plan](#implementation-plan)
6. [UI/UX Design](#uiux-design)
7. [Performance Benchmarks](#performance-benchmarks)
8. [Privacy & Security](#privacy--security)
9. [Timeline & Milestones](#timeline--milestones)
10. [Future Enhancements](#future-enhancements)

---

## Executive Summary

Prophet AI brings personalized daily spiritual insights to users through an **offline-first architecture** that prioritizes privacy, performance, and reliability. Users receive daily guidance across multiple spiritual dimensions:

- **Numerology Life Path Analysis** - Personal day/month/year cycles
- **Tarot Daily Draw** - Single-card guidance with interpretation
- **Astrology Transit Insights** - Current planetary influences
- **Meditation & Affirmation Prompts** - Practice recommendations
- **Chakra & Dosha Guidance** - Energy balancing for the day

### Key Features

- **100% Offline Operation** - No data leaves the device
- **Morning Notifications** - Daily insight cards at user-set time
- **Shareable Cards** - Beautiful UI for social sharing
- **Intelligent Caching** - Room database for previous insights
- **Cloud Sync (Optional)** - Backup to user's cloud provider when online
- **Lightweight Models** - ~50-80MB total footprint
- **Fast Inference** - <3 seconds for daily generation

### Core Value Propositions

1. **Privacy-First**: All spiritual insights generated on-device
2. **Reliable**: Works offline, perfect for morning rituals
3. **Personalized**: Uses user's complete spiritual profile
4. **Beautiful**: Shareable cards with gradients and spiritual iconography
5. **Low Battery Impact**: Efficient inference with NNAPI acceleration

---

## Technical Architecture

### High-Level System Design

```
┌─────────────────────────────────────────────────────────────┐
│                     SpiritAtlas App                          │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  ┌──────────────────┐     ┌──────────────────┐              │
│  │  Daily Insight    │────▶│  Prophet AI      │              │
│  │  UI Layer         │     │  Orchestrator    │              │
│  └──────────────────┘     └────────┬─────────┘              │
│                                     │                         │
│                    ┌────────────────┼────────────────┐       │
│                    │                │                │        │
│            ┌───────▼──────┐ ┌──────▼──────┐ ┌──────▼──────┐│
│            │ Numerology   │ │   Tarot     │ │  Astrology  ││
│            │ Calculator   │ │  Generator  │ │  Calculator ││
│            └──────────────┘ └─────────────┘ └─────────────┘│
│                                     │                         │
│                            ┌────────▼─────────┐              │
│                            │ ONNX Runtime     │              │
│                            │ Inference Engine │              │
│                            └────────┬─────────┘              │
│                                     │                         │
│                    ┌────────────────┼────────────────┐       │
│                    │                │                │        │
│            ┌───────▼──────┐ ┌──────▼──────┐ ┌──────▼──────┐│
│            │ GPT-2 Tiny   │ │ DistilGPT2  │ │ TinyBERT    ││
│            │ (Affirmations)│ │  (Insights) │ │ (Guidance)  ││
│            │   ~30MB       │ │   ~40MB     │ │   ~20MB     ││
│            └──────────────┘ └─────────────┘ └─────────────┘│
│                                     │                         │
│                            ┌────────▼─────────┐              │
│                            │  Room Database   │              │
│                            │  (Cache Layer)   │              │
│                            └──────────────────┘              │
│                                                               │
├─────────────────────────────────────────────────────────────┤
│              Android System Services                         │
├─────────────────────────────────────────────────────────────┤
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │
│  │ WorkManager  │  │ Notification │  │   NNAPI      │      │
│  │ (Scheduler)  │  │   System     │  │ (Hardware    │      │
│  │              │  │              │  │ Acceleration)│      │
│  └──────────────┘  └──────────────┘  └──────────────┘      │
└─────────────────────────────────────────────────────────────┘
```

### Component Responsibilities

#### 1. Prophet AI Orchestrator (`ProphetAiOrchestrator.kt`)

- **Location:** `/domain/src/main/java/com/spiritatlas/domain/ai/ProphetAiOrchestrator.kt`
- **Responsibilities:**
  - Coordinates model inference requests
  - Manages caching strategy (check cache → generate → store)
  - Handles fallback strategies (offline → lightweight cloud → full cloud)
  - Assembles `DailyInsight` from multiple data sources

#### 2. ONNX Inference Manager (`OnnxInferenceManager.kt`)

- **Location:** `/data/src/main/java/com/spiritatlas/data/ai/onnx/OnnxInferenceManager.kt`
- **Responsibilities:**
  - Initializes ONNX Runtime with NNAPI acceleration
  - Loads models from assets on demand (lazy loading)
  - Manages tokenization (BPE tokenizer for GPT models)
  - Executes inference with timeout protection
  - Thread-safe model lifecycle management

#### 3. Daily Insight Repository (`DailyInsightRepository.kt`)

- **Location:** `/data/src/main/java/com/spiritatlas/data/repository/DailyInsightRepository.kt`
- **Responsibilities:**
  - Room database operations (CRUD for daily insights)
  - Cache expiration logic (invalidate at midnight)
  - Sync with cloud storage (optional)
  - History management (keep 30 days, archive older)

#### 4. Background Insight Worker (`DailyInsightWorker.kt`)

- **Location:** `/data/src/main/java/com/spiritatlas/data/worker/DailyInsightWorker.kt`
- **Responsibilities:**
  - Scheduled daily insight generation (uses WorkManager)
  - Pre-generates next day's insight at 11 PM
  - Posts morning notification with insight card
  - Handles battery optimization constraints

#### 5. Notification Manager (`InsightNotificationManager.kt`)

- **Location:** `/data/src/main/java/com/spiritatlas/data/notification/InsightNotificationManager.kt`
- **Responsibilities:**
  - Creates rich notifications with card preview
  - Deep links to daily insight screen
  - Notification preferences (time, frequency)
  - Quiet hours and DND integration

---

## Model Selection & Specifications

### Overview

We select three lightweight transformer models optimized for mobile inference via ONNX Runtime. Total storage: **~90MB** (compressed), **~120MB** (runtime memory peak).

### Model 1: DistilGPT2 (Primary Text Generation)

**Use Case:** Daily insight narratives, transit interpretations, opportunity descriptions

**Specifications:**
- **Base Model:** `distilgpt2` from Hugging Face
- **Parameters:** 82M (distilled from GPT-2 124M)
- **Quantization:** INT8 (8-bit weights, 4x size reduction)
- **ONNX Size:** ~40MB (quantized)
- **Runtime Memory:** ~60MB peak
- **Inference Speed:** 1.5-2.5 seconds per insight (50-100 tokens)
- **Hardware Acceleration:** NNAPI on Android 8+ (CPU fallback)

**Why DistilGPT2?**
- Proven performance on creative text generation
- Excellent balance of quality vs. size
- Strong community support and pre-optimized ONNX exports
- Handles spiritual/esoteric vocabulary well with fine-tuning

**Fine-Tuning Dataset:**
- 10,000 sample spiritual insights (numerology, astrology, tarot)
- Curated from public domain sources + generated training data
- Style: Compassionate, insightful, actionable
- Format: `[CONTEXT] numerology day=3, moon=waxing [INSIGHT] Today brings...`

**Export Command:**
```bash
python convert_distilgpt2_to_onnx.py \
  --model distilgpt2 \
  --quantize int8 \
  --optimize-for-mobile \
  --output models/distilgpt2_int8.onnx
```

---

### Model 2: GPT-2 Tiny (Affirmations & Short Prompts)

**Use Case:** Affirmations, meditation focus phrases, evening reflections

**Specifications:**
- **Base Model:** Custom GPT-2 Tiny (28M parameters, smaller than DistilGPT2)
- **Parameters:** 28M
- **Quantization:** INT8
- **ONNX Size:** ~30MB (quantized)
- **Runtime Memory:** ~40MB peak
- **Inference Speed:** 0.8-1.2 seconds per affirmation (20-30 tokens)
- **Hardware Acceleration:** NNAPI

**Why GPT-2 Tiny?**
- Ultra-lightweight for quick affirmation generation
- Lower battery impact (faster inference)
- Sufficient for short-form content
- Dedicated model prevents context contamination

**Training Focus:**
- 5,000 affirmations across spiritual traditions
- Chakra-specific affirmations
- Numerology-aligned intentions
- Format: `[CHAKRA] heart [ENERGY] compassion [AFFIRMATION] I open...`

---

### Model 3: TinyBERT (Classification & Guidance Routing)

**Use Case:** Classify user state, recommend practice types, prioritize insights

**Specifications:**
- **Base Model:** `TinyBERT` (14.5M parameters)
- **Parameters:** 14.5M (distilled from BERT-base 110M)
- **Quantization:** INT8
- **ONNX Size:** ~20MB (quantized)
- **Runtime Memory:** ~30MB peak
- **Inference Speed:** <500ms per classification
- **Hardware Acceleration:** NNAPI

**Why TinyBERT?**
- Excellent for understanding/classification tasks
- Fast enough for real-time preference detection
- Low memory footprint
- Handles contextual understanding better than GPT models

**Use Cases in Prophet AI:**
1. **Sentiment Analysis** - Detect user's current emotional state from journal entries
2. **Practice Recommendation** - Map user profile → optimal meditation type
3. **Insight Prioritization** - Rank which spiritual insights to emphasize today
4. **Energy Classification** - Determine chakra focus based on recent activities

**Training Data:**
- 8,000 labeled examples of spiritual states and practices
- Multi-class classification (chakra focus, dosha balance, energy level)

---

### Model Storage Structure

```
app/src/main/assets/onnx_models/
├── distilgpt2_int8.onnx           # 40MB - Primary text generation
├── distilgpt2_int8_tokenizer.json # 2MB - BPE tokenizer
├── gpt2_tiny_int8.onnx            # 30MB - Affirmations
├── gpt2_tiny_int8_tokenizer.json  # 2MB - BPE tokenizer
├── tinybert_int8.onnx             # 20MB - Classification
├── tinybert_tokenizer.json        # 2MB - WordPiece tokenizer
├── model_metadata.json            # 100KB - Version, checksums, configs
└── README.md                      # Documentation
```

**Total Asset Size:** ~96MB (compressed in APK)
**Runtime Peak Memory:** ~130MB (all models loaded simultaneously)
**Typical Usage Memory:** ~60-80MB (lazy loading, one model active)

---

### Model Loading Strategy (Lazy Loading)

```kotlin
class OnnxInferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var distilGpt2Session: OrtSession? = null
    private var gpt2TinySession: OrtSession? = null
    private var tinyBertSession: OrtSession? = null

    private val sessionLock = ReentrantLock()

    /**
     * Load model only when needed, keep in memory for session
     */
    suspend fun loadDistilGpt2(): OrtSession = withContext(Dispatchers.IO) {
        sessionLock.withLock {
            distilGpt2Session ?: createSession("onnx_models/distilgpt2_int8.onnx")
                .also { distilGpt2Session = it }
        }
    }

    /**
     * Release models when app goes to background
     */
    fun releaseModels() {
        sessionLock.withLock {
            distilGpt2Session?.close()
            gpt2TinySession?.close()
            tinyBertSession?.close()
            distilGpt2Session = null
            gpt2TinySession = null
            tinyBertSession = null
        }
    }
}
```

---

## Storage Strategy

### Database Schema (Room)

```kotlin
// File: data/src/main/java/com/spiritatlas/data/db/entities/DailyInsightEntity.kt

@Entity(
    tableName = "daily_insights",
    indices = [
        Index(value = ["profile_id", "date"], unique = true),
        Index(value = ["date"]),
        Index(value = ["generated_at"])
    ]
)
data class DailyInsightEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "profile_id")
    val profileId: String,

    @ColumnInfo(name = "date")
    val date: Long, // LocalDate as epoch day

    @ColumnInfo(name = "personal_year")
    val personalYear: Int,

    @ColumnInfo(name = "personal_month")
    val personalMonth: Int,

    @ColumnInfo(name = "personal_day")
    val personalDay: Int,

    @ColumnInfo(name = "theme")
    val theme: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "opportunities_json")
    val opportunitiesJson: String, // JSON array

    @ColumnInfo(name = "challenges_json")
    val challengesJson: String, // JSON array

    @ColumnInfo(name = "optimal_times_json")
    val optimalTimesJson: String,

    @ColumnInfo(name = "practice_json")
    val practiceJson: String,

    @ColumnInfo(name = "energy_focus_json")
    val energyFocusJson: String,

    @ColumnInfo(name = "transit_info_json")
    val transitInfoJson: String?,

    @ColumnInfo(name = "generated_at")
    val generatedAt: Long, // LocalDateTime as epoch second

    @ColumnInfo(name = "ai_provider")
    val aiProvider: String, // "onnx_offline", "claude_online", etc.

    @ColumnInfo(name = "generation_time_ms")
    val generationTimeMs: Long, // Performance tracking

    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean = false, // Cloud sync status

    @ColumnInfo(name = "version")
    val version: Int = 1 // Schema version for migrations
)

@Dao
interface DailyInsightDao {
    @Query("SELECT * FROM daily_insights WHERE profile_id = :profileId AND date = :epochDay")
    suspend fun getInsightForDate(profileId: String, epochDay: Long): DailyInsightEntity?

    @Query("SELECT * FROM daily_insights WHERE profile_id = :profileId ORDER BY date DESC LIMIT :limit")
    suspend fun getRecentInsights(profileId: String, limit: Int = 30): List<DailyInsightEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInsight(insight: DailyInsightEntity)

    @Query("DELETE FROM daily_insights WHERE date < :cutoffEpochDay")
    suspend fun deleteOlderThan(cutoffEpochDay: Long)

    @Query("SELECT COUNT(*) FROM daily_insights WHERE profile_id = :profileId")
    suspend fun getInsightCount(profileId: String): Int

    @Query("UPDATE daily_insights SET is_synced = 1 WHERE id = :insightId")
    suspend fun markAsSynced(insightId: String)
}
```

### Cache Strategy

#### Cache Invalidation Rules

1. **Time-Based Expiration**
   - Cache is valid only for the target date
   - At midnight local time, previous day's cache becomes historical record
   - Next day's cache (pre-generated) becomes active

2. **Profile Change Invalidation**
   - If user updates their birth date, location, or name → invalidate all future insights
   - Past insights remain as historical record

3. **Model Update Invalidation**
   - When ONNX models are updated → flag for regeneration
   - Use `version` field in database to track model versions

4. **Storage Limits**
   - Keep last 30 days in active cache
   - Archive older insights to compressed JSON (optional cloud backup)
   - Maximum 100 insights per profile to prevent bloat

#### Pre-Generation Strategy

**Why Pre-Generate?**
- Ensures instant display when user opens app in morning
- Avoids cold-start latency (model loading + inference)
- Better battery efficiency (scheduled vs. on-demand)

**When to Pre-Generate:**
- **11:00 PM Local Time** - Generate next day's insight
- **Fallback:** If missed (device off), generate on first app open next day
- **User Override:** Manual refresh button for re-generation

**WorkManager Configuration:**

```kotlin
// File: data/src/main/java/com/spiritatlas/data/worker/DailyInsightWorker.kt

class DailyInsightWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val profileId = inputData.getString(KEY_PROFILE_ID) ?: return Result.failure()
            val targetDate = LocalDate.now().plusDays(1) // Tomorrow

            // Check if already generated
            val existing = dailyInsightRepository.getInsightForDate(profileId, targetDate)
            if (existing != null) {
                return Result.success()
            }

            // Generate new insight
            val insight = prophetAiOrchestrator.generateDailyInsight(profileId, targetDate)
            dailyInsightRepository.saveInsight(insight)

            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) {
                Result.retry() // Retry with exponential backoff
            } else {
                Result.failure()
            }
        }
    }

    companion object {
        const val KEY_PROFILE_ID = "profile_id"

        fun scheduleDailyGeneration(workManager: WorkManager, profileId: String) {
            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true) // Don't drain battery
                .build()

            val inputData = workDataOf(KEY_PROFILE_ID to profileId)

            val dailyWork = PeriodicWorkRequestBuilder<DailyInsightWorker>(
                repeatInterval = 1,
                repeatIntervalTimeUnit = TimeUnit.DAYS
            )
                .setConstraints(constraints)
                .setInputData(inputData)
                .setInitialDelay(calculateDelayUntil11PM(), TimeUnit.MILLISECONDS)
                .build()

            workManager.enqueueUniquePeriodicWork(
                "daily_insight_generation_$profileId",
                ExistingPeriodicWorkPolicy.KEEP,
                dailyWork
            )
        }

        private fun calculateDelayUntil11PM(): Long {
            val now = LocalDateTime.now()
            val target = LocalDateTime.of(now.toLocalDate(), LocalTime.of(23, 0))
            val targetTime = if (now.isAfter(target)) {
                target.plusDays(1)
            } else {
                target
            }
            return Duration.between(now, targetTime).toMillis()
        }
    }
}
```

---

### Model Asset Compression

#### APK Size Optimization

**Compression Strategy:**
- Use ONNX built-in compression (weights already quantized to INT8)
- Store models as `.onnx` (already optimized binary format)
- No additional ZIP compression (minimal gains, adds extraction overhead)

**APK Splits (Optional):**
- Use Android App Bundles to deliver models on-demand
- Base APK: ~20MB (app code + minimal assets)
- Dynamic Feature Module: ~96MB (ONNX models)
- Users download models on first launch or Wi-Fi

**Implementation:**

```gradle
// app/build.gradle.kts

android {
    bundle {
        language {
            enableSplit = false // Keep all languages in base
        }
        density {
            enableSplit = false
        }
        abi {
            enableSplit = true // Split by architecture (arm64-v8a, armeabi-v7a)
        }
    }

    dynamicFeatures += setOf(":feature:prophet_ai_models")
}
```

```gradle
// feature/prophet_ai_models/build.gradle.kts

plugins {
    id("com.android.dynamic-feature")
}

android {
    namespace = "com.spiritatlas.feature.prophet_ai_models"
}

dependencies {
    implementation(project(":app"))
}
```

---

## Implementation Plan

### Phase 1: Model Preparation & Export (Week 1)

**Tasks:**

1. **Environment Setup**
   - Install ONNX Runtime, Hugging Face Transformers, PyTorch
   - Set up Python virtual environment with dependencies
   - Verify ONNX conversion tools

2. **Model Fine-Tuning**
   - Collect/generate training data (10,000 spiritual insights)
   - Fine-tune DistilGPT2 on spiritual text corpus
   - Fine-tune GPT-2 Tiny on affirmations
   - Fine-tune TinyBERT on classification tasks

3. **ONNX Conversion & Quantization**
   - Export models to ONNX format
   - Apply INT8 quantization for size reduction
   - Validate inference output matches PyTorch baseline
   - Benchmark inference speed on Android emulator

4. **Model Testing**
   - Unit tests for tokenization accuracy
   - Integration tests for ONNX Runtime on Android
   - Quality tests for generated text (coherence, relevance)

**Deliverables:**
- `distilgpt2_int8.onnx` (40MB)
- `gpt2_tiny_int8.onnx` (30MB)
- `tinybert_int8.onnx` (20MB)
- Tokenizer JSON files
- Model conversion scripts
- Test results and benchmarks

---

### Phase 2: Inference Engine Integration (Week 2)

**Tasks:**

1. **ONNX Runtime Android Integration**
   - Add ONNX Runtime dependency to `data` module
   - Create `OnnxInferenceManager` with lazy loading
   - Implement tokenization (BPE for GPT, WordPiece for BERT)
   - Add NNAPI acceleration with CPU fallback

2. **Prophet AI Orchestrator**
   - Create domain service to coordinate inference
   - Implement prompt engineering for each insight type
   - Add fallback logic (offline → cloud → cached)
   - Error handling and timeout protection

3. **Daily Insight Repository**
   - Set up Room database schema
   - Implement DAOs for CRUD operations
   - Add cache invalidation logic
   - Write unit tests for repository layer

4. **Background Worker Setup**
   - Create `DailyInsightWorker` with WorkManager
   - Schedule daily pre-generation (11 PM)
   - Implement retry logic for failed generations
   - Add battery optimization constraints

**Deliverables:**
- `OnnxInferenceManager.kt` (fully tested)
- `ProphetAiOrchestrator.kt` (domain layer)
- `DailyInsightRepository.kt` (data layer)
- `DailyInsightWorker.kt` (background scheduler)
- Room database migrations
- 90%+ test coverage for inference layer

**Gradle Dependencies:**

```kotlin
// data/build.gradle.kts

dependencies {
    // ONNX Runtime for Android
    implementation("com.microsoft.onnxruntime:onnxruntime-android:1.17.0")

    // Room for caching
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // WorkManager for background tasks
    implementation(libs.androidx.work.runtime)

    // JSON serialization
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    kapt(libs.moshi.codegen)
}
```

---

### Phase 3: UI Implementation (Week 3)

**Tasks:**

1. **Daily Insight Screen**
   - Create `DailyInsightScreen.kt` in `feature:home` module
   - Design card layout with spiritual imagery
   - Implement swipe gestures for history browsing
   - Add pull-to-refresh for manual regeneration

2. **Notification System**
   - Create `InsightNotificationManager.kt`
   - Design rich notification with card preview
   - Add deep linking to insight screen
   - Implement notification preferences (time, enabled/disabled)

3. **Shareable Cards**
   - Create composable card renderer with Canvas
   - Add gradient backgrounds matching user's theme
   - Include spiritual icons (chakra, zodiac, tarot)
   - Export as bitmap for sharing (Instagram, Twitter, etc.)

4. **Settings Integration**
   - Add "Daily Insights" section to Settings screen
   - Toggle for notifications (enabled/disabled)
   - Time picker for preferred insight delivery
   - Regeneration history and statistics

**Deliverables:**
- `DailyInsightScreen.kt` (Compose UI)
- `InsightNotificationManager.kt`
- `ShareableInsightCard.kt` (Canvas rendering)
- Settings UI updates
- UI tests with Compose test framework

---

### Phase 4: Testing & Optimization (Week 4)

**Tasks:**

1. **Performance Optimization**
   - Profile memory usage with Android Studio Profiler
   - Optimize model loading (reduce cold-start time)
   - Benchmark inference speed across device tiers
   - Reduce battery drain (optimize WorkManager schedule)

2. **End-to-End Testing**
   - Test full flow: generation → cache → notification → display
   - Test offline mode (airplane mode, no network)
   - Test cache invalidation (midnight rollover, profile updates)
   - Test fallback scenarios (model load failure, inference timeout)

3. **Quality Assurance**
   - Manual testing on 3+ physical devices (low/mid/high tier)
   - Validate insight quality (coherence, relevance, personalization)
   - Test notification delivery across Android versions (8-14)
   - Verify shareable cards render correctly

4. **Documentation**
   - Write developer guide for model updates
   - Document inference API for future extensions
   - Create user-facing FAQ for Prophet AI feature
   - Performance benchmarks and optimization guide

**Deliverables:**
- Performance report (memory, CPU, battery)
- Test coverage report (target: 85%+)
- Device compatibility matrix
- Documentation (developer + user guides)

---

## UI/UX Design

### Daily Insight Screen

```
┌────────────────────────────────────────┐
│  ☰  Prophet AI        🔔  ⚙️           │  ← Top bar
├────────────────────────────────────────┤
│                                         │
│  ┌──────────────────────────────────┐ │
│  │                                   │ │
│  │   [Spiritual Icon - Chakra/Moon] │ │
│  │                                   │ │
│  │   WEDNESDAY, DECEMBER 11, 2025   │ │
│  │                                   │ │
│  │   ✨ "Day of Communication" ✨   │ │  ← Theme
│  │                                   │ │
│  │   Your personal day 3 brings     │ │
│  │   creative expression and social │ │
│  │   connections. The waxing moon   │ │  ← Overview
│  │   in Gemini amplifies...         │ │
│  │                                   │ │
│  │   [Gradient Background]          │ │
│  │                                   │ │
│  └──────────────────────────────────┘ │
│                                         │
│  ◀ Yesterday    Today    Tomorrow ▶    │  ← Date navigator
│                                         │
├────────────────────────────────────────┤
│  📊 Opportunities                       │
│  • Creative projects (9am-12pm)        │
│  • Important conversations (3-5pm)     │
│  • Networking events favored           │
│                                         │
│  ⚠️ Challenges                          │
│  • Scattered energy - stay focused     │
│  • Solution: Prioritize top 3 tasks    │
│                                         │
│  🧘 Practice                            │
│  Meditation: Throat chakra activation  │
│  Affirmation: "I speak my truth..."    │
│                                         │
│  🕐 Optimal Times                       │
│  Morning ⚡ HIGH - Deep work            │
│  Midday ⚡ MODERATE - Meetings          │
│  Evening ⚡ LOW - Reflection            │
│                                         │
│  🌙 Transit Info                        │
│  Moon in Gemini (communication)        │
│  Mercury trine Jupiter (expansion)     │
│                                         │
└────────────────────────────────────────┘
│  [Share Card]  [Save to Journal]       │  ← Actions
└────────────────────────────────────────┘
```

### Notification Design

**Collapsed State:**
```
┌────────────────────────────────────────┐
│ 🌟 Prophet AI                           │
│ Your daily insight is ready             │
│ "Day of Communication" - Tap to explore │
│ 6:00 AM                                 │
└────────────────────────────────────────┘
```

**Expanded State:**
```
┌────────────────────────────────────────┐
│ 🌟 Prophet AI                           │
│                                         │
│ WEDNESDAY, DECEMBER 11                  │
│ Personal Day 3 - Communication          │
│                                         │
│ [Mini Card Preview with Gradient]      │
│                                         │
│ Your personal day 3 brings creative    │
│ expression and social connections...   │
│                                         │
│ [View Full Insight]  [Dismiss]         │
│ 6:00 AM                                 │
└────────────────────────────────────────┘
```

### Shareable Card Design

**Instagram Story Format (1080x1920px)**

```
┌────────────────────────────────────────┐
│                                         │
│   [Gradient Background - User Theme]   │
│                                         │
│          [Spiritual Icon]               │
│                                         │
│      WEDNESDAY, DECEMBER 11             │
│                                         │
│    ✨ Day of Communication ✨          │
│                                         │
│  "Your personal day 3 brings creative  │
│   expression and social connections.   │
│   The waxing moon in Gemini amplifies  │
│   your voice and magnetism."           │
│                                         │
│   🧘 Today's Practice                   │
│   Throat chakra meditation              │
│                                         │
│   💬 Affirmation                        │
│   "I speak my truth with clarity and   │
│    compassion."                         │
│                                         │
│                                         │
│   Generated by SpiritAtlas Prophet AI  │
│   [App Logo]                            │
│                                         │
└────────────────────────────────────────┘
```

**Export Options:**
- Instagram Story (1080x1920, 9:16)
- Instagram Post (1080x1080, 1:1)
- Twitter Card (1200x628, 1.91:1)
- Custom (user-selected dimensions)

---

## Performance Benchmarks

### Target Performance Metrics

| Metric | Target | Acceptable | Poor |
|--------|--------|-----------|------|
| **Cold Start (first inference)** | <3s | 3-5s | >5s |
| **Warm Start (model loaded)** | <1.5s | 1.5-3s | >3s |
| **Memory Peak** | <130MB | 130-200MB | >200MB |
| **Battery Drain (daily)** | <1% | 1-3% | >3% |
| **APK Size Increase** | <100MB | 100-150MB | >150MB |
| **Cache Hit Rate** | >95% | 90-95% | <90% |
| **Notification Delivery** | 99% | 95-99% | <95% |

### Device Tier Benchmarks

#### High-End Devices (Pixel 7+, Galaxy S22+)

- **Inference Time:** 0.8-1.2s (with NNAPI)
- **Memory Usage:** 80-100MB peak
- **Model Load Time:** 1.5-2s (cold start)
- **Battery Impact:** <0.5% daily

**Optimization:**
- NNAPI acceleration enabled
- All three models can stay in memory
- Pre-generation at 11 PM with no noticeable impact

#### Mid-Range Devices (Pixel 4a, Galaxy A52)

- **Inference Time:** 1.5-2.5s (with NNAPI)
- **Memory Usage:** 100-130MB peak
- **Model Load Time:** 2.5-3.5s (cold start)
- **Battery Impact:** 0.5-1% daily

**Optimization:**
- NNAPI acceleration enabled
- Lazy loading (unload inactive models)
- Pre-generation may be delayed if battery <30%

#### Low-End Devices (Android 8-9, 2GB RAM)

- **Inference Time:** 3-5s (CPU only, NNAPI unavailable)
- **Memory Usage:** 120-150MB peak
- **Model Load Time:** 4-6s (cold start)
- **Battery Impact:** 1-2% daily

**Optimization:**
- CPU-only inference (no NNAPI)
- Aggressive model unloading after use
- Pre-generation only when charging
- Fallback to cloud generation if available

---

### Optimization Techniques

#### 1. NNAPI Acceleration

```kotlin
// Enable NNAPI in OnnxInferenceManager

val sessionOptions = OrtSession.SessionOptions().apply {
    // Use NNAPI if available (Android 8.1+)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
        addNnapi() // Hardware acceleration
    }

    // Optimization level
    setOptimizationLevel(OrtSession.SessionOptions.OptLevel.ALL_OPT)

    // Thread pool for CPU fallback
    setIntraOpNumThreads(2) // Use 2 threads for CPU inference
    setInterOpNumThreads(1)

    // Memory optimizations
    setMemoryPatternOptimization(true)
}
```

#### 2. Model Lazy Loading

```kotlin
// Only load models when needed, unload after use

suspend fun generateInsight(profile: UserProfile): DailyInsight {
    val distilGpt2 = loadDistilGpt2() // Load on demand

    try {
        val insight = runInference(distilGpt2, profile)
        return insight
    } finally {
        // Unload if memory pressure detected
        if (isMemoryPressure()) {
            releaseModel(distilGpt2)
        }
    }
}

private fun isMemoryPressure(): Boolean {
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val memoryInfo = ActivityManager.MemoryInfo()
    activityManager.getMemoryInfo(memoryInfo)

    val availableMemoryMB = memoryInfo.availMem / (1024 * 1024)
    return availableMemoryMB < 200 // Less than 200MB available
}
```

#### 3. Tokenization Caching

```kotlin
// Cache tokenizer results for common prompts

private val tokenCache = LruCache<String, LongArray>(50) // Cache 50 prompts

fun tokenize(text: String): LongArray {
    return tokenCache.get(text) ?: run {
        val tokens = tokenizer.encode(text)
        tokenCache.put(text, tokens)
        tokens
    }
}
```

#### 4. Background Priority

```kotlin
// Lower inference priority when running in background

val threadPriority = if (isAppInForeground()) {
    Process.THREAD_PRIORITY_DEFAULT
} else {
    Process.THREAD_PRIORITY_BACKGROUND // Lower priority
}

Thread.currentThread().priority = threadPriority
```

---

## Privacy & Security

### Offline-First Privacy Guarantees

1. **Zero Data Transmission**
   - All insights generated on-device
   - No user data sent to servers
   - No analytics on spiritual content

2. **Local Storage Only**
   - Room database encrypted at rest (SQLCipher optional)
   - No cloud sync by default
   - User opt-in required for backup

3. **Model Privacy**
   - Models shipped with app (no dynamic downloads by default)
   - No telemetry on inference requests
   - No model usage tracking

4. **Notification Privacy**
   - Notifications show preview only if device unlocked
   - Sensitive content hidden on lock screen
   - User control over notification content detail

### Security Measures

#### 1. Encrypted Database (Optional)

```kotlin
// Use SQLCipher for Room database encryption

@Database(
    entities = [DailyInsightEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ProphetAiDatabase : RoomDatabase() {
    abstract fun dailyInsightDao(): DailyInsightDao

    companion object {
        fun create(context: Context): ProphetAiDatabase {
            val passphrase = getOrCreatePassphrase(context)

            return Room.databaseBuilder(
                context,
                ProphetAiDatabase::class.java,
                "prophet_ai.db"
            )
                .openHelperFactory(SupportFactory(SQLiteDatabase.getBytes(passphrase)))
                .build()
        }

        private fun getOrCreatePassphrase(context: Context): String {
            // Store passphrase in EncryptedSharedPreferences
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            val prefs = EncryptedSharedPreferences.create(
                context,
                "prophet_ai_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

            return prefs.getString("db_passphrase", null) ?: run {
                val newPassphrase = UUID.randomUUID().toString()
                prefs.edit().putString("db_passphrase", newPassphrase).apply()
                newPassphrase
            }
        }
    }
}
```

#### 2. Model Integrity Verification

```kotlin
// Verify model checksums to prevent tampering

private suspend fun loadModelWithVerification(assetPath: String): OrtSession {
    val modelBytes = context.assets.open(assetPath).readBytes()

    // Verify SHA-256 checksum
    val actualChecksum = MessageDigest.getInstance("SHA-256")
        .digest(modelBytes)
        .joinToString("") { "%02x".format(it) }

    val expectedChecksum = modelMetadata[assetPath]?.checksum
    if (actualChecksum != expectedChecksum) {
        throw SecurityException("Model checksum mismatch: $assetPath")
    }

    return ortEnvironment.createSession(modelBytes)
}
```

#### 3. Secure Notification Content

```kotlin
// Hide sensitive content on lock screen

val notification = NotificationCompat.Builder(context, CHANNEL_ID)
    .setContentTitle("Prophet AI")
    .setContentText("Your daily insight is ready")
    .setSmallIcon(R.drawable.ic_prophet_ai)
    // Full content only when unlocked
    .setPublicVersion(
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Prophet AI")
            .setContentText("New insight available")
            .setSmallIcon(R.drawable.ic_prophet_ai)
            .build()
    )
    .build()
```

---

## Timeline & Milestones

### Overall Timeline: 4 Weeks

```
Week 1: Model Preparation & Export
├─ Day 1-2: Environment setup, data collection
├─ Day 3-5: Fine-tuning (DistilGPT2, GPT-2 Tiny, TinyBERT)
├─ Day 6-7: ONNX conversion, quantization, validation
└─ Deliverable: 3 quantized ONNX models + tokenizers

Week 2: Inference Engine Integration
├─ Day 8-9: ONNX Runtime Android setup, OnnxInferenceManager
├─ Day 10-11: ProphetAiOrchestrator, prompt engineering
├─ Day 12-13: Room database, DailyInsightRepository
├─ Day 14: WorkManager setup, background generation
└─ Deliverable: Functional inference pipeline with caching

Week 3: UI Implementation
├─ Day 15-16: DailyInsightScreen, card layout
├─ Day 17-18: Notification system, deep linking
├─ Day 19-20: Shareable cards, Canvas rendering
├─ Day 21: Settings integration, preferences
└─ Deliverable: Complete UI with notifications

Week 4: Testing & Optimization
├─ Day 22-23: Performance profiling, optimization
├─ Day 24-25: End-to-end testing, QA on devices
├─ Day 26-27: Quality validation, edge case testing
├─ Day 28: Documentation, final review
└─ Deliverable: Production-ready feature
```

### Milestones

- **M1 (Day 7):** Models ready and validated
- **M2 (Day 14):** Inference pipeline functional
- **M3 (Day 21):** UI complete with notifications
- **M4 (Day 28):** Production release candidate

---

## Future Enhancements

### Phase 2 Features (Post-Launch)

1. **Voice Insights**
   - TTS integration for morning audio insight
   - Bedtime story mode with calming voice
   - Guided meditation audio generation

2. **Weekly & Monthly Summaries**
   - Aggregate insights into trends
   - Identify patterns in challenges/opportunities
   - Long-term spiritual progress tracking

3. **Interactive Tarot**
   - 3-card spread generation
   - Interactive card meanings
   - Reversed card interpretations

4. **Astrology Chart Integration**
   - Full natal chart visualization
   - Transit overlays on daily insights
   - Predictive astrology (eclipses, retrogrades)

5. **Community Sharing (Opt-In)**
   - Anonymous insight sharing
   - Collective consciousness trends
   - Spiritual weather map

6. **Advanced Models**
   - Upgrade to Phi-3 Mini (3.8B params, ~2GB quantized)
   - Multi-modal insights (image generation for tarot cards)
   - Retrieval-augmented generation (RAG) for deeper context

7. **Personalization Learning**
   - Fine-tune models based on user feedback
   - Learn preferred insight styles
   - Adaptive difficulty (beginner → advanced spiritual terminology)

8. **Widget Support**
   - Home screen widget with daily theme
   - Lock screen widget (Android 14+)
   - Glanceable insights without opening app

---

## Conclusion

Prophet AI represents a **privacy-first, offline-capable spiritual companion** that leverages cutting-edge on-device AI to deliver personalized daily insights. With a 4-week development timeline, lightweight models (~90MB), and efficient caching, users will experience:

- **Instant Access:** Pre-generated insights ready each morning
- **Privacy Guaranteed:** Zero data transmission, 100% on-device
- **Battery Efficient:** <1% daily drain with optimized scheduling
- **Beautiful UX:** Shareable cards with spiritual imagery
- **Reliable:** Works offline, perfect for morning rituals

### Next Steps

1. **Approve Design:** Review and approve this technical specification
2. **Resource Allocation:** Assign developers for 4-week sprint
3. **Environment Setup:** Prepare model training infrastructure
4. **Kickoff Meeting:** Align team on milestones and deliverables

---

## Appendix

### A. Dependencies

```kotlin
// data/build.gradle.kts - New dependencies for Prophet AI

dependencies {
    // ONNX Runtime
    implementation("com.microsoft.onnxruntime:onnxruntime-android:1.17.0")

    // Room Database (already included, verify version)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // WorkManager (already included)
    implementation(libs.androidx.work.runtime)

    // SQLCipher (optional, for encrypted database)
    implementation("net.zetetic:android-database-sqlcipher:4.5.4")
    implementation("androidx.sqlite:sqlite-ktx:2.4.0")

    // JSON (already included)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    kapt(libs.moshi.codegen)
}
```

### B. Model Conversion Script

```python
# scripts/convert_distilgpt2_to_onnx.py

import torch
from transformers import GPT2LMHeadModel, GPT2Tokenizer
from onnxruntime.quantization import quantize_dynamic, QuantType
import onnx

def export_distilgpt2():
    model_name = "distilgpt2"

    # Load model and tokenizer
    model = GPT2LMHeadModel.from_pretrained(model_name)
    tokenizer = GPT2Tokenizer.from_pretrained(model_name)

    # Set to eval mode
    model.eval()

    # Dummy input for tracing
    dummy_input = torch.randint(0, 50257, (1, 20))  # vocab_size=50257

    # Export to ONNX
    output_path = "models/distilgpt2_fp32.onnx"
    torch.onnx.export(
        model,
        dummy_input,
        output_path,
        input_names=["input_ids"],
        output_names=["logits"],
        dynamic_axes={
            "input_ids": {0: "batch", 1: "sequence"},
            "logits": {0: "batch", 1: "sequence"}
        },
        opset_version=14
    )

    # Quantize to INT8
    quantized_path = "models/distilgpt2_int8.onnx"
    quantize_dynamic(
        model_input=output_path,
        model_output=quantized_path,
        weight_type=QuantType.QUInt8,
        optimize_model=True
    )

    # Save tokenizer
    tokenizer.save_pretrained("models/distilgpt2_tokenizer")

    print(f"✅ Model exported: {quantized_path}")
    print(f"✅ Tokenizer saved: models/distilgpt2_tokenizer")

if __name__ == "__main__":
    export_distilgpt2()
```

### C. References

- [Deploy on mobile | onnxruntime](https://onnxruntime.ai/docs/tutorials/mobile/)
- [Enjoy the Power of Phi-3 with ONNX Runtime](https://huggingface.co/blog/Emma-N/enjoy-the-power-of-phi-3-with-onnx-runtime)
- [Build ONNX Runtime for Android](https://onnxruntime.ai/docs/build/android.html)
- [Running Large Transformer Models on Mobile](https://huggingface.co/blog/tugrulkaya/running-large-transformer-models-on-mobile)
- [Convert Transformer models to ONNX](https://medium.com/@nabarun.barua/convert-your-bulky-transformer-models-into-lightweight-high-performance-onnx-models-5b18bc25ee06)
- [GitHub - ONNX Models](https://github.com/onnx/models)

---

**Document Version:** 1.0
**Last Updated:** 2025-12-10
**Author:** SpiritAtlas Engineering Team
**Status:** Ready for Review
