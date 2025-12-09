package com.spiritatlas.domain.ai

enum class AiProviderMode {
    AUTO,           // Automatically select best available: Gemini → Groq → OpenRouter → Local
    GEMINI,         // Google Gemini 2.5 Flash (app-provided key, free tier)
    GROQ,           // Groq Llama 3.3 70B (app-provided key, fast & free)
    OPENAI,         // OpenAI GPT-4o (user-provided key or OAuth)
    CLAUDE,         // Anthropic Claude (user-provided key or OAuth)
    OPENROUTER,     // OpenRouter (multiple models, app or user key)
    LOCAL           // Ollama (on-device, privacy-focused)
}


