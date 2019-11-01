package com.dmitrysimakov.kilogram.ui.common.choose_exercise

import androidx.lifecycle.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.dmitrysimakov.kilogram.data.local.entity.Exercise
import com.dmitrysimakov.kilogram.data.relation.FilterParam
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.ProgramDayMuscleRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingMuscleRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch
import timber.log.Timber

class ChooseExerciseViewModel (
        private val exerciseRepo: ExerciseRepository,
        private val trainingMuscleRepo: TrainingMuscleRepository,
        private val programDayMuscleRepo: ProgramDayMuscleRepository
) : ViewModel() {
    
    private val query = MediatorLiveData<SupportSQLiteQuery>()
    
    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText
    fun setSearchText(newString: String?) { _searchText.setNewValue(newString) }
    
    val addedToFavorite = MutableLiveData<Boolean>()
    val performedEarlier = MutableLiveData<Boolean>()
    
    private val _muscleName = MutableLiveData<String>()
    fun setMuscle(name: String) { _muscleName.setNewValue(name) }
    
    private val exercisesMuscles = _muscleName.switchMap { muscleName ->
        exerciseRepo.loadMuscleParams().map { list ->
            list.find { it.name == muscleName }?.is_active = true
            list
        }
    }
    
    private val _programDayId = MutableLiveData<Long>()
    fun setProgramDay(id: Long) { _programDayId.setNewValue(id) }
    
    private val programMuscles = _programDayId.switchMap {
        programDayMuscleRepo.loadParams(it)
    }
    
    private val _trainingId = MutableLiveData<Long>()
    fun setTraining(id: Long) { _trainingId.setNewValue(id) }
    
    private val trainingMuscles = _trainingId.switchMap {
        trainingMuscleRepo.loadParams(it)
    }
    
    val muscleList = MediatorLiveData<List<FilterParam>>()
    val mechanicsTypeList = exerciseRepo.loadMechanicsTypeParams()
    val exerciseTypeList = exerciseRepo.loadExerciseTypeParams()
    val equipmentList = exerciseRepo.loadEquipmentParams()
    
    init {
        muscleList.addSource(exercisesMuscles) { muscleList.value = it }
        muscleList.addSource(programMuscles) { muscleList.value = it }
        muscleList.addSource(trainingMuscles) { muscleList.value = it }
        
        query.addSource(_searchText) { query.value = getQuery() }
        query.addSource(addedToFavorite) { query.value = getQuery() }
        query.addSource(performedEarlier) { query.value = getQuery() }
        query.addSource(muscleList) { query.value = getQuery() }
    }
    
    private fun getQuery(): SupportSQLiteQuery {
        val muscles = muscleList.getActiveParamsString()
        val mechanicsTypes = mechanicsTypeList.getActiveParamsString()
        val exerciseTypes = exerciseTypeList.getActiveParamsString()
        val equipments = equipmentList.getActiveParamsString()
        
        val sb = StringBuilder("SELECT * FROM exercise WHERE name IS NOT NULL")
        _searchText.value?.let { if (it.isNotEmpty()) sb.append(" AND name LIKE '%$it%'") }
        if (addedToFavorite.value == true) sb.append(" AND is_favorite == 1")
        if (performedEarlier.value == true) sb.append(" AND executions_cnt > 0")
        if (muscles.isNotEmpty()) sb.append(" AND main_muscle IN ($muscles)")
        if (mechanicsTypes.isNotEmpty()) sb.append(" AND mechanics_type IN ($mechanicsTypes)")
        if (exerciseTypes.isNotEmpty()) sb.append(" AND exercise_type IN ($exerciseTypes)")
        if (equipments.isNotEmpty()) sb.append(" AND equipment IN ($equipments)")
        sb.append(" ORDER BY executions_cnt DESC")
        val res = sb.toString()
        Timber.d("QUERY = $res")
        return SimpleSQLiteQuery(res)
    }
    
    val exerciseList = query.switchMap {
        exerciseRepo.loadExerciseList(it)
    }
    
    fun setFavorite(exercise: Exercise, isChecked: Boolean) { viewModelScope.launch {
        exerciseRepo.setFavorite(exercise.name, isChecked)
    }}
    
    fun setChecked(data: LiveData<List<FilterParam>>, name: String, isChecked: Boolean) { viewModelScope.launch {
        data.value?.find{ it.name == name }?.is_active = isChecked
        query.value = getQuery()
    }}
    
    private fun LiveData<List<FilterParam>>.getActiveParamsString() =
            value?.filter { it.is_active }?.joinToString(", ") { "'${it.name}'" } ?: ""
}
