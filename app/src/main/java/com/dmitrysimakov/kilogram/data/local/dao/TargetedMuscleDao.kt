package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.model.TargetedMuscle

@Dao
interface TargetedMuscleDao {
    
    @Query("SELECT muscle FROM TargetedMuscle WHERE exercise = :exerciseName")
    suspend fun targetedMuscles(exerciseName: String) : List<String>
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(targetedMuscles: List<TargetedMuscle>)
}