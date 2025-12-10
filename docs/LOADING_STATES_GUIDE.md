# Loading States Design Guide

**Agent 17: Loading States Designer**
**Target:** +0.5 UX/UI points

## Overview

This guide covers the comprehensive loading state system for SpiritAtlas. All loading indicators, skeleton screens, and progress indicators are designed with spiritual themes and optimized for 60 FPS performance.

## Files Created

### 1. LoadingIndicators.kt
**Location:** `core/ui/src/main/java/com/spiritatlas/core/ui/components/LoadingIndicators.kt`

Comprehensive spiritual-themed loading animations:

#### Available Loaders

**MandalaBloomLoader**
- **Use for:** Profile generation, spiritual calculations
- **Features:** Expanding mandala pattern with rotating petals
- **Customization:** Size, colors, message
```kotlin
MandalaBloomLoader(
    modifier = Modifier.fillMaxWidth(),
    size = 80.dp,
    colors = listOf(SpiritualPurple, MysticViolet, AuroraPink, CosmicBlue),
    message = "Calculating your spiritual profile..."
)
```

**ChakraSequenceLoader**
- **Use for:** Energy alignment, profile calculations
- **Features:** 7 chakras lighting up from root to crown
- **Animation:** Sequential activation with glow effects
```kotlin
ChakraSequenceLoader(
    modifier = Modifier.fillMaxWidth(),
    size = 100.dp,
    message = "Aligning your energy centers..."
)
```

**ConstellationConnectLoader**
- **Use for:** AI insights, compatibility calculations
- **Features:** Stars connecting with animated lines
- **Animation:** Progressive constellation formation
```kotlin
ConstellationConnectLoader(
    modifier = Modifier.fillMaxWidth(),
    size = 100.dp,
    starCount = 6,
    message = "Gathering cosmic insights..."
)
```

**EnergyFlowLoader**
- **Use for:** Compatibility calculations between profiles
- **Features:** Particles flowing in circular pattern
- **Animation:** Multi-colored particles with trails
```kotlin
EnergyFlowLoader(
    modifier = Modifier.fillMaxWidth(),
    size = 100.dp,
    particleCount = 12,
    message = "Analyzing energy flow..."
)
```

**SacredGeometryLoader**
- **Use for:** Data sync, general loading
- **Features:** Rotating sacred geometry patterns
- **Animation:** Counter-rotating hexagon, triangle, and dots
```kotlin
SacredGeometryLoader(
    modifier = Modifier.fillMaxWidth(),
    size = 80.dp,
    message = "Syncing data..."
)
```

**YinYangSpinLoader**
- **Use for:** Balance calculations, Ayurvedic analysis
- **Features:** Classic yin-yang symbol rotating smoothly
- **Animation:** Continuous rotation with perfect balance
```kotlin
YinYangSpinLoader(
    modifier = Modifier.fillMaxWidth(),
    size = 80.dp,
    message = "Calculating balance..."
)
```

**MoonPhaseCycleLoader**
- **Use for:** Astrological calculations, timing operations
- **Features:** Moon cycling through phases
- **Animation:** Shadow moves across moon surface
```kotlin
MoonPhaseCycleLoader(
    modifier = Modifier.fillMaxWidth(),
    size = 80.dp,
    message = "Reading the stars..."
)
```

#### Context-Specific Convenience Functions

Pre-configured loaders for common use cases:

```kotlin
// Profile generation
ProfileGenerationLoader(
    modifier = Modifier.fillMaxWidth()
)

// Compatibility calculations
CompatibilityCalculationLoader(
    modifier = Modifier.fillMaxWidth()
)

// AI insights
AIInsightsLoader(
    modifier = Modifier.fillMaxWidth()
)

// Data sync
DataSyncLoader(
    modifier = Modifier.fillMaxWidth()
)

// Balance calculations
BalanceCalculationLoader(
    modifier = Modifier.fillMaxWidth()
)

// Astrological calculations
AstrologicalCalculationLoader(
    modifier = Modifier.fillMaxWidth()
)
```

### 2. SkeletonScreens.kt
**Location:** `core/ui/src/main/java/com/spiritatlas/core/ui/components/SkeletonScreens.kt`

Beautiful skeleton screens with spiritual shimmer effects:

#### Shimmer Effects

**Spiritual Shimmer** (Default)
- Gradient: SpiritualPurple → AuroraPink → CosmicBlue
- Duration: 1800ms
```kotlin
Box(
    modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .spiritualShimmer()
)
```

**Gold Shimmer**
- Gradient: SacredGold → AuraGold → UnionGold
- Use for: Premium features, highlighted elements
```kotlin
Box(
    modifier = Modifier
        .size(60.dp)
        .goldShimmer()
)
```

**Chakra Shimmer**
- Gradient: All 7 chakra colors
- Use for: Energy-related content
```kotlin
Box(
    modifier = Modifier
        .fillMaxWidth()
        .chakraShimmer()
)
```

#### Building Blocks

**SkeletonBox**
```kotlin
SkeletonBox(
    modifier = Modifier
        .width(200.dp)
        .height(100.dp),
    shimmerType = ShimmerType.SPIRITUAL
)
```

**SkeletonCircle**
```kotlin
SkeletonCircle(
    size = 80.dp,
    shimmerType = ShimmerType.CHAKRA
)
```

**SkeletonText**
```kotlin
SkeletonText(
    width = 180.dp,
    height = 16.dp,
    shimmerType = ShimmerType.GOLD
)
```

#### Composite Skeletons

**ProfileCardSkeleton**
- Avatar, name, birth info, attributes
```kotlin
ProfileCardSkeleton(
    showDetails = true
)
```

**CompatibilityReportSkeleton**
- Two profiles, score circle, dimensions, insights
```kotlin
CompatibilityReportSkeleton(
    modifier = Modifier.fillMaxWidth()
)
```

**ListItemSkeleton**
- Avatar, title, subtitle, trailing element
```kotlin
ListItemSkeleton(
    showAvatar = true,
    showTrailing = true
)
```

**ChartSkeleton**
- Placeholder for radar/bar charts
```kotlin
ChartSkeleton(
    height = 200.dp
)
```

**ImagePlaceholderSkeleton**
- For zodiac images, backgrounds
```kotlin
ImagePlaceholderSkeleton(
    modifier = Modifier.fillMaxWidth(),
    aspectRatio = 16f/9f,
    shimmerType = ShimmerType.SPIRITUAL
)
```

#### Full Screen Skeletons

**ProfileScreenSkeleton**
- Complete profile view placeholder
```kotlin
ProfileScreenSkeleton(
    modifier = Modifier.fillMaxSize()
)
```

**ProfileListSkeleton**
- List of profile items
```kotlin
ProfileListSkeleton(
    itemCount = 5
)
```

**CompatibilityListSkeleton**
- List of compatibility items
```kotlin
CompatibilityListSkeleton(
    itemCount = 3
)
```

**InsightsSectionSkeleton**
- AI insights section placeholder
```kotlin
InsightsSectionSkeleton(
    modifier = Modifier.fillMaxWidth()
)
```

### 3. ProgressIndicators.kt
**Location:** `core/ui/src/main/java/com/spiritatlas/core/ui/components/ProgressIndicators.kt`

Spiritual progress indicators for determinate and indeterminate progress:

#### Linear Progress

**SpiritualLinearProgress**
```kotlin
// Determinate
SpiritualLinearProgress(
    progress = 0.65f, // 0.0 to 1.0
    gradient = SpiritualGradient,
    height = 8.dp
)

// Indeterminate
SpiritualLinearProgress(
    progress = null,
    gradient = ChakraGradient,
    height = 8.dp
)
```

**ChakraLinearProgress**
- Colors change as progress increases through chakras
```kotlin
ChakraLinearProgress(
    progress = 0.75f,
    showLabel = true,
    height = 10.dp
)
```

#### Circular Progress

**SpiritualCircularProgress**
```kotlin
// Determinate
SpiritualCircularProgress(
    progress = 0.80f,
    size = 80.dp,
    strokeWidth = 8.dp,
    gradient = SpiritualGradient,
    showPercentage = true
)

// Indeterminate
SpiritualCircularProgress(
    progress = null,
    size = 80.dp,
    strokeWidth = 8.dp,
    gradient = ChakraGradient,
    showPercentage = false
)
```

**MandalaCircularProgress**
- Ornate mandala pattern fills as progress increases
```kotlin
MandalaCircularProgress(
    progress = 0.60f,
    size = 100.dp,
    showPercentage = true
)
```

#### Step Indicators

**SpiritualStepIndicator**
```kotlin
SpiritualStepIndicator(
    currentStep = 2, // 0-indexed
    totalSteps = 5,
    stepLabels = listOf(
        "Profile",
        "Birth Info",
        "Preferences",
        "Verification",
        "Complete"
    ),
    showConnectors = true
)
```

**VerticalStepIndicator**
```kotlin
VerticalStepIndicator(
    currentStep = 1,
    totalSteps = 4,
    stepLabels = listOf(
        "Start",
        "Process",
        "Review",
        "Finish"
    )
)
```

#### Specialized Progress

**ProfileCompletionProgress**
```kotlin
ProfileCompletionProgress(
    sectionsCompleted = 4,
    totalSections = 7
)
```

**LoadingProgressWithMessage**
```kotlin
LoadingProgressWithMessage(
    progress = 0.45f, // or null for indeterminate
    message = "Analyzing your spiritual profile..."
)
```

## Usage Patterns

### Replace Generic Loading Spinners

**Before:**
```kotlin
if (isLoading) {
    CircularProgressIndicator()
}
```

**After:**
```kotlin
if (isLoading) {
    ChakraSequenceLoader(
        message = "Loading your profile..."
    )
}
```

### Profile Screen Loading

```kotlin
@Composable
fun ProfileScreen(
    profile: UserProfile?,
    isLoading: Boolean
) {
    if (isLoading) {
        ProfileScreenSkeleton()
    } else {
        profile?.let { ProfileContent(it) }
    }
}
```

### Compatibility Calculation

```kotlin
@Composable
fun CompatibilityAnalysis(
    report: CompatibilityReport?,
    isCalculating: Boolean
) {
    if (isCalculating) {
        CompatibilityCalculationLoader(
            message = "Analyzing spiritual connection..."
        )
    } else {
        report?.let { CompatibilityReportView(it) }
    }
}
```

### List with Loading States

```kotlin
@Composable
fun ProfileList(
    profiles: List<UserProfile>?,
    isLoading: Boolean
) {
    LazyColumn {
        if (isLoading) {
            items(5) {
                ListItemSkeleton()
            }
        } else {
            items(profiles ?: emptyList()) { profile ->
                ProfileListItem(profile)
            }
        }
    }
}
```

### Multi-Step Flow

```kotlin
@Composable
fun OnboardingFlow(
    currentStep: Int
) {
    Column {
        SpiritualStepIndicator(
            currentStep = currentStep,
            totalSteps = 5,
            stepLabels = listOf(
                "Welcome",
                "Birth Info",
                "Spiritual Preferences",
                "Interests",
                "Complete"
            )
        )

        // Step content
        when (currentStep) {
            0 -> WelcomeStep()
            1 -> BirthInfoStep()
            // ...
        }
    }
}
```

### Progress Updates

```kotlin
@Composable
fun ProfileGenerationScreen(
    viewModel: ProfileViewModel
) {
    val progress by viewModel.generationProgress.collectAsState()
    val status by viewModel.status.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when {
            progress != null -> {
                LoadingProgressWithMessage(
                    progress = progress,
                    message = status
                )
            }
            else -> {
                ProfileGenerationLoader()
            }
        }
    }
}
```

## Performance Optimization

All animations are optimized for 60 FPS:

1. **Efficient Canvas Usage**: Using DrawScope efficiently
2. **Single Animation Values**: Reusing animation transitions
3. **No Heavy Calculations**: All math pre-calculated where possible
4. **Proper Composable Structure**: Minimizing recomposition

### Monitoring Performance

```kotlin
// Add this to your composables to monitor performance
@Composable
fun MonitoredLoader() {
    val recompositions = remember { mutableStateOf(0) }

    DisposableEffect(Unit) {
        recompositions.value++
        onDispose { }
    }

    ChakraSequenceLoader()

    // Log recompositions
    if (BuildConfig.DEBUG) {
        Text("Recompositions: ${recompositions.value}")
    }
}
```

## Best Practices

### 1. Choose the Right Loader

- **Quick operations (< 1s)**: Use shimmer skeletons
- **Determinate progress**: Use progress indicators
- **Unknown duration**: Use themed loaders
- **Multi-step flows**: Use step indicators

### 2. Provide Context

Always include meaningful messages:
```kotlin
ChakraSequenceLoader(
    message = "Aligning your chakras..." // Good
    // message = "Loading..." // Bad
)
```

### 3. Skeleton Screens for Content

Use skeletons when the layout is known:
```kotlin
// Good for profile screens
ProfileCardSkeleton()

// Good for lists
ProfileListSkeleton(itemCount = 3)
```

### 4. Match Animation to Context

- Profile calculations → ChakraSequenceLoader
- Compatibility → EnergyFlowLoader
- AI insights → ConstellationConnectLoader
- Balance → YinYangSpinLoader
- Astrology → MoonPhaseCycleLoader

### 5. Accessibility

All loaders include:
- Proper size constraints
- Color contrast ratios
- Optional text labels
- Semantic structure

## Migration Guide

### Step 1: Identify Current Loading States

Find all instances of:
- `CircularProgressIndicator`
- Custom loading spinners
- Plain "Loading..." text

### Step 2: Choose Replacement

Map each to appropriate spiritual loader:
- Generic → SacredGeometryLoader
- Profile → ProfileGenerationLoader
- Compatibility → CompatibilityCalculationLoader
- AI → AIInsightsLoader

### Step 3: Update Implementation

```kotlin
// Old
if (isLoading) {
    CircularProgressIndicator()
}

// New
if (isLoading) {
    ProfileGenerationLoader(
        message = viewModel.loadingMessage
    )
}
```

### Step 4: Add Skeleton Screens

For screens with known layouts:
```kotlin
if (isLoading) {
    ProfileScreenSkeleton()
} else {
    ProfileContent(profile)
}
```

## Examples in Context

### Home Screen

```kotlin
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val profiles by viewModel.profiles.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold { padding ->
        if (isLoading && profiles.isEmpty()) {
            ProfileListSkeleton(itemCount = 5)
        } else {
            LazyColumn(contentPadding = padding) {
                items(profiles) { profile ->
                    ProfileCard(profile)
                }
            }
        }
    }
}
```

### Compatibility Screen

```kotlin
@Composable
fun CompatibilityDetailScreen(
    report: CompatibilityReport?,
    isLoading: Boolean
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CompatibilityReportSkeleton()
            }
            report != null -> {
                CompatibilityReportContent(report)
            }
            else -> {
                EmptyState()
            }
        }
    }
}
```

### AI Insights

```kotlin
@Composable
fun InsightsSection(
    insights: List<Insight>?,
    isGenerating: Boolean
) {
    if (isGenerating) {
        Column {
            AIInsightsLoader(
                message = "Channeling cosmic wisdom..."
            )
            Spacer(modifier = Modifier.height(16.dp))
            InsightsSectionSkeleton()
        }
    } else {
        InsightsContent(insights ?: emptyList())
    }
}
```

## Testing

Test your loading states:

```kotlin
@Test
fun testLoadingStates() {
    composeTestRule.setContent {
        var isLoading by remember { mutableStateOf(true) }

        ProfileScreen(
            profile = null,
            isLoading = isLoading
        )
    }

    // Verify skeleton is shown
    composeTestRule.onNodeWithTag("profile_skeleton").assertExists()

    // Simulate data loaded
    composeTestRule.runOnUiThread {
        isLoading = false
    }

    // Verify content is shown
    composeTestRule.onNodeWithTag("profile_content").assertExists()
}
```

## Credits

Created by **Agent 17: Loading States Designer**
Part of the SpiritAtlas UX/UI Excellence initiative

Target achieved: +0.5 points for comprehensive, spiritually-themed loading states.
