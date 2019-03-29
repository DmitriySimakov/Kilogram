package com.dmitrysimakov.kilogram.ui.common.choose_exercise

import android.util.Log.d
import androidx.lifecycle.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.dmitrysimakov.kilogram.data.entity.Exercise
import com.dmitrysimakov.kilogram.data.relation.FilterParam
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.MuscleRepository
import javax.inject.Inject

class ChooseExerciseViewModel @Inject constructor(
        private val exerciseRepository: ExerciseRepository
) : ViewModel() {
    
    private val query = MediatorLiveData<SupportSQLiteQuery>()
    
    val addedToFavorite = MutableLiveData(false)
    val performedEarlier = MutableLiveData(false)
    
    val muscleList = exerciseRepository.loadMuscleParams()
    val mechanicsTypeList = exerciseRepository.loadMechanicsTypeParams()
    val exerciseTypeList = exerciseRepository.loadExerciseTypeParams()
    val equipmentList = exerciseRepository.loadEquipmentParams()
    
    init {
        query.addSource(addedToFavorite) { query.value = getQuery() }
        query.addSource(performedEarlier) { query.value = getQuery() }
    }
    
    private fun getQuery(): SupportSQLiteQuery {
        val muscleIds = muscleList.getActiveIds()
        val mechanicsTypeIds = mechanicsTypeList.getActiveIds()
        val exerciseTypeIds = exerciseTypeList.getActiveIds()
        val equipmentIds = equipmentList.getActiveIds()
        
        val sb = StringBuilder("SELECT * FROM exercise WHERE _id != 0")
        if (addedToFavorite.value == true) sb.append(" AND is_favorite == 1")
        if (performedEarlier.value == true) sb.append(" AND executions_cnt > 0")
        if (muscleIds.isNotEmpty()) sb.append(" AND main_muscle_id IN ($muscleIds)")
        if (mechanicsTypeIds.isNotEmpty()) sb.append(" AND mechanics_type_id IN ($mechanicsTypeIds)")
        if (exerciseTypeIds.isNotEmpty()) sb.append(" AND exercise_type_id IN ($exerciseTypeIds)")
        if (equipmentIds.isNotEmpty()) sb.append(" AND equipment_id IN ($equipmentIds)")
        val res = sb.toString()
        d("QUERY = ", res)
        return SimpleSQLiteQuery(res)
    }
    
    val exerciseList = Transformations.switchMap(query) {
        exerciseRepository.loadExerciseList(it)
    }
    
    fun setFavorite(exercise: Exercise, isChecked: Boolean) {
        val updated = exercise.copy()
        updated.is_favorite = isChecked
        exerciseRepository.updateExercise(updated)
    }
    
    fun setChecked(data: LiveData<List<FilterParam>>, id: Long, isChecked: Boolean) {
        data.value?.find{ it._id == id }?.isActive = isChecked
        query.value = getQuery()
    }
    
    private fun LiveData<List<FilterParam>>.getActiveIds(): String {
        val sb = StringBuilder("")
        value?.let {
            var separator = ""
            for (item in it) {
                if (item.isActive) {
                    sb.append(separator)
                    separator = ", "
                    sb.append(item._id)
                }
            }
        }
        return sb.toString()
    }
}
