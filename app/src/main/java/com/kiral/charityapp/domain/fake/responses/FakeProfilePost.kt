package com.kiral.charityapp.domain.fake.responses

import com.kiral.charityapp.domain.model.Badge

data class FakeProfilePost(
    val id: Int? = null,
    val name: String,
    val region: String,
    val donations: Int? = null,
    val credit: Double,
    val charities: String = "",
    val email: String,
    val donationRepeat: Boolean,
    val donationRepeatValue: Double,
    val donationRepeatFrequency: String,
    val badges: List<Badge> = listOf()
)