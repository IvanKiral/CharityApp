package com.kiral.charityapp.network.dtos

import com.google.gson.annotations.SerializedName

data class  AddCreditDto(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("credit")
    val credit: Double
)