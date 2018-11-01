package com.dmitrysimakov.kilogram.binding

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleInvisible")
    fun visibleInvisible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }
}