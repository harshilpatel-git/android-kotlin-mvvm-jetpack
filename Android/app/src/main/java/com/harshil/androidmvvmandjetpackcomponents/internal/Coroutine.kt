package com.harshil.androidmvvmandjetpackcomponents.internal

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Coroutine {

    // Used lambda and higher order function here
    fun main(work: suspend () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            work()
        }
    }
}