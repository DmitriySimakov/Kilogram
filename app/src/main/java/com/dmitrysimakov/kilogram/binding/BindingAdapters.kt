package com.dmitrysimakov.kilogram.binding

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import java.io.IOException

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleInvisible")
    fun visibleInvisible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun visibleGone(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
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