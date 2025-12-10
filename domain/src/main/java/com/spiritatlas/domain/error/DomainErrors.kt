package com.spiritatlas.domain.error

import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Domain-level error definitions for SpiritAtlas
 *
 * These errors bridge the gap between technical exceptions and user-friendly messaging.
 * Each error type includes spiritual-themed messages that maintain the app's mystical tone.
 */

// ═══════════════════════════════════════════════════════════════════════════════
// SEALED ERROR HIERARCHY
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Base sealed class for all domain errors
 */
sealed class SpiritAtlasError(
    override val message: String,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    /**
     * Get a user-friendly spiritual message for this error
     */
    abstract fun toUserMessage(): String

    /**
     * Get a short title for this error
     */
    abstract fun toUserTitle(): String

    /**
     * Whether this error is recoverable (user can retry)
     */
    abstract val isRecoverable: Boolean

    /**
     * Suggested action for recovery
     */
    open val suggestedAction: String? = null
}

// ═══════════════════════════════════════════════════════════════════════════════
// NETWORK ERRORS
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Network connectivity errors
 */
sealed class NetworkError(
    message: String,
    cause: Throwable? = null
) : SpiritAtlasError(message, cause) {

    override val isRecoverable: Boolean = true

    data class NoConnection(
        override val cause: Throwable? = null
    ) : NetworkError("No internet connection", cause) {
        override fun toUserTitle() = "Cosmic Connection Lost"
        override fun toUserMessage() =
            "The cosmic signals are disrupted. Please check your connection and try reconnecting to the universe."
        override val suggestedAction = "Check your internet connection"
    }

    data class Timeout(
        override val cause: Throwable? = null
    ) : NetworkError("Request timed out", cause) {
        override fun toUserTitle() = "Stars Are Taking Their Time"
        override fun toUserMessage() =
            "The celestial response is delayed. The universe needs a moment to align the energies."
        override val suggestedAction = "Please try again"
    }

    data class ServerUnreachable(
        override val cause: Throwable? = null
    ) : NetworkError("Server unreachable", cause) {
        override fun toUserTitle() = "Sacred Servers Meditating"
        override fun toUserMessage() =
            "Our spiritual servers are currently in meditation. They'll return to service soon."
        override val suggestedAction = "Wait a moment and try again"
    }

    data class SslError(
        override val cause: Throwable? = null
    ) : NetworkError("SSL certificate validation failed", cause) {
        override fun toUserTitle() = "Sacred Seal Broken"
        override fun toUserMessage() =
            "The protective seal around this connection isn't valid. This may be a security concern."
        override val suggestedAction = "Check your connection security"
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// SERVER ERRORS
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Server-side errors (5xx responses)
 */
sealed class ServerError(
    message: String,
    cause: Throwable? = null
) : SpiritAtlasError(message, cause) {

    override val isRecoverable: Boolean = true

    data class InternalError(
        val statusCode: Int = 500,
        override val cause: Throwable? = null
    ) : ServerError("Server internal error", cause) {
        override fun toUserTitle() = "Stars Realigning"
        override fun toUserMessage() =
            "The celestial servers are realigning their energies. This is temporary – the cosmos will restore balance soon."
        override val suggestedAction = "Try again in a few moments"
    }

    data class ServiceUnavailable(
        override val cause: Throwable? = null
    ) : ServerError("Service temporarily unavailable", cause) {
        override fun toUserTitle() = "Sacred Service Resting"
        override fun toUserMessage() =
            "Our spiritual services are taking a brief rest to recharge their energies."
        override val suggestedAction = "Check back shortly"
    }

    data class Maintenance(
        override val cause: Throwable? = null
    ) : ServerError("Server under maintenance", cause) {
        override fun toUserTitle() = "Cosmic Maintenance"
        override fun toUserMessage() =
            "The universe is performing scheduled maintenance to enhance your spiritual journey."
        override val suggestedAction = "We'll be back soon"
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// CLIENT ERRORS
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Client-side errors (4xx responses)
 */
sealed class ClientError(
    message: String,
    cause: Throwable? = null
) : SpiritAtlasError(message, cause) {

    override val isRecoverable: Boolean = false

    data class Unauthorized(
        override val cause: Throwable? = null
    ) : ClientError("Unauthorized access", cause) {
        override fun toUserTitle() = "Sacred Access Required"
        override fun toUserMessage() =
            "This sacred knowledge requires authentication. Please sign in to access these spiritual insights."
        override val suggestedAction = "Sign in to continue"
    }

    data class Forbidden(
        override val cause: Throwable? = null
    ) : ClientError("Access forbidden", cause) {
        override fun toUserTitle() = "Path Forbidden"
        override fun toUserMessage() =
            "This spiritual path is protected. You don't have permission to access this sacred space."
        override val suggestedAction = "Request access or check your permissions"
    }

    data class NotFound(
        val resource: String? = null,
        override val cause: Throwable? = null
    ) : ClientError("Resource not found", cause) {
        override fun toUserTitle() = "Path Not Found"
        override fun toUserMessage() =
            if (resource != null) {
                "The $resource you seek doesn't exist in our spiritual realm."
            } else {
                "This spiritual path doesn't exist in our realm."
            }
        override val suggestedAction = "Check the path and try again"
    }

    data class BadRequest(
        val details: String? = null,
        override val cause: Throwable? = null
    ) : ClientError("Invalid request", cause) {
        override fun toUserTitle() = "Invalid Request"
        override fun toUserMessage() =
            details ?: "The cosmic energies couldn't understand this request. Please check your input."
        override val suggestedAction = "Review your information"
    }

    data class TooManyRequests(
        val retryAfter: Long? = null,
        override val cause: Throwable? = null
    ) : ClientError("Rate limit exceeded", cause) {
        override val isRecoverable: Boolean = true
        override fun toUserTitle() = "Cosmic Flow Overload"
        override fun toUserMessage() =
            "You're channeling energy too quickly. Take a mindful pause and try again in a moment."
        override val suggestedAction =
            if (retryAfter != null) "Wait ${retryAfter}s and try again"
            else "Wait a moment and try again"
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// DATA ERRORS
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Data validation and processing errors
 */
sealed class DataError(
    message: String,
    cause: Throwable? = null
) : SpiritAtlasError(message, cause) {

    override val isRecoverable: Boolean = false

    data class ValidationError(
        val field: String,
        val reason: String,
        override val cause: Throwable? = null
    ) : DataError("Validation failed: $field - $reason", cause) {
        override fun toUserTitle() = "Please Check Your Input"
        override fun toUserMessage() =
            "The $field field needs your attention: $reason"
        override val suggestedAction = "Correct the highlighted fields"
    }

    data class ParsingError(
        val details: String? = null,
        override val cause: Throwable? = null
    ) : DataError("Failed to parse data", cause) {
        override fun toUserTitle() = "Cosmic Message Unclear"
        override fun toUserMessage() =
            "The cosmic data arrived in an unexpected format. This is usually temporary."
        override val suggestedAction = "Try refreshing the page"
    }

    data class NotFound(
        val item: String,
        override val cause: Throwable? = null
    ) : DataError("$item not found", cause) {
        override fun toUserTitle() = "Not Found"
        override fun toUserMessage() =
            "The $item you're looking for doesn't exist in your spiritual library."
        override val suggestedAction = "Create one to get started"
    }

    data object Empty : DataError("No data available") {
        override fun toUserTitle() = "Your Journey Awaits"
        override fun toUserMessage() =
            "Your spiritual library is empty. Start your journey by creating your first profile."
        override val suggestedAction = "Create your first profile"
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// AI ERRORS
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * AI provider-specific errors
 */
sealed class AiError(
    message: String,
    cause: Throwable? = null
) : SpiritAtlasError(message, cause) {

    override val isRecoverable: Boolean = true

    data class ProviderUnavailable(
        val provider: String,
        override val cause: Throwable? = null
    ) : AiError("AI provider unavailable", cause) {
        override fun toUserTitle() = "AI Oracle Unavailable"
        override fun toUserMessage() =
            "The $provider oracle is currently unavailable. We'll try another wisdom source."
        override val suggestedAction = "Automatic fallback in progress"
    }

    data class QuotaExceeded(
        override val cause: Throwable? = null
    ) : AiError("AI quota exceeded", cause) {
        override fun toUserTitle() = "Wisdom Quota Reached"
        override fun toUserMessage() =
            "You've reached your spiritual wisdom quota for today. The oracle will reset tomorrow."
        override val suggestedAction = "Try again tomorrow or upgrade"
    }

    data class InvalidResponse(
        override val cause: Throwable? = null
    ) : AiError("Invalid AI response", cause) {
        override fun toUserTitle() = "Oracle Speaks Unclearly"
        override fun toUserMessage() =
            "The AI oracle's message was unclear. Let's ask again for better clarity."
        override val suggestedAction = "Try generating again"
    }

    data class Timeout(
        override val cause: Throwable? = null
    ) : AiError("AI request timed out", cause) {
        override fun toUserTitle() = "Oracle Deep in Thought"
        override fun toUserMessage() =
            "The AI oracle is taking longer than expected to channel the wisdom. Let's try again."
        override val suggestedAction = "Try again with simpler request"
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// PERMISSION ERRORS
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Permission and consent errors
 */
sealed class PermissionError(
    message: String,
    cause: Throwable? = null
) : SpiritAtlasError(message, cause) {

    override val isRecoverable: Boolean = true

    data class ConsentRequired(
        val consentType: String,
        override val cause: Throwable? = null
    ) : PermissionError("Consent required", cause) {
        override fun toUserTitle() = "Your Permission Needed"
        override fun toUserMessage() =
            "To access this spiritual feature, we need your permission for $consentType."
        override val suggestedAction = "Grant permission in Settings"
    }

    data class StoragePermissionDenied(
        override val cause: Throwable? = null
    ) : PermissionError("Storage permission denied", cause) {
        override fun toUserTitle() = "Storage Access Needed"
        override fun toUserMessage() =
            "To save your spiritual journey, we need access to your device storage."
        override val suggestedAction = "Enable storage permission"
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// UNKNOWN ERRORS
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Catch-all for unexpected errors
 */
data class UnknownError(
    override val message: String = "An unexpected error occurred",
    override val cause: Throwable? = null
) : SpiritAtlasError(message, cause) {
    override val isRecoverable: Boolean = true

    override fun toUserTitle() = "Unexpected Energy"
    override fun toUserMessage() =
        "An unexpected cosmic disturbance occurred. The universe will realign itself soon."
    override val suggestedAction = "Try again or contact support"
}

// ═══════════════════════════════════════════════════════════════════════════════
// ERROR MAPPING UTILITIES
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * Maps technical exceptions to domain errors with spiritual messaging
 */
object ErrorMapper {

    /**
     * Convert a Throwable to a SpiritAtlasError
     */
    fun mapThrowable(throwable: Throwable): SpiritAtlasError {
        return when (throwable) {
            // Network errors
            is UnknownHostException -> NetworkError.NoConnection(throwable)
            is SocketTimeoutException -> NetworkError.Timeout(throwable)
            is IOException -> {
                if (throwable.message?.contains("SSL", ignoreCase = true) == true) {
                    NetworkError.SslError(throwable)
                } else {
                    NetworkError.ServerUnreachable(throwable)
                }
            }

            // Already a SpiritAtlasError
            is SpiritAtlasError -> throwable

            // Unknown
            else -> UnknownError(
                message = throwable.message ?: "An unexpected error occurred",
                cause = throwable
            )
        }
    }

    /**
     * Map HTTP status codes to domain errors
     */
    fun mapHttpError(statusCode: Int, message: String? = null, cause: Throwable? = null): SpiritAtlasError {
        return when (statusCode) {
            400 -> ClientError.BadRequest(message, cause)
            401 -> ClientError.Unauthorized(cause)
            403 -> ClientError.Forbidden(cause)
            404 -> ClientError.NotFound(message, cause)
            429 -> ClientError.TooManyRequests(cause = cause)
            500 -> ServerError.InternalError(statusCode, cause)
            503 -> ServerError.ServiceUnavailable(cause)
            in 500..599 -> ServerError.InternalError(statusCode, cause)
            else -> UnknownError(message ?: "HTTP error $statusCode", cause)
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// ERROR STATE FOR UI
// ═══════════════════════════════════════════════════════════════════════════════

/**
 * UI error state with retry capability
 */
data class ErrorState(
    val error: SpiritAtlasError,
    val retryCount: Int = 0,
    val canRetry: Boolean = error.isRecoverable,
    val timestamp: Long = System.currentTimeMillis()
) {
    val title: String = error.toUserTitle()
    val message: String = error.toUserMessage()
    val suggestedAction: String? = error.suggestedAction
}
