package com.carlosblaya.marveldemo.util.extensions

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.carlosblaya.marveldemo.R

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

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
        Glide.with(this).load(url).apply(RequestOptions.overrideOf(1024, 1024)).placeholder(R.drawable.marvel_logo).into(this)
}

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}

