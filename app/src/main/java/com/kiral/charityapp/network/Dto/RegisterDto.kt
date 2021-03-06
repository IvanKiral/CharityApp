package com.kiral.charityapp.network.Dto

import com.google.gson.annotations.SerializedName

data class RegisterDto(
    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("region")
    val region: String,

    @SerializedName("repeat_active")
    val repeatActive: Boolean,

    @SerializedName("repeat_value")
    val repeatValue: Double,

    @SerializedName("repeat_frequency")
    val repeatFrequency: String
)