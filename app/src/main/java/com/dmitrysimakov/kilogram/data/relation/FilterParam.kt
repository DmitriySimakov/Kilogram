package com.dmitrysimakov.kilogram.data.relation

import androidx.room.Ignore

class FilterParam(val _id: Long, val name: String) {
    @Ignore var isActive: Boolean = false
}