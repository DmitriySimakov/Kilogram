package com.dmitrysimakov.kilogram.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

val firebaseUser
    get() = FirebaseAuth.getInstance().currentUser

val uid
    get() = firebaseUser!!.uid

val firestore = FirebaseFirestore.getInstance()

val usersCollection = firestore.collection("users")

val userDocument
    get() = usersCollection.document(uid)

val tokensDocument
    get () = firestore.collection("registration_tokens").document(uid)

val chatsCollection
    get() = userDocument.collection("chats")

val subscriptionsCollection = firestore.collection("subscriptions")

val subscriptionsDocument
    get() = subscriptionsCollection.document(uid)

val postsCollection = firestore.collection("posts")


val firebaseStorage = FirebaseStorage.getInstance()

val profileImagesRef = firebaseStorage.reference.child("profile_images")

val msgImagesRef = firebaseStorage.reference.child("message_images")