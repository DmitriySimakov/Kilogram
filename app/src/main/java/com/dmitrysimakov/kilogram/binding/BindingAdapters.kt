package com.dmitrysimakov.kilogram.binding

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
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
    
    @BindingAdapter("hideKeyboardOnInputDone")
    @JvmStatic fun EditText.hideKeyboardOnInputDone(enabled: Boolean) {
        if (!enabled) return
        val listener = TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                clearFocus()
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(windowToken, 0)
            }
            false
        }
        setOnEditorActionListener(listener)
    }
    
    @BindingAdapter("txt")
    @JvmStatic fun EditText.setTxt(value: String) {
        setText(value)
    }
    
    @InverseBindingAdapter(attribute = "txt")
    @JvmStatic fun EditText.getTxt(): String {
        return text.toString()
    }
    
    @BindingAdapter("txtAttrChanged")
    @JvmStatic fun EditText.setListener(listener: InverseBindingListener?) {
        onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) listener?.onChange()
        }
    }
}