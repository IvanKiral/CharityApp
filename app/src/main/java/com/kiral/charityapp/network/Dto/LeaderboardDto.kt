package com.kiral.charityapp.network.Dto

import com.google.gson.annotations.SerializedName

data class LeaderboardDto(
    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("sum")
    val sum: Double
)