package com.dmitrysimakov.kilogram.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

val firebaseUser
    get() = FirebaseAuth.getInstance().currentUser

val firestore : FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

val usersCollection = firestore.collection("users")

val userDocument
    get() = usersCollection.document(checkNotNull(firebaseUser?.uid))

val chatsCollection = firestore.collection("chats")


val firebaseStorage = FirebaseStorage.getInstance()

val profileImagesRef = firebaseStorage.reference.child("profile_images")

val msgImagesRef = firebaseStorage.reference.child("message_images")