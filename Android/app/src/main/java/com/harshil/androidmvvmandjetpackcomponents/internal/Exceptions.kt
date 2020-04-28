package com.harshil.androidmvvmandjetpackcomponents.internal

import java.io.IOException

class APIException(message: String) : IOException(message)

class NoConnectivityException(message: String) : IOException(message)
