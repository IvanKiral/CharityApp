package com.kiral.charityapp.domain.fake

data class FakeDonationRepeat(
    val id: Int,
    val donorId: Int,
    var active: Boolean,
    var sum: Double,
    var repeatingStatus: String
)

var fakeDonationRepeats = mutableListOf(
    FakeDonationRepeat(
        id = 0,
        donorId = 0,
        active = true,
        sum = 5.0,
        repeatingStatus = "daily"
    ),
    FakeDonationRepeat(
        id = 1,
        donorId = 1,
        active = true,
        sum = 10.0,
        repeatingStatus = "weekly"
    )
)