package com.spiritatlas.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spiritatlas.core.ui.theme.SpiritualPurple

/**
 * Spiritual avatar with optional frame overlay.
 *
 * @param avatarResourceId The drawable resource ID for avatar from app module
 * @param frameResourceId The optional drawable resource ID for frame from app module
 */
@Composable
fun SpiritualAvatar(
    avatarResourceId: Int,
    modifier: Modifier = Modifier,
    frameResourceId: Int? = null,
    size: Dp = 80.dp
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // Avatar image
        Image(
            painter = painterResource(avatarResourceId),
            contentDescription = "Spiritual Avatar",
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
        )

        // Optional frame overlay
        if (frameResourceId != null) {
            Image(
                painter = painterResource(frameResourceId),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

/**
 * Avatar selector for profile creation.
 *
 * @param avatarResourceIds List of avatar drawable resource IDs from app module
 * @param selectedIndex The index of the currently selected avatar
 * @param onAvatarSelected Callback with selected index
 */
@Composable
fun AvatarSelector(
    avatarResourceIds: List<Int>,
    selectedIndex: Int?,
    onAvatarSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(avatarResourceIds.size) { index ->
            val isSelected = selectedIndex == index

            SpiritualAvatar(
                avatarResourceId = avatarResourceIds[index],
                size = 64.dp,
                modifier = Modifier
                    .clickable { onAvatarSelected(index) }
                    .border(
                        width = if (isSelected) 3.dp else 0.dp,
                        color = if (isSelected) SpiritualPurple else Color.Transparent,
                        shape = CircleShape
                    )
            )
        }
    }
}
