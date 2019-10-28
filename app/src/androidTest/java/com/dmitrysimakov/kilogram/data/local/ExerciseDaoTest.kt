package com.dmitrysimakov.kilogram.data.local

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.dmitrysimakov.kilogram.data.local.dao.ExerciseDao
import com.dmitrysimakov.kilogram.data.relation.DetailedExerciseR
import com.dmitrysimakov.kilogram.util.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test

class ExerciseDaoTest : DbTest() {
    
    private lateinit var dao: ExerciseDao
    
    @Before fun initDb() {
        dao = db.exerciseDao()
    }
    
    @Test fun getDetailedExerciseR() {
        val testExercise = testExercises[0]
        val targetedMuscles = testTargetedMuscles
                .filter { it.exercise_id == testExercise._id }
                .map { tm -> testMuscles.find { it._id == tm.muscle_id }?.name }
                .joinToString(", ")
        val mainMuscle = testMuscles
                .find { it._id == testExercise.main_muscle_id }?.name
        val mechanicsType = testMechanicsTypes
                .find { it._id == testExercise.mechanics_type_id }?.name
        val exerciseType = testExerciseTypes
                .find { it._id == testExercise.exercise_type_id }?.name
        val equipment = testEquipment
                .find { it._id == testExercise.equipment_id }?.name
        
        val testSample = DetailedExerciseR(
                testExercise._id,
                testExercise.name,
                testExercise.description,
                testExercise.is_favorite,
                mainMuscle,
                targetedMuscles,
                mechanicsType,
                exerciseType,
                equipment
        )
        val exercise = getValue(dao.getDetailedExerciseR(testExercise._id))
        assertThat(exercise, equalTo(testSample))
    }
    
    @Test fun setFavorite() {
        var exercise = getValue(dao.getExercise(1))
        val isFavorite = exercise.is_favorite
        dao.setFavorite(1, !isFavorite)
        exercise = getValue(dao.getExercise(1))
        assertThat(exercise.is_favorite, not(equalTo(isFavorite)))
    }
    
    @Test fun increaseExecutionsCnt() {
        var exercise = getValue(dao.getExercise(1))
        val cnt = exercise.executions_cnt
        dao.increaseExecutionsCnt(1)
        exercise = getValue(dao.getExercise(1))
        assertThat(exercise.executions_cnt, equalTo(cnt + 1))
    }
    
    @Test fun decreaseExecutionsCnt() {
        var exercise = getValue(dao.getExercise(1))
        val cnt = exercise.executions_cnt
        dao.decreaseExecutionsCnt(1)
        exercise = getValue(dao.getExercise(1))
        assertThat(exercise.executions_cnt, equalTo(cnt - 1))
    }
}