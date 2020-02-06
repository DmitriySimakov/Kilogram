package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.model.ExerciseTarget
import com.dmitrysimakov.kilogram.data.relation.FilterParam

@Dao
interface ExerciseTargetDao {

    @Query("SELECT * FROM ExerciseTarget")
    suspend fun exerciseTargets() : List<ExerciseTarget>
    
    @Query("SELECT name, 0 AS isActive FROM ExerciseTarget")
    suspend fun params() : List<FilterParam>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseTargets: List<ExerciseTarget>)
}