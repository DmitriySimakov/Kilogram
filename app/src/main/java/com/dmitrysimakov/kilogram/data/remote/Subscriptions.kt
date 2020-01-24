package com.dmitrysimakov.kilogram.data.remote

data class Subscriptions(
        val followersIds: List<String> = listOf(),
        val followedIds: List<String> = listOf()
)