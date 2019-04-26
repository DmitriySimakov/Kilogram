package com.dmitrysimakov.kilogram.data.relation

data class MeasurementR(
        val _id: Long,
        val param: String,
        val value: Double?,
        val prevValue: Double?,
        val date: String?,
        val prevDate: String?
)