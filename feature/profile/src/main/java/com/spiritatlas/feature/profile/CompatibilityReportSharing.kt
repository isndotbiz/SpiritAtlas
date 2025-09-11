package com.spiritatlas.feature.profile

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import com.spiritatlas.domain.service.DetailedCouplesReport
import com.spiritatlas.core.ui.theme.SpiritualPurple
import com.spiritatlas.core.ui.theme.CosmicOrange
import com.spiritatlas.core.ui.theme.DeepTeal
import java.io.File
import java.io.FileOutputStream
import java.time.format.DateTimeFormatter

/**
 * Modern compatibility report sharing system
 * Creates beautiful shareable images and text reports similar to Co-Star
 */
class CompatibilityReportSharing(private val context: Context) {
    
    /**
     * Share compatibility report as both image and text
     */
    fun shareCompatibilityReport(
        report: DetailedCouplesReport,
        partner1Name: String,
        partner2Name: String
    ) {
        try {
            // Create beautiful image
            val imageUri = createCompatibilityImage(report, partner1Name, partner2Name)
            
            // Create shareable text
            val shareText = createShareableText(report, partner1Name, partner2Name)
            
            // Share via Android intent
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, imageUri)
                putExtra(Intent.EXTRA_TEXT, shareText)
                putExtra(Intent.EXTRA_SUBJECT, "💕 Compatibility Report: $partner1Name & $partner2Name")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            
            context.startActivity(Intent.createChooser(shareIntent, "Share Compatibility Report"))
            
        } catch (e: Exception) {
            // Fallback to text-only sharing
            shareTextReport(report, partner1Name, partner2Name)
        }
    }
    
    /**
     * Create beautiful compatibility image with modern Co-Star aesthetic
     */
    private fun createCompatibilityImage(
        report: DetailedCouplesReport,
        partner1Name: String,
        partner2Name: String
    ): Uri {
        val width = 1080
        val height = 1350
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        
        // Background gradient
        val gradient = android.graphics.LinearGradient(
            0f, 0f, 0f, height.toFloat(),
            intArrayOf(
                android.graphics.Color.parseColor("#E8E4FF"), // Light purple
                android.graphics.Color.parseColor("#FFF8F0")  // Light peach
            ),
            null,
            android.graphics.Shader.TileMode.CLAMP
        )
        
        val backgroundPaint = Paint().apply {
            shader = gradient
            isAntiAlias = true
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)
        
        // Header
        val headerPaint = Paint().apply {
            color = SpiritualPurple.toArgb()
            textSize = 64f
            typeface = Typeface.DEFAULT_BOLD
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
        
        canvas.drawText("💕 Compatibility Report", width / 2f, 120f, headerPaint)
        
        // Partner names
        val namePaint = Paint().apply {
            color = DeepTeal.toArgb()
            textSize = 48f
            typeface = Typeface.DEFAULT_BOLD
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
        
        canvas.drawText("$partner1Name & $partner2Name", width / 2f, 200f, namePaint)
        
        // Overall score circle
        val scorePaint = Paint().apply {
            color = CosmicOrange.toArgb()
            textSize = 80f
            typeface = Typeface.DEFAULT_BOLD
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
        
        val circlePaint = Paint().apply {
            color = CosmicOrange.toArgb()
            style = Paint.Style.STROKE
            strokeWidth = 8f
            isAntiAlias = true
        }
        
        // Draw score circle
        canvas.drawCircle(width / 2f, 350f, 100f, circlePaint)
        canvas.drawText("${report.overallCompatibility.toInt()}%", width / 2f, 370f, scorePaint)
        
        // Connection description
        val descPaint = Paint().apply {
            color = SpiritualPurple.toArgb()
            textSize = 32f
            typeface = Typeface.DEFAULT
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
        
        val description = getCompatibilityDescription(report.overallCompatibility)
        canvas.drawText(description, width / 2f, 500f, descPaint)
        
        // Breakdown scores
        val labelPaint = Paint().apply {
            color = DeepTeal.toArgb()
            textSize = 28f
            typeface = Typeface.DEFAULT
            isAntiAlias = true
        }
        
        val scoreTitlePaint = Paint().apply {
            color = CosmicOrange.toArgb()
            textSize = 32f
            typeface = Typeface.DEFAULT_BOLD
            isAntiAlias = true
        }
        
        val scores = listOf(
            "Spiritual Connection" to report.compatibilityBreakdown.spiritual,
            "Emotional Harmony" to report.compatibilityBreakdown.emotional,
            "Physical Chemistry" to report.compatibilityBreakdown.physical
        )
        
        scores.forEachIndexed { index, (label, score) ->
            val y = 600f + (index * 80f)
            canvas.drawText(label, 100f, y, labelPaint)
            canvas.drawText("${score.toInt()}%", width - 150f, y, scoreTitlePaint)
        }
        
        // Key strength
        if (report.strengthsAnalysis.isNotEmpty()) {
            val strengthPaint = Paint().apply {
                color = SpiritualPurple.toArgb()
                textSize = 24f
                typeface = Typeface.DEFAULT_BOLD
                isAntiAlias = true
            }
            
            canvas.drawText("✨ Key Strength:", 60f, 900f, strengthPaint)
            
            val strengthTextPaint = Paint().apply {
                color = DeepTeal.toArgb()
                textSize = 22f
                typeface = Typeface.DEFAULT
                isAntiAlias = true
            }
            
            val strengthText = report.strengthsAnalysis.first().title
            canvas.drawText(strengthText, 60f, 940f, strengthTextPaint)
        }
        
        // Footer
        val footerPaint = Paint().apply {
            color = android.graphics.Color.GRAY
            textSize = 20f
            typeface = Typeface.DEFAULT
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
        
        val dateText = "Generated on ${java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))}"
        canvas.drawText(dateText, width / 2f, height - 100f, footerPaint)
        canvas.drawText("SpiritAtlas • Spiritual Compatibility Analysis", width / 2f, height - 60f, footerPaint)
        
        // Save to file
        val imagesDir = File(context.cacheDir, "shared_images")
        imagesDir.mkdirs()
        val imageFile = File(imagesDir, "compatibility_${System.currentTimeMillis()}.png")
        
        FileOutputStream(imageFile).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            imageFile
        )
    }
    
    /**
     * Create shareable text version of the report
     */
    private fun createShareableText(
        report: DetailedCouplesReport,
        partner1Name: String,
        partner2Name: String
    ): String {
        return buildString {
            appendLine("💕 COMPATIBILITY REPORT")
            appendLine("$partner1Name & $partner2Name")
            appendLine()
            appendLine("Overall Compatibility: ${report.overallCompatibility.toInt()}%")
            appendLine(getCompatibilityDescription(report.overallCompatibility))
            appendLine()
            appendLine("✨ BREAKDOWN:")
            appendLine("• Spiritual Connection: ${report.compatibilityBreakdown.spiritual.toInt()}%")
            appendLine("• Emotional Harmony: ${report.compatibilityBreakdown.emotional.toInt()}%")
            appendLine("• Physical Chemistry: ${report.compatibilityBreakdown.physical.toInt()}%")
            appendLine()
            
            if (report.strengthsAnalysis.isNotEmpty()) {
                appendLine("🌟 KEY STRENGTH:")
                appendLine(report.strengthsAnalysis.first().title)
                appendLine()
            }
            
            if (report.challengesAnalysis.isNotEmpty()) {
                appendLine("🌱 GROWTH AREA:")
                appendLine(report.challengesAnalysis.first().title)
                appendLine()
            }
            
            appendLine("Generated by SpiritAtlas")
            appendLine("Spiritual Compatibility Analysis")
        }
    }
    
    /**
     * Fallback text-only sharing
     */
    private fun shareTextReport(
        report: DetailedCouplesReport,
        partner1Name: String,
        partner2Name: String
    ) {
        val shareText = createShareableText(report, partner1Name, partner2Name)
        
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
            putExtra(Intent.EXTRA_SUBJECT, "💕 Compatibility Report: $partner1Name & $partner2Name")
        }
        
        context.startActivity(Intent.createChooser(shareIntent, "Share Compatibility Report"))
    }
    
    private fun getCompatibilityDescription(score: Double): String {
        return when {
            score >= 85 -> "Cosmic soulmate connection ✨"
            score >= 70 -> "Beautiful spiritual alignment 💫"
            score >= 55 -> "Meaningful connection 🌙"
            score >= 40 -> "Interesting dynamic ⚡"
            else -> "Complex relationship 🌊"
        }
    }
}

/**
 * Composable helper to easily access sharing functionality
 */
@Composable
fun rememberCompatibilityReportSharing(): CompatibilityReportSharing {
    val context = LocalContext.current
    return remember(context) {
        CompatibilityReportSharing(context)
    }
}
