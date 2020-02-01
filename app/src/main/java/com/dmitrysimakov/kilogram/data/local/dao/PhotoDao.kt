package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {
    
    @Query("SELECT * FROM Photo ORDER BY dateTime DESC")
    fun photos(): Flow<List<Photo>>
    
    @Query("SELECT * FROM Photo ORDER BY dateTime DESC LIMIT :number")
    fun recentPhotos(number: Int): Flow<List<Photo>>
    
    @Query("SELECT * FROM Photo WHERE uri = :uri")
    suspend fun photo(uri: String): Photo
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photos: List<Photo>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: Photo)
    
    
    @Query("DELETE FROM Photo WHERE uri = :uri")
    suspend fun delete(uri: String)
}