package com.spiritatlas.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spiritatlas.core.ui.theme.*

/**
 * Simple markdown renderer with spiritual aesthetics
 * Simplified version to fix build issues while maintaining functionality
 */
@Composable
fun SpiritualMarkdown(
    content: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Parse simple markdown content
            val lines = content.split("\n")
            
            for (line in lines) {
                val trimmedLine = line.trim()
                
                when {
                    trimmedLine.startsWith("# ") -> {
                        // H1 Header
                        Text(
                            text = trimmedLine.substring(2),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = SpiritualPurple,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    trimmedLine.startsWith("## ") -> {
                        // H2 Header  
                        Text(
                            text = trimmedLine.substring(3),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = SpiritualPurple,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    trimmedLine.startsWith("### ") -> {
                        // H3 Header
                        Text(
                            text = trimmedLine.substring(4),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AuraGold,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    trimmedLine.startsWith("> ") -> {
                        // Quote
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = AuraGold.copy(alpha = 0.1f)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(4.dp)
                                        .height(40.dp)
                                        .background(
                                            AuraGold,
                                            RoundedCornerShape(2.dp)
                                        )
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = trimmedLine.substring(2),
                                    fontSize = 16.sp,
                                    color = SpiritualPurple,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                    trimmedLine.startsWith("- ") || trimmedLine.startsWith("* ") -> {
                        // List item
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "✨",
                                fontSize = 16.sp,
                                color = AuraGold,
                                modifier = Modifier.width(24.dp)
                            )
                            Text(
                                text = trimmedLine.substring(2),
                                fontSize = 16.sp,
                                color = SpiritualPurple,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    trimmedLine == "---" -> {
                        // Divider
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            HorizontalDivider(
                                modifier = Modifier.weight(1f),
                                color = SpiritualPurple.copy(alpha = 0.2f)
                            )
                            Text(
                                text = "✦",
                                fontSize = 16.sp,
                                color = AuraGold,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            HorizontalDivider(
                                modifier = Modifier.weight(1f),
                                color = SpiritualPurple.copy(alpha = 0.2f)
                            )
                        }
                    }
                    trimmedLine.isNotEmpty() -> {
                        // Regular paragraph
                        Text(
                            text = trimmedLine,
                            fontSize = 16.sp,
                            color = SpiritualPurple,
                            lineHeight = 24.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
