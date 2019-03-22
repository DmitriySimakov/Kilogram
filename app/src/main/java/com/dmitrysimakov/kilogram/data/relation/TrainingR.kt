package com.dmitrysimakov.kilogram.data.relation

import com.dmitrysimakov.kilogram.util.HasId

class TrainingR(
        override val _id: Long,
        var program_day_id: Long? = null,
        val start_time: Long,
        var duration: Int? = null,
        val program_day_name: String? = null,
        val program_name: String? = null
): HasId