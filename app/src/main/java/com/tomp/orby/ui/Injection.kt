package com.tomp.orby.ui

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.tomp.orby.firebase.analytics.AnalyticsImpl
import com.tomp.orby.firebase.logger.Logger
import com.tomp.orby.firebase.logger.LoggerImpl

object Injection {

    fun provideAnnalytics(context: Context): AnalyticsImpl =
        AnalyticsImpl.getInstance(
            FirebaseAnalytics.getInstance(context)
        )

    fun provideLogger(context: Context): Logger =
        LoggerImpl.getInstance()


}