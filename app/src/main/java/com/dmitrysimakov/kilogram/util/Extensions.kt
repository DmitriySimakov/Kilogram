package com.dmitrysimakov.kilogram.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.dmitrysimakov.kilogram.R
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import kotlin.math.pow
import kotlin.math.roundToInt

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

fun Fragment.dispatchGetImageContentIntent(requestCode: Int) {
    val intent = Intent(Intent.ACTION_GET_CONTENT)
    intent.type = "image/*"
    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
    startActivityForResult(intent, requestCode)
}

private val offsetDateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
fun String.toOffsetDateTime() = offsetDateTimeFormatter.parse(this, OffsetDateTime::from)
fun OffsetDateTime.toIsoString() = format(offsetDateTimeFormatter)
fun OffsetDateTime.toDate() = DateTimeUtils.toDate(this.toInstant())

private val localDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
fun String.toLocalDate() = localDateFormatter.parse(this, LocalDate::from)
fun LocalDate.toIsoString() = format(localDateFormatter)

fun Double.round(precision: Int) : Double {
    val multiplier = 10.0.pow(precision.toDouble())
    return (this * multiplier).roundToInt() / multiplier
}

fun String.meetsQuery(query: String): Boolean {
    val queryParts = query.toLowerCase(Locale.getDefault()).trim().split(" ")
    queryParts.forEach {
        if (!this.toLowerCase(Locale.getDefault()).contains(it)) return false
    }
    return true
}