package com.dmitrysimakov.kilogram.data.remote.relation

import com.dmitrysimakov.kilogram.data.remote.models.Subscriptions
import com.dmitrysimakov.kilogram.data.remote.models.User

data class UserWithSubscriptionStatus(
        var id: String = "",
        var name: String = "",
        var photoUrl: String? = null,
        var followersCount: Int = 0,
        var followedCount: Int = 0,
        var isFollowed: Boolean = false
)

fun User.withSubscriptionStatus(subscriptions: Subscriptions?) =
    UserWithSubscriptionStatus(this.id, this.name, this.photoUrl, this.followersCount, this.followedCount,
            subscriptions?.followedIds?.contains(id) ?: false)