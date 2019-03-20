package com.dmitrysimakov.kilogram.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.entity.Training

@Dao
interface TrainingDao {
    
    @Query("SELECT * FROM training ORDER BY start_time DESC")
    fun getTrainingList() : LiveData<List<Training>>
    
    @Query("SELECT * FROM training WHERE _id = :id")
    fun getTraining(id: Long) : LiveData<Training>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(training: Training) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Training>)
    
    @Update
    fun update(training: Training)
    
    @Delete
    fun delete(training: Training)
}