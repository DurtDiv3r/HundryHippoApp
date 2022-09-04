package com.islaharper.hungryhippo.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.islaharper.hungryhippo.MainActivity
import com.islaharper.hungryhippo.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        Handler().postDelayed(Runnable {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 2000)
    }
}