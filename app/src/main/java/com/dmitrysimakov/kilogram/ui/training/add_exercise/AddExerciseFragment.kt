package com.dmitrysimakov.kilogram.ui.training.add_exercise

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.FragmentAddExerciseBinding
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

class AddExerciseFragment: DaggerFragment() {

    private val TAG = this::class.java.simpleName

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentAddExerciseBinding
    
    private lateinit var viewModel: AddExerciseViewModel

    private lateinit var params: AddExerciseFragmentArgs

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        binding = FragmentAddExerciseBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = getViewModel(viewModelFactory)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        params = AddExerciseFragmentArgs.fromBundle(arguments!!)
        viewModel.setExercise(params.exerciseId)
        
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        activity?.fab?.hide()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.dialog, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.ok -> {
                viewModel.addExercise(params.trainingId)
                findNavController().popBackStack(R.id.trainingFragment, false)
                return true
            }
        }
        return false
    }
}