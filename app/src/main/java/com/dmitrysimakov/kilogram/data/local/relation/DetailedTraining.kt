package com.dmitrysimakov.kilogram.data.local.relation

import org.threeten.bp.OffsetDateTime

data class DetailedTraining(
        val id: Long,
        val startDateTime: OffsetDateTime,
        var duration: Int? = null,
        var programDayId: Long? = null,
        val programDayName: String? = null,
        val programName: String? = null
)