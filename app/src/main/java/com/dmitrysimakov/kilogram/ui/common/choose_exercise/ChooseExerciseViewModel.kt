package com.dmitrysimakov.kilogram.ui.common.choose_exercise

import androidx.lifecycle.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.dmitrysimakov.kilogram.data.local.dao.EquipmentDao
import com.dmitrysimakov.kilogram.data.local.dao.ExerciseTargetDao
import com.dmitrysimakov.kilogram.data.local.entity.Exercise
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.local.relation.FilterParam
import com.dmitrysimakov.kilogram.data.repository.*
import com.dmitrysimakov.kilogram.util.Event
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch
import timber.log.Timber

class ChooseExerciseViewModel (
        equipmentDao: EquipmentDao,
        private val exerciseTargetDao: ExerciseTargetDao,
        private val exerciseRepo: ExerciseRepository,
        private val trainingMuscleRepo: TrainingMuscleRepository,
        private val programDayTargetsRepo: ProgramDayTargetsRepository,
        private val trainingExerciseRepository: TrainingExerciseRepository,
        private val programDayExerciseRepository: ProgramDayExerciseRepository
) : ViewModel() {
    
    private val query = MediatorLiveData<SupportSQLiteQuery>()
    
    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText
    
    val exerciseList = query.switchMap { query -> exerciseRepo.exercisesFlow(query).asLiveData() }
    
    val addedToFavorite = MutableLiveData<Boolean>()
    val performedEarlier = MutableLiveData<Boolean>()
    
    private val _exerciseTargets = MutableLiveData<List<FilterParam>>()
    val exerciseTargetList: LiveData<List<FilterParam>> = _exerciseTargets
    
    val equipmentList = liveData { emit(equipmentDao.params()) }
    
    private val _exerciseAddedEvent = MutableLiveData<Event<Unit>>()
    val exerciseAddedEvent: LiveData<Event<Unit>> = _exerciseAddedEvent
    
    init {
        listOf(searchText, addedToFavorite, performedEarlier, exerciseTargetList)
                .forEach { query.addSource(it) { updateQuery() } }
    }
    
    fun setSearchText(text: String?) = _searchText.setNewValue(text)
    
    fun setExerciseTarget(muscleName: String) = viewModelScope.launch {
        _exerciseTargets.value = exerciseTargetDao.params().map { param ->
            if (param.name == muscleName) param.apply { is_active = true } else param
        }
    }
    
    fun setProgramDayId(id: Long) = viewModelScope.launch {
        _exerciseTargets.value = programDayTargetsRepo.params(id)
    }
    
    fun setTrainingId(id: Long) = viewModelScope.launch {
        _exerciseTargets.value = trainingMuscleRepo.params(id)
    }
    
    private fun updateQuery() {
        val exerciseTargets = exerciseTargetList.getActiveParamsString()
        val equipments = equipmentList.getActiveParamsString()

        val sb = StringBuilder("SELECT * FROM exercise WHERE name IS NOT NULL")
        _searchText.value?.let { if (it.isNotEmpty()) sb.append(" AND name LIKE '%$it%'") }
        if (addedToFavorite.value == true) sb.append(" AND is_favorite == 1")
        if (performedEarlier.value == true) sb.append(" AND executions_cnt > 0")
        if (exerciseTargets.isNotEmpty()) sb.append(" AND target IN ($exerciseTargets)")
        if (equipments.isNotEmpty()) sb.append(" AND equipment IN ($equipments)")
        sb.append(" ORDER BY executions_cnt DESC")
        val res = sb.toString()
        Timber.d("QUERY = $res")
        query.value = SimpleSQLiteQuery(res)
    }
    
    fun setFavorite(exercise: Exercise) = viewModelScope.launch {
        exerciseRepo.setFavorite(exercise.name, !exercise.is_favorite)
    }
    
    fun setChecked(filterParams: LiveData<List<FilterParam>>, name: String, isChecked: Boolean) {
        filterParams.value?.find{ it.name == name }?.is_active = isChecked
        updateQuery()
    }
    
    fun addExerciseToTraining(exercise: Exercise, trainingId: Long, num: Int) = viewModelScope.launch {
        trainingExerciseRepository.insert(
                TrainingExercise(0, trainingId, exercise.name, num, 120)
        )
        _exerciseAddedEvent.value = Event(Unit)
    }
    
    fun addExerciseToProgramDay(exercise: Exercise, programDayId: Long, num: Int) = viewModelScope.launch {
        programDayExerciseRepository.insert(
                ProgramDayExercise(0, programDayId, exercise.name, num, 120)
        )
        _exerciseAddedEvent.value = Event(Unit)
    }
    
    private fun LiveData<List<FilterParam>>.getActiveParamsString() =
            value?.filter { it.is_active }?.joinToString(", ") { "'${it.name}'" } ?: ""
}
