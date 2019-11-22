package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {
    
    @Query("SELECT * FROM photo ORDER BY date DESC")
    fun photos(): Flow<List<Photo>>
    
    @Query("SELECT * FROM photo ORDER BY date DESC LIMIT :number")
    fun recentPhotos(number: Int): Flow<List<Photo>>
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: Photo)
}