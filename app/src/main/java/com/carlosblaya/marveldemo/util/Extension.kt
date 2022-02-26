package com.carlosblaya.marveldemo.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.carlosblaya.marveldemo.R
import com.google.android.material.snackbar.Snackbar

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Activity?.showMessage(message: String) {
    if (this != null) {
        Snackbar
            .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(getColor(R.color.marvel_red))
            .show()
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.fadeIn() {
    this.visibility = View.VISIBLE
    this.startAnimation(AlphaAnimation(0F, 1F).apply {
        duration = 500
        fillAfter = true
    })
}

fun View.fadeOut() {
    this.startAnimation(AlphaAnimation(1F, 0F).apply {
        duration = 200
        fillAfter = true
    })
    this.visibility = View.GONE
}

fun ImageView.fromUrl(url: String) {
    if (url.isNotEmpty())
        Glide.with(this).load(url).apply(RequestOptions.overrideOf(1024, 1024)).into(this)
}

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}

inline fun <reified T: Activity> Context.createIntent() =
    Intent(this, T::class.java)

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }


