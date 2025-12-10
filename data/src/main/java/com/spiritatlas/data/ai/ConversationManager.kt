package com.spiritatlas.data.ai

import android.content.Context
import com.spiritatlas.core.common.Result
import com.spiritatlas.domain.model.UserProfile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages multi-turn conversations with context and history tracking
 *
 * Features:
 * - Conversation history persistence
 * - Context-aware follow-up questions
 * - Session management
 * - Token usage optimization
 * - Profile-specific conversations
 */
@Singleton
class ConversationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val conversationsDir: File by lazy {
        File(context.filesDir, "conversations").apply {
            if (!exists()) mkdirs()
        }
    }

    /**
     * Represents a single turn in a conversation
     */
    data class ConversationTurn(
        val role: Role,
        val content: String,
        val timestamp: LocalDateTime = LocalDateTime.now()
    )

    enum class Role {
        USER, ASSISTANT, SYSTEM
    }

    /**
     * Represents a complete conversation session
     */
    data class Conversation(
        val id: String,
        val profileId: String,
        val turns: List<ConversationTurn>,
        val createdAt: LocalDateTime,
        val lastUpdated: LocalDateTime,
        val metadata: Map<String, String> = emptyMap()
    ) {
        val messageCount: Int get() = turns.size
        val lastMessage: String? get() = turns.lastOrNull()?.content
        val isActive: Boolean get() = turns.isNotEmpty()
    }

    /**
     * Creates a new conversation session
     */
    suspend fun createConversation(
        profileId: String,
        initialContext: String? = null
    ): Result<Conversation> = withContext(Dispatchers.IO) {
        try {
            val conversationId = generateConversationId(profileId)
            val turns = mutableListOf<ConversationTurn>()

            // Add system context if provided
            if (initialContext != null) {
                turns.add(ConversationTurn(
                    role = Role.SYSTEM,
                    content = initialContext
                ))
            }

            val conversation = Conversation(
                id = conversationId,
                profileId = profileId,
                turns = turns,
                createdAt = LocalDateTime.now(),
                lastUpdated = LocalDateTime.now()
            )

            saveConversation(conversation)
            Result.Success(conversation)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Adds a user message to the conversation
     */
    suspend fun addUserMessage(
        conversationId: String,
        message: String
    ): Result<Conversation> = withContext(Dispatchers.IO) {
        try {
            val conversation = loadConversation(conversationId)
                ?: return@withContext Result.Error(IllegalArgumentException("Conversation not found"))

            val updatedTurns = conversation.turns + ConversationTurn(
                role = Role.USER,
                content = message
            )

            val updatedConversation = conversation.copy(
                turns = updatedTurns,
                lastUpdated = LocalDateTime.now()
            )

            saveConversation(updatedConversation)
            Result.Success(updatedConversation)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Adds an assistant response to the conversation
     */
    suspend fun addAssistantResponse(
        conversationId: String,
        response: String
    ): Result<Conversation> = withContext(Dispatchers.IO) {
        try {
            val conversation = loadConversation(conversationId)
                ?: return@withContext Result.Error(IllegalArgumentException("Conversation not found"))

            val updatedTurns = conversation.turns + ConversationTurn(
                role = Role.ASSISTANT,
                content = response
            )

            val updatedConversation = conversation.copy(
                turns = updatedTurns,
                lastUpdated = LocalDateTime.now()
            )

            saveConversation(updatedConversation)
            Result.Success(updatedConversation)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Gets conversation history formatted for Claude API
     */
    fun getFormattedHistory(conversation: Conversation, maxTurns: Int = 10): JSONArray {
        val messages = JSONArray()

        // Get recent turns (exclude system messages for the messages array)
        val recentTurns = conversation.turns
            .filter { it.role != Role.SYSTEM }
            .takeLast(maxTurns * 2) // Take last N exchanges (user + assistant pairs)

        for (turn in recentTurns) {
            messages.put(JSONObject().apply {
                put("role", turn.role.name.lowercase())
                put("content", turn.content)
            })
        }

        return messages
    }

    /**
     * Gets system context from conversation
     */
    fun getSystemContext(conversation: Conversation): String? {
        return conversation.turns
            .firstOrNull { it.role == Role.SYSTEM }
            ?.content
    }

    /**
     * Loads a conversation by ID
     */
    suspend fun loadConversation(conversationId: String): Conversation? = withContext(Dispatchers.IO) {
        try {
            val file = File(conversationsDir, "$conversationId.json")
            if (!file.exists()) return@withContext null

            val json = JSONObject(file.readText())
            parseConversation(json)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Gets all conversations for a profile
     */
    suspend fun getConversationsForProfile(profileId: String): List<Conversation> = withContext(Dispatchers.IO) {
        try {
            conversationsDir.listFiles()?.mapNotNull { file ->
                if (file.extension == "json") {
                    try {
                        val json = JSONObject(file.readText())
                        val conversation = parseConversation(json)
                        if (conversation.profileId == profileId) conversation else null
                    } catch (e: Exception) {
                        null
                    }
                } else null
            }?.sortedByDescending { it.lastUpdated } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Gets the most recent active conversation for a profile
     */
    suspend fun getActiveConversation(profileId: String): Conversation? {
        return getConversationsForProfile(profileId).firstOrNull()
    }

    /**
     * Deletes a conversation
     */
    suspend fun deleteConversation(conversationId: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val file = File(conversationsDir, "$conversationId.json")
            if (file.exists()) {
                file.delete()
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Clears all conversations for a profile
     */
    suspend fun clearConversations(profileId: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val conversations = getConversationsForProfile(profileId)
            conversations.forEach { conversation ->
                deleteConversation(conversation.id)
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Summarizes conversation for token optimization
     * Reduces token usage by condensing older messages
     */
    suspend fun summarizeConversation(
        conversationId: String,
        keepRecentTurns: Int = 6
    ): Result<Conversation> = withContext(Dispatchers.IO) {
        try {
            val conversation = loadConversation(conversationId)
                ?: return@withContext Result.Error(IllegalArgumentException("Conversation not found"))

            if (conversation.turns.size <= keepRecentTurns + 1) {
                return@withContext Result.Success(conversation)
            }

            // Keep system message + recent turns
            val systemMessage = conversation.turns.firstOrNull { it.role == Role.SYSTEM }
            val recentTurns = conversation.turns.takeLast(keepRecentTurns)
            val turnsToSummarize = conversation.turns.drop(
                if (systemMessage != null) 1 else 0
            ).dropLast(keepRecentTurns)

            // Create summary of old turns
            val summary = buildString {
                appendLine("**Previous Discussion Summary:**")
                turnsToSummarize.chunked(2).forEach { pair ->
                    val userMsg = pair.firstOrNull { it.role == Role.USER }
                    val assistantMsg = pair.firstOrNull { it.role == Role.ASSISTANT }

                    if (userMsg != null && assistantMsg != null) {
                        appendLine("Q: ${userMsg.content.take(100)}...")
                        appendLine("A: ${assistantMsg.content.take(150)}...")
                        appendLine()
                    }
                }
            }

            val summaryTurn = ConversationTurn(
                role = Role.SYSTEM,
                content = summary,
                timestamp = turnsToSummarize.lastOrNull()?.timestamp ?: LocalDateTime.now()
            )

            val newTurns = buildList {
                systemMessage?.let { add(it) }
                add(summaryTurn)
                addAll(recentTurns)
            }

            val updatedConversation = conversation.copy(
                turns = newTurns,
                lastUpdated = LocalDateTime.now()
            )

            saveConversation(updatedConversation)
            Result.Success(updatedConversation)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Estimates token count for conversation (rough approximation)
     */
    fun estimateTokenCount(conversation: Conversation): Int {
        // Rough estimate: 1 token ≈ 4 characters
        return conversation.turns.sumOf { it.content.length / 4 }
    }

    // Private helper methods

    private fun generateConversationId(profileId: String): String {
        val timestamp = System.currentTimeMillis()
        return "${profileId}_${timestamp}"
    }

    private suspend fun saveConversation(conversation: Conversation) = withContext(Dispatchers.IO) {
        val file = File(conversationsDir, "${conversation.id}.json")
        val json = serializeConversation(conversation)
        file.writeText(json.toString(2))
    }

    private fun serializeConversation(conversation: Conversation): JSONObject {
        return JSONObject().apply {
            put("id", conversation.id)
            put("profileId", conversation.profileId)
            put("createdAt", conversation.createdAt.format(DateTimeFormatter.ISO_DATE_TIME))
            put("lastUpdated", conversation.lastUpdated.format(DateTimeFormatter.ISO_DATE_TIME))

            put("turns", JSONArray().apply {
                conversation.turns.forEach { turn ->
                    put(JSONObject().apply {
                        put("role", turn.role.name)
                        put("content", turn.content)
                        put("timestamp", turn.timestamp.format(DateTimeFormatter.ISO_DATE_TIME))
                    })
                }
            })

            put("metadata", JSONObject(conversation.metadata))
        }
    }

    private fun parseConversation(json: JSONObject): Conversation {
        val turns = mutableListOf<ConversationTurn>()
        val turnsArray = json.getJSONArray("turns")

        for (i in 0 until turnsArray.length()) {
            val turnJson = turnsArray.getJSONObject(i)
            turns.add(ConversationTurn(
                role = Role.valueOf(turnJson.getString("role")),
                content = turnJson.getString("content"),
                timestamp = LocalDateTime.parse(
                    turnJson.getString("timestamp"),
                    DateTimeFormatter.ISO_DATE_TIME
                )
            ))
        }

        val metadata = mutableMapOf<String, String>()
        val metadataJson = json.optJSONObject("metadata")
        metadataJson?.keys()?.forEach { key ->
            metadata[key] = metadataJson.getString(key)
        }

        return Conversation(
            id = json.getString("id"),
            profileId = json.getString("profileId"),
            turns = turns,
            createdAt = LocalDateTime.parse(
                json.getString("createdAt"),
                DateTimeFormatter.ISO_DATE_TIME
            ),
            lastUpdated = LocalDateTime.parse(
                json.getString("lastUpdated"),
                DateTimeFormatter.ISO_DATE_TIME
            ),
            metadata = metadata
        )
    }

    companion object {
        private const val MAX_TOKENS_PER_CONVERSATION = 8000 // Leave room for response
        private const val TOKENS_PER_TURN_ESTIMATE = 150 // Average tokens per exchange
    }
}
