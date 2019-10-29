package com.dmitrysimakov.kilogram.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment() {
    
    private val sharedVM: SharedViewModel by sharedViewModel()
    
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
        messagesBtn.setOnClickListener {  navController.navigate(MainFragmentDirections.toMessages()) }
        
        sharedVM.user.observe(viewLifecycleOwner, Observer { messagesBtn.visibility = if(it != null) View.VISIBLE else View.GONE })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.fab?.hide()
    }
}
