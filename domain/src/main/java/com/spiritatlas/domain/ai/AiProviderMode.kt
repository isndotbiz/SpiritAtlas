package com.spiritatlas.domain.ai

enum class AiProviderMode {
    AUTO,           // Automatically select best available: Gemini → Groq → OpenRouter → Local
    GEMINI,         // Google Gemini 2.5 Flash (free, 15 RPM)
    GROQ,           // Groq Llama 3.3 (fast inference)
    OPENROUTER,     // OpenRouter (multiple models)
    LOCAL           // Ollama (on-device)
}


