package com.tomp.orby.firebase.logger

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

class LoggerImpl : Logger {

    override fun logRepository(repositoryName: String, funName: String, vararg args: Any?) {
        Log.e("logRepository", "[Repository] $repositoryName - $funName()")
        FirebaseCrashlytics.getInstance().log("[Repository] $repositoryName - $funName()")
        args.forEach {
            FirebaseCrashlytics.getInstance().log("$it")
        }
    }

    @SuppressLint("CheckResult")
    override fun logException(throwable: Throwable) {
        Log.e("logException", "$throwable")
        val needTrack = when (throwable) {
            is SocketTimeoutException -> false
            is SSLException -> false
            is IOException -> false
            is UnknownHostException -> false
            else -> true
        }
        if(!needTrack) {
            return
        }
        sendException(throwable)
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }

    override fun logActivity(activityName: String) {
        Log.e("logActivity", "[Activity] --------- $activityName ---------")
        FirebaseCrashlytics.getInstance().log("[Activity] --------- $activityName ---------")
    }

    override fun logClick(clickText: String, vararg args: Any?) {
        var clickEventText2 = clickText
        if (args.isEmpty()) {
            clickEventText2 = clickEventText2.replace("%".toRegex(), "(pourcent)")
        }
        clickEventText2 = "[Click] $clickEventText2"

        Log.e("logClick", String.format(clickEventText2, *args))
        FirebaseCrashlytics.getInstance().log(String.format(clickEventText2, *args))
    }

    override fun logInfo(infoText: String, vararg args: Any?) {
        var clickEventText2 = infoText
        if (args.isEmpty()) {
            clickEventText2 = clickEventText2.replace("%".toRegex(), "(pourcent)")
        }
        clickEventText2 = "[Info] $clickEventText2"

        Log.e("logInfo", String.format(clickEventText2, *args))
        FirebaseCrashlytics.getInstance().log(String.format(clickEventText2, *args))
    }

    private fun sendException(throwable: Throwable) =
        FirebaseCrashlytics.getInstance().log("[Exception] " + throwable.javaClass.simpleName + " : " + throwable.message)


    companion object {

        private var INSTANCE: LoggerImpl? = null

        @JvmStatic
        fun getInstance() =
            INSTANCE
                ?: synchronized(LoggerImpl::class.java) {
                    INSTANCE
                        ?: LoggerImpl()
                            .also { INSTANCE = it }
                }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
