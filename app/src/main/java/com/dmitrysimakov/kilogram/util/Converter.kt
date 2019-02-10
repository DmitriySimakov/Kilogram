@file:JvmName("Converter")
package com.dmitrysimakov.kilogram.util

fun toTimeFormat(seconds: Int) : String {
    return if (seconds < 60) {
        seconds.toString()
    } else {
        val min = seconds / 60
        val sec = seconds % 60
        String.format("%d:%02d", min, sec)
    }
}