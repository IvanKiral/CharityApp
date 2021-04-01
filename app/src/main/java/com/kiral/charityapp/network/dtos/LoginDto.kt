package com.kiral.charityapp.network.dtos

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("email")
    val email: String
)