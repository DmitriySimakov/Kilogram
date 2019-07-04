package com.dmitrysimakov.kilogram.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : DaggerFragment() {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    private val mainViewModel by lazy { getViewModel(activity!!, viewModelFactory) }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        exerciseBtn.setOnClickListener { navController.navigate(MainFragmentDirections.toExercises()) }
        programsBtn.setOnClickListener { navController.navigate(MainFragmentDirections.toPrograms()) }
        trainingsBtn.setOnClickListener { navController.navigate(MainFragmentDirections.toTrainings()) }
        measurementsBtn.setOnClickListener { navController.navigate(MainFragmentDirections.toMeasurements()) }
        messagesBtn.setOnClickListener { navController.navigate(MainFragmentDirections.toMessages()) }
        
        mainViewModel.user.observe(this, Observer { messagesBtn.isEnabled = it != null })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.fab?.hide()
    }
}
