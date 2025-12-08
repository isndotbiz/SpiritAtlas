# Error Components Quick Reference

Fast reference for SpiritAtlas error handling components.

## Components at a Glance

| Component | Use Case | Key Features |
|-----------|----------|--------------|
| `ErrorScreen` | Critical, page-blocking errors | Full screen, animated icon, multiple actions |
| `ErrorCard` | Section-specific errors | Inline, compact mode, retry/dismiss |
| `ErrorSnackbar` | Quick notifications | Bottom toast, auto-dismiss, action button |
| `ErrorBanner` | Persistent warnings | Top banner, dismissible, stays visible |
| `NetworkErrorView` | Connectivity issues | Pre-configured network error, settings action |
| `EmptyStateView` | No data states | Beautiful empty state, call-to-action |
| `RetryButton` | Retry actions | Animated, loading state, pulsing effect |

## Error Types Quick Reference

```kotlin
ErrorType.NetworkError      // Coral - "Cosmic Connection Lost"
ErrorType.ServerError        // Purple - "The Stars Are Aligning"
ErrorType.NotFoundError      // Gold - "Path Not Found"
ErrorType.PermissionError    // Violet - "Sacred Access Required"
ErrorType.UnknownError       // Rose - "Unexpected Energy"
ErrorType.Custom(...)        // Custom icon, title, message, color
```

## Common Usage Examples

### Full Screen Error
```kotlin
ErrorScreen(
    errorType = ErrorType.NetworkError,
    onRetry = { viewModel.retry() }
)
```

### Inline Error Card
```kotlin
ErrorCard(
    errorType = ErrorType.ServerError,
    message = "Unable to load data",
    compact = true,
    onRetry = { viewModel.retry() }
)
```

### Error Snackbar
```kotlin
LaunchedEffect(error) {
    error?.let {
        snackbarHostState.showErrorSnackbar(
            errorType = ErrorType.NetworkError,
            message = "Connection lost"
        )
    }
}
```

### Error Banner
```kotlin
ErrorBanner(
    errorType = ErrorType.NetworkError,
    message = "You're offline",
    visible = isOffline,
    onDismiss = { isOffline = false }
)
```

### Empty State
```kotlin
EmptyStateView(
    title = "No Readings Yet",
    message = "Start your journey",
    actionText = "Create Reading",
    onAction = { navController.navigate("create") }
)
```

### Retry Button
```kotlin
RetryButton(
    onClick = { viewModel.retry() },
    isLoading = isRetrying
)
```

## Loading State Pattern

```kotlin
when (state) {
    Loading -> LoadingIndicator()
    Success -> Content()
    Empty -> EmptyStateView(...)
    is Error -> ErrorScreen(
        errorType = state.errorType,
        onRetry = { viewModel.retry() }
    )
}
```

## Color Codes

- NetworkError: `#F87171` (Sensual Coral)
- ServerError: `#A855F7` (Intimacy Purple)
- NotFoundError: `#FBBF24` (Aura Gold)
- PermissionError: `#7C3AED` (Mystic Violet)
- UnknownError: `#EC4899` (Tantric Rose)

## Decision Tree

**Need to show an error?**

1. **Does it block the entire screen?**
   - Yes → `ErrorScreen`
   - No → Continue

2. **Is it for a specific section?**
   - Yes → `ErrorCard`
   - No → Continue

3. **Should it persist until dismissed?**
   - Yes → `ErrorBanner`
   - No → `ErrorSnackbar`

**Need to show empty state?**
- Use `EmptyStateView`

**Need a retry button?**
- Use `RetryButton` (standalone or integrated)

## Import Statement

```kotlin
import com.spiritatlas.core.ui.components.*
```

## File Locations

- Components: `/core/ui/src/main/java/com/spiritatlas/core/ui/components/ErrorComponents.kt`
- Examples: `/core/ui/src/main/java/com/spiritatlas/core/ui/components/ErrorComponentsExample.kt`
- Full Guide: `/docs/ERROR_COMPONENTS_GUIDE.md`
