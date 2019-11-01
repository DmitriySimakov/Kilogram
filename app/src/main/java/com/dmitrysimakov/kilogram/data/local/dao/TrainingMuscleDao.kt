package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.TrainingMuscle
import com.dmitrysimakov.kilogram.data.relation.FilterParam

@Dao
interface TrainingMuscleDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<TrainingMuscle>)

    @Query("""SELECT m.name, CASE WHEN tm.training_id IS NULL THEN 0 ELSE 1 END AS is_active
        FROM muscle AS m
        LEFT JOIN training_muscle AS tm
        ON tm.training_id = :trainingId AND tm.muscle = m.name
    """)
    fun getParamList(trainingId: Long): LiveData<List<FilterParam>>
}