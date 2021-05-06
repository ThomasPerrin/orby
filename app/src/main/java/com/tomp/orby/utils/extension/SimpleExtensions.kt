package com.tomp.orby.utils.extension

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import java.lang.Exception
import java.math.BigDecimal
import java.util.*
import java.util.regex.Pattern

fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")

fun SpannableStringBuilder.boldAll(toBold: String?): SpannableStringBuilder {

    if (toBold.isNullOrEmpty()) return this

    val match = Pattern.compile(toBold).matcher(this)
    var listOfMatched = listOf<Pair<Int, Int>>()
    while (match.find()) {
        listOfMatched = listOfMatched.plus(Pair(match.start(), match.end()))
    }

    listOfMatched.forEach {
        if (it.first < 0 || it.second > this.length) return this
        this.setSpan(StyleSpan(Typeface.BOLD), it.first, it.second, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    }

    return this
}

fun SpannableStringBuilder.boldAllIgnoringCase(toBold: String): SpannableStringBuilder {
    val match = Pattern.compile(toBold, Pattern.CASE_INSENSITIVE).matcher(this)
    var listOfMatched = listOf<Pair<Int, Int>>()
    while (match.find()) {
        listOfMatched = listOfMatched.plus(Pair(match.start(), match.end()))
    }

    listOfMatched.forEach {
        this.setSpan(StyleSpan(Typeface.BOLD), it.first, it.second, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    }

    return this
}

fun SpannableStringBuilder.sizeAll(toSize: String?, sizePercentAllText: Float): SpannableStringBuilder {

    if (toSize.isNullOrEmpty()) return this

    val match = Pattern.compile(toSize).matcher(this)
    var listOfMatched = listOf<Pair<Int, Int>>()
    while (match.find()) {
        listOfMatched = listOfMatched.plus(Pair(match.start(), match.end()))
    }

    listOfMatched.forEach {
        if (it.first < 0 || it.second > this.length) return this
        this.setSpan(RelativeSizeSpan(sizePercentAllText), it.first, it.second, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    }

    return this
}

fun SpannableStringBuilder.sizeAllIgnoringCase(toSize: String?, sizePercentAllText: Float): SpannableStringBuilder {

    if (toSize.isNullOrEmpty()) return this

    val match = Pattern.compile(toSize, Pattern.CASE_INSENSITIVE).matcher(this)
    var listOfMatched = listOf<Pair<Int, Int>>()
    while (match.find()) {
        listOfMatched = listOfMatched.plus(Pair(match.start(), match.end()))
    }

    listOfMatched.forEach {
        if (it.first < 0 || it.second > this.length) return this
        this.setSpan(RelativeSizeSpan(sizePercentAllText), it.first, it.second, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    }

    return this
}

fun Double?.isNullOrEmpty(emptyValue: Double? = null) =
    this == null || this == emptyValue

fun Any?.toStringOrEmpty() =
    this?.toString() ?: ""

fun Any?.toStringOrNullIfEmpty() =
    this?.toString().let {
        if (it.isNullOrEmpty()) {
            null
        } else {
            it
        }
    }

fun Double?.valueOrDefault(default: Double) =
    this ?: default

fun Double?.toStringWithoutUselessDecimal() =
    this?.toBigDecimal()?.let {
        if(it.compareTo(BigDecimal.ZERO) == 0){
            BigDecimal.ZERO
        } else {
            it.stripTrailingZeros()
        }
    }?.toPlainString()
