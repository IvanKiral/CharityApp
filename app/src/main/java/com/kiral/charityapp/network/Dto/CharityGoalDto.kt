package com.kiral.charityapp.network.Dto

import com.google.gson.annotations.SerializedName

data class CharityGoalDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("charity_id")
    val charityId: Int,

    @SerializedName("imgSrc")
    val imgSrc: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("actual_sum")
    val actualSum: Double,

    @SerializedName("goal_sum")
    val goalSum: Double,

    @SerializedName("donorDonated")
    val donorDonated: Double,

    @SerializedName("peopleDonated")
    val peopleDonated: Int
)