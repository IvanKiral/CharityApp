package com.kiral.charityapp.domain.fake

data class FakeDonationRepeat(
    val id: Int,
    val donorId: Int,
    var active: Boolean,
    var sum: Int,
    var repeatingStatus: String
)

var fakeDonationRepeats = mutableListOf(
    FakeDonationRepeat(
        id = 0,
        donorId = 0,
        active = true,
        sum = 5,
        repeatingStatus = "daily"
    ),
    FakeDonationRepeat(
        id = 1,
        donorId = 1,
        active = true,
        sum = 10,
        repeatingStatus = "weekly"
    )
)