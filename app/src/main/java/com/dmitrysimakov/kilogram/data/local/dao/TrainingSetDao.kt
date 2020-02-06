package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.*
import com.dmitrysimakov.kilogram.data.model.TrainingSet
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingSetDao {
    
    @Query("SELECT * FROM TrainingSet WHERE trainingExerciseId = :trainingExerciseId")
    fun trainingSetsFlow(trainingExerciseId: String) : Flow<List<TrainingSet>>

    @Query("SELECT * FROM TrainingSet WHERE id = :id")
    suspend fun trainingSet(id: String) : TrainingSet
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainingSets: List<TrainingSet>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainingSet: TrainingSet)
    
    
    @Update
    suspend fun update(trainingSet: TrainingSet)
    
    
    @Query("DELETE FROM TrainingSet WHERE id = :id")
    suspend fun delete(id: String)
}