package com.dmitrysimakov.kilogram.ui.common.add_exercise

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.FragmentAddExerciseBinding
import com.dmitrysimakov.kilogram.util.getViewModel
import com.dmitrysimakov.kilogram.util.hideKeyboard
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_add_exercise.*
import javax.inject.Inject

abstract class AddExerciseFragment: DaggerFragment() {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var binding: FragmentAddExerciseBinding
    
    protected val viewModel by lazy { getViewModel<AddExerciseViewModel>(viewModelFactory) }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddExerciseBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        
        executionStrategyET.setOnFocusChangeListener { _, hasFocus ->
            executionStrategyTIL.isCounterEnabled = hasFocus
        }
        
        activity?.fab?.hide()
    }
    
    override fun onStop() {
        hideKeyboard()
        super.onStop()
    }
    
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.dialog, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    
    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.ok -> {
            addExercise()
            viewModel.updateMeasures()
            popBack()
            true
        }
        else -> false
    }
    
    abstract fun addExercise()
    
    abstract fun popBack()
}