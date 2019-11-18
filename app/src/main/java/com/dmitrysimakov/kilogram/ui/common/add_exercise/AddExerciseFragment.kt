package com.dmitrysimakov.kilogram.ui.common.add_exercise

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.FragmentAddExerciseBinding
import com.dmitrysimakov.kilogram.util.hideKeyboard
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_exercise.*
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class AddExerciseFragment: Fragment() {
    
    protected lateinit var binding: FragmentAddExerciseBinding
    
    protected val vm: AddExerciseViewModel by viewModel()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddExerciseBinding.inflate(inflater)
        binding.viewModel = vm
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
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dialog, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.ok -> {
            addExercise()
            true
        }
        else -> false
    }
    
    abstract fun addExercise()
}