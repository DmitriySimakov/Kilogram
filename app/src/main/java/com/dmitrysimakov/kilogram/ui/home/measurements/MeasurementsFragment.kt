package com.dmitrysimakov.kilogram.ui.home.measurements

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.common.measurements.MeasurementsAdapter
import com.dmitrysimakov.kilogram.ui.home.measurements.MeasurementsFragmentDirections.Companion.toAddMeasurementDialog
import com.dmitrysimakov.kilogram.util.*
import kotlinx.android.synthetic.main.fragment_measurements.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class MeasurementsFragment : Fragment() {
    
    private val args: MeasurementsFragmentArgs by navArgs()
    
    private val vm: MeasurementsViewModel by viewModel()
    
    private val adapter by lazy { MeasurementsAdapter { navigate(toAddMeasurementDialog(it.date!!.toIsoString())) }}
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_measurements, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
    
        val date = args.date.toDate()!!
        setTitle(SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date))
        
        vm.date.setNewValue(date)
        vm.measurements.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }
    
}