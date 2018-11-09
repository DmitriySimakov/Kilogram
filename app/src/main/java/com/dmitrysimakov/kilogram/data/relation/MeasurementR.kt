package com.dmitrysimakov.kilogram.data.relation

import com.dmitrysimakov.kilogram.util.HasId

data class MeasurementR(
        override val _id: Long,
        val param: String,
        val value: Double?,
        val prevValue: Double?,
        val date: String?,
        val prevDate: String?
) : HasId