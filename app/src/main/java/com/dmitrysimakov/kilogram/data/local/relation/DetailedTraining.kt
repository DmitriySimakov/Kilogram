package com.dmitrysimakov.kilogram.data.local.relation

data class DetailedTraining(
        val _id: Long,
        val start_time: Long,
        var duration: Int? = null,
        var program_day_id: Long? = null,
        val program_day_name: String? = null,
        val program_name: String? = null
)