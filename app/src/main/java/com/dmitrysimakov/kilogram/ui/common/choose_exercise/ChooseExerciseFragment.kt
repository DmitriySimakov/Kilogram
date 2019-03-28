package com.dmitrysimakov.kilogram.ui.common.choose_exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.entity.Equipment
import com.dmitrysimakov.kilogram.data.entity.ExerciseType
import com.dmitrysimakov.kilogram.data.entity.MechanicsType
import com.dmitrysimakov.kilogram.data.entity.Muscle
import com.dmitrysimakov.kilogram.util.*
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_choose_exercise.*
import javax.inject.Inject

abstract class ChooseExerciseFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var executors: AppExecutors

    protected val viewModel by lazy { getViewModel<ChooseExerciseViewModel>(viewModelFactory) }

    protected val exerciseAdapter by lazy { ExerciseListAdapter(executors) }

    protected lateinit var muscleAdapter: ChipGroupFilterAdapter
    protected lateinit var mechanicsTypeAdapter: ChipGroupFilterAdapter
    protected lateinit var exerciseTypeAdapter: ChipGroupFilterAdapter
    protected lateinit var equipmentAdapter: ChipGroupFilterAdapter
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_exercise, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = exerciseAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
    
        muscleAdapter = ChipGroupFilterAdapter(musclesCG) { id, isChecked ->
            viewModel.filter.value?.setMuscleChecked(id, isChecked)
        }
        mechanicsTypeAdapter = ChipGroupFilterAdapter(mechanicsTypeCG) { id, isChecked ->
            viewModel.filter.value?.setMechanicsTypeChecked(id, isChecked)
        }
        exerciseTypeAdapter = ChipGroupFilterAdapter(exerciseTypeCG) { id, isChecked ->
            viewModel.filter.value?.setExerciseTypeChecked(id, isChecked)
        }
        equipmentAdapter = ChipGroupFilterAdapter(equipmentCG) { id, isChecked ->
            viewModel.filter.value?.setEquipmentChecked(id, isChecked)
        }
        
        viewModel.exerciseList.observe(this, Observer { exerciseAdapter.submitList(it) })
        viewModel.muscleList.observe(this, Observer { muscleAdapter.submitList(it) })
        viewModel.mechanicsTypeList.observe(this, Observer { mechanicsTypeAdapter.submitList(it) })
        viewModel.exerciseTypeList.observe(this, Observer { exerciseTypeAdapter.submitList(it) })
        viewModel.equipmentList.observe(this, Observer { equipmentAdapter.submitList(it) })
        
        activity?.fab?.hide()
    }
}