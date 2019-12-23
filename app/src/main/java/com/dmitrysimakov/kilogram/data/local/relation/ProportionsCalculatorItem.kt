package com.dmitrysimakov.kilogram.data.local.relation

data class ProportionsCalculatorItem(
        val param: String,
        val coefficient: Double,
        val value: Double? = null,
        val recommendedValue: Double? = null,
        val percent: Double? = null
)