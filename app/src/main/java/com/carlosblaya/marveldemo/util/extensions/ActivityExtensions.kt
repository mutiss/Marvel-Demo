package com.carlosblaya.marveldemo.util.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.carlosblaya.marveldemo.R
import com.google.android.material.snackbar.Snackbar

fun Activity?.showMessage(message: String) {
    if (this != null) {
        Snackbar
            .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(getColor(R.color.black))
            .show()
    }
}

inline fun <reified T : Activity> Context.createIntent() =
    Intent(this, T::class.java)

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }


