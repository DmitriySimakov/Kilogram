package com.dmitrysimakov.kilogram.data.remote.models

data class User(
        val id: String = "",
        val name: String = "",
        val photoUrl: String? = null,
        val followersCount: Int = 0,
        val followedCount: Int = 0
)