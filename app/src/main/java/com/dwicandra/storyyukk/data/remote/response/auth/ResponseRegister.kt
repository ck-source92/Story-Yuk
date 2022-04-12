package com.dwicandra.storyyukk.data.remote.response.auth

import com.google.gson.annotations.SerializedName

data class ResponseRegister(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)
