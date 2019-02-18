package com.dmitrysimakov.kilogram.ui.common.exercises

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.MainViewModel
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_exercises.*
import javax.inject.Inject

class ExercisesFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var executors: AppExecutors

    private lateinit var viewModel: ExercisesViewModel
    
    private lateinit var params: ExercisesFragmentArgs

    lateinit var adapter: TrainingExerciseListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = getViewModel(viewModelFactory)
        params = ExercisesFragmentArgs.fromBundle(arguments!!)
        viewModel.setTraining(params.trainingId)

        adapter = TrainingExerciseListAdapter(executors) {
            findNavController().navigate(ExercisesFragmentDirections.toTrainingSetsFragment(it._id, it.exercise_id))
        }
        exercises_rv.adapter = adapter
        viewModel.exercises.observe(this, Observer { adapter.submitList(it) })
        viewModel.training.observe(this, Observer {  })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val exercise = adapter.get(viewHolder.adapterPosition)
                viewModel.deleteExercise(exercise)
            }
        }).attachToRecyclerView(exercises_rv)

        activity?.fab?.show()
        activity?.fab?.setOnClickListener{
            findNavController().navigate(ExercisesFragmentDirections.toChooseMuscleFragment(params.programDayId, params.trainingId))
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        if (params.trainingIsRunning) {
            inflater?.inflate(R.menu.active_training, menu);
        }
    }
    
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.finish_session -> {
                viewModel.finishSession()
                val mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
                mainViewModel.onTrainingSessionFinished()
                findNavController().popBackStack()
                return true
            }
        }
        return false
    }
}
