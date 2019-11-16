package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseInfo
import com.dmitrysimakov.kilogram.data.relation.DetailedTrainingExercise

@Dao
interface TrainingExerciseDao {
    
    @Query("""
        SELECT _id, exercise, indexNumber, rest,
        (SELECT secs_since_start FROM training_set WHERE training_exercise_id = _id ORDER BY secs_since_start DESC LIMIT 1) AS secs_since_start,
        strategy, state
        FROM training_exercise
        WHERE training_id = :trainingId
        ORDER BY indexNumber
    """)
    suspend fun detailedTrainingExercises(trainingId: Long): List<DetailedTrainingExercise>
    
    @Query("""
        SELECT te._id AS training_exercise_id, t._id AS training_id, t.start_time
        FROM training_exercise AS te
        INNER JOIN training AS t
        ON te.training_id = t._id
        WHERE te.exercise = :exercise
        AND t.start_time < (SELECT start_time FROM training WHERE _id = :trainingId)
        ORDER BY t.start_time DESC
        LIMIT 1
    """)
    suspend fun previousTrainingExercise(trainingId: Long, exercise: String): TrainingExerciseInfo?
    
    @Query("SELECT * FROM training_exercise WHERE training_id = :trainingId ORDER BY indexNumber")
    suspend fun trainingExercises(trainingId: Long): List<TrainingExercise>

    @Query("SELECT * FROM training_exercise WHERE _id = :id")
    suspend fun trainingExercise(id: Long): TrainingExercise
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainingExercises: List<TrainingExercise>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainingExercise: TrainingExercise)
    
    @Query("""
        INSERT INTO training_exercise (training_id, exercise, indexNumber, rest, strategy, state, measure_weight, measure_reps, measure_time, measure_distance)
        SELECT :trainingId, exercise, indexNumber, rest, strategy, 0, measure_weight, measure_reps, measure_time, measure_distance
        FROM program_day_exercise
        WHERE program_day_id = :programDayId
    """)
    suspend fun fillTrainingWithProgramExercises(trainingId: Long, programDayId: Long)
    
    @Query("UPDATE training_exercise SET state = :state WHERE _id = :id")
    suspend fun updateState(id: Long, state: Int)
    
    @Query("UPDATE training_exercise SET indexNumber = :indexNumber WHERE _id = :id")
    suspend fun setIndexNumber(id: Long, indexNumber: Int)
    
    @Transaction
    suspend fun updateIndexNumbers(exercises: List<DetailedTrainingExercise>) {
        for (exercise in exercises) {
            setIndexNumber(exercise._id, exercise.indexNumber)
        }
    }
    
    @Query("""
        UPDATE training_exercise
        SET state = :finished
        WHERE training_id = :trainingId AND state = :running""")
    suspend fun finishTrainingExercises(trainingId: Long, finished: Int, running: Int)
    
    @Query("DELETE FROM training_exercise WHERE _id = :id")
    suspend fun delete(id: Long)
}