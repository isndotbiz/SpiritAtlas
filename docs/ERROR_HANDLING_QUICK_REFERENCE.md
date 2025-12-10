# Error Handling Quick Reference

Quick reference for implementing error handling in SpiritAtlas.

## Import Statements

```kotlin
// Domain errors
import com.spiritatlas.domain.error.*

// Error handling
import com.spiritatlas.data.error.ErrorHandler
import com.spiritatlas.data.error.RetryManager
import com.spiritatlas.data.error.RetryState
import com.spiritatlas.data.error.SyncState

// UI Components
import com.spiritatlas.core.ui.components.ErrorType
import com.spiritatlas.core.ui.components.ErrorScreen
import com.spiritatlas.core.ui.components.ErrorCard
import com.spiritatlas.core.ui.components.ErrorBanner
import com.spiritatlas.core.ui.components.EmptyState
import com.spiritatlas.core.ui.components.EmptyStateType
import com.spiritatlas.core.ui.components.OfflineBanner
import com.spiritatlas.core.ui.components.SyncStatusIndicator
import com.spiritatlas.core.ui.components.ValidationState
import com.spiritatlas.core.ui.components.ValidatedTextField
import com.spiritatlas.core.ui.components.ValidationRules
```

## Quick Patterns

### 1. Simple Error Handling

```kotlin
viewModelScope.launch {
    val result = errorHandler.executeWithRetry {
        apiService.fetchData()
    }

    uiState = when {
        result.isSuccess -> UiState.Success(result.getOrNull()!!)
        else -> UiState.Error(ErrorMapper.mapThrowable(result.exceptionOrNull()!!))
    }
}
```

### 2. Network Operation with Queue

```kotlin
retryManager.executeOrQueue(
    id = "operation_$id",
    description = "Sync profile",
    operation = { repository.sync(id) }
)
```

### 3. Show Error Screen

```kotlin
ErrorScreen(
    errorType = ErrorType.NetworkError,
    onRetry = { viewModel.retry() }
)
```

### 4. Show Empty State

```kotlin
EmptyState(
    emptyStateType = EmptyStateType.NoProfiles,
    onAction = { navController.navigate("create_profile") }
)
```

### 5. Offline Banner

```kotlin
val isOnline by retryManager.isOnline.collectAsState()
val queuedCount by retryManager.queuedCount.collectAsState()

OfflineBanner(
    isOnline = isOnline,
    queuedOperations = queuedCount,
    onSync = { scope.launch { retryManager.processQueue() } }
)
```

### 6. Form Validation

```kotlin
var email by remember { mutableStateOf("") }
var emailValidation by remember { mutableStateOf(ValidationState.Idle) }

ValidatedTextField(
    value = email,
    onValueChange = {
        email = it
        emailValidation = ValidationRules.email(it)
    },
    label = "Email",
    validationState = emailValidation
)
```

## Error Type Mapping

| Technical Error | Domain Error | UI ErrorType |
|----------------|--------------|--------------|
| `UnknownHostException` | `NetworkError.NoConnection` | `ErrorType.NetworkError` |
| `SocketTimeoutException` | `NetworkError.Timeout` | `ErrorType.NetworkError` |
| HTTP 401 | `ClientError.Unauthorized` | `ErrorType.PermissionError` |
| HTTP 404 | `ClientError.NotFound` | `ErrorType.NotFoundError` |
| HTTP 500 | `ServerError.InternalError` | `ErrorType.ServerError` |

## UI State Pattern

```kotlin
sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val errorState: ErrorState) : UiState<Nothing>()
    data class Empty(val emptyType: EmptyStateType) : UiState<Nothing>()
}
```

## Common Validation Rules

```kotlin
ValidationRules.required(value, "Field name")
ValidationRules.email(email)
ValidationRules.minLength(value, 3, "Field")
ValidationRules.maxLength(value, 50, "Field")
ValidationRules.numeric(value, "Field")
ValidationRules.birthDate(date)
ValidationRules.time(time)
ValidationRules.url(url)
```

## Retry Configuration

```kotlin
errorHandler.executeWithRetry(
    maxRetries = 3,              // Max retry attempts
    initialDelay = 1000L,        // Initial delay (ms)
    maxDelay = 30000L,           // Max delay (ms)
    shouldRetry = { error, attempt -> error.isRecoverable },
    onError = { error, attempt -> analytics.log(error) },
    operation = { /* ... */ }
)
```

## Circuit Breaker

```kotlin
// Check status
if (errorHandler.isCircuitBreakerActive()) {
    // Service unavailable
}

// Manual reset
errorHandler.manualResetCircuitBreaker()
```

## Sync States

```kotlin
when (syncState) {
    is SyncState.Idle -> { /* Ready */ }
    is SyncState.Syncing -> { /* Show progress: ${state.processed}/${state.total} */ }
    is SyncState.Success -> { /* Show success */ }
    is SyncState.PartialSuccess -> { /* ${state.processed} succeeded, ${state.failed} failed */ }
    is SyncState.Failed -> { /* Show error */ }
}
```

## Spiritual Error Messages

Use spiritual-themed messages that maintain the app's mystical tone:

- ✅ "Cosmic Connection Lost" (not "Network Error")
- ✅ "Stars Are Aligning" (not "Server Error")
- ✅ "Sacred Access Required" (not "Permission Denied")
- ✅ "Path Not Found" (not "404 Error")
- ✅ "Your Journey Awaits" (not "No Data")

## Component Gallery

### Error Screens
- `ErrorScreen` - Full-screen error
- `ErrorCard` - Inline error card
- `ErrorBanner` - Dismissible top banner
- `NetworkErrorView` - Network-specific error

### Empty States
- `EmptyState` - Full-screen empty state
- `CompactEmptyState` - Inline empty state
- `EmptyProfileListState` - No profiles
- `EmptyCompatibilityState` - No compatibility
- `NoSearchResultsState` - No search results
- `OfflineState` - Offline mode

### Network Status
- `OfflineBanner` - Offline indicator
- `OnlineRestoredBanner` - Connection restored
- `SyncStatusIndicator` - Sync status chip
- `ConnectionStatusChip` - Online/offline chip
- `QueuedOperationsCard` - Queued ops display
- `SyncProgressIndicator` - Sync progress

### Loading
- `SpiritualLoadingState` - Loading screen
- `SkeletonLoadingCard` - Skeleton loader

### Validation
- `ValidatedTextField` - Field with validation
- `ValidationSummaryCard` - Form error summary
- `ValidationFeedbackChip` - Inline feedback

## Testing Checklist

- [ ] Error screen displays correctly
- [ ] Retry button works
- [ ] Offline banner appears when offline
- [ ] Queue operations when offline
- [ ] Auto-sync when connection restored
- [ ] Validation shows in real-time
- [ ] Error messages are spiritual-themed
- [ ] Empty states show for no data
- [ ] Circuit breaker prevents cascade
- [ ] Accessibility labels present

## Performance Notes

- Error handler uses exponential backoff
- Circuit breaker prevents excessive retries
- Offline queue persists in memory (not disk yet)
- Validation is synchronous (no delay)
- Network monitoring uses ConnectivityManager callback

## Migration Checklist

For existing ViewModels:

1. [ ] Replace try-catch with `executeWithRetry`
2. [ ] Replace direct API calls with `executeOrQueue`
3. [ ] Map exceptions to domain errors
4. [ ] Add error state to UI state sealed class
5. [ ] Show error UI components
6. [ ] Add empty state for no data
7. [ ] Add offline banner
8. [ ] Add validation to forms
9. [ ] Add retry actions
10. [ ] Test error recovery flows
