package com.dmitrysimakov.kilogram.data.local.relation

import org.threeten.bp.LocalDate

data class MeasurementWithPreviousResults(
        val id: Long?,
        val param: String,
        val value: Double?,
        val date: LocalDate?,
        val prevValue: Double? = null,
        val prevDate: LocalDate? = null
)