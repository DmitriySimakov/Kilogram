package com.dmitrysimakov.kilogram.ui.training.addSet

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.DialogAddSetBinding
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerAppCompatDialogFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

class AddSetDialog : DaggerAppCompatDialogFragment() {

    private lateinit var viewModel: AddSetViewModel

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: DialogAddSetBinding

    private lateinit var params: AddSetDialogArgs

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
        viewModel = getViewModel(viewModelFactory)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        params = AddSetDialogArgs.fromBundle(arguments!!)

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
                val weight = binding.weightEt.text.toString().toInt()
                val reps = binding.repsEt.text.toString().toInt()
                val time = binding.timeEt.text.toString().toInt()
                val distance = binding.distanceEt.text.toString().toInt()
                viewModel.addSet(params.trainingExerciseId, weight, reps, time, distance)
                findNavController().popBackStack()
                return true
            }
        }
        return false
    }
}
