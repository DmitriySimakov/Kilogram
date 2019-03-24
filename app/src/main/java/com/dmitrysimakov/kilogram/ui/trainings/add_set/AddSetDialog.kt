package com.dmitrysimakov.kilogram.ui.trainings.add_set

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.DialogAddSetBinding
import com.dmitrysimakov.kilogram.util.getIntValue
import com.dmitrysimakov.kilogram.util.getViewModel
import com.dmitrysimakov.kilogram.util.hideKeyboard
import com.dmitrysimakov.kilogram.util.runCircularRevealAnimation
import dagger.android.support.DaggerAppCompatDialogFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

class AddSetDialog : DaggerAppCompatDialogFragment() {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { getViewModel<AddSetViewModel>(viewModelFactory) }
    private val mainViewModel by lazy { getViewModel(activity!!, viewModelFactory) }

    private lateinit var binding: DialogAddSetBinding

    private val params by lazy { AddSetDialogArgs.fromBundle(arguments!!) }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        binding = DialogAddSetBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity!!)
                .setView(binding.root)
                .setTitle("Добавить подход")
                .setPositiveButton("Добавить", null)
                .setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }
                .create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.setTrainingExercise(params.trainingExerciseId)
        viewModel.setSet(params.setId)
        viewModel.set.observe(this, Observer {  })
        viewModel.trainingExercise.observe(this, Observer {  })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (dialog == null) {
            activity?.toolbar?.setNavigationIcon(R.drawable.baseline_close_white_24)
            setHasOptionsMenu(true)
        }

        activity?.fab?.hide()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.dialog, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.ok -> {
                hideKeyboard()
                
                viewModel.set.value?.run {
                    weight = binding.weightET.getIntValue()
                    reps = binding.repsET.getIntValue()
                    time = binding.timeET.getIntValue()
                    distance = binding.distanceET.getIntValue()
                }
                
                if (params.setId == 0L) {
                    viewModel.addSet(mainViewModel.elapsedSessionTime.value ?: 0)
                    mainViewModel.onSetCompleted(viewModel.trainingExercise.value?.rest ?: 0)
                } else {
                    viewModel.updateSet()
                }
                findNavController().popBackStack()
                return true
            }
        }
        return false
    }
}