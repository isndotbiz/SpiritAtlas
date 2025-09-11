package com.spiritatlas.feature.tantric

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.spiritatlas.core.ui.components.*
import com.spiritatlas.core.ui.theme.*
import com.spiritatlas.domain.tantric.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TantricContentScreen(
    modifier: Modifier = Modifier,
    viewModel: TantricContentViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { TantricContentType.entries.size })
    
    val selectedType = TantricContentType.entries[pagerState.currentPage]
    
    LaunchedEffect(selectedType) {
        viewModel.loadContentForType(selectedType)
    }
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top App Bar with Spiritual Gradient
        TopAppBar(
            title = {
                Text(
                    "Sacred Tantric Wisdom",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = SpiritualPurple,
                titleContentColor = Color.White
            ),
            actions = {
                IconButton(
                    onClick = { viewModel.refreshContent() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh Content",
                        tint = Color.White
                    )
                }
            }
        )
        
        // Content Type Tabs
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            indicator = { tabPositions ->
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.wrapContentSize(),
                    color = SpiritualPurple,
                    height = 4.dp,
                    width = 80.dp
                )
            }
        ) {
            TantricContentType.entries.forEachIndexed { index, type ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = type.displayName,
                            fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = when (type) {
                                TantricContentType.TANTRIC_PRACTICES -> Icons.Default.SelfImprovement
                                TantricContentType.KAMA_SUTRA -> Icons.Default.Favorite
                                TantricContentType.ROBERT_GREENE -> Icons.Default.Psychology
                                TantricContentType.COMPATIBILITY -> Icons.Default.People
                            },
                            contentDescription = null,
                            tint = if (pagerState.currentPage == index) SpiritualPurple else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }
        
        // Content Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            val contentType = TantricContentType.entries[pageIndex]
            
            when (uiState) {
                is TantricContentUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CircularProgressIndicator(
                                color = SpiritualPurple,
                                strokeWidth = 4.dp
                            )
                            Text(
                                "Loading sacred wisdom...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                
                is TantricContentUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        ModernCard(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(48.dp)
                                )
                                Text(
                                    "Unable to load content",
                                    style = MaterialTheme.typography.titleMedium,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    (uiState as TantricContentUiState.Error).message,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                                ModernButton(
                                    text = "Retry",
                                    onClick = { viewModel.loadContentForType(contentType) },
                                    variant = ButtonVariant.Primary,
                                    icon = Icons.Default.Refresh
                                )
                            }
                        }
                    }
                }
                
                is TantricContentUiState.Success -> {
                    TantricContentGrid(
                        contentType = contentType,
                        content = (uiState as TantricContentUiState.Success).content.filter { it.contentType.toUiType() == contentType },
                        onContentClick = { content ->
                            viewModel.selectContent(content)
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
    
    // Selected Content Detail Dialog
    uiState.let { state ->
        if (state is TantricContentUiState.Success && state.selectedContent != null) {
            TantricContentDetailDialog(
                content = state.selectedContent,
                onDismiss = { viewModel.clearSelectedContent() },
                onFavorite = { viewModel.toggleFavorite(state.selectedContent) }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TantricContentGrid(
    contentType: TantricContentType,
    content: List<TantricContent>,
    onContentClick: (TantricContent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalItemSpacing = 12.dp
    ) {
        items(content, key = { it.id }) { tantricContent ->
            TantricContentCard(
                content = tantricContent,
                onClick = { onContentClick(tantricContent) },
                modifier = Modifier.animateItemPlacement()
            )
        }
    }
}

@Composable
private fun TantricContentCard(
    content: TantricContent,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModernCard(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column {
            // Visual Content
            if (content.visualContent.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(content.visualContent.first().url)
                                .crossfade(true)
                                .build()
                        ),
                        contentDescription = content.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    
                    // Type indicator chip
                    TantricTypeChip(
                        tantricType = content.contentType.toUiType().displayName,
                        isSelected = true,
                        onClick = { },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    )
                }
            }
            
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = content.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Text(
                    text = content.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3
                )
                
                // Tags
                if (content.tags.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        content.tags.take(2).forEach { tag ->
                            AssistChip(
                                onClick = { },
                                label = {
                                    Text(
                                        text = tag,
                                        fontSize = 10.sp
                                    )
                                },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = SpiritualColors.TantricColors[tag]?.copy(alpha = 0.1f) 
                                        ?: MaterialTheme.colorScheme.secondaryContainer
                                )
                            )
                        }
                    }
                }
                
                // Rating
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        repeat(5) { index ->
                            Icon(
                                imageVector = if (index < content.rating) Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = null,
                                tint = AuraGold,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    
                    Text(
                        text = "${content.practiceIntensity}/10",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun TantricContentDetailDialog(
    content: TantricContent,
    onDismiss: () -> Unit,
    onFavorite: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = content.title,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = content.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                if (content.benefits.isNotEmpty()) {
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Benefits:",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                            content.benefits.forEach { benefit ->
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text("•", color = SpiritualPurple)
                                    Text(
                                        text = benefit,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
                
                if (content.instructions.isNotEmpty()) {
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Instructions:",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                            content.instructions.forEachIndexed { index, instruction ->
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = "${index + 1}.",
                                        color = SpiritualPurple,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = instruction,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            ModernButton(
                text = "Got it",
                onClick = onDismiss,
                variant = ButtonVariant.Primary
            )
        },
        dismissButton = {
            ModernButton(
                text = "Favorite",
                onClick = onFavorite,
                variant = ButtonVariant.Outline,
                icon = Icons.Default.FavoriteBorder
            )
        }
    )
}

enum class TantricContentType(val displayName: String) {
    TANTRIC_PRACTICES("Sacred Practices"),
    KAMA_SUTRA("Kama Sutra"),
    ROBERT_GREENE("Psychology"),
    COMPATIBILITY("Compatibility")
}

// Extension function to convert domain enum to UI enum
fun com.spiritatlas.domain.tantric.TantricContentType.toUiType(): TantricContentType {
    return when (this) {
        com.spiritatlas.domain.tantric.TantricContentType.TANTRIC_PRACTICES -> TantricContentType.TANTRIC_PRACTICES
        com.spiritatlas.domain.tantric.TantricContentType.KAMA_SUTRA -> TantricContentType.KAMA_SUTRA
        com.spiritatlas.domain.tantric.TantricContentType.ROBERT_GREENE -> TantricContentType.ROBERT_GREENE
        com.spiritatlas.domain.tantric.TantricContentType.COMPATIBILITY -> TantricContentType.COMPATIBILITY
    }
}
