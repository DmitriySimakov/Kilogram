package com.dmitrysimakov.kilogram.ui.home.measurements.measurements_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.home.measurements.measurements_history.MeasurementsHistoryFragmentDirections.Companion.toMeasurementsFragment
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.toIsoString
import kotlinx.android.synthetic.main.fragment_measurements_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MeasurementsHistoryFragment : Fragment() {
    
    private val vm: MeasurementsHistoryViewModel by viewModel()
    
    private val adapter by lazy { MeasurementDateAdapter{ date ->
        navigate(toMeasurementsFragment(date.toIsoString()))
    }}
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_measurements_history, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
        
        vm.measurementDates.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }
}
