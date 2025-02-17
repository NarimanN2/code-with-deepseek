package net.nariman.codewithdeepseek

import dev.langchain4j.model.ollama.OllamaChatModel

class DeepseekModel {
    private val chatModel: OllamaChatModel by lazy {
        OllamaChatModel
            .builder()
            .baseUrl(BASE_URL)
            .modelName(MODEL_NAME)
            .build()
    }

    fun chat(message: String): String {
        return chatModel.chat(message)
    }

    companion object {
        private const val MODEL_NAME = "deepseek-r1:8b"
        private const val BASE_URL = "http://localhost:11434"
    }
}