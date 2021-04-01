package com.kiral.charityapp.domain.model

data class Project(
    val id: Int,
    val charityId: Int,
    val name: String,
    val description: String,
    val goalSum: Double,
    var actualSum: Double,
    val charityName: String,
    val charityAddress: String,
    val charityImage: String,
    var peopleDonated: Int,
    var donorDonated: Double
)