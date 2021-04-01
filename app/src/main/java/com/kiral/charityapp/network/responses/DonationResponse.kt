package com.kiral.charityapp.network.responses

import com.google.gson.annotations.SerializedName

data class DonationResponse(
    @SerializedName("data")
    val data: String
)