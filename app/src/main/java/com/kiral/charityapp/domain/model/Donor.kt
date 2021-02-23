package com.kiral.charityapp.domain.model

data class Donor(
    val name: String,
    val email: String,
    val donated: Double,
    val badges: List<Badge>
)