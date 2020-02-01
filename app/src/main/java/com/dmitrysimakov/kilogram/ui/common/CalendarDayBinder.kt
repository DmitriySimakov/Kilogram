package com.dmitrysimakov.kilogram.ui.common

import android.view.View
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.local.relation.DetailedTraining
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.item_calendar_day.view.*
import org.threeten.bp.LocalDate

class CalendarDayBinder(private val onClick: ((LocalDate) -> Unit)? = null) : DayBinder<DayViewContainer> {
    
    private var trainings = listOf<DetailedTraining>()
    
    private val today = LocalDate.now()
    
    override fun create(view: View) = DayViewContainer(view)
    
    override fun bind(container: DayViewContainer, day: CalendarDay) {
        container.apply {
            val curDate = day.date
            
            onClick?.let { layout.setOnClickListener { it(curDate) } }
            
            dayText.text = curDate.dayOfMonth.toString()
    
            if (day.owner != DayOwner.THIS_MONTH) {
                dayText.setTextColor(view.resources.getColor(R.color.grey500))
            }
            
            if (curDate == today) {
                dayText.setBackgroundResource(R.drawable.oval)
                dayText.setTextColor(view.resources.getColor(R.color.white))
            }
            
            val currentDayTrainings = trainings.filter {
                it.startDateTime.toLocalDate() == curDate
            }.sortedBy { it.startDateTime }
    
            val firstTraining = currentDayTrainings.firstOrNull()
            training.visibility  = if (firstTraining != null) View.VISIBLE else View.GONE
            training.text = firstTraining?.programDayName ?: "Тренировка"
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