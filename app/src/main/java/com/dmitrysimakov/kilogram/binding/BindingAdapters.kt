package com.dmitrysimakov.kilogram.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.dmitrysimakov.kilogram.data.entity.Exercise
import android.graphics.drawable.Drawable
import com.dmitrysimakov.kilogram.data.entity.Muscle
import java.io.IOException


object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleInvisible")
    fun visibleInvisible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("assetsImage")
    fun assetsImage(view: ImageView, path: String) {
        try {
            val inputStream = view.context.assets.open("images/$path")
            view.setImageDrawable(Drawable.createFromStream(inputStream, null))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}