package com.dmitrysimakov.kilogram.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.entity.Training

@Dao
interface TrainingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(training: Training) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Training>)

    @Query("SELECT * FROM training ORDER BY date_time DESC")
    fun getTrainingList() : LiveData<List<Training>>
}