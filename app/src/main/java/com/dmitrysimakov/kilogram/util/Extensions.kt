package com.dmitrysimakov.kilogram.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import java.lang.Exception
import android.util.TypedValue
import android.widget.Toast
import androidx.lifecycle.*

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun <T> MutableLiveData<T>.setNewValue(newValue: T?) {
    if (value != newValue) {
        value = newValue
    }
}

fun EditText.getIntValue(): Int {
    return try { text.toString().toInt() } catch (e: Exception) { 0 }
}

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}