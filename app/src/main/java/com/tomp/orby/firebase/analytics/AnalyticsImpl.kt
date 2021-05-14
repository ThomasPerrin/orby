package com.tomp.orby.firebase.analytics

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import io.reactivex.disposables.CompositeDisposable

class AnalyticsImpl(
    private val firebaseAnalytics: FirebaseAnalytics
) : Analytics {

    override fun logReleveSent(percent: Int) {
        logEvent(RELEVE_SENT, mapOf(RELEVE_SENT_PERCENT to percent as? Any))
    }

    override fun logClick(key: String, args: Map<String, Any?>) {
        logEvent(key, args)
    }

    private fun logEvent(property: String, args: Map<String, Any?> = mapOf()) {
        val bundle = Bundle()
        Log.i("logEvent", "$property | arguments : $args")
        args.forEach {
            when (val value = it.value) {
                is String -> bundle.putString(it.key, value)
                is Long -> bundle.putLong(it.key, value)
                is Double -> bundle.putDouble(it.key, value)
                is Int -> bundle.putLong(it.key, value.toLong())
                else -> bundle.putString(it.key, value.toString())
            }
        }
        firebaseAnalytics.logEvent(property, bundle)
    }


    companion object {

        private const val RELEVE_SENT = "releve_envoye"
        private const val RELEVE_SENT_PERCENT = "pourcentage"

        private var INSTANCE: AnalyticsImpl? = null

        @JvmStatic
        fun getInstance(
            firebaseAnalytics: FirebaseAnalytics
        ) =
            INSTANCE
                ?: synchronized(AnalyticsImpl::class.java) {
                    INSTANCE
                        ?: AnalyticsImpl(
                            firebaseAnalytics
                        ).also { INSTANCE = it }
                }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
