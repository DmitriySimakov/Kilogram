package com.dmitrysimakov.kilogram.ui.common.person_page

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dmitrysimakov.kilogram.databinding.FragmentPersonPageBinding
import com.dmitrysimakov.kilogram.ui.common.CalendarDayBinder
import com.dmitrysimakov.kilogram.ui.common.CalendarMonthBinder
import com.dmitrysimakov.kilogram.ui.common.measurements.MeasurementsAdapter
import com.dmitrysimakov.kilogram.ui.home.HomeFragmentDirections.Companion.toPhotoFragment
import com.dmitrysimakov.kilogram.ui.home.photos.PhotosAdapter
import com.dmitrysimakov.kilogram.util.navigate
import com.kizitonwose.calendarview.utils.next
import com.kizitonwose.calendarview.utils.previous
import kotlinx.android.synthetic.main.view_calendar.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.WeekFields
import java.util.*

class PersonPageFragment : Fragment() {
    
    private val vm: PersonPageViewModel by viewModel()
    
    private lateinit var binding: FragmentPersonPageBinding
    
    private val calendarMonthBinder by lazy { CalendarMonthBinder() }
    private val calendarDayBinder by lazy { CalendarDayBinder() }
    private val photosAdapter by lazy { PhotosAdapter { navigate(toPhotoFragment(it.uri)) } }
    private val measurementsAdapter by lazy { MeasurementsAdapter() }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPersonPageBinding.inflate(inflater)
        binding.vm = vm
        binding.photosRV.adapter = photosAdapter
        binding.measurementsRV.adapter = measurementsAdapter
        setupCalendar()
        binding.lifecycleOwner = this
        return binding.root
    }
    
    @SuppressLint("DefaultLocale")
    private fun setupCalendar() {
        binding.apply {
            calendarView.apply {
                dayBinder = calendarDayBinder
                monthHeaderBinder = calendarMonthBinder
                monthScrollListener = { month ->
                    val formatter = DateTimeFormatter.ofPattern("LLLL yyyy г.")
                    dateLabel.text = month.yearMonth.format(formatter).capitalize()
                }
        
                val currentMonth = YearMonth.now()
                val firstMonth = YearMonth.of(2000, 1)
                val lastMonth = currentMonth.plusMonths(1)
                val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
                setup(firstMonth, lastMonth, firstDayOfWeek)
                scrollToMonth(currentMonth)
            }
            
            prevMonthButton.setOnClickListener {
                calendarView.findFirstVisibleMonth()?.let {
                    calendarView.smoothScrollToMonth(it.yearMonth.previous)
                }
            }
    
            nextMonthButton.setOnClickListener {
                calendarView.findFirstVisibleMonth()?.let {
                    calendarView.smoothScrollToMonth(it.yearMonth.next)
                }
            }
        }
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
//        vm.trainings.observe(viewLifecycleOwner) {
//            calendarDayBinder.submitList(it)
//            calendarView.notifyCalendarChanged()
//        }
//        vm.recentPhotos.observe(viewLifecycleOwner) { photosAdapter.submitList(it) }
//        vm.recentMeasurements.observe(viewLifecycleOwner) { measurementsAdapter.submitList(it) }
    }
}
