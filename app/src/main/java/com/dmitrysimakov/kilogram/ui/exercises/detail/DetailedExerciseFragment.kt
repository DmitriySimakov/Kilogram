package com.dmitrysimakov.kilogram.ui.exercises.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.FragmentDetailedExerciseBinding
import kotlinx.android.synthetic.main.app_bar_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailedExerciseFragment : Fragment() {
    
    private val args: DetailedExerciseFragmentArgs by navArgs()
    
    private val vm: DetailedExerciseViewModel by viewModel()
    
    private lateinit var binding: FragmentDetailedExerciseBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        binding = FragmentDetailedExerciseBinding.inflate(inflater)
        binding.vm = vm
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        vm.setExerciseName(args.exercise)
        
        activity?.fab?.hide()
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.exercise_detail, menu)
        vm.exercise.observe(viewLifecycleOwner, Observer { exercise ->
            val item = menu.findItem(R.id.addToFavorite)
            item.isChecked = exercise.is_favorite
            updateFavoriteButton(item)
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
    
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.addToFavorite -> {
            item.isChecked = !item.isChecked
            vm.setFavorite(item.isChecked)
            updateFavoriteButton(item)
            true
        }
        else -> false
    }
    
    private fun updateFavoriteButton(item: MenuItem) {
        if (item.isChecked) {
            item.setIcon(R.drawable.ic_favorite_24dp)
        } else {
            item.setIcon(R.drawable.ic_favorite_border_24dp)
        }
    }
}
