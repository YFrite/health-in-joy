package com.yfrite.healthinjoy.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.*
import com.yfrite.healthinjoy.data.UserRepository
import com.yfrite.healthinjoy.data.eaten.EatenDao
import com.yfrite.healthinjoy.data.eaten.EatenDatabase
import com.yfrite.healthinjoy.auth.RegisterActivity
import com.yfrite.healthinjoy.data.eaten.Eaten
import com.yfrite.healthinjoy.data.eaten.EatenRepository
import com.yfrite.healthinjoy.main.MainActivity
import com.yfrite.healthinjoy.util.android.time.worker.DayWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Duration
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Workers
        // Day worker
        val dayWorkRequest: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<DayWorker>(Duration.ofHours(24))
                .build()
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork("DayWorker",ExistingPeriodicWorkPolicy.KEEP, dayWorkRequest)

        // Check user already registered
        check()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun check(){

        val userRepository = UserRepository(getSharedPreferences("user", MODE_PRIVATE))

        Log.e("name", userRepository.name)

        if(userRepository.name == "0") {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
            return
        }

        startActivity(Intent(this, MainActivity::class.java))
        finish()


    }
}