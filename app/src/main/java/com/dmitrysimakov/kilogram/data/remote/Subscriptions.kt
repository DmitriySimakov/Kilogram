package com.dmitrysimakov.kilogram.data.remote

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Subscriptions(
        val followersIds: List<String> = listOf(),
        val followedIds: List<String> = listOf()
)