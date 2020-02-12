package com.dmitrysimakov.kilogram.data.remote.data_sources

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.dmitrysimakov.kilogram.AUTHORITY
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FirebaseStorageSource {
    
    private val imagesRef = FirebaseStorage.getInstance().reference.child("images")
    
    suspend fun downloadImage(context: Context, filename: String): Uri {
        val file = createTempFile(filename, ".jpg", context.getExternalFilesDir(Environment.DIRECTORY_PICTURES))
        imagesRef.child(filename).getFile(file).await()
        return FileProvider.getUriForFile(context, AUTHORITY, file)
    }
    
    suspend fun uploadImage(uri: Uri, path: String = uri.lastPathSegment!!): String {
        val imageRef = imagesRef.child(path)
        imageRef.putFile(uri).await()
        return imageRef.downloadUrl.await().toString()
    }
}