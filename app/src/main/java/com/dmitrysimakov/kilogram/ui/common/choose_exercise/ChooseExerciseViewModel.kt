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
import timber.log.Timber

class ChooseExerciseViewModel (
        private val exerciseRepo: ExerciseRepository,
        private val trainingMuscleRepo: TrainingMuscleRepository,
        private val programDayMuscleRepo: ProgramDayMuscleRepository
) : ViewModel() {
    
    private val query = MediatorLiveData<SupportSQLiteQuery>()
    
    fun setSearchText(newString: String?) { _searchText.setNewValue(newString) }
    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String>
        get() = _searchText
    
    val addedToFavorite = MutableLiveData<Boolean>()
    val performedEarlier = MutableLiveData<Boolean>()
    
    fun setMuscle(id: Long) { muscleId.setNewValue(id) }
    private val muscleId = MutableLiveData<Long>()
    private val exercisesMuscles = Transformations.switchMap(muscleId) {
        Transformations.map(exerciseRepo.loadMuscleParams()) { list ->
            list[muscleId.value!!.toInt() - 1].is_active = true
            list
        }
    }
    
    fun setProgramDay(id: Long) { programDayId.setNewValue(id) }
    private val programDayId = MutableLiveData<Long>()
    private val programMuscles = Transformations.switchMap(programDayId)  {
        programDayMuscleRepo.loadParams(it)
    }
    
    fun setTraining(id: Long) { trainingId.setNewValue(id) }
    private val trainingId = MutableLiveData<Long>()
    private val trainingMuscles = Transformations.switchMap(trainingId) {
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
        val muscleIds = muscleList.getActiveIds()
        val mechanicsTypeIds = mechanicsTypeList.getActiveIds()
        val exerciseTypeIds = exerciseTypeList.getActiveIds()
        val equipmentIds = equipmentList.getActiveIds()
        
        val sb = StringBuilder("SELECT * FROM exercise WHERE _id != 0")
        _searchText.value?.let { if (it.isNotEmpty()) sb.append(" AND name LIKE '%$it%'") }
        if (addedToFavorite.value == true) sb.append(" AND is_favorite == 1")
        if (performedEarlier.value == true) sb.append(" AND executions_cnt > 0")
        if (muscleIds.isNotEmpty()) sb.append(" AND main_muscle_id IN ($muscleIds)")
        if (mechanicsTypeIds.isNotEmpty()) sb.append(" AND mechanics_type_id IN ($mechanicsTypeIds)")
        if (exerciseTypeIds.isNotEmpty()) sb.append(" AND exercise_type_id IN ($exerciseTypeIds)")
        if (equipmentIds.isNotEmpty()) sb.append(" AND equipment_id IN ($equipmentIds)")
        sb.append(" ORDER BY executions_cnt DESC")
        val res = sb.toString()
        Timber.d("QUERY = $res")
        return SimpleSQLiteQuery(res)
    }
    
    val exerciseList = Transformations.switchMap(query) {
        exerciseRepo.loadExerciseList(it)
    }
    
    fun setFavorite(exercise: Exercise, isChecked: Boolean) {
        exerciseRepo.setFavorite(exercise._id, isChecked)
    }
    
    fun setChecked(data: LiveData<List<FilterParam>>, id: Long, isChecked: Boolean) {
        data.value?.find{ it._id == id }?.is_active = isChecked
        query.value = getQuery()
    }
    
    private fun LiveData<List<FilterParam>>.getActiveIds(): String {
        val sb = StringBuilder("")
        value?.let {
            var separator = ""
            for (item in it) {
                if (item.is_active) {
                    sb.append(separator)
                    separator = ", "
                    sb.append(item._id)
                }
            }
        }
        return sb.toString()
    }
}
