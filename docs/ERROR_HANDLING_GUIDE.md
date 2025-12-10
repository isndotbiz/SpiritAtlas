# Error Handling Guide - SpiritAtlas

Complete guide for implementing comprehensive error handling with beautiful, user-friendly error states.

## Table of Contents

1. [Overview](#overview)
2. [Architecture](#architecture)
3. [Domain Errors](#domain-errors)
4. [Error Handler](#error-handler)
5. [Retry Manager](#retry-manager)
6. [UI Components](#ui-components)
7. [Integration Examples](#integration-examples)
8. [Best Practices](#best-practices)

---

## Overview

The SpiritAtlas error handling system provides:

- **Spiritual-themed error messages** that maintain the app's mystical tone
- **Automatic retry** with exponential backoff
- **Offline queue** for failed operations
- **Beautiful error UI** components
- **Real-time validation** with inline feedback
- **Network status** indicators
- **Accessibility-friendly** error handling

---

## Architecture

```
domain/error/
  └── DomainErrors.kt         # Domain error definitions

data/error/
  ├── ErrorHandler.kt         # Retry logic & error handling
  └── RetryManager.kt         # Offline queue & sync

core/ui/components/
  ├── ErrorComponents.kt           # Error UI (screens, cards, banners)
  ├── EmptyStateComponents.kt      # Empty states
  ├── NetworkStatusComponents.kt   # Network status indicators
  └── ValidationComponents.kt      # Form validation
```

---

## Domain Errors

All errors extend `SpiritAtlasError` with spiritual messaging:

### Error Types

```kotlin
// Network errors
NetworkError.NoConnection
NetworkError.Timeout
NetworkError.ServerUnreachable
NetworkError.SslError

// Server errors (5xx)
ServerError.InternalError
ServerError.ServiceUnavailable
ServerError.Maintenance

// Client errors (4xx)
ClientError.Unauthorized
ClientError.Forbidden
ClientError.NotFound
ClientError.BadRequest
ClientError.TooManyRequests

// Data errors
DataError.ValidationError
DataError.ParsingError
DataError.NotFound
DataError.Empty

// AI errors
AiError.ProviderUnavailable
AiError.QuotaExceeded
AiError.InvalidResponse
AiError.Timeout

// Permission errors
PermissionError.ConsentRequired
PermissionError.StoragePermissionDenied

// Unknown errors
UnknownError
```

### Usage

```kotlin
// Map exceptions to domain errors
val error = ErrorMapper.mapThrowable(exception)

// Map HTTP status codes
val error = ErrorMapper.mapHttpError(statusCode, message, cause)

// Get user-friendly messages
val title = error.toUserTitle()        // "Cosmic Connection Lost"
val message = error.toUserMessage()    // Spiritual-themed explanation
val action = error.suggestedAction     // "Check your connection"
val canRetry = error.isRecoverable     // true/false
```

---

## Error Handler

Centralized error handling with automatic retry and exponential backoff.

### Basic Usage

```kotlin
val errorHandler = ErrorHandler()

// Execute with automatic retry
val result = errorHandler.executeWithRetry(
    maxRetries = 3,
    initialDelay = 1000L,
    maxDelay = 30000L,
    operation = {
        // Your operation here
        apiService.fetchData()
    }
)

// Handle result
result.onSuccess { data ->
    // Success
}.onFailure { exception ->
    val error = ErrorMapper.mapThrowable(exception)
    // Handle error
}
```

### Advanced Usage

```kotlin
// Custom retry logic
val result = errorHandler.executeWithRetry(
    maxRetries = 5,
    shouldRetry = { error, attempt ->
        // Custom retry decision
        error.isRecoverable && attempt < 3
    },
    onError = { error, attempt ->
        // Log to analytics
        analytics.logError(error, attempt)
    },
    operation = { /* ... */ }
)
```

### Flow-based Retry

```kotlin
errorHandler.executeWithRetryFlow(
    operation = { apiService.fetchData() }
).collect { state ->
    when (state) {
        is RetryState.Loading -> {
            // Show loading
        }
        is RetryState.Retrying -> {
            // Show retry progress
            // state.attempt, state.nextRetryIn
        }
        is RetryState.Success -> {
            // Handle success
        }
        is RetryState.Error -> {
            // Handle error
        }
    }
}
```

### Circuit Breaker

The error handler includes a circuit breaker to prevent cascading failures:

```kotlin
// Check circuit breaker status
if (errorHandler.isCircuitBreakerActive()) {
    // Service temporarily unavailable
}

// Manually reset circuit breaker
errorHandler.manualResetCircuitBreaker()
```

---

## Retry Manager

Manages offline queue and automatic sync when connection is restored.

### Setup (in Application or ViewModel)

```kotlin
@Inject
lateinit var retryManager: RetryManager

// Observe network state
retryManager.isOnline.collect { isOnline ->
    // Update UI
}

// Observe queued operations
retryManager.queuedCount.collect { count ->
    // Update UI badge
}

// Observe sync state
retryManager.syncState.collect { state ->
    when (state) {
        is SyncState.Idle -> { /* ... */ }
        is SyncState.Syncing -> { /* Show progress */ }
        is SyncState.Success -> { /* Show success */ }
        is SyncState.PartialSuccess -> { /* Show partial */ }
        is SyncState.Failed -> { /* Show error */ }
    }
}
```

### Queue Operations

```kotlin
// Queue operation for retry when online
val operationId = retryManager.queueOperation(
    id = "sync_profile_${profileId}",
    priority = 10, // Higher = more important
    description = "Sync profile changes",
    operation = {
        profileRepository.syncProfile(profileId)
    }
)

// Cancel queued operation
retryManager.cancelOperation(operationId)

// Clear entire queue
retryManager.clearQueue()
```

### Execute or Queue

```kotlin
// Try to execute, queue on failure
val result = retryManager.executeOrQueue(
    id = "compatibility_analysis",
    description = "Calculate compatibility",
    priority = 5,
    operation = {
        compatibilityService.analyze(profile1, profile2)
    }
)
```

### Manual Sync

```kotlin
// Trigger manual sync
scope.launch {
    retryManager.processQueue()
}
```

---

## UI Components

### 1. Error Screen (Full Screen)

```kotlin
ErrorScreen(
    errorType = ErrorType.NetworkError,
    message = "Custom message (optional)",
    onRetry = { /* Retry action */ },
    onDismiss = { /* Dismiss */ },
    customAction = "Settings" to { /* Open settings */ }
)
```

### 2. Error Card (Inline)

```kotlin
ErrorCard(
    errorType = ErrorType.ServerError,
    message = "Custom message",
    onRetry = { /* Retry */ },
    compact = false
)
```

### 3. Error Banner (Top of Screen)

```kotlin
ErrorBanner(
    errorType = ErrorType.NetworkError,
    message = "Connection lost",
    visible = showBanner,
    onDismiss = { showBanner = false },
    action = "Retry" to { /* Retry */ }
)
```

### 4. Empty States

```kotlin
// Predefined empty states
EmptyState(
    emptyStateType = EmptyStateType.NoProfiles,
    onAction = { /* Create profile */ }
)

// Custom empty state
EmptyState(
    emptyStateType = EmptyStateType.Custom(
        customIcon = Icons.Default.Search,
        customTitle = "No Results",
        customMessage = "Try a different search",
        customColor = CosmicBlue,
        customActionText = "Clear Search"
    ),
    onAction = { /* Clear */ }
)

// Compact empty state (for cards)
CompactEmptyState(
    emptyStateType = EmptyStateType.NoFavorites,
    onAction = { /* Browse profiles */ }
)
```

### 5. Offline Banner

```kotlin
OfflineBanner(
    isOnline = isOnline,
    queuedOperations = queuedCount,
    onSync = { retryManager.processQueue() },
    onDismiss = { /* Dismiss */ }
)
```

### 6. Sync Status Indicator

```kotlin
SyncStatusIndicator(
    syncState = when (syncState) {
        is SyncState.Idle -> SyncStateType.Idle
        is SyncState.Syncing -> SyncStateType.Syncing()
        else -> SyncStateType.Queued(count = 5)
    }
)
```

### 7. Validation Components

```kotlin
// Validated text field
var name by remember { mutableStateOf("") }
var nameValidation by remember { mutableStateOf(ValidationState.Idle) }

ValidatedTextField(
    value = name,
    onValueChange = {
        name = it
        nameValidation = ValidationRules.required(it, "Name")
    },
    label = "Name",
    validationState = nameValidation,
    helperText = "Enter your full name"
)

// Form validation summary
val formValidation = rememberFormValidation(
    "Name" to { ValidationRules.required(name, "Name") },
    "Email" to { ValidationRules.email(email) }
)

ValidationSummaryCard(
    validationResult = formValidation,
    onDismiss = { /* Dismiss */ }
)
```

---

## Integration Examples

### Example 1: ViewModel with Error Handling

```kotlin
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val retryManager: RetryManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadProfile(profileId: String) {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading

            val result = retryManager.executeOrQueue(
                id = "load_profile_$profileId",
                description = "Load profile",
                operation = {
                    profileRepository.getProfile(profileId)
                }
            )

            _uiState.value = when {
                result.isSuccess -> ProfileUiState.Success(result.getOrNull()!!)
                else -> {
                    val error = ErrorMapper.mapThrowable(result.exceptionOrNull()!!)
                    ProfileUiState.Error(ErrorState(error))
                }
            }
        }
    }

    fun retry() {
        // Retry logic
    }
}

sealed class ProfileUiState {
    data object Loading : ProfileUiState()
    data class Success(val profile: Profile) : ProfileUiState()
    data class Error(val errorState: ErrorState) : ProfileUiState()
}
```

### Example 2: Screen with Error Handling

```kotlin
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    retryManager: RetryManager = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isOnline by retryManager.isOnline.collectAsState()
    val queuedCount by retryManager.queuedCount.collectAsState()

    Scaffold(
        topBar = {
            Column {
                TopAppBar(title = { Text("Profile") })

                // Offline banner
                OfflineBanner(
                    isOnline = isOnline,
                    queuedOperations = queuedCount,
                    onSync = { /* Sync */ }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (val state = uiState) {
                is ProfileUiState.Loading -> {
                    SpiritualLoadingState(
                        message = "Loading your spiritual profile..."
                    )
                }

                is ProfileUiState.Success -> {
                    ProfileContent(profile = state.profile)
                }

                is ProfileUiState.Error -> {
                    ErrorScreen(
                        errorType = mapToErrorType(state.errorState.error),
                        message = state.errorState.message,
                        onRetry = { viewModel.retry() }
                    )
                }
            }
        }
    }
}
```

### Example 3: Form with Validation

```kotlin
@Composable
fun CreateProfileForm(
    onSubmit: (ProfileData) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

    var nameValidation by remember { mutableStateOf(ValidationState.Idle) }
    var emailValidation by remember { mutableStateOf(ValidationState.Idle) }
    var dateValidation by remember { mutableStateOf(ValidationState.Idle) }

    val formValidation = rememberFormValidation(
        "Name" to { nameValidation },
        "Email" to { emailValidation },
        "Birth Date" to { dateValidation }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Validation summary
        ValidationSummaryCard(
            validationResult = formValidation
        )

        // Name field
        ValidatedTextField(
            value = name,
            onValueChange = {
                name = it
                nameValidation = ValidationRules.required(it, "Name")
            },
            label = "Full Name",
            validationState = nameValidation,
            leadingIcon = Icons.Default.Person
        )

        // Email field
        ValidatedTextField(
            value = email,
            onValueChange = {
                email = it
                emailValidation = ValidationRules.email(it)
            },
            label = "Email",
            validationState = emailValidation,
            leadingIcon = Icons.Default.Email,
            keyboardType = KeyboardType.Email
        )

        // Birth date field
        ValidatedTextField(
            value = birthDate,
            onValueChange = {
                birthDate = it
                dateValidation = ValidationRules.birthDate(it)
            },
            label = "Birth Date",
            validationState = dateValidation,
            helperText = "Format: MM/DD/YYYY",
            leadingIcon = Icons.Default.DateRange
        )

        // Submit button
        ModernButton(
            text = "Create Profile",
            onClick = {
                if (formValidation.isValid) {
                    onSubmit(ProfileData(name, email, birthDate))
                }
            },
            enabled = formValidation.isValid,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
```

---

## Best Practices

### 1. Always Use Domain Errors

```kotlin
// ✅ Good
throw NetworkError.NoConnection()

// ❌ Bad
throw IOException("No connection")
```

### 2. Map Errors at Boundaries

```kotlin
// ✅ Good - Map at repository layer
try {
    apiService.fetchData()
} catch (e: Exception) {
    throw ErrorMapper.mapThrowable(e)
}

// ❌ Bad - Expose raw exceptions
apiService.fetchData() // Throws raw IOException
```

### 3. Use Retry Manager for Network Ops

```kotlin
// ✅ Good
retryManager.executeOrQueue(
    operation = { apiService.sync() }
)

// ❌ Bad - No offline handling
apiService.sync() // Fails immediately when offline
```

### 4. Show Appropriate Error UI

```kotlin
// ✅ Good - Show full error screen for critical failures
when (state) {
    is Error -> ErrorScreen(...)
    is Empty -> EmptyState(...)
}

// ❌ Bad - Silent failure
when (state) {
    is Error -> { /* Nothing */ }
}
```

### 5. Provide Recovery Actions

```kotlin
// ✅ Good
ErrorScreen(
    errorType = ErrorType.NetworkError,
    onRetry = { viewModel.retry() },
    customAction = "Settings" to { openSettings() }
)

// ❌ Bad - No way to recover
ErrorScreen(errorType = ErrorType.NetworkError)
```

### 6. Validate Early and Often

```kotlin
// ✅ Good - Real-time validation
ValidatedTextField(
    value = email,
    onValueChange = {
        email = it
        emailValidation = ValidationRules.email(it)
    },
    validationState = emailValidation
)

// ❌ Bad - Only validate on submit
TextField(
    value = email,
    onValueChange = { email = it }
)
```

### 7. Log Errors for Analytics

```kotlin
// ✅ Good
errorHandler.executeWithRetry(
    onError = { error, attempt ->
        analytics.logError(error, attempt)
    },
    operation = { /* ... */ }
)
```

### 8. Use Circuit Breaker

```kotlin
// ✅ Good - Prevent cascading failures
if (errorHandler.isCircuitBreakerActive()) {
    return ServerError.ServiceUnavailable()
}
```

### 9. Maintain Spiritual Tone

```kotlin
// ✅ Good
"The cosmic signals are disrupted. Let's reconnect to the universe."

// ❌ Bad
"Network error. Check your internet connection."
```

### 10. Accessibility

```kotlin
// ✅ Good
ErrorScreen(
    modifier = Modifier.semantics {
        contentDescription = "Error: ${errorType.title}"
    }
)
```

---

## Testing Error Flows

```kotlin
@Test
fun `test error handling with retry`() = runTest {
    val errorHandler = ErrorHandler()
    var attempts = 0

    val result = errorHandler.executeWithRetry(
        maxRetries = 3,
        operation = {
            attempts++
            if (attempts < 3) {
                throw NetworkError.Timeout()
            }
            "Success"
        }
    )

    assertTrue(result.isSuccess)
    assertEquals(3, attempts)
}

@Test
fun `test offline queue`() = runTest {
    val retryManager = RetryManager(context)

    val operationId = retryManager.queueOperation(
        description = "Test operation",
        operation = { "result" }
    )

    assertEquals(1, retryManager.queuedCount.value)

    retryManager.cancelOperation(operationId)
    assertEquals(0, retryManager.queuedCount.value)
}
```

---

## Summary

The SpiritAtlas error handling system provides a comprehensive, user-friendly approach to error management:

1. **Domain Errors** - Spiritual-themed error types
2. **Error Handler** - Automatic retry with exponential backoff
3. **Retry Manager** - Offline queue and sync management
4. **UI Components** - Beautiful error screens, cards, and indicators
5. **Validation** - Real-time form validation
6. **Network Status** - Offline banners and sync indicators

All components work together to create a seamless, recoverable error experience that maintains the app's spiritual aesthetic.
