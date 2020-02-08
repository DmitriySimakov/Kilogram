package com.dmitrysimakov.kilogram.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

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

val userTrainingsCollection
    get() = userDocument.collection("trainings")

val userTrainingExercisesCollection
    get() = userDocument.collection("training_exercises")

val userTrainingSetsCollection
    get() = userDocument.collection("training_sets")

val userProgramsCollection
    get() = userDocument.collection("programs")

val userProgramDaysCollection
    get() = userDocument.collection("program_days")

val userProgramDayExercisesCollection
    get() = userDocument.collection("program_day_exercises")

val userPhotosCollection
    get() = userDocument.collection("photos")

val userMeasurementsCollection
    get() = userDocument.collection("measurements")

val programsCollection = firestore.collection("programs")


val firebaseStorage = FirebaseStorage.getInstance()

val imagesRef = firebaseStorage.reference.child("images")