package com.dmitrysimakov.kilogram.data.relation

data class TrainingR(
        val _id: Long,
        var program_day_id: Long? = null,
        val start_time: Long,
        var duration: Int? = null,
        val program_day_name: String? = null,
        val program_name: String? = null
)