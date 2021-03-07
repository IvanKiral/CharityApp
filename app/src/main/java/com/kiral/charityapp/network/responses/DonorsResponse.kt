package com.kiral.charityapp.network.responses

import com.google.gson.annotations.SerializedName
import com.kiral.charityapp.network.dtos.DonorDto

data class DonorsResponse(
    @SerializedName("count")
    val count: Int,

    @SerializedName("donors")
    val donors: List<DonorDto>
)