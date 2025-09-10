package com.spiritatlas.domain.repository

import com.spiritatlas.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveUserProfile(profile: UserProfile)
    fun getUserProfile(): Flow<UserProfile?>
    suspend fun clearUserData()
}


