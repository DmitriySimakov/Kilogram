package com.dmitrysimakov.kilogram.data

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
        @get:Exclude var id: String = "",
        var name: String = "",
        var photoUrl: String? = null
)