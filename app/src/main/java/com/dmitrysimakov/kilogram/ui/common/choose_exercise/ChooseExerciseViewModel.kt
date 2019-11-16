package com.dmitrysimakov.kilogram.ui.common.choose_exercise

import androidx.lifecycle.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.dmitrysimakov.kilogram.data.local.dao.EquipmentDao
import com.dmitrysimakov.kilogram.data.local.dao.ExerciseTypeDao
import com.dmitrysimakov.kilogram.data.local.dao.MechanicsTypeDao
import com.dmitrysimakov.kilogram.data.local.dao.MuscleDao
import com.dmitrysimakov.kilogram.data.local.entity.Exercise
import com.dmitrysimakov.kilogram.data.relation.FilterParam
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.ProgramDayMuscleRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingMuscleRepository
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch
import timber.log.Timber

class ChooseExerciseViewModel (
        mechanicsTypeDao: MechanicsTypeDao,
        exerciseTypeDao: ExerciseTypeDao,
        equipmentDao: EquipmentDao,
        private val muscleDao: MuscleDao,
        private val exerciseRepo: ExerciseRepository,
        private val trainingMuscleRepo: TrainingMuscleRepository,
        private val programDayMuscleRepo: ProgramDayMuscleRepository
) : ViewModel() {
    
    private val query = MediatorLiveData<SupportSQLiteQuery>()
    
    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText
    
    val exerciseList = query.switchMap { query ->
        liveData { emit(exerciseRepo.exercises(query)) }
    }
    
    val addedToFavorite = MutableLiveData<Boolean>()
    val performedEarlier = MutableLiveData<Boolean>()
    
    private val _muscleList = MutableLiveData<List<FilterParam>>()
    val muscleList: LiveData<List<FilterParam>> = _muscleList
    
    val mechanicsTypeList = liveData { emit(mechanicsTypeDao.params()) }
    val exerciseTypeList = liveData { emit(exerciseTypeDao.params()) }
    val equipmentList = liveData { emit(equipmentDao.params()) }
    
    init {
        query.addSource(_searchText) { query.value = updateQuery() }
        query.addSource(addedToFavorite) { query.value = updateQuery() }
        query.addSource(performedEarlier) { query.value = updateQuery() }
        query.addSource(muscleList) { query.value = updateQuery() }
    }
    
    fun setSearchText(newString: String?) { _searchText.setNewValue(newString) }
    
    fun setMuscle(muscleName: String) = viewModelScope.launch {
        _muscleList.value = muscleDao.params().map { param ->
            if (param.name == muscleName) param.apply { is_active = true } else param
        }
    }
    
    fun setProgramDay(id: Long) = viewModelScope.launch {
        _muscleList.value = programDayMuscleRepo.params(id)
    }
    
    fun setTraining(id: Long) = viewModelScope.launch {
        _muscleList.value = trainingMuscleRepo.params(id)
    }
    
    private fun updateQuery(): SupportSQLiteQuery {
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
    
    fun setFavorite(exercise: Exercise, isChecked: Boolean) = viewModelScope.launch {
        exerciseRepo.setFavorite(exercise.name, isChecked)
    }
    
    fun setChecked(filterParams: LiveData<List<FilterParam>>, name: String, isChecked: Boolean) {
        filterParams.value?.find{ it.name == name }?.is_active = isChecked
        query.value = updateQuery()
    }
    
    private fun LiveData<List<FilterParam>>.getActiveParamsString() =
            value?.filter { it.is_active }?.joinToString(", ") { "'${it.name}'" } ?: ""
}
