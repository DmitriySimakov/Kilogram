package com.dmitrysimakov.kilogram.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.dmitrysimakov.kilogram.databinding.FragmentSearchBinding
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.feed.PostsAdapter
import com.dmitrysimakov.kilogram.ui.search.SearchFragmentDirections.Companion.toExerciseDetailFragment
import com.dmitrysimakov.kilogram.ui.search.SearchFragmentDirections.Companion.toExercisesFragment
import com.dmitrysimakov.kilogram.ui.search.SearchFragmentDirections.Companion.toPeopleFragment
import com.dmitrysimakov.kilogram.ui.search.SearchFragmentDirections.Companion.toPersonPageFragment
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.setNewValue
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    
    private lateinit var binding: FragmentSearchBinding
    
    private val peopleAdapter by lazy { UsersHorizontalAdapter { navigate(toPersonPageFragment(it.id)) } }
    private val exercisesAdapter by lazy { ExercisesHorizontalAdapter { navigate(toExerciseDetailFragment(it.name)) } }
    private val postsAdapter by lazy { PostsAdapter(sharedVM, {}, {}) }
    
    private val vm: SearchViewModel by viewModel()
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        binding.vm = vm
        binding.lifecycleOwner = this
        binding.peoplesLabel.setOnClickListener { navigate(toPeopleFragment()) }
        binding.peoplesRV.adapter = peopleAdapter
        binding.exercisesLabel.setOnClickListener { navigate(toExercisesFragment()) }
        binding.exercisesRV.adapter = exercisesAdapter
        binding.postsRV.adapter = postsAdapter
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        sharedVM.user.observe(viewLifecycleOwner) { vm.user.setNewValue(it) }
        vm.people.observe(viewLifecycleOwner) { peopleAdapter.submitList(it) }
        vm.exercises.observe(viewLifecycleOwner) { exercisesAdapter.submitList(it) }
        vm.posts.observe(viewLifecycleOwner) { postsAdapter.submitList(it) }
    }
}
