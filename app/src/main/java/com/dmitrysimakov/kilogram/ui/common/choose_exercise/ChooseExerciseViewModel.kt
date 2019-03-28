package com.dmitrysimakov.kilogram.ui.common.choose_exercise

import android.util.Log.d
import androidx.lifecycle.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.dmitrysimakov.kilogram.data.entity.Exercise
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.MuscleRepository
import com.dmitrysimakov.kilogram.util.notifyObservers
import javax.inject.Inject

class ChooseExerciseViewModel @Inject constructor(
        private val exerciseRepository: ExerciseRepository,
        muscleRepository: MuscleRepository
) : ViewModel() {
    
    val filter = Filter()
    
    val exerciseList = Transformations.switchMap(filter.query) {
        exerciseRepository.loadExerciseList(it)
    }
    
    val muscleList = muscleRepository.loadMuscleList()
    
    val mechanicsTypeList = exerciseRepository.loadMechanicsTypeList()
    
    val exerciseTypeList = exerciseRepository.loadExerciseTypeList()
    
    val equipmentList = exerciseRepository.loadEquipmentList()
    
    fun setFavorite(exercise: Exercise, isChecked: Boolean) {
        val updated = exercise.copy()
        updated.is_favorite = isChecked
        exerciseRepository.updateExercise(updated)
    }
    
    inner class Filter {
        val addedToFavorite = MutableLiveData(false)
        val performedEarlier = MutableLiveData(false)
    
        private val muscleIds = mutableListOf<Int>()
        private val mechanicsTypeIds = mutableListOf<Int>()
        private val exerciseTypeIds = mutableListOf<Int>()
        private val equipmentIds = mutableListOf<Int>()
        
        val query = MediatorLiveData<SupportSQLiteQuery>()
    
        init {
            query.addSource(addedToFavorite) { query.value = getQuery() }
            query.addSource(performedEarlier) { query.value = getQuery() }
        }
        
        private fun getQuery(): SupportSQLiteQuery {
            val sb = StringBuilder("SELECT * FROM exercise WHERE _id != 0")
            if (addedToFavorite.value == true) sb.append(" AND is_favorite == 1")
            if (performedEarlier.value == true) sb.append(" AND executions_cnt > 0")
            if (muscleIds.isNotEmpty()) sb.append(" AND main_muscle_id IN ($muscleIds)")
            if (mechanicsTypeIds.isNotEmpty()) sb.append(" AND mechanics_type_id IN ($mechanicsTypeIds)")
            if (exerciseTypeIds.isNotEmpty()) sb.append(" AND exercise_type_id IN ($exerciseTypeIds)")
            if (equipmentIds.isNotEmpty()) sb.append(" AND equipment_id IN ($equipmentIds)")
            val res = sb.toString().replace("[", "").replace("]", "")
            d("QUERY = ", res)
            return SimpleSQLiteQuery(res)
        }
        
        fun setMuscleChecked(id: Int, isChecked: Boolean) {
            if (isChecked) muscleIds.add(id) else muscleIds.remove(id)
            query.value = getQuery()
        }
        
        fun setMechanicsTypeChecked(id: Int, isChecked: Boolean) {
            if (isChecked) mechanicsTypeIds.add(id) else mechanicsTypeIds.remove(id)
            query.value = getQuery()
        }
    
        fun setExerciseTypeChecked(id: Int, isChecked: Boolean) {
            if (isChecked) exerciseTypeIds.add(id) else exerciseTypeIds.remove(id)
            query.value = getQuery()
        }
    
        fun setEquipmentChecked(id: Int, isChecked: Boolean) {
            if (isChecked) equipmentIds.add(id) else equipmentIds.remove(id)
            query.value = getQuery()
        }
    }
}
