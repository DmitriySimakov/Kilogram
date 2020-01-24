package com.dmitrysimakov.kilogram.ui.common.choose_exercise

import android.app.Application
import androidx.lifecycle.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import androidx.work.*
import com.dmitrysimakov.kilogram.data.local.dao.EquipmentDao
import com.dmitrysimakov.kilogram.data.local.dao.ExerciseTargetDao
import com.dmitrysimakov.kilogram.data.local.entity.Exercise
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.local.relation.FilterParam
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.ProgramDayExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.util.Event
import com.dmitrysimakov.kilogram.util.setNewValue
import com.dmitrysimakov.kilogram.workers.UploadTrainingExerciseWorker
import kotlinx.coroutines.launch
import timber.log.Timber

class ChooseExerciseViewModel (
        app: Application,
        equipmentDao: EquipmentDao,
        private val exerciseTargetDao: ExerciseTargetDao,
        private val exerciseRepo: ExerciseRepository,
        private val trainingExerciseRepository: TrainingExerciseRepository,
        private val programDayExerciseRepository: ProgramDayExerciseRepository
) : AndroidViewModel(app) {
    
    private val query = MediatorLiveData<SupportSQLiteQuery>()
    
    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText
    
    val exerciseList = query.switchMap { query -> exerciseRepo.exercisesFlow(query).asLiveData() }
    
    val addedToFavorite = MutableLiveData<Boolean>()
    val performedEarlier = MutableLiveData<Boolean>()
    val compound = MutableLiveData<Boolean>()
    val isolated = MutableLiveData<Boolean>()
    
    val exerciseTargetList = liveData { emit(exerciseTargetDao.params()) }
    val equipmentList = liveData { emit(equipmentDao.params()) }
    
    private val _exerciseAddedEvent = MutableLiveData<Event<Unit>>()
    val exerciseAddedEvent: LiveData<Event<Unit>> = _exerciseAddedEvent
    
    init {
        listOf(searchText, addedToFavorite, performedEarlier, isolated, exerciseTargetList)
                .forEach { query.addSource(it) { updateQuery() } }
    }
    
    fun setSearchText(text: String?) { _searchText.setNewValue(text) }
    
    private fun updateQuery() {
        val exerciseTargets = exerciseTargetList.getActiveParamsString()
        val equipments = equipmentList.getActiveParamsString()

        val sb = StringBuilder("SELECT * FROM exercise WHERE name IS NOT NULL")
        _searchText.value?.let { if (it.trim().isNotEmpty()) sb.append(" AND name LIKE '%$it%'") }
        if (addedToFavorite.value == true) sb.append(" AND isFavorite == 1")
        if (performedEarlier.value == true) sb.append(" AND executionsCount > 0")
        if (compound.value == true && isolated.value == false) sb.append(" AND isIsolated == 0")
        if (compound.value == false && isolated.value == true) sb.append(" AND isIsolated == 1")
        if (exerciseTargets.isNotEmpty()) sb.append(" AND target IN ($exerciseTargets)")
        if (equipments.isNotEmpty()) sb.append(" AND equipment IN ($equipments)")
        sb.append(" ORDER BY executionsCount DESC")
        val res = sb.toString()
        Timber.d("QUERY = $res")
        query.value = SimpleSQLiteQuery(res)
    }
    
    fun setFavorite(exercise: Exercise) = viewModelScope.launch {
        exerciseRepo.setFavorite(exercise.name, !exercise.isFavorite)
    }
    
    fun setChecked(filterParams: LiveData<List<FilterParam>>, name: String, isChecked: Boolean) {
        filterParams.value?.find{ it.name == name }?.isActive = isChecked
        updateQuery()
    }
    
    fun addExerciseToTraining(exercise: Exercise, trainingId: Long, num: Int) = viewModelScope.launch {
        val trainingExercise = TrainingExercise(0, trainingId, exercise.name, num, 120)
        val id = trainingExerciseRepository.insert(trainingExercise)
        uploadTrainingExercise(id)
        
        _exerciseAddedEvent.value = Event(Unit)
    }
    
    private fun uploadTrainingExercise(id: Long) {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val data = Data.Builder().putLong("id", id).build()
        
        val uploadTrainingExerciseRequest = OneTimeWorkRequest.Builder(UploadTrainingExerciseWorker::class.java)
                .setConstraints(constraints)
                .setInputData(data)
                .build()
        
        WorkManager.getInstance(getApplication()).enqueue(uploadTrainingExerciseRequest)
    }
    
    fun addExerciseToProgramDay(exercise: Exercise, programDayId: Long, num: Int) = viewModelScope.launch {
        programDayExerciseRepository.insert(
                ProgramDayExercise(0, programDayId, exercise.name, num, 120)
        )
        _exerciseAddedEvent.value = Event(Unit)
    }
    
    private fun LiveData<List<FilterParam>>.getActiveParamsString() =
            value?.filter { it.isActive }?.joinToString(", ") { "'${it.name}'" } ?: ""
}
