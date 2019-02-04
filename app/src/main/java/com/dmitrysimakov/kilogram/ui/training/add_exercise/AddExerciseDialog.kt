package com.dmitrysimakov.kilogram.ui.training.add_exercise

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.DialogAddExerciseBinding
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerAppCompatDialogFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

class AddExerciseDialog: DaggerAppCompatDialogFragment() {

    private val TAG = this::class.java.simpleName

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: DialogAddExerciseBinding

    private lateinit var viewModel: AddExerciseViewModel

    private lateinit var params: AddExerciseDialogArgs

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        binding = DialogAddExerciseBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity!!)
                .setView(binding.root)
                .setTitle("Добавить упражнение")
                .setPositiveButton("Добавить", null)
                .setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }
                .create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = getViewModel(viewModelFactory)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        params = AddExerciseDialogArgs.fromBundle(arguments!!)

        binding.chooseExerciseBtn.setOnClickListener{
            AddExerciseDialogDirections.toChooseMuscleFragment(params.trainingId)
        }
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
                viewModel.addExercise(params.exerciseId ,params.trainingId)
                findNavController().popBackStack()
                return true
            }
        }
        return false
    }
}