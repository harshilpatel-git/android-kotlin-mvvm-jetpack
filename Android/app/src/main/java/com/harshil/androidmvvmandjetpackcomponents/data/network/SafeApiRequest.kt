package com.harshil.androidmvvmandjetpackcomponents.data.network

import com.harshil.androidmvvmandjetpackcomponents.internal.APIException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response


// This class provides a generic response as per success and failure
abstract class SafeApiRequest {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()

        if (response.isSuccessful)
            return response.body()!!
        else {
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                } catch (e: JSONException) {
                }
                message.append("\n")
            }
            message.append("Error Code: ${response.code()}")
            throw APIException(message.toString())
        }
    }

}