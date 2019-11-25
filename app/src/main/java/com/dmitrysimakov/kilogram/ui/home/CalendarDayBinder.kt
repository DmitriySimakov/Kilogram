package com.dmitrysimakov.kilogram.ui.home

import android.content.res.Resources
import android.view.View
import com.dmitrysimakov.kilogram.R
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.calendar_day_view.view.*
import org.threeten.bp.LocalDate

class CalendarDayBinder(private val resources: Resources) : DayBinder<DayViewContainer> {
    
    private val today = LocalDate.now()
    
    override fun create(view: View) = DayViewContainer(view)
    
    override fun bind(container: DayViewContainer, day: CalendarDay) {
        container.textView.text = day.date.dayOfMonth.toString()
        
        if (day.date == today) {
            container.textView.setBackgroundResource(R.drawable.circle)
            container.textView.setTextColor(resources.getColor(R.color.white))
        }
    }
}

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView = view.calendarDayText
}