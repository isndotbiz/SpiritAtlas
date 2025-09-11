package com.spiritatlas.domain.provider

import com.spiritatlas.domain.paging.CursorPage
import com.spiritatlas.domain.paging.CursorRequest
import com.spiritatlas.domain.paging.CursorTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/**
 * Minimal provider + model check for domain layer.
 * Replace or extend UserProfile with your real 36-field model.
 */

data class UserProfile(
    val id: String,
    val name: String?,
    val birthDateEpochMs: Long?
    // add other fields from your 36-field spec as needed
)

sealed class ModelCheckResult {
    object Valid : ModelCheckResult()
    data class Invalid(val reasons: List<String>) : ModelCheckResult()
}

fun validateUserProfile(profile: UserProfile): ModelCheckResult {
    val reasons = mutableListOf<String>()
    if (profile.name.isNullOrBlank()) reasons += "name is required"
    if (profile.birthDateEpochMs == null) reasons += "birthDate is required"
    // add other required-field checks here
    return if (reasons.isEmpty()) ModelCheckResult.Valid else ModelCheckResult.Invalid(reasons)
}

/**
 * ProviderWithModelCheck:
 * - profileProvider(profileId) should return UserProfile? (suspend)
 * - fetcher(profile, request) should return CursorPage<Key, Item> (suspend)
 *
 * Usage example (pseudocode):
 *   val provider = ProviderWithModelCheck(profileProvider = { id -> ... }, fetcher = { profile, req -> ... })
 *   provider.fetchForProfile("profile123", null).collect { result -> ... }
 */
class ProviderWithModelCheck<Key : Any, Item : Any>(
    private val profileProvider: suspend (profileId: String) -> UserProfile?,
    private val fetcher: suspend (profile: UserProfile, request: CursorRequest<Key>) -> CursorPage<Key, Item>
) {
    /**
     * Returns a Flow<Result<CursorPage<Key, Item>>> that:
     *  - emits failure if profile missing or invalid
     *  - otherwise emits pages from CursorTask as Result.success(...)
     */
    suspend fun fetchForProfile(profileId: String, initialCursor: Key?, pageSize: Int = 20): Flow<Result<CursorPage<Key, Item>>> = flow {
        val profile = profileProvider(profileId)
        if (profile == null) {
            emit(Result.failure(IllegalArgumentException("profile not found: $profileId")))
            return@flow
        }

        when (val check = validateUserProfile(profile)) {
            is ModelCheckResult.Invalid -> {
                emit(Result.failure(IllegalStateException("profile invalid: ${check.reasons.joinToString(", ")}")))
            }
            ModelCheckResult.Valid -> {
                val cursorTask = CursorTask<Key, Item>(
                    pageSizeDefault = pageSize,
                    fetcher = { req -> fetcher(profile, req) }
                )
                emitAll(cursorTask.fetch(CursorRequest(initialCursor, pageSize)).map { Result.success(it) })
            }
        }
    }
}


