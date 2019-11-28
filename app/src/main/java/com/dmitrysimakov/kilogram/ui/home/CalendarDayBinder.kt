package com.dmitrysimakov.kilogram.ui.home

import android.content.res.Resources
import android.view.View
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.local.relation.DetailedTraining
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.calendar_day_view.view.*
import timber.log.Timber
import java.util.*
import java.util.Calendar.*

class CalendarDayBinder(
        private val resources: Resources,
        private val onClick: ((Calendar) -> Unit)
) : DayBinder<DayViewContainer> {
    
    private var trainings = listOf<DetailedTraining>()
    
    private val today = getInstance()
    
    override fun create(view: View) = DayViewContainer(view)
    
    override fun bind(container: DayViewContainer, day: CalendarDay) {
        Timber.d("BIND ${day.day}")
        container.apply {
            val curDayCalendar = GregorianCalendar(day.date.year, day.date.monthValue - 1, day.date.dayOfMonth)
            
            layout.setOnClickListener { onClick(curDayCalendar) }
            
            dayText.text = curDayCalendar.get(DAY_OF_MONTH).toString()
    
            if (day.owner != DayOwner.THIS_MONTH) {
                dayText.setTextColor(resources.getColor(R.color.grey500))
            }
            
            if (curDayCalendar.sameDateAs(today)) {
                dayText.setBackgroundResource(R.drawable.oval)
                dayText.setTextColor(resources.getColor(R.color.white))
            }
            
            val currentDayTrainings = trainings.filter {
                getInstance().apply { timeInMillis = it.start_time }.sameDateAs(curDayCalendar)
            }.sortedBy { it.start_time }
    
            val firstTraining = currentDayTrainings.firstOrNull()
            training.visibility  = if (firstTraining != null) View.VISIBLE else View.GONE
            training.text = firstTraining?.program_day_name ?: "Тренировка"
            more.visibility = if (currentDayTrainings.size > 1) View.VISIBLE else View.GONE
        }
    }
    
    fun submitList(trainings: List<DetailedTraining>) {
        this.trainings = trainings
    }
    
    private fun Calendar.sameMonthAs(other: Calendar) =
            get(YEAR) == other.get(YEAR) && get(MONTH) == other.get(MONTH)
    
    private fun Calendar.sameDateAs(other: Calendar) =
                    sameMonthAs(other) && get(DAY_OF_MONTH) == other.get(DAY_OF_MONTH)
}

class DayViewContainer(view: View) : ViewContainer(view) {
    val layout = view.layout
    val dayText = view.dayText
    val training = view.training
    val more = view.more
}