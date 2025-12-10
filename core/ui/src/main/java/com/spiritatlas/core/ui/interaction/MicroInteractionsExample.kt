package com.spiritatlas.core.ui.interaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.components.SpiritualPullRefresh
import com.spiritatlas.core.ui.components.SpiritualRefreshStyle
import com.spiritatlas.core.ui.haptics.HapticFeedbackType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Example screen demonstrating all micro-interactions
 * Use this as a reference for implementing micro-interactions in your screens
 */
@Composable
fun MicroInteractionsExampleScreen() {
    var showSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var showWarning by remember { mutableStateOf(false) }
    var showInfo by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var isRefreshing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        SpiritualPullRefresh(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                // Simulate network call
                scope.launch {
                    delay(2000)
                    isRefreshing = false
                }
            },
            style = SpiritualRefreshStyle.CHAKRA
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                item {
                    Text(
                        text = "Micro-Interactions Examples",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                // Button Examples
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Interactive Buttons",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .interactiveButton(
                                            hapticType = HapticFeedbackType.LIGHT,
                                            onClick = { showSuccess = true }
                                        )
                                ) {
                                    Button(onClick = {}) {
                                        Text("Success")
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .interactiveButton(
                                            hapticType = HapticFeedbackType.MEDIUM,
                                            onClick = { showError = true }
                                        )
                                ) {
                                    Button(onClick = {}) {
                                        Text("Error")
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .interactiveButton(
                                            hapticType = HapticFeedbackType.LIGHT,
                                            onClick = { showWarning = true }
                                        )
                                ) {
                                    Button(onClick = {}) {
                                        Text("Warning")
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .interactiveButton(
                                            hapticType = HapticFeedbackType.LIGHT,
                                            onClick = { showInfo = true }
                                        )
                                ) {
                                    Button(onClick = {}) {
                                        Text("Info")
                                    }
                                }
                            }
                        }
                    }
                }

                // Card Examples
                item {
                    Text(
                        text = "Interactive Cards",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                items(3) { index ->
                    InteractiveCard(
                        onClick = { /* Navigate or expand */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Interactive Card ${index + 1}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Tap to see elevation and scale animation with haptic feedback",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                // Text Field Examples
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Spiritual Text Fields",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            SpiritualTextField(
                                value = name,
                                onValueChange = {
                                    name = it
                                    nameError = false
                                },
                                label = { Text("Name") },
                                isError = nameError,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    if (name.isEmpty()) {
                                        nameError = true
                                    } else {
                                        showSuccess = true
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Validate")
                            }
                        }
                    }
                }

                // Loading Indicators
                item {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Loading Indicators",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            // TODO: Uncomment when loading indicators are implemented
                            /*
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    MandalaLoadingIndicator(size = 48.dp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Mandala", style = MaterialTheme.typography.labelSmall)
                                }

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    ChakraSequenceIndicator(size = 48.dp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Chakra", style = MaterialTheme.typography.labelSmall)
                                }

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    EnergyFlowIndicator(size = 48.dp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Energy", style = MaterialTheme.typography.labelSmall)
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    ConstellationIndicator(size = 48.dp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Stars", style = MaterialTheme.typography.labelSmall)
                                }

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    YinYangIndicator(size = 48.dp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Yin-Yang", style = MaterialTheme.typography.labelSmall)
                                }

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    ChakraBloomIndicator(size = 48.dp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("Bloom", style = MaterialTheme.typography.labelSmall)
                                }
                            }
                            */
                            Text("Loading indicators examples (to be implemented)", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }

                // Bottom spacing
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }

        // Feedback Overlays
        SuccessFeedback(
            visible = showSuccess,
            message = "Action completed successfully!",
            onComplete = { showSuccess = false },
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp)
        )

        ErrorFeedback(
            visible = showError,
            message = "An error occurred",
            onComplete = { showError = false },
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp)
        )

        WarningFeedback(
            visible = showWarning,
            message = "Please review your input",
            onComplete = { showWarning = false },
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp)
        )

        InfoFeedback(
            visible = showInfo,
            message = "Here's some helpful information",
            onComplete = { showInfo = false },
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp)
        )
    }
}

/**
 * Minimal example showing the essentials
 */
@Composable
fun MinimalMicroInteractionsExample() {
    var showSuccess by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Interactive Button
        Box(
            modifier = Modifier.interactiveButton(
                hapticType = HapticFeedbackType.LIGHT,
                onClick = { showSuccess = true }
            )
        ) {
            Button(onClick = {}) {
                Text("Press Me")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Interactive Card
        InteractiveCard(
            onClick = { /* action */ },
            modifier = Modifier.size(200.dp, 100.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Tap this card")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Loading Indicator (TODO: implement)
        // ChakraSequenceIndicator(size = 64.dp)

        // 4. Success Feedback
        SuccessFeedback(
            visible = showSuccess,
            message = "Success!",
            onComplete = { showSuccess = false }
        )
    }
}
