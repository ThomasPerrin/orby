package com.tomp.orby.firebase.logger

interface Logger {

    fun logException(throwable: Throwable)

    fun logRepository(repositoryName: String, funName: String, vararg args: Any?)

    fun logActivity(activityName: String)

    fun logClick(clickText: String, vararg args: Any?)

    fun logInfo(infoText: String, vararg args: Any?)

}
