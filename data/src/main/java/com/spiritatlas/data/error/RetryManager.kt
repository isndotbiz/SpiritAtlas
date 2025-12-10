package com.spiritatlas.data.error

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages offline queue and automatic retry when connection is restored
 *
 * Features:
 * - Queue failed requests for retry when online
 * - Monitor network connectivity
 * - Automatic retry when connection restored
 * - Persist queue across app restarts (future enhancement)
 * - Priority queue for important operations
 * - Progress tracking for queued items
 */
@Singleton
class RetryManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val errorHandler = ErrorHandler()

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Network state
    private val _isOnline = MutableStateFlow(isNetworkAvailable())
    val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()

    // Queued operations
    private val queuedOperations = ConcurrentHashMap<String, QueuedOperation<*>>()
    private val _queuedCount = MutableStateFlow(0)
    val queuedCount: StateFlow<Int> = _queuedCount.asStateFlow()

    // Sync state
    private val _syncState = MutableStateFlow<SyncState>(SyncState.Idle)
    val syncState: StateFlow<SyncState> = _syncState.asStateFlow()

    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    init {
        registerNetworkCallback()
    }

    /**
     * Queue an operation for retry when online
     *
     * @param id Unique identifier for this operation
     * @param priority Priority level (higher = more important)
     * @param operation The operation to execute
     * @return The queued operation ID
     */
    fun <T> queueOperation(
        id: String = UUID.randomUUID().toString(),
        priority: Int = 0,
        description: String = "Queued operation",
        operation: suspend () -> T
    ): String {
        val queuedOp = QueuedOperation(
            id = id,
            priority = priority,
            description = description,
            operation = operation,
            queuedAt = System.currentTimeMillis()
        )

        queuedOperations[id] = queuedOp
        _queuedCount.value = queuedOperations.size

        Log.d(TAG, "Queued operation: $description (id=$id, priority=$priority)")

        // Try to execute immediately if online
        if (_isOnline.value) {
            scope.launch {
                processQueue()
            }
        }

        return id
    }

    /**
     * Execute operation with automatic queuing on network failure
     */
    suspend fun <T> executeOrQueue(
        id: String = UUID.randomUUID().toString(),
        description: String = "Network operation",
        priority: Int = 0,
        operation: suspend () -> T
    ): Result<T> {
        // Try to execute immediately
        val result = errorHandler.executeWithRetry(
            maxRetries = 1, // Only try once before queuing
            operation = operation
        )

        // If failed due to network error, queue it
        if (result.isFailure) {
            val error = result.exceptionOrNull()
            if (error is com.spiritatlas.domain.error.NetworkError) {
                queueOperation(
                    id = id,
                    priority = priority,
                    description = description,
                    operation = operation
                )
                Log.d(TAG, "Operation failed, queued for retry: $description")
            }
        }

        return result
    }

    /**
     * Remove an operation from the queue
     */
    fun cancelOperation(id: String): Boolean {
        val removed = queuedOperations.remove(id) != null
        if (removed) {
            _queuedCount.value = queuedOperations.size
            Log.d(TAG, "Cancelled operation: $id")
        }
        return removed
    }

    /**
     * Clear all queued operations
     */
    fun clearQueue() {
        queuedOperations.clear()
        _queuedCount.value = 0
        Log.d(TAG, "Queue cleared")
    }

    /**
     * Get all queued operations
     */
    fun getQueuedOperations(): List<QueuedOperationInfo> {
        return queuedOperations.values
            .sortedByDescending { it.priority }
            .map {
                QueuedOperationInfo(
                    id = it.id,
                    description = it.description,
                    priority = it.priority,
                    queuedAt = it.queuedAt
                )
            }
    }

    /**
     * Manually trigger queue processing
     */
    suspend fun processQueue() {
        if (queuedOperations.isEmpty()) {
            return
        }

        if (!_isOnline.value) {
            Log.d(TAG, "Cannot process queue: offline")
            return
        }

        if (_syncState.value is SyncState.Syncing) {
            Log.d(TAG, "Queue processing already in progress")
            return
        }

        _syncState.value = SyncState.Syncing(
            processed = 0,
            total = queuedOperations.size
        )

        Log.d(TAG, "Processing queue: ${queuedOperations.size} operations")

        val operations = queuedOperations.values.sortedByDescending { it.priority }
        var processed = 0
        val failed = mutableListOf<String>()

        for (op in operations) {
            try {
                @Suppress("UNCHECKED_CAST")
                val result = errorHandler.executeWithRetry(
                    maxRetries = 2,
                    operation = op.operation as suspend () -> Any
                )

                if (result.isSuccess) {
                    queuedOperations.remove(op.id)
                    processed++
                    Log.d(TAG, "Successfully processed: ${op.description}")
                } else {
                    failed.add(op.id)
                    Log.w(TAG, "Failed to process: ${op.description}")
                }
            } catch (e: Exception) {
                failed.add(op.id)
                Log.e(TAG, "Error processing ${op.description}", e)
            }

            _syncState.value = SyncState.Syncing(
                processed = processed,
                total = operations.size
            )
        }

        _queuedCount.value = queuedOperations.size

        if (failed.isEmpty()) {
            _syncState.value = SyncState.Success(processed)
            Log.d(TAG, "Queue processing complete: $processed operations")
        } else {
            _syncState.value = SyncState.PartialSuccess(
                processed = processed,
                failed = failed.size
            )
            Log.w(TAG, "Queue processing complete with failures: $processed succeeded, ${failed.size} failed")
        }

        // Reset to idle after a delay
        delay(2000)
        _syncState.value = SyncState.Idle
    }

    /**
     * Check if network is available
     */
    private fun isNetworkAvailable(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    /**
     * Register network callback to monitor connectivity changes
     */
    private fun registerNetworkCallback() {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            .build()

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Log.d(TAG, "Network available")
                _isOnline.value = true

                // Auto-process queue when connection restored
                if (queuedOperations.isNotEmpty()) {
                    scope.launch {
                        delay(1000) // Small delay to ensure connection is stable
                        processQueue()
                    }
                }
            }

            override fun onLost(network: Network) {
                Log.d(TAG, "Network lost")
                _isOnline.value = isNetworkAvailable() // Check if another network is available
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                val hasInternet = networkCapabilities.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_INTERNET
                ) && networkCapabilities.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_VALIDATED
                )
                _isOnline.value = hasInternet
            }
        }

        connectivityManager.registerNetworkCallback(request, networkCallback!!)
    }

    /**
     * Unregister network callback (call in cleanup)
     */
    fun cleanup() {
        networkCallback?.let {
            connectivityManager.unregisterNetworkCallback(it)
        }
        scope.cancel()
    }

    companion object {
        private const val TAG = "RetryManager"
    }
}

/**
 * Internal representation of a queued operation
 */
private data class QueuedOperation<T>(
    val id: String,
    val priority: Int,
    val description: String,
    val operation: suspend () -> T,
    val queuedAt: Long
)

/**
 * Public information about a queued operation
 */
data class QueuedOperationInfo(
    val id: String,
    val description: String,
    val priority: Int,
    val queuedAt: Long
)

/**
 * Sync state for queue processing
 */
sealed class SyncState {
    data object Idle : SyncState()

    data class Syncing(
        val processed: Int,
        val total: Int
    ) : SyncState()

    data class Success(
        val processed: Int
    ) : SyncState()

    data class PartialSuccess(
        val processed: Int,
        val failed: Int
    ) : SyncState()

    data class Failed(
        val error: String
    ) : SyncState()
}
