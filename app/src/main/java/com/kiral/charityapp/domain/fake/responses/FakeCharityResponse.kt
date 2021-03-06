package com.kiral.charityapp.domain.fake.responses

data class FakeProjectList(
    val id: Int,
    val name: String
)

data class FakeCharityResponse(
    val id: Int,
    val name: String,
    val imgSrc: String,
    val address: String,
    val region: String,
    val description: String,
    val raised: Double,
    val peopleDonated: Int,
    val donorDonated: Double,
    //val donorId: Int,
    val projects: List<FakeProjectList>
)