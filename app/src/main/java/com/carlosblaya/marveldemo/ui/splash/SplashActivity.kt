package com.carlosblaya.marveldemo.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.carlosblaya.marveldemo.core.Konsts
import com.carlosblaya.marveldemo.databinding.ActivitySplashBinding
import com.carlosblaya.marveldemo.ui.main.MainActivity
import com.carlosblaya.marveldemo.util.extensions.createIntent
import com.carlosblaya.marveldemo.util.extensions.viewBinding

class SplashActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySplashBinding::inflate)

    private var mHandler = Handler()
    private var mRunnable: Runnable = Runnable {
        startActivity(createIntent<MainActivity>())
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mHandler.postDelayed(mRunnable, Konsts.SPLASH_TIME_OUT)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mHandler.removeCallbacks(mRunnable)
    }
}