package com.kiral.charityapp.network.dtos

import com.google.gson.annotations.SerializedName

data class ProjectDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("charityId")
    val charityId: Int,

    @SerializedName("imgSrc")
    val imgSrc: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("charityName")
    val charityName: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("actualSum")
    val actualSum: Double,

    @SerializedName("goalSum")
    val goalSum: Double,

    @SerializedName("donorDonated")
    val donorDonated: Double,

    @SerializedName("peopleDonated")
    val peopleDonated: Int
)