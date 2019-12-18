package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.ExerciseTarget
import com.dmitrysimakov.kilogram.data.local.relation.FilterParam

@Dao
interface ExerciseTargetDao {

    @Query("SELECT * FROM exercise_target")
    suspend fun exerciseTargets() : List<ExerciseTarget>
    
    @Query("SELECT name, 0 AS is_active FROM exercise_target")
    suspend fun params() : List<FilterParam>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseTargets: List<ExerciseTarget>)
}