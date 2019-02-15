package com.dmitrysimakov.kilogram.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exerciseBtn.setOnClickListener { findNavController().navigate(
                MainFragmentDirections.toChooseMuscleFragment()) }

        programsBtn.setOnClickListener { findNavController().navigate(
                MainFragmentDirections.toProgramsFragment())
        }
        
        trainingsBtn.setOnClickListener { findNavController().navigate(
                MainFragmentDirections.toTrainingsFragment()) }

        measuresBtn.setOnClickListener { findNavController().navigate(
                MainFragmentDirections.toMeasurementsTabFragment()) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.fab?.hide()
    }
}
