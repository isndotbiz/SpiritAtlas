package com.spiritatlas.data.error

import android.util.Log
import com.spiritatlas.domain.error.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.math.min
import kotlin.math.pow

/**
 * Centralized error handling with retry logic and exponential backoff
 *
 * Features:
 * - Automatic retry with exponential backoff
 * - Circuit breaker pattern to prevent cascading failures
 * - Error mapping to domain errors
 * - Retry decision based on error type
 * - Logging and analytics integration points
 */
class ErrorHandler {

    companion object {
        private const val TAG = "ErrorHandler"

        // Retry configuration
        private const val MAX_RETRY_ATTEMPTS = 3
        private const val INITIAL_BACKOFF_MS = 1000L // 1 second
        private const val MAX_BACKOFF_MS = 30000L // 30 seconds
        private const val BACKOFF_MULTIPLIER = 2.0

        // Circuit breaker configuration
        private const val CIRCUIT_BREAKER_THRESHOLD = 5
        private const val CIRCUIT_BREAKER_TIMEOUT_MS = 60000L // 1 minute
    }

    // Circuit breaker state
    private var failureCount = 0
    private var circuitBreakerOpenTime = 0L
    private var isCircuitBreakerOpen = false

    /**
     * Execute an operation with automatic retry on failure
     *
     * @param maxRetries Maximum number of retry attempts (default: 3)
     * @param initialDelay Initial delay before first retry in milliseconds (default: 1000ms)
     * @param maxDelay Maximum delay between retries in milliseconds (default: 30000ms)
     * @param shouldRetry Custom predicate to determine if error should be retried
     * @param onError Callback for each error occurrence (useful for logging/analytics)
     * @param operation The operation to execute
     * @return Result of the operation
     */
    suspend fun <T> executeWithRetry(
        maxRetries: Int = MAX_RETRY_ATTEMPTS,
        initialDelay: Long = INITIAL_BACKOFF_MS,
        maxDelay: Long = MAX_BACKOFF_MS,
        shouldRetry: (SpiritAtlasError, Int) -> Boolean = ::defaultShouldRetry,
        onError: ((SpiritAtlasError, Int) -> Unit)? = null,
        operation: suspend () -> T
    ): Result<T> {
        // Check circuit breaker
        if (isCircuitBreakerOpen) {
            if (System.currentTimeMillis() - circuitBreakerOpenTime > CIRCUIT_BREAKER_TIMEOUT_MS) {
                // Reset circuit breaker after timeout
                resetCircuitBreaker()
            } else {
                return Result.failure(
                    ServerError.ServiceUnavailable(
                        RuntimeException("Circuit breaker open - service temporarily unavailable")
                    )
                )
            }
        }

        var currentAttempt = 0
        var lastError: SpiritAtlasError? = null

        while (currentAttempt <= maxRetries) {
            try {
                val result = operation()
                // Success - reset failure count
                if (failureCount > 0) {
                    failureCount = 0
                }
                return Result.success(result)
            } catch (e: Exception) {
                val error = mapToSpiritAtlasError(e)
                lastError = error
                currentAttempt++

                // Log error
                Log.w(TAG, "Operation failed (attempt $currentAttempt/${maxRetries + 1}): ${error.message}", e)

                // Notify error callback
                onError?.invoke(error, currentAttempt)

                // Check if we should retry
                if (currentAttempt <= maxRetries && shouldRetry(error, currentAttempt)) {
                    // Calculate backoff delay with exponential increase
                    val backoffDelay = calculateBackoff(
                        attempt = currentAttempt,
                        initialDelay = initialDelay,
                        maxDelay = maxDelay
                    )

                    Log.d(TAG, "Retrying in ${backoffDelay}ms...")
                    delay(backoffDelay)
                } else {
                    break
                }
            }
        }

        // All retries failed
        lastError?.let {
            recordFailure()
        }

        return Result.failure(lastError ?: UnknownError("Operation failed after $maxRetries retries"))
    }

    /**
     * Execute operation and emit retry attempts as Flow
     * Useful for showing retry progress in UI
     */
    fun <T> executeWithRetryFlow(
        maxRetries: Int = MAX_RETRY_ATTEMPTS,
        initialDelay: Long = INITIAL_BACKOFF_MS,
        maxDelay: Long = MAX_BACKOFF_MS,
        operation: suspend () -> T
    ): Flow<RetryState<T>> = flow {
        emit(RetryState.Loading(attempt = 0))

        var currentAttempt = 0
        var lastError: SpiritAtlasError? = null

        while (currentAttempt <= maxRetries) {
            try {
                val result = operation()
                emit(RetryState.Success(result))
                return@flow
            } catch (e: Exception) {
                val error = mapToSpiritAtlasError(e)
                lastError = error
                currentAttempt++

                if (currentAttempt <= maxRetries && error.isRecoverable) {
                    val backoffDelay = calculateBackoff(currentAttempt, initialDelay, maxDelay)
                    emit(RetryState.Retrying(
                        error = error,
                        attempt = currentAttempt,
                        maxAttempts = maxRetries + 1,
                        nextRetryIn = backoffDelay
                    ))
                    delay(backoffDelay)
                } else {
                    break
                }
            }
        }

        emit(RetryState.Error(
            error = lastError ?: UnknownError("Operation failed"),
            attempts = currentAttempt
        ))
    }

    /**
     * Map any Throwable to a SpiritAtlasError
     */
    fun mapToSpiritAtlasError(throwable: Throwable): SpiritAtlasError {
        return when (throwable) {
            is SpiritAtlasError -> throwable
            else -> ErrorMapper.mapThrowable(throwable)
        }
    }

    /**
     * Calculate exponential backoff delay
     */
    private fun calculateBackoff(
        attempt: Int,
        initialDelay: Long,
        maxDelay: Long
    ): Long {
        val exponentialDelay = (initialDelay * BACKOFF_MULTIPLIER.pow(attempt - 1).toLong())
        return min(exponentialDelay, maxDelay)
    }

    /**
     * Default retry predicate - retry on recoverable errors
     */
    private fun defaultShouldRetry(error: SpiritAtlasError, attempt: Int): Boolean {
        return error.isRecoverable && !isCircuitBreakerOpen
    }

    /**
     * Record a failure for circuit breaker
     */
    private fun recordFailure() {
        failureCount++
        if (failureCount >= CIRCUIT_BREAKER_THRESHOLD) {
            openCircuitBreaker()
        }
    }

    /**
     * Open the circuit breaker
     */
    private fun openCircuitBreaker() {
        isCircuitBreakerOpen = true
        circuitBreakerOpenTime = System.currentTimeMillis()
        Log.w(TAG, "Circuit breaker opened after $failureCount consecutive failures")
    }

    /**
     * Reset the circuit breaker
     */
    private fun resetCircuitBreaker() {
        isCircuitBreakerOpen = false
        failureCount = 0
        circuitBreakerOpenTime = 0L
        Log.i(TAG, "Circuit breaker reset")
    }

    /**
     * Check if circuit breaker is currently open
     */
    fun isCircuitBreakerActive(): Boolean = isCircuitBreakerOpen

    /**
     * Manually reset circuit breaker (useful for testing or admin override)
     */
    fun manualResetCircuitBreaker() {
        resetCircuitBreaker()
    }
}

/**
 * Retry state for Flow-based operations
 */
sealed class RetryState<out T> {
    data class Loading(val attempt: Int) : RetryState<Nothing>()

    data class Retrying(
        val error: SpiritAtlasError,
        val attempt: Int,
        val maxAttempts: Int,
        val nextRetryIn: Long
    ) : RetryState<Nothing>()

    data class Success<T>(val data: T) : RetryState<T>()

    data class Error(
        val error: SpiritAtlasError,
        val attempts: Int
    ) : RetryState<Nothing>()
}

/**
 * Extension function to safely execute operations with error handling
 */
suspend fun <T> safeExecute(
    errorHandler: ErrorHandler = ErrorHandler(),
    operation: suspend () -> T
): Result<T> {
    return errorHandler.executeWithRetry(operation = operation)
}

/**
 * Extension function for Result to map to ErrorState
 */
fun <T> Result<T>.toErrorState(): ErrorState? {
    return when {
        this.isFailure -> {
            val error = this.exceptionOrNull()?.let {
                if (it is SpiritAtlasError) it else ErrorMapper.mapThrowable(it)
            } ?: UnknownError("Unknown error occurred")
            ErrorState(error)
        }
        else -> null
    }
}
