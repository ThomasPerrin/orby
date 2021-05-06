package com.tomp.orby.utils.extension

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.view.Menu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.veolia.prism.R
import com.veolia.prism.ui.Injection
import com.veolia.prism.ui.Navigator
import com.veolia.prism.ui.ViewModelFactory
import com.veolia.prism.utils.component.CountDrawable
import org.jetbrains.anko.longToast

fun <T : ViewModel> FragmentActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(viewModelClass)

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>) =
    if(activity != null) {
        ViewModelProviders.of(this, ViewModelFactory.getInstance(activity!!.applicationContext))
            .get(viewModelClass)
    } else {
        ViewModelProviders.of(this, ViewModelFactory.getInstance(context!!))
            .get(viewModelClass)
    }

@SuppressLint("CheckResult")
fun Fragment.needReLog() {
    val contextOrActivity= activity ?: context
    contextOrActivity?.let {
        val needLoginUseCase = Injection.provideNeedLoginUseCase(it.applicationContext)
        needLoginUseCase.execute().subscribe { needLogin ->
            if (needLogin) {
                it.longToast(R.string.invalidity_token)
                Navigator.navigateToConnexion(it, true)
            }
        }
    }
}

fun Fragment.setBadgeReleveToSend(menu: Menu?, size: Int?) {
    if (menu != null) {
        val menuItem = menu.findItem(R.id.synchro)
        if(menuItem != null) {
            val icon = menuItem.icon as LayerDrawable
            val badge: CountDrawable
            val reuse = icon.findDrawableByLayerId(R.id.ic_sync_count)
            badge = if (reuse != null && reuse is CountDrawable) {
                reuse
            } else {
                CountDrawable(activity!!.applicationContext)
            }

            badge.setCount(size?.toString() ?: "0")
            icon.mutate()
            icon.setDrawableByLayerId(R.id.ic_sync_count, badge)
        }
    }
}

fun Fragment.setCanSync(menu: Menu?, canSync: Boolean?){
    if(canSync != null && menu != null) {
        context?.apply {
            menu.findItem(R.id.synchro)?.isEnabled = canSync
            val upArrow = menu.findItem(R.id.synchro)?.icon
            val logout = menu.findItem(R.id.logout)?.icon
            if (!canSync) {
                upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.grisClaire), PorterDuff.Mode.SRC_ATOP)
                logout?.setColorFilter(ContextCompat.getColor(this, R.color.grisClaire), PorterDuff.Mode.SRC_ATOP)
            } else {
                upArrow?.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP)
                logout?.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP)
            }
            menu.findItem(R.id.synchro)?.icon = upArrow
        }
    }
}

fun Fragment.setBadgeComment(menu: Menu?, isNullOrEmpty: Boolean) {
    if (menu != null) {
        val menuItem = menu.findItem(R.id.comment)
        if(menuItem != null) {
            val icon = menuItem.icon as LayerDrawable
            val badge: CountDrawable
            val reuse = icon.findDrawableByLayerId(R.id.ic_sync_count)
            badge = if (reuse != null && reuse is CountDrawable) {
                reuse
            } else {
                CountDrawable(context!!)
            }

            badge.setVisibility(!isNullOrEmpty)
            icon.mutate()
            icon.setDrawableByLayerId(R.id.ic_sync_count, badge)
        }
    }
}
