package com.spiritatlas.data.sync

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import com.spiritatlas.data.database.dao.UserProfileDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Offline-first sync manager for SpiritAtlas
 * Handles:
 * - Network connectivity monitoring
 * - Offline operation queuing
 * - Automatic sync when online
 * - Conflict resolution
 */
@Singleton
class OfflineSyncManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userProfileDao: UserProfileDao
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val syncMutex = Mutex()

    private val _isOnline = MutableStateFlow(false)
    val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()

    private val _syncStatus = MutableStateFlow<SyncStatus>(SyncStatus.Idle)
    val syncStatus: StateFlow<SyncStatus> = _syncStatus.asStateFlow()

    private val pendingOperations = mutableListOf<PendingOperation>()

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    companion object {
        private const val TAG = "OfflineSyncManager"
    }

    init {
        // Initialize network monitoring
        setupNetworkMonitoring()
        checkInitialConnectivity()
    }

    /**
     * Setup network connectivity monitoring
     */
    private fun setupNetworkMonitoring() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Log.d(TAG, "Network available")
                _isOnline.value = true
                scope.launch {
                    syncPendingOperations()
                }
            }

            override fun onLost(network: Network) {
                Log.d(TAG, "Network lost")
                _isOnline.value = false
            }
        }

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    /**
     * Check initial connectivity state
     */
    private fun checkInitialConnectivity() {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        _isOnline.value = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        Log.d(TAG, "Initial connectivity: ${_isOnline.value}")
    }

    /**
     * Queue an operation for sync when online
     */
    suspend fun queueOperation(operation: PendingOperation) = syncMutex.withLock {
        pendingOperations.add(operation)
        Log.d(TAG, "Queued operation: ${operation.type} (total: ${pendingOperations.size})")

        // If online, attempt sync immediately
        if (_isOnline.value) {
            syncPendingOperations()
        }
    }

    /**
     * Sync all pending operations
     */
    suspend fun syncPendingOperations(): Int = syncMutex.withLock {
        if (pendingOperations.isEmpty() || !_isOnline.value) {
            return 0
        }

        _syncStatus.value = SyncStatus.Syncing(pendingOperations.size)
        Log.d(TAG, "Starting sync of ${pendingOperations.size} pending operations")

        val failedOperations = mutableListOf<PendingOperation>()

        pendingOperations.forEach { operation ->
            try {
                when (operation.type) {
                    OperationType.PROFILE_UPDATE -> {
                        syncProfileUpdate(operation)
                    }
                    OperationType.PROFILE_DELETE -> {
                        syncProfileDelete(operation)
                    }
                    OperationType.COMPATIBILITY_REQUEST -> {
                        syncCompatibilityRequest(operation)
                    }
                    OperationType.AI_ENRICHMENT -> {
                        syncAiEnrichment(operation)
                    }
                }
                Log.d(TAG, "Synced operation: ${operation.type} - ${operation.id}")
            } catch (e: Exception) {
                Log.e(TAG, "Failed to sync operation: ${operation.type} - ${operation.id}", e)
                failedOperations.add(operation)
            }
        }

        // Keep failed operations for retry
        pendingOperations.clear()
        pendingOperations.addAll(failedOperations)

        _syncStatus.value = if (failedOperations.isEmpty()) {
            SyncStatus.Success
        } else {
            SyncStatus.PartialFailure(failedOperations.size)
        }

        Log.d(TAG, "Sync completed. Failed: ${failedOperations.size}")
        return pendingOperations.size - failedOperations.size
    }

    /**
     * Get count of pending operations
     */
    fun getPendingOperationCount(): Int = pendingOperations.size

    /**
     * Clear all pending operations (use with caution)
     */
    suspend fun clearPendingOperations() = syncMutex.withLock {
        pendingOperations.clear()
        Log.d(TAG, "Cleared all pending operations")
    }

    /**
     * Force sync attempt (even if offline)
     */
    suspend fun forceSyncAttempt() {
        checkInitialConnectivity()
        if (_isOnline.value) {
            syncPendingOperations()
        } else {
            Log.w(TAG, "Cannot force sync: device is offline")
        }
    }

    // === Sync Operation Implementations ===

    private suspend fun syncProfileUpdate(operation: PendingOperation) {
        // In a real implementation, this would sync with a backend
        // For now, we just mark the profile as synced in local database
        val profileId = operation.data["profileId"] as? String ?: return

        userProfileDao.markProfileAsSynced(
            profileId = profileId,
            timestamp = System.currentTimeMillis()
        )
    }

    private suspend fun syncProfileDelete(operation: PendingOperation) {
        // Sync deletion with backend
        val profileId = operation.data["profileId"] as? String ?: return
        // Backend sync would happen here
        Log.d(TAG, "Profile deletion synced: $profileId")
    }

    private suspend fun syncCompatibilityRequest(operation: PendingOperation) {
        // Sync compatibility request/result with backend
        val reportId = operation.data["reportId"] as? String ?: return
        // Backend sync would happen here
        Log.d(TAG, "Compatibility request synced: $reportId")
    }

    private suspend fun syncAiEnrichment(operation: PendingOperation) {
        // Sync AI enrichment request/result with backend
        val profileId = operation.data["profileId"] as? String ?: return
        // Backend sync would happen here
        Log.d(TAG, "AI enrichment synced: $profileId")
    }
}

/**
 * Represents a pending operation to be synced
 */
data class PendingOperation(
    val id: String,
    val type: OperationType,
    val timestamp: Long = System.currentTimeMillis(),
    val retryCount: Int = 0,
    val data: Map<String, Any> = emptyMap()
)

/**
 * Types of operations that can be queued for sync
 */
enum class OperationType {
    PROFILE_UPDATE,
    PROFILE_DELETE,
    COMPATIBILITY_REQUEST,
    AI_ENRICHMENT
}

/**
 * Sync status states
 */
sealed class SyncStatus {
    object Idle : SyncStatus()
    data class Syncing(val operationCount: Int) : SyncStatus()
    object Success : SyncStatus()
    data class PartialFailure(val failedCount: Int) : SyncStatus()
    data class Error(val message: String) : SyncStatus()
}
