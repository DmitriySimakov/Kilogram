package com.dmitrysimakov.kilogram.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.relation.ExerciseMeasures
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR

@Dao
interface TrainingExerciseDao {

    @Query("""
        SELECT te._id AS _id, e._id AS exercise_id, e.name, te.num, te.rest,
        (SELECT secs_since_start FROM training_exercise_set WHERE training_exercise_id = te._id ORDER BY secs_since_start DESC LIMIT 1) AS secs_since_start,
        te.strategy, te.state
        FROM training_exercise AS te
        INNER JOIN exercise AS e
        ON te.exercise_id = e._id
        WHERE te.training_id = :training_id
        ORDER BY te.num
    """)
    fun getExercises(training_id: Long): LiveData<List<TrainingExerciseR>>
    
    @Query("""
        SELECT te._id, e._id AS exercise_id, e.name, te.num, te.rest,
        (SELECT secs_since_start FROM training_exercise_set WHERE training_exercise_id = te._id ORDER BY secs_since_start DESC LIMIT 1) AS secs_since_start,
        te.strategy, te.state
        FROM training_exercise AS te
        INNER JOIN exercise AS e
        ON te.exercise_id = e._id
        WHERE te._id = :id
    """)
    fun getExercise(id: Long): LiveData<TrainingExerciseR>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<TrainingExercise>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trainingExercise: TrainingExercise)

    @Query("DELETE FROM training_exercise WHERE _id = :id")
    fun deleteExerciseFromTraining(id: Long)
    
    @Query("""
        SELECT
        measure_weight AS weight,
        measure_reps AS reps,
        measure_time AS time,
        measure_distance AS distance
        FROM training_exercise
        WHERE _id = :trainingExerciseId
        """)
    fun getExerciseMeasures(trainingExerciseId: Long): LiveData<ExerciseMeasures>
    
    @Suppress("AndroidUnresolvedRoomSqlReference")
    @Query("""
        INSERT INTO training_exercise (training_id, exercise_id, num, rest, strategy, state, measure_weight, measure_reps, measure_time, measure_distance)
        SELECT :trainingId, exercise_id, num, rest, strategy, 0, measure_weight, measure_reps, measure_time, measure_distance
        FROM program_day_exercise
        WHERE program_day_id = :programDayId
    """)
    fun fillTrainingWithProgramExercises(trainingId: Long, programDayId: Long)
    
    @Query("UPDATE training_exercise SET state = :state WHERE _id = :id")
    fun updateState(id: Long, state: Int)
    
    @Query("""
        UPDATE training_exercise
        SET state = :finished
        WHERE training_id = :trainingId AND state = :running""")
    fun finishTrainingExercises(trainingId: Long, finished: Int, running: Int)
}