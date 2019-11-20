package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.ExerciseTarget
import com.dmitrysimakov.kilogram.data.relation.FilterParam

@Dao
interface ExerciseTargetDao {

    @Query("SELECT * FROM exercise_target")
    suspend fun exerciseTargets() : List<ExerciseTarget>
    
    @Query("SELECT name, 0 AS is_active FROM exercise_target")
    suspend fun params() : List<FilterParam>
    
    @Query("""SELECT t.name,
        CASE WHEN pdt.program_day_id IS NULL THEN 0 ELSE 1 END AS is_active
        FROM exercise_target AS t
        LEFT JOIN program_day_targets AS pdt
        ON t.name = pdt.exercise_target AND pdt.program_day_id = :programDayId
    """)
    suspend fun programDayParams(programDayId: Long) : List<FilterParam>
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseTargets: List<ExerciseTarget>)
}