package com.dmitrysimakov.kilogram.ui.common.choose_exercise

import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQueryBuilder
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.MuscleRepository
import com.dmitrysimakov.kilogram.util.FilterParameter
import com.dmitrysimakov.kilogram.util.notifyObservers
import com.dmitrysimakov.kilogram.util.setNewValue
import java.lang.StringBuilder
import javax.inject.Inject

class ChooseExerciseViewModel @Inject constructor(
        private val exerciseRepository: ExerciseRepository,
        muscleRepository: MuscleRepository
) : ViewModel() {
    
    val filter = MutableLiveData(Filter())
    
    val exerciseList = Transformations.switchMap(filter) {
        exerciseRepository.loadExerciseList(it.getQuery())
    }
    
    val muscleList = Transformations.map(muscleRepository.loadMuscleList()) { list ->
        list.map { FilterParameter(it._id.toInt(), it.name, false) }
    }
    
    val mechanicsTypeList = Transformations.map(exerciseRepository.loadMechanicsTypeList()) { list ->
        list.map { FilterParameter(it._id.toInt(), it.name, false) }
    }
    
    val exerciseTypeList = Transformations.map(exerciseRepository.loadExerciseTypeList()) { list ->
        list.map { FilterParameter(it._id.toInt(), it.name, false) }
    }
    
    val equipmentList = Transformations.map(exerciseRepository.loadEquipmentList()) { list ->
        list.map { FilterParameter(it._id.toInt(), it.name, false) }
    }
    
    inner class Filter {
        private val muscleIds = mutableListOf<Int>()
        private val mechanicsTypeIds = mutableListOf<Int>()
        private val exerciseTypeIds = mutableListOf<Int>()
        private val equipmentIds = mutableListOf<Int>()
        
        fun getQuery(): SupportSQLiteQuery {
            val sb = StringBuilder("SELECT * FROM exercise WHERE main_muscle_id IN ($muscleIds)")
            if (mechanicsTypeIds.isNotEmpty()) sb.append(" AND mechanics_type_id IN ($mechanicsTypeIds)")
            if (exerciseTypeIds.isNotEmpty()) sb.append(" AND exercise_type_id IN ($exerciseTypeIds)")
            if (equipmentIds.isNotEmpty()) sb.append(" AND equipment_id IN ($equipmentIds)")
            val res = sb.toString().replace("[", "").replace("]", "")
            d("QUERY = ", res)
            return SimpleSQLiteQuery(res)
        }
        
        fun setMuscleChecked(id: Int, isChecked: Boolean) {
            if (isChecked) muscleIds.add(id) else muscleIds.remove(id)
            filter.notifyObservers()
        }
        
        fun setMechanicsTypeChecked(id: Int, isChecked: Boolean) {
            if (isChecked) mechanicsTypeIds.add(id) else mechanicsTypeIds.remove(id)
            filter.notifyObservers()
        }
    
        fun setExerciseTypeChecked(id: Int, isChecked: Boolean) {
            if (isChecked) exerciseTypeIds.add(id) else exerciseTypeIds.remove(id)
            filter.notifyObservers()
        }
    
        fun setEquipmentChecked(id: Int, isChecked: Boolean) {
            if (isChecked) equipmentIds.add(id) else equipmentIds.remove(id)
            filter.notifyObservers()
        }
    }
}
