package com.tomp.orby.utils.extension

import android.annotation.SuppressLint
import com.veolia.prism.domain.TourneeType
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT_SERVE = "yyyy-MM-dd'T'HH:mm:ss"
const val DATE_FORMAT_SERVE_MILLI = "yyyy-MM-dd'T'HH:mm:ss.SSS"

private const val DATE_FORMAT_CREATE_EVENT = "dd/MM/yyyy HH:mm"
private const val HOUR_FORMAT_CREATE_EVENT = "HH'h'mm"
private const val DATE_FORMAT_LIST_EVENT = "dd/MM/yyyy"

const val HOUR_TO_MIN = 60
const val DAY_TO_MIN = 60 * 24

@SuppressLint("SimpleDateFormat")
fun String.toDateLong(format: String): Long {
    val dateFormat = SimpleDateFormat(format)
    return dateFormat.parse(this)?.time ?: 0L
}

fun String.parseServerDateStringToDate(): Date {
    val dateFormatServer = SimpleDateFormat(DATE_FORMAT_SERVE)
    return dateFormatServer.parse(this)!!
}

@SuppressLint("SimpleDateFormat")
fun Date.parseDateToServerDateString(): String {
    val dateFormat = SimpleDateFormat(DATE_FORMAT_SERVE_MILLI)
    return dateFormat.format(this)
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

@SuppressLint("SimpleDateFormat")
fun Date.parseDateToDisplayDateString(type: TourneeType): String {
    val format = when (type) {
        TourneeType.DATE -> SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        TourneeType.SEMESTRE -> SimpleDateFormat("MM/yyyy")
        TourneeType.TRIMESTRE -> SimpleDateFormat("MM/yyyy")
        TourneeType.ANNEE -> SimpleDateFormat("yyyy")
        TourneeType.MOIS -> SimpleDateFormat("MM/yyyy")
        TourneeType.JOUR -> SimpleDateFormat("dd/MM/yyyy")
        TourneeType.HEURE -> SimpleDateFormat("dd/MM/yyyy HH:00:00")
        TourneeType.QUART14 -> SimpleDateFormat("dd/MM/yyyy .HH")
        TourneeType.QUART13 -> SimpleDateFormat("dd/MM/yyyy .HH")
        TourneeType.QUART11 -> SimpleDateFormat("dd/MM/yyyy .HH")
        TourneeType.QUART12 -> SimpleDateFormat("dd/MM/yyyy .HH")
    }

    return format.format(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.parseDateToServerDateString(type: TourneeType): String {
    val format = when (type) {
        TourneeType.DATE -> SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        TourneeType.SEMESTRE -> SimpleDateFormat("yyyy-MM-01'T'00:00:00")
        TourneeType.TRIMESTRE -> SimpleDateFormat("yyyy-MM-01'T'00:00:00")
        TourneeType.ANNEE -> SimpleDateFormat("yyyy-01-01'T'00:00:00")
        TourneeType.MOIS -> SimpleDateFormat("yyyy-MM-01'T'00:00:00")
        TourneeType.JOUR -> SimpleDateFormat("yyyy-MM-dd'T'00:00:00")
        TourneeType.HEURE -> SimpleDateFormat("yyyy-MM-dd'T'HH:00:00")
        TourneeType.QUART14 -> SimpleDateFormat("yyyy-MM-dd'T'HH:00:00")
        TourneeType.QUART13 -> SimpleDateFormat("yyyy-MM-dd'T'HH:00:00")
        TourneeType.QUART11 -> SimpleDateFormat("yyyy-MM-dd'T'HH:00:00")
        TourneeType.QUART12 -> SimpleDateFormat("yyyy-MM-dd'T'HH:00:00")
    }
    return format.format(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.parseDateToCreateEventFormat(): String = SimpleDateFormat(DATE_FORMAT_CREATE_EVENT).format(this)

@SuppressLint("SimpleDateFormat")
fun Date.parseDateToHourFormat(): String = SimpleDateFormat(HOUR_FORMAT_CREATE_EVENT).format(this)

@SuppressLint("SimpleDateFormat")
fun Date.parseDateToDateFormatListEvent(): String = SimpleDateFormat(DATE_FORMAT_LIST_EVENT).format(this)

@SuppressLint("SimpleDateFormat")
fun String.parseCreateEventStringToDate(): Date = SimpleDateFormat(DATE_FORMAT_CREATE_EVENT).parse(this) ?: Date()

fun Long.getTimeInDaysHourMinutes(): Triple<Int,Int,Int> {
    val days = (this/DAY_TO_MIN).toInt()
    val hours = ((this - days * DAY_TO_MIN)/ HOUR_TO_MIN).toInt()
    val minutes = this - days * DAY_TO_MIN - hours * HOUR_TO_MIN
    return Triple(days, hours, minutes.toInt())
}

fun Triple<Int,Int,Int>.getTimeInMinuteFromDaysHoursMinutes(): Long =
    this.first.toLong() * DAY_TO_MIN + this.second.toLong() * HOUR_TO_MIN + this.third.toLong()
