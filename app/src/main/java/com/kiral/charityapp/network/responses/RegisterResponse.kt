package com.kiral.charityapp.network.responses

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("userId")
    val id: Int
)