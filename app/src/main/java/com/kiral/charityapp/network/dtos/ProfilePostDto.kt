package com.kiral.charityapp.network.dtos

import com.google.gson.annotations.SerializedName

data class ProfilePostDto(
    @SerializedName("userId")
    val userId: Int,

    @SerializedName("regularDonationActive")
    val regularDonationActive: Boolean? = null,

    @SerializedName("regularDonationValue")
    val regularDonationValue: Double? = null,

    @SerializedName("regularDonationFrequency")
    val regularDonationFrequency: Int? = null,

    @SerializedName("categories")
    val categories: List<Int>? = null,

    @SerializedName("credit")
    val credit: Double? = null,

    @SerializedName("region")
    val region: String? = null
)