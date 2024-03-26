package com.example.esmjusticebot

import android.graphics.Bitmap
import com.example.esmjusticebot.data.Chat

data class ChatState (
    val chatList: MutableList<Chat> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap? = null
)