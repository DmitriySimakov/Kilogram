package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseInfo
import com.dmitrysimakov.kilogram.data.relation.DetailedTrainingExercise

@Dao
interface TrainingExerciseDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<TrainingExercise>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainingExercise: TrainingExercise)
    
    @Query("""
        SELECT _id, exercise, indexNumber, rest,
        (SELECT secs_since_start FROM training_exercise_set WHERE training_exercise_id = _id ORDER BY secs_since_start DESC LIMIT 1) AS secs_since_start,
        strategy, state
        FROM training_exercise
        WHERE training_id = :training_id
        ORDER BY indexNumber
    """)
    fun getDetailedTrainingExerciseList(training_id: Long): LiveData<List<DetailedTrainingExercise>>
    
    @Query("""
        SELECT te._id AS training_exercise_id, t._id AS training_id, t.start_time
        FROM training_exercise AS te
        INNER JOIN training AS t
        ON te.training_id = t._id
        WHERE te.exercise = :exercise
        AND t.start_time < (SELECT start_time FROM training WHERE _id = :training_id)
        ORDER BY t.start_time DESC
        LIMIT 1
    """)
    fun getPrevTrainingExercise(training_id: Long, exercise: String): LiveData<TrainingExerciseInfo>
    
    @Query("SELECT * FROM training_exercise WHERE training_id = :training_id ORDER BY indexNumber")
    fun getTrainingExercises(training_id: Long): LiveData<List<TrainingExercise>>

    @Query("SELECT * FROM training_exercise WHERE _id = :id")
    fun getTrainingExercise(id: Long): LiveData<TrainingExercise>

    @Query("DELETE FROM training_exercise WHERE _id = :id")
    suspend fun deleteExerciseFromTraining(id: Long)
    
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
}