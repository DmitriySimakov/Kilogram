package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.ExerciseType
import com.dmitrysimakov.kilogram.data.relation.FilterParam

@Dao
interface ExerciseTypeDao {
    
    @Query("SELECT name, 0 AS is_active FROM exercise_type")
    suspend fun params(): List<FilterParam>
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseTypes: List<ExerciseType>)
}