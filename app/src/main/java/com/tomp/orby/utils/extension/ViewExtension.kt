package com.tomp.orby.utils.extension

import android.animation.ObjectAnimator
import android.app.ActionBar
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.tomp.orby.R
import com.tomp.orby.ui.OnSingleClickListener
import com.tomp.orby.utils.Constants
import com.tomp.orby.utils.Event

fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).run {
        addCallback(object : Snackbar.Callback() {
            override fun onShown(sb: Snackbar?) {
            }

            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            }
        })
        view.setBackgroundColor(context.getColor(R.color.backgroundSnackbar))
        view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            .setTextColor(context.getColor(R.color.textSnackbar))
        show()
    }
}

fun <T> View.setupSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<T>>,
    timeLength: Int
) {

    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            when (it) {
                is Int -> showSnackbar(context.getString(it), timeLength)
                is String -> showSnackbar(it, timeLength)
            }
        }
    })
}

fun EditText.afterTextChanged(afterTextChanged: (Editable?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable)
        }
    })
}

fun View.visibleIf(isVisible: Boolean, stateIfNot: Int = View.GONE) {
    this.visibility = if (isVisible) View.VISIBLE else stateIfNot
}

fun View.showKeyboard() {
    val inputMethodManager = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(
        this,
        InputMethodManager.SHOW_FORCED
    )
}

fun View.setOnSingleClickListener(l: View.OnClickListener) {
    setOnClickListener(OnSingleClickListener(l))
}

fun View.setOnSingleClickListener(l: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(l))
}

fun View.expand(fromHeight: Int, viewToShow: List<View?>, imageToRotate: View) {
    val targetHeight = fromHeight + viewToShow.mapNotNull {
        val widthSpec = MeasureSpec.makeMeasureSpec(this.width, MeasureSpec.EXACTLY)
        val heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        it?.measure(widthSpec, heightSpec)
        it?.measuredHeight
    }.sum()
    val v = this
    v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    v.layoutParams.height = fromHeight
    v.visibility = View.VISIBLE
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            viewToShow.forEach { it?.alpha = interpolatedTime }
            v.layoutParams.height = if (interpolatedTime == 1f) {
                ActionBar.LayoutParams.WRAP_CONTENT
            } else {
                fromHeight + ((targetHeight - fromHeight) * interpolatedTime).toInt()
            }
            v.requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    a.duration = Constants.DURATION_ANIMATION_S

    val translateIcon = ObjectAnimator.ofFloat(
        imageToRotate,
        Constants.ROTATION_PROPERTY_NAME,
        Constants.IMAGE_ROTATION_0
    )
    translateIcon.duration = a.duration

    translateIcon.start()
    viewToShow.forEach { it?.visibility = View.VISIBLE }
    v.startAnimation(a)
}

fun View.collapse(toHeight: Int, viewToShow: List<View>, imageToRotate: View) {
    val v = this
    val initialHeight = v.measuredHeight
    val a: Animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            if (interpolatedTime == 1f) {
                viewToShow.forEach { it.visibility = View.GONE }
            } else {
                viewToShow.forEach { it.alpha = 1 - interpolatedTime / 2 }
                v.layoutParams.height = initialHeight - ((initialHeight - toHeight) * interpolatedTime).toInt()
                v.requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }
    a.duration = Constants.DURATION_ANIMATION_S

    val translateIcon = ObjectAnimator.ofFloat(
        imageToRotate,
        Constants.ROTATION_PROPERTY_NAME,
        Constants.IMAGE_ROTATION_180
    )
    translateIcon.duration = a.duration

    translateIcon.start()
    v.startAnimation(a)
}
