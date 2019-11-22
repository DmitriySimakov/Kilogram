package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.TrainingTarget
import com.dmitrysimakov.kilogram.data.local.relation.FilterParam

@Dao
interface TrainingMuscleDao {
    
    @Query("""SELECT et.name, CASE WHEN tt.training_id IS NULL THEN 0 ELSE 1 END AS is_active
        FROM exercise_target AS et
        LEFT JOIN training_target AS tt
        ON tt.training_id = :trainingId AND tt.exercise_target = et.name
    """)
    suspend fun params(trainingId: Long): List<FilterParam>

    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<TrainingTarget>)
}