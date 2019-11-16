package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.*
import com.dmitrysimakov.kilogram.data.local.entity.TrainingSet

@Dao
interface TrainingExerciseSetDao {
    
    @Query("SELECT * FROM training_set WHERE training_exercise_id = :trainingExerciseId")
    suspend fun trainingSets(trainingExerciseId: Long) : List<TrainingSet>

    @Query("SELECT * FROM training_set WHERE _id = :id")
    suspend fun trainingSet(id: Long) : TrainingSet
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainingSets: List<TrainingSet>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainingSet: TrainingSet)
    
    
    @Update
    suspend fun update(trainingSet: TrainingSet)
    
    
    @Query("DELETE FROM training_set WHERE _id = :id")
    suspend fun delete(id: Long)
}