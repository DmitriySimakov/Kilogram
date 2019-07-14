package com.dmitrysimakov.kilogram.data.remote

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Person(
        var name: String = "",
        var photoUrl: String? = null,
        val registrationTokens: MutableList<String> = mutableListOf(),
        @get:Exclude var id: String = ""
)