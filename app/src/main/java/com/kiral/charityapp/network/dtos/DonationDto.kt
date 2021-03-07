package com.kiral.charityapp.network.dtos

import com.google.gson.annotations.SerializedName

data class DonationDto(
    @SerializedName("donor_id")
    val donorId: Int,

    @SerializedName("charity_id")
    val charityId: Int,

    @SerializedName("charity_goal_id")
    val charityGoalId: Int?,

    @SerializedName("sum")
    val sum: Double
)