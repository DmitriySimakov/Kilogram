package com.dmitrysimakov.kilogram.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.util.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_email_verification.*
import timber.log.Timber
import java.util.*

class EmailVerificationActivity : AppCompatActivity() {
    
    private val user = FirebaseAuth.getInstance().currentUser
    
    private val timer = Timer()
    private val task = object : TimerTask() {
        override fun run() {
            user?.run {
                reload()
                if (isEmailVerified) finish()
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_verification)
        
        textView.text = resources.getString(R.string.verification_message_sent, user?.email)
        
        user?.sendEmailVerification()
        resendBtn.setOnClickListener {
            user?.sendEmailVerification()?.addOnCompleteListener {
                if (it.isSuccessful) toast("Сообщение отправлено.")
                else toast("Не удалось отправить сообщение.")
            }
        }
    }
    
    override fun onStart() {
        Timber.d("onStart")
        super.onStart()
        timer.scheduleAtFixedRate(task, 0, 1000)
    }
    
    override fun onStop() {
        Timber.d("onStop")
        timer.cancel()
        super.onStop()
    }
    
    override fun onDestroy() {
        Timber.d("onDestroy")
        if (user?.isEmailVerified == false) user.delete()
        super.onDestroy()
    }
}
