package com.spiritatlas.data.ai

import retrofit2.http.Body
import retrofit2.http.POST

interface OllamaApi {
    @POST("/api/generate")
    suspend fun generate(@Body request: OllamaRequest): OllamaResponse
}

data class OllamaRequest(
    val model: String = "llama3",
    val prompt: String,
    val stream: Boolean = false
)

data class OllamaResponse(
    val response: String?
)


