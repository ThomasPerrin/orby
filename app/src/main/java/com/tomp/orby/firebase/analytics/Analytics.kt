package com.tomp.orby.firebase.analytics

interface Analytics {

    fun logReleveSent(percent: Int)

    fun logClick(key: String, args: Map<String, Any?>)
}
