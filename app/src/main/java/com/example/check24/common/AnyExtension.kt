package com.example.check24.common

inline fun <reified T : Any> Any.cast(): T {
    return this as T
}