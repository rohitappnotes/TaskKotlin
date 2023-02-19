package com.task.extensions

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.task.R

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun ImageView.loadImage(
    imgUrl: String,
    @DrawableRes placeholder: Int = R.drawable.ic_launcher_foreground
) {
    Glide
        .with(this)
        .load(imgUrl)
        .placeholder(placeholder)
        .into(this)

}
