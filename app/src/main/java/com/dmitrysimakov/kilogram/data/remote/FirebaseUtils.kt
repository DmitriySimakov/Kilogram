package com.dmitrysimakov.kilogram.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

val firebaseUser
    get() = FirebaseAuth.getInstance().currentUser

val uid
    get() = firebaseUser!!.uid

val firestore = FirebaseFirestore.getInstance()

fun generateId() = firestore.collection("path").document().id

val usersCollection = firestore.collection("users")

val userDocument
    get() = usersCollection.document(uid)

val userTokensDocument
    get() = firestore.document("registration_tokens/$uid")