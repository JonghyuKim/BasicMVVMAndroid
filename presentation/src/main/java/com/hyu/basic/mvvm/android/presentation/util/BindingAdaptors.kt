package com.hyu.basic.mvvm.android.presentation.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.hyu.basic.mvvm.android.presentation.util.imageloader.IImageLoader
import org.koin.core.KoinComponent
import org.koin.core.get

object BindingAdaptors : KoinComponent{
    @BindingAdapter ("bind:loadImage")
    @JvmStatic
    fun loadImage(imageView : ImageView, path : String){
        val imageLoader = get<IImageLoader>()
        imageLoader.bindImg(imageView.context, imageView, path)
    }
}