package com.kiral.charityapp.network.Responses

import com.google.gson.annotations.SerializedName
import com.kiral.charityapp.network.Dto.DonorDto

data class DonorsResponse(
    @SerializedName("count")
    val count: Int,

    @SerializedName("donors")
    val donors: List<DonorDto>
)