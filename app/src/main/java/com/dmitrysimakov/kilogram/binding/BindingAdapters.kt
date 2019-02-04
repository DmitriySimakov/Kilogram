package com.dmitrysimakov.kilogram.binding

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import java.io.IOException

object BindingAdapters {
    
    @BindingAdapter("invisibleUnless")
    @JvmStatic fun View.invisibleUnless(visible: Boolean) {
        visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }
    
    @BindingAdapter("goneUnless")
    @JvmStatic fun View.goneUnless(visible: Boolean) {
        visibility = if (visible) View.VISIBLE else View.GONE
    }
    
    @BindingAdapter("assetsImage")
    @JvmStatic fun ImageView.assetsImage(path: String) {
        try {
            val inputStream = context.assets.open("images/$path")
            setImageDrawable(Drawable.createFromStream(inputStream, null))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}