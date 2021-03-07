package com.kiral.charityapp.network.responses

import com.google.gson.annotations.SerializedName
import com.kiral.charityapp.network.dtos.CharityListItemDto

data class CharityListResponse (

    @SerializedName("count")
    val count: Int,

    @SerializedName("charities")
    val charities: List<CharityListItemDto>

)