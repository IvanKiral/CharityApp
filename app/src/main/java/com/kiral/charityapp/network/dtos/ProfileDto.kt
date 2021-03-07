package com.kiral.charityapp.network.dtos

import com.google.gson.annotations.SerializedName

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

    @SerializedName("donations")
    val donations: Int,

    @SerializedName("repeat_active")
    val repeatActive: Boolean,

    @SerializedName("repeat_value")
    val repeatValue: Double,

    @SerializedName("repeat_frequency")
    val repeatFrequency: Int,

    @SerializedName("badges")
    val badges: List<Int>
)