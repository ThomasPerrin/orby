package com.tomp.orby.utils.extension

import android.app.Activity
import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.tomp.orby.R
import com.tomp.orby.ui.ViewModelFactory
import com.tomp.orby.utils.component.CountDrawable


fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(viewModelClass)

fun AppCompatActivity.closeKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = this.currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun AppCompatActivity.showKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = this.currentFocus
    if (view == null) {
        view = EditText(this)
    }
    inputMethodManager.toggleSoftInputFromWindow(
        view.applicationWindowToken,
        InputMethodManager.SHOW_FORCED, 0
    )
}

fun AppCompatActivity.removeFocus() {
    this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
}

fun AppCompatActivity.setBadgeComment(menu: Menu?, isNullOrEmpty: Boolean) {
    if (menu != null) {
        val menuItem = menu.findItem(R.id.action_settings)
        if (menuItem != null) {
            val icon = menuItem.icon as LayerDrawable
            val badge: CountDrawable
            val reuse = icon.findDrawableByLayerId(R.id.action_settings)
            badge = if (reuse != null && reuse is CountDrawable) {
                reuse
            } else {
                CountDrawable(this)
            }

            badge.setVisibility(!isNullOrEmpty)
            icon.mutate()
            icon.setDrawableByLayerId(R.id.action_settings, badge)
        }
    }
}

