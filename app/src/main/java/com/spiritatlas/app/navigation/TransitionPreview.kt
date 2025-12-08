package com.spiritatlas.app.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Preview composable to visualize navigation transitions in Android Studio.
 * Demonstrates all transition types in an interactive format.
 *
 * To use:
 * 1. Open this file in Android Studio
 * 2. Click the "Split" or "Design" view
 * 3. Interact with the preview to see transitions
 */

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun TransitionPreviewScreen() {
    var selectedTransition by remember { mutableStateOf(TransitionType.FADE_THROUGH) }
    var showScreen by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Transition selector
        TransitionSelector(
            selectedTransition = selectedTransition,
            onTransitionSelected = { selectedTransition = it }
        )

        // Transition demo area
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AnimatedContent(
                targetState = showScreen,
                transitionSpec = {
                    val enter = getEnterTransition(selectedTransition, forward = targetState)
                    val exit = getExitTransition(selectedTransition, forward = targetState)
                    enter togetherWith exit
                },
                label = "transition_preview"
            ) { showing ->
                if (showing) {
                    DemoScreen(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        title = "Screen A",
                        description = "Tap to transition"
                    ) {
                        showScreen = false
                    }
                } else {
                    DemoScreen(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        title = "Screen B",
                        description = "Tap to go back"
                    ) {
                        showScreen = true
                    }
                }
            }
        }

        // Info panel
        TransitionInfo(
            transitionType = selectedTransition,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun TransitionSelector(
    selectedTransition: TransitionType,
    onTransitionSelected: (TransitionType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Text(
            text = "Select Transition",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TransitionType.entries.take(9).forEach { type ->
                TransitionChip(
                    text = type.name.replace("_", " "),
                    selected = type == selectedTransition,
                    onClick = { onTransitionSelected(type) }
                )
            }
        }
    }
}

@Composable
private fun TransitionChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
        )
    }
}

@Composable
private fun DemoScreen(
    color: Color,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun TransitionInfo(
    transitionType: TransitionType,
    modifier: Modifier = Modifier
) {
    val info = when (transitionType) {
        TransitionType.SHARED_ELEMENT -> TransitionDetails(
            "Shared Element",
            "Morphing transition for cards to details",
            "${SpiritDurations.MEDIUM}ms",
            "Profile list to detail"
        )
        TransitionType.FADE_THROUGH -> TransitionDetails(
            "Fade Through",
            "Material motion for peer navigation",
            "${SpiritDurations.MEDIUM}ms",
            "Between main sections"
        )
        TransitionType.SLIDE_UP -> TransitionDetails(
            "Slide Up",
            "Bottom sheet and modal presentation",
            "${SpiritDurations.MEDIUM}ms",
            "Settings, consent screens"
        )
        TransitionType.SLIDE_HORIZONTAL -> TransitionDetails(
            "Slide Horizontal",
            "Traditional forward/back navigation",
            "${SpiritDurations.MEDIUM}ms",
            "Sequential flows, wizards"
        )
        TransitionType.ZOOM_IN -> TransitionDetails(
            "Zoom In",
            "Expands from center for focus",
            "${SpiritDurations.SLOW}ms",
            "Detail views, enrichment"
        )
        TransitionType.CROSS_FADE -> TransitionDetails(
            "Cross Fade",
            "Peaceful blending without movement",
            "${SpiritDurations.MEDIUM}ms",
            "Tab switches"
        )
        TransitionType.CIRCULAR_REVEAL -> TransitionDetails(
            "Circular Reveal",
            "Dramatic expansion for revelations",
            "${SpiritDurations.MYSTICAL}ms",
            "Compatibility results"
        )
        TransitionType.SCALE_FADE -> TransitionDetails(
            "Scale Fade",
            "Mystical floating consciousness",
            "${SpiritDurations.SLOW}ms",
            "Spiritual content"
        )
        TransitionType.NONE -> TransitionDetails(
            "None",
            "Instant transition, no animation",
            "0ms",
            "Accessibility, performance"
        )
    }

    Card(
        modifier = modifier.padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = info.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = info.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoChip("Duration", info.duration)
                InfoChip("Best For", info.bestFor)
            }
        }
    }
}

@Composable
private fun InfoChip(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}

private data class TransitionDetails(
    val name: String,
    val description: String,
    val duration: String,
    val bestFor: String
)

/**
 * Preview for individual transition types
 */
@Preview(name = "Fade Through", showBackground = true)
@Composable
fun PreviewFadeThrough() {
    var showing by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable { showing = !showing }
    ) {
        AnimatedContent(
            targetState = showing,
            transitionSpec = {
                fadeThroughEnter() togetherWith fadeThroughExit()
            }
        ) { state ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        if (state) Color(0xFFE1BEE7) else Color(0xFFBBDEFB)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (state) "Screen 1" else "Screen 2",
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
}

@Preview(name = "Zoom In", showBackground = true)
@Composable
fun PreviewZoomIn() {
    var showing by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable { showing = !showing }
    ) {
        AnimatedContent(
            targetState = showing,
            transitionSpec = {
                zoomInEnter() togetherWith zoomInExit()
            }
        ) { state ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        if (state) Color(0xFFFFF9C4) else Color(0xFFC8E6C9)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (state) "List View" else "Detail View",
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
}

@Preview(name = "Circular Reveal", showBackground = true)
@Composable
fun PreviewCircularReveal() {
    var showing by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable { showing = !showing }
    ) {
        AnimatedContent(
            targetState = showing,
            transitionSpec = {
                circularRevealEnter() togetherWith circularRevealExit()
            }
        ) { state ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = if (state) {
                                listOf(Color(0xFFFF6F00), Color(0xFFFFD54F))
                            } else {
                                listOf(Color(0xFF1976D2), Color(0xFF64B5F6))
                            }
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (state) "Calculating..." else "Compatible!",
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
                )
            }
        }
    }
}
