package com.dmitrysimakov.kilogram.ui.home.measurements

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.common.measurements.MeasurementsAdapter
import com.dmitrysimakov.kilogram.ui.home.measurements.MeasurementsFragmentDirections.Companion.toAddMeasurementDialog
import com.dmitrysimakov.kilogram.util.*
import com.dmitrysimakov.kilogram.util.live_data.EventObserver
import kotlinx.android.synthetic.main.fragment_measurements.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class MeasurementsFragment : Fragment() {
    
    private val args: MeasurementsFragmentArgs by navArgs()
    
    private val vm: MeasurementsViewModel by viewModel()
    
    private val adapter by lazy { MeasurementsAdapter() }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_measurements, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        val date = args.date.toDate()!!
        
        setTitle(SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date))
        
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
    
        fab.setOnClickListener { navigate(toAddMeasurementDialog(date.toIsoString())) }
        
        vm.date.setNewValue(date)
        vm.measurements.observe(viewLifecycleOwner) { adapter.submitList(it) }
        vm.measurementsDeletedEvent.observe(viewLifecycleOwner, EventObserver { popBackStack() })
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.measurements, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.delete_measurements -> {
            vm.deleteMeasurements()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}