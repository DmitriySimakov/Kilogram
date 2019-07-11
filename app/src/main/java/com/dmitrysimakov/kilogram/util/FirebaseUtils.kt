package com.dmitrysimakov.kilogram.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

val auth: FirebaseAuth = FirebaseAuth.getInstance()

val currentUserUid
    get() = auth.currentUser?.uid

val firestore : FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

val usersCollection = firestore.collection("users")

val currentUserDocument
    get() = usersCollection.document(checkNotNull(currentUserUid))

val chatsCollection = firestore.collection("chats")


val firebaseStorage = FirebaseStorage.getInstance()

val msgImagesRef = firebaseStorage.reference.child("message_images")