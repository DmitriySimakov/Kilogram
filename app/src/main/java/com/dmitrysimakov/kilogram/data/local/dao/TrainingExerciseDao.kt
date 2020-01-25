package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.*
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.local.relation.TrainingExerciseInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingExerciseDao {
    
    @Query("SELECT * FROM TrainingExercise WHERE trainingId = :trainingId ORDER BY indexNumber")
    fun trainingExercisesFlow(trainingId: Long): Flow<List<TrainingExercise>>
    
    @Query("SELECT * FROM TrainingExercise WHERE trainingId = :trainingId ORDER BY indexNumber")
    suspend fun trainingExercises(trainingId: Long): List<TrainingExercise>
    
    @Query("SELECT * FROM TrainingExercise WHERE id = :id")
    fun trainingExerciseFlow(id: Long): Flow<TrainingExercise>
    
    @Query("SELECT * FROM TrainingExercise WHERE id = :id")
    suspend fun trainingExercise(id: Long): TrainingExercise
    
    @Query("""
        SELECT TE.id AS trainingExerciseId, T.id AS trainingId, T.startDateTime
        FROM TrainingExercise AS TE
        INNER JOIN training AS T
        ON TE.trainingId = T.id
        WHERE TE.exercise = :exercise
        AND T.startDateTime < (SELECT startDateTime FROM training WHERE id = :trainingId)
        ORDER BY T.startDateTime DESC
        LIMIT 1
    """)
    suspend fun previousTrainingExercise(trainingId: Long, exercise: String): TrainingExerciseInfo?
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainingExercises: List<TrainingExercise>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainingExercise: TrainingExercise): Long
    
    
    @Update
    suspend fun update(trainingExercises: List<TrainingExercise>)
    
    @Update
    suspend fun update(trainingExercise: TrainingExercise)
    
    @Query("UPDATE TrainingExercise SET indexNumber = :indexNumber WHERE id = :id")
    suspend fun setIndexNumber(id: Long, indexNumber: Int)
    
    @Transaction
    suspend fun updateIndexNumbers(exercises: List<TrainingExercise>) {
        for (exercise in exercises) {
            setIndexNumber(exercise.id, exercise.indexNumber)
        }
    }
    
    @Query("""
        UPDATE TrainingExercise
        SET state = :finished
        WHERE trainingId = :trainingId AND state = :running""")
    suspend fun finishTrainingExercises(trainingId: Long, finished: Int, running: Int)
    
    
    @Query("DELETE FROM TrainingExercise WHERE id = :id")
    suspend fun delete(id: Long)
}