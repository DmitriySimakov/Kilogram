package com.dmitrysimakov.kilogram.data.remote.data_sources

import android.net.Uri
import com.dmitrysimakov.kilogram.data.remote.imagesRef
import kotlinx.coroutines.tasks.await

class FirebaseStorageSource {
    
    suspend fun uploadImage(uri: Uri): String {
        val imageRef = imagesRef.child(uri.lastPathSegment!!)
        imageRef.putFile(uri).await()
        return imageRef.downloadUrl.await().toString()
    }
}