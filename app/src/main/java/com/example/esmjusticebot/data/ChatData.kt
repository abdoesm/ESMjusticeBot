package com.example.esmjusticebot.data

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import io.github.cdimascio.dotenv.Dotenv
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ChatData {
    /*var dotenv = Dotenv.load()
    val api_key: String =dotenv["JUSTICE_BOT_API_KEY"]*/


    val api_key: String ="api"
    private val preprompt: String = "Hello! I am the Justice High School Bot. " +
            "I'm here to assist you with any questions or concerns related to justice studies within the school. " +
            "Feel free to ask me anything!"
    var isFirstMessage = true // Variable to track if it's the first message

    suspend fun getResponse(prompt: String): Chat {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = api_key
        )

        try {
            // Introduce the bot if it's the first message
            if (isFirstMessage) {
                isFirstMessage = false
                return Chat(
                    prompt = preprompt,
                    bitmap = null,
                    isFromUser = false
                )
            }

            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(prompt)
            }

            return Chat(
                prompt = response.text ?: "error",
                bitmap = null,
                isFromUser = false
            )

        } catch (e: Exception) {
            return Chat(
                prompt = e.message ?: "error",
                bitmap = null,
                isFromUser = false
            )
        }
    }

    suspend fun getResponseWithImage(prompt: String = preprompt, bitmap: Bitmap): Chat {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro-vision",
            apiKey = api_key
        )

        try {
            val inputContent = content {
                image(bitmap)
                text(prompt)
            }

            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(inputContent)
            }

            return Chat(
                prompt = response.text ?: "error",
                bitmap = null,
                isFromUser = false
            )

        } catch (e: Exception) {
            return Chat(
                prompt = e.message ?: "error",
                bitmap = null,
                isFromUser = false
            )
        }
    }
}
