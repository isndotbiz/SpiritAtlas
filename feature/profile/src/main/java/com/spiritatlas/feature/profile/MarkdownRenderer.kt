package com.spiritatlas.feature.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

/**
 * Custom lightweight Markdown renderer for spiritual reports
 * Supports headings, bold, italic, lists, images, quotes, and tables
 */
@Composable
fun RichMarkdownText(
    markdown: String,
    modifier: Modifier = Modifier,
    baseStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    val lines = markdown.split('\n')
    
    Column(
        modifier = modifier
    ) {
        var i = 0
        while (i < lines.size) {
            val line = lines[i].trim()
            
            when {
                // Skip empty lines
                line.isEmpty() -> {
                    Spacer(modifier = Modifier.height(8.dp))
                    i++
                }
                
                // H1 Headers
                line.startsWith("# ") -> {
                    Text(
                        text = parseInlineMarkdown(line.removePrefix("# ")),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                    i++
                }
                
                // H2 Headers  
                line.startsWith("## ") -> {
                    Text(
                        text = parseInlineMarkdown(line.removePrefix("## ")),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        ),
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                    i++
                }
                
                // H3 Headers
                line.startsWith("### ") -> {
                    Text(
                        text = parseInlineMarkdown(line.removePrefix("### ")),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    i++
                }
                
                // Images
                line.contains("![") && line.contains("](") -> {
                    val imageResult = parseImageMarkdown(line)
                    if (imageResult != null) {
                        AsyncImage(
                            model = imageResult.url,
                            contentDescription = imageResult.alt,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .padding(vertical = 8.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Fit
                        )
                    }
                    i++
                }
                
                // Quotes (blockquotes)
                line.startsWith("> ") -> {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            text = parseInlineMarkdown(line.removePrefix("> ")),
                            style = baseStyle.copy(
                                fontStyle = FontStyle.Italic,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    i++
                }
                
                // Bullet lists
                line.startsWith("- ") || line.startsWith("* ") -> {
                    Row(
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Text(
                            text = "•",
                            style = baseStyle.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 16.sp
                            ),
                            modifier = Modifier.padding(end = 8.dp, top = 2.dp)
                        )
                        Text(
                            text = parseInlineMarkdown(line.removePrefix("- ").removePrefix("* ")),
                            style = baseStyle,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    i++
                }
                
                // Numbered lists
                Regex("^\\d+\\. ").containsMatchIn(line) -> {
                    val number = line.takeWhile { it.isDigit() }
                    val content = line.dropWhile { it.isDigit() || it == '.' || it == ' ' }
                    
                    Row(
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Text(
                            text = "$number.",
                            style = baseStyle.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = parseInlineMarkdown(content),
                            style = baseStyle,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    i++
                }
                
                // Tables (simple markdown tables)
                line.startsWith("|") && line.endsWith("|") -> {
                    val tableLines = mutableListOf<String>()
                    var j = i
                    while (j < lines.size && lines[j].trim().startsWith("|") && lines[j].trim().endsWith("|")) {
                        tableLines.add(lines[j].trim())
                        j++
                    }
                    
                    if (tableLines.size > 1) {
                        SimpleMarkdownTable(tableLines)
                        i = j
                    } else {
                        // Single line that looks like table but isn't - treat as regular text
                        Text(
                            text = parseInlineMarkdown(line),
                            style = baseStyle,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        i++
                    }
                }
                
                // Horizontal rules
                line == "---" || line == "***" -> {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 16.dp),
                        color = MaterialTheme.colorScheme.outline
                    )
                    i++
                }
                
                // Regular paragraphs
                else -> {
                    Text(
                        text = parseInlineMarkdown(line),
                        style = baseStyle.copy(
                            lineHeight = baseStyle.lineHeight * 1.4
                        ),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    i++
                }
            }
        }
    }
}

@Composable
private fun SimpleMarkdownTable(tableLines: List<String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            tableLines.forEachIndexed { index, line ->
                // Skip separator row (usually index 1)
                if (index == 1 && line.contains("---")) return@forEachIndexed
                
                val cells = line.split("|").map { it.trim() }.filter { it.isNotEmpty() }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    cells.forEach { cell ->
                        Text(
                            text = parseInlineMarkdown(cell),
                            style = if (index == 0) {
                                MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            } else {
                                MaterialTheme.typography.bodyMedium
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp)
                        )
                    }
                }
                
                if (index == 0) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}

private fun parseInlineMarkdown(text: String): androidx.compose.ui.text.AnnotatedString {
    return buildAnnotatedString {
        var currentText = text
        var currentIndex = 0
        
        while (currentIndex < currentText.length) {
            when {
                // Bold **text**
                currentText.substring(currentIndex).startsWith("**") -> {
                    val endIndex = currentText.indexOf("**", currentIndex + 2)
                    if (endIndex != -1) {
                        val boldText = currentText.substring(currentIndex + 2, endIndex)
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(boldText)
                        }
                        currentIndex = endIndex + 2
                    } else {
                        append(currentText[currentIndex])
                        currentIndex++
                    }
                }
                
                // Italic *text*
                currentText.substring(currentIndex).startsWith("*") && !currentText.substring(currentIndex).startsWith("**") -> {
                    val endIndex = currentText.indexOf("*", currentIndex + 1)
                    if (endIndex != -1) {
                        val italicText = currentText.substring(currentIndex + 1, endIndex)
                        withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                            append(italicText)
                        }
                        currentIndex = endIndex + 1
                    } else {
                        append(currentText[currentIndex])
                        currentIndex++
                    }
                }
                
                // Code `text`
                currentText.substring(currentIndex).startsWith("`") -> {
                    val endIndex = currentText.indexOf("`", currentIndex + 1)
                    if (endIndex != -1) {
                        val codeText = currentText.substring(currentIndex + 1, endIndex)
                        withStyle(SpanStyle(
                            background = androidx.compose.ui.graphics.Color.Gray.copy(alpha = 0.2f),
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                        )) {
                            append(codeText)
                        }
                        currentIndex = endIndex + 1
                    } else {
                        append(currentText[currentIndex])
                        currentIndex++
                    }
                }
                
                else -> {
                    append(currentText[currentIndex])
                    currentIndex++
                }
            }
        }
    }
}

private data class ImageMarkdown(val alt: String, val url: String)

private fun parseImageMarkdown(line: String): ImageMarkdown? {
    val imageRegex = """!\[([^\]]*)\]\(([^)]*)\)""".toRegex()
    val match = imageRegex.find(line)
    return match?.let { 
        ImageMarkdown(
            alt = it.groupValues[1],
            url = it.groupValues[2]
        )
    }
}
