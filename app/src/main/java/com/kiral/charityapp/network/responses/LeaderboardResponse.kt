package com.kiral.charityapp.network.responses

import com.google.gson.annotations.SerializedName
import com.kiral.charityapp.network.dtos.LeaderboardDto

data class LeaderboardResponse(
    @SerializedName("donors")
    val donors: List<LeaderboardDto>
)