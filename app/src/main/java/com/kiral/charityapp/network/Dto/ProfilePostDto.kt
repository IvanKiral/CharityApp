package com.kiral.charityapp.network.Dto

import com.google.gson.annotations.SerializedName

data class ProfilePostDto (
    @SerializedName("userId")
    val userId: Int,

    @SerializedName("regularDonationActive")
    val regularDonationActive: Boolean?,

    @SerializedName("regularDonationValue")
    val regularDonationValue: Double?,

    @SerializedName("regularDonationFrequency")
    val regularDonationFrequency: Int?,

    @SerializedName("categories")
    val categories: List<Int>?
)