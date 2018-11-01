package com.dmitrysimakov.kilogram.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
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

        trainingsBtn.setOnClickListener { findNavController().navigate(
                MainFragmentDirections.toTrainingsFragment()) }
    }
}
