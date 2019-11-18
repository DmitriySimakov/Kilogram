package com.dmitrysimakov.kilogram.ui.diaries.measurements.add_measurement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import kotlinx.android.synthetic.main.fragment_measurements.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MeasurementsFragment : Fragment() {
    
    private val vm: MeasurementsViewModel by viewModel()

    private val adapter by lazy { MeasurementsAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_measurements, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm.measurementList.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })

        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
    }

}
