package com.dmitrysimakov.kilogram.ui.home

import android.view.View
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import kotlinx.android.synthetic.main.view_calendar_header.view.*

class MonthViewContainer(view: View) : ViewContainer(view) {
    val legendLayout = view.legendLayout
}
class CalendarMonthBinder : MonthHeaderFooterBinder<MonthViewContainer> {
    override fun create(view: View) = MonthViewContainer(view)
    
    override fun bind(container: MonthViewContainer, month: CalendarMonth) {
        if (container.legendLayout.tag == null) {
            container.legendLayout.tag = month.yearMonth
        }
    }
}