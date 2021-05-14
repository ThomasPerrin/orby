package com.tomp.orby.utils.extension

import android.annotation.SuppressLint
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat

const val DATE_FORMAT_SERVE_MILLI = "yyyy-MM-dd'T'HH:mm:ss.SSS"

@SuppressLint("SimpleDateFormat")
fun String.toDateLong(format: String): Long {
    val dateFormat = SimpleDateFormat(format)
    return dateFormat.parse(this)?.time ?: 0L
}

@SuppressLint("SimpleDateFormat")
fun LocalDateTime.parseDateToServerDateString(): String {
    val dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT_SERVE_MILLI)
    return this.format(dateFormat)
}

@SuppressLint("SimpleDateFormat")
fun String.parseServerStringToLocalDateTime(): LocalDateTime {
    val dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT_SERVE_MILLI)
    return LocalDateTime.parse(this, dateFormat)
}