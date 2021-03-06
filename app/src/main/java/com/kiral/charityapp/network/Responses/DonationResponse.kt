package com.kiral.charityapp.network.Responses

import com.google.gson.annotations.SerializedName

data class DonationResponse(
    @SerializedName("data")
    val data: String
)