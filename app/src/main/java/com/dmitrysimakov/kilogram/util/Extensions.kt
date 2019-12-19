package com.dmitrysimakov.kilogram.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.dmitrysimakov.kilogram.R
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

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

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.setXNavIcon() {
    (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
}

fun Fragment.setTitle(title: String) {
    (activity as AppCompatActivity).supportActionBar?.title = title
}

private val offsetDateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
fun String.toOffsetDateTime() = offsetDateTimeFormatter.parse(this, OffsetDateTime::from)
fun OffsetDateTime.toIsoString() = format(offsetDateTimeFormatter)

private val localDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
fun String.toLocalDate() = localDateFormatter.parse(this, LocalDate::from)
fun LocalDate.toIsoString() = format(localDateFormatter)