package com.harshil.androidmvvmandjetpackcomponents.internal

import kotlinx.coroutines.*

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        // For calling function parallelly or concurrently
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}