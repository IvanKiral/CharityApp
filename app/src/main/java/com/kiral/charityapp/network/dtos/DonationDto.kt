package com.kiral.charityapp.network.dtos

import com.google.gson.annotations.SerializedName

data class DonationDto(
    @SerializedName("userId")
    val donorId: Int,

    @SerializedName("charityId")
    val charityId: Int,

    @SerializedName("projectId")
    val charityGoalId: Int?,

    @SerializedName("sum")
    val sum: Double
)