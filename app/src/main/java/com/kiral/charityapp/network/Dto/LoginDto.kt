package com.kiral.charityapp.network.Dto

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("email")
    val email: String
)