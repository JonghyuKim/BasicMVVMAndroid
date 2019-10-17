package com.hyu.basic.mvvm.android.presentation.util.imageloader

import android.content.Context
import android.widget.ImageView

interface IImageLoader {
    fun bindImg(context: Context, targetView: ImageView, imgPath : String)
    var onCompliteBinding : (() -> Unit)?

    fun release()
}