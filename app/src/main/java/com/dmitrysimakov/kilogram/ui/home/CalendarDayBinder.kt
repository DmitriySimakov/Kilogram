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
import org.threeten.bp.LocalDate
import timber.log.Timber

class CalendarDayBinder(
        private val resources: Resources,
        private val onClick: ((LocalDate) -> Unit)
) : DayBinder<DayViewContainer> {
    
    private var trainings = listOf<DetailedTraining>()
    
    private val today = LocalDate.now()
    
    override fun create(view: View) = DayViewContainer(view)
    
    override fun bind(container: DayViewContainer, day: CalendarDay) {
        Timber.d("BIND ${day.day}")
        container.apply {
            val curDate = day.date
            
            layout.setOnClickListener { onClick(curDate) }
            
            dayText.text = curDate.dayOfMonth.toString()
    
            if (day.owner != DayOwner.THIS_MONTH) {
                dayText.setTextColor(resources.getColor(R.color.grey500))
            }
            
            if (curDate == today) {
                dayText.setBackgroundResource(R.drawable.oval)
                dayText.setTextColor(resources.getColor(R.color.white))
            }
            
            val currentDayTrainings = trainings.filter {
                it.start_date_time.toLocalDate() == curDate
            }.sortedBy { it.start_date_time }
    
            val firstTraining = currentDayTrainings.firstOrNull()
            training.visibility  = if (firstTraining != null) View.VISIBLE else View.GONE
            training.text = firstTraining?.program_day_name ?: "Тренировка"
            more.visibility = if (currentDayTrainings.size > 1) View.VISIBLE else View.GONE
        }
    }
    
    fun submitList(trainings: List<DetailedTraining>) {
        this.trainings = trainings
    }
}

class DayViewContainer(view: View) : ViewContainer(view) {
    val layout = view.layout
    val dayText = view.dayText
    val training = view.training
    val more = view.more
}