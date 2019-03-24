package com.dmitrysimakov.kilogram.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

fun Fragment.runCircularRevealAnimation(fab: View, view: View, startColorId: Int, finalColorId: Int) {
    view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
            v.removeOnLayoutChangeListener(this)
            val cx = (fab.x + fab.width/2).toInt()
            val cy = (fab.y + fab.height/2).toInt()
            val startRadius = Math.min(fab.width, fab.height)/2f
            val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
            val duration = 750L //200, 400, 500
    
            val circularAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, startRadius, finalRadius)
            circularAnimator.duration = duration
            circularAnimator.interpolator = FastOutSlowInInterpolator()
            
            val colorAnimator = buildColorAnimator(view, startColorId, finalColorId, duration)
            
            circularAnimator.start()
            colorAnimator.start()
        }
    })
}

fun Fragment.runCircularExitAnimation(view: View, fab: View, startColorId: Int, finalColorId: Int, callback: (() -> Unit)? = null) {
    val cx = (fab.x + fab.width/2).toInt()
    val cy = (fab.y + fab.height/2).toInt()
    val startRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
    val finalRadius = Math.min(fab.width, fab.height)/2f
    val duration = 750L //200, 400, 500
    
    val circularAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, startRadius, finalRadius)
    circularAnimator.duration = duration
    circularAnimator.interpolator = FastOutSlowInInterpolator()
    if (callback != null) {
        circularAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) { callback.invoke() }
        })
    }
    
    val colorAnimator = buildColorAnimator(view, startColorId, finalColorId, duration)
    
    circularAnimator.start()
    colorAnimator.start()
}

private fun buildColorAnimator(view: View, startColorId: Int, finalColorId: Int, animationDuration: Long): ValueAnimator {
    return ValueAnimator().apply {
        val context = view.context
        val startColor = ContextCompat.getColor(context, startColorId)
        val finalColor = ContextCompat.getColor(context, finalColorId)
        setIntValues(startColor, finalColor)
        setEvaluator(ArgbEvaluator())
        addUpdateListener { valueAnimator -> view.setBackgroundColor(valueAnimator.animatedValue as Int) }
        duration = animationDuration
    }
}