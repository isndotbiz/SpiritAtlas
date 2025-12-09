# SpiritAtlas Error Components Guide

Complete documentation for the spiritual-themed error handling UI components.

## Overview

The SpiritAtlas error components provide a beautiful, calming, and spiritually-themed approach to error handling. Instead of harsh red colors and alarming messages, these components use soft, encouraging tones and mystical aesthetics to maintain the app's spiritual atmosphere even when things go wrong.

## Design Principles

1. **Calming Colors**: Use soft corals, purples, and golds instead of harsh reds
2. **Encouraging Copy**: Messages like "The stars will align again" instead of "Error occurred"
3. **Subtle Animations**: Gentle pulsing and floating animations
4. **Mystical Aesthetics**: Icons and illustrations that match the spiritual theme
5. **Accessibility First**: Proper contrast ratios, semantic descriptions, and screen reader support
6. **Flexible Actions**: Support for retry, custom actions, and dismissal

## Components

### 1. ErrorType (Sealed Class)

Defines different error types with appropriate styling and messages.

#### Available Types

```kotlin
// Network connectivity errors
ErrorType.NetworkError
// Icon: Warning
// Color: Sensual Coral
// Message: "The cosmic signals are disrupted. Let's reconnect to the universe."

// Server-side errors
ErrorType.ServerError
// Icon: Info
// Color: Intimacy Purple
// Message: "The celestial servers are realigning. Please wait a moment."

// 404 / Not found errors
ErrorType.NotFoundError
// Icon: Search
// Color: Aura Gold
// Message: "This spiritual path doesn't exist in our realm."

// Permission/authorization errors
ErrorType.PermissionError
// Icon: Lock
// Color: Mystic Violet
// Message: "This sacred knowledge requires special permissions."

// Generic/unknown errors
ErrorType.UnknownError
// Icon: Close
// Color: Tantric Rose
// Message: "An unexpected energy disrupted the flow."

// Custom errors
ErrorType.Custom(
    customIcon = Icons.Default.Star,
    customTitle = "Custom Title",
    customMessage = "Custom message",
    customColor = SpiritualPurple
)
```

### 2. ErrorScreen

Full-screen error display for critical errors that prevent the entire screen from functioning.

#### Usage

```kotlin
ErrorScreen(
    errorType = ErrorType.NetworkError,
    message = "Optional custom message",
    onRetry = {
        // Handle retry logic
        viewModel.retryOperation()
    },
    onDismiss = {
        // Handle dismissal
        navController.navigateUp()
    },
    customAction = "Settings" to {
        // Custom action
        navController.navigate("settings")
    }
)
```

#### Parameters

- `errorType: ErrorType` - The type of error to display
- `modifier: Modifier` - Optional modifier
- `message: String?` - Optional custom message (overrides default)
- `onRetry: (() -> Unit)?` - Retry button callback
- `onDismiss: (() -> Unit)?` - Dismiss button callback
- `customAction: Pair<String, () -> Unit>?` - Custom action button

#### Features

- Animated error icon with gentle rotation and scaling
- Gradient background matching error color
- Full accessibility support with semantic descriptions
- Flexible action button configuration

### 3. ErrorCard

Inline error card for displaying errors within specific sections of a screen.

#### Usage

```kotlin
// Standard error card
ErrorCard(
    errorType = ErrorType.ServerError,
    message = "Unable to load spiritual readings",
    onRetry = { viewModel.retryLoadReadings() },
    onDismiss = { viewModel.clearError() }
)

// Compact error card
ErrorCard(
    errorType = ErrorType.PermissionError,
    compact = true,
    onRetry = { requestPermissions() }
)
```

#### Parameters

- `errorType: ErrorType` - The type of error
- `modifier: Modifier` - Optional modifier
- `message: String?` - Optional custom message
- `onRetry: (() -> Unit)?` - Retry button callback
- `onDismiss: (() -> Unit)?` - Dismiss button callback
- `compact: Boolean` - Use compact layout (default: false)

#### Features

- Inline display within content flow
- Compact mode for space-constrained layouts
- Colored background matching error type
- Icon and action buttons integrated

### 4. ErrorSnackbar

Custom snackbar component for quick, dismissible error notifications.

#### Usage

```kotlin
val snackbarHostState = remember { SnackbarHostState() }

Scaffold(
    snackbarHost = {
        SnackbarHost(snackbarHostState) { data ->
            ErrorSnackbar(
                errorType = ErrorType.NetworkError,
                message = data.visuals.message,
                action = "Retry" to {
                    viewModel.retry()
                },
                onDismiss = { data.dismiss() }
            )
        }
    }
) { padding ->
    // Content
}

// Show snackbar
LaunchedEffect(errorState) {
    if (errorState is Error) {
        snackbarHostState.showErrorSnackbar(
            errorType = ErrorType.NetworkError,
            message = "Connection lost",
            actionLabel = "Retry"
        )
    }
}
```

#### Parameters

- `errorType: ErrorType` - The type of error
- `message: String` - Error message to display
- `modifier: Modifier` - Optional modifier
- `action: Pair<String, () -> Unit>?` - Optional action button
- `onDismiss: (() -> Unit)?` - Dismiss callback

#### Features

- Material Design 3 snackbar styling
- Colored background matching error type
- Icon integrated into message
- Extension function for easy SnackbarHostState usage

### 5. ErrorBanner

Dismissible banner for persistent but non-critical errors.

#### Usage

```kotlin
var showBanner by remember { mutableStateOf(true) }

Column(modifier = Modifier.fillMaxSize()) {
    ErrorBanner(
        errorType = ErrorType.NetworkError,
        message = "You're currently offline",
        visible = showBanner,
        onDismiss = { showBanner = false },
        action = "Retry" to {
            viewModel.retryConnection()
        }
    )

    // Rest of content
}
```

#### Parameters

- `errorType: ErrorType` - The type of error
- `message: String` - Error message to display
- `modifier: Modifier` - Optional modifier
- `visible: Boolean` - Visibility state (default: true)
- `onDismiss: () -> Unit` - Dismiss callback
- `action: Pair<String, () -> Unit>?` - Optional action button

#### Features

- Animated appearance/dismissal
- Stays visible until dismissed
- Suitable for top-of-screen placement
- Non-blocking to content below

### 6. NetworkErrorView

Specialized view for network connectivity issues.

#### Usage

```kotlin
NetworkErrorView(
    onRetry = {
        viewModel.retryConnection()
    },
    customMessage = "Custom network error message"
)
```

#### Parameters

- `modifier: Modifier` - Optional modifier
- `onRetry: () -> Unit` - Retry callback
- `customMessage: String?` - Optional custom message

#### Features

- Pre-configured for network errors
- Includes "Check Settings" action
- Full-screen error display
- Specialized for connectivity issues

### 7. EmptyStateView

Beautiful empty state display (not an error, but complementary).

#### Usage

```kotlin
// No data state
EmptyStateView(
    title = "No Readings Yet",
    message = "Begin your spiritual journey by creating your first reading.",
    icon = Icons.Default.Star,
    actionText = "Create Reading",
    onAction = {
        navController.navigate("create_reading")
    }
)

// Search results empty
EmptyStateView(
    title = "No Results Found",
    message = "Try adjusting your search criteria.",
    icon = Icons.Default.Search,
    actionText = "Clear Filters",
    onAction = { viewModel.clearFilters() }
)
```

#### Parameters

- `title: String` - Empty state title
- `message: String` - Empty state message
- `modifier: Modifier` - Optional modifier
- `icon: ImageVector` - Icon to display (default: Search)
- `actionText: String?` - Optional action button text
- `onAction: (() -> Unit)?` - Optional action callback
- `color: Color` - Theme color (default: SpiritualPurple)

#### Features

- Animated icon with gentle pulsing
- Centered layout for full-screen display
- Optional call-to-action button
- Customizable icon and color

### 8. RetryButton

Animated retry button with loading state support.

#### Usage

```kotlin
var isLoading by remember { mutableStateOf(false) }

RetryButton(
    onClick = {
        isLoading = true
        viewModel.retry()
    },
    text = "Try Again",
    isLoading = isLoading,
    color = SpiritualPurple
)

// Stop loading after operation
LaunchedEffect(isLoading) {
    if (isLoading) {
        delay(2000)
        isLoading = false
    }
}
```

#### Parameters

- `onClick: () -> Unit` - Click callback
- `modifier: Modifier` - Optional modifier
- `text: String` - Button text (default: "Try Again")
- `isLoading: Boolean` - Loading state (default: false)
- `color: Color` - Button color (default: SpiritualPurple)
- `enabled: Boolean` - Enabled state (default: true)

#### Features

- Gentle pulsing animation when idle
- Smooth transition to loading state
- Circular progress indicator when loading
- Custom color support

## Usage Patterns

### Pattern 1: Loading States with Error Handling

```kotlin
sealed class LoadingState {
    object Loading : LoadingState()
    object Success : LoadingState()
    object Empty : LoadingState()
    data class Error(
        val errorType: ErrorType,
        val message: String? = null
    ) : LoadingState()
}

@Composable
fun MyScreen(viewModel: MyViewModel) {
    val loadingState by viewModel.loadingState.collectAsState()

    when (loadingState) {
        is LoadingState.Loading -> {
            LoadingIndicator()
        }

        is LoadingState.Success -> {
            SuccessContent()
        }

        is LoadingState.Empty -> {
            EmptyStateView(
                title = "No Data",
                message = "Get started by adding content",
                actionText = "Add",
                onAction = { viewModel.navigateToAdd() }
            )
        }

        is LoadingState.Error -> {
            val error = loadingState as LoadingState.Error
            ErrorScreen(
                errorType = error.errorType,
                message = error.message,
                onRetry = { viewModel.retry() }
            )
        }
    }
}
```

### Pattern 2: Section-Specific Error Handling

```kotlin
@Composable
fun MySection(viewModel: MySectionViewModel) {
    val errorState by viewModel.errorState.collectAsState()

    Column {
        SectionHeader("My Section")

        if (errorState != null) {
            ErrorCard(
                errorType = errorState!!.type,
                message = errorState!!.message,
                compact = true,
                onRetry = { viewModel.retrySection() },
                onDismiss = { viewModel.clearError() }
            )
        } else {
            SectionContent()
        }
    }
}
```

### Pattern 3: Global Error Handling

```kotlin
@Composable
fun MyApp() {
    val snackbarHostState = remember { SnackbarHostState() }
    val globalError by viewModel.globalError.collectAsState()

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                ErrorSnackbar(
                    errorType = ErrorType.NetworkError,
                    message = data.visuals.message,
                    onDismiss = { data.dismiss() }
                )
            }
        }
    ) { padding ->
        // App content

        // Show snackbar for global errors
        LaunchedEffect(globalError) {
            globalError?.let { error ->
                snackbarHostState.showErrorSnackbar(
                    errorType = error.type,
                    message = error.message
                )
            }
        }
    }
}
```

### Pattern 4: Multi-Level Error Display

```kotlin
@Composable
fun ComplexScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    var showBanner by remember { mutableStateOf(false) }
    var criticalError by remember { mutableStateOf<ErrorType?>(null) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                ErrorSnackbar(
                    errorType = ErrorType.NetworkError,
                    message = data.visuals.message,
                    onDismiss = { data.dismiss() }
                )
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Persistent banner for ongoing issues
            ErrorBanner(
                errorType = ErrorType.NetworkError,
                message = "Limited connectivity",
                visible = showBanner,
                onDismiss = { showBanner = false }
            )

            // Critical error blocks entire screen
            if (criticalError != null) {
                ErrorScreen(
                    errorType = criticalError!!,
                    onRetry = { criticalError = null }
                )
            } else {
                // Normal content with section errors
                Content()
            }
        }
    }
}
```

## Accessibility

All components include proper accessibility support:

1. **Semantic Descriptions**: All major components use `semantics` modifiers
2. **Content Descriptions**: Icons have appropriate content descriptions
3. **Color Contrast**: Error colors meet WCAG AA contrast requirements
4. **Screen Reader Support**: Error messages are properly announced
5. **Keyboard Navigation**: All buttons are keyboard accessible

## Customization

### Custom Error Types

Create your own error types for specific use cases:

```kotlin
val AstrologicalError = ErrorType.Custom(
    customIcon = Icons.Default.Star,
    customTitle = "Planetary Alignment Issue",
    customMessage = "The planets are not in the right position for this reading.",
    customColor = AuraGold
)

ErrorScreen(errorType = AstrologicalError)
```

### Custom Colors

Override default colors to match your theme:

```kotlin
RetryButton(
    onClick = { },
    text = "Reconnect",
    color = MaterialTheme.colorScheme.primary
)
```

### Custom Messages

Always provide context-specific error messages:

```kotlin
ErrorCard(
    errorType = ErrorType.ServerError,
    message = "Unable to save your spiritual profile. Please try again in a moment."
)
```

## Best Practices

1. **Choose the Right Component**
   - Use `ErrorScreen` for critical, page-blocking errors
   - Use `ErrorCard` for section-specific errors
   - Use `ErrorSnackbar` for quick, dismissible notifications
   - Use `ErrorBanner` for persistent but non-critical issues

2. **Provide Context**
   - Always customize error messages to explain what went wrong
   - Offer specific actions the user can take
   - Use encouraging, calm language

3. **Enable Recovery**
   - Always provide a retry option when applicable
   - Offer alternative actions (Settings, Go Back, etc.)
   - Allow dismissal when appropriate

4. **Maintain Aesthetics**
   - Keep the spiritual theme even in error states
   - Use appropriate colors for each error type
   - Ensure smooth animations don't distract

5. **Test Edge Cases**
   - Test all error states in different themes (light/dark)
   - Verify accessibility with screen readers
   - Test on different screen sizes
   - Ensure proper navigation after errors

## Color Reference

All error components use the spiritual color palette:

- **NetworkError**: Sensual Coral (`#F87171`)
- **ServerError**: Intimacy Purple (`#A855F7`)
- **NotFoundError**: Aura Gold (`#FBBF24`)
- **PermissionError**: Mystic Violet (`#7C3AED`)
- **UnknownError**: Tantric Rose (`#EC4899`)
- **Custom**: Configurable (default: Spiritual Purple `#8B5CF6`)

## Animation Details

All components use gentle, non-intrusive animations:

1. **Error Icon**: Subtle rotation (-5° to 5°) and scaling (1.0 to 1.05)
2. **Retry Button**: Gentle pulse when idle (1.0 to 1.02 scale)
3. **Empty State Icon**: Slow breathing animation (1.0 to 1.1 scale)
4. **Banner**: Smooth slide in/out with fade
5. **Loading State**: Standard circular progress with smooth transition

All animations use `FastOutSlowInEasing` for natural movement.

## File Locations

- **Components**: `/core/ui/src/main/java/com/spiritatlas/core/ui/components/ErrorComponents.kt`
- **Examples**: `/core/ui/src/main/java/com/spiritatlas/core/ui/components/ErrorComponentsExample.kt`
- **Documentation**: `/docs/ERROR_COMPONENTS_GUIDE.md`
- **Theme Colors**: `/core/ui/src/main/java/com/spiritatlas/core/ui/theme/Color.kt`

## Contributing

When adding new error components or types:

1. Follow the spiritual theme and calming aesthetic
2. Include proper accessibility support
3. Add usage examples to the example file
4. Document parameters and features
5. Test with screen readers
6. Verify color contrast meets WCAG standards

## Support

For issues or questions about error components:

1. Check the examples file for usage patterns
2. Refer to this documentation for API details
3. Ensure all required parameters are provided
4. Verify theme colors are properly imported
