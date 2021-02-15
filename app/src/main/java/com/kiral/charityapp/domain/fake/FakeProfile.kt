package com.kiral.charityapp.domain.fake

data class FakeProfile(
    val id: Int,
    val name: String,
    var region: String,
    var credit: Double,
    val email: String,
    //var categories ??
    //var Birthday
)

var fakeProfiles = mutableListOf(
    FakeProfile(
        id = 0,
        name = "Alžbeta Pekná",
        region = "cze",
        credit = 150.0,
        email = "alzbeta@email.com"
    ),
    FakeProfile(
        id = 1,
        name = "Ivan Horváth",
        region = "svk",
        credit = 100.0,
        email = "ivan@email.com"
    )
)
