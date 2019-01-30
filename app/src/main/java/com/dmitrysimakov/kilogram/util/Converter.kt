package com.dmitrysimakov.kilogram.util

import android.content.Context
import androidx.databinding.InverseMethod
import com.dmitrysimakov.kilogram.R

object Converter {
    
    @InverseMethod("stringToInt")
    @JvmStatic fun intToString(value: Int): String {
        return value.toString()
    }
    
    @JvmStatic fun stringToInt(value: String): Int {
        return value.toInt()
    }
}