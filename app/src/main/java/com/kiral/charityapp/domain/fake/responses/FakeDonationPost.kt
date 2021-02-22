package com.kiral.charityapp.domain.fake.responses

data class FakeDonationPost(
    val charityId: Int,
    val donorId: Int,
    val donationGoalId: Int? = null,
    val sum: Double
)