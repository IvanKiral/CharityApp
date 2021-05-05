package com.kiral.charityapp.network.responses

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("id")
    val id: Int
)