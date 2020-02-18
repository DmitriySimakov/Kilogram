package com.dmitrysimakov.kilogram.data.repository

import android.content.Context
import android.net.Uri
import com.dmitrysimakov.kilogram.data.local.dao.PhotoDao
import com.dmitrysimakov.kilogram.data.model.Photo
import com.dmitrysimakov.kilogram.data.remote.data_sources.FirebaseStorageSource
import com.dmitrysimakov.kilogram.data.remote.data_sources.PhotoSource
import com.dmitrysimakov.kilogram.workers.UploadPhotoWorker

class PhotoRepository(
        private val dao: PhotoDao,
        private val src: PhotoSource,
        private val firebaseStorage: FirebaseStorageSource
) {
    
    fun photos() = dao.photos()
    
    fun recentPhotos(number: Int) = dao.recentPhotos(number)
    
    suspend fun photo(uri: String) = dao.photo(uri)
    
    suspend fun insert(photo: Photo) {
        dao.insert(photo)
        src.scheduleUpload(photo.uri, UploadPhotoWorker::class.java)
    }
    
    suspend fun uploadPhoto(photo: Photo) {
        firebaseStorage.uploadImage(Uri.parse(photo.uri), photo.id)
        src.uploadPhoto(photo)
    }
    
    suspend fun delete(uri: String) {
        dao.delete(uri)
        src.scheduleDeletion(uri, UploadPhotoWorker::class.java)
    }
    
    suspend fun syncPhotos(context: Context, lastUpdate: Long) {
        val items = src.newPhotos(lastUpdate)
        val (deletedItems, existingItems) = items.partition { it.deleted }
        
        deletedItems.forEach { dao.delete(it.uri) }
        existingItems.forEach { photo ->
            if (photo.dateTime == photo.lastUpdate) {
                val uri = firebaseStorage.downloadImage(context, photo.id)
                dao.insert(photo.copy(uri = uri.toString()))
            } else dao.insert(photo)
        }
    }
}
