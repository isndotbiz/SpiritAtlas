package com.spiritatlas.feature.profile

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spiritatlas.core.ui.components.*
import com.spiritatlas.core.ui.components.SacredGeometryBackground
import com.spiritatlas.core.ui.theme.*
import com.spiritatlas.domain.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

/**
 * Modern profile library with Co-Star inspired design
 * Shows all profiles, allows sharing, and enables compatibility analysis
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileLibraryScreen(
    onNavigateToProfile: (String) -> Unit,
    onNavigateToCompatibility: (String, String) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: ProfileLibraryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val profiles by viewModel.profiles.collectAsState()
    
    var selectedForComparison by remember { mutableStateOf<Set<String>>(emptySet()) }
    var showSearch by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    // Memoize filtered profiles to avoid recomputation on every recomposition
    val filteredProfiles by remember(profiles, searchQuery) {
        derivedStateOf {
            if (searchQuery.isBlank()) profiles
            else profiles.filter { profile ->
                profile.profileName.contains(searchQuery, ignoreCase = true) ||
                profile.displayName?.contains(searchQuery, ignoreCase = true) == true
            }
        }
    }

    SacredGeometryBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Modern App Bar
            ProfileLibraryTopBar(
                showSearch = showSearch,
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onToggleSearch = { showSearch = !showSearch },
                onNavigateBack = onNavigateBack,
                selectedCount = selectedForComparison.size,
                onClearSelection = { selectedForComparison = emptySet() },
                onCompareSelected = {
                    if (selectedForComparison.size == 2) {
                        val profileIds = selectedForComparison.toList()
                        onNavigateToCompatibility(profileIds[0], profileIds[1])
                    }
                }
            )
            
            // Profile Grid/List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Quick Actions Section
                item {
                    QuickActionsSection(
                        onCreateNewProfile = { onNavigateToProfile("") },
                        onImportProfile = { viewModel.importProfile() },
                        onExportAll = { viewModel.exportAllProfiles() }
                    )
                }
                
                // Profiles with stagger animation
                itemsIndexed(
                    items = filteredProfiles,
                    key = { _, profile -> profile.id }
                ) { index, profile ->
                    EnhancedModernProfileCard(
                        profile = profile,
                        isSelected = selectedForComparison.contains(profile.id),
                        onCardClick = { onNavigateToProfile(profile.id) },
                        onSelectionToggle = {
                            selectedForComparison = if (selectedForComparison.contains(profile.id)) {
                                selectedForComparison - profile.id
                            } else if (selectedForComparison.size < 2) {
                                selectedForComparison + profile.id
                            } else selectedForComparison
                        },
                        onShareProfile = { viewModel.shareProfile(profile.id) },
                        onEnrichProfile = { viewModel.enrichProfile(profile.id) },
                        onDeleteProfile = { viewModel.deleteProfile(profile.id) },
                        selectionMode = selectedForComparison.isNotEmpty(),
                        index = index
                    )
                }
                
                // Empty state
                if (profiles.isEmpty()) {
                    item {
                        EmptyProfileLibrary(
                            onCreateProfile = { onNavigateToProfile("") }
                        )
                    }
                }
            }
        }
    }

    // Handle UI state changes
    LaunchedEffect(uiState.message) {
        uiState.message?.let { message ->
            // Show snackbar with message
            viewModel.clearMessage()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileLibraryTopBar(
    showSearch: Boolean,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onToggleSearch: () -> Unit,
    onNavigateBack: () -> Unit,
    selectedCount: Int,
    onClearSelection: () -> Unit,
    onCompareSelected: () -> Unit
) {
    if (selectedCount > 0) {
        // Selection mode top bar
        TopAppBar(
            title = {
                Text(
                    text = "$selectedCount selected",
                    fontWeight = FontWeight.SemiBold
                )
            },
            navigationIcon = {
                IconButton(onClick = onClearSelection) {
                    Icon(Icons.Default.Close, contentDescription = "Clear selection")
                }
            },
            actions = {
                if (selectedCount == 2) {
                    SpiritualButton(
                        text = "Compare",
                        onClick = onCompareSelected,
                        style = SpiritualButtonStyle.PRIMARY,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White.copy(alpha = 0.95f)
            )
        )
    } else if (showSearch) {
        // Search mode top bar
        TopAppBar(
            title = {
                TextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = { Text("Search profiles...") },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            },
            navigationIcon = {
                IconButton(onClick = onToggleSearch) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Close search")
                }
            },
            actions = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear search")
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White.copy(alpha = 0.95f)
            )
        )
    } else {
        // Normal mode top bar
        TopAppBar(
            title = {
                Text(
                    text = "Profile Library",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = SpiritualPurple
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = onToggleSearch) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )
    }
}

// ===== ENHANCED PROFILE CARD WITH PREMIUM VISUAL EFFECTS =====

@Composable
fun EnhancedModernProfileCard(
    profile: UserProfile,
    isSelected: Boolean,
    onCardClick: () -> Unit,
    onSelectionToggle: () -> Unit,
    onShareProfile: () -> Unit,
    onEnrichProfile: () -> Unit,
    onDeleteProfile: () -> Unit,
    selectionMode: Boolean,
    index: Int
) {
    var isPressed by remember { mutableStateOf(false) }
    var showActions by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Entrance animation with stagger
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(index * 50L) // Stagger by 50ms
        isVisible = true
    }

    // Press animation
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    // Selection border animation
    val infiniteTransition = rememberInfiniteTransition(label = "border")
    val animatedBorderOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "borderOffset"
    )

    // Entrance animation
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(400)) +
                slideInVertically(
                    initialOffsetY = { it / 2 },
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(200))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .scale(scale)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            val released = try {
                                tryAwaitRelease()
                            } finally {
                                isPressed = false
                            }
                        },
                        onTap = {
                            if (selectionMode) {
                                onSelectionToggle()
                            } else {
                                onCardClick()
                            }
                        },
                        onLongPress = {
                            coroutineScope.launch {
                                showActions = !showActions
                            }
                        }
                    )
                }
        ) {
            // Main Card with glassmorphism
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = if (isSelected) 12.dp else 4.dp,
                        shape = RoundedCornerShape(24.dp),
                        spotColor = SpiritualPurple.copy(alpha = 0.3f)
                    )
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        brush = if (isSelected) {
                            Brush.linearGradient(
                                colors = listOf(
                                    SpiritualPurple.copy(alpha = 0.15f),
                                    MysticViolet.copy(alpha = 0.1f)
                                )
                            )
                        } else {
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.95f),
                                    Color.White.copy(alpha = 0.85f)
                                )
                            )
                        }
                    )
                    .then(
                        if (isSelected) {
                            Modifier.border(
                                width = 2.dp,
                                brush = Brush.sweepGradient(
                                    colors = listOf(
                                        SpiritualPurple,
                                        MysticViolet,
                                        AuraGold,
                                        SpiritualPurple
                                    ),
                                    center = Offset(
                                        cos(Math.toRadians(animatedBorderOffset.toDouble())).toFloat(),
                                        sin(Math.toRadians(animatedBorderOffset.toDouble())).toFloat()
                                    )
                                ),
                                shape = RoundedCornerShape(24.dp)
                            )
                        } else Modifier
                    )
                    .blur(if (isSelected) 0.dp else 0.5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left: Avatar with completion ring
                    AnimatedProfileAvatar(
                        profile = profile,
                        completionPercentage = profile.profileCompletion.completionPercentage,
                        modifier = Modifier.size(80.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Center: Profile info
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        // Name
                        Text(
                            text = profile.profileName,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = SpiritualPurple,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        profile.displayName?.let { displayName ->
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = displayName,
                                fontSize = 14.sp,
                                color = DeepTeal.copy(alpha = 0.8f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Spiritual badges row
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Zodiac badge (mock data - replace with real)
                            profile.birthDateTime?.let {
                                ZodiacBadge(
                                    zodiacSign = getZodiacSign(it.monthValue, it.dayOfMonth)
                                )
                            }

                            // Life path badge (mock data - replace with real)
                            profile.name?.let {
                                LifePathBadge(lifePathNumber = calculateLifePath(it))
                            }

                            // Dosha indicator (mock - placeholder)
                            DoshaIndicator(doshaType = "Vata")
                        }

                        // Enrichment status
                        if (profile.enrichmentResult != null) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Enriched",
                                    tint = AuraGold,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "AI Enriched",
                                    fontSize = 12.sp,
                                    color = AuraGold,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Right: Action button or checkbox
                    if (selectionMode) {
                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = { onSelectionToggle() },
                            colors = CheckboxDefaults.colors(
                                checkedColor = SpiritualPurple
                            )
                        )
                    } else {
                        IconButton(
                            onClick = { expanded = !expanded },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = if (expanded) Icons.Default.KeyboardArrowUp
                                             else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Expand",
                                tint = SpiritualPurple
                            )
                        }
                    }
                }
            }

            // Expandable actions with smooth animation
            AnimatedVisibility(
                visible = expanded && !selectionMode,
                enter = expandVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ) + fadeIn(),
                exit = shrinkVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ) + fadeOut()
            ) {
                SwipeActions(
                    onEdit = onCardClick,
                    onShare = onShareProfile,
                    onEnrich = onEnrichProfile,
                    onDelete = onDeleteProfile,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

// ===== HELPER COMPOSABLES =====

@Composable
fun AnimatedProfileAvatar(
    profile: UserProfile,
    completionPercentage: Double,
    modifier: Modifier = Modifier
) {
    // Pulsing animation for completion ring
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    val completionColor = when {
        completionPercentage >= 80 -> CosmicBlue
        completionPercentage >= 60 -> AuraGold
        completionPercentage >= 40 -> TantricRose
        else -> SpiritualPurple.copy(alpha = 0.5f)
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Completion ring
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    val strokeWidth = 6.dp.toPx()
                    val radius = (size.minDimension - strokeWidth) / 2

                    // Background ring
                    drawCircle(
                        color = Color.Gray.copy(alpha = 0.1f),
                        radius = radius,
                        style = Stroke(width = strokeWidth)
                    )

                    // Progress ring
                    drawArc(
                        color = completionColor.copy(alpha = pulseAlpha),
                        startAngle = -90f,
                        sweepAngle = (completionPercentage / 100 * 360).toFloat(),
                        useCenter = false,
                        style = Stroke(
                            width = strokeWidth,
                            cap = androidx.compose.ui.graphics.StrokeCap.Round
                        )
                    )
                }
        )

        // Avatar circle with initials
        Box(
            modifier = Modifier
                .fillMaxSize(0.75f)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            SpiritualPurple.copy(alpha = 0.8f),
                            MysticViolet.copy(alpha = 0.6f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getInitials(profile.profileName),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun ZodiacBadge(zodiacSign: String) {
    // Glow animation
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    Box(
        modifier = Modifier
            .size(32.dp)
            .shadow(
                elevation = 4.dp,
                shape = CircleShape,
                spotColor = AuraGold.copy(alpha = glowAlpha)
            )
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        AuraGold.copy(alpha = 0.9f),
                        CosmicOrange.copy(alpha = 0.7f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = getZodiacEmoji(zodiacSign),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LifePathBadge(lifePathNumber: Int) {
    // Pulsing animation
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val numerologyColor = SpiritualColors.NumerologyColors[lifePathNumber] ?: SpiritualPurple

    Box(
        modifier = Modifier
            .size(32.dp)
            .scale(scale)
            .shadow(
                elevation = 4.dp,
                shape = CircleShape,
                spotColor = numerologyColor.copy(alpha = 0.5f)
            )
            .clip(CircleShape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        numerologyColor.copy(alpha = 0.9f),
                        numerologyColor.copy(alpha = 0.6f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = lifePathNumber.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun DoshaIndicator(doshaType: String) {
    val doshaColor = when (doshaType) {
        "Vata" -> CosmicBlue
        "Pitta" -> CosmicOrange
        "Kapha" -> ChakraGreen
        else -> SpiritualPurple
    }

    Box(
        modifier = Modifier
            .height(32.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(doshaColor.copy(alpha = 0.2f))
            .border(
                width = 1.dp,
                color = doshaColor.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = doshaType,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = doshaColor
        )
    }
}

@Composable
fun SwipeActions(
    onEdit: () -> Unit,
    onShare: () -> Unit,
    onEnrich: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.8f))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Edit action
        ActionButton(
            icon = Icons.Default.Edit,
            label = "Edit",
            color = SpiritualPurple,
            onClick = onEdit
        )

        // Share action
        ActionButton(
            icon = Icons.Default.Share,
            label = "Share",
            color = CosmicBlue,
            onClick = onShare
        )

        // Enrich action
        ActionButton(
            icon = Icons.Default.Star,
            label = "Enrich",
            color = AuraGold,
            onClick = onEnrich
        )

        // Delete action
        ActionButton(
            icon = Icons.Default.Delete,
            label = "Delete",
            color = ChakraRed,
            onClick = onDelete
        )
    }
}

@Composable
fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scale"
    )

    Column(
        modifier = Modifier
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = { onClick() }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.15f))
                .border(1.dp, color.copy(alpha = 0.3f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = color.copy(alpha = 0.8f)
        )
    }
}

// ===== UTILITY FUNCTIONS =====

private fun getInitials(name: String): String {
    val parts = name.trim().split(" ")
    return when {
        parts.isEmpty() -> "?"
        parts.size == 1 -> parts[0].take(2).uppercase()
        else -> "${parts[0].first()}${parts.last().first()}".uppercase()
    }
}

private fun getZodiacSign(month: Int, day: Int): String {
    return when {
        (month == 1 && day >= 20) || (month == 2 && day <= 18) -> "Aquarius"
        (month == 2 && day >= 19) || (month == 3 && day <= 20) -> "Pisces"
        (month == 3 && day >= 21) || (month == 4 && day <= 19) -> "Aries"
        (month == 4 && day >= 20) || (month == 5 && day <= 20) -> "Taurus"
        (month == 5 && day >= 21) || (month == 6 && day <= 20) -> "Gemini"
        (month == 6 && day >= 21) || (month == 7 && day <= 22) -> "Cancer"
        (month == 7 && day >= 23) || (month == 8 && day <= 22) -> "Leo"
        (month == 8 && day >= 23) || (month == 9 && day <= 22) -> "Virgo"
        (month == 9 && day >= 23) || (month == 10 && day <= 22) -> "Libra"
        (month == 10 && day >= 23) || (month == 11 && day <= 21) -> "Scorpio"
        (month == 11 && day >= 22) || (month == 12 && day <= 21) -> "Sagittarius"
        else -> "Capricorn"
    }
}

private fun getZodiacEmoji(sign: String): String {
    return when (sign) {
        "Aries" -> "♈"
        "Taurus" -> "♉"
        "Gemini" -> "♊"
        "Cancer" -> "♋"
        "Leo" -> "♌"
        "Virgo" -> "♍"
        "Libra" -> "♎"
        "Scorpio" -> "♏"
        "Sagittarius" -> "♐"
        "Capricorn" -> "♑"
        "Aquarius" -> "♒"
        "Pisces" -> "♓"
        else -> "✨"
    }
}

private fun calculateLifePath(name: String): Int {
    // Simple numerology calculation (sum of ASCII values mod 9)
    val sum = name.filter { it.isLetter() }.sumOf { it.code }
    val reduced = sum % 9
    return if (reduced == 0) 9 else reduced
}

@Composable
fun QuickActionsSection(
    onCreateNewProfile: () -> Unit,
    onImportProfile: () -> Unit,
    onExportAll: () -> Unit
) {
    SpiritualSectionHeader(
        title = "Quick Actions",
        subtitle = "Create, import, or manage profiles"
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SpiritualButton(
            text = "➕ New Profile",
            onClick = onCreateNewProfile,
            style = SpiritualButtonStyle.PRIMARY,
            modifier = Modifier.weight(1f)
        )
        
        SpiritualButton(
            text = "📥 Import",
            onClick = onImportProfile,
            style = SpiritualButtonStyle.SECONDARY,
            modifier = Modifier.weight(1f)
        )
    }
    
    Spacer(modifier = Modifier.height(32.dp))
    
    SpiritualSectionHeader(
        title = "All Profiles",
        subtitle = "Select two profiles to analyze compatibility"
    )
    
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun EmptyProfileLibrary(
    onCreateProfile: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "✨",
            fontSize = 64.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "No Profiles Yet",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = SpiritualPurple
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Create your first spiritual profile to begin your journey of self-discovery and find your perfect compatibility matches.",
            fontSize = 16.sp,
            color = DeepTeal,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            lineHeight = 24.sp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        SpiritualButton(
            text = "Create First Profile",
            onClick = onCreateProfile,
            style = SpiritualButtonStyle.PRIMARY,
            modifier = Modifier.fillMaxWidth(0.7f)
        )
    }
}
