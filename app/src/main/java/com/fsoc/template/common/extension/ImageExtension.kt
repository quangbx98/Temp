package com.ominext.healthy.common.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fsoc.template.R


const val THUMBNAIL_SIZE = 128

fun ImageView.loadImage(url: String?){
    val option = RequestOptions.placeholderOf(R.color.colorPrimaryDark)
    Glide.with(this)
        .load(url)
        .apply(option)
        .into(this)
}


fun ImageView.loadThumb(url: String?){
    val option = RequestOptions.placeholderOf(R.color.colorPrimaryDark)
    Glide.with(this)
        .load(url)
        .centerCrop()
        .thumbnail(0.1f)
        .override(THUMBNAIL_SIZE, THUMBNAIL_SIZE)
        .apply(option)
        .into(this)
}