package com.dmitrysimakov.kilogram.data.local.relation

data class MeasurementWithPreviousResults(
        val _id: Long,
        val param: String,
        val value: Double?,
        val prevValue: Double?,
        val date: String?,
        val prevDate: String?
)