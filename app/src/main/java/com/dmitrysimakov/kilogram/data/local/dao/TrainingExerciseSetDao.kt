package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExerciseSet

@Dao
interface TrainingExerciseSetDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<TrainingExerciseSet>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: TrainingExerciseSet)
    
    @Query("SELECT * FROM training_exercise_set WHERE training_exercise_id = :training_exercise_id")
    fun getSets(training_exercise_id: Long) : LiveData<List<TrainingExerciseSet>>

    @Query("SELECT * FROM training_exercise_set WHERE _id = :id")
    fun getSet(id: Long) : LiveData<TrainingExerciseSet>
    
    @Query("DELETE FROM training_exercise_set WHERE _id = :id")
    suspend fun delete(id: Long)
    
    @Update
    suspend fun update(set: TrainingExerciseSet)
}