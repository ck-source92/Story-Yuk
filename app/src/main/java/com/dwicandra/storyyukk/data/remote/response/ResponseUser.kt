package com.dwicandra.storyyukk.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseUser(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("password")
    val password: String? = null,
)