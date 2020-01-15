package com.dmitrysimakov.kilogram.data.remote

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Subscriptions(
        val followersIds: MutableList<String> = mutableListOf(),
        val followedIds: MutableList<String> = mutableListOf()
)