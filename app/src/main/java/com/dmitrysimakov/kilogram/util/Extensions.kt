package com.dmitrysimakov.kilogram.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.dmitrysimakov.kilogram.ui.MainViewModel
import java.lang.Exception
import android.util.TypedValue
import androidx.lifecycle.*

inline fun <reified T : ViewModel> Fragment.getViewModel(factory: ViewModelProvider.Factory) : T {
    return ViewModelProviders.of(this, factory).get(T::class.java)
}

fun getViewModel(activity: FragmentActivity, factory: ViewModelProvider.Factory): MainViewModel {
    return ViewModelProviders.of(activity, factory).get(MainViewModel::class.java)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
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

fun Fragment.getAttrColor(id: Int): Int {
    val typedValue = TypedValue()
    context!!.theme.resolveAttribute(id, typedValue, true)
    return typedValue.data
}

fun <T> MutableLiveData<T>.notifyObservers() {
    value = value
}

inline fun <T> List<T>.findIndex(predicate: (T) -> Boolean): Int? {
    for (i in 0 until this.size) if (predicate(get(i))) return i
    return null
}