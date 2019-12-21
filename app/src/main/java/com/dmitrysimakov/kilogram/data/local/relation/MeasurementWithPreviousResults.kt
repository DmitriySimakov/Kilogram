package com.dmitrysimakov.kilogram.data.local.relation

import org.threeten.bp.LocalDate

data class MeasurementWithPreviousResults(
        val _id: Long?,
        val param: String,
        val value: Double?,
        val date: LocalDate?,
        val prev_value: Double? = null,
        val prev_date: LocalDate? = null
)