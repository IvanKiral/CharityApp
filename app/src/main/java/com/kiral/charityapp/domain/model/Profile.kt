package com.kiral.charityapp.domain.model

data class Profile(
    var id: Int? = null,
    val email: String,
    var name: String,
    var donations: Int,
    var region: String,
    var credit: Double,
    var charities: String,
    var automaticDonations: Boolean,
    var automaticDonationsValue: Double,
    var automaticDonationTimeFrequency: String ="day",
    val badges: List<Badge>
)