package com.dwicandra.storyyukk.util

import com.google.gson.Gson
import retrofit2.Response

class ErrorParse {
    companion object {
        inline fun <reified T> parse(response: Response<T>): T? {
            response.errorBody()?.charStream().let {
                return Gson().fromJson(it, T::class.java)
            }

        }
    }
}