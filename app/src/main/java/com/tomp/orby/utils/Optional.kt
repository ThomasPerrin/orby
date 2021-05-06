package com.tomp.orby.utils

data class Optional<T>(val value: T?)

fun <T> T?.carry() = Optional(this)
