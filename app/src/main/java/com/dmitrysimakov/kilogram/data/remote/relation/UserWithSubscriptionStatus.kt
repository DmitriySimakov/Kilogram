package com.dmitrysimakov.kilogram.data.remote.relation

import com.dmitrysimakov.kilogram.data.remote.Subscriptions
import com.dmitrysimakov.kilogram.data.remote.User

data class UserWithSubscriptionStatus(
        var id: String = "",
        var name: String = "",
        var photoUrl: String? = null,
        val isFollowed: Boolean = false
)

fun User.withSubscriptionStatus(subscriptions: Subscriptions?) =
    UserWithSubscriptionStatus(this.id, this.name, this.photoUrl,
            subscriptions?.followedIds?.contains(id) ?: false)