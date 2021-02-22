package com.kiral.charityapp.domain.fake.responses

data class FakeDonationGoalReponse(
    val id: Int,
    val charityId: Int,
    val donorId: Int,
    val name: String,
    val goalSum: Double,
    val actualSum: Double,
    val description: String,
    val charityName: String,
    val charityAdress: String,
    val charityImage: String,
    val peopleDonated: Int,
    val donorDonated: Double
)