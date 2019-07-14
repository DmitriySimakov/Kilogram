package com.dmitrysimakov.kilogram.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FirebaseDao {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    
    val user
        get() = auth.currentUser
    
    val firestore : FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    
    val usersCollection = firestore.collection("users")
    
    val userDocument = usersCollection.document(user!!.uid)
    
    val chatsCollection = firestore.collection("chats")
    
    
    val firebaseStorage = FirebaseStorage.getInstance()
    
    val msgImagesRef = firebaseStorage.reference.child("message_images")
}