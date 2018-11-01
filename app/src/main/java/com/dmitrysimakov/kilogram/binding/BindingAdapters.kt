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
    @BindingAdapter("muscleImage")
    fun bindMuscleImage(view: ImageView, id: Long) {
        try {
            val inputStream = view.context.assets.open("images/muscles/$id.png")
            val drawable = Drawable.createFromStream(inputStream, null)
            view.setImageDrawable(drawable)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    @BindingAdapter("firstExerciseImage")
    fun bindExerciseImage(view: ImageView, id: Long) {
        try {
            val inputStream = view.context.assets.open("images/exercises/${id}_1.jpeg")
            val drawable = Drawable.createFromStream(inputStream, null)
            view.setImageDrawable(drawable)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}