package com.tomp.orby.utils.extension

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

fun Context.isNetworkAvailable(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager?
    return cm?.activeNetworkInfo != null && cm.activeNetworkInfo?.isConnectedOrConnecting == true
}

@SuppressLint("ShowToast")
fun Context.toast(content: Int, sizeText: Float? = null, time: Int? = null){

    val toast = Toast.makeText(this, content, time ?: Toast.LENGTH_SHORT)
    val group = toast.view as ViewGroup
    val messageTextView = group.getChildAt(0) as TextView
    if(sizeText != null) messageTextView.textSize = sizeText

    toast.show()
}

@SuppressLint("ShowToast")
fun Context.toast(content: String, sizeText: Float? = null, time: Int? = null){

    val toast = Toast.makeText(this, content, time ?: Toast.LENGTH_SHORT)
    val group = toast.view as ViewGroup
    val messageTextView = group.getChildAt(0) as TextView
    if(sizeText != null) messageTextView.textSize = sizeText

    toast.show()
}

fun Context.dpToPx(dp: Int): Int{
    val displayMetrics = this.resources.displayMetrics
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

fun Context.pxToDp(px: Int): Int{
    val displayMetrics = this.resources.displayMetrics
    return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

fun Context.spToPx(sp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this.resources.displayMetrics).toInt()
}

