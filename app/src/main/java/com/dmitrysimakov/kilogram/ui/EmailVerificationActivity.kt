package com.dmitrysimakov.kilogram.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.util.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_email_verification.*
import java.util.*

class EmailVerificationActivity : AppCompatActivity() {
    
    private val timer = Timer()
    
    var user = FirebaseAuth.getInstance().currentUser
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_verification)
    
        textView.text = resources.getString(R.string.verification_message_sent, user?.email)
        
        user?.sendEmailVerification()
        resendBtn.setOnClickListener {
            user?.sendEmailVerification()?.addOnCompleteListener {
                if (it.isSuccessful) toast("Сообщение отправлено!")
            }
        }
        
        val task = object : TimerTask() {
            override fun run() {
                user?.run {
                    reload()
                    if (isEmailVerified) {
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                }
            }
        }
        timer.scheduleAtFixedRate(task, 0, 1000)
    }
    
    override fun onStop() {
        timer.cancel()
        super.onStop()
    }
}
