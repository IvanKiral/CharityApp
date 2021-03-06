package com.kiral.charityapp.network.Dto

import com.google.gson.annotations.SerializedName

data class DonorDto(
    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("sum")
    val sum: Double,

    @SerializedName("badges")
    val badges: List<Int>
)