package com.kiral.charityapp.domain.model

data class Profile(
    var id: Int,
    val email: String,
    var name: String,
    var donations: Int,
    var region: String,
    var credit: Double,
    var categories: List<Int>,
    var regularDonationActive: Boolean,
    var regularDonationValue: Double,
    var regularDonationFrequency: Int,
    val badges: List<Badge>
)