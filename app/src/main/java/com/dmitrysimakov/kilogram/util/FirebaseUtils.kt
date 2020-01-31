package com.dmitrysimakov.kilogram.util

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
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
    get() = firestore.document("registration_tokens/$uid")

val trainingsCollection
    get() = userDocument.collection("trainings")

val trainingExercisesCollection
    get() = userDocument.collection("training_exercises")

val trainingSetsCollection
    get() = userDocument.collection("training_sets")

val programsCollection
    get() = userDocument.collection("programs")

val programDaysCollection
    get() = userDocument.collection("program_days")

val programDayExercisesCollection
    get() = userDocument.collection("program_day_exercises")

val photosCollection
    get() = userDocument.collection("photos")

val measurementsCollection
    get() = userDocument.collection("measurements")

val chatsCollection
    get() = userDocument.collection("chats")

val subscriptionsCollection = firestore.collection("subscriptions")

val subscriptionsDocument
    get() = subscriptionsCollection.document(uid)

val postsCollection = firestore.collection("posts")


val firebaseStorage = FirebaseStorage.getInstance()

val profileImagesRef = firebaseStorage.reference.child("profile_images")

val msgImagesRef = firebaseStorage.reference.child("message_images")


fun CollectionReference.getNewDataTask(lastUpdate: String?): Task<QuerySnapshot> {
    return if (lastUpdate == null) get()
    else whereGreaterThan("lastUpdate", lastUpdate).get()
}