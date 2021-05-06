package com.tomp.orby.utils.extension

import android.annotation.SuppressLint
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
import com.veolia.prism.R
import com.veolia.prism.ui.Injection
import com.veolia.prism.ui.Navigator
import com.veolia.prism.ui.ViewModelFactory
import com.veolia.prism.utils.component.CountDrawable
import org.jetbrains.anko.longToast
import java.util.Locale


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

@SuppressLint("CheckResult")
fun AppCompatActivity.needReLog() {
    val needLoginUseCase = Injection.provideNeedLoginUseCase(this)
    val logout = Injection.provideLogoutRepositoryImpl(this)
    needLoginUseCase.execute().subscribe { needLogin ->
        if (needLogin) {
            longToast(R.string.invalidity_token)
            logout.resetPreferences().blockingAwait()
            Navigator.navigateToConnexion(this, true)
        }
    }
}

fun AppCompatActivity.setBadgeComment(menu: Menu?, isNullOrEmpty: Boolean) {
    if (menu != null) {
        val menuItem = menu.findItem(R.id.comment)
        if (menuItem != null) {
            val icon = menuItem.icon as LayerDrawable
            val badge: CountDrawable
            val reuse = icon.findDrawableByLayerId(R.id.ic_sync_count)
            badge = if (reuse != null && reuse is CountDrawable) {
                reuse
            } else {
                CountDrawable(this)
            }

            badge.setVisibility(!isNullOrEmpty)
            icon.mutate()
            icon.setDrawableByLayerId(R.id.ic_sync_count, badge)
        }
    }
}

fun AppCompatActivity.setBadgeReleveToSend(menu: Menu?, size: Int?) {
    if (menu != null) {
        val menuItem = menu.findItem(R.id.synchro)
        if (menuItem != null) {
            val icon = menuItem.icon as LayerDrawable
            val badge: CountDrawable
            val reuse = icon.findDrawableByLayerId(R.id.ic_sync_count)
            badge = if (reuse != null && reuse is CountDrawable) {
                reuse
            } else {
                CountDrawable(this)
            }

            badge.setCount(size?.toString() ?: "0")
            icon.mutate()
            icon.setDrawableByLayerId(R.id.ic_sync_count, badge)
        }
    }
}

fun AppCompatActivity.setLanguage() {
    val language = Injection.provideGetSelectedLanguageRepositoryImpl(this).getLanguageSelected().blockingGet().code
    val conf = this.resources.configuration
    val local = when (language) {
        "fr-FR" -> Locale("fr")
        "en-GB" -> Locale("en")
        "ja-JP" -> Locale("ja")
        else -> conf.locale
    }
    Locale.setDefault(local)
    conf.setLocale(local)
    this.resources.updateConfiguration(conf, this.resources.displayMetrics)
    this.application?.resources?.updateConfiguration(conf, this.application?.resources?.displayMetrics)
}

