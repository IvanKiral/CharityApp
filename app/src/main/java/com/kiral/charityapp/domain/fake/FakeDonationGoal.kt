package com.kiral.charityapp.domain.fake

data class FakeDonationGoal(
    val id: Int,
    val charityId: Int,
    val goalSum: Int,
    val actualSum: Float,
    val description: String,
)