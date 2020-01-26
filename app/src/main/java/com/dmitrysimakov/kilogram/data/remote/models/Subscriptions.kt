package com.dmitrysimakov.kilogram.data.remote.models

data class Subscriptions(
        val followersIds: List<String> = listOf(),
        val followedIds: List<String> = listOf()
)

enum class SubscriptionAction{ SUBSCRIBE, UNSUBSCRIBE }