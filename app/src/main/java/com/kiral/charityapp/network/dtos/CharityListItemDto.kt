package com.kiral.charityapp.network.dtos

import com.google.gson.annotations.SerializedName

data class CharityListItemDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("imgSrc")
    val imgSrc: String
)