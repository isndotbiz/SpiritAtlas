package com.spiritatlas.data.ai

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenRouterApi {
    @POST("/api/v1/chat/completions")
    suspend fun complete(
        @Header("Authorization") authorization: String,
        @Body request: CompletionRequest
    ): CompletionResponse
}

data class CompletionRequest(
    val model: String,
    val messages: List<Message>,
    @com.squareup.moshi.Json(name = "max_tokens")
    val maxTokens: Int? = null
)

data class Message(
    val role: String,
    val content: String
)

data class CompletionResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)


