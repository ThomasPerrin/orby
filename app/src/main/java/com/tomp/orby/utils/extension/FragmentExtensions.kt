package com.tomp.orby.utils.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.tomp.orby.ui.ViewModelFactory

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
