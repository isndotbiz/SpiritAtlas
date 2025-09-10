package com.spiritatlas.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.spiritatlas.domain.ai.AiTextProvider
import com.spiritatlas.domain.repository.ConsentRepository
import com.spiritatlas.domain.repository.ConsentType
import com.spiritatlas.domain.model.ConsentStatus
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

/**
 * Background worker that syncs user data and generates AI insights
 * when appropriate consent is given.
 */
@HiltWorker
class DataSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val aiProvider: AiTextProvider,
    private val consentRepository: ConsentRepository
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): ListenableWorker.Result {
        return try {
            Log.d("DataSyncWorker", "Starting data sync work")
            
            // Check if user has consented to AI processing
            val aiConsent = consentRepository.getConsentStatus(ConsentType.AI_ENRICHMENT).first()
            if (aiConsent != ConsentStatus.GRANTED) {
                Log.d("DataSyncWorker", "AI processing consent not granted, skipping AI sync")
                return ListenableWorker.Result.success()
            }
            
            // Check if AI provider is available
            if (!aiProvider.isAvailable()) {
                Log.w("DataSyncWorker", "AI provider not available, will retry later")
                return ListenableWorker.Result.retry()
            }
            
            // Perform background sync operations
            performDataSync()
            
            Log.d("DataSyncWorker", "Data sync completed successfully")
            ListenableWorker.Result.success()
            
        } catch (e: Exception) {
            Log.e("DataSyncWorker", "Data sync failed", e)
            ListenableWorker.Result.failure()
        }
    }
    
    private suspend fun performDataSync() {
        // Example sync operations:
        // 1. Sync local data with remote if needed
        // 2. Generate AI insights for user data
        // 3. Update cached recommendations
        // 4. Clear expired data
        
        Log.d("DataSyncWorker", "Performing background data synchronization")
        
        // This is where you'd implement actual sync logic
        // For now, we'll just log that the work was performed
        kotlinx.coroutines.delay(1000) // Simulate work
    }
    
    companion object {
        const val WORK_NAME = "data_sync_work"
    }
}
