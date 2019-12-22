package com.dmitrysimakov.kilogram.data.local.relation

data class ProportionsCalculatorItem(
        val param: String,
        val coefficient: Double,
        var value: Double? = null,
        var recommendedValue: Double? = null
)