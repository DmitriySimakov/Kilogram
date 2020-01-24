package com.dmitrysimakov.kilogram.data.remote

data class Chat(
        val id: String = "",
        val companion: User = User(),
        val lastMessage: Message = Message()
)