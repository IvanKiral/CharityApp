package com.kiral.charityapp.domain.model

data class Profile(
    var id: Int? = null,
    val email: String,
    var name: String,
    var donations: Int,
    var credit: Float,
    var charities: String,
    var automaticDonations: Boolean,
    var automaticDonationsValue: Int,
    var automaticDonationTimeFrequency: String ="day",
    val badges: List<Badge>
)