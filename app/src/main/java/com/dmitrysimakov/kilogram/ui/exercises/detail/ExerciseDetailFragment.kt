package com.dmitrysimakov.kilogram.ui.exercises.detail

import android.os.Bundle
import android.util.Log.d
import android.view.*
import android.widget.ToggleButton
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.FragmentExerciseDetailBinding
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

class ExerciseDetailFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    private val viewModel by lazy { getViewModel<ExerciseDetailViewModel>(viewModelFactory) }
    
    private lateinit var binding: FragmentExerciseDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        binding = FragmentExerciseDetailBinding.inflate(inflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        val params = ExerciseDetailFragmentArgs.fromBundle(arguments!!)
        viewModel.setExercise(params.exerciseId)
        
        activity?.fab?.hide()
    }
    
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.exercise_detail, menu)
        menu?.let {
            viewModel.exercise.observe(this, Observer { exercise ->
                val item = menu.findItem(R.id.addToFavorite)
                item.isChecked = exercise.is_favorite
                updateFavoriteButton(item)
            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
    
    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.addToFavorite -> {
            item.isChecked = !item.isChecked
            viewModel.setFavorite(item.isChecked)
            updateFavoriteButton(item)
            true
        }
        else -> false
    }
    
    private fun updateFavoriteButton(item: MenuItem) {
        if (item.isChecked) {
            item.setIcon(R.drawable.baseline_favorite_24)
        } else {
            item.setIcon(R.drawable.baseline_favorite_border_24)
        }
    }
}
