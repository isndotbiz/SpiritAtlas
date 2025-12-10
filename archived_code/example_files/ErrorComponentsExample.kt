package com.spiritatlas.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.theme.SpiritAtlasTheme

/**
 * Example usage of SpiritAtlas error handling components
 * This file demonstrates all the different error components and their usage patterns
 */

// ═══════════════════════════════════════════════════════════════════════════════
// FULL SCREEN ERROR EXAMPLES
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun NetworkErrorExample() {
    var isRetrying by remember { mutableStateOf(false) }

    ErrorScreen(
        errorType = ErrorType.NetworkError,
        onRetry = {
            isRetrying = true
            // Simulate retry logic
            // In real app: viewModel.retryConnection()
        },
        customAction = "Settings" to {
            // Navigate to settings
        }
    )
}

@Composable
fun ServerErrorExample() {
    ErrorScreen(
        errorType = ErrorType.ServerError,
        message = "Our spiritual servers are performing a cosmic upgrade. Please return in a few moments.",
        onRetry = {
            // Retry logic
        },
        onDismiss = {
            // Navigate back
        }
    )
}

@Composable
fun NotFoundErrorExample() {
    ErrorScreen(
        errorType = ErrorType.NotFoundError,
        message = "This spiritual path no longer exists in our realm.",
        customAction = "Go Home" to {
            // Navigate to home
        },
        onDismiss = {
            // Navigate back
        }
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// INLINE ERROR CARD EXAMPLES
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun ErrorCardExamples() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Error Card Examples",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        // Standard error card
        item {
            ErrorCard(
                errorType = ErrorType.NetworkError,
                onRetry = {
                    // Retry logic
                },
                onDismiss = {
                    // Dismiss logic
                }
            )
        }

        // Compact error card
        item {
            ErrorCard(
                errorType = ErrorType.PermissionError,
                message = "You need special access to view this content.",
                compact = true,
                onRetry = {
                    // Request permissions
                }
            )
        }

        // Custom error card
        item {
            ErrorCard(
                errorType = ErrorType.Custom(
                    customIcon = Icons.Default.Star,
                    customTitle = "Astrological Data Unavailable",
                    customMessage = "The planetary alignments are temporarily obscured. Please try again later.",
                    customColor = MaterialTheme.colorScheme.tertiary
                ),
                onRetry = {
                    // Retry logic
                }
            )
        }

        // Server error card
        item {
            ErrorCard(
                errorType = ErrorType.ServerError,
                message = "The spiritual database is realigning.",
                onRetry = null,
                onDismiss = {
                    // Dismiss
                }
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// ERROR SNACKBAR EXAMPLES
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun ErrorSnackbarExample() {
    val snackbarHostState = remember { SnackbarHostState() }
    var showSnackbar by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                ErrorSnackbar(
                    errorType = ErrorType.NetworkError,
                    message = data.visuals.message,
                    action = data.visuals.actionLabel?.let { label ->
                        label to {
                            // Action logic
                        }
                    },
                    onDismiss = {
                        data.dismiss()
                    }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {
                showSnackbar = true
            }) {
                Text("Show Error Snackbar")
            }

            LaunchedEffect(showSnackbar) {
                if (showSnackbar) {
                    snackbarHostState.showErrorSnackbar(
                        errorType = ErrorType.NetworkError,
                        message = "Connection to the cosmic servers lost",
                        actionLabel = "Retry"
                    )
                    showSnackbar = false
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// ERROR BANNER EXAMPLES
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun ErrorBannerExample() {
    var showBanner by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize()) {
        ErrorBanner(
            errorType = ErrorType.NetworkError,
            message = "You're currently offline. Some features may be limited.",
            visible = showBanner,
            onDismiss = { showBanner = false },
            action = "Retry" to {
                // Retry connection
            }
        )

        // Rest of your content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Main content goes here")
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// NETWORK ERROR VIEW EXAMPLE
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun NetworkErrorViewExample() {
    var isRetrying by remember { mutableStateOf(false) }

    NetworkErrorView(
        onRetry = {
            isRetrying = true
            // Implement retry logic
            // viewModel.retryNetworkConnection()
        },
        customMessage = "The universe is waiting to reconnect with you. Check your connection and try again."
    )
}

// ═══════════════════════════════════════════════════════════════════════════════
// EMPTY STATE EXAMPLES
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun EmptyStateExamples() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // No readings yet
        item {
            EmptyStateView(
                title = "No Readings Yet",
                message = "Begin your spiritual journey by creating your first reading. The stars are waiting to guide you.",
                icon = Icons.Default.Star,
                actionText = "Create Reading",
                onAction = {
                    // Navigate to create reading
                }
            )
        }

        // No connections
        item {
            EmptyStateView(
                title = "No Connections",
                message = "You haven't connected with any spiritual companions yet. Start exploring kindred souls.",
                icon = Icons.Default.Favorite,
                actionText = "Explore",
                onAction = {
                    // Navigate to explore
                }
            )
        }

        // Search results empty
        item {
            EmptyStateView(
                title = "No Results Found",
                message = "The cosmic search didn't find any matches. Try adjusting your search criteria.",
                icon = Icons.Default.Search,
                actionText = "Clear Filters",
                onAction = {
                    // Clear search filters
                }
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// RETRY BUTTON EXAMPLES
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun RetryButtonExamples() {
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Retry Button Variants",
            style = MaterialTheme.typography.headlineMedium
        )

        // Standard retry button
        RetryButton(
            onClick = {
                isLoading = true
                // Simulate async operation
            },
            text = "Try Again",
            isLoading = isLoading
        )

        // Loading retry button
        RetryButton(
            onClick = { },
            text = "Reconnecting",
            isLoading = true
        )

        // Custom color retry button
        RetryButton(
            onClick = { },
            text = "Restore Connection",
            color = MaterialTheme.colorScheme.tertiary
        )

        // Disabled retry button
        RetryButton(
            onClick = { },
            text = "Try Again",
            enabled = false
        )

        // Simulate loading completion
        LaunchedEffect(isLoading) {
            if (isLoading) {
                kotlinx.coroutines.delay(2000)
                isLoading = false
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// COMBINED ERROR HANDLING PATTERNS
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Example of handling different loading states with error handling
 */
@Composable
fun LoadingStatePatternExample() {
    var loadingState by remember { mutableStateOf<LoadingState>(LoadingState.Loading) }

    when (loadingState) {
        is LoadingState.Loading -> {
            // Show loading indicator
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }

        is LoadingState.Success -> {
            // Show content
            Text("Content loaded successfully!")
        }

        is LoadingState.Error -> {
            val error = (loadingState as LoadingState.Error)
            ErrorScreen(
                errorType = error.errorType,
                message = error.message,
                onRetry = {
                    loadingState = LoadingState.Loading
                    // Retry logic
                }
            )
        }

        is LoadingState.Empty -> {
            EmptyStateView(
                title = "No Data Available",
                message = "Start your journey by adding your first spiritual reading.",
                actionText = "Get Started",
                onAction = {
                    // Navigate to create
                }
            )
        }
    }
}

/**
 * Sealed class for managing different loading states
 */
sealed class LoadingState {
    data object Loading : LoadingState()
    data object Success : LoadingState()
    data object Empty : LoadingState()
    data class Error(
        val errorType: ErrorType,
        val message: String? = null
    ) : LoadingState()
}

// ═══════════════════════════════════════════════════════════════════════════════
// ERROR HANDLING WITH SNACKBAR HOST
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun CompleteErrorHandlingExample() {
    val snackbarHostState = remember { SnackbarHostState() }
    var showBanner by remember { mutableStateOf(false) }
    var pageError by remember { mutableStateOf<ErrorType?>(null) }

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Top banner for persistent but non-critical errors
            ErrorBanner(
                errorType = ErrorType.NetworkError,
                message = "Limited connectivity",
                visible = showBanner,
                onDismiss = { showBanner = false }
            )

            // Main content area
            if (pageError != null) {
                // Show full screen error
                ErrorScreen(
                    errorType = pageError!!,
                    onRetry = {
                        pageError = null
                        // Retry logic
                    },
                    onDismiss = {
                        pageError = null
                    }
                )
            } else {
                // Regular content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(onClick = { showBanner = true }) {
                        Text("Show Banner Error")
                    }

                    Button(onClick = {
                        // Show snackbar error
                        // Use LaunchedEffect or coroutine scope to show
                    }) {
                        Text("Show Snackbar Error")
                    }

                    Button(onClick = {
                        pageError = ErrorType.ServerError
                    }) {
                        Text("Show Full Screen Error")
                    }

                    // Content section with inline error
                    ErrorCard(
                        errorType = ErrorType.PermissionError,
                        message = "Unable to load this section",
                        compact = true,
                        onRetry = {
                            // Retry loading this section
                        }
                    )
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// THEME PREVIEW
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
fun ErrorComponentsShowcase() {
    SpiritAtlasTheme {
        Surface {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    Text(
                        text = "Error Components Showcase",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }

                item {
                    Text(
                        text = "Error Cards",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                item {
                    ErrorCard(
                        errorType = ErrorType.NetworkError,
                        onRetry = { }
                    )
                }

                item {
                    ErrorCard(
                        errorType = ErrorType.ServerError,
                        compact = true,
                        onDismiss = { }
                    )
                }

                item {
                    Text(
                        text = "Retry Buttons",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                item {
                    RetryButton(onClick = { })
                }

                item {
                    RetryButton(
                        onClick = { },
                        isLoading = true
                    )
                }

                item {
                    Text(
                        text = "Empty States",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                item {
                    Box(modifier = Modifier.height(400.dp)) {
                        EmptyStateView(
                            title = "No Content",
                            message = "There's nothing here yet. Start your journey now!",
                            actionText = "Get Started",
                            onAction = { }
                        )
                    }
                }
            }
        }
    }
}
