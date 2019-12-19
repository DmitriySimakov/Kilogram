package com.dmitrysimakov.kilogram.binding

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.dmitrysimakov.kilogram.R
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
    
    @BindingAdapter("imageUrl")
    @JvmStatic fun ImageView.imageUrl(url: String?) {
        val circularProgressDrawable = CircularProgressDrawable(this.context).apply {
            strokeWidth = 4f
            centerRadius = 32f
            start()
        }
        Glide.with(context).load(url)
                .placeholder(circularProgressDrawable)
                .fallback(R.drawable.no_photo)
                .error(R.drawable.no_photo)
                .into(this)
    }
    
    @BindingAdapter("notEmpty")
    @JvmStatic fun EditText.notEmpty(isNotEmpty: Boolean) {
        if (isNotEmpty) {
            onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus and text.isEmpty()) {
                    setText("0")
                }
            }
        }
    }
}