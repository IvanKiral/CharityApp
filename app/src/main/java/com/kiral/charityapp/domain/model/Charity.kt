package com.kiral.charityapp.domain.model

data class CharityProject(
    val id: Int,
    val name: String
)

data class Charity(
    val imgSrc: String,
    val id: Int,
    val name: String,
    val address: String,
    val description: String,
    val raised: Float,
    val peopleDonated: Int,
    val donorDonated: Double,
    val projects: List<CharityProject> = listOf()
)