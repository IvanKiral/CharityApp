package com.kiral.charityapp.network.Dto

import com.google.gson.annotations.SerializedName

data class ProjectListItemDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)


data class CharityDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("imgSrc")
    val imgSrc: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("charityStory")
    val charityStory: String,

    @SerializedName("whyToDonate")
    val whyToDonate:String,

    @SerializedName("howDonationHelps")
    val howDonationHelps: String,

    @SerializedName("raised")
    val raised: Double,

    @SerializedName("donorDonated")
    val donorDonated: Double,

    @SerializedName("peopleDonated")
    val peopleDonated: Int,

    @SerializedName("projects")
    val projects: List<ProjectListItemDto>
)