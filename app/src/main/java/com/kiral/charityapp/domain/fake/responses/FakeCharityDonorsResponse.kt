package com.kiral.charityapp.domain.fake.responses

data class FakeDonorDonation(
    val name: String,
    val email: String,
    val donated: Double,
)


data class FakeCharityDonorsResponse(
    val count: Int,
    val donors: List<FakeDonorDonation>
)