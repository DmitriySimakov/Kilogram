package com.dmitrysimakov.kilogram.ui

import android.os.Bundle
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.remote.FirebaseDao
import com.dmitrysimakov.kilogram.util.toast
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_email_verification.*
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class EmailVerificationActivity : DaggerAppCompatActivity() {
    
    @Inject lateinit var firebase: FirebaseDao
    
    private val user by lazy { firebase.user!! }
    
    private val timer = Timer()
    private val task = object : TimerTask() {
        override fun run() {
            user.run {
                reload()
                if (isEmailVerified) finish()
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_verification)
        
        textView.text = resources.getString(R.string.verification_message_sent, user.email)
        
        user.sendEmailVerification()
        resendBtn.setOnClickListener {
            user.sendEmailVerification().addOnCompleteListener {
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
        if (!user.isEmailVerified) {
            Timber.d("delete")
            user.delete()
            firebase.userDocument.delete()
        }
        super.onDestroy()
    }
}
