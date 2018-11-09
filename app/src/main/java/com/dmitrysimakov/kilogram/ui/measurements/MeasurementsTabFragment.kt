package com.dmitrysimakov.kilogram.ui.measurements

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.main.MainFragment
import com.dmitrysimakov.kilogram.ui.measurements.addMeasurement.MeasurementsFragment
import kotlinx.android.synthetic.main.fragment_measurements_tab.*

class MeasurementsTabFragment : Fragment() {

    private val CALCULATOR_POS = 0
    private val ADD_MEASUREMENT_POS = 1
    private val HISTORY_POS = 2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_measurements_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.adapter = MeasurementsPagerAdapter(childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        viewPager.currentItem = ADD_MEASUREMENT_POS;
    }

    inner class MeasurementsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int) = when (position) {
            CALCULATOR_POS ->  MeasurementsFragment()
            ADD_MEASUREMENT_POS ->  MeasurementsFragment()
            HISTORY_POS ->  MeasurementsFragment()
            else -> null
        }

        override fun getCount() = 3

        override fun getPageTitle(position: Int) = when (position) {
            CALCULATOR_POS ->  getString(R.string.calculator)
            ADD_MEASUREMENT_POS ->  getString(R.string.add_measurement)
            HISTORY_POS ->  getString(R.string.measurement_history)
            else -> null
        }
    }
}
