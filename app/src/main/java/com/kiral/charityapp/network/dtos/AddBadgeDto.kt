package com.kiral.charityapp.network.dtos

import com.google.gson.annotations.SerializedName

data class  AddBadgeDto(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("badgeId")
    val badgeId: Int
)