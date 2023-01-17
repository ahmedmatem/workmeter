package com.ahmedmatem.android.workmeter.utils

import android.util.Size
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("sizeWidth")
fun bindSizeToWidth(view: TextView, size: String){
    view.text = Size.parseSize(size).width.toString()
}

@BindingAdapter("sizeHeight")
fun bindSizeToHeight(view: TextView, size: String){
    view.text = Size.parseSize(size).height.toString()
}