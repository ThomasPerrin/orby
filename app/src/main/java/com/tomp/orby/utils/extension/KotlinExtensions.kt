package com.tomp.orby.utils.extension

import android.util.Log
import com.google.gson.Gson
import okhttp3.ResponseBody
import java.math.MathContext
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

@Suppress("UNCHECKED_CAST")
fun <R> readInstanceProperty(instance: Any, propertyName: String): R {
    val property = instance::class.memberProperties
        // don't cast here to <Any, R>, it would succeed silently
        .first { it.name == propertyName } as KProperty1<Any, *>
    // force a invalid cast exception if incorrect type here
    return property.get(instance) as R
}

fun Double?.round(decimal: Int) =
    this?.toBigDecimal(MathContext(decimal))?.toDouble()

inline fun <reified T> ResponseBody?.errorBodyToObject(): T? =
    if (T::class.java != Void::class.java) {
        this?.string()?.let {
            try {
                Gson().fromJson<T>(it, T::class.java)
            } catch (e: Exception) {
                Log.e("Exception","$e")
                null
            }
        }
    } else {
        null
    }
