package com.kiral.charityapp.network.Responses

import com.google.gson.annotations.SerializedName
import com.kiral.charityapp.network.Dto.LeaderboardDto

data class LeaderboardResponse(
    @SerializedName("donors")
    val donors: List<LeaderboardDto>
)