package com.tomp.orby.utils.extension

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:textPercent")
fun TextView.setText(value: Float?){
    val text = "${value?.toInt()}%"
    this.text = text
}

@BindingAdapter("android:enabled")
fun View.setEnabled(isEnabled: Boolean?) {
    this.isEnabled = isEnabled ?: false
}

@BindingAdapter("app:visibleIf")
fun View.setVisibleIf(isVisible: Boolean?) {
    this.visibleIf(isVisible ?: false)
}

@BindingAdapter("app:goneIf")
fun View.goneIf(isInvisible: Boolean?) {
    this.visibleIf(
        if (isInvisible != null) {
            !isInvisible
        } else {
            true
        }
    )
}
