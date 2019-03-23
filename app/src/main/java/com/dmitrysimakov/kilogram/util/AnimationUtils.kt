package com.dmitrysimakov.kilogram.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.animation.addListener
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

fun Fragment.runCircularRevealAnimation(from: View, to: View, startColorId: Int, endColorId: Int) {
    to.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
            v.removeOnLayoutChangeListener(this)
            
            val cx = (from.x + from.width/2).toInt()
            val cy = (from.y + from.height/2).toInt()
            val duration = 750 //200, 400, 500
            val startRadius = Math.min(from.width, from.height)/2f
            val finalRadius = Math.sqrt((v.width * v.width + v.height * v.height).toDouble()).toFloat()
            val circularRevealAnimator = ViewAnimationUtils.createCircularReveal(v, cx, cy, startRadius, finalRadius).setDuration(duration.toLong())
            circularRevealAnimator.interpolator = FastOutSlowInInterpolator()
    
            val colorAnimator = ValueAnimator()
            colorAnimator.setIntValues(ContextCompat.getColor(context!!, startColorId), ContextCompat.getColor(context!!, endColorId))
            colorAnimator.setEvaluator(ArgbEvaluator())
            colorAnimator.addUpdateListener { valueAnimator -> v.setBackgroundColor(valueAnimator.animatedValue as Int) }
            colorAnimator.duration = duration.toLong()
            
            circularRevealAnimator.start()
            colorAnimator.start()
        }
    })
}