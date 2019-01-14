package com.dmitrysimakov.kilogram.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.entity.Training

@Dao
interface TrainingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(training: Training) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Training>)

    @Query("SELECT * FROM training ORDER BY date_time DESC")
    fun getTrainingList() : LiveData<List<Training>>

    @Delete
    fun delete(training: Training)
}