package com.kiral.charityapp.network.Responses

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id")
    val id: Int
)