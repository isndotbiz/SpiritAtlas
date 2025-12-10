package com.spiritatlas.core.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spiritatlas.core.ui.animation.pulsingAnimation
import com.spiritatlas.core.ui.haptics.HapticFeedbackType
import com.spiritatlas.core.ui.haptics.rememberHapticFeedback
import com.spiritatlas.core.ui.theme.*
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month

/**
 * DELIGHT MOMENTS
 *
 * Easter eggs, surprises, and personalized touches that make users smile.
 *
 * Features:
 * - Personalized greetings based on time and astrology
 * - Special date celebrations (birthdays, full moon, solstice, etc.)
 * - Hidden easter eggs for power users
 * - Encouraging messages and affirmations
 * - Surprise animations
 * - Cosmic event notifications
 */

// ============================================================================
// PERSONALIZED GREETINGS
// ============================================================================

/**
 * Personalized greeting that changes based on:
 * - Time of day
 * - Current moon phase
 * - Planetary transits
 * - Special dates
 *
 * Usage:
 * ```kotlin
 * PersonalizedGreeting(
 *     userName = "Sarah",
 *     zodiacSign = "Sagittarius"
 * )
 * ```
 */
@Composable
fun PersonalizedGreeting(
    modifier: Modifier = Modifier,
    userName: String? = null,
    zodiacSign: String? = null
) {
    val greeting = remember { generatePersonalizedGreeting(userName, zodiacSign) }
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(600)) + slideInVertically { -it / 2 }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Greeting emoji
            Text(
                text = greeting.emoji,
                fontSize = 48.sp
            )

            // Main greeting
            Text(
                text = greeting.message,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = SpiritualPurple,
                textAlign = TextAlign.Center
            )

            // Subtext
            greeting.subtext?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

private data class Greeting(
    val emoji: String,
    val message: String,
    val subtext: String? = null
)

private fun generatePersonalizedGreeting(
    userName: String?,
    zodiacSign: String?
): Greeting {
    val now = LocalDateTime.now()
    val hour = now.hour
    val date = now.toLocalDate()

    // Check for special dates first
    getSpecialDateGreeting(date)?.let { return it }

    // Time-based greetings
    val timeGreeting = when (hour) {
        in 0..4 -> Greeting(
            emoji = "🌙",
            message = "Midnight mystic${userName?.let { ", $it" } ?: ""}",
            subtext = "The veil between worlds is thinnest now"
        )
        in 5..11 -> Greeting(
            emoji = "🌅",
            message = "Good morning${userName?.let { ", $it" } ?: ""}",
            subtext = "May your day be filled with cosmic alignment"
        )
        in 12..16 -> Greeting(
            emoji = "☀️",
            message = "Good afternoon${userName?.let { ", $it" } ?: ""}",
            subtext = "The universe is conspiring in your favor"
        )
        in 17..20 -> Greeting(
            emoji = "🌇",
            message = "Good evening${userName?.let { ", $it" } ?: ""}",
            subtext = "Perfect time for reflection and gratitude"
        )
        else -> Greeting(
            emoji = "✨",
            message = "Hello, starseeker${userName?.let { " $it" } ?: ""}",
            subtext = "The cosmos await your exploration"
        )
    }

    return timeGreeting
}

private fun getSpecialDateGreeting(date: LocalDate): Greeting? {
    // Full Moon (approximate - would need real calculation)
    // This is a simplified check, replace with actual lunar calendar

    // Solstices and Equinoxes
    return when {
        date.month == Month.DECEMBER && date.dayOfMonth == 21 -> Greeting(
            emoji = "❄️",
            message = "Happy Winter Solstice!",
            subtext = "The longest night, a time for renewal and rebirth"
        )
        date.month == Month.MARCH && date.dayOfMonth == 20 -> Greeting(
            emoji = "🌸",
            message = "Happy Spring Equinox!",
            subtext = "Balance and new beginnings fill the air"
        )
        date.month == Month.JUNE && date.dayOfMonth == 21 -> Greeting(
            emoji = "☀️",
            message = "Happy Summer Solstice!",
            subtext = "The sun's power is at its peak today"
        )
        date.month == Month.SEPTEMBER && date.dayOfMonth == 22 -> Greeting(
            emoji = "🍂",
            message = "Happy Autumn Equinox!",
            subtext = "Time to harvest what you've sown"
        )
        else -> null
    }
}

// ============================================================================
// BIRTHDAY CELEBRATIONS
// ============================================================================

/**
 * Special birthday celebration animation
 *
 * Shows when user's birthday or saved profile birthday matches current date
 */
@Composable
fun BirthdayCelebration(
    modifier: Modifier = Modifier,
    name: String,
    age: Int? = null,
    zodiacSign: String? = null,
    onDismiss: () -> Unit = {}
) {
    val haptic = rememberHapticFeedback()
    var showConfetti by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        haptic.performHaptic(HapticFeedbackType.SPIRITUAL_PULSE)
        delay(500)
        showConfetti = true
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (showConfetti) {
            ConfettiCelebration(
                particleCount = 150,
                onComplete = { }
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp)
        ) {
            GlassmorphCard(elevation = 3) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "🎂",
                        fontSize = 64.sp
                    )

                    Text(
                        text = "Happy Birthday, $name!",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = AuraGold,
                        textAlign = TextAlign.Center
                    )

                    age?.let {
                        Text(
                            text = "Another trip around the sun complete!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center
                        )
                    }

                    zodiacSign?.let {
                        Text(
                            text = "May this $it year bring you cosmic blessings",
                            style = MaterialTheme.typography.bodyMedium,
                            color = SpiritualPurple,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    SpiritualButton(
                        onClick = onDismiss,
                        text = "Thank You",
                        style = SpiritualButtonStyle.PRIMARY,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

// ============================================================================
// EASTER EGGS
// ============================================================================

/**
 * Hidden easter egg detector
 *
 * Detects special tap patterns, gestures, or conditions
 *
 * Usage:
 * ```kotlin
 * var easterEggFound by remember { mutableStateOf(false) }
 *
 * Box(
 *     modifier = Modifier.easterEggTrigger(
 *         onEasterEggFound = { easterEggFound = true }
 *     )
 * ) {
 *     AppLogo()
 * }
 * ```
 */
fun Modifier.easterEggTrigger(
    tapCount: Int = 7,
    timeWindowMs: Long = 3000,
    onEasterEggFound: () -> Unit
): Modifier = this.then(
    Modifier.pointerInput(Unit) {
        var taps = 0
        var lastTapTime = 0L

        detectTapGestures(
            onTap = {
                val currentTime = System.currentTimeMillis()

                if (currentTime - lastTapTime > timeWindowMs) {
                    taps = 1
                } else {
                    taps++
                }

                lastTapTime = currentTime

                if (taps >= tapCount) {
                    onEasterEggFound()
                    taps = 0
                }
            }
        )
    }
)

/**
 * Easter egg reveal animation
 */
@Composable
fun EasterEggReveal(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    emoji: String = "🎉",
    onDismiss: () -> Unit = {}
) {
    val haptic = rememberHapticFeedback()
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        haptic.performHaptic(HapticFeedbackType.SUCCESS)
        delay(200)
        visible = true
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(500)) + slideInVertically { it / 2 }
        ) {
            Box(
                modifier = Modifier
                    .padding(32.dp)
                    .clickable { onDismiss() }
            ) {
                GlassmorphCard(elevation = 3) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = emoji,
                            fontSize = 64.sp
                        )

                        Text(
                            text = "Secret Discovered!",
                            style = MaterialTheme.typography.labelLarge,
                            color = AuraGold,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "Tap to dismiss",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}

// ============================================================================
// COSMIC EVENT NOTIFICATIONS
// ============================================================================

/**
 * Notification for cosmic events (Mercury retrograde, full moon, etc.)
 */
@Composable
fun CosmicEventNotification(
    modifier: Modifier = Modifier,
    event: CosmicEvent,
    onDismiss: () -> Unit = {}
) {
    var visible by remember { mutableStateOf(false) }
    val haptic = rememberHapticFeedback()

    LaunchedEffect(Unit) {
        delay(500)
        haptic.performHaptic(HapticFeedbackType.SPIRITUAL_PULSE)
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(600)) + slideInVertically { -it }
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            GlassmorphCard(
                elevation = 2,
                modifier = Modifier.clickable { onDismiss() }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Event icon with pulse
                    Text(
                        text = event.emoji,
                        fontSize = 48.sp,
                        modifier = Modifier.pulsingAnimation()
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Cosmic Event",
                            style = MaterialTheme.typography.labelSmall,
                            color = CosmicBlue,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = event.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = event.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}

data class CosmicEvent(
    val title: String,
    val description: String,
    val emoji: String,
    val type: CosmicEventType
)

enum class CosmicEventType {
    FULL_MOON,
    NEW_MOON,
    MERCURY_RETROGRADE,
    PLANETARY_TRANSIT,
    ECLIPSE,
    SOLSTICE,
    EQUINOX
}

// ============================================================================
// ENCOURAGING MESSAGES
// ============================================================================

/**
 * Random encouraging message that appears occasionally
 *
 * Shows supportive, spiritual messages to uplift users
 */
@Composable
fun EncouragingMessage(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {}
) {
    val messages = remember {
        listOf(
            "You're exactly where you need to be ✨",
            "Your energy is magnetic today 🌟",
            "The universe is working in your favor 🌌",
            "Trust your intuition, it's your superpower 🦋",
            "You're growing in beautiful ways 🌱",
            "Your light is needed in this world 💫",
            "Every challenge is a cosmic lesson 🔮",
            "You're aligned with your highest good 🧘",
            "The stars are proud of you tonight ⭐",
            "You're manifesting magic daily 🪄"
        )
    }

    val message = remember { messages.random() }
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
        delay(4000)
        visible = false
        delay(500)
        onDismiss()
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(500)) + slideInVertically { it / 2 },
        exit = fadeOut(tween(500))
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                SpiritualPurple.copy(alpha = 0.9f),
                                MysticViolet.copy(alpha = 0.9f)
                            )
                        )
                    )
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// ============================================================================
// MILESTONE CELEBRATIONS
// ============================================================================

/**
 * Celebrate app usage milestones
 */
@Composable
fun MilestoneCelebration(
    modifier: Modifier = Modifier,
    milestone: AppMilestone,
    onDismiss: () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Show confetti
        ConfettiCelebration(
            particleCount = 80,
            onComplete = {}
        )

        // Show milestone message
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp)
        ) {
            GlassmorphCard(elevation = 3) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = milestone.emoji,
                        fontSize = 64.sp
                    )

                    Text(
                        text = "Milestone Reached!",
                        style = MaterialTheme.typography.labelLarge,
                        color = AuraGold,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = milestone.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = milestone.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    SpiritualButton(
                        onClick = onDismiss,
                        text = "Continue Journey",
                        style = SpiritualButtonStyle.PRIMARY,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

data class AppMilestone(
    val title: String,
    val description: String,
    val emoji: String,
    val type: MilestoneType
)

enum class MilestoneType {
    FIRST_PROFILE,
    FIVE_PROFILES,
    TEN_PROFILES,
    FIRST_COMPATIBILITY,
    TEN_COMPATIBILITIES,
    SEVEN_DAY_STREAK,
    THIRTY_DAY_STREAK,
    HUNDRED_DAY_STREAK
}

// ============================================================================
// SURPRISE AND DELIGHT
// ============================================================================

/**
 * Random surprise animation that occasionally appears
 *
 * Small delights that make users smile
 */
@Composable
fun SurpriseDelight(
    modifier: Modifier = Modifier
) {
    val surprises = remember {
        listOf("✨", "🌟", "💫", "⭐", "🌙", "☀️", "🌈", "🦋")
    }

    val surprise = remember { surprises.random() }
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(300)
        visible = true
        delay(2000)
        visible = false
    }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "surpriseScale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        label = "surpriseAlpha"
    )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = surprise,
            fontSize = 64.sp,
            modifier = Modifier
                .scale(scale)
                .alpha(alpha)
        )
    }
}
