package com.kiral.charityapp.network.responses

import com.google.gson.annotations.SerializedName

data class RankUpResponse(
    @SerializedName("rankUp")
    val rankUp:Boolean
)
