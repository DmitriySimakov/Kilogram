package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.TargetedMuscle

@Dao
interface TargetedMuscleDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<TargetedMuscle>)
    
    @Query("SELECT muscle FROM targeted_muscle WHERE exercise = :exerciseName")
    fun getTargetedMuscles(exerciseName: String) : LiveData<List<String>>
}