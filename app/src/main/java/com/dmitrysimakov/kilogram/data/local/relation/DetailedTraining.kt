package com.dmitrysimakov.kilogram.data.local.relation

import org.threeten.bp.OffsetDateTime

data class DetailedTraining(
        val _id: Long,
        val start_date_time: OffsetDateTime,
        var duration: Int? = null,
        var program_day_id: Long? = null,
        val program_day_name: String? = null,
        val program_name: String? = null
)