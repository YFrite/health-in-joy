package com.yfrite.healthinjoy.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yfrite.healthinjoy.R

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("err", "start")
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }
}