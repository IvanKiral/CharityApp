package com.kiral.charityapp.domain.model

data class Profile(
    val id: Int,
    val email: String,
    val name: String,
    val donations: Int,
    val credit: Float,
    val automaticDonations: Boolean,
    val automaticDonationsValue: Int,
    val automaticDonationTimeFrequency: String,
    val badges: List<Badge>
)