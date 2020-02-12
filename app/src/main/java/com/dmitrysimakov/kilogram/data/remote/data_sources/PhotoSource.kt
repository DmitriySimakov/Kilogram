package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.data.model.Photo
import com.dmitrysimakov.kilogram.data.remote.firestore
import com.dmitrysimakov.kilogram.data.remote.uid

class PhotoSource(
        workManager: WorkManager
) : RemoteDataSource(workManager) {
    
    private val photosRef
        get() = firestore.collection("users/$uid/photos")
    
    suspend fun newPhotos(lastUpdate: Long) =
            getNewData(Photo::class.java, photosRef, lastUpdate)
    
    fun uploadPhoto(photo: Photo) {
        photosRef.document(photo.id).set(photo) }
}