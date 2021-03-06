package com.kiral.charityapp.network.Responses

import com.google.gson.annotations.SerializedName
import com.kiral.charityapp.network.Dto.CharityListItemDto

data class CharityListResponse (

    @SerializedName("count")
    val count: Int,

    @SerializedName("charities")
    val charities: List<CharityListItemDto>

)