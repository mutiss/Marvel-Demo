package com.carlosblaya.marveldemo.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.carlosblaya.marveldemo.R

open class BaseActivity : AppCompatActivity() {

    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createProgressDialog()
    }

    private fun createProgressDialog() {
        progressDialog = Dialog(this, R.style.ProgressBarTheme).apply {
            val view: View =
                LayoutInflater.from(this@BaseActivity).inflate(R.layout.progress_bar, null)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            window?.setBackgroundDrawableResource(R.color.progress_bar_bg)
            setContentView(view)
        }
    }

    fun showProgress() {
        if (!isFinishing) {
            hideProgress()
            createProgressDialog()
            progressDialog.show()
        }
    }

    fun hideProgress() {
        if (!isFinishing) {
            progressDialog.dismiss()
        }
    }


}