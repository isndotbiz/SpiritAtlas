# Spiritual Pull-to-Refresh - Quick Reference

## Basic Usage

```kotlin
SpiritualPullRefresh(
    isRefreshing = isRefreshing,
    onRefresh = { /* refresh logic */ },
    indicatorType = RefreshIndicatorType.Lotus
) {
    LazyColumn { /* content */ }
}
```

## 5 Indicator Types

| Type | Symbol | Use Case | Colors |
|------|--------|----------|--------|
| **Lotus** | 🪷 | Meditation, Enlightenment | Purple, Violet, Gold |
| **MoonPhase** | 🌙 | Lunar, Cycles | White, Gold |
| **ZodiacWheel** | ♈ | Astrology, Horoscope | Purple, Gold |
| **Constellation** | ✨ | Destiny, Fate | White, Gold, Purple |
| **Chakra** | 🧘 | Energy, Healing | Red→Violet (7 colors) |

## When to Use Which

```kotlin
// Profile / Energy features
indicatorType = RefreshIndicatorType.Chakra

// Astrology / Zodiac features
indicatorType = RefreshIndicatorType.ZodiacWheel

// Meditation / Spiritual features
indicatorType = RefreshIndicatorType.Lotus

// Lunar / Calendar features
indicatorType = RefreshIndicatorType.MoonPhase

// Destiny / Reading features
indicatorType = RefreshIndicatorType.Constellation
```

## With ViewModel

```kotlin
@Composable
fun MyScreen(viewModel: MyViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    
    SpiritualPullRefresh(
        isRefreshing = uiState.isRefreshing,
        onRefresh = { viewModel.refresh() },
        indicatorType = RefreshIndicatorType.Lotus
    ) {
        LazyColumn {
            items(uiState.items) { item ->
                ItemCard(item)
            }
        }
    }
}
```

## Animation States

```
Idle → Pulling → Armed → Refreshing → Complete
 0%     1-99%     100%    Spinning     Fade
```

## Key Features

- ✅ Haptic feedback at 100% pull
- ✅ 60 FPS Canvas animations
- ✅ Material 3 foundation
- ✅ Works with all scrollables
- ✅ Auto state management

## Files

```
Core:        SpiritualPullRefresh.kt
Example:     SpiritualPullRefreshExample.kt
Integration: SpiritualPullRefreshIntegration.kt
Docs:        SpiritualPullRefreshReadme.md
```

## Test It

```kotlin
// Run the demo
SpiritualPullRefreshExampleScreen()
```

## Common Patterns

### Simple List
```kotlin
SpiritualPullRefresh(
    isRefreshing = isRefreshing,
    onRefresh = { isRefreshing = true }
) {
    LazyColumn { /* items */ }
}
```

### With Error Handling
```kotlin
when (uiState) {
    is Error -> ErrorScreen()
    else -> SpiritualPullRefresh(
        isRefreshing = uiState.isRefreshing,
        onRefresh = { viewModel.retry() }
    ) {
        ContentList(uiState.data)
    }
}
```

### Dynamic Indicator
```kotlin
val indicator = when (contentType) {
    CHAKRA -> RefreshIndicatorType.Chakra
    ASTRO -> RefreshIndicatorType.ZodiacWheel
    else -> RefreshIndicatorType.Lotus
}

SpiritualPullRefresh(
    isRefreshing = isRefreshing,
    onRefresh = { refresh() },
    indicatorType = indicator
) { /* content */ }
```

---

**Quick, beautiful, spiritual pull-to-refresh!** ✨
