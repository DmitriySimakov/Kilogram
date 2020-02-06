package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.PhotoDao
import com.dmitrysimakov.kilogram.data.model.Photo
import com.dmitrysimakov.kilogram.data.remote.data_sources.PhotoSource

class PhotoRepository(
        private val dao: PhotoDao,
        private val src: PhotoSource
) {
    
    fun photos() = dao.photos()
    
    fun recentPhotos(number: Int) = dao.recentPhotos(number)
    
    suspend fun photo(uri: String) = dao.photo(uri)
    
    suspend fun insert(photo: Photo) {
        dao.insert(photo)
        src.uploadPhoto(photo.uri)
    }
}
