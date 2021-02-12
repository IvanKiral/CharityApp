package com.kiral.charityapp.domain.fake

data class FakeDonation(
    val id: Int,
    val donorId: Int,
    val charityId: Int,
    val donationGoalId: Int?,
    val sum: Double
)

var fakeDonations = listOf(
    FakeDonation(
        id = 0,
        donorId = 1,
        charityId = 1,
        donationGoalId = null,
        sum = 50.0
    ),
    FakeDonation(
        id = 1,
        donorId = 1,
        charityId = 4,
        donationGoalId = null,
        sum = 10.0
    )


)