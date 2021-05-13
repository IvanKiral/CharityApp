package com.kiral.charityapp.domain.model

data class CharityProject(
    val id: Int,
    val name: String
)

data class Charity(
    val imgSrc: String,
    var id: Int,
    val name: String,
    val address: String,
    val description: String,
    val howDonationHelps: String,
    val whyToDonate: String,
    var raised: Double,
    val peopleDonated: Int,
    var donorDonated: Double,
    //val donorId: Int,
    val projects: List<CharityProject> = listOf()
)