package com.kiral.charityapp.network.dtos

import com.google.gson.annotations.SerializedName
import java.util.*

data class ProfileDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("credit")
    val credit: Double,

    @SerializedName("region")
    val region: String,

    @SerializedName("categories")
    val categories: List<Int>,

    @SerializedName("birthday")
    val birthday: Date,

    @SerializedName("donations")
    val donations: Int,

    @SerializedName("regularDonationActive")
    val repeatActive: Boolean,

    @SerializedName("regularDonationValue")
    val repeatValue: Double,

    @SerializedName("regularDonationFrequency")
    val repeatFrequency: Int,

    @SerializedName("userBadges")
    val badges: List<Int>
)